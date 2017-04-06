
Project.Test{

	entity(Customer){
		description """Das erste Entity das beschrieben wird: Customer -> ist trivial
						 2 Zeilen oder mehr Text, falls die Beschreibung mal länger sein sollte"""
		
		attr	firstName, 		type:Text,		description: "Vorname",	 dbName: "first_name"
		attr	lastName, 		type:Text,		description: "Nachname"
		hasMany	Contract
	}
	
	
	entity(Contract) {
		description """ Normaler Vertrag"""
		
		attr	monthlyCosts,	type:Zahl
		attr	tarif,			type:Text
	}
	
	repository(CustomerRepository){
		description """Das Repository für den Customer"""
		has Customer
	}
}