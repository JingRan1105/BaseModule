package AddDeleteModifyQuery.Add;

public interface AddIndividualAndProperty {

	/** ��ӵ��ʵ��ࣺ�޷���ֵ-------------------------------------------------------
	 * 
	 * @param yourClass����Ҫ��ӵĵ��ʵ���
	 * @param yourInstance����Ҫ��ӵĵ���
	 */
	void addClass(String yourClass, String yourInstance);
	
	/**��ӵ��ʵı�ǩLabel���޷���ֵ
	 * 
	 * @param yourInstance����Ҫ��ӵĵ���
	 */
	void addLabel(String yourInstance);


	/**��ӵ��ʵ����ԣ��������ԣ����޷���ֵ-----------------------------------------------
	 * 
	 * @param propertyLabel����Ҫ��ӵĵ������ԣ��硰����ID����
	 * @param yourInstance����Ҫ��ӵĵ���
	 * @param yourProperty����Ҫ��ӵĵ�������ֵ���硰����ID����Ӧ������ֵΪ��1/1/1/1��
	 */
	void addProperty(String propertyLabel, String yourInstance,
			String yourProperty);


	/**��Ӿ��ӵ��ࣺ�޷���ֵ--------------------------------------------------------
	 * 
	 * @param yourClass����Ҫ��ӵľ��ӵ���
	 * @param yourInstance����Ҫ��ӵľ���
	 */
	void addSentenceClass(String yourClass, String yourInstance);
	
	/**��Ӿ��ӵı�ǩLabel���޷���ֵ--------------------------------------------------
	 * 
	 * @param yourInstance����Ҫ��ӵľ���
	 */
	void addSentenceLabel(String yourInstance);

	/**��Ӿ��ӵ����ԣ��޷���ֵ-------------------------------------------------------
	 * 
	 * @param propertyLabel����Ҫ��ӵľ������ԣ��硰����ID����
	 * @param yourInstance����Ҫ��ӵľ���
	 * @param yourProperty����Ҫ��ӵľ�������ֵ���硰����ID����Ӧ������ֵΪ��2/2/2/2��
	 */
	void addSentenceProperty(String propertyLabel, String yourInstance,
			String yourProperty);
	
	/**Ϊ�޸ĵ�����Ƶ���ӷ������޷���ֵ------------------------------------------------
	 * 
	 * @param propertyLabel����Ҫ��ӵĵ�����������(DataProperty)
	 * @param yourInstance����Ҫ��ӵĵ���ʵ��
	 * @param yourProperty����Ҫ��ӵĵ�������ֵ
	 * @param yourID����ʵ����Ӧ�ĵ���ID����Ϊ������ʵ����������Ҫ�Ե���IDֵ����
	 */
	void addPropertyForModify(String propertyLabel, String yourInstance,
			String yourProperty, String yourID);

	/**Ϊ�޸ľ�����Ƶ���ӷ������޷���ֵ------------------------------------------------
	 * 
	 * @param propertyLabel����Ҫ��ӵľ�����������(DataProperty)
	 * @param yourInstance����Ҫ��ӵľ���ʵ��
	 * @param yourProperty����Ҫ��ӵľ�������ֵ
	 * @param yourID����ʵ����Ӧ�ĵ���ID����Ϊ������ʵ����������Ҫ�Ծ���IDֵ����
	 */
	void addSentencePropertyForModify(String propertyLabel, String yourInstance,
			String yourProperty, String yourID);
	
	/**��ӵȼ۹�ϵsameAs
	 * 
	 * @param question1
	 * @param question2
	 */
	void addRelationSameAs(String question1, String question2);
}
