/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Escructura;


/**
 *
 * @author kenie
 * 
 */
public class DoublyLinkedList {
    private Node head;
    private Node tail;
    private int size;
    
     public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
     
     public void add(Object data) {
         Node newNode =  new Node(data);
         
         if (isEmpty()) {
             head = newNode;
             tail = newNode;
         }else {
             tail.setNext(newNode);
             newNode.setPrev(tail);
             tail = newNode;
         }
         size++;
     }
     
     public void addFirst (Object data) {
         Node newNode = new Node (data);
         
         if (isEmpty()) {
             head = newNode;
             tail = newNode;
         }else {
             newNode.setNext(head);
             head.setPrev(newNode);
             head = newNode;
         }
         size++;
     }
     
     public void addAt (int index, Object data) {
         if (index <0 || index > size) {
             throw new IndexOutOfBoundsException("Indice fuera de rango");
         }
         if (index == 0) {
             addFirst (data);
             return;
         }
         
         if (index == size) {
             add(data);
             return;
         }
         
         if (index == size) {
             add(data);
             return;
         }
         
         Node newNode = new Node(data);
         Node current = getNodeAt (index);
         Node previous = current.getPrev();
         
         newNode.setNext(current);
         newNode.setPrev(previous);
         previous.setNext(newNode);
         current.setPrev(newNode);
         
         size++;
     }
     
     public Object get(int index) {
         if(index <0 || index >= size) {
             throw new IndexOutOfBoundsException ("Indice fuera de rango");
         }
         
         Node node = getNodeAt(index);
         return node.getData();
     }
     
     private Node getNodeAt(int index) {
      Node current;
      
      if (index < size / 2) {
          current = head;
          for (int i = 0; i < index; i++) {
              current = current.getNext();
          }
      }else {
          current = tail;
          for (int i = size - 1; i > index; i--) {
              current = current.getPrev();
          }
      }
      return current;
     }
     
     public Object getFirst(){
         if (isEmpty()) {
             throw new IllegalStateException ("La lista esta vacia");
         }
         return head.getData();
     }
     
     public Object getLast(){
         if (isEmpty()) {
             throw new IllegalStateException("La lista esta vacia");
         }
         return tail.getData();
     }
     
     public Object remove (int index) {
         if (index <0 || index >= size) {
             throw new IndexOutOfBoundsException ("Indice fuera de rango");
         }
         Node nodeToRemove = getNodeAt (index);
         Object data = nodeToRemove.getData();
         
         if (size == 1) {
             head = null;
             tail = null;
         }else if (index == 0) {
             head = head.getNext();
             head.setPrev(null);
         }else if (index == size -1) {
             tail = tail.getPrev();
             tail.setNext(null);
         }else {
             Node previous = nodeToRemove.getPrev();
             Node next = nodeToRemove.getNext();
             previous.setNext(next);
             next.setPrev(previous);
         }
         
         size--;
         return data;
     }
     
     public Object removeFirst(){
         if (isEmpty()) {
             throw new IllegalStateException("La lista esta vacia");
         }
         return remove(0);
     }
     
     public Object removelast() {
         if (isEmpty()) {
             throw new IllegalStateException("La lista esta vacia");
         }
         return remove (size - 1);
     }
     
     public boolean removeByValue(Object data) {
         Node current = head;
         int index = 0;
         
         while (current != null) {
             if (current.getData().equals(data)) {
                 remove(index);
                 return true;
             }
             current = current.getNext();
             index++;
         }
         return false;
     }
     
     public int indexOf(Object data) {
         Node current = head;
         int index = 0;
         
         while (current != null) {
             if (current.getData().equals(data)){
                 return index;
             }
             current = current.getNext();
             index++;
         }
         
         return -1;
     }
     
     public boolean contains (Object data) {
         return indexOf(data) != -1;
     }
     
     public void set (int index, Object data) {
         if (index <0 || index >= size) {
             throw new IndexOutOfBoundsException("Indice fuera de rango");
         }
         
         Node node = getNodeAt(index);
         node.setData(data);
     }
     
     public void clear() {
         head = null;
         tail = null;
         size = 0;
     }
     
     public boolean isEmpty() {
        return size == 0;
    }
     
     public int size(){
         return size;
     }
     
     public Object[] toArray() {
    Object[] array = new Object [size];
    Node current = head;
    int index = 0;
    
    while (current != null) {
        array [index++] = current.getData();
        current = current.getNext();
    }
    return array;
}
     public void reverse() {
         if (size <= 1) return;
         
         Node current = head;
         Node temp = null;
         
         tail = head;
         
         while (current != null) {
             temp = current.getPrev();
             current.setPrev(current.getNext());
             current.setNext(temp);
             current = current.getPrev();
             
         }
         
         if (temp != null) {
             head = temp.getPrev();
         }
     }
     
     @Override
     public String toString() {
         if (isEmpty()) {
             return "[]";
         }
         
         StringBuilder sb = new StringBuilder ("[");
         Node current = head;
         
         while (current != null) {
             sb.append(current.getData());
             if (current.hasNext()) {
                 sb.append("<->");
             }
             current = current.getNext();
         }
         
         sb.append("]");
         return sb.toString();
         
     }
     
     public String toStringReverse(){
         if (isEmpty()){
             return "[]";
         }
         StringBuilder sb = new StringBuilder("[");
         Node current = tail;
         
         while (current != null) {
             sb.append (current.getData());
             if (current.hasPrev()) {
                 sb.append("<->");
             }
             
             current = current.getPrev();
         }
         
         sb.append ("]");
         return sb.toString();
     }
}
