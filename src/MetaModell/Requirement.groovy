package MetaModell;

import groovy.transform.ToString

@ToString(includes='content')
public class Requirement {
	String content
	
	public Requirement(def content){
		this.content = content
	}
}
