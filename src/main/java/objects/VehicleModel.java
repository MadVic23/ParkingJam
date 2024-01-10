package objects;


public enum VehicleModel {
	MYCAR, TRUCK, AUTO, NULL;

	@Override
	public String toString() {
		switch(this) {
		case MYCAR: return "car";
		case TRUCK: return "truck";
		case AUTO: return "auto";
		default:
			return "";
		}
	}
}
