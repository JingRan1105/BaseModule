package similarity.hownet2000.util;

import java.util.Collection;

/**
 * �ж��Ƿ�Ϊ�յĹ�����
 * 
 * @author �ڻ�
 */
public class BlankUtils {
	/**
	 * �ж��ַ���s�Ƿ��ǿմ�
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String string){
		return string==null || string.trim().equals("");
	}	
	
	/**
	 * �ж������Ƿ��ǿ�
	 * @param array
	 * @return
	 */
	public static boolean isBlank(Object[] array){
		return array==null || array.length==0;
	}
	
	/**
	 * �жϼ����Ƿ��ǿ�
	 * @param array
	 * @return
	 */
	public static boolean isBlank(Collection<? extends Object> array){
		return array==null || array.size()==0;
	}
	
	/**
	 * �ж����еļ����Ƿ�Ϊ��
	 * @param collections 
	 * @return
	 */
	public static boolean isBlankAll(Collection<?>...collections){
		for(Collection<?> c:collections){
			if(!isBlank(c)){
				return false;
			}
		}

		return true;	
	}
	
	/**
	 * �ж��ַ���strings���Ƿ��ǿմ�
	 * @param strings
	 * @return
	 */
	public static boolean isBlankAll(String... strings){
		for(String s:strings){
			if(!isBlank(s)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * �ж�collections�������Ƿ�������һ��Ϊ��
	 * @param collections
	 * @return
	 */
	public static boolean isBlankAtLeastOne(Collection<?>...collections){
		for(Collection<?> c:collections){
			if(isBlank(c)){
				return true;
			}
		}

		return false;	
	}
	
	/**
	 * �ж��ַ���strings���Ƿ�֮����һ��Ϊ��
	 * @param strings
	 * @return
	 */
	public static boolean isBlankAtLeastOne(String... strings){
		for(String s:strings){
			if(isBlank(s)){
				return true;
			}
		}
		
		return false;
	}
}
