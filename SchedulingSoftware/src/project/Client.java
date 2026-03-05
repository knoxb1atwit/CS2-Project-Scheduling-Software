package project;

public class Client {
//attributes
	private String name;
	private String phoneNumber;
	private Dentist prefferedDentist;

//constructors
	
	public Client(String name, String phoneNumber, Dentist prefferedDentist) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.prefferedDentist = prefferedDentist;
	}
	
//methods
	public void updatePhoneNumber(String newPhoneNumber) {
		phoneNumber.equals(newPhoneNumber);
	}

	public void setPrefferedDentist(Dentist d) {
		d= prefferedDentist;
	}

	public String getClientInfo() {
		return String.format("%s%n %s%n" + prefferedDentist.getName(), name, phoneNumber); 
	}
	
}
