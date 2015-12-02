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

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.vocabulary.RDF;

import AddDeleteModifyQuery.Add.AddIndividualAndProperty;
import AddDeleteModifyQuery.Delete.DeleteIndividual;
import AddDeleteModifyQuery.Modify.ModifyPropertyValue;
import AddDeleteModifyQuery.Query.QueryDependOnId;
import AddDeleteModifyQuery.Query.QueryIndividual;
import AddDeleteModifyQuery.Query.QueryIndividualAndProperty;
import AddDeleteModifyQuery.Query.QuerySentenceDependOnId;
import AddDeleteModifyQuery.Query.Impl.QueryDependOnIdImpl;
import AddDeleteModifyQuery.WriteOwl.WriteOwlImpl.WriteOwlImpl;

public class Adapter extends FusekiOperation {

	public static HashMap<String, Vector> ALLWORDS;
	public static HashMap<String, Vector> ALLMEANS;

	private AddIndividualAndProperty addIndividualAndProperty;
	private DeleteIndividual deleteIndividual;
	private ModifyPropertyValue modifyPropertyValue;
	private QueryIndividual queryIndividual;
	private QueryIndividualAndProperty queryIndividualAndProperty;
	private QueryDependOnId queryDependOnId;
	private QuerySentenceDependOnId querySentenceDependOnId;
	private WriteOwlImpl writeOwl;

	private String[] propertyLabel = new String[] { "����ID", "����", "����-��������",
			"����-����", "Hownet�еĸ���", "����", "��������", "���ĺ���", "���ʽ̲İ汾", "���ʲ���",
			"�Ѷ�", "����ԭ��", "�龳����", "����", "ͬ���", "�����", "��չ", "�ٿ�", "�÷�", "��������",
			"����" };
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
	private String[] sentencePropertyLabel = new String[] { "����ID", "���ӽ̲İ汾",
			"���Ӳ���", "�������", "����", "�ش�", "�龳�Ի�", "��Ҫ����", "��ص���" };
	private static String[] sentencePropertySPARQLValue = { "?propertyID",
			"?propertyVersion", "?propertyBook", "?propertyClass",
			"?instanceLabel", "?propertyAnswer", "?propertyScene",
			"?propertySentencePattern", "?propertyRelatedWords" };

	public Adapter(AddIndividualAndProperty addIndividualAndProperty) {
		Init();
		this.addIndividualAndProperty = addIndividualAndProperty;
	}

	public Adapter(DeleteIndividual deleteIndividual) {
		Init();
		this.deleteIndividual = deleteIndividual;
	}

	public Adapter(ModifyPropertyValue modifyPropertyValue) {
		Init();
		this.modifyPropertyValue = modifyPropertyValue;
	}

	public Adapter(QueryIndividual queryIndividual) {
		Init();
		this.queryIndividual = queryIndividual;
	}

	public Adapter(QueryIndividualAndProperty queryIndividualAndProperty) {
		Init();
		this.queryIndividualAndProperty = queryIndividualAndProperty;
	}

	public Adapter(QueryDependOnId queryDependOnId) {
		Init();
		this.queryDependOnId = queryDependOnId;
	}

	public Adapter(QuerySentenceDependOnId querySentenceDependOnId) {
		Init();
		this.querySentenceDependOnId = querySentenceDependOnId;
	}

	public Adapter(WriteOwlImpl writeOwl) {
		Init();
		this.writeOwl = writeOwl;
	}

