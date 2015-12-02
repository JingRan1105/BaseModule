package AddDeleteModifyQuery.WriteOwl.WriteOwlImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import AddDeleteModifyQuery.Query.QueryDependOnId;
import AddDeleteModifyQuery.Query.QuerySentenceDependOnId;
import AddDeleteModifyQuery.Query.Impl.QueryDependOnIdImpl;
import AddDeleteModifyQuery.Query.Impl.QuerySentenceDependOnIdImpl;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

public class WriteOwlImpl {

	private static String SERVER = "http://localhost:3030/EnglishLearningDataset/query";
	private static String[] propertySPARQLValue = { "?propertyID",
			"?propertyChinese", "?propertyFunction", "?propertyTopic",
			"?propertyBook", "?propertyAntonym", "?propertySynonyms",
			"?propertyCommonUse", "?propertyExtend", "?propertyScene",
			"?propertyExpand", "?propertyVersion", "?propertyUse",
			"?propertyNcyclopedia", "?propertyAssociate",
			"?propertyPartsOfSpeech", "?propertyWordProperty", "?propertyText",
			"?propertyDifficulty" };
	private static String[] propertySPARQLName = { "?relationID",
			"?relationChinese", "?relationFunction", "?relationTopic",
			"?relationBook", "?relationAntonym", "?relationSynonyms",
			"?relationCommonUse", "?relationExtend", "?relationScene",
			"?relationExpand", "?relationVersion", "?relationUse",
			"?relationNcyclopedia", "?relationAssociate",
			"?relationPartsOfSpeech", "?relationWordProperty", "?relationText",
			"?relationDifficulty" };
	private static String[] sentencePropertySPARQLValue = { "?propertyAnswer",
			"?propertyID", "?propertyVersion", "?propertyBook",
			"?propertyScene", "?propertySentencePattern",
			"?propertyRelatedWords" };
	private static String[] sentencePropertySPARQLName = { "?relationAnswer",
			"?relationID", "?relationVersion", "?relationBook",
			"?relationScene", "?relationSentencePattern",
			"?relationRelatedWords" };
	private static int haveWriteToOwlNum = 0;

	// private static String[] property = { "���ĺ���@zh", "����-��������@zh", "����-����@zh",
	// "����@zh", "�����@zh", "ͬ���@zh", "����@zh", "��������@zh", "�龳����@zh",
	// "��չ@zh", "�̲İ汾@zh", "�÷�@zh", "�ٿ�@zh", "����@zh", "����@zh", "��������@zh",
	// "����ԭ��@zh", "�Ѷ�@zh" };

	public void writeBackToOwl() throws IOException {

		String[] allFile = new String[] { "OnlyClass.owl",
				"OnlyClassSentence.owl" };
		Model model = ModelFactory.createDefaultModel();
		OntModel ontModel = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_MEM, model);

