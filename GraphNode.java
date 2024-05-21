import java.util.ArrayList;

public class GraphNode {
    String name;
    String match;
    int flow = 0;
    int capacity = 1;
    ArrayList<GraphNode> adj = new ArrayList<>();
    GraphNode reverseEdge = null;
    int level = -1;

    public GraphNode(String name) {
        this.name = name;
    }

    public void addAdj(GraphNode node) {
        adj.add(node);
    }
}