/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Escructura;

/**
 *
 * @author kenie
 */
public class CircularLinList {
    private Node head;
    private Node tail;
    private int size;
    
    public CircularLinList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    public void add (Object data) {
        Node newNode = new Node(data);
        
        if(isEmpty()) {
            head = newNode;
            tail = newNode;
            newNode.setNext(head);
        }else {
            tail.setNext(newNode);
            newNode.setNext(head);
            tail = newNode;
        }
        
        size++;
    }
    
    public void addFirst(Object data) {
        Node newNode = new Node(data);
        
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            newNode.setNext(head);
        }else {
            newNode.setNext(head);
            tail.setNext(newNode);
            head = newNode;
        }
        
        size++;
    }
    
    public void addAt(int index, Object data) {
        if (index <0 || index > size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }
        
        if (index == 0) {
            addFirst(data);
            return;
        }
        
        if (index == size) {
            add(data);
            return;
        }
        
        if (index == size ){
            add(data);
            return;
        }
        
        Node newNode = new Node(data);
        Node current = head;
        
        for (int i = 0; i < index -1; i++) {
            current = current.getNext();
            size++;
        }
    }
    
    public Object get (int index) {
        if (index <0 || index >= size) {
            throw new IndexOutOfBoundsException ("Indice fuera de rango");
        }
        
        Node current = head;
        for (int i = 0; i < index; i++){
            current = current.getNext();
        }
        
        return current.getData();
    }
    
    public Object getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException ("La lista esta vacia");
        }
        
        return tail.getData();
    }
    
    
    public Object remove (int index) {
        if (index <0 ||  index >= size) {
            throw new IndexOutOfBoundsException ("Indice fuera de rango");
        }
        
        Object data;
        
        if (size == 1) {
            data = head.getData();
            head = null;
            tail = null;
            tail = null;
        }else if (index == 0) {
            data = head.getData();
            head = head.getNext();
            tail.setNext(head);
        }else{
            Node current = head;
            for (int i = 0; i < index -1; i++){
                current = current.getNext();
            }
            
            Node nodeToRemove = current.getNext();
            data = nodeToRemove.getData();
            current.setNext(nodeToRemove.getNext());
            
            if(nodeToRemove == tail) {
                tail = current;
            }
        }
        
        size--;
        return data;
        
        
    }
    
    public Object removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("La lista está vacía");
        }
        return remove(0);
    }
    
    public Object removeList(){
        if (isEmpty()) {
            throw new IllegalStateException("La lista esta vacia");
        }
        return remove (size -1);
    }
    
    public boolean removeByValue(Object data) {
        if (isEmpty()) {
            return false;
        }
        
       Node current = head;
       int index = 0;
       
       do {
           if (current.getData().equals(data)){
               remove(index);
               return true;
           }
           current = current.getNext();
           index++;
       }while (current != head && index < size);
       
       return false;
    }
    
    public int indexOff(Object data) {
        if(isEmpty()) {
            return -1;
        }
        
        Node current = head;
        int index = 0;
        
        do {
            if (current.getData().equals(data)){
                return index;
            }
            current = current.getNext();
            index++;
        }while (current != head && index < size);
        
        return -1;
    }
    
    public boolean contains (Object data) {
        return indexOff(data) != -1;
    }
    
    public void set (int index, Object data) {
        if (index <0 || index >= size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }
        
        Node current = head;
        for (int i = 0; i < index; i++){
            current = current.getNext();
        }
        
        current. setData(data);
    }
    
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
    
    public int size(){
        return size;
    }
    
    public Object[] toArray(){
        if (isEmpty()){
            return new Object[0];
        }
        
        Object[] array = new Object[size];
        Node current = head;
        int index = 0;
        
        do {
            array[index++] = current.getData();
            current = current.getNext();
        }while (current != head);
        
        return array;
    }
    
    
    public void rotate(int positions) {
        if (isEmpty() || size == 1){
            return;
        }
        
        positions = positions % size;
        if (positions == 0) {
            return;
        }
        
        for (int i = 0; i < positions; i++){
            head = head.getNext();
            tail = tail.getNext();
        }
    }
    
    public Object navigate (int steps) {
        if (isEmpty()){
            throw new IllegalStateException("La lista esta vacia");
        }
        
        Node current = head;
        steps = steps % size;
        
        for (int i = 0; i < steps; i++) {
            current = current.getNext();
        }
        
        return current.getData();
    }
    
    @Override
    public String toString(){
        if(isEmpty()){
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        
        do{
            sb.append(current.getData());
            current = current.getNext();
            if (current != head) {
                sb.append("->");
            }
        }while (current != head);
        
        sb.append("->(HEAD)");
        return sb.toString();
    }
    
    public void display() {
        if (isEmpty()) {
            System.out.println("La lista esta vacia");
        }
        
    System.out.println("╔════════════════════════════╗");
    System.out.println("║  LISTA CIRCULAR            ║");
    System.out.println("╠════════════════════════════╣");
    
    Node current = head;
    int position = 1;
    
    do{
        String marker = "";
        if(current == head) marker = "[HEAD]";
        if(current == tail) marker += "[TAIL]";
        
        System.out.println("║ " + position + ". " + current.getData() + marker);
        current = current.getNext();
        position++;
    }while (current != head);
    
    System.out.println("╚════════════════════════════╝");
    System.out.println("Tamaño: " + size);
    System.out.println("El último nodo apunta de vuelta al primero (SE REFIERE A Lis.Circular)");
    
        
    }
    

}
