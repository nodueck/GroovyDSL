package parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Singleton
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
			if(object != null && notYetResolvedObjects.containsKey(nameValuePair)){
				def parent = notYetResolvedObjects.get(nameValuePair)
				def child = symbolTable.get(nameValuePair)
				parent.add(child)
				notYetResolvedObjects.remove(nameValuePair)
			}
		}
	}
	
	
}
