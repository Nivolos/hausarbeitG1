# 📘 Prüfungsaufgabe – Bibliotheksverwaltung (Hausarbeit IAA)

## Für alle Themen verpflichtend

1. Vor und während der Entwicklung muss ein (kurz gehaltenes) **DV-Konzept** erstellt werden, das folgende Punkte beinhaltet:
   - **UML-Klassenmodell** der zentralen Geschäftsobjekte (fachliche Domäne)
   - **Dialogmodell** (Skizzen der Maskenlayouts mit Darstellung der Dialogflüsse)
   - Beschreibung der wesentlichen **Testfälle**  
     *(eine automatische Durchführung der Testfälle ist nicht erforderlich)*

2. **Annahmen/Abgrenzungen** sind mit dem „Kunden“ abzusprechen und zu dokumentieren.

3. Die Entwicklung muss unter Nutzung der in der Vorlesung besprochenen Architektur (**Angular, Spring, Hibernate**) erfolgen.  
   Die Aufgabe wird mit **Angular** umgesetzt.  
   → Der Einsatz von Zusatztechnologien ist **erlaubt**, aber **nicht erforderlich**.

4. Es gelten die in den Vorlesungsfolien vorgestellten Musskriterien und funktionalen/nicht-funktionalen Anforderungen.  
   Dazu gehören insbesondere (Eingabe-)Validierungen in der Anwendung.

5. **Abgabe** per E-Mail an die Dozenten mit Angabe des Repository-Namens  
   im Format **`hausarbeit_iaa_<zenturie>_<gruppe>`** sowie der letzten Commit-ID.
   Die Dozenten sind als **Reporter** zu berechtigen.

**Folgende Bestandteile sind verpflichtend:**
1. Vollständige Anwendung mit Quellcode (IntelliJ IDEA-Projekt)
2. DV-Konzept (Verzeichnis: `documentation`)
3. Installationsanleitung
4. Eidesstattliche Versicherung (Selbstständigkeitserklärung)
5. Projektverlauf mit Stundenprotokoll (Verzeichnis: `tasklogs`)
6. Die Datenbank muss in einen **initialen Stand** versetzbar sein, der einen Basisdatenbestand enthält und Tests ermöglicht.
7. Jedes Teammitglied führt ein eigenes **Stunden- und Aufgabenprotokoll**.
8. **Hilfsmittel und Quellen** sind vollständig anzugeben.
9. Nach Abgabe erfolgt eine **Feedbackrunde** mit Detailfragen zur Arbeit.  
Teilnahme ist **verpflichtend** für alle Teammitglieder.

---

## 🏛️ Thema: Bibliotheksverwaltung

Für die Bibliothek der Nordakademie soll ein neues Verwaltungssystem entwickelt werden,  
das die Verwaltung des Bücherbestands und die Verfolgung der Ausleihvorgänge ermöglicht.

**Ziel:**  
DV-technische Konzeption und Realisierung der nachfolgend aufgezählten Anwendungsfälle  
im Rahmen einer webbasierten Anwendung.

---

## 🎯 Anwendungsfälle

### 1. Verwaltung des Bibliotheksbestands
CRUD-Funktionen für Publikationen: Anzeigen, Anlegen, Bearbeiten, Löschen.  
Felder einer Publikation:
- Eindeutiger NAK-Publikationsschlüssel *(Pflichtfeld)*
- Titel
- Autor(en)
- Veröffentlichungsdatum
- Verlag
- Art der Publikation *(z. B. Buch, Zeitschrift, Hausarbeit, Bachelorarbeit etc.; Auswahlfeld)*
- ISBN *(optional)*
- Schlagwörter *(Mehrfachauswahl möglich)*
- Anzahl der Exemplare im Bestand

Nicht alle Felder sind immer befüllbar (z. B. keine ISBN bei Hausarbeit).

---

### 2. Suche nach Publikationen
Das System bietet eine komfortable Suchfunktion mit optionalen Kriterien.  
Ergebnisse werden in einer sortierbaren Tabelle angezeigt, aus der heraus Datensätze bearbeitet werden können.

---

### 3. Erfassung von Ausleihvorgängen
Die Anwendung soll Ausleihen und Rückgaben abbilden.  
Für einen Ausleihvorgang werden gespeichert:
- Ausgabedatum
- Publikation
- Ausleiherdaten (Vorname, Nachname, Matrikelnummer)
- Rückgabedatum *(automatisch vergeben)*

**Regel:**  
Ein Ausleihvorgang ist nur möglich, wenn noch Exemplare verfügbar sind.  
Sonst muss eine Rückgabe erfolgen, bevor erneut ausgeliehen werden kann.

---

### 4. Übersicht offener Ausleihvorgänge
- Tabellarische Gesamtübersicht aller ausgeliehenen Exemplare  
- Detailansicht einer Publikation zeigt ihre offenen Ausleihen

---

### 5. Mahnwesen
Säumige Ausleiher sollen erkannt werden.

- Eine Ausleihe gilt als **überzogen**, wenn das Rückgabedatum +1 Tag überschritten ist.  
- Für überzogene Ausleihen wird ein **Mahnvorgang** eröffnet (max. 3 Mahnungen, je mit Datum).  
- Zwischen zwei Mahnungen liegt i. d. R. eine Woche.  
- Hauptseite zeigt Tabelle aller offenen Mahnfälle und noch zu mahnender Ausleihen.

**Mahnvorgang endet:**
1. durch Rückgabe  
2. oder durch Markierung als **„Verlust“**  
- darf erst nach 3 Mahnungen erfolgen  
- Bestand wird um 1 Exemplar reduziert

---

### 6. Verwaltung von Stammdaten
CRUD-Funktionen für:
- Publikationsarten  
- Schlagwörter  
- Ausleiher  

---

### 7. Sortierbare Tabellenanzeigen
Alle Tabellenansichten (z. B. Suchergebnisse) müssen auf- und absteigend sortierbar sein.

---

## 🧩 Weitere Randbedingungen
- Sämtliche Eingaben müssen auf gültige Werte geprüft werden (Validierung).  
- Datenhaltung **normalisiert**.  
- Rückgabedatum wird automatisch auf Basis einer zentralen **Verleihperiode** gesetzt  
(Parameter, nicht über UI pflegbar).

---

## ⚙️ Optionale Anwendungsfälle

### a) Verlängerung eines Verleihvorgangs
- Eine Ausleihe kann bis zu zweimal verlängert werden.  
- Jede Verlängerung verschiebt das Rückgabedatum um eine Verleihperiode.  
- Eine Verlängerung beendet ggf. laufende Mahnvorgänge.

---

> 📄 **Hinweis:**  
> Diese Datei (`docs/Pruefungsaufgabe.md`) dient dem Codex-Agenten als Referenz für Anforderungen,  
> Akzeptanzkriterien und Funktionsumfang der Bibliotheksverwaltung.
