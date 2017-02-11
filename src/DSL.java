import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.codehaus.groovy.control.CompilationFailedException;
import MetaModell.Project;
import generator.plantuml.DMDStructureToPlantUmlConverter;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

class DSL {

	Project result;
	
	private void loadScript(File file) throws CompilationFailedException, IOException{
		String scriptText = new Scanner(file).useDelimiter("\\Z").next();
		scriptText = "new DMDStructureBuilder()." + scriptText;
		Script script = new GroovyShell().parse(scriptText);
		result = ((Project) script.run());
	}

	public String generatePlantUml(File file) throws CompilationFailedException, IOException{
		loadScript(file);
		return DMDStructureToPlantUmlConverter.projectToPlantUml(result);
	}
}
