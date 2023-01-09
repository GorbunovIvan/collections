package org.example.set;

import org.example.map.HashMap1;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HashSet1<E>
        extends AbstractSet<E>
        implements Set<E>
{

    private final Map<E,Object> map;

    private static final Object PRESENT = new Object();

    public HashSet1() {
        map = new HashMap1<>();
    }

    public HashSet1(Collection<? extends E> c) {
        map = new HashMap1<>(Math.max((int)(c.size()*0.75) + 1, 16));
        addAll(c);
    }

    public HashSet1(int initialCapacity, float loadFactor) {
        map = new HashMap1<>(initialCapacity, loadFactor);
    }

    public HashSet1(int initialCapacity) {
        map = new HashMap1<>(initialCapacity);
    }

    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    public void clear() {
        map.clear();
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Object[] toArray() {
        return map.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Object[] arrayFromSet = toArray();
        if (a.length < arrayFromSet.length)
            a = (T[]) new Object[arrayFromSet.length];
        int index = 0;
        for (Object o : arrayFromSet)
            a[index++] = (T) o;
        return a;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        boolean result = false;
        for (E e : this) {
            if (filter.test(e)) {
                remove(e);
                result = true;
            }
        }
        return result;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        for (E e : this)
            action.accept(e);
    }

    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }
}
