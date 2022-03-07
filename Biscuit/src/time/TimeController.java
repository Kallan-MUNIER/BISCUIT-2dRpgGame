package time;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class TimeController {

	private final static int MAX_OPACITY = 140;
	
	private int timeValue = 0;
	private int timeMaxValue;
	
	private int dayTime;
	private int nightTime;
	private int dayToNightTime;
	private int nightToDayTime;
	
	private TimeState state;
	
	public TimeController(TimeState defaultState, int dayTime, int nightTime, int dayToNightTime, int nightToDayTime) {
		state = defaultState;
		
		this.dayTime = dayTime;
		this.nightTime = nightTime;
		this.dayToNightTime = dayToNightTime;
		this.nightToDayTime = nightToDayTime;
	}
	
	public void draw(Graphics2D graphics2d, GamePanel gamePanel) {
		
		if(gamePanel.getKeyHandler().isDebugActif()) {
			Font font = new Font("Serif", Font.PLAIN, 32);
			 
			graphics2d.setFont(font);
			graphics2d.setColor(new Color(255, 255, 255, 220));

			graphics2d.drawString("TimeController debug:", 10, 190);
			graphics2d.drawString("value: " + timeValue + "/" + timeMaxValue, 60, 225);
			graphics2d.drawString("state: " + state.name(), 60, 260);
		}
		
		switch(state) {
		case DAY:
			graphics2d.setColor(new Color(0, 0, 0, 0));
			break;
		case DAY_TO_NIGHT:
			int opacityValue = (int)((double)MAX_OPACITY*((double)timeValue/timeMaxValue));
			graphics2d.setColor(new Color(0, 0, 0, opacityValue));
			break;
		case NIGHT:
			graphics2d.setColor(new Color(0, 0, 0, MAX_OPACITY));
			break;
		case NIGHT_TO_DAY:
			opacityValue = (int)((double)MAX_OPACITY-(MAX_OPACITY*((double)timeValue/timeMaxValue)));
			graphics2d.setColor(new Color(0, 0, 0, opacityValue));
			break;
		}
		
		graphics2d.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
		
		updateValue();
	}
	
	public void updateValue() {
		timeValue++;
		
		if(timeValue > timeMaxValue) {
			timeValue = 0;
			state = state.next();
			switch(state) {
			case DAY:
				timeMaxValue = dayTime;
				break;
			case DAY_TO_NIGHT:
				timeMaxValue = dayToNightTime;
				break;
			case NIGHT:
				timeMaxValue = nightTime;
				break;
			case NIGHT_TO_DAY:
				timeMaxValue = nightToDayTime;
				break;
			}
		}
	}
}