package generator.java

import metamodell.DomainProperty
import metamodell.DomainReference
import metamodell.businessobjects.DomainObject

@Singleton
class Model2JavaMapper {
	
	def typeSystemMapping =
	[Text: 			"String",
	 Datum: 		"Date",
	 GeldBetrag: 	"BigDecimal",
	 Status: 		"String",
	 Wahrheitswert: "boolean",
	 Zahl: 			"long"]

	String getType(DomainProperty domainProp){
		return typeSystemMapping.get(domainProp.type.class.simpleName)
	}
	
	String getReferenceType(DomainReference ref){
		if(ref.referenceType == "hasMany"){
			return "${ref.referencedObject.name}${ref.referencedObject.class.simpleName}[]"
		} else if (ref.referenceType == "has") {
			return "${ref.referencedObject.name}${ref.referencedObject.class.simpleName}"
		}
	}
	
}
