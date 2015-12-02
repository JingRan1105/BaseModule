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

	private String[] propertyLabel = new String[] { "单词ID", "单词", "主题-功能意念",
			"主题-话题", "Hownet中的父类", "词性", "词性属性", "中文含义", "单词教材版本", "单词册数",
			"难度", "课文原句", "情境段落", "联想", "同义词", "反义词", "拓展", "百科", "用法", "延伸例句",
			"常用" };
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
	private String[] sentencePropertyLabel = new String[] { "句子ID", "句子教材版本",
			"句子册数", "问题句型", "问题", "回答", "情境对话", "重要句型", "相关单词" };
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

	public void Add() {

		int i = 0;

		Scanner sc = new Scanner(System.in);

		System.out.println("请输入你想添加的实例:\n");
		String yourInstance = null;
		yourInstance = sc.nextLine();

		String yourClass = findDEF(yourInstance);
		System.out.println("你选择的父类（返回）：" + yourClass);

		addIndividualAndProperty.addInstance(yourClass, yourInstance);

		String yourProperty;
		for (i = 0; i < propertyLabel.length; i++) {
			if (propertyLabel[i].equals("单词")
					|| propertyLabel[i].equals("Hownet中的父类")) {

			} else {
				System.out.println("请输入实例的" + propertyLabel[i] + ":\n");
				yourProperty = null;
				yourProperty = sc.nextLine();
				addIndividualAndProperty.addProperty(propertyLabel[i],
						yourInstance, yourProperty);
			}
		}
		System.out.println("单词插入成功！");
	}

	public void AddBatch() throws BiffException, IOException {

		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;

		// 输入Excel文件路径
		// System.out.println("请输入Excel文件路径，如 F:\\所有项目\\单词game\\需求\\单词汇总\\每个人的单词汇总\\汇总\\test.xls"
		// + ":\n");
		// Scanner sc = new Scanner(System.in);
		// String yourExcel = null;
		// yourExcel = sc.nextLine();

		InputStream stream = new FileInputStream(
				"G:\\所有项目\\单词game\\需求\\单词汇总\\每个人的单词汇总\\汇总\\要放入程序中运行的Excel们\\北京版.xls");

		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(stream);

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);

		// 行数(表头的目录不需要，从1开始)
		for (int i = 0; i < sheet.getRows(); i++) {

			// 创建一个数组 用来存储每一列的值
			String[] str = new String[sheet.getColumns()];

			// 列数
			for (int j = 0; j < sheet.getColumns(); j++) {

				// 获取第i行，第j列的值
				cell = sheet.getCell(j, i);
				str[j] = cell.getContents();

			}
			// 把刚获取的列存入list
			list.add(str);
		}

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("要添加的Excel中的内容" + str[j]);
			}

			// 我自己的excel不要注释掉以下三句
			String yourClass = null;
			yourClass = str[4];
			System.out.println("你想添加的类:" + yourClass);

			String yourInstance = null;
			yourInstance = str[1];
			System.out.println("你想添加的实例:" + yourInstance);

			// 我自己的excel不要注释掉以下
			if (yourClass.equals("（先不在Protégé中填写）")) {

			} else {
				addIndividualAndProperty.addInstance(yourClass, yourInstance);
				String yourProperty;
				for (int k = 0; k < 21; k++) {
					if (propertyLabel[k].equals("单词")
							|| propertyLabel[k].equals("Hownet中的父类")) {
						// 当为“单词”或“Hownet中的父类”时，什么都不做
					} else {
						yourProperty = null;
						yourProperty = str[k];
						addIndividualAndProperty.addProperty(propertyLabel[k],
								yourInstance, yourProperty);
					}
				}
			}
			// 到这里

			// 我自己的excel要注释掉以下
			// String yourClass = findDEF(yourInstance);
			// System.out.println("你选择的父类（返回）：" + yourClass);

			// if (yourClass == null) {
			// System.out.println(yourInstance + "的父类不在HowNet中");
			// } else {
			// addIndividualAndProperty.addInstance(yourClass, yourInstance);
			// }
			// 到这里
		}
		System.out.println("批量单词插入成功！");
	}

	public void AddSentenceBatch() throws BiffException, IOException {

		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;

		// 输入Excel文件路径
		// System.out.println("请输入Excel文件路径，如 F:\\所有项目\\单词game\\需求\\单词汇总\\每个人的单词汇总\\汇总\\test.xls"
		// + ":\n");
		// Scanner sc = new Scanner(System.in);
		// String yourExcel = null;
		// yourExcel = sc.nextLine();

		InputStream stream = new FileInputStream(
				"G:\\所有项目\\本体\\需求阶段\\句子Excel\\用于程序的Excel\\北京版-规范版.xls");

		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(stream);

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);

		// 行数(表头的目录不需要，从1开始)
		for (int i = 0; i < sheet.getRows(); i++) {

			// 创建一个数组 用来存储每一列的值
			String[] str = new String[sheet.getColumns()];

			// 列数
			for (int j = 0; j < sheet.getColumns(); j++) {

				// 获取第i行，第j列的值
				cell = sheet.getCell(j, i);
				str[j] = cell.getContents();

			}
			// 把刚获取的列存入list
			list.add(str);
		}

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("要添加的Excel中的内容" + str[j]);
			}

			String yourClass = null;
			yourClass = str[3];
			System.out.println("你想添加的类:" + yourClass + "\n");

			String yourInstance = null;
			yourInstance = str[4];
			System.out.println("你想添加的实例:" + yourInstance + "\n");

			if (yourClass.equals("（先不在Protégé中填写）")) {

			} else {
				addIndividualAndProperty.addSentenceInstance(yourClass,
						yourInstance);
			}

			String yourProperty;
			for (int k = 0; k < sentencePropertyLabel.length; k++) {
				if (sentencePropertyLabel.equals("问题句型")
						|| sentencePropertyLabel.equals("问题")) {
					// 当为“问题句型”或“问题”时，什么都不做
				} else {
					yourProperty = null;
					yourProperty = str[k];
					addIndividualAndProperty.addSentenceProperty(
							sentencePropertyLabel[k], yourInstance,
							yourProperty);
				}
			}
		}
		System.out.println("批量句子插入成功！");
	}

	public void Delete() {
		Scanner sc = new Scanner(System.in);

		System.out.println("请输入要删除的实例的ID：\n");
		String yourInstanceID = sc.nextLine();

		QueryDependOnId querydeleteInstanceProperty = new QueryDependOnIdImpl();
		ResultSet resultsProperty = querydeleteInstanceProperty
				.checkPropertyDependOnId(yourInstanceID);

		if (resultsProperty.hasNext()) {
			while (resultsProperty.hasNext()) {

				QuerySolution solutionInstance = resultsProperty.next();

				int i = 0;
				for (i = 0; i < propertyLabel.length; i++) {
					System.out.println("    ————"
							+ propertyLabel[i]
							+ "："
							+ substringManage(solutionInstance.get(
									propertySPARQLValue[i]).toString()));

					if (propertyLabel[i].equals("单词")
							|| propertyLabel[i].equals("Hownet中的父类")) {

					} else {
						deleteIndividual.deleteInstanceProperty(
								solutionInstance.get("?instanceLabel").toString(),
								solutionInstance.get(propertyRelation[i]).toString(),
								solutionInstance.get(propertySPARQLValue[i]).toString());

					}

				}
				
				//删除父类和Label
				deleteIndividual.deleteClassAndLabel(solutionInstance.get("?instanceLabel").toString(), solutionInstance.get("?propertyClass").toString());
				
				System.out.println("单词删除成功\n");
			}

		} else {
			System.out.println("知识本体库中没有此实例");
		}
	}

	public void Modify() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要修改的实例：\n");
		String yourInstance = sc.nextLine();
		System.out.println("请输入要修改的实例的属性：\n");
		String propertyLabel = sc.nextLine();
		System.out.println("请输入要修改的实例的属性值：\n");
		String yourProperty = sc.nextLine();
		modifyPropertyValue.ModifyInstance(yourProperty, yourInstance,
				propertyLabel);
	}

	public void QueryWord() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要查询的类：\n");
		String yourClass = sc.nextLine();
		ResultSet resultsInstance = queryIndividual.checkInstance(yourClass);

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
										solutionInstance.get("?instanceLabel")
												.toString().indexOf("@"))
						+ "\n");
			}
		} else {
			System.out.println("知识本体库中没有此实例");
		}
	}

	public void QueryWordPropertyValue() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要查询的单词：\n");
		String yourWord = sc.nextLine();
		ResultSet resultsInstance = queryIndividualAndProperty
				.checkProperty(yourWord);

		if (resultsInstance.hasNext()) {
			while (resultsInstance.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// 移动到下个result上
				QuerySolution solutionInstance = resultsInstance.next();

				System.out.println("实例：" + yourWord + "\n");
				for (int i = 0; i < propertyLabel.length; i++) {
					if (!propertyLabel[i].equals("Hownet中的父类")) {
						System.out.println("    ————"
								+ propertyLabel[i]
								+ "："
								+ substringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));
					} else {
						System.out.println("    ————"
								+ propertyLabel[i]
								+ "："
								+ substringManage(solutionInstance.get(
										"?propertyClass").toString()));
					}

				}
				System.out.println("\n");
			}
		} else {
			System.out.println("知识本体库中没有此实例");
		}
	}

	private static String substringManage(String string) {
		String newString = string.substring(string.indexOf(")") + 1,
				string.lastIndexOf("@"));
		return newString;
	}

	public void QueryIndividualDependOnId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要查询单词的ID：\n");
		String yourId = sc.nextLine();
		ResultSet resultsInstance = queryDependOnId
				.checkPropertyDependOnId(yourId);

		if (resultsInstance.hasNext()) {
			while (resultsInstance.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// 移动到下个result上
				QuerySolution solutionInstance = resultsInstance.next();

				int i = 0;
				for (i = 0; i < propertyLabel.length; i++) {
					if (!propertyLabel[i].equals("Hownet中的父类")) {
						System.out.println("    ————"
								+ propertyLabel[i]
								+ "："
								+ substringManage(solutionInstance.get(
										propertySPARQLValue[i]).toString()));
					} else {
						System.out.println("    ————"
								+ propertyLabel[i]
								+ "："
								+ substringManage(solutionInstance.get(
										"?propertyClass").toString()));
					}

				}
				System.out.println("\n");
			}

		} else {
			System.out.println("知识本体库中没有此实例");
		}
	}

	public void QueryIndividualSentenceDependOnId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要查询句子的ID：\n");
		String yourId = sc.nextLine();
		ResultSet resultsInstance = querySentenceDependOnId
				.checkSentencePropertyDependOnId(yourId);

		if (resultsInstance.hasNext()) {
			while (resultsInstance.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// 移动到下个result上
				QuerySolution solutionInstance = resultsInstance.next();

				// System.out.println("ID：" + yourId + "\n");
				System.out.println("    ————" + sentencePropertyLabel[0] + "："
						+ solutionInstance.get(sentencePropertySPARQLValue[0]));
				int i = 0;
				for (i = 1; i < sentencePropertyLabel.length; i++) {
					System.out
							.println("    ————"
									+ sentencePropertyLabel[i]
									+ "："
									+ substringManage(solutionInstance.get(
											sentencePropertySPARQLValue[i])
											.toString()));
				}
			}
		} else {
			System.out.println("知识本体库中没有此实例");
		}
	}

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

	public void WriteBackToOwl() throws IOException {
		writeOwl.writeBackToOwl();
	}
}
