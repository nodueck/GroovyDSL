package parser

import java.util.Map
import metamodell.DomainModel
import groovy.util.FactoryBuilderSupport

class DomainModelFactory extends AbstractFactory{
	
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map map)
			throws InstantiationException, IllegalAccessException {
			
			if(map.isEmpty()){
				def domain = new DomainModel()
				domain.name = value
				return domain
			} else {
				throw new Exception("$name called: DomainModel $value doesn't expect any parameters")
			}
	}
	
	@Override
	public void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
		if(parent != null){
			throw new Exception("DomainModel can't have a parent.")
		}
	}
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
		SymbolicTable.instance.resolveObjects()
	}

}
