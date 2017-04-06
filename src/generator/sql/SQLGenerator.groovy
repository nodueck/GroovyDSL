package generator.sql

import groovy.text.SimpleTemplateEngine
import java.io.File

import metamodell.DomainModel
import metamodell.businessobjects.Entity

class SQLGenerator {

	public static void model2SQL(DomainModel model){
		addSnakeCaseMethodToStringClass()
		
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		
		def path = getSQLGenPath()
		
		generateSqlForEntity(model, path, engine)
		
		model.modules.each { modul ->
			def modulPath = new File(path, modul.path)
			modulPath = new File(modulPath, modul.name.toLowerCase()) //add module name to path
			modulPath.mkdirs()
			generateSqlForEntity(modul, modulPath, engine)
		}
		
	}

	private static generateSqlForEntity(DomainModel model, File path, SimpleTemplateEngine engine) {
		model.domainObjects.each {	domainObj ->
			if(domainObj instanceof Entity){
				def entity = domainObj as Entity
				entity.dbName = entity.dbName ?: entity.name.toSnakeCase()
				// define binding
				def bind = [entity: entity, m2sqlMapper: Model2SqlMapper.instance]

				// generate business object
				def sqlFileName = "${entity.name}.sql"
				def sqlFile = new File(path, sqlFileName)
				def templateFilePath = this.getResource("templates/SQL.tmpl")
				def template = engine.createTemplate(templateFilePath).make(bind)
				sqlFile.write template.toString()

			}
		}
	}
	
	private static File getSQLGenPath() {
		def path = new File("env/sql")
		path.mkdirs()
		return path
	}

	private static addSnakeCaseMethodToStringClass() {
		String.metaClass.toSnakeCase = { delegate.replaceAll( /([A-Z])/, /_$1/ ).toLowerCase().replaceAll( /^_/, '' ) }
	}
}
