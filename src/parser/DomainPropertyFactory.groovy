package parser

import java.util.Map

import metamodell.DomainAbstraction
import metamodell.DomainModel
import metamodell.DomainProperty
import metamodell.Requirement
import metamodell.businessobjects.Aggregate
import metamodell.businessobjects.Entity
import metamodell.businessobjects.ValueObject
import metamodell.typesystem.Datum
import metamodell.typesystem.GeldBetrag
import metamodell.typesystem.Status
import metamodell.typesystem.Text
import metamodell.typesystem.Wahrheitswert
import metamodell.typesystem.Zahl
import groovy.util.FactoryBuilderSupport

class DomainPropertyFactory extends AbstractFactory {
	
	public boolean isLeaf() {
		// Wenn Methoden innerhalb von attr ... aufgerufen werden, muss dies auf false gestellt werden
		// z.B. attr name, Text(min:10), description: "Vorname"
		return true;
	}

	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map map)
			throws InstantiationException, IllegalAccessException {
		map.put("name", value)

		if(map.containsKey("type")){
			def type = map.get("type")
			
			if(type == Text.class || (type as String).equalsIgnoreCase("Text")){
				type = new Text()					
			}
			else if(type == Zahl.class || (type as String).equalsIgnoreCase("Zahl")){
				type = new Zahl()	
			}
			else if(type == Status.class || (type as String).equalsIgnoreCase("Status")){
				type = new Status()	
			}
			else if(type == Datum.class || (type as String).equalsIgnoreCase("Datum")){
				type = new Datum()	
			}
			else if(type == Wahrheitswert.class || (type as String).equalsIgnoreCase("Wahrheitswert")){
				type = new Wahrheitswert()	
			}
			else if(type == GeldBetrag.class || (type as String).equalsIgnoreCase("Geldbetrag")){
				type = new GeldBetrag()	
			} else {
				new Exception("Type $type not Supported")
			}
			map.put("type", type)
		}
		
		if(map.containsKey('description')){
			map.put("requirement", new Requirement(map.get("description")))
			map.remove("description")
		}
		return new DomainProperty(map)
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
		println parent.name
		if(parent instanceof DomainAbstraction){
			(parent as DomainAbstraction).domainProperties.add(child)
		} else {
			throw new Exception('Parent of a DomainProperty needs to be a DomainAbstraction')
		}
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
	}

}
