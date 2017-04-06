package generator
import static org.junit.Assert.*

import java.awt.dnd.DragSourceListener
import java.io.File

import org.junit.Test

import dmd.dsl.DSL
import generator.plantuml.PlantUmlGenerator
import groovy.json.StringEscapeUtils
import metamodell.DomainModel


/**
 * assures that the generated code corresponds the model(elements)
 * @author DUEC007
 *
 */
class PlantUmlGeneratorTest {

	@Test
	public void testGeneratePlantUML() {
		DSL script = new DSL()
		String plantUmlScript = script.generatePlantUml(new File("test/resources/TestProject.groovy"))
		plantUmlScript = script.generatePlantUml(new File("test/resources/TestProject.groovy"))
		
		String expectedPlantUMLScript =
"""@startUml

class Customer << Entity >> {
	
	id
	firstName
	lastName
	
}
note "Das erste Entity das beschrieben wird: Customer -> ist trivial\\n 2 Zeilen oder mehr Text, falls die Beschreibung mal länger sein sollte" as NCustomer
Customer .. NCustomer

Customer -- "*" Contract


class Contract << Entity >> {
	
	id
	tarif
	
}
note " Normaler Vertrag" as NContract
Contract .. NContract




class CustomerRepository << Repository >> {
	
}
note "Das Repository für den Customer" as NCustomerRepository
CustomerRepository .. NCustomerRepository

CustomerRepository -- Customer


@enduml """


		assertEquals(expectedPlantUMLScript,plantUmlScript) 
	}

}
