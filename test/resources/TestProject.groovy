
Project.Test{

	entity(Customer){
		description """Das erste Entity das beschrieben wird: Customer -> ist trivial
						 2 Zeilen oder mehr Text, falls die Beschreibung mal länger sein sollte"""
		
		attr	id,				type:Zahl, 		description: "Die ID"
		attr	firstName, 		type:Text,		description: "Vorname",	 dbName: "first_name"
		attr	lastName, 		type:Text,		description: "Nachname"
		hasMany	Contract
	}
	
	
	entity(Contract) {
		description """ Normaler Vertrag"""
		
		attr	id,				type:Zahl
		attr	tarif,			type:Text
	}
}