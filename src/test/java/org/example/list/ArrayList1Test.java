package org.example.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArrayList1Test {

    private ArrayList1<Integer> list;
    private ArrayList<Integer> originalList;

    @BeforeEach
    void setUp() {
        List<Integer> listPrototype = List.of(3, 5, 1, 4, 5, 1, 3, 4, 5, 10, 11);
        list = new ArrayList1<>(listPrototype);
        originalList = new ArrayList<>(listPrototype);
    }

    @Test
    void testConstructors() {
        assertEquals(originalList, list);
        assertEquals(new ArrayList<>(), new ArrayList1<>());
        assertEquals(new ArrayList<>(87), new ArrayList1<>(87));
    }
    
    @Test
    void testGet() {
        assertEquals(originalList.get(0), list.get(0));
        assertEquals(originalList.get(5), list.get(2));
        assertEquals(originalList.get(originalList.size()-1), list.get(list.size()-1));
    }

    @Test
    void testSet() {
        list.set(0, 99);
        originalList.set(0, 99);
        assertEquals(originalList, list);

        list.set(5, 87);
        originalList.set(5, 87);
        assertEquals(originalList, list);

        list.set(list.size()-1, 14);
        originalList.set(originalList.size()-1, 14);
        assertEquals(originalList, list);
    }

    @Test
    void testAdd() {
        list.add(44);
        originalList.add(44);
        assertEquals(originalList, list);

        list.add(5, 55);
        originalList.add(5, 55);
        assertEquals(originalList, list);
    }

    @Test
    void testAddAll() {
        boolean resultList = list.addAll(Collections.emptyList());
        boolean resultOriginalList = originalList.addAll(Collections.emptyList());
        assertEquals(resultList, resultOriginalList);
        assertEquals(originalList, list);

        resultList = list.addAll(List.of(5, 3, 4));
        resultOriginalList = originalList.addAll(List.of(5, 3, 4));
        assertEquals(resultList, resultOriginalList);
        assertEquals(originalList, list);

        resultList = list.addAll(5, Collections.emptyList());
        resultOriginalList = originalList.addAll(5, Collections.emptyList());
        assertEquals(resultList, resultOriginalList);
        assertEquals(originalList, list);

        resultList = list.addAll(5, List.of(99, 98, 97));
        resultOriginalList = originalList.addAll(5, List.of(99, 98, 97));
        assertEquals(resultList, resultOriginalList);
        assertEquals(originalList, list);
    }

    @Test
    void testRemove() {
        list.remove(10);
        originalList.remove(10);
        assertEquals(originalList, list);

        list.remove(5);
        originalList.remove(5);
        assertEquals(originalList, list);

        list.remove(Integer.valueOf(4));
        originalList.remove(Integer.valueOf(4));
        assertEquals(originalList, list);
        assertEquals(list.get(7), list.remove(7));
        assertEquals(list.get(0), list.remove(0));

        assertEquals(originalList.remove(Integer.valueOf(1)), list.remove(Integer.valueOf(1)));
        assertEquals(originalList.remove(Integer.valueOf(100)), list.remove(Integer.valueOf(100)));
    }

    @Test
    void testRemoveAllObjects() {
        boolean result = list.removeAll(5);
        assertTrue(result);
        assertEquals(8, list.size());

        result = list.removeAll(1);
        assertTrue(result);
        assertEquals(6, list.size());

        result = list.removeAll(100);
        assertFalse(result);
    }

    @Test
    void testRemoveAll() {
        boolean resultList = list.removeAll(Collections.emptyList());
        boolean resultOriginalList = originalList.removeAll(Collections.emptyList());
        assertEquals(resultOriginalList, resultList);
        assertEquals(originalList, list);

        resultList = list.removeAll(List.of(4, 5, 10, 100));
        resultOriginalList = originalList.removeAll(List.of(4, 5, 10, 100));
        assertEquals(resultOriginalList, resultList);
        assertEquals(originalList, list);
    }

    @Test
    void testRetainAll() {
        boolean resultList = list.retainAll(List.of(4, 5, 10, 100));
        boolean resultOriginalList = originalList.retainAll(List.of(4, 5, 10, 100));
        assertEquals(resultOriginalList, resultList);
        assertEquals(originalList, list);

        resultList = list.retainAll(Collections.emptyList());
        resultOriginalList = originalList.retainAll(Collections.emptyList());
        assertEquals(resultOriginalList, resultList);
        assertEquals(originalList, list);

        resultList = list.retainAll(Collections.emptyList());
        resultOriginalList = originalList.retainAll(Collections.emptyList());
        assertEquals(resultOriginalList, resultList);
        assertEquals(originalList, list);
    }

    @Test
    void testClear() {
        list.clear();
        assertEquals(0, list.size());
        assertEquals(10, list.capacity());
    }

    @Test
    void testIndexOf() {
        assertEquals(originalList.indexOf(null), list.indexOf(null));

        for (Integer o : list)
            assertEquals(originalList.indexOf(o), list.indexOf(o));

        list.add(null);
        originalList.add(null);
        assertEquals(originalList.indexOf(null), list.indexOf(null));
    }

    @Test
    void testLastIndexOf() {
        assertEquals(originalList.lastIndexOf(null), list.lastIndexOf(null));

        for (Integer o : list)
            assertEquals(originalList.lastIndexOf(o), list.lastIndexOf(o));

        list.add(null);
        originalList.add(null);
        assertEquals(originalList.lastIndexOf(null), list.lastIndexOf(null));
    }

    @Test
    void testContains() {
        assertEquals(originalList.contains(5), list.contains(5));
        assertEquals(originalList.contains(100), list.contains(100));
        assertEquals(originalList.contains(originalList.get(originalList.size()-1)), list.contains(list.get(list.size()-1)));
    }

    @Test
    void testContainsAll() {
        assertEquals(originalList.containsAll(Collections.emptyList()), list.containsAll(Collections.emptyList()));
        assertEquals(originalList.containsAll(List.of(97, 98)), list.containsAll(List.of(97, 98)));
        assertEquals(originalList.containsAll(List.of(5, 10, 14)), list.containsAll(List.of(5, 10, 14)));
        assertEquals(originalList.containsAll(List.of(5, 10, 3)), list.containsAll(List.of(5, 10, 3)));
    }

    @Test
    void testForEach() {
        StringBuilder listStringBuilder = new StringBuilder();
        list.forEach(listStringBuilder::append);

        StringBuilder originalListStringBuilder = new StringBuilder();
        originalList.forEach(originalListStringBuilder::append);

        assertEquals(originalListStringBuilder.toString(), listStringBuilder.toString());
    }

    @Test
    void testSize() {
        assertEquals(originalList.size(), list.size());
        assertEquals(0, new ArrayList1().size());
    }

    @Test
    void testCapacity() {
        assertEquals(11, list.capacity());
        assertEquals(10, new ArrayList1().capacity());
    }

    @Test
    void testIsEmpty() {
        assertEquals(originalList.isEmpty(), list.isEmpty());
        assertEquals(new ArrayList().isEmpty(), new ArrayList1().isEmpty());
    }

    @Test
    void testEnsureCapacity() {
        int newCapacity = list.capacity() + 7;
        list.ensureCapacity(newCapacity);
        assertEquals(newCapacity, list.capacity());
    }

    @Test
    void testTrimToSize() {
        list.trimToSize();
        assertEquals(list.size(), list.capacity());

        list.clear();
        list.trimToSize();
        assertEquals(10, list.capacity());

        list.add(5);
        list.trimToSize();
        assertEquals(10, list.capacity());
    }

    @Test
    void testCompressCapacity() {
        for (int i = 0; i < 100; i++)
            list.add(i);
        for (int i = list.size()-1; i >= 20; i--)
            list.remove(i); // compressing is inside remove()
        assertTrue(list.capacity() < list.size()*2);

        list.clear();
        list.compressCapacity();
        assertEquals(10, list.capacity());
    }

    @Test
    void testToArray() {
        assertArrayEquals(originalList.toArray(), list.toArray());
    }

    @Test
    void testIterator() {
        Iterator<Integer> iteratorOfOriginalList = originalList.iterator();
        Iterator<Integer> iteratorOfList = list.iterator();
        while (iteratorOfOriginalList.hasNext() && iteratorOfList.hasNext()) {
            assertEquals(iteratorOfOriginalList.next(), iteratorOfList.next());
        }
        assertEquals(iteratorOfOriginalList.hasNext(), iteratorOfList.hasNext());

        iteratorOfOriginalList = originalList.iterator();
        iteratorOfList = list.iterator();
        while (iteratorOfOriginalList.hasNext() && iteratorOfList.hasNext()) {
            assertEquals(iteratorOfOriginalList.next(), iteratorOfList.next());
            iteratorOfOriginalList.remove();
            iteratorOfList.remove();
        }
        assertEquals(iteratorOfOriginalList.hasNext(), iteratorOfList.hasNext());
        assertEquals(originalList, list);
    }

    @Test
    void testListIterator() {
        ListIterator<Integer> iteratorOfOriginalList = originalList.listIterator();
        ListIterator<Integer> iteratorOfList = list.listIterator();
        while (iteratorOfOriginalList.hasNext() && iteratorOfList.hasNext()) {
            assertEquals(iteratorOfOriginalList.next(), iteratorOfList.next());
            assertEquals(iteratorOfOriginalList.nextIndex(), iteratorOfList.nextIndex());
            assertEquals(iteratorOfOriginalList.previousIndex(), iteratorOfList.previousIndex());
        }
        assertEquals(iteratorOfOriginalList.hasNext(), iteratorOfList.hasNext());

        while (iteratorOfOriginalList.hasPrevious() && iteratorOfList.hasPrevious()) {
            assertEquals(iteratorOfOriginalList.previous(), iteratorOfList.previous());
            assertEquals(iteratorOfOriginalList.nextIndex(), iteratorOfList.nextIndex());
            assertEquals(iteratorOfOriginalList.previousIndex(), iteratorOfList.previousIndex());
        }
        assertEquals(iteratorOfOriginalList.hasPrevious(), iteratorOfList.hasPrevious());

        while (iteratorOfOriginalList.hasNext() && iteratorOfList.hasNext()) {
            assertEquals(iteratorOfOriginalList.next(), iteratorOfList.next());
            iteratorOfOriginalList.remove();
            iteratorOfList.remove();
        }
        assertEquals(iteratorOfOriginalList.hasNext(), iteratorOfList.hasNext());
        assertEquals(originalList, list);
    }

    @Test
    void testReplaceAll() {
        list.replaceAll(n -> n+1);
        originalList.replaceAll(n -> n+1);
        assertEquals(originalList, list);
    }

    @Test
    void testSort() {
        list.sort(Comparator.naturalOrder());
        originalList.sort(Comparator.naturalOrder());
        assertEquals(originalList, list);

        list.sort(Comparator.reverseOrder());
        originalList.sort(Comparator.reverseOrder());
        assertEquals(originalList, list);
    }

    @Test
    void testClone() {
        assertEquals(list, list.clone());
    }

    @Test
    void testHashCode() {
        assertEquals(originalList.hashCode(), list.hashCode());
        assertEquals(new ArrayList().hashCode(), new ArrayList1().hashCode());
        assertEquals(new ArrayList1<>(List.of(2, 4, 87)).hashCode(), new ArrayList1<>(List.of(2, 4, 87)).hashCode());

        list.add(null);
        originalList.add(null);
        assertEquals(originalList.hashCode(), list.hashCode());

        assertNotEquals(new ArrayList1<>(List.of(2, 4, 87)).hashCode(), new ArrayList1<>(List.of(2, 4, 88)).hashCode());
        assertNotEquals(new ArrayList1<>(List.of(2, 4, 87)).hashCode(), new ArrayList1<>(List.of(2, 87, 4)).hashCode());
        assertNotEquals(new ArrayList1<>(List.of(2, 4, 87)).hashCode(), new ArrayList1<>(List.of(2, 4)).hashCode());
    }

    @Test
    void testEquals() {
        assertEquals(originalList, list);
        assertEquals(list, list);
        assertEquals(new ArrayList1(), new ArrayList());
        assertEquals(new ArrayList1<>(List.of(2, 4, 87)), new ArrayList1<>(List.of(2, 4, 87)));

        list.add(null);
        originalList.add(null);
        assertEquals(originalList, list);
        assertEquals(list, originalList);

        assertNotEquals(list, null);
        assertNotEquals(list, new Object());
        assertNotEquals(new ArrayList1<>(List.of(2, 4, 87)), new ArrayList1<>(List.of(2, 4, 88)));
        assertNotEquals(new ArrayList1<>(List.of(2, 4, 87)), new ArrayList1<>(List.of(2, 87, 4)));
        assertNotEquals(new ArrayList1<>(List.of(2, 4, 87)), new ArrayList1<>(List.of(2, 4)));
    }

    @Test
    void testToString() {
        assertEquals(originalList.toString(), list.toString());
        assertEquals(new ArrayList().toString(), new ArrayList1().toString());
    }
}