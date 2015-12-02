package similarity.hownet2000.sememe;

/**
 * ����֪����ԭ�Ļ�������, �������ܿ��ǣ���δ�õ���Ӣ�����ơ�������ڼ���ʱ����, ��׼ȷ����������[Ӣ�Ķ���|���Ķ���]
 * ��Ϊһ��������д��������������ֻ�������Ķ���Ϳ��Ա�ʶ��������˺��Բ��ơ�
 *
 * @author �ڻ�
 */
public class Sememe {
	/** ��ԭ��� */
	private int id;
	/** ָ����λ��Ԫ�� */
	private int parentId;
	/** ��ԭ����ԭ���е���� */
	private int depth;
	/** ��ԭ����������*/
	private String cnWord;
	/** ��ԭ��Ӣ������ */
	private String enWord;
	/** ��ԭ�Ķ��壬���û��(��������)����Ϊ�մ� */
	private String define;
	/** ��ԭ������ */
	private int type;	

	/**
	 * ÿһ�е���ʽΪ��be|�� {relevant,isa}/{relevant,descriptive} 
	 * <br/>���� official|�� [#organization|��֯,#employee|Ա] 
	 * <br/>���� amount|���� 
	 * <br/>����Ӧ�Ĳ��ָ��費ͬ������
	 * �������ܿ��ǣ���δ�õ���Ӣ�����ơ�����Ⱥ���
	 * @param id
	 * @param parentId
	 * @param item ��ȡ�ļ��е�һ��
	 */
	public Sememe(int id, int parentId, int depth, String item) {
		this.id = id;
		this.parentId = parentId;
		this.depth = depth;
		
		int pos = item.indexOf('|');
		if (pos < 0) {
			this.cnWord = item;
			this.enWord = item;
		} else {
			this.enWord = item.substring(0, pos);
			// ȥ��"|"����
			String nextPart = item.substring(pos + 1);
			pos = nextPart.indexOf(' ');
			if (pos <= 0) {
				this.cnWord = nextPart;
			} else {
				this.cnWord = nextPart.substring(0, pos);
				this.define = nextPart.substring(pos).trim();
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getCnWord() {
		return cnWord;
	}

	public void setCnWord(String cnWord) {
		this.cnWord = cnWord;
	}

	public String getEnWord() {
		return enWord;
	}

	public void setEnWord(String enWord) {
		this.enWord = enWord;
	}

	public String getDefine() {
		return define;
	}

	public void setDefine(String define) {
		this.define = define;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("id=");
		sb.append(id);
		sb.append("; parentId=");
		sb.append(parentId);
		sb.append("; depth=");
		sb.append(depth);
		sb.append("; cnWord=");
		sb.append(cnWord);
		sb.append("; enWord=");
		sb.append(enWord);
		sb.append("; define=");
		sb.append(define);
		return sb.toString();
	}

}

