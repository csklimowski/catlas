package teamchrisplus.model;


import android.graphics.Rect;

import java.util.List;

public class Room {
	
	private String name;
	private List<String> searchTerms;
	private Rect roomRect;

	public Room(String name, List<String> searchTerms)
	{
		this(name, searchTerms, new Rect(0, 0, 0, 0));
	}

	public Room(String name, List<String> searchTerms, Rect roomRect)
	{
		this.name = name;
		this.searchTerms = searchTerms;
		this.roomRect = roomRect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(List<String> searchTerms) {
		this.searchTerms = searchTerms;
	}

	public Rect getRoomRect(){
		return roomRect;
	}
	
}
