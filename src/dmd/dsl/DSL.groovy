package dmd.dsl;
import dmd.dsl.DMDStructureBuilder;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.codehaus.groovy.control.CompilationFailedException;
import metamodell.DomainModel;
import parser.DMDFactoryBuilder
import generator.java.JavaGenerator
import generator.plantuml.PlantUmlGenerator;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

class DSL {

	DomainModel result;
	
	private void parseScript(File file) throws CompilationFailedException, IOException{
//		String scriptText = new Scanner(file).useDelimiter("\\Z").next();
		//Wichtig den eigenen Classloader zu nutzen, da beim Aufruf durch das EclipsePlugin dmd.dsl.DMDStructureBuilder nicht gefunden wird
		
		def binding = new Binding([Project: new DMDFactoryBuilder()])
		
		Script script = new GroovyShell(this.class.classLoader, binding).parse(file); 
		result = ((DomainModel) script.run());
	}

	public String generatePlantUml(File file) throws DSLException{
		try {
			parseScript(file);
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		return PlantUmlGenerator.projectToPlantUml(result);
	}
	
	public void generateJava(File file) throws Exception {
		try {
			parseScript(file);
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		JavaGenerator.modelToJavaSource(result);
	}
	
}
