# Bibliotheksverwaltung – Modernisierte Module

Dieses Repository enthält zwei aktive Modernisierungsstränge neben der unveränderten Legacy-Struts-Anwendung:

- `/backend` – Spring Boot 3.3.x als WAR (Tomcat 10.1+), Java 21, Hibernate 6, REST unter `/api/**`.
- `/frontend` – Angular 18 Standalone-App mit Proxy auf `/api`.
- `/legacy` – ursprüngliche Struts/JSP-Anwendung (bleibt lauffähig, keine Änderungen in den PRs).

## Schnellstart
| Schritt | Kommando | Hinweis |
| --- | --- | --- |
| Legacy prüfen | `mvn -q validate` | Sicherstellen, dass der alte Build weiterläuft. |
| Backend starten | `mvn -f backend/pom.xml spring-boot:run` | H2-Seed liefert Beispiel-Publikationen, Borrower und Loans. |
| Frontend starten | `cd frontend && npm install && npm start` | Dev-Server auf http://localhost:4200, Proxy → http://localhost:8080. |

Weitere Details zur lokalen Einrichtung finden sich in [`docs/INSTALL.md`](docs/INSTALL.md), zum Tomcat-Deploy in [`docs/DEPLOY.md`](docs/DEPLOY.md).

## Ausleihlogik & Konfiguration
- Standard-Leihdauer: **14 Tage** (konfigurierbar über `loan.period.days`).
- Borrow prüft `stock > activeLoanCount`; Konflikte liefern `409 Conflict` mit ProblemDetail (`detail`, `publicationId`).
- Rückgaben setzen `returnedAt` und geben den Bestand wieder frei.
- Überfällige Ausleihen werden im Frontend hervorgehoben (`dueAt < today` & `returnedAt` leer).

## Fehlerbilder (ProblemDetails)
| HTTP-Status | Wann | Nutzlast |
| --- | --- | --- |
| 404 Not Found | Publikation/Loan/Borrower nicht vorhanden | `type`, `title`, `status`, `detail`, optional Id. |
| 409 Conflict | Bestand erschöpft, aktive Loans verhindern Delete/Return | wie oben, ergänzt um `publicationId` bzw. `loanId`. |
| 422 Unprocessable Entity | Validierungsfehler (z. B. negative Bestände) | Validierungsdetails im `detail`-Feld. |

## Weiterführende Dokumente
- [`docs/Pruefungsaufgabe.md`](docs/Pruefungsaufgabe.md) – fachliche Grundlage.
- [`docs/INSTALL.md`](docs/INSTALL.md) – lokale Einrichtung.
- [`docs/DEPLOY.md`](docs/DEPLOY.md) – Tomcat/WAR-Deploy.
- [`docs/REFERENCES.md`](docs/REFERENCES.md) – Stil-/Pattern-Beispiele (nicht bauen).

## Beiträge & Qualitätsleitplanken
- REST-Endpunkte unter `/api/**`, DTOs statt Entities binden.
- ProblemDetails für fachliche Konflikte (keine 500er bei erwartbaren Fehlern).
- Nur aktive Loans (`returnedAt IS NULL`) blockieren Bestand oder Delete.
- Keine UI-Frameworks (Material, Bootstrap) und keine Struts2-Erweiterungen.
- Kleine, nachvollziehbare PRs; Legacy-Code unverändert lassen.
