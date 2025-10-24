# Beitragshinweise

Diese Modernisierung folgt den Leitplanken aus `AGENTS.md` und `docs/Pruefungsaufgabe.md`. Bitte beachte folgende Do's & Don'ts:

## Do
- REST-Endpunkte immer unter `/api/**` veröffentlichen und DTOs verwenden (keine Entities im Controller binden).
- ProblemDetails für fachliche Fehler (404/409/422) nutzen, damit Frontend und Nutzer klare Rückmeldungen erhalten.
- Nur aktive Loans (`returnedAt IS NULL`) für Bestandslogik, Delete-Sperren und Mahnwesen berücksichtigen.
- Kleine, fokussierte PRs erstellen; sicherstellen, dass `mvn -q validate` (Root-Aggregator) weiterhin grün ist und Legacy-Code im Referenzordner bleibt.
- Dokumentation (INSTALL, DEPLOY, README, Tasklogs) aktuell halten, sobald Verhalten oder Setup sich ändern.

## Don't
- Keine Struts2/JSP/Tiles-Komponenten in den neuen Modulen verwenden oder erweitern.
- Keine zusätzlichen UI-Frameworks (Material, Bootstrap, o. Ä.) einführen.
- Keine Wildcard-Imports oder automatischen Groß-Refactorings über das gesamte Repo anwenden.
- Keine Dateien aus `docs/references/**` in produktive Module kopieren oder in CI-Builds einbeziehen.
- Keine Maven-/Node-Abhängigkeiten global installieren; nutze Projektkonfiguration (`pom.xml`, `package.json`).

## Workflow-Erinnerung
1. `mvn -q validate` im Root ausführen (Aggregator → `/backend`).
2. Module separat testen (`mvn -f backend/pom.xml spring-boot:run`, `npm start`).
3. Optional: Legacy nur bei Bedarf mit `mvn -f legacy/struts-app/pom.xml validate` prüfen.
4. Checkliste aus `.github/pull_request_template.md` verwenden.
5. CI-Workflow (`.github/workflows/ci.yml`) prüft Maven-Validate und versucht den Angular-Build (Soft-Fail bei Offline-Registry).
