/*
 * ------------- Main.java ------------------------------------------
 * Purpose:
 *     Tests Dinitz's Algorithm on a given bipartite graph
 * 
 * Functionality:
 *     - Reads in a file
 *     - Creates Graph from .txt file data
 *     - Calls Dinitz's algorithm on Graph object
 *     - Displays maximum bipartite matchings
 * 
 * Assumptions:
 *     - Inputted .txt file data is correctly formatted
 * 
 * Note:
 *     - I observed that with some test cases, the program was too 
 *            greedy with its choices. This meant that it would not 
 *            achieve the maximum bipartite matching for bipartite
 *            graphs that require the use of reverse edges.
 *     - With the given test case, the correct number of matchings
 *            was found, but it is not guaranteed it will work
 *            for other test cases.
 * Author(s):
 *     - Khushnain Gobindpuri
 * ------------------------------------------------------------------
 */


import java.io.File;

public class Main {
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
}
