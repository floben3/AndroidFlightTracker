package com.example.sophiartflighttracker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlightsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlightsAdapter adapter;
    private List<Flight> flightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        flightList = new ArrayList<>();
        adapter = new FlightsAdapter(flightList);
        recyclerView.setAdapter(adapter);

        // Récupérer les données envoyées par MainActivity
        String departureAirport = getIntent().getStringExtra("dep_iata");
        String arrivalAirport = getIntent().getStringExtra("arr_iata");
        String flightNumber = getIntent().getStringExtra("flight_iata");

        // Lancer la requête API si les données sont présentes
        if (departureAirport != null && arrivalAirport != null) {
            // Si on a les aéroports, lancer la recherche par aéroports
            executeRequest("&dep_iata=" + departureAirport + "&arr_iata=" + arrivalAirport);
        } else if (flightNumber != null) {
            // Si on a le numéro de vol, lancer la recherche par numéro de vol
            executeRequest("&flight_iata=" + flightNumber);
        }
    }

    private void executeRequest(final String params) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String result = makeApiRequest(params);

                // Une fois la tâche terminée, mettre à jour l'interface utilisateur sur le thread principal
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            parseAndDisplayFlights(result);
                        } else {
                            Toast.makeText(FlightsActivity.this, "Erreur lors du chargement des données", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private String makeApiRequest(String params) {
        String urlString = "http://10.3.122.123?access_key=dee39d73a02f584cab2302c5ecabccf5";  // Remplacer par l'URL de l'API
        String paramString = params;

        URL url = null;
        try {
            // Construire l'URL complète avec les paramètres
            url = new URL(urlString + paramString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Lire la réponse
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            int read;
            char[] buffer = new char[1024];
            while ((read = reader.read(buffer)) != -1) {
                response.append(buffer, 0, read);
            }
            reader.close();

            return response.toString();
        } catch (Exception e) {
            Log.e("FlightsActivity", "Erreur de connexion", e);
            Log.d("API_URL", "Requête URL : " + url);
            return null;
        }
    }

    private void parseAndDisplayFlights(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray flightsArray = jsonObject.getJSONArray("data");

            flightList.clear();
            for (int i = 0; i < flightsArray.length(); i++) {
                JSONObject flightObject = flightsArray.getJSONObject(i);

                // Création de l'objet Flight avec les données live
                Flight flight = new Flight(
                        flightObject.getString("flightNumber"),
                        flightObject.getString("airline"),
                        flightObject.getString("departureAirportIata"),
                        flightObject.getString("arrivalAirportIata"),
                        flightObject.getString("scheduledDepartureTime"),
                        flightObject.getString("scheduledArrivalTime"),
                        flightObject.getString("estimatedDepartureTime"),
                        flightObject.getString("estimatedArrivalTime"),
                        flightObject.getString("departureTerminal"),
                        flightObject.getString("arrivalTerminal"),
                        flightObject.getString("aircraftRegistration"),
                        flightObject.getString("aircraftIata"),
                        flightObject.getString("lastUpdated"),
                        flightObject.getDouble("latitude"),
                        flightObject.getDouble("longitude")
                );
                flightList.add(flight);
            }

            // Notifier l'adaptateur
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("FlightsActivity", "Erreur de parsing", e);
            Toast.makeText(FlightsActivity.this, "Erreur lors du traitement des données", Toast.LENGTH_SHORT).show();
        }
    }
}
