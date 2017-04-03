package parser
import static org.junit.Assert.*

import org.junit.Before

import MetaModell.DomainProperty
import MetaModell.BusinessObjects.ValueObject
import MetaModell.TypeSystem.Text
import parser.DMDFactoryBuilder
import MetaModell.DomainAbstraction
import MetaModell.DomainModel
import dmd.dsl.DMDStructureBuilder

import org.junit.Test
/**
 * Assures that model is populated correctly
 * @author DUEC007
 *
 */
class DMDStructureBuilderTest {
	
	@Before
	void setUp() {
		DomainModel.instance.domainObjects = []
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
		
		println SymbolicTable.instance.symbolTable
		
		assertNotNull(project.domainObjects)
		assertEquals(1, project.domainObjects.size())
		
		DomainProperty attribute = project.domainObjects.get(0).domainProperties.get(0)
		assertEquals("attributeWithProperties", attribute.name)
		assertTrue(attribute.type instanceof Text)
		assertEquals("description for attribute with properties", attribute.requirement.content)
	}

}
