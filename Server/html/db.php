<?php
function getDbConnection() {
    // Définir le chemin absolu vers flights.db
    $dbPath = __DIR__ . '/flights.db';
    
    // Vérifier si la base de données existe
    if (!file_exists($dbPath)) {
        die("Erreur : La base de données flights.db est introuvable.");
    }

    try {
        // Créer une nouvelle connexion SQLite
        $db = new PDO('sqlite:' . $dbPath);
        // Configurer PDO pour afficher les erreurs
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        return $db;
    } catch (PDOException $e) {
        die("Erreur de connexion à la base de données : " . $e->getMessage());
    }
}
?>
