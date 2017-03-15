package generator.plantuml;

import MetaModell.Entity
import MetaModell.Project
import MetaModell.Attribute
import groovy.lang.Singleton;
import groovy.text.markup.AutoNewLineTransformer

public class DMDStructureToPlantUmlConverter {
	
	public static String entityToPlantUml(Entity entity){
		StringBuilder strB = new StringBuilder()
		strB.append("class $entity.name << Entity >> {\n");
		for(Attribute attr: entity.attributeList){
			strB.append("\t$attr.name \n")
		}
		strB.append("}\n")
		strB.append(note(entity.name, entity.description))
		strB.toString()
	}
	
	public static String projectToPlantUml(Project project){
		StringBuilder strB = new StringBuilder()
		strB.append("@startUml\n")
		strB.append("\n")
		for(Entity entity : project.entityList){
			strB.append(entityToPlantUml(entity))
			strB.append("\n\n")
		}
		strB.toString()
		strB.append("@enduml")
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
