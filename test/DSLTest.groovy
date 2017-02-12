import static org.junit.Assert.*

import java.awt.dnd.DragSourceListener

import org.junit.Test

import dmd.dsl.DSL

class DSLTest {

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
