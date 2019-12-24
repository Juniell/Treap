import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.*;

class TreapTest {
    private Treap<Integer> treapFix = new Treap<>();
    private Treap<Integer> treapRand = new Treap<>();
    private Treap<Integer> treapEmpty = new Treap<>();

    void fillFix() {
        treapFix.add(7, 13);
        treapFix.add(10, 10);
        treapFix.add(3, -61);
        treapFix.add(6, 94);
        treapFix.add(15, -86);
        treapFix.add(5, 5);
        treapFix.add(8, 8);
        treapFix.add(11, 11);
        treapFix.add(2, 2);
        treapFix.add(70, 70);
    }

    void fillRand() {
        treapRand.add(7);
        treapRand.add(10);
        treapRand.add(3);
        treapRand.add(6);
        treapRand.add(15);
        treapRand.add(5);
        treapRand.add(8);
        treapRand.add(11);
        treapRand.add(2);
        treapRand.add(70);
    }

    @Test
    void addFix() {
        assertFalse(treapEmpty.add(null, null));
        assertTrue(treapFix.add(7, 13));
        assertFalse(treapFix.add(7, 13));
        assertTrue(treapFix.add(10, 10));
        assertFalse(treapFix.add(10, 10));
        assertTrue(treapFix.add(3, -61));
        assertFalse(treapFix.add(3, -61));
        assertTrue(treapFix.add(6, 94));
        assertFalse(treapFix.add(6, 94));
        assertTrue(treapFix.add(15, -86));
        assertFalse(treapFix.add(15, -86));
        assertTrue(treapFix.add(5, 5));
        assertFalse(treapFix.add(5, 5));
        assertTrue(treapFix.add(8, 8));
        assertFalse(treapFix.add(8, 8));
        assertTrue(treapFix.add(11, 11));
        assertFalse(treapFix.add(11, 11));
        assertTrue(treapFix.add(2, 2));
        assertFalse(treapFix.add(2, 2));
        assertTrue(treapFix.add(70, 70));
        assertFalse(treapFix.add(70, 70));
    }

    @Test
    void addRand() {
        assertFalse(treapEmpty.add(null));
        assertTrue(treapRand.add(7));
        assertFalse(treapRand.add(7));
        assertTrue(treapRand.add(10));
        assertFalse(treapRand.add(10));
        assertTrue(treapRand.add(3));
        assertFalse(treapRand.add(3));
        assertTrue(treapRand.add(6));
        assertFalse(treapRand.add(6));
        assertTrue(treapRand.add(15));
        assertFalse(treapRand.add(15));
        assertTrue(treapRand.add(5));
        assertFalse(treapRand.add(5));
        assertTrue(treapRand.add(8));
        assertFalse(treapRand.add(8));
        assertTrue(treapRand.add(11));
        assertFalse(treapRand.add(11));
        assertTrue(treapRand.add(2));
        assertFalse(treapRand.add(2));
        assertTrue(treapRand.add(70));
        assertFalse(treapRand.add(70));
    }

    @Test
    void addAll() {
        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(7);
        list.add(11);
        assertTrue(treapRand.addAll(list));
        assertTrue(treapRand.containsAll(list));
    }

    @Test
    void size() {
        assertEquals(0, treapEmpty.size());
        treapEmpty.add(10);
        assertEquals(1, treapEmpty.size());
        fillFix();
        assertEquals(10, treapFix.size());
        fillRand();
        assertEquals(treapFix.size(), treapRand.size());
        treapFix.remove(7);
        assertEquals(9, treapFix.size());
        treapRand.remove(15);
        assertEquals(9, treapRand.size());
    }

    @Test
    void isEmpty() {
        assertTrue(treapEmpty.isEmpty());
        assertTrue(treapRand.isEmpty());
        treapRand.add(0);
        assertFalse(treapRand.isEmpty());
        fillRand();
        assertFalse(treapRand.isEmpty());
    }

    @Test
    void clear() {
        fillRand();
        assertNotEquals(0, treapRand.size());
        treapRand.clear();
        assertTrue(treapRand.isEmpty());
        assertEquals(0,treapRand.size());
        List<Integer> list  = new ArrayList<>();
        list.add(7);
        list.add(10);
        list.add(3);
        list.add(6);
        list.add(15);
        list.add(5);
        list.add(8);
        list.add(11);
        list.add(2);
        list.add(70);
        assertFalse(treapRand.containsAll(list));
    }

