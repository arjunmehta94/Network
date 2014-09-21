/* BoardNode.java */

package player;

public class BoardNode{

	//Variables
	public final static int UPLEFT = 1;
	public final static int UP = 2;
	public final static int UPRIGHT = 3;
	public final static int RIGHT = 4;
	public final static int LEFT = 5;
	public final static int DOWNLEFT = 6;
	public final static int DOWN = 7;
	public final static int DOWNRIGHT = 8;
	
	private int color;
	private int x;
	private int y;
	private boolean visit = false;
	private int dir;
	//Methods

	//Constructs a BoardNode at position (x,y) of color and direction -1
	//@param x is of type int and represents x coordinate of BoardNode
	//@param y is of type int and represents y coordinate of BoardNode
	public BoardNode(int x, int y){
		color= -1;
		this.x=x;
		this.y=y;
		dir = -1;
		}

	//@return the direction of the node
	public int getDir(){
		return dir;
	}

	//Sets the direction of the node
	//@param direction of type int
	public void setDir(int direction){
		dir = direction;
	}

	//@return color of type int and is either 0(black) or 1(white)
	public int getColor(){
		return color;
	}


	//@return the X position of the node
	public int getX(){
		return x;
	}


	//Returns the Y position of the node
	public int getY(){
		return y;
	}

	//Changes the color of the node
	//@param color of type int and is either 0(black) or 1(white)
	public void changeColor(int color){
		if (color==0 || color==1 || color==-1)
			this.color=color;
	}


	//@return true if the node has no color(empty) and false if it has a color
	public boolean isEmpty(){
		return color==-1;
	}


	//@return true if the node has veen visited and false if it hasn't
	public boolean getVisit(){
		return visit;
	}

	//Changes the visit of the node
	//@param val of type boolean
	public void changeVisit(boolean val){
		visit = val;
	}


	//@return the X and Y position as a string
	public String printNode(){
		return "X is: " + x + " Y is: " + y;
	}

	//@return the node as a string
	public String toString(){

		if(color == -1){
			if(x == 0 && y == 0 || x == 7 && y == 7 || x == 7 && y == 0 || x == 0 && y == 7){
				return "| " + "X ";
			}
			return "| " + "  ";
		}
		else if(color == 0){
			return "| " + "B ";
		}
		else if(color == 1){
			return "| " + "W ";
		}
		return null;
	}
}