package objects.blocked;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import objects.ObjectCategory;
import objects.ObjectType;
import objects.SuperObject;

public class BlockedObject extends SuperObject {
	
	protected ArrayList<BufferedImage> openSprites = new ArrayList<>();
	
	protected ArrayList<SuperObject> objects;
	
	protected int openSpriteCounter = 0;
	protected int openSpriteMaxCounter;
	protected int openSpriteValue = 0;
	protected int openSpriteMaxValue;
	
	protected boolean hasOpenObject;
	protected ObjectType openType;
	
	protected boolean isOpen;
	
	public BlockedObject(ObjectType type, GamePanel gamePanel) {
		super(type, gamePanel);
		
		category = ObjectCategory.BLOCKED;
		
		this.objects = gamePanel.getObjects();
		
		openSpriteMaxCounter = config.getInt("open.open-sprites-animation-counter");
		
		hasOpenObject = config.getBoolean("open.has-open-object");
		
		if(hasOpenObject) {
			openType = ObjectType.valueOf(config.getString("open.open-object-type"));
		}
		
		try{
			for(String path : config.getStringList("open.open-sprites-path")) {
				openSprites.add(ImageIO.read(getClass().getResourceAsStream(path)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		openSpriteMaxValue = openSprites.size()-1;
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		if(!isOpen) {
			super.draw(graphics2d);
			return;
		}
		
		int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
		int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();
		
		if(!(screenX < -gamePanel.getTileSize() || screenY < -gamePanel.getTileSize() || screenX > gamePanel.getWidth() || screenY > gamePanel.getHeight())) {
			graphics2d.drawImage(openSprites.get(openSpriteValue), screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
			
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
	
		updateOpenSpriteCounter();
	}
	
	public void updateOpenSpriteCounter() {
		openSpriteCounter++;
		
		if(openSpriteCounter > openSpriteMaxCounter) {
			openSpriteCounter = 0;
			openSpriteValue++;
			if(openSpriteValue > openSpriteMaxValue) {
				delete();
			}
		}
	}

	private void delete() {
		objects.remove(this);
		
		if(hasOpenObject) {
			objects.add(SuperObject.create(openType, gamePanel, tileX, tileY));
		}
	}
	
	public void setIsOpen(boolean value) {
		isOpen = value;
	}
}