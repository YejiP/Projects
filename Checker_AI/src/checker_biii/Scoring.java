package checker_biii;


public class Scoring {
	public static void main(String[] args) {
		new Scoring();
	}
	
	//assign values
	//pawn = 10 + location points, king = 30
	public int score(Pawn[][] p_list) {
		int total = 0;
		for (int i = 0; i<4;i++) {
			for (int j = 0; j<8; j++) {
				String type = p_list[i][j].id;
				if (type.equals("BP")) {
					 total = total +10+ 3*i;}
				if (type.equals("RP")) {
					total = total - 10-3*(7-i);}
				if (type.equals("RK")) {
					total = total - 30;}
				if (type.equals("BK")) {
					total = total +30;}
				}
			}
		
		for (int i = 7; i>3;i--) {
			for (int j = 0; j<8; j++) {
				String type = p_list[i][j].id;
				if (type.equals("RP")) {
					 total = total -10 - 3*(7-i);}
				if (type.equals("BP")) {
					total = total +10 + 3*i;}
				if (type.equals("RK")) {
					total = total - 30;}
				if (type.equals("BK")) {
					total = total +30;}
				}
			}
		
		return total;
		}
	
	//check if someone won	
	public int win(Pawn[][] p_list) {
		int win = 0;
		for (int i = 0; i<8;i++) {
			for (int j = 0; j<8; j++) {
				String type = p_list[i][j].id;
				//COMPUTER WIN
				if (type.equals("BP") || type.equals("BK") ) {
					//win ==-1 --> rp or rk exists. 
					if (win == -1) {
						return 0;
						
					}
					//if only computer's pawn exists

					win = 1;}
				
				//USER WIN
				if (type.equals("RP") || type.equals("RK")) {
					//win ==-1 --> bp or bk exists. 
					if (win == 1) {
						return 0;
						
					}
					//if only user's pawn exists
					win = -1;
				}
		
		
	}}return win;}
	}

