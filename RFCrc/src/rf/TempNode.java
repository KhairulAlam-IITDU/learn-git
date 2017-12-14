package rf;

import java.util.HashMap;
import java.util.Map;

public class TempNode {
	
	public Node leftChild;
	public Node rightChild;
	
	public double Gain;
	public double nodeValue;
	public int featureIndexRow;
	public int featureIndexColumn;
	Map<Double, Double> clsProb = new HashMap<>();

	public TempNode() {}
	
	TempNode(Node leftChild, Node rightChild, double Gain) {
		
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.Gain = Gain;
	}

}

