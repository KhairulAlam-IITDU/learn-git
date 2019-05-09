package samsung;

public class Queue {
	private int size;
	QueueNode top, tail;
	
	public Queue() {
		this.size = 0;
		this.top = this.tail = null;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	public void enq(QueueNode node) {
		
		if(this.top == null) {
			this.top = this.tail = node;
		}
		else {
			this.tail.next = node;
			this.tail = node;
		}
		
		this.size++;
	}
	
	public QueueNode deq() {
		
		if(this.size == 0)
			return null;
		
		QueueNode node = this.top;
		
		if(this.size == 1)
			this.top = this.tail = null;
		else
			this.top = this.top.next;
		
		this.size--;
		return node;
			
	}
}
