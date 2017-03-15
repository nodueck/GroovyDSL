package generator
import static org.junit.Assert.*

import java.awt.dnd.DragSourceListener

import org.junit.Test

import dmd.dsl.DSL


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
		
		String expectedPlantUMLScript ="""@startUml

object Customer {
	id 
	firstName 
	lastName 
}

object Contract {
	id 
	tarif 
}

@enduml"""
		
		assertEquals(expectedPlantUMLScript,plantUmlScript) 
	}

}
