import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class FlightFetcher {
    
    // Methode principale pour recuperer et stocker les donnees des vols
    public static void main(String[] args) {
        if (args.length != 1 && args.length != 2) {
            System.out.println("Usage: java FlightFetcher <dep_iata> <arr_iata> OR <flight_iata>");
            return;
        }

        String apiKey = "REDACTED";
        String urlStr = "";

        if (args.length == 2) {
            // Si 2 paramètres, recuperer les vols entre deux aeroports
            String depIata = args[0];
            String arrIata = args[1];
            urlStr = "http://api.aviationstack.com/v1/flights?access_key=" + apiKey + "&dep_iata=" + depIata + "&arr_iata=" + arrIata;
        } else {
            // Si 1 paramètre, recuperer les vols selon le numero de vol
            String flightIata = args[0];
            urlStr = "http://api.aviationstack.com/v1/flights?access_key=" + apiKey + "&flight_iata=" + flightIata;
        }

        try {
            // Appel à l'API AviationStack
            String response = makeApiRequest(urlStr);

            // Analyse de la reponse JSON
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray flightsData = jsonResponse.getJSONArray("data");

            // Connexion à la base de donnees SQLite
            Connection connection = DriverManager.getConnection("jdbc:sqlite:/var/www/html/flights.db");

            // Parcours des donnees de vols et insertion dans la base de donnees
            for (int i = 0; i < flightsData.length(); i++) {
                JSONObject flight = flightsData.getJSONObject(i);

                String flightNumber = flight.optJSONObject("flight").optString("iata", "N/A");
                String departureAirportIata = flight.optJSONObject("departure").optString("iata", "N/A");
                String arrivalAirportIata = flight.optJSONObject("arrival").optString("iata", "N/A");
                String airline = flight.optJSONObject("airline").optString("name", "N/A");
                String departureAirport = flight.optJSONObject("departure").optString("airport", "N/A");
                String arrivalAirport = flight.optJSONObject("arrival").optString("airport", "N/A");
                String flightDate = flight.optString("flight_date", "N/A");
                String scheduledDepartureTime = flight.optJSONObject("departure").optString("scheduled", "N/A");
                String scheduledArrivalTime = flight.optJSONObject("arrival").optString("scheduled", "N/A");
                String estimatedDepartureTime = flight.optJSONObject("departure").optString("estimated", "N/A");
                String estimatedArrivalTime = flight.optJSONObject("arrival").optString("estimated", "N/A");
                String departureTerminal = flight.optJSONObject("departure").optString("terminal", "N/A");
                String arrivalTerminal = flight.optJSONObject("arrival").optString("terminal", "N/A");
                JSONObject aircraft = flight.optJSONObject("aircraft");
                String aircraftRegistration = aircraft != null ? aircraft.optString("registration", "N/A") : "N/A";
                String aircraftIata = aircraft != null ? aircraft.optString("iata", "N/A") : "N/A";
                JSONObject live = flight.optJSONObject("live");
                String lastUpdated = live != null ? live.optString("updated", "N/A") : "N/A";
                Double latitude = live != null ? live.optDouble("latitude", 0.0) : 0.0;
                Double longitude = live != null ? live.optDouble("longitude", 0.0) : 0.0;
     

                storeFlight(connection, flightNumber, departureAirportIata, arrivalAirportIata, airline,
                        departureAirport, arrivalAirport, flightDate, scheduledDepartureTime, scheduledArrivalTime,
                        estimatedDepartureTime, estimatedArrivalTime, departureTerminal, arrivalTerminal,
                        aircraftRegistration, aircraftIata, lastUpdated, latitude, longitude);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fonction pour envoyer une requête HTTP à l'API AviationStack
    public static String makeApiRequest(String urlStr) throws Exception {
        @SuppressWarnings("deprecation")
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    // Fonction pour inserer un vol dans la base de donnees
    public static void storeFlight(Connection connection, String flightNumber, String departureAirportIata, 
                                   String arrivalAirportIata, String airline, String departureAirport, 
                                   String arrivalAirport, String flightDate, String scheduledDepartureTime, 
                                   String scheduledArrivalTime, String estimatedDepartureTime, 
                                   String estimatedArrivalTime, String departureTerminal, String arrivalTerminal,
                                   String aircraftRegistration, String aircraftIata, String lastUpdated,
                                   Double latitude, Double longitude) {
        try {
            // Formater les dates (si elles sont vides, on les laisse nulles)
            String scheduledDepartureTimeFormatted = formatDateTime(scheduledDepartureTime);
            String scheduledArrivalTimeFormatted = formatDateTime(scheduledArrivalTime);
            String estimatedDepartureTimeFormatted = formatDateTime(estimatedDepartureTime);
            String estimatedArrivalTimeFormatted = formatDateTime(estimatedArrivalTime);
            String lastUpdatedFormatted = formatDateTime(lastUpdated);

            // Verifier si un vol existe dejà avec le même numero et heure de depart
            String query = "SELECT id FROM flights WHERE flightNumber = ? AND scheduledDepartureTime = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, flightNumber);
            stmt.setString(2, scheduledDepartureTimeFormatted);
            var result = stmt.executeQuery();

            // Si un vol existe, on le supprime
            if (result.next()) {
                String deleteQuery = "DELETE FROM flights WHERE id = ?";
                PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
                deleteStmt.setInt(1, result.getInt("id"));
                deleteStmt.executeUpdate();
            }

            // Insertion du vol dans la base de donnees
            String insertQuery = "INSERT INTO flights (flightNumber, departureAirportIata, arrivalAirportIata, airline, " +
                    "departureAirport, arrivalAirport, flightDate, scheduledDepartureTime, scheduledArrivalTime, " +
                    "estimatedDepartureTime, estimatedArrivalTime, departureTerminal, arrivalTerminal, " +
                    "aircraftRegistration, aircraftIata, lastUpdated, latitude, longitude) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, flightNumber);
            insertStmt.setString(2, departureAirportIata);
            insertStmt.setString(3, arrivalAirportIata);
            insertStmt.setString(4, airline);
            insertStmt.setString(5, departureAirport);
            insertStmt.setString(6, arrivalAirport);
            insertStmt.setString(7, flightDate);
            insertStmt.setString(8, scheduledDepartureTimeFormatted);
            insertStmt.setString(9, scheduledArrivalTimeFormatted);
            insertStmt.setString(10, estimatedDepartureTimeFormatted);
            insertStmt.setString(11, estimatedArrivalTimeFormatted);
            insertStmt.setString(12, departureTerminal);
            insertStmt.setString(13, arrivalTerminal);
            insertStmt.setString(14, aircraftRegistration);
            insertStmt.setString(15, aircraftIata);
            insertStmt.setString(16, lastUpdatedFormatted);
            insertStmt.setDouble(17, latitude);
            insertStmt.setDouble(18, longitude);
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fonction pour formater une date au format "YYYY-MM-DD HH:mm"
    public static String formatDateTime(String dateTime) {
        if (dateTime == "N/A" || dateTime.isEmpty()) {
            return "N/A";
        }
        try {
            return dateTime.substring(0, 16).replace("T", " ");
        } catch (Exception e) {
            return "N/A";
        }
    }
}