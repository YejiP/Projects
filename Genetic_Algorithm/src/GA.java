import java.util.Random;

public class GA {
	
	static Random rand = new Random();
	static data Data = new data();
	static int population =1000;
	
	//course array
	static Course[] c_array = Data.courses;
	
	static Population Pop; 
	
	static Class[][] popul= new Class[population][c_array.length];

	public static void main(String[] args) {
		//initialize the first population. (1000 individuals)
		init();
		//indicator for the answer, if it is 1 then answer exist, so exit the while loop
		int sign=0;
		int generation =0;
		
		while (sign==0) {
			//select function will select 2 individuals from population, and then cross over those two, --> 2 off springs.
			//population is fixed number in this algorithm, so repeat (population/2) times. 
		for (int a=0; a < (population/2); a++) {
			select(a);
			}
		//to avoid stuck in the local optima. 
		mutation();
		generation ++;
		
		System.out.print("generation : "+ generation);
		
		// if it the fitness is 1, then it will return 1.
		sign = Pop.calculate_fitness();

		}
		represent(sign);
	}

	//make initial individual and initial population
	public static void init() {
		//making individual. the minimum unit is Class class.
		for (int j=0;j<population;j++) {
				for (int i =0;i< c_array.length;i++) {
					popul[j][i] = new Class();
			}
		}
		Pop = new Population(population, popul ,c_array);
		System.out.print("generation : "+ 0);
		Pop.calculate_fitness();
	}

	//select two individual( by tournament), and crossover.
	public static void select(int repeat) {
		crossover(Pop.select(),Pop.select(),repeat);
	}

	//choose random crossover point, and then exchange the Class info.
	public static void crossover(Class[] parent1, Class[] parent2, int repeat) {
		int cross_point=(int)(Math.random()*c_array.length);
		
		for (int i=0; i<cross_point ; i++) {
			popul[repeat][i] = parent1[i];
			
			popul[repeat+1][i] = parent2[i];
			
		}
		
		//add it to population.
		for (int i=cross_point; i<c_array.length ; i++) {
			popul[repeat][i]= parent2[i];
			popul[repeat+1][i] = parent1[i];
		}
		
	}

	//exchange random individual's Class to new Class 
	public static void mutation() {
		for (int i =0; i <100; i++){
			int ran = rand.nextInt(population);
			int s_ran = rand.nextInt(c_array.length);
			popul[ran][s_ran] = new Class();
		}
	}
	
	//represent the answer as a time schedule format on console.
	private static void represent(int j) {
		int t_blocks = Data.num_time() +1;
		int r_blocks = Data.num_room()+1;
		String[][] ind = new String[t_blocks][r_blocks];
		//initialized
		for (int a=0;a<t_blocks;a++) {
			for (int b=0;b<r_blocks;b++) {
				ind[a][b] = " ";
			}
		}
		
		for (int i=1; i < r_blocks; i++) {
			ind[0][i] =("Room" + i + "("+Data.rooms[i-1].room_capacity+")" + "    ");
		}
		
		int in=0;
		for (String i : Data.time){
			in++;
			ind[in][0] = i+"      ";;
		}

		System.out.println();
		String t ="";
		for (int z=0;z<Data.courses.length;z++) {
			for (int ti =1;ti <Data.mon()+1;ti++) {
				t= "MF" + ti;
				if  (t.equals(popul[j][z].get_time())) {
					ind[ti][popul[j][z].get_rooms().room_num] =c_array[z].toString();
				}
				}

			int l= Data.num_time() - Data.mon();
			for (int ti =1;ti < l+1;ti++) {
				t = "TTH" + ti;
				if  (t.equals(popul[j][z].get_time())) {
					ind[ti+Data.mon()][popul[j][z].get_rooms().room_num] =c_array[z].toString();
				}
	
			}
	}
		
		
		for(int i =0; i<t_blocks;i++) {
			for (int q =0; q<r_blocks;q++) {
				System.out.print(String.format("%35s", ind[i][q]));
							}
		System.out.println();	
		System.out.println();	

		}
		
		}
}

