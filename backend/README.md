# Backend (Spring Boot WAR) — Skeleton
Dieses Verzeichnis enthält das künftige Spring-Boot-Backend (WAR für externen Tomcat).
Status: PR1 legt nur das Skelett an. Keine Code-Artefakte, keine POMs.

Zielstände (später): Java ≥ 18 (empf. 21 LTS), Spring Boot 3.3.x, Hibernate 6.x.

Initiales Spring Boot 3.3.x WAR Skeleton mit GET /api/publications.
Build: mvn -f backend/pom.xml package

## Aktueller Stand (PR4)
- Domänenmodelle: Publication, Borrower, Loan (JPA/Hibernate).
- Publication-REST: GET/POST/DELETE unter `/api/publications` mit DTO-Mapping.
- Seed-Daten: `src/main/resources/data.sql` (H2 In-Memory) für Schnelltests.

### Löschen & Konflikte (409)
- Publikationen mit **aktiven** Ausleihen liefern beim DELETE einen **409 Conflict** samt ProblemDetail (`publicationId`, `detail`).
- Bereits vollständig zurückgegebene Ausleihen blockieren das Löschen nicht mehr.
- Frontend zeigt diese Rückmeldung an; für neue Publikationen ohne aktive Loans bleibt `204 No Content` unverändert.

### Quick Checks
```bash
mvn -f backend/pom.xml package -DskipTests
curl -s http://localhost:8080/api/publications | jq
```
