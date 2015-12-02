package AddDeleteModifyQuery.Delete.Impl;

import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateProcessor;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.vocabulary.RDF;

import AddDeleteModifyQuery.Delete.DeleteIndividual;

public class DeleteIndividualImpl implements DeleteIndividual {

	private static String UPDATE_SERVER = "http://127.0.0.1:3030/EnglishLearningDataset/update";

	public DeleteIndividualImpl() {

	}

	public void deleteInstanceProperty(String yourInstanceLabel, String property, String propertyValue) {

		if (yourInstanceLabel.contains("@")) {
			yourInstanceLabel = yourInstanceLabel.substring(0, yourInstanceLabel.indexOf("@"));
		}
		if (propertyValue.contains("@")) {
			propertyValue = propertyValue.substring(0, propertyValue.indexOf("@"));
		}

		String sparqlDeleteInstance = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstanceLabel
				+ "'@zh> <"
				+ property
				+ "> \""
				+ propertyValue
				+ "\"@zh}";
		executeSPARQL(sparqlDeleteInstance);

	}
	
	@Override
	public void deleteClassAndLabel(String yourInstanceLabel, String yourInstanceClass) {
		
		if (yourInstanceLabel.contains("@")) {
			yourInstanceLabel = yourInstanceLabel.substring(0, yourInstanceLabel.indexOf("@"));
		}
		if (yourInstanceClass.contains("@")) {
			yourInstanceClass = yourInstanceClass.substring(0, yourInstanceClass.indexOf("@"));
		}

		String sparqlDeleteInstanceClass = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstanceLabel
				+ "'@zh> <"
				+ RDF.type
				+ "> \""
				+ yourInstanceClass 
				+ "\"@zh}";
		executeSPARQL(sparqlDeleteInstanceClass);
		
		String sparqlDeleteInstanceLabel = "delete data{<http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31#'"
				+ yourInstanceLabel
				+ "'@zh> <"
				+ "http://www.w3.org/2000/01/rdf-schema#label"
				+ "> \""
				+ yourInstanceLabel 
				+ "\"@zh}";
		executeSPARQL(sparqlDeleteInstanceLabel);
		
	}

	// ÷¥––SPARQL”Ô—‘
	private static void executeSPARQL(String sparql) {
		UpdateRequest updateInstance = UpdateFactory.create(sparql);
		UpdateProcessor qexecInstance = UpdateExecutionFactory.createRemote(
				updateInstance, UPDATE_SERVER);
		qexecInstance.execute();
	}
}
