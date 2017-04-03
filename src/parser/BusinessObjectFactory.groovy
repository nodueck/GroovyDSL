package parser

import java.util.Map
import metamodell.DomainModel
import metamodell.businessobjects.Aggregate
import metamodell.businessobjects.Entity
import metamodell.businessobjects.ValueObject
import groovy.util.FactoryBuilderSupport

class BusinessObjectFactory extends AbstractFactory {
	
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map map)
			throws InstantiationException, IllegalAccessException {
		map = map.isEmpty() ? new HashMap<String, Object>() : map; //leere Map ist immutable, deshalb ggf. eine neue Anlegen
		map.put("name", value)
		
		def businessObject
		switch(name) {
			case 'entity':
				businessObject = new Entity(map)
				break
			case 'valueObject':
				businessObject = new ValueObject(map)
				break
			case 'aggregate':
				businessObject = new Aggregate(map)
				break
			default:
				throw new Exception('Not Supported')
		}

		// add to symbol table		
		SymbolicTable.instance.symbolTable.put(name:value, type:name, businessObject)
		
		return businessObject
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
		if(parent instanceof DomainModel){
			(parent as DomainModel).domainObjects.add(child)
		} else {
			throw new Exception('Parent of a BusinessObject needs to be a DomainModel')
		}
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
	}

		

}
