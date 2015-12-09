package similarity.hownet2000.sememe;

import java.io.IOException;

import similarity.hownet2000.util.BlankUtils;

/**
 * ��������ȡ�ļ�����ԭ���ƶȵķ���, ʵ����SememeParser�ж���ĳ��󷽷�
 * 
 * @author �ڻ�
 */
public class MySememeParser extends SememeParser {

    public MySememeParser() throws IOException {
        super();
    }

    /**
     * ����������ԭ�����ƶ�
     */
    @Override
    public double getSimilarity(final Sememe sememe1, final Sememe sememe2) {
        Sememe sem1 = sememe1;
        Sememe sem2 = sememe2;

        if (sememe1 == null || sememe2 == null) {
            return 0.0f;
        } else if (sememe1.getId() == sememe2.getId()) {
            return 1.0f;
        }

        //��Ϊ�����ͬ��Ȼ��һ�����ҹ�ͬ�ĸ��ڵ�
        int level = sememe1.getDepth() - sememe2.getDepth();
        for (int i = 0; i < ((level < 0) ? level * -1 : level); i++) {
            if (level > 0) {
                sem1 = SEMEMES[sem1.getParentId()];
            } else {
                sem2 = SEMEMES[sem2.getParentId()];
            }
        }

        while (sem1.getId() != sem2.getId()) {
            // �����һ���Ѿ�������ڵ㣬��Ȼ��ͬ���򷵻�0
            if (sem1.getId() == sem1.getParentId() || sem2.getId() == sem2.getParentId()) {
                return 0.0f;
            }

            sem1 = SEMEMES[sem1.getParentId()];
            sem2 = SEMEMES[sem2.getParentId()];
        }

        System.out.println("Spd="+sem1.getDepth());
        System.out.println("Depth1="+sememe1.getDepth());
        System.out.println("Depth2="+sememe2.getDepth());
        return sem1.getDepth() * 2.0f / (sememe1.getDepth() + sememe2.getDepth());
    }

    /**
     * ����������Ԫ֮������ƶȣ�������Ԫ������ͬ��������Ϊ�������ƶ������ similarity = alpha/(distance+alpha),
     * ��������ַ�����ͬ��Ϊ�գ�ֱ�ӷ���1.0
     *
     * @param key1 ��һ����ԭ�ַ���
     * @param key2 �ڶ�����ԭ�ַ���
     * @return
     */
    @Override
    public double getSimilarity(String item1, String item2) {
        if (BlankUtils.isBlankAll(item2, item2)) {
            return 1.0;
        } else if (BlankUtils.isBlankAtLeastOne(item1, item2)) {
            return 0.0;
        } else if (item1.equals(item2)) {
            return 1.0;
        }

        String key1 = item1.trim();
        String key2 = item2.trim();

        // ȥ��()����
        if ((key1.charAt(0) == '(') && (key1.charAt(key1.length() - 1) == ')')) {

            if (key2.charAt(0) == '(' && key2.charAt(key2.length() - 1) == ')') {
                key1 = key1.substring(1, key1.length() - 1);
                key2 = key2.substring(1, key2.length() - 1);
            } else {
                return 0.0;
            }

        }

        // �����ϵ��Ԫ,��x=y�����
        int pos = key1.indexOf('=');
        if (pos > 0) {
            int pos2 = key2.indexOf('=');
            // ����ǹ�ϵ��Ԫ�����ж�ǰ�沿���Ƿ���ͬ�������ͬ����תΪ������沿�ֵ����ƶȣ�����Ϊ0
            if ((pos == pos2) && key1.substring(0, pos).equals(key2.substring(0, pos2))) {
                key1 = key1.substring(pos + 1);
                key2 = key2.substring(pos2 + 1);
            } else {
                return 0.0;
            }
        }

        // ���������Ԫ,��ǰ����������ŵ���Ԫ
        String symbol1 = key1.substring(0, 1);
        String symbol2 = key2.substring(0, 1);

        for (int i = 0; i < Symbol_Descriptions.length; i++) {
            if (symbol1.equals(Symbol_Descriptions[i][0])) {
                if (symbol1.equals(symbol2)) {
                    key1 = item1.substring(1);
                    key2 = item2.substring(1);
                    break;
                } else {
                    return 0.0; // �������ͬһ��ϵ���ţ������ƶ�ֱ�ӷ���0
                }
            }
        }

        if ((pos = key1.indexOf("|")) >= 0) {
            key1 = key1.substring(pos + 1);
        }
        if ((pos = key2.indexOf("|")) >= 0) {
            key2 = key2.substring(pos + 1);
        }

        // ��������ַ�����ȣ�ֱ�ӷ��ؾ���Ϊ0
        if (key1.equals(key2)) {
            return 1.0;
        }

        Integer[] myset1 = getSememes(key1);
        Integer[] myset2 = getSememes(key2);

        double similarity = 0.0;
        for (int id1 : myset1) {
            for (int id2 : myset2) {
                double s = getSimilarity(SEMEMES[id1], SEMEMES[id2]);
                if (s > similarity) {
                    similarity = s;
                }
            }
        }

        return similarity;
    }

    public static void main(String[] args) throws IOException {
        MySememeParser mySememeParser = new MySememeParser();
        System.out.println(mySememeParser.isSememe("��"));
        System.out.println(mySememeParser.getPath("��"));
        System.out.println(mySememeParser.isSememe("ˮ��"));
        System.out.println(mySememeParser.getPath("ˮ��"));
        System.out.println(mySememeParser.getSimilarity("��", "ˮ��"));
    }
}