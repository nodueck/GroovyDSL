package generator.plantuml;

import groovy.json.StringEscapeUtils
import groovy.text.SimpleTemplateEngine
import metamodell.DomainAbstraction
import metamodell.DomainModel
import metamodell.businessobjects.DomainObject

public class PlantUmlGenerator {
	
	
	public static String model2PlantUml(DomainModel model){
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def templateFilePath = this.getResource("templates/DomainModel.tmpl")
		def template = engine.createTemplate(templateFilePath).make([domainModel: model, generator: PlantUmlGenerator])
		return template.toString()
	}
	
	public static String domainObjectToPlantUml(DomainObject domainObj){
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def templateFilePath = this.getResource("templates/DomainObject.tmpl")
		def template = engine.createTemplate(templateFilePath).make([domainObject: domainObj, generator: PlantUmlGenerator])
		return template.toString()
	}
	
	public static String otherDomainAbstractionsToPlantUml(DomainAbstraction domainAbstr) {
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def templateFilePath = this.getResource("templates/DomainAbstraction.tmpl")
		def template = engine.createTemplate(templateFilePath).make([domainAbstr: domainAbstr, generator: PlantUmlGenerator])
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
