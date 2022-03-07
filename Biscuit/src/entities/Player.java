package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import objects.SuperObject;

public class Player extends Entity {

	private GamePanel gamePanel;
	private KeyHandler keyHandler;
	
	private final int screenX;
	private final int screenY;
	
	private double stamina;
	private double maxStamina;
	private int staminaWaitNumber;
	private int staminaWaitMax;
	
	private int currentSpeed;
	
	private int key = 0;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		super(gamePanel, 2, 10, new Rectangle(gamePanel.getTileSize()/4, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2, gamePanel.getTileSize()/2));
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
		
		screenX = gamePanel.getScreenWidth()/2;
		screenY = gamePanel.getScreenHeight()/2;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gamePanel.getTileSize()*3;
		worldY = gamePanel.getTileSize()*10;
		defaultSpeed = 4;
		direction = EntityDirection.DOWN;
		
		maxStamina = 180;
		stamina = maxStamina;
		
		staminaWaitMax = 90;
		staminaWaitNumber = 0;
	}
	
	public void getPlayerImage() {
		addImage(EntityDirection.DOWN, "/player/characters.png", 0*48, 0*48, 48, 48);
		addImage(EntityDirection.DOWN, "/player/characters.png", 1*48, 0*48, 48, 48);
		addImage(EntityDirection.DOWN, "/player/characters.png", 2*48, 0*48, 48, 48);
		
		addImage(EntityDirection.LEFT, "/player/characters.png", 0*48, 1*48, 48, 48);
		addImage(EntityDirection.LEFT, "/player/characters.png", 1*48, 1*48, 48, 48);
		addImage(EntityDirection.LEFT, "/player/characters.png", 2*48, 1*48, 48, 48);
		
		addImage(EntityDirection.RIGHT, "/player/characters.png", 0*48, 2*48, 48, 48);
		addImage(EntityDirection.RIGHT, "/player/characters.png", 1*48, 2*48, 48, 48);
		addImage(EntityDirection.RIGHT, "/player/characters.png", 2*48, 2*48, 48, 48);
		
		addImage(EntityDirection.UP, "/player/characters.png", 0*48, 3*48, 48, 48);
		addImage(EntityDirection.UP, "/player/characters.png", 1*48, 3*48, 48, 48);
		addImage(EntityDirection.UP, "/player/characters.png", 2*48, 3*48, 48, 48);
	}
	
	public void update() {
		collisionOn = false;
		
		currentSpeed = speed;
		
		if(keyHandler.isSpeedPressed() && stamina > 0 && keyHandler.hasKeyPressed()) {
			currentSpeed*=2;
			stamina--;
			staminaWaitNumber = staminaWaitMax;
		}
		else if(stamina < maxStamina) {
			if(staminaWaitNumber > 0) {
				staminaWaitNumber--;
			}
			else {
				stamina+=1;
			}
		}

		if(keyHandler.isUpPressed()) {
			direction = EntityDirection.UP;
			collisionCheck();
			if(!collisionOn) worldY -= currentSpeed;
		}
		else if(keyHandler.isDownPressed()) {
			direction = EntityDirection.DOWN;
			collisionCheck();
			if(!collisionOn) worldY += currentSpeed;
		}
		else if(keyHandler.isLeftPressed()) {
			direction = EntityDirection.LEFT;
			collisionCheck();
			if(!collisionOn) worldX -= currentSpeed;
		}
		else if(keyHandler.isRightPressed()) {
			direction = EntityDirection.RIGHT;
			collisionCheck();
			if(!collisionOn) worldX += currentSpeed;
		}
		
		calculTile();
	}
	
	private void pickUpObject(SuperObject object) {
		
		switch(object.getCategory()) {
		case PICK_UP:
			gamePanel.getObjects().remove(object);
			key++;
			gamePanel.getSound().setFile(1);
			gamePanel.getSound().play();
			break;
		case BLOCKED:
			if(key > 0) {
				gamePanel.getObjects().remove(object);
				key--;
				gamePanel.getSound().setFile(2);
				gamePanel.getSound().play();
			}
			break;
		case ELEVATOR:
			break;
		case OPEN:
			break;
		default:
			break;
		}
	}
	
	private void collisionCheck() {
		SuperObject object = gamePanel.getCollisionChecker().checkObject(this, true);
		if(object != null) {
			pickUpObject(object);
		}
		
		gamePanel.getCollisionChecker().checkTile(this);
	}

	public void draw(Graphics2D graphics2d) {
		
		BufferedImage image = getImage(direction, spriteValue);

		if(stamina < maxStamina) {
			graphics2d.setColor(new Color(255, 255, 255, 200));
			graphics2d.fillRect(screenX, screenY - 10, (int)(stamina/maxStamina*gamePanel.getTileSize()), 5);
		}
		
		if(image != null) {
			graphics2d.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
		}
		else {
			graphics2d.setColor(Color.WHITE);
			graphics2d.drawRect(worldX, worldY, gamePanel.getTileSize(), gamePanel.getTileSize());
		}
		
		if(keyHandler.hasKeyPressed()) {
			updateSpriteCounter();
		}
		
		if(keyHandler.isDebugActif()) {
			Font font = new Font("Serif", Font.PLAIN, 32);
			 
			graphics2d.setFont(font);
			graphics2d.setColor(new Color(255, 255, 255, 220));

			graphics2d.drawString("Player debug:", 10, 50);
			graphics2d.drawString("x: " + tileX, 60, 85);
			graphics2d.drawString("y: " + tileY, 60, 120);
			graphics2d.drawString("tile_info: " + currentTile, 60, 155);
			
			graphics2d.fillRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
			
			graphics2d.setColor(new Color(255, 0, 0, 255));
			graphics2d.fillRect(-1 + screenX + hitBox.x + hitBox.width/2, -1 + screenY + hitBox.y + hitBox.height/2, 3, 3);
		}
	}
	
	@Override
	public int getSpeed() {
		return currentSpeed;
	}

	public int getScreenX() {
		return screenX;
	}

	public int getScreenY() {
		return screenY;
	}
}