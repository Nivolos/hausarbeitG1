# Angular Frontend (Publications CRUD)

Das Angular-Frontend greift per Proxy (`/api`) auf das Spring-Boot-Backend zu und stellt eine einfache Verwaltung der Publikationen bereit (Liste, Neu anlegen, Löschen).

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

## Weitere Hinweise
- Tests (`npm test`) verwenden Karma/Jasmine (Chrome Headless).
- Optionale Debug-Logs in den Services/Komponenten sind auskommentiert und können bei Bedarf aktiviert werden.
