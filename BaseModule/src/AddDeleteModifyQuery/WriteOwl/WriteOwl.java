package AddDeleteModifyQuery.WriteOwl;

public interface WriteOwl {
	//把Fuseki数据库中的数据写回.owl文件中，且单词和句子的.owl文件是分开的：返回值：无；参数：无
	public void writeBackToOwl();
}
