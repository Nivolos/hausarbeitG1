# Beschreibung der Klassen

## Action

Die `Action`-Klasse ist die abstrakte Oberklasse, von der alle eigentlichen Action-Implementierungen abgeleitet werden.
Die Klasse definiert zwei abstrakte Methoden, die von den Unterklassen implementiert werden müssen.
Ähnlich wie beim Struts-Framework dienen die Action-Klassen in der hier verwendeten Architektur dazu,
die Interaktion mit dem Benutzer abzubilden. Die eigentliche Persistenzlogik ist nicht Bestandteil der Action-Implementierung.

Die Methode `getLabel()` muss eine Zeichenkette zurückgeben, die dann im Menü zum Anzeigen eines entsprechenden
Eintrags verwendet wird. 

Die Methode `execute()` hingegen enthält die eigentliche Logik der Action, die beim Auswählen der Action aufgerufen wird.
Somit muss für jeden Menüeintrag eine eigene Action-Unterklasse erstellt werden.

