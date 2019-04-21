
/**
 * @author Andrew Yang
 * This class implements a doubly linked list in which the nodes are of the class DLNode.
 */
public class DLList<T> implements DLListADT<T>{
	
	private DLNode<T> front;
	private DLNode<T> rear;
	private int count;
	
	/**
	 * constructor initiates count, front and null of an empty doubly linked list
	 */
	public DLList() {
		count = 0;	//The value of this variable is the number of data items in the linked list, which is set to empty.
		front = null;	// This is a reference to the first node of the doubly linked list.
		rear = null;	//This is a reference to the last node of the doubly linked list.
	}

	/**
	 * Adds a new DLNode storing the given dataItem and value to the rear of the doubly linked list.
	 * @param dataItem A reference to the data item stored in this node.
	 * @param value This is the value of the data item stored in this node. 
	 */
	public void insert(T dataItem, int value) {
		
		DLNode<T> new_node = new DLNode<T>(dataItem, value);	//a new node is created
		
		//if the doubly linked list is empty, front and rear are set to the node
		if (isEmpty()) {	
			front = new_node;
			rear = front;
			front.setNext(null);
			front.setPrevious(null);
		//if the doubly linked list is not empty, the node is added to the end of the doubly linked list
		}else {
			new_node.setPrevious(rear);
			new_node.setNext(null);
			rear.setNext(new_node);
			rear = new_node;
		}
		count ++;	//the count is incremented because a new node is added
	}
	
	/**
	 * Returns the integer value associated to the specified dataItem. 
	 * @param dataItem finds the dataItem in the doubly linked list and returns its value
	 * @throws InvalidDataItemException thrown if the given dataItem is not in the list.
	 * @return Returns the integer value associated to the specified dataItem. 
	 */
	public int getDataValue(T dataItem) throws InvalidDataItemException {
		
		DLNode<T> current = front;
		
		//current is incremented and each time it checks for the dataItem until current reaches the end of the doubly linked list 
		while (current != null) {
			if (current.getData().equals(dataItem)) {
				return current.getValue();	//once the dataItem is found, its value is returned
			}
			current = current.getNext();
		}
		throw new InvalidDataItemException ("Node with specific data item does not exist.");
	}

	/**
	 * Changes the value of dataItem to newValue.
	 * @param dataItem finds the dataItem in the doubly linked list and changes its value
	 * @param newValue changes the value of the dataItem node to newValue
	 * @throws thrown if the given dataItem is not in the list.
	 */
	public void changeValue(T dataItem, int newValue) throws InvalidDataItemException {
		DLNode<T> current = front;
		
		//current is incremented and each time it checks for the dataItem until current reaches the end of the doubly linked list 
		while (current != null) {
			if (current.getData().equals(dataItem)) {
				current.setValue(newValue); 	//once the dataItem is found, its value is changed to newValue
				return;
			}
			current = current.getNext();
		}
		throw new InvalidDataItemException ("Node with specific data item does not exist.");
	}
	
	/**
	 * Removes and returns the data item in the list with smallest associated value.
	 * @return the data item in the list with smallest associated value.
	 * @throws thrown if the list is empty. 
	 */
	public T getSmallest() throws EmptyListException {
		if (isEmpty()) {
			throw new EmptyListException("List is empty.");
		}
		//finding the smallest value in the doubly linked list:
		int min = front.getValue();
		DLNode<T> current = front;
		DLNode<T> smallest = front;
		while (current != null) {
			if (min > current.getValue()) {
				min = current.getValue();
				smallest = current;
			}
			current = current.getNext();
		}
		
		//removing the smallest value in the double linked list:
		if (front == rear) {	//if there is only one dataItem, front and rear are set to null
			front = null;
			rear = null;
			count--;	//count is always decremented
		
		//if the smallest dataItem is at the front:
		}else if(smallest == front) {	
			front = smallest.getNext(); 	
			smallest.getNext().setPrevious(null);
			count--;
			
		//if the smallest dataItem is at the rear:
		}else if(smallest == rear) {	
			rear = smallest.getPrevious();
			smallest.getPrevious().setNext(null);
			count--;
			
		//if the smallest dataItem is in the middle of the list:
		}else {		
			smallest.getPrevious().setNext(smallest.getNext());
			smallest.getNext().setPrevious(smallest.getPrevious());
			count--;
		}
		return smallest.getData();
	}
	
	/**
	 * @return 	returns true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return (count==0);
	}
	
	/**
	 * @return 	returns count which is the number of elements in the doubly linked list
	 */
	public int size() {
		return count;
	}
	
	/**
	 * invoke the toString method from each data item in the priority queue and concatenate these strings to produce a string
	 * @return the doubly linked list in string format
	 */
	public String toString() {
		
		DLNode<T> current = front; 	//current points to front of list
		String output = "Array: "; 	//output String 
		
		if (!isEmpty()) {	//checks that the list is not empty to avoid error
			output = output + current.getData().toString() + "," + current.getValue() + ";"; 	//adds the first item into the output
			while(current.getNext() != null) { 		//until current reaches the end of the list, the next item is added to the output
				current = current.getNext();
				output = output + current.getData().toString() + "," + current.getValue() + ";";
			}
		}else {
			output = null;  	//if the list is empty, null is returned
		}
		
		return output; 
		
	}

}
