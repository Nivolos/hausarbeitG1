# Übung: Raumverwaltung mit Hibernate

In dieser Übung wollen wir die Persistenz mit Hilfe von Hibernate umsetzen.

## Erstellung einer „Stand-alone“ Java-Applikation

* Pflegefunktionen für Räume und Vorlesung in einer relationalen Datenbank:
  * Anlegen
  * Editieren
  * Löschen
  * Anzeigen von einzelnen und allen Datensätzen
* Benutzerinteraktion erfolgt über Konsole
* Ziele der Architektur:
  * Schichten isolieren (Benutzer-IO, Prozesslogik, Daten)
  * Auslagerung der Transaktionsdemarkationen

Inhalt dieser Übung ist es, einen ersten Überblick über die Hibernate-Konfiguration
und die elementaren Hibernate-Funktionen (CRUD) zu bekommen.

Die Übung ist in mehrere Schritte aufgeteilt und beginnt mit diesem initialen
Projektstand.

Bitte dieses Projekt aus Gitlab heraus klonen. Sollen die späteren Änderungen committed werden,
ist es notwendig, einen Fork zu erzeugen und anschließend den eigenen Fork zu klonen.

## Aufbau

Maven-konform nutzt das Projekt die folgende Verzeichnisstruktur genutzt:

* `src`: Hauptverzeichnis für alle Quelldateien
    * `main`: Produktionscode
        * `java`: Java-Quellcode (`.java`-Dateien)
        * `resources`: Andere Ressourcendateien (bspw. `.properties`, `.xml`, ...)
* `target`: Enthält die von der IDE bzw. Maven generierten Dateien.

## Übungsschritte

Die Übung besteht aus den folgenden Teilschritten, die nacheinander aufeinander aufbauen. Die
einzelnen Schritte sind nach der Auflistung weiter beschrieben.

1. Anpassen der Hibernate-Konfiguration
2. Erzeugen einer Raum-Klasse
3. Aus Klasse wird Entität
4. Persistieren eines Raums (**C**RUD)
5. Laden eines Raums (C**R**UD)
6. Aktualisieren eines Raums (CR**U**D)
7. Löschen eines Raums (CRU**D**)

## 1. Anpassen der Hibernate-Konfiguration

1. Machen Sie sich mit der Hibernate-Konfiguration vertraut. Sie befindet sich
in der Konfigurationsdatei `persistence.xml` im Verzeichnis `/src/main/resources`.

2. Anschließend machen Sie die JDBC-URL in dieser Datei ausfindig, mit der festgelegt wird, wo die Datenbank
zu finden ist. Aktuell zeigt diese Konfiguration auf einen Pfad, der eventuell auf Ihrem System
nicht zu finden ist. In diesem Fall passen Sie die Konfiguration entsprechend an.

## 2. Erzeugen einer Raum-Klasse

Bevor wir eine Klasse in die Datenbank speichern können, brauchen wir die Klasse. 

1. Legen Sie ein neues Java Package mit dem Namen `de.nordakademie.iaa.hibernate.model` an.

2. Erzeugen Sie die Klasse `Room` in diesem Paket.

3. Die Klasse sollte die unten angegebenen Attribute haben - und bitte die dazugehörigen Zugriffsmethoden
nicht vergessen.

Folgende Attribute benötigt die Klasse:

- Attributname: `id`, Typ: `Long`
- Attributname: `building`, Typ: `String`
- Attributname: `roomNumber`, Typ: `Integer`
- Attributname: `seats`, Typ: `Integer`
- Attributname: `projectorPresent`, Typ: `boolean`

## 3. Aus Klasse wird Entität

Die gerade erstellte Klasse muss nun noch in eine Entität umgewandelt werden, damit Hibernate
die Objekte der Klasse auch persistieren kann. Dabei ist neben der Hinterlegung von Metadaten
in der Klasse auch eine Anpassung der Hibernate-Konfiguration notwendig.

1. Deklarieren Sie die Klasse `Room` als Entität unter Verwendung der Annotation `@Entity`. Ggf. helfen die
Vorlesungsfolien.

2. Eine Entität benötigt zwingend einen *Identifier* (Primärschlüsssel). Bestimmen Sie das passende Attribut und
deklarieren Sie den Identifier über die Annotation `@Id`.

3. Ein Identifier wird idealerweise automatisch generiert. Hibernate kann dies entsprechend übernehmen. Damit das
auch passiert, setzen Sie die Annotation `@GeneratedValue` ein.

4. Damit Hibernate auch nach den hinterlegten Annotation sucht, ist es notwendig, die Klasse in der Konfigurationsdatei
`persistence.xml` aufzuführen. Fügen Sie in der Konfigurationsdatei dafür ein neues `<class>`-Element ein. Achten Sie
dabei auf die korrekte Position, damit das Schema nicht verletzt wird. IntelliJ meckert hier ggf.

