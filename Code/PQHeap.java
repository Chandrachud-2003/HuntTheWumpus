/**
 * Chandrachud Malali Gowda
 * CS231 A - Data Structures and Algorithms
 * 13th December 2021
 * Project 09: Hunt the Wumpus
 * PQHeap.java
 */

// Importing the required libraries
import java.util.Comparator;

public class PQHeap<T> {

    // Instance variables
    private Comparator<T> comp;
    private int size;
    private Object[] heap;

    // Constructor
    public PQHeap(Comparator<T> comp) {
        this.comp = comp;
        this.size = 0;
        this.heap = (T[]) new Object[10];
    }

    // Returns the number of elements in the heap
    public int size() {
        return this.size;
    }

    // Returns the position of parent
    private int parent(int position) {
        return position / 2;
    }

    // Returns the left child of the given position
    private int leftChild(int position) {
        return 2 * position;
    }

    // Returns the right child of the given position
    private int rightChild(int position) {
        return (2 * position) + 1;
    }

    // Swaps the elements at the given indices
    private void swap(int firstPosition, int secondPosition) {
        T temp;
        temp = (T) this.heap[firstPosition];
        this.heap[firstPosition] = this.heap[secondPosition];
        this.heap[secondPosition] = temp;
        /** 
            * Extension (Loss of Dynamic Memory):
            * Last reference to the T object 'temp' -> this.heap[firstPosition]
            after which references to it are removed
        */
    }

    // Returns true if the given node is a leaf
    private boolean isLeaf(int position) {
        return (position >= (this.size / 2) && position <= this.size);
    }

    // Adds a value to the heap and increments the size
    public void add(T obj) {

        this.size += 1;

        if(this.isFull()) {
            this.resize();
        }

        this.heap[this.size] = obj;

        int current = size;
        while(current != 1 && this.comp.compare((T) this.heap[current], (T) this.heap[this.parent(current)]) > 0) {
            this.swap(current, this.parent(current));
            current = this.parent(current);
        }
        
    }

    // Removes and returns the highest priority element from the heap
    public T remove() {

        if(this.isEmpty()) {
            System.out.println("Heap is empty");
            return null;
        }

        T removed = (T) this.heap[1];
        this.heap[1] = this.heap[this.size--];
        this.heap[size + 1] = removed;

        /** 
            * Extension (Loss of Dynamic Memory):
            * Last reference to the T object 'removed'
            after which references to it are removed
        */

        this.maxHeapify(1);
        
        return removed;
    }

    // Reheaping the binary heap to maintain the max heap property
    private void maxHeapify(int position) {
        int left = this.leftChild(position);
        int right = this.rightChild(position);

        while(left <= this.size) {
            int maxIndex = left;

            if(right < this.size && this.comp.compare((T) this.heap[right], (T) this.heap[left]) > 0) {
                maxIndex = right;
            }

            if(this.comp.compare((T) this.heap[position], (T) this.heap[maxIndex]) < 0) {
                this.swap(position, maxIndex);
                position = maxIndex;
                left = this.leftChild(position);
                right = this.rightChild(position);
            } else {
                return;
            }
        }
    }

    // Clearing the heap
    public void clear() {
        int capacity = this.heap.length;
        this.heap = new Object[capacity];
        this.size = 0;
    }

    // Checking of the heap is empty
    private boolean isEmpty() {
        return this.size == 0;
    }

    // Checking if the heap is full
    private boolean isFull() {
        return this.size == this.heap.length - 1;
    }

    // Resizing the heap when the heap is full
    private void resize() {
        Object[] tempArray = new Object[this.heap.length * 2];
        for(int i = 0; i < this.heap.length; i++) {
            tempArray[i] = this.heap[i];
        }

        this.heap = tempArray;

        /** 
            * Extension (Loss of Dynamic Memory):
            * Last reference to the Object[] array 'tempArray'
            after which references to it are removed
        */
        
    }

}