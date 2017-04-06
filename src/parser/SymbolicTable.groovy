package parser

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import metamodell.DomainAbstraction
import metamodell.DomainReference

@Singleton
@Canonical
class SymbolicTable {
	
	/**
	 * Wird zwischen den Modellelementen referenziert ist eine Art Symbolic Table erforderlich.
	 * Dies speichert die Objekte, die von anderen Objekten referenziert werden sollen.
	 * Bsp.: Contract referenziert einen Customer. Anstatt den Customer im Modell zu suchen, kann er leicht in der Symbolic Table gefunden werden.
	 */
	HashMap<NameTypePair, Object> symbolTable = new HashMap<NameTypePair, Object>()
	
	/**
	 * Ist das Objekt im Symbolic Table nicht vorhanden, wird es hier gehalten.
	 */
	HashMap<NameTypePair, Object> notYetResolvedObjects = new HashMap<NameTypePair, Object>()
	
	@EqualsAndHashCode
	@ToString
	class NameTypePair {
		String name //z.B. Contract
		String type //z.B. ValueObject
	}
	
	public void resolveObjects(){
		symbolTable.each { nameValuePair, object ->
			if(notYetResolvedObjects.containsKey(nameValuePair)){
				def emptyObject = notYetResolvedObjects.get(nameValuePair)
				def resolvedObject = symbolTable.get(nameValuePair)
				if(emptyObject instanceof DomainReference){
					(emptyObject as DomainReference).referencedObject = resolvedObject
					notYetResolvedObjects.remove(nameValuePair)
				}
			}
		}
		
		if(notYetResolvedObjects) {
			println "Folgende Objekte konnten nicht aufgelöst werden:"
			notYetResolvedObjects.each { println it	}
		}
	}
}
