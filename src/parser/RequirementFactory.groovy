package parser

import java.util.Map
import MetaModell.DomainAbstraction
import MetaModell.Requirement
import groovy.util.AbstractFactory
import groovy.util.FactoryBuilderSupport

class RequirementFactory extends AbstractFactory {
	
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map map)
			throws InstantiationException, IllegalAccessException {
				
		new Requirement(value)
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
		parent.requirement = child
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
	}

}