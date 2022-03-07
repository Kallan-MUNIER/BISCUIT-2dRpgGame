package objects;

public enum ObjectCategory {

	SIMPLE(0),
	PICK_UP(1),
	OPEN(2),
	BLOCKED(3),
	ELEVATOR(4);
	
	private int id;

	ObjectCategory(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}