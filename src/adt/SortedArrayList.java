package adt; //author: Yoo Xin Wei

import java.util.Comparator;
import java.util.Iterator;

public class SortedArrayList<T> implements ListStackQueueInterface<T> {
    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 100;
    private final Comparator<T> comparator;

    public SortedArrayList(Comparator<T> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }

    public SortedArrayList(int initialCapacity, Comparator<T> comparator) {
        this.numberOfEntries = 0;
        this.array = (T[]) new Object[initialCapacity];
        this.comparator = comparator;
    }

    @Override
    public boolean add(T newEntry) {
        if (isArrayFull()) {
            doubleArray();
        }
        
        int position = findPosition(newEntry);
        
        for (int i = numberOfEntries; i > position; i--) {
            array[i] = array[i - 1];
        }
        
        array[position] = newEntry;
        numberOfEntries++;
        return true;
    }

    private int findPosition(T newEntry) {
        int low = 0;
        int high = numberOfEntries - 1;
        
        while (low <= high) {
            int mid = (low + high) / 2;
            int compareResult = comparator.compare(newEntry, array[mid]);
            
            if (compareResult < 0) {
                high = mid - 1;
            } else if (compareResult > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        
        return low;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            result = array[givenPosition - 1];

            for (int i = givenPosition - 1; i < numberOfEntries - 1; i++) {
                array[i] = array[i + 1];
            }

            numberOfEntries--;
            array[numberOfEntries] = null;
        }

        return result;
    }

    @Override
    public void clear() {
        for (int i = 0; i < numberOfEntries; i++) {
            array[i] = null;
        }
        numberOfEntries = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            T oldEntry = array[givenPosition - 1];
            array[givenPosition - 1] = null;
            numberOfEntries--;
            
            add(newEntry);
            return true;
        }
        return false;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            result = array[givenPosition - 1];
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        int low = 0;
        int high = numberOfEntries - 1;
        
        while (low <= high) {
            int mid = (low + high) / 2;
            int compareResult = comparator.compare(anEntry, array[mid]);
            
            if (compareResult == 0) {
                return true;
            } else if (compareResult < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return false;
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public int size() {
        return numberOfEntries;
    }
    
    @Override
    public Iterator<T> getIterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < numberOfEntries;
            }

            @Override
            public T next() {
                return array[currentIndex++];
            }
        };
    }
    
    public int binarySearch(T target) {
        int low = 0;
        int high = numberOfEntries - 1;
        
        while (low <= high) {
            int mid = (low + high) / 2;
            int comparison = comparator.compare(target, array[mid]);
            
            if (comparison == 0) {
                return mid + 1;
            } else if (comparison < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return -1;
    }

    private void doubleArray() {
        T[] oldArray = array;
        array = (T[]) new Object[oldArray.length * 2];
        System.arraycopy(oldArray, 0, array, 0, oldArray.length);
    }

    private boolean isArrayFull() {
        return numberOfEntries == array.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < numberOfEntries; ++index) {
            sb.append(array[index]).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void push(T newEntry) {
        throw new UnsupportedOperationException("push operation not supported");
    }

    @Override
    public T pop() {
        throw new UnsupportedOperationException("pop operation not supported");
    }

    @Override
    public T peek() {
        throw new UnsupportedOperationException("peek operation not supported");
    }

    @Override
    public void display() {
        System.out.println(toString());
    }

    @Override
    public void enqueue(T newEntry) {
        throw new UnsupportedOperationException("enqueue operation not supported");
    }

    @Override
    public T dequeue() {
        throw new UnsupportedOperationException("dequeue operation not supported");
    }

    @Override
    public T getFront() {
        throw new UnsupportedOperationException("getFront operation not supported");
    }

    @Override
    public int getFrontIndex() {
        throw new UnsupportedOperationException("getFrontIndex operation not supported");
    }

    @Override
    public boolean removeById(String id) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i] instanceof entity.Interview) {
                entity.Interview interview = (entity.Interview) array[i];
                if (interview.getInterviewId().equals(id)) {
                    for (int j = i; j < numberOfEntries - 1; j++) {
                        array[j] = array[j + 1];
                    }
                    array[numberOfEntries - 1] = null;
                    numberOfEntries--;
                    return true;
                }
            } else if (array[i] instanceof entity.InterviewTimetable) {
                entity.InterviewTimetable slot = (entity.InterviewTimetable) array[i];
                if (slot.getSlotId().equals(id)) {
                    for (int j = i; j < numberOfEntries - 1; j++) {
                        array[j] = array[j + 1];
                    }
                    array[numberOfEntries - 1] = null;
                    numberOfEntries--;
                    return true;
                }
            }
        }
        return false;
    }
}