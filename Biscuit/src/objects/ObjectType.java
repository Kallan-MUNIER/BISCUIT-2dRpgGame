package objects;

public enum ObjectType {

	KEY(-1, "/objects/data/test_key.yaml"),
	DOOR(-2, "/objects/data/test_door.yaml"),
	CHEST(-3, "/objects/data/test_chest.yaml"),
	OPEN_DOOR(-4, "/objects/data/test_open_door.yaml"),
	TREE(-5, "/objects/data/tree.yaml");
	
	private int id;
	private String path;

	ObjectType(int id, String path) {
		this.id = id;
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public String getPath() {
		return path;
	}
}