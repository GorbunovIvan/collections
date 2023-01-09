package org.example.queue;

import java.util.*;
import java.util.function.Consumer;

public class PriorityQueue1<E>  extends AbstractQueue<E> {

    private Object[] queue;
    private int size;
    private Comparator<? super E> comparator;

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    public PriorityQueue1() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    public PriorityQueue1(int initCapacity) {
        this(initCapacity, null);
    }

    public PriorityQueue1(Comparator<? super E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }

    public PriorityQueue1(int initCapacity, Comparator<? super E> comparator) {
        this.queue = new Object[initCapacity];
        this.comparator = comparator;
    }

    public PriorityQueue1(Collection<? extends E> c) {
        this.queue = new Object[c.size()];
        addAll(c);
    }

    public Comparator<? super E> comparator() {
        return comparator;
    }

    public void setComparator(Comparator<? super E> comparator) {
        if (!isEmpty())
            throw new IllegalStateException("queue must be empty");
        this.comparator = comparator;
    }

    E get(int index) {
      Objects.checkIndex(index, size());
      return (E) queue[index];
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty())
            return false;
        ensureCapacity(size() + c.size());
        for (E e : c)
            add(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        Objects.requireNonNull(e);
        ensureCapacity();
        int index = getPlaceFor(e);
        if (index < size() && !isEmpty()) {
            Object[] queuePrior = subArray(queue, index, size() - index);
            System.arraycopy(queuePrior, 0, queue, index + 1, queuePrior.length);
        }
        queue[index] = e;
        size++;
        return true;
    }

    private int getPlaceFor(E e) {
        if (isEmpty())
            return 0;
        for (int i = 0; i < size(); i++) {
            if (comparator == null) {
                if (((Comparable<E>) get(i)).compareTo(e) > 0)
                    return i;
            } else {
                if (comparator.compare(get(i), e) > 0)
                    return i;
            }
        }
        return size();
    }

    @Override
    public E poll() {
        if (isEmpty())
            return null;
        return remove();
    }

    @Override
    public E peek() {
        if (isEmpty())
            return null;
        return get(0);
    }

    @Override
    public E element() {
        if (isEmpty())
            throw new NoSuchElementException();
        return get(0);
    }

    @Override
    public E remove() {
        if (isEmpty())
            throw new NoSuchElementException();
        E value = get(0);
        queue = subArray(queue, 1, --size);
        return value;
    }

    @Override
    public void clear() {
        queue = new Object[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    int capacity() {
        return queue.length;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty() || isEmpty())
            return false;
        for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    public int indexOf(Object o) {
        return indexOfRange(o, 0, size());
    }

    private int indexOfRange(Object o, int start, int end) {
        Objects.requireNonNull(o);
        for (int i = start; i < end; i++)
            if (get(i).equals(o))
                return i;
        return -1;
    }

    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size());
    }

    private int lastIndexOfRange(Object o, int start, int end) {
        Objects.requireNonNull(o);
        for (int i = end-1; i >= start; i--)
            if (get(i).equals(o))
                return i;
        return -1;
    }

    private void ensureCapacity() {
        if (size() == capacity())
            grow();
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > capacity())
            grow();
    }

    private void grow() {
        int newCapacity = (int) (capacity()*1.5);
        grow(newCapacity);
    }

    private void grow(int newCapacity) {
        if (newCapacity <= capacity())
            throw new IllegalArgumentException();
        changeCapacity(newCapacity);
    }

    private void changeCapacity(int newCapacity) {
        queue = Arrays.copyOf(queue, newCapacity);
    }

    private Object[] subArray(Object[] array, int start, int length) {
        if (start == 0 && length == array.length)
            return array.clone();
        Object[] subArray = new Object[length];
        System.arraycopy(array, start, subArray, 0, length);
        return subArray;
    }

    public void forEach(Consumer<? super E> action) {
        for (E e : this)
            action.accept(e);
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    public final class Itr implements Iterator<E> {

        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return (E) queue[cursor++];
        }
    }
}
