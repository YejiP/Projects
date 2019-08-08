
public class Course {
	//COURSE INFO
	String course_name;
	int n_students;
	String prof;
	String s_group;
	public Course(String a, int b, String c, String d) {
		this.course_name =a;
		this.n_students=b;
		this.prof =c;
		this.s_group = d;
	}
	
	public String toString() {
		return (course_name +"," +n_students+","+prof +"("+ s_group +")");
	}

}
