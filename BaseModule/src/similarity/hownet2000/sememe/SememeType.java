package similarity.hownet2000.sememe;

/**
 * ��ԭ�����Ͷ���<br/>
 * <ul>
 * <li>1��Event|�¼�</li>
 * <li>2��Entity|ʵ�� </li>
 * <li>3:Attribute|���� </li>
 * <li>4��Quantity|����</li>
 * <li>5��aValue|����ֵ</li>
 * <li>6��qValue|����ֵ</li>
 * <li>7: Secondary Feature|�ڶ�����</li>
 * <li>8: Syntax|�﷨</li>
 * <li>9: EventRole|��̬��ɫ</li>
 * <li>10:EventFeatures|��̬����</li>
 * <li>0��δ֪</li>
 * </ul>
 * 
 * ����1~7Ϊ������Ԫ��8Ϊ�﷨��Ԫ��9��10Ϊ��ϵ��Ԫ<br/>
 *
 * @author �ڻ�
 */
public interface SememeType {	 
	  /** Event|�¼����Ͷ��� */
	  public static final int Event = 1;
	  
	  /** Entity|ʵ�����Ͷ���*/
	  public static final int Entity = 2;
	  
	  /** Attribute|�������Ͷ���*/
	  public static final int Attribute = 3;

	  /** Quantity|�������Ͷ���*/
	  public static final int Quantity = 4;

	  /** aValue|����ֵ���Ͷ���*/
	  public static final int AValue = 5;

	  /** qValue|����ֵ���Ͷ���*/
	  public static final int QValue = 6;
	  
	  /** Secondary Feature|�ڶ��������Ͷ���*/
	  public static final int SecondaryFeature = 7;
	  
	  /** Syntax|�﷨���Ͷ���*/
	  public static final int Syntax = 8;
	  
	  /** EventRole|��̬��ɫ���Ͷ���*/
	  public static final int EventRoleAndFeature = 9;
	  
	  /** EventFeatures|��̬�������Ͷ���*/
	  public static final int EventFeature = 10;
	  
	  /** δ֪���Ͷ���*/
	  public static final int Unknown = 0;
	  
}
