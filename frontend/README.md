# Angular Frontend (Publications & Loans)

Das Angular-Frontend greift per Proxy (`/api`) auf das Spring-Boot-Backend zu und stellt derzeit folgende Funktionen bereit:
- Publikationen listen, neu anlegen und löschen (Delete bleibt gesperrt, solange aktive Ausleihen existieren).
- Ausleihen erstellen (Borrow) inkl. Bestandsprüfung (`stock` vs. `activeLoanCount`).
- Aktive Ausleihen anzeigen, inklusive Überfälligkeits-Hinweis, und Rückgaben (Return) auslösen.

## Voraussetzungen
- Node.js 20+
- npm 10+
- Angular CLI (`npm install -g @angular/cli`) – optional, lokale `node_modules` liegen nicht im Repo.

## Lokale Entwicklung
```bash
npm install
npm start
```
Der Dev-Server läuft anschließend unter http://localhost:4200 und leitet API-Aufrufe an http://localhost:8080 weiter (`proxy.conf.json`).

## Build
```bash
npm run build
```
Das Ergebnis liegt anschließend in `dist/library-app`.

## Fehlermeldungen & Hinweise
- ProblemDetail-Antworten des Backends (z. B. 404/409) werden direkt angezeigt und verhindern generische Fehlermeldungen.
- Der Ausleihen-Button bleibt deaktiviert, solange kein Ausleiher ausgewählt oder kein Bestand mehr verfügbar ist.
- Rückgaben markieren aktive Loans als erledigt; Überfälligkeiten werden über ein rotes Badge gekennzeichnet.

## Weitere Hinweise
- Tests (`npm test`) verwenden Karma/Jasmine (Chrome Headless).
- Optionale Debug-Logs in den Services/Komponenten sind auskommentiert und können bei Bedarf aktiviert werden.
- Die Borrower-Liste wird beim Laden abgerufen (`GET /api/borrowers`) und dient als einfache Auswahl für Ausleihen.
