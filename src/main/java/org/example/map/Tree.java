package org.example.map;

import java.util.*;
import java.util.stream.IntStream;

public class Tree implements Comparable<Tree>, Iterable<Tree> {

    Integer key;
    String value;
    int hash;

    Tree parent;
    Tree left;
    Tree right;

    public Tree(Integer key, String value) {
        this(key, value, null, null, null);
    }

    public Tree(Integer key, String value, Tree parent, Tree left, Tree right) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.hash = HashMap1.Node.hashForKey(this);
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String setValue(String value) {
        String oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public Tree getParent() {
        return parent;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
        if (parent != null) {
            if (parent.compareTo(this) >= 0)
                parent.left = this;
            else
                parent.right = this;
        }
    }

    public Tree getLeft() {
        return left;
    }

    public void setLeft(Tree left) {
        if (this.left != null) {
            this.left.add(left);
        } else {
            this.left = left;
            left.parent = this;
        }
    }

    public Tree getRight() {
        return right;
    }
    public void setRight(Tree right) {
        if (this.right != null) {
            this.right.add(right);
        } else {
            this.right = right;
            right.parent = this;
        }
    }

    public void add(Tree Tree) {
        if (compareTo(Tree) >= 0)
            setLeft(Tree);
        else
            setRight(Tree);
    }

    public Tree findNode(Integer key) {
        int compare = getKey().compareTo(key);
        if (compare == 0)
            return this;
        return compare > 0 ? left : right;
    }

    @Override
    public int compareTo(Tree o) {
        return getKey().compareTo(o.getKey());
    }

    @Override
    public String toString() {
        return key + "=" + value + " (parent=" + (parent == null ? "null" : parent.key) + ")";
    }

    @Override
    public Iterator<Tree> iterator() {
        return new TreeIterator(this);
    }

    public Iterator<Tree> iterator3() {
        return new TreeIterator3(this);
    }

    public static void main(String[] args) {
        List<Integer> ints = new Random()
                .ints(1112, 0, 11230)
                .boxed()
//                .distinct()
                .toList();
        Tree root = new Tree(ints.get(0), ints.get(0).toString());
        for (Integer i : ints)
            root.add(new Tree(i, i.toString()));

        Iterator<Tree> iter = root.iterator();
        Iterator<Tree> iter3 = root.iterator3();
        List<Tree> list1 = new ArrayList<>();
        List<Tree> list3 = new ArrayList<>();
        while (iter.hasNext() && iter3.hasNext()) {
            var next1 = iter.next();
            var next3 = iter3.next();
//            System.out.println(next1);
            list1.add(next1);
            list3.add(next3);
        }
        if (iter.hasNext() != iter3.hasNext())
            System.out.println("has next not equals");
        Collections.sort(list1);
        Collections.sort(list3);
        if (!list1.equals(list3))
            System.out.println("lists not equals");
    }

    private static void iterate(Tree root) {
        System.out.println(root);
        if (root.left != null)
            iterate(root.left);
        if (root.right != null)
            iterate(root.right);
    }

    private static void iterate2(Tree root) {
        while (root != null) {
            System.out.println(root);
            root = root.left;
        }
    }
}

class TreeIterator implements Iterator<Tree> {

    Tree next;

    public TreeIterator(Tree root) {
        this.next = root;
        // going to the leftmost node
        if (this.next != null) {
            while (this.next.left != null)
                this.next = this.next.left;
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Tree next() {
        if (!hasNext())
            throw new NoSuchElementException();
        Tree current = next;
        if (next.right != null) {
            next = next.right;
            while (next.left != null)
                next = next.left;
            return current;
        }
        while (true) {
            // if this node is root, then finish
            if (next.parent == null) {
                next = null;
                return current;
            }
            // go one-step upside
            // but if this node was left for its parent then return it now
            if (next.parent.left == next) {
                next = next.parent;
                return current;
            }
            next = next.parent;
        }
    }
}

class TreeIterator2 implements Iterator<Tree> {

    Tree next;
    boolean rightWayIsPassed = false;
    boolean leftWayIsPassed = false;

    public TreeIterator2(Tree root) {
        this.next = root;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Tree next() {
        if (!hasNext())
            throw new NoSuchElementException();
        Tree current = next;
        if (next.left != null)
            next = next.left;
        else if (next.right != null)
            next = next.right;
        return current;
    }
}

class TreeIterator3 implements Iterator<Tree> {

    Tree next;

    public TreeIterator3(Tree root) {
        this.next = root;
        // going to the leftmost node
        if (this.next != null) {
            while (this.next.left != null)
                this.next = this.next.left;
        } else if (this.next.right != null) {
            this.next = this.next.right;
            while (this.next.left != null)
                this.next = this.next.left;
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Tree next() {
        if (!hasNext())
            throw new NoSuchElementException();
        Tree current = next;
        if (next.right != null) {
            next = next.right;
            while (next.left != null)
                next = next.left;
            return current;
        }
        while (true) {
            // if this node is root, then finish
            if (next.parent == null) {
                next = null;
                return current;
            }
            // go one-step upside
            // but if this node was left for its parent then return it now
            if (next.parent.left == next) {
                next = next.parent;
                return current;
            }
            next = next.parent;
        }
    }
}
