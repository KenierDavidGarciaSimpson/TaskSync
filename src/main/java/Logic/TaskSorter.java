/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import Escructura.DoublyLinkedList;
import model.Task;

/**
 *
 * @author kenie
 */
public class TaskSorter {
    
    /**
     * Ordena tareas por prioridad usando Bubble Sort
     * Orden: Alta > Media > Baja
     */
    public static void sortByPriority(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < tasks.size() - 1; i++) {
                Task task1 = (Task) tasks.get(i);
                Task task2 = (Task) tasks.get(i + 1);
                
                if (comparePriority(task1.getPriority(), task2.getPriority()) > 0) {
                    // Intercambiar
                    tasks.set(i, task2);
                    tasks.set(i + 1, task1);
                    swapped = true;
                }
            }
        } while (swapped);
    }
    
    /**
     * Compara dos prioridades
     * Retorna: negativo si p1 < p2, 0 si iguales, positivo si p1 > p2
     */
    private static int comparePriority(String p1, String p2) {
        int value1 = priorityValue(p1);
        int value2 = priorityValue(p2);
        return value2 - value1; // Orden descendente
    }
    
    /**
     * Convierte prioridad a valor numérico
     */
    private static int priorityValue(String priority) {
        switch (priority.toLowerCase()) {
            case "alta": return 3;
            case "media": return 2;
            case "baja": return 1;
            default: return 0;
        }
    }
    
    /**
     * Ordena tareas por fecha de creación usando Selection Sort
     * Más recientes primero
     */
    public static void sortByDate(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        for (int i = 0; i < tasks.size() - 1; i++) {
            int maxIndex = i;
            Task maxTask = (Task) tasks.get(i);
            
            for (int j = i + 1; j < tasks.size(); j++) {
                Task currentTask = (Task) tasks.get(j);
                if (currentTask.getCreatedAt().isAfter(maxTask.getCreatedAt())) {
                    maxIndex = j;
                    maxTask = currentTask;
                }
            }
            
            if (maxIndex != i) {
                Task temp = (Task) tasks.get(i);
                tasks.set(i, maxTask);
                tasks.set(maxIndex, temp);
            }
        }
    }
    
    /**
     * Ordena tareas por título usando Insertion Sort
     * Orden alfabético
     */
    public static void sortByTitle(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        for (int i = 1; i < tasks.size(); i++) {
            Task key = (Task) tasks.get(i);
            int j = i - 1;
            
            while (j >= 0) {
                Task current = (Task) tasks.get(j);
                if (current.getTitle().compareToIgnoreCase(key.getTitle()) <= 0) {
                    break;
                }
                tasks.set(j + 1, current);
                j--;
            }
            
            tasks.set(j + 1, key);
        }
    }
    
    /**
     * Ordena tareas por estado (completadas al final)
     */
    public static void sortByStatus(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < tasks.size() - 1; i++) {
                Task task1 = (Task) tasks.get(i);
                Task task2 = (Task) tasks.get(i + 1);
                
                // Pendientes primero, completadas después
                if (task1.isCompleted() && !task2.isCompleted()) {
                    tasks.set(i, task2);
                    tasks.set(i + 1, task1);
                    swapped = true;
                }
            }
        } while (swapped);
    }
    
    /**
     * Ordena tareas por categoría alfabéticamente
     */
    public static void sortByCategory(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        for (int i = 1; i < tasks.size(); i++) {
            Task key = (Task) tasks.get(i);
            int j = i - 1;
            
            while (j >= 0) {
                Task current = (Task) tasks.get(j);
                if (current.getCategory().compareToIgnoreCase(key.getCategory()) <= 0) {
                    break;
                }
                tasks.set(j + 1, current);
                j--;
            }
            
            tasks.set(j + 1, key);
        }
    }
    
    /**
     * Ordena tareas por ID ascendente
     */
    public static void sortById(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        for (int i = 1; i < tasks.size(); i++) {
            Task key = (Task) tasks.get(i);
            int j = i - 1;
            
            while (j >= 0) {
                Task current = (Task) tasks.get(j);
                if (current.getId() <= key.getId()) {
                    break;
                }
                tasks.set(j + 1, current);
                j--;
            }
            
            tasks.set(j + 1, key);
        }
    }
    
    /**
     * Ordena tareas por cantidad de subtareas (descendente)
     */
    public static void sortBySubtaskCount(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < tasks.size() - 1; i++) {
                Task task1 = (Task) tasks.get(i);
                Task task2 = (Task) tasks.get(i + 1);
                
                if (task1.getSubtaskCount() < task2.getSubtaskCount()) {
                    tasks.set(i, task2);
                    tasks.set(i + 1, task1);
                    swapped = true;
                }
            }
        } while (swapped);
    }
    
    /**
     * Ordena tareas por progreso de subtareas (descendente)
     */
    public static void sortByProgress(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < tasks.size() - 1; i++) {
                Task task1 = (Task) tasks.get(i);
                Task task2 = (Task) tasks.get(i + 1);
                
                if (task1.getSubtaskProgress() < task2.getSubtaskProgress()) {
                    tasks.set(i, task2);
                    tasks.set(i + 1, task1);
                    swapped = true;
                }
            }
        } while (swapped);
    }
    
    /**
     * Ordenamiento compuesto: Prioridad + Fecha
     */
    public static void sortByPriorityAndDate(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < tasks.size() - 1; i++) {
                Task task1 = (Task) tasks.get(i);
                Task task2 = (Task) tasks.get(i + 1);
                
                int priorityCompare = comparePriority(task1.getPriority(), task2.getPriority());
                
                // Si tienen la misma prioridad, ordenar por fecha
                if (priorityCompare == 0) {
                    if (task1.getCreatedAt().isBefore(task2.getCreatedAt())) {
                        tasks.set(i, task2);
                        tasks.set(i + 1, task1);
                        swapped = true;
                    }
                } else if (priorityCompare > 0) {
                    tasks.set(i, task2);
                    tasks.set(i + 1, task1);
                    swapped = true;
                }
            }
        } while (swapped);
    }
    
    /**
     * Invierte el orden de la lista
     */
    public static void reverse(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        int left = 0;
        int right = tasks.size() - 1;
        
        while (left < right) {
            Task temp = (Task) tasks.get(left);
            tasks.set(left, tasks.get(right));
            tasks.set(right, temp);
            left++;
            right--;
        }
    }
    
    /**
     * Mezcla aleatoriamente la lista (shuffle)
     */
    public static void shuffle(DoublyLinkedList tasks) {
        if (tasks.size() <= 1) {
            return;
        }
        
        java.util.Random random = new java.util.Random();
        
        for (int i = tasks.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            
            Task temp = (Task) tasks.get(i);
            tasks.set(i, tasks.get(j));
            tasks.set(j, temp);
        }
    }
}
