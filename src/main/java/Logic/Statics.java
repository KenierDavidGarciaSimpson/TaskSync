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
public class Statics {
    public static class Statistics {
    private DoublyLinkedList tasks;
    
    public Statistics(DoublyLinkedList tasks) {
        this.tasks = tasks;
    }
    
    
   
    public int getTotalTasks() {
        return tasks.size();
    }
    
   
    public int getCompletedTasks() {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.isCompleted()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Cuenta tareas pendientes
     */
    public int getPendingTasks() {
        return getTotalTasks() - getCompletedTasks();
    }
    
    /**
     * Calcula el porcentaje de completado
     */
    public double getCompletionPercentage() {
        if (getTotalTasks() == 0) {
            return 0.0;
        }
        return (double) getCompletedTasks() / getTotalTasks() * 100;
    }
    
    /**
     * Cuenta tareas por prioridad
     */
    public int getTasksByPriority(String priority) {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getPriority().equalsIgnoreCase(priority)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Cuenta tareas por categoría
     */
    public int getTasksByCategory(String category) {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getCategory().equalsIgnoreCase(category)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Obtiene todas las categorías únicas
     */
    public String[] getUniqueCategories() {
        DoublyLinkedList categoriesList = new DoublyLinkedList();
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            String category = task.getCategory();
            
            boolean exists = false;
            for (int j = 0; j < categoriesList.size(); j++) {
                if (categoriesList.get(j).equals(category)) {
                    exists = true;
                    break;
                }
            }
            
            if (!exists) {
                categoriesList.add(category);
            }
        }
        
        Object[] categoriesObj = categoriesList.toArray();
        String[] categories = new String[categoriesObj.length];
        for (int i = 0; i < categoriesObj.length; i++) {
            categories[i] = (String) categoriesObj[i];
        }
        
        return categories;
    }
    
    /**
     * Cuenta el total de subtareas
     */
    public int getTotalSubtasks() {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            count += task.getSubtaskCount();
        }
        return count;
    }
    
    /**
     * Cuenta subtareas completadas
     */
    public int getCompletedSubtasks() {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            count += task.getCompletedSubtaskCount();
        }
        return count;
    }
    
    /**
     * Cuenta el total de comentarios
     */
    public int getTotalComments() {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            count += task.getCommentCount();
        }
        return count;
    }
    
    /**
     * Obtiene la tarea más reciente
     */
    public Task getMostRecentTask() {
        if (tasks.isEmpty()) {
            return null;
        }
        
        Task mostRecent = (Task) tasks.get(0);
        for (int i = 1; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getCreatedAt().isAfter(mostRecent.getCreatedAt())) {
                mostRecent = task;
            }
        }
        
