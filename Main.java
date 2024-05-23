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
 *      - Tests 
 * 
 * Method Parameters:
 *      - String[] args
 * 
 * Pre-conditions:
 *      - None.
 * 
 * Post-conditions:
 *      - Returns the distance between closest pair of points
 * ------------------------------------------------------------------
 */
public static void main(String[] args) {
  File file = new File("program3data.txt");

  Graph graph = new Graph(file);

  graph.runDinitz();

  // int flow = graph.getMaxFlow();
  // System.out.println(flow);

  graph.displayMatchings();

}