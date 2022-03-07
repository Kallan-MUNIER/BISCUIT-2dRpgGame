package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import main.GamePanel;
import tiles.Tile;

public class Entity {
	
	protected GamePanel gamePanel;

	protected int worldX;
	protected int worldY;
	
	protected int tileX;
	protected int tileY;
	protected Tile currentTile;
	
	protected int defaultSpeed;
	protected int speed;
	
	protected HashMap<EntityDirection, ArrayList<BufferedImage>> sprites = new HashMap<>();
	
	protected EntityDirection direction;
	
	protected int spriteCounter = 0;
	protected int spriteMaxCounter;
	protected int spriteValue = 0;
	protected int spriteMaxValue;
	
	protected Rectangle hitBox;
	protected int hitBoxCenterX;
	protected int hitBoxCenterY;
	protected boolean collisionOn = false;
	
	public Entity(GamePanel gamePanel, int spriteMaxValue, int spriteMaxCounter, Rectangle hitBox) {
		
		this.gamePanel = gamePanel;
		this.spriteMaxCounter = spriteMaxCounter;
		this.spriteMaxValue = spriteMaxValue;
		this.hitBox = hitBox;
		
		for(EntityDirection direction : EntityDirection.values()) {
			sprites.put(direction, new ArrayList<>());
		}
		
		calculTile();
	}

	public BufferedImage getImage(EntityDirection direction, int spriteNumber) {
		ArrayList<BufferedImage> sprites = this.sprites.get(direction);
		
		if(spriteNumber < sprites.size()) {
			return sprites.get(spriteNumber);
		}
		
		return null;
	}
	
	public void addImage(EntityDirection direction, String path) {
		try {
			this.sprites.get(direction).add(ImageIO.read(getClass().getResourceAsStream(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addImage(EntityDirection direction, String path, int x, int y, int width, int height) {
		try {
			this.sprites.get(direction).add(ImageIO.read(getClass().getResourceAsStream(path)).getSubimage(x, y, width, height));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public void calculTile() {
		
		hitBoxCenterX = (int)((worldX + hitBox.x + hitBox.width/2)/gamePanel.getTileSize());
		hitBoxCenterY = (int)((worldY + hitBox.y + hitBox.height/2)/gamePanel.getTileSize());
		
		currentTile = gamePanel.getTileManager().getTile(hitBoxCenterX, hitBoxCenterY);
		
		double doubleX = (double)worldX/gamePanel.getTileSize();
		double doubleY = (double)worldY/gamePanel.getTileSize();
		
		doubleX = doubleX > 0 ? doubleX +0.5 : doubleX -0.5;
		doubleY = doubleY > 0 ? doubleY +0.5 : doubleY -0.5;
		
		tileX = (int)doubleX;
		tileY = (int)doubleY;
		
		speed = (int)(defaultSpeed*currentTile.getSpeedModifier());
	}

	public int getWorldX() {
		return worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public int getSpeed() {
		return speed;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public EntityDirection getDirection() {
		return direction;
	}

	public boolean isCollisionOn() {
		return collisionOn;
	}

	public void setCollisionOn(boolean collisionOn) {
		this.collisionOn = collisionOn;
	}
}