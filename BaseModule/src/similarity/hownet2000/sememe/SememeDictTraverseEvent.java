package similarity.hownet2000.sememe;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import similarity.hownet2000.util.TraverseEvent;


/**
 * ʵ�ֱ���������ԭ��Ϣ����ԭ����, ��ԭ�ʵ����֯��֪�������ĸ�ʽΪ��׼���磺<br/> 
 * - entity|ʵ�� <br/> 
 * �� thing|���� [#time|ʱ��,#space|�ռ�] <br/> 
 * �� �� physical|���� [!appearance|���] <br/> 
 * �� �� �� animate|���� [*alive|����,!age|����,*die|��,*metabolize|��л] <br/> 
 * �� �� �� �� AnimalHuman|���� [!sex|�Ա�,*AlterLocation|��ռ�λ��,*StateMental|����״̬] <br/> 
 * �� �� �� ��<br/>
 * �ȵ� <br>
 *
 * @author �ڻ�
 */
public class SememeDictTraverseEvent implements TraverseEvent<String>{
	/** ��ԭ��ŵ��б�, ����˳������ID����ŵ����Ա��� */
	private List<Sememe> sememeList = null;
	
	public SememeDictTraverseEvent(){
		this.sememeList = new ArrayList<Sememe>();
	}
	
	/**
	 * ��ȡ���غ����ԭ��Ϣ�������±�˳���ţ����Ĳ�ι�ϵͨ�������±��ʾ
	 * @return
	 */
	public Sememe[] getSememes(){
		return sememeList.toArray(new Sememe[sememeList.size()]);
	}
	
	
	private void processXML(Document document, Element root, int parentId, String fullParentId){
		int position = 1;
		for(int i=0; i<sememeList.size(); i++){
			Sememe sememe = sememeList.get(i);
			if(sememe.getParentId()==parentId && sememe.getId()!=parentId){
				Element sememeNode = document.createElement("sememe");
				String fullId = fullParentId + "-" + (position++);
				sememeNode.setAttribute("id", fullId);				
				sememeNode.setAttribute("cn", sememe.getCnWord());
				sememeNode.setAttribute("en", sememe.getEnWord());
				if(sememe.getDefine()!=null && !sememe.getDefine().equals("")){
					sememeNode.setAttribute("define", sememe.getDefine());
				}
				root.appendChild(sememeNode);	
				processXML(document, root, sememe.getId(), fullId);
			}
		}
	}
	
	/**
	 * ���浽XML�ļ���, �°汾��xsimilarity����xml��ʽ�洢��ԭ�����ʽΪ
	 * &lt;sememes>
	 *   &lt;sememe cn="�¼�" en="event" id="1"/>
	 *   &lt;sememe cn="��̬" en="static" id="1-1"/>
	 * ...
	 * &lt;/sememes>
	 * @param xmlFile
	 * @throws Exception 
	 */
	public void saveToXML(String xmlFile) throws Exception{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder=factory.newDocumentBuilder(); 
		Document document=builder.newDocument();
		Element root=document.createElement("sememes"); 
		document.appendChild(root); 
		int position = 1;
		for(Sememe sememe:sememeList){
			if(sememe.getId()!=sememe.getParentId()){
				continue;
			}
			
			Element sememeNode = document.createElement("sememe");
			String fullId = Integer.toString(position++);
			
			sememeNode.setAttribute("id", fullId);			
			sememeNode.setAttribute("cn", sememe.getCnWord());
			sememeNode.setAttribute("en", sememe.getEnWord());
			if(sememe.getDefine()!=null && !sememe.getDefine().equals("")){
				sememeNode.setAttribute("define", sememe.getDefine());
			}
			root.appendChild(sememeNode);			
			processXML(document, root, sememe.getId(), fullId);
		}
		
		TransformerFactory tf=TransformerFactory.newInstance(); 
		Transformer transformer=tf.newTransformer(); 
		DOMSource source=new DOMSource(document); 
		transformer.setOutputProperty(OutputKeys.ENCODING,"utf8"); 
		transformer.setOutputProperty(OutputKeys.INDENT,"yes"); 
		PrintWriter pw=new PrintWriter(new FileOutputStream(xmlFile)); 
		StreamResult result=new StreamResult(pw); 
		transformer.transform(source,result); 
	}
	
