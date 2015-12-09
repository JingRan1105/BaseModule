package similarity.hownet2000.concept;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import similarity.word.hownet2000.HownetMeta;

/**
 * ֪���ĸ����ʾ�� 
 * 
 * @author �ڻ�
 */
public class Concept implements HownetMeta, Comparable<Concept> {

    /** ���ĸ������� */
    protected String word;
    /** ����: Part of Speech */
    protected String pos;
    /** ���� */
    protected String define;
    /** �Ƿ���ʵ�ʣ�false��ʾΪ���, һ��Ϊʵ�� */
    protected boolean bSubstantive;
    /** ��һ������ԭ */
    protected String mainSememe;
    /** ����������ԭ */
    protected String[] secondSememes;
    /** ��ϵ��Ԫԭ */
    protected String[] relationSememes;
    /** ��ϵ�������� */
    protected String[] symbolSememes;
    static String[][] Concept_Type = {{"=", "�¼�"},
        {"aValue|����ֵ", "����ֵ"}, {"qValue|����ֵ", "����ֵ"},
        {"attribute|����", "����"}, {"quantity|����", "����"},
        {"unit|", "��λ"}, {"%", "����"}};

    public Concept(String word, String pos, String def) {
        this.word = word;
        this.pos = pos;
        this.define = (def == null) ? "" : def.trim();

        // �����{***}��ʾ
        if (define.length() > 0 && define.charAt(0) == '{' && define.charAt(define.length() - 1) == '}') {
            this.bSubstantive = false;
        } else {
            this.bSubstantive = true;
        }

        parseDefine();
    }

    /**
     * �����壬�Ѷ����Ϊ��һ������Ԫ������������Ԫ����ϵ��Ԫ�ͷ�����Ԫ����
     */
    private void parseDefine() {
        List<String> secondList = new ArrayList<String>(); 		//����������ԭ
        List<String> relationList = new ArrayList<String>(); 	//��ϵ��ԭ
        List<String> symbolList = new ArrayList<String>(); 		//������ԭ

        String tokenString = this.define;

        //�������ʵ�ʣ�����{}���е�����
        if (!this.bSubstantive) {
            tokenString = define.substring(1, define.length() - 1);
        }

        StringTokenizer token = new StringTokenizer(tokenString, ",", false);

        // ��һ��Ϊ��һ������Ԫ
        if (token.hasMoreTokens()) {
            this.mainSememe = token.nextToken();
        }

        main_loop:
        while (token.hasMoreTokens()) {
            String item = token.nextToken();
            if (item.equals("")) {
                continue;
            }

            // ���ж��Ƿ�Ϊ������Ԫ
            String symbol = item.substring(0, 1);
            for (int i = 0; i < Symbol_Descriptions.length; i++) {
                if (symbol.equals(Symbol_Descriptions[i][0])) {
                    symbolList.add(item);
                    continue main_loop;
                }
            }

            //������Ƿ�����Ԫ�����һ���ж��ǹ�ϵ��Ԫ���ǵڶ�������Ԫ, ���С�=����ʾ��ϵ��ԭ
            if (item.indexOf('=') > 0) {
                relationList.add(item);
            } else {
                secondList.add(item);
            }
        }

        this.secondSememes = secondList.toArray(new String[secondList.size()]);
        this.relationSememes = relationList.toArray(new String[relationList.size()]);
        this.symbolSememes = symbolList.toArray(new String[symbolList.size()]);
    }

    /**
     * ��ȡ��һ��Ԫ
     *
     * @return
     */
    public String getMainSememe() {
        return mainSememe;
    }

    /**
     * ��ȡ����������Ԫ����
     *
     * @return
     */
    public String[] getSecondSememes() {
        return secondSememes;
    }

    /**
     * ��ȡ��ϵ��Ԫ����
     *
     * @return
     */
    public String[] getRelationSememes() {
        return relationSememes;
    }

    /**
     * ��ȡ������Ԫ����
     *
     * @return
     */
    public String[] getSymbolSememes() {
        return symbolSememes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name=");
        sb.append(this.word);
        sb.append("; pos=");
        sb.append(this.pos);
        sb.append("; define=");
        sb.append(this.define);
        sb.append("; ��һ������Ԫ:[" + mainSememe);

        sb.append("]; ����������Ԫ����:[");
        for (String sem : secondSememes) {
            sb.append(sem);
            sb.append(";");
        }

        sb.append("]; [��ϵ��Ԫ����:");
        for (String sem : relationSememes) {
            sb.append(sem);
            sb.append(";");
        }

        sb.append("]; [��ϵ��������:");
        for (String sem : symbolSememes) {
            sb.append(sem);
            sb.append(";");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * ��ʵ�ʻ������
     *
     * @return true:ʵ�ʣ�false:���
     */
    public boolean isSubstantive() {
        return this.bSubstantive;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDefine() {
        return define;
    }

    public void setDefine(String define) {
        this.define = define;
    }

    /**
     * ��ȡ�ø��������
     *
     * @return
     */
    public String getType() {
        for (int i = 0; i < Concept_Type.length; i++) {
            if (define.toUpperCase().indexOf(Concept_Type[i][0].toUpperCase()) >= 0) {
                return Concept_Type[i][1];
            }
        }
        return "��ͨ����";
    }

    /**
     * ���ո�������ƽ��бȽ�
     */
    public int compareTo(Concept o) {
        return word.compareTo(o.word);
    }

    //////////////////////////////////////////////
    /**
     * ������parse�бȽϸ���������ķ���
     * @param another
     * @return
     */
    public int compareTo(String another) {
        return word.compareTo(another);
    }

    public boolean equals(String another) {
        return word.equals(another);
    }
}