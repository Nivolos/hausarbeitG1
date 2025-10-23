# AGENTS.md — Migration & Modernisierung für **Bibliotheksverwaltung** (Angular • Spring Boot • Hibernate)

> Ziel: Das vorliegende Projekt (historische Struts/Standalone-Beispiele) zu einer konsistenten, build‑baren Full‑Stack‑Anwendung nach Prüfungsaufgabe transformieren – unter Beibehaltung von **Tomcat-Deployment** (WAR) und strikter Vermeidung von Struts2 im Endprodukt.

---

## 0) Referenzen & Musskriterien
- **Pflichtanforderungen & Use-Cases:** siehe `docs/Pruefungsaufgabe.md`. Diese Datei ist Quelle für Domäne, Funktionen (CRUD, Suche, Ausleihen/Rückgaben, Mahnwesen, Stammdaten) sowie Validierungen.
- **Bewertung & Musskriterien:** Drei‑Schichten‑Architektur, Transaktionen, validierbare Eingaben, installierbar <5 Min, Tasklogs/Doks. Details s. Folienauszug „Muss- & Bewertungskriterien“ in `/docs`.  

> Der Agent richtet **alle Architektur-, Code- und Doku-Entscheidungen** an diesen Dateien aus.

---

## 1) Architektur-Entscheidungen (Soll)
- **Struktur:** Richte dich an der bestehenden Projektstruktur aus (Ordner-/Modulnamen, Skriptpfade). Nur dort anpassen/umbenennen, wo es für Angular/Spring/Hibernate oder Tomcat-WAR zwingend ist. Erhalte vorhandene Deployment-/Tomcat-Start-Workflows.
- **Frontend:** Angular (CLI).
- **Backend:** Spring Boot 3.x (REST), JPA/Hibernate.
- **Java-Laufzeit:** **Java ≥ 18** (empfohlen: **Java 21 LTS**). Build & CI entsprechend konfigurieren.
- **Persistenz:** JPA/Hibernate, H2 (dev) – optional später PostgreSQL.
- **Deployment:** **externe Tomcat-Instanz** (WAR). Spring Boot wird dafür als `war` verpackt und ohne embedded Tomcat gestartet.
- **Kommunikation:** REST unter `/api/**`.
- **Validierung:** Bean Validation (Jakarta Validation) auf DTOs + serverseitige Fehlerantworten, einfache Client-Validierungen in Angular-Forms.
- **Tests:** Minimaler Build‑Smoke‑Test + Unit‑/Slice‑Tests für Services/Repos (Happy Path).

**No‑Go:** Struts2‑Artefakte, Servlet/JSP-Controller, Dubletten von Services/DTOs, zusätzliche UI/Build Frameworks.

---

## 2) Ziel-Repositorystruktur (an bestehendes Repo angelehnt)
```
/backend        # Spring Boot (WAR) – REST + JPA + Validation
/frontend       # Angular CLI App
/docs           # DV‑Konzept (UML, Dialogmodell, Testfälle), Install, Tasklogs-Vorlage
/scripts        # dev/verify helper
/.github/workflows  # CI (Maven + Angular Build)
```

---

## 3) Migrationsfahrplan (PR‑Sequenz, jeweils build‑bar)
1. **PR1 – Cleanup & Skeleton**
   - Entferne Altlasten: `*.zip`, `target/`, `dist/`, IDE‑Metadaten, Struts‑Configs, demo‑Apps, die nicht wiederverwendet werden.
   - Erzeuge `/backend`, `/frontend`, `/docs`, `/scripts`.
   - `.gitignore` für Java/Node/IDE.

2. **PR2 – Backend Basis (WAR)**
   - Spring Boot 3.x (oder höher) Projekt als **WAR**: `packaging=war`, `spring-boot-starter-tomcat` als *provided*.
   - Deps minimal: `spring-boot-starter-web`, `spring-boot-starter-validation`, `spring-boot-starter-data-jpa`, `h2`, optional `lombok`.
   - `application.properties` (dev):
     ```properties
     server.port=8080
     spring.datasource.url=jdbc:h2:mem:libdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=false
     ```
   - Starte mit **Publication** Domäne (Entity/Repo/Service/Controller) + DTO/Mapper (manuell, kein MapStruct).
   - Healthcheck: `GET /api/publications` → 200/[]

3. **PR3 – Angular Basis**
   - Angular CLI App `library-mgmt` in `/frontend`.
   - `proxy.conf.json` → Proxy auf `http://localhost:8080` für `/api`.
   - Feature **/publications**: Container/List/Form, Service, Routing.

