package MyReasoner.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

import MyReasoner.MyReasoner;

public class MyReasonerImpl implements MyReasoner {
	
	public MyReasonerImpl(){
		
	}

	@Override
	public InfModel ReasonSameAs() {
		Model model = ModelFactory.createDefaultModel();
		OntModel ontModel = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_MEM, model);
		File file = new File("OnlyClassSentence.owl");

		// ���ʱ�����뵽ģ����
		ReadToModel(file, ontModel);

		String rules = "[Rule: (?x1 ?p ?y),(?x1 owl:sameAs ?x2) ->(?x2 ?p ?y)]";
		GenericRuleReasoner reasoner = new GenericRuleReasoner(
				Rule.parseRules(rules)); // �ѱ�д�Ĺ������Jena2 �����������ȥ
		reasoner.setMode(GenericRuleReasoner.FORWARD_RETE);// ���û���Rete�㷨��ǰ��������ģʽ
		InfModel inf = ModelFactory.createInfModel(reasoner, ontModel);

		return inf;
	}

	// ���ʱ�����뵽ģ����
	private static void ReadToModel(File file, OntModel ontModel) {
		FileInputStream in;
		try {
			in = new FileInputStream(file);

			ontModel.read(
					in,
					"http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85",
					"RDF/XML-ABBREV");

			in.close();
			in = null;// ����

		} catch (FileNotFoundException e1) {// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
