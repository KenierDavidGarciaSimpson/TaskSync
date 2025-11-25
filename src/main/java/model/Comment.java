/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kenie
 */
public class Comment {
    private static int nextId = 1;
    
    private int id;
    private String author;
    private String text;
    private LocalDateTime timestamp;
    
    /**
     * Constructor
     */
    public Comment(String author, String text) {
        this.id = nextId++;
        this.author = author;
        this.text = text;
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * Constructor con ID (para cargar desde archivo)
     */
    public Comment(int id, String author, String text, LocalDateTime timestamp) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.timestamp = timestamp;
        
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
    
    // ============= GETTERS Y SETTERS =============
    
    public int getId() {
        return id;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    /**
     * Retorna la fecha formateada
     */
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return timestamp.format(formatter);
    }
    
    /**
     * Verifica si el comentario fue hecho por un autor específico
     */
    public boolean isAuthor(String authorName) {
        return this.author.equalsIgnoreCase(authorName);
    }
    
    /**
     * Verifica si el comentario contiene un texto específico
     */
    public boolean containsText(String searchText) {
        return this.text.toLowerCase().contains(searchText.toLowerCase());
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: %s", 
            getFormattedTimestamp(), author, text);
    }
    
    /**
     * Retorna una representación detallada del comentario
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\033[0;36m").append("┌─────────────────────────────────┐\n");
        sb.append("│ Comentario #").append(id).append("\n");
        sb.append("├─────────────────────────────────┤\n");
        sb.append("\033[0m");
        sb.append("│ Autor: ").append("\033[0;33m").append(author).append("\033[0m").append("\n");
        sb.append("│ Fecha: ").append(getFormattedTimestamp()).append("\n");
        sb.append("│ Texto: ").append(text).append("\n");
        sb.append("\033[0;36m").append("└─────────────────────────────────┘").append("\033[0m");
        return sb.toString();
    }
    
    /**
     * Convierte el comentario a formato CSV
     */
    public String toCSV() {
        return id + "," + author + "," + text.replace(",", ";") + "," + timestamp.toString();
    }
    
    /**
     * Crea un comentario desde formato CSV
     */
    public static Comment fromCSV(String csv) {
        String[] parts = csv.split(",", 4);
        if (parts.length < 4) {
            return null;
        }
        
        try {
            int id = Integer.parseInt(parts[0]);
            String author = parts[1];
            String text = parts[2].replace(";", ",");
            LocalDateTime timestamp = LocalDateTime.parse(parts[3]);
            
            return new Comment(id, author, text, timestamp);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Comment comment = (Comment) obj;
        return id == comment.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
    

