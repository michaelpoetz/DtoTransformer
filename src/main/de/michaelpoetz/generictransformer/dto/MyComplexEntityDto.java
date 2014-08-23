package de.michaelpoetz.generictransformer.dto;

import de.michaelpoetz.generictransformer.entity.MyEntity;

public class MyComplexEntityDto {
	private String string;

	private MyEntity entity;

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
