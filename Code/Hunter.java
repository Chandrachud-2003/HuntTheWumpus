/**
 * Chandrachud Malali Gowda
 * CS231 A - Data Structures and Algorithms
 * 13th December 2021
 * Project 09: Hunt the Wumpus
 * Hunter.java
 */

// Importing the required libraries
import java.awt.Color;
import java.awt.Graphics;

public class Hunter {

    // Instance variables
    private Vertex currentPosition;

    // Constructor
    public Hunter(int x, int y) {
        this.currentPosition = new Vertex(x, y, true);
    }

    // Getters and setters

    // Getting the current position of the hunter
    public Vertex getCurrentPosition() {
        return this.currentPosition;
    }

    // Setting the current position of the hunter
    public void setCurrentPosition(int x, int y) {
        this.currentPosition.setPosition(x, y);
    }

   // Drawing the hunter
    public void draw(Graphics g, int scale) {
        g.setColor(Color.BLUE);
        int xpos = (int) currentPosition.getX() * scale;
        int ypos = (int) currentPosition.getY() * scale;
        g.drawOval(xpos + 12, ypos + 12, 40, 40);
        g.fillOval(xpos + 12, ypos + 12, 40, 40);
    }
    
}
