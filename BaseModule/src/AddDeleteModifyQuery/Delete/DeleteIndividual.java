package AddDeleteModifyQuery.Delete;

public interface DeleteIndividual {
	
	//ɾ��ʵ�������ԣ��޷���ֵ��������ʵ������
	void deleteInstanceProperty(String yourInstanceLabel, String property, String propertyValue);
	
	//ɾ�����ʵ��Label
	void deleteClassAndLabel(String yourInstanceLabel, String yourInstanceClass);

}
