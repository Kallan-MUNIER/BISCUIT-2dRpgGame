package tiles;

public enum TileType {

	GRASS(0, "/tiles/data/grass.yaml"),
	WALL(1, "/tiles/data/wall.yaml"),
	WATER(2, "/tiles/data/water.yaml");
	
	private int id;
	private String path;

	TileType(int id, String path) {
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