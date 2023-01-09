package org.example.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TreeSet1Test {

    private TreeSet1<Integer> set;
    private TreeSet<Integer> originalSet;

    @BeforeEach
    void SetUp() {
        set = new TreeSet1<>(Comparator.reverseOrder());
        originalSet = new TreeSet<>(Comparator.reverseOrder());
        List<Integer> ints = new ArrayList<>(IntStream.range(0, 20).boxed().toList());
        Collections.shuffle(ints);
        for (Integer e : ints) {
            set.add(e);
            originalSet.add(e);
        }
        ints = new Random().ints(300, -100, 100).boxed().toList();
        for (Integer e : ints) {
            set.add(e);
            originalSet.add(e);

            set.add(543);
            originalSet.add(543);
        }
    }

    @Test
    void testConstructors() {
        assertEquals(new TreeSet<>(), new TreeSet1<>());
        assertEquals(new TreeSet<>(Comparator.naturalOrder()), new TreeSet1<>(Comparator.naturalOrder()));
        assertEquals(new TreeSet<>(Comparator.reverseOrder()), new TreeSet1<>(Comparator.reverseOrder()));

        assertEquals(originalSet, set);
        assertEquals(originalSet, new TreeSet1<>(set));
        assertEquals(originalSet, new TreeSet1<>(originalSet));

        List<Integer> list = List.of(4, 5, 2, 5, 1);
        assertEquals(new TreeSet<>(list), new TreeSet1<>(list));
    }
    @Test
    void testAdd() {
        assertEquals(originalSet.add(4), set.add(4));
        assertEquals(originalSet.add(5), set.add(5));
        assertEquals(originalSet.add(-999), set.add(-999));
        assertEquals(originalSet, set);

        assertThrows(NullPointerException.class, () -> set.add(null));
    }

    @Test
    void testAddAll() {
        Set<Integer> set1 = new TreeSet1<>();
        set1.add(888);
        set1.add(777);
        set.addAll(set1);
        originalSet.addAll(set1);
        assertEquals(originalSet, set);

        set = new TreeSet1<>();
        set.addAll(originalSet);
        assertEquals(originalSet, set);
    }

    @Test
    void testRemove() {
        assertEquals(originalSet.remove(4), set.remove(4));
        assertEquals(originalSet.remove(5), set.remove(5));
        assertEquals(originalSet.remove(25), set.remove(25));
        assertEquals(originalSet.remove(-999), set.remove(-999));
        assertEquals(originalSet, set);

        assertThrows(NullPointerException.class, () -> set.remove(null));
    }

    @Test
    void testContains() {
        assertEquals(originalSet.contains(4), set.contains(4));
        assertEquals(originalSet.contains(5), set.contains(5));
        assertEquals(originalSet.contains(-999), set.contains(-999));
    }

    @Test
    void testSize() {
        assertEquals(originalSet.size(), set.size());

        assertEquals(originalSet.add(4), set.add(4));
        assertEquals(originalSet.add(-999), set.add(-999));
        assertEquals(originalSet.size(), set.size());

        assertEquals(originalSet.remove(4), set.remove(4));
        assertEquals(originalSet.remove(-999), set.remove(-999));
        assertEquals(originalSet.size(), set.size());
    }

    @Test
    void testIsEmpty() {
        assertEquals(originalSet.isEmpty(), set.isEmpty());
        assertEquals(originalSet.add(4), set.add(4));
        assertEquals(originalSet.isEmpty(), set.isEmpty());
        assertEquals(originalSet.remove(4), set.remove(4));
        assertEquals(originalSet.isEmpty(), set.isEmpty());
    }

    @Test
    void testClear() {
        originalSet.clear();
        set.clear();
        assertEquals(originalSet, set);
    }

    @Test
    void testIterator() {
        List<Integer> listSet = new ArrayList<>();
        List<Integer> listOriginalSet = new ArrayList<>();
        Iterator<Integer> iteratorSet = set.iterator();
        Iterator<Integer> iteratorOriginalSet = originalSet.iterator();
        while (iteratorOriginalSet.hasNext() && iteratorSet.hasNext()) {
            listSet.add(iteratorSet.next());
            listOriginalSet.add(iteratorOriginalSet.next());
        }
        assertEquals(iteratorOriginalSet.hasNext(), iteratorSet.hasNext());

        listSet.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        listOriginalSet.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        assertEquals(listOriginalSet, listSet);
    }

    @Test
    void testDescendingIterator() {
        List<Integer> listSet = new ArrayList<>();
        List<Integer> listOriginalSet = new ArrayList<>();
        Iterator<Integer> iteratorSet = set.descendingIterator();
        Iterator<Integer> iteratorOriginalSet = originalSet.descendingIterator();
        while (iteratorOriginalSet.hasNext() && iteratorSet.hasNext()) {
            listSet.add(iteratorSet.next());
            listOriginalSet.add(iteratorOriginalSet.next());
        }
        assertEquals(iteratorOriginalSet.hasNext(), iteratorSet.hasNext());

        listSet.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        listOriginalSet.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        assertEquals(listOriginalSet, listSet);
    }
}