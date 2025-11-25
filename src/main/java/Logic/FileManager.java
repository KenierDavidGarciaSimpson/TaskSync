/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import Escructura.DoublyLinkedList;
import model.Task;
import model.Subtask;
import model.Comment;
import java.io.*;
import java.time.LocalDateTime;

/**
 *
 * @author kenie
 */
public class FileManager {
    
    private static final String TASKS_FILE = "tasks.csv";
    private static final String SEPARATOR = "|";
    
    /**
     * Guarda todas las tareas en un archivo
     */
    public static boolean saveTasks(DoublyLinkedList tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TASKS_FILE))) {
            // Escribir encabezado
            writer.println("# TaskSync Data File");
            writer.println("# Format: id|title|description|category|priority|completed|createdAt|updatedAt|completedAt");
            writer.println("# Subtasks: STK|subtaskId|description|completed|createdAt|completedAt");
            writer.println("# Comments: CMT|commentId|author|text|timestamp");
            writer.println("---TASKS---");
            
            // Escribir cada tarea
            for (int i = 0; i < tasks.size(); i++) {
                Task task = (Task) tasks.get(i);
                
                // Escribir datos principales de la tarea
                writer.println(taskToCSV(task));
                
                // Escribir subtareas
                DoublyLinkedList subtasks = task.getSubtasks();
                for (int j = 0; j < subtasks.size(); j++) {
                    Subtask st = (Subtask) subtasks.get(j);
                    writer.println("STK" + SEPARATOR + subtaskToCSV(st));
                }
                
                // Escribir comentarios
                DoublyLinkedList comments = task.getComments();
                for (int j = 0; j < comments.size(); j++) {
                    Comment c = (Comment) comments.get(j);
                    writer.println("CMT" + SEPARATOR + commentToCSV(c));
                }
                
                writer.println("---END_TASK---");
            }
            
            System.out.println("\033[0;32m" + "Datos guardados en: " + TASKS_FILE + "\033[0m");
            return true;
            
        } catch (IOException e) {
            System.err.println("\033[0;31m" + "Error al guardar: " + e.getMessage() + "\033[0m");
            return false;
        }
    }
    
    /**
     * Carga todas las tareas desde un archivo
     */
    public static DoublyLinkedList loadTasks() {
        DoublyLinkedList tasks = new DoublyLinkedList();
        File file = new File(TASKS_FILE);
        
        if (!file.exists()) {
            System.out.println("\033[0;33m" + "No se encontró archivo de datos. Iniciando con lista vacía." + "\033[0m");
            return tasks;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Task currentTask = null;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Ignorar comentarios y líneas vacías
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                
                // Marcadores de sección
                if (line.equals("---TASKS---")) {
                    continue;
                }
                
                if (line.equals("---END_TASK---")) {
                    if (currentTask != null) {
                        tasks.add(currentTask);
                        currentTask = null;
                    }
                    continue;
                }
                
                // Procesar subtareas
                if (line.startsWith("STK" + SEPARATOR)) {
                    if (currentTask != null) {
                        String data = line.substring(4); // Remover "STK|"
                        Subtask subtask = subtaskFromCSV(data);
                        if (subtask != null) {
                            currentTask.addSubtask(subtask);
                        }
                    }
                    continue;
                }
                
                // Procesar comentarios
                if (line.startsWith("CMT" + SEPARATOR)) {
                    if (currentTask != null) {
                        String data = line.substring(4); // Remover "CMT|"
                        Comment comment = commentFromCSV(data);
                        if (comment != null) {
                            currentTask.addComment(comment);
                        }
                    }
                    continue;
                }
                
                // Procesar tarea
                Task task = taskFromCSV(line);
                if (task != null) {
                    currentTask = task;
                }
            }
            
            System.out.println("\033[0;32m" + "Cargadas " + tasks.size() + " tareas desde: " + TASKS_FILE + "\033[0m");
            return tasks;
            
        } catch (IOException e) {
            System.err.println("\033[0;31m" + "Error al cargar: " + e.getMessage() + "\033[0m");
            return tasks;
        }
    }
    
    /**
     * Convierte una tarea a formato CSV
     */
    private static String taskToCSV(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(SEPARATOR);
        sb.append(escapeCSV(task.getTitle())).append(SEPARATOR);
        sb.append(escapeCSV(task.getDescription())).append(SEPARATOR);
        sb.append(escapeCSV(task.getCategory())).append(SEPARATOR);
        sb.append(task.getPriority()).append(SEPARATOR);
        sb.append(task.isCompleted()).append(SEPARATOR);
        sb.append(task.getCreatedAt().toString()).append(SEPARATOR);
        sb.append(task.getUpdatedAt().toString()).append(SEPARATOR);
        sb.append(task.getCompletedAt() != null ? task.getCompletedAt().toString() : "null");
        return sb.toString();
    }
    
    /**
     * Crea una tarea desde formato CSV
     */
    private static Task taskFromCSV(String csv) {
        try {
            String[] parts = csv.split("\\" + SEPARATOR);
            if (parts.length < 9) {
                return null;
            }
            
            int id = Integer.parseInt(parts[0]);
            String title = unescapeCSV(parts[1]);
            String description = unescapeCSV(parts[2]);
            String category = unescapeCSV(parts[3]);
            String priority = parts[4];
            boolean completed = Boolean.parseBoolean(parts[5]);
            LocalDateTime createdAt = LocalDateTime.parse(parts[6]);
            LocalDateTime updatedAt = LocalDateTime.parse(parts[7]);
            LocalDateTime completedAt = parts[8].equals("null") ? null : LocalDateTime.parse(parts[8]);
            
            return new Task(id, title, description, category, priority, 
                          completed, createdAt, updatedAt, completedAt);
                          
        } catch (Exception e) {
            System.err.println("\033[0;31m" + "Error al parsear tarea: " + e.getMessage() + "\033[0m");
            return null;
        }
    }
    
    /**
     * Convierte una subtarea a formato CSV
     */
    private static String subtaskToCSV(Subtask subtask) {
        StringBuilder sb = new StringBuilder();
        sb.append(subtask.getId()).append(SEPARATOR);
        sb.append(escapeCSV(subtask.getDescription())).append(SEPARATOR);
        sb.append(subtask.isCompleted()).append(SEPARATOR);
        sb.append(subtask.getCreatedAt().toString()).append(SEPARATOR);
        sb.append(subtask.getCompletedAt() != null ? subtask.getCompletedAt().toString() : "null");
        return sb.toString();
    }
    
    /**
     * Crea una subtarea desde formato CSV
     */
    private static Subtask subtaskFromCSV(String csv) {
        try {
            String[] parts = csv.split("\\" + SEPARATOR);
            if (parts.length < 5) {
                return null;
            }
            
            int id = Integer.parseInt(parts[0]);
            String description = unescapeCSV(parts[1]);
            boolean completed = Boolean.parseBoolean(parts[2]);
            LocalDateTime createdAt = LocalDateTime.parse(parts[3]);
            LocalDateTime completedAt = parts[4].equals("null") ? null : LocalDateTime.parse(parts[4]);
            
            return new Subtask(id, description, completed, createdAt, completedAt);
            
        } catch (Exception e) {
            System.err.println("\033[0;31m" + "Error al parsear subtarea: " + e.getMessage() + "\033[0m");
            return null;
        }
    }
    
    /**
     * Convierte un comentario a formato CSV
     */
    private static String commentToCSV(Comment comment) {
        StringBuilder sb = new StringBuilder();
        sb.append(comment.getId()).append(SEPARATOR);
        sb.append(escapeCSV(comment.getAuthor())).append(SEPARATOR);
        sb.append(escapeCSV(comment.getText())).append(SEPARATOR);
        sb.append(comment.getTimestamp().toString());
        return sb.toString();
    }
    
    /**
     * Crea un comentario desde formato CSV
     */
    private static Comment commentFromCSV(String csv) {
        try {
            String[] parts = csv.split("\\" + SEPARATOR);
            if (parts.length < 4) {
                return null;
            }
            
            int id = Integer.parseInt(parts[0]);
            String author = unescapeCSV(parts[1]);
            String text = unescapeCSV(parts[2]);
            LocalDateTime timestamp = LocalDateTime.parse(parts[3]);
            
            return new Comment(id, author, text, timestamp);
            
        } catch (Exception e) {
            System.err.println("\033[0;31m" + "Error al parsear comentario: " + e.getMessage() + "\033[0m");
            return null;
        }
    }
    
    /**
     * Escapa caracteres especiales en CSV
     */
    private static String escapeCSV(String text) {
        if (text == null) {
            return "";
        }
        return text.replace(SEPARATOR, "\\|")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }
    
    /**
     * Desescapa caracteres especiales en CSV
     */
    private static String unescapeCSV(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\|", SEPARATOR)
                   .replace("\\n", "\n")
                   .replace("\\r", "\r");
    }
    
    /**
     * Crea una copia de respaldo del archivo
     */
    public static boolean createBackup() {
        File original = new File(TASKS_FILE);
        if (!original.exists()) {
            return false;
        }
        
        String timestamp = LocalDateTime.now().toString().replace(":", "-");
        File backup = new File("backup_" + timestamp + ".csv");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(original));
             PrintWriter writer = new PrintWriter(new FileWriter(backup))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
            
            System.out.println("\033[0;32m" + "Respaldo creado: " + backup.getName() + "\033[0m");
            return true;
            
        } catch (IOException e) {
            System.err.println("\033[0;31m" + "Error al crear respaldo: " + e.getMessage() + "\033[0m");
            return false;
        }
    }
    
    /**
     * Verifica si existe el archivo de datos
     */
    public static boolean fileExists() {
        return new File(TASKS_FILE).exists();
    }
    
    /**
     * Elimina el archivo de datos
     */
    public static boolean deleteFile() {
        File file = new File(TASKS_FILE);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
