package time;

public enum TimeState {

	NIGHT,
	NIGHT_TO_DAY,
	DAY,
	DAY_TO_NIGHT;
	
	public TimeState next() {
		switch(this) {
		case DAY:
			return DAY_TO_NIGHT;
		case DAY_TO_NIGHT:
			return NIGHT;
		case NIGHT:
			return NIGHT_TO_DAY;
		case NIGHT_TO_DAY:
			return DAY;
		}
		return null;
	}
}