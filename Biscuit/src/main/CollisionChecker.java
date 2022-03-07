package main;

import java.awt.Rectangle;

import entities.Entity;
import entities.EntityDirection;
import objects.SuperObject;
import tiles.Tile;

public class CollisionChecker {

	private GamePanel gamePanel;
	
	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.getWorldX() + entity.getHitBox().x;
		int entityRightWorldX = entityLeftWorldX + entity.getHitBox().width;
		int entityTopWorldY = entity.getWorldY() + entity.getHitBox().y;
		int entityBottomWorldY = entityTopWorldY + entity.getHitBox().height;
		
		int entityLeftCol = entityLeftWorldX/gamePanel.getTileSize();
		int entityRightCol = entityRightWorldX/gamePanel.getTileSize();
		int entityTopRow = entityTopWorldY/gamePanel.getTileSize();
		int entityBottomRow = entityBottomWorldY/gamePanel.getTileSize();
		
		Tile tile1, tile2;
		
		switch(entity.getDirection()) {
		case DOWN:
			entityBottomRow = (entityBottomWorldY + entity.getSpeed())/gamePanel.getTileSize();
			tile1 = gamePanel.getTileManager().getTile(entityLeftCol, entityBottomRow);
			tile2 = gamePanel.getTileManager().getTile(entityRightCol, entityBottomRow);
			if(tile1.isCollision() || tile2.isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case LEFT:
			entityLeftCol = (entityLeftWorldX - entity.getSpeed())/gamePanel.getTileSize();
			tile1 = gamePanel.getTileManager().getTile(entityLeftCol, entityTopRow);
			tile2 = gamePanel.getTileManager().getTile(entityLeftCol, entityBottomRow);
			if(tile1.isCollision() || tile2.isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case RIGHT:
			entityRightCol = (entityRightWorldX + entity.getSpeed())/gamePanel.getTileSize();
			tile1 = gamePanel.getTileManager().getTile(entityRightCol, entityTopRow);
			tile2 = gamePanel.getTileManager().getTile(entityRightCol, entityBottomRow);
			if(tile1.isCollision() || tile2.isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case UP:
			entityTopRow = (entityTopWorldY - entity.getSpeed())/gamePanel.getTileSize();
			tile1 = gamePanel.getTileManager().getTile(entityLeftCol, entityTopRow);
			tile2 = gamePanel.getTileManager().getTile(entityRightCol, entityTopRow);
			if(tile1.isCollision() || tile2.isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		default:
			break;
		
		}
	}
	
	public void checkTile(Entity entity, EntityDirection direction) {
		int entityLeftWorldX = entity.getWorldX() + entity.getHitBox().x;
		int entityRightWorldX = entityLeftWorldX + entity.getHitBox().width;
		int entityTopWorldY = entity.getWorldY() + entity.getHitBox().y;
		int entityBottomWorldY = entityTopWorldY + entity.getHitBox().height;
		
		int entityLeftCol = entityLeftWorldX/gamePanel.getTileSize();
		int entityRightCol = entityRightWorldX/gamePanel.getTileSize();
		int entityTopRow = entityTopWorldY/gamePanel.getTileSize();
		int entityBottomRow = entityBottomWorldY/gamePanel.getTileSize();
		
		Tile tile1, tile2;
		
		switch(direction) {
		case DOWN:
			entityBottomRow = (entityBottomWorldY + entity.getSpeed())/gamePanel.getTileSize();
			tile1 = gamePanel.getTileManager().getTile(entityLeftCol, entityBottomRow);
			tile2 = gamePanel.getTileManager().getTile(entityRightCol, entityBottomRow);
			if(tile1.isCollision() || tile2.isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case LEFT:
			entityLeftCol = (entityLeftWorldX - entity.getSpeed())/gamePanel.getTileSize();
			tile1 = gamePanel.getTileManager().getTile(entityLeftCol, entityTopRow);
			tile2 = gamePanel.getTileManager().getTile(entityLeftCol, entityBottomRow);
			if(tile1.isCollision() || tile2.isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case RIGHT:
			entityRightCol = (entityRightWorldX + entity.getSpeed())/gamePanel.getTileSize();
			tile1 = gamePanel.getTileManager().getTile(entityRightCol, entityTopRow);
			tile2 = gamePanel.getTileManager().getTile(entityRightCol, entityBottomRow);
			if(tile1.isCollision() || tile2.isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		case UP:
			entityTopRow = (entityTopWorldY - entity.getSpeed())/gamePanel.getTileSize();
			tile1 = gamePanel.getTileManager().getTile(entityLeftCol, entityTopRow);
			tile2 = gamePanel.getTileManager().getTile(entityRightCol, entityTopRow);
			if(tile1.isCollision() || tile2.isCollision()) {
				entity.setCollisionOn(true);
			}
			break;
		default:
			break;
		
		}
	}
	
	public SuperObject checkObject(Entity entity, boolean player) {
		for(SuperObject object : gamePanel.getObjects()) {
			
			Rectangle entityHitBox = (Rectangle) entity.getHitBox().clone();
			Rectangle objectHitBox = (Rectangle) object.getHitBox().clone();
			
			entityHitBox.x += entity.getWorldX();
			entityHitBox.y += entity.getWorldY();
			
			objectHitBox.x += object.getWorldX();
			objectHitBox.y += object.getWorldY();
			
			switch(entity.getDirection()) {
			case DOWN:
				entityHitBox.y += entity.getSpeed();
				break;
			case LEFT:
				entityHitBox.x -= entity.getSpeed();
				break;
			case RIGHT:
				entityHitBox.x += entity.getSpeed();
				break;
			case UP:
				entityHitBox.y -= entity.getSpeed();
				break;
			default:
				break;
			}
			
			if(entityHitBox.intersects(objectHitBox)) {
				if(object.isCollision()) {
					entity.setCollisionOn(true);
				}
				
				if(player) {
					return object;
				}
				else {
					return null;
				}
			}
		}
		
		return null;
	}
	
	public SuperObject checkObject(Entity entity, boolean player, EntityDirection direction) {
		for(SuperObject object : gamePanel.getObjects()) {
			
			Rectangle entityHitBox = (Rectangle) entity.getHitBox().clone();
			Rectangle objectHitBox = (Rectangle) object.getHitBox().clone();
			
			entityHitBox.x += entity.getWorldX();
			entityHitBox.y += entity.getWorldY();
			
			objectHitBox.x += object.getWorldX();
			objectHitBox.y += object.getWorldY();
			
			switch(direction) {
			case DOWN:
				entityHitBox.y += entity.getSpeed();
				break;
			case LEFT:
				entityHitBox.x -= entity.getSpeed();
				break;
			case RIGHT:
				entityHitBox.x += entity.getSpeed();
				break;
			case UP:
				entityHitBox.y -= entity.getSpeed();
				break;
			default:
				break;
			}
			
			if(entityHitBox.intersects(objectHitBox)) {
				if(object.isCollision()) {
					entity.setCollisionOn(true);
				}
				
				if(player) {
					return object;
				}
				else {
					return null;
				}
			}
		}
		
		return null;
	}
}