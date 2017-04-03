package metamodell.businessobjects

import groovy.transform.ToString

@ToString(includeSuper=true)
class ValueObject extends DomainObject{
	
	boolean webObject = false;
}
