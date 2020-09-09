package model;

public class Exposition {

	private String id;
	private String name;
	private String description;
	private String beginDate;
	private String endDate;
	private String curatorId;

	public Exposition() {
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

	public String getCuratorId() {
		return curatorId;
	}

	public void setCuratorId(String curatorId) {
		this.curatorId = curatorId;
	}

	@Override
	public String toString() {
		return "CodiceMostra: " + id + "\nDenominazione: " + name + "\nDescrizione: " + description + "\nDataInizio: "
				+ beginDate + "\nDataFine: " + endDate + "\nCodiceCuratore: " + curatorId;
	}

}
