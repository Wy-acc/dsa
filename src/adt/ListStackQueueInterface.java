/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt; //author: Yaw Wei Ying, Tay Mian Yin, Tay Zhuang Yin, Yoo Xin Wei

import java.util.Iterator;

public interface ListStackQueueInterface<T> {

    public boolean add(T newEntry);
    public T remove(int givenPosition);
    public void clear();
    public boolean replace(int givenPosition, T newEntry);
    public T getEntry(int givenPosition);
    public boolean contains(T anEntry);
    public int getNumberOfEntries();
    public boolean isEmpty();
    public boolean isFull();
    public int size();
    
    public void push(T newEntry);
    public T pop();
    public T peek();
    public void display();
    
    public void enqueue(T newEntry);
    public T dequeue();
    public int getFrontIndex();
    public T getFront();
    Iterator<T> getIterator();
    
    public boolean removeById(String jobId);
}
