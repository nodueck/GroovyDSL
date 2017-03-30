package MetaModell

import MetaModell.TypeSystem.DomainType

class DomainProperty extends DomainAbstraction{

	DomainType type
	def attributes
	
	boolean loeschweitergabe = false
	boolean foreignKey = false
	
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
	
	
	
	@Override
	String toString() {
		return "${this.name} : ${this.type} with attributes: ${attributes}"
	}
	
	
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
