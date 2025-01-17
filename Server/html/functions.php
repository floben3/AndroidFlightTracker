<?php
// Inclure la connexion à la base de données
require_once 'db.php';

/**
 * Fonction pour récupérer et stocker les vols selon les aéroports
 */
function fetchAndStoreFlightsByAirports($dep_iata, $arr_iata) {
    // Définir le chemin absolu du répertoire Java
    $javaProjectDir = '/var/www/FlightFetcher';

    // Commande pour appeler l'application Java depuis le répertoire du projet Java
    $command = "cd $javaProjectDir && make run ARGS=\"$dep_iata $arr_iata\"";

    // Exécution de la commande
    shell_exec($command);
}

/**
 * Fonction pour récupérer et stocker les vols selon le numéro de vol
 */
function fetchAndStoreFlightsByNumber($flight_iata) {
    // Définir le chemin absolu du répertoire Java
    $javaProjectDir = '/var/www/FlightFetcher';

    // Commande pour appeler l'application Java depuis le répertoire du projet Java
    $command = "cd $javaProjectDir && make run ARGS=\"$flight_iata\"";

    // Exécution de la commande
    shell_exec($command);
}

/**
 * Fonction pour récupérer les vols selon les aéroports
 */
function getFlightsByAirports($dep_iata, $arr_iata) {
    $db = getDbConnection();
    $stmt = $db->prepare("SELECT flightNumber, airline, departureAirportIata, arrivalAirportIata, flightDate, 
                                scheduledDepartureTime, scheduledArrivalTime, estimatedDepartureTime, 
                                estimatedArrivalTime, departureTerminal, arrivalTerminal, aircraftRegistration, 
                                aircraftIata, lastUpdated, latitude, longitude
                          FROM flights
                          WHERE departureAirportIata = ?
                          AND arrivalAirportIata = ?
                          ORDER BY scheduledDepartureTime DESC
                          LIMIT 100");
    $stmt->execute([$dep_iata, $arr_iata]);
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * Fonction pour récupérer les vols selon le numéro de vol
 */
function getFlightsByNumber($flight_iata) {
    $db = getDbConnection();
    $stmt = $db->prepare("SELECT flightNumber, airline, departureAirportIata, arrivalAirportIata, flightDate, 
                                scheduledDepartureTime, scheduledArrivalTime, estimatedDepartureTime, 
                                estimatedArrivalTime, departureTerminal, arrivalTerminal, aircraftRegistration, 
                                aircraftIata, lastUpdated, latitude, longitude
                          FROM flights
                          WHERE flightNumber = ?
                          ORDER BY scheduledDepartureTime DESC
                          LIMIT 100");
    $stmt->execute([$flight_iata]);
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}
?>