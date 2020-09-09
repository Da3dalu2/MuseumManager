package model;

public class Employee {

	String id;
	String name;
	String surname;
	String phone;
	String tipology;

	public Employee() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTipology() {
		return tipology;
	}

	public void setTipology(String tipology) {
		this.tipology = tipology;
	}

	@Override
	public String toString() {
		return "CodiceId: " + id + "\nNome: =" + name + "\nCognome: " + surname + "\nTelefono: " + phone
				+ "\nTipologia: " + tipology;
	}

}
