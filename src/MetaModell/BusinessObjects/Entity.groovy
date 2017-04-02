package MetaModell.BusinessObjects

import MetaModell.DomainAbstraction

class Entity extends PersistentDO{
	
	def attributeList = []
	def dbName
	
	@Override
	public String toString() {
		return this.name + " attributes: " + attributeList + " description: " + this.requirement
	}

}
