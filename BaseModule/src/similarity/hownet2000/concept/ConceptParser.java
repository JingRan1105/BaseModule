package similarity.hownet2000.concept;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import similarity.hownet2000.util.BlankUtils;
import similarity.hownet2000.util.FileUtils;
import similarity.word.hownet2000.HownetMeta;
import similarity.hownet2000.sememe.SememeParser;

/**
 * ���������: ��������ļ��أ��ڲ���֯����������ѯ�Լ���������ƶȼ����.
 * ����浽������, û�б��浽Map��, �Ծ������Ͷ��ڴ�ռ��ʹ��.
 * �㷨�ĺ���˼����ο�����
 * <br/><br/>
 * improvement:
 * <ol>
 * <li>������ԭ���ϵ����㷽ʽ֧�־�ֵ��ʽ��Fuzzy��ʽ</li>
 * </ol>
 * 
 * 
 * @author �ڻ�
 */
public abstract class ConceptParser implements HownetMeta {

    //��1ʵ�ʸ����һ������ԭ����ʽ��Ȩ��
    public double beta1;
    //��2ʵ�ʸ�������������ԭ����ʽ��Ȩ��
    public double beta2;
    //��3ʵ�ʸ����ϵ��ԭ����ʽ��Ȩ��
    public double beta3;
    //��4ʵ�ʸ��������ԭ����ʽ��Ȩ��
    public double beta4;

    /** the logger */
    //protected Log LOG = LogFactory.getLog(this.getClass());
    /** ���и����ŵ����� */
    private static Concept[] CONCEPTS = null;
    protected SememeParser sememeParser = null;
    //protected SememeParser sememeParser = null;

    /** �����������ͣ�Ŀǰ֧�־�ֵ�����ģ��������������ʽ */
    public enum SET_OPERATE_TYPE {

        AVERAGE, FUZZY
    };
    /** Ĭ�ϵļ�����������Ϊ��ֵ�� */
    private SET_OPERATE_TYPE currentSetOperateType = SET_OPERATE_TYPE.AVERAGE;

    public ConceptParser(SememeParser sememeParser) throws IOException {
        this.sememeParser = sememeParser;
        synchronized (this) {
            if (CONCEPTS == null) {
                String conceptFile = getClass().getPackage().getName().replaceAll("\\.", "/") + "/concept.dat";
                InputStream input = this.getClass().getClassLoader().getResourceAsStream(conceptFile);
                load(input, "UTF-8");
            }
        }
    }

    /**
     * ���ļ��м��ظ���֪ʶ�����Զ���������
     *
     * @throws IOException
     */
    public void load(InputStream input, String encoding) throws IOException {
        ConceptDictTraverseEvent event = new ConceptDictTraverseEvent();
        //LOG.info("loading conecpt dictionary...");
        long time = System.currentTimeMillis();

        FileUtils.traverseLines(input, encoding, event);
        CONCEPTS = event.getConcepts();
        System.out.println("���и�������:" + CONCEPTS.length);

        time = System.currentTimeMillis() - time;
        //LOG.info("loading concept dictionary completely. time elapsed: " + time);
    }

    /**
     * �ж�һ�������Ƿ���һ������
     * @param word
     * @return
     */
    public boolean isConcept(String word) {
        return !BlankUtils.isBlank(getDefinitions(word));
    }

    /**
     * �������ƻ�ȡ��Ӧ�ĸ������Ϣ������һ��������ܶ�Ӧ��������˷���һ������<br/>
     * ���ҵĹ��̲��ö��ֲ���
     *
     * @param key Ҫ���ҵĸ�������
     * @return
     */
    public Collection<Concept> getConcepts(String key, String definition) {
        Collection<Concept> results = new ArrayList<Concept>();
        for (int i = 0; i < CONCEPTS.length; i++) {
            if (CONCEPTS[i].getWord().equals(key) && CONCEPTS[i].getDefine().equals(definition)) {
                results.add(CONCEPTS[i]);
            }
        }
        return results;
    }

    public Collection<String> getDefinitions(String key) {
        Collection<Concept> results = new ArrayList<Concept>();
        Collection<String> definitions = new ArrayList<String>();
        for (int i = 0; i < CONCEPTS.length; i++) {
            if (CONCEPTS[i].getWord().equals(key)) {
                results.add(CONCEPTS[i]);
            }
        }
        for (Concept temp : results) {
            if (!definitions.contains(temp.getDefine())) {
                definitions.add(temp.getDefine());
            }
        }
        return definitions;
    }


    /** ���õ�ǰ�ļ����������� */
    public void setSetOperateType(SET_OPERATE_TYPE type) {
        this.currentSetOperateType = type;
    }

}
