package org.example.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HashMap1Test {

    private HashMap1<Key, String> mapDifferentKeys;
    private HashMap<Key, String> originalMapDifferentKeys;

    private HashMap1<KeyDuplicatedHashCodes, String> mapDuplicatedKeys;
    private HashMap<KeyDuplicatedHashCodes, String> originalMapDuplicatedKeys;

    @BeforeEach
    void testSetUp() {
        mapDifferentKeys = new HashMap1<>();
        originalMapDifferentKeys = new HashMap<>();
        mapDuplicatedKeys = new HashMap1<>();
        originalMapDuplicatedKeys = new HashMap<>();

        List<Integer> ints = new ArrayList<>(IntStream.range(0, 20).boxed().toList());
        Collections.shuffle(ints);
        for (Integer e : ints) {
            mapDifferentKeys.put(new Key(e), String.valueOf(e));
            originalMapDifferentKeys.put(new Key(e), String.valueOf(e));

            mapDuplicatedKeys.put(new KeyDuplicatedHashCodes(e), String.valueOf(e));
            originalMapDuplicatedKeys.put(new KeyDuplicatedHashCodes(e), String.valueOf(e));

            mapDifferentKeys.put(null, "222");
            originalMapDifferentKeys.put(null, "222");
            mapDuplicatedKeys.put(null, "222");
            originalMapDuplicatedKeys.put(null, "222");

            mapDifferentKeys.put(new Key(543), null);
            originalMapDifferentKeys.put(new Key(543), null);
            mapDuplicatedKeys.put(new KeyDuplicatedHashCodes(543), null);
            originalMapDuplicatedKeys.put(new KeyDuplicatedHashCodes(543), null);
        }
    }

    @Test
    void testConstructors() {
        assertEquals(new HashMap<>(), new HashMap1<>());
        assertEquals(new HashMap<>(87), new HashMap1<>(87));

        assertEquals(originalMapDifferentKeys, mapDifferentKeys);
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);

        assertEquals(originalMapDifferentKeys, new HashMap1<>(originalMapDifferentKeys));
        assertEquals(originalMapDuplicatedKeys, new HashMap1<>(originalMapDuplicatedKeys));

        assertThrows(IllegalArgumentException.class, () -> new HashMap1<>(-1));
        assertThrows(IllegalArgumentException.class, () -> new HashMap1<>(4, 0));
    }

    @Test
    void testGet() {
        assertEquals(originalMapDifferentKeys.get(new Key(4)), mapDifferentKeys.get(new Key(4)));
        assertEquals(originalMapDifferentKeys.get(new Key(5)), mapDifferentKeys.get(new Key(5)));
        assertEquals(originalMapDifferentKeys.get(new Key(-999)), mapDifferentKeys.get(new Key(-999)));
        assertEquals(originalMapDifferentKeys.get(null), mapDifferentKeys.get(null));

        assertEquals(originalMapDuplicatedKeys.get(new KeyDuplicatedHashCodes(4)), mapDuplicatedKeys.get(new KeyDuplicatedHashCodes(4)));
        assertEquals(originalMapDuplicatedKeys.get(new KeyDuplicatedHashCodes(5)), mapDuplicatedKeys.get(new KeyDuplicatedHashCodes(5)));
        assertEquals(originalMapDuplicatedKeys.get(new KeyDuplicatedHashCodes(-999)), mapDuplicatedKeys.get(new KeyDuplicatedHashCodes(-999)));
        assertEquals(originalMapDuplicatedKeys.get(null), mapDuplicatedKeys.get(null));
    }

    @Test
    void testGetOrDefault() {
        assertEquals(originalMapDifferentKeys.getOrDefault(new Key(4), ""), mapDifferentKeys.getOrDefault(new Key(4), ""));
        assertEquals(originalMapDifferentKeys.getOrDefault(new Key(5), ""), mapDifferentKeys.getOrDefault(new Key(5), ""));
        assertEquals(originalMapDifferentKeys.getOrDefault(new Key(-999), "9"), mapDifferentKeys.getOrDefault(new Key(-999), "9"));
        assertEquals(originalMapDifferentKeys.getOrDefault(null, ""), mapDifferentKeys.getOrDefault(null, ""));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        assertEquals(originalMapDuplicatedKeys.getOrDefault(new KeyDuplicatedHashCodes(4), ""), mapDuplicatedKeys.getOrDefault(new KeyDuplicatedHashCodes(4), ""));
        assertEquals(originalMapDuplicatedKeys.getOrDefault(new KeyDuplicatedHashCodes(5), ""), mapDuplicatedKeys.getOrDefault(new KeyDuplicatedHashCodes(5), ""));
        assertEquals(originalMapDuplicatedKeys.getOrDefault(new KeyDuplicatedHashCodes(-999), "9"), mapDuplicatedKeys.getOrDefault(new KeyDuplicatedHashCodes(-999), "9"));
        assertEquals(originalMapDuplicatedKeys.getOrDefault(null, ""), mapDuplicatedKeys.getOrDefault(null, ""));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);
    }

    @Test
    void testPut() {
        assertEquals(originalMapDifferentKeys.put(new Key(4), ""), mapDifferentKeys.put(new Key(4), ""));
        assertEquals(originalMapDifferentKeys.put(new Key(5), ""), mapDifferentKeys.put(new Key(5), ""));
        assertEquals(originalMapDifferentKeys.put(new Key(-999), "9"), mapDifferentKeys.put(new Key(-999), "9"));
        assertEquals(originalMapDifferentKeys.put(null, ""), mapDifferentKeys.put(null, ""));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        assertEquals(originalMapDuplicatedKeys.put(new KeyDuplicatedHashCodes(4), ""), mapDuplicatedKeys.put(new KeyDuplicatedHashCodes(4), ""));
        assertEquals(originalMapDuplicatedKeys.put(new KeyDuplicatedHashCodes(5), ""), mapDuplicatedKeys.put(new KeyDuplicatedHashCodes(5), ""));
        assertEquals(originalMapDuplicatedKeys.put(new KeyDuplicatedHashCodes(-999), "9"), mapDuplicatedKeys.put(new KeyDuplicatedHashCodes(-999), "9"));
        assertEquals(originalMapDuplicatedKeys.put(null, ""), mapDuplicatedKeys.put(null, ""));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testPutAll() {
        Map<Key, String> map1 = new HashMap<>();
        map1.put(new Key(888), "888");
        map1.put(new Key(777), "777");
        mapDifferentKeys.putAll(map1);
        originalMapDifferentKeys.putAll(map1);
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        Map<KeyDuplicatedHashCodes, String> map2 = new HashMap<>();
        map2.put(new KeyDuplicatedHashCodes(888), "888");
        map2.put(new KeyDuplicatedHashCodes(777), "777");
        mapDuplicatedKeys.putAll(map2);
        originalMapDuplicatedKeys.putAll(map2);
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);

        mapDifferentKeys = new HashMap1<>();
        mapDuplicatedKeys = new HashMap1<>();

        mapDifferentKeys.putAll(originalMapDifferentKeys);
        mapDuplicatedKeys.putAll(originalMapDuplicatedKeys);

        assertEquals(originalMapDifferentKeys, mapDifferentKeys);
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testPutIfAbsent() {
        assertEquals(originalMapDifferentKeys.putIfAbsent(new Key(4), ""), mapDifferentKeys.putIfAbsent(new Key(4), ""));
        assertEquals(originalMapDifferentKeys.putIfAbsent(new Key(5), ""), mapDifferentKeys.putIfAbsent(new Key(5), ""));
        assertEquals(originalMapDifferentKeys.putIfAbsent(new Key(-999), "9"), mapDifferentKeys.putIfAbsent(new Key(-999), "9"));
        assertEquals(originalMapDifferentKeys.putIfAbsent(null, ""), mapDifferentKeys.putIfAbsent(null, ""));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        assertEquals(originalMapDuplicatedKeys.putIfAbsent(new KeyDuplicatedHashCodes(4), ""), mapDuplicatedKeys.putIfAbsent(new KeyDuplicatedHashCodes(4), ""));
        assertEquals(originalMapDuplicatedKeys.putIfAbsent(new KeyDuplicatedHashCodes(5), ""), mapDuplicatedKeys.putIfAbsent(new KeyDuplicatedHashCodes(5), ""));
        assertEquals(originalMapDuplicatedKeys.putIfAbsent(new KeyDuplicatedHashCodes(-999), "9"), mapDuplicatedKeys.putIfAbsent(new KeyDuplicatedHashCodes(-999), "9"));
        assertEquals(originalMapDuplicatedKeys.putIfAbsent(null, ""), mapDuplicatedKeys.putIfAbsent(null, ""));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testRemove() {
        assertEquals(originalMapDifferentKeys.remove(new Key(4)), mapDifferentKeys.remove(new Key(4)));
        assertEquals(originalMapDifferentKeys.remove(new Key(5)), mapDifferentKeys.remove(new Key(5)));
        assertEquals(originalMapDifferentKeys.remove(new Key(25)), mapDifferentKeys.remove(new Key(25)));
        assertEquals(originalMapDifferentKeys.remove(new Key(-999)), mapDifferentKeys.remove(new Key(-999)));
        assertEquals(originalMapDifferentKeys.remove(null), mapDifferentKeys.remove(null));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(4)), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(4)));
        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(5)), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(5)));
        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(25)), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(25)));
        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(-999)), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(-999)));
        assertEquals(originalMapDuplicatedKeys.remove(null), mapDuplicatedKeys.remove(null));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testRemoveKeyValue() {
        assertEquals(originalMapDifferentKeys.remove(new Key(4), "4"), mapDifferentKeys.remove(new Key(4), "4"));
        assertEquals(originalMapDifferentKeys.remove(new Key(5), ""), mapDifferentKeys.remove(new Key(5), ""));
        assertEquals(originalMapDifferentKeys.remove(new Key(-999), "9"), mapDifferentKeys.remove(new Key(-999), "9"));
        assertEquals(originalMapDifferentKeys.remove(null, ""), mapDifferentKeys.remove(null, ""));
        assertEquals(originalMapDifferentKeys.remove(null, "222"), mapDifferentKeys.remove(null, "222"));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(4), "4"), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(4), "4"));
        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(5), ""), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(5), ""));
        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(-999), "9"), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(-999), "9"));
        assertEquals(originalMapDuplicatedKeys.remove(null, ""), mapDuplicatedKeys.remove(null, ""));
        assertEquals(originalMapDuplicatedKeys.remove(null, "222"), mapDuplicatedKeys.remove(null, "222"));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testReplace() {
        assertEquals(originalMapDifferentKeys.replace(new Key(4), "2"), mapDifferentKeys.replace(new Key(4), "2"));
        assertEquals(originalMapDifferentKeys.replace(new Key(5), "2"), mapDifferentKeys.replace(new Key(5), "2"));
        assertEquals(originalMapDifferentKeys.replace(new Key(3), "3"), mapDifferentKeys.replace(new Key(3), "3"));
        assertEquals(originalMapDifferentKeys.replace(new Key(5), "1"), mapDifferentKeys.replace(new Key(5), "1"));
        assertEquals(originalMapDifferentKeys.replace(new Key(15), "12"), mapDifferentKeys.replace(new Key(15), "12"));
        assertEquals(originalMapDifferentKeys.replace(new Key(789), "9"), mapDifferentKeys.replace(new Key(789), "9"));
        assertEquals(originalMapDifferentKeys.replace(null, null), mapDifferentKeys.replace(null, null));
        assertEquals(originalMapDifferentKeys.replace(null, "222"), mapDifferentKeys.replace(null, "222"));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(4), "2"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(4), "2"));
        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(5), "2"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(5), "2"));
        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(3), "3"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(3), "3"));
        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(5), "1"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(5), "1"));
        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(15), "12"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(15), "12"));
        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(789), "9"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(789), "9"));
        assertEquals(originalMapDuplicatedKeys.replace(null, null), mapDuplicatedKeys.replace(null, null));
        assertEquals(originalMapDuplicatedKeys.replace(null, "222"), mapDuplicatedKeys.replace(null, "222"));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testReplaceKeyValue() {
        assertEquals(originalMapDifferentKeys.replace(new Key(4), "4", "2"), mapDifferentKeys.replace(new Key(4), "4", "2"));
        assertEquals(originalMapDifferentKeys.replace(new Key(5), "3", "2"), mapDifferentKeys.replace(new Key(5), "3", "2"));
        assertEquals(originalMapDifferentKeys.replace(new Key(789), "3", "2"), mapDifferentKeys.replace(new Key(789), "3", "2"));
        assertEquals(originalMapDifferentKeys.replace(null, null, "222"), mapDifferentKeys.replace(null, null, "222"));
        assertEquals(originalMapDifferentKeys.replace(null, "222", "223"), mapDifferentKeys.replace(null, "222", "223"));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(4), "4", "2"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(4), "4", "2"));
        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(5), "3", "2"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(5), "3", "2"));
        assertEquals(originalMapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(789), "3", "2"), mapDuplicatedKeys.replace(new KeyDuplicatedHashCodes(789), "3", "2"));
        assertEquals(originalMapDuplicatedKeys.replace(null, null, "222"), mapDuplicatedKeys.replace(null, null, "222"));
        assertEquals(originalMapDuplicatedKeys.replace(null, "222", "223"), mapDuplicatedKeys.replace(null, "222", "223"));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testReplaceAll() {
        mapDifferentKeys.replaceAll((k, v) -> k + v);
        originalMapDifferentKeys.replaceAll((k, v) -> k + v);
        assertEquals(mapDifferentKeys, originalMapDifferentKeys);

        mapDuplicatedKeys.replaceAll((k, v) -> k + v);
        originalMapDuplicatedKeys.replaceAll((k, v) -> k + v);
        assertEquals(mapDuplicatedKeys, originalMapDuplicatedKeys);
    }

    @Test
    void testClear() {
        originalMapDifferentKeys.clear();
        mapDifferentKeys.clear();
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        originalMapDuplicatedKeys.clear();
        mapDuplicatedKeys.clear();
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testContainsKey() {
        assertEquals(originalMapDifferentKeys.containsKey(new Key(4)), mapDifferentKeys.containsKey(new Key(4)));
        assertEquals(originalMapDifferentKeys.containsKey(new Key(5)), mapDifferentKeys.containsKey(new Key(5)));
        assertEquals(originalMapDifferentKeys.containsKey(new Key(-999)), mapDifferentKeys.containsKey(new Key(-999)));
        assertEquals(originalMapDifferentKeys.containsKey(null), mapDifferentKeys.containsKey(null));

        assertEquals(originalMapDuplicatedKeys.containsKey(new KeyDuplicatedHashCodes(4)), mapDuplicatedKeys.containsKey(new KeyDuplicatedHashCodes(4)));
        assertEquals(originalMapDuplicatedKeys.containsKey(new KeyDuplicatedHashCodes(5)), mapDuplicatedKeys.containsKey(new KeyDuplicatedHashCodes(5)));
        assertEquals(originalMapDuplicatedKeys.containsKey(new KeyDuplicatedHashCodes(-999)), mapDuplicatedKeys.containsKey(new KeyDuplicatedHashCodes(-999)));
        assertEquals(originalMapDuplicatedKeys.containsKey(null), mapDuplicatedKeys.containsKey(null));
    }

    @Test
    void testContainsValue() {
        assertEquals(originalMapDifferentKeys.containsValue("4"), mapDifferentKeys.containsValue("4"));
        assertEquals(originalMapDifferentKeys.containsValue("5"), mapDifferentKeys.containsValue("5"));
        assertEquals(originalMapDifferentKeys.containsValue("-999"), mapDifferentKeys.containsValue("-999"));
        assertEquals(originalMapDifferentKeys.containsValue("222"), mapDifferentKeys.containsValue("222"));
        assertEquals(originalMapDifferentKeys.containsValue("null"), mapDifferentKeys.containsValue("null"));
        assertEquals(originalMapDifferentKeys.containsValue(null), mapDifferentKeys.containsValue(null));

        assertEquals(originalMapDuplicatedKeys.containsValue("4"), mapDuplicatedKeys.containsValue("4"));
        assertEquals(originalMapDuplicatedKeys.containsValue("5"), mapDuplicatedKeys.containsValue("5"));
        assertEquals(originalMapDuplicatedKeys.containsValue("-999"), mapDuplicatedKeys.containsValue("-999"));
        assertEquals(originalMapDuplicatedKeys.containsValue("222"), mapDuplicatedKeys.containsValue("222"));
        assertEquals(originalMapDuplicatedKeys.containsValue("null"), mapDuplicatedKeys.containsValue("null"));
        assertEquals(originalMapDuplicatedKeys.containsValue(null), mapDuplicatedKeys.containsValue(null));
    }

    @Test
    void testSize() {
        assertEquals(originalMapDifferentKeys.size(), mapDifferentKeys.size());

        assertEquals(originalMapDifferentKeys.put(new Key(4), ""), mapDifferentKeys.put(new Key(4), ""));
        assertEquals(originalMapDifferentKeys.put(new Key(-999), "9"), mapDifferentKeys.put(new Key(-999), "9"));
        assertEquals(originalMapDifferentKeys.put(null, ""), mapDifferentKeys.put(null, ""));
        assertEquals(originalMapDifferentKeys.size(), mapDifferentKeys.size());

        assertEquals(originalMapDifferentKeys.remove(new Key(4)), mapDifferentKeys.remove(new Key(4)));
        assertEquals(originalMapDifferentKeys.remove(new Key(-999)), mapDifferentKeys.remove(new Key(-999)));
        assertEquals(originalMapDifferentKeys.remove(null), mapDifferentKeys.remove(null));
        assertEquals(originalMapDifferentKeys.size(), mapDifferentKeys.size());

        assertEquals(originalMapDuplicatedKeys.size(), mapDuplicatedKeys.size());

        assertEquals(originalMapDuplicatedKeys.put(new KeyDuplicatedHashCodes(4), ""), mapDuplicatedKeys.put(new KeyDuplicatedHashCodes(4), ""));
        assertEquals(originalMapDuplicatedKeys.put(new KeyDuplicatedHashCodes(-999), "9"), mapDuplicatedKeys.put(new KeyDuplicatedHashCodes(-999), "9"));
        assertEquals(originalMapDuplicatedKeys.put(null, ""), mapDuplicatedKeys.put(null, ""));
        assertEquals(originalMapDuplicatedKeys.size(), mapDuplicatedKeys.size());

        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(4)), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(4)));
        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(-999)), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(-999)));
        assertEquals(originalMapDuplicatedKeys.remove(null), mapDuplicatedKeys.remove(null));
        assertEquals(originalMapDuplicatedKeys.size(), mapDuplicatedKeys.size());
    }

    @Test
    void testIsEmpty() {
        assertEquals(originalMapDifferentKeys.isEmpty(), mapDifferentKeys.isEmpty());
        assertEquals(originalMapDifferentKeys.put(new Key(4), ""), mapDifferentKeys.put(new Key(4), ""));
        assertEquals(originalMapDifferentKeys.isEmpty(), mapDifferentKeys.isEmpty());
        assertEquals(originalMapDifferentKeys.remove(new Key(4)), mapDifferentKeys.remove(new Key(4)));
        assertEquals(originalMapDifferentKeys.isEmpty(), mapDifferentKeys.isEmpty());

        assertEquals(originalMapDuplicatedKeys.isEmpty(), mapDuplicatedKeys.isEmpty());
        assertEquals(originalMapDuplicatedKeys.put(new KeyDuplicatedHashCodes(4), ""), mapDuplicatedKeys.put(new KeyDuplicatedHashCodes(4), ""));
        assertEquals(originalMapDuplicatedKeys.isEmpty(), mapDuplicatedKeys.isEmpty());
        assertEquals(originalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(4)), mapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(4)));
        assertEquals(originalMapDuplicatedKeys.isEmpty(), mapDuplicatedKeys.isEmpty());
    }

    @Test
    void testEntrySet() {
        Set<Map.Entry<Key, String>> entrySetMapDifferentKeys = mapDifferentKeys.entrySet();
        Set<Map.Entry<Key, String>> entrySetOriginalMapDifferentKeys = originalMapDifferentKeys.entrySet();
        assertEquals(entrySetOriginalMapDifferentKeys.size(), entrySetMapDifferentKeys.size());
        assertEquals(entrySetOriginalMapDifferentKeys, entrySetMapDifferentKeys);

        List<String> listMapDifferentKeys = new ArrayList<>();
        List<String> listOriginalMapDifferentKeys = new ArrayList<>();
        entrySetMapDifferentKeys.forEach(e -> listMapDifferentKeys.add(e.toString()));
        entrySetOriginalMapDifferentKeys.forEach(e -> listOriginalMapDifferentKeys.add(e.toString()));
        Collections.sort(listMapDifferentKeys);
        Collections.sort(listOriginalMapDifferentKeys);
        assertEquals(listOriginalMapDifferentKeys, listMapDifferentKeys);

        Map.Entry<Key, String> entryFromMapDifferentKeys = null;
        Map.Entry<Key, String> entryFromOriginalMapDifferentKeys = null;
        for (Map.Entry<Key, String> entry : entrySetMapDifferentKeys) {
            entryFromMapDifferentKeys = entry;
            break;
        }
        assert entryFromMapDifferentKeys != null;
        for (Map.Entry<Key, String> entry : entrySetOriginalMapDifferentKeys) {
            if (Objects.equals(entryFromMapDifferentKeys.getKey(), entry.getKey())
                && Objects.equals(entryFromMapDifferentKeys.getValue(), entry.getValue())) {
                entryFromOriginalMapDifferentKeys = entry;
                break;
            }
        }
        assert entryFromOriginalMapDifferentKeys != null;
        assertEquals(entrySetOriginalMapDifferentKeys.remove(entryFromOriginalMapDifferentKeys), entrySetMapDifferentKeys.remove(entryFromMapDifferentKeys));
        assertEquals(entrySetOriginalMapDifferentKeys.remove(null), entrySetMapDifferentKeys.remove(null));
        assertEquals(entrySetOriginalMapDifferentKeys, entrySetMapDifferentKeys);

        assertEquals(entrySetOriginalMapDifferentKeys.contains(entryFromOriginalMapDifferentKeys), entrySetMapDifferentKeys.contains(entryFromMapDifferentKeys));
        assertEquals(entrySetOriginalMapDifferentKeys.contains(null), entrySetMapDifferentKeys.contains(null));

        assertArrayEquals(entrySetOriginalMapDifferentKeys.toArray(), entrySetMapDifferentKeys.toArray());

        entrySetMapDifferentKeys.clear();
        entrySetOriginalMapDifferentKeys.clear();
        assertEquals(entrySetOriginalMapDifferentKeys.size(), entrySetMapDifferentKeys.size());
        assertEquals(entrySetOriginalMapDifferentKeys, entrySetMapDifferentKeys);

        // duplicated keys
        Set<Map.Entry<KeyDuplicatedHashCodes, String>> entrySetMapDuplicatedKeys = mapDuplicatedKeys.entrySet();
        Set<Map.Entry<KeyDuplicatedHashCodes, String>> entrySetOriginalMapDuplicatedKeys = originalMapDuplicatedKeys.entrySet();
        assertEquals(entrySetOriginalMapDuplicatedKeys.size(), entrySetMapDuplicatedKeys.size());
        assertEquals(entrySetOriginalMapDuplicatedKeys, entrySetMapDuplicatedKeys);

        List<String> listMapDuplicatedKeys = new ArrayList<>();
        List<String> listOriginalMapDuplicatedKeys = new ArrayList<>();
        entrySetMapDuplicatedKeys.forEach(e -> listMapDuplicatedKeys.add(e.toString()));
        entrySetOriginalMapDuplicatedKeys.forEach(e -> listOriginalMapDuplicatedKeys.add(e.toString()));
        Collections.sort(listMapDuplicatedKeys);
        Collections.sort(listOriginalMapDuplicatedKeys);
        assertEquals(listMapDuplicatedKeys, listOriginalMapDuplicatedKeys);

        Map.Entry<KeyDuplicatedHashCodes, String> entryFromMapDuplicatedKeys = null;
        Map.Entry<KeyDuplicatedHashCodes, String> entryFromOriginalMapDuplicatedKeys = null;
        for (Map.Entry<KeyDuplicatedHashCodes, String> entry : entrySetMapDuplicatedKeys) {
            entryFromMapDuplicatedKeys = entry;
            break;
        }
        assert entryFromMapDuplicatedKeys != null;
        for (Map.Entry<KeyDuplicatedHashCodes, String> entry : entrySetOriginalMapDuplicatedKeys) {
            if (Objects.equals(entryFromMapDuplicatedKeys.getKey(), entry.getKey())
                    && Objects.equals(entryFromMapDuplicatedKeys.getValue(), entry.getValue())) {
                entryFromOriginalMapDuplicatedKeys = entry;
                break;
            }
        }
        assert entryFromOriginalMapDuplicatedKeys != null;
        assertEquals(entrySetOriginalMapDuplicatedKeys.remove(entryFromOriginalMapDuplicatedKeys), entrySetMapDuplicatedKeys.remove(entryFromMapDuplicatedKeys));
        assertEquals(entrySetOriginalMapDuplicatedKeys.remove(null), entrySetMapDuplicatedKeys.remove(null));
        assertEquals(entrySetOriginalMapDuplicatedKeys, entrySetMapDuplicatedKeys);

        assertEquals(entrySetOriginalMapDuplicatedKeys.contains(entryFromOriginalMapDuplicatedKeys), entrySetMapDuplicatedKeys.contains(entryFromMapDuplicatedKeys));
        assertEquals(entrySetOriginalMapDuplicatedKeys.contains(null), entrySetMapDuplicatedKeys.contains(null));

        assertArrayEquals(entrySetOriginalMapDuplicatedKeys.toArray(), entrySetMapDuplicatedKeys.toArray());

        entrySetMapDuplicatedKeys.clear();
        entrySetOriginalMapDuplicatedKeys.clear();
        assertEquals(entrySetOriginalMapDuplicatedKeys.size(), entrySetMapDuplicatedKeys.size());
        assertEquals(entrySetOriginalMapDuplicatedKeys, entrySetMapDuplicatedKeys);
    }

    @Test
    void testKeySet() {
        Set<Key> keySetMapDifferentKeys = mapDifferentKeys.keySet();
        Set<Key> keySetOriginalMapDifferentKeys = originalMapDifferentKeys.keySet();
        assertEquals(keySetOriginalMapDifferentKeys.size(), keySetMapDifferentKeys.size());
        assertEquals(keySetOriginalMapDifferentKeys, keySetMapDifferentKeys);

        List<String> listMapDifferentKeys = new ArrayList<>();
        List<String> listOriginalMapDifferentKeys = new ArrayList<>();
        keySetMapDifferentKeys.forEach(e -> listMapDifferentKeys.add(e == null ? "null" : e.toString()));
        keySetOriginalMapDifferentKeys.forEach(e -> listOriginalMapDifferentKeys.add(e == null ? "null" : e.toString()));
        Collections.sort(listMapDifferentKeys);
        Collections.sort(listOriginalMapDifferentKeys);
        assertEquals(listMapDifferentKeys, listOriginalMapDifferentKeys);

        assertEquals(keySetOriginalMapDifferentKeys.remove(new Key(789)), keySetMapDifferentKeys.remove(new Key(789)));
        assertEquals(keySetOriginalMapDifferentKeys.remove(new Key(5)), keySetMapDifferentKeys.remove(new Key(5)));
        assertEquals(keySetOriginalMapDifferentKeys, keySetMapDifferentKeys);

        assertEquals(keySetOriginalMapDifferentKeys.contains(new Key(789)), keySetMapDifferentKeys.contains(new Key(789)));
        assertEquals(keySetOriginalMapDifferentKeys.contains(new Key(5)), keySetMapDifferentKeys.contains(new Key(5)));

        assertArrayEquals(keySetOriginalMapDifferentKeys.toArray(), keySetMapDifferentKeys.toArray());

        keySetMapDifferentKeys.clear();
        keySetOriginalMapDifferentKeys.clear();
        assertEquals(keySetOriginalMapDifferentKeys.size(), keySetMapDifferentKeys.size());
        assertEquals(keySetOriginalMapDifferentKeys, keySetMapDifferentKeys);

        // duplicated keys
        Set<KeyDuplicatedHashCodes> keySetMapDuplicatedKeys = mapDuplicatedKeys.keySet();
        Set<KeyDuplicatedHashCodes> keySetOriginalMapDuplicatedKeys = originalMapDuplicatedKeys.keySet();
        assertEquals(keySetOriginalMapDuplicatedKeys.size(), keySetMapDuplicatedKeys.size());
        assertEquals(keySetOriginalMapDuplicatedKeys, keySetMapDuplicatedKeys);

        List<String> listMapDuplicatedKeys = new ArrayList<>();
        List<String> listOriginalMapDuplicatedKeys = new ArrayList<>();
        keySetMapDuplicatedKeys.forEach(e -> listMapDuplicatedKeys.add(e == null ? "null" : e.toString()));
        keySetOriginalMapDuplicatedKeys.forEach(e -> listOriginalMapDuplicatedKeys.add(e == null ? "null" : e.toString()));
        Collections.sort(listMapDuplicatedKeys);
        Collections.sort(listOriginalMapDuplicatedKeys);
        assertEquals(listMapDuplicatedKeys, listOriginalMapDuplicatedKeys);

        assertEquals(keySetOriginalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(789)), keySetMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(789)));
        assertEquals(keySetOriginalMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(5)), keySetMapDuplicatedKeys.remove(new KeyDuplicatedHashCodes(5)));
        assertEquals(keySetOriginalMapDuplicatedKeys, keySetMapDuplicatedKeys);

        assertEquals(keySetOriginalMapDuplicatedKeys.contains(new KeyDuplicatedHashCodes(789)), keySetMapDuplicatedKeys.contains(new KeyDuplicatedHashCodes(789)));
        assertEquals(keySetOriginalMapDuplicatedKeys.contains(new KeyDuplicatedHashCodes(5)), keySetMapDuplicatedKeys.contains(new KeyDuplicatedHashCodes(5)));

        assertArrayEquals(keySetOriginalMapDuplicatedKeys.toArray(), keySetMapDuplicatedKeys.toArray());

        keySetMapDuplicatedKeys.clear();
        keySetOriginalMapDuplicatedKeys.clear();
        assertEquals(keySetOriginalMapDuplicatedKeys.size(), keySetMapDuplicatedKeys.size());
        assertEquals(keySetOriginalMapDuplicatedKeys, keySetMapDuplicatedKeys);
    }

    @Test
    void testValues() {
        Collection<String> valuesMapDifferentKeys = mapDifferentKeys.values();
        Collection<String> valuesOriginalMapDifferentKeys = originalMapDifferentKeys.values();
        assertEquals(valuesOriginalMapDifferentKeys.size(), valuesMapDifferentKeys.size());
        List<String> valuesMapDifferentKeysArrayList = new ArrayList<>(valuesMapDifferentKeys.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList());
        List<String> valuesOriginalMapDifferentKeysArrayList = new ArrayList<>(valuesOriginalMapDifferentKeys.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList());
        assertEquals(valuesOriginalMapDifferentKeysArrayList, valuesMapDifferentKeysArrayList);

        List<String> listMapDifferentKeys = new ArrayList<>();
        List<String> listOriginalMapDifferentKeys = new ArrayList<>();
        valuesMapDifferentKeys.forEach(e -> listMapDifferentKeys.add(e == null ? "null" : e));
        valuesOriginalMapDifferentKeys.forEach(e -> listOriginalMapDifferentKeys.add(e == null ? "null" : e));
        Collections.sort(listMapDifferentKeys);
        Collections.sort(listOriginalMapDifferentKeys);
        assertEquals(listMapDifferentKeys, listOriginalMapDifferentKeys);

        assertEquals(valuesOriginalMapDifferentKeys.contains("789"), valuesMapDifferentKeys.contains("789"));
        assertEquals(valuesOriginalMapDifferentKeys.contains("5"), valuesMapDifferentKeys.contains("5"));

        assertArrayEquals(valuesOriginalMapDifferentKeys.toArray(), valuesMapDifferentKeys.toArray());

        valuesMapDifferentKeys.clear();
        valuesOriginalMapDifferentKeys.clear();
        assertEquals(valuesOriginalMapDifferentKeys.size(), valuesMapDifferentKeys.size());
        assertEquals(Arrays.toString(valuesOriginalMapDifferentKeys.toArray()), Arrays.toString(valuesMapDifferentKeys.toArray()));

        // duplicated keys
        Collection<String> valuesMapDuplicatedKeys = mapDuplicatedKeys.values();
        Collection<String> valuesOriginalMapDuplicatedKeys = originalMapDuplicatedKeys.values();
        assertEquals(valuesOriginalMapDuplicatedKeys.size(), valuesMapDuplicatedKeys.size());
        List<String> valuesMapDuplicatedKeysArrayList = new ArrayList<>(valuesMapDuplicatedKeys.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList());
        List<String> valuesOriginalMapDuplicatedKeysArrayList = new ArrayList<>(valuesOriginalMapDuplicatedKeys.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList());
        assertEquals(valuesOriginalMapDuplicatedKeysArrayList, valuesMapDuplicatedKeysArrayList);

        List<String> listMapDuplicatedKeys = new ArrayList<>();
        List<String> listOriginalMapDuplicatedKeys = new ArrayList<>();
        valuesMapDuplicatedKeys.forEach(e -> listMapDuplicatedKeys.add(e == null ? "null" : e));
        valuesOriginalMapDuplicatedKeys.forEach(e -> listOriginalMapDuplicatedKeys.add(e == null ? "null" : e));
        Collections.sort(listMapDuplicatedKeys);
        Collections.sort(listOriginalMapDuplicatedKeys);
        assertEquals(listMapDuplicatedKeys, listOriginalMapDuplicatedKeys);

        assertEquals(valuesOriginalMapDuplicatedKeys.remove("789"), valuesMapDuplicatedKeys.remove("789"));
        assertEquals(valuesOriginalMapDuplicatedKeys.remove("5"), valuesMapDuplicatedKeys.remove("5"));
        assertEquals(Arrays.toString(valuesOriginalMapDuplicatedKeys.toArray()), Arrays.toString(valuesMapDuplicatedKeys.toArray()));

        assertEquals(valuesOriginalMapDuplicatedKeys.contains("789"), valuesMapDuplicatedKeys.contains("789"));
        assertEquals(valuesOriginalMapDuplicatedKeys.contains("5"), valuesMapDuplicatedKeys.contains("5"));

        assertArrayEquals(valuesOriginalMapDuplicatedKeys.toArray(), valuesMapDuplicatedKeys.toArray());

        valuesMapDuplicatedKeys.clear();
        valuesOriginalMapDuplicatedKeys.clear();
        assertEquals(valuesOriginalMapDuplicatedKeys.size(), valuesMapDuplicatedKeys.size());
        assertEquals(Arrays.toString(valuesOriginalMapDuplicatedKeys.toArray()), Arrays.toString(valuesMapDuplicatedKeys.toArray()));
    }

    @Test
    void testComputeIfAbsent() {
        Function<Key, String> func1 = k -> k == null ? null : k.key + " (computed)";
        assertEquals(originalMapDifferentKeys.computeIfAbsent(new Key(789), func1), mapDifferentKeys.computeIfAbsent(new Key(789), func1));
        assertEquals(originalMapDifferentKeys.computeIfAbsent(new Key(5), func1), mapDifferentKeys.computeIfAbsent(new Key(5), func1));
        assertEquals(originalMapDifferentKeys.computeIfAbsent(new Key(543), func1), mapDifferentKeys.computeIfAbsent(new Key(543), func1));
        assertEquals(originalMapDifferentKeys.computeIfAbsent(null, func1), mapDifferentKeys.computeIfAbsent(null, func1));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        Function<KeyDuplicatedHashCodes, String> func2 = k -> k == null ? null : k.key + " (computed)";
        assertEquals(originalMapDuplicatedKeys.computeIfAbsent(new KeyDuplicatedHashCodes(789), func2), mapDuplicatedKeys.computeIfAbsent(new KeyDuplicatedHashCodes(789), func2));
        assertEquals(originalMapDuplicatedKeys.computeIfAbsent(new KeyDuplicatedHashCodes(5), func2), mapDuplicatedKeys.computeIfAbsent(new KeyDuplicatedHashCodes(5), func2));
        assertEquals(originalMapDuplicatedKeys.computeIfAbsent(new KeyDuplicatedHashCodes(543), func2), mapDuplicatedKeys.computeIfAbsent(new KeyDuplicatedHashCodes(543), func2));
        assertEquals(originalMapDuplicatedKeys.computeIfAbsent(null, func2), mapDuplicatedKeys.computeIfAbsent(null, func2));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testComputeIfPresent() {
        BiFunction<Key, String, String> func1 = (k, v) ->  k == null ? null : v == null ? k.toString() : v.concat(k.toString());
        assertEquals(originalMapDifferentKeys.computeIfPresent(new Key(789), func1), mapDifferentKeys.computeIfPresent(new Key(789), func1));
        assertEquals(originalMapDifferentKeys.computeIfPresent(new Key(5), func1), mapDifferentKeys.computeIfPresent(new Key(5), func1));
        assertEquals(originalMapDifferentKeys.computeIfPresent(null, func1), mapDifferentKeys.computeIfPresent(null, func1));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        BiFunction<KeyDuplicatedHashCodes, String, String> func2 = (k, v) ->  k == null ? null : v == null ? k.toString() : v.concat(k.toString());
        assertEquals(originalMapDuplicatedKeys.computeIfPresent(new KeyDuplicatedHashCodes(789), func2), mapDuplicatedKeys.computeIfPresent(new KeyDuplicatedHashCodes(789), func2));
        assertEquals(originalMapDuplicatedKeys.computeIfPresent(new KeyDuplicatedHashCodes(5), func2), mapDuplicatedKeys.computeIfPresent(new KeyDuplicatedHashCodes(5), func2));
        assertEquals(originalMapDuplicatedKeys.computeIfPresent(null, func1), mapDuplicatedKeys.computeIfPresent(null, func1));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testCompute() {
        BiFunction<Key, String, String> func1 = (k, v) ->  k == null ? null : v == null ? k.toString() : v.concat(k.toString());
        assertEquals(originalMapDifferentKeys.compute(new Key(789), func1), mapDifferentKeys.compute(new Key(789), func1));
        assertEquals(originalMapDifferentKeys.compute(new Key(789), func1), mapDifferentKeys.compute(new Key(789), func1));
        assertEquals(originalMapDifferentKeys.compute(new Key(5), func1), mapDifferentKeys.compute(new Key(5), func1));
        assertEquals(originalMapDifferentKeys.compute(null, func1), mapDifferentKeys.compute(null, func1));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        BiFunction<KeyDuplicatedHashCodes, String, String> func2 = (k, v) ->  k == null ? null : v == null ? k.toString() : v.concat(k.toString());
        assertEquals(originalMapDuplicatedKeys.compute(new KeyDuplicatedHashCodes(789), func2), mapDuplicatedKeys.compute(new KeyDuplicatedHashCodes(789), func2));
        assertEquals(originalMapDuplicatedKeys.compute(new KeyDuplicatedHashCodes(789), func2), mapDuplicatedKeys.compute(new KeyDuplicatedHashCodes(789), func2));
        assertEquals(originalMapDuplicatedKeys.compute(new KeyDuplicatedHashCodes(5), func2), mapDuplicatedKeys.compute(new KeyDuplicatedHashCodes(5), func2));
        assertEquals(originalMapDuplicatedKeys.compute(null, func1), mapDuplicatedKeys.compute(null, func1));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testMerge() {
        BiFunction<String, String, String> func1 = (v1, v2) ->  v1 == null || v2 == null ? null : v1 + v2;
        assertEquals(originalMapDifferentKeys.merge(new Key(789), "789", func1), mapDifferentKeys.merge(new Key(789), "789", func1));
        assertEquals(originalMapDifferentKeys.merge(new Key(789), "789", func1), mapDifferentKeys.merge(new Key(789), "789", func1));
        assertEquals(originalMapDifferentKeys.merge(new Key(5), "3", func1), mapDifferentKeys.merge(new Key(5), "3", func1));
        assertEquals(originalMapDifferentKeys.merge(null, "3", func1), mapDifferentKeys.merge(null, "3", func1));
        assertEquals(originalMapDifferentKeys.merge(new Key(543), "3", func1), mapDifferentKeys.merge(new Key(543), "3", func1));
        assertEquals(originalMapDifferentKeys, mapDifferentKeys);

        BiFunction<String, String, String> func2 = (v1, v2) ->  v1 == null || v2 == null ? null : v1 + v2;
        assertEquals(originalMapDuplicatedKeys.merge(new KeyDuplicatedHashCodes(789), "789", func2), mapDuplicatedKeys.merge(new KeyDuplicatedHashCodes(789), "789", func2));
        assertEquals(originalMapDuplicatedKeys.merge(new KeyDuplicatedHashCodes(789), "789", func2), mapDuplicatedKeys.merge(new KeyDuplicatedHashCodes(789), "789", func2));
        assertEquals(originalMapDuplicatedKeys.merge(new KeyDuplicatedHashCodes(5), "3", func2), mapDuplicatedKeys.merge(new KeyDuplicatedHashCodes(5), "3", func2));
        assertEquals(originalMapDuplicatedKeys.merge(null, "3", func2), mapDuplicatedKeys.merge(null, "3", func2));
        assertEquals(originalMapDuplicatedKeys.merge(new KeyDuplicatedHashCodes(543), "3", func2), mapDuplicatedKeys.merge(new KeyDuplicatedHashCodes(543), "3", func2));
        assertEquals(originalMapDuplicatedKeys, mapDuplicatedKeys);
    }

    @Test
    void testForEach() {
        List<String> listMapDifferentKeys = new ArrayList<>();
        List<String> listOriginalMapDifferentKeys = new ArrayList<>();
        mapDifferentKeys.forEach((k, v) -> listMapDifferentKeys.add(k + " = " + v));
        originalMapDifferentKeys.forEach((k, v) -> listOriginalMapDifferentKeys.add(k + " = " + v));
        Collections.sort(listMapDifferentKeys);
        Collections.sort(listOriginalMapDifferentKeys);
        assertEquals(listMapDifferentKeys, listOriginalMapDifferentKeys);

        List<String> listMapDuplicatedKeys = new ArrayList<>();
        List<String> listOriginalMapDuplicatedKeys = new ArrayList<>();
        mapDuplicatedKeys.forEach((k, v) -> listMapDuplicatedKeys.add(k + " = " + v));
        originalMapDuplicatedKeys.forEach((k, v) -> listOriginalMapDuplicatedKeys.add(k + " = " + v));
        Collections.sort(listMapDuplicatedKeys);
        Collections.sort(listOriginalMapDuplicatedKeys);
        assertEquals(listMapDuplicatedKeys, listOriginalMapDuplicatedKeys);
    }
}

class Key {
    int key;
    Key(int key) {
        this.key = key;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key key1)) return false;

        return key == key1.key;
    }
    @Override
    public int hashCode() {
        return key;
    }
    @Override
    public String toString() {
        return key + " [" + hashCode() + "]";
    }
}

class KeyDuplicatedHashCodes extends Key {
    KeyDuplicatedHashCodes(int key) {
        super(key);
    }
    @Override
    public int hashCode() {
        return key%5;
    }
}
