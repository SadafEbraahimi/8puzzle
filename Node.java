import java.util.ArrayList;

public class Node implements Comparable<Node> {

    private boolean visited;
    private String state;
    private ArrayList<Node> children;
    private Node parent;
    private int costSoFar;
    private int costToMoveToThisNode;
    private int expectedCostTillGoal;
    private int depth;
    private boolean deadEnd;

    public Node(String state) {
        this.state = state;
        this.deadEnd = false;
    }

    public Node(String state, Node parent) {
        this.state = state;
        this.visited = false;
        this.parent = parent;
        this.deadEnd = false;
    }

    public void setDeadEnd(boolean deadEnd) { this.deadEnd = deadEnd; }

    public void setCostToMoveToThisNode(int costToMoveToThisNode) {
        this.costToMoveToThisNode = costToMoveToThisNode;
    }

    public void setExpectedCostTillGoal(int expectedCostTillGoal) {
        this.expectedCostTillGoal = expectedCostTillGoal;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setCostSoFar(int costSoFar) { this.costSoFar = costSoFar; }

    public boolean isVisited() {
        return visited;
    }

    public int getCostToMoveToThisNode() {
        return costToMoveToThisNode;
    }

    public int getExpectedCostTillGoal() {
        return expectedCostTillGoal;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public String getState() {
        return state;
    }

    public int getDepth() {
        return depth;
    }

    public int getCostSoFar() { return costSoFar; }

    public void addChild(Node node) {
        this.children.add(node);
    }

    public boolean isDeadEnd() { return deadEnd; }

    @Override
    public int compareTo(Node that) {
        if (costSoFar > that.costSoFar)
            return 1;
        else if (costSoFar < that.costSoFar )
            return - 1;
        else
            return 0;
    }

    /*This method is used to check if a node is already expanded or not.
    *  If the state of two nodes are the same, we consider them the same.
    */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!Node.class.isAssignableFrom(obj.getClass()))
            return false;

        final Node other = (Node) obj;
        if (this.state.equals(other.getState()))
            return true;
        return false;
    }

    /* Returns an ArrayList of all the children of the specified node. It creates the children by swapping
    *  the 0 in the father node with the 0 in the child node.
    * */

    public static ArrayList<Node> getSuccessors(Node node) {
        ArrayList<Node> successors = new ArrayList<>();
        String state = node.getState();

        if (node.getState().indexOf("0") == 0) {
            successors.add(new Node(swap(state, 0, 1)));    //right
            successors.add(new Node(swap(state, 0, 3)));    //down
        }

        else if (node.getState().indexOf("0") == 1) {
            successors.add(new Node(swap(state, 1, 0)));    //left
            successors.add(new Node(swap(state, 1, 2)));    //right
            successors.add(new Node(swap(state, 1, 4)));    //down
        }

        else if (node.getState().indexOf("0") == 2) {
            successors.add(new Node(swap(state, 2, 1)));    //left
            successors.add(new Node(swap(state, 2, 5)));    //down
        }

        else if (node.getState().indexOf("0") == 3) {
            successors.add(new Node(swap(state, 3, 0)));    //up
            successors.add(new Node(swap(state, 3, 4)));    //right
            successors.add(new Node(swap(state, 3, 6)));    //down
        }

        else if (node.getState().indexOf("0") == 4) {
            successors.add(new Node(swap(state, 4, 3)));    //left
            successors.add(new Node(swap(state, 4, 1)));    //up
            successors.add(new Node(swap(state, 4, 5)));    //right
            successors.add(new Node(swap(state, 4, 7)));    //down
        }

        else if (node.getState().indexOf("0") == 5) {
            successors.add(new Node(swap(state, 5, 4)));    //left
            successors.add(new Node(swap(state, 5, 2)));    //up
            successors.add(new Node(swap(state, 5, 8)));    //down
        }

        else if (node.getState().indexOf("0") == 6) {
            successors.add(new Node(swap(state, 6, 3)));    //up
            successors.add(new Node(swap(state, 6, 7)));    //right
        }

        else if (node.getState().indexOf("0") == 7) {
            successors.add(new Node(swap(state, 7, 6)));    //left
            successors.add(new Node(swap(state, 7, 4)));    //up
            successors.add(new Node(swap(state, 7, 8)));    //right

        }

        else if (node.getState().indexOf("0") == 8) {
            successors.add(new Node(swap(state, 8, 7)));    //left
            successors.add(new Node(swap(state, 8, 5)));    //up
        }

        return successors;
    }

    /* str represents a state in a node and i and j are index of the two values to be swapped.
    * */
    public static String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

}
