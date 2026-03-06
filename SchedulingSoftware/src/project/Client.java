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
	
	public String getName() {
		return this.name;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public Dentist getPrefferedDentists() {
		return this.prefferedDentist;
	}
	
//methods
	public void updatePhoneNumber(String newPhoneNumber) {
		phoneNumber.equals(newPhoneNumber);
	}

	public void setPrefferedDentist(Dentist d) {
		prefferedDentist=d;
	}

	public String getClientInfo() {
		return String.format("%s%n %s%n" + prefferedDentist.getName(), name, phoneNumber); 
	}
	
}
