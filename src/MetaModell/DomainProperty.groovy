package MetaModell

class Attribute extends AbstractElement{

	def type
	def attributes
	
	@Override
	String toString() {
		return "${this.name} : ${this.type} with attributes: ${attributes}"
	}
}
