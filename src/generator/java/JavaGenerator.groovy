package generator.java

import java.util.Currency.CurrencyNameGetter

import groovy.text.SimpleTemplateEngine
import metamodell.DomainModel
import metamodell.DomainReference

class JavaGenerator {

	public static void modelToJavaSource(DomainModel model){
		// extend String Class by toSnakeCase method (alias toUnderScoreCase())
		String.metaClass.toSnakeCase = { delegate.replaceAll( /([A-Z])/, /_$1/ ).toLowerCase().replaceAll( /^_/, '' ) }
		
		// where to put generated source
		def path = new File("src/srcgen")
		path.mkdirs()
		
		def engine = new SimpleTemplateEngine(true) // set to true for verbose mode
		
		model.domainObjects.each {	domainObj ->
			
			// define binding
			def bind = [domainObject: 		domainObj, 
						domainObjectType:	domainObj.class.simpleName, 
						domainObjectName:	domainObj.name,
						rootPackage:		path.toString().replace("src\\", "").replace("\\", "."),
						fullName:			"${domainObj.name}${domainObj.class.simpleName}",
						m2jMapper:			Model2JavaMapper.instance]
			
			// generate business object
			def javaObjectFileName = "${domainObj.name}${domainObj.class.simpleName}.java"
			def javaObjectFile = new File(path, javaObjectFileName)
			def template = engine.createTemplate(new File("src/generator/java/JavaObject.tmpl")).make(bind)
			javaObjectFile.write template.toString()
			
			// generate DataBaseAccessor for Entity
			if(domainObj.class.simpleName == "Entity"){
				def DBAFileName = "${domainObj.name}DBA.java"
				def DBAFile = new File(path, DBAFileName)
				template = engine.createTemplate(new File("src/generator/java/DBAccessor.tmpl")).make(bind)
				DBAFile.write template.toString()
			}
		}
	}
}
