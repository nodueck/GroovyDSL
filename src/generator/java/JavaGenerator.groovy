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
		
		model.domainObjects.each {	domainObj ->
			
			// define binding
			def bind = [domainObject: 		domainObj, 
						domainObjectType:	domainObj.class.simpleName, 
						domainObjectName:	domainObj.name,
						rootPackage:		path.toString().replace("src\\", "").replace("\\", "."),
						fullName:			domainObj.fullName(),
						m2jMapper:			Model2JavaMapper.instance]
			
			// generate business object
			def javaObjectFileName = "${domainObj.name}${domainObj.class.simpleName}.java"
			def javaObjectFile = new File(path, javaObjectFileName)
			def template = engine.createTemplate(new File("src/generator/java/templates/JavaObject.tmpl")).make(bind)
			javaObjectFile.write template.toString()
			
			// generate DataBaseAccessor for Entity
			if(domainObj.class.simpleName == "Entity"){
				def DBAFileName = "${domainObj.name}DBA.java"
				def DBAFile = new File(path, DBAFileName)
				template = engine.createTemplate(new File("src/generator/java/templates/DBAccessor.tmpl")).make(bind)
				DBAFile.write template.toString()
			}
		}
		
		model.otherDomainAbstractions.each { otherDomainAbstraction ->
			if(otherDomainAbstraction instanceof Repository){
				//generate Repository
				def repo = otherDomainAbstraction as Repository
				addFirstEntityMethodToRepository(repo)
				
				def bind = [repository: 	repo,
							rootPackage:	path.toString().replace("src\\", "").replace("\\", ".")]
				
				// generate RepositoryImpl
				def javaObjectFileName = "${repo.name}Impl.java"
				def javaObjectFile = new File(path, javaObjectFileName)
				def template = engine.createTemplate(new File("src/generator/java/templates/Repository.tmpl")).make(bind)
				javaObjectFile.write template.toString()
				
				// generate RepositoryInterface
				javaObjectFileName = "${repo.name}I.java"
				javaObjectFile = new File(path, javaObjectFileName)
				template = engine.createTemplate(new File("src/generator/java/templates/RepositoryInterface.tmpl")).make(bind)
				javaObjectFile.write template.toString()
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
		repo.getMetaClass().firstEntity = repo.domainReferences.get(0).referencedObject 
	}
}
