package teamchrisplus.model;

import java.util.List;

public class Building {

	private int _id;
	private String name;
	private List<Floor> floors;
	private int activeFloor;
	
	public Building(String name, List<Floor> floors) {
		this.name = name;
		this.floors = floors;
		this.activeFloor = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Floor> getFloors() {
		return floors;
	}

	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	public int getActiveFloor() {
		return activeFloor;
	}

	public void setActiveFloor(int activeFloor) {
		this.activeFloor = activeFloor;
	}

	public int get_id() { return _id; }

}
