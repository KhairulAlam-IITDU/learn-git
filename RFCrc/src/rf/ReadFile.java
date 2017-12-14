package rf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class ReadFile {

	private Node totalData = new Node();
	private Node traineeData = new Node();
	private Node testData = new Node();
	private Map<Double, List<List<Double>>> sortedDatasThroughDecision = new TreeMap<>();

	/*
	//pre-defined train data
	public Node makeTraineeData() {

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			is = new FileInputStream("trainData.txt");

			isr = new InputStreamReader(is);

			br = new BufferedReader(isr);

			String s = new String();

			while ((s = br.readLine()) != null) {

				String[] separate = s.split(","); // ","

				List<Double> temp = new ArrayList<>();

				double decisionAttribute = Double.parseDouble(separate[0]); // [0], [separate.length-1]

				traineeData.decision.add(decisionAttribute);

				for (int i = 1; i < separate.length; i++) {					// int i = 0; i < separate.length-1; i++
					double matrixElement = Double.parseDouble(separate[i]);
					temp.add(matrixElement);

				}

				traineeData.attribute.add(temp);

			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return traineeData;

	}
	//end of pre-defined train data


	//pre-defined test data
	public Node makeTestData() {

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			is = new FileInputStream("testData.txt");

			isr = new InputStreamReader(is);

			br = new BufferedReader(isr);

			String s = new String();

			while ((s = br.readLine()) != null) {

				String[] separate = s.split(","); // ","

				List<Double> temp = new ArrayList<>();

				double decisionAttribute = Double.parseDouble(separate[0]); // [0], [separate.length-1]

				testData.decision.add(decisionAttribute);

				for (int i = 1; i < separate.length; i++) {					// int i = 0; i < separate.length-1; i++
					double matrixElement = Double.parseDouble(separate[i]);
					temp.add(matrixElement);

				}

				testData.attribute.add(temp);

			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return testData;

	}
	//end of pre-defined test data
	 */



	//original process
	public void fileReader() {

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {
			is = new FileInputStream("glass.txt");

			isr = new InputStreamReader(is);

			br = new BufferedReader(isr);

			String s = new String();

			while ((s = br.readLine()) != null) {

				String[] separate = s.split(","); // ","

				List<Double> temp = new ArrayList<>();

				double decisionAttribute = Double.parseDouble(separate[0]); // [0], [separate.length-1]

				totalData.decision.add(decisionAttribute);

				for (int i = 1; i < separate.length; i++) {					// int i = 0; i < separate.length-1; i++
					double matrixElement = Double.parseDouble(separate[i]);
					temp.add(matrixElement);

				}

				totalData.attribute.add(temp);

			}



		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//new added on 10/6/2017 at 10.38 AM
	public Node[] makeData() {

		Node[] output = new Node[2];

		try {

			for (int counter = 0; counter < totalData.decision.size(); counter++) {

				if (!sortedDatasThroughDecision.containsKey(totalData.decision.get(counter))) {

					List<List<Double>> temp = new ArrayList<>();
					temp.add(totalData.attribute.get(counter));
					sortedDatasThroughDecision.put(totalData.decision.get(counter), temp);

				} else {

					List<List<Double>> temp = sortedDatasThroughDecision.get(totalData.decision.get(counter));
					sortedDatasThroughDecision.remove(totalData.decision.get(counter));
					temp.add(totalData.attribute.get(counter));
					sortedDatasThroughDecision.put(totalData.decision.get(counter), temp);
				}
			}

			for (Double d : sortedDatasThroughDecision.keySet()) {

				int numberOfTraineeDatasForClass = (int) (sortedDatasThroughDecision.get(d).size() * 0.9);

				//System.out.println(("Class: " + d + ", total data count: " + sortedDatasThroughDecision.get(d).size()));
				//System.out.println("Class: " + d + ", taken Data count: " + numberOfTraineeDatasForClass);

				for (int i = 0; i < sortedDatasThroughDecision.get(d).size(); i++) {


					if(i < numberOfTraineeDatasForClass){
						
						//System.out.println("Traindata for class: " + d + ", ind: " + i);
						traineeData.decision.add(d);
						traineeData.attribute.add(sortedDatasThroughDecision.get(d).get(i));	
					}
					else{
						
						//System.out.println("Testdata for class: " + d + ", ind: " + i);
						testData.decision.add(d);
						testData.attribute.add(sortedDatasThroughDecision.get(d).get(i));
					}
				}

			}

			output[0] = traineeData;
			output[1] = testData;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;

	}
	//new added on 10/6/2017 at 10.38 AM
	
	

	public Node makeTraineeData() {

		Random random = new Random();

		random.setSeed((long) 1);

		try {

			for (int counter = 0; counter < totalData.decision.size(); counter++) {

				if (!sortedDatasThroughDecision.containsKey(totalData.decision.get(counter))) {

					List<List<Double>> temp = new ArrayList<>();
					temp.add(totalData.attribute.get(counter));
					sortedDatasThroughDecision.put(totalData.decision.get(counter), temp);

				} else {

					List<List<Double>> temp = sortedDatasThroughDecision.get(totalData.decision.get(counter));
					sortedDatasThroughDecision.remove(totalData.decision.get(counter));
					temp.add(totalData.attribute.get(counter));
					sortedDatasThroughDecision.put(totalData.decision.get(counter), temp);
				}
			}

			for (Double d : sortedDatasThroughDecision.keySet()) {

				int numberOfTraineeDatasForClass = (int) (sortedDatasThroughDecision.get(d).size() * 0.9);

				//System.out.println(("Class: " + d + ", total data count: " + sortedDatasThroughDecision.get(d).size()));
				//System.out.println("Class: " + d + ", taken Data count: " + numberOfTraineeDatasForClass);

				for (int i = 0; i < numberOfTraineeDatasForClass; i++) {

						int index = random.nextInt(sortedDatasThroughDecision.get(d).size());
						//System.out.println("Traindata for class: " + d + ", ind: " + i);
						traineeData.decision.add(d);
						traineeData.attribute.add(sortedDatasThroughDecision.get(d).get(index));	// i <--> index
						sortedDatasThroughDecision.get(d).remove(index);	// i <--> index
				}

			}

			//System.out.println();
			//print training Data



		} catch (Exception e) {
			e.printStackTrace();
		}
		return traineeData;

	}

	public Node makeTestData()
	{
		try{

			for(Double d : sortedDatasThroughDecision.keySet())
			{
				//System.out.println("Class: " + d + ", test data count: " + sortedDatasThroughDecision.get(d).size());

				for(int i = 0; i < sortedDatasThroughDecision.get(d).size(); i++)
				{
					testData.decision.add(d);
					testData.attribute.add(sortedDatasThroughDecision.get(d).get(i));
				}
			}
			//System.out.println();

		}catch(Exception e){
			e.printStackTrace();
		}
		return testData;

	}
}