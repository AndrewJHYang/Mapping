import java.io.*;

public class ShortestPath {

	Map cityMap;
	
	public ShortestPath(String filename) {
		//this try catch block will attempt to make a new Map object.
		//it will handle all the exception the Map constructor throws, for more info on what exceptions it throws refer to the error messages
		try {
			cityMap = new Map(filename);
		
		}catch(InvalidMapException e) {
			
			System.out.println("The map has the wrong format!");
			
		}catch(FileNotFoundException e) {
			
			System.out.println("File was not found!");
			
		}catch(IOException e) {
			
			System.out.println("process of finding file could not be completed");
			
		}
	}
		
	private MapCell nextCell(MapCell cell) {

				if(cell.isOmniSwitch() || cell.isPowerStation()) {
					for(int x=0;x<=3;x++) { //loops through all the neighbours
						if(cell.getNeighbour(x) != null) {	//to avoid null pointer exceptio	
							//if the current cell is the customers house then it returns the cell at its index and exits the method
							if(cell.getNeighbour(x).isCustomer()) { 
							
								return cell.getNeighbour(x);
						
							//this will check if the neighbouring cell is an omni switch and is not marked
							//it also checks if an omni cell has already been found at the previous indexes. This way the omni cell with the smaller index will always be favored
							}else if(cell.getNeighbour(x).isOmniSwitch() && !cell.getNeighbour(x).isMarked()) {
					
								return cell.getNeighbour(x);
							
							//this checks if the best neighbour is a vertical switch
							//itll only run if the index coheres with the indexes a vertical switch can connect to and if an omni switch is not yet found
							}else if(cell.getNeighbour(x).isVerticalSwitch() && (x==0 || x==2) && !cell.getNeighbour(x).isMarked()) {
							
								return cell.getNeighbour(x);
							
							//this if loop is similar to the one of the vertical switch, refer to that for more info on its conditions	
							}else if(cell.getNeighbour(x).isHorizontalSwitch() && (x==1 || x==3) && !cell.getNeighbour(x).isMarked()) {
							
								return cell.getNeighbour(x);
							
							}
						}					
					}
				
				}else if(cell.isVerticalSwitch()) {
				
					//this for loop will only access indexes 0 and 2 as they are the only ones a vertical switch can access
					for(int x=0; x<4;x+=2) { 
					
						//if the customer cell is one of the neighbours return that index and break out of the loop
						if(cell.getNeighbour(x).isCustomer()) {
						
							return cell.getNeighbour(x);
						
						//if the index is an unmarked omni switch
						}else if(cell.getNeighbour(x).isOmniSwitch() && !cell.getNeighbour(x).isMarked()){ 
						
							return cell.getNeighbour(x);
						
						//will only run if an omni switch has not been found
						}else if(cell.getNeighbour(x).isVerticalSwitch() && !cell.getNeighbour(x).isMarked()) { 
						
							return cell.getNeighbour(x);
						
						}
					
					}
				
				}else if(cell.isHorizontalSwitch()) { //this is the same as the if loop that checks for a vertical switch so refer to that if you have any questions about this loop
				
					//this for loop will only access indexes 0 and 2 as they are the only ones a vertical switch can access
					for(int x=1; x<4;x+=2) {
					
						if(cell.getNeighbour(x).isCustomer()) {
						
							return cell.getNeighbour(x);
						
						}else if(cell.getNeighbour(x).isOmniSwitch() && !cell.getNeighbour(x).isMarked()){
						
							return cell.getNeighbour(x);
						
						}else if(cell.getNeighbour(x).isHorizontalSwitch() && !cell.getNeighbour(x).isMarked()) {
						
							return cell.getNeighbour(x);
						
						}
					}
				}
		return null;
	}

	public static void main (String[] args) {
		
		ShortestPath s = new ShortestPath(args[0]);
		DLList<MapCell> cellList = new DLList<MapCell>();
		boolean destinationFound = false;
		
		cellList.insert(s.cityMap.getStart(), 0);
		MapCell startCell = s.cityMap.getStart();
		startCell.markInList();
		
		while(!cellList.isEmpty() && !destinationFound) {
			
			MapCell currentCell = cellList.getSmallest();
			currentCell.markOutList();
			
			if(currentCell.isCustomer()) {
				destinationFound = true;
				System.out.println("yes");
				System.out.println(cellList.toString());
				break;
			}else{
					MapCell nextBestCell = s.nextCell(currentCell);
					for(int x=0; x<4; x++) {
						if(nextBestCell != null) {
							int distance = 1 + s.cityMap.start.getDistanceToStart();
							if((s.cityMap.start.getDistanceToStart()) > distance) {
								s.cityMap.start.setDistanceToStart(distance);
								cellList.changeValue(nextBestCell, s.cityMap.start.getDistanceToStart());
								nextBestCell.setPredecessor(currentCell);
							}
							int p = s.cityMap.start.getDistanceToStart();
							if(nextBestCell.isMarkedInList() && p<cellList.getDataValue(nextBestCell)) {
								cellList.changeValue(nextBestCell, p);
							}else {
								if(!nextBestCell.isMarkedInList()) {
									cellList.insert(nextBestCell, p);
									nextBestCell.setPredecessor(currentCell);
									System.out.println(cellList.toString());
									nextBestCell.markInList();
								}
							}
						}
						nextBestCell = s.nextCell(currentCell);
					}
				}
			}
		}
}
