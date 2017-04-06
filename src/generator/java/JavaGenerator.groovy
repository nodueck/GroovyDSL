package generator.java


import groovy.text.SimpleTemplateEngine
import metamodell.DomainModel
import metamodell.businessobjects.DomainObject
import metamodell.otherDomainAbstractions.Repository

class JavaGenerator {

	public static void model2JavaSource(DomainModel model){
		// extend String Class by toSnakeCase method (alias toUnderScoreCase())
		addSnakeCaseMethodToStringClass()
		addFullNameMethodToDomainObject()
		
		// where to put generated source
		def path = getSrcGenPath()
		
		def engine = new SimpleTemplateEngine(false) // set to true for verbose mode
		
		generateDomainObjects(model, path, engine)
		generateRepository(model, path, engine)
		
		model.modules.each { modul ->
			def modulPath = new File(path, modul.path)
			modulPath = new File(modulPath, modul.name.toLowerCase()) //add module name to path
			modulPath.mkdirs()
			
			generateDomainObjects(modul, modulPath, engine)
			generateRepository(modul, modulPath, engine)
		}
	}

	private static generateRepository(DomainModel model, File path, SimpleTemplateEngine engine) {
		model.otherDomainAbstractions.each { otherDomainAbstraction ->
			if(otherDomainAbstraction instanceof Repository){
				//generate Repository
				def repo = otherDomainAbstraction as Repository
				addFirstEntityMethodToRepository(repo)

				def bind = [repository: 	repo,
					rootPackage:	path.toString().replace("src\\", "").replace("\\", ".")]

				// generate RepositoryImpl
				def javaFileName = "${repo.name}Impl.java"
				def javaFile = new File(path, javaFileName)
				def templateFilePath = this.getResource("templates/Repository.tmpl")
				def template = engine.createTemplate(templateFilePath).make(bind)
				javaFile.write template.toString()

				// generate RepositoryInterface
				javaFileName = "${repo.name}I.java"
				javaFile = new File(path, javaFileName)
				templateFilePath = this.getResource("templates/RepositoryInterface.tmpl")
				template = engine.createTemplate(templateFilePath).make(bind)
				javaFile.write template.toString()
			}
		}
	}

	private static generateDomainObjects(DomainModel model, File path, SimpleTemplateEngine engine) {
		model.domainObjects.each {	domainObj ->

			// define binding
			def bind = [domainObject: 		domainObj,
				domainObjectType:	domainObj.class.simpleName,
				domainObjectName:	domainObj.name,
				rootPackage:		path.toString().replace("src\\", "").replace("\\", "."),
				fullName:			domainObj.fullName(),
				m2jMapper:			Model2JavaMapper.instance]

			// generate business object
			def javaFileName = "${domainObj.name}${domainObj.class.simpleName}.java"
			def javaFile = new File(path, javaFileName)
			def templateFilePath = this.getResource("templates/JavaObject.tmpl")
			def template = engine.createTemplate(templateFilePath).make(bind)
			javaFile.write template.toString()

			// generate DataBaseAccessor for Entity
			if(domainObj.class.simpleName == "Entity"){
				def DBAFileName = "${domainObj.name}DBA.java"
				def DBAFile = new File(path, DBAFileName)
				templateFilePath = this.getResource("templates/DBAccessor.tmpl")
				template = engine.createTemplate(templateFilePath).make(bind)
				DBAFile.write template.toString()
			}
		}
	}


	private static File getSrcGenPath() {
		def path = new File("src/srcgen")
		path.mkdirs()
		return path
	}

	private static addSnakeCaseMethodToStringClass() {
		String.metaClass.toSnakeCase = { delegate.replaceAll( /([A-Z])/, /_$1/ ).toLowerCase().replaceAll( /^_/, '' ) }
	}
	
	private static addFullNameMethodToDomainObject() {
		DomainObject.getMetaClass().fullName = {"${delegate.name}${delegate.class.simpleName}"}
	}
	
	private static addFirstEntityMethodToRepository(Repository repo) {
		if(repo.domainReferences){
			repo.getMetaClass().firstEntity = repo.domainReferences.get(0).referencedObject
		} 
	}
}
