package teamchrisplus.model;

public class Path {
	
	private String id;
	private Room source, destination;
	private int distance;
	
	
	public Path(String id, Room source, Room destination, int distance) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Room getSource() {
		return source;
	}


	public void setSource(Room source) {
		this.source = source;
	}


	public Room getDestination() {
		return destination;
	}


	public void setDestination(Room destination) {
		this.destination = destination;
	}


	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}

}
