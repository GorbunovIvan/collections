package org.example.map;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class TreeMap1<K,V>
        extends AbstractMap<K,V> {

    private final Comparator<? super K> comparator;
    private Entry<K,V> root;
    private int size;

    public TreeMap1() {
        this.comparator = null;
    }

    public TreeMap1(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    public TreeMap1(Map<? extends K, ? extends V> m) {
        this.comparator = null;
        putAll(m);
    }

    public V get(Object key) {
        Entry<K,V> entry = getEntry((K) key);
        return entry == null ? null : entry.getValue();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        Entry<K,V> entry = getEntry((K) key);
        if (entry != null)
            if (entry.getValue() != null)
                return entry.getValue();
        return defaultValue;
    }

    public V put(K key, V value) {
        Entry<K,V> entry = getEntryOrCreate(key);
        V oldValue = entry.getValue();
        entry.setValue(value);
        return oldValue;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        Entry<K,V> entry = getEntryOrCreate(key);
        V oldValue = entry.getValue();
        if (oldValue == null)
            entry.setValue(value);
        return oldValue;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    @Override
    public V remove(Object key) {
        Entry<K, V> entry = getEntry((K) key);
        if (entry == null)
            return null;
        removeEntry(entry);
        return entry.getValue();
    }

    @Override
    public boolean remove(Object key, Object value) {
        Entry<K, V> entry = getEntry((K) key);
        if (entry == null)
            return false;
        if (!Objects.equals(value, entry.getValue()))
            return false;
        removeEntry(entry);
        return true;
    }

    private void removeEntry(Entry<K,V> entry) {
        if (entry.parent == null) {
            if (entry.left == null && entry.right == null) {
                root = null;
            } else if (entry.left != null && entry.right != null) {
                root = entry.right;
                root.parent = null;
                int sizeBefore = size();
                putEntry(entry.left);
                this.size = sizeBefore;
            } else if (entry.left != null) {
                root = entry.left;
                root.parent = null;
            } else {
                root = entry.right;
                root.parent = null;
            }
        } else {
            if (entry.left == null && entry.right == null) {
                if (entry.parent.left == entry)
                    entry.parent.left = null;
                else
                    entry.parent.right = null;
            } else if (entry.left != null && entry.right != null) {
                if (entry.parent.left == entry)
                    entry.parent.left = entry.right;
                else
                    entry.parent.right = entry.right;
                entry.left.parent = null;
                int sizeBefore = size();
                putEntry(entry.left);
                this.size = sizeBefore;
            } else if (entry.left != null) {
                if (entry.parent.left == entry)
                    entry.parent.left = entry.left;
                else
                    entry.parent.right = entry.left;
                entry.left.parent = entry.parent;
            } else {
                if (entry.parent.left == entry)
                    entry.parent.left = entry.right;
                else
                    entry.parent.right = entry.right;
                entry.right.parent = entry.parent;
            }
        }
        size--;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Entry<K,V> entry = getEntry(key);
        if (entry == null)
            return false;
        if (!Objects.equals(oldValue, entry.getValue()))
            return false;
        entry.setValue(newValue);
        return true;
    }

    @Override
    public V replace(K key, V value) {
        Entry<K,V> entry = getEntry(key);
        if (entry == null)
            return null;
        V oldValue = entry.getValue();
        entry.setValue(value);
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
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (Map.Entry<K,V> entry : entrySet())
            action.accept(entry.getKey(), entry.getValue());
    }

    public boolean containsKey(Object key) {
        return getEntry((K) key) != null;
    }

    public boolean containsValue(Object value) {
        for (V v : values())
            if (Objects.equals(value, v))
                return true;
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public K firstKey() {
        if (root == null)
            return null;
        Entry<K,V> entry = root;
        while (entry.left != null)
            entry = entry.left;
        return entry.getKey();
    }

    public K lastKey() {
        if (root == null)
            return null;
        Entry<K,V> entry = root;
        while (entry.right != null)
            entry = entry.right;
        return entry.getKey();
    }

    public Comparator<? super K> comparator() {
        return comparator;
    }

    private Entry<K,V> getEntry(K key) {
        if (root == null)
            return null;
        if (comparator == null)
            Objects.requireNonNull(key);
        Entry<K,V> entry = root;
        while (entry != null) {
            int comparing;
            if (comparator != null)
                comparing = comparator.compare(key, entry.getKey());
            else
                comparing = ((Comparable<? super K>) key).compareTo(entry.getKey());
            if (comparing == 0)
                break;
            if (comparing > 0)
                entry = entry.right;
            else
                entry = entry.left;
        }
        return entry;
    }

    private Entry<K,V> getEntryOrCreate(K key) {
        if (comparator == null)
            Objects.requireNonNull(key);
        Entry<K,V> entry = root;
        Entry<K,V> previousEntry = null;
        while (entry != null) {
            int comparing;
            if (comparator != null)
                comparing = comparator.compare(key, entry.getKey());
            else
                comparing = ((Comparable<? super K>) key).compareTo(entry.getKey());
            if (comparing == 0)
                break;
            previousEntry = entry;
            if (comparing > 0)
                entry = entry.right;
            else
                entry = entry.left;
        }
        if (entry == null)
            entry = addEntry(key, null, previousEntry);
        return entry;
    }

    private void putEntry(Entry<K,V> entry) {
        Entry<K,V> entryAsNew = getEntryOrCreate(entry.getKey());
        if (entryAsNew.parent != null) {
            if (entryAsNew.parent.left == entryAsNew)
                entryAsNew.parent.left = entry;
            else if (entryAsNew.parent.right == entryAsNew)
                entryAsNew.parent.right = entry;
            entry.parent = entryAsNew.parent;
        } else {
            root = entry;
            entry.parent = null;
        }
    }

    private Entry<K,V> addEntry(K key, V value, Entry<K,V> parent) {
        Entry<K,V> entry = new Entry<>(key, value);
        entry.parent = parent;
        if (parent == null) {
            if (root != null)
                throw new IllegalStateException();
            root = entry;
        } else {
            int comparing;
            if (comparator != null)
                comparing = comparator.compare(parent.getKey(), key);
            else
                comparing = ((Comparable<? super K>) parent.getKey()).compareTo(key);
            if (comparing == 0)
                throw new IllegalStateException(); // we can not add existing key as a new one
            if (comparing > 0)
                parent.left = entry;
            else
                parent.right = entry;
        }
        size++;
        return entry;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public Set<K> keySet() {
        return new KeySet();
    }

    @Override
    public Collection<V> values() {
        return new Values();
    }

    final class EntrySet extends AbstractSet<Map.Entry<K,V>> {
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }
        @Override
        public void forEach(Consumer<? super Map.Entry<K, V>> action) {
            for (Map.Entry<K,V> entry : this)
                action.accept(entry);
        }
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry entry))
                return false;
            Entry<K,V> entryFound = getEntry((K) entry.getKey());
            if (entryFound == null)
                return false;
            return Objects.equals(entryFound.getValue(), entry.getValue());
        }
        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry entry))
                return false;
            int oldSize = size();
            removeEntry((Entry<K,V>) entry);
            return oldSize == size()+1;
        }
        @Override
        public int size() {
            return size;
        }
        @Override
        public void clear() {
            TreeMap1.this.clear();
        }
    }

    final class KeySet extends AbstractSet<K> {
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
        @Override
        public void forEach(Consumer<? super K> action) {
            for (K key : this)
                action.accept(key);
        }
        @Override
        public boolean contains(Object key) {
            return containsKey(key);
        }
        @Override
        public boolean remove(Object o) {
            return TreeMap1.this.remove((K) o) != null;
        }
        @Override
        public Object[] toArray() {
            Object[] array = new Object[size()];
            int index = 0;
            for (K key : this)
                array[index++] = key;
            return array;
        }
        @Override
        public int size() {
            return size;
        }
        @Override
        public void clear() {
            TreeMap1.this.clear();
        }
    }

    final class Values extends AbstractCollection<V> {
        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
        @Override
        public void forEach(Consumer<? super V> action) {
            for (V value : this)
                action.accept(value);
        }
        @Override
        public boolean contains(Object value) {
            return containsValue(value);
        }
        @Override
        public int size() {
            return size;
        }
        @Override
        public void clear() {
            TreeMap1.this.clear();
        }
    }

    final class EntryIterator implements Iterator<Map.Entry<K,V>> {
        Entry<K,V> next;
        public EntryIterator() {
            this.next = root;
            if (this.next != null)
                while (this.next.left != null)
                    this.next = this.next.left;
        }
        @Override
        public boolean hasNext() {
            return next != null;
        }
        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Entry<K,V> current = next;
            if (next.right != null) {
                next = next.right;
                while (next.left != null)
                    next = next.left;
                return current;
            }
            while (true) {
                if (next.parent == null) {
                    next = null;
                    return current;
                }
                if (next.parent.left == next) {
                    next = next.parent;
                    return current;
                }
                next = next.parent;
            }
        }
    }

    final class KeyIterator implements Iterator<K> {
        EntryIterator entryIterator = new EntryIterator();
        @Override
        public boolean hasNext() {
            return entryIterator.hasNext();
        }
        @Override
        public K next() {
            return entryIterator.next().getKey();
        }
    }

    final class ValueIterator implements Iterator<V> {
        EntryIterator entryIterator = new EntryIterator();
        @Override
        public boolean hasNext() {
            return entryIterator.hasNext();
        }
        @Override
        public V next() {
            return entryIterator.next().getValue();
        }
    }

    static final class Entry<K,V> implements Map.Entry<K,V> {
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        public Entry(K key, V value) {
            this(key, value, null, null, null);
        }
        public Entry(K key, V value, Entry<K, V> left, Entry<K, V> right, Entry<K, V> parent) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Entry<?, ?> entry)) return false;

            if (!Objects.equals(key, entry.key)) return false;
            return Objects.equals(value, entry.value);
        }
        @Override
        public int hashCode() {
            int keyHash = Objects.hashCode(key);
            int valueHash = Objects.hashCode(value);
            return keyHash ^ valueHash;
        }
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
