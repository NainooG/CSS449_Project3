/*
 * ------------- Main.java ------------------------------------------
 * Purpose:
 *     - 
 * 
 * Functionality:
 *     - 
 * 
 * Assumptions:
 *     - Inputted .txt file data is correctly formatted
 * 
 * Author(s):
 *     - Khushnain Gobindpuri
 * ------------------------------------------------------------------
 */

import java.io.File;

/*
 * ------------- Main -----------------------------------------------
 * Purpose:
 *      - Tests Dinitz's Algorithm on a bipartite graph
 * 
 * Method Parameters:
 *      - String[] args
 * 
 * Pre-conditions:
 *      - None.
 * 
 * Post-conditions:
 *      - Returns the maximum bipartite matching of a graph
 * ------------------------------------------------------------------
 */
public static void main(String[] args) {
  File file = new File("program3data.txt");

  // Create Graph
  Graph graph = new Graph(file);

  // Run Dinitz's Algorithm
  graph.runDinitz();

  // Display Maximum Bipartite Matchings
  graph.displayMatchings();

}