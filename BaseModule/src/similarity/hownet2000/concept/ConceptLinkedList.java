package similarity.hownet2000.concept;

import java.util.LinkedList;

/**
 * ���ڸ�����LinkedList
 * 
 * @author �ڻ�
 */
public class ConceptLinkedList extends LinkedList<Concept> {

    /**
     * ɾ��������������size��Ԫ��
     * @param size
     */
    public void removeLast(int size) {
        for (int i = 0; i < size; i++) {
            this.removeLast();
        }
    }

    /**
     * ���ݸ���Ķ����ж��Ƿ��Ѿ����뵽������
     * @param concept
     */
    public void addByDefine(Concept concept) {
        for (Concept c : this) {
            if (c.getDefine().equals(concept.getDefine())) {
                return;
            }
        }

        this.add(concept);
    }
}
