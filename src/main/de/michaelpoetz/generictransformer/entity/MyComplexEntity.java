package de.michaelpoetz.generictransformer.entity;

import de.michaelpoetz.generictransformer.annotation.Dto;

public class MyComplexEntity {

	private String string;

	@Dto(field = "string")
	private MyEntity entity;

	public MyComplexEntity(String string2, MyEntity myEntity) {
		this.string = string2;
		this.entity = myEntity;
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
}
