# Übung Struts Room Management

Inhalt dieser Übung ist die Erstellung einer kleinen Raumverwaltung mit zwei Oberflächen mithilfe von Struts 2. 
Dabei ging es im ersten Schritt um die folgenden, in der Vorlesung vorgestellten Themen:

* Arbeiten mit dem Action Context und dem Value Stack
* Einsatz von häufig verwendeten Struts-Tags
* Einsatz von Iterationen
* Navigation
* Einsatz von Mehrsprachigkeit

Im zweiten Schritt sollen nun die folgenden Themen bearbeitet werden:

* Einsatz von Validierung (deklarativ und quellcode-basiert)
* Einsatz auf Tiles

## Aufbau

### Domain Model

Im Projekt ist bereits ein Domain Model implementiert. Es handelt sich hierbei um eine simple Klasse, die einen
Raum repräsentiert. Die Klasse enthält die folgenden Eigenschaften:

* `String building`: Buchstabe des Gebäudes
* `Integer roomNumber`: Nummer des Raums
* `Integer seats`: Anzahl der Sitzplätze
* `boolean projectorPresent`: Flag für das Vorhandenseins eines Beamers

### Service

Das Projekt enthält ein einfaches Service-Interface, dass in diesem Beispiel das Backend repräsentiert. Der
Service bietet dabei die folgenden Methoden zum Bearbeiten eines Raums an:

* `List<Room> listAllRooms()`: Laden aller Räume
* `Optional<Room> loadRoom(String building, Integer roomNumber)`: Laden eines einzelnen Raums
* `void saveRoom(Room room)`: Anlegen/Ändern eines Raums
* `void deleteRoom(Room room)`: Löschen eines Raums

Auf die Implementierungsklasse des Services kann nicht direkt zugegriffen werden - und das ist auch so beabsichtigt.
Die Struts-Actions sollen ausschließlich mit dem Interface arbeiten. Wenn ein Zugriff auf den Service benötigt wird,
geschieht dies durch den folgenden Code:

```java
RoomService roomService = ServiceFactory.getRoomService();
```

## Aufgaben 1. Teil

Die folgenden, einzelnen Aufgaben sollen im Rahmen der Übung umgesetzt werden:

1. Implementieren einer Tabellenanzeige für eine Liste von Räumen. Dies beinhaltet sowohl das Bauen der Java Server Page
als auch der Action.
2. Implementieren eines einfachen Formulars zum Editieren aller Attribute eines einzelnen Raums. Auch hier ist sowohl
die Implementierung der Java Server Page als auch der Action notwendig.
3. Implementierung einer Datensatzauswahl in der Tabelle und einer Navigation zwischen den Oberflächen
4. Umstellen der Oberflächen für eine Unterstützung von Mehrsprachigkeit

Die einzelnen Aufgaben sind im Folgenden genauer beschrieben.

### Implementierung einer Tabellenanzeige

Ziel ist es, eine generierte Liste von Räumen in einer Tabelle anzuzeigen. Der oben vorgestellte Service ist dabei
so gebaut, dass ein Abruf der Raumliste bereits eine Ergebnisliste liefert.

*1. Implementierung einer Java Server Page für die Liste.*

Für die Anzeige der Raumtabelle soll eine neue Java Server Page `roomlist.jsp` erstellt werden. Nutzen Sie die bereits
besprochenen Struts-Tags, um die Oberfläche zu gestalten. Es geht erst einmal um die reine Darstellung, d.h.
Schaltflächen oder eine Auswahl von Tabellenzeilen werden nicht benötigt.

Eine Interation über eine Liste von Daten erreicht man übrigens über das Iterator-Tag:

```
<s:iterator value="rooms">
    <!-- Hier wird iteriert -->
</s:iterator>
```
Überlegen Sie bitte einmal, wie man innerhalb der Iteration auf das aktuelle Element zugreifen kann. Die Antwort
ist dabei ggf. einfacher als gedacht.

*2. Implementierung der Action-Klasse.*

