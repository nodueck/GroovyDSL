package metamodell

import groovy.transform.ToString

@ToString(includes='name')
class DomainModel {
	def name
	def otherDomainAbstractions = []
	def domainObjects = []
}
