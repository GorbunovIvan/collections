package org.example.queue;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueue1<E> extends AbstractQueue<E>
        implements BlockingQueue<E> {

    private Object[] items;
    
    private int size;

    private final boolean fair;

    private int takeIndex;

    public ArrayBlockingQueue1(int capacity) {
        this(capacity, false);
    }

    public ArrayBlockingQueue1(int capacity, boolean fair) {
        this(capacity, fair, Collections.emptyList());
    }

    public ArrayBlockingQueue1(int capacity, boolean fair,
                              Collection<? extends E> c) {
        this.items = new Object[capacity];
        this.fair = fair;
        try {
            putAll(c);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void put(E e) throws InterruptedException {
        while (isFull())
            wait();
        putTail(e);
        notifyAll();
    }

    void putAll(Collection<? extends E> c) throws InterruptedException {
        for (E e : c)
            put(e);
    }

    @Override
    public synchronized boolean offer(E e) {
        if (isFull())
            return false;
        boolean result;
        synchronized (this) {
            result = putTail(e);
            notifyAll();
        }
        return result;
    }

    @Override
    public synchronized boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        if (isFull())
            wait(unit.toMillis(timeout));
        boolean result = putTail(e);
        notifyAll();
        return result;
    }

    private boolean putTail(E e) {
        Objects.requireNonNull(e);
        if (isFull())
            return false;
        items[size] = e;
        size++;
        return true;
    }

    @Override
    public synchronized E take() throws InterruptedException {
        while (isEmpty())
            wait();
        E item = removeHead();
        if (item == null)
            throw new IllegalStateException();
        notifyAll();
        return item;
    }

    @Override
    public E poll() {
        if (isEmpty())
            return null;
        E item;
        synchronized (this) {
            item = removeHead();
            notifyAll();
        }
        return item;
    }

    @Override
    public synchronized E poll(long timeout, TimeUnit unit) throws InterruptedException {
        if (isEmpty())
            wait(unit.toMillis(timeout));
        E item = removeHead();
        notifyAll();
        return item;
    }

    private E removeHead() {
        if (isEmpty())
            return null;
        E item = (E) items[0];
        Object[] newItems = new Object[capacity()];
        if (size() - 1 >= 0)
            System.arraycopy(items, 1, newItems, 0, size() - 1);
        items = newItems;
        size--;
        return item;
    }

    @Override
    public synchronized E peek() {
        if (isEmpty())
            return null;
        return (E) items[0];
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        Objects.requireNonNull(c);
        if (c == this)
            throw new IllegalArgumentException();
        if (isEmpty())
            return 0;
        int elementsTransferred;
        synchronized (this) {
            elementsTransferred = size();
            for (E e : this)
                c.add(e);
            clear();
            notifyAll();
        }
        return elementsTransferred;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        Objects.requireNonNull(c);
        if (c == this)
            throw new IllegalArgumentException();
        if (isEmpty())
            return 0;
        int elementsTransferred = 0;
        synchronized (this) {
            if (size() <= maxElements)
                return drainTo(c);
            while (elementsTransferred < maxElements) {
                try {
                    c.add(take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                elementsTransferred++;
            }
            notifyAll();
        }
        return elementsTransferred;
    }

    @Override
    public int remainingCapacity() {
        return capacity() - size();
    }

    @Override
    public int size() {
        return size;
    }

    int capacity() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean isFull() {
        return size() == capacity();
    }

    @Override
    public synchronized void clear() {
        items = new Object[capacity()];
        size = 0;
        notifyAll();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        System.arraycopy(items, 0, array, 0, size());
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor = 0;
        @Override
        public boolean hasNext() {
            return cursor < size();
        }
        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return (E) items[cursor++];
        }
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        if (fair) {
            for (E e : this)
                hashCode += 31 * hashCode + e.hashCode();
        } else {
            for (E e : this)
                hashCode += 31 * e.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BlockingQueue<?> that))
            return false;
        if (size != that.size())
            return false;
        if (fair) {
            Iterator<E> iter = iterator();
            Iterator<?> iterThat = that.iterator();
            while (iter.hasNext() && iterThat.hasNext()) {
                if (!iter.next().equals(iterThat.next()))
                    return false;
            }
            return iter.hasNext() == iterThat.hasNext();
        } else {
            return this.containsAll(that);
        }
    }
}
