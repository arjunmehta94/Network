/* Board.java */
 
//*************************************************
//*********** MONDAY FILE> FINALish
//*************************************************
 
package player;
 
import player.*;
 
import list.*;
 
 
public class Board {
 
    //Variables
    private BoardNode gameBoard[][];
    private int numWhite;
    private int numBlack;
 
 
    //Methods
    public Board(){
        gameBoard = new BoardNode[8][8];
        for(int i=0; i<8; i++){
            for(int j=0;j<8;j++) {
                gameBoard[i][j]= new BoardNode(i, j);
            }
        }
        numWhite=0;
        numBlack=0;
 
    }
 
    //HELPER - changes the color of a node
    //@param x is of type int and represents x coordinate of position
    //@param y is of type int and represents y coordinate of position
    //@param newcolor is either 0(black) or 1(white)
    public void setColor(int x, int y, int newcolor){
        gameBoard[x][y].changeColor(newcolor);
    }
    
    //HELPER
    //@param x is of type int and represents x coordinate of position
    //@param y is of type int and represents y coordinate of position
    //@return BoardNode at position (x,y)
    public BoardNode getNode(int x, int y){
        return gameBoard[x][y];
    }
 
    //returns the number of nodes of a given color
    //@param board is a Board object
    //@param color of type int and is either 0(black) or 1(white)
    //@return the number of white nodes if color==1 or the number of 
    //black nodes if color==0 
    public static int getNumNodes(Board board, int color){
        if(color == 1){
            return board.numWhite;
        }
        else{
            return board.numBlack;
        }
    }
    
    //HELPER - sets the visit of the whole board to false
    //@param board is a Board object 
    public static void resetToUnvisited(Board board){
            for(int i = 0; i<8; i++){
                for(int j = 0; j<8; j++){
                    board.getNode(j,i).changeVisit(false);
                }
                 
            }
        }
    //@param obj of type Move
    //@param color of type int and is either 0(black) or 1(white)
    //@return true if a move is a valid and false if it is invalid
    public boolean isValidMove(Move obj, int color){
        //move is out of bounds (not on board)
        if (obj.x1<0||obj.x1>7||obj.y1<0||obj.y1>7){
            
            return false;
        }
        //cannot add here if a chip is already present
        if (gameBoard[obj.x1][obj.y1].isEmpty()==false){
            
            return false;
        }
         
        if (obj.moveKind==2){
            if (gameBoard[obj.x2][obj.y2].isEmpty())
                
                return false;
        }
        //cannot make step moves if chips < 10
        if(obj.moveKind == 2){
            if(color == 0 && numBlack < 10){
                
                return false;
            }
            if(color == 1 && numWhite < 10){ 
                
                return false;
            }   
        }
        //cannot make add move if chips >= 10
        if(obj.moveKind == 1){
            if(color == 0 && numBlack == 10){
                
                return false;
            }
            if(color == 1 && numWhite == 10){
                
                return false;
            }   
        }
 
        //corners cannot be occupied
        if ((obj.y1==0&&obj.x1==0) || (obj.y1==0&&obj.x1==7) || (obj.y1==7&&obj.x1==7) ||
            (obj.y1==7&&obj.x1==0)){
            
            return false;
        }
        else if (isCluster(obj, color)==true){
            
            return false;
        }
        //checks goal areas for black
        if (obj.x1==0 || obj.x1==7 ){
            if (color==0){
               
                return false;
            }
        }
        //checks goal areas for white
        if (obj.y1==0 || obj.y1==7){
            if (color==1){
                
                return false;
            }
        }
         
        return true;
         
    }
 
    //HELPER - returns true if a node is a goal node
    //@param node is of type BoardNode 
    //@return true if node is in goal area or false if  node isn't in goal area
    public static boolean isGoalNode(BoardNode node){
        if(node.getColor() == 0){
            if(node.getY() == 0 || node.getY() == 7){
                return true;
            }
        }
        else if(node.getColor() == 1){
            if(node.getX() == 0 || node.getX() == 7){
                return true;
            }
        }
         
        return false;
         
    }
 
    //HELPER - returns true if a node of a particular color is an starting node
    //ending goal for white x = 0, black when y = 0.
    //@param node of type BoardNode
    //@param color of type int and is either 0(black) or 1(white)
    public static boolean isStartingNode(BoardNode node, int color){
        if(color == 0){
            if(node.getY() == 0){
                return true;
            }
        }
        if(color == 1){
            if(node.getX() == 0){
                return true;
            }
        }
        return false;
    }
 