        return mostRecent;
    }
    
    /**
     * Obtiene la tarea más antigua
     */
    public Task getOldestTask() {
        if (tasks.isEmpty()) {
            return null;
        }
        
        Task oldest = (Task) tasks.get(0);
        for (int i = 1; i < tasks.size(); i++) {
            Task task = (Task) tasks.get(i);
            if (task.getCreatedAt().isBefore(oldest.getCreatedAt())) {
                oldest = task;
            }
        }
        
        return oldest;
    }
    
    /**
     * Genera un reporte de categorías
     */
    public String getCategoryReport() {
        String[] categories = getUniqueCategories();
        StringBuilder sb = new StringBuilder();
        
        sb.append("\033[0;36m").append("╔══════════════════════════════════╗\n");
        sb.append("║  REPORTE POR CATEGORÍAS          ║\n");
        sb.append("╠══════════════════════════════════╣\n");
        sb.append("\033[0m");
        
        for (String category : categories) {
            int total = getTasksByCategory(category);
            int completed = 0;
            
            for (int i = 0; i < tasks.size(); i++) {
                Task task = (Task) tasks.get(i);
                if (task.getCategory().equalsIgnoreCase(category) && task.isCompleted()) {
                    completed++;
                }
            }
            
            double percentage = total > 0 ? (double) completed / total * 100 : 0;
            
            sb.append(String.format("║ %s: %d tareas (%.1f%% completadas)\n", 
                category, total, percentage));
        }
        
        sb.append("\033[0;36m").append("╚══════════════════════════════════╝\n");
        sb.append("\033[0m");
        
        return sb.toString();
    }
    
    /**
     * Genera un reporte de prioridades
     */
    public String getPriorityReport() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\033[0;36m").append("╔══════════════════════════════════╗\n");
        sb.append("║  REPORTE POR PRIORIDAD           ║\n");
        sb.append("╠══════════════════════════════════╣\n");
        sb.append("\033[0m");
        
        String[] priorities = {"Alta", "Media", "Baja"};
        
        for (String priority : priorities) {
            int total = getTasksByPriority(priority);
            int completed = 0;
            
            for (int i = 0; i < tasks.size(); i++) {
                Task task = (Task) tasks.get(i);
                if (task.getPriority().equalsIgnoreCase(priority) && task.isCompleted()) {
                    completed++;
                }
            }
            
            int pending = total - completed;
            
            String color = priority.equals("Alta") ? "\033[0;31m" : 
                          priority.equals("Media") ? "\033[0;33m" : "\033[0;32m";
            
            sb.append(String.format("║ %s%s%s: %d total (%d pendientes, %d completadas)\n", 
                color, priority, "\033[0m", total, pending, completed));
        }
        
        sb.append("\033[0;36m").append("╚══════════════════════════════════╝\n");
        sb.append("\033[0m");
        
        return sb.toString();
    }
    
    /**
     * Muestra todas las estadísticas
     */
    public void displayStatistics() {
        System.out.println("\033[0;36m" + "\n╔════════════════════════════════════════════════╗");
        System.out.println("║         ESTADÍSTICAS DEL SISTEMA            ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("\033[0m");
        
        System.out.println("\033[1m" + "TAREAS:" + "\033[0m");
        System.out.println("  • Total de tareas: " + getTotalTasks());
        System.out.println("  • Tareas completadas: " + "\033[0;32m" + getCompletedTasks() + "\033[0m");
        System.out.println("  • Tareas pendientes: " + "\033[0;33m" + getPendingTasks() + "\033[0m");
        System.out.println("  • Progreso general: " + "\033[0;34m" + 
            String.format("%.1f%%", getCompletionPercentage()) + "\033[0m");
        
        System.out.println("\033[1m" + "\nPRIORIDADES:" + "\033[0m");
        System.out.println("  • Alta: " + "\033[0;31m" + getTasksByPriority("Alta") + "\033[0m");
        System.out.println("  • Media: " + "\033[0;33m" + getTasksByPriority("Media") + "\033[0m");
        System.out.println("  • Baja: " + "\033[0;32m" + getTasksByPriority("Baja") + "\033[0m");
        
        System.out.println("\033[1m" + "\nSUBTAREAS:" + "\033[0m");
        System.out.println("  • Total de subtareas: " + getTotalSubtasks());
        System.out.println("  • Subtareas completadas: " + "\033[0;32m" + getCompletedSubtasks() + "\033[0m");
        
        System.out.println("\033[1m" + "\nCOMENTARIOS:" + "\033[0m");
        System.out.println("  • Total de comentarios: " + getTotalComments());
        
        System.out.println("\033[1m" + "\nCATEGORÍAS:" + "\033[0m");
        String[] categories = getUniqueCategories();
        System.out.println("  • Categorías únicas: " + categories.length);
        for (String category : categories) {
            System.out.println("    - " + category + ": " + getTasksByCategory(category) + " tareas");
        }
        
        System.out.println("\033[0;36m" + "\n╚════════════════════════════════════════════════╝");
        System.out.println("\033[0m");
        
        
        System.out.println(getPriorityReport());
        System.out.println(getCategoryReport());
    }
  }
}
    

