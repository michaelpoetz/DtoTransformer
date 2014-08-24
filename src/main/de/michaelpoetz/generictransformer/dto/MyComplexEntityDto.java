package de.michaelpoetz.generictransformer.dto;

public class MyComplexEntityDto {

	// Fields in the Dto have to be named like the ones in the original entity
	private String string;
	// even if you just want an inner feld of the object (see @Dto(field="string"))
	private String entity;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
}
