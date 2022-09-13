/**
 * Chandrachud Malali Gowda
 * CS231 A - Data Structures and Algorithms
 * 13th December 2021
 * Project 09: Hunt the Wumpus
 * HuntTheWumpus.java
 */

// Importing the required libraries
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

public class HuntTheWumpus {
    
    // Instance variables
    private JFrame win;
    private LandscapePanel canvas;    
    private Landscape scape; 
    private int scale;

    private Graph graph;
    private Hunter hunter;
    private Wumpus wumpus;

    /** fields related to demo of mouse interaction **/
    // Unless you have a good reason to report the mouse position in
    // HuntTheWumpus, you should remove these fields and all the
    // code that affects them.
    // There here to demonstrate how you would add a message to the bottom
    // of the window. For HuntTheWumpus, you may want to use it to indicate
    // that the Hunter is armed or close to the Wumpus, or dead.
    private JLabel fieldX; // Label field 1, displays the X location of the mouse 
    private JLabel fieldY; // Label field 2, displays the Y location of the mouse 
    private JLabel infoField; // Label infoFlied, displays the current state of the game and when the hunter's arrow is armed or not
    private JButton pauseButton; // Button to pause the game
    private JButton quitButton; // Button to quit the game
    private JButton restartButton; // Button to restart the game

    // controls whether the game is playing or exiting
    private enum PlayState { PLAY, STOP, PAUSE, FINISHED, REPLAY }
    private PlayState state;
    private boolean armed;

    /**
     * Creates the main window
     * @param scape the Landscape that will hold the objects we display
     * @param scale the size of each grid element
     **/		 
    public HuntTheWumpus() {

        // Creating the game
        this.createGame();
        
        // Make the main window
        this.win = new JFrame("Basic Interactive Display");
        this.win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);

        // make the main drawing canvas (this is the usual
        // LandscapePanel we have been using). and put it in the window
        this.canvas = new LandscapePanel( this.scape.getWidth(),
					                                        this.scape.getHeight() );
        this.win.add( this.canvas, BorderLayout.CENTER );
        this.win.pack();

        // make the labels and a button and put them into the frame
        // below the canvas.
        this.fieldX = new JLabel("X");
        this.fieldY = new JLabel("Y");
        this.infoField = new JLabel("");
        this.quitButton = new JButton("Quit");
        this.quitButton.setFocusable(false);
        this.pauseButton = new JButton("Pause");
        this.pauseButton.setFocusable(false);
        this.restartButton = new JButton("Replay");
        this.restartButton.setFocusable(false);
        JPanel panel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
        panel.add( this.infoField );
        panel.add( this.restartButton );
        panel.add( this.pauseButton );
        panel.add( this.fieldX );
        panel.add( this.fieldY );
        panel.add( this.quitButton );
        this.win.add( panel, BorderLayout.SOUTH);
        this.win.pack();

        // Add key and button controls.
        // We are binding the key control object to the entire window.
        // That means that if a key is pressed while the window is
        // in focus, then control.keyTyped will be executed.
        // Because we are binding quit (the button) to control, control.actionPerformed will
        // be called if the quit button is pressed. If you make more than one button,
        // then the same function will be called. Use an if-statement in the function
        // to determine which button was pressed (check out Control.actionPerformed and
        // this advice should make sense)
        Control control = new Control();
        this.win.addKeyListener(control);
        this.win.setFocusable(true);
        this.win.requestFocus();
        this.quitButton.addActionListener( control );
        this.pauseButton.addActionListener( control );
        this.restartButton.addActionListener( control );

        // for mouse control
        // Make a MouseControl object and then bind it to the canvas
        // (the part that displays the Landscape). When the mouse
        // enters, exits, moves, or clicks in the canvas, the appropriate
        // method will be called.
        MouseControl mc = new MouseControl();
        this.canvas.addMouseListener( mc );
        this.canvas.addMouseMotionListener( mc );

