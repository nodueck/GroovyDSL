import static org.junit.Assert.*

import java.awt.dnd.DragSourceListener

import DSL

import org.junit.Test

class DSLTest {

	@Test
	public void testGeneratePlantUML() {
		DSL script = new DSL()
		String plantUmlScript = script.generatePlantUml(new File("src/TestProject.groovy"))
		
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
