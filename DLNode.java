
/**
 * This class represents the nodes of a doubly linked list. 
 * @author Andrew Yang
 */
public class DLNode<T> {
	private T dataItem; 	//A reference to the data item stored in this node.
	private DLNode<T> next; 	// A reference to the next node in the liked list
	private DLNode<T> previous; 	//A reference to the previous node in the linked list. 
	private int value; 		//This is the value of the data item stored in this node. 
	
	public DLNode (T data, int value) {
		this.value = value;
		dataItem = data;
		next = null;
		previous= null;
	}
	
	public int getValue() {
		return value;
	}
	
	public T getData() {
		return dataItem;
	}
	
	public DLNode<T> getNext() {
		return next;
	}
	
	public DLNode<T> getPrevious(){
		return previous;
	}
	
	public void setData(T data){
		dataItem = data;
	}
	
	public void setNext(DLNode<T> node) {
		next = node;
	}
	
	public void setPrevious(DLNode<T> node) {
		previous = node;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
