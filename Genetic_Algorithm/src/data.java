
//info of class room, times, and courses
public class data {
	room[] rooms = {new room(1,25),new room(2,30),new room(3,40),new room(4,45)};
	String[] time = {"MF1","MF2","MF3","MF4","MF5","TTH1","TTH2","TTH3","TTH4","TTH5"};
	
	//room (3) *time zone (4) =18, course = 10
	
	//add mon first,
	Course[] courses = {new Course("CMPT302",15,"Prof.Feaver","2"), new Course("CMPT305A",35,"Prof.Janzen","2"),
			new Course("CMPT340",35,"Prof.Tappenden","3"), new Course("CMPT360",40,"Prof.Feaver","3"),
			new Course("CMPT375",35,"Prof.Janzen","3"), new Course("CMPT440",10,"Prof.Tappenden","4_a"),
			new Course("CMPT470",25,"Prof.Janzen","4_b"), new Course("CMPT480A",15,"Prof.Feaver","4_c"),
			new Course("CMPT480B",15,"Prof.Tappenden","4_a"), new Course("CMPT480C",35,"Prof.Janzen","4_b"),
			new Course("CMPT481A",35,"Prof.Feaver","4_c"), new Course("CMPT481B",40,"Prof.Tappenden","4_b"),
			new Course("CMPT481C",35,"Prof.Janzen","4_b"), new Course("CMPT496",10,"Prof.Tba","4_c"),
			new Course("CMPT496A",25,"Prof.Tappenden","4_a"),new Course("CMPT496B",15,"Prof.Janzen","4_b"),
			new Course("BIOL204",15,"Prof.Feaver","2"), new Course("BIOL207",35,"Prof.Janzen","2"),
			new Course("BIOL207",35,"Prof.Prior","b_2"), new Course("BIOL211",15,"Prof.Peters","b_2"),
			new Course("BIOL320",35,"Prof.Prior","b_2"),new Course("BIOL364",35,"Prof.Looy","b_3"), 
			new Course("BIOL397",15,"Prof.Visscher","b_3"), new Course("BIOL423",35,"Prof.Wolmarans","b_4"),
			new Course("BIOL436",35,"Prof.Peters","b_4") 
	};
	
	public int num_room() {
		int n = rooms.length;
		return n;
	}
	public int mon() {
		int n= 0;

		for (int i=0;i<num_time();i++) {
			if(time[i].contains("M")) {
				n++;
			}
		}
		return n;
	}	
	public int num_time() {
		int n= time.length;
		return n;
	}	
	public room get_rooms(){
		int ind = (int)(rooms.length*Math.random());
		return rooms[ind];
		}
	
	public String get_time() {
		int ind = (int)(time.length*Math.random());
		return time[ind];
	}

}
