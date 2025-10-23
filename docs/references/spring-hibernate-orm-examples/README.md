# Zweck
JPA/Hibernate-Patterns: Entities, Relationen, Transaktionen, Validation.

# Was übernehmen?
- Entity-Mapping (1:n, n:m), Lazy/Eager-Balance, Value Objects.
- Repository-/Service-Split mit Transaktionsgrenzen (@Transactional).
- Bean Validation an DTOs + feldbezogene Fehlerrückgaben.
- Testmuster für Repositories/Services (Slice-Tests, H2 in-memory).

# Nicht übernehmen
- javax.*-APIs – im Hauptprojekt Jakarta-Namespace (Boot 3) verwenden.
- Veraltete Hibernate-/JPA-Konfigurationen (xml-mapping o. ä.).
- Logik direkt in Controllern – strikt Services nutzen.

# Hinweise
- `ddl-auto` im Hauptprojekt: `update` (dev) und Migrationsstrategie später via Flyway.
- Fehlerformat: kompaktes Problem-JSON (Feldfehler gesammelt).
