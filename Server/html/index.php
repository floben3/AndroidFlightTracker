<?php
require_once 'functions.php';

header('Content-Type: application/json');

// Vérification de la clé d'API
$api_key = $_GET['access_key'] ?? null;
$valid_api_key = 'dee39d73a02f584cab2302c5ecabccf5';

if ($api_key !== $valid_api_key) {
    // Si la clé est invalide, renvoyer un code 403 et un message d'erreur
    http_response_code(403);
    echo json_encode(['error' => 'Invalid or missing API key']);
    exit; // Arrêter l'exécution du script si la clé est invalide
}

// Récupérer les autres paramètres de la requête GET
$dep_iata = $_GET['dep_iata'] ?? null;
$arr_iata = $_GET['arr_iata'] ?? null;
$flight_iata = $_GET['flight_iata'] ?? null;

if ($dep_iata && $arr_iata) {
    // Requête par aéroports
    fetchAndStoreFlightsByAirports($dep_iata, $arr_iata);
    $flights = getFlightsByAirports($dep_iata, $arr_iata);
    echo json_encode(['data' => $flights], JSON_PRETTY_PRINT);
} elseif ($flight_iata) {
    // Requête par numéro de vol
    $flights = fetchAndStoreFlightsByNumber($flight_iata);
    $flights = getFlightsByNumber($flight_iata);
    echo json_encode(['data' => $flights], JSON_PRETTY_PRINT);
} else {
    echo json_encode(['error' => 'Invalid request']);
}
?>
