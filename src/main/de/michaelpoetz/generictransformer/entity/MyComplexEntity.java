package de.michaelpoetz.generictransformer.entity;

import de.michaelpoetz.generictransformer.annotation.Dto;

public class MyComplexEntity {

	private String string;

	/*
	 * Keep in mind that MyEntity is just a placeholder for other objects.
	 * These could be of totally different types.
	 */

	@Dto(field = "string")
	// Demonstrates the use if someone wants special fields in his dto
	private MyEntity entity;

	@Dto(field = "id")
	// Demonstrates the use if someone wants special fields in his dto
	private MyEntity dotherEntity;

	public MyComplexEntity(String string2, MyEntity myEntity) {
		this.string = string2;
		this.entity = myEntity;
		this.dotherEntity = myEntity;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public MyEntity getEntity() {
		return entity;
	}

	public void setEntity(MyEntity entity) {
		this.entity = entity;
	}

	public MyEntity getId() {
		return dotherEntity;
	}

	public void setId(MyEntity id) {
		this.dotherEntity = id;
	}
}
