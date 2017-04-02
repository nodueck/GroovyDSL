package dmd.dsl;
import dmd.dsl.DMDStructureBuilder;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.codehaus.groovy.control.CompilationFailedException;
import MetaModell.DomainModel;
import generator.plantuml.DMDStructureToPlantUmlConverter;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

class DSL {

	DomainModel result;
	
	private void loadScript(File file) throws CompilationFailedException, IOException{
//		String scriptText = new Scanner(file).useDelimiter("\\Z").next();
		//Wichtig den eigenen Classloader zu nutzen, da beim Aufruf durch das EclipsePlugin dmd.dsl.DMDStructureBuilder nicht gefunden wird
		
		def binding = new Binding([Project: new DMDStructureBuilder()])
		
		Script script = new GroovyShell(this.class.classLoader, binding).parse(file); 
		result = ((DomainModel) script.run());
	}

	public String generatePlantUml(File file) throws DSLException{
		try {
			loadScript(file);
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		return DMDStructureToPlantUmlConverter.projectToPlantUml(result);
	}
	
}
