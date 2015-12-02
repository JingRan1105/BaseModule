package similarity.hownet2000.concept;

import java.util.LinkedList;

/**
 * 用于概念处理的LinkedList
 * 
 * @author 于欢
 */
public class ConceptLinkedList extends LinkedList<Concept> {

    /**
     * 删除链表中最后面的size个元素
     * @param size
     */
    public void removeLast(int size) {
        for (int i = 0; i < size; i++) {
            this.removeLast();
        }
    }

    /**
     * 根据概念的定义判断是否已经加入到链表中
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
