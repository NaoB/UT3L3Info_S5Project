# L3 Info : Projet S5 (ALLET, AUZI, BELLAVIA)

## Création de la base de données
La base de données peut être importée à partir du fichier `bdd.sql` (avec des données déjà présentes) ou `bdd_vide.sql` (contenant uniquement la structure et les valeurs par défaut pour les différents types de capteurs)

Par défault l'application accède une base de données sur un serveur.
Les informations de connexion à la base de données peuvent être modifiées dans le fichier `model.Database.java` aux lignes 11-14 (par exemple pour utiliser Wamp):
```java
private String dbHost = "xxx.xxx.xxx.xxx"; // IP de la base de données
private String dbName = "project"; // Nom de la base de données
private String dbUser = "root"; // Nom de l'utilisateur
private String dbPass = ""; // Mot de passe de l'utilisateur
```

## Lancement de l'application
L'application peut être lancée avec la commande `java -jar Projet_S5_Allet_Auzi_Bellavia.jar` **(Attention, la version minimale du JDK est la version 1.8)**
Le port par défaut utilisé par le simulateur de capteurs est le port `8952`

## Développement
Pour le développement, l'application nécessite les librairies suivantes
- mysql-connector-java (v8.0.22)
- jfreechart (v1.0.19)
- jcommon (v1.0.23)
