package parser
import static org.junit.Assert.*

import org.junit.Before

import metamodell.DomainProperty
import metamodell.DomainReference
import metamodell.businessobjects.Entity
import metamodell.businessobjects.ValueObject
import metamodell.typesystem.Text
import parser.DMDFactoryBuilder
import parser.SymbolicTable.NameTypePair
import metamodell.DomainAbstraction
import metamodell.DomainModel
import groovy.util.ObjectGraphBuilder.DefaultNewInstanceResolver

import org.junit.Test

import dmd.dsl.DSL
/**
 * Assures that model is populated correctly
 * @author DUEC007
 *
 */
class DMDFactoryBuilderTest {
	
	@Before
	void setUp() {
		DomainModel.instance.domainObjects = []
		SymbolicTable.instance.symbolTable.clear()
		SymbolicTable.instance.notYetResolvedObjects.clear()
	}
	
	@Test
	public void testProject() {
		def project = new DMDFactoryBuilder().Test { }
		println project
		assertEquals("Test", project.name)
	}

	@Test
	public void testEntity() {
		DomainModel project = new DMDFactoryBuilder().Test {
			entity("TestEntity")
			entity("SecondEntity")
		}
		
		assertNotNull(project.domainObjects)
		assertEquals(2, project.domainObjects.size())
		
		//First element in DSL equals first element in the List
		assertEquals("TestEntity", project.domainObjects.get(0).name)
		assertEquals("SecondEntity", project.domainObjects.get(1).name)
	}
	
	@Test
	public void testValueObject() {
		DomainModel project = new DMDFactoryBuilder().Test {
			valueObject(TestVO)
			valueObject(SecondVO)
		}
		
		
		assertNotNull(project.domainObjects)
		assertEquals(2, project.domainObjects.size())
		
		//First element in DSL equals first element in the List
		assertEquals("TestVO", project.domainObjects.get(0).name)
		assertEquals(ValueObject.class, project.domainObjects.get(0).class)
	}
	
	@Test
	public void testAttributes() {
		DomainModel project = new DMDFactoryBuilder().Test {
			valueObject(TestVO) {
				attr attributeWithProperties, type: Text, description: "description for attribute with properties"
			}
		}
		
		assertNotNull(project.domainObjects)
		assertEquals(1, project.domainObjects.size())
		
		DomainProperty attribute = project.domainObjects.get(0).domainProperties.get(0)
		assertEquals("attributeWithProperties", attribute.name)
		assertTrue(attribute.type instanceof Text)
		assertEquals("description for attribute with properties", attribute.requirement.content)
	}
	
	@Test
	public void testDomainObjectReference() {
		DomainModel project = new DMDFactoryBuilder().Test {
			entity(Customer) {
				attr name, 		type:Text
				attr nachname, 	type:Text
				hasMany Contract, type: entity
			}
			
			entity(Contract) {
				attr tarif, type:Text
				has Customer, type:entity
			}
		}
		
		Entity customer = project.instance.domainObjects.get(0)
		Entity contract = project.instance.domainObjects.get(1)
		
		//Test reference at customer entity
		DomainReference customer2contractReference = customer.domainReferences.get(0)
		assertFalse(customer2contractReference.foreignKey)
		assertFalse(customer2contractReference.loeschweitergabe)
		assertNotNull(customer2contractReference.referencedObject)
		assertEquals(contract, customer2contractReference.referencedObject)
		assertEquals("hasMany", customer2contractReference.referenceType)
		
		//Test reference at contract entity
		DomainReference contract2customerReference = contract.domainReferences.get(0)
		assertFalse(contract2customerReference.foreignKey)
		assertFalse(contract2customerReference.loeschweitergabe)
		assertNotNull(contract2customerReference.referencedObject)
		assertEquals(customer, contract2customerReference.referencedObject)
		assertEquals("has", contract2customerReference.referenceType)
		
	}
	
	@Test
	public void testFactoryBuilder() {
		DSL dslScript = new DSL()
		dslScript.parseDirectory(new File("test/resources/"));
	}

}
