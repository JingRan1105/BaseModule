package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QueryIndividual {
	
	//����һ�����µ�����ʵ�����ƣ�����ֵResultSet������������
	public ResultSet checkInstance(String yourClass);
	
}
