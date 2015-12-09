import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import jxl.read.biff.BiffException;

import OntologyManage.OntologyManage;
import OntologyManage.Impl.OntologyManageImpl;

public class MainTest {

	public static HashMap<String, Vector> ALLWORDS;
	public static HashMap<String, Vector> ALLMEANS;

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
			"?propertyVersion", "?propertyBook", "?instanceLabel",
			"?propertyClass", "?propertyAnswer", "?propertyScene",
			"?propertySentencePattern", "?propertyRelatedWords" };
	private static String[] sentencePropertyRelation = { "?relationID",
			"?relationVersion", "?relationBook", "?instanceLabel",
			"?relationClass", "?relationAnswer", "?relationScene",
			"?relationSentencePattern", "?relationRelatedWords" };

	public static void read() throws FileNotFoundException, IOException {

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

	/**
	 * @param args
	 * @throws IOException
	 * @throws BiffException
	 */
	public static void main(String[] args) throws BiffException, IOException {

		try {
			read();
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}

		System.out.println("��ѡ������Ҫִ�еĲ���������1-10����");
		System.out.println("1.��ӵ���");
		System.out.println("2.��Ӿ���");
		System.out.println("3.������ӵ���");
		System.out.println("4.������Ӿ���");
		System.out.println("5.ɾ������");
		System.out.println("6.ɾ������");
		System.out.println("7.�޸ĵ���");
		System.out.println("8.�޸ľ���");
		System.out.println("9.������鿴���е���");
		System.out.println("10.����ID�鿴���ʼ�������");
		System.out.println("11.�鿴���ʼ�������");
		System.out.println("12.������鿴���о���");
		System.out.println("13.����ID�鿴���Ӽ�������");
		System.out.println("14.�鿴���Ӽ�������");
		System.out.println("15.��Fuseki���ݿ��е�����д��owl�ļ���");
		System.out.println("16.������ӵȼ۹�ϵ");
		System.out.println("17.����ȼ۹�ϵ");
		Scanner option = new Scanner(System.in);
		int operation = option.nextInt();

		OntologyManage ontologyManage = new OntologyManageImpl();
		String[] wordParameter = new String[21];
		String[] sentenceParameter = new String[9];

		Scanner sc = null;
		String yourProperty = null;
		String yourInstance = null;
		switch (operation) {
		case 1:
			int i = 0;
			sc = new Scanner(System.in);

			// addIndividualAndProperty.addInstance(yourClass, yourInstance);

			// ����ʵ����Label+����+���е�����
			for (i = 0; i < propertyLabel.length; i++) {
				if (propertyLabel[i].equals("����")) {
					// ����Ҫ��ӵ�ʵ��
					System.out.println("������������ӵĵ���:");
					yourInstance = sc.nextLine();
					wordParameter[i] = yourInstance;

				} else if (propertyLabel[i].equals("Hownet�еĸ���")) {
					// ����Ҫ��ӵ�ʵ����Hownet����
					String yourClass = findDEF(yourInstance);
					wordParameter[i] = yourClass;
				} else {
					System.out.println("�����뵥�ʵ�" + propertyLabel[i] + ":\n");
					yourProperty = null;
					yourProperty = sc.nextLine();
					wordParameter[i] = yourProperty;
				}
			}

			// ���β�ִ��
			ontologyManage.Add(wordParameter);

			System.out.println("���ʲ���ɹ���");
			break;

		case 2:
			sc = new Scanner(System.in);
			// ����ʵ����Label+����+���е�����
			for (i = 0; i < sentencePropertyLabel.length; i++) {
				if (sentencePropertyLabel[i].equals("����")) {
					// ����Ҫ��ӵ�ʵ��
					System.out.println("������������ӵľ���:");
					yourInstance = sc.nextLine();
					yourInstance = yourInstance.replace(' ', '_');
					sentenceParameter[i] = yourInstance;

				} else if (sentencePropertyLabel[i].equals("�������")) {
					// ����Ҫ��ӵ�ʵ����Hownet����
					String yourClass = yourInstance.substring(0,
							yourInstance.indexOf("_"));
					sentenceParameter[i] = yourClass;
				} else {
					System.out.println("��������ӵ�" + sentencePropertyLabel[i]
							+ ":\n");
					yourProperty = null;
					yourProperty = sc.nextLine();
					sentenceParameter[i] = yourProperty;
				}
			}

			// ���β�ִ��
			ontologyManage.AddSentence(sentenceParameter);

			System.out.println("���Ӳ���ɹ���");
			break;

		case 3:
			sc = new Scanner(System.in);
			System.out.println("������������ӵ�excel·��:");
			String yourPath = sc.nextLine();
			InputStream yourInputStream = new FileInputStream(yourPath);
			ontologyManage.AddBatch(yourInputStream);
			break;
		case 4:
			sc = new Scanner(System.in);
			System.out.println("������������ӵ�excel·��:");
			yourPath = sc.nextLine();
			yourInputStream = new FileInputStream(yourPath);
			ontologyManage.AddSentenceBatch(yourInputStream);
			break;
		case 5:
			sc = new Scanner(System.in);
			System.out.println("������Ҫɾ���ĵ��ʵ�ID��\n");
			String yourInstanceID = sc.nextLine();
			ontologyManage.Delete(yourInstanceID);
			break;
		case 6:
			sc = new Scanner(System.in);
			System.out.println("������Ҫɾ���ľ��ӵ�ID��\n");
			yourInstanceID = sc.nextLine();
			ontologyManage.DeleteSentence(yourInstanceID);
			break;
		case 7:
			sc = new Scanner(System.in);
			System.out.println("������Ҫ�޸ĵĵ���ID��\n");
			String yourID = sc.nextLine();
			System.out.println("������Ҫ�޸ĵĵ��ʵ����ԣ�\n");
			String yourPropertyLabel = sc.nextLine();
			// System.out.println("������Ҫ�޸ĵ�ʵ��������ֵ��\n");
			// yourProperty = sc.nextLine();

			String yourSPARQLProperty = null;
			String yourRelationProperty = null;
			for (i = 0; i < propertyLabel.length; i++) {
				if (propertyLabel[i].equals(yourPropertyLabel)) {
					yourSPARQLProperty = propertySPARQLValue[i];
					yourRelationProperty = propertyRelation[i];
					break;
				}
			}
			ontologyManage.Modify(yourID, yourPropertyLabel,
					yourSPARQLProperty, yourRelationProperty);
			break;
		case 8:
			sc = new Scanner(System.in);
			System.out.println("������Ҫ�޸ĵľ���ID��\n");
			yourID = sc.nextLine();
			System.out.println("������Ҫ�޸ĵľ��ӵ����ԣ�\n");
			yourPropertyLabel = sc.nextLine();

			yourSPARQLProperty = null;
			yourRelationProperty = null;
			for (i = 0; i < sentencePropertyLabel.length; i++) {
				if (sentencePropertyLabel[i].equals(yourPropertyLabel)) {
					yourSPARQLProperty = sentencePropertySPARQLValue[i];
					yourRelationProperty = sentencePropertyRelation[i];
					break;
				}
			}
			ontologyManage.ModifySentence(yourID, yourPropertyLabel,
					yourSPARQLProperty, yourRelationProperty);

			break;
		case 9:
			sc = new Scanner(System.in);
			System.out.println("������Ҫ��ѯ���ࣺ\n");
			String yourClass = sc.nextLine();

			ResultSet resultsInstance = ontologyManage.QueryWord(yourClass);

			// ��ӡ���
			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					QuerySolution solutionInstance = resultsInstance.next();
					System.out.println("�ࣺ"
							+ yourClass
							+ "\n"
							+ "    ��������ʵ����"
							+ solutionInstance
									.get("?instanceLabel")
									.toString()
									.substring(
											0,
											solutionInstance
													.get("?instanceLabel")
													.toString().indexOf("@"))
							+ "\n");
				}
			} else {
				System.out.println("֪ʶ�������û�д�ʵ��");
			}
			break;
		case 10:
			sc = new Scanner(System.in);
			System.out.println("������Ҫ��ѯ�ĵ���ID��\n");
			yourID = sc.nextLine();
			resultsInstance = ontologyManage.QueryIndividualDependOnId(yourID);

			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// �ƶ����¸�result��
					QuerySolution solutionInstance = resultsInstance.next();

					System.out.println("ʵ��ID��" + yourID + "\n");
					for (i = 0; i < propertyLabel.length; i++) {
						System.out.println("    ��������"
								+ propertyLabel[i]
								+ "��"
								+ subStringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));
					}
					System.out.println("\n");
				}
			} else {
				System.out.println("֪ʶ�������û�д�ʵ��");
			}

			break;

		case 11:
			sc = new Scanner(System.in);
			System.out.println("������Ҫ��ѯ�ĵ��ʣ�\n");
			String yourWord = sc.nextLine();
			resultsInstance = ontologyManage.QueryIndividual(yourWord);

			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// �ƶ����¸�result��
					QuerySolution solutionInstance = resultsInstance.next();

					System.out.println("ʵ����" + yourWord + "\n");
					for (i = 0; i < propertyLabel.length; i++) {
						System.out.println("    ��������"
								+ propertyLabel[i]
								+ "��"
								+ subStringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));
					}
					System.out.println("\n");
				}
			} else {
				System.out.println("֪ʶ�������û�д�ʵ��");
			}
			break;
		case 12:
			sc = new Scanner(System.in);
			System.out.println("������Ҫ��ѯ���ࣺ\n");
			yourClass = sc.nextLine();

			resultsInstance = ontologyManage.QueryWord(yourClass);

			// ��ӡ���
			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					QuerySolution solutionInstance = resultsInstance.next();
					System.out.println("�ࣺ"
							+ yourClass
							+ "\n"
							+ "    ��������ʵ����"
							+ solutionInstance
									.get("?instanceLabel")
									.toString()
									.substring(
											0,
											solutionInstance
													.get("?instanceLabel")
													.toString().indexOf("@"))
							+ "\n");
				}
			} else {
				System.out.println("֪ʶ�������û�д�ʵ��");
			}
			break;

		case 13:
			sc = new Scanner(System.in);
			System.out.println("������Ҫ��ѯ�ľ���ID��\n");
			yourID = sc.nextLine();
			resultsInstance = ontologyManage
					.QuerySentenceIndividualDependOnId(yourID);

			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// �ƶ����¸�result��
					QuerySolution solutionInstance = resultsInstance.next();

					System.out.println("����ID��" + yourID + "\n");
					for (i = 0; i < sentencePropertyLabel.length; i++) {

						System.out.println("    ��������"
								+ sentencePropertyLabel[i]
								+ "��"
								+ subStringManage(solutionInstance.get(
										sentencePropertySPARQLValue[i])
										.toString()));
					}
					System.out.println("\n");
				}
			} else {
				System.out.println("֪ʶ�������û�д�ʵ��");
			}

			break;

		case 14:
			sc = new Scanner(System.in);
			System.out.println("������Ҫ��ѯ�ľ��ӣ�\n");
			String yourSentence = sc.nextLine();
			resultsInstance = ontologyManage
					.QuerySentenceIndividual(yourSentence);

			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// �ƶ����¸�result��
					QuerySolution solutionInstance = resultsInstance.next();

					System.out.println("���ӣ�" + yourSentence + "\n");
					for (i = 0; i < sentencePropertyLabel.length; i++) {

						System.out.println("    ��������"
								+ sentencePropertyLabel[i]
								+ "��"
								+ subStringManage(solutionInstance.get(
										sentencePropertySPARQLValue[i])
										.toString()));
					}
					System.out.println("\n");
				}
			} else {
				System.out.println("֪ʶ�������û�д�ʵ��");
			}
			break;

		case 15:
			ontologyManage.WriteBackToOwl();
			break;

		case 16:
			sc = new Scanner(System.in);
			System.out.println("������������ȼ۹�ϵ�ӵ�excel·��:");
			yourPath = sc.nextLine();
			yourInputStream = new FileInputStream(yourPath);
			ontologyManage.InsertRelationSameAs(yourInputStream);
			break;

		case 17:
			//����֮ǰҪ�Ȱε�Fuseki����д��OWL��
			//ontologyManage.WriteBackToOwl();
			
			sc = new Scanner(System.in);
			System.out.println("������������ҵľ���:");
			yourSentence = sc.nextLine();
			ArrayList<String> allInformation = ontologyManage.ReasonSameAs(yourSentence);
			
			i = 0;
			for (String temp : allInformation) {
				i++;
				System.out.println(temp +"  ");
				if(i%2 == 0){
					System.out.println("\n");
				}
			}
			break;

		default:
			System.out.println("������1��17");
		}
	}

	// ����Hownet�еĸ���
	public static String findDEF(String W_e) {
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
		String newString = string.substring(string.indexOf(")") + 1,
				string.lastIndexOf("@"));
		return newString;
	}
}
