# Angular Frontend (Publications & Loans)

Das Angular-Frontend (Standalone, Angular 18) konsumiert das Spring-Boot-Backend über einen Proxy auf `/api`.

## Features
- Publikationen listen, anlegen und löschen (Delete gesperrt, solange `activeLoanCount > 0`).
- Borrow-Dialog mit Borrower-Auswahl und Bestandsprüfung (`stock > activeLoanCount`).
- Aktive Ausleihen inkl. Rückgabe-Aktion; Überfällige Loans werden visuell markiert.
- Fehlermeldungen setzen auf ProblemDetails (z. B. 404, 409, 422) und zeigen konkrete Hinweise statt generischer Alerts.

## Voraussetzungen
- Node.js 20+
- npm 10+
- Angular CLI optional (`npm install -g @angular/cli`), lokale `node_modules` werden via `npm ci` erzeugt.

## Lokale Entwicklung
```bash
cd frontend
npm ci
npm install
npm start
```
- Dev-Server unter http://localhost:4200.
- Proxy (`proxy.conf.json`) leitet `/api` nach http://localhost:8080 weiter.

## Build & Tests
```bash
npm run build        # erzeugt dist/library-app
npm test             # Karma/Jasmine (Chrome Headless)
```
- Für CI existiert ein Workflow, der `npm ci && npm run build` versucht (Soft-Fail bei Offline-Registry).

## Fehlerbilder & UX-Hinweise
- 409 Conflict → „Bestand erschöpft“ bzw. „Loan bereits zurückgegeben“.
- 404 Not Found → Ressource nicht mehr vorhanden (z. B. paralleles Löschen).
- 422 Unprocessable Entity → Formular validieren (Pflichtfelder, positive Bestände).
- Borrow-Button bleibt deaktiviert, solange kein Borrower gewählt oder kein Bestand verfügbar ist.

## Weiterführend
- Backend-Dokumentation: [`../backend/README.md`](../backend/README.md)
- Installationshinweise: [`../docs/INSTALL.md`](../docs/INSTALL.md)
- Deploy-Anleitung: [`../docs/DEPLOY.md`](../docs/DEPLOY.md)
