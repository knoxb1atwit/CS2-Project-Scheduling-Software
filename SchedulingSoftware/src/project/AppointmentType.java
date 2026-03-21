package project;

public class AppointmentType {
//attributes
	private String appointmentName;
	private int duration;

//Constructors
	public AppointmentType(String appointmentName, int duration) {
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

	public String toString() {
	    return appointmentName + " (" + duration + " min)";
	}
	

}
