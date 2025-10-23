# Zweck
Angular-Referenz für CRUD + Suche + Sortierung (Tabellen, Reactive Forms, Routing).

# Was übernehmen?
- Struktur für Listen-/Detail-/Form-Views (Container/Presentational).
- Reactive Forms inkl. Validierung und Fehlermeldungen.
- Service-Layer mit HttpClient, einfache Filter-/Such- und Paging-Muster.
- Routing-Konventionen und modulare Aufteilung (Feature-Module).

# Nicht übernehmen
- Veraltete Angular-APIs, nicht mehr benötigte Polyfills.
- Framework-spezifisches CSS oder UI-Libs (Material/Bootstrap) – im Hauptprojekt plain CSS belassen.
- Direkte Kopie von Models → im Hauptprojekt eigene DTOs/Types definieren.

# Hinweise
- Endpunkte im Hauptprojekt werden unter `/api/**` bereitgestellt (Proxy nutzen).
- Für Tabellen: einfache Client-Sortierung/Filterung reicht (keine Drittlibs).
