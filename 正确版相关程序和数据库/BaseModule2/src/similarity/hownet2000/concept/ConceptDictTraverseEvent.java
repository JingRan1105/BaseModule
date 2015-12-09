package similarity.hownet2000.concept;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
 * ʵ�ֱ������ظ�����Ϣ���������, ����ʵ����֯��֪�������ĸ�ʽΪ��׼����ʽ���£�<br/>
 * ����                	N    	human|��,ProperName|ר,past|��<br/>
 * ����                	N    	human|��,family|��,male|��<br/>
 * ���� &lt;����&gt; &lt;�ո��������&gt; &lt;����&gt; &lt;�ո��������&gt; &lt;����&gt;"
 * <br/>
 * ����浽�����У�û�б��浽Map�У����Խ��Ͷ��ڴ�ռ��ʹ��
 * 
 * @author �ڻ�
 */
public class ConceptDictTraverseEvent implements TraverseEvent<String> {

    private List<Concept> conceptList = null;

    public ConceptDictTraverseEvent() {
        conceptList = new ArrayList<Concept>();
    }

    public Concept[] getConcepts() {
        Concept[] concepts = conceptList.toArray(new Concept[conceptList.size()]);
        Arrays.sort(concepts);
        return concepts;
    }

    /**
     * ��ȡ����ʵ��е�һ�У������н�������
     */
    public boolean visit(String line) {
        String word = null;
        String pos = null;
        String define = "";
        char ch;
        //�Է���//��ʼ����ע����
        if (line.startsWith("//")) {
            return true;
        }
        int lastPosition = 0;	//���һ�δ������ݵ�������Ŀ�ʼλ��
        int processFlag = 0;	//��ǰ�����ֵı�־ 0������word�� 1�����ԣ�2������
        //������һ���еĸ����������
        loop:
        for (int position = 0; position < line.length(); position++) {
            ch = line.charAt(position);
            if ((ch == ' ') || (ch == '\t') || (position == (line.length() - 1))) {
                String item = line.substring(lastPosition, (position == (line.length() - 1)) ? (position + 1) : position);
                switch (processFlag) {
                    case 0:
                        word = item;
                        processFlag++;
                        break;
                    case 1:
                        pos = item;
                        processFlag++;
                        break;
                    case 2:
                        //define = item;
                        //processFlag++;
                        define = line.substring(lastPosition).trim();
                        break loop;
                    case 3:
                        System.out.println(line);
                        break;
                }

                for (; (position < line.length()); position++) {
                    ch = line.charAt(position);
                    if ((ch != ' ') && (ch != '\t')) {
                        lastPosition = position;
                        break;
                    }
                }

            }
        }
        conceptList.add(new Concept(word, pos, define));
        return true;
    }

    public void saveToXML(File xmlFile) throws Exception {
        String conceptFile = getClass().getPackage().getName().replaceAll("\\.", "/") + "/concept.dat";
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(conceptFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(input, "utf8"));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("concepts");
        document.appendChild(root);

        String line = null;

        while ((line = in.readLine()) != null) {
            saveLineToXML(document, root, line);
        }

        input.close();
        in.close();

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        PrintWriter pw = new PrintWriter(new FileOutputStream(xmlFile));
        StreamResult result = new StreamResult(pw);
        transformer.transform(source, result);
    }

    /**
     * ��ȡ����ʵ��е�һ�У������н�������
     */
    private boolean saveLineToXML(Document document, Element root, String line) {
        String word = null;
        String pos = null;
        String define = "";
        char ch;

        //�Է���//��ʼ����ע����
        if (line.startsWith("//")) {
            return true;
        }

        int lastPosition = 0;	//���һ�δ������ݵ�������Ŀ�ʼλ��
        int processFlag = 0;	//��ǰ�����ֵı�־ 0������word�� 1�����ԣ�2������
        //������һ���еĸ����������
        loop:
        for (int position = 0; position < line.length(); position++) {
            ch = line.charAt(position);

            if ((ch == ' ') || (ch == '\t') || (position == (line.length() - 1))) {
                String item = line.substring(lastPosition, (position == (line.length() - 1)) ? (position + 1) : position);
                switch (processFlag) {
                    case 0:
                        word = item;
                        processFlag++;
                        break;
                    case 1:
                        pos = item;
                        processFlag++;
                        break;
                    case 2:
                        //define = item;
                        //processFlag++;
                        define = line.substring(lastPosition).trim();
                        break loop;
                    case 3:
                        System.out.println(line);
                        break;
                }

                for (; (position < line.length()); position++) {
                    ch = line.charAt(position);
                    if ((ch != ' ') && (ch != '\t')) {
                        lastPosition = position;
                        break;
                    }
                }

            }
        }
        Element e = document.createElement("c");
        e.setAttribute("w", word);
        e.setAttribute("p", pos);
        e.setAttribute("d", define);
        root.appendChild(e);
        return true;
    }

    public static void main(String[] args) throws Exception {
        new ConceptDictTraverseEvent().saveToXML(new File("C:/Users/Administrator/Desktop/XiaTian/XiaTian/src/similarity/word/hownet/concept.xml"));
    }
}
