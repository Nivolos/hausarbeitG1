# Angular Roommanagement

In dieser Übung wollen wir unsere NAK Raumverwaltung mit Hilfe von Angular umsetzen. 
Das Prinzip ist dabei das gleiche wie in der Struts Aufgabe. Wir verwalten Räume, die wir zu Beginn
tabellarisch darstellen wollen. Durch unterschiedliche Buttons (*Erstellen*, *Bearbeiten*, *Löschen*) springen wir, wenn nötig,
in die einzelnen Masken. 


## Aufbau und Vorbereitung

Schauen Sie sich die Ordnerstruktur des Projektes an. Wir haben einen Ordner auf der höchsten Ebene mit dem Namen `client`. Dieser enthält unseren
Angular Quellcode. Darin befindet sich unter anderem der `src`-Ordner mit einem Angular *root*-Module.

Wir wollen unsere Verwaltung der Raummaske in unterschiedliche Komponenten aufteilen. 

Die spätere Struktur soll dabei wie folgt aussehen: 

- `app`-Modul (root)
  - `rooms`-Component (Steuernde Komponente für die Raumverwaltung)
    - `room-list`-Component (Tabellarische Anzeige der Räume)
    - `room-form`-Component (Komponente für das Einzelformular eines Raums) 


**1.** Überprüfen Sie, welches dieser Module/Komponenten bereits vorhanden sind und welche noch fehlen. 

**2.** Was macht der Ordner `shared` innerhalb der `rooms`-Komponente? Wofür könnte er 
nützlich sein?

**3.** Öffnen Sie das Terminal in der IntelliJ (`ALT+F12`) und installieren Sie als erstes die
Abhängigkeiten mit dem Befehl `npm install`. (Hierfür müssen Sie sich im Ordner `client` befinden.)

**4.** Starten Sie die Anwendung mit dem Befehl `ng serve`.

## Initiales Laden der Räume

Damit wir überhaupt Räume angezeigt bekommen, müssen wir die Räume, welche angezeigt werden sollen, laden 
und die oben genannten `room-list`-Komponenten erstellen und  implementieren.

### Implementierung der `rooms`-Komponente

Wir wollen die bereits existierende `rooms`-Komponente verwenden, um die Liste der Räume zu halten und als "Vermittler"
zwischen den Subkomponenten `room-list` und `room-form` zu agieren. 
 
**1.** Fügen Sie der `rooms.component.ts` eine "public property" mit dem Namen und dem Typ `rooms: Room[] = [];` hinzu.

Hier werden wir unsere Liste von Räumen halten, die nachfolgend angezeigt und bearbeitet werden können.  

**2.** Erzeugen Sie den nachfolgenden Konstruktor: `constructor(private roomService: RoomService) {}`. 
                                                        
Dieser Konstruktor übermittelt uns beim Erzeugen der Komponenten den `RoomService`, von welchem wir die Daten 
beziehen können.

Was macht eigentlich das Wort `private` innerhalb der Parameter? 

**3.** Implementieren Sie die Methode `private reloadList()`. 

Wir wollen mit dieser Methode an einer zentralen Stelle innerhalb der Komponente den `roomService` nach der 
Liste aller Räume fragen und diese nachfolgend in unser `rooms`-Attribut übernehmen. 

*Tipp:* Sie erreichen den `roomService` über `this`. Da die Methode `listAllRooms()` ein Observable zurück gibt, 
können wir mit der Methode `subscribe((rooms: Room[]) => void)` auf das Ergebnis der Methode zugreife, 
wenn es verfügbar ist.

*Pro-Tipp:* Arrow-Functions! Ein Blick in die Folien könnte hier helfen.

Sowohl das Service- als auch das Observable-Konstrukt haben wir noch nicht detailliert besprochen, werden aber 
noch in der kommenden Vorlesung darauf eingehen.

Nun sollte automatisch über die Methode `ngOnInit()` unsere `rooms`-Variable mit Räumen gefüllt sein. 
Nur leider können wir diese noch nicht darstellen. Das tun wir jetzt...

### Erzeugen der `room-list`-Komponente

Lasse Sie uns die tabellarische Darstellung der Räume implementieren. Hierzu erzeugen wir eine neue Komponente.

**1.** Navigieren Sie mit Hilfe des Terminals zum Pfad `client/src/app/rooms` 

