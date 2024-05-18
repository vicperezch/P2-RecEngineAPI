package com.games4u.engine.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SorterTest {
    @Test
    public void testGetTopOccurrences() {
        List<Integer> list = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        List<Integer> topOccurrences = Sorter.getTopOccurrences(list);
        assertEquals(Arrays.asList(4, 3, 2), topOccurrences);
    }

    @Test
    public void testSortByValue() {
        Map<String, Double> map = new HashMap<>();
        map.put("A", 0.2);
        map.put("B", 0.4);
        map.put("C", 1.0);
        List<String> sortedKeys = Sorter.sortByValue(map);
        assertEquals(Arrays.asList("C", "B", "A"), sortedKeys);
    }
}
