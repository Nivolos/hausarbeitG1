# Beschreibung der Klassen

## ApplicationController

Diese Klasse startet den Aufbau des Funktionsmenüs in der Konsole und wartet anschließend auf die Benutzerauswahl,
um dann die ausgewählte Aktion aufrufen zu können. In dieser Klasse befindet sich auch die Transaktionssteuerung,
so dass jede Aktion in einer eigenen Transaktionsklammer ausgeführt wird. Wurde die Aktion erfolgreich abgeschlossen,
wird die Transaktion bestätigt („commit“). Hat allerdings die Ausführung der Aktion eine Ausnahme ausgelöst, 
so wird die Transaktion zurückgerollt („rollback“).

## MenuController

Dies ist eine Hilfsklasse, die von der `ApplicationController`-Klasse verwendet wird, um das Funktionsmenü 
anzuzeigen und die anschließende Auswahl des Benutzers wieder einzulesen. Die Klasse fängt dabei 
auch ungültige Eingaben ab.

## ConsoleUtil

Auch hierbei handelt es sich um eine Hilfsklasse. Die Klasse bietet statische Methoden an, mit deren Hilfe
sich Benutzereingaben von der Konsole einlesen lassen. Die Klasse unterstützt dabei das Einlesen von
Zeichenketten sowie ganzzahligen und booleschen Werten. Verwendet wird die Klasse von den einzelnen Action-Klassen,
um eine Interaktion mit dem Anwender zu vereinfachen.