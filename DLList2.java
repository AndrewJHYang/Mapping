
public class DLList<T> implements DLListADT<T>{
	
	private DLNode<T>front;
	private DLNode<T>rear;
	private int count;
	
	public DLList() {
		count=0;
		front = null;
		rear = null;
	}
	
	
	public void insert(T dataItem, int value) {
		DLNode<T>newNode = new DLNode<T>(dataItem, value);
		if(isEmpty()) {
			front = newNode; 
			rear = front;
			front.setNext(null);
			front.setPrevious(null);
		}else{
			newNode.setPrevious(rear);
			newNode.setNext(null);
			rear.setNext(newNode);
			rear = newNode;
		}
		count++;
	}


	public T getSmallest() throws EmptyListException{
		if(!isEmpty()) {
			DLNode<T>current = front;
			DLNode<T>smallest = current;
			int small = current.getValue();
			
			for(int x=0; x<count; x++) {
				if(current.getValue() < small) {
					small = current.getValue();
					smallest = current;
				}
				current = current.getNext();
			}
			
			if(front==rear) {
				front=null;
				rear=null;
				count--;
			}else{
				if(smallest==front) {
					front=smallest.getNext();
					smallest.getNext().setPrevious(null);
					count--;
				}else{
					if(smallest==rear) {
						rear = smallest.getPrevious();
						smallest.getPrevious().setNext(null);
						count--;
					}else{
						smallest.getPrevious().setNext(smallest.getNext());
						smallest.getNext().setPrevious(smallest.getPrevious());
						count--;
					}
				}
			}
			return smallest.getData();
		}else {
			throw new EmptyListException("List is empty!");
		}
	}

	public void changeValue(T dataItem, int newValue) throws InvalidDataItemException{
		
		DLNode<T>current = front;
		
		for(int x=0; x<count; x++) {
			if(current.getData().equals(dataItem)) {
				current.setValue(newValue);
				return;
			}
			current = current.getNext();
		}
		
		throw new InvalidDataItemException("data item is not in the list!");
		
	}


	public int getDataValue(T dataItem) throws InvalidDataItemException{
		
		DLNode<T>current = front;
		
		for(int x=0; x<count; x++) {
			if(current.getData().equals(dataItem)) {
				return current.getValue();
			}
			current = current.getNext();
		}
		
		throw new InvalidDataItemException("data item is not in the list!");
	}
	

	public boolean isEmpty() {
		return (count==0);
	}


	public int size() {
		return count;
	}


	public String toString() {
		
		DLNode<T>current = front;
		String message = "List: ";
		
		for(int x=0; x<count; x++) {
			message = message+" data "+(x+1)+" ," + current.getValue() + ";";
			current = current.getNext();
		}
		return message;
	}

}
