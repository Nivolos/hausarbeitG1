# Zweck
Angular-Grundmuster: Komponenten, Services, Reactive Forms, Error-Handling.

# Was übernehmen?
- Ordnerstruktur (feature-first), klare Trennung aus Components/Services/Models.
- HttpClient-Nutzung (Interceptors für Error/JSON), einfache State-Handhabung.
- Reactive Forms (FormBuilder), Sync-/Async-Validatoren.
- Saubere Unsubscribe-/Cleanup-Muster (z. B. takeUntil).

# Nicht übernehmen
- Alte RxJS-Syntax oder deprecated Operators.
- Globale Stile/Theme-Setups aus dem Beispiel.
- Hardcodierte URLs – im Hauptprojekt `environment` + Proxy verwenden.

# Hinweise
- Für Errors konsistentes Toast-/Inline-Feedback vorsehen (minimal, ohne UI-Libs).
- Strict TypeScript-Settings im Hauptprojekt aktiv lassen (keine any-Löcher).
