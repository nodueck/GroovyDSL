package parser

import java.util.logging.Level
import java.util.logging.Logger

import groovy.util.FactoryBuilderSupport

class DMDFactoryBuilder extends FactoryBuilderSupport {	
	
	public DMDFactoryBuilder(boolean init = true) {
		super(init)
	}
	
	def registerObjectFactories() {
		registerFactory("module", new DomainModelFactory())
		registerFactory("Test", new DomainModelFactory())
		registerFactory("entity", new BusinessObjectFactory())
		registerFactory("valueObject", new BusinessObjectFactory())
		registerFactory("attr", new DomainPropertyFactory())
		registerFactory("description", new RequirementFactory())
		registerFactory("has", new DomainReferenceFactory())
		registerFactory("hasMany", new DomainReferenceFactory())
		registerFactory("repository", new RepositoryFactory())
	}
	
	def propertyMissing(String name) {
		//add to list and initalize later
		name
	}

}
