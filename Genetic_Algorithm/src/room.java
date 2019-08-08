
public class room {
	int room_num;
	int room_capacity;
	
	public room(int i, int j) {
		this.room_num=i;
		this.room_capacity=j;
	}
	
	public int get_capacity() {
		return room_capacity;
	}
	public String toString() {
		return ("room# :" +room_num +" capacity: "+room_capacity);
	}
}
