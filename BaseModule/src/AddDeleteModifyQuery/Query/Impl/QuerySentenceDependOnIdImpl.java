package AddDeleteModifyQuery.Query.Impl;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

import AddDeleteModifyQuery.Query.QuerySentenceDependOnId;

public class QuerySentenceDependOnIdImpl implements QuerySentenceDependOnId {

	// ����fuseki���ݿ����ӵ�ַ/EnglishLearningDataset query
	public static String SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/query";

	public QuerySentenceDependOnIdImpl() {

	}

	public ResultSet checkSentencePropertyDependOnId(String yourId) {
		
		if (yourId.contains("@")) {
			yourId = yourId.substring(0, yourId.indexOf("@"));
		}
		
		// ������ʵ������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������
		String string1 = "SELECT ?instanceLabel ?propertyClass ?propertyAnswer ?propertyID ?propertyVersion ?propertyBook ?propertyScene ?propertySentencePattern ?propertyRelatedWords ?relationAnswer ?relationID ?relationVersion ?relationBook ?relationScene ?relationSentencePattern ?relationRelatedWords WHERE{ ?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"����ID\"@zh. ?instance ?relationID \"";
		String string2 = "\"@zh."
				+ "?instance <http://www.w3.org/2000/01/rdf-schema#label> ?instanceLabel."

				+ "?instance <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?class."
				+ "?class <http://www.w3.org/2000/01/rdf-schema#label> ?propertyClass."

				+ "?relationAnswer <http://www.w3.org/2000/01/rdf-schema#label> \"�ش�\"@zh."
				+ "?instance ?relationAnswer ?propertyAnswer FILTER regex(?propertyAnswer, \""
				+ yourId
				+ "\")."

				+ "?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"����ID\"@zh."
				+ "?instance ?relationID ?propertyID FILTER regex(?propertyID, \""
				+ yourId
				+ "\")."

				+ "?relationVersion <http://www.w3.org/2000/01/rdf-schema#label> \"���ӽ̲İ汾\"@zh."
				+ "?instance ?relationVersion ?propertyVersion FILTER regex(?propertyVersion, \""
				+ yourId
				+ "\")."

				+ "?relationBook <http://www.w3.org/2000/01/rdf-schema#label> \"���Ӳ���\"@zh."
				+ "?instance ?relationBook ?propertyBook FILTER regex(?propertyBook, \""
				+ yourId
				+ "\")."

				+ "?relationScene <http://www.w3.org/2000/01/rdf-schema#label> \"�龳�Ի�\"@zh."
				+ "?instance ?relationScene ?propertyScene FILTER regex(?propertyScene, \""
				+ yourId
				+ "\")."

				+ "?relationSentencePattern <http://www.w3.org/2000/01/rdf-schema#label> \"��Ҫ����\"@zh."
				+ "?instance ?relationSentencePattern ?propertySentencePattern FILTER regex(?propertySentencePattern, \""
				+ yourId
				+ "\")."

				+ "?relationRelatedWords <http://www.w3.org/2000/01/rdf-schema#label> \"��ص���\"@zh."
				+ "?instance ?relationRelatedWords ?propertyRelatedWords FILTER regex(?propertyRelatedWords, \""
				+ yourId + "\")." + "}";

		String sparqlInstance = string1 + yourId + string2;
		// System.out.println(sparqlInstance);

		// Results from a query in a table-like manner for SELECT queries.
		// ��SELECT��ѯ��������еĲ�ѯ���
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
