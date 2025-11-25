/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Task;

/**
 *
 * @author kenie
 */
public class Subtask {

    private static int nextId = 1;
    
    private int id;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    
    /**
     * Constructor
     */
    public Subtask(String description) {
        this.id = nextId++;
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.completedAt = null;
    }
    
    /**
     * Constructor completo (para cargar desde archivo)
     */
    public Subtask(int id, String description, boolean completed, 
                   LocalDateTime createdAt, LocalDateTime completedAt) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
    
    // ============= GETTERS Y SETTERS =============
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed && completedAt == null) {
            this.completedAt = LocalDateTime.now();
        } else if (!completed) {
            this.completedAt = null;
        }
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    /**
     * Marca la subtarea como completada
     */
    public void complete() {
        this.completed = true;
        this.completedAt = LocalDateTime.now();
    }
    
    /**
     * Reabre la subtarea
     */
    public void reopen() {
        this.completed = false;
        this.completedAt = null;
    }
    
    /**
     * Alterna el estado de completado
     */
    public void toggleCompleted() {
        if (completed) {
            reopen();
        } else {
            complete();
        }
    }
    
    /**
     * Retorna el estado como símbolo
     */
    public String getStatusSymbol() {
        return completed ? "[X]" : "[ ]";
    }
    
    /**
     * Retorna el estado como texto con color
     */
    public String getColoredStatus() {
        if (completed) {
            return "\033[0;32m" + "Completada" + "\033[0m";
        } else {
            return "\033[0;33m" + "Pendiente" + "\033[0m";
        }
    }
    
    /**
     * Retorna la fecha de creación formateada
     */
    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return createdAt.format(formatter);
    }
    
    /**
     * Retorna la fecha de completado formateada
     */
    public String getFormattedCompletedAt() {
        if (completedAt == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return completedAt.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", 
            getStatusSymbol(), description, 
            completed ? "Completada" : "Pendiente");
    }
    
    /**
     * Retorna una representación detallada
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\033[0;34m").append("  ├─ Subtarea #").append(id).append("\033[0m").append("\n");
        sb.append("  │  Descripción: ").append(description).append("\n");
        sb.append("  │  Estado: ").append(getColoredStatus()).append("\n");
        sb.append("  │  Creada: ").append(getFormattedCreatedAt()).append("\n");
        if (completed) {
            sb.append("  │  Completada: ").append(getFormattedCompletedAt()).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Convierte la subtarea a formato CSV
     */
    public String toCSV() {
        return id + "," + 
               description.replace(",", ";") + "," + 
               completed + "," + 
               createdAt.toString() + "," + 
               (completedAt != null ? completedAt.toString() : "null");
    }
    
    /**
     * Crea una subtarea desde formato CSV
     */
    public static Subtask fromCSV(String csv) {
        String[] parts = csv.split(",", 5);
        if (parts.length < 5) {
            return null;
        }
        
        try {
            int id = Integer.parseInt(parts[0]);
            String description = parts[1].replace(";", ",");
            boolean completed = Boolean.parseBoolean(parts[2]);
            LocalDateTime createdAt = LocalDateTime.parse(parts[3]);
            LocalDateTime completedAt = parts[4].equals("null") ? null : LocalDateTime.parse(parts[4]);
            
            return new Subtask(id, description, completed, createdAt, completedAt);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Subtask subtask = (Subtask) obj;
        return id == subtask.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
