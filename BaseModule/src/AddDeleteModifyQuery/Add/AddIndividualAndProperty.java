package AddDeleteModifyQuery.Add;

public interface AddIndividualAndProperty {

	// ��ӵ���ʵ�����ƣ��޷���ֵ��������Ҫ��ӵ����࣬ʵ��������������
	void addInstance(String yourClass, String yourInstance);
	//void addInstance(String yourInstance);

	// ��ӵ���ʵ�����ԣ��������ԣ����޷���ֵ����������������ʵ�����ƣ�����ֵ
	void addProperty(String propertyLabel, String yourInstance,
			String yourProperty);

	// ��Ӿ���ʵ�����ƣ��޷���ֵ��������Ҫ��ӵ����࣬ʵ��������������
	void addSentenceInstance(String yourClass, String yourInstance);

	// ��Ӿ���ʵ�����ԣ��������ԣ����޷���ֵ����������������ʵ�����ƣ�����ֵ
	void addSentenceProperty(String propertyLabel, String yourInstance,
			String yourProperty);
	
}
