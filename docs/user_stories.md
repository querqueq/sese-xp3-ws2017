# Hotelverwaltung

## User stories von Seite 1-2 des Pflichtenhefts

~~**Zimmer buchen**~~

**Kundenverzeichnis durchsuchen**

  Als Mitarbeiter kann man Kundenstammdaten auflisten und durch Eingabe von Suchfiltern die Auflistung beschränken, um diese in weiterer Folge zu ändern, Reservierungen zu tätigen, Rechnungen und Reservierungen zu stornieren und gegebenfalls Kunden zu informieren.

**Kundenstammdaten erfassen als Mitarbeiter**

  Als Mitarbeiter kann man neue Kunden erfassen, um zukünftig Reservierungen für diese zu tätigen.
  
  ~~Ein Mitarbeiter kann im Laufe einer Reservierung neue Kunden erfassen.~~

**Kundenstammdaten ändern**

  Als Mitarbeiter kann man die Kundenstammdaten eines Kunden editieren, um die Kundenstammdaten 
  zu korrigieren und aktualisieren.
  
**Rechnung ausstellen**

  Als Mitarbeiter kann man für ein oder mehrere Zimmer für einen oder mehrere Kunden eine Rechnung 
  für einen ununterbrochenen Zeitraum ausstellen. Falls eine Reservierung besteht, 
  kann diese als Vorlage für die Rechnung verwendet werden. 
  Es können nur Rechnung für Zimmer ausgestellt werden, welche nicht für den 
  Buchungszeitraum belegt bzw. reserviert sind. Rechnungen können nicht nachträglich geändert werden, 
  nur storniert und neu ausgestellt werden.

**Rechnung stornieren**

  Als Mitarbeiter kann man Rechnungen stornieren, um Fehler zu berichtigen und um Geld zurückzuerstatten.

**Rechnung suchen**

  Als Mitarbeiter kann man alle Rechnungen auflisten und durch Eingabe von Suchfiltern die Auflistung
  beschränken, um diese in weiterer Folge zu stornieren oder vollständig anzuzeigen.

**Gesamtüberblick über Zimmerauslastung**

  Als Mitarbeiter kann man sich die Auslastung alle Zimmer für einen vom Mitarbeiter bestimmten Zeitraum anzeigen
  lassen, um Kunden über Buchungs- und Reservierungsmöglichkeiten zu informieren und in einem weiterem Schritt die Daten 
  zu einem Zimmer bearbeiten.

**Zimmer erfassen**

  Als Mitarbeiter kann man Zimmern im Hotelverwaltungssystem erfassen, um diese in weiterer Folge in Reservierungen 
  und Rechnungen zu referenzieren und um etwaige Mängel erfassen.

**Zimmer-Daten bearbeiten**

  Als Mitarbeiter kann man Zimmer-Daten bearbeiten, um Fehler zu korrigieren bzw. Preise anzupassen.

**Zimmerverzeichnis durchsuchen**

  Als Mitarbeiter kann man alle Zimmer-Daten mit Suchfiltern durchsuchen, um deren Status überprüfen zu können und 
  um deren Daten ändern zu können.

**Mitarbeiter erstellt Reservierung**

  Als Mitarbeiter kann man für min. einen Kunden und min. ein Zimmer eine Reservierurng begrenzt auf einen
  endlichen Zeitraum erstellen. Reservierungen dürfen sich nicht mit anderen Reservierungen oder Buchungen 
  überschneiden.

**Reservierung stornieren**

  Als Mitarbeiter kann man eine Reservierung stornieren solange dies früh genug erfolgt und noch möglich ist, um eine falsch getätigte Reservierung bzw. eine nicht mehr benötigte
  Reservierung nichtig zu machen.

**Reservierungsstornierungsabbrechzeitraumspezifikation**

  Als Mitarbeiter kann man den Zeitraum vor dem Aufenthalt konfigurieren, in dem man eine Reservierung nicht mehr stornieren kann.

## Fragen

* Wieso kann eine Reservierung von mehreren Kunden getätigt werden und wie soll diese Reservierung gehandelt werden?
* Was heißt nachträglich storniert?
  * Nach erfolgter Bezahlung
  * Nach konsumierter Leistung
* Was passiert mit bereits stornierten Resevierungen, wenn der Reservierungsstornierungsabbrechzeitraum geändert wird, so dass die Stornierung ungültig wäre?
* Soll die Reservierungsstornierungsabbrechzeitraumspezifikation für alle bestehenden Reservierungen übernommen werden oder nur für alle zukünftigen?
* Nur Kunden können die Web-Oberfläche nutzen zum Reservieren. Heißt das eine Person die noch nie Kunde war kann kein Kunde via der Web-Oberfläche werden?
* Wie erhält ein Vorort entstandener Kunde seine Zugangsdaten für die Web-Oberfläche?
* Können Kundenstammdaten erfasst werden ohne das eine Person bereits eine Leistung in Anspruch genommen hat?
* Kann sich ein Kunde selbst über die Web-Oberfläche registrieren?
* Kann ein Kunde seine eigenen Stammdaten editieren?
* Müssen Gäste, welche nicht die Zimmer bezahlen, aber bspw. ein Einzelzimmer belegen, trotzdem ihre
  Personalien angeben?
* Ist es möglich das Zimmer derart große Mängel aufweisen, dass diese gesperrt werden müssen und somit nicht zur Buchung verfügbar sind? (Bspw. Sperrung auch wegen Renovierungsarbeiten)
