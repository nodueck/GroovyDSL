package MetaModell.BusinessObjects

import MetaModell.DomainAbstraction
import MetaModell.DomainProperty


class DomainObject extends DomainAbstraction{

	String technicalName
	DomainObjectOperation[] operations;
	
}
