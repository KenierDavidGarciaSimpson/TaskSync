/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Escructura;

import model.Task;

/**
 *
 * @author kenie
 */
public class SimpleHashTable {
    private static final int DEFAULT_CAPACITY = 100;
    private HashNode[] table;
    private int size;
    private int capacity;
    
    /**
     * Clase interna para nodos de la tabla hash
     */
    private class HashNode {
        String key;
        Task value;
        HashNode next;
        
        public HashNode(String key, Task value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    
    /**
     * Constructor - Crea una tabla hash con capacidad por defecto
     */
    public SimpleHashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = new HashNode[capacity];
        this.size = 0;
    }
    
    /**
     * Constructor con capacidad personalizada
     */
    public SimpleHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new HashNode[capacity];
        this.size = 0;
    }
    
    /**
     * Función hash simple
     */
    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % capacity;
        }
        return Math.abs(hash);
    }
    
    /**
     * Inserta o actualiza un par clave-valor
     */
    public void put(String key, Task value) {
        if (key == null) {
            throw new IllegalArgumentException("La clave no puede ser null");
        }
        
        int index = hash(key);
        HashNode node = table[index];
        
        // Buscar si la clave ya existe
        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value; // Actualizar
                return;
            }
            node = node.next;
        }
        
        // Insertar nuevo nodo al inicio de la lista
        HashNode newNode = new HashNode(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
    }
    
    /**
     * Obtiene el valor asociado a una clave
     */
    public Task get(String key) {
        if (key == null) {
            return null;
        }
        
        int index = hash(key);
        HashNode node = table[index];
        
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        
        return null;
    }
    
    /**
     * Elimina un par clave-valor
     */
    public boolean remove(String key) {
        if (key == null) {
            return false;
        }
        
        int index = hash(key);
        HashNode node = table[index];
        HashNode prev = null;
        
        while (node != null) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        
        return false;
    }
    
    /**
     * Verifica si existe una clave
     */
    public boolean containsKey(String key) {
        return get(key) != null;
    }
    
    /**
     * Verifica si la tabla está vacía
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Retorna el número de elementos
     */
    public int size() {
        return size;
    }
    
    /**
     * Limpia toda la tabla
     */
    public void clear() {
        table = new HashNode[capacity];
        size = 0;
    }
    
    /**
     * Retorna todas las claves
     */
    public String[] keys() {
        String[] keys = new String[size];
        int index = 0;
        
        for (int i = 0; i < capacity; i++) {
            HashNode node = table[i];
            while (node != null) {
                keys[index++] = node.key;
                node = node.next;
            }
        }
        
        return keys;
    }
    
    /**
     * Retorna todos los valores
     */
    public Task[] values() {
        Task[] values = new Task[size];
        int index = 0;
        
        for (int i = 0; i < capacity; i++) {
            HashNode node = table[i];
            while (node != null) {
                values[index++] = node.value;
                node = node.next;
            }
        }
        
        return values;
    }
    
    /**
     * Calcula el factor de carga
     */
    public double loadFactor() {
        return (double) size / capacity;
    }
    
    /**
     * Muestra estadísticas de la tabla hash
     */
    public void displayStatistics() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║  ESTADÍSTICAS TABLA HASH           ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ Capacidad: " + capacity);
        System.out.println("║ Elementos: " + size);
        System.out.println("║ Factor de carga: " + String.format("%.2f", loadFactor()));
        
        int usedBuckets = 0;
        int maxChainLength = 0;
        
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                usedBuckets++;
                int chainLength = 0;
                HashNode node = table[i];
                while (node != null) {
                    chainLength++;
                    node = node.next;
                }
                maxChainLength = Math.max(maxChainLength, chainLength);
            }
        }
        
        System.out.println("║ Buckets usados: " + usedBuckets);
        System.out.println("║ Cadena más larga: " + maxChainLength);
        System.out.println("╚════════════════════════════════════╝");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HashTable {\n");
        
        for (int i = 0; i < capacity; i++) {
            HashNode node = table[i];
            if (node != null) {
                sb.append("  [").append(i).append("]: ");
                while (node != null) {
                    sb.append(node.key).append("=").append(node.value.getTitle());
                    if (node.next != null) {
                        sb.append(" -> ");
                    }
                    node = node.next;
                }
                sb.append("\n");
            }
        }
        
        sb.append("}");
        return sb.toString();
    }
}