import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    private int size;
    private int numOfEdges;
    private ArrayList<GraphNode> vertices;
    private int maxFlow = 0;
    private GraphNode source = new GraphNode("source");
    private GraphNode sink = new GraphNode("sink");
    private ArrayList<ArrayList<GraphNode>> matches = new ArrayList<>();

    /*
    * ------------- Graph Constructor ----------------------------
    * Purpose:
    *      - Constructs Graph object given a data .txt file
    * 
    * Method Parameters:
    *      - Input File file
    * 
    * Pre-conditions:
    *      - Input File file is correctly formatted
    * 
    * Post-conditions:
    *      - Returns constructed Graph object
    * ---------------------------------------------------------------
    */
    public Graph(File file) {
        try {
            Scanner sc = new Scanner(file);

            // Capture size
            this.size = Integer.parseInt(sc.nextLine());

            // Iniatializes vertices field
            this.vertices = new ArrayList<>();

            // Left side of bipartite graph
            // Connected to source
            for (int idx = 1; idx <= (size / 2); ++idx) {
                String name = sc.nextLine();

                GraphNode newNode = new GraphNode(name);
                newNode.setReverseEdge(source);

                vertices.add(newNode);
                source.addAdj(newNode);
            }

            // Right side of bipartite graph
            // Connected to sink
            for (int idx = ((size / 2) + 1); idx <= size; ++idx) {
                String name = sc.nextLine();
                GraphNode newNode = new GraphNode(name);

                newNode.addAdj(sink);
                sink.setReverseEdge(newNode);
                vertices.add(newNode);
            }

            // Read edges
            this.numOfEdges = Integer.parseInt(sc.nextLine());

            // Establish edges between vertices
            for (int idx = 0; idx < this.numOfEdges; ++idx) {
                String edge = sc.nextLine();
                Scanner sc1 = new Scanner(edge);

                GraphNode from = vertices.get(sc1.nextInt() - 1);
                GraphNode to = vertices.get(sc1.nextInt() - 1);

                from.addAdj(to);
                to.setReverseEdge(from);

                sc1.close();
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.err.println("File cannot be found");
        }
    }

    /*
    * ------------- Run Dinitz's Algorithm -------------------
    * Purpose:
    *      - Runs Dinitz's Algorithm on a Bipartite Graph
    * 
    * Method Parameters:
    *      - None
    * 
    * Pre-conditions:
    *      - Graph is constructed
    * 
    * Post-conditions:
    *      - Dinitz's Algorithm has been executed, maximum bipartite
    *           matchings have been found
    * ---------------------------------------------------------------
    */
    public void runDinitz() {
        boolean done = false;
        // Run BFS while a path from source to sink can be found
        while (!done) {
            done = bfs();
            ArrayList<GraphNode> path = new ArrayList<>();
            Set<GraphNode> visited = new HashSet<>();
            // Run dfs until no path can be found
            while (dfs(source, path, visited)) {
            }
        }
    }

    /*
    * ------------- Breadth First Search To Create Level Graph-------------------
    * Purpose:
    *      - Runs Breadth First Search to Create Level Graph
    * 
    * Method Parameters:
    *      - None
    * 
    * Pre-conditions:
    *      - Graph is constructed
    * 
    * Post-conditions:
    *      - Level Graph has been created
    * ---------------------------------------------------------------
    */
    public boolean bfs() {
        // Reset source level every new BFS iteration
        this.source.setLevel(0);

        // Reset levels of GraphNodes
        for (int i = 1; i < this.vertices.size(); i++) {
            this.vertices.get(i).setLevel(-1);
        }

        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(source);

        while (!queue.isEmpty()) {
            GraphNode currNode = queue.remove();

            for (int i = 0; i < currNode.adj.size(); i++) {
                GraphNode currNeighbor = currNode.adj.get(i);

                if (currNeighbor.getLevel() == -1 && currNeighbor.getCapacity() > 0) {
                    currNeighbor.setLevel(currNode.getLevel() + 1);
                    queue.add(currNeighbor);
                }
            }
        }
        // Return if the sink is reachable
        return this.sink.getLevel() != -1;
    }

    /*
    * ------------- Depth First Search for finding Blocking Flows -------------------
    * Purpose:
    *      - Runs Depth-First Search on a Bipartite Graph to find Blocking Flows
    *       
    * Method Parameters:
    *      - GraphNode currNode (source), 
    *      - ArrayList<GraphNode> path (to track current path)
    *      - Set<GraphNode> visited (so we do not visit already visited nodes)
    *
    * Pre-conditions:
    *      - Graph is constructed
    * 
    * Post-conditions:
    *      - Blocking flows have been found.
    * ---------------------------------------------------------------
    */
    public boolean dfs(GraphNode currNode, ArrayList<GraphNode> path, Set<GraphNode> visited) {
        
        // If current node is sink, augment flow and increment max flow
        if (currNode == sink) {
            this.matches.add(path);
            for (int i = 0; i < path.size(); i++) {
                if (path.get(i) != sink && path.get(i) != source) {
                    GraphNode l = path.get(i);
                    l.setCapacity(0);
                    l.reverseEdge.setCapacity(1);
                }
            }
            incrementMaxFlow();
            return true;
        } else {
            // Add to visited if current node isn't source
            if (currNode != source) {
                visited.add(currNode);
            }
            // If stuck, retreat
            if (currNode.getCapacity() == 0) {
                return false;
            } else {
                // Advance along to a node, we aren't stuck
                for (GraphNode neighbor : currNode.adj) {
                    if (!visited.contains(neighbor) && neighbor.getLevel() == currNode.getLevel() + 1
                            && neighbor.getCapacity() > 0) {
                        path.add(neighbor);
                        if (dfs(neighbor, path, visited)) {
                            return true;
                        }
                        // Remove neighbor if we can't find a path from it
                        path.remove(neighbor);
                        visited.remove(neighbor);
                    }
                }
            }
        }
        return false;
    }

    /*
    * ------------- Get Max Flow -------------------
    * Purpose:
    *      - Returns Max Flow for debugging purposes
    *       
    * Method Parameters:
    *      - None
    *
    * Pre-conditions:
    *      - Graph has been constructed
    *      - Level Graph has been created
    *      - Dinitz's Algorithm has been run
    *
    * Post-conditions:
    *      - Returns max flow of bipartite graph
    * ---------------------------------------------------------------
    */
    public int getMaxFlow() {
        return this.maxFlow;
    }

    /*
    * ------------- Increment Current Max Flow -------------------
    * Purpose:
    *      - Increments Max flow when augmenting path is found
    *       
    * Method Parameters:
    *      - None
    *
    * Pre-conditions:
    *      - Graph has been constructed
    *      - Level Graph has been created
    *      - Dinitz's Algorithm has been run
    *      - Path to augment is found
    * 
    * Post-conditions:
    *      - Increments max flow, indicating a path
    *           has been augmented
    * ---------------------------------------------------------------
    */
    public void incrementMaxFlow() {
        this.maxFlow += 1;
    }

    /*
    * ------------- Display Maximum Bipartite Matchings -------------------
    * Purpose:
    *      - Display Maximum bipartite matchings of a bipartite graph
    * 
    * Method Parameters:
    *      - None
    * 
    * Pre-conditions:
    *      - Graph has been constructed
    *      - Level Graph has been created
    *      - Dinitz's Algorithm has been executed and finished
    * 
    * Post-conditions:
    *      - Maximum Bipartite Matchings have been outputted
    * ---------------------------------------------------------------
    */
    public void displayMatchings() {
        // Clean up matchings
        ArrayList<GraphNode> match = this.matches.get(0);
        for (int i = 0; i < match.size(); i++) {
            if (match.get(i).name == "sink") {
                match.remove(i);
            }
        }

        // Display matchings
        for (int i = 0; i < match.size(); i += 2) {
            System.out.print(match.get(i).name + " / ");
            System.out.print(match.get(i + 1).name);

            System.out.println();
        }
    }
}