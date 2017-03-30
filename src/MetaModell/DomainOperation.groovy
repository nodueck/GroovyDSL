package MetaModell

import groovy.transform.ToString

@ToString(includes='name')
abstract class DomainOperation {
	
	public enum TrxAttribute {
		Ignorieren,
		Requires_New,
		Supports,
		Required,
		Mandatory,
		Not_Supported
	}
	
	String name
	Requirement requirement
	TrxAttribute trxAttribute
}

