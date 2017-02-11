import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

import MetaModell.Project;
import generator.plantuml.DMDStructureToPlantUmlConverter;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

class DSL {

	Project result;
	
	private void loadScript(File file) throws CompilationFailedException, IOException{
		Script script = new GroovyShell().parse(file);
		result = (Project) script.run();
	}

	public String generatePlantUml(File file) throws CompilationFailedException, IOException{
		loadScript(file);
		return DMDStructureToPlantUmlConverter.projectToPlantUml(result);
	}
}
