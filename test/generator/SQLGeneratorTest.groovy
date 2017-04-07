package generator

import dmd.dsl.DSL
import groovy.io.FileType

import java.io.File
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Test

class SQLGeneratorTest extends Assert {
	
	@After
	public void tearDown() {
		new File("env/hsql/sql.dgen").deleteDir()
		new File("env/hsql").delete()
		new File("env").delete()
	}

	@Test
	public void testGenerateSQL() {
		DSL script = new DSL()
		script.generateSQL(new File("test/resources"))
		
		new File("env/hsql/sql.dgen").eachFileRecurse(FileType.FILES) { file ->
			// all are sql files
			assertEquals("sql", getExtention(file))
		}
	}
	
	private String getExtention(File file){
		file.name.substring(file.name.lastIndexOf('.') + 1)
	}
}
