# Backend (Spring Boot WAR)
Dieses Modul liefert das moderne REST-Backend der Bibliotheksverwaltung. Es wird als WAR verpackt und in einen externen Tomcat 10.1+ deployt.

## Build & Laufzeit
```bash
mvn -f backend/pom.xml spring-boot:run   # Dev-Start (H2)
mvn -f backend/pom.xml -DskipTests package   # WAR erzeugen
```
- Java 21, Spring Boot 3.3.x, Hibernate 6.x.
- `spring-boot-starter-tomcat` ist als *provided* deklariert – WAR läuft im externen Container.

## Domäne & Use-Cases
- **Publication** – CRUD, aktiver Bestand (`stock`) wird durch aktive Loans (`returnedAt IS NULL`) reduziert.
- **Borrower** – Ausleiher-Verwaltung (Basisdaten für Auswahl).
- **Loan** – Ausleihen & Rückgaben inkl. Bestandsprüfung.

## REST-Endpunkte (Auszug)
| Endpoint | Methode | Beschreibung |
| --- | --- | --- |
| `/api/publications` | GET/POST/DELETE | Listen, anlegen, löschen (Delete blockt bei aktiven Loans). |
| `/api/borrowers` | GET | Borrower-Auswahl für Frontend. |
| `/api/loans` | POST | Ausleihen (`publicationId`, `borrowerId`). |
| `/api/loans/{id}/return` | POST | Rückgabe (setzt `returnedAt`). |

## Ausleihlogik & Konfiguration
- Standard-Leihdauer: **14 Tage** (`loan.period.days`, überschreibbar via Property oder `--loan.period.days=<tage>`).
- Borrow prüft `stock > activeLoanCount`; liefert bei Engpässen `409 Conflict` (ProblemDetail mit `publicationId`).
- Rückgabe erlaubt nur aktive Loans – doppelte Rückgabe → `409 Conflict` (`loanId`).
- Delete einer Publikation fängt Race Conditions über `DataIntegrityViolationException` ab und antwortet ebenfalls mit 409.

## ProblemDetails
Alle relevanten Fehlerfälle liefern strukturierte Antworten (`type`, `title`, `status`, `detail`, optionale IDs). Beispiele:
- 404 – Ressource nicht gefunden.
- 409 – aktiver Bestand belegt oder Loan bereits zurückgegeben.
- 422 – Bean-Validation verletzt (z. B. leerer Titel, negativer Bestand).

## Seed-Daten
`src/main/resources/data.sql` legt mehrere Publikationen, Borrower und gemischte Loans an, sodass Ausleihen/Rückgaben sofort getestet werden können (`curl`-Beispiele siehe unten).

## Quick Checks
```bash
curl -s http://localhost:8080/api/publications | jq
curl -s -X POST http://localhost:8080/api/loans \
  -H "Content-Type: application/json" \
  -d '{"publicationId":1003,"borrowerId":2002}'
curl -s -X POST http://localhost:8080/api/loans/3001/return
```

## Optionale Diagnose
- In `src/test/java` liegen kommentierte MockMvc-Smoke-Tests (`LoanControllerSmokeTest`), die bei aktiven Loans 409 erwarten.
- Auskommentierte Log-Levels (`logging.level.org.hibernate.SQL`, `...jdbc.bind`) sind in `application.properties` dokumentiert und können bei Bedarf aktiviert werden.
