package objects.open;

import main.GamePanel;
import objects.ObjectCategory;
import objects.ObjectType;
import objects.SuperObject;

public class OpenObject extends SuperObject {

	public OpenObject(ObjectType type, GamePanel gamePanel) {
		super(type, gamePanel);
		
		category = ObjectCategory.OPEN;
	}
}