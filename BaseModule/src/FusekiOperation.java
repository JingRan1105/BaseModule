import java.io.IOException;

import jxl.read.biff.BiffException;

public class FusekiOperation {

	public void Add() {
		System.out.println("���");
	}
	
	public void AddBatch() throws BiffException, IOException {
		System.out.println("�����������");
	}
	
	public void AddSentenceBatch() throws BiffException, IOException {
		System.out.println("�����������");
	}

	public void Delete() {
		System.out.println("ɾ��");
	}

	public void Modify() {
		System.out.println("�޸�");
	}

	public void QueryWord() {
		System.out.println("��ѯ����");
	}
	
	public void QueryWordPropertyValue() {
		System.out.println("��ѯ��������");
	}
	
	public void QueryIndividualDependOnId() {
		System.out.println("����ID��ѯ���ʼ�������");
	}
	
	public void QueryIndividualSentenceDependOnId(){
		System.out.println("����ID��ѯ���Ӽ�������");
	}
	
	public void WriteBackToOwl() throws IOException{
		System.out.println("��Fuseki�е�����д��.owl�ļ�");
	}
}
