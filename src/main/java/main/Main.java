/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import java.util.Scanner;

import Escructura.CustomQueue;
import Escructura.CustomStack;
import Logic.Statics;
import Logic.TaskManager;
import model.Action;
import model.Task;

/**
 *
 * @author kenie
 */
public class Main {
    private static TaskManager taskManager;
    private static Scanner scanner;
    private static CustomStack actionHistory;
    private static CustomQueue notificationQueue;
    
    public static void main(String[] args) {
        initialize();
        showWelcome();
        runMainMenu();
    }
    
    
    private static void initialize() {
        scanner = new Scanner(System.in);
        taskManager = new TaskManager();
        actionHistory = new CustomStack();
        notificationQueue = new CustomQueue(100); // Tamaño máximo de 100 notificaciones
        
        taskManager.loadFromFile();
        addNotification("Sistema TaskSync iniciado correctamente");
    }
    
  
    private static void showWelcome() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔═══════════════════════════════════════════════════════╗");
        System.out.println("║              TASKSYNC - GESTOR DE TAREAS           ║");
        System.out.println("║          Sistema con Estructuras de Datos             ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝" + "\033[0m");
        System.out.println("\033[0;33m" + "\n[INFO] Utilizando:");
        System.out.println("  - Listas Doblemente Enlazadas");
        System.out.println("  - Pilas (Historial de Acciones)");
        System.out.println("  - Colas (Notificaciones)");
        System.out.println("  - Listas Circulares (Categorías)");
        System.out.println("  - Tablas Hash (Búsqueda Rápida)" + "\033[0m");
        System.out.println("\nPresiona ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Menú principal del sistema
     */
    private static void runMainMenu() {
        boolean running = true;
        
        while (running) {
            try {
                clearScreen();
            } catch (Exception e) {
                // Si falla, continuar de todas formas
            }
            showNotifications();
            showMainMenu();
            
            int option = readInt("Selecciona una opción: ");
            
            switch (option) {
                case 1:
                    createTaskMenu();
                    break;
                case 2:
                    listTasksMenu();
                    break;
                case 3:
                    searchTaskMenu();
                    break;
                case 4:
                    modifyTaskMenu();
                    break;
                case 5:
                    deleteTaskMenu();
                    break;
                case 6:
                    subtasksMenu();
                    break;
                case 7:
                    commentsMenu();
                    break;
                case 8:
                    categoriesMenu();
                    break;
                case 9:
                    viewHistoryMenu();
                    break;
                case 10:
                    statisticsMenu();
                    break;
                case 11:
                    sortTasksMenu();
                    break;
                case 12:
                    exportImportMenu();
                    break;
                case 13:
                    running = exitProgram();
                    break;
                default:
                    showError("Opción no válida");
                    pause();
            }
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\033[0;36m" + "\n╔════════════════════════════════════════╗");
        System.out.println("║         MENÚ PRINCIPAL - TASKSYNC      ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        System.out.println("\033[0;32m" + "\nGESTIÓN DE TAREAS:");
        System.out.println("\033[0m" + "  1.  Crear nueva tarea");
        System.out.println("  2.  Listar tareas");
        System.out.println("  3.  Buscar tarea");
        System.out.println("  4.  Modificar tarea");
        System.out.println("  5.  Eliminar tarea");
        
        System.out.println("\033[0;34m" + "\nSUBTAREAS Y COMENTARIOS:");
        System.out.println("\033[0m" + "  6.  Gestionar subtareas");
        System.out.println("  7.  Gestionar comentarios");
        
        System.out.println("\033[0;33m" + "\nORGANIZACIÓN:");
        System.out.println("\033[0m" + "  8.  Gestionar categorías");
        System.out.println("  9.  Ver historial de acciones (Pila)");
        System.out.println("  10. Ver estadísticas");
        System.out.println("  11. Ordenar tareas");
        
        System.out.println("\033[0;35m" + "\nDATOS:");
        System.out.println("\033[0m" + "  12. Exportar/Importar");
        System.out.println("  13. Salir\n");
    }
    
    private static void createTaskMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║           CREAR NUEVA TAREA            ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        scanner.nextLine(); // Limpiar buffer
        
        System.out.print("\nTítulo: ");
        String title = scanner.nextLine();
        
        System.out.print("Descripción: ");
        String description = scanner.nextLine();
        
        System.out.print("Categoría: ");
        String category = scanner.nextLine();
        
        System.out.println("\nPrioridad:");
        System.out.println("  1. Alta");
        System.out.println("  2. Media");
        System.out.println("  3. Baja");
        int prioOption = readInt("Selecciona: ");
        
        String priority = "Media";
        switch (prioOption) {
            case 1: priority = "Alta"; break;
            case 2: priority = "Media"; break;
            case 3: priority = "Baja"; break;
        }
        
        Task newTask = new Task(title, description, category, priority);
        taskManager.addTask(newTask);
        
        Action action = new Action("CREATE", "Tarea creada: " + title);
        actionHistory.push(action);
        
        addNotification("Tarea creada: " + title);
        showSuccess("Tarea creada exitosamente con ID: " + newTask.getId());
        pause();
    }
    
    private static void listTasksMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║            LISTAR TAREAS               ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        System.out.println("\n1. Todas las tareas");
        System.out.println("2. Tareas pendientes");
        System.out.println("3. Tareas completadas");
        System.out.println("4. Por categoría");
        System.out.println("5. Por prioridad");
        
        int option = readInt("\nSelecciona: ");
        
        scanner.nextLine(); // Limpiar buffer
        
        switch (option) {
            case 1:
                taskManager.listAllTasks();
                break;
            case 2:
                taskManager.listTasksByStatus(false);
                break;
            case 3:
                taskManager.listTasksByStatus(true);
                break;
            case 4:
                System.out.print("Categoría: ");
                String category = scanner.nextLine();
                taskManager.listTasksByCategory(category);
                break;
            case 5:
                System.out.print("Prioridad (Alta/Media/Baja): ");
                String priority = scanner.nextLine();
                taskManager.listTasksByPriority(priority);
                break;
            default:
                showError("Opción no válida");
        }
        
        pause();
    }
    
    private static void searchTaskMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║            BUSCAR TAREA                ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        System.out.println("\n1. Buscar por ID");
        System.out.println("2. Buscar por título (Hash Table)");
        
        int option = readInt("\nSelecciona: ");
        scanner.nextLine();
        
        Task found = null;
        
        if (option == 1) {
            int id = readInt("ID de la tarea: ");
            found = taskManager.findTaskById(id);
        } else if (option == 2) {
            System.out.print("Título: ");
            String title = scanner.nextLine();
            found = taskManager.findTaskByTitle(title);
        }
        
        if (found != null) {
            System.out.println("\n" + "\033[0;32m" + "Tarea encontrada:" + "\033[0m");
            System.out.println(found.toDetailedString());
        } else {
            showError("Tarea no encontrada");
        }
        
        pause();
    }
    
    private static void modifyTaskMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║          MODIFICAR TAREA               ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        int id = readInt("\nID de la tarea: ");
        Task task = taskManager.findTaskById(id);
        
        if (task == null) {
            showError("Tarea no encontrada");
            pause();
            return;
        }
        
        System.out.println("\nTarea actual:");
        System.out.println(task.toDetailedString());
        
        System.out.println("\n¿Qué deseas modificar?");
        System.out.println("1. Título");
        System.out.println("2. Descripción");
        System.out.println("3. Categoría");
        System.out.println("4. Prioridad");
        System.out.println("5. Estado (Completar/Reabrir)");
        System.out.println("6. Volver");
        
        int option = readInt("\nSelecciona: ");
        scanner.nextLine();
        
        switch (option) {
            case 1:
                System.out.print("Nuevo título: ");
                task.setTitle(scanner.nextLine());
                break;
            case 2:
                System.out.print("Nueva descripción: ");
                task.setDescription(scanner.nextLine());
                break;
            case 3:
                System.out.print("Nueva categoría: ");
                task.setCategory(scanner.nextLine());
                break;
            case 4:
                System.out.print("Nueva prioridad (Alta/Media/Baja): ");
                task.setPriority(scanner.nextLine());
                break;
            case 5:
                task.setCompleted(!task.isCompleted());
                break;
            case 6:
                return;
            default:
                showError("Opción no válida");
                pause();
                return;
        }
        
        taskManager.updateTask(task);
        
        Action action = new Action("UPDATE", "Tarea ID " + id + " modificada");
        actionHistory.push(action);
        
        addNotification("Tarea actualizada: " + task.getTitle());
        showSuccess("Tarea actualizada exitosamente");
        pause();
    }
    
    // ============= ELIMINAR TAREA =============
    private static void deleteTaskMenu() {
        clearScreen();
        System.out.println("\033[0;31m" + "╔════════════════════════════════════════╗");
        System.out.println("║          ELIMINAR TAREA                ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        int id = readInt("\nID de la tarea a eliminar: ");
        Task task = taskManager.findTaskById(id);
        
        if (task == null) {
            showError("Tarea no encontrada");
            pause();
            return;
        }
        
        System.out.println("\nTarea a eliminar:");
        System.out.println(task.toDetailedString());
        
        System.out.print("\033[0;31m" + "\n¿Confirmas la eliminación? (S/N): " + "\033[0m");
        scanner.nextLine();
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("S")) {
            String title = task.getTitle();
            taskManager.deleteTask(id);
            
            Action action = new Action("DELETE", "Tarea eliminada: " + title);
            actionHistory.push(action);
            
            addNotification("Tarea eliminada: " + title);
            showSuccess("Tarea eliminada exitosamente");
        } else {
            System.out.println("\033[0;33m" + "Eliminación cancelada" + "\033[0m");
        }
        
        pause();
    }
    
    // ============= GESTIÓN DE SUBTAREAS =============
    private static void subtasksMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║        GESTIÓN DE SUBTAREAS            ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        int taskId = readInt("\nID de la tarea: ");
        Task task = taskManager.findTaskById(taskId);
        
        if (task == null) {
            showError("Tarea no encontrada");
            pause();
            return;
        }
        
        boolean managing = true;
        while (managing) {
            clearScreen();
            System.out.println("\033[0;36m" + "Tarea: " + task.getTitle() + "\033[0m");
            System.out.println("\n1. Ver subtareas");
            System.out.println("2. Agregar subtarea");
            System.out.println("3. Completar subtarea");
            System.out.println("4. Eliminar subtarea");
            System.out.println("5. Volver");
            
            int option = readInt("\nSelecciona: ");
            scanner.nextLine();
            
            switch (option) {
                case 1:
                    task.listSubtasks();
                    pause();
                    break;
                case 2:
                    System.out.print("Descripción de la subtarea: ");
                    String description = scanner.nextLine();
                    task.addSubtask(description);
                    showSuccess("Subtarea agregada");
                    pause();
                    break;
                case 3:
                    int completeId = readInt("ID de la subtarea a completar: ");
                    task.completeSubtask(completeId);
                    showSuccess("Subtarea completada");
                    pause();
                    break;
                case 4:
                    int deleteId = readInt("ID de la subtarea a eliminar: ");
                    task.removeSubtask(deleteId);
                    showSuccess("Subtarea eliminada");
                    pause();
                    break;
                case 5:
                    managing = false;
                    break;
                default:
                    showError("Opción no válida");
                    pause();
            }
        }
    }
    
