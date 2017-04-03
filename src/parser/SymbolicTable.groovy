package parser

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import metamodell.DomainAbstraction
import metamodell.DomainReference

@Singleton
@Canonical
class SymbolicTable {
	
	HashMap<NameTypePair, Object> symbolTable = new HashMap<NameTypePair, Object>()
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
				}
				
				notYetResolvedObjects.remove(nameValuePair)
			}
		}
	}
}
