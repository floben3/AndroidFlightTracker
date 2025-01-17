# AndroidFlightTracker
Projet universitaire de développement d'une application Android de suivi de vols en temps réél
---

1. Nous avons créé une application Android en Java grâce à Android Studio
   - Cette application permet :
     - D'afficher une liste des vols, au départ d'un aéroport et à destination d'un aéroport
     - D'afficher une liste des vols à partir d'un numéro de vol IATA
     - De parcourir cette liste
     - D'afficher les détails et la position de chaque vol

2. Nous avons également créé un serveur Web pour gérer les requêtes de notre application
   - Ce serveur Web Apache2 tourne sur une machine Debian 12
   - Le back-end est développé en PHP
   - Nous utilisons une base de données SQLite3

3. De plus nous utilisons une application Java côté serveur
   - Elle permet d'interroger l'API d'Avion Stack
   - Elle stocke les données reçues dans la base de données
