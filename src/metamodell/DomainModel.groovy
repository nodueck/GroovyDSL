package metamodell

import groovy.transform.ToString

@ToString(includes='name')
@Singleton
class DomainModel {
	def name
	def otherDomainAbstractions = []
	def domainObjects = []
}
