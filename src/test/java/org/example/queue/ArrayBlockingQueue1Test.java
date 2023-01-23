package org.example.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ArrayBlockingQueue1Test {

    private ArrayBlockingQueue1<Integer> queue;
    private ArrayBlockingQueue<Integer> originalQueue;

    @BeforeEach
    void setUp() {
        List<Integer> ints = List.of(3, 5, 1, 4, 5, 1, 3, 4, 5, 10, 11);
        queue = new ArrayBlockingQueue1<>(15, true, ints);
        originalQueue = new ArrayBlockingQueue<>(15, true, ints);
    }

    @Test
    void testConstructors() {
        assertEquals(queue, originalQueue);
        assertTrue(new ArrayBlockingQueue1<Integer>(0).isEmpty());

        queue = new ArrayBlockingQueue1<>(3);
        assertEquals(0, queue.size());
    }

    @Test
    void testPut() throws InterruptedException {
        int remainingCapacity = queue.remainingCapacity();
        for (int i = 0; i < remainingCapacity; i++) {
            queue.put(i);
            originalQueue.put(i);
        }
        assertEquals(queue, originalQueue);
        assertEquals(originalQueue.remainingCapacity(), queue.remainingCapacity());

        assertThrows(AssertionFailedError.class,
                () -> assertTimeoutPreemptively(Duration.ofSeconds(1), () -> queue.put(1)));
        assertThrows(AssertionFailedError.class,
                () -> assertTimeoutPreemptively(Duration.ofSeconds(1), () -> originalQueue.put(1)));
        assertEquals(queue, originalQueue);

        assertEquals(originalQueue.take(), queue.take());
        assertEquals(queue, originalQueue);

        queue.put(4);
        originalQueue.put(4);
        assertEquals(queue, originalQueue);
    }

    @Test
    void testOffer() throws InterruptedException {
        int remainingCapacity = queue.remainingCapacity();
        for (int i = 0; i < remainingCapacity; i++)
            assertEquals(originalQueue.offer(i), queue.offer(i));
        assertEquals(queue, originalQueue);
        assertEquals(originalQueue.remainingCapacity(), queue.remainingCapacity());

        assertEquals(originalQueue.offer(2), queue.offer(2));
        assertEquals(queue, originalQueue);

        assertEquals(originalQueue.take(), queue.take());
        assertEquals(originalQueue.take(), queue.take());
        assertEquals(queue, originalQueue);

        assertEquals(originalQueue.offer(4), queue.offer(4));
        assertEquals(queue, originalQueue);

        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> queue.offer(3));
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> originalQueue.offer(3));
        assertEquals(queue, originalQueue);

        remainingCapacity = queue.remainingCapacity();
        for (int i = 0; i < remainingCapacity; i++)
            assertEquals(originalQueue.offer(i), queue.offer(i));

        assertFalse(queue.offer(5));
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> !queue.offer(1, 1, TimeUnit.SECONDS));
    }

    @Test
    void testTake() throws InterruptedException {
        while (!queue.isEmpty())
            assertEquals(originalQueue.take(), queue.take());
        assertEquals(queue, originalQueue);

        assertThrows(AssertionFailedError.class,
                () -> assertTimeoutPreemptively(Duration.ofSeconds(1), () -> queue.take()));
        assertThrows(AssertionFailedError.class,
                () -> assertTimeoutPreemptively(Duration.ofSeconds(1), () -> originalQueue.take()));
        assertEquals(queue, originalQueue);

        queue.put(5);
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> queue.take());

        assertEquals(queue, originalQueue);
    }

    @Test
    void testPoll() throws InterruptedException {
        while (!queue.isEmpty())
            assertEquals(originalQueue.poll(), queue.poll());
        assertEquals(queue, originalQueue);

        assertEquals(originalQueue.poll(), queue.poll());
        assertEquals(queue, originalQueue);

        assertThrows(AssertionFailedError.class,
                () -> assertTimeoutPreemptively(Duration.ofSeconds(1), () -> queue.poll(2, TimeUnit.SECONDS)));
        assertThrows(AssertionFailedError.class,
                () -> assertTimeoutPreemptively(Duration.ofSeconds(1), () -> originalQueue.poll(2, TimeUnit.SECONDS)));
        assertEquals(queue, originalQueue);

        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> queue.poll(1, TimeUnit.SECONDS));
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> queue.poll(1, TimeUnit.SECONDS));
        assertEquals(queue, originalQueue);

        queue.put(5);
        originalQueue.put(5);

        assertEquals(originalQueue.poll(), queue.poll());
        assertEquals(queue, originalQueue);
    }

    @Test
    void testPeek() {
        assertEquals(originalQueue.peek(), queue.peek());
        assertEquals(queue, originalQueue);

        queue.clear();
        originalQueue.clear();

        assertEquals(originalQueue.peek(), queue.peek());
        assertEquals(queue, originalQueue);
    }

    @Test
    void testDrainTo() throws InterruptedException {
        List<Integer> listFromQueue = new ArrayList<>();
        List<Integer> listFromOriginalQueue = new ArrayList<>();
        assertEquals(originalQueue.drainTo(listFromOriginalQueue), queue.drainTo(listFromQueue));
        assertEquals(listFromOriginalQueue, listFromQueue);
        assertEquals(queue, originalQueue);

        assertEquals(originalQueue.drainTo(listFromOriginalQueue), queue.drainTo(listFromQueue));
        assertEquals(listFromOriginalQueue, listFromQueue);
        assertEquals(queue, originalQueue);

        for (int i = 0; i < queue.capacity(); i++) {
            queue.put(i);
            originalQueue.put(i);
        }
        listFromQueue.clear();
        listFromOriginalQueue.clear();
        assertEquals(originalQueue.drainTo(listFromOriginalQueue, 4), queue.drainTo(listFromQueue, 4));
        assertEquals(listFromOriginalQueue, listFromQueue);
        assertEquals(queue, originalQueue);

        assertEquals(originalQueue.drainTo(listFromOriginalQueue, queue.size()), queue.drainTo(listFromQueue, queue.size()));
        assertEquals(listFromOriginalQueue, listFromQueue);
        assertEquals(queue, originalQueue);

        assertThrows(IllegalArgumentException.class, () -> queue.drainTo(queue));
    }

    @Test
    void testRemainingCapacity() throws InterruptedException {
        assertEquals(originalQueue.remainingCapacity(), queue.remainingCapacity());

        queue.put(3);
        originalQueue.put(3);
        assertEquals(originalQueue.remainingCapacity(), queue.remainingCapacity());

        queue.take();
        originalQueue.take();
        assertEquals(originalQueue.remainingCapacity(), queue.remainingCapacity());

        queue.clear();
        originalQueue.clear();
        assertEquals(originalQueue.remainingCapacity(), queue.remainingCapacity());
    }

    @Test
    void testSize() throws InterruptedException {
        assertEquals(originalQueue.size(), queue.size());

        queue.put(3);
        originalQueue.put(3);
        assertEquals(originalQueue.size(), queue.size());

        queue.take();
        originalQueue.take();
        assertEquals(originalQueue.size(), queue.size());

        queue.clear();
        originalQueue.clear();
        assertEquals(originalQueue.size(), queue.size());
    }

    @Test
    void testIsEmpty() throws InterruptedException {
        assertEquals(originalQueue.isEmpty(), queue.isEmpty());

        queue.put(3);
        originalQueue.put(3);
        assertEquals(originalQueue.isEmpty(), queue.isEmpty());

        queue.take();
        originalQueue.take();
        assertEquals(originalQueue.isEmpty(), queue.isEmpty());

        queue.clear();
        originalQueue.clear();
        assertEquals(originalQueue.isEmpty(), queue.isEmpty());
    }

    @Test
    void testClear() {
        queue.clear();
        originalQueue.clear();
        assertEquals(queue, originalQueue);
    }

    @Test
    void testToArray() throws InterruptedException {
        assertArrayEquals(originalQueue.toArray(), queue.toArray());

        queue.put(3);
        originalQueue.put(3);
        assertArrayEquals(originalQueue.toArray(), queue.toArray());

        queue.take();
        originalQueue.take();
        assertArrayEquals(originalQueue.toArray(), queue.toArray());

        queue.clear();
        originalQueue.clear();
        assertArrayEquals(originalQueue.toArray(), queue.toArray());
    }

    @Test
    void testIterator() {
        Iterator<Integer> iter = queue.iterator();
        Iterator<Integer> iterOriginal = originalQueue.iterator();
        while (iter.hasNext() && iterOriginal.hasNext())
            assertEquals(iterOriginal.next(), iter.next());
        assertEquals(iterOriginal.hasNext(), iter.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter.next());

        queue.clear();
        originalQueue.clear();
        Iterator<Integer> iter2 = queue.iterator();
        Iterator<Integer> iterOriginal2 = originalQueue.iterator();
        assertEquals(iterOriginal2.hasNext(), iter2.hasNext());
        assertThrows(NoSuchElementException.class, () -> iter2.next());
    }

    @Test
    void testHashCode() {
        assertEquals(queue.hashCode(), new ArrayBlockingQueue1<>(15, true, queue).hashCode());
        assertNotEquals(queue.hashCode(), new ArrayBlockingQueue1<>(15).hashCode());
    }
}