        // The last thing to do is make it all visible.
        this.win.setVisible( true );

    }

    // Creating a new hunter and wumpus and creating new a new graph
    public void createGame() {
        // Creating the hunter and the wumpus objects
        this.hunter = new Hunter(4, 0);
        this.wumpus = new Wumpus(5, 2);

        // The game should begin in the play state.
        this.state = PlayState.PLAY;
        this.armed = false; 

        // This is test code that adds a few vertices.
        // You will need to update this to make a Graph first, then
        // add the vertices to the Landscape.
        Vertex v1 = new Vertex( 4, 0 );
        v1.setVisible( true );
        v1.setCost( 3 );
        Vertex v2 = new Vertex( 4, 1 );
        v2.setVisible( false );
        v2.setCost( 2 );
        Vertex v3 = new Vertex( 4, 2 );
        v3.setVisible( false );
        v3.setCost( 1 );
        Vertex v4 = new Vertex( 4, 3 );
        v4.setVisible( false );
        v4.setCost( 0 );
        Vertex v5 = new Vertex( 4, 4 );
        v5.setVisible( false );
        v5.setCost( 0 );
        Vertex v6 = new Vertex( 3, 4 );
        v6.setVisible( false );
        v6.setCost( 0 );
        Vertex v7 = new Vertex( 3, 2 );
        v7.setVisible( false );
        v7.setCost( 0 );
        Vertex v8 = new Vertex( 5, 2 );
        v8.setVisible( false );
        v8.setCost( 0 );


        v1.connect( v2, Vertex.Direction.EAST );
        v2.connect( v1, Vertex.Direction.WEST );
        v2.connect( v3, Vertex.Direction.EAST );
        v3.connect( v2, Vertex.Direction.WEST );
        v3.connect( v4, Vertex.Direction.EAST );
        v4.connect( v3, Vertex.Direction.WEST );
        v4.connect( v5, Vertex.Direction.EAST );
        v5.connect( v4, Vertex.Direction.WEST );
        v5.connect( v6, Vertex.Direction.EAST );
        v6.connect( v5, Vertex.Direction.WEST );
        v6.connect( v7, Vertex.Direction.EAST );
        v7.connect( v6, Vertex.Direction.WEST );
        v8.connect( v3, Vertex.Direction.WEST );
        v3.connect( v8, Vertex.Direction.EAST );
        

        /**
         * // Creating the hunter and the wumpus objects
        this.hunter = new Hunter(5, 3);
        this.wumpus = new Wumpus(5, 3);

        // The game should begin in the play state.
        this.state = PlayState.PLAY;
        this.armed = false; 

        // This is test code that adds a few vertices.
        // You will need to update this to make a Graph first, then
        // add the vertices to the Landscape.
        Vertex v1 = new Vertex( 5, 3 );
        v1.setVisible( true );
        v1.setCost( 0 );
        Vertex v2 = new Vertex( 5, 4 );
        v2.setVisible( false );
        v2.setCost( 1 );
        Vertex v3 = new Vertex( 5, 5 );
        v3.setVisible( false );
        v3.setCost( 2 );
        Vertex v4 = new Vertex( 5, 6 );
        v4.setVisible( false );
        v4.setCost( 3 );
        Vertex v5 = new Vertex( 5, 7 );
        v5.setVisible( false );
        v5.setCost( 4 );
        Vertex v6 = new Vertex( 4, 7 );
        v6.setVisible( false );
        v6.setCost( 5 );
        Vertex v7 = new Vertex( 3, 7 );
        v7.setVisible( false );
        v7.setCost( 6 );
        Vertex v8 = new Vertex( 5, 6 );
        v8.setVisible( false );
        v8.setCost( 3 );


        v1.connect( v2, Vertex.Direction.EAST );
        v2.connect( v1, Vertex.Direction.WEST );

        v2.connect( v3, Vertex.Direction.EAST );
        v3.connect( v2, Vertex.Direction.WEST );

        v3.connect( v4, Vertex.Direction.EAST );
        v4.connect( v3, Vertex.Direction.WEST );

        v4.connect( v5, Vertex.Direction.EAST );
        v5.connect( v4, Vertex.Direction.WEST );

        v5.connect( v6, Vertex.Direction.EAST );
        v6.connect( v5, Vertex.Direction.WEST );

        v6.connect( v7, Vertex.Direction.EAST );
        v7.connect( v6, Vertex.Direction.WEST );

        v3.connect( v8, Vertex.Direction.EAST );
        v8.connect( v3, Vertex.Direction.WEST );
         */

        // Create the elements of the Landscape and the game.
        this.scale = 64; // determines the size of the grid
        this.scape = new Landscape(this.scale*10, this.scale*7, this.hunter, this.wumpus);

        this.graph = new Graph();
        this.graph.addBiEdge(v1, v2);
        this.graph.addBiEdge(v2, v3);
        this.graph.addBiEdge(v3, v4);
        this.graph.addBiEdge(v4, v5);
        this.graph.addBiEdge(v5, v6);
        this.graph.addBiEdge(v6, v7);
        this.graph.addBiEdge(v3, v8);
        this.scape.addBackgroundAgent( v1 );
        this.scape.addBackgroundAgent( v2 );
        this.scape.addBackgroundAgent( v3 );
        this.scape.addBackgroundAgent( v4 );   
        this.scape.addBackgroundAgent( v5 ); 
        this.scape.addBackgroundAgent( v6 ); 
        this.scape.addBackgroundAgent( v7 ); 
        this.scape.addBackgroundAgent( v8 ); 
    }

    private class LandscapePanel extends JPanel {
		
        /**
         * Creates the drawing canvas
         * @param height the height of the panel in pixels
         * @param width the width of the panel in pixels
         **/
        public LandscapePanel(int height, int width) {
            super();
            this.setPreferredSize( new Dimension( width, height ) );
            this.setBackground(Color.white);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         * 
         * @param g		the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            scape.draw( g, scale );
        }
    } // end class LandscapePanel

    // This is the class where you define functions that are 
    // executed when certain mouse events take place.
    private class MouseControl extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) {
            fieldX.setText( "" + e.getPoint().x );
            fieldY.setText( "" + e.getPoint().y );
        }

        public void mouseDragged(MouseEvent e) {
            fieldX.setText( "" + e.getPoint().x );
            fieldY.setText( "" + e.getPoint().y );
        }
        
        public void mousePressed(MouseEvent e) {
            System.out.println( "Pressed: " + e.getClickCount() );
        }

        public void mouseReleased(MouseEvent e) {
            System.out.println( "Released: " + e.getClickCount());
        }

        public void mouseEntered(MouseEvent e) {
            System.out.println( "Entered: " + e.getPoint() );
        }

        public void mouseExited(MouseEvent e) {
            System.out.println( "Exited: " + e.getPoint() );
        }

        public void mouseClicked(MouseEvent e) {
    	    System.out.println( "Clicked: " + e.getClickCount() );
        }
    } // end class MouseControl

    private class Control extends KeyAdapter implements ActionListener {

        public void keyTyped(KeyEvent e) {

            // Getting the x and y coordinates
            int x = hunter.getCurrentPosition().getX();
            int y = hunter.getCurrentPosition().getY();

            // Checking if the game should quit
            if( ("" + e.getKeyChar()).equalsIgnoreCase("q") ) {
                state = PlayState.STOP;
            } 

            // Moving the Hunter upwards
            else if(("" + e.getKeyChar()).equalsIgnoreCase("w") && graph.inGraph(new Vertex(x, y - 1)) && state == PlayState.PLAY) {
                if(armed) {
                    if(wumpus.getHomeVertex().getX() == x && wumpus.getHomeVertex().getY() == y - 1) {
                        infoField.setText("Wumpus Hit! You Win");
                        infoField.setForeground(Color.GREEN);
                    } else {
                        infoField.setText("Wumpus Missed! You Lose");
                        infoField.setForeground(Color.RED);
                    }
                    wumpus.getHomeVertex().setVisible(true);
                    wumpus.setVisibility(true);
                    scape.getBackgroundAgent(wumpus.getHomeVertex().getX(), wumpus.getHomeVertex().getY()).setVisible(true);
                    scape.getBackgroundAgent(x, y - 1).setVisible(true);
                    state = PlayState.FINISHED;
                } else {
                    scape.getBackgroundAgent(x, y - 1).setVisible(true);
                    hunter.setCurrentPosition(x, y - 1);

                    if(x == wumpus.getHomeVertex().getX() && y - 1 == wumpus.getHomeVertex().getY()) {
                        infoField.setText("Wumpus Ate You! You Lost");
                        infoField.setForeground(Color.RED);
                        state = PlayState.FINISHED;
                        wumpus.getHomeVertex().setVisible(true);
                    }
                }
            } 
            // Moving the Hunter to the left
            else if(("" + e.getKeyChar()).equalsIgnoreCase("a") && graph.inGraph(new Vertex(x - 1, y)) && state == PlayState.PLAY) {
                if(armed) {
                    if(wumpus.getHomeVertex().getX() == x - 1 && wumpus.getHomeVertex().getY() == y) {
                        infoField.setText("Wumpus Hit! You Win");
                        infoField.setForeground(Color.GREEN);
                    } else {
                        infoField.setText("Wumpus Missed! You Lose");
                        infoField.setForeground(Color.RED);
                    }
                    wumpus.getHomeVertex().setVisible(true);
                    wumpus.setVisibility(true);
                    scape.getBackgroundAgent(wumpus.getHomeVertex().getX(), wumpus.getHomeVertex().getY()).setVisible(true);
                    scape.getBackgroundAgent(x - 1, y).setVisible(true);
                    state = PlayState.FINISHED;
                } else {
                    scape.getBackgroundAgent(x - 1, y).setVisible(true);
                    hunter.setCurrentPosition(x - 1, y);

                    if(x - 1 == wumpus.getHomeVertex().getX() && y == wumpus.getHomeVertex().getY()) {
                        infoField.setText("Wumpus Ate You! You Lost");
                        infoField.setForeground(Color.RED);
                        state = PlayState.FINISHED;
                        wumpus.getHomeVertex().setVisible(true);
                    }
                }
            } 

            // Moving the Hunter downwards
            else if(("" + e.getKeyChar()).equalsIgnoreCase("s") && graph.inGraph(new Vertex(x, y + 1)) && state == PlayState.PLAY) {
                if(armed) {
                    if(wumpus.getHomeVertex().getX() == x && wumpus.getHomeVertex().getY() == y + 1) {
                        infoField.setText("Wumpus Hit! You Win");
                        infoField.setForeground(Color.GREEN);
                    } else {
                        infoField.setText("Wumpus Missed! You Lose");
                        infoField.setForeground(Color.RED);
                    }
                    wumpus.getHomeVertex().setVisible(true);
                    wumpus.setVisibility(true);
                    scape.getBackgroundAgent(wumpus.getHomeVertex().getX(), wumpus.getHomeVertex().getY()).setVisible(true);
                    scape.getBackgroundAgent(x, y + 1).setVisible(true);
                    state = PlayState.FINISHED;
                } else {
                    hunter.setCurrentPosition(x, y + 1);
                    scape.getBackgroundAgent(x, y + 1).setVisible(true);

                    if(x == wumpus.getHomeVertex().getX() && y + 1 == wumpus.getHomeVertex().getY()) {
                        infoField.setText("Wumpus Ate You! You Lost");
                        infoField.setForeground(Color.RED);
                        state = PlayState.FINISHED;
                        wumpus.getHomeVertex().setVisible(true);
                    }
                }
            } 
            // Moving the Hunter to the right
            else if(("" + e.getKeyChar()).equalsIgnoreCase("d") && graph.inGraph(new Vertex(x + 1, y)) && state == PlayState.PLAY) {
                if(armed) {
                    if(wumpus.getHomeVertex().getX() == x + 1 && wumpus.getHomeVertex().getY() == y) {
                        infoField.setText("Wumpus Hit! You Win");
                        infoField.setForeground(Color.GREEN);
                    } else {
                        infoField.setText("Wumpus Missed! You Lose");
                        infoField.setForeground(Color.RED);
                    }
                    wumpus.getHomeVertex().setVisible(true);
                    wumpus.setVisibility(true);
                    scape.getBackgroundAgent(wumpus.getHomeVertex().getX(), wumpus.getHomeVertex().getY()).setVisible(true);
                    scape.getBackgroundAgent(x + 1, y).setVisible(true);
                    state = PlayState.FINISHED;
                } else {
                    hunter.setCurrentPosition(x + 1, y);
                    scape.getBackgroundAgent(x + 1, y).setVisible(true);

                    if(x + 1 == wumpus.getHomeVertex().getX() && y == wumpus.getHomeVertex().getY()) {
                        infoField.setText("Wumpus Ate You! You Lost");
                        infoField.setForeground(Color.RED);
                        state = PlayState.FINISHED;
                        wumpus.getHomeVertex().setVisible(true);
                    }
                }
            }

            // Detecting when the space bar is clicked and the hunter arms his arrow
            else if(("" + e.getKeyChar()).equalsIgnoreCase(" ") && state == PlayState.PLAY) {
                if(!armed) {
                    infoField.setText("Arrow armed!");
                    infoField.setForeground(Color.RED);
                    armed = true;
                } else {
                    armed = false;
                    infoField.setText("Arrow disarmed!");
                }
            } 
        
        }

        public void actionPerformed(ActionEvent event) {
            // If the Quit button was pressed
            if( event.getActionCommand().equalsIgnoreCase("Quit") ) {
		        System.out.println("Quit button clicked");
                state = PlayState.STOP;
            } else if(event.getActionCommand().equalsIgnoreCase("Pause")) {
                System.out.println("Pause button clicked");
                state = PlayState.PAUSE;
                pauseButton.setText("Resume");
            } else if(event.getActionCommand().equalsIgnoreCase("Resume")) {
                System.out.println("Resume button clicked");
                state = PlayState.PLAY;
                pauseButton.setText("Pause");
            } else if(event.getActionCommand().equalsIgnoreCase("Replay")) {
                System.out.println("Replay button clicked");
                state = PlayState.REPLAY;
                pauseButton.setText("Pause");
                armed = false;
                infoField.setText("");
            }
        }
    
        
    } // end class Control

    // Repaints the JFrame
    public void repaint() {
    	this.win.repaint();
    }

    // Disposes of the JFrame
    public void dispose() {
	    this.win.dispose();
    }

    // Restarts the game
    public void restart() {
        // Setting up the UI elements
        this.pauseButton.setText("Pause");
        this.infoField.setText("");
        this.createGame();
    }

    // Main method to play the game
    public static void main(String[] argv) throws InterruptedException {
        HuntTheWumpus game = new HuntTheWumpus();
        // Running the game as long as PLAY is true
        while (game.state == PlayState.PLAY || game.state == PlayState.PAUSE || game.state == PlayState.FINISHED || game.state == PlayState.REPLAY) {
            game.repaint();
            if(game.state == PlayState.REPLAY) {
                game.restart();
            }
            Thread.sleep(33);
        }
        // Disposing of the JFrame
        System.out.println("Disposing window");
        game.dispose();
    }

}
