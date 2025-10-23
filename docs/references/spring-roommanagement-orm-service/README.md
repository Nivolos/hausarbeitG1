# Zweck
Domänennahe ORM-/Service-Layer-Referenz (Room→Publication übertragbar).

# Was übernehmen?
- Service-Methoden als Anwendungsfälle (z. B. borrow/return-Logik analog Loans).
- Konsistente Paketierung: domain, repository, service, web (controller/dto).
- Guard-Logik/Business-Fehler (z. B. Bestand > 0) mit sauberen Exceptions (409).

# Nicht übernehmen
- Controller-Binding direkt auf Entities – im Hauptprojekt DTOs nutzen.
- Framework- oder Container-spezifische XML-Wiring – Boot-Autokonfiguration bevorzugen.
- Unnötige AOP/Proxy-Spielereien – minimal halten.

# Hinweise
- Für Ausleihen: `dueAt` zentral anhand Konstante (LOAN_PERIOD_DAYS) berechnen.
- Rückgabe setzt `returnedAt`, Mahnstufen als separate Service-Funktion halten.
