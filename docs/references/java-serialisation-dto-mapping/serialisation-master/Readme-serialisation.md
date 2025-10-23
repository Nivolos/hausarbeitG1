
# Übung: Persistenz mit Objektserialisierung

In dieser Übung wollen wir die einfachste Variante der Persistenz ausprobieren. Ziel ist dabei das 
Speichern eines oder mehrerer Datensätze durch Serialisierung innerhalb einer Datei. 

## Package erzeugen

Erzeugt euch eine Packagestruktur unterhalb des Java Ordners. 

Eine Idee wäre hier beispielsweise:
 
```java
de.nordakademie.iaa.serialization
```

## Erstellen des Models

Nun benötigen wir etwas, was wir speichern können. 

**Legt** euch im zuvor erzeugten Package eine
Java-Class an mit dem Namen `Person`.

Die erstellte Klasse repräsentiert eine Person in unserem späteren System und soll die folgenden 
Eigenschaften und Typen erhalten:
 
- Vorname (String)
- Nachname (String)
- Geburtstag (LocalDate)

**Erzeugt** die Eigenschaften als Feldvariablen und **generiert** die benötigten Getter- und Setter-Methoden. 

## Serialisation des Zustandes

**Erstellt** eine neue Java-Class im gleichen Package wie *Person* mit dem Namen `SerializationExample`. 

In dieser Java-Class werden wir nun die gesamte Logik entwickeln, die unsere Objekte speichert und lädt.

### Erstellen der Main-Methode

Um unser Java-Programm starten zu können, benötigen wir eine Main-Methode. Diese hat in
 Java immer die gleiche Signatur. 
 
 **Fügt** in die *SerializationExample* Klasse nun folgende Methode ein:
 
 ```java
public static void main(String[] args) throws IOException, ClassNotFoundException {
    //Objekte erstellen
    //Objekte speichern
    //Objekte wieder laden und ausgeben
}
```

### Erzeugen von Daten

Wie der erste Kommentar innerhalb der eben kopierten Methode bereits suggeriert, soll im 
ersten Schritt ein Objekt erstellt werden. 

**Erzeugt** euch eine neue Instanz einer Person innerhalb der Methode zum Speichern einer neuen 
Person. 

**Setzt** über die erzeugten Setter-Methoden alle drei Eigenschaften einer Person. 

### Speichern in einer Datei

Unser Persistieren ist technisch in zwei Schritte unterteilt:
 
1. Das Java-Objekt serialisieren 
2. Das serialisierte Objekt auf die Festplatte speichern (persistieren)

#### Objekte serialisieren

Objekte können mit Hilfe eines `ObjectOutputStream` serialisiert werden. Jedes Objekt, welches wir durch die Methode 
`ObjectOutputStream.writeObject(obectToPersist)` in den Stream geben, wird automatisch serialisiert. 

Damit wir einen `ObjectOutputStream` erzeugen können, benötigen wir noch einen `OutputStream`, also einen "Ausgang" auf den 
unsere serialisierten Objekte geschrieben werden sollen. Wir bedienen uns hierzu eines speziellen OutputStreams, der 
die Daten auf die Festplatte schreibt. Bei Java ist dies der `FileOutputStream`, welcher im Konstruktor noch 
einen Dateipfad erwartet, auf dem er schreiben soll. Als Pfad nehmen wir `./person.txt`. 

**Erzeugt** euch nun einen *FileOutputStream* und **verwendet** diesen zur Erzeugung eines *ObjectOutputStream*.

**Schreibt** eure Person in den *ObjectOutputStream* mit der Methode `writeObject(obj)`.  

**Führt** euer kleines Programm aus. **Tipp:** Klickt auf das "Play"-Symbol auf der Höhe der Methodendefinition.

Ihr solltet einen Fehler bekommen. 
Java serialisiert nur Klassen, wenn sie mit dem Interface `Serializable` markiert sind.    

**Erweitert** die `Person.java` und lasst die Klasse das Interace `Serializable` implementieren. 

**Führt** euer Programm erneut aus. Es sollte mit `code 0` beendet werden.  

### Daten auslesen

Wir haben nun Daten erzeugt und auf die Festplatte gespeichert. Genauer in die Datei `person.txt`.

**Öffnet** die Datei und schaut euch den Inhalt an. Könnt ihr die Eigenschaften wiederfinden?

Nun wollen wir die Daten auch wieder programmatisch auslesen. 

Dies funktioniert analog zum Abspeichern der Daten, allerdings nun mit einem `FileInputStream` und einem 
`ObjectIntputStream`.

**Lest** nun eure Person wieder aus der `person.txt` mit Hilfe der InputStreams und gibt die einzelnen Eigenschaften 
auf der Console aus. 

Die Ausgabe kann wie folgt aussehen: 

```bash
Vorname: Max
Nachname: Mustermann
Geburtstag: 2000-12-20

Process finished with exit code 0
```   

    
