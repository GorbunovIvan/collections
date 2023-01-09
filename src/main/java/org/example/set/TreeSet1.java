package org.example.set;

import org.example.map.TreeMap1;

import java.util.*;

public class TreeSet1<E> extends AbstractSet<E> {

    private final Map<E,Object> m;

    private static final Object PRESENT = new Object();

    public TreeSet1() {
        this.m = new TreeMap1<>();
    }

    public TreeSet1(Map<E,Object> m) {
        this.m = new TreeMap1<>(m);
    }

    public TreeSet1(Comparator<? super E> comparator) {
        this(new TreeMap1<>(comparator));
    }

    public TreeSet1(Collection<? extends E> c) {
        this(new TreeMap1<>());
        addAll(c);
    }

    public TreeSet1(SortedSet<E> s) {
        this(s.comparator());
        addAll(s);
    }

    public boolean add(E e) {
        return m.put(e, PRESENT) == null;
    }

    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E e : c)
            if (add(e))
                result = true;
        return result;
    }

    public boolean remove(Object o) {
        return m.remove(o) != null;
    }

    public boolean contains(Object o) {
        return m.containsKey(o);
    }

    public int size() {
        return m.size();
    }

    public boolean isEmpty() {
        return m.isEmpty();
    }

    public void clear() {
        m.clear();
    }

    public Iterator<E> iterator() {
        return m.keySet().iterator();
    }

    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    // linear speed (bad-bad)
    final class DescendingIterator implements Iterator<E> {
        E next;
        int cursor = size();
        public DescendingIterator() {
            setNext();
        }
        @Override
        public boolean hasNext() {
            return next != null;
        }
        @Override
        public E next() {
            E current = next;
            setNext();
            return current;
        }
        private void setNext() {
            if (cursor == 0) {
                next = null;
            } else {
                Iterator<E> iter = iterator();
                for (int i = 0; i < cursor-1; i++)
                    iter.next();
                next = iter.next();
                cursor--;
            }
        }
    }
}
