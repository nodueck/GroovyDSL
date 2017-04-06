package parser

import java.util.Map
import metamodell.DomainAbstraction
import metamodell.DomainModel
import metamodell.Requirement
import metamodell.otherDomainAbstractions.Repository
import groovy.util.AbstractFactory
import groovy.util.FactoryBuilderSupport

class RepositoryFactory extends AbstractFactory {
	
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map map)
			throws InstantiationException, IllegalAccessException {
				
		new Repository(name:value)
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
		if(parent instanceof DomainModel){
			(parent as DomainModel).otherDomainAbstractions.add(child)
		} else {
			throw new Exception('Parent of a Repository needs to be a DomainModel')
		}
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
	}

}