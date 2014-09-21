/* MachinePlayer.java */

package player;


/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
  protected int color;
  protected int searchDepth;
  protected Board gameBoard;
  protected final static int WHITE = 1;
  protected final static int BLACK = 0;


  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    this.color = color;
    gameBoard = new Board();
    searchDepth = 2;
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    this.color = color;
    this.searchDepth = searchDepth;
    gameBoard = new Board();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    /// make evaulation class. call evaluation when doing mini max search. 
    /// AI COMPUTATION
    /// compute everything and finally decide on what move to take.
    Best myBest;

    myBest = computeMove(color, -100, 100, searchDepth);
    Move m = myBest.move;

    gameBoard.moveTo(m, color);
        
    return m;
  } 

  // computeMove is the minimax function which uses the evaluation function to produce 
   // a best possible move. 
   // @param moveColor represents the color of the side who is playing
   // @param alpha stores the alpha value for minimax
   // @param beta stores the beta value for minimax
   // @param searchDepth stores the recursive depth till which the minimax function should taverse
    // @return a Best object which holds the best move and the score corresponding it.

  public Best computeMove(int moveColor, double alpha, double beta, int searchDepth){
    Best myBest = new Best(); 
    Best reply;

    if(Network.hasValidNetwork(gameBoard, 0) && Network.hasValidNetwork(gameBoard, 1)){
      if(moveColor == 1){
        myBest.evalScore = 100.0 * (Math.pow(0.97,(this.searchDepth - searchDepth)));
        return myBest; 
      } else if(moveColor == 0){
        myBest.evalScore = -100.0 * (Math.pow(0.97,(this.searchDepth - searchDepth)));
        return myBest; 
      }
    }

    if(Network.hasValidNetwork(gameBoard, 0)){
        myBest.evalScore = -100.0 * (Math.pow(0.97,(this.searchDepth - searchDepth)));
        return myBest; 
    }

    if(Network.hasValidNetwork(gameBoard, 1)){
        myBest.evalScore = 100.0 * (Math.pow(0.97,(this.searchDepth - searchDepth)));
        return myBest; 
    }

    if(searchDepth == 0){
      double score = Evaluator.evalScore(gameBoard, moveColor); /// board is evaulated at the last recursive depth , moveColor
      myBest.evalScore = score;
      return myBest;
    }

    if(moveColor == 1){
      myBest.evalScore = alpha;
    } else{
      myBest.evalScore = beta;
    }

    Move[] allMoves = gameBoard.isValidMoveList(moveColor); 

    myBest.move = allMoves[0];
    for(Move m : allMoves){
      if(m!=null){
        gameBoard.moveTo(m, moveColor);
        reply = computeMove(1-moveColor, alpha, beta, searchDepth-1);
        gameBoard.undoMove(m, moveColor);
        if((moveColor == 1) && (reply.evalScore > myBest.evalScore)){
          myBest.move = m;
          myBest.evalScore = reply.evalScore;
          alpha = reply.evalScore;
        } else if((moveColor == 0) && (reply.evalScore < myBest.evalScore)) {
          myBest.move = m;      
          myBest.evalScore = reply.evalScore;
          beta = reply.evalScore;
        }
        if (alpha >= beta){ 
          return myBest; 

        }
      }
    }
    
    return myBest;
  }


  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    int opponentColor = 1-color;
    if(gameBoard.isValidMove(m, opponentColor)){ // check if the move is valid for the given color.
      // perform move
      gameBoard.moveTo(m, opponentColor);
      return true;
    } 
    return false;
    
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) { /// player move.
    if(gameBoard.isValidMove(m, color)){
      gameBoard.moveTo(m, color);
      return true;
    } 
    return false;
  }

  public static void main (String[] args){

     MachinePlayer me3 = new MachinePlayer(1, 2);
      me3.forceMove(new Move(0,3));
      me3.forceMove(new Move(0,5));
      me3.forceMove(new Move(2,3));
      me3.forceMove(new Move(2,4));
      me3.forceMove(new Move(4,6));
      me3.opponentMove(new Move(3,3));
      me3.opponentMove(new Move(3,4));
      me3.opponentMove(new Move(6,3));
      me3.opponentMove(new Move(5,5));
      me3.opponentMove(new Move(5,6));
      System.out.println("6.1 MOVE is :" + me3.chooseMove());
      System.out.println(me3.gameBoard);
          
      MachinePlayer me1 = new MachinePlayer(1, 2); 
      me1.forceMove(new Move(0,2));
      
      me1.forceMove(new Move(4,3));
      me1.forceMove(new Move(1,6));
      me1.forceMove(new Move(4,6));
      me1.opponentMove(new Move(1,0));
      me1.opponentMove(new Move(1,2));
      me1.opponentMove(new Move(6,2));
      me1.opponentMove(new Move(6,7));
      System.out.println("6.2 MOVE is :" + me1.chooseMove());
      System.out.println(me1.gameBoard);
       MachinePlayer me2 = new MachinePlayer(1, 2);
       me2.forceMove(new Move(0,1));
       //me.forceMove(new Move(2,2));
       me2.forceMove(new Move(0,4));
       me2.forceMove(new Move(1,5));
       me2.forceMove(new Move(4,2));
       me2.forceMove(new Move(5,1));
       //me.forceMove(new Move(6,3))
      
       me2.opponentMove(new Move(2,0));
       me2.opponentMove(new Move(3,1));
       me2.opponentMove(new Move(2,7));
       me2.opponentMove(new Move(3,5));
       me2.opponentMove(new Move(5,3));
       System.out.println("7.1 MOVE is :" + me2.chooseMove());
       System.out.println(me2.gameBoard);
  
      MachinePlayer me = new MachinePlayer(1, 2);
       me.forceMove(new Move(0,1));
       me.forceMove(new Move(2,2));
       me.forceMove(new Move(2,3));
       me.forceMove(new Move(6,1));
       me.forceMove(new Move(7,4));
       me.forceMove(new Move(7,6));
      //me.forceMove(new Move(6,3))
      
       me.opponentMove(new Move(1,0));
       me.opponentMove(new Move(1,6));
       //me.opponentMove(new Move(2,3));
       me.opponentMove(new Move(3,4));
       me.opponentMove(new Move(6,4));
       me.opponentMove(new Move(6,7));
       System.out.println("7.2 MOVE is :" + me.chooseMove());
       System.out.println(me.gameBoard);
                
      MachinePlayer me4 = new MachinePlayer(1, 2);
      me4.forceMove(new Move(0,2));
      me4.forceMove(new Move(1,2));
      me4.forceMove(new Move(1,5));
      me4.forceMove(new Move(4,2));
      me4.forceMove(new Move(4,5));
      //me.forceMove(new Move(7,5));
      //me.forceMove(new Move(6,3))
      
      me4.opponentMove(new Move(1,1));
      me4.opponentMove(new Move(2,1));
      //me.opponentMove(new Move(2,3));
      me4.opponentMove(new Move(4,1));
      me4.opponentMove(new Move(5,1));
      me4.opponentMove(new Move(1,6));
      
      //System.out.println(Network.hasValidNetwork(me.gameBoard, 1));
      System.out.println(me4.gameBoard);
      System.out.println(" 5. MOVE is :" + me4.chooseMove());
   }
}
