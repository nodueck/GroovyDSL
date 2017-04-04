package generator.plantuml;

import metamodell.DomainModel
import metamodell.businessobjects.DomainObject
import metamodell.businessobjects.Entity
import metamodell.DomainProperty
import groovy.json.StringEscapeUtils
import groovy.lang.Singleton;
import groovy.text.GStringTemplateEngine
import groovy.text.SimpleTemplateEngine
import groovy.text.markup.AutoNewLineTransformer

public class DMDStructureToPlantUmlConverter {
	
	public static String projectToPlantUml(DomainModel model){
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def template = engine.createTemplate(new File("src/generator/plantuml/DomainModel.tmpl")).make([domainModel: model])
		return template.toString()
	}
	
	public static String domainObjectToPlantUml(DomainObject domainObj){
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		def template = engine.createTemplate(new File("src/generator/plantuml/DomainObject.tmpl")).make([domainObject: domainObj])
		return template.toString()
	}
	
	
	public static String getNote(DomainObject domainObj){
		if(domainObj.requirement.content){
			String escaped = StringEscapeUtils.escapeJava(domainObj.requirement.content)
			escaped = escaped.replace("\\n", "\\\\n").replace("\\t", "")
			return StringEscapeUtils.unescapeJava(escaped)
		}
	}
}
