package generator

import dmd.dsl.DSL

import org.junit.After
import org.junit.Assert
import org.junit.Test
import groovy.io.FileType

class JavaGeneratorTest extends Assert{
	
	@After
	public void tearDown() {
		new File("src.dgen").deleteDir()
	}

	@Test
	public void testGenerateJavaFiles() {
		DSL script = new DSL()
		String javaSource = script.generateJava(new File("test/resources"))
		
		new File("src.dgen").eachFileRecurse(FileType.FILES) { file ->
			// all are java files
			assertEquals("java", getExtention(file))
		}
	}
	
	
	private String getExtention(File file){
		file.name.substring(file.name.lastIndexOf('.') + 1)
	}
}
