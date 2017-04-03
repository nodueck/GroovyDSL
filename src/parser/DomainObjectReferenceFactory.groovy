package parser

import java.util.Map
import parser.SymbolicTable.*
import metamodell.DomainAbstraction
import metamodell.DomainModel
import metamodell.DomainReference
import metamodell.businessobjects.Aggregate
import metamodell.businessobjects.Entity
import metamodell.businessobjects.ValueObject
import groovy.util.FactoryBuilderSupport

class DomainObjectReferenceFactory extends AbstractFactory {

	def nameTypePair
		
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map map)
			throws InstantiationException, IllegalAccessException {
		map = map.isEmpty() ? new HashMap<String, Object>() : map; //leere Map ist immutable, deshalb ggf. eine neue Anlegen
		map.put("referenceType", name)
		
		if(!map.containsKey("type")){
			map.put("type", "entity") //convinience
		}
		nameTypePair = [name:value, type:map.get("type")]
		map.remove("type")
		return new DomainReference(map)		
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
		if(parent instanceof DomainAbstraction){
			(parent as DomainAbstraction).domainReferences.add(child)
		} else {
			throw new Exception('Parent of a BusinessObject needs to be a DomainModel')
		}
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
		if(SymbolicTable.instance.symbolTable.containsKey(nameTypePair)){
			(node as DomainReference).referencedObject = SymbolicTable.instance.symbolTable.get(nameTypePair) 
		} else {
			SymbolicTable.instance.notYetResolvedObjects.put(nameTypePair, (node as DomainReference))
		}		
	}


}
