package org.example.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TreeMap1Test {

    private TreeMap1<Integer, String> map;
    private TreeMap<Integer, String> originalMap;

    @BeforeEach
    void testSetUp() {
        map = new TreeMap1<>();
        originalMap = new TreeMap<>();
        List<Integer> ints = new ArrayList<>(IntStream.range(0, 20).boxed().toList());
        Collections.shuffle(ints);
        for (Integer e : ints) {
            map.put(e, String.valueOf(e));
            originalMap.put(e, String.valueOf(e));
        }
        ints = new Random().ints(300, -100, 100).boxed().toList();
        for (Integer e : ints) {
            map.put(e, String.valueOf(e));
            originalMap.put(e, String.valueOf(e));

            map.put(543, null);
            originalMap.put(543, null);
        }
    }

    @Test
    void testConstructors() {
        assertEquals(new TreeMap<>(), new TreeMap1<>());
        assertEquals(new TreeMap<>(Comparator.naturalOrder()), new TreeMap1<>(Comparator.naturalOrder()));
        assertEquals(new TreeMap<>(Comparator.reverseOrder()), new TreeMap1<>(Comparator.reverseOrder()));
        assertEquals(originalMap, map);
        assertEquals(originalMap, new TreeMap1<>(originalMap));
    }

    @Test
    void testGet() {
        assertEquals(originalMap.get(4), map.get(4));
        assertEquals(originalMap.get(5), map.get(5));
        assertEquals(originalMap.get(-999), map.get(-999));
        
        assertThrows(NullPointerException.class, () -> map.get(null));
    }

    @Test
    void testGetOrDefault() {
        assertEquals(originalMap.getOrDefault(4, ""), map.getOrDefault(4, ""));
        assertEquals(originalMap.getOrDefault(5, ""), map.getOrDefault(5, ""));
        assertEquals(originalMap.getOrDefault(-999, "9"), map.getOrDefault(-999, "9"));
        assertEquals(originalMap, map);
    }

    @Test
    void testPut() {
        assertEquals(originalMap.put(4, ""), map.put(4, ""));
        assertEquals(originalMap.put(5, ""), map.put(5, ""));
        assertEquals(originalMap.put(-999, "9"), map.put(-999, "9"));
        assertEquals(originalMap, map);

        assertThrows(NullPointerException.class, () -> map.put(null, ""));
    }

    @Test
    void testPutIfAbsent() {
        assertEquals(originalMap.putIfAbsent(4, ""), map.putIfAbsent(4, ""));
        assertEquals(originalMap.putIfAbsent(5, ""), map.putIfAbsent(5, ""));
        assertEquals(originalMap.putIfAbsent(-999, "9"), map.putIfAbsent(-999, "9"));
        assertEquals(originalMap, map);
    }

    @Test
    void testPutAll() {
        Map<Integer, String> map1 = new TreeMap1<>();
        map1.put(888, "888");
        map1.put(777, "777");
        map.putAll(map1);
        originalMap.putAll(map1);
        assertEquals(originalMap, map);

        map = new TreeMap1<>();
        map.putAll(originalMap);
        assertEquals(originalMap, map);
    }


    @Test
    void testRemove() {
        assertEquals(originalMap.remove(4), map.remove(4));
        assertEquals(originalMap.remove(5), map.remove(5));
        assertEquals(originalMap.remove(25), map.remove(25));
        assertEquals(originalMap.remove(-999), map.remove(-999));
        assertEquals(originalMap, map);

        assertThrows(NullPointerException.class, () -> map.remove(null));

        map.clear();
        assertNull(map.remove(5));

        map.put(4, "2");
        map.put(0, "3");
        map.put(5, "1");
        assertNull(map.remove(7));
        assertEquals(3, map.size());

        assertEquals("2", map.remove(4));
        assertEquals(2, map.size());

        assertEquals("3", map.remove(0));
        assertEquals(1, map.size());

        assertEquals("1", map.remove(5));
        assertTrue(map.isEmpty());

        assertNull(map.remove(null));
    }

    @Test
    void testRemoveKeyValue() {
        assertEquals(originalMap.remove(4, "4"), map.remove(4, "4"));
        assertEquals(originalMap.remove(5, ""), map.remove(5, ""));
        assertEquals(originalMap.remove(-999, "9"), map.remove(-999, "9"));
        assertEquals(originalMap, map);

        assertThrows(NullPointerException.class, () -> map.remove(null, ""));
    }

    @Test
    void testReplace() {
        assertEquals(originalMap.replace(4, "2"), map.replace(4, "2"));
        assertEquals(originalMap.replace(5, "2"), map.replace(5, "2"));
        assertEquals(originalMap.replace(3, "3"), map.replace(3, "3"));
        assertEquals(originalMap.replace(5, "1"), map.replace(5, "1"));
        assertEquals(originalMap.replace(15, "12"), map.replace(15, "12"));
        assertEquals(originalMap.replace(789, "9"), map.replace(789, "9"));
        assertEquals(originalMap, map);
    }

    @Test
    void testReplaceKeyValue() {
        assertEquals(originalMap.replace(4, "4", "2"), map.replace(4, "4", "2"));
        assertEquals(originalMap.replace(5, "3", "2"), map.replace(5, "3", "2"));
        assertEquals(originalMap.replace(7, "7", "7"), map.replace(7, "7", "7"));
        assertEquals(originalMap.replace(789, "3", "2"), map.replace(789, "3", "2"));
        assertEquals(originalMap, map);
    }

    @Test
    void testReplaceAll() {
        map.replaceAll((k, v) -> k + v);
        originalMap.replaceAll((k, v) -> k + v);
        assertEquals(map, originalMap);
    }

    @Test
    void testForEach() {
        List<String> listMap = new ArrayList<>();
        List<String> listOriginalMap = new ArrayList<>();
        map.forEach((k, v) -> listMap.add(k + " = " + v));
        originalMap.forEach((k, v) -> listOriginalMap.add(k + " = " + v));
        Collections.sort(listMap);
        Collections.sort(listOriginalMap);
        assertEquals(listMap, listOriginalMap);
    }

    @Test
    void testContainsKey() {
        assertEquals(originalMap.containsKey(4), map.containsKey(4));
        assertEquals(originalMap.containsKey(5), map.containsKey(5));
        assertEquals(originalMap.containsKey(-999), map.containsKey(-999));
    }

    @Test
    void testContainsValue() {
        assertEquals(originalMap.containsValue("4"), map.containsValue("4"));
        assertEquals(originalMap.containsValue("5"), map.containsValue("5"));
        assertEquals(originalMap.containsValue("-999"), map.containsValue("-999"));
        assertEquals(originalMap.containsValue("222"), map.containsValue("222"));
        assertEquals(originalMap.containsValue("null"), map.containsValue("null"));
        assertEquals(originalMap.containsValue(null), map.containsValue(null));
    }

    @Test
    void testSize() {
        assertEquals(originalMap.size(), map.size());

        assertEquals(originalMap.put(4, ""), map.put(4, ""));
        assertEquals(originalMap.put(-999, "9"), map.put(-999, "9"));
        assertEquals(originalMap.size(), map.size());

        assertEquals(originalMap.remove(4), map.remove(4));
        assertEquals(originalMap.remove(-999), map.remove(-999));
        assertEquals(originalMap.size(), map.size());
    }

    @Test
    void testIsEmpty() {
        assertEquals(originalMap.isEmpty(), map.isEmpty());
        assertEquals(originalMap.put(4, ""), map.put(4, ""));
        assertEquals(originalMap.isEmpty(), map.isEmpty());
        assertEquals(originalMap.remove(4), map.remove(4));
        assertEquals(originalMap.isEmpty(), map.isEmpty());
    }

    @Test
    void testClear() {
        originalMap.clear();
        map.clear();
        assertEquals(originalMap, map);
    }

    @Test
    void testFirstKey() {
        assertEquals(originalMap.firstKey(), map.firstKey());
        assertNull(new TreeMap1<>().firstKey());
    }

    @Test
    void testLastKey() {
        assertEquals(originalMap.lastKey(), map.lastKey());
        assertNull(new TreeMap1<>().lastKey());
    }

    @Test
    void testComparator() {
        assertEquals(originalMap.comparator(), map.comparator());
        assertEquals(new TreeMap<>(Comparator.naturalOrder()), new TreeMap1<>(Comparator.naturalOrder()));
        assertEquals(new TreeMap<>(Comparator.reverseOrder()), new TreeMap1<>(Comparator.reverseOrder()));
    }

    @Test
    void testEntrySet() {
        Set<Map.Entry<Integer, String>> entrySetMap = map.entrySet();
        Set<Map.Entry<Integer, String>> entrySetOriginalMap = originalMap.entrySet();
        assertEquals(entrySetOriginalMap.size(), entrySetMap.size());
        assertEquals(entrySetOriginalMap, entrySetMap);

        List<String> listMap = new ArrayList<>();
        List<String> listOriginalMap = new ArrayList<>();
        entrySetMap.forEach(e -> listMap.add(e.toString()));
        entrySetOriginalMap.forEach(e -> listOriginalMap.add(e.toString()));
        Collections.sort(listMap);
        Collections.sort(listOriginalMap);
        assertEquals(listOriginalMap, listMap);

        TreeMap1<Integer, String> mapClone = new TreeMap1<>(map.comparator());
        mapClone.putAll(map);
        Iterator<Map.Entry<Integer, String>> iteratorMap = map.entrySet().iterator();
        Iterator<Map.Entry<Integer, String>> iteratorMapClone = mapClone.entrySet().iterator();
        while (iteratorMap.hasNext() && iteratorMapClone.hasNext()) {
            Map.Entry<Integer, String> nextFromMap = iteratorMap.next();
            Map.Entry<Integer, String> nextFromMapClone = iteratorMapClone.next();
            assertEquals(nextFromMap, nextFromMapClone);
            assertEquals(nextFromMap.hashCode(), nextFromMapClone.hashCode());
        }
        assertEquals(iteratorMap.hasNext(), iteratorMapClone.hasNext());

        Map.Entry<Integer, String> entryFromMap = null;
        Map.Entry<Integer, String> entryFromOriginalMap = null;
        for (Map.Entry<Integer, String> entry : entrySetMap) {
            entryFromMap = entry;
            break;
        }
        assertNotNull(entryFromMap);
        for (Map.Entry<Integer, String> entry : entrySetOriginalMap) {
            if (Objects.equals(entryFromMap.getKey(), entry.getKey())
                    && Objects.equals(entryFromMap.getValue(), entry.getValue())) {
                entryFromOriginalMap = entry;
                break;
            }
        }
        assertNotNull(entryFromOriginalMap);
        assertEquals(entrySetOriginalMap.remove(entryFromOriginalMap), entrySetMap.remove(entryFromMap));
        assertEquals(entrySetOriginalMap.remove(null), entrySetMap.remove(null));
        assertEquals(entrySetOriginalMap, entrySetMap);

        assertEquals(entrySetOriginalMap.contains(entryFromOriginalMap), entrySetMap.contains(entryFromMap));
        assertEquals(entrySetOriginalMap.contains(null), entrySetMap.contains(null));

        assertArrayEquals(entrySetOriginalMap.toArray(), entrySetMap.toArray());

        entrySetMap.clear();
        entrySetOriginalMap.clear();
        assertEquals(entrySetOriginalMap.size(), entrySetMap.size());
        assertEquals(entrySetOriginalMap, entrySetMap);
    }

    @Test
    void testKeySet() {
        Set<Integer> keySetMap = map.keySet();
        Set<Integer> keySetOriginalMap = originalMap.keySet();
        assertEquals(keySetOriginalMap.size(), keySetMap.size());
        assertEquals(keySetOriginalMap, keySetMap);

        List<String> listMap = new ArrayList<>();
        List<String> listOriginalMap = new ArrayList<>();
        keySetMap.forEach(e -> listMap.add(e == null ? "null" : e.toString()));
        keySetOriginalMap.forEach(e -> listOriginalMap.add(e == null ? "null" : e.toString()));
        Collections.sort(listMap);
        Collections.sort(listOriginalMap);
        assertEquals(listMap, listOriginalMap);

        assertEquals(keySetOriginalMap.remove(789), keySetMap.remove(789));
        assertEquals(keySetOriginalMap.remove(5), keySetMap.remove(5));
        assertEquals(keySetOriginalMap, keySetMap);

        assertEquals(keySetOriginalMap.contains(789), keySetMap.contains(789));
        assertEquals(keySetOriginalMap.contains(5), keySetMap.contains(5));

        assertArrayEquals(keySetOriginalMap.toArray(), keySetMap.toArray());

        keySetMap.clear();
        keySetOriginalMap.clear();
        assertEquals(keySetOriginalMap.size(), keySetMap.size());
        assertEquals(keySetOriginalMap, keySetMap);
    }

    @Test
    void testValues() {
        Collection<String> valuesMap = map.values();
        Collection<String> valuesOriginalMap = originalMap.values();
        assertEquals(valuesOriginalMap.size(), valuesMap.size());
        List<String> valuesMapArrayList = new ArrayList<>(valuesMap.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList());
        List<String> valuesOriginalMapArrayList = new ArrayList<>(valuesOriginalMap.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList());
        assertEquals(valuesOriginalMapArrayList, valuesMapArrayList);

        List<String> listMap = new ArrayList<>();
        List<String> listOriginalMap = new ArrayList<>();
        valuesMap.forEach(e -> listMap.add(e == null ? "null" : e));
        valuesOriginalMap.forEach(e -> listOriginalMap.add(e == null ? "null" : e));
        Collections.sort(listMap);
        Collections.sort(listOriginalMap);
        assertEquals(listMap, listOriginalMap);

        assertEquals(valuesOriginalMap.contains("789"), valuesMap.contains("789"));
        assertEquals(valuesOriginalMap.contains("5"), valuesMap.contains("5"));

        assertArrayEquals(valuesOriginalMap.toArray(), valuesMap.toArray());

        valuesMap.clear();
        valuesOriginalMap.clear();
        assertEquals(valuesOriginalMap.size(), valuesMap.size());
        assertEquals(Arrays.toString(valuesOriginalMap.toArray()), Arrays.toString(valuesMap.toArray()));
    }

}