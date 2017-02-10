package types

class AbstractType {
	def min
	def max
	
	String toString() {
		this.class.getName() + " -> min: ${min}, max: ${max}"
	}
}
