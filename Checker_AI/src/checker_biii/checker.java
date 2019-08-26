package checker_biii;

import java.util.ArrayList;
import java.util.Scanner;

public class checker {
	static board brd = new board();
	static Scoring score = new Scoring();
	static Pawn[][] p_list = brd.initial_array();
	
	static int num;

	public static void main(String[] args){
		new checker();
		//draw board
		brd.drawing_board(p_list);
		ASK();
	}

	private static void ASK() {
		Scanner reader = new Scanner(System.in);
		String Red;
		String Black;
		int b_ply = 0;
		int r_ply=0;
		int combination=0;
		
		System.out.println("Type AI or H(Human)for RED pawn ");
		Red = reader.next();
		if (Red.equals("AI") || Red.equals("ai")) {
			combination = combination+10;
			System.out.println("Ply?");
			r_ply =reader.nextInt();
		}
		

		System.out.println("Type AI or H(Human) for BLACK pawn ");
		Black = reader.next();
		if (Black.equals("AI") || Black.equals("ai")) {
			combination = combination+1;
			System.out.println("Ply?");
			b_ply =reader.nextInt();
		}
		//0,1,10,11
		//0 --> both human, 1 --> black ai , 10 --> red ai, 11 --> black and red ai
		switch(combination) {
			case 0: 
				both_human();
				break;
			case 1: 
				Black_AI(b_ply);
				break;	
			case 10: 
				Red_AI(r_ply);
				break;	
			case 11: 
				both_AI(b_ply,r_ply);
				break;				
		
		}
	}

	private static void both_AI(int b_ply, int r_ply) {
		while (true) {
			//if user enters quit, then stop the program
			long a = System.currentTimeMillis();
			p_list = comp_move(p_list,b_ply);
			long b = System.currentTimeMillis();
			System.out.println(b-a+ " milliseconds");
			
			//evaluate
			brd.drawing_board(p_list);
			int win = score.win(p_list);
			if (win==1) {
				System.out.println("Black won");
				break;
			}
			if (win==-1) {
				System.out.println("Red won");
				break;
			}
			
			a = System.currentTimeMillis();
			p_list = r_comp_move(p_list,r_ply);
			b = System.currentTimeMillis();
			System.out.println(b-a+ " milliseconds");
			
			brd.drawing_board(p_list);			
			//check
			win = score.win(p_list);
			if (win==1) {
				System.out.println("Black won");
				break;
			}
			if (win==-1) {
				System.out.println("Red won");
				break;
			}
		}				
		
	}

	private static void Red_AI(int r_ply) {
		int quit;
		while (true) {
			//human
			//if user enters quit, then stop the program
			quit =input(p_list);
			if (quit==1) {
				break;
			}
			//evaluate
			System.out.println("score : "+score.score(p_list));
			brd.drawing_board(p_list);
			int win = score.win(p_list);
			if (win==1) {
				System.out.println("Black won");
				break;
			}
			if (win==-1) {
				System.out.println("Red won");
				break;
			}
			
			
			//computer
			long a = System.currentTimeMillis();
			p_list = r_comp_move(p_list,r_ply);
			long b = System.currentTimeMillis();
			System.out.println(b-a+ " milliseconds");

			//evaluate
			brd.drawing_board(p_list);			
			win = score.win(p_list);
			if (win==1) {
				System.out.println("Black won");
				break;
			}
			if (win==-1) {
				System.out.println("Red won");
				break;
			}
		}				
	}

	
	private static void Black_AI(int b_ply) {
		int quit;
		while (true) {
			//user
			quit =input(p_list);
			if (quit==1) {
				break;
			}
			
			//evaluate
			System.out.println("score : "+score.score(p_list));
			brd.drawing_board(p_list);
			int win = score.win(p_list);
			if (win==1) {
				System.out.println("Black won");
				break;
			}
			if (win==-1) {
				System.out.println("Red won");
				break;
			}
			
			
			//computer
			long a = System.currentTimeMillis();
			p_list = comp_move(p_list,b_ply);
			long b = System.currentTimeMillis();
			System.out.println(b-a+ " milliseconds");
			
			//evaluate
			brd.drawing_board(p_list);
			win = score.win(p_list);
			if (win==1) {
				System.out.println("Black won");
				break;
			}
			if (win==-1) {
				System.out.println("Red won");
				break;
			}
		}		
	}

	private static void both_human() {
		int quit;
		while (true) {
			//human
			quit =input(p_list);
			if (quit==1) {
				break;
			}
			
			//evaluate
			System.out.println("score : "+score.score(p_list));
			brd.drawing_board(p_list);
			int win = score.win(p_list);
			if (win==1) {
				System.out.println("Black won");
				break;
			}
			if (win==-1) {
				System.out.println("Red won");
				break;
			}

			
			//human
			quit =input(p_list);
			if (quit==1) {
				break;
			}
			
			//evaluate
			System.out.println("score : "+score.score(p_list));
			brd.drawing_board(p_list);
			win = score.win(p_list);
			System.out.println(win);
			if (win==1) {
				System.out.println("Black won");
				break;
			}
			if (win==-1) {
				System.out.println("Red won");
				break;
			}
		}			
	}


