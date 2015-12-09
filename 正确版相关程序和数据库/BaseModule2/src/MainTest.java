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

	private static String[] propertyLabel = new String[] { "单词ID", "单词",
			"主题-功能意念", "主题-话题", "Hownet中的父类", "词性", "词性属性", "中文含义", "单词教材版本",
			"单词册数", "难度", "课文原句", "情境段落", "联想", "同义词", "反义词", "拓展", "百科", "用法",
			"延伸例句", "常用" };
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
	private static String[] sentencePropertyLabel = new String[] { "句子ID",
			"句子教材版本", "句子册数", "问题", "问题句型", "回答", "情境对话", "重要句型", "相关单词" };
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

			// 读取一个No的txt内容
			temp = in.readLine();
			String W_c = temp.substring(4);// 读取中文
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			String W_e = temp.substring(temp.indexOf("=") + 1);// 读取英文
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			temp = in.readLine();
			String DEF = temp.substring(temp.indexOf("=") + 1);// 读取DEF

			//
			if (ALLWORDS.containsKey(W_e) && ALLMEANS.containsKey(W_e)) {
				W_C = ALLWORDS.get(W_e);
				DEFS = ALLMEANS.get(W_e);
			} else {
				W_C = new Vector<String>();
				DEFS = new Vector<String>();
			}

			/* 判断之前是否出现过同样的W_C和DEF */
			Iterator<String> It_1 = W_C.iterator();
			boolean judge_1 = false;
			while (It_1.hasNext()) {
				String m = It_1.next();
				if (m.equals(W_c)) {
					judge_1 = true;
					break;
				}
			}
			/* 判断之前是否出现过同样的DEF */
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

		System.out.println("请选择您将要执行的操作（输入1-10）：");
		System.out.println("1.添加单词");
		System.out.println("2.添加句子");
		System.out.println("3.批量添加单词");
		System.out.println("4.批量添加句子");
		System.out.println("5.删除单词");
		System.out.println("6.删除句子");
		System.out.println("7.修改单词");
		System.out.println("8.修改句子");
		System.out.println("9.根据类查看所有单词");
		System.out.println("10.根据ID查看单词及其属性");
		System.out.println("11.查看单词及其属性");
		System.out.println("12.根据类查看所有句子");
		System.out.println("13.根据ID查看句子及其属性");
		System.out.println("14.查看句子及其属性");
		System.out.println("15.把Fuseki数据库中的数据写回owl文件中");
		System.out.println("16.批量添加等价关系");
		System.out.println("17.推理等价关系");
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

			// 输入实例的Label+父类+所有的属性
			for (i = 0; i < propertyLabel.length; i++) {
				if (propertyLabel[i].equals("单词")) {
					// 输入要添加的实例
					System.out.println("请输入你想添加的单词:");
					yourInstance = sc.nextLine();
					wordParameter[i] = yourInstance;

				} else if (propertyLabel[i].equals("Hownet中的父类")) {
					// 查找要添加的实例的Hownet父类
					String yourClass = findDEF(yourInstance);
					wordParameter[i] = yourClass;
				} else {
					System.out.println("请输入单词的" + propertyLabel[i] + ":\n");
					yourProperty = null;
					yourProperty = sc.nextLine();
					wordParameter[i] = yourProperty;
				}
			}

			// 传参并执行
			ontologyManage.Add(wordParameter);

			System.out.println("单词插入成功！");
			break;

		case 2:
			sc = new Scanner(System.in);
			// 输入实例的Label+父类+所有的属性
			for (i = 0; i < sentencePropertyLabel.length; i++) {
				if (sentencePropertyLabel[i].equals("问题")) {
					// 输入要添加的实例
					System.out.println("请输入你想添加的句子:");
					yourInstance = sc.nextLine();
					yourInstance = yourInstance.replace(' ', '_');
					sentenceParameter[i] = yourInstance;

				} else if (sentencePropertyLabel[i].equals("问题句型")) {
					// 查找要添加的实例的Hownet父类
					String yourClass = yourInstance.substring(0,
							yourInstance.indexOf("_"));
					sentenceParameter[i] = yourClass;
				} else {
					System.out.println("请输入句子的" + sentencePropertyLabel[i]
							+ ":\n");
					yourProperty = null;
					yourProperty = sc.nextLine();
					sentenceParameter[i] = yourProperty;
				}
			}

			// 传参并执行
			ontologyManage.AddSentence(sentenceParameter);

			System.out.println("句子插入成功！");
			break;

		case 3:
			sc = new Scanner(System.in);
			System.out.println("请输入你想添加的excel路径:");
			String yourPath = sc.nextLine();
			InputStream yourInputStream = new FileInputStream(yourPath);
			ontologyManage.AddBatch(yourInputStream);
			break;
		case 4:
			sc = new Scanner(System.in);
			System.out.println("请输入你想添加的excel路径:");
			yourPath = sc.nextLine();
			yourInputStream = new FileInputStream(yourPath);
			ontologyManage.AddSentenceBatch(yourInputStream);
			break;
		case 5:
			sc = new Scanner(System.in);
			System.out.println("请输入要删除的单词的ID：\n");
			String yourInstanceID = sc.nextLine();
			ontologyManage.Delete(yourInstanceID);
			break;
		case 6:
			sc = new Scanner(System.in);
			System.out.println("请输入要删除的句子的ID：\n");
			yourInstanceID = sc.nextLine();
			ontologyManage.DeleteSentence(yourInstanceID);
			break;
		case 7:
			sc = new Scanner(System.in);
			System.out.println("请输入要修改的单词ID：\n");
			String yourID = sc.nextLine();
			System.out.println("请输入要修改的单词的属性：\n");
			String yourPropertyLabel = sc.nextLine();
			// System.out.println("请输入要修改的实例的属性值：\n");
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
			System.out.println("请输入要修改的句子ID：\n");
			yourID = sc.nextLine();
			System.out.println("请输入要修改的句子的属性：\n");
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
			System.out.println("请输入要查询的类：\n");
			String yourClass = sc.nextLine();

			ResultSet resultsInstance = ontologyManage.QueryWord(yourClass);

			// 打印结果
			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					QuerySolution solutionInstance = resultsInstance.next();
					System.out.println("类："
							+ yourClass
							+ "\n"
							+ "    ————实例："
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
				System.out.println("知识本体库中没有此实例");
			}
			break;
		case 10:
			sc = new Scanner(System.in);
			System.out.println("请输入要查询的单词ID：\n");
			yourID = sc.nextLine();
			resultsInstance = ontologyManage.QueryIndividualDependOnId(yourID);

			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// 移动到下个result上
					QuerySolution solutionInstance = resultsInstance.next();

					System.out.println("实例ID：" + yourID + "\n");
					for (i = 0; i < propertyLabel.length; i++) {
						System.out.println("    ————"
								+ propertyLabel[i]
								+ "："
								+ subStringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));
					}
					System.out.println("\n");
				}
			} else {
				System.out.println("知识本体库中没有此实例");
			}

			break;

		case 11:
			sc = new Scanner(System.in);
			System.out.println("请输入要查询的单词：\n");
			String yourWord = sc.nextLine();
			resultsInstance = ontologyManage.QueryIndividual(yourWord);

			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// 移动到下个result上
					QuerySolution solutionInstance = resultsInstance.next();

					System.out.println("实例：" + yourWord + "\n");
					for (i = 0; i < propertyLabel.length; i++) {
						System.out.println("    ————"
								+ propertyLabel[i]
								+ "："
								+ subStringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));
					}
					System.out.println("\n");
				}
			} else {
				System.out.println("知识本体库中没有此实例");
			}
			break;
		case 12:
			sc = new Scanner(System.in);
			System.out.println("请输入要查询的类：\n");
			yourClass = sc.nextLine();

			resultsInstance = ontologyManage.QueryWord(yourClass);

			// 打印结果
			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					QuerySolution solutionInstance = resultsInstance.next();
					System.out.println("类："
							+ yourClass
							+ "\n"
							+ "    ————实例："
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
				System.out.println("知识本体库中没有此实例");
			}
			break;

		case 13:
			sc = new Scanner(System.in);
			System.out.println("请输入要查询的句子ID：\n");
			yourID = sc.nextLine();
			resultsInstance = ontologyManage
					.QuerySentenceIndividualDependOnId(yourID);

			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// 移动到下个result上
					QuerySolution solutionInstance = resultsInstance.next();

					System.out.println("句子ID：" + yourID + "\n");
					for (i = 0; i < sentencePropertyLabel.length; i++) {

						System.out.println("    ————"
								+ sentencePropertyLabel[i]
								+ "："
								+ subStringManage(solutionInstance.get(
										sentencePropertySPARQLValue[i])
										.toString()));
					}
					System.out.println("\n");
				}
			} else {
				System.out.println("知识本体库中没有此实例");
			}

			break;

		case 14:
			sc = new Scanner(System.in);
			System.out.println("请输入要查询的句子：\n");
			String yourSentence = sc.nextLine();
			resultsInstance = ontologyManage
					.QuerySentenceIndividual(yourSentence);

			if (resultsInstance.hasNext()) {
				while (resultsInstance.hasNext()) {
					// QuerySolution next()
					// Moves onto the next result.
					// 移动到下个result上
					QuerySolution solutionInstance = resultsInstance.next();

					System.out.println("句子：" + yourSentence + "\n");
					for (i = 0; i < sentencePropertyLabel.length; i++) {

						System.out.println("    ————"
								+ sentencePropertyLabel[i]
								+ "："
								+ subStringManage(solutionInstance.get(
										sentencePropertySPARQLValue[i])
										.toString()));
					}
					System.out.println("\n");
				}
			} else {
				System.out.println("知识本体库中没有此实例");
			}
			break;

		case 15:
			ontologyManage.WriteBackToOwl();
			break;

		case 16:
			sc = new Scanner(System.in);
			System.out.println("请输入你想添等价关系加的excel路径:");
			yourPath = sc.nextLine();
			yourInputStream = new FileInputStream(yourPath);
			ontologyManage.InsertRelationSameAs(yourInputStream);
			break;

		case 17:
			//推理之前要先拔掉Fuseki数据写回OWL中
			//ontologyManage.WriteBackToOwl();
			
			sc = new Scanner(System.in);
			System.out.println("请输入你想查找的句子:");
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
			System.out.println("请输入1到17");
		}
	}

	// 查找Hownet中的父类
	public static String findDEF(String W_e) {
		String yourClass = null;
		Vector<String> DEFS = ALLMEANS.get(W_e);
		ArrayList<String> result = new ArrayList();
		if (DEFS == null || DEFS.size() <= 0) {
			System.out.println(W_e + "的父类不在HowNet中");
		} else {
			for (int i = 0; i < DEFS.size(); i++) {
				// System.out.println(m.get(i)[1]);
				result.add(DEFS.get(i));
			}
			System.out.println("请选择你需要的父类（仅输入整数）");
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
			System.out.println("你选择的父类：" + yourClass);
		}
		return yourClass;
	}

	// 处理字符串
	private static String subStringManage(String string) {
		String newString = string.substring(string.indexOf(")") + 1,
				string.lastIndexOf("@"));
		return newString;
	}
}