    @Test
    void contains() {
        assertFalse(treapEmpty.contains(6));
        fillRand();
        assertTrue(treapRand.contains(7));
        assertTrue(treapRand.contains(10));
        assertTrue(treapRand.contains(3));
        assertTrue(treapRand.contains(6));
        assertTrue(treapRand.contains(15));
        assertTrue(treapRand.contains(5));
        assertTrue(treapRand.contains(8));
        assertTrue(treapRand.contains(11));
        assertTrue(treapRand.contains(2));
        assertTrue(treapRand.contains(70));
        assertFalse(treapRand.contains(100));
    }

    @Test
    void containsAll() {
        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(7);
        list.add(11);
        assertFalse(treapEmpty.containsAll(list));
        treapRand.add(6);
        assertFalse(treapRand.containsAll(list));
        fillRand();
        assertTrue(treapRand.containsAll(list));
    }

    @Test
    void toArray() {
        fillRand();
        Object[] arr = treapRand.toArray();
        for (Object el : arr)
            assertTrue(treapRand.contains(el));
        assertEquals(arr.length, treapRand.size());
    }

    @Test
    void retainAll() {
        fillRand();
        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(7);
        list.add(11);
        assertTrue(treapRand.retainAll(list));
        assertTrue(treapRand.containsAll(list));
        assertEquals(treapRand.size(), list.size());
        fillFix();
        assertTrue(treapFix.retainAll(list));
        assertTrue(treapFix.containsAll(list));
        assertEquals(treapFix.size(), list.size());
    }

    @Test
    void remove() {
        assertFalse(treapEmpty.remove(0));
        fillRand();
        int size = treapRand.size();
        assertFalse(treapRand.remove(0));
        assertTrue(treapRand.remove(15));
        assertFalse(treapRand.contains(15));
        assertEquals(size - 1, treapRand.size());
        assertFalse(treapRand.remove(15));
        assertTrue(treapRand.remove(10));
        assertFalse(treapRand.contains(10));
    }

    @Test
    void removeAll() {
        fillRand();
        int size = treapRand.size();
        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(7);
        list.add(11);
        assertTrue(treapRand.removeAll(list));
        assertEquals(size - treapRand.size(), list.size());
        assertFalse(treapRand.containsAll(list));
    }

    @Test
    void subSet() {
        fillRand();
        SortedSet<Integer> subTreap = treapRand.subSet(5, 10);  // [5, 10)
        List<Integer> listNotCont = new ArrayList<>();
        List<Integer> listCont = new ArrayList<>();
        listCont.add(7);
        listCont.add(6);
        listCont.add(5);
        listCont.add(8);
        listNotCont.add(10);
        listNotCont.add(3);
        listNotCont.add(15);
        listNotCont.add(11);
        listNotCont.add(2);
        listNotCont.add(70);
        assertTrue(subTreap.containsAll(listCont));
        assertFalse(subTreap.containsAll(listNotCont));
    }

    @Test
    void headSet() {
        fillRand();
        SortedSet<Integer> headTreap = treapRand.headSet(7);   // < 7
        List<Integer> listNotCont = new ArrayList<>();
        List<Integer> listCont = new ArrayList<>();
        listCont.add(2);
        listCont.add(3);
        listCont.add(6);
        listCont.add(5);
        listNotCont.add(7);
        listNotCont.add(8);
        listNotCont.add(10);
        listNotCont.add(15);
        listNotCont.add(11);
        listNotCont.add(70);
        assertTrue(headTreap.containsAll(listCont));
        assertFalse(headTreap.containsAll(listNotCont));
    }

    @Test
    void tailSet() {
        fillRand();
        SortedSet<Integer> tailTreap = treapRand.tailSet(7);   // >= 7
        List<Integer> listCont = new ArrayList<>();
        List<Integer> listNotCont = new ArrayList<>();
        listCont.add(7);
        listCont.add(8);
        listCont.add(10);
        listCont.add(15);
        listCont.add(11);
        listCont.add(70);
        listNotCont.add(2);
        listNotCont.add(3);
        listNotCont.add(6);
        listNotCont.add(5);
        assertTrue(tailTreap.containsAll(listCont));
        assertFalse(tailTreap.containsAll(listNotCont));
    }

    @Test
    void first() {
        fillRand();
        assertEquals(2, treapRand.first());
    }

    @Test
    void last() {
        fillRand();
        assertEquals(70, treapRand.last());
    }
}