package objects.pick_up;

import main.GamePanel;
import objects.ObjectCategory;
import objects.ObjectType;
import objects.SuperObject;

public class PickUpObject extends SuperObject {

	public PickUpObject(ObjectType type, GamePanel gamePanel) {
		super(type, gamePanel);
		
		category = ObjectCategory.PICK_UP;
	}
}