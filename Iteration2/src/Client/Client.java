package Client;

public class Client{
	String name;
	String surname;
	String email;
	String phoneNumber;

	public Client(String clientName, String clientSurname, String clientEmail, String clientPhoneNumber){
		name = clientName;
		surname = clientSurname;
		email = clientEmail;
		phoneNumber = clientPhoneNumber;
	}

	void printClientDetails(){
		System.out.println("Client details: " + this.name + " " + this.surname + ", " + this.email);
	}

	@Override
	public String toString() {
		return name + "," + surname + "," + email + "," + phoneNumber;
	}
}