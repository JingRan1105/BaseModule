package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QueryWithManyWays {

	/**
	 * ����Id���ҵ��ʼ������ԣ�����ResultSet----------------------------------
	 * 
	 * @param yourId
	 *            ������ID
	 * @return
	 */
	public ResultSet checkPropertyDependOnId(String yourId);

	/**
	 * ����Id����ʵ���������ԣ�����ֵResultSet---------------------------------
	 * 
	 * @param yourId
	 *            ������ID
	 * @return
	 */
	public ResultSet checkSentencePropertyDependOnId(String yourId);

	/**
	 * ����һ�����µ�����ʵ����ǩLabel������ֵResultSet--------------------------
	 * 
	 * @param yourClass
	 *            �����ʵ���
	 * @return
	 */
	public ResultSet checkInstance(String yourClass);

	/**
	 * ���Ҹ�ʵ��������ʵ���������ԣ�����ֵResultSet-------------------------------
	 * 
	 * @param yourWord
	 *            ������
	 * @return
	 */
	public ResultSet checkProperty(String yourWord);

	/**
	 * ����ʵ�����Ʋ��Ҿ�����������ֵ������ֵResultSet-----------------------------
	 * 
	 * @param yourSentence
	 * @return
	 */
	public ResultSet checkSentenceProperty(String yourSentence);

	/**
	 * �ж����ݿ����Ƿ���ڴ�ʵ��������ֵboolean--------------------------------
	 * 
	 * @param Instance
	 * @return
	 */
	public boolean checkIfInDB(String Instance);

	/**
	 * ����������Ԫ�飺����ֵResultSet--------------------------------------
	 * 
	 * @return
	 */
	public ResultSet checkAllTriple();
	
	/**
	 * ���ҵȼ�sameAs��ϵ
	 * 
	 * @param yourInstance
	 * @return
	 */
	public ResultSet checkPropertySameAs(String yourInstance);
	
	/**
	 * ֻ��ʵ����Label
	 * 
	 * @param yourInstance
	 * @return
	 */
	public ResultSet checkOnlyInstanceURI(String yourInstance);
	
	/**
	 * ֻ����������
	 * 
	 * @param yourPropertyURI
	 * @return
	 */
	public ResultSet checkOnlyPropertyLabel(String yourPropertyURI);
	
	/**
	 * ����ID�����丸��+��ǩLabel+ID����Ԫ��(Ϊ�޸�������Ƶ�)
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet checkClass_Label(String yourID);
	
	
	// -----------------------------------------------------------------------------------------
	/**
	 * ����ʵ��+ĳ����+����ֵ�����Ҹ���Ԫ�飺����ֵResultSet------------------------
	 * 
	 * @param yourInstance
	 *            ��ʵ����ǩ
	 * @param propertyLabel
	 *            ��������
	 * @param flag
	 *            ����־������Ϊ��31���� ����Ϊ��85����
	 * @return
	 */
	public ResultSet checkThisTriple(String yourInstance, String propertyLabel,
			String flag);

	/**
	 * ����ʵ��Label��������ID������ֵResultSet-------------------------------
	 * 
	 * @param yourInstance
	 *            ��ʵ����ǩ
	 * @param flag
	 * @return����־������Ϊ��31���� ����Ϊ��85����
	 */
	public ResultSet checkAllID(String yourInstance, String flag);

}