**2.** Nutzen Sie `ng create` um eine Komponente mit dem Namen `room-list` zu erzeugen. 

*Tipp*: Es bietet sich an, den CLI-Befehel `ng generate component` hierfür zu verwenden.

*Pro Tipp:* Noch kürzer geht es mit `ng g c`. 

**3.** Fügen Sie den Selektor der gerade generierten Komponente in der `rooms.component.html` hinzu. 

Sehr wahrscheinlich ist der selector für Ihre Komponente `app-room-list`, d.h. Sie können in der HTML einfach 
folgendes einfügen: 

````html 
<app-room-list></app-room-list>
````

**4.** Starten Sie die Anwendung wieder (falls sie nicht noch läuft) und schauen Sie, ob Sie `room-list works!` 
beim Aufruf von `http://localhost:4200` sehen. Sollte dies nicht der Fall sein, scheint irgendetwas 
schiefgelaufen zu sein. 

### Implementieren der Logik für die `room-list`-Komponente

Unsere Komponente soll eine Liste von Räumen darstellen. Bevor wir uns um die Darstellung kümmern, wollen wir 
sicherstellen, dass zumindest unsere UI-Logik bereits vorhanden ist. 

Wir befinden uns nun also in der `room-list.component.ts`. Damit unsere Komponente mit Räumen umgehen kann, 
benötigen wir wieder eine Variable die eine Liste von Räumen hält. 

**1.** Fügen Sie die public property `rooms: Room[] = [];` unserer Implementation hinzu. 

Wir wollen nun die besagten Räume nicht nochmal laden, denn unsere übergeordnete Komponente `rooms` lädt diese Räume 
ja bereits. Stattdessen wollen wir die Liste der Räume als `Input` für unsere Komponente definieren. 

**2.** Setzen Sie den `@Input()`-Decorator für `rooms` ein. 

Wir können nun unserer Komponente eine Liste von Räumen mitgeben, die sie anzeigen soll. 
Dieses neue Attribut ziehen wir am besten direkt in der `rooms.component.html` nach:

**3.** Fügen Sie `[rooms]="rooms"` unserem gerade eingefügten `app-room-list`-Tag hinzu.
 
### Entwickeln der View für die `room-list`-Komponente

Nun haben wir zwar die Daten, aber wir können Sie noch immer nicht darstellen. Diesem "Problem" widmen wir uns jetzt. 

**1.** Öffnen Sie die Datei, welche für die View unserer Komponenten zuständig ist.

*Tipp:* Die Datei heißt sicher irgendwas mit `room-list` und endet auf `.html`.  Hier wir auch unser 
bekanntes `room-list works!` wieder. 

**2.** Implementieren Sie nun eine Tabelle, die als Tabellenkopf wieder unsere bekannten Felder enthält: 

- Building
- Room Number
- Seats
- Projector present? 

**3.** In der darauf folgenden Tabellenzeile sollten Sie die entsprechenden Attribute der Räume ausgeben.

Beginnen Sie am einfachsten mit einer Zeile `<tr>`, die als Zelle `<td>` das jeweilige Attribut zur Spalte als 
*String interpolation* ausgibt. 

*Tipp:* Das würde für *Building* wie folgt aussehen: `<td>{{room.building}}</td>`.

**4.** Iterieren Sie mit Hilfe der Direktive `*ngFor` im `<tr>`-Tag über die Liste unserer Räume. 
Erzeugen Sie sich hierzu eine Laufvariable `room` wie in den Vorlesungsfolien gezeigt. 

**5.** Testen Sie die Anwendung. Sie sollten nun eine Liste von Räumen sehen. 

## Die erste *CRUD*-Funktionalität

Wir wollen nun noch die einfachste aller *CRUD*-Funktionalitäten entwickeln, nämlich das *Delete*. 

### Erweitern der UI mit einem Delete-Button

Lassen Sie uns den ersten Button hinzufügen, welcher es uns ermöglicht, einen Raum zu löschen. 
Leider müssen wir hierzu noch etwas anderes vorbereiten, denn wir können aktuell noch keinen Raum auswählen. 

Beginnen wir also mit der Auswahl eines Datensatzes.

#### Auswahl eines Raumes in der Liste

