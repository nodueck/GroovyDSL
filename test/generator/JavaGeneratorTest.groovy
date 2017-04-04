package generator

import dmd.dsl.DSL
import org.junit.Test

class JavaGeneratorTest {

	@Test
	public void testGenerateJava() {
		DSL script = new DSL()
		String javaSource = script.generateJava(new File("test/resources/TestProject.groovy"))
		println javaSource
	}
}
