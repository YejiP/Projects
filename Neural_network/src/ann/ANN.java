package ann;

//two hidden layers model

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ANN {
	static ArrayList<perceptron> i_layer =new ArrayList<perceptron>();
	static ArrayList<Integer> answer =new ArrayList<Integer>();
	static ArrayList<perceptron> h1 = new ArrayList<perceptron>();
	static ArrayList<perceptron> h2 = new ArrayList<perceptron>();
	static ArrayList<perceptron> o_layer = new ArrayList<perceptron>();

	static ArrayList<Float> w1_mean = new ArrayList<Float>();
	static ArrayList<Float> w2_mean = new ArrayList<Float>();
	static ArrayList<Float> w3_mean = new ArrayList<Float>();

	private static int hidden1 =256;
	private static int hidden2 =256;

	private static int i_num = 784;
	private static int a_num = 10;
	static ArrayList<String[]> numb = new ArrayList<String[]>();

	public static void main(String[] args) throws IOException{
        String line = "";
        BufferedReader bufReader = null;

		try{
            File file = new File("C:\\Users\\21500\\eclipse-workspace\\ann\\src\\ann\\mnist_train.csv");
            FileReader FR = new FileReader(file);
            bufReader = new BufferedReader(FR);
            for (int a =0; a<30000; a++){
                line = bufReader.readLine();
            	String[] hi = line.split(",");
            	numb.add(hi);
            }
            bufReader.close();
        }catch (FileNotFoundException e) {
        }catch(IOException e){
            System.out.println(e);
        }
		
		// makes perceptrons, and initialize the weights. 
		init();
		for (int j=0;j<3;j++) {
		//training each examples.
			for(int i=j*10000;i<(j+1)*10000;i++) {
				/* the argument for training is 
				 "input,right answer"
				 since the string that we have now contains	the information of A, B, at front, 
				 cut this info and then pass it to the training function.*/ 	
				training(numb.get(i));
			}/*
			for(int a=0;a<i_num;a++) {
				w1_mean.add(i_layer.get(a).weights);}
			h1.get(i).weights
			h2.get(i).weights*/
			}

			try {
			      BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\21500\\eclipse-workspace\\ann\\src\\out.txt"));

			      for (int i =0;i<i_num;i++) {
			    	  out.write(i_layer.get(i).weights.toString()); 
			    	  out.newLine();
			      }
			      
			      for (int i =0;i<hidden1;i++) {
			    	  out.write(h1.get(i).weights.toString()); 
			    	  out.newLine();
			      }

			      out.close();
			      
			    } catch (IOException e) {
			        System.err.println(e); // 에러가 있다면 메시지 출력
			        System.exit(1);
			    }
			test();
			//get input from buttons, and then test with the trained model.
	}

	
	//for the initialize the model.make perceptrons for input, hidden, output layer, and set weights for each layer.
	private static void init() {
		//Input perceptrons.
		for (int i=0;i<i_num;i++) {
			i_layer.add(new perceptron());
		}
		//
		//The perceptrons in the input layer hold weights(from input to hidden layer) value. 
		for (int i =0 ; i<i_num; i++) {
			i_layer.get(i).set_weight(i_num,hidden1);
		}
		
		
		//layer2 (hidden perceptrons)
		for (int i=0 ;i<hidden1+1;i++) {
			h1.add(new perceptron());
		}
		h1.get(hidden1).set_output(1);

		for (int i =0 ; i<hidden1+1; i++) {
			h1.get(i).set_weight(hidden1 + 1, hidden2 + 1);
			}

		//layer3 (hidden perceptrons)
		for (int i=0 ;i<hidden2 + 1;i++) {
			h2.add(new perceptron());
		}
		//The perceptrons in the hiddenlayer(h1) have the weights to output layer.
		for (int i =0 ; i<hidden2 +1; i++) {
			h2.get(i).set_weight(hidden2+1,a_num);
			}		
		
		
		//output layer
		for (int i=0 ;i<a_num;i++) {
			o_layer.add(new perceptron());
		}
	}	
	
	
	public static void training(String[] numbers) {
		answer.clear();
		//input layer
		//save the input value to perceptrons in the input layer from the alphabets string
		for (int i =0;i<i_num;i++) {
			if (Integer.valueOf(numbers[i+1])>240) {
				i_layer.get(i).input_layer(1);
			}else {
				i_layer.get(i).input_layer(0);
			}
		}
		
		//save the right answer to array from the alphabets string
		for (int i =0; i<Integer.parseInt(numbers[0]); i++) {
			answer.add(0);
		}
		answer.add(1);
		for (int i =Integer.parseInt(numbers[0])+1; i<a_num ; i++) {
			answer.add(0);
		}
		
		//feed forward network
		forward();
		
		//back propagation
		change_weight();

	}

	
	//proceed forward propagation, and then shows cross entropy error
	private static void forward() {
		// Calculate values of perceptrons in the layer 2. and then save that value into each perceptrons.
		float temp =0;
		
		for (int j=0; j<hidden1  ;j++) {
			temp = 0;
			for (int i =0 ; i<i_num; i++) {
				//calculate all values for h1, and then save in temp variable,
				temp = temp + i_layer.get(i).get_weight(j)*i_layer.get(i).get_output();
		}
			//set each perceptron's value (= temp value)
			//it is just total value, not logistic(total value). logistic function is conducted when .get_output() function is called.
			h1.get(j).set_output(temp);
		}
		
		// 
		h1.get(hidden1).set_output(1);
		
		for (int j=0; j<hidden2 ;j++) {
			temp = 0;
			for (int i =0 ; i<hidden1+1; i++) {
				//calculate all values for h1, and then save in temp variable,
				temp = temp + h1.get(i).get_weight(j)*h1.get(i).get_output();
		}
			h2.get(j).set_output(temp);
		}
		
		h2.get(hidden2).set_output(1);

		
		for (int k=0; k<a_num ;k++) {
			temp = 0;
			for (int i =0 ; i<hidden2 +1; i++) {
				//calculate all values for h1, and then save in temp variable,
				temp = temp + h2.get(i).get_weight(k)*h2.get(i).get_output();
		}
			//set each perceptron's value (= temp value)
			//it is just total value, not logistic(total value). logistic function is conducted when .get_output() function is called.
			o_layer.get(k).set_output(temp);
		}
	

		//calculate errors using softmax and cross entropy error.
		softmax_and_error();
	}
	
	//The explanation is in the "softmax and cross entropy error" text file.
	private static void softmax_and_error() {
		//float soft = float.MIN_VALUE; --> wrong, because this returns the smallest positive value..!!! 
		// to get minimum 'negative' float value
		float output_max = -Float.MAX_VALUE;
		
		//find the highest number.
		for(int s=0;s<a_num;s++) {
			if (o_layer.get(s).get_total() > output_max) {
				output_max = o_layer.get(s).get_total();
			}
		}	

		//subtract,(to prevent from having too big number) and set e value for each perceptrons in the output layer.
		for(int s=0;s<a_num;s++) {
			o_layer.get(s).set_e((float) Math.exp(o_layer.get(s).get_total() - output_max));
		}
		
		// for the denominator
		float soft_total =0;
		for(int s=0;s<a_num;s++) {
			soft_total = soft_total + o_layer.get(s).get_e();
		}
		
		//set probability for each class.
		for(int s=0;s<a_num;s++) {
			o_layer.get(s).set_prob(o_layer.get(s).get_e()/soft_total);
		}
		
		//evaluate, cost function = (the right answer)* -ln(probability + small number) 
		for (int i=0;i< o_layer.size();i++) {
			//0 무시
			o_layer.get(i).set_error(o_layer.get(i).get_prob()-answer.get(i));
		}
		
		float c=0;
	
		for (int i=0;i< hidden2;i++) {
			c=0;
			for (int j=0;j<a_num;j++) {
				c += o_layer.get(j).get_error()*h2.get(i).get_weight(j);
				}
			h2.get(i).set_error(c);
			}
		
		
		for (int i=0;i<hidden1  ;i++) {
			c=0;
			for (int j=0;j<hidden2;j++) {
				c += h2.get(j).get_error()*h1.get(i).get_weight(j);
				}
			h1.get(i).set_error(c);
		}
	}
	
	//change the weight based on the error and derivative.
	private static void change_weight() {
		ArrayList<Float> change = new ArrayList<Float>();
		
		for(int i =0; i<hidden2+1;i++) {
			change.clear();
			for (int j=0;j<a_num;j++){
				//*dif 곱해줌..
				change.add((float) (h2.get(i).get_weight(j)-0.01*(o_layer.get(j).get_error()*h2.get(i).dif())));
			}
			h2.get(i).change_weight(change);
		}
		
		
		
	
		for(int i=0; i <hidden1;i++) {
			change.clear();
			for (int j=0;j<hidden2+1;j++) {
				change.add((float) (h1.get(i).get_weight(j)-0.01*h2.get(j).get_error()*h1.get(i).dif()));
			}
			h1.get(i).change_weight(change);
		}
		
		for(int i=0; i <i_num;i++) {
			change.clear();
			for (int j=0;j<hidden1;j++) {
				change.add((float) (i_layer.get(i).get_weight(j)-0.01*h1.get(j).get_error()*i_layer.get(i).get_output()));
			}
			i_layer.get(i).change_weight(change);
		}
		
		
		forward();
	}
	
	 // getting input from the buttons, and then change it to "0101.." string information

	//test
	private static void test() {
        ArrayList<String[]> n = new ArrayList<String[]>();

		//get a input from argument. The input is a string which consists of 35 characters.
		try{

        File file = new File("C:\\Users\\21500\\eclipse-workspace\\ann\\src\\ann\\mnist_test.csv");
        FileReader FR = new FileReader(file);
        BufferedReader bufReader = new BufferedReader(FR);
        String line;
        for (int a =0; a<10000; a++){
            line = bufReader.readLine();
        	String[] h = line.split(",");
        	n.add(h);
        }
        bufReader.close();
    }catch (FileNotFoundException e) {
    }catch(IOException e){
        System.out.println(e);
    }
	float correct = 0;
	for ( int j=0 ; j < n.size();j++) {
			
		for (int i =0;i<i_num;i++) {
			
			if (Integer.valueOf(n.get(j)[i+1])>240) {
				i_layer.get(i).input_layer(1);
			}else {
				i_layer.get(i).input_layer(0);
			}			}
			forward();

			float w = 0;
			int index=0;
			// find out the class which has the highest probability. 
			for (int j1 =0 ; j1<a_num;j1++) {
				if (w<o_layer.get(j1).get_prob()) {
					w = o_layer.get(j1).get_prob();
					index= j1;
				}
			}
			
			//result..
			//String result = "Estimation: " + index+ " probability " + output.get(index).get_prob() + "correct answer: "+ n.get(j)[0];
			//System.out.println(result);
			
			if (index == Integer.valueOf(n.get(j)[0])) {
				correct++;
			}
			
			
		}
	System.out.println((float)(correct/n.size()));
	
	}
	

}
