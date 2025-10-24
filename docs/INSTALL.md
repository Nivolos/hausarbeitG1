# INSTALL (Skeleton)

## Ziele
- Modernisierte Module (Spring Boot + Angular) build- und lauffähig halten.
- Legacy-Struts bleibt als Referenz unter `legacy/struts-app`, wird aber nicht mehr automatisch gebaut.

## Voraussetzungen (Dev)
- Java 21 (oder ≥ 18), Maven, Node 20 (später Angular CLI).
- Externer Tomcat 10.1+ (für spätere Boot-WAR-Deploys).

## Schnelltest Backend
```
mvn -q validate
```
- Führt den Aggregator-Build aus und validiert das Spring-Boot-Modul.

## Lokale Entwicklung (moderne Module)
### Backend (Spring Boot WAR)
```
mvn -f backend/pom.xml spring-boot:run
```
- startet unter http://localhost:8080 (nutzt H2 In-Memory-Seed).
- `loan.period.days` lässt sich via Umgebungsvariable oder `--loan.period.days=<tage>` überschreiben.

### Frontend (Angular 18)
```
cd frontend
npm ci
npm start
```
- Dev-Server auf http://localhost:4200, Proxy routet `/api` → `http://localhost:8080`.
- Falls der Backend-Port wechselt, `proxy.conf.json` anpassen.

### Optional: Legacy-Referenz bauen
```
mvn -f legacy/struts-app/pom.xml -q validate
```
- Nur nötig, wenn die historische Anwendung nachvollzogen werden soll.

## Troubleshooting
| Problem | Ursache | Lösung |
| --- | --- | --- |
| Maven 403 (Central) | CI/Netzwerk blockiert Downloads | Lokales Maven-Repo vorab befüllen oder `mvn -o` (offline) nutzen. |
| `npm ci` Fehler | Proxy/Registry gesperrt | Fallback: `npm ci --prefer-offline` oder temporär `npm install` mit lokalem Cache. |
| Angular-Proxy greift nicht | Backend läuft auf anderem Host/Port | `proxy.conf.json` aktualisieren oder `npm start -- --proxy-config` nutzen. |
| CORS-Fehler ohne Proxy | Frontend ruft Backend direkt auf | Proxy verwenden oder Backend um CORS-Konfiguration erweitern (nur dev). |

## Nächste Schritte
- PR2: /backend → Spring Boot WAR (GET /api/publications → 200 []).
- PR3: /frontend → Angular 18 (Proxy auf /api, Basisnavigation).
- PR6: Ausleihlogik inklusive Borrow/Return-UI testen (Bestandsprüfung, ProblemDetails).
