import java.util.Comparator;

public class OverallCostComparator implements Comparator<Node> {

    /* This comparator is used for A* search algorithm. It gives priority to the node that minimizes g(n) + h(n) which is
    *  getCostSoFar() + getExpectedCostTilLGoal()
    * */
    public int compare(Node node1, Node node2) {
        if (node1.getCostSoFar() + node1.getExpectedCostTillGoal() < node2.getCostSoFar() + node2.getExpectedCostTillGoal())
            return -1;
        else if (node1.getCostSoFar() + node1.getExpectedCostTillGoal() > node2.getCostSoFar() + node2.getExpectedCostTillGoal())
            return 1;
        return 0;
    }

}