    //HELPER 
    //ending goal for white x = 7, black when y = 7.
    //@param color of type int and is either 0(black) or 1(white)
    //@param node of type BoardNode
    //@return true if node is at an ending network position and false if it isnt
    public static boolean isEndingNode(BoardNode node, int color){
        if(color == 0){
            if(node.getY() == 7){
                return true;
            }
        }
        if(color == 1){
            if(node.getX() == 7){
                return true;
            }
        }
        return false;
 
    }
 
    //HELPER 
    //starting goal for white is when x = 0, for black when y = 0 by definition
    //@param color of type int and is either 0(black) or 1(white)
    //@return an array of BoardNodes that reside on the starting goal area for a given color
 
    public BoardNode[] startingNodes(int color){
        int count = 0;
        if(color == 1){
            int y;
            for(y = 1; y<7; y++){
                if(this.getNode(0, y).getColor() == color){
                    count++;
             
                }
            }
            y = 1;
            BoardNode[] start = new BoardNode[count];
            count = 0;
            for(; y < 7; y++){
                if(this.getNode(0, y).getColor() == color){
                    start[count] = this.getNode(0, y);
                    count++;
             
                }
            }
            return start;
 
        }
        else{
            int x;
            for(x = 1; x < 7; x++){
                if(this.getNode(x, 0).getColor() == color){
                    count++;
             
                }
            }
            x = 1;
            BoardNode[] start = new BoardNode[count];
            count = 0;
            for(; x<7; x++){
                if(this.getNode(x, 0).getColor() == color){
                    start[count] = this.getNode(x, 0);
                    count++;
                     
                }
            }
            return start;
 
        }
 
 
    }
 

      //updates a board for a particular move
      //@param newcolor of type int and is either 0(black) or 1(white)
      //@param obj of type Move
    public void moveTo(Move obj, int newcolor){
        if(isValidMove(obj, newcolor)){
            if(obj.moveKind==1){
                this.setColor(obj.x1, obj.y1, newcolor);
                if (newcolor==0){
                    numBlack++;
                    
                }
                else if (newcolor==1){
                    numWhite++;
                    
                    
                }
            }
            else if(obj.moveKind==2){
                this.setColor(obj.x1, obj.y1, newcolor);
                this.setColor(obj.x2, obj.y2, -1);
            }
 
        }
    }
 

    //updates a board for undoing a particular move
    //@param obj of type Move
    //@param color of type int and is either 0(black) or 1(white)
    public void undoMove(Move obj, int color){
        if (obj.moveKind==1){
            this.setColor(obj.x1, obj.y1, -1);
            if (color==0)
                numBlack--;
            else if (color==1)
                numWhite--;
 
        }
        else if (obj.moveKind==2){
            this.setColor(obj.x1, obj.y1, -1);
            this.setColor(obj.x2, obj.y2, color);
        }
    }
     


     //HELPER - calls check cluster for add and step moves 
     //@param obj of type Move
    //@param color of type int and is either 0(black) or 1(white)
    //@return true if cluster is present and false if isnt
    public boolean isCluster(Move m, int color){
        boolean result;
        if(m.moveKind == 1){
            result = checkCluster(m.x1, m.y1, color);
            return result;
        } else if(m.moveKind == 2){
            gameBoard[m.x2][m.y2].changeColor(-1);
            result = checkCluster(m.x1, m.y1, color);
            gameBoard[m.x2][m.y2].changeColor(color);
            return result;
        }
        else{
            return false;
        }
    }
 

