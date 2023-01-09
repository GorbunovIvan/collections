package org.example.list;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

public class ArrayList1<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elementData;
    private int size;

    public ArrayList1() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList1(int initCapacity) {
        elementData = new Object[initCapacity];
    }

    public ArrayList1(Collection<? extends E> c) {
        elementData = new Object[c.size()];
        addAll(c);
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elementData[index];
    }

    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    public boolean add(E element) {
        ensureCapacity();
        elementData[size++] = element;
        return true;
    }

    public void add(int index, E element) {
        Objects.checkIndex(index, size+1);
        if (index == size) {
            add(element);
            return;
        }
        Object[] elementsRight = getRightElements(index);
        ensureCapacity();
        elementData[index] = element;
        System.arraycopy(elementsRight, 0, elementData, index+1, elementsRight.length);
        size++;
    }

    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty())
            return false;
        for (E e : c)
            add(e);
        return true;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty())
            return false;
        Object[] elementsRight = getRightElements(index);
        ensureCapacity(size() + c.size());
        int currentIndex = index;
        for (E e : c)
            elementData[currentIndex++] = e;
        for (int i = 0; i < elementsRight.length; i++)
            elementData[currentIndex++] = elementsRight[i];
        size += c.size();
        return true;
    }

    public E remove(int index) {
        Objects.checkIndex(index, size());
        E value = (E) get(index);
        for (int i = index; i < size()-1; i++)
            elementData[i] = elementData[i+1];
        elementData[--size] = null;
        compressCapacity();
        return value;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1)
            return false;
        remove(index);
        return true;
    }

    public boolean removeAll(Object o) {
        int index = indexOf(o);
        if (index == -1)
            return false;
        while (index != -1) {
            remove(index);
            index = indexOfRange(o, index, size());
        }
        return true;
    }

    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty() || isEmpty())
            return false;
        boolean result = false;
        for (Object o : c)
            result = removeAll(o) || result;
        return result;
    }

    public boolean retainAll(Collection<?> c) {
        if (isEmpty())
            return false;
        if (c.isEmpty()) {
            clear();
            return true;
        }
        boolean result = false;
        int index = 0;
        while (index < size()) {
            E value = get(index);
            if (!c.contains(value)) {
                remove(index);
                result = true;
            } else
                index++;
        }
        return result;
    }

    public void clear() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int indexOf(Object o) {
        return indexOfRange(o, 0, size());
    }

    public int indexOfRange(Object o, int start, int end) {
        if (o == null) {
            for (int i = start; i < end; i++)
                if (get(i) == null)
                    return i;
        } else {
            for (int i = start; i < end; i++)
                if (get(i).equals(o))
                    return i;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size());
    }

    public int lastIndexOfRange(Object o, int start, int end) {
        if (o == null) {
            for (int i = end-1; i >= start; i--)
                if (get(i) == null)
                    return i;
        } else {
            for (int i = end-1; i >= start; i--)
                if (get(i).equals(o))
                    return i;
        }
        return -1;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public boolean containsAll(Collection<?> c) {
        for (Iterator<?> it = c.iterator(); it.hasNext();)
            if (!contains(it.next()))
                return false;
        return true;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        for (E e : this)
            action.accept(e);
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return elementData.length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private void ensureCapacity() {
        if (size == capacity())
            grow();
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > capacity())
            grow(minCapacity);
    }

    private void grow() {
        int newCapacity = (int) Math.max(DEFAULT_CAPACITY, size()*1.5);
        grow(newCapacity);
    }

    private void grow(int newCapacity) {
        if (newCapacity <= capacity())
            return;
        Object[] newElementData = new Object[newCapacity];
        System.arraycopy(elementData, 0, newElementData, 0, elementData.length);
        elementData = newElementData;
    }

    public void trimToSize() {
        if (capacity() == size())
            return;
        Object[] newElementData = new Object[Math.max(10, size())];
        System.arraycopy(elementData, 0, newElementData, 0, size());
        elementData = newElementData;
    }

    public void compressCapacity() {
        if (capacity() < size()*2 || capacity() == DEFAULT_CAPACITY)
            return;
        int newCapacity = (int) Math.max(DEFAULT_CAPACITY, size()*1.5);
        Object[] newElementData = new Object[newCapacity];
        System.arraycopy(elementData, 0, newElementData, 0, newCapacity);
        elementData = newElementData;
    }

    @Override
    public E[] toArray() {
        return (E[]) Arrays.copyOf(elementData.clone(), size());
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return super.toArray(generator);
    }

    private Object[] getRightElements(int index) {
        return Arrays.copyOfRange(elementData, index, size);
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

    public class Itr implements Iterator<E> {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return get(cursor++);
        }

        @Override
        public void remove() {
            if (cursor == 0)
                throw new IllegalStateException();
            ArrayList1.this.remove(--cursor);
        }
    }

    public ListIterator<E> listIterator() {
        return new ListItr();
    }

    public class ListItr implements ListIterator<E> {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return get(cursor++);
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            return get(--cursor);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor-1;
        }

        @Override
        public void remove() {
            if (cursor == 0)
                throw new IllegalStateException();
            ArrayList1.this.remove(--cursor);
        }

        @Override
        public void set(E e) {
            if (hasNext())
                throw new IllegalStateException();
            ArrayList1.this.set(cursor, e);
        }

        @Override
        public void add(E e) {
            if (hasNext())
                throw new IllegalStateException();
            ArrayList1.this.add(cursor++, e);
        }
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        for (int i = 0; i < size(); i++)
            set(i, operator.apply(get(i)));
    }

    @Override
    public void sort(Comparator<? super E> c) {
        Arrays.sort(elementData, (Comparator) c);
    }

    @Override
    public ArrayList1<E> clone() {
        try {
            return (ArrayList1<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof List))
            return false;
        List<?> list = (List<?>) o;
        if (size() != list.size())
            return false;
        Iterator<E> iter = iterator();
        Iterator<E> iterList = (Iterator<E>) list.iterator();
        while (iter.hasNext() && iterList.hasNext()) {
            Object value = iter.next();
            Object valueList = iterList.next();
            if (valueList == null) {
                if (value != null)
                    return false;
            } else if (!valueList.equals(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Iterator<E> it = iterator(); it.hasNext();) {
            sb.append(it.next());
            if (it.hasNext())
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