**1.** Erstellen Sie in `room-list.component.ts` ein "public property", mit dessen Hilfe wir einen 
ausgewählten Raum speichern können.

Weil der Raum nicht immer ausgewählt ist, sollte das Attribut wie folgt aussehen: `selectedRoom?: Room;`

**2.** Erstellen Sie die nachfolgende Methode

```typescript
onSelect(room: Room): void {
    this.selectedRoom = room;
}
```

Diese Methode können wir nun vom UI aufrufen, um einen Raum zu selektieren. 

**3.** Fügen Sie in `room-list.component.html` in unserer `<tr>`-Zeile einen Event-Listener für `click` ein. 

Einen Klick auf die Tabellenzeile soll den darin enthaltenen Raum an die `onSelect(room: Room)`-Methode übergeben. 

*Tipp:* Es handelt sich um ein *event binding*. 

**4.** Fügen Sie der `<tr>` Zeile noch das folgende *property binding* hinzu: `[class.selected]="room === selectedRoom"`. 

Überlegen Sie mal, was diese Zeile wohl bedeutet. 

**5.** Machen Sie die Auswahl sichtbar. 

Hierzu passen wir die `room-list.component.css` an und fügen beispielsweise nachfolgenden CSS-Code ein, 
damit unsere Tabellenzeile bei Auswahl hellblau hinterlegt ist: 

```css
.selected {
    background-color: lightblue;
}
```

### Die Delete-Funktionalität in der `room-list`-Komponente

Nun wollen wir unserer Komponente noch das Löschen beibringen. Erinnern Sie sich daran, dass die `room-list` zwar 
die Räume zum Anzeigen benötigt, diese aber eigentlich von der `rooms`-Komponente bekommt und auch von dieser
verwaltet werden?. Wir sollten daher das Löschen von Räumen auch wieder an die `rooms`-Komponente weiterleiten. 

**1.** Erstellen Sie in `room-list.component.ts` ein Attribut mit dem Namen `delete` und initialisieren 
Sie die Variable mit `new EventEmitter<Room>();`

**2.** Fügen Sie den `@Output()`-Decorator hinzu, damit wir später auf dieses Event hören können. 

Eine andere Komponente kann sich nun bei uns "registrieren" und auf gelöschte Räume reagieren. Das ist sehr 
zweckdienlich, da wir so die `rooms`-Komponente anmelden können und diese dann nachfolgend den Raum "wirklich" 
löschen kann. 
 
**3.** Erstellen Sie eine neue Methode mit folgendem Inhalt: 

```typescript
onDelete(): void {
    this.delete.emit(this.selectedRoom);
}
```

Diese Methode wollen wir aus dem UI aufrufen, wenn ein Nutzer auf unseren `Delete`-Button klickt. 

**4.** Fügen Sie der `room-list.component.html` den folgenden Button hinzu: 

```html
<button (click)="onDelete()" [disabled]="!selectedRoom">Delete</button>
```

Überlegen Sie, was wohl das *property binding* `disabled` an dieser Stelle tut. 


### Erweitern der `rooms`-Komponenten

Unsere `room-list`-Komponente hat nun alles, was sie zum Löschen eines Raumes benötigt. Nun müssen wir noch 
den Raum aus der `rooms`-Komponente löschen.

**1.** Erzeugen Sie in `rooms.component.ts` die Methode `onDeleteRoom(roomToBeDeleted: Room)` und implementieren 
Sie diese. 

Wir wollen in der Implementation den übergebenen Raum an den `roomService` und dessen Methode 
`deleteRoom(room: Room): Observable<any>` weiterleiten. Sobald das Observable etwas zurückmeldet, aktualisieren wir 
die Liste der Räume mit einem Aufruf von `this.reloadList()`. 

**2.** Fügen Sie der `rooms.component.html` das *event binding* für unsere *Delete*-Funktion hinzu. 

*Tipp:* Wir haben den Output-Decorator `delete` genannt und die aufzurufende Methode eben `onDeleteRoom`. 
Daraus ergibt sich das folgendes event binding für unsere `<app-room-list>` : `(delete)="onDeleteRoom($event)`. 

Nun sollte auch das Delete funktionieren.

# Testen testen testen

Testen Sie die Anwendung. Wie bekommen Sie wohl die Räume wieder, wenn Sie alle gelöscht haben?  

 
                                                     
