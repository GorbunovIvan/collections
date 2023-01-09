package org.example.map;

import java.util.*;
import java.util.function.*;

public class HashMap1<K,V> extends AbstractMap<K,V>
        implements Map<K,V> {

    private int size;
    private int sizeOfTable;
    private final float loadFactor;
    private int threshold;
    AbstractNode<K,V>[] table;

    Set<K> keySet;
    Set<Map.Entry<K,V>> entrySet;
    Collection<V> values;

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int TREEIFY_THRESHOLD = 8;
    static final int UNTREEIFY_THRESHOLD = 6;
    static final int MIN_TREEIFY_CAPACITY = 64;

    public HashMap1() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap1(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap1(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (loadFactor <= 0)
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        int capacity = tableSizeFor(initialCapacity);
        table = new AbstractNode[capacity];
        this.threshold = (int) (capacity * loadFactor);
        this.loadFactor = loadFactor;
    }

    public HashMap1(Map<? extends K, ? extends V> m) {
        this();
        putAll(m);
    }

    @Override
    public V get(Object key) {
        AbstractNode<K,V> node = getNode(key);
        if (node == null)
            return null;
        return node.getValue();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        AbstractNode<K,V> node = getNode((K) key);
        if (node != null)
            if (node.getValue() != null)
                return node.getValue();
        return defaultValue;
    }

    @Override
    public V put(K key, V value) {
        AbstractNode<K,V> node = getNodeOrCreate(key);
        V oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    @Override
    public V putIfAbsent(K key, V value) {
        AbstractNode<K,V> node = getNodeOrCreate(key);
        V oldValue = node.getValue();
        if (oldValue == null)
            node.setValue(value);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        int index = placeForNode(Node.hashForKey(key));
        AbstractNode<K,V> firstNode = table[index];
        if (firstNode == null)
            return null;
        AbstractNode<K,V> prevNode = null;
        Iterator<AbstractNode<K,V>> iter = firstNode.iterator();
        while (iter.hasNext()) {
            AbstractNode<K,V> node = iter.next();
            if (Objects.equals(key, node.getKey())) {
                V oldValue = node.getValue();
                AbstractNode<K,V> nextNode = null;
                if (iter.hasNext())
                    nextNode = iter.next();
                if (prevNode != null) {
                    if (prevNode instanceof Node<K,V>)
                        prevNode.removeNext(node);
                    else
                        node.remove();
                } else {
                    table[index] = node.remove();
                    if (table[index] == null)
                        sizeOfTable--;
                }
                size--;
                return oldValue;
            }
            prevNode = node;
        }
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        AbstractNode<K,V> node = getNode(key);
        if (node == null)
            return false;
        if (!Objects.equals(oldValue, node.getValue()))
            return false;
        node.setValue(newValue);
        return true;
    }

    @Override
    public V replace(K key, V value) {
        AbstractNode<K,V> node = getNode(key);
        if (node == null)
            return null;
        V oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        for (Map.Entry<K,V> entry : entrySet()) {
            V newValue = function.apply(entry.getKey(), entry.getValue());
            entry.setValue(newValue);
        }
    }

    @Override
    public void clear() {
        clearWithResizing(DEFAULT_INITIAL_CAPACITY);
    }

    private void clearWithResizing(int newCapacity) {
        table = new AbstractNode[newCapacity];
        size = 0;
        sizeOfTable = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return getNodeByValue(value) != null;
    }

    public Map.Entry<K,V> getNodeByValue(Object value) {
        for (Map.Entry<K,V> entry : entrySet())
            if (Objects.equals(entry.getValue(), value))
                return entry;
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private int capacity() {
        return table.length;
    }

    private AbstractNode<K,V> getNode(Object key) {
        int index = placeForNode(Node.hashForKey(key));
        AbstractNode<K,V> node = table[index];
        if (node == null)
            return null;
        for (AbstractNode<K,V> currentNode : node)
            if (Objects.equals(key, currentNode.getKey()))
                return currentNode;
        return null;
    }

    private AbstractNode<K,V> getNodeOrCreate(K key) {
        ensureCapacity();
        int hash = Node.hashForKey(key);
        int index = placeForNode(hash);
        AbstractNode<K,V> node = table[index];
        if (node == null) {
            node = new Node<>((K) key, null);
//            node = new TreeNode<>((K) key, null);
            table[index] = (AbstractNode<K,V>) node;
            size++;
            sizeOfTable++;
            return node;
        }
        if (Objects.equals(key, node.getKey()))
            return node;
        for (AbstractNode<K,V> currentNode : node)
            if (Objects.equals(key, currentNode.getKey()))
                return currentNode;
        size++;
        AbstractNode<K,V> newNode = null;
        if (node instanceof Node<K,V>) {
            newNode = new Node<>((K) key, null);
        } else if (node instanceof TreeNode) {
            newNode = new TreeNode<>((K) key, null);
        }
        node.add(newNode);
        return newNode;
    }

    private void ensureCapacity() {
        if (size < threshold)
            return;
        if (size > threshold)
            throw new IllegalStateException("size (" + size + ") > threshold (" + threshold + ")");
        int newCapacity = table.length * 2;
        threshold = (int) (newCapacity * loadFactor);
        resize(newCapacity);
    }

    private void resize(int newCapacity) {
        AbstractNode<K,V>[] oldTable = table;
        clearWithResizing(newCapacity);
        for (AbstractNode<K,V> node : oldTable)
            if (node != null)
                for (AbstractNode<K,V> currentNode : node)
                    put(currentNode.getKey(), currentNode.getValue());
    }

    private int tableSizeFor(int capacity) {
        for (int powerOf2 = 0; powerOf2 <= 30; powerOf2++)
            if (2 << powerOf2 >= capacity)
                return 2 << powerOf2;
        return 2 << 30;
    }

    private int placeForNode(int hash) {
        return hash & (capacity()-1);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V computedValue = mappingFunction.apply(key);
        AbstractNode<K,V> node = getNode(key);
        if (node == null) {
            put(key, computedValue);
            return computedValue;
        }
        if (node.getValue() != null)
            return node.getValue();
        node.setValue(computedValue);
        return computedValue;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        AbstractNode<K,V> node = getNode(key);
        if (node == null || node.getValue() == null)
            return null;
        V computedValue = remappingFunction.apply(node.getKey(), node.getValue());
        if (computedValue != null)
            node.setValue(computedValue);
        else
            remove(key);
        return computedValue;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        AbstractNode<K,V> node = getNodeOrCreate(key);
        V computedValue = remappingFunction.apply(node.getKey(), node.getValue());
        if (computedValue != null)
            node.setValue(computedValue);
        else
            remove(key);
        return computedValue;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        AbstractNode<K,V> node = getNode(key);
        if (node == null) {
            put(key, value);
            return value;
        }
        V oldValue = node.getValue();
        V computedValue;
        if (oldValue == null)
            computedValue = value;
        else
            computedValue = remappingFunction.apply(oldValue, value);
        if (computedValue != null)
            node.setValue(computedValue);
        else
            remove(key);
        return computedValue;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (Map.Entry<K,V> entry : entrySet())
            action.accept(entry.getKey(), entry.getValue());
    }

    @Override
    public Set<K> keySet() {
        if (keySet == null)
            keySet = new KeySet();
        return keySet;
    }

    @Override
    public Set<Map.Entry<K,V>> entrySet() {
        if (entrySet == null)
            entrySet = new EntrySet();
        return entrySet;
    }

    @Override
    public Collection<V> values() {
        if (values == null)
            values = new Values();
        return values;
    }

    interface AbstractNode<K,V> extends Map.Entry<K,V>, Iterable<AbstractNode<K,V>> {
        void add(AbstractNode<K,V> node);
        AbstractNode<K,V> remove();
        AbstractNode<K,V> removeNext(AbstractNode<K,V> node);
        Iterator<AbstractNode<K, V>> iterator(boolean intermediate);
    }

    final static class Node<K,V> implements AbstractNode<K,V> {
        K key;
        V value;
        int hash;
        Node<K,V> next;
        public Node(K key, V value) {
            this(key, value, null);
        }
        public Node(K key, V value, Node<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = hashForKey(this);
        }
        @Override
        public K getKey() {
            return key;
        }
        @Override
        public V getValue() {
            return value;
        }
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
        @Override
        public void add(AbstractNode<K,V> node) {
            Node<K,V> lastNode = this;
            while (lastNode.next != null)
                lastNode = lastNode.next;
            lastNode.next = (Node<K,V>) node;
        }
        public AbstractNode<K,V> remove() {
            return next;
        }
        @Override
        public AbstractNode<K,V> removeNext(AbstractNode<K,V> node) {
            if (!(node instanceof Node<K,V> nodeToRemove) || next != nodeToRemove)
                throw new IllegalArgumentException();
            this.next = nodeToRemove.next;
            return this.next;
        }
        @Override
        public Iterator<AbstractNode<K,V>> iterator() {
            return iterator(false);
        }
        @Override
        public Iterator<AbstractNode<K,V>> iterator(boolean intermediate) {
            return new NodeIterator<>(this);
        }
        @Override
        public String toString() {
            return key + "=" + value;
        }
        static <K,V> int hashForKey(Map.Entry<K,V> entry) {
            if (entry == null)
                return 0;
            return hashForKey(entry.getKey());
        }
        static <K> int hashForKey(K key) {
            if (key == null)
                return 0;
            return key.hashCode();
        }
    }

    final static class TreeNode<K,V>
            implements AbstractNode<K,V>
    {
        K key;
        V value;
        int hash;
        TreeNode<K,V> parent;
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        public TreeNode(K key, V value) {
            this(key, value, null, null, null);
        }
        public TreeNode(K key, V value, TreeNode<K,V> parent, TreeNode<K,V> left, TreeNode<K,V> right) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.hash = Node.hashForKey(this);
        }
        @Override
        public K getKey() {
            return key;
        }
        @Override
        public V getValue() {
            return value;
        }
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
        public TreeNode<K,V> getParent() {
            return parent;
        }
        public void setParent(TreeNode<K,V> parent) {
            this.parent = parent;
            if (parent != null) {
                if (parent.compareTo(this) >= 0)
                    parent.left = this;
                else
                    parent.right = this;
            }
        }
        public TreeNode<K,V> getLeft() {
            return left;
        }
        public void setLeft(TreeNode<K,V> left) {
            if (this.left != null) {
                this.left.add(left);
            } else {
                this.left = left;
                left.parent = this;
            }
        }
        public TreeNode<K,V> getRight() {
            return right;
        }
        public void setRight(TreeNode<K,V> right) {
            if (this.right != null) {
                this.right.add(right);
            } else {
                this.right = right;
                right.parent = this;
            }
        }
        @Override
        public void add(AbstractNode<K,V> treeNode) {
            Objects.requireNonNull(treeNode);
            TreeNode<K,V> newNode = (TreeNode<K,V>) treeNode;
            if (compareTo(newNode) >= 0)
                setLeft(newNode);
            else
                setRight(newNode);
        }
        public AbstractNode<K,V> remove() {
            if (parent == null) {
                if (left == null && right == null) {
                    return null;
                } else if (left != null && right != null) {
                    right.add(left);
                    right.parent = null;
                    return right;
                } else if (left == null) {
                    right.parent = null;
                    return right;
                } else {
                    left.parent = null;
                    return left;
                }
            } else {
                return parent.removeNext(this);
            }
        }
        @Override
        public AbstractNode<K,V> removeNext(AbstractNode<K,V> node) {
            if (!(node instanceof TreeNode<K,V> nodeToRemove))
                throw new IllegalArgumentException();
            if (nodeToRemove == this.left) {
                if (nodeToRemove.right != null) {
                    this.left = nodeToRemove.right;
                    this.left.parent = this;
                    if (nodeToRemove.left != null)
                        this.left.add(nodeToRemove.left);
                } else {
                    this.left = nodeToRemove.left;
                    this.left.parent = this;
                }
                return this.left;
            } else if (nodeToRemove == this.right) {
                if (nodeToRemove.right != null) {
                    this.right = nodeToRemove.right;
                    this.right.parent = this;
                    if (nodeToRemove.left != null)
                        this.right.add(nodeToRemove.left);
                } else {
                    this.right = nodeToRemove.left;
                    this.right.parent = this;
                }
                return this.right;
            } else {
                throw new IllegalArgumentException();
            }
        }
        public TreeNode<K,V> findNode(K key) {
            int comparing = compareTo(key);
            if (comparing == 0)
                return this;
            return comparing > 0 ? left : right;
        }
        public boolean isLeft() {
            if (parent == null)
                throw new IllegalStateException();
            return parent.left == this;
        }
        @Override
        public Iterator<AbstractNode<K,V>> iterator() {
            return iterator(false);
        }
        @Override
        public Iterator<AbstractNode<K,V>> iterator(boolean intermediate) {
            return new TreeNodeIterator<>(this, intermediate);
        }
        public int compareTo(TreeNode<K,V> o) {
//            return getKey().compareTo((K) o.getKey());
            return compareTo(o.getKey());
        }
        public int compareTo(K objKey) {
            if (getKey() == null)
                return -1;
            if (objKey == null)
                return 1;
            if (getKey().equals(objKey))
                return 0;
            return hash - Node.hashForKey(objKey);
        }
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    final static class NodeIterator<K,V> implements Iterator<AbstractNode<K,V>> {
        Node<K,V> lastReturned;
        Node<K,V> next;
        public NodeIterator(Node<K,V> node) {
            this.next = node;
        }
        @Override
        public boolean hasNext() {
            return next != null;
        }
        @Override
        public Node<K,V> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            lastReturned = next;
            next = lastReturned.next;
            return lastReturned;
        }
    }

    final static class TreeNodeIterator<K,V> implements Iterator<AbstractNode<K,V>> {
        TreeNode<K,V> next;
        boolean singleNodeInTree;
        public TreeNodeIterator(TreeNode<K,V> root, boolean intermediate) {
            this.next = root;
            this.singleNodeInTree = root.left == null && root.right == null;
            if (!intermediate) {
                // going to the leftmost node
                if (this.next != null) {
                    if (this.next.left != null) {
                        while (this.next.left != null)
                            this.next = this.next.left;
                    } else if (this.next.right != null) {
                        this.next = this.next.right;
                        while (this.next.left != null)
                            this.next = this.next.left;
                    }
                }
            }
        }
        @Override
        public boolean hasNext() {
            return next != null;
        }
        @Override
        public TreeNode<K,V> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            TreeNode<K,V> current = next;
            if (next.right != null) {
                next = next.right;
                while (next.left != null)
                    next = next.left;
                return current;
            }
            // go one-step upside
            while (true) {
                // if this node is root, then finish
                if (next.parent == null) {
//                    current = next;
                    next = null;
                    return current;
                }
                // if this node was left for its parent then return it now
                if (next.parent.left == next) {
                    next = next.parent;
                    return current;
                }
                next = next.parent;
            }
        }
    }

    final class EntrySet extends AbstractSet<Map.Entry<K,V>> {
        @Override
        public Iterator<Map.Entry<K,V>> iterator() {
            return iterator(false);
        }
        public Iterator<Map.Entry<K,V>> iterator(boolean intermediate) {
            return new HashMap1.EntryIterator(intermediate);
        }
        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry<?, ?> entry))
                return false;
            if (!containsKey(entry.getKey()))
                return false;
            HashMap1.this.remove(entry.getKey());
            return true;
        }
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry<?, ?> entry))
                return false;
            return containsKey(entry.getKey());
        }
        private boolean containsKey(Object o) {
            return HashMap1.this.containsKey(o);
        }
        @Override
        public void forEach(Consumer<? super Map.Entry<K,V>> action) {
            for (Map.Entry<K,V> entry : this)
                action.accept(entry);
        }
        @Override
        public Object[] toArray() {
            Object[] entries = new Object[size()];
            int index = 0;
            for (Map.Entry<K,V> entry : this)
                entries[index++] = entry;
            return entries;
        }
        @Override
        public int size() {
            return HashMap1.this.size();
        }
        @Override
        public void clear() {
            HashMap1.this.clear();
        }
    }

    final class KeySet extends AbstractSet<K> {
        @Override
        public Iterator<K> iterator() {
            return new HashMap1.KeyIterator();
        }
        @Override
        public boolean remove(Object o) {
            if (!containsKey(o))
                return false;
            HashMap1.this.remove(o);
            return true;
        }
        @Override
        public boolean contains(Object o) {
            return HashMap1.this.containsKey(o);
        }
        @Override
        public void forEach(Consumer<? super K> action) {
            for (K key : this)
                action.accept(key);
        }
        @Override
        public Object[] toArray() {
            Object[] keys = new Object[size()];
            int index = 0;
            for (K key : this)
                keys[index++] = key;
            return keys;
        }
        @Override
        public int size() {
            return HashMap1.this.size();
        }
        @Override
        public void clear() {
            HashMap1.this.clear();
        }
    }

    final class Values extends AbstractCollection<V> {
        @Override
        public Iterator<V> iterator() {
            return new HashMap1.ValueIterator();
        }
        @Override
        public boolean contains(Object o) {
            return HashMap1.this.containsValue(o);
        }
        @Override
        public boolean remove(Object o) {
            Map.Entry<K,V> node = getNodeByValue(o);
            if (node == null)
                return false;
            HashMap1.this.remove(node.getKey(), node.getValue());
            return true;
        }
        @Override
        public void forEach(Consumer<? super V> action) {
            for (V value : this)
                action.accept(value);
        }
        @Override
        public Object[] toArray() {
            Object[] values = new Object[size()];
            int index = 0;
            for (V value : this)
                values[index++] = value;
            return values;
        }
        @Override
        public int size() {
            return HashMap1.this.size();
        }
        @Override
        public void clear() {
            HashMap1.this.clear();
        }
    }

    final class EntryIterator implements Iterator<Map.Entry<K,V>> {
        int cursorInTable = 0;
        AbstractNode<K,V> currentNode;
        EntryIterator(boolean intermediate) {
            if (capacity() != 0)
                setNextNotNull();
        }
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }
        @Override
        public Entry<K,V> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Map.Entry<K,V> node = currentNode;
            setNextNotNull();
            return node;
        }
        void setNextNotNull() {
            while (cursorInTable < capacity()) {
                if (currentNode != null) {
                    Iterator<AbstractNode<K,V>> iter = currentNode.iterator();
                    AbstractNode<K,V> firstNodeOfItsIterator = iter.next();
                    if (iter.hasNext())
                        currentNode = iter.next();
                    else
                        currentNode = null;
                }
                if (currentNode == null)
                    currentNode = table[cursorInTable++];
                if (currentNode != null)
                    return;
            }
            currentNode = null;
        }
    }

    final class KeyIterator implements Iterator<K> {
        Iterator<Map.Entry<K,V>> iterEntry = entrySet().iterator();
        @Override
        public boolean hasNext() {
            return iterEntry.hasNext();
        }
        @Override
        public K next() {
            return iterEntry.next().getKey();
        }
    }

    final class ValueIterator implements Iterator<V> {
        Iterator<Map.Entry<K,V>> iterEntry = entrySet().iterator();
        @Override
        public boolean hasNext() {
            return iterEntry.hasNext();
        }
        @Override
        public V next() {
            return iterEntry.next().getValue();
        }
    }
}
