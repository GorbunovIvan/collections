package org.example.queue;

import org.example.list.ArrayList1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueue1Test {

    private PriorityQueue1<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new PriorityQueue1<>(List.of(3, 5, 1, 4, 5, 1, 3, 4, 5, 10, 11));
    }

    @Test
    void testConstructors() {
        assertFalse(queue.isEmpty());
        assertTrue(new PriorityQueue1<Integer>().isEmpty());

        Object[] arraySorted = queue.toArray();
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, queue.toArray());

        queue = new PriorityQueue1<>(3);
        assertEquals(0, queue.size());

        queue = new PriorityQueue1<>(3, Comparator.reverseOrder());
        assertEquals(0, queue.size());
        assertEquals(Comparator.reverseOrder(), queue.comparator());

        queue = new PriorityQueue1<>(Comparator.reverseOrder());
        assertEquals(0, queue.size());

        queue = new PriorityQueue1<>(Comparator.naturalOrder());
        queue.offer(1);
        queue.offer(3);
        assertArrayEquals(new Integer[]{1, 3}, queue.toArray());

        queue = new PriorityQueue1<>(Comparator.reverseOrder());
        queue.offer(1);
        queue.offer(3);
        assertArrayEquals(new Integer[]{3, 1}, queue.toArray());
    }

    @Test
    void testComparator() {
        queue = new PriorityQueue1<>();
        assertNull(queue.comparator());

        queue = new PriorityQueue1<>(Comparator.naturalOrder());
        assertEquals(Comparator.naturalOrder(), queue.comparator());
    }

    @Test
    void testSetComparator() {
        assertThrows(IllegalStateException.class, () -> queue.setComparator(Comparator.reverseOrder()));
        queue.clear();
        queue.setComparator(Comparator.reverseOrder());
        assertEquals(Comparator.reverseOrder(), queue.comparator());

        queue.setComparator(null);
        assertNull(queue.comparator());
    }

    @Test
    void testAdd() {
        queue = new PriorityQueue1<>();
        queue.add(1);
        queue.add(2);
        queue.add(0);
        assertArrayEquals(new Integer[]{0, 1, 2}, queue.toArray());
    }

    @Test
    void testAddAll() {
        Object[] arraySorted = queue.toArray();
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, queue.toArray());

        queue = new PriorityQueue1<>();
        queue.addAll(List.of(1, 2, 0));
        assertArrayEquals(new Integer[]{0, 1, 2}, queue.toArray());

        queue.addAll(List.of(7, 2, 10));
        assertArrayEquals(new Integer[]{0, 1, 2, 2, 7, 10}, queue.toArray());

        queue = new PriorityQueue1<>();
        queue.addAll(Collections.emptyList());
        assertArrayEquals(new Integer[0], queue.toArray());
    }

    @Test
    void testOffer() {
        queue = new PriorityQueue1<>();
        List<Integer> list = new ArrayList1<>(new Random()
                                            .ints(55, -7, 18)
                                            .boxed().toList());
        for (int e : list)
            queue.offer(e);

        list.sort(Comparator.naturalOrder());
        assertEquals(list, queue.stream().toList());

        list.sort(Comparator.reverseOrder());
        assertNotEquals(list, queue.stream().toList());
    }

    @Test
    void testPoll() {
        Integer previous = queue.poll();
        while (!queue.isEmpty())
            assertTrue(queue.poll().compareTo(previous) >= 0);
        assertNull(queue.poll());
    }

    @Test
    void testPeek() {
        int sizeOld = queue.size();
        int value = queue.peek();
        for (int i = 1; i < sizeOld; i++)
            assertEquals(value, queue.peek());
        assertEquals(sizeOld, queue.size());

        queue.clear();
        assertNull(queue.peek());
    }

    @Test
    void testElement() {
        int sizeOld = queue.size();
        int value = queue.element();
        for (int i = 1; i < sizeOld; i++)
            assertEquals(value, queue.element());
        assertEquals(sizeOld, queue.size());

        queue.clear();
        assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @Test
    void testRemove() {
        Integer previous = queue.remove();
        while (!queue.isEmpty())
            assertTrue(queue.remove().compareTo(previous) >= 0);
        assertThrows(NoSuchElementException.class, () -> queue.remove());
    }

    @Test
    void testClear() {
        queue.clear();
        assertEquals(0, queue.size());
        assertThrows(NoSuchElementException.class, () -> queue.remove());
    }

    @Test
    void testSize() {
        assertEquals(11, queue.size());
        assertEquals(0, new PriorityQueue1<>().size());

        queue.clear();
        assertEquals(0, queue.size());
    }

    @Test
    void testIsEmpty() {
        assertFalse(queue.isEmpty());
        assertTrue(new PriorityQueue1<>().isEmpty());

        queue.clear();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testContains() {
        assertTrue(queue.contains(5));
        assertFalse(queue.contains(555));
    }

    @Test
    void testContainsAll() {
        assertTrue(queue.containsAll(List.of(3, 1, 5, 11)));
        assertFalse(queue.containsAll(List.of(3, 1, 5, 98, 11)));
        assertFalse(queue.containsAll(Collections.emptyList()));
    }

    @Test
    void testIndexOf() {
        queue = new PriorityQueue1<>();
        for (int i = 0; i < 100; i++)
            queue.offer(i);
        for (int i = 0; i < 100; i++)
            assertEquals(i, queue.indexOf(i));

        queue = new PriorityQueue1<>(Comparator.reverseOrder());
        for (int i = 0; i < 100; i++)
            queue.offer(i);
        for (int i = 0; i < 100; i++)
            assertEquals(99-i, queue.indexOf(i));

        assertEquals(-1, queue.indexOf(555));
    }

    @Test
    void testLastIndexOf() {
        queue = new PriorityQueue1<>();
        for (int i = 0; i < 100; i++)
            queue.offer(i);
        for (int i = 0; i < 100; i++)
            assertEquals(i, queue.lastIndexOf(i));

        queue = new PriorityQueue1<>(Comparator.reverseOrder());
        for (int i = 0; i < 100; i++)
            queue.offer(i);
        for (int i = 0; i < 100; i++)
            assertEquals(99-i, queue.lastIndexOf(i));

        queue.offer(0);
        assertEquals(queue.size()-2, queue.indexOf(0));
        assertEquals(queue.size()-1, queue.lastIndexOf(0));

        assertEquals(-1, queue.lastIndexOf(555));
    }

    @Test
    void testForEach() {
        queue = new PriorityQueue1<>();
        for (int i = 0; i < 10; i++)
            queue.offer(i);
        StringBuilder sb = new StringBuilder();
        queue.forEach(sb::append);
        assertEquals("0123456789", sb.toString());
    }

    @Test
    void testIterator() {
        queue = new PriorityQueue1<>();
        for (int i = 0; i < 10; i++)
            queue.offer(i);
        int i = 0;
        Iterator<Integer> iter = queue.iterator();
        while (iter.hasNext())
            assertEquals(i++, iter.next());
        assertEquals(i, 10);
        assertThrows(NoSuchElementException.class, iter::next);
    }
}