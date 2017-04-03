package metamodell

import groovy.transform.ToString


@ToString(includes='name')
abstract class DomainAbstraction {

	Requirement requirement;
	String name;
	
	boolean libraryClass = false
	
	def domainOperations = []
	def domainProperties = []
	def domainReferences = []
}
