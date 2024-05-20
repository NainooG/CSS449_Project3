import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;

public class Graph {
    private int size;
    private ArrayList<LinkedList<GraphNode>> adjacencyList;
    private int numEdges;
    private String[] names;

    public Graph(File file) {
        try {
            Scanner scan = new Scanner(file);

            // method here
            this.size = Integer.parseInt(scan.nextLine());

            // i have arraylist of linkedlists,
            // create graphnode, insert into linkedlist, inssert into arraylist

            // method here
            this.adjacencyList = new ArrayList<>();

            // method here, for each adjacency list ,add the persons name
            for (int i = 0; i < this.size; i++) {
                LinkedList<GraphNode> list = new LinkedList<>();
                this.adjacencyList.add(list);
            }

            // method here
            this.names = new String[this.size];
            
            for (int i = 0; i < this.size; i++) {
                String name = scan.nextLine();
                this.names[i] = name;
            }

            this.numEdges = Integer.parseInt(scan.nextLine());

            for (int i = 0; i < this.numEdges; i++) {
                Scanner lineScanner = new Scanner(scan.nextLine());
                int from = lineScanner.nextInt();
                int to = lineScanner.nextInt();

                // have addEdge method
                LinkedList<GraphNode> currentList = this.adjacencyList.get(from - 1);

                GraphNode newNode = new GraphNode(this.names[to - 1]);

                currentList.add(newNode);
            }

            for(LinkedList<GraphNode> l : this.adjacencyList) {
                for (GraphNode g : l) {
                    System.out.print(g.name + " ");
                }
                System.out.println();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

