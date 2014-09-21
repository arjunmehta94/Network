/******Evaluator.java******/
package player;
//takes a board and returns a "score".
//(#of white connections - #of black connections) / total connections <= 1.
public class Evaluator{
	
	public final static int UPLEFT = 1;
	public final static int UP = 2;
	public final static int UPRIGHT = 3;
	public final static int RIGHT = 4;
	public final static int LEFT = 5;
	public final static int DOWNLEFT = 6;
	public final static int DOWN = 7;
	public final static int DOWNRIGHT = 8;


	// evalScore is the main Evaluator function, this calls various helper methods to find the number of connection between white chips and black chips,
    // and then subtract them to give the total number of connections.
    // @param board is a Gameboard, an internal representation of the Game.
    // @param int color takes in the color
    // @return an evalscore which is used my minimax
	public static double evalScore(Board board, int color){
		//first find array of each color;
		BoardNode[] myArray = nodeFinder(board, color);
		BoardNode[] opponentArray = nodeFinder(board, 1-color);
		int myScore = numConnections(board, myArray);
		int opponentScore = numConnections(board, opponentArray);
		if(color == 0){
			return ((double)(opponentScore - myScore));
		} else if ( color== 1){
			return ((double)(myScore - opponentScore));
		}
		
		return 0.0;
	}
	
