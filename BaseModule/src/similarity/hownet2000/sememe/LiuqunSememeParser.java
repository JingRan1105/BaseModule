package similarity.hownet2000.sememe;

import java.io.IOException;

/**
 * ��Ⱥ��ʦ������ԭ���ƶȵķ���, ʵ����SememeParser�ж���ĳ��󷽷�
 *
 * @author �ڻ�
 */
public class LiuqunSememeParser extends SememeParser {

    /** ������Ԫ���ƶȵĿɵ��ڵĲ�����Ĭ��Ϊ1.6 */
    private static double alpha;

    public LiuqunSememeParser() throws IOException {
        super();
    }

    public void setParameter(double a){
        alpha=a;
    }

    /**
     * ����������Ԫ֮������ƶȣ�������Ԫ������ͬ��������Ϊ�������ƶ������
     * <br/>similarity = alpha/(distance+alpha)
     *
     * @param key1
     * @param key2
     * @return
     */
    @Override
    public double getSimilarity(String item1, String item2) {
        int pos;

        // ���Ϊ�մ���ֱ�ӷ���0
        if (item1 == null || item2 == null || item1.equals("") || item2.equals("")) {
            return 0.0;
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
        if ((pos = key1.indexOf('=')) > 0) {
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

        int distance = getDistance(key1, key2);
        if (distance < 0) {
            return 0.0;
        } else {
            return alpha / (distance + alpha);
        }
    }

    @Override
    public double getSimilarity(Sememe sem1, Sememe sem2) {
        int distance = getDistance(sem1, sem2);
        if (distance <= 0) {
            return 0.0f;
        } else {
            return alpha / (distance + alpha);
        }
    }

    public static void main(String[] args) throws IOException {
        LiuqunSememeParser myLiuqunSememeParser = new LiuqunSememeParser();
        System.out.println(myLiuqunSememeParser.getPath("��"));
        System.out.println(myLiuqunSememeParser.getPath("ˮ��"));
        myLiuqunSememeParser.setParameter(1.6);
        System.out.println(myLiuqunSememeParser.getSimilarity("��", "ˮ��"));
    }
}
