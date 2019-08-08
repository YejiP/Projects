
public class Population {
	int num_pop;
	Class[][] popul;
	double[] fitness;
	
	//course array
	Course[] c_array;

	public Population(int population, Class[][] classes, Course[] c_array) {
		this.num_pop = population;
		this.popul = classes;
		fitness = new double[num_pop];	
		this.c_array = c_array; 
		}

	
	//fitness = 1/(1+violated constraints)
	/*
	 * 1. room capacity > students who are taking that course
	 * 2. one room can be only used once at the same time
	 * 3. professor cannot have lecture at the same time
	 * 4. same student group cannot have two lecture at the same time.
	 * */
	
	public int calculate_fitness() {
		for (int j=0;j<num_pop;j++) {
			int cs =0;
			for (int  i =0;i< c_array.length;i++) {
				int sn=  c_array[i].n_students;
				int capa = popul[j][i].get_capacity();
				if ( sn > capa) {
					cs ++;
				}
				cs += constrain(j);
				
			}
			double denom = 1+cs;
			fitness[j]= (1/denom);
			if (fitness[j]==1) {
				System.out.println("fitness: "+ denom);
				System.out.println("found");
				return j;
			}
			}
		double total=0;
		for (int i=0; i<num_pop ; i++){
			total += fitness[i];
		}
		System.out.println("  , " + total);
		return 0;
		}
	
	/*
	 * 
	 * 2. one room can be only used once at the same time
	 * 3. professor cannot have lecture at the same time
	 * 4. same student group cannot have two lecture at the same time.
	 *  check those 3 constraints .
	 *  
	 * */
	
	private int constrain(int j) {
		int c =0;
		for (int  i =0;i< c_array.length;i++) {
			for (int k=i+1; k<c_array.length;k++) {
				if (popul[j][i].get_time().equals(popul[j][k].get_time())) {
					if(popul[j][i].get_rooms().toString().equals(popul[j][k].get_rooms().toString())) {
						c++;
					}
					if (c_array[i].prof.equals(c_array[k].prof)) {
						c++;
					}
					if (c_array[i].s_group.equals(c_array[k].s_group)) {
						c++;
					}
				}
			}
		}
		return c;
	}
	
	//get random 10 individuals from population and then pick the best one.
	public Class[] select(){
		double best=0;
		int ind = 0;
		
		for (int i=0; i<100;i++) {
			int rand= (int)(fitness.length*Math.random());
			if (fitness[rand]>best) {
				best= fitness[rand];
				ind = rand;
			}
					}
			return popul[ind];
	}
	
	
	//unused, tournament works better for my problem.
	public Class[] p_select(){
		double sum =0;
	
		double[] perc = new double[fitness.length];
	
		for (int i=0;i< fitness.length;i++) {
			sum += fitness[i];
				}
	
		perc[0]= (fitness[0]/sum);
		for (int j=0; j<fitness.length-1; j++) {
			perc[j+1] = perc[j] + (fitness[j+1]/sum); 
	}
		int selected=0;

	double rand= Math.random();
	
	for(int i=0; i<perc.length-1;i++) {
		if(perc[i] < rand  && rand <perc[i+1]){
			selected = i;

			System.out.println("rand:" +rand);
			System.out.println("Selected: " + perc[i]);
			System.out.println("Selected: " + perc[i+1]);
			System.out.println("Selected: " + fitness[i]);

			break;
			
		}
	}
	//System.out.println(fitness[selected]);
		return popul[selected];
		
	}
	


	
}
