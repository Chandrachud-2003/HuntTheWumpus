/**
 * Chandrachud Malali Gowda
 * CS231 A - Data Structures and Algorithms
 * 13th December 2021
 * Project 09: Hunt the Wumpus
 * Landscape.java
 */

// Importing the required libraries
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

public class Landscape {

    // Instance variables
    private int width;
    private int height;
    private ArrayList<Vertex> vertices;

    private Hunter hunter;
    private Wumpus wumpus;

    // Constructor
    public Landscape(int width, int height) {
        this.width = width;
        this.height = height;
        this.vertices = new ArrayList<>();
        this.hunter = new Hunter(0, 0);
        this.wumpus = new Wumpus(0, 0);
    }

    // Constructor with the hunter and the wumpus
    public Landscape(int width, int height, Hunter hunter, Wumpus wumpus) {
        this.width = width;
        this.height = height;
        this.vertices = new ArrayList<>();
        this.hunter = hunter;
        this.wumpus = wumpus;
    }

    // Getters and setters

    // Getter for the width
    public int getWidth() {
        return this.width;
    }

    // Setter for the width
    public void setWidth(int width) {
        this.width = width;
    }

    // Getter for the height
    public int getHeight() {
        return this.height;
    }

    // Setter for the height
    public void setHeight(int height) {
        this.height = height;
    }

    // Function to add a vertex to the vertex lsit
    public void addBackgroundAgent(Vertex vertex) {
        this.vertices.add(vertex);
    }

    // Function to get a vertex from the vertex list
    public Vertex getBackgroundAgent(int x, int y) {
        for(Vertex v : this.vertices) {
            if(v.getX() == x && v.getY() == y) {
                return v;
            }
        }

        return null;
    }

    // Function to loop through the CheckoutAgents, calling the draw method on each
    public void draw(Graphics g, int scale) {

        // Drawing the vertices as long as they arwe set to visible
        for(Vertex vertex : this.vertices) {
            if(vertex.isVisible()) {
                vertex.draw(g, scale);
            }
        }

        // Drawing the Hunter
        this.hunter.draw(g, scale);

        // Drawing the Wumpus if the homeVertex is set to visible
        if(this.wumpus.getHomeVertex().isVisible()) {
            this.wumpus.draw(g, scale);
        }
    }


}
