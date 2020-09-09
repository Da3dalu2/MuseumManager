package model;

public class Artwork {

	String id;
	String name;
	String conservationState;
	String material;
	String tecnique;
	String unit;
	String height;
	String width;
	String depth;
	String chronologyId;
	String excavationId;
	String archivistId;
	String currentCollectionId;

	public Artwork() {
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

	public String getConservationState() {
		return conservationState;
	}

	public void setConservationState(String conservationState) {
		this.conservationState = conservationState;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getTecnique() {
		return tecnique;
	}

	public void setTecnique(String tecnique) {
		this.tecnique = tecnique;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getChronologyId() {
		return chronologyId;
	}

	public void setChronologyId(String chronologyId) {
		this.chronologyId = chronologyId;
	}

	public String getExcavationId() {
		return excavationId;
	}

	public void setExcavationId(String excavationId) {
		this.excavationId = excavationId;
	}

	public String getArchivistId() {
		return archivistId;
	}

	public void setArchivistId(String archivistId) {
		this.archivistId = archivistId;
	}

	public String getCurrentCollectionId() {
		return currentCollectionId;
	}

	public void setCurrentCollectionId(String currentCollectionId) {
		this.currentCollectionId = currentCollectionId;
	}

	@Override
	public String toString() {
		return "CodiceArchivio: " + id + "\nDefinizione: " + name + "\nStato di conservazione: " + conservationState
				+ "\nMateria: " + material + "\nTecnica: " + tecnique + "\nUnità di misura: " + unit + "\nAltezza: "
				+ height + "\nLarghezza: " + width + "\nProfondità: " + depth + "\nIdentificativo cronologia: "
				+ chronologyId + "\nCodice scavo: " + excavationId + "\nCodice archivista: " + archivistId
				+ "\nCodice collezione corrente: " + currentCollectionId + "]";
	}

}
