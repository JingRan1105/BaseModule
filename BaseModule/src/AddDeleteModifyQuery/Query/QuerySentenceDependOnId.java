package AddDeleteModifyQuery.Query;

import com.hp.hpl.jena.query.ResultSet;

public interface QuerySentenceDependOnId {
	//����Id����ʵ���������ԣ�����ֵResultSet��������Id
		public ResultSet checkSentencePropertyDependOnId(String yourId);
}
