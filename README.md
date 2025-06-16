# LAMONACA CONTENTWISE CHALLENGE
## How to run it
L'applicativo è scritto in java17 usando SpringBoot come framework,
Gradle per la parte di build e MongoDb come database non relazionale per 
la parte dei dati.

Con Docker già installato, per far partire l'applicativo è sufficiente clonare il progetto ed effettuare una
docker-compose up --build -d nella cartella root del progetto.

Verrà in automatico:
- buildato il progetto
- fatto partire l'applicativo
- fatto partire il database mongo e popolato con il preset fornito in partenza

L'applicativo rimane in ascolto sulla porta 8080 mentre il db è sulla porta 28017.
Assicurarsi che tali porte siano libere per l'uso.

All'interno del progetto vi è un file json Contentwise challenge.postman_collection.json
(raggiungibile anche al link https://github.com/effelam/lamonacacontentwisechallenge/blob/master/Contentwise%20challenge.postman_collection.json).
Importando tale json all'interno di Postman si otterrà la collezione di tutti gli endpoint implementati
in modo da poter subito provare l'applicativo.