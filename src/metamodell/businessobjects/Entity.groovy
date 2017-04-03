package metamodell.businessobjects

import metamodell.DomainAbstraction
import groovy.transform.ToString

@ToString(includeSuper=true)
class Entity extends PersistentDO{
	
	def dbName

}
