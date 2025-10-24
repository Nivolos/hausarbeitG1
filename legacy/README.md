# Legacy (Struts/JSP)

Die ursprüngliche Struts/JSP-Anwendung liegt komplett unter `legacy/struts-app` (inkl. früherem `pom.xml` und `src`).
Sie dient nur als Referenz und wird von den modernen Build-Schritten nicht berührt.

- Root-Build (`mvn -q validate`) kümmert sich ausschließlich um `/backend`.
- Wer das alte Projekt nachvollziehen möchte, kann es manuell über `mvn -f legacy/struts-app/pom.xml validate` prüfen.
