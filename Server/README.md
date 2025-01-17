# Instructions pour installer et configurer le serveur

1. Installer une machine sous Debian 12
2. Installer les paquets Apache2, PHP, MySQL, OpenJDK
   - sudo apt update
   - sudo apt install apache2
   - sudo apt php php-cli libapache2-mod-php php8.2-sqlite3
   - sudo apt install sqlite3 libsqlite3-dev
   - sudo apt install default-jdk
3. Activer PHP sur Apache2, et redémarrer le serveur Apache2
   - sudo a2enmod php
   - sudo systemctl restart apache2
4. Installer le répertoire dans le répertoire /var/www/
   - sudo rm -rf /var/www/html
   - sudo unzip server.zip
5. Créer la base de données
   - cd /var/www/html
   - sqlite3 flights.db < create_table.sql
6. Configurer les clées d'API
   - Mettre une clée d'API AvionStack valide dans le fichier /var/www/FlightFetcher/src/FlightFetcher.java
7. Compiler le projet Java
   - cd /var/www/FlightFetcher
   - make clean & make compile
8. Configurer la propriété des répertoires et fichiers
   - cd /var/www
   - sudo chown -R www-data:www-data *
9. Configurer les droits des répertoires et fichiers
   - chmod -R 640 html/
   - chmod -R 750 FlightFletcher/
   - chmod 750 html/ FlightFetcher/
