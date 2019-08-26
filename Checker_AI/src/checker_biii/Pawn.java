package checker_biii;

import java.util.ArrayList;

public class Pawn {
	String  id = "default";
	
	public Pawn() {
	}
	
	public Pawn(Pawn pawn){
        this.id = pawn.id;

    }

	public static void main(String[] args) {
		new Pawn();		
	}
	
	public void setID(String args) {
		id = args;
	}
	
	public String getName() {
		String my_str = "default";
		if (id == "default") {
			 my_str = "   ";
		}else {
			if (id == "BP") {
				my_str = " BP";
			}else {
				if (id == "RP") {
					my_str = " RP";
				}
			}
		}
		if (id == "RK") {
			my_str = " RK";
		}
		if (id == "BK") {
			my_str = " BK";
		}
		return my_str;
	}
	


// get user move
	public int[] get_op_move(int x, int y,Pawn[][] ps) {
		//check the pawn type
		int[] possible_move = new int[11];
		if (ps[x][y].id == "RP" ||ps[x][y].id == "RK"  ) {
			
			//from (x,y)
			possible_move[0] = x;
			possible_move[1] = y;
			int l = 0;
			//check if the place where we want to go is inside the board
			if (x-1 >=0 && y+1 <8) {
				if (ps[x-1][y+1].id== "default") {
					//for index
					l = l+2;
					possible_move[l] = x-1;
					possible_move[l+1] = y+1;
					
				}}

			//check if the place where we want to go is inside the board
			if (x-2>=0 && y+2 <8) {
				//see if it can attack opponent
				if ((ps[x-1][y+1].id== "BP" || ps[x-1][y+1].id== "BK")&&ps[x-2][y+2].id== "default" ) {
					l = l+2;
					possible_move[l] = x-2;
					possible_move[l+1] = y+2;
				}}
			
			
			//check if the place where we want to go is inside the board
			if (x-1>=0 && y-1>=0) {
				if (ps[x-1][y-1].id== "default") {
					l = l+2;
					possible_move[l] = x-1;
					possible_move[l+1] = y-1;
				}
			}
			
			//check if the place where we want to go is inside the board

			if (x-2 >=0 && y-2>=0) {
				//see if it can attack opponent
				if ((ps[x-1][y-1].id== "BP" || ps[x-1][y-1].id== "BK")&& ps[x-2][y-2].id== "default" ) {
					l = l+2;
					possible_move[l] = x-2;
					possible_move[l+1] = y-2;
				}
			}
			
		//for RK, it can move to additional 2 places. 	
		if(id =="RK") {
			if(x+1 <8 && y+1<8) {
				if (ps[x+1][y+1].id== "default") {
					l = l+2;
					possible_move[l] = x+1;
					possible_move[l+1] = y+1;
				}
			}
			
			if(x+2<8 && y+2 < 8){
				if ((ps[x+1][y+1].id== "BP" || ps[x+1][y+1].id== "BK") && ps[x+2][y+2].id== "default") {
					l = l+2;
					possible_move[l] = x+2;
					possible_move[l+1] = y+2;
				}
			}

			
			if(x+1<8 && y-1>=0) {
				if (ps[x+1][y-1].id== "default") {
					l = l+2;
					possible_move[l] = x+1;
					possible_move[l+1] = y-1;
				}}
			
			
			if(x+2 <8 && y-2>=0) {
				if ((ps[x+1][y-1].id== "BP" || ps[x+1][y-1].id== "BK") && ps[x+2][y-2].id== "default" ) {
					l = l+2;
					possible_move[l] = x+2;
					possible_move[l+1] = y-2;
				}
			}
		}
		//save the number info of available move
		// if it is from (1,2) to (0,1) , (0,3) , this array would be {1,2,0,1,0,3,null,null,null,null,4}
		//it is not really efficient..
		// it returns the possible movement of selected piece. 
		possible_move[10] = l;

		
	}
		return possible_move;
}
	
