package com.games4u.engine.util;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author Victor Pérez
 * @since 18/05/2024
 * Clase utilizada para realizar ordenamientos
 */
public class Sorter {

    /**
     * Obtiene las 3 ocurrencias más frecuentes de una lista
     * @param list Lista a evaluar
     * @return Lista con las 3 ocurrencias
     */
    public static <T> List<T> getTopOccurrences(List<T> list) {
        Map<T, Integer> frequencies = new HashMap<>();

        // Agrega cada elemento junto con su frecuencia a un map
        for (T element: list) {
            Integer j = frequencies.get(element);
            if (j == null) {
                frequencies.put(element, 1);

            } else {
                frequencies.put(element, j + 1);
            }
        }

        // Utiliza un priority queue para determinar los más frecuentes
        PriorityQueue<T> heap = new PriorityQueue<>((a, b) -> frequencies.get(b) - frequencies.get(a));
        heap.addAll(frequencies.keySet());

        List<T> topOccurrences = new ArrayList<>();
        for (int i = 0; i < 3 && !heap.isEmpty(); i++) {
            topOccurrences.add(heap.poll());
        }

        return topOccurrences;
    }


    /**
     * Ordena un mapa según sus valores
     * @param map Mapa a evaluar
     * @return Lista con las llaves que tienen los valores más altos
     */
    public static <K, V extends Comparable<? super V>> List<K> sortByValue(Map<K, V> map) {
        // Ordena una lista con las entradas del map
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        // Obtiene únicamente las llaves
        List<K> topKeys = new ArrayList<>();
        for (Entry<K, V> entry: list) {
            topKeys.addFirst(entry.getKey());
        }

        return topKeys;
    }
}
