package metamodell

import metamodell.typesystem.DomainType
import groovy.transform.ToString

@ToString
class DomainProperty extends DomainAbstraction{

	DomainType type
	
	Requirement requirement
	
	boolean editierUnterstuetzung = false
	ChoiceType auswahlDialog
	boolean nichtEditierbar
	String beschriftung
	boolean flach
	SortierbarType sortierbarTyp
	String laenge = 0
	String erlaubteZeichen
	String werteBereich
	String regexp
	boolean pflicht
	boolean validieren
	String technicalName
	boolean immerVersteckt
	boolean trnsient
	Integer nachkommastellen
	
	
	enum ChoiceType {
		Auswahlfeld,
		Liste,
		Tabelle
	}
	
	enum SortierbarType {
		ja,
		nein,
		standard
	}
}