	public static void Init() {
		try {
			read();
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

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

	public void Add() {

		int i = 0;

		Scanner sc = new Scanner(System.in);

		System.out.println("������������ӵ�ʵ��:\n");
		String yourInstance = null;
		yourInstance = sc.nextLine();

		String yourClass = findDEF(yourInstance);
		System.out.println("��ѡ��ĸ��ࣨ���أ���" + yourClass);

		addIndividualAndProperty.addInstance(yourClass, yourInstance);

		String yourProperty;
		for (i = 0; i < propertyLabel.length; i++) {
			if (propertyLabel[i].equals("����")
					|| propertyLabel[i].equals("Hownet�еĸ���")) {

			} else {
				System.out.println("������ʵ����" + propertyLabel[i] + ":\n");
				yourProperty = null;
				yourProperty = sc.nextLine();
				addIndividualAndProperty.addProperty(propertyLabel[i],
						yourInstance, yourProperty);
			}
		}
		System.out.println("���ʲ���ɹ���");
	}

	public void AddBatch() throws BiffException, IOException {

		// ����һ��list �����洢��ȡ������
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;

		// ����Excel�ļ�·��
		// System.out.println("������Excel�ļ�·������ F:\\������Ŀ\\����game\\����\\���ʻ���\\ÿ���˵ĵ��ʻ���\\����\\test.xls"
		// + ":\n");
		// Scanner sc = new Scanner(System.in);
		// String yourExcel = null;
		// yourExcel = sc.nextLine();

		InputStream stream = new FileInputStream(
				"G:\\������Ŀ\\����game\\����\\���ʻ���\\ÿ���˵ĵ��ʻ���\\����\\Ҫ������������е�Excel��\\������.xls");

		// ��ȡExcel�ļ�����
		rwb = Workbook.getWorkbook(stream);

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
				addIndividualAndProperty.addInstance(yourClass, yourInstance);
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

	public void AddSentenceBatch() throws BiffException, IOException {

		// ����һ��list �����洢��ȡ������
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;

		// ����Excel�ļ�·��
		// System.out.println("������Excel�ļ�·������ F:\\������Ŀ\\����game\\����\\���ʻ���\\ÿ���˵ĵ��ʻ���\\����\\test.xls"
		// + ":\n");
		// Scanner sc = new Scanner(System.in);
		// String yourExcel = null;
		// yourExcel = sc.nextLine();

		InputStream stream = new FileInputStream(
				"G:\\������Ŀ\\����\\����׶�\\����Excel\\���ڳ����Excel\\������-�淶��.xls");

		// ��ȡExcel�ļ�����
		rwb = Workbook.getWorkbook(stream);

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

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("Ҫ��ӵ�Excel�е�����" + str[j]);
			}

			String yourClass = null;
			yourClass = str[3];
			System.out.println("������ӵ���:" + yourClass + "\n");

			String yourInstance = null;
			yourInstance = str[4];
			System.out.println("������ӵ�ʵ��:" + yourInstance + "\n");

			if (yourClass.equals("���Ȳ���Prot��g������д��")) {

			} else {
				addIndividualAndProperty.addSentenceInstance(yourClass,
						yourInstance);
			}

			String yourProperty;
			for (int k = 0; k < sentencePropertyLabel.length; k++) {
				if (sentencePropertyLabel.equals("�������")
						|| sentencePropertyLabel.equals("����")) {
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

	public void Delete() {
		Scanner sc = new Scanner(System.in);

		System.out.println("������Ҫɾ����ʵ����ID��\n");
		String yourInstanceID = sc.nextLine();

		QueryDependOnId querydeleteInstanceProperty = new QueryDependOnIdImpl();
		ResultSet resultsProperty = querydeleteInstanceProperty
				.checkPropertyDependOnId(yourInstanceID);

		if (resultsProperty.hasNext()) {
			while (resultsProperty.hasNext()) {

				QuerySolution solutionInstance = resultsProperty.next();

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
								solutionInstance.get("?instanceLabel").toString(),
								solutionInstance.get(propertyRelation[i]).toString(),
								solutionInstance.get(propertySPARQLValue[i]).toString());

					}

				}
				
				//ɾ�������Label
				deleteIndividual.deleteClassAndLabel(solutionInstance.get("?instanceLabel").toString(), solutionInstance.get("?propertyClass").toString());
				
				System.out.println("����ɾ���ɹ�\n");
			}

		} else {
			System.out.println("֪ʶ�������û�д�ʵ��");
		}
	}

	public void Modify() {
		Scanner sc = new Scanner(System.in);
		System.out.println("������Ҫ�޸ĵ�ʵ����\n");
		String yourInstance = sc.nextLine();
		System.out.println("������Ҫ�޸ĵ�ʵ�������ԣ�\n");
		String propertyLabel = sc.nextLine();
		System.out.println("������Ҫ�޸ĵ�ʵ��������ֵ��\n");
		String yourProperty = sc.nextLine();
		modifyPropertyValue.ModifyInstance(yourProperty, yourInstance,
				propertyLabel);
	}

	public void QueryWord() {
		Scanner sc = new Scanner(System.in);
		System.out.println("������Ҫ��ѯ���ࣺ\n");
		String yourClass = sc.nextLine();
		ResultSet resultsInstance = queryIndividual.checkInstance(yourClass);

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
										solutionInstance.get("?instanceLabel")
												.toString().indexOf("@"))
						+ "\n");
			}
		} else {
			System.out.println("֪ʶ�������û�д�ʵ��");
		}
	}

	public void QueryWordPropertyValue() {
		Scanner sc = new Scanner(System.in);
		System.out.println("������Ҫ��ѯ�ĵ��ʣ�\n");
		String yourWord = sc.nextLine();
		ResultSet resultsInstance = queryIndividualAndProperty
				.checkProperty(yourWord);

		if (resultsInstance.hasNext()) {
			while (resultsInstance.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// �ƶ����¸�result��
				QuerySolution solutionInstance = resultsInstance.next();

				System.out.println("ʵ����" + yourWord + "\n");
				for (int i = 0; i < propertyLabel.length; i++) {
					if (!propertyLabel[i].equals("Hownet�еĸ���")) {
						System.out.println("    ��������"
								+ propertyLabel[i]
								+ "��"
								+ substringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));
					} else {
						System.out.println("    ��������"
								+ propertyLabel[i]
								+ "��"
								+ substringManage(solutionInstance.get(
										"?propertyClass").toString()));
					}

				}
				System.out.println("\n");
			}
		} else {
			System.out.println("֪ʶ�������û�д�ʵ��");
		}
	}

	private static String substringManage(String string) {
		String newString = string.substring(string.indexOf(")") + 1,
				string.lastIndexOf("@"));
		return newString;
	}

	public void QueryIndividualDependOnId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("������Ҫ��ѯ���ʵ�ID��\n");
		String yourId = sc.nextLine();
		ResultSet resultsInstance = queryDependOnId
				.checkPropertyDependOnId(yourId);

		if (resultsInstance.hasNext()) {
			while (resultsInstance.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// �ƶ����¸�result��
				QuerySolution solutionInstance = resultsInstance.next();

				int i = 0;
				for (i = 0; i < propertyLabel.length; i++) {
					if (!propertyLabel[i].equals("Hownet�еĸ���")) {
						System.out.println("    ��������"
								+ propertyLabel[i]
								+ "��"
								+ substringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));
					} else {
						System.out.println("    ��������"
								+ propertyLabel[i]
								+ "��"
								+ substringManage(solutionInstance.get(
										"?propertyClass").toString()));
					}

				}
				System.out.println("\n");
			}

		} else {
			System.out.println("֪ʶ�������û�д�ʵ��");
		}
	}

	public void QueryIndividualSentenceDependOnId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("������Ҫ��ѯ���ӵ�ID��\n");
		String yourId = sc.nextLine();
		ResultSet resultsInstance = querySentenceDependOnId
				.checkSentencePropertyDependOnId(yourId);

		if (resultsInstance.hasNext()) {
			while (resultsInstance.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// �ƶ����¸�result��
				QuerySolution solutionInstance = resultsInstance.next();

				// System.out.println("ID��" + yourId + "\n");
				System.out.println("    ��������" + sentencePropertyLabel[0] + "��"
						+ solutionInstance.get(sentencePropertySPARQLValue[0]));
				int i = 0;
				for (i = 1; i < sentencePropertyLabel.length; i++) {
					System.out
							.println("    ��������"
									+ sentencePropertyLabel[i]
									+ "��"
									+ substringManage(solutionInstance.get(
											sentencePropertySPARQLValue[i])
											.toString()));
				}
			}
		} else {
			System.out.println("֪ʶ�������û�д�ʵ��");
		}
	}

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

	public void WriteBackToOwl() throws IOException {
		writeOwl.writeBackToOwl();
	}
}
