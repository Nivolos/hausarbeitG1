# Übung Raumverwaltung

Inhalt dieser Übung ist die Implementierung einer kleinen Anwendung mit Konsolen-Frontend. Die Anwendung
dient zur Verwaltung von Räumen, Kursen und Vorlesungen und bietet die Standard-CRUD-Funktionen für diese
Entitäten an. Die Anwendung wird dabei im Laufe des nächsten Vorlesungen immer weiter erweitert.

## Grundaufbau

Der Grundaufbau der Anwendung ist wie folgt:

* `src`: Hauptverzeichnis für alle Quelldateien
    * `main`: Produktionscode
        * `java`: Java-Quellcode (`.java`-Dateien)
        * `resources`: Andere Ressourcendateien (bspw. `.properties`, `.xml`, ...)
* `target`: Enthält die von der IDE bzw. Maven generierten Dateien.

Die Paketstruktur ist dabei wie folgt aufgebaut:

* `de.nordakademie.iaa.roommgmt`
    * `action`: Beinhaltet die Actions - das UI - der Anwendung. Hier findet sich die Verarbeitung aller Benutzeraktionen.
    * `dao`: Beinhaltet die Datenbankzugriffsobjekte, in denen die Hibernate-Funktionen aufgerufen werden.
    * `model`: Beinhaltet die Entitäten der Anwendung.
    
In den einzelnen Paketen sind spezifische README.md-Dateien, die die Details der Klassen kurz beschreiben.

## Übungsschritte

Die Anwendung enthält aktuell schon eine fertige CRUD-Operation für die bekannte Raum-Entität. Die Anwendung
soll nun um eine weitere Entität `Course` erweitert werden, die ein Vorlesungsmodul darstellt.

Die Entität soll dabei die folgenden Datenbanktabelle abbilden:

* `ID`: `BIGINT`, `PK`
* `FIELD_OF_STUDY`: `VARCHAR(50)`, `NOT NULL`
* `NUMBER`: `INT`, `NOT NULL`
* `LECTURER`: `VARCHAR(50)`, `NOT NULL`
* `TITLE`: `VARCHAR(100)`, `NOT NULL`

Die folgenden Schritte sollen der Reihe nach ausgeführt werden:

1. Bauen Sie eine Klasse `Course` für die Vorlesung.
2. Erstellen Sie das Mapping für die erstellte Klasse, dass die oben dargestellte Tabellenstruktur berücksichtigt.
3. Nehmen Sie die Klasse in die `persistence.xml` auf.
4. Erstellen Sie ein Data Access Object `CourseDAO`.
5. Erstellen Sie eine Methode `persistCourse(Course course)` in der Klasse `CourseDAO` und implementieren Sie diese.
6. Erstellen und implementieren Sie die Action-Klasse `CreateCourseAction`.
7. Nehmen Sie die Action-Klasse in der Klasse `RoomManagement` in die Menü-Liste auf.
8. Testen Sie die Anwendung.
9. Wiederholen Sie die Schritte 5-8 für die weiteren Funktionen `updateCourse`, `deleteCourse` und `showCourse`.
10.	Zusatzaufgabe: Implementieren Sie eine `listCourses`-Funktionalität.
 