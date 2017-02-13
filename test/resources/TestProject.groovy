import types.*

Test{

	entity("Customer") {
		description """Das erste Entity das beschrieben wird: Customer -> ist trivial
						 2 Zeilen oder mehr Text, falls die Beschreibung mal länger sein sollte"""
		
		id 			Number, 				description: "Die ID"
		
		firstName 	Text(min:1, max:50),	description: "Vorname",			dbName: "first_name"
					
		lastName 	Text(min:1, max:50),	description: "Nachname"
	}
	
	
	entity("Contract") {
		description """ Normaler Vertrag"""
		
		id			Number
		tarif		Text
	}
}