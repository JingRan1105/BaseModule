package similarity.hownet2000.concept;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import similarity.hownet2000.util.BlankUtils;
import similarity.hownet2000.sememe.LiuqunSememeParser;
import similarity.hownet2000.sememe.SememeParser;

/**
 * ��������ȡ�����ƶȼ��㷽ʽ
 * 
 * �����������ʵ��,���ڻ�ȡ��������������ƶȵ�, ��ԭ���ıȽϣ������˼�֦��������Ϲ����ʱ�򣬾��Զ�ֹͣ�������������
 * ��֤�����ٶ�
 * 
 * @author �ڻ�
 */
public class MyConceptParser extends ConceptParser {

    private static final int MAX_COMBINED_COUNT = 12;
    private static MyConceptParser instance = null;

    public static MyConceptParser getInstance() {
        if (instance == null) {
            try {
                instance = new MyConceptParser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private MyConceptParser() throws IOException {
        //super(new MySememeParser());
        super(new LiuqunSememeParser());
    }

    public MyConceptParser(SememeParser sememeParser) throws IOException {
        super(sememeParser);
    }

    public void setParameter(double a, double b1, double b2, double b3, double b4) throws IOException {
        LiuqunSememeParser myLiuqunSememeParser = new LiuqunSememeParser();
        myLiuqunSememeParser.setParameter(a);
        beta1 = b1;
        beta2 = b2;
        beta3 = b3;
        beta4 = b4;
    }

    protected double calculate(double sim_v1, double sim_v2, double sim_v3, double sim_v4) {
        return beta1 * sim_v1 + beta2 * sim_v1 * sim_v2 + beta3 * sim_v1 * sim_v3 + beta4 * sim_v1 * sim_v4;
    }

    /**
     * ��δ��¼�ʽ��и����з�, �γɶ�����������������������֯,
     * �硰���ֳ����з���Ϻ��ų�: ������ �� �����֡�
     * @param oov_word δ��¼��
     * @return
     */
    private List<String> segmentOOV(String oov_word) {
        List<String> results = new LinkedList<String>();
        String word = oov_word;
        while (word != null && !word.equals("")) {
            String token = word;
            while (token.length() > 1 && BlankUtils.isBlank(getDefinitions(token))) {
                token = token.substring(1);
            }
            results.add(token);
            word = word.substring(0, (word.length() - token.length()));
        }
        return results;
    }

    /**
     * ����δ��¼����oov_word�Զ��������
     * @param oov_word δ��¼�ʣ��˴�ָ֪��������δ���ֵĴ����Ҫ�����з�,���������壬
     * ��ϵ������ϵͨ�����ո���refConcepts��������
     * @param refConcepts �򵥼������oov_word�ĸ���壬��Ҫͨ��refConcepts������ԭ֮��ķ��š���ϵ��
     * @return
     */
    public Collection<Concept> autoCombineConcepts(String oov_word, Collection<Concept> refConcepts) {
        ConceptLinkedList oovConcepts = new ConceptLinkedList();

        if (oov_word == null) {
            return oovConcepts;
        }

        for (String concept_word : segmentOOV(oov_word)) {
            Collection<String> definitions = getDefinitions(concept_word);
            Collection<Concept> concepts = new ArrayList<Concept>();
            for (String tempdef : definitions) {
                for (Concept tempcon : getConcepts(concept_word, tempdef)) {
                    concepts.add(tempcon);
                }
            }
            if (oovConcepts.size() == 0) {
                oovConcepts.addAll(concepts);
                continue;
            }

//			//��֦��������̫�࣬һ�����Ӱ�������ٶȣ���һ������Ϲ��������Ҳ���Ǻ�����
//			if(oovConcepts.size()>3){
//				break;
//			}

            ConceptLinkedList tmpConcepts = new ConceptLinkedList();
            for (Concept head : concepts) {
                for (Concept tail : oovConcepts) {
                    if (!BlankUtils.isBlank(refConcepts)) {
                        for (Concept ref : refConcepts) {
                            tmpConcepts.addByDefine(autoCombineConcept(head, tail, ref));
                        }
                    } else {
                        tmpConcepts.addByDefine(autoCombineConcept(head, tail, null));
                    }
                }
            }
            oovConcepts = tmpConcepts;
        }
        /** �����Ϲ��࣬��ɾ������1/3����� */
        if ((oovConcepts.size() > MAX_COMBINED_COUNT)) {
            oovConcepts.removeLast(MAX_COMBINED_COUNT / 3);
        }
        return oovConcepts;
    }

    /**
     * ���������������ϸ���, ��������и��ݲ��ո���������Ͻ��, ʵ��Ӧ���е���������
     * Ӧ����һ�����Ⱥ��ϵ(���ֺ�����ĺ��ơ��ص�), ��������ֳ���first="����" second="��",
     * ���⣬ ����Ҫ������һ�������еķ�����ԭ���ڵڶ�����������ԭ��ʵ�ʹ�ϵ�������ո���������ʱ��
     * ������ָ������ֵ������Ҫ�ж��Ƿ�ѵ�ǰ��ԭ������ϸ����У����ڵ�һ���������Ҫͬʱ�������Ź�ϵ,
     * ���Ϲ�ϵ����ո����һ��.
     *
     * @param head ��һ������
     * @param tail �ڶ�������
     * @param ref ���ո���
     * @return
     */
    public Concept autoCombineConcept(Concept head, Concept tail, Concept ref) {
        //һ��Ϊnull��һ����null��ֱ�ӷ��ط�null�Ŀ�¡�¸���
        if (tail == null && head != null) {
            return new Concept(head.getWord(), head.getPos(), head.getDefine());
        } else if (head == null && tail != null) {
            return new Concept(tail.getWord(), tail.getPos(), tail.getDefine());
        }

        //�ڶ��������ʵ�ʣ�ֱ�ӷ��ص�һ������
        if (!tail.isSubstantive()) {
            return new Concept(head.getWord() + tail.getWord(), head.getPos(), head.getDefine());
        }

        // ���û�в��ո�����߲��ո���Ϊ��ʣ���ֱ����ӣ������ո����������
        if (ref == null || !ref.isSubstantive()) {
            String define = tail.getDefine();	//define����µĶ�����

            //�ѵ�һ������Ķ���ϲ����ڶ�����
            List<String> sememeList = getAllSememes(head, true);
            for (String sememe : sememeList) {
                if (!define.contains(sememe)) {
                    define = define + "," + sememe;
                }
            }
            return new Concept(head.getWord() + tail.getWord(), tail.getPos(), define);
        }

        //�����������ո���ǿգ�������ʵ�ʸ���
        String define = tail.getMainSememe();	//define����µĶ�����

        List<String> refSememes = getAllSememes(ref, false);
        List<String> headSememes = getAllSememes(head, true);
        List<String> tailSememes = getAllSememes(tail, false);

        //������ո�����ڶ������������ԭ����ԭ���ƶȴ�����ֵTHETA��
        //��������ϸ��������ڶ���������ص���ԭ����Ϊ: �ڶ����������ԭ��������ո�����ԭ���ϵ�ģ������
        double main_similarity = sememeParser.getSimilarity(tail.getMainSememe(), ref.getMainSememe());
        if (main_similarity >= PARAM_THETA) {
            // �󽻼�
            for (String tail_sememe : tailSememes) {
                double max_similarity = 0.0;
                String max_ref_sememe = null;
                for (String ref_sememe : refSememes) {
                    double value = sememeParser.getSimilarity(tail_sememe, ref_sememe);
                    if (value > max_similarity) {
                        max_similarity = value;
                        max_ref_sememe = ref_sememe;
                    }
                }

                //���tail_sememe����ո����е����ƶ�������ԭ��thetaԼ���󳬹���ֵXI����������ɵ���ϸ������
                if (max_similarity * main_similarity >= PARAM_XI) {
                    define = define + "," + tail_sememe;
                    refSememes.remove(max_ref_sememe);
                }
            }//end for
        } else {
            define = tail.getDefine();
        }//end if

        //�ϲ���һ���������ԭ����ϸ������
        for (String head_sememe : headSememes) {
            double max_similarity = 0.0;
            String max_ref_sememe = "";
            for (String ref_sememe : refSememes) {
                double value = sememeParser.getSimilarity(getPureSememe(head_sememe), getPureSememe(ref_sememe));
                if (value > max_similarity) {
                    max_similarity = value;
                    max_ref_sememe = ref_sememe;
                }
            }

            if (main_similarity * max_similarity >= PARAM_OMEGA) {
                //�������Ź�ϵ, �ò��ո���ķ��Ź�ϵ�滻ԭ���Ź�ϵ, ͨ���Ѳ��ո���ķǷ��Ų����滻��ǰ����ԭ�ķǷ������ݼ���
                String sememe = max_ref_sememe.replace(getPureSememe(max_ref_sememe), getPureSememe(head_sememe));
                if (!define.contains(sememe)) {
                    define = define + "," + sememe;
                }
            } else if (!define.contains(head_sememe)) {
                define = define + "," + head_sememe;
            }
        }//end for
        return new Concept(head.getWord() + tail.getWord(), tail.getPos(), define);
    }

    /**
     * ��ȡ�����������ԭ
     * @param concept
     * @param includeMainSememe  �Ƿ��������ԭ
     * @return
     */
    private List<String> getAllSememes(Concept concept, boolean includeMainSememe) {
        List<String> results = new ArrayList<String>();
        if (concept != null) {
            if (includeMainSememe) {
                results.add(concept.getMainSememe());
            }

            for (String sememe : concept.getSecondSememes()) {
                results.add(sememe);
            }

            for (String sememe : concept.getSymbolSememes()) {
                results.add(sememe);
            }

            for (String sememe : concept.getRelationSememes()) {
                results.add(sememe);
            }
        }
        return results;
    }

    /**
     * ȥ����ԭ�Ĺ�ϵ���߷���
     *
     * @param sememe ԭʼ��ԭ
     * @return ȥ����ԭ�Ĺ�ϵ���߷��ŵ���ֵ
     */
    private String getPureSememe(String sememe) {
        String line = sememe.trim();

        if ((line.charAt(0) == '(') && (line.charAt(line.length() - 1) == ')')) {
            line = line.substring(1, line.length() - 1);
        }

        //���ж��Ƿ�Ϊ������Ԫ
        String symbol = line.substring(0, 1);
        for (int i = 0; i < Symbol_Descriptions.length; i++) {
            if (symbol.equals(Symbol_Descriptions[i][0])) {
                return line.substring(1);
            }
        }

        //������Ƿ�����Ԫ�����һ���ж��ǹ�ϵ��Ԫ���ǵڶ�������Ԫ
        int pos = line.indexOf('=');
        if (pos > 0) {
            line = line.substring(pos + 1);
        }
        return line;
    }

    public static void main(String[] args) {
    }
}
