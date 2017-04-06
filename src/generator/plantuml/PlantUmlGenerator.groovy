package generator.plantuml;

import metamodell.DomainAbstraction
import metamodell.DomainModel
import metamodell.businessobjects.DomainObject
import metamodell.businessobjects.Entity
import metamodell.otherDomainAbstractions.Repository
import metamodell.DomainProperty
import groovy.json.StringEscapeUtils
import groovy.lang.Singleton;
import groovy.text.GStringTemplateEngine
import groovy.text.SimpleTemplateEngine
import groovy.text.markup.AutoNewLineTransformer

public class PlantUmlGenerator {
	
	public static String model2PlantUml(DomainModel model){
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def template = engine.createTemplate(new File("src/generator/plantuml/templates/DomainModel.tmpl")).make([domainModel: model])
		return template.toString()
	}
	
	public static String domainObjectToPlantUml(DomainObject domainObj){
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def template = engine.createTemplate(new File("src/generator/plantuml/templates/DomainObject.tmpl")).make([domainObject: domainObj])
		return template.toString()
	}
	
	public static String otherDomainAbstractionsToPlantUml(DomainAbstraction domainAbstr) {
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def template = engine.createTemplate(new File("src/generator/plantuml/templates/DomainAbstraction.tmpl")).make([domainAbstr: domainAbstr])
		return template.toString()
	}
	
	
	public static String getNote(DomainAbstraction domainAbstr){
		if(domainAbstr.requirement){
			String escaped = StringEscapeUtils.escapeJava(domainAbstr.requirement.content)
			escaped = escaped.replace("\\n", "\\\\n").replace("\\t", "")
			return StringEscapeUtils.unescapeJava(escaped)
		}
	}
}