	//get available place for computer
	//same as get_op_move, which is right in front of this code.
	//only difference is just the direction
	public int[] get_available_move(int x, int y,Pawn[][] ps) {
		int[] possible_move = new int[11];
		if (ps[x][y].id == "BP" ||ps[x][y].id == "BK"  ) {
			
			possible_move[0] = x;
			possible_move[1] = y;
			int l = 0;
			if (x+1 <8 && y+1 <8) {
				if (ps[x+1][y+1].id== "default") {
					l = l+2;
					possible_move[l] = x+1;
					possible_move[l+1] = y+1;
					
				}}
			
			if (x+2<8 && y+2 <8) {
				if ((ps[x+1][y+1].id== "RP" || ps[x+1][y+1].id== "RK") &&  ps[x+2][y+2].id== "default") {
					l = l+2;
					possible_move[l] = x+2;
					possible_move[l+1] = y+2;
				}}
			
			if (x+1<8 && y-1>=0) {
				if (ps[x+1][y-1].id== "default") {
					l = l+2;
					possible_move[l] = x+1;
					possible_move[l+1] = y-1;
				}
			}
			
			if (x+2 <8 && y-2>=0) {
				if ((ps[x+1][y-1].id== "RP" || ps[x+1][y-1].id== "RK")&& ps[x+2][y-2].id== "default" ) {
					l = l+2;
					possible_move[l] = x+2;
					possible_move[l+1] = y-2;
				}
			}
		if(id =="BK") {
			if(x-1 >=0 && y+1<8) {
				if (ps[x-1][y+1].id== "default") {
					l = l+2;
					possible_move[l] = x-1;
					possible_move[l+1] = y+1;
				}
			}
			
			if(x-2>=0 && y+2 < 8){
				if ((ps[x-1][y+1].id== "RP" || ps[x-1][y+1].id== "RK") && ps[x-2][y+2].id== "default") {
					l = l+2;
					possible_move[l] = x-2;
					possible_move[l+1] = y+2;
				}
			}

			
			if(x-1>=0 && y-1>=0) {
				if (ps[x-1][y-1].id== "default") {
					l = l+2;
					possible_move[l] = x-1;
					possible_move[l+1] = y-1;
				}}
			
			
			if(x-2 >=0 && y-2>=0) {
				if ((ps[x-1][y-1].id== "RP" || ps[x-1][y-1].id== "RK") && ps[x-2][y-2].id== "default" ) {
					l = l+2;
					possible_move[l] = x-2;
					possible_move[l+1] = y-2;
				}
			}
		}
		possible_move[10] = l;
		
	}
		return possible_move;
				
}
	
	public ArrayList<nd> c_move(int x, int y, nd parent, int depth){
		Pawn[][] plist = copy_array(parent.get_array());
		int[] move = get_available_move(x,y,plist);
		ArrayList<nd> nodes = new ArrayList<nd>() ;
		nd a;

		
		for (int i = 2; i < move[10] +1 ;i=i+2) {
			plist[move[i]][move[i+1]].setID(plist[x][y].id);
			plist[x][y].setID("default");
			
			if (y - move[i + 1] == 2 || y - move[i + 1] == -2) {
				int m = (y - move[i + 1]) / 2;
				int n = (x - move[i]) / 2;
				plist[x - n][y - m].setID("default");
				}
			//check if it is the last row for BP
			if (move[i]==7) {
				plist[move[i]][move[i + 1]].setID("BK");
			}
			a = new nd(plist,depth);
			parent.set_child(a);
			nodes.add(a);
			plist =copy_array(parent.get_array());
			
		}
		return nodes;
	}
	
	public ArrayList<nd> u_move(int x, int y, nd parent, int depth) {
		Pawn[][] plist = copy_array(parent.get_array());
		int[] move = get_op_move(x,y,plist);
		nd a;
		ArrayList<nd> nodes = new ArrayList<nd>() ;
		for (int i = 2; i < move[10]+1;i=i+2) {
			plist[move[i]][move[i+1]].setID(plist[x][y].id);
			plist[x][y].setID("default");
			
			if (y - move[i + 1] == 2 || y - move[i + 1] == -2) {
				int m = (y - move[i + 1]) / 2;
				int n = (x - move[i]) / 2;
				plist[x - n][y - m].setID("default");
				}
			//check if it is the last row for BP
			if (move[i]==0) {
				plist[move[i]][move[i + 1]].setID("RK");
			}
			a = new nd(plist,depth);
			parent.set_child(a);
			nodes.add(a);
			plist =copy_array(parent.get_array());			
		}
		return nodes;
	}
	
	
	
	public Pawn[][] copy_array(Pawn[][] p) {
		Pawn[][] copy_list = new Pawn[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copy_list[i][j] = new Pawn(p[i][j]);
			}
		}
		return copy_list;
	}
	
	
}

	
	





