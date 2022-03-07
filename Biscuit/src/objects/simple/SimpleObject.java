package objects.simple;

import main.GamePanel;
import objects.ObjectCategory;
import objects.ObjectType;
import objects.SuperObject;

public class SimpleObject extends SuperObject {

	public SimpleObject(ObjectType type, GamePanel gamePanel) {
		super(type, gamePanel);
		
		category = ObjectCategory.SIMPLE;
	}
}