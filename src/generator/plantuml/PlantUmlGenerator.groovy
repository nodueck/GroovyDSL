package generator.plantuml;

import groovy.json.StringEscapeUtils
import groovy.text.SimpleTemplateEngine
import metamodell.DomainAbstraction
import metamodell.DomainModel
import metamodell.businessobjects.DomainObject

public class PlantUmlGenerator {
	
	
	public static String model2PlantUml(DomainModel model){
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append("@startuml\n");
		
		model.domainObjects.each { 
			strBuilder.append(domainAbstractionToPlantUml(it))
		}
		model.otherDomainAbstractions.each {
			strBuilder.append(domainAbstractionToPlantUml(it))
		}
		
		strBuilder.append("\n@enduml");
		
		return strBuilder.toString()
	}
	
	public static String domainAbstractionToPlantUml(DomainAbstraction domainAbstr) {
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def templateFilePath = this.getResource("templates/DomainAbstraction.tmpl")
		def template = engine.createTemplate(templateFilePath).make([domainAbstr: domainAbstr, generator: PlantUmlGenerator])
		return template.toString()
	}
	
	/** Tabulatorabstände aus der Notiz entfernen */ 
	public static String getNote(DomainAbstraction domainAbstr){
		if(domainAbstr.requirement){
			String escaped = StringEscapeUtils.escapeJava(domainAbstr.requirement.content)
			escaped = escaped.replace("\\n", "\\\\n").replace("\\t", "")
			return StringEscapeUtils.unescapeJava(escaped)
		}
	}
}
