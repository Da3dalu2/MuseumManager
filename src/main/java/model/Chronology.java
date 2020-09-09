package model;

public class Chronology {

	private String id;
	private String referenceSlot;
	private String chronologicalFraction;

	public Chronology() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReferenceSlot() {
		return referenceSlot;
	}

	public void setReferenceSlot(String referenceSlot) {
		this.referenceSlot = referenceSlot;
	}

	public String getChronologicalFraction() {
		return chronologicalFraction;
	}

	public void setChronologicalFraction(String chronologicalFraction) {
		this.chronologicalFraction = chronologicalFraction;
	}

	@Override
	public String toString() {
		return "IdCronologia: " + id + "\nFascia di riferimento: " + referenceSlot + "\n Frazione cronologica: "
				+ chronologicalFraction;
	}

}