4. **PR4 – Loans (Ausleihen/Rückgaben)**
   - Entities: `Loan`, Relation zu `Publication` & `Borrower`. Service‑Regel: Ausleihe nur bei Bestand > 0; Rückgabe setzt `returnedAt`.
   - REST: Borrow/Return‑Aktionen. Frontend: /loans Listen + Buttons.

5. **PR5 – Stammdaten**
   - Entities: `Borrower`, `Keyword`, `PublicationType`. CRUD Endpunkte + einfache Angular CRUD‑Screens.

6. **PR6 – Mahnwesen (optional)**
   - Service‑Logik für Mahnstufen (1–3), Level 3 → Verlust, `publication.stock--`.
   - UI: Mahn‑Übersicht + Aktionen (keine zusätzlichen Libs).

7. **PR7 – Docs & Scripts**
   - `/docs/DV-Konzept.md` (UML, Dialogmodell, Testfälle), `/docs/INSTALL.md` (<5 Min), Tasklogs‑Vorlage.
   - `/scripts/dev.sh`, `/scripts/verify.sh`.

---

## 4) Backend—Implementierungsleitfaden
### 4.1 Build (Maven)
- **Parent:** `org.springframework.boot` (3.x +), **Java 18+** (empfohlen 21 LTS).
- **Compiler/Toolchain:** `maven-compiler-plugin` mit `<release>21</release>` (oder 18, falls Umgebung erfordert) und optional Java-Toolchains.
- **War‑Konfiguration:**
  - `spring-boot-starter-tomcat` → `<scope>provided</scope>`
  - `SpringBootServletInitializer` in `LibraryApplication` überschreiben (für WAR/Tomcat).

### 4.2 Paket- & API‑Konventionen Paket- & API‑Konventionen
- Package Root: `de.nordakademie.iaa.library`
- REST Pfade:
  - `/api/publications`
  - `/api/loans`
  - `/api/borrowers`
  - `/api/keywords`
  - `/api/pubtypes`

### 4.3 Domänenmodell (Minimalstand)
- `Publication(id, nakKey, title, authors, publishedAt, publisher, type, isbn?, stock:int, keywords*)`
- `Loan(id, publication, borrower, issuedAt, dueAt, returnedAt?)`
- `Borrower(id, matriculation, firstName, lastName, email)`
- `Keyword(id, name)`
- `PublicationType(id, name)`

> **Regeln**: Bestand darf bei Borrow nicht < 0 fallen; Rückgabe setzt `returnedAt`; `dueAt` wird zentral anhand `LOAN_PERIOD_DAYS` berechnet.

### 4.4 Schichten & Transaktionen
- **Controller**: Request/Response DTOs, 4xx/5xx saubere Fehler (Validation → 400, NotFound → 404, Business → 409).
- **Service (@Transactional)**: Fachlogik (Borrow/Return, Dunning), Repos nur hier nutzen.
- **Repository**: `JpaRepository` Schnittstellen, ggf. einfache Finder.

#### Notwendige Schichtenunterteilung:
- Presentationlayer
- Transactionlayer
- Datalayer

### 4.5 Validierung & Fehlerbilder
- DTO‑Annotationen (`@NotBlank`, `@Email`, `@Positive` …).
- Globales `@ControllerAdvice` → `Problem+JSON` ähnliches Fehlerformat (minimal), Feldfehler gesammelt zurückgeben.

### 4.6 Initialdaten
- `data.sql` für ein paar Publikationen, Borrower, Types, Keywords.

### 4.7 Tomcat‑Deployment (ohne embedded)
- Build: `mvn -DskipTests package` → `/backend/target/library.war`
- Deploy: WAR in externen Tomcat (`webapps/`) legen; Start via Tomcat‑Runconfig (IntelliJ) beibehalten.

---

## 5) Frontend—Implementierungsleitfaden
### 5.1 Setup
- **Angular 18+**; Node 20+; `npm ci` für reproduzierbare Builds.
- `proxy.conf.json`:
  ```json
  { "/api": { "target": "http://localhost:8080", "secure": false, "changeOrigin": true } }
  ```
- `npm start` → `ng serve --proxy-config proxy.conf.json`

### 5.2 Feature-Slices Feature-Slices
- **/publications**: Liste (sortierbar clientseitig), Formular (Pflichtfelder/Min/Max), Suche nach Titel/Autor/Keywords/Type.
- **/loans**: Liste offener Ausleihen, Aktionen Borrow/Return, Anzeige `dueAt` & Überfälligkeit.
- **/masters**: CRUD Borrower/Keywords/PublicationTypes.

### 5.3 Services & Formen
- `PublicationService`, `LoanService`, `MasterDataService` – nutzen `HttpClient`, Rückgabe `Observable<T>`.
- Reactive Forms (Basis), UI‑Fehlerhinweise bei Invalidität.

