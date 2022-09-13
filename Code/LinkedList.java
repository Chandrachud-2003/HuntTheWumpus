/**
 * Chandrachud Malali Gowda
 * CS231 A - Data Structures and Algorithms
 * 13th December 2021
 * Project 09: Hunt the Wumpus
 * LinkedList.java
 */

// Importing the required libraries
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

public class LinkedList<T> implements Iterable<T> {

    // Node class
    private class Node<T> {

        T data;
        Node<T> next;

        public Node(T newData) {
            this.data = newData;
            this.next = null;
        }

        public Node<T> getNext() {
            return this.next;
        }

        public T getThing() {
            return this.data;
        }

        public void setNext(Node<T> newNext) {
            this.next = newNext;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    // Constructor
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Clearing the list
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Getting the size of the list
    public int size() {
        return this.size;
    }

    // Adding an element to the linked list
    public void add(T newItem) {
        if(this.size == 0) {
            Node<T> newNode = new Node<T>(newItem);
            this.head = newNode;
            this.tail = newNode;
        }

        this.size++;
    }

    // Adding an element to end of the linked list
    public void addLast(T newItem) {
        Node<T> newNode = new Node<T>(newItem);
        if(this.tail != null && this.head != null) {
            this.tail.next = newNode;
            this.tail = newNode;
        } else {
            this.tail = newNode;
            this.head = newNode;
        }
        this.size++;
    }

    // Adding an element to the beginning of the linked list
    public void addFirst(T newItem) {
        Node<T> newNode = new Node<T>(newItem);
        if(this.head != null && this.tail != null) {
            newNode.setNext(this.head);
            this.head = newNode;
        } else {
            this.head = newNode;
            this.tail = newNode;
        }
        this.size++;
    }

    // Adding an element at a specific index
    public void add(int index, T newItem) {
        Node<T> newNode = new Node<T>(newItem);
        if(this.head != null) {
            Node<T> temp = this.head;

            int counter = 0;
            while(counter < index - 1) {
                temp = temp.next;
                counter++;
            }
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        } else {
            this.head = newNode;
            this.tail = newNode;
        }

        this.size++;
    }

    // Getting the element at a specific index
    public T get(int index) {

        Node<T> temp = this.head;
        int counter = 0;

        while(counter < index) {
            temp = temp.getNext();
            counter++;
        }

        T result = temp.getThing();
        return result;
    }

    // Removing an element at a specific index
    public T remove(int index) {
        if(this.size != 0) {
            Node<T> current = this.head;
            Node<T> previous = null;

            int counter = 0;
            while(counter < index) {
                previous = current;
                current = current.getNext();
                counter++;
            }

            if(current == this.head) {
                T result = current.getThing();
                this.head = this.head.getNext();
                this.size--;
                return result;
            } else if(current == this.tail) {
                T result = current.getThing();
                this.tail = previous;
                previous.setNext(null);
                this.size--;
                return result;
            } else {
                T result = current.getThing();
                previous.setNext(current.getNext());
                this.size--;
                return result;
            }
        }
        return null;
    }

    // Overriding the toString() method
    public String toString() {
        String result = "";
        Node<T> temp = this.head;

        while(temp != null) {
            result += temp.getThing() + ", ";
            temp = temp.getNext();
        }

        return result;
    }

    // Reverse the linked list
    public void reverse() {
        Node<T> previousNode = null;
        Node<T> currentNode = this.head;
        Node<T> nextNode = this.head;

        while(currentNode != null) {
            nextNode = nextNode.getNext();
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        this.head = previousNode;
        
    }

    // Returns a new LLIterator pointing to the head of the list
    public Iterator<T> iterator() {
        return new LLIterator(this.head);
    }

    // Function that retruns an ArrayList of the list contents in order
    public ArrayList<T> toArrayList() {
        ArrayList<T> list = new ArrayList<T>();

        Node<T> currentNode = this.head;
        while(currentNode != null) {
            list.add(currentNode.getThing());
            currentNode = currentNode.getNext();
        }

        return list;
    }

    // Function to return an ArrayList of the list contents in shuffled order
    public ArrayList<T> toShuffledList() {
        ArrayList<T> list = this.toArrayList();
        this.shuffle(list);
        return list;
    }

    // Shuflle the list
    public void shuffle(ArrayList<T> list) {

        Random generator = new Random();

        for(int i = list.size() - 1; i >= 0; i--) {
            int randIndex = generator.nextInt(i + 1);
            T current = list.get(i);
            list.set(i, list.get(randIndex));
            list.set(randIndex, current);

        }
    }

    // Main method to test the functions
    public static void main(String[] args) {
        LinkedList<Integer> myList = new LinkedList<Integer>();
        myList.add(25);
        myList.addLast(3);
        System.out.println(myList);
        myList.addFirst(5);

		System.out.println("after addFirst: " + myList);

		myList.add(2, 35);

		//System.out.println("after add at index: " + myList);

		//System.out.println("get(1) should be 25: " + myList.get(1));

		System.out.println("before remove: " + myList);

		System.out.println("remove index 0, should return 5: " 
			+ myList.remove(0));

		System.out.println("current list: " + myList);

		System.out.println("remove index 0, should return 35: " 
			+ myList.remove(1));

        myList.addFirst(15);
		myList.addFirst(55);
		myList.addFirst(75);

		System.out.println("current list: " + myList);

		System.out.println("remove index 0, should return 3: " 
			+ myList.remove(4));


		System.out.println("current list: " + myList);

        myList.reverse();
        System.out.println("Reversed Linked List: " + myList);
    }
     
    // Inner class to implement the Iterator
    private class LLIterator implements Iterator<T> {

        private Node next;

        // Constructor for the LLIterator given the head of the list
        public LLIterator(Node head) {
            this.next = head;
        }

        // Returns true if there are still values to traverse
        public boolean hasNext() {
            return this.next != null;
        }

        // Returns the next item in the list
        public T next() {
            Node returnNode = next;
            next = next.getNext();
            return (T) returnNode.getThing();
        }

        // Optional implementation
        public void remove() {
            // Does nothing
        }
    }
}


