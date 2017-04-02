package dmd.dsl
import java.lang.ClassValue.Identity

import MetaModell.*
import MetaModell.BusinessObjects.Entity
import MetaModell.BusinessObjects.ValueObject
import MetaModell.TypeSystem.*

class DMDStructureBuilder extends BuilderSupport {

	def ident = 0
	def debug = true
	
	DomainModel project
	
	
	@Override
	protected Object createNode(Object name) {
		//should be only the first element
		debug "createNode(${name})"
		return new DomainModel(name:name)
	}

	@Override
	protected Object createNode(Object name, Object value) {
		switch(name) {
			case "entity":
				debug "create Entity (${name}, ${value})"
				return this.entity(value)
			case "valueObject":
				debug "create ValueObject (${name}, ${value})"
				return new ValueObject(name:value)
			case "description":
				debug "create Description(${name}, ${value})"
				return new Requirement(content: value)
			case "Text":
				debug "create Text(${name}, ${value})"
				return new Text()
			case "Zahl":
				debug "create Number(${name}, ${value})"
				return new Zahl()
			default:
			debug "create Attribute (${name}, ${value})"
			if(value == "Zahl" || value.class == Zahl.class) {
				value = new Zahl()
			}
			if(value == "Text" || value.class == Text.class) {
				value = new Text()
			}
				return new DomainProperty(name:name, type:value)	
		}
		
	}

	@Override
	protected Object createNode(Object name, Map attributes) {
		debug "name: ${name} attributes ${attributes}"
		switch(name) {
			case "Text":
				debug "create Text(${name}, ${attributes})"
				return new Text()
			case "Zahl":
				debug "create Zahl(${name}, ${attributes})"
				return new Zahl()
			default:
				throw new Exception("Not Supported")
		}
	}

	@Override
	protected Object createNode(Object name, Map attributes, Object value) {
		debug "create Attribute(name: ${name} attributes ${attributes} value ${value})"
		def wert
		if(value == "Zahl" || value.class == Zahl.class) {
			wert = new Zahl()
		}
		if(value == "Text" || value.class == Text.class) {
			wert = new Text()
		}
		return new DomainProperty(name:name, type:wert, attributes:attributes)
	}

	@Override
	protected void setParent(Object parent, Object child) {
//		ident.times {print "\t"}
//		debug "setParent(${parent}, ${child})"
		if(child.class == Requirement.class){
			if(parent.class == DomainAbstraction.class){
				((DomainAbstraction)parent).requirement = child
			}
			if(parent.class == DomainOperation.class){
				((DomainOperation)parent).requirement = child
			}
		}
	}
	
	@Override
	protected void nodeCompleted(Object parent, Object node) {
		debug "nodeCompleted parent = " + parent + " node = " + node
		
		if(parent != null){
			if(parent.class == DomainModel.class){
				if(node.class == Entity.class){
					((DomainModel) parent).entityList.add(node)
				} else if (node.class == ValueObject.class) {
					((DomainModel) parent).valueObjectList.add(node)
				}
			} else if (node.class == DomainProperty.class){
				if(parent.class == Entity.class){
					((Entity) parent).attributeList.add(node)
				} else if (parent.class == ValueObject.class){
					((ValueObject) parent).attributeList.add(node)
				}
			} else if (parent.class == DomainProperty.class){
					((DomainProperty) parent).type = node
			}
		} else {
			project = node
		}
	}
	
	def propertyMissing(String name) {
		//add to list and initalize later
		name
	}
	
	def debug(msg) {
		if(debug)
			println msg
	}
	
	Entity entity(String name){
		debug "create Entity (${name})"
		return new Entity(name:name)
	}
	
}


