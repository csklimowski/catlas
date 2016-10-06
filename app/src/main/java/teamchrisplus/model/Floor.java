package teamchrisplus.model;

import java.util.List;


public class Floor {
	
	private String name;
	private List<Room> rooms;
	private List<Path> paths;
	
	public Floor(String name, List<Room> rooms, List<Path> paths) {
		this.name = name;
		this.rooms = rooms;
		this.paths = paths;
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

	public List<Path> getPaths() {
		return paths;
	}

	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}
	
	/**
	 * Eventually will find the shortest series of paths between Room start
	 * and Room end and return it in a List. Right now, it just returns a 
	 * list containing only paths.get(0).
	 * 
	 * @param Room start
	 * @param Room end
	 * @return list of Paths
	 */
	public List<Path> findShortestPath(Room start, Room end) {
		return paths.subList(0, 1);
	}
	
}
