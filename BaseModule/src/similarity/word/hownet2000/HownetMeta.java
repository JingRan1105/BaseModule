package similarity.word.hownet2000;

/**
 * Metadata for Hownet2000
 *
 * @author �ڻ�
 */
public interface HownetMeta {

    /** Algorithm of XIA Tian */
    public static final int ALGORITHM_XIA = 1;
    /** Algorithm of LIU Qun */
    public static final int ALGORITHM_LIU = 2;
    /**
     * Hownet symbol descriptions
     */
    public static final String Symbol_Descriptions[][] = {
        {
            "#", "��ʾ�������"}, {
            "%", "���䲿��"}, {
            "$", "���Ա���V���ã����Ǹ�V�����¡����������������"}, {
            "*", "ʩ�»򹤾�"}, {
            "+", "����ǵĽ�ɫ�����Եģ�������ʵ�������в������"}, {
            "&", "ָ��"}, {
            "~", "����ǣ�����У��ܿ���"}, {
            "@", "������V�Ŀռ��ʱ��"}, {
            "?", "����ʹN�Ĳ���"}, {
            "(", "�������е�Ӧ����һ���ʱ��"}, {
            "^", "�����ڣ���û�У�����"}, {
            "!", "��ʾĳһ����Ϊһ���е����ԣ���ζ��֮��ʳ��"}, {
            "[", "��ʾ����Ĺ�������"}
    };
    /** �ã����������Ԫ�����ƶ�һ��Ϊһ����С�ĳ��� */
    public static final double gamma = 0.2;
    /** ��:��һ���ǿ�ֵ���ֵ�����ƶ�Ϊһ����С�ĳ������˴�Ϊ0.2 */
    public static final double delta = 0.2;
    //��1ʵ�ʸ����һ������ԭ����ʽ��Ȩ��
    /*public static final double beta1 = 0.5;
    //��2ʵ�ʸ�������������ԭ����ʽ��Ȩ��
    public static final double beta2 = 0.2;
    //��3ʵ�ʸ����ϵ��ԭ����ʽ��Ȩ��
    public static final double beta3 = 0.17;
    //��4ʵ�ʸ��������ԭ����ʽ��Ȩ��
    public static final double beta4 = 0.13;*/
    /**
     * �� �������������ԭ����ո���������ԭ��������ƶ�, ������������������ԭ���ƶȵĻ�(����ԭͨ���÷�ʽ��Լ������),
     * �����ֵ���ڸ�ֵʱ�Ż����������, ȥ������Ĳ���Ҫ��ԭ
     */
    public static final double PARAM_THETA = 0.5;
    /**
     * �� ����ǰ��������ԭ����ո���������ԭ��������ƶȣ�������������������ԭ���ƶȵĻ�(����ԭͨ���÷�ʽ��Լ������),
     * �����ֵ���ڸ�ֵʱ�Ż����ǰ��������ԭ����, ������������
     */
    public static final double PARAM_OMEGA = 0.8;
    /** */
    public static final double PARAM_XI = 0.6;
}
