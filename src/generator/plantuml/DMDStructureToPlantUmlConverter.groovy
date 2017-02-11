package generator.plantuml;

import MetaModell.Entity
import MetaModell.Project
import MetaModell.Attribute
import groovy.lang.Singleton;
import groovy.text.markup.AutoNewLineTransformer

@Singleton
public class DMDStructureToPlantUmlConverter {

	def convertEntities = true
	def convertValueObjects = true
	
	
	public String entityToPlantUml(Entity entity){
		if(!convertEntities){
			return ""
		}
		
		StringBuilder strB = new StringBuilder()
		strB.append("object $entity.name {\n");
		for(Attribute attr: entity.attributeList){
			strB.append("\t$attr.name \n")
		}
		strB.append("}")
		strB.toString()
	}
	
	public String projectToPlantUml(Project project){
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
}
