package de.michaelpoetz.generictransformer.dto;

import java.util.List;

import de.michaelpoetz.generictransformer.entity.Address;

public class CorporationDto {

	private String name;

	private List<String> employees;

	private Address address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getEmployees() {
		return employees;
	}

	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
