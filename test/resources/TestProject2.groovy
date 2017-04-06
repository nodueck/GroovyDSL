module("Tarif"){

	entity(Tarif){
		description "Tarif"
		
		attr	tarifname, 		type:Text,		description: "Tarifname"
		attr	product, 		type:Text,		description: "Produktname"
	}
	
	repository(TarifRepository){
		description """Das Repository für den Tarif"""
		has Tarif
	}
}