    //HELPER - checks if there is a cluster after adding a node
    //@param obj of type Move
    //@param color of type int and is either 0(black) or 1(white)
    //@return true if cluster is present and false if isnt
    public boolean checkCluster(int x, int y, int color){
        int count=0;
        for (int i =x-1; i<x+2 ; i++) {
            for (int j =y-1; j<y+2; j++) {
                if (i>-1 && i<8 && j>-1 && j<8){
                    if (gameBoard[i][j].isEmpty()==false && gameBoard[i][j].getColor() == color) {
                        if (i==x && j==y){
                            //break;
                            }   
                        else {
                            count++;
                            for (int k =i-1; k<i+2 ; k++) {
                                for (int l =j-1; l <j+2; l++) {
                                    if ((x==k && y==l) || (k==i && l==j)){
                                        //break;
                                        }
                                    else if (k>-1 && k<8 && l>-1 && l<8){
                                        if (gameBoard[k][l].isEmpty()==false && gameBoard[k][l].getColor() == color){
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }   
                }
            }   
        }
        if (count>1){
            return true;
        }
        else{
            return false;
        }
    }
         
     
     //@param color of type int and is either 0(black) or 1(white)     
     //@return a list of all valid moves 
    public Move[] isValidMoveList(int color) {
        Move[] moveList = new Move[500];
        Move next;
        int count = 0;
         
 
        int x1 = 0;
        int y1 = 0;
        //Adding all possible step moves // 10 chips or more
        while(x1<8 && y1<8){
            if (gameBoard[x1][y1].getColor() == color){
                for (int i =0; i<8 ; i++) {
                    for (int j =0; j <8; j++) {
                        next = new Move(i, j, x1, y1);
                        if (isValidMove(next, color)){
                            moveList[count] = next;
                            count++;
                        }
                    }
                }       
            }
            if(x1==7) {
                y1++;
                x1=0;
            }
            else {
                x1++;
            }
        }
        //Adding all possible add moves
        x1=0;
        y1=0;
        if(color == 1 && numWhite < 10 || color ==  0 && numBlack < 10){
            while(x1<8 && y1 <8){
                next = new Move(x1, y1);
                if (isValidMove(next, color)){
                    moveList[count] = next;
                    count++;
                }
                 
                if(x1==7) {
                    y1++;
                    x1=0;
                }
 
                else {
                    x1++;
                }
            }
        }
        // no quit move needed
        // //Adding the quit move
        // moveList[count] = new Move();
 
        Move[] newList = new Move[count];
        for(int i =0 ; i<count; i++){
            newList[i] = moveList[i];
        }
 
       
        return newList;
    } 
 
     
    public String toString(){
        String x = new String();
        String top = "    0   1   2   3   4   5   6   7 \n";
        String line = "  ---------------------------------\n";
        for(int i = 0; i<8; i++){
            String start = i + " ";
            for(int j = 0; j<8; j++){
                start += this.getNode(j, i) ;
            }
            start = start + "|\n";
            x = x + start + line;
        }
        x = top + line + x;
        return x;
    }
 
  //    public static void main(String[] args) { 
  //     Board x = new Board();

  //     x.moveTo(new Move(2,2),1);
  //     x.moveTo(new Move(4,2),1);
  //     x.moveTo(new Move(2,4),1);
  //     x.moveTo(new Move(4,4),1);  
  //     x.moveTo(new Move(6,2),1); 
  //     x.moveTo(new Move(6,4),1);

  //     System.out.println(Evaluator.evalScore(x));

  //     System.out.println(x);
         
  // //       System.out.println("Test 1:");
  // //       Board game1 = new Board();
  // //       game1.moveTo(new Move(2,0), 0);
  // //       game1.moveTo(new Move(6,0), 0);
  // //       game1.moveTo(new Move(4,2), 0);
  // //       game1.moveTo(new Move(1,3), 0);
  // //       game1.moveTo(new Move(3,3), 0);
  // //       game1.moveTo(new Move(2,5), 0);
  // //       game1.moveTo(new Move(3,5), 0);
  // //       game1.moveTo(new Move(5,5), 0);
  // //       game1.moveTo(new Move(6,5), 0);
  // //       game1.moveTo(new Move(5,7), 0);
 
  // //       //System.out.println(game1);
  // //       System.out.println("Network should be true: "+Network.hasValidNetwork(game1, 0));
 
  // //       System.out.println("Test 2 (<6 Nodes):");
  // //       Board game2 = new Board();
  // //       game2.moveTo(new Move(2,0), 0);
  // //       game2.moveTo(new Move(4,2), 0);
  // //       game2.moveTo(new Move(3,3), 0);
  // //       game2.moveTo(new Move(5,5), 0);
  // //       game2.moveTo(new Move(5,7), 0);
 
  // //       //System.out.println(game2);
  // //       System.out.println("Network should be false: "+Network.hasValidNetwork(game2, 0));
 
  // //       System.out.println("Test 3 (Blocking):");
  // //       Board game3 = new Board();
  // //       game3.moveTo(new Move(2,0), 0);
  // //       game3.moveTo(new Move(3,1), 1);
  // //       game3.moveTo(new Move(2,1), 1);
  // //       game3.moveTo(new Move(6,0), 0);
  // //       game3.moveTo(new Move(4,2), 0);
  // //       game3.moveTo(new Move(1,3), 0);
  // //       game3.moveTo(new Move(3,3), 0);
  // //       game3.moveTo(new Move(2,5), 0);
  // //       game3.moveTo(new Move(3,5), 0);
  // //       game3.moveTo(new Move(5,5), 0);
  // //       game3.moveTo(new Move(6,5), 0);
  // //       game3.moveTo(new Move(5,7), 0);
 
  // //       //System.out.println(game3);
  // //       System.out.println("Network should be false: "+Network.hasValidNetwork(game3, 0));
 
  // //       System.out.println("Test 4:");
  // //       Board game4 = new Board();
  // //       game4.moveTo(new Move(2,0), 0);
  // //       game4.moveTo(new Move(3,1), 1);
  // //       game4.moveTo(new Move(3,4), 1);
  // //       game4.moveTo(new Move(4,5), 1);
  // //       game4.moveTo(new Move(6,0), 0);
  // //       game4.moveTo(new Move(4,2), 0);
  // //       game4.moveTo(new Move(1,3), 0);
  // //       game4.moveTo(new Move(3,3), 0);
  // //       game4.moveTo(new Move(2,5), 0);
  // //       game4.moveTo(new Move(3,5), 0);
  // //       game4.moveTo(new Move(5,5), 0);
  // //       game4.moveTo(new Move(6,5), 0);
  // //       game4.moveTo(new Move(5,7), 0);
 
  // //       //System.out.println(game4);
  // //       System.out.println("Network should be true: "+Network.hasValidNetwork(game4, 0));
 
  // //       System.out.println("Test 5(Double Direction Test):");
  // //       Board game5 = new Board();
  // //       game5.moveTo(new Move(2,0), 0);
  // //       game5.moveTo(new Move(4,2), 0);
  // //       game5.moveTo(new Move(3,3), 0);
  // //       game5.moveTo(new Move(1,5), 0);
  // //       game5.moveTo(new Move(5,5), 0);
  // //       game5.moveTo(new Move(7,5), 0);
  // //       game5.moveTo(new Move(5,7), 0);
 
  // //       //System.out.println(game5);
  // //       System.out.println("Network should be false: "+Network.hasValidNetwork(game5, 0));
 
  // //       System.out.println("Test 6(Double Base Test):");
  // //       Board game6 = new Board();
  // //       game6.moveTo(new Move(2,0), 0);
  // //       game6.moveTo(new Move(6,0), 0);
  // //       game6.moveTo(new Move(4,2), 0);
  // //       game6.moveTo(new Move(5,5), 0);
  // //       game6.moveTo(new Move(6,4), 0);
  // //       game6.moveTo(new Move(7,5), 0);
  // //       game6.moveTo(new Move(5,7), 0);
 
  // //       //System.out.println(game6);
  // //       System.out.println("Network should be false: "+Network.hasValidNetwork(game6, 0));
 
  // //       System.out.println("Test 7(Same Node Twice Test):");
  // //       Board game7 = new Board();
  // //       game7.moveTo(new Move(2,0), 0);
  // //       game7.moveTo(new Move(3,3), 0);
  // //       game7.moveTo(new Move(2,5), 0);
  // //       game7.moveTo(new Move(3,5), 0);
  // //       game7.moveTo(new Move(5,5), 0);
  // //       game7.moveTo(new Move(5,7), 0);
 
  // //       //System.out.println(game7);
  // //       System.out.println("Network should be false: "+Network.hasValidNetwork(game1, 0));
 
  // //       System.out.println("Test 8 (Nodes>7):");
  // //       Board game8 = new Board();
  // //       game8.moveTo(new Move(2,0), 0);
  // //       game8.moveTo(new Move(1,3), 0);
  // //       game8.moveTo(new Move(3,3), 0);
  // //       game8.moveTo(new Move(2,5), 0);
  // //       game8.moveTo(new Move(3,5), 0);
  // //       game8.moveTo(new Move(5,5), 0);
  // //       game8.moveTo(new Move(5,7), 0);
 
  // //       //System.out.println(game8);
  // //       System.out.println("Network should be true: "+Network.hasValidNetwork(game8, 0));
 
 
  // //   //  //Board game3 = new Board();
  // //   //  Board game3 = new Board();
  // //   //  game3.moveTo(new Move(0,1),1);
  // //   //  game3.moveTo(new Move(2,3), 1);
  // //   //  game3.moveTo(new Move(0,5), 1);
  // //   //  game3.moveTo(new Move(4,5), 1);
  // //   //  game3.moveTo(new Move(6,3), 1);
  // //   //  game3.moveTo(new Move(7,3), 1);
  // //   //  System.out.println(game3);
  // //   //  System.out.println("Network: "+Network.hasValidNetwork(game3, 1));
  // //       //valid network ***DO NOT CHANGE****
  // //       // game3.moveTo(new Move(3,0), 0);
  // //       // game3.moveTo(new Move(2,1), 0);
  // //       // game3.moveTo(new Move(6,1), 0);
  // //       // game3.moveTo(new Move(6,3), 0);
  // //       // game3.moveTo(new Move(2,3), 0);
  // //       // game3.moveTo(new Move(2,4), 0);
  // //       // game3.moveTo(new Move(4,6), 0);
  // //       // game3.moveTo(new Move(3,7), 0);
  // //       // game3.moveTo(new Move(2,2), 1);
  // //       // game3.moveTo(new Move(4,1), 1);
  // //       // game3.moveTo(new Move(3,5), 1);
  // //       // game3.moveTo(new Move(4,3), 1);
  // //       // game3.moveTo(new Move(5,0), 0);
  // //       // game3.moveTo(new Move(6,4), 0);
  // //       //********************************
 
         
  // //       //valid network ***DO NOT CHANGE****
  // //       // game3.moveTo(new Move(2,0), 0);
  // //       // game3.moveTo(new Move(1,1), 0);
  // //       // game3.moveTo(new Move(3,3), 0);
  // //       // game3.moveTo(new Move(3,4), 0);
  // //       // game3.moveTo(new Move(1,6), 0);
  // //       // game3.moveTo(new Move(2,7), 0);
  // //       // game3.moveTo(new Move(4,0), 0);
  // //       // game3.moveTo(new Move(4,6), 0);
  // //       // game3.moveTo(new Move(4,7), 0);
  // //       // game3.moveTo(new Move(6,0), 0);
  // //       // game3.moveTo(new Move(6,2), 0);
  // //       // game3.moveTo(new Move(6,4), 0);
  // //       // game3.moveTo(new Move(2,1), 1);
  // //       // game3.moveTo(new Move(2,2), 1);
 
 
  // //       ///Valid Network ***DO NOT CHANGE***
  // //       // game3.moveTo(new Move(2,0), 0);
  // //       // game3.moveTo(new Move(4,2), 1);
  // //       // game3.moveTo(new Move(6,4), 0);
  // //       // game3.moveTo(new Move(1,4), 0);
  // //       // game3.moveTo(new Move(2,2), 0);
  // //       // game3.moveTo(new Move(2,4), 0);
  // //       // game3.moveTo(new Move(1,6), 0);
  // //       // game3.moveTo(new Move(4,6), 0);
  // //       // game3.moveTo(new Move(5,7), 0);
  // //       // game3.moveTo(new Move(2,6), 0);
         
  // //       //Valid network **DO NOT CHANGE **
  // //       // game3.moveTo(new Move(0,2), 1);
  // //       // game3.moveTo(new Move(2,2), 1);
  // //       // game3.moveTo(new Move(4,2), 1);
  // //       // game3.moveTo(new Move(4,4), 1);
  // //       // game3.moveTo(new Move(6,4), 1);
  // //       // game3.moveTo(new Move(7,6), 1);
  // //       // game3.moveTo(new Move(1,3), 0);
  // //       // game3.moveTo(new Move(6,2), 1);
  // //       // game3.moveTo(new Move(0,6), 1);
  // //       // game3.moveTo(new Move(6,6), 1);
 
  // //       //Valid Network
  // //       // game3.moveTo(new Move(0,2), 1);
  // //       // game3.moveTo(new Move(2,4), 1);
  // //       // game3.moveTo(new Move(4,6), 1);
  // //       // game3.moveTo(new Move(6,6), 1);
  // //       // game3.moveTo(new Move(6,4), 1);
  // //       // game3.moveTo(new Move(7,4), 1);
 
  // //       // game3.moveTo(new Move(3,3), 0);
         
  // //       // game3.moveTo(new Move(4,1), 1);
  // //       // game3.moveTo(new Move(3,2), 0);
  // //       // game3.moveTo(new Move(5, 0), 0);
 
  // //       // game3.moveTo(new Move(3,0), 0);
  // //       // game3.moveTo(new Move(2,1), 0);
  // //       // game3.moveTo(new Move(4,3), 0);
  // //       // game3.moveTo(new Move(2,3), 0);
  // //       // game3.moveTo(new Move(2,4), 0);
  // //       // game3.moveTo(new Move(4,6), 0);
  // //       // game3.moveTo(new Move(4,7), 0);
  // //       // game3.moveTo(new Move(2,2), 1);
  // //       // game3.moveTo(new Move(3,3), 1);
  // //       // game3.moveTo(new Move(5,0), 0);
  // //       // game3.moveTo(new Move(6,1),0);
  // //       // game3.moveTo(new Move(3,1), 1);
  // //       // game3.moveTo(new Move(6,3), 0);
  // //       // game3.moveTo(new Move(3,1), 1);
  // //       // game3.moveTo(new Move(3,0), 1);
  // //       // game3.moveTo(new Move(2,1), 1);
  // //       // game3.moveTo(new Move(5,2), 1);
  // //       // game3.moveTo(new Move(5,3), 1);
  // //       // game3.moveTo(new Move(2,4), 1);
  // //       // game3.moveTo(new Move(1,6), 0);
  // //       // game3.moveTo(new Move(4,7), 0);
  // //       // game3.moveTo(new Move(6,5),0);
  // // //    game3.moveTo(new Move(2,5),0);
  // // //    game3.moveTo(new Move(1,0), 0);
  // // //    game3.moveTo(new Move(1,1), 1);
  // // //    game3.moveTo(new Move(1,2), 0);
  // // //    game3.moveTo(new Move(2,0), 1);
  // // //    game3.moveTo(new Move(2,2), 0);
  // // //    game3.moveTo(new Move(3,1), 0);
  // // //    game3.moveTo(new Move(3,2), 0);
  // //       // game3.moveTo(new Move(1,0), 1);
  // //       // game3.moveTo(new Move(1,2), 0);
  // //       // game3.moveTo(new Move(5,4), 1);
  // //       // game3.moveTo(new Move(5,2), 0);
  // //       // game3.moveTo(new Move(2,4), 1);
  // //       // //game3.moveTo(new Move(1,6), 0);
  // //       // game3.moveTo(new Move(2,6), 1);
  // //       // game3.moveTo(new Move(3,7), 0);
  // //       // game3.moveTo(new Move(1,1), 1);
  // //       // game3.moveTo(new Move(1,3), 1);
  // //       // game3.moveTo(new Move(4,1), 1);
  // //       // game3.moveTo(new Move(4,2), 1);
  // //       // game3.moveTo(new Move(6,5), 1);
 
  // //       //*************************************************
  // //       // System.out.println(game3);
  // //       // System.out.println(Evaluator.evalScore(game3));
  // //       // Move[] list = game3.isValidMoveList(0);
  // //       // for(Move m : list){
  // //       //  System.out.println(m);
  // //       // }
 
  // //       //System.out.println("Network: "+Network.hasValidNetwork(game3, 1));
  // //       //*************************************************
 
  // //       /*
  // //       Board test = new Board();
  // //       //adding 10 chips of each color
  // //       test.moveTo(new Move(3,1), 0);
  // //       test.moveTo(new Move(2,3), 0);
  // //       test.moveTo(new Move(3,5), 0);
  // //       test.moveTo(new Move(2,6), 0);
  // //       test.moveTo(new Move(1,2), 0);
  // //       test.moveTo(new Move(6,7), 0);
  // //       test.moveTo(new Move(4,0), 0);
  // //       test.moveTo(new Move(6,4), 0);
  // //       test.moveTo(new Move(6,0), 0);
  // //       test.moveTo(new Move(4,3), 0);
 
  // //       test.moveTo(new Move(0,1), 1);
  // //       test.moveTo(new Move(0,3), 1);
  // //       test.moveTo(new Move(0,5), 1);
  // //       test.moveTo(new Move(1,6), 1);
  // //       test.moveTo(new Move(3,6), 1);
  // //       test.moveTo(new Move(5,1), 1);
  // //       test.moveTo(new Move(6,3), 1);
  // //       test.moveTo(new Move(6,5), 1);
  // //       test.moveTo(new Move(7,1), 1);
  // //       test.moveTo(new Move(1,1), 1);
 
         
  // //       System.out.println(test);
  // //       //should return a step moves list?
  // //       Move[] hi = test.isValidMoveList(0);
  // //       int i;
  // //       for(i =0 ; i<hi.length ; i++){
  // //           System.out.println(hi[i]);
  // //       }
  // //       System.out.println("The number of move = " + i);
 
 
  // //       BoardNode[] x = test.startingNodes(0);
  // //       //System.out.println("The number of move = " + i);
  // //       for(int j = 0; j<x.length; j++){
  // //           System.out.println(x[j]);
  // //       }
  // //       //System.out.println(test.isValidMoveList(0));
         
  // //       System.out.println("Creating Board");
  // //       Board game= new Board();
  // //       System.out.println("Successful");
         
  // //       System.out.println("Checking corners:");
  // //       Move addc1= new Move(0,0);
  // //       System.out.println("Should be false: "+game.isValidMove(addc1,0));
  // //       Move addc2= new Move(7,7);
  // //       System.out.println("Should be false: "+game.isValidMove(addc2,0));
  // //       Move addc3= new Move(0,7);
  // //       System.out.println("Should be false: "+game.isValidMove(addc3,0));
  // //       Move addc4= new Move(7,0);
  // //       System.out.println("Should be false: "+game.isValidMove(addc4,0));
 
  // //       System.out.println("Checking isValidMove on Add: ");
         
  // //       Move add1= new Move(2,3);
  // //       System.out.println("Should be true: "+game.isValidMove(add1,0));
  // //       game.moveTo(add1, 0);
  // //       Move add2= new Move(2,4);
  // //       System.out.println("Should be true: "+game.isValidMove(add2,0));
  // //       game.moveTo(add2, 0);
  // //       Move add3= new Move(1,3);
  // //       System.out.println("Should be false: "+game.isValidMove(add3,0));
  // //       Move add4= new Move(2,5);
  // //       System.out.println("Should be false: "+game.isValidMove(add4,0));
  // //       Move add5= new Move(2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(add5,0));
 
  // //       System.out.println("Checking isValidMove on Step: ");
  // //       Move step1= new Move(2,1,2,3);
  // //       System.out.println("Should be true: "+game.isValidMove(step1,0));
  // //       Move step2= new Move(2,4,2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(step2,0));
  // //       Move step3= new Move(2,5,2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(step3,0));
  // //       Move step4= new Move(1,3,2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(step4,0));
  // //       game.moveTo(step1, 0);
         
  // //       Move step5= new Move(2,2,2,4);
  // //       System.out.println("Should be true: "+game.isValidMove(step5,0));
  // //       game.moveTo(step5, 0);
  // //       System.out.println("Should be false: "+game.isValidMove(add5,0));
  // //       Move step6= new Move(2,3,2,5);
  // //       System.out.println("Should be false: "+game.isValidMove(step6,0));
  // //       Move add6= new Move(2,4);
  // //       game.moveTo(add6, 0);
  // //       System.out.println("Should be false: "+game.isValidMove(add5,0));
  // //       Move step7= new Move(3,3,2,1);
  // //       System.out.println("Should be false: "+game.isValidMove(step7,0));
  // //       Move step8= new Move(5,6,2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(step8,0));
         
     
 
  // //       System.out.println("Checking isValidMoveList:");
  // //       Board game2= new Board();
  // //       Move[] vmoves2 = game2.isValidMoveList(0);
  // //       Move[] vmoves=game.isValidMoveList(0);
  // //       for(int b = 0; b<vmoves.length; b++){
  // //           System.out.println("Game1, Available Moves: "+ vmoves[b].toString());   
  // //       }
  // //       System.out.println("");
  // //       for(int c = 0; c<vmoves2.length; c++){
  // //           System.out.println("EmptyGame, Available Moves: "+ vmoves2[c].toString());
             
  // //       }
  // //       for(int r=0; r<8;r++){
  // //           for(int j=0;j<8;j++){
  // //               game2.gameBoard[r][j].changeColor(0);
  // //           }
  // //       }
  // //       vmoves2=game2.isValidMoveList(0);
  // //       for(int d = 0; d<vmoves2.length; d++){
  // //           System.out.println("FullGame, Available Moves: "+ vmoves2[d].toString());
     
  // //       }
  // //       /*
  // //       Board a = new Board();
  // //       a.moveTo(new Move(0, 1), 1);
  // //       a.moveTo(new Move(1, 1), 1);
  // //       a.moveTo(new Move(2, 3), 1);
  // //       a.moveTo(new Move(1, 5), 1);
  // //       a.moveTo(new Move(7, 3), 1);
  // //       a.moveTo(new Move(3, 1), 1);
  // //       a.moveTo(new Move(6, 2), 1);
  // //       a.moveTo(new Move(6, 6), 1);
 
  // //       a.moveTo(new Move(1, 2), 0);
  // //       a.moveTo(new Move(2, 2), 0);
  // //       a.moveTo(new Move(4, 0), 0);
  // //       a.moveTo(new Move(6, 4), 0);
  // //       a.moveTo(new Move(4, 6), 0);
  // //       a.moveTo(new Move(4, 7), 0);
  // //       a.moveTo(new Move(5, 2), 0);
  // //       a.moveTo(new Move(2, 7), 0);
         
         
         
         
  // //       System.out.println(a);
  // //       System.out.println(getNumNodes(a, 1));
  // //       System.out.println(getNumNodes(a, 0));
         
  // //       System.out.println("Creating Board");
  // //       Board game= new Board();
  // //       System.out.println("Successful");
         
  // //       System.out.println("Checking corners:");
  // //       Move addc1= new Move(0,0);
  // //       System.out.println("Should be false: "+game.isValidMove(addc1,0));
  // //       Move addc2= new Move(7,7);
  // //       System.out.println("Should be false: "+game.isValidMove(addc2,0));
  // //       Move addc3= new Move(0,7);
  // //       System.out.println("Should be false: "+game.isValidMove(addc3,0));
  // //       Move addc4= new Move(7,0);
  // //       System.out.println("Should be false: "+game.isValidMove(addc4,0));
 
  // //       System.out.println("Checking isValidMove on Add: ");
         
  // //       Move add1= new Move(2,3);
  // //       System.out.println("Should be true: "+game.isValidMove(add1,0));
  // //       game.moveTo(add1, 0);
  // //       Move add2= new Move(2,4);
  // //       System.out.println("Should be true: "+game.isValidMove(add2,0));
  // //       game.moveTo(add2, 0);
  // //       Move add3= new Move(1,3);
  // //       System.out.println("Should be false: "+game.isValidMove(add3,0));
  // //       Move add4= new Move(2,5);
  // //       System.out.println("Should be false: "+game.isValidMove(add4,0));
  // //       Move add5= new Move(2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(add5,0));
 
  // //       System.out.println("Checking isValidMove on Step: ");
  // //       Move step1= new Move(2,1,2,3);
  // //       System.out.println("Should be true: "+game.isValidMove(step1,0));
  // //       Move step2= new Move(2,4,2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(step2,0));
  // //       Move step3= new Move(2,5,2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(step3,0));
  // //       Move step4= new Move(1,3,2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(step4,0));
  // //       game.moveTo(step1, 0);
         
  // //       Move step5= new Move(2,2,2,4);
  // //       System.out.println("Should be true: "+game.isValidMove(step5,0));
  // //       game.moveTo(step5, 0);
  // //       System.out.println("Should be false: "+game.isValidMove(add5,0));
  // //       Move step6= new Move(2,3,2,5);
  // //       System.out.println("Should be false: "+game.isValidMove(step6,0));
  // //       Move add6= new Move(2,4);
  // //       game.moveTo(add6, 0);
  // //       System.out.println("Should be false: "+game.isValidMove(add5,0));
  // //       Move step7= new Move(3,3,2,1);
  // //       System.out.println("Should be false: "+game.isValidMove(step7,0));
  // //       Move step8= new Move(5,6,2,3);
  // //       System.out.println("Should be false: "+game.isValidMove(step8,0));
         
  // //       Board game2= new Board();
  // //       Move add7=new Move(6,6);
  // //       game2.moveTo(add7, 0);
  // //       Move add8= new Move(4,6);
  // //       game2.moveTo(add8, 0);
  // //       Move add9= new Move(5,6);
  // //       System.out.println("Should be false: "+game2.isValidMove(add9,0));
     
 
  // //       System.out.println("Checking isValidMoveList:");
  // //       game2=new Board();
  // //       DList vmoves2 = game2.isValidMoveList(0);
  // //       DList vmoves=game.isValidMoveList(0);
  // //       System.out.println("Game1, Available Moves: "+ vmoves.toString());
  // //       System.out.println("");
  // //       System.out.println("EmptyGame, Available Moves: "+ vmoves2.toString());
  // //       for(int i=0; i<8;i++){
  // //           for(int j=0;j<8;j++){
  // //               game2.gameBoard[i][j].changeColor(0);
  // //           }
  // //       }
  // //       vmoves2=game2.isValidMoveList(0);
  // //       System.out.println("FullGame, Available Moves: "+ vmoves2.toString());
  // //       */
  //   }
}