package model;

public class Collection {

	private String id;
	private String name;
	private String description;
	private String dimension;
	private String curatorId;

	public Collection() {
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

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getCuratorId() {
		return curatorId;
	}

	public void setCuratorId(String curatorId) {
		this.curatorId = curatorId;
	}

	@Override
	public String toString() {
		return "Collezione: \nCodiceCollezione: " + id + "\nDenominazione: " + name + "\nDescrizione: " + description
				+ "Dimensione: " + dimension + "\nCodiceCuratore: " + curatorId;
	}

}
