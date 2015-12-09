package similarity.hownet2000.sememe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * һ���µ�Map������׼��Map��ͬ�����ĵ�Key�������ظ�, �ڲ����ÿ�������Ͷ��ֲ���,
 * ���ֽ��ٵı������ṹ�򵥣��ɸ����������ҷ��صĽ����һ������
 * 
 * 
 * @param <T>
 * @param <V>
 * @author �ڻ�
 */
public class FastSimpleMap<K extends Comparable<K>, V> {
	private K[] keys;
	private V[] values;
	
	public FastSimpleMap(K[] keys, V[] values) throws IOException{
		if(keys.length!=values.length){
			throw new IOException("keys length must be equals values");
		}
		this.keys = keys;
		this.values = values;
		
		// ����keys��������
		quicksort(0, keys.length-1);
	}
	
	/**
	 * ���Ҽ���Ӧ��ֵ����
	 * @param key
	 * @return
	 */
	public Collection<V> get(K key) {
		int low = 0;
		int high = keys.length - 1;
		
		Collection<V> results = new ArrayList<V>();

		while (low <= high) {
			int mid = (low + high) >> 1;
			K item = keys[mid];
			int cmp = key.compareTo(item);
			
			if (cmp > 0) {
				low = mid + 1;
			} else if (cmp < 0) {
				high = mid - 1;
			} else {				
				// �ҵ���ʼλ�ã���λ��ǰ����ͬ�Ķ��Ǹ�������Ӧ��ֵ
				for(int i=mid;i>=0 && keys[i].equals(key); i--){
					results.add(values[i]);
				}				
				for(int i=mid+1; i<keys.length && keys[i].equals(key); i++){
					results.add(values[i]);
				}
				
				break; // break while
			}
		}
		
		return results;
	}
	
	/**
	 * ����keys�������������ͬʱ����values
	 * 
	 * @param a
	 * @param low
	 * @param high
	 */
	private void quicksort (int low, int high)
	{
		//low is the lower index, high is the upper index
		//of the region of array a that is to be sorted
	    int i=low, j=high;
	    K h;
	    V v;
	    K x=keys[(low+high)>>1];

	    //partition
	    do {    
	        while (keys[i].compareTo(x)<0) i++; 
	        while (keys[j].compareTo(x)>0) j--;
	        
	        if (i<=j)
	        {
	            h=keys[i]; keys[i]=keys[j]; keys[j]=h;
	            v=values[i]; values[i]=values[j]; values[j]=v;
	            i++; j--;
	        }
	    } while (i<=j);

	    //  recursion
	    if (low<j) quicksort(low, j);
	    if (i<high) quicksort(i, high);
	}
}
