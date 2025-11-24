/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Escructura;

/**
 *
 * @author kenie
 */
public class CustomQueue {
    private Node front;
    private  Node rear;
    private int size;
    private int maxSize;
    
    public CustomQueue(int maxSize) {
        this.front = null;
        this.rear = null;
        this.size =0;
        this.maxSize = maxSize;
    }
    
    public void enqueue (Object data) {
        if (isFull()) {
            throw new IllegalStateException("La cola esta llena");
            
        }
        
        Node newNode = new Node(data);
        
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        }else {
            rear.setNext(newNode);
            rear = newNode;
        }
        
        size++;
    }
    
    public Object dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException ("La cola esta vacia");
        }
        
        Object data = front.getData();
        front = front.getNext();
        
        if (front == null ) {
            rear = null;
        }
        
        size--;
        return data;
    }
    
    public Object peek(){
        if (isEmpty()){
            throw new IllegalStateException ("La cola esta vacia");
        }
        return front.getData();
    }
    
    public Object peekRear(){
        if(isEmpty()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        return rear.getData();
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
    
    public boolean isFull(){
        return maxSize != -1 && size >= maxSize;
    }
    
    public int size(){
        return size;
    }
    
    public void clear (){
        front = null;
        rear = null;
        size = 0;
    }
    
    public boolean contains (Object data) {
        Node current = front;
        
        while (current != null) {
            if(current.getData().equals(data)){
                return true;
            }
            
            current = current.getNext();
        }
        
        return false;
    }
    
    public int indexOf(Object data) {
        Node current = front;
        int position = 0;
        
        while (current != null) {
                if (current.getData().equals(data)){
                    return position;
                
            }
             current = current.getNext();
             position++;
        }
        
        return -1;
    }
    
    public Object[] toArray() {
        Object[] array = new Object [size];
        Node current = front;
        int index = 0;
        
        while (current != null) {
            array [index++] = current.getData();
            current = current.getNext();
        }
        
        return array;
    }
    
 public void reverse() {
    if (size <= 1) return;

    CustomStack tempStack = new CustomStack();

    while (!isEmpty()) {
        tempStack.push(dequeue());
    }

    while (!tempStack.isEmpty()) {
        enqueue(tempStack.pop());
    }
}
 
public CustomQueue copy() {
    CustomQueue newQueue = new CustomQueue(maxSize);
    Node current = front;
    
    while (current != null) {
        newQueue.enqueue(current.getData());
        current = current.getNext();
    }
    return newQueue;
}

@Override
public String toString() {
    if(isEmpty()) {
        return "Queue:[]";
    }
    
    StringBuilder sb = new StringBuilder("Queue (Front -> Rear):[");
    Node current = front;
    
    while (current != null) {
        sb.append(current.getData());
        if (current.hasNext()) {
            sb.append("->");
        }
        current = current.getNext();
    }
    
    sb.append("]");
    return sb.toString();
}

public void display(){
    if (isEmpty()) {
        System.out.println("La cola esta vacia");
        return;
    }
    
     System.out.println("╔════════════════════╗");
     System.out.println("║  CONTENIDO COLA    ║");
     System.out.println("╠════════════════════╣");
     
     
     Node current = front;
     int position = 1;
     
     while (current != null) {
         String marker = "";
         if (current == front) marker = "[FRONT]";
         if (current == rear) marker = "[REAR]";
         
         System.out.println("║ " + position + ". " + current.getData() + marker);
          current = current.getNext();
          position++;
     }
     
      System.out.println("╚════════════════════╝");
        System.out.println("Tamaño: " + size);
    
    
}

}

    
