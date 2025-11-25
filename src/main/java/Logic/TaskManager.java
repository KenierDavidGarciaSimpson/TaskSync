/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import Escructura.*;
import model.Task;
import Logic.Statics;

/**
 *
 * @author kenie
 */
public class TaskManager {
    private DoublyLinkedList tasks;              // Lista principal de tareas
    private SimpleHashTable taskIndex;           // Índice por título para búsqueda rápida
    private CircularLinList categories;          // Lista circular de categorías
    private Statics.Statistics statistics;      // Estadísticas del sistema
    
    /**
     * Constructor
     */
    public TaskManager() {
        this.tasks = new DoublyLinkedList();
        this.taskIndex = new SimpleHashTable();
        this.categories = new CircularLinList();
        this.statistics = new Statics.Statistics(tasks);
    }
    
    // ============= OPERACIONES CRUD =============
    
    /**
     * Agrega una nueva tarea
     */
    public void addTask(Task task) {
        tasks.add(task);
        taskIndex.put(task.getTitle().toLowerCase(), task);
        addCategoryIfNotExists(task.getCategory());
        
        System.out.println("\033[0;32m" + "Tarea agregada: " + task.getTitle() + "\033[0m");
    }
    
    /**
     * Busca una tarea por ID
     */
    public Task findTaskById(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    
    /**
     * Busca una tarea por título usando la tabla hash
     */
    public Task findTaskByTitle(String title) {
        return taskIndex.get(title.toLowerCase());
    }
    
    /**
     * Actualiza una tarea existente
     */
    public void updateTask(Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getId() == updatedTask.getId()) {
                // Actualizar en la tabla hash si cambió el título
                if (!task.getTitle().equals(updatedTask.getTitle())) {
                    taskIndex.remove(task.getTitle().toLowerCase());
                    taskIndex.put(updatedTask.getTitle().toLowerCase(), updatedTask);
                }
                
                tasks.set(i, updatedTask);
                addCategoryIfNotExists(updatedTask.getCategory());
                System.out.println("\033[0;32m" + "Tarea actualizada" + "\033[0m");
                return;
            }
        }
        System.out.println("\033[0;31m" + "Tarea no encontrada" + "\033[0m");
    }
    
    /**
     * Elimina una tarea por ID
     */
    public boolean deleteTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getId() == id) {
                taskIndex.remove(task.getTitle().toLowerCase());
                tasks.remove(i);
                System.out.println("\033[0;32m" + "Tarea eliminada" + "\033[0m");
                return true;
            }
        }
        System.out.println("\033[0;31m" + "Tarea no encontrada" + "\033[0m");
        return false;
    }
    
    // ============= LISTADOS =============
    
    /**
     * Lista todas las tareas
     */
    public void listAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\033[0;33m" + "\nNo hay tareas registradas" + "\033[0m");
            return;
        }
        
        System.out.println("\033[0;36m" + "\n╔════════════════════════════════════════════════╗");
        System.out.println("║           LISTADO DE TODAS LAS TAREAS         ║");
        System.out.println("╚════════════════════════════════════════════════╝" + "\033[0m");
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            System.out.println(task.toString());
        }
        
        System.out.println("\033[0;34m" + "\nTotal: " + tasks.size() + " tareas" + "\033[0m");
    }
    
    /**
     * Lista tareas por estado
     */
    public void listTasksByStatus(boolean completed) {
        String status = completed ? "COMPLETADAS" : "PENDIENTES";
        System.out.println("\033[0;36m" + "\n╔════════════════════════════════════════════════╗");
        System.out.println("║         TAREAS " + status + "                    ║");
        System.out.println("╚════════════════════════════════════════════════╝" + "\033[0m");
        
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.isCompleted() == completed) {
                System.out.println(task.toString());
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("\033[0;33m" + "No hay tareas " + status.toLowerCase() + "\033[0m");
        } else {
            System.out.println("\033[0;34m" + "\nTotal: " + count + " tareas" + "\033[0m");
        }
    }
    
    /**
     * Lista tareas por categoría
     */
    public void listTasksByCategory(String category) {
        System.out.println("\033[0;36m" + "\n╔════════════════════════════════════════════════╗");
        System.out.println("║         TAREAS DE: " + category.toUpperCase());
        System.out.println("╚════════════════════════════════════════════════╝" + "\033[0m");
        
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getCategory().equalsIgnoreCase(category)) {
                System.out.println(task.toString());
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("\033[0;33m" + "No hay tareas en esta categoría" + "\033[0m");
        } else {
            System.out.println("\033[0;34m" + "\nTotal: " + count + " tareas" + "\033[0m");
        }
    }
    
    /**
     * Lista tareas por prioridad
     */
    public void listTasksByPriority(String priority) {
        System.out.println("\033[0;36m" + "\n╔════════════════════════════════════════════════╗");
        System.out.println("║         TAREAS PRIORIDAD: " + priority.toUpperCase());
        System.out.println("╚════════════════════════════════════════════════╝" + "\033[0m");
        
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getPriority().equalsIgnoreCase(priority)) {
                System.out.println(task.toString());
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("\033[0;33m" + "No hay tareas con esta prioridad" + "\033[0m");
        } else {
            System.out.println("\033[0;34m" + "\nTotal: " + count + " tareas" + "\033[0m");
        }
    }
    
    // ============= CATEGORÍAS =============
    
    /**
     * Agrega una categoría si no existe
     */
    private void addCategoryIfNotExists(String category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }
    
    /**
     * Lista todas las categorías
     */
    public void listCategories() {
        if (categories.isEmpty()) {
            System.out.println("\033[0;33m" + "\nNo hay categorías" + "\033[0m");
            return;
        }
        
        System.out.println("\033[0;36m" + "\n╔════════════════════════════════════════════════╗");
        System.out.println("║           CATEGORÍAS DISPONIBLES              ║");
        System.out.println("╚════════════════════════════════════════════════╝" + "\033[0m");
        
        for (int i = 0; i < categories.size(); i++) {
            String category = (String) categories.get(i);
            int taskCount = countTasksByCategory(category);
            System.out.println("  • " + category + " (" + taskCount + " tareas)");
        }
    }
    
    /**
     * Cuenta tareas por categoría
     */
    private int countTasksByCategory(String category) {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getCategory().equalsIgnoreCase(category)) {
                count++;
            }
        }
        return count;
    }
    
    // ============= ORDENAMIENTO =============
    
    public void sortTasksByPriority() {
        TaskSorter.sortByPriority(tasks);
    }
    
    public void sortTasksByDate() {
        TaskSorter.sortByDate(tasks);
    }
    
    public void sortTasksByTitle() {
        TaskSorter.sortByTitle(tasks);
    }
    
    public void sortTasksByStatus() {
        TaskSorter.sortByStatus(tasks);
    }
    
    public void sortTasksByCategory() {
        TaskSorter.sortByCategory(tasks);
    }
    
    // ============= ESTADÍSTICAS =============
    
    public Statics.Statistics getStatistics() {
        return new Statics.Statistics(tasks);
    }
    
    // ============= PERSISTENCIA =============
    
    /**
     * Guarda todas las tareas en archivo
     */
    public void saveToFile() {
        FileManager.saveTasks(tasks);
    }
    
    /**
     * Carga todas las tareas desde archivo
     */
    public void loadFromFile() {
        DoublyLinkedList loadedTasks = FileManager.loadTasks();
        
        // Reconstruir el índice y categorías
        tasks = loadedTasks;
        taskIndex.clear();
        categories.clear();
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            taskIndex.put(task.getTitle().toLowerCase(), task);
            addCategoryIfNotExists(task.getCategory());
        }
        
        statistics = new Statics.Statistics(tasks);
    }
    
    // ============= BÚSQUEDAS AVANZADAS =============
    
    /**
     * Busca tareas que contengan un texto en el título o descripción
     */
    public DoublyLinkedList searchTasks(String searchText) {
        DoublyLinkedList results = new DoublyLinkedList();
        String search = searchText.toLowerCase();
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getTitle().toLowerCase().contains(search) ||
                task.getDescription().toLowerCase().contains(search)) {
                results.add(task);
            }
        }
        
        return results;
    }
    
    /**
     * Filtra tareas por múltiples criterios
     */
    public DoublyLinkedList filterTasks(String category, String priority, Boolean completed) {
        DoublyLinkedList results = new DoublyLinkedList();
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            boolean matches = true;
            
            if (category != null && !task.getCategory().equalsIgnoreCase(category)) {
                matches = false;
            }
            
            if (priority != null && !task.getPriority().equalsIgnoreCase(priority)) {
                matches = false;
            }
            
            if (completed != null && task.isCompleted() != completed) {
                matches = false;
            }
            
            if (matches) {
                results.add(task);
            }
        }
        
        return results;
    }
    
    // ============= UTILIDADES =============
    
    /**
     * Obtiene el número total de tareas
     */
    public int getTaskCount() {
        return tasks.size();
    }
    
    /**
     * Verifica si hay tareas
     */
    public boolean hasTasks() {
        return !tasks.isEmpty();
    }
    
    /**
     * Limpia todas las tareas
     */
    public void clearAll() {
        tasks.clear();
        taskIndex.clear();
        categories.clear();
        System.out.println("\033[0;33m" + "Todas las tareas han sido eliminadas" + "\033[0m");
    }
    
    /**
     * Obtiene la lista de tareas
     */
    public DoublyLinkedList getTasks() {
        return tasks;
    }
    
    /**
     * Muestra información del sistema
     */
    public void displaySystemInfo() {
        System.out.println("\033[0;36m" + "\n╔════════════════════════════════════════════════╗");
        System.out.println("║         INFORMACIÓN DEL SISTEMA               ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("\033[0m");
        System.out.println("║ Total de tareas: " + tasks.size());
        System.out.println("║ Categorías: " + categories.size());
        System.out.println("║ Tareas en índice: " + taskIndex.size());
        System.out.println("\033[0;36m" + "╚════════════════════════════════════════════════╝");
        System.out.println("\033[0m");
    }
}
