package MetaModell

class Entity extends AbstractElement{
	
	def attributeList = []
	def tableName
	
	@Override
	public String toString() {
		return this.name + " attributes: " + attributeList + " description: " + this.description
	}

}
