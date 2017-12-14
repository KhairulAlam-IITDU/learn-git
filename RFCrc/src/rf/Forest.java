package rf;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class Forest {

	public ArrayList <DecisionTree> totalTrees = new ArrayList<>();

	public void makeDecisionTree(Node rtnd) { //rtnd

		for(int i = 0; i < 100; i++) {

			Node rootNode = bootstrap(rtnd);
			
			//System.out.println("\nTree " + (i+1) + " is building...");
			
			Random rand = new Random(); //new
			
			rand.setSeed((long) i*i);

			int numberOfFeatures = rootNode.attribute.get(0).size();

			//int featuresForSingleTree = (int)Math.ceil(Math.sqrt(numberOfFeatures));
			
			int featuresForSingleTree = (int)(Math.log10(numberOfFeatures)/Math.log10(2))+1;

			Node temp = new Node();

			int[] columnIndexesForFeature = new int[featuresForSingleTree]; //new

			Map<Integer, Integer> mapTrimmedDataColumnWithMainDataColumn = new HashMap<>();

			//new loop
			//System.out.println("Selected features: ");
			
			for(int j = 0; j < featuresForSingleTree; j++) {

				int column = rand.nextInt(numberOfFeatures);

				columnIndexesForFeature[j] = column;

				//System.out.print(columnIndexesForFeature[j] + "\t");

				mapTrimmedDataColumnWithMainDataColumn.put(j, columnIndexesForFeature[j]);

			}//end new loop
			
			//System.out.println();
			
			
			//System.out.println("number of features: " + numberOfFeatures);
			//System.out.println("features for single tree: " + featuresForSingleTree);

			for(int k = 0; k < rootNode.attribute.size(); k++) {

				List<Double> tempData = new ArrayList<>();
				// int column <--> k = rand.nextInt(rootNode.attribute.size());

				//int column = rand.nextInt(rootNode.attribute.size());
				
				for(int l = 0; l < featuresForSingleTree; l++) {

					tempData.add(rootNode.attribute.get(k).get(columnIndexesForFeature[l])); // column <--> k

				}

				temp.attribute.add(tempData);

				temp.decision.add(rootNode.decision.get(k)); // column <--> k
			}


			/*for (int l = 0; l < temp.attribute.size(); l++) {

				//System.out.print(temp.decision.get(l) + "\t");

				for (int j = 0; j < temp.attribute.get(0).size(); j++) {

					System.out.print(temp.attribute.get(l).get(j) + "\t");

				}
				System.out.println();
			}*/

			DecisionTree  decisionTree = new DecisionTree(temp, mapTrimmedDataColumnWithMainDataColumn);

			//decisionTree.makeTree(temp, 0);

			//decisionTree.makeTree(temp, 0, mapTrimmedDataColumnWithMainDataColumn); //new
			
			//decisionTree.processBhtDst(temp);
			
			decisionTree.inorderTraverse(decisionTree.root);

			totalTrees.add(decisionTree);

		}
	}
	
	
    public Node bootstrap(Node data) {
        
    	Random random = new Random();

    	Node bootstrappedData = new Node();

        for (int i = 0; i < data.attribute.size(); i++) {
            
        	int index = random.nextInt(data.attribute.size());
        	
        	bootstrappedData.attribute.add(data.attribute.get(index));
        	bootstrappedData.decision.add(data.decision.get(index));
        }

        return bootstrappedData;
    }


	public double traverseForest(List<Double> testSet) {

		int count = 0;

		Map<Double, Double> counter = new TreeMap<>();

		//loop start
		for(DecisionTree dt : totalTrees) {

			//System.out.println(dt.root.column1);

			count++;
			
			//System.out.println("\nTree number: " + count);
			
			dt.traverse(dt.root, testSet);
			
			/*
			//start majority vote
			if(counter.containsKey(dt.decisionClass)) {

				double value = counter.get(dt.decisionClass);
				counter.remove(dt.decisionClass);
				counter.put(dt.decisionClass, value+1.00);

				//System.out.println("Class: " + k + "\tCumulative Probability: " + counter.get(k));
			}
			else {

				counter.put(dt.decisionClass, 1.00);

				//System.out.println("\tPredicted Class: " + k + "\nCumulative Probability: " + counter.get(k));
			}			
			//end majority vote
			*/
			
			//System.out.println("start new DT");
			//start class probability
			if(!dt.singleTreeClassProbability.isEmpty()) {

				for(Double k : dt.singleTreeClassProbability.keySet()) {

					//System.out.println("\nClass: " + k + "\tProbability: " + dt.singleTreeClassProbability.get(k));

					if(counter.containsKey(k)) {

						double value = counter.get(k);
						counter.remove(k);
						counter.put(k, value + dt.singleTreeClassProbability.get(k));

						//System.out.println("Class: " + k + "\tCumulative Probability: " + counter.get(k));
					}
					else {

						counter.put(k, dt.singleTreeClassProbability.get(k));

						//System.out.println("\tPredicted Class: " + k + "\nCumulative Probability: " + counter.get(k));
					}
				}
			}
			else {

				System.out.println("\nOOPS! Blank Tree Probability! Check Code...");
			}
			//end class probability
			

		}//loop end

		double result = -100.0;
		double finalDecision = -100.0;

		if(!counter.isEmpty()) {
			
			for(Double key : counter.keySet()) {

				if(result < counter.get(key)) {

					result = counter.get(key);

					finalDecision = key;
				}
			}
		}
		else {
			
			System.out.println("\nOOPS! Blank Forest Probability! Check Code...");
		}

		return finalDecision;
	}

}
