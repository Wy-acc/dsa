package adt;//author: Tay Zhuang Yin

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularArrayQueue<T> implements ListStackQueueInterface<T> {

    private T[] array;
    private int frontIndex;
    private int backIndex;
    private int size;
    private static final int DEFAULT_CAPACITY = 50;

    public CircularArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    public CircularArrayQueue(int initialCapacity) {
        array = (T[]) new Object[initialCapacity + 1]; // +1 for one unused space in circular queue
        frontIndex = 0;
        size = 0;
        backIndex = initialCapacity;
    }

    @Override
    public void enqueue(T newEntry) {
        if (isArrayFull()) {
            expandCapacity();
        }
        backIndex = (backIndex + 1) % array.length;
        array[backIndex] = newEntry;
        size++;
    }

    @Override
    public T getFront() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        return array[frontIndex];
    }
    
    @Override
    public int getFrontIndex() {
        return frontIndex;
    }

    @Override
    public T dequeue() {
        T front = null;
        if (!isEmpty()) {
            front = array[frontIndex];
            array[frontIndex] = null; // Clear the element from the array
            frontIndex = (frontIndex + 1) % array.length; // Update the front index
            size--;
        }
        return front;
    }

    @Override
    public boolean isEmpty() {
        return frontIndex == ((backIndex + 1) % array.length); // Queue is empty if front and back are one position apart
    }

    @Override
    public void clear() {
        if (!isEmpty()) {
            for (int index = frontIndex; index != backIndex; index = (index + 1) % array.length) {
                array[index] = null;
            }
            array[backIndex] = null;
        }
        frontIndex = 0;
        backIndex = array.length - 1;
    }

    private boolean isArrayFull() {
        return size == array.length;
    }

    private void expandCapacity() {
        T[] newArray = (T[]) new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(frontIndex + i) % array.length]; // Copy elements in correct order
        }
        array = newArray;
        frontIndex = 0;
        backIndex = size - 1;
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public Iterator<T> getIterator() {
        return new QueueIterator();
    }

    @Override
    public boolean add(T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T remove(int givenPosition) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T getEntry(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        int actualIndex = (frontIndex + index) % array.length;
        return array[actualIndex];
    }

    @Override
    public boolean contains(T anEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getNumberOfEntries() {
        return size;
    }

    @Override
    public boolean isFull() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void push(T newEntry) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T pop() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T peek() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public boolean removeById(String jobId) {
        int originalSize = size;
        boolean found = false;

        for (int i = 0; i < originalSize; i++) {
            T current = dequeue();

            if (current instanceof entity.Job) {
                entity.Job job = (entity.Job) current;  // Traditional cast
                if (job.getJobID().equals(jobId)) {
                    found = true; 
                } else {
                    enqueue(current);
                }
            } else {
                enqueue(current);
            }
        }

        return found;
    }

    private class QueueIterator implements Iterator<T> {
        private int currentIndex = frontIndex;
        private int visitedCount = 0;
    
        @Override
        public boolean hasNext() {
            return visitedCount < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the queue.");
            }
            T data = array[currentIndex];
            currentIndex = (currentIndex + 1) % array.length;
            visitedCount++;
            return data;
        }
    }
}