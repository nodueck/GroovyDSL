package MetaModell

class ValueObject extends AbstractElement{
	def attributeList = []
	
	@Override
	public String toString() {
		return this.name + " attributes: " + attributeList + " description: " + this.description
	}
}
