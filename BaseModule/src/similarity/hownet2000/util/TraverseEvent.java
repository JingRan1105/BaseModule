package similarity.hownet2000.util;

/**
 * �����ӿ�, ������Ҫ�����Ķ�����ͨ������ýӿڣ�����ʵ��ʵ�ʵķ��ʴ���
 * 
 * @author �ڻ�
 * 
 * @param <T>
 */
public interface TraverseEvent<T> {
	
	/** 
	 * ����ʱ�������е�һ����Ŀ
	 * @param item
	 * @return
	 */
	public boolean visit(T item);
}
