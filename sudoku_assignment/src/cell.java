import java.util.ArrayList;

public class cell {
	
	int value = 0;
	
	
	//initial array
	Boolean[] p= {true,true,true,true,true,true,true,true,true}; 

	public cell() {
	}
	//for copy array, make copy constructor.
	public cell(cell a) {
		Boolean[] h = new Boolean[9];
		for (int i =0;i<9;i++) {
			h[i] = a.p[i];
		}
		
		this.value = a.value;
		this.p = h;
	}
	
	
	//it will turn true to false except arg value.
	public void set_value(int arg) {
		value = arg;
		
		for (int i = 0; i<9;i++) {
			if (i != value-1 && p[i]){
				p[i]=false;
				
			}
		}
	}
	
	// it returns the index of true value in array.
	public ArrayList<Integer> get_ind() {
		ArrayList<Integer> c_ind = new ArrayList<Integer>(); 
		for (int i=0;i<9;i++) {
			if (p[i]) {
				c_ind.add(i);
			}
		}
		return c_ind;
	}
	
	public int get_value() {
		return value;
	}
	
	//change true to false
	public int change(int c) {
		int check =0;
		if (p[c-1] ) {
			check = 1;
			p[c-1] = false;
			}
		return check;
	}
	
	//check how many elements are true
	public int n() {
		int n=0;
		for (boolean ele:p) {
			if (ele) {
				n++;
			}
		}
		return n;
	}
	/* for the lab..
	public void get_array() {
		System.out.print("{");
		for (int i = 0; i<9;i++) {
			if (p[i]==true) {
				System.out.print(i+1 +",");
			}
		}
		System.out.print("}");
	}
	*/
}

