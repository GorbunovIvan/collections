package org.example.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class Stack1Test {
    
    private Stack1<Integer> stack;
    private Stack<Integer> originalStack;

    @BeforeEach
    void testSetUp() {
        List<Integer> listPrototype = List.of(3, 5, 1, 4, 5, 1, 3, 4, 5, 10, 11);

        stack = new Stack1<>(listPrototype);

        originalStack = new Stack<>();
        originalStack.addAll(listPrototype);
    }

    @Test
    void testConstructors() {
        assertEquals(stack, originalStack);
        assertEquals(new Stack1<>(), new Stack<>());
    }

    @Test
    void testPush() {
        stack.push(9);
        originalStack.push(9);
        assertEquals(stack, originalStack);

        stack.clear();
        originalStack.clear();
        stack.push(2);
        originalStack.push(2);
        assertEquals(stack, originalStack);
    }

    @Test
    void testPop() {
        assertEquals(stack.pop(), originalStack.pop());
        assertEquals(stack, originalStack);

        stack.clear();
        originalStack.clear();
        stack.push(2);
        originalStack.push(2);
        assertEquals(stack.pop(), originalStack.pop());
        assertEquals(stack, originalStack);
    }

    @Test
    void testPeek() {
        assertEquals(stack.peek(), originalStack.peek());
        assertEquals(stack, originalStack);

        stack.clear();
        originalStack.clear();
        stack.push(2);
        originalStack.push(2);
        assertEquals(stack.peek(), originalStack.peek());
        assertEquals(stack, originalStack);

        stack.push(null);
        originalStack.push(null);
        assertEquals(stack.peek(), originalStack.peek());
        assertEquals(stack, originalStack);
    }

    @Test
    void testEmpty() {
        assertEquals(originalStack.isEmpty(), stack.isEmpty());

        stack.pop();
        originalStack.pop();
        assertEquals(originalStack.size(), stack.size());
        assertEquals(stack, originalStack);

        stack.clear();
        originalStack.clear();
        assertEquals(originalStack.size(), stack.size());

        assertEquals(new Stack1().isEmpty(), new Stack().isEmpty());
    }

    @Test
    void testSearch() {
        assertEquals(originalStack.search(null), stack.search(null));

        for (Integer o : originalStack)
            assertEquals(originalStack.search(o), stack.search(o));

        stack.push(null);
        originalStack.push(null);
        assertEquals(originalStack.search(null), stack.search(null));
    }

    @Test
    void testIterator() {
        Iterator<Integer> iteratorOfOriginalStack = originalStack.iterator();
        Iterator<Integer> iteratorOfList = stack.iterator();
        while (iteratorOfOriginalStack.hasNext() && iteratorOfList.hasNext()) {
            assertEquals(iteratorOfOriginalStack.next(), iteratorOfList.next());
        }
        assertEquals(iteratorOfOriginalStack.hasNext(), iteratorOfList.hasNext());
        assertEquals(stack, originalStack);
    }
    
    @Test
    void testHashCode() {
        assertEquals(originalStack.hashCode(), stack.hashCode());
        assertEquals(new Stack().hashCode(), new Stack1().hashCode());
        assertEquals(new Stack1<>(List.of(2, 4, 87)).hashCode(), new Stack1<>(List.of(2, 4, 87)).hashCode());

        stack.push(null);
        originalStack.push(null);
        assertEquals(originalStack.hashCode(), stack.hashCode());

        assertNotEquals(new Stack1<>(List.of(2, 4, 87)).hashCode(), new Stack1<>(List.of(2, 4, 88)).hashCode());
        assertNotEquals(new Stack1<>(List.of(2, 4, 87)).hashCode(), new Stack1<>(List.of(2, 87, 4)).hashCode());
        assertNotEquals(new Stack1<>(List.of(2, 4, 87)).hashCode(), new Stack1<>(List.of(2, 4)).hashCode());
    }

    @Test
    void testEquals() {
        stack.push(null);
        originalStack.push(null);

        assertEquals(stack, stack);
        assertEquals(stack, originalStack);
        assertEquals(new Stack1(), new Stack());

        originalStack.clear();
        originalStack.addAll(List.of(2, 4, 87));
        assertEquals(new Stack1<>(List.of(2, 4, 87)), originalStack);

        assertNotEquals(stack, null);
        assertNotEquals(stack, new Object());

        originalStack.clear();
        originalStack.addAll(List.of(2, 4, 88));
        assertNotEquals(new Stack1<>(List.of(2, 4, 87)), originalStack);

        originalStack.clear();
        originalStack.addAll(List.of(2, 87, 4));
        assertNotEquals(new Stack1<>(List.of(2, 4, 87)), originalStack);

        originalStack.clear();
        originalStack.addAll(List.of(2, 4));
        assertNotEquals(new Stack1<>(List.of(2, 4, 87)), originalStack);

        stack.clear();
        originalStack.clear();
        stack.push(1);
        stack.push(2);
        originalStack.push(null);
        originalStack.push(2);
        assertNotEquals(stack, originalStack);
    }

    @Test
    void testToString() {
        assertEquals(originalStack.toString(), stack.toString());
        assertEquals(new Stack1().toString(), new Stack().toString());
    }
}