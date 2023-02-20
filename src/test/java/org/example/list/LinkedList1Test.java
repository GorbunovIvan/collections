package org.example.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LinkedList1Test {

    private LinkedList1<Integer> list;
    private LinkedList<Integer> originalList;

    @BeforeEach
    void testSetUp() {
        List<Integer> listPrototype = List.of(3, 5, 1, 4, 5, 1, 3, 4, 5, 10, 11);
        list = new LinkedList1<>(listPrototype);
        originalList = new LinkedList<>(listPrototype);
    }

    @Test
    void testConstructors() {
        assertEquals(originalList, list);
        assertEquals(new LinkedList<>(), new LinkedList1<>());
    }

    @Test
    void testAdd() {
        list.add(44);
        originalList.add(44);
        assertEquals(list, originalList);

        list.add(5, 55);
        originalList.add(5, 55);
        assertEquals(list, originalList);

        list.add(0, 103);
        originalList.add(0, 103);
        assertEquals(list, originalList);
    }

    @Test
    void testAddAll() {
        boolean resultList = list.addAll(Collections.emptyList());
        boolean resultOriginalList = originalList.addAll(Collections.emptyList());
        assertEquals(resultList, resultOriginalList);
        assertEquals(list, originalList);

        resultList = list.addAll(List.of(5, 3, 4));
        resultOriginalList = originalList.addAll(List.of(5, 3, 4));
        assertEquals(resultList, resultOriginalList);
        assertEquals(list, originalList);

        resultList = list.addAll(5, Collections.emptyList());
        resultOriginalList = originalList.addAll(5, Collections.emptyList());
        assertEquals(resultList, resultOriginalList);
        assertEquals(list, originalList);

        resultList = list.addAll(5, List.of(99, 98, 97));
        resultOriginalList = originalList.addAll(5, List.of(99, 98, 97));
        assertEquals(resultList, resultOriginalList);
        assertEquals(list, originalList);
    }

    @Test
    void testAddFirst() {
        list.addFirst(44);
        originalList.addFirst(44);
        assertEquals(originalList, list);

        list.addFirst(55);
        originalList.addFirst(55);
        assertEquals(originalList, list);

        list.addFirst(null);
        originalList.addFirst(null);
        assertEquals(originalList, list);

        list.clear();
        originalList.clear();
        list.addFirst(2);
        originalList.addFirst(2);
        assertEquals(originalList, list);
    }

    @Test
    void testAddLast() {
        list.addLast(44);
        originalList.addLast(44);
        assertEquals(originalList, list);

        list.addLast(55);
        originalList.addLast(55);
        assertEquals(originalList, list);

        list.addLast(null);
        originalList.addLast(null);
        assertEquals(originalList, list);

        list.clear();
        originalList.clear();
        list.addLast(2);
        originalList.addLast(2);
        assertEquals(originalList, list);
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
    void testGet() {
        assertEquals(originalList.get(0), list.get(0));
        assertEquals(originalList.get(5), list.get(2));
        assertEquals(originalList.get(originalList.size()-1), list.get(list.size()-1));
    }

    @Test
    void testGetFirst() {
        assertEquals(originalList.getFirst(), list.getFirst());

        originalList.clear();
        list.clear();
        originalList.add(null);
        list.add(null);
        assertEquals(originalList.getFirst(), list.getFirst());
    }

    @Test
    void testGetLast() {
        assertEquals(originalList.getLast(), list.getLast());

        originalList.clear();
        list.clear();
        originalList.add(null);
        list.add(null);
        assertEquals(originalList.getLast(), list.getLast());
    }

    @Test
    void testPop() {
        assertEquals(originalList.pop(), list.pop());
        assertEquals(originalList, list);
    }

    @Test
    void testPoll() {
        assertEquals(originalList.poll(), list.poll());
        assertEquals(originalList, list);
    }

    @Test
    void testPollFirst() {
        assertEquals(originalList.pollFirst(), list.pollFirst());
        assertEquals(originalList, list);
    }

    @Test
    void testPollLast() {
        assertEquals(originalList.pollLast(), list.pollLast());
        assertEquals(originalList, list);
    }

    @Test
    void testElement() {
        assertEquals(originalList.element(), list.element());
        assertEquals(originalList, list);
    }

    @Test
    void testPeek() {
        assertEquals(originalList.peek(), list.peek());
        assertEquals(originalList, list);

        originalList.clear();
        list.clear();
        assertEquals(originalList.peek(), list.peek());
        assertEquals(originalList, list);
    }

    @Test
    void testPeekFirst() {
        assertEquals(originalList.peekFirst(), list.peekFirst());
        assertEquals(originalList, list);

        originalList.clear();
        list.clear();
        assertEquals(originalList.peekFirst(), list.peekFirst());
        assertEquals(originalList, list);
    }

    @Test
    void testPeekLast() {
        assertEquals(originalList.peekLast(), list.peekLast());
        assertEquals(originalList, list);

        originalList.clear();
        list.clear();
        assertEquals(originalList.peekLast(), list.peekLast());
        assertEquals(originalList, list);
    }

    @Test
    void testPush() {
        originalList.push(5);
        list.push(5);
        assertEquals(originalList, list);
    }

    @Test
    void testOffer() {
        list.offer(44);
        originalList.offer(44);
        assertEquals(originalList, list);

        list.offer(55);
        originalList.offer(55);
        assertEquals(originalList, list);

        list.offer(null);
        originalList.offer(null);
        assertEquals(originalList, list);

        list.clear();
        originalList.clear();
        list.offer(2);
        originalList.offer(2);
        assertEquals(originalList, list);
    }

    @Test
    void testOfferFirst() {
        list.offerFirst(44);
        originalList.offerFirst(44);
        assertEquals(originalList, list);

        list.offerFirst(55);
        originalList.offerFirst(55);
        assertEquals(originalList, list);

        list.offerFirst(null);
        originalList.offerFirst(null);
        assertEquals(originalList, list);

        list.clear();
        originalList.clear();
        list.offerFirst(2);
        originalList.offerFirst(2);
        assertEquals(originalList, list);
    }

    @Test
    void testOfferLast() {
        list.offerLast(44);
        originalList.offerLast(44);
        assertEquals(originalList, list);

        list.offerLast(55);
        originalList.offerLast(55);
        assertEquals(originalList, list);

        list.offerLast(null);
        originalList.offerLast(null);
        assertEquals(originalList, list);

        list.clear();
        originalList.clear();
        list.offerLast(2);
        originalList.offerLast(2);
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
    void testRemoveFirst() {
        list.removeFirst();
        originalList.removeFirst();
        assertEquals(originalList, list);

        list.removeFirst();
        originalList.removeFirst();
        assertEquals(originalList, list);
    }

    @Test
    void testRemoveLast() {
        list.removeLast();
        originalList.removeLast();
        assertEquals(originalList, list);

        list.removeLast();
        originalList.removeLast();
        assertEquals(originalList, list);

        list.clear();
        originalList.clear();
        list.add(5);
        originalList.add(5);
        assertEquals(originalList.removeLast(), list.removeLast());
        assertEquals(originalList, list);
    }

    @Test
    void testRemoveFirstOccurrence() {
        list.removeFirstOccurrence(10);
        originalList.removeFirstOccurrence(10);
        assertEquals(originalList, list);

        list.removeFirstOccurrence(5);
        originalList.removeFirstOccurrence(5);
        assertEquals(originalList, list);

        list.removeFirstOccurrence(4);
        originalList.removeFirstOccurrence(4);
        assertEquals(originalList, list);
        assertEquals(originalList.removeFirstOccurrence(originalList.get(7)), list.removeFirstOccurrence(list.get(7)));
        assertEquals(originalList.removeFirstOccurrence(99), list.removeFirstOccurrence(99));

        assertEquals(originalList.removeFirstOccurrence(1), list.removeFirstOccurrence(1));
        assertEquals(originalList.removeFirstOccurrence(100), list.removeFirstOccurrence(100));
    }

    @Test
    void testRemoveLastOccurrence() {
        list.removeLastOccurrence(10);
        originalList.removeLastOccurrence(10);
        assertEquals(originalList, list);

        list.removeLastOccurrence(5);
        originalList.removeLastOccurrence(5);
        assertEquals(originalList, list);

        list.removeLastOccurrence(4);
        originalList.removeLastOccurrence(4);
        assertEquals(originalList, list);
        assertEquals(originalList.removeLastOccurrence(originalList.get(7)), list.removeLastOccurrence(list.get(7)));
        assertEquals(originalList.removeLastOccurrence(99), list.removeLastOccurrence(99));

        assertEquals(originalList.removeLastOccurrence(1), list.removeLastOccurrence(1));
        assertEquals(originalList.removeLastOccurrence(100), list.removeLastOccurrence(100));
    }

    @Test
    void testClear() {
        list.clear();
        assertEquals(0, list.size());
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
    void testSize() {
        assertEquals(originalList.size(), list.size());

        list.removeLast();
        originalList.removeLast();
        assertEquals(originalList.size(), list.size());

        list.clear();
        originalList.clear();
        assertEquals(originalList.size(), list.size());

        assertEquals(0, new LinkedList1().size());
    }

    @Test
    void testIsEmpty() {
        assertEquals(originalList.isEmpty(), list.isEmpty());

        list.removeLast();
        originalList.removeLast();
        assertEquals(originalList.size(), list.size());
        assertEquals(originalList, list);

        list.clear();
        originalList.clear();
        assertEquals(originalList.size(), list.size());

        assertEquals(new LinkedList().isEmpty(), new LinkedList1().isEmpty());
    }

    @Test
    void testToArray() {
        assertArrayEquals(originalList.toArray(), list.toArray());
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
    void testDescendingIterator() {
        Iterator<Integer> iteratorOfOriginalList = originalList.descendingIterator();
        Iterator<Integer> iteratorOfList = list.descendingIterator();
        while (iteratorOfOriginalList.hasNext() && iteratorOfList.hasNext()) {
            assertEquals(iteratorOfOriginalList.next(), iteratorOfList.next());
        }
        assertEquals(iteratorOfOriginalList.hasNext(), iteratorOfList.hasNext());

        iteratorOfOriginalList = originalList.descendingIterator();
        iteratorOfList = list.descendingIterator();
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
            Integer valueList = iteratorOfList.next();
            Integer valueOriginalList = iteratorOfOriginalList.next();
            assertEquals(valueOriginalList, valueList);

            assertEquals(iteratorOfOriginalList.nextIndex(), iteratorOfList.nextIndex());
            assertEquals(iteratorOfOriginalList.previousIndex(), iteratorOfList.previousIndex());

            iteratorOfList.set(valueList + 7);
            iteratorOfOriginalList.set(valueOriginalList + 7);
            iteratorOfOriginalList.remove();
            iteratorOfList.remove();
        }
        assertEquals(iteratorOfOriginalList.hasNext(), iteratorOfList.hasNext());
        assertEquals(originalList, list);
    }
}