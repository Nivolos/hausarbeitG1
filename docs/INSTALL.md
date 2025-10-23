# INSTALL (Skeleton)
## Ziele
- Legacy-Struts weiterhin build- und lauffähig.
- Schrittweise Migration zu Angular + Spring Boot (WAR) + Hibernate.

## Voraussetzungen (Dev)
- Java 21 (oder ≥ 18), Maven, Node 20 (später Angular CLI).
- Externer Tomcat 10.1+ (für spätere Boot-WAR-Deploys).

## Schnelltest Legacy
mvn -q validate

## Nächste Schritte
- PR2: /backend → Spring Boot WAR (GET /api/publications → 200 []).
- PR3: /frontend → Angular 18 (Proxy auf /api, Basisnavigation).

