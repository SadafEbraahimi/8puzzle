import java.util.*;

public class SearchStrategies {

    private Node root;
    private String goalSate;

    public SearchStrategies(Node root, String goalSate) {
        this.root = root;
        root.setDepth(0);
        this.goalSate = goalSate;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void setGoalSate(String goalSate) {
        this.goalSate = goalSate;
    }

    public Node getRoot() {
        return root;
    }

    public String getGoalSate() {
        return goalSate;
    }


    /* breadthFirstSearch find a solution based on Breadth First Search algorithm.
    *  It has a queue that keeps track of nodes to be expanded. ArrayList explored keeps track of the nodes that
    *  were already expanded so we won't add them again. Every time it polls a node from the queue and add its children
    *  to the end of the queue and checks if it is a goal state or not. It terminates if finding a solution takes more than 5 minutes.
    *  */

    public void breadthFirstSearch() {
        long start = System.currentTimeMillis();
        int space = 0;
        int time = 0;
        long end = start + 300 * 1000;
        Node node = root;
        if (node.getState().equals(goalSate)) {
            System.out.println("Start state is already the answer.");
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        ArrayList<Node> explored = new ArrayList<>();
        while (System.currentTimeMillis() < end) {
            if (queue.isEmpty()) {
                System.out.println("Could not find a solution.");
                return;
            }
            node = queue.poll();
            time++;
            explored.add(node);
            ArrayList<Node> successors = Node.getSuccessors(node);
            for (Node adjacent : successors) {
                if (!explored.contains(adjacent)) {
                    setVariables(adjacent, node, "BFS");
                    if (adjacent.getState().equals(goalSate)) {
                        explored.add(adjacent);
                        System.out.println("found");
                        Monitor.displayResult(explored, adjacent, root, space, time);
                        return;
                    }
                    queue.add(adjacent);
                    if (queue.size() > space)
                        space = queue.size();
                }
            }
        }
        System.out.println("Could not find the answer in 5 minutes.");
    }

    /* depthFirstSearch find a solution based on Depth First Search algorithm.
     *  It has a stack that keeps track of nodes to be expanded. ArrayList explored keeps track of the nodes that
     *  were already expanded so we won't add them again. Every time it pops a node from the stack, checks if it is a
     *  goal state or not, then adds its children to explored. It terminates if finding a solution takes more than 5 minutes.
     *  */
    public void depthFirstSearch() {
        long start = System.currentTimeMillis();
        int space = 0;
        int time = 0;
        long end = start + 300 * 1000;
        Stack<Node> stack = new Stack<>();
        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Node> successors;

        Node node = root;
        stack.add(node);
        space++;
        while (System.currentTimeMillis() < end) {
            if (node.getState().equals(goalSate)) {
                System.out.println("found at depth " + node.getDepth());
                Monitor.displayResult(explored, node, root, space, time);
                return;
            }
            node = stack.pop();
            time++;
            explored.add(node);
            if (!explored.contains(node))
                explored.add(node);

            successors = Node.getSuccessors(node);
            for (Node adjacent : successors) {
                if (!explored.contains(adjacent)) {
                    setVariables(adjacent, node, "DFS");
                    stack.add(adjacent);
                    if (stack.size() > space)
                        space = stack.size();
                }
            }
        }
        System.out.println("Could not find the answer in 5 minutes.");
    }

    /* iterativeDeepeningDepthFirstSearch find a solution based on Iterative Deepening Search algorithm.
     *  It takes an integer from the user, calls depthLimitedSearch() on every level from 0 to the depth
     *  that user defined. If a answer is not returned, it checks the search tree on the next depth too
     *  until it reaches the limit that user provided.
     *  */
    public void iterativeDeepeningDepthFirstSearch(int depth) {
        for (int i = 0; i < depth; i++)
            if(depthLimitedSearch(i))
                return;
            else
                System.out.println("Could not find until depth " + i + ". Trying depth " + (i+1) + "...");
    }

    /* depthLimitedSearch find a solution based on Depth Limited Search algorithm.
     * It has a stack that keeps track of nodes to be expanded. ArrayList explored keeps track of the nodes that
     * were already expanded so we won't add them again. Every time it pops a node from the stack, checks if its
     * height is more than or equal to the height that method recieved as an argument. If it's more then it
     * does not takes its children into consideration and marks it as a deadend. But if not, then adds its children
     * to explored. It terminates if finding a solution takes more than 5 minutes.
     * */
    public boolean depthLimitedSearch(int depth) {
        long start = System.currentTimeMillis();
        int space = 0;
        int time = 0;
        long end = start + 300 * 1000;
        Stack<Node> stack = new Stack<>();
        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Node> successors;

        Node node = root;
        stack.add(node);
        space++;
        while (System.currentTimeMillis() < end) {
            if(stack.isEmpty())
                return false;

            node = stack.pop();
            if(node.getDepth() >= depth) {
                node.setDeadEnd(true);
                continue;
            }
            time++;
            explored.add(node);
            if (!explored.contains(node))
                explored.add(node);

            if (node.getState().equals(goalSate)) {
                System.out.println("Answer found at depth " + depth);
                Monitor.displayResult(explored, node, root, space, time);
                return true;
            }

            successors = Node.getSuccessors(node);
            for (Node adjacent : successors) {
                if (!explored.contains(adjacent)) {
                    setVariables(adjacent, node, "DFS");
                    stack.add(adjacent);
                    if (stack.size() > space)
                        space = stack.size();
                }
            }
        }
        System.out.println("Could not find the answer in 5 minutes.");
        return false;
    }

    /* uniformCostSearch find a solution based on Uniform Cost Search algorithm.
     *  It has a stack that keeps track of nodes to be expanded. ArrayList explored keeps track of the nodes that
     *  were already expanded so we won't add them again. Every time it pops a node from the stack, checks if it is a
     *  goal state or not, then adds its children to explored. It prioritizes the nodes in the queue based on compareTo()
     *  method in Node class. Node that has lower cost so far plus tile to be moved is in priority.
     *  It terminates if finding a solution takes more than 5 minutes.
     *  */
    public void uniformCostSearch() {
        long start = System.currentTimeMillis();
        int space = 0;
        int time = 0;
        long end = start + 300 * 1000;
        Node node = root;
        ArrayList<Node> explored = new ArrayList<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(node);

        while (System.currentTimeMillis() < end) {
            node = priorityQueue.poll();
            time++;
            explored.add(node);
            if (node.getState().equals(goalSate)) {
                Monitor.displayResult(explored, node, root, space, time);
                return;
            }
            ArrayList<Node> successors = Node.getSuccessors(node);
            for (Node adjacent: successors) {
                if (!explored.contains(adjacent)) {
                    setVariables(adjacent, node, "UniformCostSearch");
                    priorityQueue.add(adjacent);
                    explored.add(adjacent);
                    if (priorityQueue.size() > space)
                        space = priorityQueue.size();
                }
            }
        }
        System.out.println("Could not find the answer in 5 minutes.");
    }

    /* bestFirstSearch find a solution based on Best First Search algorithm.
     *  It has a stack that keeps track of nodes to be expanded. ArrayList explored keeps track of the nodes that
     *  were already expanded so we won't add them again. Every time it pops a node from the stack, checks if it is a
     *  goal state or not, then adds its children to explored. It prioritizes the nodes in the queue based on comparator
     *  in ExpectedCostComparator class. It terminates if finding a solution takes more than 5 minutes.
     *  */
    public void bestFirstSearch() {
        long start = System.currentTimeMillis();
        int space = 0;
        int time = 0;
        long end = start + 300 * 1000;
        Node node = root;
        ArrayList<Node> explored = new ArrayList<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new ExpectedCostComparator());
        priorityQueue.add(node);

        while (System.currentTimeMillis() < end) {
            node = priorityQueue.poll();
            time++;
            explored.add(node);
            if (node.getState().equals(goalSate)) {
                System.out.println("found");
                Monitor.displayResult(explored, node, root, space, time);
                return;
            }
            ArrayList<Node> successors = Node.getSuccessors(node);
            for (Node adjacent : successors) {
                if (!explored.contains(adjacent)) {
                    setVariables(adjacent, node, "BestFirstSearch");
                    priorityQueue.add(adjacent);
                }
            }
            explored.add(node);
            if (priorityQueue.size() > space)
                space = priorityQueue.size();
        }
        System.out.println("Could not find the answer in 5 minutes.");
    }

    /* uniformCostSearch find a solution based on Unoform Cost Search algorithm.
     *  It has a stack that keeps track of nodes to be expanded. ArrayList explored keeps track of the nodes that
     *  were already expanded so we won't add them again. Every time it pops a node from the stack, checks if it is a
     *  goal state or not, then adds its children to explored. It prioritizes the nodes in the queue based on comparator
     *  in OverallCostComparator class. It terminates if finding a solution takes more than 5 minutes.
     *  */
    public void aStar(String heuristic) {
        long start = System.currentTimeMillis();
        int space = 0;
        int time = 0;
        long end = start + 300 * 1000;
        Node node = root;
        ArrayList<Node> explored = new ArrayList<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new OverallCostComparator());
        priorityQueue.add(node);

        while (System.currentTimeMillis() < end) {
            node = priorityQueue.poll();
            time++;
            explored.add(node);
            if (node.getState().equals(goalSate)) {
                //System.out.println("found");
                Monitor.displayResult(explored, node, root, space, time);
                return;
            }
            ArrayList<Node> successors = Node.getSuccessors(node);
            for (Node adjacent : successors) {
                if (!explored.contains(adjacent)) {
                    setVariables(adjacent, node, heuristic);
                    priorityQueue.add(adjacent);
                }
            }
            explored.add(node);
            if (priorityQueue.size() > space)
                space = priorityQueue.size();
        }
        System.out.println("Could not find the answer in 5 minutes.");
    }

