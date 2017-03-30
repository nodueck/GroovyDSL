package MetaModell

import groovy.transform.ToString

@ToString(includes='name')
class DomainModel {
	def name
	def entityList = []
	def valueObjectList = []
}
