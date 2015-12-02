package Integration.Impl;

import Integration.AddDeleteModifyQueryInterface;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.vocabulary.RDF;

public class AddDeleteModifyQueryImpl implements AddDeleteModifyQueryInterface{

	private static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";
	private static String UPDATE_SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/update";

	public AddDeleteModifyQueryImpl() {

	}

	// ���ӡ���������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������
	public void addInstance(String yourClass, String yourInstance) {
		// ��������ֵurl
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext())
			solutionClass = resultsClass.next();

		// ���ʵ������
		String sparqlAddInstance = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class") + ">}";
		System.out.println(sparqlAddInstance);
		// ���ʵ��Label
		String sparqlAddInstanceLabel = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh>"
				+ "<http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourInstance + "\"@zh.}";
		UpdateRequest updateInstance = UpdateFactory.create(sparqlAddInstance);
		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
				updateInstance, UPDATE_SERVER);
		qexecInstance.execute();

		UpdateRequest updateInstanceLabel = UpdateFactory
				.create(sparqlAddInstanceLabel);
		UpdateProcessor qexecInstanceLabel = UpdateExecutionFactory
				.createRemote(updateInstanceLabel, UPDATE_SERVER);
		qexecInstanceLabel.execute();
	}

	public void addProperty(String propertyLabel, String yourInstance,
			String yourProperty) {
		// ��������url
		String sparqlProperty = "SELECT ?Property WHERE{?Property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel + "\"@zh.}";
		ResultSet results = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (results.hasNext())
			solutionProperty = results.next();

		// �������
		String stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?Property")
				+ "> \"" + yourProperty + "\"@zh.}";
		UpdateRequest updateProperty = UpdateFactory.create(stringProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();
	}

	// ɾ��ʵ������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������
	public void deleteInstance(String yourClass, String yourInstance) {
		// ��������url
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext())
			solutionClass = resultsClass.next();

		// ɾ��ʵ������
		String sparqlDeleteInstance = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class") + ">}";
		UpdateRequest updateInstance = UpdateFactory
				.create(sparqlDeleteInstance);
		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
				updateInstance, UPDATE_SERVER);
		qexecInstance.execute();
		// ɾ��ʵ��Label
		String sparqlDeleteInstanceLabel = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh>"
				+ "<http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourInstance + "\"@zh.}";
		UpdateRequest updateInstanceLabel = UpdateFactory
				.create(sparqlDeleteInstanceLabel);
		UpdateProcessor qexecInstanceLabel = UpdateExecutionFactory
				.createRemote(updateInstanceLabel, UPDATE_SERVER);
		qexecInstanceLabel.execute();
	}

	public void deleteProperty(String yourInstance, String yourProperty) {

		// ��������URI
		String sparqlProperty = "SELECT ?property WHERE{?property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourProperty + "\"@zh.}";
		ResultSet resultsProperty = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (resultsProperty.hasNext())
			solutionProperty = resultsProperty.next();
		System.out.println(sparqlProperty);
		System.out.println(solutionProperty.get("?property"));
		// ��������ֵ
		// String sparqlPropertyValue = "SELECT ?propertyValue WHERE{?property"
		// + solutionProperty.get("?property")+ "?propertyValue}";
		String sparqlPropertyValue = "SELECT ?propertyValue WHERE{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + "?propertyValue}";
		ResultSet resultsPropertyValue = Result(sparqlPropertyValue);
		QuerySolution solutionPropertyValue = null;
		while (resultsPropertyValue.hasNext())
			solutionPropertyValue = resultsPropertyValue.next();
		System.out.println(sparqlPropertyValue);
		System.out.println(solutionPropertyValue.get("?propertyValue"));

		String string1 = solutionPropertyValue.toString();
		int i = string1.length();
		i = i - 2;
		String string2 = (String) string1.subSequence(19, i);
		System.out.println(string2);

		// ɾ��ʵ������
		String sparqlDeleteProperty = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + string2 + "}";
		System.out.println(sparqlDeleteProperty);

		UpdateRequest updateProperty = UpdateFactory
				.create(sparqlDeleteProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();

	}

	// �޸ġ���������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������
	public void ModifyInstance(String yourProperty, String yourInstance,
			String propertyLabel) {

		// ��������URI
		String sparqlProperty = "SELECT ?property WHERE{?property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel + "\"@zh.}";
		ResultSet resultsProperty = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (resultsProperty.hasNext())
			solutionProperty = resultsProperty.next();
		System.out.println(sparqlProperty);
		System.out.println(solutionProperty.get("?property"));
		// ��������ֵ
		// String sparqlPropertyValue = "SELECT ?propertyValue WHERE{?property"
		// + solutionProperty.get("?property")+ "?propertyValue}";
		String sparqlPropertyValue = "SELECT ?propertyValue WHERE{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + "?propertyValue}";
		ResultSet resultsPropertyValue = Result(sparqlPropertyValue);
		QuerySolution solutionPropertyValue = null;
		while (resultsPropertyValue.hasNext())
			solutionPropertyValue = resultsPropertyValue.next();
		System.out.println(sparqlPropertyValue);
		System.out.println(solutionPropertyValue.get("?propertyValue"));

		String string1 = solutionPropertyValue.toString();
		int i = string1.length();
		i = i - 2;
		String string2 = (String) string1.subSequence(19, i);
		System.out.println(string2);

		// ɾ��ʵ������
		String sparqlDeleteProperty = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?property")
				+ "> " + string2 + "}";
		System.out.println(sparqlDeleteProperty);

		UpdateRequest updateProperty = UpdateFactory
				.create(sparqlDeleteProperty);
		UpdateProcessor qexecProperty = UpdateExecutionFactory.createRemote(
				updateProperty, UPDATE_SERVER);
		qexecProperty.execute();

		String sparqlProperty1 = "SELECT ?Property WHERE{?Property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel + "\"@zh.}";
		ResultSet results = Result(sparqlProperty1);
		QuerySolution solutionProperty1 = null;
		while (results.hasNext())
			solutionProperty1 = results.next();

		// �������
		String stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty1.get("?Property")
				+ "> \"" + yourProperty + "\"@zh}";
		System.out.println(stringProperty);
		UpdateRequest updateProperty1 = UpdateFactory.create(stringProperty);
		UpdateProcessor qexecProperty1 = UpdateExecutionFactory.createRemote(
				updateProperty1, UPDATE_SERVER);
		qexecProperty1.execute();
	}

	// ���ҡ���������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������
	public ResultSet checkInstance(String yourClass) {

		String string1 = "SELECT ?instanceLabel WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel}";
		String sparqlInstance = string1 + yourClass + string2;
		System.out.println(sparqlInstance);

		ResultSet resultsInstance = Result(sparqlInstance);
		return resultsInstance;
//		while (resultsInstance.hasNext()) {
//			QuerySolution solutionInstance = resultsInstance.next();
//			System.out.println("�ࣺ" + yourClass + "\n" + "    ��������ʵ����"
//					+ solutionInstance.get("?instanceLabel") + "\n");
//		}
	}

	public void checkProperty(String yourClass) {
		// ������ʵ��
		String string1 = "SELECT ?instanceLabel ?propertyID ?propertyChinese ?propertyFunction ?propertyTopic ?propertyBook ?propertyAntonym ?propertySynonyms ?propertyCommonUse ?propertyExtend ?propertyScene ?propertyExpand ?propertyVersion ?propertyUse ?propertyNcyclopedia ?propertyAssociat ?propertyPartsOfSpeeche ?propertyWordProperty ?propertyText ?propertyDifficulty WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."
				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"ID\"@zh."
				+ "?instance ?relationID ?propertyID."
				+ "?relationChinese <http://www.w3.org/2000/01/rdf-schema#label> \"���ĺ���\"@zh."
				+ "?instance ?relationChinese ?propertyChinese."
				+ "?relationFunction <http://www.w3.org/2000/01/rdf-schema#label> \"����-��������\"@zh."
				+ "?instance ?relationFunction ?propertyFunction."
				+ "?relationTopic <http://www.w3.org/2000/01/rdf-schema#label> \"����-����\"@zh."
				+ "?instance ?relationTopic ?propertyTopic."
				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"����\"@zh."
				+ "?instance ?relationBook ?propertyBook."
				+ "?relationAntonym <http://www.w3.org/2000/01/rdf-schema#label> \"�����\"@zh."
				+ "?instance ?relationAntonym ?propertyAntonym."
				+ "?relationSynonyms <http://www.w3.org/2000/01/rdf-schema#label> \"ͬ���\"@zh."
				+ "?instance ?relationSynonyms ?propertySynonyms."
				+ "?relationCommonUse <http://www.w3.org/2000/01/rdf-schema#label> \"����\"@zh."
				+ "?instance ?relationCommonUse ?propertyCommonUse."
				+ "?relationExtend <http://www.w3.org/2000/01/rdf-schema#label> \"��������\"@zh."
				+ "?instance ?relationExtend ?propertyExtend."
				+ "?relationScene <http://www.w3.org/2000/01/rdf-schema#label> \"�龳����\"@zh."
				+ "?instance ?relationScene ?propertyScene."
				+ "?relationExpand <http://www.w3.org/2000/01/rdf-schema#label> \"��չ\"@zh."
				+ "?instance ?relationExpand ?propertyExpand."
				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"�̲İ汾\"@zh."
				+ "?instance ?relationVersion ?propertyVersion."
				+ "?relationUse <http://www.w3.org/2000/01/rdf-schema#label> \"�÷�\"@zh."
				+ "?instance ?relationUse ?propertyUse."
				+ "?relationNcyclopedia <http://www.w3.org/2000/01/rdf-schema#label> \"�ٿ�\"@zh."
				+ "?instance ?relationNcyclopedia ?propertyNcyclopedia."
				+ "?relationAssociate <http://www.w3.org/2000/01/rdf-schema#label> \"����\"@zh."
				+ "?instance ?relationAssociate ?propertyAssociate."
				+ "?relationPartsOfSpeech <http://www.w3.org/2000/01/rdf-schema#label> \"����\"@zh."
				+ "?instance ?relationPartsOfSpeech ?propertyPartsOfSpeech."
				+ "?relationWordProperty <http://www.w3.org/2000/01/rdf-schema#label> \"��������\"@zh."
				+ "?instance ?relationWordProperty ?propertyWordProperty."
				+ "?relationText <http://www.w3.org/2000/01/rdf-schema#label> \"����ԭ��\"@zh."
				+ "?instance ?relationText ?propertyText."
				+ "?relationDifficulty <http://www.w3.org/2000/01/rdf-schema#label> \"�Ѷ�\"@zh."
				+ "?instance ?relationDifficulty ?propertyDifficulty." + "}";
		String sparqlInstance = string1 + yourClass + string2;
		// System.out.println(sparqlInstance);

		// Results from a query in a table-like manner for SELECT queries.
		// ��SELECT��ѯ��������еĲ�ѯ���
		// Each row corresponds to a set of bindings which fulfil the conditions
		// of the query.
		// ÿһ�ж�Ӧһ���󶨼�������ִ�в�ѯ����
		// Access to the results is by variable name.
		// ͨ�����������ʽ��
		ResultSet resultsInstance = Result(sparqlInstance);

		while (resultsInstance.hasNext()) {
			// QuerySolution next()
			// Moves onto the next result.
			// �ƶ����¸�result��
			QuerySolution solutionInstance = resultsInstance.next();

			System.out.println("�ࣺ" + yourClass + "\n" + "    ��������ʵ����"
					+ solutionInstance.get("?instanceLabel") + "\n"
					+ "    ��������ID��" + solutionInstance.get("?propertyID")
					+ "\n" + "    �����������ĺ��壺"
					+ solutionInstance.get("?propertyChinese") + "\n"
					+ "    ������������-�������"
					+ solutionInstance.get("?propertyFunction") + "\n"
					+ "    ������������-���⣺" + solutionInstance.get("?propertyTopic")
					+ "\n" + "    ��������������"
					+ solutionInstance.get("?propertyBook") + "\n"
					+ "    ������������ʣ�" + solutionInstance.get("?propertyAntonym")
					+ "\n" + "    ��������ͬ��ʣ�"
					+ solutionInstance.get("?propertySynonyms") + "\n"
					+ "    �����������ã�"
					+ solutionInstance.get("?propertyCommonUse") + "\n"
					+ "    ���������������䣺" + solutionInstance.get("?propertyExtend")
					+ "\n" + "    ���������龳���䣺"
					+ solutionInstance.get("?propertyScene") + "\n"
					+ "    ����������չ��" + solutionInstance.get("?propertyExpand")
					+ "\n" + "    ���������̲İ汾��"
					+ solutionInstance.get("?propertyVersion") + "\n"
					+ "    ���������÷���" + solutionInstance.get("?propertyUse")
					+ "\n" + "    ���������ٿƣ�"
					+ solutionInstance.get("?propertyNcyclopedia") + "\n"
					+ "    �����������룺"
					+ solutionInstance.get("?propertyAssociate") + "\n"
					+ "    �����������ԣ�"
					+ solutionInstance.get("?propertyPartsOfSpeech") + "\n"
					+ "    ���������������ԣ�"
					+ solutionInstance.get("?propertyWordProperty") + "\n"
					+ "    ������������ԭ�䣺" + solutionInstance.get("?propertyText")
					+ "\n" + "    ���������Ѷȣ�"
					+ solutionInstance.get("?propertyDifficulty"));
		}
	}

	
	private static ResultSet Result(String sparql) {
		// public static Query create(String queryString)
		// Create a SPARQL query from the given string.
		// �Ӹ�����string�д���һ��SPARQL��ѯ
		Query queryInstance = QueryFactory.create(sparql);
		// public static QueryExecution sparqlService(String service,Query
		// query)
		// Create a QueryExecution that will access a SPARQL service over HTTP
		// ����һ��QueryExecution��������HTTP�Ϸ���SPARQL����
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		// ResultSet execSelect()
		// Execute a SELECT query
		// ִ��һ��SELECT��ѯ
		// Important: The name of this method is somewhat of a misnomer in that
		// depending on the underlying implementation this typically does not
		// execute the SELECT query but rather answers a wrapper over an
		// internal data structure that can be used to answer the query. In
		// essence calling this method only returns a plan for executing this
		// query which only gets evaluated when you actually start iterating
		// over the results.
		ResultSet results = qexecInstance.execSelect();
		return results;
	}
}
