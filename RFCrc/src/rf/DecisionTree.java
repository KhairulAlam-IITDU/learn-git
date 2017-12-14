package rf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DecisionTree {

	public Node root;
	//double decision, probability;
	public Map<Double, Double> singleTreeClassProbability = new HashMap<>();
	public double bhtValue = 0.0;
	public double miValue = 1.0;
	public double decisionClass = -1.0;

	public DecisionTree(Node rootNode, Map<Integer, Integer> mapDataColumn) {
		this.root = makeTree(this.root, rootNode.attribute, rootNode.decision, 0, mapDataColumn);
	}

	//public void makeTree(Node rootNode, int height) //old

	int cnt = 0;

	public Node makeTree(Node currentNode, List<List<Double>> feature, List<Double> label, int height, Map<Integer, Integer> mapDataColumn) {

		if(label.size() == 0){

			return null;
		}

		EntropyCalculation e1 = new EntropyCalculation();

		Node n = e1.maxGainedElement(feature, label); //new


		if(e1.zeroEntropy){

			currentNode = new Node();

			currentNode.attribute = feature;
			currentNode.decision = label;
			currentNode.nodeValue = label.get(0);

			currentNode.classProb = evaluateClassProbability(currentNode.decision);

			return currentNode;
		}

		//Map<Double, Double> testMap = evaluateClassProbability(n.decision);

		currentNode = new Node();

		int tempCol = n.featureIndexColumn;

		currentNode.featureIndexColumn = mapDataColumn.get(tempCol);
		currentNode.featureIndexRow = n.featureIndexRow;

		currentNode.attribute = feature;
		currentNode.decision = label;

		currentNode.nodeValue = feature.get(currentNode.featureIndexRow).get(tempCol);
		currentNode.classProb = evaluateClassProbability(currentNode.decision);

		if(height > 7){
			return currentNode;
		}

		currentNode.leftChild = new Node();
		currentNode.rightChild = new Node();

		List<List<Double>> leftNodefeature = new ArrayList<>();
		List<List<Double>> rightNodefeature = new ArrayList<>();

		List<Double> leftNodeLabel = new ArrayList<>();
		List<Double> rightNodeLabel = new ArrayList<>();

		for (int i = 0; i < feature.size(); i++) {

			if(currentNode.nodeValue >= feature.get(i).get(tempCol)) {

				leftNodefeature.add(feature.get(i));
				leftNodeLabel.add(label.get(i));

			}
			else {

				rightNodefeature.add(feature.get(i));
				rightNodeLabel.add(label.get(i));
			}
		}

		if((leftNodeLabel.isEmpty() || rightNodeLabel.isEmpty()) && height == 0){
			System.out.println("Ghapla");
		}
		
		currentNode.leftChild = makeTree(currentNode.leftChild, leftNodefeature, leftNodeLabel, height+1, mapDataColumn);

		currentNode.rightChild = makeTree(currentNode.rightChild, rightNodefeature, rightNodeLabel, height+1, mapDataColumn);

		return currentNode;
	}

	public void traverse(Node current, List<Double> testSet) {

		if(current != null){

			if(current.leftChild == null && current.rightChild == null) {

				this.singleTreeClassProbability = current.classProb;

				//System.out.println();
				if(!this.singleTreeClassProbability.isEmpty()){

					double cpb = -1.00;

					for(Double key : this.singleTreeClassProbability.keySet()){

						double tmp = this.singleTreeClassProbability.get(key);

						if(tmp > cpb) {
							cpb = tmp;
							this.decisionClass = key;
						}

						//System.out.println("Key: " + key + ",\tProbability: " + tmp);
						//System.out.println("BHT: " + this.bhtValue + ",\tProbability*BHT: " + tmp*this.bhtValue);

						//System.out.println("MI: " + this.miValue);
						//this.singleTreeClassProbability.remove(key);
						//this.singleTreeClassProbability.put(key, tmp*this.bhtValue);
					}
				}
				else{

					System.out.println("Empty Class Probability");
				}

				return;

			}

			/*if(current.featureIndexColumn >= 0 && current.featureIndexRow < 0) {

			this.singleTreeClassProbability = current.classProb;

			//System.out.println();
			if(!this.singleTreeClassProbability.isEmpty()){

				double cpb = -1.00;

				for(Double key : this.singleTreeClassProbability.keySet()){

					double tmp = this.singleTreeClassProbability.get(key);

					if(tmp > cpb) {
						cpb = tmp;
						this.decisionClass = key;
					}

					//System.out.println("Key: " + key + ",\tProbability: " + tmp);
					//System.out.println("Key: " + key + ",\tProbability*MI: " + tmp*this.miValue);

					//this.singleTreeClassProbability.remove(key);
					this.singleTreeClassProbability.put(key, tmp*this.miValue);
				}
			}

			return;
		}*/

			if(current.nodeValue >= testSet.get(current.featureIndexColumn)) {

				traverse(current.leftChild, testSet);

			}
			else {

				traverse(current.rightChild, testSet);

			}


		}
		//System.out.println("Current node is null");
	}
	
	
	public void inorderTraverse(Node root){
		
		if (root == null) {
            return;
        }
        
        Stack<Node> stack = new Stack<Node>();
        Node node = root;
         
        //first node to be visited will be the left one
        while (node != null) {
            stack.push(node);
            node = node.leftChild;
        }
         
        // traverse the tree
        while (stack.size() > 0) {
           
            // visit the top node
            node = stack.pop();
            
            if(node.leftChild == null && node.rightChild == null)
            	System.out.print(node.nodeValue + ", ");
            
            if (node.rightChild != null) {
                node = node.rightChild;
                 
                // the next node to be visited is the leftmost
                while (node != null) {
                    stack.push(node);
                    node = node.leftChild;
                }
            }
        }
	}

	private  Map<Double, Double> evaluateClassProbability(List<Double> classes) {

		Map<Double, Double> clsprb = new HashMap<>();

		//System.out.println("Inside evClsProbFunc: " + subsetData.decision.size());

		int numberOfMembers = classes.size();

		Map<Double, Integer> temp = new HashMap<>();

		for(Double d : classes) {

			if(temp.containsKey(d)) {

				int count = temp.get(d);
				temp.remove(d);
				temp.put(d, count+1);
			}
			else {
				temp.put(d, 1);
			}
		}

		for(Double d : temp.keySet()) {

			double x = (double)temp.get(d)/(double)numberOfMembers;

			//x *= this.miValue;
			//System.out.println("Class: " + d + "\tProbability: " + x);

			clsprb.put(d, x);

		}

		temp.clear();

		return clsprb;
	}

	/*public void calculateMutualInformation(Node temp) {
		// TODO Auto-generated method stub

		List<List<Double>> dataset = new ArrayList<>();

		for(List<Double> lst : temp.attribute){
			dataset.add(lst);
		}

		for(int i = 0; i < temp.decision.size(); i++){
			dataset.get(i).add(temp.decision.get(i));
		}

		
		for (int i = 0; i < dataset.size(); i++) {

			for (int j = 0; j < dataset.get(i).size(); j++) {
				System.out.print(dataset.get(i).get(j) + "\t");
			}
			System.out.println();
		}
		
		
		double[] firstVector = new double[dataset.size()];
		double[] secondVector = new double[dataset.size()];

		double tempRes1 = 0.0;

		int lastIndex = dataset.get(0).size()-1;

		for(int i = 0; i < dataset.get(0).size()-1; i++){

			for(int j = 0; j < dataset.size(); j++){

				firstVector[j] = dataset.get(j).get(i);
				secondVector[j] = dataset.get(j).get(lastIndex);
			}

			//firstVector = rangeSplit(firstVector, 12);
			
			tempRes1 += MutualInformation.calculateMutualInformation(firstVector, secondVector);

		}

		double res1 = tempRes1 / (lastIndex);

		//System.out.println(res1);

		if(lastIndex < 2){

			this.miValue = 1.0;
			return;
		}

		int t1 = factorial(lastIndex);
		int t2 = factorial(lastIndex-2);

		int loopCount = t1/(t2*2);

		double tempRes2 = 0.0;
		double tempRes3 = 0.0;

		double[] conditionVector = new double[dataset.size()];

		for(int i = 0; i < lastIndex-1; i++){

			for(int j = i+1; j < lastIndex-1; j++){

				for(int k = 0; k < dataset.size(); k++){

					firstVector[k] = dataset.get(k).get(i);
					secondVector[k] = dataset.get(k).get(j);
					conditionVector[k] = dataset.get(k).get(lastIndex);
				}

				//firstVector = rangeSplit(firstVector, 12);
				//secondVector = rangeSplit(secondVector, 12);
				
				tempRes2 += MutualInformation.calculateMutualInformation(firstVector, secondVector);
				tempRes3 += MutualInformation.calculateConditionalMutualInformation(firstVector, secondVector, conditionVector);
			}

		}

		//System.out.println(tempRes2);

		double res2 = tempRes2 / (double)loopCount;

		//System.out.println(res2);

		//System.out.println(tempRes3);

		double res3 = tempRes3 / (double)loopCount;

		//System.out.println(res3);

		this.miValue = 	res1 - res2 + res3;

		//System.out.println("MI value: " + this.miValue);

	}

	public static int factorial(int number){

		if(number == 1 || number == 0){
			return 1;
		}
		else{
			return number*factorial(number-1);
		}
	}*/
	
	public void processBhtDst(Node temp) {
		// TODO Auto-generated method stub

		double[] firstVector = new double[temp.attribute.size()];
		double[] secondVector = new double[temp.attribute.size()];
		
		List<Integer> first = new ArrayList<>();
		List<Integer> second = new ArrayList<>();

		double value = 0.0;
		int count = 0;

		int lastIndex = temp.attribute.get(0).size();

		for(int i = 0; i < lastIndex; i++){

			for(int j = i+1; j < lastIndex; j++){

				count++;
				
				for(int k = 0; k < temp.attribute.size(); k++){

					firstVector[k] = temp.attribute.get(k).get(i);
					secondVector[k] = temp.attribute.get(k).get(j);
					
				}

				first = null;
				second = null;
				
				first = rangeSplit(firstVector, 10);
				second = rangeSplit(secondVector, 10);

				value += calculateBhtDst(first, second, 10);
			}

		}
				
		value = value/count;
		
		//System.out.println("bht dst: " + value);
		
		this.bhtValue = value;
	}
	
	private List<Integer> rangeSplit(double[] vector, int fold) {
		// TODO Auto-generated method stub
		
		int[] output = new int[vector.length];
		
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		
		for(int j = 0; j < vector.length; j++){
			
			if(vector[j] > max){
				max = vector[j];
			}
			
			if(vector[j] < min){
				min = vector[j];
			}
		}
		
		double range = (max-min)/(double)fold;
		
		double[] ranges = new double[fold];
		
		for(int k = 0; k < fold; k++){
			
			ranges[k] = min + (k+1)*range;
		}
		
		/*for(int k = 0; k < fold; k++){
			
			System.out.print("\t" + ranges[k]);
		}
		System.out.println();*/
		
		for(int j = 0; j < vector.length; j++){
			
			for(int k = 0; k < fold; k++){
				
				if(vector[j] <= ranges[k]){
					
					output[j] = k + 1;
					//System.out.println("Original value: " + vector[j] + "\trange check: " + output[j] + "\tK: " + k);
					break;
				}
			}
		}
		
		List<Integer> otpt = new ArrayList<>();
		
		for(int j = 0; j < output.length; j++){
			
			otpt.add(output[j]);
		}
		
		return otpt;
	}
	
	public double calculateBhtDst(List<Integer> first, List<Integer> second, int fold){

		double result = 0.0;

		Map<Integer, Integer> histOneMap = new HashMap<>();
		Map<Integer, Integer> histTwoMap = new HashMap<>();

		for(int elm : first){

			if(histOneMap.containsKey(elm)) {

				int count = histOneMap.get(elm);
				histOneMap.put(elm, count+1);
			}
			else {
				histOneMap.put(elm, 1);
			}
		}

		for(int elm : second){

			if(histTwoMap.containsKey(elm)) {

				int count = histTwoMap.get(elm);
				histTwoMap.put(elm, count+1);
			}
			else {
				histTwoMap.put(elm, 1);
			}
		}
		
		for(int i = 1; i <= fold; i++){
			
			if(!histOneMap.containsKey(i)){
				histOneMap.put(i, 0);
			}
			if(!histTwoMap.containsKey(i)){
				histTwoMap.put(i, 0);
			}
		}

		double prob1 = 0.0;
		double prob2 = 0.0;

		for(int i = 0; i < fold; i++){
			
			prob1 = (double)histOneMap.get(i + 1)/(double)first.size();
			prob2 = (double)histTwoMap.get(i + 1)/(double)second.size();
			
			result += Math.sqrt(prob1*prob2);		
		}

		if(result > 1){
			result = 1.0;
		}
		
		result = Math.sqrt(1.0 - result);

		return result;
	}

}