package generator

import dmd.dsl.DSL
import org.junit.Test

class SQLGeneratorTest {

	@Test
	public void testGenerateSQL() {
		DSL script = new DSL()
		script.generateSQL(new File("test/resources/TestProject.groovy"))
	}
}
