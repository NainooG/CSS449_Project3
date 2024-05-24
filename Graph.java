import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    private int size;
    private int numOfEdges;
    // private GraphNode[] vertices;
    private ArrayList<GraphNode> vertices;
    private int maxFlow = 0;
    private GraphNode source = new GraphNode("source");
    private GraphNode sink = new GraphNode("sink");
    // new
    private ArrayList<ArrayList<GraphNode>> matches = new ArrayList<>();

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
                newNode.reverseEdge = source;

                vertices.add(newNode);
                source.addAdj(newNode);

            }

            // Right side of bipartite graph
            // Connected to sink
            for (int idx = ((size / 2) + 1); idx <= size; ++idx) {
                String name = sc.nextLine();
                GraphNode newNode = new GraphNode(name);
                
                newNode.addAdj(sink);
                sink.reverseEdge = newNode; 
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
                to.reverseEdge = from;

                sc1.close();
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.err.println("File cannot be found");
        }
    }

    public void runDinitz() {
        boolean done = false;
        while (!done) {
            done = bfs();
            ArrayList<GraphNode> path = new ArrayList<>();
            Set<GraphNode> visited = new HashSet<>();
            while (dfs(source, path, visited)) {
            }
        }
    }

    public boolean bfs() {
        this.source.level = 0;

        for (int i = 1; i < this.vertices.size(); i++) {
            this.vertices.get(i).level = -1;
        }

        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(source);

        while (!queue.isEmpty()) {
            GraphNode currNode = queue.remove();

            for (int i = 0; i < currNode.adj.size(); i++) {
                GraphNode currNeighbor = currNode.adj.get(i);

                if (currNeighbor.level == -1 && currNeighbor.capacity > 0) {
                    currNeighbor.level = currNode.level + 1;
                    queue.add(currNeighbor);
                }
            }
        }
        return this.sink.level != -1;
    }

    public boolean dfs(GraphNode currNode, ArrayList<GraphNode> path, Set<GraphNode> visited) {
        if (currNode == sink) {
            this.matches.add(path);
            for (int i = 0; i < path.size(); i++) {
                if (path.get(i) != sink && path.get(i) != source) {
                    GraphNode l = path.get(i);
                    l.capacity = 0;
                    l.reverseEdge.capacity = 1;
                }
            }
            this.maxFlow += 1;
            return true;
        } else {
            if (currNode != source) {
                visited.add(currNode);
            }
            if (currNode.capacity == 0) {
                return false;
            } else {
                for (GraphNode neighbor : currNode.adj) {
                    if (!visited.contains(neighbor) && neighbor.level == currNode.level + 1 && neighbor.capacity > 0) {
                        if (dfs(neighbor, path, visited)) {
                            path.add(neighbor);
                            return true;
                        }
                        path.remove(neighbor);
                        visited.remove(neighbor);
                    }
                }
            }
        }
        return false;
    }

    public int getMaxFlow() {
        return this.maxFlow;
    }

    public void displayMatchings() {
        ArrayList<GraphNode> match = this.matches.get(0);
        for (int i = 0; i < match.size(); i++) {
            if (match.get(i).name == "sink") {
                match.remove(i);
            }
        }

        for (int i = 0; i < match.size(); i += 2) {
            System.out.print(match.get(i + 1).name + " / ");
            System.out.print(match.get(i).name);
            System.out.println();
        }
    }
}