Erzeugen Sie die dazu gehörige Action-Klasse `de.nordakademie.iaa.roommanagement.action.ShowRoomListAction` und implementieren
Sie die Klasse. Die Liste der Räume bekommen Sie über den Einsatz von `ServiceFactory` und `RoomService` (siehe oben).

*3. Konfiguration der Action in der `struts.xml`.*

Abschließend ist eine Konfiguration der erstellten Action in der `struts.xml` notwendig. Die Action soll dabei mit dem
Namen `showRoomList` konfiguriert werden.

### Implementierung eines Formulars

Es soll ein einfaches Eingabeformular angezeigt werden, mit dem der Benutzer alle Attribute eines Raums editieren kann.
Die notwendigen Schritte hierfür sind nachfolgend aufgelistet.

*1. Implementierung einer Java Server Page für das Formular.*

Für die Bearbeitung eines Raums soll eine neue Java Server Page `roomform.jsp` erstellt werden. Nutzen Sie die bereits
besprochenen Struts-Tags, um die Oberfläche zu gestalten. Bei der Seite handelt es sich um ein Formular, dass
alle Attribute des Raums als Eingabeelemente darstellt. Zusätzlich muss es zwei Schaltflächen geben: "Speichern" und
"Abbrechen".

*2. Implementierung der Action-Klasse.*

Erzeugen Sie die dazu gehörige Action-Klasse `de.nordakademie.iaa.roommgmt.action.RoomAction` und implementieren
Sie die Klasse. Diese Action-Klasse hat dabei eine Besonderheit: Anders als bisher wird diese Action mehrere
Methoden haben. Da die Action letztendlich die Bearbeitungsschritte für einen einzelnen Raum zulässt, sind die
folgenden Methoden notwendig:

* `String execute()`: Stellt das leere Formular da (neuer Raum wird angelegt).
* `String load()`: Lädt einen bestehenden Raum und zeigt ihn im Formular an.
* `String save()`: Speichert die Änderungen an einem Raum.

Sind auch noch weitere Aktionen notwendig? Lassen Sie uns an dieser Stelle einmal gemeinsam überlegen, wie man eine
solche "CRUD-Bearbeitung" am besten umsetzen kann und welche Attribute und Operationen hierfür notwendig sind.

*3. Konfiguration der Action in der `struts.xml`.*

Abschließend ist wieder eine Konfiguration der erstellten Action in der `struts.xml` notwendig. Basis für die 
Konfiguration ist die gemeinsam festgelegte Struktur.

*4. Einfügen einer "Hinzufügen"-Schaltfläche in die Tabellenansicht*

Damit das gebaute Formular überhaupt aufgerufen werden kann, brauchen wir eine neue Schaltfläche
zum Anlegen eines Raums. Fügen Sie diese Schaltfläche der `roomlist.jsp` hinzu und rufen Sie die Action auf,
die das leere Raumformular anzeigt.

### Datensatzauswahl und Navigation

Wenn die beiden Formulare und Action-Klassen implementiert sind, fehlt als letztes Bindeglied noch die Auswahl eines
Datensatzes in der Tabelle. Dies ist notwendig, damit neben der Neuanlage eines Raums auch die Bearbeitung eines
bestehenden Raums möglich ist.

Eine Möglichkeit ist hier bspw. die Nutzung von Radiobuttons. Das Tag für die Erstellung von Radiobuttons haben wir 
in den Folien schon besprochen, allerdings nur in Verbindung mit einer Liste von Auswahlmöglichkeiten. 
Hier wollen wir jedoch, dass nur ein Radiobutton erzeugt wird, und zwar in jeder Tabellenzeile.

Durch einen Trick ist dies möglich. Im folgenden Beispiel wird ein Radiobutton erzeugt, der als Wert das 
Attribut `exampleKey` nutzt:

```
<tr>
    <s:radio name="selection" list="#{exampleKey:''}" theme="simple"/>
</tr>
```

