package teamchrisplus.model;


import java.util.List;

public class Room {
	
	private String name;
	private List<String> searchTerms;
	
	public Room(String name, List<String> searchTerms) {
		this.name = name;
		this.searchTerms = searchTerms;
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
	
}
