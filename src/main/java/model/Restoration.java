package model;

public class Restoration {

	String artworkId;
	String id;
	String description;
	String beginDate;
	String endDate;

	public Restoration() {
	}

	public String getArtworkId() {
		return artworkId;
	}

	public void setArtworkId(String artworkId) {
		this.artworkId = artworkId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Codice opera in restauro: " + artworkId + "\nCodice restauro: " + id + "\nDescrizione: " + description
				+ "\nData di inizio: " + beginDate + "\nData di fine: " + endDate;
	}

}
