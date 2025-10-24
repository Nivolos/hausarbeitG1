# Änderungen in PR5V2

## Überblick
Dieser Pull Request stellt sicher, dass die Modernisierungsschritte an Backend und Frontend vollständig auf die Anforderungen aus `docs/Pruefungsaufgabe.md` und den Fahrplan aus `AGENTS.md` abgestimmt bleiben. Im Fokus stehen die jüngsten Ergänzungen rund um Ausleihe, Rückgabe, Such-/Sortierfunktionen und Stammdatenpflege.

## Backend
- Spring-Boot-WAR (`/backend`) bietet jetzt alle Publikationsattribute inklusive NAK-Schlüssel, Veröffentlichungsdatum, Typ und Schlagwörter.
- Stammdaten-CRUD für PublicationType, Keyword und Borrower ist implementiert und liefert konsistente DTOs.
- Borrow-/Return-Endpunkte erzwingen Bestandsprüfungen, setzen `issuedAt`/`dueAt` anhand der konfigurierbaren Leihdauer (`loan.period.days`) und beantworten Konflikte mit ProblemDetails (404/409/422).
- Publication-Endpoints unterstützen Filter- und Sortierparameter (`q`, `type`, `keyword`, `publishedBefore`, `publishedAfter`, `page`, `size`, `sort`).

## Frontend
- Angular-18-Anwendung `/frontend` nutzt eine filter- und sortierbare Bestandsübersicht inkl. Borrow/Return-Buttons, Borrower-Auswahl und Overdue-Anzeige.
- Stammdaten werden clientseitig über Services verwaltet; Formulare validieren Pflichtfelder (u. a. NAK-Schlüssel, Publikationstyp, Schlagwörter).
- Borrow/Return-Aktionen reagieren auf ProblemDetails (z. B. 409 „Bestand erschöpft“) mit nutzerfreundlichen Meldungen.

## Dokumentation & Hilfen
- README-Dateien (Root, Backend, Frontend) beschreiben die neue Domäne, Filteroptionen und Standard-Leihdauer.
- `docs/INSTALL.md` und `docs/DEPLOY.md` erläutern Setup, Tomcat-WAR-Deploy und Troubleshooting (z. B. Maven-Central-403, Proxy-Probleme).
- CONTRIBUTING.md und Templates (`.github/*`) stellen sicher, dass künftige PRs Tests, ProblemDetails und Dokumentation beachten.

## Tests & Validierung
- `mvn -q validate` läuft für Legacy und Modernisierungs-Module.
- Frontend-Build (`npm ci && npm run build`) ist in CI hinterlegt; bei Netzwerkproblemen gibt es Hinweise für Offline-Fallbacks.

## Offene Aufgaben
- DV-Konzept (UML/Dialogmodell/Testfälle) weiter ausarbeiten.
- Mahnwesen (PR6) und weitere Prüfungsanforderungen iterativ ergänzen.
