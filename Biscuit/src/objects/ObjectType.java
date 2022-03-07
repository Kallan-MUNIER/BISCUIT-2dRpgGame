package objects;

public enum ObjectType {

	TEST_KEY(0, "/objects/data/test_key.yaml"),
	TEST_DOOR(1, "/objects/data/test_door.yaml"),
	TEST_CHEST(2, "/objects/data/test_chest.yaml");
	
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