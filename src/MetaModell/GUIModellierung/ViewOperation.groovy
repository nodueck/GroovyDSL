package MetaModell.GUIModellierung

import MetaModell.DomainOperation

class ViewOperation extends DomainOperation {
	String beschriftung
	String gruppe
	boolean immerSichtbar = false
	boolean dialogEnde = false
	
	//Maskenfluss (dependency zwischen Operation und View/Entity usw)
}