    private static void commentsMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║        GESTIÓN DE COMENTARIOS          ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        int taskId = readInt("\nID de la tarea: ");
        Task task = taskManager.findTaskById(taskId);
        
        if (task == null) {
            showError("Tarea no encontrada");
            pause();
            return;
        }
        
        boolean managing = true;
        while (managing) {
            clearScreen();
            System.out.println("\033[0;36m" + "Tarea: " + task.getTitle() + "\033[0m");
            System.out.println("\n1. Ver comentarios");
            System.out.println("2. Agregar comentario");
            System.out.println("3. Eliminar comentario");
            System.out.println("4. Volver");
            
            int option = readInt("\nSelecciona: ");
            scanner.nextLine();
            
            switch (option) {
                case 1:
                    task.listComments();
                    pause();
                    break;
                case 2:
                    System.out.print("Autor: ");
                    String author = scanner.nextLine();
                    System.out.print("Comentario: ");
                    String text = scanner.nextLine();
                    task.addComment(author, text);
                    showSuccess("Comentario agregado");
                    pause();
                    break;
                case 3:
                    int commentId = readInt("ID del comentario a eliminar: ");
                    task.removeComment(commentId);
                    showSuccess("Comentario eliminado");
                    pause();
                    break;
                case 4:
                    managing = false;
                    break;
                default:
                    showError("Opción no válida");
                    pause();
            }
        }
    }
    
    // ============= GESTIÓN DE CATEGORÍAS =============
    private static void categoriesMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║       GESTIÓN DE CATEGORÍAS            ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        System.out.println("\n1. Ver categorías disponibles");
        System.out.println("2. Ver tareas por categoría");
        System.out.println("3. Volver");
        
        int option = readInt("\nSelecciona: ");
        scanner.nextLine();
        
        switch (option) {
            case 1:
                taskManager.listCategories();
                pause();
                break;
            case 2:
                System.out.print("Categoría: ");
                String category = scanner.nextLine();
                taskManager.listTasksByCategory(category);
                pause();
                break;
            case 3:
                break;
            default:
                showError("Opción no válida");
                pause();
        }
    }
    
    private static void viewHistoryMenu() {
        clearScreen();
        // Consumir posible newline pendiente del readInt anterior
        scanner.nextLine();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║    HISTORIAL DE ACCIONES              ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        if (actionHistory.isEmpty()) {
            System.out.println("\033[0;33m" + "\nNo hay acciones en el historial" + "\033[0m");
        } else {
            System.out.println("\033[0;32m" + "\nÚltimas acciones realizadas:" + "\033[0m");
            actionHistory.display();
        }
        
        pauseWithoutClear();
    }
    
    private static void statisticsMenu() {
        clearScreen();
        // Consumir posible newline pendiente del readInt anterior
        scanner.nextLine();
        Statics.Statistics stats = taskManager.getStatistics();
        stats.displayStatistics();
        pauseWithoutClear();
    }
    
    // ============= ORDENAR TAREAS =============
    private static void sortTasksMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║          ORDENAR TAREAS                ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        System.out.println("\n1. Ordenar por prioridad");
        System.out.println("2. Ordenar por fecha de creación");
        System.out.println("3. Ordenar por título");
        System.out.println("4. Volver");
        
        int option = readInt("\nSelecciona: ");
        
        switch (option) {
            case 1:
                taskManager.sortTasksByPriority();
                showSuccess("Tareas ordenadas por prioridad");
                taskManager.listAllTasks();
                break;
            case 2:
                taskManager.sortTasksByDate();
                showSuccess("Tareas ordenadas por fecha");
                taskManager.listAllTasks();
                break;
            case 3:
                taskManager.sortTasksByTitle();
                showSuccess("Tareas ordenadas por título");
                taskManager.listAllTasks();
                break;
            case 4:
                return;
            default:
                showError("Opción no válida");
        }
        
        pause();
    }
    
    private static void exportImportMenu() {
        clearScreen();
        System.out.println("\033[0;36m" + "╔════════════════════════════════════════╗");
        System.out.println("║        EXPORTAR / IMPORTAR             ║");
        System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
        
        System.out.println("\n1. Guardar cambios");
        System.out.println("2. Recargar desde archivo");
        System.out.println("3. Volver");
        
        int option = readInt("\nSelecciona: ");
        
        switch (option) {
            case 1:
                taskManager.saveToFile();
                showSuccess("Datos guardados correctamente");
                pause();
                break;
            case 2:
                taskManager.loadFromFile();
                showSuccess("Datos recargados correctamente");
                pause();
                break;
            case 3:
                break;
            default:
                showError("Opción no válida");
                pause();
        }
    }
    
    private static boolean exitProgram() {
        clearScreen();
        System.out.println("\033[0;33m" + "╔════════════════════════════════════════╗");System.out.println("║              SALIR                     ║");
    System.out.println("╚════════════════════════════════════════╝" + "\033[0m");
    
    System.out.print("\n¿Deseas guardar los cambios antes de salir? (S/N): ");
    scanner.nextLine();
    String save = scanner.nextLine();
    
    if (save.equalsIgnoreCase("S")) {
        taskManager.saveToFile();
        showSuccess("Datos guardados correctamente");
    }
    
    System.out.println("\033[0;36m" + "\n¡Gracias por usar TaskSync!" + "\033[0m");
    System.out.println("\033[0;33m" + "Proyecto de Estructuras de Datos" + "\033[0m");
    
    scanner.close();
    return false;
}