Die etwas seltsam aussehende Notation im Attribut `list` erstellt per OGNL eine Map, in der als Schlüssel der Wert 
des Attributs `exampleKey` genutzt wird, als Wert enthält die Map einen leeren String. Das führt dazu, dass der 
Radiobutton keinen Text anzeigt, was wir in der Tabellenauswahl auch nicht wollen. 

Die Angabe `theme="simple"` sorgt schließlich dafür, dass keine Layouttabelle von Struts erzeugt wird – diese würde 
unsere Tabellenanzeige stören. 

Obwohl wir so in jeder Zeile einen eigenen Radiobutton erzeugen, verhalten sich die Buttons gemeinsam trotzdem als 
Gruppe. Wir nutzen hier das übliche HTML-Verhalten: Da alle das gleiche `name`-Attribut haben, kann immer nur 
einer zurzeit ausgewählt sein.

Da ein Datensatz ausgewählt werden soll, bietet sich als Schlüsselwert für den Radiobutton der Primärschlüssel des 
jeweiligen Datensatzes an. Im hier beschriebenen Fall wäre das die Kombination von Gebäude- und Raumnummer. Die 
beiden Werte lassen sich zum Beispiel durch ein Pipe-Zeichen trennen:

```
<s:radio name="selectedKey" list="#{building + '|' + roomNumber:''}" theme="simple"/>
```

*1. Versuchen Sie einmal, das oben beschriebene Verfahren zu implementieren.*

*2. Laden Sie den ausgewählten Raum und zeigen Sie diesen mit allen Daten im Formular an.*

### Umstellung auf Mehrsprachigkeit

Mit hoher Wahrscheinlichkeit sind alle von Ihnen erstellten Oberflächen nun ausschließlich direkt in einer Sprache
gebaut. In großen Anwendungen ist das i.d.R. nicht ausreichend.

*1. Stellen Sie die Oberflächen auf Mehrsprachigkeit um.*

Die Grundvoraussetzungen hierfür sind im Projekt bereits geschaffen. Es geht also nur noch um die Erstellung
von Textbausteinen und die Einbindung in die jeweiligen Java Server Pages.

### Löschen von Räumen

Eine Operation ist noch offen: Man kann aktuell noch keine Räume löschen. Versuchen Sie bitte
einmal, diese Funktion eigenständig zu implementieren. Dabei sind unterschiedliche
Lösungen möglich, je nachdem, wie Sie die Benutzerführung gestalten wollen.

## Aufgaben 2. Teil

Die folgenden Aufgaben sollen im zweiten Teil bearbeitet werden:

1. Überprüfung der Formulareingaben durch deklarative Validierung
2. Überprüfung der Tabellenauswahl durch code-basierte Validierung
3. Umstellung auf eine template-basierte Oberfläche

Im Folgenden werden die Aufgaben wieder näher beschrieben.

### Überprüfung der Formulareingaben durch deklarative Validierung

Der Einsatz der deklarativen Validierung ist im Projekt schon etwas vorbereitet, indem es eine leere Templatedatei
für die Hinterlegung der Validierungsregeln gibt:

```
/src/main/resources/de/nordakademie/iaa/roommgmt/action/RoomAction-saveRoom-validation.xml
```

Der lange Pfad ergibt sich dadurch, dass sich die Datei in der gleichen Verzeichnisstruktur wie die Action-Klasse
befinden muss, auf die sich die Datei bezieht. Durch die Maven-Aufteilung in Javacode (`src/main/java`) und
Ressourcen (`/src/main/resources`) gibt es die Verzeichnisstruktur `de/nordakademie...` nun zweimal.

Der Name der Datei gibt an, wofür die Validierung gedacht ist:
* `RoomAction`: Die Validierung gilt für die Action-Klasse `RoomAction`.
* `saveRoom`: Die Validierung gilt nur für den Alias (Actionname) `"saveRoom"`.
* `validation`: Verpflichtendes Suffix für eine Validierungskonfiguration.

