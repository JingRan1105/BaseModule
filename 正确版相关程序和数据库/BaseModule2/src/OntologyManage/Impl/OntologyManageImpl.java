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
			"?propertyVersion", "?propertyBook", "?propertyClass",
			"?instanceLabel", "?propertyAnswer", "?propertyScene",
			"?propertySentencePattern", "?propertyRelatedWords" };
	private static String[] sentencePropertyRelation = { "?relationID",
			"?relationVersion", "?relationBook", "?relationClass",
			"?instanceLabel", "?relationAnswer", "?relationScene",
			"?relationSentencePattern", "?relationRelatedWords" };

	// 构造函数
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

		// 添加单词父类和Label
		addIndividualAndProperty.addClass(parameter[4], parameter[1]);
		addIndividualAndProperty.addLabel(parameter[1]);

		// 添加单词属性
		int i = 0;
		for (i = 0; i < parameter.length; i++) {
			System.out.println(i + parameter[i]);
			if (i == 1 || i == 4) {

			} else {
				// 调用public void addProperty(String propertyLabel, String
				// yourInstance,String yourProperty)
				addIndividualAndProperty.addProperty(propertyLabel[i],
						parameter[1], parameter[i]);
			}
		}

	}

	@Override
	public void AddSentence(String[] parameter) {

		// 添加单词父类和Label
		addIndividualAndProperty.addSentenceClass(parameter[4], parameter[3]);
		addIndividualAndProperty.addSentenceLabel(parameter[3]);

		// 添加单词属性
		int i = 0;
		for (i = 0; i < parameter.length; i++) {
			System.out.println(i + parameter[i]);
			if (i == 3 || i == 4) {

			} else {
				// 调用public void addProperty(String propertyLabel, String
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
				addIndividualAndProperty.addClass(yourClass, yourInstance);
				addIndividualAndProperty.addLabel(yourInstance);
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

	@Override
	public void AddSentenceBatch(InputStream yourPath) throws BiffException,
			IOException {

		List<String[]> list = readExcel(yourPath);

		String[] str = null;
		for (int i = 0; i < list.size(); i++) {
			str = (String[]) list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println("要添加的Excel中的内容" + str[j]);
			}

			String yourClass = null;
			yourClass = str[4];
			System.out.println("你想添加的类:" + yourClass + "\n");

			String yourInstance = null;
			yourInstance = str[3];
			System.out.println("你想添加的实例:" + yourInstance + "\n");

			if (yourClass.equals("（先不在Protégé中填写）")) {

			} else {
				addIndividualAndProperty.addSentenceClass(yourClass,
						yourInstance);
				addIndividualAndProperty.addSentenceLabel(yourInstance);
			}

			String yourProperty;
			for (int k = 0; k < sentencePropertyLabel.length; k++) {
				if (sentencePropertyLabel[k].equals("问题句型")
						|| sentencePropertyLabel[k].equals("问题")) {
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

	@Override
	public void Delete(String yourInstanceID) {

		ResultSet resultsProperty = queryWithManyWays
				.checkPropertyDependOnId(yourInstanceID);

		if (resultsProperty.hasNext()) {
			while (resultsProperty.hasNext()) {

				QuerySolution solutionInstance = resultsProperty.next();

				if (solutionInstance.get("?relationID").toString()
						.contains("31")) {// 如果是单词则执行

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
									solutionInstance.get("?instanceLabel")
											.toString(),
									solutionInstance.get(propertyRelation[i])
											.toString(),
									solutionInstance
											.get(propertySPARQLValue[i])
											.toString());

						}

					}

					// 删除父类和Label
					deleteIndividual.deleteClass(
							solutionInstance.get("?instanceLabel").toString(),
							solutionInstance.get("?propertyClass").toString());
					deleteIndividual.deleteLabel(solutionInstance.get(
							"?instanceLabel").toString());

					System.out.println("单词删除成功\n");
				} else {
					// 如果是句子则什么都不做
				}
			}
		}

		else {
			System.out.println("知识本体库中没有此实例");
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
						.contains("85")) {// 如果是句子则执行
					int i = 0;
					for (i = 0; i < sentencePropertyLabel.length; i++) {
						System.out.println("    ————"
								+ sentencePropertyLabel[i]
								+ "："
								+ substringManage(solutionInstance.get(
										sentencePropertySPARQLValue[i])
										.toString()));

						if (sentencePropertyLabel[i].equals("问题")
								|| sentencePropertyLabel[i].equals("问题句型")) {

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

					// 删除父类和Label
					deleteIndividual.deleteSentenceClass(
							solutionInstance.get("?instanceLabel").toString(),
							solutionInstance.get("?propertyClass").toString());
					deleteIndividual.deleteSentenceLabel(solutionInstance.get(
							"?instanceLabel").toString());

					System.out.println("句子删除成功\n");
				} else {
					// 如果是单词则什么都不做
				}

			}

		} else {
			System.out.println("知识本体库中没有此实例");
		}
	}

	@Override
	public void Modify(String yourID, String yourPropertyLabel,
			String yourSPARQLProperty, String yourRelationProperty) {

		// 查找对应的三元组
		// ResultSet result = queryWithManyWays.checkThisTriple(yourID,
		// yourPropertyLabel, "单词");
		ResultSet result = queryWithManyWays.checkPropertyDependOnId(yourID);
		QuerySolution solution = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				solution = result.next();
				if (yourPropertyLabel.equals("单词ID")) {

					modifyID(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel);

				} else if (yourPropertyLabel.equals("Hownet中的父类")) {

					modifyClass(solution, yourSPARQLProperty);

				} else if (yourPropertyLabel.equals("单词")) {

					modifyLabel(solution, yourID);
				} else {
					modifyPropertyValue(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel, yourID);
				}

			}
		} else {
			System.out.println("无此三元组");
		}

		System.out.println("修改成功");
	}

	@Override
	public void ModifySentence(String yourID, String yourPropertyLabel,
			String yourSPARQLProperty, String yourRelationProperty) {

		// 查找对应的三元组
		// ResultSet result = queryWithManyWays.checkThisTriple(yourID,
		// yourPropertyLabel, "单词");
		ResultSet result = queryWithManyWays
				.checkSentencePropertyDependOnId(yourID);
		QuerySolution solution = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				solution = result.next();
				if (yourPropertyLabel.equals("句子ID")) {

					modifySentenceID(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel);

				} else if (yourPropertyLabel.equals("问题句型")) {

					modifySentenceClass(solution, yourSPARQLProperty);

				} else if (yourPropertyLabel.equals("问题")) {

					modifySentenceLabel(solution, yourID);

				} else {
					modifySentencePropertyValue(solution, yourRelationProperty,
							yourSPARQLProperty, yourPropertyLabel, yourID);
				}

			}
			System.out.println("修改成功");
		} else {
			System.out.println("无此三元组");
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
				System.out.println("要添加的Excel中的内容" + str[j]);
			}

			String question1 = null;
			question1 = str[0];

			String question2 = null;
			question2 = str[1];
			// 用“_”代替“ ”
			question1 = question1.replace(" ", "_");
			question2 = question2.replace(" ", "_");
			System.out.println("question1:" + question1);
			System.out.println("question2:" + question2);

			// 判断question2是否在数据库中
			boolean ifInDB = queryWithManyWays.checkIfInDB(question2);
			String ID = null;
			if (ifInDB == false) {
				// 抽取question2父类(可调用王文辉接口找父类)在这里用我自己的笨方法
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
					System.out.println("没有找到该句子的父类");
				}

				// 若有缩写，如：What's
				// if (question2Class.contains("'")) {
				// question2Class = question2Class.substring(0,
				// question2Class.indexOf("'"));
				// }

				// 添加question2父类
				addIndividualAndProperty.addSentenceClass(question2Class,
						question2);

				// 添加标签Label
				addIndividualAndProperty.addSentenceLabel(question2);

				// 添加句子ID
				System.out.println("句子的版本");