	/**
	 * ������ǰ��ԭ��Ϣ�ı���<br/>
	 * �ж϶����һ���ı�����Ԫ���еĵڼ��㣬����ĸ�ʽ���磺<br>
	 *  - entity|ʵ�� <br>
	 *   �� thing|���� [#time|ʱ��,#space|�ռ�] <br>
	 *   �� �� physical|���� [!appearance|���] <br>
	 *   �� �� �� animate|���� [*alive|����,!age|����,*die|��,*metabolize|��л] <br>
	 * 
	 * @param item
	 * @return �������ԭ����info[0]���ز�����(info[0]>=0); info[1]���ؾ������Ԫ������ʼλ�ã�����info[0]����-1
	 */
	private int[] parseSememeLine(String item) {
		int[] info = new int[2];
		info[0] = -1;

		int prefixLen = 0; // ǰ׺����Ŀ�������ո��"-,��,��"�ȷ��ţ����пո��"-"������һ�����ȣ�������2��
		for (int i = 0; i < item.length(); i++) {
			char ch = item.charAt(i);
			if ((ch == ' ') || (ch == '-')) {
				prefixLen++;
			} else if ((ch == '��') || (ch == '��') || (ch == '��')) {
				prefixLen += 2;
			} else {
				// ������ǰ׺�ַ�����⣬����ǰ׺��ȣ����Ϊ2������0������һ��������ÿ����3����ȼ�1
				if (prefixLen >= 2) {
					info[0] = (prefixLen - 2) / 3;
					info[1] = i;
				}
				break;
			}
		}
		return info;
	}
	
	/**
	 * �����ַ����ж���Ԫ������
	 * 
	 * @param item
	 * @return
	 */
	private int parseSememeType(String item) {
		String myItem = item.toLowerCase().trim();
		if (myItem.indexOf("event|") == 0)
			return SememeType.Event;
		else if (myItem.indexOf("entity|") == 0)
			return SememeType.Entity;
		else if (myItem.indexOf("attribute|") == 0)
			return SememeType.Attribute;
		else if (myItem.indexOf("quantity|") == 0)
			return SememeType.Quantity;
		else if (myItem.indexOf("avalue|") == 0)
			return SememeType.AValue;
		else if (myItem.indexOf("qvalue|") == 0)
			return SememeType.QValue;
		else if (myItem.indexOf("secondary feature") == 0)
			return SememeType.SecondaryFeature;
		else if (myItem.indexOf("syntax") == 0)
			return SememeType.Syntax;
		else if (myItem.indexOf("eventrole and features") == 0)
			return SememeType.EventRoleAndFeature;
		else
			return 0;
	}
	
	/**
	 * ʵ��TraverseEvent<String>��ʵ�ʷ��ʽӿ�, ����ֵû��ʹ��
	 * @see ke.commons.util.TraverseEvent
	 */
	public boolean visit(String line) {
		//�ж��Ƿ�Ϊע����
        if(line.trim().equals("")||line.trim().charAt(0)=='#') return true;
        
        //��ǰ��ԭ��������ԭ�б��е�λ��
        int position = sememeList.size();
        
        //������ǰ��ԭ��Ϣ�ı���, info[0]��ʾ��ǰ��ԭ�Ĳ��, info[1]��ʾ��ǰ��ԭ��ʵ����Ϣ���ı����еĿ�ʼλ��
        int[] info = parseSememeLine(line);
        int curDepth = info[0];
        
        //������<0������
        if(info[0]<0) return false;

        //ȡ����������ԭ�ַ���
        String sememeString = line.substring(info[1]);
        
        //���Ϊ0����ʾΪ���ڵ�
        if(info[0]==0){
        	Sememe sememe = new Sememe(position, position, 0, sememeString);
        	int sememeType = parseSememeType(sememeString);
        	sememe.setType(sememeType);
        	sememeList.add(sememe);
        }else{
        	Sememe parentSememe = sememeList.get(position-1);
        	//���һ����ȱȵ�ǰ��ȴ�1����ԭ��Ϊ����ԭ�ĸ��ڵ�
        	
        	while((parentSememe.getDepth()-curDepth)!=-1){
        		parentSememe = sememeList.get(parentSememe.getParentId());
        	}
        	Sememe sememe = new Sememe(position, parentSememe.getId(), curDepth, sememeString);
        	sememe.setType(parentSememe.getType());
        	sememeList.add(sememe);
        }
		
        return true;
	}
}