private static void addNotification(String message) {
    notificationQueue.enqueue(message);
}


private static void showNotifications() {
    if (!notificationQueue.isEmpty()) {
        System.out.println("\033[0;34m" + "\nNOTIFICACIONES:" + "\033[0m");
        while (!notificationQueue.isEmpty()) {
            System.out.println("  • " + notificationQueue.dequeue());
        }
        System.out.println();
    }
}

/**
 * Lee un entero con manejo de errores
 */
private static int readInt(String prompt) {
    while (true) {
        try {
            System.out.print(prompt);
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            showError("Por favor, ingresa un número válido");
        }
    }
}

/**
 * Limpia la pantalla (compatible con Windows y otros sistemas)
 */
private static void clearScreen() {
    System.out.flush();
    try {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                Process process = pb.start();
                process.waitFor();
            } catch (Exception ex) {
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            }
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    } catch (Exception e) {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    System.out.flush();
}

/**
 * Pausa la ejecución y limpia la pantalla después
 */
private static void pause() {
    System.out.println("\nPresiona ENTER para continuar...");
    try {
        scanner.nextLine();
        clearScreen(); // Limpiar después de pausar
    } catch (Exception e) {
        // Ignorar
    }
}

/**
 * Pausa la ejecución sin limpiar la pantalla (para ver contenido)
 */
private static void pauseWithoutClear() {
    System.out.println("\nPresiona ENTER para continuar...");
    System.out.flush();
    try {
        scanner.nextLine();
        clearScreen();
    } catch (Exception e) {
        // Ignorar
    }
}

/**
 * Muestra un mensaje de error
 */
private static void showError(String message) {
    System.out.println("\033[0;31m" + "\nERROR: " + message + "\033[0m");
}

/**
 * Muestra un mensaje de éxito
 */
private static void showSuccess(String message) {
    System.out.println("\033[0;32m" + "\n" + message + "\033[0m");
}
}
