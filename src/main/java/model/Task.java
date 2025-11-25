/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Escructura.DoublyLinkedList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kenie
 */
public class Task {

  private static int nextId = 1;
    
    private int id;
    private String title;
    private String description;
    private String category;
    private String priority; // Alta, Media, Baja
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    
    // Subtareas y comentarios usando listas enlazadas
    private DoublyLinkedList subtasks;
    private DoublyLinkedList comments;
    
    /**
     * Constructor principal
     */
    public Task(String title, String description, String category, String priority) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.completedAt = null;
        this.subtasks = new DoublyLinkedList();
        this.comments = new DoublyLinkedList();
    }
    
    /**
     * Constructor completo (para cargar desde archivo)
     */
    public Task(int id, String title, String description, String category, 
                String priority, boolean completed, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime completedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
        this.subtasks = new DoublyLinkedList();
        this.comments = new DoublyLinkedList();
        
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
    
    // ============= GETTERS Y SETTERS =============
    
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.updatedAt = LocalDateTime.now();
        if (completed) {
            this.completedAt = LocalDateTime.now();
        } else {
            this.completedAt = null;
        }
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    // ============= MÉTODOS DE SUBTAREAS =============
    
    public void addSubtask(String description) {
        Subtask subtask = new Subtask(description);
        subtasks.add(subtask);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void removeSubtask(int subtaskId) {
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask st = (Subtask) subtasks.get(i);
            if (st.getId() == subtaskId) {
                subtasks.remove(i);
                this.updatedAt = LocalDateTime.now();
                return;
            }
        }
    }
    
    public void completeSubtask(int subtaskId) {
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask st = (Subtask) subtasks.get(i);
            if (st.getId() == subtaskId) {
                st.complete();
                this.updatedAt = LocalDateTime.now();
                return;
            }
        }
    }
    
    public Subtask getSubtask(int subtaskId) {
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask st = (Subtask) subtasks.get(i);
            if (st.getId() == subtaskId) {
                return st;
            }
        }
        return null;
    }
    
    public int getSubtaskCount() {
        return subtasks.size();
    }
    
    public int getCompletedSubtaskCount() {
        int count = 0;
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask st = (Subtask) subtasks.get(i);
            if (st.isCompleted()) {
                count++;
            }
        }
        return count;
    }
    
    public double getSubtaskProgress() {
        if (subtasks.isEmpty()) {
            return 0.0;
        }
        return (double) getCompletedSubtaskCount() / getSubtaskCount() * 100;
    }
    
    public void listSubtasks() {
        if (subtasks.isEmpty()) {
            System.out.println("\033[0;33m" + "No hay subtareas" + "\033[0m");
            return;
        }
        
        System.out.println("\033[0;36m" + "\nSUBTAREAS:" + "\033[0m");
        for (int i = 0; i < subtasks.size(); i++) {
            Subtask st = (Subtask) subtasks.get(i);
            System.out.println(st.toDetailedString());
        }
        System.out.println("\033[0;34m" + "Progreso: " + 
            String.format("%.1f%%", getSubtaskProgress()) + "\033[0m");
    }
    
    // ============= MÉTODOS DE COMENTARIOS =============
    
    public void addComment(String author, String text) {
        Comment comment = new Comment(author, text);
        comments.add(comment);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void addComment(Comment comment) {
        comments.add(comment);
        this.updatedAt = LocalDateTime.now();
    }
    
    public void removeComment(int commentId) {
        for (int i = 0; i < comments.size(); i++) {
            Comment c = (Comment) comments.get(i);
            if (c.getId() == commentId) {
                comments.remove(i);
                this.updatedAt = LocalDateTime.now();
                return;
            }
        }
    }
    
    public Comment getComment(int commentId) {
        for (int i = 0; i < comments.size(); i++) {
            Comment c = (Comment) comments.get(i);
            if (c.getId() == commentId) {
                return c;
            }
        }
        return null;
    }
    
    public int getCommentCount() {
        return comments.size();
    }
    
    public void listComments() {
        if (comments.isEmpty()) {
            System.out.println("\033[0;33m" + "No hay comentarios" + "\033[0m");
            return;
        }
        
        System.out.println("\033[0;36m" + "\nCOMENTARIOS:" + "\033[0m");
        for (int i = 0; i < comments.size(); i++) {
            Comment c = (Comment) comments.get(i);
            System.out.println(c.toDetailedString());
        }
    }
    
    // ============= MÉTODOS AUXILIARES =============
    
    public String getPriorityColor() {
        switch (priority.toLowerCase()) {
            case "alta": return "\033[0;31m";
            case "media": return "\033[0;33m";
            case "baja": return "\033[0;32m";
            default: return "\033[0m";
        }
    }
    
    public String getStatusSymbol() {
        return completed ? "[X]" : "[ ]";
    }
    
    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return createdAt.format(formatter);
    }
    
    public String getFormattedUpdatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return updatedAt.format(formatter);
    }
    
    public String getFormattedCompletedAt() {
        if (completedAt == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return completedAt.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("[%d] %s %s - %s%s%s (%s)", 
            id, getStatusSymbol(), title, 
            getPriorityColor(), priority, "\033[0m", category);
    }
    
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\033[0;36m").append("\n╔════════════════════════════════════════════════╗\n");
        sb.append("║  TAREA #").append(id).append(" - ").append(title).append("\n");
        sb.append("╠════════════════════════════════════════════════╣\n");
        sb.append("\033[0m");
        sb.append("║ Descripción: ").append(description).append("\n");
        sb.append("║ Categoría: ").append("\033[0;34m").append(category).append("\033[0m").append("\n");
        sb.append("║ Prioridad: ").append(getPriorityColor()).append(priority).append("\033[0m").append("\n");
        sb.append("║ Estado: ").append(completed ? "\033[0;32m" + "Completada" : "\033[0;33m" + "Pendiente").append("\033[0m").append("\n");
        sb.append("║ Creada: ").append(getFormattedCreatedAt()).append("\n");
        sb.append("║ Actualizada: ").append(getFormattedUpdatedAt()).append("\n");
        if (completed) {
            sb.append("║ Completada: ").append(getFormattedCompletedAt()).append("\n");
        }
        sb.append("║ Subtareas: ").append(getCompletedSubtaskCount()).append("/").append(getSubtaskCount());
        if (getSubtaskCount() > 0) {
            sb.append(" (").append(String.format("%.1f%%", getSubtaskProgress())).append(")");
        }
        sb.append("\n");
        sb.append("║ Comentarios: ").append(getCommentCount()).append("\n");
        sb.append("\033[0;36m").append("╚════════════════════════════════════════════════╝").append("\033[0m");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Task task = (Task) obj;
        return id == task.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    
    public DoublyLinkedList getSubtasks() {
        return subtasks;
    }
    
    public DoublyLinkedList getComments() {
        return comments;
    }
}
