import java.util.Comparator;

class ExpectedCostComparator implements Comparator<Node> {

    /* This comparator is used for Best First Search algorithm. It gives priority to the node that is the most promising
        node chosen according to a specified rule which is misplaced tiles relative to the goal.
     * */
    public int compare(Node node1, Node node2) {
        if (node1.getExpectedCostTillGoal() < node2.getExpectedCostTillGoal())
            return -1;
        else if (node1.getExpectedCostTillGoal() > node2.getExpectedCostTillGoal())
            return 1;
        return 0;
    }

}