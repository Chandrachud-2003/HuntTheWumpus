/**
 * Chandrachud Malali Gowda
 * CS231 A - Data Structures and Algorithms
 * 13th December 2021
 * Project 09: Hunt the Wumpus
 * Wumpus.java
 */

// Importing the required libraries
import java.awt.Graphics;
import java.awt.Color;

public class Wumpus {
    
    // Instance variables
    private Vertex homeVertex;

    // Constructor
    public Wumpus(int x, int y) {
        this.homeVertex = new Vertex(x, y, false);
    }

    // Setter for the visibility
    public void setVisibility(boolean visibility) {
        this.homeVertex.setVisible(visibility);
    }

    // Getter for visibility
    public boolean getVisibility() {
        return this.homeVertex.isVisible();
    }

    // Getter for the home vertex
    public Vertex getHomeVertex() {
        return this.homeVertex;
    }

    // Setter for the home vertex
    public void setHomeVertex(int x, int y) {
        this.homeVertex.setPosition(x, y);
    }

    // Draw function for the Wumpus
    public void draw(Graphics g, int scale) {
        g.setColor(Color.BLACK);
        int xpos = (int)homeVertex.getX()*scale;
        int ypos = (int)homeVertex.getY()*scale;
        g.drawOval(xpos + 12, ypos + 12, 40, 40);
        g.fillOval(xpos + 12, ypos + 12, 40, 40);
    }
}
