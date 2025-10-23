# Zweck
DTO-/Serialisierungsmuster für REST-Contracts (Jackson), Request/Response-Modelle.

# Was übernehmen?
- Klare DTOs (Request/Response) mit Validation-Annotations.
- Jackson-Konfiguration (z. B. ISO-8601-Datumsformate), globale ObjectMapper-Defaults.
- Mapper-Strategie: manuell/klein halten, keine komplexen Auto-Mapper nötig.

# Nicht übernehmen
- Java-Serialisierung alter Art (java.io.Serializable für API-Objekte).
- Magische Reflection-Mapper mit versteckten Nebenwirkungen.
- Kopplung von DTOs und Entities.

# Hinweise
- Einheitliches Fehler-Response-Schema dokumentieren (Code, Message, FieldErrors[]).
- API-Contracts stabil halten; Breaking Changes versionieren.