	//black move 
	public static Pawn[][] comp_move(Pawn[][] p_list, int dep) {
		ArrayList<nd> nodes = new ArrayList<nd>();
		//only one, it will contain the Pawn array that we are looking for.
		nd root = new nd(copy_array(p_list), 0);
		nd parent;
		nodes.add(root);
		
		ArrayList<nd> nds = new ArrayList<nd>();
		//make children of node which is in 'nodes' array
		//nodes which have depth ==1 contain array of 'Black' pawn movement 

		for (int n =0; n < nodes.size(); n++) {
			parent = nodes.get(n);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (parent.get_array()[i][j].id == "BP" || parent.get_array()[i][j].id == "BK") {
						//depth ==1
						nds.addAll(parent.get_array()[i][j].c_move(i, j,parent, 1));
			}
			}}

			}
		
		for (int d = 2; d<= dep; d=d+2) {
		nodes.clear();
		//make children of node which is in 'nds' array
		for (int n =0; n< nds.size();n++) {
			parent = nds.get(n);
			for (int ii = 0; ii < 8; ii++) {
				for (int jj = 0; jj < 8; jj++) {
		 			if (parent.get_array()[ii][jj].id == "RP" || parent.get_array()[ii][jj].id == "RK") {
		 				nodes.addAll(parent.get_array()[ii][jj].u_move(ii, jj,nds.get(n),d));
		}}}}
		
		//depth check
		if (dep == d) {
			break;
		}
		
		nds.clear();
		for (int n =0; n < nodes.size(); n++) {
			parent = nodes.get(n);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (parent.get_array()[i][j].id == "BP" || parent.get_array()[i][j].id == "BK") {
						nds.addAll(parent.get_array()[i][j].c_move(i, j,parent, d+1));
			}
			}}

			}

		}
		System.out.println("Score : " + root.minimax(Integer.MIN_VALUE,Integer.MAX_VALUE,0));
		p_list = root.get_array();
		return p_list;
	}
	
	//red move , logic is almost the same with comp_move
	public static Pawn[][] r_comp_move(Pawn[][] p_list, int dep) {
		ArrayList<nd> nodes = new ArrayList<nd>();
		nd root = new nd(copy_array(p_list), 0);
		nd parent;
		nodes.add(root);
		ArrayList<nd> nds = new ArrayList<nd>();
		//nodes which have depth ==1 contain array of 'red' pawn movement 
		for (int n =0; n < nodes.size(); n++) {
			parent = nodes.get(n);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (parent.get_array()[i][j].id == "RP" || parent.get_array()[i][j].id == "RK") {
						nds.addAll(parent.get_array()[i][j].u_move(i, j,parent, 1));
			}
			}}
			}
		
		for (int d = 2; d<= dep; d=d+2){
		nodes.clear();

		for (int n =0; n< nds.size();n++) {
			parent = nds.get(n);
			for (int ii = 0; ii < 8; ii++) {
				for (int jj = 0; jj < 8; jj++) {
		 			if (parent.get_array()[ii][jj].id == "BP" || parent.get_array()[ii][jj].id == "BK") {
		 				nodes.addAll(parent.get_array()[ii][jj].c_move(ii, jj,nds.get(n),d));
		}}}}

		nds.clear();
		if (dep ==d) {
			break;
		}

		for (int n =0; n < nodes.size(); n++) {
			parent = nodes.get(n);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (parent.get_array()[i][j].id == "RP" || parent.get_array()[i][j].id == "RK") {
						nds.addAll(parent.get_array()[i][j].u_move(i, j,parent, d+1));
			}
			}}

			}

		}
		
		System.out.println("Score : " + root.r_minimax(Integer.MIN_VALUE,Integer.MAX_VALUE,0) );
		p_list = root.get_array();
		return p_list;
	}
	
	public static  Pawn[][] copy_array(Pawn[][] p) {
		Pawn[][] copy_list = new Pawn[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copy_list[i][j] = new Pawn(p[i][j]);
			}
		}
		return copy_list;
	}

	
	public static int input(Pawn[][] p_list) {
		String n;
		char[] chars;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		
		
		// convert to array index
		Scanner reader = new Scanner(System.in);
		System.out.println("enter from to ex) C3D4: ");
		n = reader.nextLine();
		chars = n.toCharArray();
		
		String[] alp = { "A", "B", "C", "D", "E", "F", "G", "H" };
		if (n.equals("quit")) {
			return 1;
		}
		try {
		for (int i = 0; i < 8; i++) {
			if (alp[i].equals(Character.toString(chars[0]))) {
				i2 = i;
			}
			if (alp[i].equals(Character.toString(chars[2]))){
				i4= i;
			}
		}

		i1 = Character.getNumericValue(chars[1]);
		i1 = 8 - i1;
		i3 = Character.getNumericValue(chars[3]);
		i3 = 8 - i3;
		p_list[i3][i4].setID(p_list[i1][i2].id);
		
		if (i1-i3 ==2 || i1-i3 == -2) {
			p_list[i1 + (i3-i1)/2][i2 + (i4-i2)/2].setID("default");
		}
		
		if (i3==0 && !p_list[i1][i2].id.equals("BK")) {
			p_list[i3][i4].setID("RK");

		}
		
		if (i3==7 && !p_list[i1][i2].id.equals("RK")) {
			p_list[i3][i4].setID("BK");

		}
		p_list[i1][i2].setID("default");

	}catch (Exception e) {
		System.out.println("Invalid movement");
		input(p_list);
	}
		
		return 0;
		}
	
	
}






