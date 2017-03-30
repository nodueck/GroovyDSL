package MetaModell.OtherDomainAbstractions

import MetaModell.DomainAbstraction

class ApplicationController extends DomainAbstraction {
	
	AppCtrlType appCtrlType
	
	enum AppCtrlType {
		Component,
		General,
		AutomaticClient
	}
}
