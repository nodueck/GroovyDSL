package MetaModell.BusinessObjects

class ValueObject extends DomainObject{
	
	def attributeList = []
	boolean webObject = false;
	
	@Override
	public String toString() {
		return this.name + " attributes: " + attributeList + " description: " + this.description
	}
}
