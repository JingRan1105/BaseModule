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

	// 添加单词父类和Label-----------------------------------------------------------------------------------------------------------------------------
	public void addInstance(String yourClass, String yourInstance) {

		QuerySolution solutionClass = findClassURL(yourClass);

		// 添加单词实例父类
		addInstanceClass(yourInstance, solutionClass, "31");

		// 添加单词实例Label
		addInstanceLable(yourInstance, "31");

		System.out.println("单词类和Label添加成功");
	}

	// 添加单词属性-----------------------------------------------------------------------------------------------------------------------------------
	public void addProperty(String propertyLabel, String yourInstance,
			String yourProperty) {

		if (propertyLabel == "单词ID") {
			ID = yourProperty;
			// 查找单词属性url
			QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

			// 添加单词属性
			addIDProperty(yourInstance, solutionProperty, yourProperty, "31");

			System.out.println(yourInstance + propertyLabel + "属性添加成功");
		} else {
			// 查找单词属性url
			QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

			// 添加单词属性
			addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
					"31");

			System.out.println(yourInstance + propertyLabel + "属性添加成功");
		}
	}

	// 添加句子实例---------------------------------------------------------------------------------------------------------------------------------
	public void addSentenceInstance(String yourClass, String yourInstance) {
		// 查找句子类的url
		QuerySolution solutionClass = findClassURL(yourClass);

		// 添加句子实例父类
		addInstanceClass(yourInstance, solutionClass, "85");

		// 添加句子实例Label
		addInstanceLable(yourInstance, "85");
		System.out.println("句子Label添加成功");
	}

	// 添加句子属性-----------------------------------------------------------------------------------------------------------------------------------
	public void addSentenceProperty(String propertyLabel, String yourInstance,
			String yourProperty) {
		// 查找句子属性url
		QuerySolution solutionProperty = queryPropertyURL(propertyLabel);

		if (propertyLabel == "句子ID") {

			ID = yourProperty;
			// 添加句子属性
			addIDProperty(yourInstance, solutionProperty, yourProperty, "85");

			System.out.println(yourInstance + propertyLabel + "属性添加成功");
		} else {
			// 添加句子属性
			addOthersProperty(yourInstance, solutionProperty, yourProperty, ID,
					"85");

			System.out.println(yourInstance + propertyLabel + "属性添加成功");
		}
	}

	// 查找类的url
	private static QuerySolution findClassURL(String yourClass) {
		String sparqlClass = "SELECT ?class WHERE{?class <http://www.w3.org/2000/01/rdf-schema#label> \""
				+ yourClass + "\"@zh.}";
		ResultSet resultsClass = Result(sparqlClass);
		QuerySolution solutionClass = null;
		while (resultsClass.hasNext() == true)
			solutionClass = resultsClass.next();// Moves onto the next result.
		return solutionClass;
	}

	// 添加实例父类
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

	// 添加实例Label
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

	// 查找属性url
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

	// 添加ID属性
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

	// 添加除ID的其他属性
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

	// 根据SPARQL语言查询，并返回结果集
	private static ResultSet Result(String sparql) {
		Query queryInstance = QueryFactory.create(sparql);
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		ResultSet results = qexecInstance.execSelect();
		return results;
	}

	// 执行SPARQL语言
	private static void executeSPARQL(String sparql) {
		UpdateRequest updateInstance = UpdateFactory.create(sparql);
		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
				updateInstance, UPDATE_SERVER);
		qexecInstance.execute();
	}
}
