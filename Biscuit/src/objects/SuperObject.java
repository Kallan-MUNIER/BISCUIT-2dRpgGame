package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import main.GamePanel;
import main.Test;
import objects.blocked.BlockedObject;
import objects.open.OpenObject;
import objects.pick_up.PickUpObject;

public class SuperObject {

	protected ArrayList<BufferedImage> sprites = new ArrayList<>();
	
	protected int spriteCounter = 0;
	protected int spriteMaxCounter;
	protected int spriteValue = 0;
	protected int spriteMaxValue;
	
	protected String name;
	protected boolean hasCollision;
	protected int worldX, worldY;
	protected Rectangle hitBox;
	protected GamePanel gamePanel;
	
	protected ObjectCategory category;
	protected ObjectType type;
	protected File file;
	protected YamlConfiguration config;
	
	public SuperObject(ObjectType type, GamePanel gamePanel) {
		this.type = type;
		this.gamePanel = gamePanel;
		
		file = new File(getClass().getResource(type.getPath()).getFile());
		config = YamlConfiguration.loadConfiguration(file);
		
		name = config.getString("global.name");
		
		hasCollision = config.getBoolean("global.collision");
		
		int hitBoxX = gamePanel.getTileSize()/config.getInt("global.hit-box.x");
		int hitBoxY = gamePanel.getTileSize()/config.getInt("global.hit-box.y");
		int hitBoxWidth = gamePanel.getTileSize()/config.getInt("global.hit-box.width");
		int hitBoxHeight = gamePanel.getTileSize()/config.getInt("global.hit-box.height");
		
		spriteMaxCounter = config.getInt("global.sprites-animation-counter");
		
		hitBox = new Rectangle(hitBoxX, hitBoxY, hitBoxWidth, hitBoxHeight);
		
		try{
			for(String path : config.getStringList("global.sprites-path")) {
				sprites.add(ImageIO.read(getClass().getResourceAsStream(path)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		spriteMaxValue = sprites.size()-1;
	}
	
	public void draw(Graphics2D graphics2d) {
		int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
		int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();
		
		if(!(screenX < -gamePanel.getTileSize() || screenY < -gamePanel.getTileSize() || screenX > gamePanel.getWidth() || screenY > gamePanel.getHeight())) {
			graphics2d.drawImage(sprites.get(spriteValue), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
			
			if(gamePanel.getKeyHandler().isDebugActif()) {
				
				if(hasCollision) {
					graphics2d.setColor(new Color(255, 50, 50, 220));
				}
				else {
					graphics2d.setColor(new Color(50, 255, 50, 220));
				}
				
				graphics2d.setStroke(new BasicStroke(3.0f));
				graphics2d.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
			}
		}
		
		updateSpriteCounter();
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
	
	public static SuperObject create(ObjectType type, GamePanel gamePanel, int tileX, int tileY) {
		
		File file = new File(new Test().getClass().getResource(type.getPath()).getFile());
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		ObjectCategory category = ObjectCategory.valueOf(config.getString("global.category"));
		
		switch(category) {
		case BLOCKED:
			BlockedObject blockedObject = new BlockedObject(type, gamePanel);
			blockedObject.worldX = tileX*gamePanel.getTileSize();
			blockedObject.worldY = tileY*gamePanel.getTileSize();
			return blockedObject;
		case ELEVATOR:
			break;
		case OPEN:
			OpenObject openObject = new OpenObject(type, gamePanel);
			openObject.worldX = tileX*gamePanel.getTileSize();
			openObject.worldY = tileY*gamePanel.getTileSize();
			return openObject;
		case PICK_UP:
			PickUpObject pickUpObject = new PickUpObject(type, gamePanel);
			pickUpObject.worldX = tileX*gamePanel.getTileSize();
			pickUpObject.worldY = tileY*gamePanel.getTileSize();
			return pickUpObject;
		}
		
		return null;
	}
	
	public void addSprite(BufferedImage image) {
		sprites.add(image);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isCollision() {
		return hasCollision;
	}
	
	public void setCollision(boolean hasCollision) {
		this.hasCollision = hasCollision;
	}
	
	public int getWorldX() {
		return worldX;
	}
	
	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}
	
	public int getWorldY() {
		return worldY;
	}
	
	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public ObjectType getType() {
		return type;
	}

	public ObjectCategory getCategory() {
		return category;
	}
}