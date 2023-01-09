package org.example.list;

import java.util.*;

public class Stack1<E> {

    private final LinkedList1<E> list;

    public Stack1() {
        list = new LinkedList1<>();
    }

    public Stack1(Collection<? extends E> c) {
        list = new LinkedList1<>(c);
    }

    public E push(E item) {
        list.add(item);
        return item;
    }

    public E pop() {
        return list.pollLast();
    }

    public E peek() {
        return list.peekLast();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int search(Object o) {
        int index = list.lastIndexOf(o);
        return index == -1 ? index : size()-index;
    }

    public void clear() {
        list.clear();
    }

    public Iterator<E> iterator() {
        return new Iter();
    }

    public class Iter implements Iterator<E> {
        ListIterator<E> listIter = list.listIterator();
        @Override
        public boolean hasNext() {
            return listIter.hasNext();
        }
        @Override
        public E next() {
            return listIter.next();
        }
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Iterator<E> iter = iterator(); iter.hasNext();) {
            E e = iter.next();
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Collection))
            return false;
        Collection<E> collection = (Collection<E>) o;
        if (list.size() != collection.size())
            return false;
        Iterator<E> iterList = iterator();
        Iterator<E> iterCollection = collection.iterator();
        while (iterList.hasNext() && iterCollection.hasNext()) {
            Object valueList = iterList.next();
            Object valueCollection = iterCollection.next();
            if (valueCollection == null) {
                if (valueList != null)
                    return false;
            } else if (!valueCollection.equals(valueList)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
