package AddDeleteModifyQuery.Delete;

public interface DeleteIndividual {
	
	//删除实例的属性：无返回值；参数：实例名称
	void deleteInstanceProperty(String yourInstanceLabel, String property, String propertyValue);
	
	//删除类和实例Label
	void deleteClassAndLabel(String yourInstanceLabel, String yourInstanceClass);

}
