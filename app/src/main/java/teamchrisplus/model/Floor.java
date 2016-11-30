package teamchrisplus.model;

import java.util.List;


public class Floor {

	private int _id;
	private String name;
	private List<DBRoom> rooms;
	private FloorGraph graph;
	
	public Floor(String name, List<DBRoom> rooms, FloorGraph graph) {
		this(name, rooms, graph, -1);
	}

	public Floor(String name, List<DBRoom> rooms, FloorGraph graph, int id)
	{
		this.name = name;
		this.rooms = rooms;
		this.graph = graph;
		this._id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DBRoom> getRooms() {
		return rooms;
	}

	public int get_id() { return _id; }

	public void setRooms(List<DBRoom> rooms) {
		this.rooms = rooms;
	}

	public void addRoom(DBRoom room) { this.rooms.add(room); }

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