Damit die Validierung funktioniert, sind aber noch zwei vorbereitende Schritte zu Anfang notwendig.

*1. Leiten Sie die Klasse `RoomAction` von der Klasse `com.opensymphony.xwork2.ActionSupport` ab.*

Die Validierung erfordert ein paar Methoden in der Action. Diese sind glücklicherweise schon in der genannten
Oberklasse implementiert, so dass dies der einfachste Weg ist.

*2. Konfigurieren Sie ein neues Result `"input"` für die `"saveRoom"`-Action.*

Aktuell hat die `"saveRoom"`-Action nur ein Default-Result in der Konfiguration (`struts.xml`). Da wir
bei einem Validierungsfehler allerdings auf die Eingabeseite zurück müssen, ruft Struts standardmäßig das Result
`"input"` auf. Dieses muss daher in der Konfiguration hinterlegt werden und auf die Eingabemaske verweisen:

```xml
<result name="input">/roomform.jsp</result>
```

*3. Deklarieren Sie die Validierungsregeln für das Formular*

Die Deklaration erfolgt in der Datei `RoomAction-saveRoom-validition.xml`. Hilfestellung geben hierbei die Folien
aus der Vorlesung.

Die folgenden Regeln sollen deklariert werden:

| Formularfeld    | Validierungsregeln                     |
| --------------- | -------------------------------------- |
| room.building   | Mussfeld, genau ein Zeichen            |
| room.roomNumber | Mussfeld, Zahlen im Bereich von 1-999  |
| room.seats      | Mussfeld, Zahlen im Bereich von 1-9999 |

*4. Definieren Sie sinnvolle Fehlermeldungen im Resource Bundle*

Die Fehlermeldungen können direkt in der Deklaration der Regeln angegeben werden, aber sinnvoller ist es,
auch die Fehlermeldungen in das Resource Bundle mit den Textbausteinen auszulagern. Schauen Sie auch hier ggf.
einmal in die Folien, wie so eine Konfiguration aussieht.

*5. Testen Sie die Validierung*

Schauen Sie zum Schluss einmal, ob die Validierung funktioniert. Sie sollte nur beim Speichern des Formulars
aufgerufen werden.

### Überprüfung der Tabellenauswahl durch code-basierte Validierung

Neben der deklarativen Validierung kann man auch eine code-basierte Validierung direkt in der Action-Klasse bauen.
Wir nutzen diese Möglichkeit, um sicherzustellen, dass beim Aufrufen von "Bearbeiten" oder "Löschen" auch ein
Datensatz ausgewählt wurde.

*1. Überschreiben Sie in der Action-Klasse `RoomAction` die Methode `validate()` aus der Oberklasse.*

*2. Implementieren Sie die Validierungslogik* 

Die Validierung soll dann fehl schlagen, wenn kein Raum in der Tabelle ausgewählt wurde. Überlegen Sie einmal,
wie der Code hierfür aussehen müsste bzw. was überprüft werden muss. Und denken Sie bitte auch daran, dass die
`validate()`-Methode bei jede Action-Methode in der Klasse aufgerufen wird.

*3. Geben Sie eine Fehlermeldung aus, wenn die Validierung nicht erfolgreich war.*

Fehlermeldungen lassen sich direkt in der `validate()`-Methode ausgeben:

```java
addActionError("Dies ist ein Fehlertext");
```

Versuchen Sie idealerweise, auch diesen Text in das Resource Bundle auszulagern.

*4. Fügen Sie das notwendige "input"-Result hinzu*

Auch für diese Validierung ist ein weiteres `"input"`-Result notwendig. Überlegen Sie, für welche Action dies
notwendig ist ... und auf welche Seite zurückgegangen werden muss.

*5. Zeigen Sie die Fehlermeldung an*

Da es sich bei der Fehlermeldung um keine feldspezifische handelt, wird die Meldung standardmäßig von Struts
nicht dargestellt. Hierfür ist das Hinzufügen eines neuen Tags in die Java Server Page notwendig:

