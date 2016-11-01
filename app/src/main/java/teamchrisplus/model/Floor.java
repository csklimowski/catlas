package teamchrisplus.model;

import java.util.List;
import java.util.Stack;


public class Floor {
	
	private String name;
	private List<Room> rooms;
	private FloorGraph graph;
	
	public Floor(String name, List<Room> rooms, FloorGraph graph) {
		this.name = name;
		this.rooms = rooms;
		this.graph = graph;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public void addRoom(Room room) { this.rooms.add(room); }

	public void findShortestPath(FloorNode source, FloorNode destination) {
		graph.initPaths(source, destination);
		/*Stack<FloorNode> path = new Stack<FloorNode>();
		FloorNode currentNode = destination;
		while(currentNode != null) {
			path.push(currentNode);
			currentNode = currentNode.getPrevNode();
		}

		return path;*/
	}

    public FloorNode findNode(int x, int y) {
        for(FloorNode node : graph.getNodes()) {
            if (node.hasCoordinates(x, y))
                return node;
        }

        return null;
    }
}
