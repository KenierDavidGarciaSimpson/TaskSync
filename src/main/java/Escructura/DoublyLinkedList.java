/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Escructura;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author kenie
 * @param <T> 
 */
public class DoublyLinkedList {
   private Node<T> head;
   private Node<T> tail;
   private int size;
   
   
   public DoublyLinkedList() {
       this.head = null;
       this.tail = null;
       this.size = 0;
   }
   
   public void add (T data) {
       Node<T> newNode = new Node <> (data);
       if(head == null) {
           head = tail = newNode;
           
       }else {
           tail.next = newNode;
           newNode.prev = tail;
           tail = newNode;
       }
       size++;
   }
    
}