```xml
<s:actionerror/>
```

*6. Testen Sie die Validierung*

Überprüfen Sie, ob die Anwendung noch vollständig funktioniert.

### Umstellung auf eine template-basierte Oberfläche

Als letztes soll die Anwendung auf ein Templating umgestellt werden, d.h. die einzelnen Anwendungsmasken werden
aus mehreren Teilen zusammengesetzt. Grundlage hierfür ist das Zusatzframework Tiles, das bereits in der Anwendung
konfiguriert ist (siehe Folien).

*1. Erzeugen Sie eine neue Java Server Page `template.jsp`*

Diese JSP wird das Basistemplate für die Anwendung und wird zusätzlich die einzige, vollständige HTML-Seite sein.

*2. Füllen Sie die erstellte JSP mit einer "Grundseite"*

Nachfolgend ein Beispiel für ein solches Grundgerüst:

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
<head>
	<title>Room Management</title>
	<s:head/>
</head>
<body>
<!-- <Header> -->
<hr/>
<!-- <Content> -->
</body>
</html>
```

*3. Fügen Sie dem Template die Tiles Tag Library hinzu*

Die folgende Tag Library muss zusätzlich zur Struts Tag Library eingebunden werden:

```
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
```

*4. Fügen Sie Platzhalter für die Inhaltsbereiche `<Header>` und `<Content>` ein*

Im Beispiel oben sind zwei Bereiche markiert: `<Header>` und `<Content>`. An diesen beiden Stellen sollen
später andere Seiteninhalte eingefügt werden. Die dafür notwendigen Platzhalter kann man über die Tiles Tag Library
erzeugen:

```html
<tiles:insertAttribute name="header"/>
```

*5. Erzeugen Sie eine neue Java Server Page `header.jsp` mit einem Kopfbereich*

Der Inhalt dieser Datei soll später in den Bereich `<Header>` eingefügt werden. Beim Erstellen der Seite geht es hier 
wirklich nur ums Prinzip: Eine einfache Überschrift reicht.

Aber Achtung: Es handelt sich hierbei um einen Inhalt, der später in das Template eingefügt wird. D.h. die Datei 
darf keine vollständige HTML-Struktur beinhalten, da wir ansonsten HTML-Seiten schachteln würden.

*6. Erstellen Sie eine Definition für das Template in der Tiles-Konfiguration*

Das Template muss anschließend in der Tiles-Konfiguration (`/src/main/webapp/WEB-INF/tiles.xml`) deklariert werden.
Ein Beispiel hierfür finden Sie in den Vorlesungsfolien.

Stellen Sie dabei sicher, dass der Bereich `"header"` mit dem Inhalt der eben erstellten Datei `header.jsp`
vorbelegt wird.

*7. Bereiten Sie die JSPs `roomlist.jsp` und `roomform.jsp` vor.*

Aktuell handelt es sich bei den Dateien noch um vollständige HTML-Seiten. Aber auch hier gilt nun, dass der Inhalt
in das Template eingefügt wird. Deshalb müssen eventuell dann doppelte HTML-Strukturen entfernt werden.

*8. Erstellen Sie Definitionen für die beiden Masken in der Tiles-Konfiguration*

Die Definitionen sollen jeweils die zuvor erstellte Definition für das Template erweitern. Verwenden Sie
folgende Namen für die Definitionen:

* `roomlist.jsp`: `"roomList"`
* `roomform.jsp`: `"roomForm"`

*9. Passen Sie die Results in der Action-Konfiguration an, so dass nun die Tiles-Masken verwendet werden.*

Die Umstellung auf Tiles erfordert auch eine Anpassung der Results, da nun nicht mehr die JSPs direkt geladen werden,
sondern die in der Tiles-Konfiguration definierten Masken.

Nachfolgend ein Beispiel:

```xml
<result type="tiles">roomList</result>
<result name="input" type="tiles">roomForm</result>
```

*10. Testen Sie die Anwendung.*

Ende gut, alles gut?