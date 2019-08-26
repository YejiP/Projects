import java.util.Scanner;

public class sdk {
	
	static cell[][] s_array = new cell[9][9];
	
	public static void main(String[] args) {
		//make new array
		//I used object which contains boolean array.
		for (int i = 0 ; i <9;i ++) {
			for (int j  = 0 ; j<9;j++) {
				s_array[i][j]=new cell();
			}
		}
		
		Scanner reader = new Scanner(System.in);
		String st = reader.next();
		//it will contain the input information
		char[] s = st.toCharArray();
		
		//check the input and then remove not possible element in domain
		initialize(s);
		check_iter(s_array);
		
		back_track(0,s_array);
		
		show(s_array);
	}	


	/*BACK TRACKING, RECURSIVE FUNCTION
	 * 
	this function checks possible value in domains. 
	copy exact same array(ary), which name is "temp".
	ary and temp has all cell's information, which means it contains 81 cell objects. 
	
	set cell's value at temp array (which is the copy of ary, do not change anything in 'ary' array)
	
	ex) {2,3,4,7}
	it will set "2" as a cell value at first. then the domain of this cell will change into {2}.	
	now, delete 2 at some cells (same row, same column and same block)
	after deleting it, check the domain of those cells(same row, same column and same block).
	and then it will check if any constraints are violated. (no element in domain --> violated)
	
	if it had some violation ,
	then this time, the cell value will set as '3' (Second element from {2,3,4,7})
	if the value for this cell is not violated, go to next cell, by "back_track(current cell's index +1, temp)" 
	
	if all value were tested and then every value were violated then return -1.
	if back tracking is wrong, it will try another value in the cell's domain.
	but if all value were tested and then every value were violated then it will return -1.
	
	before change another value in the same cell, 
	copy array from parameter into temp array; reset temp array.

	if current cell's index +1 ==81, then set s_array as temp array ( it means this could go thru till the end without any violations.)
	 */
	
	private static int back_track(int ind, cell[][] ary) {
		int a = ind/9;
		int b = ind%9;
		int ic =0;
		//copy ary into temp. 
		cell[][] temp = copy_array(ary);
		
		// if index == 81 --> done.! ( get to the end without any violations!)
		if (ind == 81) {
			s_array = ary;
			return 1;
		}
		
		for (int e:temp[a][b].get_ind()) {
			ic=ic+1;
			if (temp[a][b].n()==1) {
				temp[a][b].set_value(e+1);
				check_iter(temp);
				back_track(ind+1,temp);
			}else {
			temp[a][b].set_value(e+1);
			//check iter
			if (check_iter(temp) ==-1) {
				//check every possibilties, but everything is wrong. ic== how many time you check? , ary[a][b].n() == every possibilities.
				// if those two has same number, which means it checked everything and then everything was violated.
				if (ic == ary[a][b].n()) {
					return -1;
				}
			}else {
				//back track. if it does not return -1, then good to go. recursive..
				if ( back_track(ind+1,temp)!= -1) {
				}else {
					// violated.. so return -1
					if(ic == ary[a][b].n()) {
						return -1;}
				}
			}
		}
			//current value did not work out, so try other value. to do that, reset array..
			temp= copy_array(ary);

			}
		return 0;
	}

	// according to the first input, delete unavailable value in domain. (check row, column, block)
	private static void initialize(char[] s) {
		for (int i= 0; i <81;i++) {
			// a : 0~8 , row number	
			int a=i/9;

			// b : 0~8 , column number
			int b = i%9;
			
			//if it is not 0, set value .
			//set_value method let other elements be false.
			// ex) if it is 3,(it will change to index "2" , not "3") then 
			// {true,true,true,true,true,true,true,true,true} to			
			//{false,false,true,false,false,false,false,false,false}
			if (s[i] != "0".charAt(0)){

				s_array[a][b].set_value(Character.getNumericValue(s[i]));
				
				//row
				//check other cells in same row, and then remove value from domain
				for (int k =0;k <9;k++) {
					if ( k!= b) {
						//if it is not s_array[a][b] cell, remove input value from domain --> change it to false
					s_array[a][k].change(Character.getNumericValue(s[i]));
				}}
				
				//column
				//check other cells in same column, and then remove value from domain
				for (int k =0;k <9;k++) {
					if ( k!= a) {
						//if it is not s_array[a][b] cell, remove input value from domain --> change it to false
					s_array[k][b].change(Character.getNumericValue(s[i]));
				}}
				
				//block check, it will go to the corresponding block, and then remove input value for other elements of domain
				block(a,b,Character.getNumericValue(s[i]),s_array);		
			}
			}		

	}

