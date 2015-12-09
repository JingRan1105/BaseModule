package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QueryWithManyWays {

	/**
	 * 根据Id查找单词及其属性：返回ResultSet----------------------------------
	 * 
	 * @param yourId
	 *            ：单词ID
	 * @return
	 */
	public ResultSet checkPropertyDependOnId(String yourId);

	/**
	 * 根据Id查找实例及其属性：返回值ResultSet---------------------------------
	 * 
	 * @param yourId
	 *            ：句子ID
	 * @return
	 */
	public ResultSet checkSentencePropertyDependOnId(String yourId);

	/**
	 * 查找一个类下的所有实例标签Label：返回值ResultSet--------------------------
	 * 
	 * @param yourClass
	 *            ：单词的类
	 * @return
	 */
	public ResultSet checkInstance(String yourClass);

	/**
	 * 查找该实例的所有实例及其属性：返回值ResultSet-------------------------------
	 * 
	 * @param yourWord
	 *            ：单词
	 * @return
	 */
	public ResultSet checkProperty(String yourWord);

	/**
	 * 根据实例名称查找句子所有属性值：返回值ResultSet-----------------------------
	 * 
	 * @param yourSentence
	 * @return
	 */
	public ResultSet checkSentenceProperty(String yourSentence);

	/**
	 * 判断数据库中是否存在此实例：返回值boolean--------------------------------
	 * 
	 * @param Instance
	 * @return
	 */
	public boolean checkIfInDB(String Instance);

	/**
	 * 查找所有三元组：返回值ResultSet--------------------------------------
	 * 
	 * @return
	 */
	public ResultSet checkAllTriple();
	
	/**
	 * 查找等价sameAs关系
	 * 
	 * @param yourInstance
	 * @return
	 */
	public ResultSet checkPropertySameAs(String yourInstance);
	
	/**
	 * 只找实例的Label
	 * 
	 * @param yourInstance
	 * @return
	 */
	public ResultSet checkOnlyInstanceURI(String yourInstance);
	
	/**
	 * 只找属性名称
	 * 
	 * @param yourPropertyURI
	 * @return
	 */
	public ResultSet checkOnlyPropertyLabel(String yourPropertyURI);
	
	/**
	 * 根据ID查找其父类+标签Label+ID的三元组(为修改特例设计的)
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet checkClass_Label(String yourID);
	
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 根据实例+某属性+属性值，查找该三元组：返回值ResultSet------------------------
	 * 
	 * @param yourInstance
	 *            ：实例标签
	 * @param propertyLabel
	 *            ：属性名
	 * @param flag
	 *            ：标志（单词为“31”， 句子为“85”）
	 * @return
	 */
	public ResultSet checkThisTriple(String yourInstance, String propertyLabel,
			String flag);

	/**
	 * 根据实例Label查其所有ID：返回值ResultSet-------------------------------
	 * 
	 * @param yourInstance
	 *            ：实例标签
	 * @param flag
	 * @return：标志（单词为“31”， 句子为“85”）
	 */
	public ResultSet checkAllID(String yourInstance, String flag);

}
