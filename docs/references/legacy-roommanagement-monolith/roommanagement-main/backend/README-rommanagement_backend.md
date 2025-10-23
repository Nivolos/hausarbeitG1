# Übung Raumverwaltung

Inhalt dieser Übung ist die Implementierung einer kleinen Anwendung mit Konsolen-Frontend. Die Anwendung
dient zur Verwaltung von Räumen, Kursen und Vorlesungen und bietet die Standard-CRUD-Funktionen für diese
Entitäten an. Die Anwendung wird dabei im Laufe des nächsten Vorlesungen immer weiter erweitert.

## Erweiterung für Spring (2. Teil)

Der erste Umbau für Spring ist abgeschlossen. Dabei sind die folgenden Schritte nun im Projekt
umgesetzt worden:

* Konfiguration der `Data Source`
* Konfiguration der `Entity Manager Factory`
* Umbau der DAOs und Injizieren des Entity Managers
* Neubau von Service-Objekten
* Umbau der Actions und Injizierung der Services 

Zusätzlich wurde die alte, manuelle Transaktionsbehandlung entfernt, da sie noch auf der "alten"
`HibernateUtil`-Variante aufbaut und nicht in Kombination mit Spring einsetzbar ist.

Wenn man die Anwendung aktuell testet, sieht soweit alles gut aus - nur leider stellt man schnell
fest, dass alle ändernden Funktionen auf der Datenbank in einen Fehler laufen. Die Ursache hierfür
liegt in der nun fehlenden Transaktionsbehandlung.

Im Folgenden soll daher das Projekt auf die AOP-basierte Transaktionsbehandlung umgestellt werden.

## Übungsschritte

Für die AOP-basierte Transaktionsbehandlung müssen die folgenden Konfigurationen in der `spring-config.xml`
der Reihe nach durchgeführt werden:

1. Erzeugen Sie die Bean `txManager` mit der Klasse `org.springframework.orm.jpa.JpaTransactionManager`.
2. Injizieren Sie die `entityManagerFactory` in das gleichnamige Property des `txManager`.
3. Konfigurieren Sie den `txAdvice` nach Vorlage in den Vorlesungsfolien. Nutzen Sie dabei den zuvor erstellten
`txManager`.
4. Erzeugen Sie die AOP-Konfiguration mit dem passenden "Point Cut" und dem "Advisor". Auch hierbei helfen
die Folien.
5. Testen Sie die Anwendung und schauen Sie, ob nun auch auch die schreibenden Datenbankfunktionen korrekt
arbeiten.