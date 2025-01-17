package com.example.sophiartflighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private LinearLayout formAirports, formFlightNumber;
    private AutoCompleteTextView departureAirport, arrivalAirport;
    private EditText flightNumber;
    private Button btnShowFlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        radioGroup = findViewById(R.id.radioGroup);
        formAirports = findViewById(R.id.form_airports);
        formFlightNumber = findViewById(R.id.form_flight_number);
        departureAirport = findViewById(R.id.departureAirport);
        arrivalAirport = findViewById(R.id.arrivalAirport);
        flightNumber = findViewById(R.id.flightNumber);
        btnShowFlights = findViewById(R.id.btnShowFlights);

// Liste des aéroports majeurs avec codes IATA
        String[] airports = {
                "Paris Charles de Gaulle (CDG)", "Paris Orly (ORY)", "Paris Beauvais (BVA)",
                "Lyon Saint-Exupéry (LYS)", "Marseille Provence (MRS)", "Nice Côte d'Azur (NCE)",
                "Toulouse Blagnac (TLS)", "Bordeaux Mérignac (BOD)", "Nantes Atlantique (NTE)",
                "Lille Lesquin (LIL)", "Strasbourg Entzheim (SXB)", "Montpellier Méditerranée (MPL)",
                "Rennes Saint-Jacques (RNS)", "Toulon Hyères (TLN)", "Biarritz Pays Basque (BIQ)",
                "Ajaccio Napoléon Bonaparte (AJA)", "Bastia Poretta (BIA)", "Calvi Sainte-Catherine (CLY)",
                "Figari Sud Corse (FSC)", "Clermont-Ferrand Auvergne (CFE)", "Pau Pyrénées (PUF)",
                "Perpignan Rivesaltes (PGF)", "Grenoble Alpes-Isère (GNB)", "Chambéry Savoie (CMF)",
                "Angers Loire (ANE)", "Avignon Provence (AVN)", "Brest Bretagne (BES)",
                "Caen Carpiquet (CFR)", "Carcassonne Salvaza (CCF)", "Dijon Bourgogne (DIJ)",
                "La Rochelle Île de Ré (LRH)", "Limoges Bellegarde (LIG)", "Lorient Bretagne Sud (LRT)",
                "Metz-Nancy-Lorraine (ETZ)", "Mulhouse EuroAirport Basel-Mulhouse-Freiburg (MLH)",
                "Nîmes Garons (FNI)", "Poitiers Biard (PIS)", "Quimper Cornouaille (UIP)",
                "Rodez Aveyron (RDZ)", "Tarbes Lourdes Pyrénées (LDE)", "Tours Val de Loire (TUF)",
                "Deauville Normandie (DOL)", "Le Havre Octeville (LEH)", "Brive Vallée de la Dordogne (BVE)",
                "Castres Mazamet (DCM)", "Aurillac Tronquières (AUR)", "Bergerac Dordogne Périgord (EGC)",
                "Calais Dunkerque (CQF)", "Périgueux Bassillac (PGX)", "St-Brieuc Armor (SBK)",
                "Albi Le Sequestre (LBI)", "Châteauroux Centre (CHR)", "Le Puy-Loudes (LPY)",
                "Lannion Côte de Granit (LAI)", "Dole Jura (DLE)", "Agen La Garenne (AGF)",
                "Annecy Mont-Blanc (NCY)", "Béziers Cap d'Agde (BZR)", "Tarbes Laloubère (LBI)",
                "New York John F. Kennedy (JFK)", "New York LaGuardia (LGA)", "Newark (EWR)",
                "London Heathrow (LHR)", "London Gatwick (LGW)", "London Stansted (STN)", "London City (LCY)",
                "Tokyo Haneda (HND)", "Tokyo Narita (NRT)", "Osaka Kansai (KIX)",
                "Dubai (DXB)", "Abu Dhabi (AUH)",
                "Los Angeles (LAX)", "San Francisco (SFO)", "Chicago O'Hare (ORD)", "Atlanta (ATL)",
                "Singapore Changi (SIN)", "Hong Kong (HKG)",
                "Sydney (SYD)", "Melbourne (MEL)", "Brisbane (BNE)",
                "Toronto Pearson (YYZ)", "Vancouver (YVR)", "Montreal (YUL)",
                "Frankfurt (FRA)", "Munich (MUC)", "Berlin Brandenburg (BER)",
                "Amsterdam Schiphol (AMS)",
                "Madrid Barajas (MAD)", "Barcelona El Prat (BCN)",
                "Rome Fiumicino (FCO)", "Milan Malpensa (MXP)",
                "Istanbul (IST)", "Ankara (ESB)",
                "Beijing Capital (PEK)", "Shanghai Pudong (PVG)",
                "Seoul Incheon (ICN)",
                "Bangkok Suvarnabhumi (BKK)",
                "Mumbai (BOM)", "Delhi (DEL)",
                "Johannesburg (JNB)", "Cape Town (CPT)",
                "Mexico City (MEX)", "Cancun (CUN)",
                "Rio de Janeiro (GIG)", "São Paulo (GRU)",
                "Buenos Aires (EZE)"
        };

        // Configuration des AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, airports);
        departureAirport.setAdapter(adapter);
        arrivalAirport.setAdapter(adapter);

        // Gestion du changement de type de recherche
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_airports) {
                formAirports.setVisibility(View.VISIBLE);
                formFlightNumber.setVisibility(View.INVISIBLE);
            } else if (checkedId == R.id.radio_flight_number) {
                formFlightNumber.setVisibility(View.VISIBLE);
                formAirports.setVisibility(View.INVISIBLE);
            }
        });

        // Gestion du clic sur le bouton "Afficher les vols"
        btnShowFlights.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FlightsActivity.class);

            if (formAirports.getVisibility() == View.VISIBLE) {
                // Récupérer les données des aéroports
                String departure = departureAirport.getText().toString().trim();
                String arrival = arrivalAirport.getText().toString().trim();

                // Vérifier si les champs sont vides
                if (departure.isEmpty() || arrival.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez saisir les aéroports de départ et d'arrivée", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Extraire les codes IATA
                String depIata = extractIataCode(departure);
                String arrIata = extractIataCode(arrival);

                // Vérifier que les codes IATA sont valides
                if (depIata == null || arrIata == null) {
                    Toast.makeText(MainActivity.this, "Format d'aéroport incorrect. Veuillez utiliser un format valide comme 'Paris (CDG)'", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Envoyer les codes IATA à FlightsActivity
                intent.putExtra("dep_iata", depIata);
                intent.putExtra("arr_iata", arrIata);
            } else if (formFlightNumber.getVisibility() == View.VISIBLE) {
                // Récupérer le numéro de vol
                String flightNum = flightNumber.getText().toString().trim();

                // Vérifier si le champ est vide
                if (flightNum.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez saisir un numéro de vol", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Envoyer le numéro de vol à FlightsActivity
                intent.putExtra("flight_iata", flightNum);
            }

            startActivity(intent);
        });
    }

    // Méthode pour extraire le code IATA à partir du texte saisi
    private String extractIataCode(String input) {
        if (input.contains("(") && input.contains(")")) {
            return input.substring(input.indexOf("(") + 1, input.indexOf(")")).trim();
        }
        return null;
    }
}