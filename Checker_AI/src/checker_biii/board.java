package checker_biii;

//drawing board
public class board {	
	public static void main() {
		new board();
	}
	
	public void drawing_board(Pawn[][] ps) {
		for (int i = 0; i < 8; i++) {
			System.out.println("");
			System.out.println("  -----------------------------------------");
			System.out.print(8-i + " ");
			for (int j = 0 ; j< 8; j++){
				System.out.print(" |"+ ps[i][j].getName());
		}}
		System.out.println("");
		System.out.println( " -------------------------------------------");
		System.out.println("     A    B    C    D    E    F    G     H  ");
	}
	
	public Pawn[][] initial_array(){
		Pawn[][] ps = new Pawn[8][8];
		
		for (int i = 0; i < 8; i++){
			for (int k = 0; k<8; k++){
				Pawn p = new Pawn();
				p.setID("default");
				ps[i][k] = p;
		}
		}		
		
		for (int i = 0; (i < 3) ; i=i+2){
			for (int k = 1; k<8; k= k+2){
				Pawn p = new Pawn();
				p.setID("BP");
				ps[i][k] = p;
		}
		}	
		
		for (int k = 0; k<8; k= k+2){
			Pawn p = new Pawn();
			p.setID("BP");
			ps[1][k] = p;
	}		
		
		for (int i = 5; i < 8; i= i+2){
			for (int k = 0; k<8; k= k+2){
				Pawn p = new Pawn();
				p.setID("RP");
				ps[i][k] = p;
		}
		}
		
		for (int k = 1; k<8; k= k+2){
				Pawn p = new Pawn();
				p.setID("RP");
				ps[6][k] = p;
		}
		return ps;		
	}
}