    /* Two setVariables() methods simply set the attributes of every node.*/
    public void setVariables(Node child, Node parent) {
        child.setParent(parent);
        child.setDepth(parent.getDepth() + 1);
    }

    /* Two setVariables() methods simply set the attributes of every node.*/
    public void setVariables(Node child, Node parent, String mode) {
        child.setParent(parent);
        if (parent == null) {
            child.setDepth(0);
            child.setCostSoFar(0);
            child.setCostToMoveToThisNode(0);
            child.setExpectedCostTillGoal(0);
        }
        else {
            child.setDepth(parent.getDepth() + 1);
            if (mode == "BFS" || mode == "DFS" || mode == "UniformCostSearch") {
                int cost = costFromParentToChild(child, parent);
                child.setCostToMoveToThisNode(cost);
                child.setCostSoFar(parent.getCostSoFar() + cost);
                child.setExpectedCostTillGoal(0);
            }
            else if (mode == "BestFirstSearch" || mode == "A Star H1"){
                int cost = costFromParentToChild(child, parent);
                child.setCostToMoveToThisNode(cost);
                child.setCostSoFar(parent.getCostSoFar() + cost);
                cost = misplacedTilesHeuristic(child.getState(), goalSate);
                child.setExpectedCostTillGoal(cost);
            }
            else if (mode == "A Star H2") {
                int cost = costFromParentToChild(child, parent);
                child.setCostToMoveToThisNode(cost);
                child.setCostSoFar(parent.getCostSoFar() + cost);
                cost = manhattanDistanceHeuristic(child, goalSate);
                child.setExpectedCostTillGoal(cost);
            }
            else if (mode == "A Star H3") {
                int cost = costFromParentToChild(child, parent);
                child.setCostToMoveToThisNode(cost);
                child.setCostSoFar(parent.getCostSoFar() + cost);
                cost = directReversalHeuristic(child, goalSate);
                child.setExpectedCostTillGoal(cost);
            }
        }
        child.setDepth(parent.getDepth() + 1);
    }
    /* Detects how much it costs us to make a transition from child node to parent node.
    *  Returns values of the tiles being moved.
    * */
    public static int costFromParentToChild(Node child, Node parent) {
        int indexOfZero = parent.getState().indexOf("0");
        int costToMove = Integer.parseInt(String.valueOf(child.getState().charAt(indexOfZero)));
        return costToMove;
    }

