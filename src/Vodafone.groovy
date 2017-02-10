import types.*

def Project = new DMDStructureBuilder()

Project.Vodafone{

	entity("Customer") {
		description """Das erste Entity das beschrieben wird: Customer -> ist trivial
						 2 Zeilen Text, falls die Beschreibung mal länger sein sollte"""
		
		id 			Number, 				Description: "Die ID"
		
		firstName 	Text(min:1, max:50),	Description: "Vorname",			dbName: "first_name"
					
		lastName 	Text(min:1, max:50),	Description: "Nachname"
	}
	
	
	
	entity("Contract") {
		description """ Normaler Vertrag"""
		
		id			Number
		tarif		Text
	}
}

Project.project.entityList.each { println it } 

println Project.missingProperties