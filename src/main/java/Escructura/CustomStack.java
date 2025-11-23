/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Escructura;

/**
 *
 * @author kenie
 */
public class CustomStack {
    private Node top;
    private int size;
    private int maxSize;
    
      public CustomStack() {
        this.top = null;
        this.size = 0;
        this.maxSize = -1; 
    }
      
        public CustomStack(int maxSize) {
        this.top = null;
        this.size = 0;
        this.maxSize = maxSize;
    }
        
    public void push(Object data) {
        if (isFull()) {
            throw new IllegalStateException ("La pila esta llena");
        }
        Node newNode = new Node (data, top);
        top = newNode;
        size++;
    }
    
    public Object pop(){
        if (isEmpty()) {
            throw new IllegalStateException ("La pila esta vacia");
        }
        
        Object data = top.getData();
        top = top.getNext();
        size--;
        return data;
    }
    
    public Object peak(){
        if(isEmpty()){
            throw new IllegalStateException("La pila esta vacia");
        }
        return top.getData();
    }
    
    public boolean isFull() {
        return maxSize != -1 && size >= maxSize;
    }
    
    public boolean isEmpty() {
    return size == 0;
}

    
    public int size(){
        return size;
    }
    
    public void clear(){
        top = null;
        size = 0;
    }
    
    public boolean contains (Object data) {
        Node current = top;
        
        while (current != null) {
            if (current.getData().equals(data)){
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
    
    public int search (Object data) {
        Node current =  top;
        int position = 1;
        
        while (current != null) {
            if (current.getData().equals(data)){
                return position;
            }
            current = current.getNext();
            position ++;
        }
        return -1;
    }
    
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node current = top;
        int index = 0;
        
        while (current != null) {
            array[index++] = current.getData();
            current = current.getNext();
        }
        return array;
    }
    
    public CustomStack copy() {
        CustomStack newStack = new CustomStack(maxSize);
        
        if (isEmpty()) {
            return newStack;
            
        }
        
        CustomStack tempStack = new CustomStack();
        Node current = top;
        
        while (current != null) {
            tempStack.push(current.getData());
            current = current.getNext();
        }
        
        current = tempStack.top;
        while (current != null ) {
            newStack.push(current.getData());
            current = current.getNext();
        }
        
        return newStack;
    }
    
    @Override 
    public String toString(){
        if (isEmpty()) {
            return "Stack: []";
        }
        
        StringBuilder sb = new StringBuilder ("Stack (Top -> Bottom): [");
        Node current = top;
        
        while (current != null) {
            sb.append(current.getData());
            if (current.hasNext()) {
                sb.append("<-");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
    
    public void display() {
        if (isEmpty()) {
            System.out.println("La pila esta vacia");
            return;
        }
        
        System.out.println("╔════════════════════╗");
        System.out.println("║  CONTENIDO PILA    ║");
        System.out.println("╠════════════════════╣");
        
        Node current = top;
        int position = 1;
        
        while (current != null) {
            System.out.println("║ " + position + ". " + current.getData());
            current = current.getNext();
            position++;
        }
        
        System.out.println("╚════════════════════╝");
        System.out.println("Tamaño: " + size);
    }
    
    
}
