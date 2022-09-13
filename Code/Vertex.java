/**
 * Chandrachud Malali Gowda
 * CS231 A - Data Structures and Algorithms
 * 13th December 2021
 * Project 09: Hunt the Wumpus
 * Vertex.java
 */

// Importing the required libraries
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

public class Vertex implements Comparable<Vertex> {

    // Instance variables
    private ArrayList<Vertex> adjacencyList;
    private int x;
    private int y;
    private boolean visible;
    private double distance;
    private boolean visited;
    private Vertex parent;

    // Enums
    // controls the direction in which the vertexes are connected
    public enum Direction { EAST, SOUTH, WEST, NORTH }
    private Direction direction;

    // Constructor
    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = true;
        this.distance = 0.0d;
        this.visited = false;
        this.parent = null;
        this.adjacencyList = new ArrayList<Vertex>();
    }

    // Constructor with visible field
    public Vertex(int x, int y, boolean visible) {
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.distance = 0.0d;
        this.visited = false;
        this.parent = null;
        this.adjacencyList = new ArrayList<Vertex>();
    }

    // Constructor with visible field and parent field
    public Vertex(int x, int y, boolean visible, Vertex parent) {
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.distance = 0.0d;
        this.visited = false;
        this.parent = parent;
        this.adjacencyList = new ArrayList<Vertex>();
    }

    // Getter and Setter methods for all fields

    // Getter for the Adjacency List
    public ArrayList<Vertex> getAdjacencyList() {
        return this.adjacencyList;
    }

    // Setter for the Adjacency List
    public void setAdjacencyList(ArrayList<Vertex> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    // Getter for the x coordinate
    public int getX() {
        return this.x;
    }

    // Setter for the x coordinate
    public void setX(int x) {
        this.x = x;
    }

    // Getter for the y coordinate
    public int getY() {
        return this.y;
    }

    // Setter for the y coordinate
    public void setY(int y) {
        this.y = y;
    }

    // Getter for the visibility
    public boolean isVisible() {
        return this.visible;
    }

    // Setter for the visibility
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // Getter for the distance
    public double getCost() {
        return this.distance;
    }

    // Setter for the distance
    public void setCost(double distance) {
        this.distance = distance;
    }

    // Getter for the visited
    public boolean isVisited() {
        return this.visited;
    }

    // Setter for the visited
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // Getter for the parent
    public Vertex getParent() {
        return this.parent;
    }

    // Setter for the parent
    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    // Returns the distance between two vertices
    public double distance(Vertex other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
    }

    // Connects two vertices (uni-directional link)
    public void connect(Vertex other, Direction direction) {
        other.direction = direction;
        this.adjacencyList.add(other);
    }

    // Connects two vertices (uni-directional link)
    public void connect(Vertex other) {
        this.adjacencyList.add(other);
    }

    // Returns Vertex at position (x, y) if it is in the adjacency list
    public Vertex getNeighbor(int x, int y) {
        for (Vertex v : adjacencyList) {
            if (v.getX() == x && v.getY() == y) {
                return v;
            }
        }

        return null;
    }

    // Gets all neighbors of the vertex
    public ArrayList<Vertex> getNeighbours() {
        return this.adjacencyList;
    }

    // Returns the number of connected vertices
    public int numNeighbors() {
        return this.adjacencyList.size();
    }

    // Setting the position for the x and y coordinates
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Draw method for each Vertex
    public void draw(Graphics g, int scale) {
        if (!this.visible)
            return;
        int xpos = (int)this.getX()*scale;
        int ypos = (int)this.getY()*scale;
        int border = 2;
        int half = scale / 2;
        int eighth = scale / 8;
        int sixteenth = scale / 16;
        
        // draw rectangle for the walls of the room
        if (this.getCost() <= 1)
            // wumpus is nearby
            g.setColor(Color.red);
        else
            // wumpus is not nearby
            g.setColor(Color.black);
        
        g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
        
        // draw doorways as boxes
        g.setColor(Color.black);
        
        // Checking if the vertex is connected to the north
        if (this.getNeighbor( this.getX(), this.getY()-1 ) != null )
            g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
        // Checking if the vertex is connected to the south
        if (this.getNeighbor( this.getX(), this.getY()+1 ) != null )
            g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), 
                       eighth, eighth + sixteenth);
        // Checking if the vertex is connected to the east
        if (this.getNeighbor( this.getX()-1, this.getY() ) != null)
            g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
        // Checking if the vertex is connected to the west
        if (this.getNeighbor( this.getX()+1, this.getY() ) != null)
            g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, 
                       eighth + sixteenth, eighth);
    }
	

    // Overrides the toString method
    @Override
    public String toString() {
        String output = "";

        output += "Number of neighbours: " + this.numNeighbors() + "\n";
        output += "Vertex cost: " + this.distance + "\n";
        output += "Vertex coordinates: (" + this.x + ", " + this.y + ")\n";
        output += "Has the vertex been visited? " + this.visited + "\n";

        return output;
    }

    // Overrides the compareTo method
    @Override
    public int compareTo(Vertex o) {
        if(this.distance < o.getCost()) {
            return -1;
        } else if(this.distance > o.getCost()) {
            return 1;
        } else {
            return 0;
        }
    }

}