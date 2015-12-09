package OntologyManage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.hp.hpl.jena.query.ResultSet;

import jxl.read.biff.BiffException;


public interface OntologyManage {
	/**添加一个单词及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param parameter
	 */
	public void Add(String[] parameter);

	/**添加一个句子及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param parameter
	 */
	public void AddSentence(String[] parameter);
	
	/**从Excel中批量添加单词：无返回值--------------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void AddBatch(InputStream yourPath) throws BiffException, IOException;

	/**从Excel中批量添加句子：无返回值--------------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void AddSentenceBatch(InputStream yourPath) throws BiffException, IOException;

	/**删除一个单词及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param yourInstanceID
	 */
	public void Delete(String yourInstanceID);
	
	/**删除一个句子及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param yourInstanceID
	 */
	public void DeleteSentence(String yourInstanceID);

	/**修改一个单词的某个属性：无返回值----------------------------------------------------------
	 * 
	 * @param yourProperty
	 * @param yourPropertyLabel
	 * @param yourSPARQLProperty
	 * @param yourRelationProperty
	 */
	public void Modify(String yourProperty, String yourPropertyLabel, String yourSPARQLProperty, String yourRelationProperty);
	
	/**修改一个句子及其属性：无返回值----------------------------------------------------------
	 * 
	 * @param yourProperty
	 * @param yourPropertyLabel
	 * @param yourSPARQLProperty
	 * @param yourRelationProperty
	 */
	public void ModifySentence(String yourProperty, String yourPropertyLabel, String yourSPARQLProperty, String yourRelationProperty);
	
	/**根据类查找类下的所有单词Label：返回结果集ResultSet-----------------------------------------
	 * 
	 * @param yourClass
	 * @return
	 */
	public ResultSet QueryWord(String yourClass);

	/**根据ID查找该单词及其所有属性：返回结果集ResultSet-------------------------------------------
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet QueryIndividualDependOnId(String yourID);

	/**查一个单词的所有属性：返回结果集ResultSet------------------------------------------------
	 * 
	 * @param yourWord
	 * @return
	 */
	public ResultSet QueryIndividual(String yourWord);
	
	/**根据ID查找该句子及其所有属性：返回结果集ResultSet-------------------------------------------
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet QuerySentenceIndividualDependOnId(String yourID);

	/**查一个句子的所有属性：返回结果集ResultSet--------------------------------------------------
	 * 
	 * @param yourSentence
	 * @return
	 */
	public ResultSet QuerySentenceIndividual(String yourSentence);
	
	/**从Fuseki中写回OWL文件中，且单词和句子分开：无返回值-------------------------------------------
	 * 
	 * @throws IOException
	 */
	public void WriteBackToOwl() throws IOException;
	
	/**从Excel中添加等价关系SameAs：无返回值---------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void InsertRelationSameAs(InputStream yourPath) throws BiffException, IOException;
	
	/**
	 * 推理等价关系
	 * 
	 * @param yourWord
	 * @return
	 */
	public ArrayList<String> ReasonSameAs(String yourSentence);

}
