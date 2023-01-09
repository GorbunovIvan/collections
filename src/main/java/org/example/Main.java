package org.example;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<>();
        map.put(2, "2");
        map.put(4, "5");
        map.put(1, "4");
        map.put(3, "1");
        map.put(5, "3");

//        map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);

        map = sortMap(map);
        map.entrySet().forEach(System.out::println);
    }

    private static TreeMap<Integer, String> sortMap(Map<Integer, String> map) {
        TreeMap<Integer, String> sortedMap = new TreeMap<>(new Comparator1<>(map));
        sortedMap.putAll(map);
        return sortedMap;
    }

    static class Comparator1<K> implements Comparator<K> {
        private final TreeMap<K, String> map;
        public Comparator1(Map<K,String> map) {
            this.map = (TreeMap<K,String>) map;
        }
        @Override
        public int compare(K o1, K o2) {
            return map.get(o1).compareTo(map.get(o2));
        }
    }

}
