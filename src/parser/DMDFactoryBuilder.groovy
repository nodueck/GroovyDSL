package parser

import java.util.logging.Level
import java.util.logging.Logger

import groovy.util.FactoryBuilderSupport

class DMDFactoryBuilder extends FactoryBuilderSupport {
	
	/**
	 * Wird zwischen den Modellelementen referenziert ist eine Symbolic Table erforderlich.
	 * Dies speichert die zu referenzierenden Objekte.
	 * Bsp.: Contract referenziert einen Customer. Anstatt den Customer im Modell zu suchen, kann er leicht in der Symbolic Table gefunden werden.
	 */
	
	
	
	public DMDFactoryBuilder(boolean init = true) {
		super(init)
	}
	
	def registerObjectFactories() {
		registerFactory("Test", new DomainModelFactory())
		registerFactory("entity", new BusinessObjectFactory())
		registerFactory("valueObject", new BusinessObjectFactory())
		registerFactory("attr", new DomainPropertyFactory())
		registerFactory("description", new RequirementFactory())
		registerFactory("has", new DomainObjectReferenceFactory())
		registerFactory("hasMany", new DomainObjectReferenceFactory())
	}
	
	def propertyMissing(String name) {
		//add to list and initalize later
		name
	}

}