> **Kein** Material/Prime/Bootstrap. Styling minimal mit CSS.

---

## 5.4 Versionen & Kompatibilität (Ziel)
- **Java:** ≥ 18 (Ziel: 21 LTS)
- **Spring Boot:** 3.3.x (Jakarta Namespace, kompatibel zu Tomcat 10.1+)
- **Hibernate:** 6.x (via Spring Boot BOM)
- **Angular:** 18+
- **Node:** 20+
- **Tomcat (extern):** 10.1+
> Agent passt Minor‑Versionen an lokale/CI‑Gegebenheiten an, hält aber obige Mindeststände ein.

---

## 6) CI & Scripts
- **CI Workflow** (`/.github/workflows/ci.yml`): Maven Build `/backend` (ohne Tests ok) + Angular Build `/frontend`.
- **Scripts**
  - `scripts/dev.sh`: Backend build (pkg), Tomcat‑Hint, Frontend dev.
  - `scripts/verify.sh`: Maven pkg + `npm ci && npm run build` (Headless‑Check).

---

## 7) Aufräumen & Reuse-Regeln
- **Wiederverwenden statt neu bauen**: Modelle/Listen/Form‑Muster aus Room‑Projekten adaptieren (Umbenennen, Felder anpassen).
- **Entfernen**: Struts2 Controller, JSPs, veraltete Hibernate‑Standalone‑Mains, Gulp/Grunt, Demo‑Serialisierung und Personalisierte Muster, Bilder und Daten (wie der Pinguin).
- **Behalten**: Nützliche README‑Anleitungen, Angular‑Listen/Form‑Patterns.

---

## 8) Akzeptanz- & Done‑Kriterien
**Backend**
- `mvn -q -DskipTests package` in `/backend` erzeugt WAR
- `GET /api/publications` liefert 200/JSON

**Frontend**
- `npm ci && npm run build` erfolgreich
- CRUD für Publications & Borrow/Return manuell testbar

**Doku/Install**
- Installation unter 5 Min (Tomcat + WAR + Angular dev)

**Konformität**
- Nur Angular/Spring/Hibernate
- Tomcat‑Deployment extern
- Keine 400/500 Überraschungen bei Normalbedienung, definierte Fehlermeldungen

---

## 9) Umsetzungsnotizen für den Agent
- **Kleine PRs**; jeder PR muss für sich bauen.
- **DTOs explizit** statt Entity‑Binding im Controller.
- **Minimize Deps**: keine MapStruct/ModelMapper, keine UI‑Libs.
- **Beachte Prüfungsunterlagen**: DV‑Konzept (UML, Dialogmodell, Testfälle) in `/docs` pflegen; Install/Tasklogs bereitstellen.

---

## 10) Checklisten
### Backend (PR2)
- [ ] `pom.xml` auf Boot 3.x, packaging=war
- [ ] **Java auf 21 (oder min. 18) setzen** (Compiler/Toolchain)
- [ ] `SpringBootServletInitializer`
- [ ] Deps (Web, Validation, Data JPA, H2)
- [ ] Entities + Repos + Service (Publication)
- [ ] Controller + DTO + Validation
- [ ] `data.sql`

### Frontend (PR3)
- [ ] Angular **18+** CLI init (Name/Ordner an Bestand anlehnen)
- [ ] Proxy auf `/api`
- [ ] Publications List/Form
- [ ] Angular CLI init
- [ ] Proxy auf `/api`
- [ ] Publications List/Form

### Loans (PR4)
- [ ] Entities & Service‑Regeln
- [ ] REST Borrow/Return
- [ ] UI Aktionen

### Stammdaten (PR5)
- [ ] CRUD Borrower/Keywords/Types

### Mahnwesen (PR6, optional)
- [ ] Service Level 1–3
- [ ] UI Übersicht/Aktionen

### Docs & Scripts (PR7)
- [ ] DV‑Konzept (UML, Dialogmodell, Testfälle)
- [ ] INSTALL.md (<5 Min)
- [ ] dev/verify scripts

---

## 11) Appendix: Beispiel‑Snippets
**WAR-Bootstrap**
```java
@SpringBootApplication
public class LibraryApplication extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(LibraryApplication.class);
  }
  public static void main(String[] args) { SpringApplication.run(LibraryApplication.class, args); }
}
```

**Maven WAR Kernteile**
```xml
<packaging>war</packaging>
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

**Angular Proxy**
```json
{
  "/api": { "target": "http://localhost:8080", "secure": false, "changeOrigin": true }
}
```

