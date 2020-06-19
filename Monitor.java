import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Monitor {

    public static MovementType movementDetector(String host, String destination) {
        int difference = host.indexOf('0') - destination.indexOf('0');
        if (difference == 1)
            return MovementType.LEFT;
        else if (difference == 3)
            return MovementType.UP;
        else if (difference == -1)
            return MovementType.RIGHT;
        else if (difference == -3)
            return MovementType.DOWN;
        else
            return null;
    }

    public static void displayResult(ArrayList<Node> explored, Node last, Node root, int space, int time) {
        HashMap<Node, Node> childAndParent = new HashMap<>();
        Stack<Node> solution = new Stack<>();
        for (Node node: explored) {
            if (node.getState() == root.getState())
                childAndParent.put(node, null);
            else
                childAndParent.put(node, node.getParent());
        }

        solution.push(last);
        Node father = childAndParent.get(last);
        while(father != null) {
            solution.push(father);
            father = childAndParent.get(father);
        }
        int moves = solution.size();
        while(!solution.isEmpty()){
            Node ans = solution.pop();
            display(ans.getState());
            if (ans.getState() == last.getState())
                break;
            System.out.println("\nMove 0 to " + movementDetector(ans.getState(), solution.peek().getState()) + " =>");
            System.out.println();
        }

        int cost = last.getCostSoFar();
        System.out.println("\nTransitions to get to goal: " + (moves - 1));
        System.out.println("Number of visited states: " + explored.size());
        System.out.println("Total cost: " + cost);
        System.out.println("Space: " + space);
        System.out.println("Time: " + time);
    }

    public static void display(String state) {
        System.out.println(state.charAt(0) + "|" + state.charAt(1) + "|" + state.charAt(2) + "\n" +
                state.charAt(3) + "|" + state.charAt(4) + "|" + state.charAt(5) + "\n" +
                state.charAt(6) + "|" + state.charAt(7) + "|" + state.charAt(8));
    }

}
