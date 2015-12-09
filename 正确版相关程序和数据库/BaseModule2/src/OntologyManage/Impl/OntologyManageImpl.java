package OntologyManage.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

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
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import AddDeleteModifyQuery.Add.AddIndividualAndProperty;
import AddDeleteModifyQuery.Add.Impl.AddIndividualAndPropertyImpl;
import AddDeleteModifyQuery.Delete.DeleteIndividual;
import AddDeleteModifyQuery.Delete.Impl.DeleteIndividualImpl;
import AddDeleteModifyQuery.Query.QueryWithManyWays;
import AddDeleteModifyQuery.Query.Impl.QueryWithManyWaysImpl;
import AddDeleteModifyQuery.WriteOwl.WriteOwl;
import AddDeleteModifyQuery.WriteOwl.Impl.WriteOwlImpl;
import MyReasoner.MyReasoner;
import MyReasoner.Impl.MyReasonerImpl;
import OntologyManage.OntologyManage;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class OntologyManageImpl implements OntologyManage {

	private static String[] propertyLabel = new String[] { "����ID", "����",
			"����-��������", "����-����", "Hownet�еĸ���", "����", "��������", "���ĺ���", "���ʽ̲İ汾",
			"���ʲ���", "�Ѷ�", "����ԭ��", "�龳����", "����", "ͬ���", "�����", "��չ", "�ٿ�", "�÷�",
			"��������", "����" };
	private static String[] propertySPARQLValue = { "?propertyID",
			"?instanceLabel", "?propertyFunction", "?propertyTopic",
			"?propertyClass", "?propertyPartsOfSpeech",
			"?propertyWordProperty", "?propertyChinese", "?propertyVersion",
			"?propertyBook", "?propertyDifficulty", "?propertyText",
			"?propertyScene", "?propertyAssociate", "?propertyAntonym",
			"?propertySynonyms", "?propertyExtend", "?propertyNcyclopedia",
			"?propertyUse", "?propertyExpand", "?propertyCommonUse" };
	private static String[] propertyRelation = { "?relationID",
			"?relationInstance", "?relationFunction", "?relationTopic",
			"?relationClass", "?relationPartsOfSpeech",
			"?relationWordProperty", "?relationChinese", "?relationVersion",
			"?relationBook", "?relationDifficulty", "?relationText",
			"?relationScene", "?relationAssociate", "?relationAntonym",
			"?relationSynonyms", "?relationExtend", "?relationNcyclopedia",
			"?relationUse", "?relationExpand", "?relationCommonUse" };
	private static String[] sentencePropertyLabel = new String[] { "����ID",
			"���ӽ̲İ汾", "���Ӳ���", "����", "�������", "�ش�", "�龳�Ի�", "��Ҫ����", "��ص���" };
	private static String[] sentencePropertySPARQLValue = { "?propertyID",
			"?propertyVersion", "?propertyBook", "?propertyClass",
			"?instanceLabel", "?propertyAnswer", "?propertyScene",
			"?propertySentencePattern", "?propertyRelatedWords" };
	private static String[] sentencePropertyRelation = { "?relationID",
			"?relationVersion", "?relationBook", "?relationClass",
			"?instanceLabel", "?relationAnswer", "?relationScene",
			"?relationSentencePattern", "?relationRelatedWords" };

	// ���캯��
	public OntologyManageImpl() {

	}

	public static HashMap<String, Vector> ALLWORDS;
	public static HashMap<String, Vector> ALLMEANS;

	private static AddIndividualAndProperty addIndividualAndProperty = new AddIndividualAndPropertyImpl();
	private static DeleteIndividual deleteIndividual = new DeleteIndividualImpl();;
	private static QueryWithManyWays queryWithManyWays = new QueryWithManyWaysImpl();
	private static WriteOwl writeOwl = new WriteOwlImpl();
	private static MyReasoner myReasoner = new MyReasonerImpl();

	@Override
	public void Add(String[] parameter) {

		// ��ӵ��ʸ����Label
		addIndividualAndProperty.addClass(parameter[4], parameter[1]);
		addIndividualAndProperty.addLabel(parameter[1]);

		// ��ӵ�������
		int i = 0;
		for (i = 0; i < parameter.length; i++) {
			System.out.println(i + parameter[i]);
			if (i == 1 || i == 4) {

			} else {
				// ����public void addProperty(String propertyLabel, String
				// yourInstance,String yourProperty)
				addIndividualAndProperty.addProperty(propertyLabel[i],
						parameter[1], parameter[i]);
			}
		}

	}

	@Override
	public void AddSentence(String[] parameter) {

		// ��ӵ��ʸ����Label
		addIndividualAndProperty.addSentenceClass(parameter[4], parameter[3]);
		addIndividualAndProperty.addSentenceLabel(parameter[3]);

		// ��ӵ�������
		int i = 0;
		for (i = 0; i < parameter.length; i++) {
			System.out.println(i + parameter[i]);
			if (i == 3 || i == 4) {

			} else {
				// ����public void addProperty(String propertyLabel, String
				// yourInstance,String yourProperty)
				addIndividualAndProperty.addSentenceProperty(
						sentencePropertyLabel[i], parameter[3], parameter[i]);
			}
		}
	}

	@Override
	public void AddBatch(InputStream yourPath) throws BiffException,
			IOException {
		List<String[]> list = readExcel(yourPath);

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("Ҫ��ӵ�Excel�е�����" + str[j]);
			}

			// ���Լ���excel��Ҫע�͵���������
			String yourClass = null;
			yourClass = str[4];
			System.out.println("������ӵ���:" + yourClass);

			String yourInstance = null;
			yourInstance = str[1];
			System.out.println("������ӵ�ʵ��:" + yourInstance);

			// ���Լ���excel��Ҫע�͵�����
			if (yourClass.equals("���Ȳ���Prot��g������д��")) {

			} else {
				addIndividualAndProperty.addClass(yourClass, yourInstance);
				addIndividualAndProperty.addLabel(yourInstance);
				String yourProperty;
				for (int k = 0; k < 21; k++) {
					if (propertyLabel[k].equals("����")
							|| propertyLabel[k].equals("Hownet�еĸ���")) {
						// ��Ϊ�����ʡ���Hownet�еĸ��ࡱʱ��ʲô������
					} else {
						yourProperty = null;
						yourProperty = str[k];
						addIndividualAndProperty.addProperty(propertyLabel[k],
								yourInstance, yourProperty);
					}
				}
			}
			// ������

			// ���Լ���excelҪע�͵�����
			// String yourClass = findDEF(yourInstance);
			// System.out.println("��ѡ��ĸ��ࣨ���أ���" + yourClass);

			// if (yourClass == null) {
			// System.out.println(yourInstance + "�ĸ��಻��HowNet��");
			// } else {
			// addIndividualAndProperty.addInstance(yourClass, yourInstance);
			// }
			// ������
		}
		System.out.println("�������ʲ���ɹ���");
	}

	@Override
	public void AddSentenceBatch(InputStream yourPath) throws BiffException,
			IOException {

		List<String[]> list = readExcel(yourPath);

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("Ҫ��ӵ�Excel�е�����" + str[j]);
			}

			String yourClass = null;
			yourClass = str[4];
			System.out.println("������ӵ���:" + yourClass + "\n");

			String yourInstance = null;
			yourInstance = str[3];
			System.out.println("������ӵ�ʵ��:" + yourInstance + "\n");

			if (yourClass.equals("���Ȳ���Prot��g������д��")) {

			} else {
				addIndividualAndProperty.addSentenceClass(yourClass,
						yourInstance);
				addIndividualAndProperty.addSentenceLabel(yourInstance);
			}

			String yourProperty;
			for (int k = 0; k < sentencePropertyLabel.length; k++) {
				if (sentencePropertyLabel[k].equals("�������")
						|| sentencePropertyLabel[k].equals("����")) {
					// ��Ϊ��������͡������⡱ʱ��ʲô������
				} else {
					yourProperty = null;
					yourProperty = str[k];
					addIndividualAndProperty.addSentenceProperty(
							sentencePropertyLabel[k], yourInstance,
							yourProperty);
				}
			}
		}
		System.out.println("�������Ӳ���ɹ���");
	}

	@Override
	public void Delete(String yourInstanceID) {

		ResultSet resultsProperty = queryWithManyWays
				.checkPropertyDependOnId(yourInstanceID);

		if (resultsProperty.hasNext()) {
			while (resultsProperty.hasNext()) {

				QuerySolution solutionInstance = resultsProperty.next();

				if (solutionInstance.get("?relationID").toString()
						.contains("31")) {// ����ǵ�����ִ��

					int i = 0;
					for (i = 0; i < propertyLabel.length; i++) {
						System.out.println("    ��������"
								+ propertyLabel[i]
								+ "��"
								+ substringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));

						if (propertyLabel[i].equals("����")
								|| propertyLabel[i].equals("Hownet�еĸ���")) {

						} else {
							deleteIndividual.deleteInstanceProperty(
									solutionInstance.get("?instanceLabel")
											.toString(),
									solutionInstance.get(propertyRelation[i])
											.toString(),
									solutionInstance
											.get(propertySPARQLValue[i])
											.toString());

						}

					}

					// ɾ�������Label
					deleteIndividual.deleteClass(
							solutionInstance.get("?instanceLabel").toString(),
							solutionInstance.get("?propertyClass").toString());
					deleteIndividual.deleteLabel(solutionInstance.get(
							"?instanceLabel").toString());

					System.out.println("����ɾ���ɹ�\n");
				} else {
					// ����Ǿ�����ʲô������
				}
			}
		}

		else {
			System.out.println("֪ʶ�������û�д�ʵ��");
		}
	}

	@Override
	public void DeleteSentence(String yourInstanceID) {

		ResultSet resultsProperty = queryWithManyWays
				.checkSentencePropertyDependOnId(yourInstanceID);

		if (resultsProperty.hasNext()) {
			while (resultsProperty.hasNext()) {

				QuerySolution solutionInstance = resultsProperty.next();

				if (solutionInstance.get("?relationID").toString()
						.contains("85")) {// ����Ǿ�����ִ��
					int i = 0;
					for (i = 0; i < sentencePropertyLabel.length; i++) {
						System.out.println("    ��������"
								+ sentencePropertyLabel[i]
								+ "��"
								+ substringManage(solutionInstance.get(
										sentencePropertySPARQLValue[i])
										.toString()));

						if (sentencePropertyLabel[i].equals("����")
								|| sentencePropertyLabel[i].equals("�������")) {

						} else {
							deleteIndividual.deleteSentenceInstanceProperty(
									solutionInstance.get("?instanceLabel")
											.toString(),
									solutionInstance.get(
											sentencePropertyRelation[i])
											.toString(),
									solutionInstance.get(
											sentencePropertySPARQLValue[i])
											.toString());

						}

					}

					// ɾ�������Label
					deleteIndividual.deleteSentenceClass(
							solutionInstance.get("?instanceLabel").toString(),
							solutionInstance.get("?propertyClass").toString());
					deleteIndividual.deleteSentenceLabel(solutionInstance.get(
							"?instanceLabel").toString());

					System.out.println("����ɾ���ɹ�\n");
				} else {
					// ����ǵ�����ʲô������
				}

			}

		} else {
			System.out.println("֪ʶ�������û�д�ʵ��");
		}
	}

	@Override
	public void Modify(String yourID, String yourPropertyLabel,
			String yourSPARQLProperty, String yourRelationProperty) {

		// ���Ҷ�Ӧ����Ԫ��
		// ResultSet result = queryWithManyWays.checkThisTriple(yourID,
		// yourPropertyLabel, "����");
		ResultSet result = queryWithManyWays.checkPropertyDependOnId(yourID);
		QuerySolution solution = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				solution = result.next();
				if (yourPropertyLabel.equals("����ID")) {

					modifyID(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel);

				} else if (yourPropertyLabel.equals("Hownet�еĸ���")) {

					modifyClass(solution, yourSPARQLProperty);

				} else if (yourPropertyLabel.equals("����")) {

					modifyLabel(solution, yourID);
				} else {
					modifyPropertyValue(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel, yourID);
				}

			}
		} else {
			System.out.println("�޴���Ԫ��");
		}

		System.out.println("�޸ĳɹ�");
	}

	@Override
	public void ModifySentence(String yourID, String yourPropertyLabel,
			String yourSPARQLProperty, String yourRelationProperty) {

		// ���Ҷ�Ӧ����Ԫ��
		// ResultSet result = queryWithManyWays.checkThisTriple(yourID,
		// yourPropertyLabel, "����");
		ResultSet result = queryWithManyWays
				.checkSentencePropertyDependOnId(yourID);
		QuerySolution solution = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				solution = result.next();
				if (yourPropertyLabel.equals("����ID")) {

					modifySentenceID(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel);

				} else if (yourPropertyLabel.equals("�������")) {

					modifySentenceClass(solution, yourSPARQLProperty);

				} else if (yourPropertyLabel.equals("����")) {

					modifySentenceLabel(solution, yourID);

				} else {
					modifySentencePropertyValue(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel, yourID);
				}

			}
			System.out.println("�޸ĳɹ�");
		} else {
			System.out.println("�޴���Ԫ��");
		}
	}

	public ResultSet QueryWord(String yourClass) {

		ResultSet resultsInstance = queryWithManyWays
				.checkPropertyDependOnId(yourClass);

		return resultsInstance;

	}

	@Override
	public ResultSet QueryIndividualDependOnId(String yourID) {

		ResultSet resultsInstance = queryWithManyWays
				.checkPropertyDependOnId(yourID);

		return resultsInstance;
	}

	@Override
	public ResultSet QueryIndividual(String yourWord) {

		ResultSet resultsInstance = queryWithManyWays.checkProperty(yourWord);

		return resultsInstance;
	}

	@Override
	public ResultSet QuerySentenceIndividualDependOnId(String yourID) {

		ResultSet resultsInstance = queryWithManyWays
				.checkSentencePropertyDependOnId(yourID);

		return resultsInstance;
	}

	@Override
	public ResultSet QuerySentenceIndividual(String yourSentence) {

		ResultSet resultsInstance = queryWithManyWays
				.checkSentenceProperty(yourSentence);

		return resultsInstance;
	}

	@Override
	public void WriteBackToOwl() throws IOException {

		writeOwl.writeBackToOwl();

	}

	@Override
	public void InsertRelationSameAs(InputStream yourPath)
			throws BiffException, IOException {
		List<String[]> list = readExcel(yourPath);

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			System.out.println("i = " + i);
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("Ҫ��ӵ�Excel�е�����" + str[j]);
			}

			String question1 = null;
			question1 = str[0];

			String question2 = null;
			question2 = str[1];
			// �á�_�����桰 ��
			question1 = question1.replace(" ", "_");
			question2 = question2.replace(" ", "_");
			System.out.println("question1:" + question1);
			System.out.println("question2:" + question2);

			// �ж�question2�Ƿ������ݿ���
			boolean ifInDB = queryWithManyWays.checkIfInDB(question2);
			String ID = null;
			if (ifInDB == false) {
				// ��ȡquestion2����(�ɵ������ĻԽӿ��Ҹ���)�����������Լ��ı�����
				String[] sentenceAllClass = { "Where", "What", "How", "When",
						"Which", "Would", "Shall", "Have", "Why", "Whose",
						"Is", "May", "Could", "Can", "Did", "Will", "Do",
						"Dose", "Was", "Are" };
				String[] sentenceAllClass2 = { "where", "what", "how", "when",
						"which", "would", "shall", "have", "why", "whose",
						"Is", "may", "could", "can", "did", "will", "do",
						"dose", "was", "are" };
				String question2Class = null;
				int j = 0;
				for (j = 0; j < sentenceAllClass.length; j++) {
					if (question2.contains(sentenceAllClass[j])) {
						question2Class = sentenceAllClass[j];
						System.out.println("question2Class: " + question2Class);
						break;
					} else if (question2.contains(sentenceAllClass2[j])) {
						question2Class = sentenceAllClass[j];
						System.out.println("question2Class: " + question2Class);
						break;
					}
				}
				if (j >= sentenceAllClass.length) {
					System.out.println("û���ҵ��þ��ӵĸ���");
				}

				// ������д���磺What's
				// if (question2Class.contains("'")) {
				// question2Class = question2Class.substring(0,
				// question2Class.indexOf("'"));
				// }

				// ���question2����
				addIndividualAndProperty.addSentenceClass(question2Class,
						question2);

				// ��ӱ�ǩLabel
				addIndividualAndProperty.addSentenceLabel(question2);

				// ��Ӿ���ID
				System.out.println("���ӵİ汾");
//				Scanner sc = new Scanner(System.in);
//				String sentenceVersion = sc.nextLine();
				String sentenceVersion = str[2];
				System.out.println("���ӵĲ�����1-12��");
//				sc = new Scanner(System.in);
//				String sentenceBook = sc.nextLine();
				String sentenceBook= str[3];
				System.out.println("���ӵĵ�Ԫ��");
//				sc = new Scanner(System.in);
//				String sentenceUnit = sc.nextLine();
				String sentenceUnit= str[4];
				ID = sentenceVersion + "/" + sentenceBook + "/" + sentenceUnit
						+ "/";

				int countID = 1;
				// ���ҳ�������Ԫ��
				ResultSet resultAllTriples = queryWithManyWays.checkAllTriple();
				ResultSet resultInstanceAllProperty = null;
				QuerySolution solutionAllTriples = null;
				if (resultAllTriples.hasNext()) {
					while (resultAllTriples.hasNext()) {
						// QuerySolution next()
						// Moves onto the next result.
						// �ƶ����¸�result��
						solutionAllTriples = resultAllTriples.next();
						if (solutionAllTriples.get("?s").toString()
								.contains("'")) {
							resultInstanceAllProperty = queryWithManyWays
									.checkAllID(
											solutionAllTriples
													.get("?s")
													.toString()
													.substring(
															solutionAllTriples
																	.get("?s")
																	.toString()
																	.indexOf(
																			"'") + 1,
															solutionAllTriples
																	.get("?s")
																	.toString()
																	.lastIndexOf(
																			"'")),
											"85");// ���Ҹ�ʵ��������ID
							if (resultInstanceAllProperty.hasNext()) {// ���ͬ�汾��ͬ������ͬ��Ԫ�ĵ��ʸ���������ID
								while (resultInstanceAllProperty.hasNext()) {
									QuerySolution solution = resultInstanceAllProperty
											.next();
									if (solution.get("?allID").toString()
											.contains(ID)) {// ��ͬ�汾��ͬ������ͬ��Ԫ
										countID++;// ��countID��1
									} else {
										continue;
									}
								}
							} else {
								System.out
										.println("��ͬ�汾��ͬ������ͬ��Ԫ�ĵ��ʣ�countID���� = 1");
							}
							System.out.println("countID: " + countID);
						} else {
							continue;
						}

					}
				} else {
					System.out.println("����Ԫ��");
				}

				System.out.println("����countID: " + countID);
				ID = ID + String.valueOf(countID);
				// ���ID
				addIndividualAndProperty.addSentencePropertyForModify("����ID",
						question2, ID, ID);

			} else {
				// ʲô������
			}

			// ��ӵȼ۹�ϵ
			addIndividualAndProperty.addRelationSameAs(question1, question2);
			// addIndividualAndProperty.addRelationSameAs(question2, question1);
		}
		System.out.println("��������ȼ۹�ϵ�ɹ���");

	}

	@Override
	public ArrayList<String> ReasonSameAs(String yourSentence) {

		yourSentence = yourSentence.replace(" ", "_");

		InfModel inf = myReasoner.ReasonSameAs();

		// ͨ����ǩ��URI
		ResultSet result = queryWithManyWays.checkOnlyInstanceURI(yourSentence);

		System.out.println(yourSentence + " * * =>\n");
		Iterator list = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// �ƶ����¸�result��
				QuerySolution solution = result.next();
				Resource yourSentenceSubject = solution.get("?instance")
						.asResource();
				list = inf.listStatements(yourSentenceSubject, null,
						(RDFNode) null);
			}
		}

		ArrayList<String> allString = new ArrayList();
		int index = 0;
		// while (list.hasNext()) {
		// String[] listStringArray = list.next().toString().split(",");
		//
		// for (String ss : listStringArray) {
		// // System.out.println(ss);
		// allString.add(ss);
		// index++;
		// }
		// }

		while (list.hasNext()) {
			String[] listStringArray = list.next().toString().split("]");

			for (String ss : listStringArray) {//�ֽ��ÿһ����Ԫ��
				// System.out.println(ss);

				String str1 = ss.substring(0,ss.indexOf(","));
				System.out.println("str1��������" + str1);
				allString.add(str1);
				index++;
				
				String str2 = ss.substring(ss.indexOf(",")+1, ss.length());
				//System.out.println("str2��������" + str2);
				
				String str3 = str2.substring(0, str2.indexOf(","));
				System.out.println("str3��������" + str3);
				allString.add(str3);
				index++;
				
				if(str2.substring((str2.indexOf(",")+1)).contains("\"")){
						String str4 = str2.substring(str2.indexOf("\"")+1, str2.lastIndexOf("\""));
						if(str4.contains(")")){
							str4 = str4.substring(str4.indexOf(")")+1);
						}
						System.out.println("str4��������" + str4 + "\n");
						allString.add(str4);		
						index++;
				}else if(str2.substring((str2.indexOf(",")+1)).contains("'")){
					String str4 = str2.substring(str2.indexOf("\'")+1, str2.lastIndexOf("\'"));
					System.out.println("str4��������" + str4 + "\n");
					allString.add(str4);		
					index++;
				}else{
					String str4 = str2.substring(str2.indexOf(",")+1);
					if(str4.contains(",")){
						str4 = str2.substring(str2.indexOf(",")+1, str2.indexOf(","));
					}
					System.out.println("str4��������" + str4 + "\n");
					allString.add(str4);		
					index++;
				}
			}
		}
		
		ArrayList<String> returnString = new ArrayList();
		for (int i = 0; i < index; i++) {
			String everyString = allString.subList(i, i + 1).toString();
			if (i % 3 == 1) {

				//ɾ����һ���ո�
				if(everyString.contains("]")){					
					everyString = everyString.substring(everyString.indexOf(" ")+1, everyString.indexOf("]"));			
				}
				if (everyString.equals(RDFS.label.toString())) {
					returnString.add("����Label");
				} else if (everyString.equals(RDF.type.toString())) {
					returnString.add("���⸸��");

					everyString = allString.subList(i + 1, i + 2).toString();
					everyString = everyString.substring(everyString.indexOf(" ")+1,everyString.indexOf("]"));
					everyString = QueryLabelAndReturn(everyString);
					returnString.add(everyString);
					i++;

				} else if (everyString.equals(OWL.sameAs.toString())) {
					returnString.add("�ȼ�");

					everyString = allString.subList(i + 1, i + 2).toString();
					everyString = everyString.substring(everyString.indexOf("[")+1,everyString.indexOf("]"));
					returnString.add(everyString);
					i++;
				} else {
					everyString = QueryLabelAndReturn(everyString);
					returnString.add(everyString);
				}

			} else if (i % 3 == 2) {
				everyString = everyString.substring(everyString.indexOf("[")+1,everyString.indexOf("]"));
				returnString.add(everyString);
			}
			
		}
		return returnString;
	}

	// ��̬����-------------------------------------------------------------------------------------------------------------------------------------
	private static String substringManage(String string) {
		String newString = string.substring(string.indexOf(")") + 1,
				string.lastIndexOf("@"));
		return newString;
	}

	private static void Init() {
		try {
			read();
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

	private static void read() throws FileNotFoundException, IOException {
		ALLWORDS = new HashMap<String, Vector>();
		ALLMEANS = new HashMap<String, Vector>();
		BufferedReader in = new BufferedReader(new FileReader(new File(
				"Data/HowNet.txt")));
		String temp = in.readLine();
		while (temp != null) {
			Vector<String> DEFS;
			Vector<String> W_C;

			// ��ȡһ��No��txt����
			temp = in.readLine();
			String W_c = temp.substring(4);// ��ȡ����
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			String W_e = temp.substring(temp.indexOf("=") + 1);// ��ȡӢ��
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			String DEF = temp.substring(temp.indexOf("=") + 1);// ��ȡDEF

			//
			if (ALLWORDS.containsKey(W_e) && ALLMEANS.containsKey(W_e)) {
				W_C = ALLWORDS.get(W_e);
				DEFS = ALLMEANS.get(W_e);
			} else {
				W_C = new Vector<String>();
				DEFS = new Vector<String>();
			}

			/* �ж�֮ǰ�Ƿ���ֹ�ͬ����W_C��DEF */
			Iterator<String> It_1 = W_C.iterator();
			boolean judge_1 = false;
			while (It_1.hasNext()) {
				String m = It_1.next();
				if (m.equals(W_c)) {
					judge_1 = true;
					break;
				}
			}
			/* �ж�֮ǰ�Ƿ���ֹ�ͬ����DEF */
			Iterator<String> It_2 = DEFS.iterator();
			boolean judge_2 = false;
			while (It_2.hasNext()) {
				String m = It_2.next();
				if (m.equals(DEF)) {
					judge_2 = true;
					break;
				}
			}
			if (!judge_1) {
				W_C.add(W_c);
			}
			if (!judge_2) {
				DEFS.add(DEF);
			}
			ALLWORDS.put(W_e, W_C);
			ALLMEANS.put(W_e, DEFS);
			temp = in.readLine();
			temp = in.readLine();
		}
		in.close();
		System.out.println("HowNet Load Succeed!");
		// findDEF("man");
	}

	// ����Hownet�еĸ���
	private static String findDEF(String W_e) {
		if (W_e.contains("@")) {
			W_e = subStringManage(W_e);
		}
		String yourClass = null;
		Vector<String> DEFS = ALLMEANS.get(W_e);
		ArrayList<String> result = new ArrayList();
		if (DEFS == null || DEFS.size() <= 0) {
			System.out.println(W_e + "�ĸ��಻��HowNet��");
		} else {
			for (int i = 0; i < DEFS.size(); i++) {
				// System.out.println(m.get(i)[1]);
				result.add(DEFS.get(i));
			}
			System.out.println("��ѡ������Ҫ�ĸ��ࣨ������������");
			int num = 0;
			for (String temp : result) {
				num++;
				System.out.println(num + temp);
			}

			Scanner sc = new Scanner(System.in);
			int yourClassNum = 0;
			yourClassNum = sc.nextInt();
			num = 0;
			for (String temp : result) {
				num++;
				if (num == yourClassNum) {
					System.out.println(num + temp);
					yourClass = temp;
				}
			}
			int index = 0;
			index = yourClass.indexOf(":");
			System.out.println("index: " + index);
			if (index == -1) {
				yourClass = yourClass.substring(1, yourClass.length() - 1);
			} else {
				yourClass = yourClass.substring(1, index);
			}
			System.out.println("��ѡ��ĸ��ࣺ" + yourClass);
		}
		return yourClass;
	}

	// �����ַ���
	private static String subStringManage(String string) {
		string = string.substring(0, string.indexOf("@"));
		return string;
	}

	// �޸ĵ���ID
	private static void modifyID(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel) {
		// ɾ������Ԫ��
		deleteIndividual.deleteInstanceProperty(solution.get("?instanceLabel")
				.toString(), solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// ����µ���Ԫ��
		Scanner sc = new Scanner(System.in);
		System.out.println("�����������޸ĵ�����ֵ:");
		String yourModifyValue = sc.nextLine();
		String yourID = yourModifyValue;
		addIndividualAndProperty.addPropertyForModify(yourPropertyLabel,
				solution.get("?instanceLabel").toString(), yourModifyValue,
				yourID);

		for (int i = 0; i < propertyRelation.length; i++) {
			if (propertyLabel[i].equals("����")
					|| propertyLabel[i].equals("Hownet�еĸ���")
					|| propertyLabel[i].equals("����ID")) {
				// ʲô������
			} else {

				// ����֮ǰ������ֵ
				String prePropertyValue = solution.get(propertySPARQLValue[i])
						.toString();
				System.out.println("prePropertyValue:" + prePropertyValue);
				String newPropertyValue = prePropertyValue
						.substring(prePropertyValue.indexOf(")") + 1);
				System.out.println("newPropertyValue:" + newPropertyValue);

				// ɾ������Ԫ��
				deleteIndividual.deleteInstanceProperty(
						solution.get("?instanceLabel").toString(), solution
								.get(propertyRelation[i]).toString(), solution
								.get(propertySPARQLValue[i]).toString());

				// ����µ���Ԫ��
				addIndividualAndProperty.addPropertyForModify(propertyLabel[i],
						solution.get("?instanceLabel").toString(),
						newPropertyValue, yourID);

			}
		}
	}

	// �޸ĵ��ʸ���
	private static void modifyClass(QuerySolution solution,
			String yourSPARQLProperty) {
		Init();
		// ɾ������Ԫ��
		deleteIndividual.deleteClass(solution.get("?instanceLabel").toString(),
				solution.get(yourSPARQLProperty).toString());

		// ����µ���Ԫ��
		System.out.println("��ѡ�������޸ĵĸ���:");
		// ����Ҫ��ӵ�ʵ����Hownet����
		String yourClass = findDEF(solution.get("?instanceLabel").toString());

		addIndividualAndProperty.addClass(yourClass,
				solution.get("?instanceLabel").toString());
	}

	// �޸ĵ���Label
	private static void modifyLabel(QuerySolution solution, String yourID) {
		// �޸�Label
		// ɾ������Ԫ��
		deleteIndividual.deleteLabel(solution.get("?instanceLabel").toString());
		// ����µ���Ԫ��
		Scanner sc = new Scanner(System.in);
		System.out.println("�����������޸ĵ�����ֵ:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addLabel(yourModifyValue);

		// �޸���
		// ɾ������Ԫ��
		deleteIndividual.deleteClass(solution.get("?instanceLabel").toString(),
				solution.get("?propertyClass").toString());
		// ����µ���Ԫ��
		addIndividualAndProperty.addClass(solution.get("?propertyClass")
				.toString(), yourModifyValue);

		for (int i = 0; i < propertyRelation.length; i++) {
			if (propertyLabel[i].equals("����")
					|| propertyLabel[i].equals("Hownet�еĸ���")) {
				// ʲô������
			} else {

				// ɾ����Ӧ���Ե���Ԫ��
				deleteIndividual.deleteInstanceProperty(
						solution.get("?instanceLabel").toString(), solution
								.get(propertyRelation[i]).toString(), solution
								.get(propertySPARQLValue[i]).toString());

				// �����Ӧ���Ե���Ԫ��
				addIndividualAndProperty.addPropertyForModify(propertyLabel[i],
						yourModifyValue, solution.get(propertySPARQLValue[i])
								.toString(), yourID);

			}
		}
	}

	// �޸ĵ�������
	private static void modifyPropertyValue(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel, String yourID) {
		// ɾ������Ԫ��
		deleteIndividual.deleteInstanceProperty(solution.get("?instanceLabel")
				.toString(), solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// ����µ���Ԫ��
		Scanner sc = new Scanner(System.in);
		System.out.println("�����������޸ĵĵ�������:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addPropertyForModify(yourPropertyLabel,
				solution.get("?instanceLabel").toString(), yourModifyValue,
				yourID);
	}

	// �޸ľ���ID
	private static void modifySentenceID(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel) {
		// ɾ������Ԫ��
		deleteIndividual.deleteSentenceInstanceProperty(
				solution.get("?instanceLabel").toString(),
				solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// ����µ���Ԫ��
		Scanner sc = new Scanner(System.in);
		System.out.println("�����������޸ĵ�����ֵ:");
		String yourModifyValue = sc.nextLine();
		String yourID = yourModifyValue;
		addIndividualAndProperty.addSentencePropertyForModify(
				yourPropertyLabel, solution.get("?instanceLabel").toString(),
				yourModifyValue, yourID);

		for (int i = 0; i < sentencePropertyRelation.length; i++) {
			if (sentencePropertyLabel[i].equals("����")
					|| sentencePropertyLabel[i].equals("�������")
					|| sentencePropertyLabel[i].equals("����ID")) {
				// ʲô������
			} else {

				// ����֮ǰ������ֵ
				String prePropertyValue = solution.get(
						sentencePropertySPARQLValue[i]).toString();
				System.out.println("prePropertyValue:" + prePropertyValue);
				String newPropertyValue = prePropertyValue
						.substring(prePropertyValue.indexOf(")") + 1);
				System.out.println("newPropertyValue:" + newPropertyValue);

				// ɾ������Ԫ��
				deleteIndividual
						.deleteSentenceInstanceProperty(
								solution.get("?instanceLabel").toString(),
								solution.get(sentencePropertyRelation[i])
										.toString(),
								solution.get(sentencePropertySPARQLValue[i])
										.toString());

				// ����µ���Ԫ��
				addIndividualAndProperty.addSentencePropertyForModify(
						sentencePropertyLabel[i], solution
								.get("?instanceLabel").toString(),
						newPropertyValue, yourID);

			}
		}
	}

	// �޸ľ��Ӹ���
	private static void modifySentenceClass(QuerySolution solution,
			String yourSPARQLProperty) {
		// ɾ������Ԫ��
		deleteIndividual.deleteSentenceClass(solution.get("?instanceLabel")
				.toString(), solution.get(yourSPARQLProperty).toString());

		// ����µ���Ԫ��
		System.out.println("��ѡ�������޸ĵ��������:");
		// ����Ҫ��ӵ�ʵ����Hownet����
		String yourClass = solution
				.get("?instanceLabel")
				.toString()
				.substring(0,
						solution.get("?instanceLabel").toString().indexOf("_"));
		// ������д���磺What's
		if (yourClass.contains("'")) {
			yourClass = yourClass.substring(0, yourClass.indexOf("'"));
		}

		addIndividualAndProperty.addSentenceClass(yourClass,
				solution.get("?instanceLabel").toString());
	}

	// �޸ľ���Label
	private static void modifySentenceLabel(QuerySolution solution,
			String yourID) {
		// �޸�Label
		// ɾ������Ԫ��
		deleteIndividual.deleteSentenceLabel(solution.get("?instanceLabel")
				.toString());
		// ����µ���Ԫ��
		Scanner sc = new Scanner(System.in);
		System.out.println("�����������޸ĵ�����:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addSentenceLabel(yourModifyValue);

		// �޸���
		// ɾ������Ԫ��
		deleteIndividual.deleteSentenceClass(solution.get("?instanceLabel")
				.toString(), solution.get("?propertyClass").toString());
		// ����µ���Ԫ��
		addIndividualAndProperty.addSentenceClass(solution
				.get("?propertyClass").toString(), yourModifyValue);

		for (int i = 0; i < sentencePropertyRelation.length; i++) {
			if (sentencePropertyLabel[i].equals("����")
					|| sentencePropertyLabel[i].equals("�������")) {
				// ʲô������
			} else {

				// ɾ����Ӧ���Ե���Ԫ��
				deleteIndividual
						.deleteSentenceInstanceProperty(
								solution.get("?instanceLabel").toString(),
								solution.get(sentencePropertyRelation[i])
										.toString(),
								solution.get(sentencePropertySPARQLValue[i])
										.toString());

				// �����Ӧ���Ե���Ԫ��
				addIndividualAndProperty
						.addSentencePropertyForModify(sentencePropertyLabel[i],
								yourModifyValue,
								solution.get(sentencePropertySPARQLValue[i])
										.toString(), yourID);

			}
		}
	}

	// �޸ľ�������
	private static void modifySentencePropertyValue(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel, String yourID) {
		// ɾ������Ԫ��
		deleteIndividual.deleteSentenceInstanceProperty(
				solution.get("?instanceLabel").toString(),
				solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// ����µ���Ԫ��
		Scanner sc = new Scanner(System.in);
		System.out.println("�����������޸ĵľ�������:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addSentencePropertyForModify(
				yourPropertyLabel, solution.get("?instanceLabel").toString(),
				yourModifyValue, yourID);
	}

	// ��ȡExcel�������ַ�������
	private static List<String[]> readExcel(InputStream yourPath)
			throws BiffException, IOException {
		// ����һ��list �����洢��ȡ������
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;
		// ��ȡExcel�ļ�����
		rwb = Workbook.getWorkbook(yourPath);

		// ��ȡ�ļ���ָ�������� Ĭ�ϵĵ�һ��
		Sheet sheet = rwb.getSheet(0);

		// ����(��ͷ��Ŀ¼����Ҫ����1��ʼ)
		for (int i = 0; i < sheet.getRows(); i++) {

			// ����һ������ �����洢ÿһ�е�ֵ
			String[] str = new String[sheet.getColumns()];

			// ����
			for (int j = 0; j < sheet.getColumns(); j++) {

				// ��ȡ��i�У���j�е�ֵ
				cell = sheet.getCell(j, i);
				str[j] = cell.getContents();

			}
			// �Ѹջ�ȡ���д���list
			list.add(str);
		}
		return list;
	}

	// ��ѯLabel������������������Label
	private static String QueryLabelAndReturn(String URI) {
		ResultSet resultPropertyLabel = queryWithManyWays
				.checkOnlyPropertyLabel(URI);
		String label = null;
		if (resultPropertyLabel.hasNext()) {
			while (resultPropertyLabel.hasNext()) {

				QuerySolution solutionPropertyLabel = resultPropertyLabel
						.next();
				label = solutionPropertyLabel.get("propertyLabel").toString();

				if (label.contains("@")) {
					label = label.substring(0, label.indexOf("@"));
				}
			}
		}
		return label;
	}
}
