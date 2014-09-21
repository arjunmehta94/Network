/**Network.java**/

 
package player;
import list.*;
public class Network{

    /*
    hasValidNetwork determines whether Board "board" has a valid network for "color".
    
    Unusual conditions:
        --> If nodes contain invalid values, do not expect proper behavior

    @param board is a Gameboard, an internal representation of the Game.
    @param color is the color for which network must be determined, black or white.
    @return true if "color" has a valid Network.
    */
    public static boolean hasValidNetwork(Board board, int color){
        BoardNode[] goals = goalNodeFinder(board, color);
        Board.resetToUnvisited(board);
        boolean valReturn = isNetwork(board, goals);
        return valReturn;
    }


    /*
    goalNodeFinder returns an array of the Nodes at the starting goal of a board for a color
    starting goal defined as y coordinate 0 for black, x coordinate 0 for white

    @param board is a param board is a Gameboard, an internal representation of the Game.
    @param color is the color for which network must be determined, black or white.
    @returns an array of type BoardNode of goal Nodes
    */
    public static BoardNode[] goalNodeFinder(Board board, int color){
        return board.startingNodes(color);
 
    }


    /* 
    isNetwork determine if is there a network for a given starting goal  
    from an array of goal Nodes.
    
    @param bo
    alNode, a BoardNode array of goals
    @returns true if there is a network for an array of goalNodes on a given board. <called by hasValidNetwork>
    */
    public static boolean isNetwork(Board board, BoardNode[] goalNodes){
        
        for(BoardNode goal : goalNodes){
            boolean x = networkFinder(board, goal, 1, -2);
            if(x == true){
                return true;
            }
        }
        return false;
    }


    /*
    networkFinder performs depth first search to determine if a given node forms network connections.
    
    @param board is a param board is a Gameboard, an internal representation of the Game.
    @param node, a particular node on the board of type BoardNode
    @param numNodes keeps track of the number of nodes in the network
    @param prevDir records direction of previous node.
    @returns true if there is a network with the satisfactory constraints.
    */
    public static boolean networkFinder(Board board, BoardNode node, int numNodes, int prevDir){
         
            //base case - node is a goal and numNodes of that color >= 6
            if(Board.isEndingNode(node, node.getColor()) && numNodes >= 6){
                return true;
            }
            node.changeVisit(true);
            
            BoardNode[] childrenNodeArray; 
            childrenNodeArray = getNodeConnections(board, node);
 
            for(BoardNode child: childrenNodeArray){
                if(child.getVisit() == false){
                    
                    //checks for a connection to another goal in the same starting region as the first goal
                    if(Board.isStartingNode(child, child.getColor()) == true){
                        continue;
                    }

                    if((Board.isEndingNode(child, child.getColor()) == true) && numNodes<5){
                        continue;
                    }
                    //checks for change in direction after 2 in the same direction. 
 
                    if(Evaluator.getDirection(node, child) == prevDir){
                        continue;
                    }
                    boolean y = networkFinder(board, child, numNodes+1, Evaluator.getDirection(node, child));
                    if(y == true){
                        
                        return true;
                    }
                }
            }
            node.changeVisit(false);
            return false;
 
    }


    /*
    getNodeConnections returns an array of all connected nodes on a board from a given node of a given color
     
     @param board is a param board is a Gameboard, an internal representation of the Game.
     @param parent, a BoardNode that represents a node from which connections are to be found
     @returns an array of BoardNodes that connect to "parent"
     */
    public static BoardNode[] getNodeConnections(Board board, BoardNode parent){
        int col = parent.getColor();
        DList tmp = new DList();
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if(Evaluator.isConnected(board, parent, board.getNode(j,i)) && board.getNode(j,i).getVisit() == false && 
                    Evaluator.notBlocked(board, parent, board.getNode(j,i)) && !BlackGoalConnect(parent, board.getNode(j,i))
                    && !WhiteGoalConnect(parent, board.getNode(j,i))){
                    
                    tmp.insertFront(board.getNode(j,i));
 
                }           
            }
        }
        BoardNode[] x = new BoardNode[tmp.length()];
        int count = 0;
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if(Evaluator.isConnected(board, parent, board.getNode(j,i)) && board.getNode(j,i).getVisit() == false
                    && Evaluator.notBlocked(board, parent, board.getNode(j,i)) && !BlackGoalConnect(parent, board.getNode(j,i))
                    && !WhiteGoalConnect(parent, board.getNode(j,i)) ){//&& !Board.isGoalNode(board.getNode(j,i))
                    
                    x[count] = board.getNode(j,i);
                    Evaluator.getDirection(parent, x[count]);
                    count++;
                }           
            }
        }
        
        return x;
    }
    

    /*
    BlackGoalConnect checks if 2 nodes are in the black starting or ending goal area, i.e.
    y coordinate is 0 or 7 
    
    @param goal1, the first BoardNode to check
    @param goal2, the second BoardNode to check
    @returns true if 2 nodes are in the black starting or ending goal area
    */
    private static boolean BlackGoalConnect(BoardNode goal1, BoardNode goal2){
        return Evaluator.isHorizontallyAligned(goal1, goal2) && (goal1.getY() == 0 || goal1.getY() == 7) 
        && (goal2.getY() == 0 || goal1.getY() == 7);
    }
    /*
    WhiteGoalConnect checks if 2 nodes are in the white starting or ending goal area, i.e.
    x coordinate is 0 or 7
     
     @param goal1, the first BoardNode to check
     @param goal2, the second BoardNode to check
    returns true if 2 nodes are in the white starting or ending goal area
    */
    private static boolean WhiteGoalConnect(BoardNode goal1, BoardNode goal2){
        return Evaluator.isVerticallyAligned(goal1, goal2) && (goal1.getX() == 0 || goal1.getX() == 7) 
        && (goal2.getX() == 0 || goal1.getX() == 7);
    }










   
}