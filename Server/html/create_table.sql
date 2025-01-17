-- Création de la table flights
CREATE TABLE IF NOT EXISTS flights (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    flightNumber TEXT NOT NULL,
    departureAirportIata TEXT NOT NULL,
    arrivalAirportIata TEXT NOT NULL,
    airline TEXT NOT NULL,
    departureAirport TEXT NOT NULL,
    arrivalAirport TEXT NOT NULL,
    flightDate TEXT NOT NULL,
    scheduledDepartureTime TEXT,
    scheduledArrivalTime TEXT,
    estimatedDepartureTime TEXT,
    estimatedArrivalTime TEXT,
    departureTerminal TEXT,
    arrivalTerminal TEXT,
    aircraftRegistration TEXT,
    aircraftIata TEXT,
    lastUpdated TEXT,
    latitude REAL,
    longitude REAL
);