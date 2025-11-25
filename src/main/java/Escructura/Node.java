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
public class Node {
    private Object data;
    private Node next;
    private Node prev;
    
    public Node(Object data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }
    
    public Node (Object data, Node next) {
        this.data = data;
        this.next = next;
        this.prev = null;
    }
    
    public Node (Object data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
 
   //Verificacion 
    public boolean hasNext() {
        return this.next != null;
    }
    
    //Biceversa
    public boolean hasPrev(){
        return this.prev != null;
    }
    
    @Override
    public String toString(){
        return "Node-{data=" + data + "}";
    }
    
}
