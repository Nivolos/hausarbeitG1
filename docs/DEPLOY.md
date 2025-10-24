# DEPLOY (Tomcat 10.1+)

## Zielbild
- Spring Boot wird als WAR paketiert und in einen externen Tomcat 10.1+ gelegt.
- Angular-Frontend wird separat gebaut und über einen Webserver (oder den Tomcat `ROOT`) ausgeliefert.
- Proxy leitet `/api`-Aufrufe zum Backend weiter.

## Vorbereitungen
1. **Backend builden**
   ```bash
   mvn -f backend/pom.xml -DskipTests package
   ```
   Das WAR liegt anschließend unter `backend/target/library-application.war` (finalName laut POM).
2. **Frontend bauen**
   ```bash
   cd frontend
   npm ci
   npm run build
   ```
   Artefakte befinden sich unter `frontend/dist/library-app/`.

## Tomcat-Deployment
1. Tomcat stoppen (`bin/shutdown.sh`).
2. Vorhandenes WAR (falls Legacy noch benötigt → parallel laufen lassen) unter `webapps/` sichern.
3. Neues Spring-Boot-WAR nach `webapps/library.war` kopieren (Name wie in `pom.xml` konfigurieren).
4. Optional: Frontend-Build nach `${CATALINA_BASE}/webapps/library-ui/` kopieren oder per reverse proxy (nginx) bereitstellen.
5. Tomcat starten (`bin/startup.sh`).
6. Test: http://<host>:8080/api/publications → sollte Seed-Daten liefern.

## Konfiguration
| Property | Zweck | Standard | Hinweis |
| --- | --- | --- | --- |
| `spring.datasource.url` | H2-In-Memory für Dev | `jdbc:h2:mem:library` | Für Prod auf Postgres o. Ä. wechseln. |
| `loan.period.days` | Ausleihdauer | `14` | Kann via Systemproperty oder Umgebungsvariable gesetzt werden. |
| `server.servlet.context-path` | Kontextpfad des WAR | leer | Für parallelen Betrieb Legacy/Modern ggf. `/api` setzen. |
| `server.port` | HTTP-Port | `8080` | Muss mit Proxy/Frontend abgestimmt sein. |

## Frontend-Integration
- Proxy (`proxy.conf.json`) erwartet Backend unter `http://localhost:8080`.
- Für Produktion: Frontend-Build mit Webserver ausliefern und `/api` via Reverse Proxy (nginx/Apache) weiterleiten.
- Alternativ statische Dateien in Tomcat `ROOT` legen und Proxy-Regeln über `conf/server.xml` definieren.

## Troubleshooting
- **HTTP 409 bei Delete/Borrow**: aktive Loans prüfen (`GET /api/publications`). Erst Rückgabe (`POST /api/loans/{id}/return`) ausführen.
- **H2-Konsole aktivieren**: `spring.h2.console.enabled=true` setzen (nur lokal!).
- **Maven-403 im CI**: Cache oder Artifactory konfigurieren; Workflow dokumentiert den Fehler, Build sollte nicht abbrechen.
