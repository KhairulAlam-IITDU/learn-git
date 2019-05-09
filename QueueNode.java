package samsung;

public class QueueNode {
	int id, x, y, dist, prev;
	QueueNode next;
	
	public QueueNode(int id) {
		this.id = id;
		this.dist = 0;
		this.next = null;
	}
	
	public QueueNode(int x, int y) {
		this.x = x;
		this.y = y;
		this.dist = 0;
		this.next = null;
	}
}
