package org.example.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class HashSet1Test {

    private HashSet1<Integer> set;
    private HashSet<Integer> originalSet;

    @BeforeEach
    void setUp() {
        set = new HashSet1<>();
        originalSet = new HashSet<>();

        List<Integer> ints = new ArrayList<>(IntStream.range(0, 20).boxed().toList());
        Collections.shuffle(ints);
        for (Integer e : ints) {
            set.add(e);
            originalSet.add(e);

            set.add(null);
            originalSet.add(null);

            set.add(543);
            originalSet.add(543);
        }
    }

    @Test
    void testConstructors() {
        assertEquals(new HashSet<>(), new HashSet1<>());
        assertEquals(new HashSet<>(87), new HashSet1<>(87));
        assertEquals(originalSet, set);
        assertEquals(originalSet, new HashSet1<>(set));

        assertThrows(IllegalArgumentException.class, () -> new HashSet1<>(-1));
        assertThrows(IllegalArgumentException.class, () -> new HashSet1<>(4, 0));
    }
    
    @Test
    void testAdd() {
        assertEquals(originalSet.add(4), set.add(4));
        assertEquals(originalSet.add(5), set.add(5));
        assertEquals(originalSet.add(-999), set.add(-999));
        assertEquals(originalSet.add(null), set.add(null));
        assertEquals(originalSet, set);
    }

    @Test
    void testRemove() {
        assertEquals(originalSet.remove(4), set.remove(4));
        assertEquals(originalSet.remove(5), set.remove(5));
        assertEquals(originalSet.remove(25), set.remove(25));
        assertEquals(originalSet.remove(-999), set.remove(-999));
        assertEquals(originalSet.remove(null), set.remove(null));
        assertEquals(originalSet, set);
    }

    @Test
    void testContains() {
        assertEquals(originalSet.contains(4), set.contains(4));
        assertEquals(originalSet.contains(5), set.contains(5));
        assertEquals(originalSet.contains(-999), set.contains(-999));
        assertEquals(originalSet.contains(null), set.contains(null));
    }

    @Test
    void testClear() {
        originalSet.clear();
        set.clear();
        assertEquals(originalSet, set);
    }

    @Test
    void testSize() {
        assertEquals(originalSet.size(), set.size());

        assertEquals(originalSet.add(4), set.add(4));
        assertEquals(originalSet.add(-999), set.add(-999));
        assertEquals(originalSet.add(null), set.add(null));
        assertEquals(originalSet.size(), set.size());

        assertEquals(originalSet.remove(4), set.remove(4));
        assertEquals(originalSet.remove(-999), set.remove(-999));
        assertEquals(originalSet.remove(null), set.remove(null));
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
    void testToArray() {
        assertArrayEquals(new HashSet<>().toArray(), new HashSet1<>().toArray());
//        assertArrayEquals(new HashSet<>().toArray(new Integer[] {5, 2, 3}), new HashSet1<>().toArray(new Integer[] { 5, 2, 3}));

        List<Integer> listFromArrayFromSet = Arrays.stream(set.toArray()).mapToInt(e -> e == null ? 0 : (int)e).sorted().boxed().toList();
        List<Integer> listFromArrayFromOriginalSet = Arrays.stream(originalSet.toArray()).mapToInt(e -> (e == null ? 0 : (int)e)).sorted().boxed().toList();
        assertEquals(listFromArrayFromOriginalSet, listFromArrayFromSet);
        assertArrayEquals(originalSet.toArray(new Integer[] {2, 4, 77}), set.toArray(new Integer[] {2, 4, 77}));
    }

    @Test
    void testRemoveIf() {
        set.removeIf(e -> false);
        originalSet.removeIf(e -> false);
        assertEquals(originalSet, set);

        set.removeIf(e -> e != null && e > 5 && e < 15);
        originalSet.removeIf(e -> e != null && e > 5 && e < 15);
        assertEquals(originalSet, set);

        set.removeIf(e -> true);
        originalSet.removeIf(e -> true);
        assertEquals(originalSet, set);
    }

    @Test
    void testForEach() {
        List<Integer> listSet = new ArrayList<>();
        List<Integer> listOriginalSet = new ArrayList<>();
        set.forEach(listSet::add);
        originalSet.forEach(listOriginalSet::add);
        listSet.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        listOriginalSet.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        assertEquals(listSet, listOriginalSet);
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
}