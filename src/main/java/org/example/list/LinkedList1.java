package org.example.list;

import java.util.*;

public class LinkedList1<E>
        extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, Cloneable
{
    private int size;
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    public LinkedList1() {
    }

    public LinkedList1(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    private void linkFirst(E e) {
        if (isEmpty()) {
            first = new Node<E>(null, e, null);
            last = first;
            size++;
            return;
        }
        Node<E> oldFirst = first;
        first = new Node<E>(null, e, oldFirst);
        oldFirst.prev = first;
        size++;
    }

    private void linkLast(E e) {
        if (isEmpty()) {
            last = new Node<E>(null, e, null);
            first = last;
            size++;
            return;
        }
        Node<E> oldLast = last;
        last = new Node<E>(oldLast, e, null);
        oldLast.next = last;
        size++;
    }

    private E unlinkFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        E item = first.item;
        if (size() == 1) {
            clear();
            return item;
        }
        first = first.next;
        first.prev = null;
        size--;
        return item;
    }

    private E unlinkLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        E item = last.item;
        if (size() == 1) {
            clear();
            return item;
        }
        last = last.prev;
        last.next = null;
        size--;
        return item;
    }

    private void unlink(Node<E> node) {
        if (isEmpty())
            throw new NoSuchElementException();
        if (node == first) {
            unlinkFirst();
            return;
        }
        if (node == last) {
            unlinkLast();
            return;
        }
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        prev.next = next;
        next.prev = prev;
        size--;
    }

    private void linkBefore(E e, Node<E> node) {
        if (isEmpty())
            throw new NoSuchElementException();
        if (node == first) {
            linkFirst(e);
            return;
        }
        Node<E> previous = node.prev;
        Node<E> newNode = new Node<>(previous, e, node);
        previous.next = newNode;
        node.prev = newNode;
        size++;
    }

    private void linkAfter(E e, Node<E> node) {
        if (isEmpty())
            throw new NoSuchElementException();
        if (node == last) {
            linkLast(e);
            return;
        }
        Node<E> next = node.next;
        Node<E> newNode = new Node<>(node, e, next);
        next.prev = newNode;
        node.next = newNode;
        size++;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void add(int index, E e) {
        Objects.checkIndex(index, size());
        if (index == 0) {
            linkBefore(e, first);
            return;
        }
        Node<E> currentNode = getNode(index);
        linkBefore(e, currentNode);
    }

    void add(Node<E> node, E e) {
        linkBefore(e, node);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty())
            return false;
        for (E e : c)
            add(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Objects.checkIndex(index, size());
        if (c.isEmpty())
            return false;
        Node<E> currentNode = getNode(index);
        for (E e : c)
            add(currentNode, e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        linkFirst(e);
    }

    @Override
    public void addLast(E e) {
        linkLast(e);
    }

    E set(Node<E> node, E e) {
        E oldValue = node.item;
        node.item = e;
        return oldValue;
    }

    public E set(int index, E e) {
        Node<E> node = getNode(index);
        E oldValue = node.item;
        node.item = e;
        return oldValue;
    }

    public E get(int index) {
        Objects.checkIndex(index, size());
        if (index >= size()/2) {
            int i = size()-1;
            for (Iterator<E> itr = descendingIterator(); itr.hasNext();)
                if (i-- == index)
                    return itr.next();
        } else {
            int i = 0;
            for (E e : this)
                if (i++ == index)
                    return e;
        }
        throw new IllegalStateException();
    }

    Node<E> getNode(int index) {
        Objects.checkIndex(index, size());
        if (index >= size()/2) {
            int i = size()-1;
            for (Node<E> node = last; node != null; node = node.prev)
                if (i-- == index)
                    return node;
        } else {
            int i = 0;
            for (Node<E> node = first; node != null; node = node.next)
                if (i++ == index)
                    return node;
        }
        throw new IllegalStateException();
    }

    Node<E> getNode(Object o) {
        return getNode(o, false);
    }

    Node<E> getNode(Object o, boolean lastOccurrence) {
        if (isEmpty())
            return null;
        if (lastOccurrence) {
            for (Node<E> node = last; node != null; node = node.prev) {
                if (o == null) {
                    if (node.item == null)
                        return node;
                } else {
                    if (o.equals(node.item))
                        return node;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o == null) {
                    if (node.item == null)
                        return node;
                } else {
                    if (o.equals(node.item))
                        return node;
                }
            }
        }
        return null;
    }

    @Override
    public E getFirst() {
        if (first == null)
            throw new NoSuchElementException();
        return (E) first.item;
    }

    @Override
    public E getLast() {
        if (last == null)
            throw new NoSuchElementException();
        return (E) last.item;
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (isEmpty())
            return null;
        return unlinkFirst();
    }

    @Override
    public E pollLast() {
        if (isEmpty())
            return null;
        return unlinkLast();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public E peekFirst() {
        if (first == null)
            return null;
        return (E) first.item;
    }

    @Override
    public E peekLast() {
        if (last == null)
            return null;
        return (E) last.item;
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E remove(int index) {
        Objects.checkIndex(index, size());
        Node<E> node = getNode(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof LinkedList1.Node) {
            unlink((Node) o);
            return true;
        }
        Node<E> node = getNode(o);
        if (node == null)
            return false;
        unlink(node);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (isEmpty() || c.isEmpty())
            return false;
        boolean result = false;
        for (Object e : c) {
            Node<E> node = getNode(e);
            while (node != null) {
                remove(node);
                result = true;
                node = getNode(e);
            }
        }
        return result;
    }

    @Override
    public E removeFirst() {
        return unlinkFirst();
    }

    @Override
    public E removeLast() {
        return unlinkLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        Node<E> node = getNode(o);
        if (node == null)
            return false;
        unlink(node);
        return true;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<E> node = getNode(o, true);
        if (node == null)
            return false;
        unlink(node);
        return true;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size());
    }

    public int indexOfRange(Object o, int start, int end) {
        int index = 0;
        if (o == null) {
            for (E e : this) {
                if (index >= end)
                    break;
                if (index >= start)
                    if (e == null)
                        return index;
                index++;
            }
        } else {
            for (E e : this) {
                if (index >= end)
                    break;
                if (index >= start)
                    if (o.equals(e))
                        return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size());
    }

    public int lastIndexOfRange(Object o, int start, int end) {
        int index = end-1;
        if (o == null) {
            for (Iterator<E> itr = descendingIterator(); itr.hasNext();) {
                if (index < start)
                    break;
                if (itr.next() == null)
                    return index;
                index--;
            }
        } else {
            for (Iterator<E> itr = descendingIterator(); itr.hasNext();) {
                if (index < start)
                    break;
                if (o.equals(itr.next()))
                    return index;
                index--;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public E[] toArray() {
        Object[] array = new Object[size()];
        int index = 0;
        for (E e : this)
            array[index++] = e;
        return (E[]) array;
    }

    @Override
    public void sort(Comparator<? super E> comparator) {
        E[] array = toArray();
        Arrays.sort(array, comparator);
        clear();
        addAll(Arrays.asList(array));
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr();
    }

    public class DescendingIterator implements Iterator<E> {

        ListItr listItr = new ListItr(last);

        @Override
        public boolean hasNext() {
            return listItr.hasPrevious();
        }

        @Override
        public E next() {
            return listItr.previous();
        }

        @Override
        public void remove() {
            listItr.remove();
        }
    }

    public class ListItr implements ListIterator<E> {

        Node<E> currentNode = first;
        Node<E> lastReturnedNode;
        int cursor;

        ListItr() {}

        ListItr(Node<E> currentNode) {
            this.currentNode = currentNode;
            if (currentNode == last)
                cursor = size();
            else {
                cursor = indexOf(currentNode) + 1;
            }
        }

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            lastReturnedNode = currentNode;
            if (currentNode != last)
                currentNode = currentNode.next;
            cursor++;
            return lastReturnedNode.item;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new IllegalStateException();
            lastReturnedNode = currentNode;
            if (currentNode != first)
                currentNode = currentNode.prev;
            cursor--;
            return lastReturnedNode.item;
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
            if (lastReturnedNode == null)
                throw new IllegalStateException();
            unlink(lastReturnedNode);
            if (lastReturnedNode.prev != currentNode)
                cursor--;
            lastReturnedNode = null;
        }

        @Override
        public void set(E e) {
            if (lastReturnedNode == null)
                throw new IllegalStateException();
            LinkedList1.this.set(lastReturnedNode, e);
        }

        @Override
        public void add(E e) {
            LinkedList1.this.add(currentNode, e);
        }
    }
}