//				Scanner sc = new Scanner(System.in);
//				String sentenceVersion = sc.nextLine();
				String sentenceVersion = str[2];
				System.out.println("句子的册数（1-12）");
//				sc = new Scanner(System.in);
//				String sentenceBook = sc.nextLine();
				String sentenceBook= str[3];
				System.out.println("句子的单元数");
//				sc = new Scanner(System.in);
//				String sentenceUnit = sc.nextLine();
				String sentenceUnit= str[4];
				ID = sentenceVersion + "/" + sentenceBook + "/" + sentenceUnit
						+ "/";

				int countID = 1;
				// 查找出所有三元组
				ResultSet resultAllTriples = queryWithManyWays.checkAllTriple();
				ResultSet resultInstanceAllProperty = null;
				QuerySolution solutionAllTriples = null;
				if (resultAllTriples.hasNext()) {
					while (resultAllTriples.hasNext()) {
						// QuerySolution next()
						// Moves onto the next result.
						// 移动到下个result上
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
											"85");// 查找该实例的所有ID
							if (resultInstanceAllProperty.hasNext()) {// 算出同版本、同册数、同单元的单词个数，计算ID
								while (resultInstanceAllProperty.hasNext()) {
									QuerySolution solution = resultInstanceAllProperty
											.next();
									if (solution.get("?allID").toString()
											.contains(ID)) {// 若同版本、同册数、同单元
										countID++;// 则countID加1
									} else {
										continue;
									}
								}
							} else {
								System.out
										.println("无同版本、同册数、同单元的单词，countID不变 = 1");
							}
							System.out.println("countID: " + countID);
						} else {
							continue;
						}

					}
				} else {
					System.out.println("无三元组");
				}

				System.out.println("最后的countID: " + countID);
				ID = ID + String.valueOf(countID);
				// 添加ID
				addIndividualAndProperty.addSentencePropertyForModify("句子ID",
						question2, ID, ID);

			} else {
				// 什么都不做
			}

			// 添加等价关系
			addIndividualAndProperty.addRelationSameAs(question1, question2);
			// addIndividualAndProperty.addRelationSameAs(question2, question1);
		}
		System.out.println("批量插入等价关系成功！");

	}

	@Override
	public ArrayList<String> ReasonSameAs(String yourSentence) {

		yourSentence = yourSentence.replace(" ", "_");

		InfModel inf = myReasoner.ReasonSameAs();

		// 通过标签找URI
		ResultSet result = queryWithManyWays.checkOnlyInstanceURI(yourSentence);

		System.out.println(yourSentence + " * * =>\n");
		Iterator list = null;
		if (result.hasNext()) {
			while (result.hasNext()) {
				// QuerySolution next()
				// Moves onto the next result.
				// 移动到下个result上
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

			for (String ss : listStringArray) {//分解出每一个三元组
				// System.out.println(ss);

				String str1 = ss.substring(0,ss.indexOf(","));
				System.out.println("str1————" + str1);
				allString.add(str1);
				index++;
				
				String str2 = ss.substring(ss.indexOf(",")+1, ss.length());
				//System.out.println("str2————" + str2);
				
				String str3 = str2.substring(0, str2.indexOf(","));
				System.out.println("str3————" + str3);
				allString.add(str3);
				index++;
				
				if(str2.substring((str2.indexOf(",")+1)).contains("\"")){
						String str4 = str2.substring(str2.indexOf("\"")+1, str2.lastIndexOf("\""));
						if(str4.contains(")")){
							str4 = str4.substring(str4.indexOf(")")+1);
						}
						System.out.println("str4————" + str4 + "\n");
						allString.add(str4);		
						index++;
				}else if(str2.substring((str2.indexOf(",")+1)).contains("'")){
					String str4 = str2.substring(str2.indexOf("\'")+1, str2.lastIndexOf("\'"));
					System.out.println("str4————" + str4 + "\n");
					allString.add(str4);		
					index++;
				}else{
					String str4 = str2.substring(str2.indexOf(",")+1);
					if(str4.contains(",")){
						str4 = str2.substring(str2.indexOf(",")+1, str2.indexOf(","));
					}
					System.out.println("str4————" + str4 + "\n");
					allString.add(str4);		
					index++;
				}
			}
		}
		
		ArrayList<String> returnString = new ArrayList();
		for (int i = 0; i < index; i++) {
			String everyString = allString.subList(i, i + 1).toString();
			if (i % 3 == 1) {

				//删除第一个空格
				if(everyString.contains("]")){					
					everyString = everyString.substring(everyString.indexOf(" ")+1, everyString.indexOf("]"));			
				}
				if (everyString.equals(RDFS.label.toString())) {
					returnString.add("句子Label");
				} else if (everyString.equals(RDF.type.toString())) {
					returnString.add("问题父类");

					everyString = allString.subList(i + 1, i + 2).toString();
					everyString = everyString.substring(everyString.indexOf(" ")+1,everyString.indexOf("]"));
					everyString = QueryLabelAndReturn(everyString);
					returnString.add(everyString);
					i++;

				} else if (everyString.equals(OWL.sameAs.toString())) {
					returnString.add("等价");

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

	// 静态方法-------------------------------------------------------------------------------------------------------------------------------------
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

	// 查找Hownet中的父类
	private static String findDEF(String W_e) {
		if (W_e.contains("@")) {
			W_e = subStringManage(W_e);
		}
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
		string = string.substring(0, string.indexOf("@"));
		return string;
	}

	// 修改单词ID
	private static void modifyID(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel) {
		// 删除该三元组
		deleteIndividual.deleteInstanceProperty(solution.get("?instanceLabel")
				.toString(), solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的属性值:");
		String yourModifyValue = sc.nextLine();
		String yourID = yourModifyValue;
		addIndividualAndProperty.addPropertyForModify(yourPropertyLabel,
				solution.get("?instanceLabel").toString(), yourModifyValue,
				yourID);

		for (int i = 0; i < propertyRelation.length; i++) {
			if (propertyLabel[i].equals("单词")
					|| propertyLabel[i].equals("Hownet中的父类")
					|| propertyLabel[i].equals("单词ID")) {
				// 什么都不做
			} else {

				// 保存之前的属性值
				String prePropertyValue = solution.get(propertySPARQLValue[i])
						.toString();
				System.out.println("prePropertyValue:" + prePropertyValue);
				String newPropertyValue = prePropertyValue
						.substring(prePropertyValue.indexOf(")") + 1);
				System.out.println("newPropertyValue:" + newPropertyValue);

				// 删除该三元组
				deleteIndividual.deleteInstanceProperty(
						solution.get("?instanceLabel").toString(), solution
								.get(propertyRelation[i]).toString(), solution
								.get(propertySPARQLValue[i]).toString());

				// 添加新的三元组
				addIndividualAndProperty.addPropertyForModify(propertyLabel[i],
						solution.get("?instanceLabel").toString(),
						newPropertyValue, yourID);

			}
		}
	}

	// 修改单词父类
	private static void modifyClass(QuerySolution solution,
			String yourSPARQLProperty) {
		Init();
		// 删除该三元组
		deleteIndividual.deleteClass(solution.get("?instanceLabel").toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		System.out.println("请选择你想修改的父类:");
		// 查找要添加的实例的Hownet父类
		String yourClass = findDEF(solution.get("?instanceLabel").toString());

		addIndividualAndProperty.addClass(yourClass,
				solution.get("?instanceLabel").toString());
	}

	// 修改单词Label
	private static void modifyLabel(QuerySolution solution, String yourID) {
		// 修改Label
		// 删除该三元组
		deleteIndividual.deleteLabel(solution.get("?instanceLabel").toString());
		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的属性值:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addLabel(yourModifyValue);

		// 修改类
		// 删除该三元组
		deleteIndividual.deleteClass(solution.get("?instanceLabel").toString(),
				solution.get("?propertyClass").toString());
		// 添加新的三元组
		addIndividualAndProperty.addClass(solution.get("?propertyClass")
				.toString(), yourModifyValue);

		for (int i = 0; i < propertyRelation.length; i++) {
			if (propertyLabel[i].equals("单词")
					|| propertyLabel[i].equals("Hownet中的父类")) {
				// 什么都不做
			} else {

				// 删除相应属性的三元组
				deleteIndividual.deleteInstanceProperty(
						solution.get("?instanceLabel").toString(), solution
								.get(propertyRelation[i]).toString(), solution
								.get(propertySPARQLValue[i]).toString());

				// 添加相应属性的三元组
				addIndividualAndProperty.addPropertyForModify(propertyLabel[i],
						yourModifyValue, solution.get(propertySPARQLValue[i])
								.toString(), yourID);

			}
		}
	}

	// 修改单词属性
	private static void modifyPropertyValue(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel, String yourID) {
		// 删除该三元组
		deleteIndividual.deleteInstanceProperty(solution.get("?instanceLabel")
				.toString(), solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的单词属性:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addPropertyForModify(yourPropertyLabel,
				solution.get("?instanceLabel").toString(), yourModifyValue,
				yourID);
	}

	// 修改句子ID
	private static void modifySentenceID(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel) {
		// 删除该三元组
		deleteIndividual.deleteSentenceInstanceProperty(
				solution.get("?instanceLabel").toString(),
				solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的属性值:");
		String yourModifyValue = sc.nextLine();
		String yourID = yourModifyValue;
		addIndividualAndProperty.addSentencePropertyForModify(
				yourPropertyLabel, solution.get("?instanceLabel").toString(),
				yourModifyValue, yourID);

		for (int i = 0; i < sentencePropertyRelation.length; i++) {
			if (sentencePropertyLabel[i].equals("问题")
					|| sentencePropertyLabel[i].equals("问题句型")
					|| sentencePropertyLabel[i].equals("句子ID")) {
				// 什么都不做
			} else {

				// 保存之前的属性值
				String prePropertyValue = solution.get(
						sentencePropertySPARQLValue[i]).toString();
				System.out.println("prePropertyValue:" + prePropertyValue);
				String newPropertyValue = prePropertyValue
						.substring(prePropertyValue.indexOf(")") + 1);
				System.out.println("newPropertyValue:" + newPropertyValue);

				// 删除该三元组
				deleteIndividual
						.deleteSentenceInstanceProperty(
								solution.get("?instanceLabel").toString(),
								solution.get(sentencePropertyRelation[i])
										.toString(),
								solution.get(sentencePropertySPARQLValue[i])
										.toString());

				// 添加新的三元组
				addIndividualAndProperty.addSentencePropertyForModify(
						sentencePropertyLabel[i], solution
								.get("?instanceLabel").toString(),
						newPropertyValue, yourID);

			}
		}
	}

	// 修改句子父类
	private static void modifySentenceClass(QuerySolution solution,
			String yourSPARQLProperty) {
		// 删除该三元组
		deleteIndividual.deleteSentenceClass(solution.get("?instanceLabel")
				.toString(), solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		System.out.println("请选择你想修改的问题句型:");
		// 查找要添加的实例的Hownet父类
		String yourClass = solution
				.get("?instanceLabel")
				.toString()
				.substring(0,
						solution.get("?instanceLabel").toString().indexOf("_"));
		// 若有缩写，如：What's
		if (yourClass.contains("'")) {
			yourClass = yourClass.substring(0, yourClass.indexOf("'"));
		}

		addIndividualAndProperty.addSentenceClass(yourClass,
				solution.get("?instanceLabel").toString());
	}

	// 修改句子Label
	private static void modifySentenceLabel(QuerySolution solution,
			String yourID) {
		// 修改Label
		// 删除该三元组
		deleteIndividual.deleteSentenceLabel(solution.get("?instanceLabel")
				.toString());
		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的问题:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addSentenceLabel(yourModifyValue);

		// 修改类
		// 删除该三元组
		deleteIndividual.deleteSentenceClass(solution.get("?instanceLabel")
				.toString(), solution.get("?propertyClass").toString());
		// 添加新的三元组
		addIndividualAndProperty.addSentenceClass(solution
				.get("?propertyClass").toString(), yourModifyValue);

		for (int i = 0; i < sentencePropertyRelation.length; i++) {
			if (sentencePropertyLabel[i].equals("问题")
					|| sentencePropertyLabel[i].equals("问题句型")) {
				// 什么都不做
			} else {

				// 删除相应属性的三元组
				deleteIndividual
						.deleteSentenceInstanceProperty(
								solution.get("?instanceLabel").toString(),
								solution.get(sentencePropertyRelation[i])
										.toString(),
								solution.get(sentencePropertySPARQLValue[i])
										.toString());

				// 添加相应属性的三元组
				addIndividualAndProperty
						.addSentencePropertyForModify(sentencePropertyLabel[i],
								yourModifyValue,
								solution.get(sentencePropertySPARQLValue[i])
										.toString(), yourID);

			}
		}
	}

	// 修改句子属性
	private static void modifySentencePropertyValue(QuerySolution solution,
			String yourRelationProperty, String yourSPARQLProperty,
			String yourPropertyLabel, String yourID) {
		// 删除该三元组
		deleteIndividual.deleteSentenceInstanceProperty(
				solution.get("?instanceLabel").toString(),
				solution.get(yourRelationProperty).toString(),
				solution.get(yourSPARQLProperty).toString());

		// 添加新的三元组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你想修改的句子属性:");
		String yourModifyValue = sc.nextLine();
		addIndividualAndProperty.addSentencePropertyForModify(
				yourPropertyLabel, solution.get("?instanceLabel").toString(),
				yourModifyValue, yourID);
	}

	// 读取Excel，返回字符串数组
	private static List<String[]> readExcel(InputStream yourPath)
			throws BiffException, IOException {
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;
		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(yourPath);

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
		return list;
	}

	// 查询Label，并返回完整无杂质Label
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
