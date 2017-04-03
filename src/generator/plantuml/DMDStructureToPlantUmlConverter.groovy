package generator.plantuml;

import metamodell.DomainModel
import metamodell.businessobjects.DomainObject
import metamodell.businessobjects.Entity
import metamodell.DomainProperty
import groovy.lang.Singleton;
import groovy.text.markup.AutoNewLineTransformer

public class DMDStructureToPlantUmlConverter {
	
	
	public static String projectToPlantUml(DomainModel project){
		StringBuilder strB = new StringBuilder()
		strB.append("@startUml\n")
		strB.append("\n")
		project.domainObjects.each { domainObj -> strB.append(domainObjectToPlantUml(domainObj)) }
		project.domainObjects.each { domainObj -> strB.append(domainReferencesToPlantUml(domainObj)) }
		strB.toString()
		strB.append("@enduml")
	}
	
	private static String domainObjectToPlantUml(DomainObject domainObj){
		StringBuilder strB = new StringBuilder()
		strB.append("class $domainObj.name << Entity >> {\n");
		domainObj.domainProperties.each { attr ->  strB.append("\t$attr.name \n") }
		strB.append("}\n")
		strB.append(note(domainObj.name, domainObj.requirement.content))
		strB.append("\n\n")
		strB.toString()
	}
	
	private static String domainReferencesToPlantUml(DomainObject domainObj){
		StringBuilder strB = new StringBuilder()
		domainObj.domainReferences.each { reference -> 
			strB.append("$domainObj.name")
			strB.append(" -- ")
			strB.append(reference.referenceType == "hasMany" ? ' "*" ' : ' "1" ')
			strB.append("$reference.referencedObject.name") 
			} 
		strB.append("\n\n")
		strB.toString()
	}
	
	private static String note(String to, String note){
		if(note){
			def noteName = "N$to"
			note = note.replace("\n", "\\n").replace("\r", "").replace("\t","")
			def plantUmlCmd = "note \"$note\" as $noteName\n"
			plantUmlCmd += "$to .. $noteName"
			return plantUmlCmd
		}
		return ""
	}
}
