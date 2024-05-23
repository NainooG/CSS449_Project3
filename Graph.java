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
            // this.vertices = new GraphNode[size + 1];
            this.vertices = new ArrayList<>();

            // Left side of bipartite graph
            // Connected to source
            for (int idx = 1; idx <= (size / 2); ++idx) {
                String name = sc.nextLine();

                GraphNode newNode = new GraphNode(name);
                newNode.reverseEdge = source;

                // vertices[idx] = newNode;
                vertices.add(newNode);
                
                source.addAdj(newNode);

            }

            // Right side of bipartite graph
            // Connected to sink
            for (int idx = ((size / 2) + 1); idx <= size; ++idx) {
                String name = sc.nextLine();
                GraphNode newNode = new GraphNode(name);
                newNode.addAdj(sink);
                // sink.reverseEdge = newNode; // ??

                // vertices[idx] = newNode;
                vertices.add(newNode);
            }

            // Read edges
            this.numOfEdges = Integer.parseInt(sc.nextLine());

            // Establish edges between vertices
            for (int idx = 0; idx < this.numOfEdges; ++idx) {
                String edge = sc.nextLine();
                Scanner sc1 = new Scanner(edge);

                // GraphNode from = vertices[sc1.nextInt()];
                // GraphNode to = vertices[sc1.nextInt()];

                GraphNode from = vertices.get(sc1.nextInt() - 1);
                GraphNode to = vertices.get(sc1.nextInt() - 1);

                from.addAdj(to);
                to.reverseEdge = from;
            }

            // correctly prints out adjacency list for graph
            // for(int idx = 1; idx <= size; ++idx) {
            // // System.out.print(idx + " ");
            // ArrayList<GraphNode> curr = this.vertices[idx].adj;
            // System.out.print("Level: " + this.vertices[idx].level + " " +
            // this.vertices[idx].name);
            // for (GraphNode g : curr) {
            // System.out.print("Level: " + g.level + " " + g.name);
            // }
            // System.out.println();
            // }

            

            sc.close();

        } catch (FileNotFoundException e) {
            System.err.println("File cannot be found");
        }
    }

    public void runDinitz() {
        while (bfs()) {
            ArrayList<GraphNode> path = new ArrayList<>();
            Set<GraphNode> visited = new HashSet<>();
            while (dfs(source, path, visited)) {
                path.clear();
                visited.clear();
                // this.sink.level = 0;
            }
        }
    }

    public boolean bfs() {
        this.source.level = 0;
        // this.sink.level = 0;
        // for (int i = 1; i < this.vertices.length; i++) {
        //     this.vertices[i].level = -1;
        // }

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

        for (int idx = 0; idx < this.vertices.size(); ++idx) {
            // System.out.print(idx + " ");
            ArrayList<GraphNode> curr = this.vertices.get(idx).adj;
            System.out.print("Level: " + this.vertices.get(idx).level + " " +
                    this.vertices.get(idx).name);
            for (GraphNode g : curr) {
                System.out.print(" Level: " + g.level + " " + g.name);
            }
            System.out.println();
        }

        return this.sink.level != -1;
    }


    public boolean dfs(GraphNode currNode, ArrayList<GraphNode> path, Set<GraphNode> visited) {
        System.out.println(currNode.level);
        if (currNode == sink) {
            this.matches.add(path);
            for (int i = 0; i < path.size(); i++) {
                System.out.println("i am in the path");
                GraphNode l = path.get(i);
                l.capacity = 0;
                System.out.println(l.name);
                l.reverseEdge.capacity = 1;
            }
            this.maxFlow += 1;
            return true;
        } else {
            visited.add(currNode);
            if (currNode.capacity == 0) {
                currNode.level = -1;
                return false;
            } else {
                for (GraphNode neighbor : currNode.adj) {
                    // System.out.println("i am in the neighbors");
                    if (!visited.contains(neighbor) && neighbor.level == currNode.level + 1 && neighbor.capacity > 0) {
                        path.add(neighbor);
                        if (dfs(neighbor, path, visited)) {
                            return true;
                        }
                        path.remove(path.size() - 1);
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
        for (ArrayList<GraphNode> g : this.matches) {
            for (GraphNode l : g) {
                System.out.print(l);
            }
            System.out.println();
        }
    }

}