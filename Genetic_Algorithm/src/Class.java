
//Class info, 
public class Class {
	room Room;
	String Time;
	
	//random time and room are selected when this class is created.
	public Class() {
		set_rooms();
		set_time();
	}

	
	data Data = new data();
	
	public void set_rooms() {
		Room = Data.get_rooms();
	}

	public void set_time() {
		Time = Data.get_time();
	}
	
	public room get_rooms() {
		return Room;
	}
	public int get_capacity() {
		return Room.get_capacity();
	}
	
	public String get_time() {
		return Time;
	}
	
	public String room_and_time() {
		return ("Room: "+Room.room_num+ " Capacity: "+ get_capacity() + " class time :" +Time);
	}

}
