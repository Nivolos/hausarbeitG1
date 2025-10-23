# Beschreibung der Klassen

## RoomDAO

Diese Klasse (ein sogenanntes Data Access Object) beinhaltet die Persistenzlogik
für die Verwaltung der Räume. Die angebotenen Methoden erlauben das Erstellen,
Bearbeiten, Löschen und Anzeigen von Räumen. Aufgerufen und verwendet wird diese
Klasse von den einzelnen Action-Implementierungen. Die so geschaffene Trennung von
Benutzerinteraktion (Action) und Persistenz (DAO) ist ein erster Schritt in die
Aufteilung einer Anwendung in mehrere Schichten. 