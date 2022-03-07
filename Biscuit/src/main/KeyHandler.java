package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;

import entities.EntityDirection;

public class KeyHandler implements KeyListener {
	
	private boolean upPressed, downPressed, leftPressed, rightPressed, debugPressed, speedPressed;
	private AtomicBoolean debugActif = new AtomicBoolean(false);

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		updateKeyState(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		updateKeyState(e.getKeyCode(), false);
	}
	
	public void updateKeyState(int code, boolean isPressed) {
		switch(code) {
		case KeyEvent.VK_Z:
			upPressed = isPressed;
			break;
		case KeyEvent.VK_Q:
			leftPressed = isPressed;
			break;
		case KeyEvent.VK_D:
			rightPressed = isPressed;
			break;
		case KeyEvent.VK_S:
			downPressed = isPressed;
			break;
		case KeyEvent.VK_CONTROL:
			speedPressed = isPressed;
			break;
		case KeyEvent.VK_A:
			checkNewInput(debugPressed, isPressed, this.debugActif);
			debugPressed = isPressed;
			break;
		}
	}
	
	public EntityDirection getDirection() {
		int nb = numberOfDirectionImputPressed();
		
		if(nb == 1) {
			if(upPressed) return EntityDirection.UP;
			if(downPressed) return EntityDirection.DOWN;
			if(rightPressed) return EntityDirection.RIGHT;
			if(leftPressed) return EntityDirection.LEFT;
		}
		else if(nb == 2) {
			if(upPressed && rightPressed) return EntityDirection.UP_RIGHT;
			if(upPressed && leftPressed) return EntityDirection.UP_LEFT;
			if(downPressed && rightPressed) return EntityDirection.DOWN_RIGHT;
			if(downPressed && leftPressed) return EntityDirection.DOWN_LEFT;
		}
		
		return EntityDirection.NONE;
	}
	
	private void checkNewInput(boolean debugPressed, boolean isPressed, AtomicBoolean debugActif) {
		if(!isPressed) return;
		if(debugPressed == isPressed) return;
		
		debugActif.set(!debugActif.get());
	}
	
	public int numberOfDirectionImputPressed() {
		int value = 0;
		if(upPressed) value++;
		if(leftPressed) value++;
		if(rightPressed) value++;
		if(downPressed) value++;
		
		return value;
	}

	public boolean hasKeyPressed() {
		return upPressed || downPressed || leftPressed || rightPressed;
	}

	public boolean isUpPressed() {
		return upPressed;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public boolean isDebugActif() {
		return debugActif.get();
	}

	public boolean isSpeedPressed() {
		return speedPressed;
	}
}