	//find out where this element is located. 
	/*
	 index*
	0/1/2/3/4/5/6/7/8
	9/10/11/ ....../17
	18 ................
	..............
	..............
	...
	...
	...
	..................80/
	
	num --> (num/9,num%9) == (row number, column number)
	
	(0,0)/(0,1)/(0,2)/......(0,8)
	(1,0)/(1,1)/(1,2)/ ....../(1,8)
	18 ................
	.....(omit)
	...
	..........................(8,8)
	
	divide both row number and column number by 3. then,
	ex)	
	table a) row
	0 0 0 0 0 0 0 0 0
	0 0 0 0 0 0 0 0 0
	0 0 0 0 0 0 0 0 0
	1 1 1 1 1 1 1 1 1
	1 1 1 1 1 1 1 1 1
	1 1 1 1 1 1 1 1 1
	2 2 2 2 2 2 2 2 2
	2 2 2 2 2 2 2 2 2
	2 2 2 2 2 2 2 2 2	
	table b) column.
	0 0 0 1.omit...2
	0 0 0 1...omit.2
	0 0 0 1.omit...2
	0 0 0 1..omit..2
	0 0 0 1..omit..2
	0 0 0 1..omit..2
	0 0 0 1..omit..2
	0 0 0 1..omit..2
	 by this information,  if input is (1,3) --> (1/3,3/3) = (0,1) --> 
	 which means that we can delete input element from other's domain which has 0 in table a, and 1 in table b.	
	
 */
	public static int block(int a, int b, int val, cell[][] ar) {
		// by divided by 3, it will give information of where this cell belong,
		int c = a/3;
		int d= b/3;
		
		// indicator of change. if something changed, then variable 'check' will has positive value.
		int check =0;
		
		switch(c) {
		case 0:
			if (d==0) {
				for (int i=0; i<3;i++) {
					for (int j =0;j<3;j++) {
						//some code is for assignment.., the main point here for lab is that ar[i][j].change(val)
						//so, if it is not ar[i][j], it will turn that value true to false
						if (i!=a && j!=b) {
							// ar[i][j].change(val) returns 1
							check = check+ar[i][j].change(val);
							if (ar[i][j].n()==0) {
								return -1;
							}
							}
					}
				}
			}else {
				if (d==1) {
					for (int i=0; i<3;i++) {
						for (int j =3;j<6;j++) {
							if (i!=a && j!=b) {
								check = check+ar[i][j].change(val);
								if (ar[i][j].n()==0) {
									return -1;
								}

								}
						}
					}}
				else {
					for (int i=0; i<3;i++) {
						for (int j =6;j<9;j++) {
							if (i!=a && j!=b) {
								check = check+ar[i][j].change(val);
								if (ar[i][j].n()==0) {
									return -1;
								}

								}
						}
					}	
				}
			}
			break;
		case 1:
			if (d==0) {
				for (int i=3; i<6;i++) {
					for (int j =0;j<3;j++) {
						if (i!=a && j!=b) {
							check = check+ar[i][j].change(val);
							if (ar[i][j].n()==0) {
								return -1;
							}
						}
				}	
						}}else {
							if (d==1) {
								for (int i=3; i<6;i++) {
									for (int j =3;j<6;j++) {
										if (i!=a && j!=b) {
											check = check+ar[i][j].change(val);
											if (ar[i][j].n()==0) {
												return -1;
											}
										}
								}
								
							}}else {
								
								for (int i=3; i<6;i++) {
									for (int j =6;j<9;j++) {
										if (i!=a && j!=b) {
											check = check+ar[i][j].change(val);
											if (ar[i][j].n()==0) {
												return -1;
											}
											}
								}
							}
						}}
			break;
		
		case 2:
			if (d==0) {
				for (int i=6; i<9;i++) {
					for (int j =0;j<3;j++) {
						if (i!=a && j!=b) {
							check = check+ar[i][j].change(val);
							if (ar[i][j].n()==0) {
								return -1;
							}	
						}
				}}	
			}else {
				if (d==1) {
					for (int i=6; i<9;i++) {
						for (int j =3;j<6;j++) {
							if (i!=a && j!=b) {
								check = check+ar[i][j].change(val);
								if (ar[i][j].n()==0) {
									return -1;
								}
						}
					}}
					
				}else {
					
					for (int i=6; i<9;i++) {
						for (int j =6;j<9;j++) {
							if (i!=a && j!=b) {
								check = check+ar[i][j].change(val);
								if (ar[i][j].n()==0) {
									return -1;
								}
							}}	
						}
					}
				}
			break;
	
		}
		return check;
		}	


