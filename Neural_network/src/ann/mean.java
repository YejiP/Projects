package ann;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class mean {
	static ArrayList<String[]> numb = new ArrayList<String[]>();
	static ArrayList<String[]> numb2 = new ArrayList<String[]>();
	static ArrayList<String[]> numb3 = new ArrayList<String[]>();

	static ArrayList<Float[]> means = new ArrayList<Float[]>();
	
	public static void main(String[] args) throws IOException{
		init();
		means();

		try {
		      BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\21500\\eclipse-workspace\\ann\\src\\ensemble.txt"));
		      //bias추가된거 256까지한다.
		      for (int i =0;i<784;i++) {
		    	  for(int j=0;j<256;j++) {
		    		  out.write(String.valueOf(means.get(i)[j]+", "));
		    	  }
		    	  out.write(String.valueOf(means.get(i)[numb.get(0).length-1]));
		 
		    	  out.newLine();
		      }
		      for (int i =784;i<1040;i++) {
		    	  for(int j=0;j<9;j++) {
		    		  out.write(String.valueOf(means.get(i)[j]+", "));
		    	  }
		    	  out.write(String.valueOf(means.get(i)[9]));
		 
		    	  out.newLine();
		      }  
		      
		      out.close();
		      
		    } catch (IOException e) {
		        System.err.println(e); // 에러가 있다면 메시지 출력
		        System.exit(1);
		    }
	}
	//오 얘는 다르다.! 
	private static void means() {
		for (int i= 0;i<784;i++) {
			Float[] ary = new Float[numb.get(1).length];
			for (int j=0;j<256;j++) {
				ary[j]= (Float.parseFloat(numb2.get(i)[j]) + Float.parseFloat(numb.get(i)[j])+Float.parseFloat(numb3.get(i)[j]) )/3;
			}
			means.add(ary);
		}
		//bias
		for (int i= 784;i<1040;i++) {
			Float[] ary = new Float[numb.get(1).length];
			for (int j=0;j<10;j++) {
				ary[j]= (Float.parseFloat(numb2.get(i)[j]) + Float.parseFloat(numb.get(i)[j])+Float.parseFloat(numb3.get(i)[j]))/3;
			}
			means.add(ary);
		}
		
		
	}	
	
	
	/*
	private static void means() {
		Float[] ary = new Float[numb.get(1).length];
		for (int i= 0;i<numb.size();i++) {
			for (int j=0;j<numb.get(i).length;j++) {
				ary[j]= (Float.parseFloat(numb2.get(i)[j]) + Float.parseFloat(numb.get(i)[j]))/2;
			}
			//흠..이거 값만 복사 됐을지?? 아니면 숫자 다 똑같을지// --> 이러면 다 똑같다.
			means.add(ary);
		}
	}*/
	
	private static void init() {
        String line = "";
        BufferedReader bufReader = null;

		try{
            File file = new File("C:\\Users\\21500\\eclipse-workspace\\ann\\src\\ann\\out_0.82.txt");
            FileReader FR = new FileReader(file);
            bufReader = new BufferedReader(FR);

            while ( (line = bufReader.readLine())!= null){
            	String[] hi = line.split(",");
            	numb.add(hi);

            }
            bufReader.close();
        }catch (FileNotFoundException e) {
        }catch(IOException e){
            System.out.println(e);
        }

		try{
        File file = new File("C:\\Users\\21500\\eclipse-workspace\\ann\\src\\ann\\out_0.87.txt");
        FileReader FR = new FileReader(file);
        bufReader = new BufferedReader(FR);
        int i=0;
        while ( (line = bufReader.readLine())!= null){
        	String[] hi = line.split(",");
        	numb2.add(hi);
        }

        bufReader.close();
    }catch (FileNotFoundException e) {
    }catch(IOException e){
        System.out.println(e);
    }

		try{
            File file = new File("C:\\Users\\21500\\eclipse-workspace\\ann\\src\\ann\\out_0.8.txt");
            FileReader FR = new FileReader(file);
            bufReader = new BufferedReader(FR);
            while ( (line = bufReader.readLine())!= null){
            	String[] hi = line.split(",");
            	numb3.add(hi);
            }
            bufReader.close();
        }catch (FileNotFoundException e) {
        }catch(IOException e){
            System.out.println(e);
        }
        
}	
}	

