package objects.blocked;

import main.GamePanel;
import objects.ObjectCategory;
import objects.ObjectType;
import objects.SuperObject;

public class BlockedObject extends SuperObject {

	public BlockedObject(ObjectType type, GamePanel gamePanel) {
		super(type, gamePanel);
		
		category = ObjectCategory.BLOCKED;
	}
}