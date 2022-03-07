package tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

public class Tile {

	private ArrayList<BufferedImage> sprites = new ArrayList<>();
	
	protected int spriteCounter = 0;
	protected int spriteMaxCounter;
	protected int spriteValue = 0;
	protected int spriteMaxValue;
	
	private TileType type;
	private File file;
	private YamlConfiguration config;
	
	private boolean hasCollision;
	private double speedModifier;
	
	public Tile(TileType type) {
		
		this.type = type;
		
		file = new File(getClass().getResource(type.getPath()).getFile());
		config = YamlConfiguration.loadConfiguration(file);
		
		hasCollision = config.getBoolean("global.collision");
		speedModifier = config.getDouble("global.speed-modifier");
		
		spriteMaxCounter = config.getInt("global.sprites-animation-counter");
			
		try{
			for(String path : config.getStringList("global.sprites-path")) {
				sprites.add(ImageIO.read(getClass().getResourceAsStream(path)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		spriteMaxValue = sprites.size()-1;
	}

	public BufferedImage getImage() {
		return sprites.get(spriteValue);
	}

	public boolean isCollision() {
		return hasCollision;
	}

	public double getSpeedModifier() {
		return speedModifier;
	}
	
	public String toString() {
		return type.name() + " speed_modifier=" + speedModifier;
	}
	
	public void updateSpriteCounter() {
		spriteCounter++;
		
		if(spriteCounter > spriteMaxCounter) {
			spriteCounter = 0;
			spriteValue++;
			if(spriteValue > spriteMaxValue) {
				spriteValue = 0;
			}
		}
	}
}