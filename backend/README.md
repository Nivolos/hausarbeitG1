# Backend (Spring Boot WAR) — Skeleton
Dieses Verzeichnis enthält das künftige Spring-Boot-Backend (WAR für externen Tomcat).
Status: PR1 legt nur das Skelett an. Keine Code-Artefakte, keine POMs.

Zielstände (später): Java ≥ 18 (empf. 21 LTS), Spring Boot 3.3.x, Hibernate 6.x.

Initiales Spring Boot 3.3.x WAR Skeleton mit GET /api/publications.
Build: mvn -f backend/pom.xml package

## Aktueller Stand (PR6)
- Domänenmodelle: Publication, Borrower, Loan (JPA/Hibernate) inkl. Ausleih-/Rückgaberegeln.
- Publication-REST: GET/POST/DELETE unter `/api/publications` mit DTO-Mapping und aktiven Ausleihen (inkl. Überfälligkeitsstatus).
- Loan-REST: `POST /api/loans` (Ausleihen) + `POST /api/loans/{id}/return` (Rückgabe) mit Bestands-/Konfliktprüfung.
- Borrower-REST: `GET /api/borrowers` für einfache Auswahl im Frontend.
- Seed-Daten: `src/main/resources/data.sql` (H2 In-Memory) liefert mehrere Publikationen, Borrower sowie aktive & abgeschlossene Loans.

### Löschen & Konflikte (409)
- Publikationen mit **aktiven** Ausleihen liefern beim DELETE einen **409 Conflict** samt ProblemDetail (`publicationId`, `detail`).
- Bereits vollständig zurückgegebene Ausleihen blockieren das Löschen nicht mehr.
- Frontend zeigt diese Rückmeldung an; für neue Publikationen ohne aktive Loans bleibt `204 No Content` unverändert.

### Borrow & Return (ProblemDetails)
- `POST /api/loans` prüft Bestand (`stock > activeLoanCount`) und liefert bei Engpässen `409 Conflict` mit sprechender Detailnachricht.
- Rückgabe versucht `POST /api/loans/{id}/return`; doppelte Rückgaben erzeugen `409 Conflict` (ProblemDetail enthält `loanId`).
- Alle 404/409-Fälle sind als ProblemDetail umgesetzt, um einheitliche Fehlerbilder zwischen Backend und Angular bereitzustellen.

### Quick Checks
```bash
mvn -f backend/pom.xml package -DskipTests
curl -s http://localhost:8080/api/publications | jq
curl -s -X POST http://localhost:8080/api/loans \
  -H "Content-Type: application/json" \
  -d '{"publicationId":1003,"borrowerId":2002}'
```
