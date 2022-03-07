package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import objects.SuperObject;
import objects.blocked.BlockedObject;

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
	
	private boolean move = false;
	
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
		defaultSpeed = gamePanel.getTileSize()/16;
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
		
		addImage(EntityDirection.DOWN_LEFT, "/player/characters.png", 0*48, 1*48, 48, 48);
		addImage(EntityDirection.DOWN_LEFT, "/player/characters.png", 1*48, 1*48, 48, 48);
		addImage(EntityDirection.DOWN_LEFT, "/player/characters.png", 2*48, 1*48, 48, 48);
		
		addImage(EntityDirection.DOWN_RIGHT, "/player/characters.png", 0*48, 2*48, 48, 48);
		addImage(EntityDirection.DOWN_RIGHT, "/player/characters.png", 1*48, 2*48, 48, 48);
		addImage(EntityDirection.DOWN_RIGHT, "/player/characters.png", 2*48, 2*48, 48, 48);
		
		addImage(EntityDirection.LEFT, "/player/characters.png", 0*48, 1*48, 48, 48);
		addImage(EntityDirection.LEFT, "/player/characters.png", 1*48, 1*48, 48, 48);
		addImage(EntityDirection.LEFT, "/player/characters.png", 2*48, 1*48, 48, 48);
		
		addImage(EntityDirection.RIGHT, "/player/characters.png", 0*48, 2*48, 48, 48);
		addImage(EntityDirection.RIGHT, "/player/characters.png", 1*48, 2*48, 48, 48);
		addImage(EntityDirection.RIGHT, "/player/characters.png", 2*48, 2*48, 48, 48);
		
		addImage(EntityDirection.UP, "/player/characters.png", 0*48, 3*48, 48, 48);
		addImage(EntityDirection.UP, "/player/characters.png", 1*48, 3*48, 48, 48);
		addImage(EntityDirection.UP, "/player/characters.png", 2*48, 3*48, 48, 48);
		
		addImage(EntityDirection.UP_LEFT, "/player/characters.png", 0*48, 1*48, 48, 48);
		addImage(EntityDirection.UP_LEFT, "/player/characters.png", 1*48, 1*48, 48, 48);
		addImage(EntityDirection.UP_LEFT, "/player/characters.png", 2*48, 1*48, 48, 48);
		
		addImage(EntityDirection.UP_RIGHT, "/player/characters.png", 0*48, 2*48, 48, 48);
		addImage(EntityDirection.UP_RIGHT, "/player/characters.png", 1*48, 2*48, 48, 48);
		addImage(EntityDirection.UP_RIGHT, "/player/characters.png", 2*48, 2*48, 48, 48);
	}
	
	public void update() {
		
		EntityDirection tmpDirection = keyHandler.getDirection();
		if(tmpDirection == EntityDirection.NONE) {
			move = false;
		}
		else {
			direction = tmpDirection;
			move = true;
		}
		
		collisionOn = false;
		
		currentSpeed = speed;
		
		if(move && stamina > 0 && keyHandler.isSpeedPressed()) {
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
		
		if(tmpDirection == EntityDirection.NONE) {
			return;
		}
		
		switch(direction) {
		case DOWN:
			collisionCheck();
			if(!collisionOn) worldY += currentSpeed;
			break;
		case DOWN_LEFT:
			currentSpeed/=Math.sqrt(2);
			collisionCheck(EntityDirection.DOWN);
			if(!collisionOn) worldY += currentSpeed;
			collisionOn = false;
			collisionCheck(EntityDirection.LEFT);
			if(!collisionOn) worldX -= currentSpeed;
			break;
		case DOWN_RIGHT:
			currentSpeed/=Math.sqrt(2);
			collisionCheck(EntityDirection.DOWN);
			if(!collisionOn) worldY += currentSpeed;
			collisionOn = false;
			collisionCheck(EntityDirection.RIGHT);
			if(!collisionOn) worldX += currentSpeed;
			break;
		case LEFT:
			collisionCheck();
			if(!collisionOn) worldX -= currentSpeed;
			break;
		case NONE:
			break;
		case RIGHT:
			collisionCheck();
			if(!collisionOn) worldX += currentSpeed;
			break;
		case UP:
			collisionCheck();
			if(!collisionOn) worldY -= currentSpeed;
			break;
		case UP_LEFT:
			currentSpeed/=Math.sqrt(2);
			collisionCheck(EntityDirection.UP);
			if(!collisionOn) worldY -= currentSpeed;
			collisionOn = false;
			collisionCheck(EntityDirection.LEFT);
			if(!collisionOn) worldX -= currentSpeed;
			break;
		case UP_RIGHT:
			currentSpeed/=Math.sqrt(2);
			collisionCheck(EntityDirection.UP);
			if(!collisionOn) worldY -= currentSpeed;
			collisionOn = false;
			collisionCheck(EntityDirection.RIGHT);
			if(!collisionOn) worldX += currentSpeed;
			break;
		}
		
		calculTile();
	}

	private void pickUpObject(SuperObject object) {
		
		switch(object.getCategory()) {
		case PICK_UP:
			gamePanel.getObjects().remove(object);
			key++;
			gamePanel.getSound().setFile(1, 1F);
			gamePanel.getSound().play();
			break;
		case BLOCKED:
			BlockedObject blockedObject = (BlockedObject) object;
			if(key > 0) {
				blockedObject.setIsOpen(true);
				key--;
				gamePanel.getSound().setFile(2, 1F);
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
	
	private void collisionCheck(EntityDirection direction) {
		SuperObject object = gamePanel.getCollisionChecker().checkObject(this, true, direction);
		if(object != null) {
			pickUpObject(object);
		}
		
		gamePanel.getCollisionChecker().checkTile(this, direction);
	}

	public void draw(Graphics2D graphics2d) {
		
		BufferedImage image = getImage(direction, spriteValue);

		if(stamina < maxStamina && stamina > 0) {		
			graphics2d.setColor(new Color(70, 180, 255, 220));
			graphics2d.fillRect(screenX, screenY - gamePanel.getTileSize()/8, (int)(stamina/maxStamina*gamePanel.getTileSize()), gamePanel.getTileSize()/16);
			graphics2d.setColor(new Color(90, 230, 255, 230));
			graphics2d.drawRect(screenX, screenY - gamePanel.getTileSize()/8, (int)(stamina/maxStamina*gamePanel.getTileSize()), gamePanel.getTileSize()/16);
		}
		
		if(image != null) {
			graphics2d.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
		}
		else {
			graphics2d.setColor(Color.WHITE);
			graphics2d.drawRect(screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize());
		}
		
		if(move) {
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