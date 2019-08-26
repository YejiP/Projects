package checker_biii;

import java.util.ArrayList;
import java.util.Collections;

public class nd {
	//default array
	Pawn[][] d_array;
	int child_num =0 ;
	String type ;
	nd parent;
	int depth;
	static Scoring score = new Scoring();
	ArrayList<nd> children = new ArrayList<nd>();
    
	public nd(Pawn[][] v, int depth ) {
        d_array = v;
        this.depth = depth;
    }
	
	
	public static void main(String[] args) {
	}
	

	public void set_child(nd child) {
		children.add(child);
		child.parent = this;
		child_num = child_num+1;
	}


	public Pawn[][] get_array() {
		return d_array;
	}
	
	public boolean is_leaf() {
		if (child_num ==0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	   public int minimax(int g_min , int g_max, int it) {
		      //depth==1 --> COMPUTER
		      int mini = Integer.MAX_VALUE;
		      int maxi = Integer.MIN_VALUE;
		      int index = -1;
		      Collections.shuffle(children);
		      //leaf node.		     
		      if (is_leaf()) {
		    	  //System.out.print(score.score(d_array) );
		    	  return score.score(d_array);
		      }else {
		      if (depth %2 == 1) {
		          //System.out.println("depth : " +  depth );
		          //System.out.print("user value : " );
		          //System.out.println("IT" +it);

		          if (it ==0) {
		        	  g_min = Integer.MIN_VALUE;
		          }
		          
		          for (int i =0; i<child_num;i++) {
		            int mi = children.get(i).minimax(Integer.MAX_VALUE,mini, i );

		            if (mini > mi) {
		            	mini = mi;
		            }
		            
			        //if value is smaller than g_min, stop searching
			        //because, at the next level, maximum will be picked 
		            if (mi <= g_min && it >0) {
		            	//System.out.println("STOP "+g_min);
			        	 i=child_num;
		            }
		         }
		         

		         //System.out.println("Minimum "+mini);
		  
		         return mini;
		      }
		      
		      
	          //System.out.println("depth : " +  depth );
		      //System.out.print("computer value : " );
	          //System.out.println("IT" +it);
	          
	          if (it == 0) {
	        	  g_max = Integer.MAX_VALUE;
	          }
		      for (int i =0; i<child_num;i++) {
		         int ma = children.get(i).minimax(maxi,Integer.MIN_VALUE ,i);
		         
		         if (it ==0) {
		        	 g_max = maxi;
		         }
		         
		         if (maxi < ma) {
		        	 
		            maxi = ma;
		            index = i;
		            
		         }
		           
		         
		         if (ma >= g_max && it >0) {
		        	 //System.out.println("STOP "+g_max);
		        	 i=child_num;
		            }			         
		      }
		      
		      //System.out.println("MAXIMUM "+maxi);
		      //get array if it is root node. (this array is compter's next move)
		      if (depth == 0) {
		    	  d_array = children.get(index).d_array;
		    	  return score.score(d_array); 
		      }
		      return maxi;} }

	   
	   public int r_minimax(int g_min , int g_max, int it) {
		      //depth==1 --> COMPUTER
		      int mini = Integer.MAX_VALUE;
		      int maxi = Integer.MIN_VALUE;
		      int index = -1;

		      //leaf node.		     
		      if (is_leaf()) {
		    	  //System.out.print(score.score(d_array) );
		    	  return score.score(d_array);
		      }else {
		      if (depth %2 == 1) {
		          //System.out.println("depth : " +  depth );
		          //System.out.print("op value : " );
		          //System.out.println("IT" +it);

		          if (it ==0) {
		        	  g_max = Integer.MAX_VALUE;
		          }
		          
		          for (int i =0; i<child_num;i++) {
		            int ma = children.get(i).r_minimax(maxi,Integer.MIN_VALUE, i );

		            if (maxi < ma) {
		            	maxi = ma;
		            }
		            
		            
		            if (ma >= g_max && it >0) {
		            	//System.out.println("STOP "+g_max);
			        	 i=child_num;
		            }
		         }
		         
		         //System.out.println("Maximum "+maxi);
		  
		         return maxi;
		      }
		      
	          
		      //System.out.println("depth : " +  depth );
		      //System.out.print("computer value : " );
	          //System.out.println("IT" +it);
		      
	          if (it == 0) {
	        	  g_min = Integer.MIN_VALUE;
	          }


		      for (int i =0; i<child_num;i++) {
		         int mi = children.get(i).r_minimax(Integer.MAX_VALUE,mini ,i);
		         
		         if (it ==0) {
		        	 g_min = mini;
		         }
		         
		         if (mini > mi) {
		        	 
		            mini = mi;
		            index = i;
		            
		         }
		        
		         //if value is smaller than g_min, stop searching
		        //because, at the next level, maximum will be picked 
		         if (mi <= g_min && it >0) {
		        	 //System.out.println("STOP "+g_min);
		        	 i=child_num;
		            }	

		      }
		      //System.out.println("MINI "+mini);

			      //get array if it is root node. (this array is compter's next move)
		      if (depth == 0) {
		    	  d_array = children.get(index).d_array;
		    	  return score.score(d_array); 
		      }
		      return mini;} }
}

