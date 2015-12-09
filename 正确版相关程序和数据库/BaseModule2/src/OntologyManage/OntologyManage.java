package OntologyManage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.hp.hpl.jena.query.ResultSet;

import jxl.read.biff.BiffException;


public interface OntologyManage {
	/**���һ�����ʼ������ԣ��޷���ֵ----------------------------------------------------------
	 * 
	 * @param parameter
	 */
	public void Add(String[] parameter);

	/**���һ�����Ӽ������ԣ��޷���ֵ----------------------------------------------------------
	 * 
	 * @param parameter
	 */
	public void AddSentence(String[] parameter);
	
	/**��Excel��������ӵ��ʣ��޷���ֵ--------------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void AddBatch(InputStream yourPath) throws BiffException, IOException;

	/**��Excel��������Ӿ��ӣ��޷���ֵ--------------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void AddSentenceBatch(InputStream yourPath) throws BiffException, IOException;

	/**ɾ��һ�����ʼ������ԣ��޷���ֵ----------------------------------------------------------
	 * 
	 * @param yourInstanceID
	 */
	public void Delete(String yourInstanceID);
	
	/**ɾ��һ�����Ӽ������ԣ��޷���ֵ----------------------------------------------------------
	 * 
	 * @param yourInstanceID
	 */
	public void DeleteSentence(String yourInstanceID);

	/**�޸�һ�����ʵ�ĳ�����ԣ��޷���ֵ----------------------------------------------------------
	 * 
	 * @param yourProperty
	 * @param yourPropertyLabel
	 * @param yourSPARQLProperty
	 * @param yourRelationProperty
	 */
	public void Modify(String yourProperty, String yourPropertyLabel, String yourSPARQLProperty, String yourRelationProperty);
	
	/**�޸�һ�����Ӽ������ԣ��޷���ֵ----------------------------------------------------------
	 * 
	 * @param yourProperty
	 * @param yourPropertyLabel
	 * @param yourSPARQLProperty
	 * @param yourRelationProperty
	 */
	public void ModifySentence(String yourProperty, String yourPropertyLabel, String yourSPARQLProperty, String yourRelationProperty);
	
	/**������������µ����е���Label�����ؽ����ResultSet-----------------------------------------
	 * 
	 * @param yourClass
	 * @return
	 */
	public ResultSet QueryWord(String yourClass);

	/**����ID���Ҹõ��ʼ����������ԣ����ؽ����ResultSet-------------------------------------------
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet QueryIndividualDependOnId(String yourID);

	/**��һ�����ʵ��������ԣ����ؽ����ResultSet------------------------------------------------
	 * 
	 * @param yourWord
	 * @return
	 */
	public ResultSet QueryIndividual(String yourWord);
	
	/**����ID���Ҹþ��Ӽ����������ԣ����ؽ����ResultSet-------------------------------------------
	 * 
	 * @param yourID
	 * @return
	 */
	public ResultSet QuerySentenceIndividualDependOnId(String yourID);

	/**��һ�����ӵ��������ԣ����ؽ����ResultSet--------------------------------------------------
	 * 
	 * @param yourSentence
	 * @return
	 */
	public ResultSet QuerySentenceIndividual(String yourSentence);
	
	/**��Fuseki��д��OWL�ļ��У��ҵ��ʺ;��ӷֿ����޷���ֵ-------------------------------------------
	 * 
	 * @throws IOException
	 */
	public void WriteBackToOwl() throws IOException;
	
	/**��Excel����ӵȼ۹�ϵSameAs���޷���ֵ---------------------------------------------------
	 * 
	 * @param yourPath
	 * @throws BiffException
	 * @throws IOException
	 */
	public void InsertRelationSameAs(InputStream yourPath) throws BiffException, IOException;
	
	/**
	 * ����ȼ۹�ϵ
	 * 
	 * @param yourWord
	 * @return
	 */
	public ArrayList<String> ReasonSameAs(String yourSentence);

}
