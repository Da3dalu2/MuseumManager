package model;

public class Excavation {

	private String id;
	private String name;
	private String yearRetrival;
	private String placeRetrival;

	public Excavation() {
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

	public String getYearRetrival() {
		return yearRetrival;
	}

	public void setYearRetrival(String yearRetrival) {
		this.yearRetrival = yearRetrival;
	}

	public String getPlaceRetrival() {
		return placeRetrival;
	}

	public void setPlaceRetrival(String placeRetrival) {
		this.placeRetrival = placeRetrival;
	}

	@Override
	public String toString() {
		return "CodiceScavo: " + id + "\nDenominazione: " + name + "\nAnno di reperimento: " + yearRetrival
				+ "\nLuogo di reperimento: " + placeRetrival;
	}

}
