/*
  Bruce A. Maxwell
  CS 231 Spring 2020
  Project 10: Graphs 

  Graphs for testing Dijkstra's algorithm

  Modified by Naser Al Madi (Nov 2021)
*/

import java.util.ArrayList;

public class GraphTester {

    // test a graph with seven nodes connected in a loop
    public void test1() {

	// make the vertices
	Vertex v00 = new Vertex(0, 0, false );
	Vertex v02 = new Vertex(0, 2, false );
	Vertex v04 = new Vertex(0, 4, false );
	Vertex v24 = new Vertex(2, 4, false );
	Vertex v34 = new Vertex(3, 4, false );
	Vertex v22 = new Vertex(2, 2, false );
	Vertex v44 = new Vertex(4, 4, false );

	// build the graph
	Graph g = new Graph();

	g.addBiEdge( v00, v02 );
	g.addBiEdge( v00, v22 );
	g.addBiEdge( v02, v04 );
	g.addBiEdge( v04, v24 );
	g.addBiEdge( v24, v34 );
	g.addBiEdge( v34, v44 );
	g.addBiEdge( v22, v44 );

	// print out before shortestPath
	ArrayList<Vertex> set = g.getVertices();

	System.out.println("Before shortestPath");
	for( Vertex v: set ) {
	    System.out.println( v );
	}

	// run shortest path from node (0, 0)
	g.shortestPath( v00 );


	// print out after shortest path
	System.out.println("\nAfter shortestPath");
	for( Vertex v: set ) {
	    System.out.println( v );
	}

	// print out what the values should be
	System.out.println("Cost for node (0,0) should be: 0.0 - ");
	System.out.println("Cost for node (0,2) should be: 2.0");
	System.out.println("Cost for node (2,2) should be: 2.83");
	System.out.println("Cost for node (0,4) should be: 4.0");
	System.out.println("Cost for node (2,4) should be: 6.0");
	System.out.println("Cost for node (3,4) should be: 6.66");
	System.out.println("Cost for node (4,4) should be: 5.66");
    }


    // test a grid of 6 rows x 7 columns connected NSEW
    public void test2() {
	int Nx=7, Ny=6;
	Graph g = new Graph();
	    
	/* make a grid */
	Vertex grid[][] = new Vertex[Ny][Nx];

	for(int i=0;i<Ny;i++) {
	    for(int j=0;j<Nx;j++) {
		grid[i][j] = new Vertex( i, j, false );
	    }
	}

	// make a grid-connected graph
	for(int i=0;i<Ny;i++) {
	    for(int j=0;j<Nx;j++) {
		if( i > 0 ) {
		    g.addBiEdge( grid[i][j], grid[i-1][j] );
		}
		if( j > 0 ) {
		    g.addBiEdge( grid[i][j], grid[i][j-1] );
		}
		if( i < Ny-1 ) {
		    g.addBiEdge( grid[i][j], grid[i+1][j] );
		}
		if( j < Nx-1 ) {
		    g.addBiEdge( grid[i][j], grid[i][j+1] );
		}
	    }
	}

	// run shortest path from (1, 1)
	g.shortestPath( grid[1][1] );

	System.out.println("\nTest 2:  root = (1, 1)\n");
	String s = "";
	for(int i=0;i<Ny;i++) {
	    String t = "";
	    for(int j=0;j<Nx;j++) {
		s += grid[i][j].getCost();
		if( j < Nx-1 ) {s += " -- ";}
		t += " |     ";
	    }
	    s += "\n";
	    if( i < Ny-1 ) {
		s += t + "\n";
	    }
	}
	System.out.println(s);


	// run shortest path from (4, 3)
	g.shortestPath( grid[4][3] );

    	System.out.println("\nTest 2:  root = (4, 3)\n");
	s = "";
	for(int i=0;i<Ny;i++) {
	    String t = "";
	    for(int j=0;j<Nx;j++) {
		s += grid[i][j].getCost();
		if( j < Nx-1 ) {s += " -- ";}
		t += " |     ";
	    }
	    s += "\n";
	    if( i < Ny-1 ) {
		s += t + "\n";
	    }
	}
	System.out.println(s);
    }



    public static void main(String[] argv) {

	GraphTester gt = new GraphTester();

	gt.test1();

	gt.test2();


    }
    
}