package generator.sql

import metamodell.DomainProperty
import metamodell.DomainReference
import metamodell.businessobjects.DomainObject

@Singleton
class Model2SQLMapper {
	
	def typeSystemMapping =
	[Text: 			"VARCHAR(50)",
	 Datum: 		"DATETIME",
	 GeldBetrag: 	"NUMERIC(10)",
	 Status: 		"VARCHAR(50)",
	 Wahrheitswert: "VARCHAR(1)",
	 Zahl: 			"NUMERIC(10)"]

	String getType(DomainProperty domainProp){
		return typeSystemMapping.get(domainProp.type.class.simpleName)
	}
	
}