		for (int whichOwl = 0; whichOwl < 2; whichOwl++) {

			File file = new File(allFile[whichOwl]);

			// ���ʱ�����뵽ģ����
			ReadToModel(file, ontModel, whichOwl);

			// �õ�Fuseki��һ���ж���������
			int totalFusekiNum = TotalFusekiNum();

			// for (int cycleTimes_10 = totalFusekiNum; cycleTimes_10 > 0;
			// cycleTimes_10 = cycleTimes_10 - 10) {
			// �������ʱ���
			ontModel = WriteModel(ontModel, whichOwl);

			// д�ص���owl�ļ���
			OutputStreamToOwl(file, ontModel);

//			haveWriteToOwlNum += 10;
//			System.out
//					.println("%%%%%%%%%%%%%%%%%%%%%%%%haveWriteToOwlNum%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"
//							+ haveWriteToOwlNum);

			// }

			ontModel.removeAll();
			haveWriteToOwlNum = 0;

		}
		model = null;// ����
		ontModel = null;// ����

	}

	// ���ʱ�����뵽ģ����
	private static void ReadToModel(File file, OntModel ontModel, int i) {
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			if (i == 0) {
				ontModel.read(
						in,
						"http://www.semanticweb.org/administrator/ontologies/2015/6/untitled-ontology-31",
						"RDF/XML-ABBREV");
			} else {
				ontModel.read(
						in,
						"http://www.semanticweb.org/administrator/ontologies/2015/8/untitled-ontology-85",
						"RDF/XML-ABBREV");
			}
			in.close();
			in = null;// ����

		} catch (FileNotFoundException e1) {// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �õ�Fuseki��һ���ж���������
	private static int TotalFusekiNum() {
		// String sparql =
		// "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}";
		String sparql = "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}";// �ҳ��С�ID�����Ե���Ԫ��
		ResultSet resultsInstance = Result(sparql);// �Ѳ�ѯ��������ʵ��?s��ID?o���ڽ������
		// System.out.println(resultsInstance);
		int totalNum = 0;
		// System.out.print(totalNum);
		// if (resultsInstance.hasNext()) {
		while (resultsInstance.hasNext()) {
			// System.out.print(totalNum);
			resultsInstance.next();
			totalNum++;
		}
		System.out.print(totalNum);
		return totalNum;
	}

	// �������ʱ���
	private static OntModel WriteModel(OntModel ontModel, int whichOwl) {

		// String sparql1 =
		// "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}limit 50 offset ";
		String sparql = "SELECT ?s ?o WHERE{?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o}";
		//String sparql2 = String.valueOf(haveWriteToOwlNum);
		//String sparql = sparql1 + sparql2;
		// Statement wordStatement = null;
		// Statement sentenceStatement = null;
		ResultSet resultsInstance = Result(sparql);// �Ѳ�ѯ��������ʵ��?s����?o���ڽ������

		Statement wordStatement = null;
		Statement sentenceStatement = null;
		// if (resultsInstance.hasNext()) {
		while (resultsInstance.hasNext()) {

			QuerySolution solutionInstance = resultsInstance.next();// ����ʵ��?s����?o

			System.out.println("ʵ����" + solutionInstance.get("?s") + "\n" + "�ࣺ"
					+ solutionInstance.get("?o"));

			String instanceURI = solutionInstance.get("?s").toString();
			String URINum = null;
			String yourInstance = null;
			if (instanceURI.contains("#")) {
				URINum = instanceURI.substring(instanceURI.indexOf("#") - 2,
						instanceURI.indexOf("#"));// �����Ҫ�洢����һ��owl�ļ��У��õ�31��85��
				if (instanceURI.contains("\'")) {
					yourInstance = solutionInstance
							.get("?s")
							.toString()
							.substring(
									solutionInstance.get("?s").toString()
											.indexOf("\'") + 1,
									solutionInstance.get("?s").toString()
											.lastIndexOf("\'"));// �����ʵ��label
				} else {
					continue;
				}
			} else {
				continue;
			}
			instanceURI = null;// ����

			// while (resultsFindHowMaySame.hasNext()) {// �м���ͬ������ѭ������
			// howManySame++;

			// QuerySolution solutionSameInstance =
			// resultsFindHowMaySame.next();

			ArrayList<OntProperty> propertyURI = new ArrayList<OntProperty>();
			if (whichOwl == 0) {// ������ǵ���owl������Ҫ�浽����owl�ļ���
				if (URINum.equals("31")) {
					
					// �ҳ����ʵ���м���ͬlabel��ʵ��
					//int howManySame = 0;
					String findHowMaySame1 = "SELECT ?propertyID ?instance WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";
					String findHowMaySame2 = "\"@zh. ?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"����ID\"@zh. ?instance ?relationID ?propertyID.}";
					String findHowMaySame = findHowMaySame1 + yourInstance
							+ findHowMaySame2;
					ResultSet resultsFindHowMaySame = Result(findHowMaySame);//���ͬLabel�Ĳ�ͬID
					findHowMaySame = null;// ����
					
					int i = 0;
					// �õ�Property���͵ĵ�URI
					Iterator alldatapry = ontModel.listDatatypeProperties();
					while (alldatapry.hasNext()) {
						OntProperty datapry = (OntProperty) alldatapry.next();
						// ����URI
						String dataprystr = datapry.toString();
						// System.out.print("����URI��" + dataprystr + "\n");

						propertyURI.add(datapry);
						i++;
						datapry = null;// ����
					}
					alldatapry = null;// ����

					// System.out.println(propertyURI);

					while (resultsFindHowMaySame.hasNext()) {// �м���ͬ������ѭ������
						//howManySame++;

						QuerySolution solutionSameInstance = resultsFindHowMaySame
								.next();
						System.out.println("ID��������"
								+ solutionSameInstance.get("?propertyID")
										.toString());

						// ����ID���ò��ң��õ������
						QueryDependOnId queryDependOnId = new QueryDependOnIdImpl();

						ResultSet resultsInstanceProperty = queryDependOnId
								.checkPropertyDependOnId(solutionSameInstance
										.get("?propertyID").toString());

						while (resultsInstanceProperty.hasNext()) {
							QuerySolution solutionInstanceProperty = resultsInstanceProperty
									.next();

							// System.out.println(propertyURI.size());
							// System.out.println(solutionInstance.get("?s"));
							for (int propertyNum = 0; propertyNum < propertySPARQLValue.length; propertyNum++) {// ��ӵ�������
								for (i = 0; i < propertyURI.size(); i++) {
									// System.out
									// .println("SPARQL��ȡ��property��������"
									// + solutionInstanceProperty
									// .get(propertySPARQLName[propertyNum])
									// .toString() + "\n������property��������" +
									// propertyURI.get(i).toString());
									if (solutionInstanceProperty
											.get(propertySPARQLName[propertyNum])
											.toString()
											.equals(propertyURI.get(i)
													.toString())) {// ������property��URI���жԱȣ���һ�����õ���Ӧ��property
										Statement wordPropertyStatement = ontModel
												.createStatement(
														(Resource) solutionInstance
																.get("?s"),
														propertyURI.get(i),
														solutionInstanceProperty
																.get(propertySPARQLValue[propertyNum]));// ������Ԫ��

										ontModel.add(wordPropertyStatement);// ��Ԫ����ӵ�ģ����
										break;
									}
								}
							}
							wordStatement = ontModel.createStatement(
									(Resource) solutionInstance.get("?s"),
									RDF.type, solutionInstance.get("?o"));
							ontModel.add(wordStatement);
						}
						queryDependOnId = null;// ����
						resultsInstanceProperty = null;// ����
					}
					System.out.println("����" + yourInstance + "��Ԫ��ɹ�" + "\n");

				}
				wordStatement = null;// ����
				yourInstance = null;// ����
				propertyURI = null;// ����
				System.gc();

			} else {// ������Ǿ���owl������Ҫ�洢������owl�ļ���
				if (URINum.equals("85")) {

					// �ҳ����ʵ���м���ͬlabel��ʵ��
					//int howManySame = 0;
					String findHowMaySame1 = "SELECT ?propertyID ?instance WHERE{?instance <http://www.w3.org/2000/01/rdf-schema#label> \"";
					String findHowMaySame2 = "\"@zh. ?relationID <http://www.w3.org/2000/01/rdf-schema#label> \"����ID\"@zh. ?instance ?relationID ?propertyID.}";
					String findHowMaySame = findHowMaySame1 + yourInstance
							+ findHowMaySame2;
					ResultSet resultsFindHowMaySame = Result(findHowMaySame);
					findHowMaySame = null;// ����

					int j = 0;
					// �õ�Property���͵ĵ�URI

					// �г����е���������
					Iterator alldatapry = ontModel.listDatatypeProperties();
					while (alldatapry.hasNext()) {
						OntProperty datapry = (OntProperty) alldatapry.next();
						// ����URI
						String dataprystr = datapry.toString();
						// System.out.print("����URI��" + dataprystr +
						// "\n");

						propertyURI.add(datapry);
						j++;
						datapry = null;// ����
					}
					alldatapry = null;// ����

					while (resultsFindHowMaySame.hasNext()) {// �м���ͬ������ѭ������
						//howManySame++;

						QuerySolution solutionSameInstance = resultsFindHowMaySame
								.next();
						System.out.println("ID��������"
								+ solutionSameInstance.get("?propertyID")
										.toString());

						// ����ID���ò��ң��õ������
						QuerySentenceDependOnId querySentenceDependOnId = new QuerySentenceDependOnIdImpl();
						ResultSet resultsInstanceProperty = querySentenceDependOnId
								.checkSentencePropertyDependOnId(solutionSameInstance
										.get("?propertyID").toString());

						while (resultsInstanceProperty.hasNext()) {
							QuerySolution solutionInstanceProperty = resultsInstanceProperty
									.next();

							// System.out.println(propertyURI.size());
							//System.out.println(solutionInstance.get("?s"));
							for (int propertyNum = 0; propertyNum < sentencePropertySPARQLValue.length; propertyNum++) {
								for (j = 0; j < propertyURI.size(); j++) {
									if (solutionInstanceProperty
											.get(sentencePropertySPARQLName[propertyNum])
											.toString()
											.equals(propertyURI.get(j)
													.toString())) {
										Statement sentencePropertyStatement = ontModel
												.createStatement(
														(Resource) solutionInstance
																.get("?s"),
														propertyURI.get(j),
														solutionInstanceProperty
																.get(sentencePropertySPARQLValue[propertyNum]));

										ontModel.add(sentencePropertyStatement);
										break;
									}
								}
							}
							sentenceStatement = ontModel.createStatement(
									(Resource) solutionInstance.get("?s"),
									RDF.type,
									solutionInstance.get("?o"));
							ontModel.add(sentenceStatement);
						}
						querySentenceDependOnId = null;// ����
						resultsInstanceProperty = null;// ����
					}
					System.out.println("����" + yourInstance + "��Ԫ��ɹ�" + "\n");

				}
				sentenceStatement = null;// ����
				yourInstance = null;// ����
				propertyURI = null;// ����
				System.gc();

			}
		}

		// }
		// } else {
		// System.out.println("֪ʶ�������û�д�ʵ��");
		// }
		// resultsInstance = null;// ����
		// System.gc();

		return ontModel;
	}

	// д�ص���owl�ļ���
	private static void OutputStreamToOwl(File file, OntModel ontModel) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			ontModel.writeAll(out, "RDF/XML");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out
				.println("###################Fuseki���ݿ�д����ϣ�����###################");
	}

	private static ResultSet Result(String sparql) {
		Query queryInstance = QueryFactory.create(sparql);
		QueryExecution qexecInstance = QueryExecutionFactory.sparqlService(
				SERVER, queryInstance);
		ResultSet results = qexecInstance.execSelect();
		return results;
	}
}
