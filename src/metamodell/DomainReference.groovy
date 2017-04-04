package metamodell

class DomainReference {
		
	boolean loeschweitergabe = false
	boolean foreignKey = false //macht nur Sinn bei einer hasOne Beziehung
	String referenceType
	DomainAbstraction referencedObject
	Requirement requirement
}
