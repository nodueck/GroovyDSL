package metamodell.businessobjects

import metamodell.DomainAbstraction
import metamodell.DomainProperty


class DomainObject extends DomainAbstraction{

	String technicalName
	DomainObjectOperation[] operations;
	
}
