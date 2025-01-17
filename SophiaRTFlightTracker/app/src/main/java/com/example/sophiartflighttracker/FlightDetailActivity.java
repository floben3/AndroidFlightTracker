package com.example.sophiartflighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class FlightDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView flightNumberAndAircraftRegistration;
    private TextView airlineAndAircraftIata;
    private TextView departureAirport;
    private TextView departureDate;
    private TextView scheduledDepartureTime;
    private TextView estimatedDepartureTime;
    private TextView departureTerminal;
    private TextView arrivalAirport;
    private TextView arrivalDate;
    private TextView scheduledArrivalTime;
    private TextView estimatedArrivalTime;
    private TextView arrivalTerminal;
    private TextView lastUpdated;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);

        // Initialisation des TextViews
        flightNumberAndAircraftRegistration = findViewById(R.id.flightNumberAndAircraftRegistration);
        airlineAndAircraftIata = findViewById(R.id.airlineAndAircraftIata);
        departureAirport = findViewById(R.id.departureAirport);
        departureDate = findViewById(R.id.departureDate);
        scheduledDepartureTime = findViewById(R.id.scheduledDepartureTime);
        estimatedDepartureTime = findViewById(R.id.estimatedDepartureTime);
        departureTerminal = findViewById(R.id.departureTerminal);
        arrivalAirport = findViewById(R.id.arrivalAirport);
        arrivalDate = findViewById(R.id.arrivalDate);
        scheduledArrivalTime = findViewById(R.id.scheduledArrivalTime);
        estimatedArrivalTime = findViewById(R.id.estimatedArrivalTime);
        arrivalTerminal = findViewById(R.id.arrivalTerminal);
        lastUpdated = findViewById(R.id.lastUpdated);

        // Récupérer les données passées depuis l'adapter
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("flight")) {
            Flight flight = (Flight) intent.getSerializableExtra("flight");

            // Mise à jour des TextViews
            flightNumberAndAircraftRegistration.setText("Vol " + flight.getFlightNumber() + "  |  Avion " + flight.getAircraftRegistration());
            airlineAndAircraftIata.setText(flight.getAirline() + " " + flight.getAircraftIata());
            departureAirport.setText(FlightsAdapter.formatAirport(flight.getDepartureAirport()));
            arrivalAirport.setText(FlightsAdapter.formatAirport(flight.getArrivalAirport()));
            departureDate.setText(FlightsAdapter.extractDate(flight.getScheduledDepartureTime()));
            arrivalDate.setText(FlightsAdapter.extractDate(flight.getScheduledArrivalTime()));
            scheduledDepartureTime.setText("Prévu\n" + FlightsAdapter.extractTime(flight.getScheduledDepartureTime()));
            scheduledArrivalTime.setText("Prévu\n" + FlightsAdapter.extractTime(flight.getScheduledArrivalTime()));
            estimatedDepartureTime.setText("Estimé\n" + FlightsAdapter.extractTime(flight.getEstimatedDepartureTime()));
            estimatedArrivalTime.setText("Estimé\n" + FlightsAdapter.extractTime(flight.getEstimatedArrivalTime()));
            departureTerminal.setText("Terminal " + flight.getDepartureTerminal());
            arrivalTerminal.setText("Terminal " + flight.getArrivalTerminal());

            // Vérification des coordonnées de l'avion
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            if (!Objects.equals(flight.getLastUpdated(), "N/A") && flight.getLatitude() != 0 && flight.getLongitude() != 0) {
                // Si les coordonnées sont disponibles, afficher le message et la carte
                String lastUpdatedDate = FlightsAdapter.extractDate(flight.getLastUpdated());
                String lastUpdatedTime = FlightsAdapter.extractTime(flight.getLastUpdated());
                lastUpdated.setText("Emplacement au " + lastUpdatedDate + " à " + lastUpdatedTime + ".");

                if (mapFragment != null) {
                    mapFragment.getMapAsync(this);
                }
            } else {
                // Si les coordonnées ne sont pas disponibles, afficher un message d'erreur et cacher la carte
                lastUpdated.setText("Aucune information disponible concernant la position de l'avion");

                if (mapFragment != null) {
                    mapFragment.getView().setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Ajout du marker et déplacement de la caméra si les coordonnées sont disponibles
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("flight")) {
            Flight flight = (Flight) intent.getSerializableExtra("flight");
            if (flight.getLatitude() != 0 && flight.getLongitude() != 0) {
                LatLng flightLocation = new LatLng(flight.getLatitude(), flight.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(flightLocation).title("Position de l'avion"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(flightLocation, 5));
            }
        }
    }
}