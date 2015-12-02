package AddDeleteModifyQuery.Query.Impl;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import AddDeleteModifyQuery.Query.QueryDependOnId;

public class QueryDependOnIdImpl implements QueryDependOnId {

	// ����fuseki���ݿ����ӵ�ַ/EnglishLearningDataset query
	public static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";

	public QueryDependOnIdImpl() {

	}

	public ResultSet checkPropertyDependOnId(String yourId) {

		if (yourId.contains("@")) {
			yourId = yourId.substring(0, yourId.indexOf("@"));
		}

		String string1 = "SELECT ?instanceLabel ?propertyClass ?propertyID ?propertyChinese ?propertyFunction ?propertyTopic ?propertyBook ?propertyAntonym ?propertySynonyms ?propertyCommonUse ?propertyExtend ?propertyScene ?propertyExpand ?propertyVersion ?propertyUse ?propertyNcyclopedia ?propertyAssociate ?propertyPartsOfSpeech ?propertyWordProperty ?propertyText ?propertyDifficulty ?relationID ?relationChinese ?relationFunction ?relationTopic ?relationBook ?relationAntonym ?relationSynonyms ?relationCommonUse ?relationExtend ?relationScene ?relationExpand ?relationVersion ?relationUse ?relationNcyclopedia ?relationAssociate ?relationPartsOfSpeech ?relationWordProperty ?relationText ?relationDifficulty WHERE{?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"����ID\"@zh. ?instance ?relationID \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."

				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?class <http://www.w3.org/2000/01/rdf-schema#label> ?propertyClass."

				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"����ID\"@zh."
				+ "?instance ?relationID ?propertyID FILTER regex(?propertyID, \""
				+ yourId
				+ "\")."

				+ "?relationChinese <http://www.w3.org/2000/01/rdf-schema#label> \"���ĺ���\"@zh."
				+ "?instance ?relationChinese ?propertyChinese FILTER regex(?propertyChinese, \""
				+ yourId
				+ "\")."

				+ "?relationFunction <http://www.w3.org/2000/01/rdf-schema#label> \"����-��������\"@zh."
				+ "?instance ?relationFunction ?propertyFunction FILTER regex(?propertyFunction, \""
				+ yourId
				+ "\")."

				+ "?relationTopic <http://www.w3.org/2000/01/rdf-schema#label> \"����-����\"@zh."
				+ "?instance ?relationTopic ?propertyTopic FILTER regex(?propertyTopic, \""
				+ yourId
				+ "\")."

				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"���ʲ���\"@zh."
				+ "?instance ?relationBook ?propertyBook FILTER regex(?propertyBook, \""
				+ yourId
				+ "\")."

				+ "?relationAntonym <http://www.w3.org/2000/01/rdf-schema#label> \"�����\"@zh."
				+ "?instance ?relationAntonym ?propertyAntonym FILTER regex(?propertyAntonym, \""
				+ yourId
				+ "\")."

				+ "?relationSynonyms <http://www.w3.org/2000/01/rdf-schema#label> \"ͬ���\"@zh."
				+ "?instance ?relationSynonyms ?propertySynonyms FILTER regex(?propertySynonyms, \""
				+ yourId
				+ "\")."

				+ "?relationCommonUse <http://www.w3.org/2000/01/rdf-schema#label> \"����\"@zh."
				+ "?instance ?relationCommonUse ?propertyCommonUse FILTER regex(?propertyCommonUse, \""
				+ yourId
				+ "\")."

				+ "?relationExtend <http://www.w3.org/2000/01/rdf-schema#label> \"��������\"@zh."
				+ "?instance ?relationExtend ?propertyExtend FILTER regex(?propertyExtend, \""
				+ yourId
				+ "\")."

				+ "?relationScene <http://www.w3.org/2000/01/rdf-schema#label> \"�龳����\"@zh."
				+ "?instance ?relationScene ?propertyScene FILTER regex(?propertyScene, \""
				+ yourId
				+ "\")."

				+ "?relationExpand <http://www.w3.org/2000/01/rdf-schema#label> \"��չ\"@zh."
				+ "?instance ?relationExpand ?propertyExpand FILTER regex(?propertyExpand, \""
				+ yourId
				+ "\")."

				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"���ʽ̲İ汾\"@zh."
				+ "?instance ?relationVersion ?propertyVersion FILTER regex(?propertyVersion, \""
				+ yourId
				+ "\")."

				+ "?relationUse <http://www.w3.org/2000/01/rdf-schema#label> \"�÷�\"@zh."
				+ "?instance ?relationUse ?propertyUse FILTER regex(?propertyUse, \""
				+ yourId
				+ "\")."

				+ "?relationNcyclopedia <http://www.w3.org/2000/01/rdf-schema#label> \"�ٿ�\"@zh."
				+ "?instance ?relationNcyclopedia ?propertyNcyclopedia FILTER regex(?propertyNcyclopedia, \""
				+ yourId
				+ "\")."

				+ "?relationAssociate <http://www.w3.org/2000/01/rdf-schema#label> \"����\"@zh."
				+ "?instance ?relationAssociate ?propertyAssociate FILTER regex(?propertyAssociate, \""
				+ yourId
				+ "\")."

				+ "?relationPartsOfSpeech <http://www.w3.org/2000/01/rdf-schema#label> \"����\"@zh."
				+ "?instance ?relationPartsOfSpeech ?propertyPartsOfSpeech FILTER regex(?propertyPartsOfSpeech, \""
				+ yourId
				+ "\")."

				+ "?relationWordProperty <http://www.w3.org/2000/01/rdf-schema#label> \"��������\"@zh."
				+ "?instance ?relationWordProperty ?propertyWordProperty FILTER regex(?propertyWordProperty, \""
				+ yourId
				+ "\")."

				+ "?relationText <http://www.w3.org/2000/01/rdf-schema#label> \"����ԭ��\"@zh."
				+ "?instance ?relationText ?propertyText FILTER regex(?propertyText, \""
				+ yourId
				+ "\")."

				+ "?relationDifficulty <http://www.w3.org/2000/01/rdf-schema#label> \"�Ѷ�\"@zh."
				+ "?instance ?relationDifficulty ?propertyDifficulty FILTER regex(?propertyDifficulty, \""
				+ yourId + "\")." + "}";

		String sparqlInstance = string1 + yourId + string2;
		// System.out.println(sparqlInstance);

		// Results from a query in a table-like manner for SELECT queries.
		// ��SELECT��ѯ����������еĲ�ѯ���
		// Each row corresponds to a set of bindings which fulfil the conditions
		// of the query.
		// ÿһ�ж�Ӧһ���󶨼�������ִ�в�ѯ����
		// Access to the results is by variable name.
		// ͨ�����������ʽ��
		ResultSet resultsInstance = Result(sparqlInstance);

		return resultsInstance;
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