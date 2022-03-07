package objects;

public enum ObjectCategory {

	PICK_UP(0),
	OPEN(1),
	BLOCKED(2),
	ELEVATOR(3);
	
	private int id;

	ObjectCategory(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}