	// nodeFinder finds the nodes present of the board for a given color.
    // @param board is a Gameboard, an internal representation of the Game.
    // @param color represents the side
    // @return an array of BoardNodes
	//returns an array of BoardNodes of a given color for a given board
	public static BoardNode[] nodeFinder(Board board, int color){
		BoardNode[] nodeArray = new BoardNode[Board.getNumNodes(board, color)];
		int pos = 0;
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(i == 7 && j == 7){
					break;
				}
				if(board.getNode(j, i).getColor() == color){
					nodeArray[pos] = board.getNode(j, i);
					pos++;
				}
			}	
		}
		return nodeArray;
	}
	
	
	// numConnections uses the array to compute the number of connections between various nodes.
    // @param board is a Gameboard, an internal representation of the Game.
    // @param nodeArray an array nodes which need to be checked for number of connections
    // @return an integer number of connections
	//returns the number of connections for a given nodeArray
	public static int numConnections(Board board, BoardNode[] nodeArray){
		int connections = 0;
		// if(nodeArray.length < 6){
		// 	//System.err.println("Not enough chips");
		// }
		for(int i = 0; i<nodeArray.length; i++){
			for(int j = i+1; j<nodeArray.length; j++){
				connections += connections(board, nodeArray[i], nodeArray[j]);
			}
		}
		
		return connections;
	}
	
	 // connections check if 2 given nodes form a connection, 1 if connected 0 if not
    // @param board is a Gameboard, an internal representation of the Game.
    // @param node1
    // @param node2
    // @return either 1 or 0 indicating the connection.
	public static int connections(Board board, BoardNode node1, BoardNode node2){
		if(isConnected(board, node1, node2)){
			return 1;
		}
		return 0;
	}

	 // determines if 2 nodes are connected given the restrictions described in readme.
    // @param board is a Gameboard, an internal representation of the Game.
    // @param node1
    // @param node2
	// returns true if 2 nodes are connected - either orthogonally or diagonally.
	public static boolean isConnected(Board board, BoardNode node1, BoardNode node2){
		//vertically aligned
		if(isVerticallyAligned(node1, node2) && node1.getColor() == node2.getColor() && notBlocked(board, node1, node2)){
			return true;
		}
		//horizontally aligned
		if(isHorizontallyAligned(node1, node2) && node1.getColor() == node2.getColor() && notBlocked(board, node1, node2)){
			return true;
		}
		//diagonally aligned
		if(isDiagonallyAligned(node1, node2) && node1.getColor() == node2.getColor() && notBlocked(board, node1, node2)){
			return true;
		}
		return false;

	}

	//determines if 2 nodes are vertically aligned
    // @param node1
    // @param node2
    // @return true if nodes are vertically aligned
	public static boolean isVerticallyAligned(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		
		int y1 = node1.getY();
		
		int x2 = node2.getX();
		
		int y2 = node2.getY();
		
		if(x1 == x2 && y1 != y2){
			return true;
		}
		return false;
	}
	
	 //determines if 2 nodes are horizontally aligned
    // @param node1
    // @param node2
    // @return true if nodes are horizontally aligned
	public static boolean isHorizontallyAligned(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 == y2 && x1 != x2){
			return true;
		}
		return false;
	}
	
	//determines if 2 nodes are diagonally aligned
    
    // @param node1
    // @param node2
    // @return true if nodes are diagonally aligned
	public static boolean isDiagonallyAligned(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(Math.abs(x2-x1) == Math.abs(y2-y1)){
			return true;
		}
		return false;
	}
	
	//determines if 2 connected nodes have no enemy nodes between them
    // @param board is a Gameboard, an internal representation of the Game.
    // @param node1
    // @param node2
    // returns true if 2 nodes are not blocked
	public static boolean notBlocked(Board board, BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(isVerticallyAligned(node1, node2)){
			//vertically down
			
			if(isDown(node1,node2) == true){
				for(int i = y1 + 1; i<y2; i++){
					if(board.getNode(x1,i).getColor() != -1 ){ 
						return false;
					}
				}
			}
			//vertically up
			
			else if(isUp(node1, node2) == true){
				for(int i = y1 - 1; i>y2; i--){
					if(board.getNode(x1,i).getColor() != -1){
					}
				}
			}
		}
		if(isHorizontallyAligned(node1, node2)){
			//horizontally right
			
			if(isRight(node1, node2) == true){
				for(int i = x1 + 1; i<x2; i++){
					if(board.getNode(i,y1).getColor() != -1){
						return false;
					}
				}
			}
			//horizontally left
			
			else if(isLeft(node1, node2) == true){
				for(int i = x1 -1; i>x2; i--){
					if(board.getNode(i,y1).getColor() != -1){
						return false;
					}
				}
			}
		}
		if(isDiagonallyAligned(node1, node2)){
			//diagonally right down
			
			if(isDownRight(node1, node2) == true){
				int change = y2-y1;
				for(int i = 1; i<change; i++){
					if(board.getNode(x1+i,y1+i).getColor() != -1){
						return false;
					}
				}
			}
			//diagonally left up
			
			if(isUpLeft(node1, node2) == true){
				int change = y1 - y2;
				for(int i = 1; i<change; i++){
					if(board.getNode(x1-i,y1-i).getColor() != -1){
						return false;
					}
				}
			}
			//diagonally left down
			
			if(isDownLeft(node1, node2) == true){
				int change = y2-y1;
				for(int i = 1; i<change; i++){
					if(board.getNode(x1-i,y1+i).getColor() != -1){
						return false;
					}
				}
			}
			//diagonally right up
			
			if(isUpRight(node1, node2) == true){
				int change = x2-x1;
				for(int i = 1; i<change; i++){
					if(board.getNode(x1+i,y1-i).getColor() != -1){
						return false;
					}
				}
			}

		}
		return true;
	}
	
	//determines if 1 node is vertically down from the other
    // @param node1
    // @param node2
    // @returns true if 1 node is vertically down from the other
	public static boolean isDown(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 < y2 && x1 == x2){
			return true;
		}
		return false;
	}

	//determines if 1 node is vertically up from the other
    // @param node1
    // @param node2
    // @returns true if 1 node is vertically up from the other
	public static boolean isUp(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 > y2 && x1 == x2){
			return true;
		}
		return false;
	}
	
	//determines if 1 node is horizontally right from the other
    // @param node1
    // @param node2
    // @returns true if 1 node is horizontally right from the other
	public static boolean isRight(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 == y2 && x1 < x2){
			return true;
		}
		return false;
	}

	 //determines if 1 node is horizontally left from the other
    // @param node1
    // @param node2
    // @returns true if 1 node is horizontally left from the other
	public static boolean isLeft(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 == y2 && x1 > x2){
			return true;
		}
		return false;
	}


	//determines if 1 node is diagonally right down from the other
    // @param node1
    // @param node2
    // @returns true if 1 node is diagonally right down from the other
	public static boolean isDownRight(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 < y2 && x1 < x2){
			return true;
		}
		return false;
	}

	//determines if 1 node is diagonally left up from the other
    // @param node1
    // @param node2
    // @returns true if 1 node is diagonally left up from the other
	public static boolean isUpLeft(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 > y2 && x1 > x2){
			return true;
		}
		return false;
	}

	//determines if 1 node is diagonally down left from the other
    // @param node1
    // @param node2
    // @returns true if 1 node is diagonally down left from the other
	public static boolean isDownLeft(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 < y2 && x1 > x2){
			return true;
		}
		return false;
	}

	//determines if 1 node is diagonally up right from the other
    // @param node1
    // @param node2
    // @returns true if 1 node is diagonally up right from the other
	public static boolean isUpRight(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		if(y1 > y2 && x1 < x2){
			return true;
		}
		return false;
	}

	//sets the direction value of the second node based on its direction
    // @param node1
    // @param node2
    // @returns an int value representing a direction
	public static int getDirection(BoardNode node1, BoardNode node2){
		int x1 = node1.getX();
		int y1 = node1.getY();
		int x2 = node2.getX();
		int y2 = node2.getY();
		


		if(isDown(node1, node2) == true){
			node2.setDir(DOWN);
		}
		else if(isUp(node1, node2) == true){
			node2.setDir(UP);
			}
		else if(isLeft(node1, node2) == true){
			node2.setDir(LEFT);
		}
		else if(isRight(node1, node2) == true){
			node2.setDir(RIGHT);
		}
		else if(isDownLeft(node1, node2) == true){
			node2.setDir(DOWNLEFT);
		}
		else if(isDownRight(node1, node2) == true){
			node2.setDir(DOWNRIGHT);
		}
		else if(isUpRight(node1, node2) == true){
			node2.setDir(UPRIGHT);
		}
		else if(isUpLeft(node1, node2) == true){
			node2.setDir(UPLEFT);
		}
		else{
			node2.setDir(-1);
		}

		return node2.getDir();
	
	}
	
}	
