package com.example.sophiartflighttracker;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.FlightViewHolder> {

    private final List<Flight> flightList;
    private static Map<String, String> airportMap;

    public FlightsAdapter(List<Flight> flightList) {
        this.flightList = flightList;
        initializeAirportMap();  // Initialisation de la map dans le constructeur
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = flightList.get(position);

        holder.flightNumberAndAircraftRegistration.setText("Vol " + flight.getFlightNumber() + "  |  Avion " + flight.getAircraftRegistration());
        holder.airlineAndAircraftIata.setText(flight.getAirline() + " " + flight.getAircraftIata());
        holder.departureAirport.setText(formatAirport(flight.getDepartureAirport()));
        holder.arrivalAirport.setText(formatAirport(flight.getArrivalAirport()));
        holder.departureDate.setText(extractDate(flight.getScheduledDepartureTime()));
        holder.arrivalDate.setText(extractDate(flight.getScheduledArrivalTime()));
        holder.scheduledDepartureTime.setText("Prévu\n" + extractTime(flight.getScheduledDepartureTime()));
        holder.scheduledArrivalTime.setText("Prévu\n" + extractTime(flight.getScheduledArrivalTime()));
        holder.estimatedDepartureTime.setText("Estimé\n" + extractTime(flight.getEstimatedDepartureTime()));
        holder.estimatedArrivalTime.setText("Estimé\n" + extractTime(flight.getEstimatedArrivalTime()));
        holder.departureTerminal.setText("Terminal " + flight.getDepartureTerminal());
        holder.arrivalTerminal.setText("Terminal " + flight.getArrivalTerminal());

        holder.itemView.setOnClickListener(v -> {
            // Ajouter une animation d'agrandissement, et de la couleur
            holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0")); // Gris plus foncé
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1f, 0.95f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1f, 0.95f, 1f);
            set.playTogether(scaleX, scaleY);
            set.setDuration(200);
            set.start();

            Intent intent = new Intent(holder.itemView.getContext(), FlightDetailActivity.class);
            intent.putExtra("flight", flight);// Transfert de l'objet Flight
            holder.itemView.getContext().startActivity(intent);
        });
    }

    // Fonction pour formater l'aéroport avec son code IATA
    public static String formatAirport(String iataCode) {
        String airportName = airportMap.get(iataCode);
        if (airportName != null) {
            return airportName + " (" + iataCode + ")";
        } else {
            return iataCode; // Si le code IATA n'est pas trouvé dans la map, renvoie le code seul
        }
    }

    // Map des aéroports avec leurs codes IATA
    private void initializeAirportMap() {
        airportMap = new HashMap<>();

        // Aéroports en France
        airportMap.put("CDG", "Paris");
        airportMap.put("ORY", "Paris");
        airportMap.put("BVA", "Paris");
        airportMap.put("LYS", "Lyon");
        airportMap.put("MRS", "Marseille");
        airportMap.put("NCE", "Nice");
        airportMap.put("TLS", "Toulouse");
        airportMap.put("BOD", "Bordeaux");
        airportMap.put("NTE", "Nantes");
        airportMap.put("LIL", "Lille");
        airportMap.put("SXB", "Strasbourg");
        airportMap.put("MPL", "Montpellier");
        airportMap.put("RNS", "Rennes");
        airportMap.put("TLN", "Toulon");
        airportMap.put("BIQ", "Biarritz");
        airportMap.put("AJA", "Ajaccio");
        airportMap.put("BIA", "Bastia");
        airportMap.put("CLY", "Calvi");
        airportMap.put("FSC", "Figari");
        airportMap.put("CFE", "Clermont-Ferrand");
        airportMap.put("PUF", "Pau");
        airportMap.put("PGF", "Perpignan");
        airportMap.put("GNB", "Grenoble");
        airportMap.put("CMF", "Chambéry");
        airportMap.put("ANE", "Angers");
        airportMap.put("AVN", "Avignon");
        airportMap.put("BES", "Brest");
        airportMap.put("CFR", "Caen");
        airportMap.put("CCF", "Carcassonne");
        airportMap.put("DIJ", "Dijon");
        airportMap.put("LRH", "La Rochelle");
        airportMap.put("LIG", "Limoges");
        airportMap.put("LRT", "Lorient");
        airportMap.put("ETZ", "Metz");
        airportMap.put("MLH", "Mulhouse");
        airportMap.put("FNI", "Nîmes");
        airportMap.put("PIS", "Poitiers");
        airportMap.put("UIP", "Quimper");
        airportMap.put("RDZ", "Rodez");
        airportMap.put("LDE", "Tarbes");
        airportMap.put("TUF", "Tours");
        airportMap.put("DOL", "Deauville");
        airportMap.put("LEH", "Le Havre");
        airportMap.put("BVE", "Brive");
        airportMap.put("DCM", "Castres");
        airportMap.put("AUR", "Aurillac");
        airportMap.put("EGC", "Bergerac");
        airportMap.put("CQF", "Calais");
        airportMap.put("PGX", "Périgueux");
        airportMap.put("SBK", "St-Brieuc");
        airportMap.put("LBI", "Albi");
        airportMap.put("CHR", "Châteauroux");
        airportMap.put("LPY", "Le Puy");
        airportMap.put("LAI", "Lannion");
        airportMap.put("DLE", "Dole");
        airportMap.put("AGF", "Agen");
        airportMap.put("NCY", "Annecy");
        airportMap.put("BZR", "Béziers");
        airportMap.put("LBI", "Tarbes");

        // Aéroports internationaux
        airportMap.put("JFK", "New York");
        airportMap.put("LGA", "New York");
        airportMap.put("EWR", "Newark");
        airportMap.put("LHR", "London");
        airportMap.put("LGW", "London");
        airportMap.put("STN", "London");
        airportMap.put("LCY", "London");
        airportMap.put("HND", "Tokyo");
        airportMap.put("NRT", "Tokyo");
        airportMap.put("KIX", "Osaka");
        airportMap.put("DXB", "Dubai");
        airportMap.put("AUH", "Abu Dhabi");
        airportMap.put("LAX", "Los Angeles");
        airportMap.put("SFO", "San Francisco");
        airportMap.put("ORD", "Chicago");
        airportMap.put("ATL", "Atlanta");
        airportMap.put("SIN", "Singapore");
        airportMap.put("HKG", "Hong Kong");
        airportMap.put("SYD", "Sydney");
        airportMap.put("MEL", "Melbourne");
        airportMap.put("BNE", "Brisbane");
        airportMap.put("YYZ", "Toronto");
        airportMap.put("YVR", "Vancouver");
        airportMap.put("YUL", "Montreal");
        airportMap.put("FRA", "Frankfurt");
        airportMap.put("MUC", "Munich");
        airportMap.put("BER", "Berlin");
        airportMap.put("AMS", "Amsterdam");
        airportMap.put("MAD", "Madrid");
        airportMap.put("BCN", "Barcelona");
        airportMap.put("FCO", "Rome");
        airportMap.put("MXP", "Milan");
        airportMap.put("IST", "Istanbul");
        airportMap.put("ESB", "Ankara");
        airportMap.put("PEK", "Beijing");
        airportMap.put("PVG", "Shanghai");
        airportMap.put("ICN", "Seoul");
        airportMap.put("BKK", "Bangkok");
        airportMap.put("BOM", "Mumbai");
        airportMap.put("DEL", "Delhi");
        airportMap.put("JNB", "Johannesburg");
        airportMap.put("CPT", "Cape Town");
        airportMap.put("MEX", "Mexico City");
        airportMap.put("CUN", "Cancun");
        airportMap.put("GIG", "Rio de Janeiro");
        airportMap.put("GRU", "São Paulo");
        airportMap.put("EZE", "Buenos Aires");
    }

    public static String extractDate(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            return "";
        }
        String datePart = dateTime.split(" ")[0];
        String[] dateComponents = datePart.split("-");

        if (dateComponents.length != 3) {
            return "";
        }
        String day = dateComponents[2];
        int month = Integer.parseInt(dateComponents[1]);
        String[] months = {
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        };
        return day + " " + months[month - 1];
    }

    public static String extractTime(String dateTime) {
        if (dateTime == null || !dateTime.contains(" ")) {
            return dateTime;
        }
        return dateTime.split(" ")[1];
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    static class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView flightNumberAndAircraftRegistration, airlineAndAircraftIata;
        TextView departureAirport, arrivalAirport;
        TextView departureDate, arrivalDate;
        TextView scheduledDepartureTime, scheduledArrivalTime;
        TextView estimatedDepartureTime, estimatedArrivalTime;
        TextView departureTerminal, arrivalTerminal;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            flightNumberAndAircraftRegistration = itemView.findViewById(R.id.flightNumberAndAircraftRegistration);
            airlineAndAircraftIata = itemView.findViewById(R.id.airlineAndAircraftIata);
            departureAirport = itemView.findViewById(R.id.departureAirport);
            arrivalAirport = itemView.findViewById(R.id.arrivalAirport);
            departureDate = itemView.findViewById(R.id.departureDate);
            arrivalDate = itemView.findViewById(R.id.arrivalDate);
            scheduledDepartureTime = itemView.findViewById(R.id.scheduledDepartureTime);
            scheduledArrivalTime = itemView.findViewById(R.id.scheduledArrivalTime);
            estimatedDepartureTime = itemView.findViewById(R.id.estimatedDepartureTime);
            estimatedArrivalTime = itemView.findViewById(R.id.estimatedArrivalTime);
            departureTerminal = itemView.findViewById(R.id.departureTerminal);
            arrivalTerminal = itemView.findViewById(R.id.arrivalTerminal);
        }
    }
}
