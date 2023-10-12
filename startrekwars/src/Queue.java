
public class Queue {
	private int rear, front;
	private Object[] elements;
	
	Queue(int capacity){
		elements= new Object[capacity];
		rear=-1;
		front=0;
	}
	
	public void enqueue(Object data) {
		if(isFull()) {
			System.out.println("queue overflow");
		}
		else {
			rear= (rear+1) % elements.length;
			elements[rear]= data;
		}
	}
	public Object dequeue() {
		if(isEmpty()) {
			System.out.println("queue is empty");
			return null;
		}
		else {
			Object retData= elements[front];
			elements[front]= null;
			front=(front+1)% elements.length;
			return retData;
		}
	}
	public Object peek() {
		if(isEmpty()) {
			System.out.println("queue is empty");
			return null;
		}
		else {
			return elements[front];
		}
	}
	public boolean isEmpty() {
		return elements[front]==null;
	}
	public boolean isFull() {
		return (front== (rear + 1)% elements.length && elements[front]!=null && elements[rear]!= null);
	}
	public int size() {
		if(rear>= front) {
			return rear- front+1;
		}
		else if(elements[front]!= null) {
			return elements.length -(front-rear)+1;
		}
		else 
			return 0;
	}
	
}
