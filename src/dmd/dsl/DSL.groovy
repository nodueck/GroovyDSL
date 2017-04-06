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
import groovy.io.FileType
import groovy.lang.GroovyShell;
import groovy.lang.Script;

class DSL {

	DomainModel result;
	
	public String generatePlantUml(File script) throws DSLException{
		try {
			parseFile(script);
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		
		PlantUmlGenerator.model2PlantUml(result);
	}
	
	/**
	 * Generates Java Files
	 * @param file path to a file or directory
	 * @throws Exception
	 */
	public void generateJava(File file) throws Exception {
		try {
			if(file.isDirectory()){
				parseDirectory(file)
			} else {
				parseFile(file)
			}
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		
		JavaGenerator.model2JavaSource(result);
	}
	
	/**
	 * Generates SQL Files
	 * @param file path to a file or directory
	 * @throws Exception
	 */
	public void generateSQL(File file) throws Exception {
		try {
			if(file.isDirectory()){
				parseDirectory(file)
			} else {
				parseFile(file)
			}
		} catch (CompilationFailedException | IOException e) {
			throw new DSLException(e);
		}
		
		SQLGenerator.model2SQL(result);
	}
	
	
	private void parseFile(File file) throws CompilationFailedException, IOException{
		Script script = new GroovyShell(this.class.classLoader).parse(file);
		result = new DMDFactoryBuilder().build(script)
	}
	
	private void parseDirectory(File dir){
		DomainModel domainModel = new DomainModel();
		domainModel.setName("ProjectName")
		domainModel.path = ""
		
		dir.eachFileRecurse(FileType.FILES) { file ->
			Script script = new GroovyShell().parse(file);
			DomainModel modul = new DMDFactoryBuilder().build(script)
			modul.path = file.getParent().toLowerCase()
			domainModel.modules.add(modul)
		}
		result = domainModel
	}
}
