package project;

public class AppointmentType {
//attributes
	private String appointmentName;
	private Integer duration;

//Constructors

	public AppointmentType(String appointmentName, Integer duration) {
		this.appointmentName = appointmentName;
		this.duration = duration;
	}
	
	public String getAppointmentName() {
		return this.appointmentName;
	}
	
	public Integer getDuration() {
		return this.duration;
	}
//Methods
	public int getDuration(int time) {
		if (time == 1) {
			duration.equals(1);
		} else if (time == 2) {
			duration.equals(2);
		}
		return duration;
	}

	public String getAppointmentName() {
		if (duration == 1) {
			return "Tooth Cleaning";
		}
		return "Cavity Filling";
	}

}
