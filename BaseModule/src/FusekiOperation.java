import java.io.IOException;

import jxl.read.biff.BiffException;

public class FusekiOperation {

	public void Add() {
		System.out.println("添加");
	}
	
	public void AddBatch() throws BiffException, IOException {
		System.out.println("单词批量添加");
	}
	
	public void AddSentenceBatch() throws BiffException, IOException {
		System.out.println("句子批量添加");
	}

	public void Delete() {
		System.out.println("删除");
	}

	public void Modify() {
		System.out.println("修改");
	}

	public void QueryWord() {
		System.out.println("查询单词");
	}
	
	public void QueryWordPropertyValue() {
		System.out.println("查询单词属性");
	}
	
	public void QueryIndividualDependOnId() {
		System.out.println("根据ID查询单词及其属性");
	}
	
	public void QueryIndividualSentenceDependOnId(){
		System.out.println("根据ID查询句子及其属性");
	}
	
	public void WriteBackToOwl() throws IOException{
		System.out.println("把Fuseki中的内容写回.owl文件");
	}
}
