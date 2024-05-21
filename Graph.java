import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;

public class Graph {
  private int size;
  private int numOfEdges;
  private GraphNode[] vertices;
  private GraphNode source = new GraphNode("source");
  private GraphNode sink = new GraphNode("sink");

  public Graph(File file) {
    try {
      Scanner sc = new Scanner(file);

      // Capture size
      this.size = Integer.parseInt(sc.nextLine());

      // Iniatializes vertices field
      this.vertices = new GraphNode[size + 1];

      // Left side of bipartite graph
      // Connected to source
      for(int idx = 1; idx <= (size / 2); ++idx) {
        String name = sc.nextLine();
        GraphNode newNode = new GraphNode(name);
        vertices[idx] = newNode;
        source.addAdj(newNode);
      }

      // Right side of bipartite graph
      // Connected to sink
      for(int idx = ((size / 2) + 1); idx <= size; ++idx) {
        String name = sc.nextLine();
        GraphNode newNode = new GraphNode(name);
        newNode.addAdj(sink);
        vertices[idx] = newNode;
      }

      // Read edges
      this.numOfEdges = Integer.parseInt(sc.nextLine());

      // Establish edges between vertices
      for(int idx = 0; idx < this.numOfEdges; ++idx) {
        String edge = sc.nextLine();
        Scanner sc1 = new Scanner(edge);

        GraphNode from = vertices[sc1.nextInt()];
        GraphNode to = vertices[sc1.nextInt()];

        from.addAdj(to);
      }

      for(int idx = 1; idx <= size; ++idx) {
        System.out.println(vertices[idx].name);
      }

      sc.close();

    } catch (FileNotFoundException e) {
      System.err.println("File cannot be found");
    }
  }
}
