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
public class Action {
     private static int nextId = 1;
    
    private int id;
    private String type;          // CREATE, UPDATE, DELETE, COMPLETE, etc.
    private String description;
    private LocalDateTime timestamp;
    
    /**
     * Constructor
     */
    public Action(String type, String description) {
        this.id = nextId++;
        this.type = type;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * Constructor completo (para cargar desde archivo)
     */
    public Action(int id, String type, String description, LocalDateTime timestamp) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.timestamp = timestamp;
        
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
    
    // ============= GETTERS Y SETTERS =============
    
    public int getId() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    /**
     * Retorna la fecha formateada
     */
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    /**
     * Retorna el tiempo transcurrido desde la acción
     */
    public String getTimeAgo() {
        LocalDateTime now = LocalDateTime.now();
        long minutes = java.time.Duration.between(timestamp, now).toMinutes();
        
        if (minutes < 1) {
            return "Hace un momento";
        } else if (minutes < 60) {
            return "Hace " + minutes + " minuto" + (minutes > 1 ? "s" : "");
        } else {
            long hours = minutes / 60;
            if (hours < 24) {
                return "Hace " + hours + " hora" + (hours > 1 ? "s" : "");
            } else {
                long days = hours / 24;
                return "Hace " + days + " día" + (days > 1 ? "s" : "");
            }
        }
    }
    
    /**
     * Retorna el color según el tipo de acción
     */
    public String getTypeColor() {
        switch (type.toUpperCase()) {
            case "CREATE":
                return "\033[0;32m";
            case "UPDATE":
                return "\033[0;33m";
            case "DELETE":
                return "\033[0;31m";
            case "COMPLETE":
                return "\033[0;34m";
            case "REOPEN":
                return "\033[0;35m";
            default:
                return "\033[0m";
        }
    }
    
    /**
     * Retorna el símbolo según el tipo de acción
     */
    public String getTypeSymbol() {
        switch (type.toUpperCase()) {
            case "CREATE":
                return "[+]";
            case "UPDATE":
                return "[E]";
            case "DELETE":
                return "[D]";
            case "COMPLETE":
                return "[X]";
            case "REOPEN":
                return "[R]";
            default:
                return "•";
        }
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s%s %s%s - %s", 
            getFormattedTimestamp(),
            getTypeColor(),
            getTypeSymbol(),
            type,
            "\033[0m",
            description);
    }
    
    /**
     * Retorna una representación detallada de la acción
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTypeColor()).append("┌─────────────────────────────────┐\n");
        sb.append("│ Acción #").append(id).append(" - ").append(type).append("\n");
        sb.append("├─────────────────────────────────┤\n");
        sb.append("\033[0m");
        sb.append("│ ").append(getTypeSymbol()).append(" ").append(description).append("\n");
        sb.append("│ ").append(getFormattedTimestamp()).append("\n");
        sb.append("│ ").append(getTimeAgo()).append("\n");
        sb.append(getTypeColor()).append("└─────────────────────────────────┘").append("\033[0m");
        return sb.toString();
    }
    
    /**
     * Convierte la acción a formato CSV
     */
    public String toCSV() {
        return id + "," + type + "," + description.replace(",", ";") + "," + timestamp.toString();
    }
    
    /**
     * Crea una acción desde formato CSV
     */
    public static Action fromCSV(String csv) {
        String[] parts = csv.split(",", 4);
        if (parts.length < 4) {
            return null;
        }
        
        try {
            int id = Integer.parseInt(parts[0]);
            String type = parts[1];
            String description = parts[2].replace(";", ",");
            LocalDateTime timestamp = LocalDateTime.parse(parts[3]);
            
            return new Action(id, type, description, timestamp);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Verifica si la acción es de un tipo específico
     */
    public boolean isType(String actionType) {
        return this.type.equalsIgnoreCase(actionType);
    }
    
    /**
     * Verifica si la acción contiene un texto en su descripción
     */
    public boolean containsText(String searchText) {
        return this.description.toLowerCase().contains(searchText.toLowerCase());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Action action = (Action) obj;
        return id == action.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
        
        
    
}