	//-1 : violated, 0 --> no change >0 --> changed
	private static int iteration(cell[][] ary) {
		//variable 'change' will check if deletion occurs in any domain.
		// if it is postive then it means something has been changed, if it is negative, it means nothing changed.
		int change = 0;
		for (int i =0; i<81;i++) {
			int a = i/9;
			int b = i%9;
			// if cell has only 1 element
			if (ary[a][b].n()==1) {
				int v=0;
				// get the value.
				//k is index, the value is k+1. (index +1 represents value)
				for (int k =0; k <9;k++) {
					if (ary[a][b].p[k]) {
						v = k+1;
					}
					
				}
				
				
				// deleting value from cells which are in the same column, and if some cell has no element, then return -1
				//if something changed, add +1 to change variable ( change.(v) return 1)
				for (int k =0;k <9;k++) {
					if ( k!= b) {
					change = change + ary[a][k].change(v);
					// if this cell has no element in domain, then return -1 == current input value cannot be the answer.
					if (ary[a][k].n()==0) {
						return -1;
					}
				}}
				
				// deleting value from cells which are in the same row, and if some cell has no element, then return -1
				for (int k =0;k <9;k++) {
					if ( k!= a) {
						change = change + ary[k][b].change(v);
						if (ary[k][b].n()==0) {
							return -1; 
						}
				}}
				
				
				// deleting value from cells which are in the same block, and if some cell has no element, then return -1
				if (block(a,b,v,ary)==-1) {
					return -1;
				}
				
				change = change + block(a,b,v,ary);		
			}
			if(ary[a][b].n()==0) {
				return -1;
			}
			}
		// if change is bigger than 0, something has been changed, if it is 0, then nothing changed == so can finish iteration..
		return change;
		}


	//detect change in domain, if something changed then conduct "iteration" method again.
	//if it is -1, then it means that no change occurred. so end this method.
	//if it something has been changed, then run 'iteration' method again, till no change shows up.
	private static int check_iter(cell[][] ary) {
		int change =1;
		while (change > 0){
			change=0;
			if (iteration(ary)==-1) {
				return -1;
			}
			change = iteration(ary);
		}
		return change;
	}
	
	//copy array .. 
	private static cell[][] copy_array(cell[][] ar) {
		cell[][] ary= new cell[9][9];
		
		for (int i =0;i<81; i++) {
			int a = i/9;
			int b = i%9;
			ary[a][b] =new cell(ar[a][b]);		
			}
		return ary;
	}

	//show the board
	private static void show(cell[][] ary) {
		for (int l= 0; l <81;l++) {
			int q = l/9;
			int w = l%9;	
			if (w==0) {
			System.out.println();}
			if (q%3==0 && w==0 && q!=0) {
				System.out.println(" -----------");
			} 
			if (w%3==0) {
				System.out.print("|");
			}

	
			System.out.print( ary[q][w].get_value());
			if (w%8 ==0) {
				if (w!=0) {
				System.out.print("|");
			}}
	}}


	
}
