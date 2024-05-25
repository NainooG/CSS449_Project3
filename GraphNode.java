import java.util.ArrayList;

public class GraphNode {
    String name;
    int flow = 0;
    int capacity = 1;
    ArrayList<GraphNode> adj = new ArrayList<>();
    GraphNode reverseEdge = null;
    int level = -1;

    /*
    * ------------- GraphNode Constructor ----------------------------
    * Purpose:
    *      - Constructs GraphNode object given a String name
    * 
    * Method Parameters:
    *      - Input File file
    * 
    * Pre-conditions:
    *      - Input File file is correctly formatted
    * 
    * Post-conditions:
    *      - Returns constructed GraphNode object
    * ---------------------------------------------------------------
    */
    public GraphNode(String name) {
        this.name = name;
    }

    /*
    * ------------- Retrieve a GraphNode's name -------------------
    * Purpose:
    *      - Gets a GraphNode's name
    * 
    * Method Parameters:
    *      - None
    * 
    * Pre-conditions:
    *      - None.
    * 
    * Post-conditions:
    *      - Returns name of GraphNode
    * ---------------------------------------------------------------
    */
    public String getName() {
        return this.name;
    }

    /*
    * ------------- Add to A GraphNode's adjacency list -------------------
    * Purpose:
    *      - Add a new GraphNode to this current GraphNode's adjacency list
    * 
    * Method Parameters:
    *      - GraphNode node
    * 
    * Pre-conditions:
    *      - None.
    * 
    * Post-conditions:
    *      - New GraphNode has been added to this GraphNode's adjacency list
    * ---------------------------------------------------------------
    */
    public void addAdj(GraphNode node) {
        adj.add(node);
    }

    /*
    * ------------- Retrieve a GraphNode's level -------------------
    * Purpose:
    *      - Gets a GraphNode's level
    * 
    * Method Parameters:
    *      - None
    * 
    * Pre-conditions:
    *      - None.
    * 
    * Post-conditions:
    *      - Returns level of GraphNode
    * ---------------------------------------------------------------
    */
    public int getLevel() {
        return this.level;
    }

    /*
    * ------------- Set a GraphNode's level -------------------
    * Purpose:
    *      - Sets a GraphNode's level
    * 
    * Method Parameters:
    *      - int Level
    * 
    * Pre-conditions:
    *      - None.
    * 
    * Post-conditions:
    *      - Sets level of GraphNode
    * ---------------------------------------------------------------
    */
    public void setLevel(int level) {
        this.level = level;
    }

    /*
    * ------------- Retrieve a GraphNode's capacity -------------------
    * Purpose:
    *      - Gets a GraphNode's capacity
    * 
    * Method Parameters:
    *      - None
    * 
    * Pre-conditions:
    *      - None.
    * 
    * Post-conditions:
    *      - Returns capacity of GraphNode
    * ---------------------------------------------------------------
    */
    public int getCapacity() {
        return this.capacity;
    }

    /*
    * ------------- Set a GraphNode's Capacity -------------------
    * Purpose:
    *      - Sets a GraphNode's Capacity
    * 
    * Method Parameters:
    *      - int Capacity
    * 
    * Pre-conditions:
    *      - None.
    * 
    * Post-conditions:
    *      - Capacity of GraphNode has been set
    * ---------------------------------------------------------------
    */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /*
    * ------------- Set a GraphNode's Reverse Edge -------------------
    * Purpose:
    *      - Sets a GraphNode's Reverse Edge
    * 
    * Method Parameters:
    *      - GraphNode reverseEdge
    * 
    * Pre-conditions:
    *      - None.
    * 
    * Post-conditions:
    *      - Sets Reverse Edge of GraphNode
    * ---------------------------------------------------------------
    */
    public void setReverseEdge(GraphNode reverseEdge) {
        this.reverseEdge = reverseEdge;
    }
}