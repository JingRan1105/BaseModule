package similarity.hownet2000.sememe;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;


import similarity.hownet2000.util.BlankUtils;
import similarity.hownet2000.util.FileUtils;
import similarity.word.hownet2000.HownetMeta;

/**
 * ��ԭ������, ������Ԫ���ݵļ��أ���Ԫ����֯����������ѯ �Լ���Ԫ�ľ����������ƶȼ����.
 * �㷨�ĺ���˼����ο�����
 *
 * @author �ڻ�
 */
public abstract class SememeParser implements HownetMeta {

    /** ���е���ԭ����ŵ�һ������֮�У�������Ԫ��ID����������±���ͬ */
    protected Sememe[] SEMEMES;
    /** ͨ������ԭ�ĺ������������������ݸ��������ٶ�λ��ԭ���ҳ���ԭ��id���ٵ�sememes�в��� */
    private FastSimpleMap<String, Integer> sememeMap = null;

    public SememeParser() throws IOException {
        String sememeFile = getClass().getPackage().getName().replaceAll("\\.", "/") + "/sememe.dat";
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(sememeFile);
        load(input, "UTF-8");
    }

    /**
     * ��ȡ������ԭ�����������ƶ�
     * @param sememeName1
     * @param sememeName2
     * @see ke.commons.similarity.Similariable
     * @return
     */
    public abstract double getSimilarity(String sememeName1, String sememeName2);

    /**
     * ��ȡ����ȷ����ԭ�����ƶ�
     * @param sememe1
     * @param sememe2
     * @return
     */
    public abstract double getSimilarity(Sememe sememe1, Sememe sememe2);

    /**
     * ���ļ��м�����Ԫ֪ʶ
     *
     * @throws IOException
     */
    public void load(InputStream input, String encoding) throws IOException {
        SememeDictTraverseEvent event = new SememeDictTraverseEvent();
        long time = System.currentTimeMillis();
        FileUtils.traverseLines(input, encoding, event);
        this.SEMEMES = event.getSememes();

        String[] keys = new String[SEMEMES.length];
        Integer[] values = new Integer[SEMEMES.length];

        //��������
        for (int i = 0; i < SEMEMES.length; i++) {
            keys[i] = SEMEMES[i].getCnWord();
            values[i] = SEMEMES[i].getId();
        }
        sememeMap = new FastSimpleMap<String, Integer>(keys, values);

        time = System.currentTimeMillis() - time;
    }

    /**
     * ���ݺ��ﶨ�������Ԫ֮��ľ���,Integer.MAX_VALUE����������Ԫ֮��ľ���Ϊ�����
     * <br/>���ڿ��ܶ����Ԫ����ͬ�ĺ������ʼ�����Ϊ���о�����С��
     *
     * @param key1
     * @param key2
     * @return
     */
    public int getDistance(String key1, String key2) {
        int distance = Integer.MAX_VALUE;

        // ��������ַ�����ȣ�ֱ�ӷ��ؾ���Ϊ0
        if (key1.equals(key2)) {
            return 0;
        }

        Integer[] semArray1 = getSememes(key1);
        Integer[] semArray2 = getSememes(key2);

        // ���key1����key2������Ԫ������key1<>key2,�򷵻������
        if (semArray1.length == 0 || semArray2.length == 0) {
            return Integer.MAX_VALUE;
        }

        for (int i : semArray1) {
            for (int j : semArray2) {
                int d = getDistance(SEMEMES[i], SEMEMES[j]);
                if (d < distance) {
                    distance = d;
                }
            }
        }

        return distance;
    }

    /**
     * ��ȡ������Ԫ����ԭ���еľ���
     *
     * @param sem1
     *            ��һ����ԭ
     * @param sem2
     *            �ڶ�����ԭ
     * @return ������ԭ�ľ���
     */
    public int getDistance(Sememe sem1, Sememe sem2) {
        Sememe mysem1 = sem1;
        Sememe mysem2 = sem2;
        int distance = 0;

        if (mysem1 == null || mysem2 == null) {
            return Integer.MAX_VALUE;
        }

        //��Ϊ�����ͬ��Ȼ��һ�����ҹ�ͬ�ĸ��ڵ�
        int level = mysem1.getDepth() - mysem2.getDepth();
        for (int i = 0; i < ((level < 0) ? level * -1 : level); i++) {
            if (level > 0) {
                mysem1 = SEMEMES[mysem1.getParentId()];
            } else {
                mysem2 = SEMEMES[mysem2.getParentId()];
            }
            distance++;
        }

        //�Ӳ�ͬ�ķ�֧�������ͬ��ͬʱ����Ѱ�ҹ�ͬ�����Ƚڵ�
        while (mysem1.getId() != mysem2.getId()) {
            // ����Ѿ�������ڵ㣬��Ȼ��ͬ���򷵻������(-1)
            if (mysem1.getId() == mysem1.getParentId() || mysem2.getId() == mysem2.getParentId()) {
                distance = Integer.MAX_VALUE;
                break;
            }

            mysem1 = SEMEMES[mysem1.getParentId()];
            mysem2 = SEMEMES[mysem2.getParentId()];
            distance += 2;
        }

        return distance;
    }

    /**
     * �ж�һ�������Ƿ���һ����ԭ
     * @param word
     * @return
     */
    public boolean isSememe(String word) {
        return !BlankUtils.isBlank(getSememes(word));
    }

    /**
     * ��ȡ�Ӹ���Ԫ�����ڵ��·����ʾ�ַ���
     *
     * @param key
     * @return
     */
    public String getPath(String key) {
        StringBuilder path = new StringBuilder();

        Sememe sem = getSememe(key);
        while (sem != null && sem.getId() != sem.getParentId()) {
            path.insert(0, "->" + sem.getCnWord());
            sem = SEMEMES[sem.getParentId()];
        }

        if (sem != null) {
            path.insert(0, sem.getCnWord());
        }
        //path.insert(0, "START");
        return path.toString();
    }

    /**
     * ������ԭ�����֣���ȡ����ԭ��λ����Ϣ����ԭ��ϵ����ʱ����һ�����ֶ�Ӧ�����ԭ��һ�����ص�
     * ��ԭ������
     * @param sememeName
     * @return
     */
    public Integer[] getSememes(String sememeName) {
        Collection<Integer> ids = sememeMap.get(sememeName);

        return ids.toArray(new Integer[ids.size()]);
    }

    /**
     * ��ȡ���е�һ����ԭ���󲿷���ԭ��ֻ��һ��
     * @param sememeName
     * @return
     */
    public Sememe getSememe(String sememeName) {
        Integer[] ids = getSememes(sememeName);

        if (BlankUtils.isBlank(ids)) {
            return null;
        } else {
            return SEMEMES[ids[0]];
        }
    }

    /**
     * ������ԭ�ַ�����ȥ�����е�Ӣ�Ĳ���
     * @param sememeString
     * @return
     */
    protected String filterSememeString(String sememeString) {
        int pos = sememeString.indexOf("|");
        if (pos >= 0) {
            sememeString = sememeString.substring(pos + 1);
        }
        return sememeString;
    }
}
