import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class computes an algorithm to find a path from the WPC cell to the customer cell
 * @author Andrew Yang
 */
public class ShortestPath {
	
	/**
	 * declares cityMap as a Map type object
	 */
	Map cityMap;
	
	/**
	 * constructor that initiates a Map object
	 * @param filename the given input file name
	 * @exception catches InvalidMapException, FileNotFoundException, and IOException
	 */
	public ShortestPath(String filename) throws InvalidMapException, FileNotFoundException, IOException {
		try {
			cityMap = new Map(filename);
		}catch(InvalidMapException e) {
			System.out.println("Invalid map.");
		}catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}catch (IOException e) {
			System.out.println("Process could not be completed.");
		}
	}
	
	/**
	 * nextCell finds the next cell to continue the path from the current one based on the order
	 * given in the assignment
	 * @param cell the current cell
	 * @return the next cell to continue towards
	 */
	private MapCell nextCell(MapCell cell) {
		/**
		 * omnilow holds the lowest index position of an omni switch
		 * switchlow holds the lowest index position of an horizontal or vertical switch
		 */
		int omnilow = 4;
		int switchlow = 4;
		
		if (cell.isOmniSwitch() || cell.isPowerStation()) {
			//looping through each position around the current cell
			for (int i = 0; i < 4; i++) {
				try {
					if (cell.getNeighbour(i) != null) { 
						MapCell ncell = cell.getNeighbour(i);
						/**
						 * proceeding if and else if statements check surrounding cell's type, index value, whether its been marked
						 * the omnilow and switchlow variables are reset to the lowest value if the conditions are met
						 */
						if (ncell.isCustomer()) {
							return ncell;      //cell is returned if it is the customer cell
						}else if (ncell.isOmniSwitch() && (i < omnilow) && !ncell.isMarked()) {
							omnilow = i;
						}else if (ncell.isVerticalSwitch() && (i < switchlow) && ((i == 0) || (i == 2)) && !ncell.isMarked()) {
							switchlow = i;
						}else if (ncell.isHorizontalSwitch() && (i < switchlow) && ((i == 1) || (i == 3)) && !ncell.isMarked()) {
							switchlow = i;
						}
					}
				}catch(InvalidNeighbourIndexException e) {    //catches the exception if the index value is not between 0 and 3, or negative
					System.out.println("Index is out of range.");
				}
			}
		}else if (cell.isVerticalSwitch()) {
			//looping through each position around the current cell
			for (int i = 0; i < 4; i+=2) {
				try {
					if (cell.getNeighbour(i) != null) {
						MapCell ncell = cell.getNeighbour(i);
						/**
						 * proceeding if and else if statements check surrounding cell's type, index value, whether its been marked
						 * the omnilow and switchlow variables are reset to the lowest value if the conditions are met
						 */						
						if (ncell.isCustomer()) {
							return ncell;      //cell is returned if it is the customer cell
						}else if (ncell.isOmniSwitch() && (i < omnilow) && !ncell.isMarked()) {
							omnilow = i;
						}else if (ncell.isVerticalSwitch() && (i < switchlow) && !ncell.isMarked()) {
							switchlow = i;
						}
					}	
				}catch(InvalidNeighbourIndexException e) {    //catches the exception if the index value is not between 0 and 3, or negative
					System.out.println("Index is out of range.");
				}
			}
		}else if (cell.isHorizontalSwitch()) {
			//looping through each position around the current cell
			for (int i = 1; i < 4; i+=2) {
				try {
					if (cell.getNeighbour(i) != null) {
						MapCell ncell = cell.getNeighbour(i);
						/**
						 * proceeding if and else if statements check surrounding cell's type, index value, whether its been marked
						 * the omnilow and switchlow variables are reset to the lowest value if the conditions are met
						 */
						if (ncell.isCustomer()) {
							return ncell;      //cell is returned if it is the customer cell
						}else if (ncell.isOmniSwitch() && (i < omnilow) && !ncell.isMarked()) {
							omnilow = i;
						}else if (ncell.isHorizontalSwitch() && (i < switchlow) && !ncell.isMarked()) {
							switchlow = i;
						}
					}
				}catch(InvalidNeighbourIndexException e) {    //catches the exception if the index value is not between 0 and 3, or negative
					System.out.println("Index is out of range.");
				}
			}
		}
		
		
		if (omnilow < switchlow && omnilow < 4) {	//if the position of omni switch is lower, the lowest omni switch is returned
			return cell.getNeighbour(omnilow);
		}else if(switchlow < omnilow && switchlow < 4) {	//if the position of vert/horz switch is lower, the lowest vert/horz switch is returned
			return cell.getNeighbour(switchlow);
		}else{
			return null;	//else null is returned
		}
		
	}
	
	/**
	 * main method tries to find a path from the WPC cell to the destination cell C
	 * @param args the name of the input file as the command line argument
	 */
	public static void main(String[] args) throws InvalidMapException, FileNotFoundException, IOException {
		
		//shortestPath constructor is fired and tha doubly linked list is initiated
		ShortestPath file = new ShortestPath(args[0]);
		DLList<MapCell> dll = new DLList<MapCell>();
		
		//the WPC is pushed into the stack and marked
		dll.insert(file.cityMap.getStart(),0);
		file.cityMap.getStart().markInList();
		
		boolean found = false;	//boolean variable for if the customer has been found
		
		//loop continues until the customer cell is found or until all reachable cells have emptied from the doubly linked list
		while (!dll.isEmpty() && !found) {
			
			MapCell small = dll.getSmallest(); 	//gets the cell with the shorstest distance to WPC
			small.markOutList();	//marks the small cell out of list
			
			//if the customer cell is found: a message is printed and the loop exists
			if (small.isCustomer()) {	
				found = true;
				System.out.println(dll.toString());
				System.out.println("The shortest path has length " + (small.getDistanceToStart()+1) + ".");
				break;
			}else {
				MapCell next = file.nextCell(small);
				
				//loops four times to check each of the neighboring cells until they are all null or marked out of the list
				for (int i = 0; i < 4; i++) {
					if(next != null) { //check for marked cells and non switch/vertical/horizontal cells are done in shortest path method
						
						//if the DST is not the shortest path, reset it based on new shortest DST
						int d = 1 + small.getDistanceToStart();			
						if (next.getDistanceToStart() > d) { 	
							next.setDistanceToStart(d);
							next.setPredecessor(small);
						}
						
						//updating the value of DST/dataItem to the new shortest DST
						int p = next.getDistanceToStart();
						if (next.isMarkedInList() && p < dll.getDataValue(next)){	
							dll.changeValue(next, p);
						
						// if the next cell is not marked, the cell is marked and inserted into the doubly linked list
						}else if (!next.isMarkedInList()) {
							dll.insert(next, p);
							next.setPredecessor(small); //L
							System.out.println(dll.toString());
							next.markInList();
						}
					}
					next = file.nextCell(small); //the cell is reset to continue to the next cell
				}
	
			}
		
		}
		//if the customer is not found, an error message is printed
		if(!found) {
			System.out.println("Customer cell was not found");
		}
		
	}

}

