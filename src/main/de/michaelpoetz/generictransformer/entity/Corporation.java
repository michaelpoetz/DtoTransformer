package de.michaelpoetz.generictransformer.entity;

import java.util.ArrayList;
import java.util.List;

import de.michaelpoetz.generictransformer.annotation.Dto;

/**
 * Real world example for Dto transfer
 * 
 * @author michael
 *
 */
public class Corporation {

	private String name;

	@Dto(field = "name")
	private List<Employee> employees;

	private Address address;

	public Corporation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		if (employees != null) {
			return employees;
		} else {
			return new ArrayList<Employee>();
		}
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
