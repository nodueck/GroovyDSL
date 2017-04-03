package parser

import java.util.Map
import parser.SymbolicTable.NameTypePair
import MetaModell.DomainAbstraction
import MetaModell.DomainModel
import MetaModell.DomainReference
import MetaModell.BusinessObjects.Aggregate
import MetaModell.BusinessObjects.Entity
import MetaModell.BusinessObjects.ValueObject
import groovy.util.FactoryBuilderSupport

class DomainObjectReferenceFactory extends AbstractFactory {

	NameTypePair id
		
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map map)
			throws InstantiationException, IllegalAccessException {
		map = map.isEmpty() ? new HashMap<String, Object>() : map; //leere Map ist immutable, deshalb ggf. eine neue Anlegen
		map.put("referenceType", name)
		map.put("referencedObject", value)
		id = new NameTypePair(name, value)
		
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
		if(SymbolicTable.instance.symbolTable.containsKey(id)){
			(node as DomainReference).referencedObject = SymbolicTable.instance.symbolTable.get(id) 
		} else {
			SymbolicTable.instance.notYetResolvedObjects.put(id, parent)
		}		
	}


}
