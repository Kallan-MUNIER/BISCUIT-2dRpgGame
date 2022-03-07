package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;

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
	
	private void checkNewInput(boolean debugPressed, boolean isPressed, AtomicBoolean debugActif) {
		if(!isPressed) return;
		if(debugPressed == isPressed) return;
		
		debugActif.set(!debugActif.get());
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