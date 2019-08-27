package ann;

import java.util.ArrayList;

public class perceptron {
	//Each perceptron holds one output value, input value and weights for next layer.
	//containing inputs from previous layer/ input info
	ArrayList<Float> inputs = new ArrayList<Float>();	
	//Containing weights for current perceptron to next perceptron
	ArrayList<Float> weights = new ArrayList<Float>();
	
	float total=0;
	float f= 0;
	float input = 0;
	boolean is_input = false;
	float error =0;
	
	//variables for the output layer
	float e_value =0;
	float prob =0;

	private float output;
	
	//this method is only for perceptrons in input layer.
	public void input_layer(float a) {
		total = a;
		output = a;
	}
	
	//Set perceptron's value.
	public void set_output(float a) {
		total =a;
		relu();
		}
	
	public void relu() {
		if (total >0) {
			output = total;
		}else {
			output = 0;
		}
	}
	public int dif() {
		if (total >0) {
			return 1;
		}else {
			return 0;
		}
	}
	//weights for next layer. If the number of perceptrons in the next layer is 50, then it holds 50 weights values.
	public void set_weight(int a,int b) {
	    for (int i = 0; i<b; i++) {
	         if(Math.random()>0.5) {
	          weights.add((float) ((Math.random()*(b-a) +a)/Math.sqrt(a/2)));
	         }else {
		          weights.add((float) (-(Math.random()*(b-a) +a)/Math.sqrt(a/2)));
	         }
	}
	      }
	
	//reset the weights.
	public void change_weight(ArrayList<Float> n) {
		weights.clear();
		for (int i =0;i<n.size();i++) {
			weights.add(n.get(i));
		}
	}


	//Euler's number of total value. ( to make softmax)
	public void set_e(float s) {
		e_value = s;
	}
	public float get_e() {
		return e_value;
	}

	// if it is from the input layer, just return total.
	//if it is from hidden layer, return relu(total)
	public float get_output() {
		return output;		
	}
	
	public float get_total() {
		return total;
	}

	
	public float get_weight(int i) {
		return weights.get(i);
	}
	
	public void set_error(float e){
		error = e;
	}

	
	public float get_error(){
		return error;
	}
	
	//set probability
	public void set_prob(float s) {
		prob =s;
	}
	public float get_prob() {
		return prob;
	}



}
