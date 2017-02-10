import MetaModell.*
import types.*

class DMDStructureBuilder extends BuilderSupport {

	def ident = 0
	def debug = false
	
	Project project
	
	
	@Override
	protected Object createNode(Object name) {
		//should be only the first element
		debug "createNode(${name})"
		return new Project(name:name)
	}

	@Override
	protected Object createNode(Object name, Object value) {
		switch(name) {
			case "entity":
				debug "create Entity (${name}, ${value})"
				return new Entity(name:value)
			case "valueObject":
				debug "create ValueObject (${name}, ${value})"
				return new ValueObject(name:value)
			case "description":
				debug "create Description(${name}, ${value})"
				return new Description(description:value)
			case "Text":
				debug "create Text(${name}, ${value})"
				return new Text()
			case "Number":
				debug "create Number(${name}, ${value})"
				return new Number()
			default:
			debug "create Attribute (${name}, ${value})"
				return new Attribute(name:name, type:value)	
		}
		
	}

	@Override
	protected Object createNode(Object name, Map attributes) {
		debug "name: ${name} attributes ${attributes}"
		switch(name) {
			case "Text":
				debug "create Text(${name}, ${attributes})"
				return new Text(min:attributes.min,max:attributes.max)
			case "Number":
				debug "create Number(${name}, ${attributes})"
				return new Number(min:attributes.min,max:attributes.max)
			default:
				throw new Exception("Not Supported")
		}
	}

	@Override
	protected Object createNode(Object name, Map attributes, Object value) {
		debug "create Attribute(name: ${name} attributes ${attributes} value ${value})"
		return new Attribute(name:name, type:value, attributes:attributes)
	}

	@Override
	protected void setParent(Object parent, Object child) {
//		ident.times {print "\t"}
//		debug "setParent(${parent}, ${child})"
	}
	
	@Override
	protected void nodeCompleted(Object parent, Object node) {
		debug "nodeCompleted parent = " + parent + " node = " + node
		
		if(parent != null){
			if(parent.class == Project.class){
				if(node.class == Entity.class){
					((Project) parent).entityList.add(node)
				} else if (node.class == ValueObject.class) {
					((Project) parent).valueObjectList.add(node)
				}
			} else if (node.class == Attribute.class){
				if(parent.class == Entity.class){
					((Entity) parent).attributeList.add(node)
				} else if (parent.class == ValueObject.class){
					((ValueObject) parent).attributeList.add(node)
				}
			} else if(node.class == Description.class){
				if(parent.class == Entity.class){
					((Entity) parent).description = ((Description) node).description
				} else if (parent.class == ValueObject.class){
					((ValueObject) parent).description = ((Description) node).description
				}
			} else if (parent.class == Attribute.class){
					((Attribute) parent).type = node
			}
		} else {
			project = node
		}
	}
	
	def propertyMissing(name) {
		//add to list and initalize later
		null
	}
	
	def debug(msg) {
		if(debug)
			println msg
	}
	
}


