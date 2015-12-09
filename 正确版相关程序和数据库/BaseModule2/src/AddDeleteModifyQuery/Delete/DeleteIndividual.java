package AddDeleteModifyQuery.Delete;

public interface DeleteIndividual {

	/**ɾ�����ʵ����ԣ��޷���ֵ-------------------------------------------------------------
	 * 
	 * @param yourInstanceLabel����Ҫɾ���ĵ���
	 * @param property����Ҫɾ���ĵ�������
	 * @param propertyValue����Ҫɾ���ĵ�������ֵ
	 */
	void deleteInstanceProperty(String yourInstanceLabel, String property,
			String propertyValue);

	/**ɾ�����ʵ��ࣺ�޷���ֵ--------------------------------------------------------------
	 * 
	 * @param yourInstanceLabel����Ҫɾ���ĵ���
	 * @param yourInstanceClass����Ҫɾ���ĵ��ʵ���
	 */
	void deleteClass(String yourInstanceLabel,String yourInstanceClass);
	
	/**ɾ�����ʵı�ǩLabel���޷���ֵ--------------------------------------------------------
	 * 
	 * @param yourInstanceLabel����Ҫɾ���ĵ���
	 */
	void deleteLabel(String yourInstanceLabel);

	
	/**ɾ�����ӵ����ԣ��޷���ֵ-------------------------------------------------------------
	 * 
	 * @param yourInstanceLabel����Ҫɾ���ľ���
	 * @param property����Ҫɾ���ľ��ӵ�����
	 * @param propertyValue����Ҫɾ���ľ��ӵ�����ֵ
	 */
	void deleteSentenceInstanceProperty(String yourInstanceLabel, String property,
			String propertyValue);

	/**ɾ�����ӵ��ࣺ�޷���ֵ--------------------------------------------------------------
	 * 
	 * @param yourInstanceLabel����Ҫɾ���ľ���
	 * @param yourInstanceClass����Ҫɾ���ľ��ӵ���
	 */
	void deleteSentenceClass(String yourInstanceLabel, String yourInstanceClass);
	
	/**ɾ�����ӵ�ʵ��Label���޷���ֵ--------------------------------------------------------
	 * 
	 * @param yourInstanceLabel����Ҫɾ���ľ���
	 */
	void deleteSentenceLabel(String yourInstanceLabel);

}
