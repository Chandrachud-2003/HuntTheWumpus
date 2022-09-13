/**
 * Chandrachud Malali Gowda
 * CS231 A - Data Structures and Algorithms
 * 13th December 2021
 * Project 09: Hunt the Wumpus
 * Graph.java
 */

// Importing the required libraries
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Graph {

    // Instance variables
    private ArrayList<Vertex> vertices;

    // Constructor
    public Graph() {
        this.vertices = new ArrayList<Vertex>();
    }

    // Getter for the list of vertices
    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }

    // Returns the number of vertices in the graph
    public int vertexCount() {
        return this.vertices.size();
    }

    // Checks if the query vertex is in the graph
    public boolean inGraph(Vertex v) {

        for(Vertex vertex : this.vertices) {
            if(vertex.equals(v) || (v.getX() == vertex.getX() && v.getY() == vertex.getY())) {
                return true;
            }
        }

        return false;
    }

    // Adds two vertexes to the graph and adds an edge creating a uni-directional link
    public void addUniEdge(Vertex v1, Vertex v2) {

        if(!this.inGraph(v1)) {
            this.vertices.add(v1);
        }
        if(!this.inGraph(v2)) {
            this.vertices.add(v2);
        }

        v1.connect(v2);

    }

    // Adds two vertexes to the graph and adds an edge creating a uni-directional link with direction
    public void addUniEdge(Vertex v1, Vertex v2, Vertex.Direction direction) {

        if(!this.inGraph(v1)) {
            this.vertices.add(v1);
        }

        if(!this.inGraph(v2)) {
            this.vertices.add(v2);
        }

        v1.connect(v2, direction);

    }

    // Adds two vertexes to the graph and adds an edge creating a bi-directional link
    public void addBiEdge(Vertex v1, Vertex v2) {

        if(!this.inGraph(v1)) {
            this.vertices.add(v1);
        }

        if(!this.inGraph(v2)) {
            this.vertices.add(v2);
        }

        v1.connect(v2);
        v2.connect(v1);

    }

    // Adds two vertexes to the graph and adds an edge creating a bi-directional link with direction
    public void addBiEdge(Vertex v1, Vertex v2, Vertex.Direction direction) {

        if(!this.inGraph(v1)) {
            this.vertices.add(v1);
        }

        if(!this.inGraph(v2)) {
            this.vertices.add(v2);
        }

        v1.connect(v2, direction);

        if(direction == Vertex.Direction.EAST) {
            v2.connect(v1, Vertex.Direction.WEST);
        } else if(direction == Vertex.Direction.WEST) {
            v2.connect(v1, Vertex.Direction.EAST);
        } else if(direction == Vertex.Direction.NORTH) {
            v2.connect(v1, Vertex.Direction.SOUTH);
        } else if(direction == Vertex.Direction.SOUTH) {
            v2.connect(v1, Vertex.Direction.NORTH);
        }

    }

    // Implements Dijkstra's algorithm to find the shortest path between two vertexes
    public void shortestPath(Vertex v0) {

        // Initializing the vertices
        for(Vertex vertex : this.vertices) {
            vertex.setCost(Double.POSITIVE_INFINITY);
            vertex.setVisited(false);
            vertex.setParent(null);
        }

        // Creating a priority queue to hold the vertices
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();

        // Set the distance of the starting vertex to 0
        v0.setCost(0);
        // Adding the starting vertex to the priority queue
        pq.add(v0);

        while(!pq.isEmpty()) {

            // Removing the vertex with the minimum distance from the priority queue
            Vertex v = pq.remove();

            // Marking the vertex as visited
            v.setVisited(true);

            // Iterating through the vertex's neighbors
            for(Vertex w : v.getAdjacencyList()) {

                // Computing the distance of the neighbor
                double distance = v.distance(w);

                // If the neighbor is not visited and the distance is less than the current distance
                if(!w.isVisited() && (v.getCost() + distance < w.getCost())) {
                    w.setCost(v.getCost() + distance);
                    w.setParent(v);
                    pq.add(w);
                }
            }
        }
    } 

    // Checks if there is a path between two vertexes
    public boolean hasPath(Vertex v0, Vertex v1) {

        // Initializing the vertices
        for(Vertex vertex : this.vertices) {
            vertex.setVisited(false);
        }

        // Creating a stack to hold the vertices
        Stack<Vertex> stack = new Stack<Vertex>();

        // Adding the starting vertex to the stack
        stack.push(v0);

        while(!stack.isEmpty()) {

            // Removing the vertex from the stack
            Vertex v = stack.pop();

            // Marking the vertex as visited
            v.setVisited(true);

            // Iterating through the vertex's neighbors
            for(Vertex w : v.getAdjacencyList()) {

                // If the neighbor is not visited
                if(!w.isVisited()) {

                    // If the neighbor is the target vertex
                    if(w.equals(v1)) {
                        return true;
                    }

                    // Adding the neighbor to the stack
                    stack.push(w);
                }
            }
        }

        return false;
    }

    // Main method to test the graph
    public static void main(String[] args) {

        // Creating the graph
        Graph graph = new Graph();

        // Adding vertices to the Graph
        Vertex a = new Vertex(0, 0);
        Vertex b = new Vertex(3, 4);
        Vertex c = new Vertex(8, 6);
        Vertex d = new Vertex(15, 4);
        Vertex e = new Vertex(15, 20);
        Vertex f = new Vertex(20, 0);

        graph.addUniEdge(a, b);
        graph.addUniEdge(a, c);
        graph.addUniEdge(b, d);
        graph.addUniEdge(b, e);
        graph.addUniEdge(c, d);
        graph.addUniEdge(c, e);
        graph.addUniEdge(d, e);
        graph.addUniEdge(d, f);
        graph.addUniEdge(e, f);

        // Printing out the graph before running Djiksra's algorithm
        ArrayList<Vertex> vertexes = graph.getVertices();

	    System.out.println("Before Shortest Path: ");
	    for( Vertex v: vertexes ) {
	        System.out.println( v );
	    }

        // Running Djiksra's algorithm from the starting vertex (0, 0)
        graph.shortestPath(a);

        // Printing out the graph after running Djiksra's algorithm
        System.out.println("After shortest path: \n");


        // Printing out what the parent and cost of each node should be:
        System.out.println("Parent of Node A should be nothing : " + (a.getParent() == null));
        System.out.println("Cost of Node A should be 0 : " + a.getCost());

        System.out.println("Parent of Node B should be Node A : " + (b.getParent() == a));
        System.out.println("Cost of Node B should be 5 : " + b.getCost());

        System.out.println("Parent of Node C should be Node A : " + (c.getParent() == a));
        System.out.println("Cost of Node C should be 10 : " + c.getCost());

        System.out.println("Parent of Node D should be Node B : " + (d.getParent() == b));
        System.out.println("Cost of Node D should be 17 : " + d.getCost());

        System.out.println("Parent of Node E should be Node B : " + (e.getParent() == b));
        System.out.println("Cost of Node E should be 25 : " + e.getCost());

        System.out.println("Parent of Node F should be Node D : " + (f.getParent() == d));
        System.out.println("Cost of Node F should be 23 : " + f.getCost());

    }

}
