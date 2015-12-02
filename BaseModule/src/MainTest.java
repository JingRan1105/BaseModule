
import java.io.IOException;
import java.util.Scanner;

import jxl.read.biff.BiffException;

import AddDeleteModifyQuery.Add.AddIndividualAndProperty;
import AddDeleteModifyQuery.Add.Impl.AddIndividualAndPropertyImpl;
import AddDeleteModifyQuery.Delete.DeleteIndividual;
import AddDeleteModifyQuery.Delete.Impl.DeleteIndividualImpl;
import AddDeleteModifyQuery.Modify.ModifyPropertyValue;
import AddDeleteModifyQuery.Modify.Impl.ModifyPropertyValueImpl;
import AddDeleteModifyQuery.Query.QueryIndividual;
import AddDeleteModifyQuery.Query.QueryDependOnId;
import AddDeleteModifyQuery.Query.QuerySentenceDependOnId;
import AddDeleteModifyQuery.Query.Impl.QueryIndividualImpl;
import AddDeleteModifyQuery.Query.Impl.QueryDependOnIdImpl;
import AddDeleteModifyQuery.Query.Impl.QuerySentenceDependOnIdImpl;
import AddDeleteModifyQuery.WriteOwl.WriteOwlImpl.WriteOwlImpl;

public class MainTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws BiffException
	 */
	public static void main(String[] args) throws BiffException, IOException {
		AddIndividualAndProperty addIndividualAndProperty = new AddIndividualAndPropertyImpl();
		DeleteIndividual deleteIndividual = new DeleteIndividualImpl();
		ModifyPropertyValue modifyPropertyValue = new ModifyPropertyValueImpl();
		QueryIndividual queryIndividual = new QueryIndividualImpl();
		QueryDependOnId queryDepandOnId = new QueryDependOnIdImpl();
		QuerySentenceDependOnId querySentenceDepandOnId = new QuerySentenceDependOnIdImpl();
		WriteOwlImpl writeOwl = new WriteOwlImpl();
		
		System.out.println("请选择您将要执行的操作（输入1-10）：");
		System.out.println("1.添加单词");
		System.out.println("2.批量添加单词");
		System.out.println("3.批量添加句子");
		System.out.println("4.删除单词");
		System.out.println("5.删除句子");
		System.out.println("6.修改单词");
		System.out.println("7.根据类查看所有单词");
		System.out.println("8.根据ID查看单词及其属性");
		System.out.println("9.根据类查看所有句子");
		System.out.println("10.根据ID查看句子及其属性");
		System.out.println("11.把Fuseki数据库中的数据写回owl文件中");
		Scanner sc = new Scanner(System.in);
		int operation = sc.nextInt();
		switch (operation) {
		case 1:
			Adapter adapterAdd = new Adapter(addIndividualAndProperty);
			adapterAdd.Add();
			break;
		case 2:
			Adapter adapterAddBatch = new Adapter(addIndividualAndProperty);
			adapterAddBatch.AddBatch();
			break;
		case 3:
			Adapter adapterAddSentenceBatch = new Adapter(
					addIndividualAndProperty);
			adapterAddSentenceBatch.AddSentenceBatch();
			break;
		case 4:
			Adapter adapterDelete = new Adapter(deleteIndividual);
			adapterDelete.Delete();
			break;
		//case 5:
		case 6:
			Adapter adapterModify = new Adapter(modifyPropertyValue);
			adapterModify.Modify();
			break;
		case 7:
			Adapter adapterQuery = new Adapter(queryIndividual);
			adapterQuery.QueryWord();
			break;
		case 8:
			Adapter adapterQueryDependOnId = new Adapter(queryDepandOnId);
			adapterQueryDependOnId.QueryIndividualDependOnId();
			break;
		case 9:
			Adapter adapterQueryAllSentence = new Adapter(queryIndividual);
			adapterQueryAllSentence.QueryWord();
			break;
		case 10:
			Adapter adapterQuerySentenceDependOnId = new Adapter(
					querySentenceDepandOnId);
			adapterQuerySentenceDependOnId.QueryIndividualSentenceDependOnId();
			break;
		case 11:
			Adapter adapterWriteOwl = new Adapter(
					writeOwl);
			adapterWriteOwl.WriteBackToOwl();
			break;
		default:
			System.out.println("请输入1到11");
		}
	}
}
