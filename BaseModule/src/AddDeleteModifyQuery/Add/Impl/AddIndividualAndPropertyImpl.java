package AddDeleteModifyQuery.Add.Impl;

import AddDeleteModifyQuery.Add.AddIndividualAndProperty;

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

public class AddIndividualAndPropertyImpl implements AddIndividualAndProperty {

	// private static String UPDATE_SERVER =
	// "http://127.0.0.1:3030/EnglishLearningDataset/update";

	private static String SERVER = "http://localhost:3030/EnglishLearningDataset/query";
	private static String UPDATE_SERVER = "http://localhost:3030/EnglishLearningDataset/update";
	private static String ID = null;

	// ��ӵ��ʸ����Label-----------------------------------------------------------------------------------------------------------------------------
	public void addInstance(String yourClass, String yourInstance) {

		QuerySolution solutionClass = findClassURL(yourClass);

		// ��ӵ���ʵ������
		addInstanceClass(yourInstance, solutionClass, "31");

		// ��ӵ���ʵ��Label
		addInstanceLable(yourInstance, "31");

		System.out.println("�������Label��ӳɹ�");
	}

	// ��ӵ�������-----------------------------------------------------------------------------------------------------------------------------------
	public void addProperty(String propertyLabel, String yourInstance,
			String yourProperty) {

		if (propertyLabel == "����ID") {
			ID = yourProperty;
			// ���ҵ�������url
			QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

			// ��ӵ�������
			addIDProperty(yourInstance, solutionProperty, yourProperty, "31");

			System.out.println(yourInstance + propertyLabel + "������ӳɹ�");
		} else {
			// ���ҵ�������url
			QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

			// ��ӵ�������
			addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
					"31");

			System.out.println(yourInstance + propertyLabel + "������ӳɹ�");
		}
	}

	// ��Ӿ���ʵ��---------------------------------------------------------------------------------------------------------------------------------
	public void addSentenceInstance(String yourClass, String yourInstance) {
		// ���Ҿ������url
		QuerySolution solutionClass = findClassURL(yourClass);

		// ��Ӿ���ʵ������
		addInstanceClass(yourInstance, solutionClass, "85");

		// ��Ӿ���ʵ��Label
		addInstanceLable(yourInstance, "85");
		System.out.println("����Label��ӳɹ�");
	}

	// ��Ӿ�������-----------------------------------------------------------------------------------------------------------------------------------
	public void addSentenceProperty(String propertyLabel, String yourInstance,
			String yourProperty) {
		// ���Ҿ�������url
		QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

		if (propertyLabel == "����ID") {

			ID = yourProperty;
			// ��Ӿ�������
			addIDProperty(yourInstance, solutionProperty, yourProperty, "85");

			System.out.println(yourInstance + propertyLabel + "������ӳɹ�");
		} else {
			// ��Ӿ�������
			addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
					"85");

			System.out.println(yourInstance + propertyLabel + "������ӳɹ�");
		}
	}

	// �������url
	private static QuerySolution findClassURL(String yourClass) {
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext() == true)
			solutionClass = resultsClass.next();// Moves onto the next result.
		return solutionClass;
	}

	// ���ʵ������
	private static void addInstanceClass(String yourInstance,
			QuerySolution solutionClass, String flag) {
		String sparqlAddInstance = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-"
				+ flag
				+ "#'"
				+ yourInstance
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class") + ">}";
		executeSPARQL(sparqlAddInstance);
	}

	// ���ʵ��Label
	private static void addInstanceLable(String yourInstance, String flag) {
		String sparqlAddInstanceLabel = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-"
				+ flag
				+ "#'"
				+ yourInstance
				+ "'@zh>"
				+ "<http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourInstance + "\"@zh.}";
		executeSPARQL(sparqlAddInstanceLabel);
	}

	// ��������url
	private static QuerySolution queryPropertyURL(String propertyLabel) {
		String sparqlProperty = "SELECT ?Property WHERE{?Property <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ propertyLabel + "\"@zh.}";
		ResultSet results = Result(sparqlProperty);
		QuerySolution solutionProperty = null;
		while (results.hasNext()) {
			solutionProperty = results.next();
		}
		return solutionProperty;
	}

	// ���ID����
	private static void addIDProperty(String yourInstance,
			QuerySolution solutionProperty, String yourProperty, String flag) {
		String stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-"
				+ flag
				+ "#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?Property")
				+ "> \""
				+ yourProperty
				+ "\"@zh.}";

		executeSPARQL(stringProperty);
	}

	// ��ӳ�ID����������
	private static void addOthersProperty(String yourInstance,
			QuerySolution solutionProperty, String yourProperty, String wordID,
			String flag) {
		String stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-"
				+ flag
				+ "#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?Property")
				+ "> \"("
				+ wordID
				+ ")"
				+ yourProperty + "\"@zh.}";

		executeSPARQL(stringProperty);
	}

	// ����SPARQL���Բ�ѯ�������ؽ����
	private static ResultSet Result(String sparql) {
		Query queryInstance = QueryFactory.create(sparql);
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		ResultSet results = qexecInstance.execSelect();
		return results;
	}

	// ִ��SPARQL����
	private static void executeSPARQL(String sparql) {
		UpdateRequest updateInstance = UpdateFactory.create(sparql);
		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
				updateInstance, UPDATE_SERVER);
		qexecInstance.execute();
	}
}
