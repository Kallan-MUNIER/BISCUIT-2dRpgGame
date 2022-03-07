package main;

import objects.ObjectType;
import objects.SuperObject;

public class AssetSetter {

	private GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setObject() {
		
		gamePanel.getObjects().add(SuperObject.create(ObjectType.TEST_KEY, gamePanel, 3, 12));
		gamePanel.getObjects().add(SuperObject.create(ObjectType.TEST_DOOR, gamePanel, 8, 7));
		gamePanel.getObjects().add(SuperObject.create(ObjectType.TEST_CHEST, gamePanel, 8, 4));
	}
}