Eine kleine Zusatzaufgabe: Vielleicht bemerken Sie im Rahmen dieser Teilübung, dass sich die Annotation an unterschiedlichen Stellen in der
Klasse verwenden lassen. Versuchen Sie einmal - bspw. über die Dokumentation oder Internetrecherche - herauszubekommen,
ob dies zu einem unterschiedlichen Verhalten führt. Und wenn ja, zu welchem?

## 4. Persistieren eines Raums

Wenn nun durch die vorherigen Schritte die Voraussetzungen erfüllt sind, können wir als erstes versuchen, ein neues
Raumobjekt in der Datenbank zu persistieren.

Das Projekt enthält bereits eine Klasse `Application` mit einer `main`-Methode, die sich ausführen lässt. Zusätzlich
enthält das Projekt die in den Folien vorgestellte `HibernateUtil`-Klasse, über die sich eine Instanz des
`EntityManager` erzeugen lässt. Mit diesem Objekt kann auf die Funktionen von Hibernate zugegriffen werden.

1. Erzeugen Sie in der Klasse `Application` eine neue Methode mit der Signatur `private static void persistRoom(Room room)`.

2. Fügen Sie innerhalb diese Methode die notwendigen Code-Zeilen ein, um das übergebene `room`-Objekt unter Verwendung des
`EntityManager` zu persistieren. Eine Hilfe bei der Implementierung finden Sie in den Vorlesungsfolien.

3. Denken Sie auch daran, die Transaktionsklammern in der Methode korrekt zu setzen. Zugriff auf die Transaktionen bietet
ebenfalls der `EntityManager`. Auch hier helfen die Folien.

4. Erzeugen Sie in der `main`-Methode ein neues `Room`-Objekt und übergeben Sie es an die erstellt `create`-Methode. Denken
Sie daran, dass Sie nicht alle Attribute setzen müssen, da diese ggf. von Hibernate generiert werden. Wenn
Sie alles richtig gemacht haben, wird dieses Objekt anschließend in der Datenbank persistiert.

## 5. Laden eines Raums

Ist der Raum in der Datenbank persistiert, soll er als nächstes aus dieser geladen werden. Die Implementierung geschieht
nach gleichem Muster wie beim Persistieren.

1. Erzeugen Sie in der Klasse `Application` eine neue Methode mit der Signatur `private static Room findRoomBy(Long id)`.

2. Fügen Sie innerhalb dieser Methode die notwendigen Code-Zeilen ein, um unter Nutzung des übergebenen Identifiers `id`
einen Raum zu laden. Eine Hilfe bei der Implementierung finden Sie in den Vorlesungsfolien.

3. Denken Sie auch daran, die Transaktionsklammern in der Methode korrekt zu setzen. Zugriff auf die Transaktionen bietet
ebenfalls der `EntityManager`. Auch hier helfen die Folien.

4. Rufen Sie in der `main`-Methode die neue `findRoomBy`-Methode auf und versuchen Sie, den zuvor erstellten Raum wieder in
ein neue Variable zu laden. Prüfen Sie bspw. durch Ausgabe der Raum-Attribute, ob dies auch funktioniert hat.

## 6. Aktualisieren eines Raums

Nach dem Schreiben und Lesen soll nun ein bereits in der Datenbank persistiertes Raum-Objekt aktualisiert werden.

1. Erzeugen Sie in der Klasse `Application` eine neue Methode mit der Signatur `private static void updateRoom(Room room)`.

2. Implementieren Sie die Methode und denken Sie wieder an die Transaktionsklammern.

3. Testen Sie die Implementierung durch einen Aufruf aus der `main`-Methode heraus.

## 7. Löschen eines Raums

Als letzte Operation soll nun ein bereits in der Datenbank persistiertes Raum-Objekt wieder gelöscht werden.

1. Erzeugen Sie in der Klasse `Application` eine neue Methode mit der Signatur `private static void deleteRoom(Room room)`.

2. Implementieren Sie die Methode und denken Sie wieder an die Transaktionsklammern.

3. Testen Sie die Implementierung durch einen Aufruf aus der `main`-Methode heraus.

## Zusatzaufgaben

Wenn die oberen Aufgabenschritte umgesetzt wurden, sind noch weitere Zusatzaufgaben denkbar, die Sie gerne nach Belieben einmal
angehen können:

1. Bauen Sie eine Methode `private static List<Room> findAllRooms()`, die alle Räume aus der Datenbank zurückgibt.

2. Schauen Sie sich einmal die Konfigurationen `hibernate.show_sql` und `hibernate.format_sql` in der `persistence.xml` an und
probieren Sie aus, was man damit machen kann.

3. In der `persistence.xml` gibt es eine Konfiguration `hibernate.hbm2ddl.auto`. Welche Optionen gibt es hier und was kann man mit
diesen erreichen?
