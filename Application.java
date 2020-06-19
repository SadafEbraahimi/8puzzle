public class Application {
    final static private String EASY = "134862705";
    final static private String MEDIUM = "281043765";
    final static private String HARD = "567408321";
    final static private String GOAL_STATE = "123804765";


    public static void main(String[] args) {
        SearchStrategies easy = new SearchStrategies(new Node(EASY), GOAL_STATE);
        SearchStrategies medium = new SearchStrategies(new Node(MEDIUM), GOAL_STATE);
        SearchStrategies hard = new SearchStrategies(new Node(HARD), GOAL_STATE);


        //medium.uniformCostSearch();
        easy.breadthFirstSearch();
        /*easy.uniformCostSearch();
        easy.bestFirstSearch();
        easy.iterativeDeepeningDepthFirstSearch(100);
        easy.aStar("A Star H1");
        easy.aStar("A Star H2");
        easy.aStar("A Star H3");*/

    }


}
