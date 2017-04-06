package dmd.dsl;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.codehaus.groovy.control.CompilationFailedException;
import metamodell.DomainModel;
import parser.DMDFactoryBuilder
import generator.java.JavaGenerator
import generator.plantuml.PlantUmlGenerator;
import generator.sql.SQLGenerator
import groovy.lang.GroovyShell;
import groovy.lang.Script;

class DSL {

	DomainModel result;
	
	private void parseScript(File file) throws CompilationFailedException, IOException{
//		String scriptText = new Scanner(file).useDelimiter("\\Z").next();
		//Wichtig den eigenen Classloader zu nutzen, da sonst beim Aufruf durch das EclipsePlugin dmd.dsl.DMDStructureBuilder nicht gefunden wird
		
		def binding = new Binding([Project: new DMDFactoryBuilder()])
		
		Script script = new GroovyShell(this.class.classLoader, binding).parse(file); 
		result = ((DomainModel) script.run());
	}

	public String generatePlantUml(File script) throws DSLException{
		try {
			parseScript(script);
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		return PlantUmlGenerator.model2PlantUml(result);
	}
	
	public void generateJava(File file) throws Exception {
		try {
			parseScript(file);
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		JavaGenerator.model2JavaSource(result);
	}
	
	public void generateSQL(File file) throws Exception {
		try {
			parseScript(file);
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		SQLGenerator.model2SQL(result);
	}
	
}
