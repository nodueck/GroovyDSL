import static org.junit.Assert.*

import MetaModell.Attribute
import MetaModell.Project
import org.junit.Test

class DMDStructureBuilderTest {

	@Test
	public void testEntity() {
		def project = new DMDStructureBuilder().Test {
			entity("TestEntity")
			entity("SecondEntity")
		}
		
		assertNotNull(project.entityList)
		assertEquals(2, project.entityList.size())
		
		//First element in DSL equals first element in the List
		assertEquals("TestEntity", project.entityList.get(0).name)
	}
	
	@Test
	public void testValueObject() {
		def project = new DMDStructureBuilder().Test {
			valueObject("TestVO")
			valueObject("SecondVO")
		}
		
		
		assertNotNull(project.valueObjectList)
		assertEquals(2, project.valueObjectList.size())
		
		//First element in DSL equals first element in the List
		assertEquals("TestVO", project.valueObjectList.get(0).name)
	}
	
	@Test
	public void testAttributes() {
		def project = new DMDStructureBuilder().Test {
			valueObject("TestVO") {
				id
				attributeWithType Text
				attributeWithProperties Text(min:1, max:20), Description: "description for attribute with properties"
			}
		}
		
		
		
		assertNotNull(project.valueObjectList)
		assertEquals(1, project.valueObjectList.size())
		
		def attributes = project.valueObjectList.get(0).attributeList
		assertEquals(2, attributes.size())
		
		Attribute attribute1 = attributes.get(0) as Attribute
		assertEquals("attributeWithType", attribute1.name)
//		assertEquals(Text, attribute1.type) TODO
		
		Attribute attribute2 = attributes.get(1) as Attribute
		assertEquals("attributeWithProperties", attribute2.name)
//		assertEquals(Text, attribute2.type)
		println attribute2.attributes["Description"]
		assertEuqlas("description for attribute with properties", attribute2.attributes["Description"])
		
		//TODO: test, there's some output that id of valueObject "TestVO" was ignored
	}

}
