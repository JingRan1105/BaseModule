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
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class AddIndividualAndPropertyImpl implements AddIndividualAndProperty {

	// private static String UPDATE_SERVER =
	// "http://127.0.0.1:3030/EnglishLearningDataset/update";

	private static String SERVER = "http://localhost:3030/EnglishLearningDataset/query";
	private static String UPDATE_SERVER = "http://localhost:3030/EnglishLearningDataset/update";
	private static String ID = null;

	// ��ӵ��ʸ���-----------------------------------------------------------------------------------------------------------------------------
	public void addClass(String yourClass, String yourInstance) {
		if (yourClass.contains("@")) {
			yourClass = subStringManag(yourClass);
		}
		if (yourInstance.contains("@")) {
			yourInstance = subStringManag(yourInstance);
		}

		QuerySolution solutionClass = findClassURL(yourClass);

		// ��ӵ���ʵ������
		addInstanceClass(yourInstance, solutionClass, "6", "31");

		System.out.println("��������ӳɹ�");
	}

	// ��ӵ���Label-----------------------------------------------------------------------------------------------------------------------------
	public void addLabel(String yourInstance) {

		if (yourInstance.contains("@")) {
			yourInstance = subStringManag(yourInstance);
		}

		// ��ӵ���ʵ��Label
		addInstanceLable(yourInstance, "6", "31");

		System.out.println("����Label��ӳɹ�");
	}

	// ��ӵ�������-----------------------------------------------------------------------------------------------------------------------------------
	public void addProperty(String propertyLabel, String yourInstance,
			String yourProperty) {
		if (yourInstance.contains("@")) {
			yourInstance = subStringManag(yourInstance);
		}
		if (yourProperty.contains("@")) {
			yourProperty = subStringManag(yourProperty);
			if (yourProperty.contains("(" + ID + ")")) {
				yourProperty = yourProperty
						.substring(yourProperty.indexOf(")"));
			}
		}

		if (propertyLabel.equals("����ID")) {
			ID = yourProperty;
			// ���ҵ�������url
			QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

			// ��ӵ�������
			addIDProperty(yourInstance, solutionProperty, yourProperty, "6",
					"31");

			System.out.println(yourInstance + propertyLabel + "������ӳɹ�");
		} else {
			// ���ҵ�������url
			QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

			// ��ӵ�������
			addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
					"6", "31");

			System.out.println(yourInstance + propertyLabel + "������ӳɹ�");
		}
	}

	// Ϊ�޸ĵ�����Ƶ���ӷ���-----------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void addPropertyForModify(String propertyLabel, String yourInstance,
			String yourProperty, String yourID) {
		ID = yourID;
		addProperty(propertyLabel, yourInstance, yourProperty);
	}

	// ��Ӿ�����---------------------------------------------------------------------------------------------------------------------------------
	public void addSentenceClass(String yourClass, String yourInstance) {

		if (yourClass.contains("@")) {
			yourClass = subStringManag(yourClass);
		}
		if (yourInstance.contains("@")) {
			yourInstance = subStringManag(yourInstance);
		}

		// ���Ҿ������url
		QuerySolution solutionClass = findClassURL(yourClass);

		// ��Ӿ���ʵ������
		addInstanceClass(yourInstance, solutionClass, "8", "85");

		System.out.println("���Ӹ�����ӳɹ�");
	}

	// ��Ӿ���Label---------------------------------------------------------------------------------------------------------------------------------
	public void addSentenceLabel(String yourInstance) {

		if (yourInstance.contains("@")) {
			yourInstance = subStringManag(yourInstance);
		}

		// ��Ӿ���ʵ��Label
		addInstanceLable(yourInstance, "8", "85");

		System.out.println("����Label��ӳɹ�");
	}

	// ��Ӿ�������-----------------------------------------------------------------------------------------------------------------------------------
	public void addSentenceProperty(String propertyLabel, String yourInstance,
			String yourProperty) {

		if (yourInstance.contains("@")) {
			yourInstance = subStringManag(yourInstance);
		}
		if (yourProperty.contains("@")) {
			yourProperty = subStringManag(yourProperty);
			if (yourProperty.contains("(" + ID + ")")) {
				yourProperty = yourProperty
						.substring(yourProperty.indexOf(")"));
			}
		}

		// ���Ҿ�������url
		QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

		if (propertyLabel.equals("����ID")) {

			ID = yourProperty;
			// ��Ӿ�������
			addIDProperty(yourInstance, solutionProperty, yourProperty, "8",
					"85");

			System.out.println(yourInstance + propertyLabel + "������ӳɹ�");
		} else {
			// ��Ӿ�������
			addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
					"8", "85");

			System.out.println(yourInstance + propertyLabel + "������ӳɹ�");
		}
	}

	// Ϊ�޸ľ�����Ƶ���ӷ���-----------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void addSentencePropertyForModify(String propertyLabel,
			String yourInstance, String yourProperty, String yourID) {
		if (yourInstance.contains("@")) {
			yourInstance = subStringManag(yourInstance);
		}
		ID = yourID;
		addSentenceProperty(propertyLabel, yourInstance, yourProperty);
	}

	// ��ӵȼ۹�ϵsameAs
	@Override
	public void addRelationSameAs(String question1, String question2) {
		addSameAs(question1, question2);
	}

	// ��̬����-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// �����ַ���
	public static String subStringManag(String string) {
		string = string.substring(0, string.indexOf("@"));
		return string;
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
			QuerySolution solutionClass, String flag1, String flag2) {
		String sparqlAddInstance = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/"
				+ flag1
				+ "/untitled-ontology-"
				+ flag2
				+ "#'"
				+ yourInstance
				+ "'@zh> <"
				+ RDF.type
				+ "> <"
				+ solutionClass.get("?class")
				+ ">}";
		executeSPARQL(sparqlAddInstance);
	}

	// ���ʵ��Label
	private static void addInstanceLable(String yourInstance, String flag1,
			String flag2) {
		String sparqlAddInstanceLabel = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/"
				+ flag1
				+ "/untitled-ontology-"
				+ flag2
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
			QuerySolution solutionProperty, String yourProperty, String flag1,
			String flag2) {
		String stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/"
				+ flag1
				+ "/untitled-ontology-"
				+ flag2
				+ "#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?Property")
				+ "> \""
				+ yourProperty + "\"@zh.}";

		executeSPARQL(stringProperty);
	}

	// ��ӳ�ID����������
	private static void addOthersProperty(String yourInstance,
			QuerySolution solutionProperty, String yourProperty, String wordID,
			String flag1, String flag2) {
		String stringProperty = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/"
				+ flag1
				+ "/untitled-ontology-"
				+ flag2
				+ "#'"
				+ yourInstance
				+ "'@zh> <"
				+ solutionProperty.get("?Property")
				+ "> \"("
				+ wordID + ")" + yourProperty + "\"@zh.}";

		executeSPARQL(stringProperty);
	}

	// ��ӵȼ۹�ϵ
	private static void addSameAs(String question1, String question2) {
		String sparqlAddSameAs = "insert data{<http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85#'"
				+ question1
				+ "'@zh> <"
				+ OWL.sameAs
				+ "> <http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85#'"
				+ question2 + "'@zh>.}";
		executeSPARQL(sparqlAddSameAs);
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
