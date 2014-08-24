package de.michaelpoetz.generictransformer.entity;

public class MyEntity {

	private int id;

	private String string;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MyEntity(int id, String string2) {
		this.id = id;
		this.string = string2;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
}
