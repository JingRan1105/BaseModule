/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package similarity.hownet2000.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import similarity.hownet2000.concept.MyConceptParser;

import java.util.Scanner;
import java.util.Vector;

/**
 * 
 * @author Administrator
 */
public class Interface_Xia {

	public String Keyword1;
	public ArrayList<String> Keyword1_AllMeans = new ArrayList<String>();
	public String Keyword2;
	public ArrayList<String> Keyword2_AllMeans = new ArrayList<String>();
	public double a_Value;
	public double b1_Value;
	public double b2_Value;
	public double b3_Value;
	public double b4_Value;
	public int Sense_flag1;// ���ڱ�ʶKeyword1�Ƿ�Ϊδ��¼�ʣ�flag1=0��Keyword1Ϊδ��¼�ʣ�flag1=1��Keyword1Ϊ�ѵ�¼��
	public int Sense_flag2;// ���ڱ�ʶKeyword2�Ƿ�Ϊδ��¼�ʣ�flag2=0��Keyword2Ϊδ��¼�ʣ�flag2=1��Keyword2Ϊ�ѵ�¼��
	public int Sememe_flag1;
	public int Sememe_flag2;
	public String Keyword1_SelectMean;
	public String Keyword2_SelectMean;
	public int type;

	public void findFather() {// �鿴����1
		boolean isNewWord = true;

		// �����������
		Scanner sc = new Scanner(System.in);

		// ��ȡ�û�������ַ���
		System.out.print("�����������ַ�:");
		Keyword1 = sc.nextLine();
		System.out.println("��������ַ�Ϊ:" + Keyword1);

		// for (int i = 0; i < Keyword1_ComboBox.getItemCount(); i++) {
		// if (Keyword1_ComboBox.getItemAt(i).equals(Keyword1)) {
		// isNewWord = false;
		// break;
		// }
		// }
		// if (isNewWord) {
		// Keyword1_ComboBox.addItem(Keyword1);//����������ݼ�����Ͽ�������˵���
		// }
		// HowNet myHowNet = new HowNet();
		Vector<String> v = new Vector<String>();
		if (MyConceptParser.getInstance().isConcept(Keyword1)) {
			Sense_flag1 = 1;
			Collection<String> result1 = MyConceptParser.getInstance()
					.getDefinitions(Keyword1);
			for (String temp : result1) {
				System.out.println("-------" + temp);
				v.add(temp);
			}

		} else {
			Sense_flag1 = 0;
			v.add("δ��¼�ʣ���Ҫ������ϸ���!");
			// System.out.println("Not Found!");
		}
	}

	public static void main(String[] args) {
		Interface_Xia myInterface = new Interface_Xia();
		myInterface.findFather();
	}
}
