package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QueryDependOnId {

	//����Id����ʵ���������ԣ�����ֵResultSet��������Id
	public ResultSet checkPropertyDependOnId(String yourId);
}