    /* It detects how many tiles we have to move to get to goalState from state.*/
    public int misplacedTilesHeuristic (String state, String goalState) {
        int cost = 0;
        for (int i = 0; i < 9; i++)
            if (state.charAt(i) != goalState.charAt(i))
                cost++;
        return cost;
    }

    /* It calculates the Manhattan distance from state node to goalState node.*/
    public static int manhattanDistanceHeuristic (Node state, String goalState) {
        String stateStr = state.getState();
        int cost = 0;
        for (int i = 0; i < stateStr.length(); i++) {
            if(stateStr.charAt(i) == '0')
                continue;
            int j = goalState.indexOf(stateStr.charAt(i));
            cost += (Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 - j / 3);
        }
        return cost;
    }

    /* A pair of tiles form are reversed if the values on tiles are in reverse order of their appearance in goal state.
    *  This method detects how many tiles are in revered position. Then it returns the sum of Manhattan distance of state and goalState
    *  and count of revered tiles.
    * */
    public static int directReversalHeuristic(Node state, String goalSate) {
        int manhattanDistance = manhattanDistanceHeuristic(state, goalSate);
        int reveresedTiles = 0;
        String stateStr = state.getState();
        if (stateStr.charAt(0) == 2 && stateStr.charAt(1) == 1)
            reveresedTiles++;
        if (stateStr.charAt(0) == 8 && stateStr.charAt(3) == 1)
            reveresedTiles++;
        if (stateStr.charAt(1) == 3 && stateStr.charAt(2) == 2)
            reveresedTiles++;
        if (stateStr.charAt(1) == 0 && stateStr.charAt(4) == 2)
            reveresedTiles++;
        if (stateStr.charAt(2) == 4 && stateStr.charAt(5) == 3)
            reveresedTiles++;
        if (stateStr.charAt(3) == 0 && stateStr.charAt(4) == 8)
            reveresedTiles++;
        if (stateStr.charAt(3) == 7 && stateStr.charAt(6) == 8)
            reveresedTiles++;
        if (stateStr.charAt(4) == 4 && stateStr.charAt(5) == 0)
            reveresedTiles++;
        if (stateStr.charAt(4) == 6 && stateStr.charAt(7) == 0)
            reveresedTiles++;
        if (stateStr.charAt(5) == 5 && stateStr.charAt(8) == 4)
            reveresedTiles++;
        if (stateStr.charAt(6) == 6 && stateStr.charAt(7) == 7)
            reveresedTiles++;
        if (stateStr.charAt(7) == 5 && stateStr.charAt(8) == 6)
            reveresedTiles++;
        return manhattanDistance + reveresedTiles;
    }

    public static char[][] convertTo2DArray(String state) {
        char[][] arr = new char[3][3];
        for (int i = 0; i < 3; i++) {
            int k = 0;
            for (int j = i * 3; j <= i * 3 + 2; j++) {
                arr[i][k++] = state.charAt(j);
            }
        }
        return arr;
    }

}
