package rf;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int totalTestData = 0;
		int correctAnswer = 0;

		//for (int j = 0; j < 5; j++) {

		ReadFile r = new ReadFile();
		r.fileReader();

		//Node traineeData = r.makeTraineeData();

		Node[] TrainTestData = r.makeData();

		/*for (int i = 0; i < traineeData.attribute.size(); i++) {

			System.out.print(traineeData.decision.get(i) + "\t");
			for (int j = 0; j < traineeData.attribute.get(0).size(); j++) {

				System.out.print(traineeData.attribute.get(i).get(j) + "\t");

			}
			System.out.println();
		}*/

		Node testData = TrainTestData[1];

		totalTestData = testData.decision.size();

		for (int j = 0; j < 10; j++) {

			System.out.println("Forest " + j + " is building...");
			
			Forest forest = new Forest();

			//forest.makeDecisionTree(traineeData);

			forest.makeDecisionTree(TrainTestData[0]);

			//Node testData = r.makeTestData();


			//System.out.println("Total Test Data: " + totalTestData + "\n");

			int tstdtcnt = 1;

			for (int i = 0; i < testData.decision.size(); i++) {

				double key = testData.decision.get(i);

				/*System.out.println("Test Data: " + (tstdtcnt++));
			for(Double d : testData.attribute.get(i)) {

				System.out.print(d + ", ");

			}*/

				double returnedKey = forest.traverseForest(testData.attribute.get(i));

				//System.out.println("dataset = " + testData.attribute.get(i) + "\nActual decision = " + key
				//	+ "\nReturned decision = " + returnedKey);

				if (key == returnedKey) {

					//System.out.println("\nOriginal class: " + key + "\tPredicted class: " + returnedKey + "\tRight Prediction.");
					correctAnswer++;
				}
				else {
					System.out.println("Original class: " + key + "\tPredicted class: " + returnedKey + "\tOOPS! Wrong Prediction.");
				}
			}
			//System.out.println("\n\n\n");
			//}

		}

		double accuracyRate = ((double)correctAnswer / ((double)totalTestData*10)) * 100.0;

		System.out.println("\nTotal test Data = " + testData.decision.size()*10 + ",\tCorrect Answer = " + correctAnswer);

		System.out.println("\nAccuracy Rate = " + accuracyRate + "%");

	}

}
