package de.michaelpoetz.generictransformer.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.michaelpoetz.generictransformer.dto.CorporationDto;
import de.michaelpoetz.generictransformer.dto.MyEntityDto;
import de.michaelpoetz.generictransformer.entity.Address;
import de.michaelpoetz.generictransformer.entity.Corporation;
import de.michaelpoetz.generictransformer.entity.Employee;
import de.michaelpoetz.generictransformer.entity.MyEntity;
import de.michaelpoetz.generictransformer.transformer.EntityToDtoTransformer;

public class MyTest {

	@Test
	public void shouldTransformSimpleEntityToDto() {
		final MyEntity entity = new MyEntity(1, "mystring");
		MyEntityDto dto = new MyEntityDto();
		dto = new EntityToDtoTransformer<MyEntity, MyEntityDto>().apply(entity, dto);

		assertEquals(dto.getString(), entity.getString());
	}

	@Test
	public void shouldTransformMoreComplexEntityToDto() {
		final Corporation entity = new Corporation("ACME");
		entity.setAddress(new Address("XSample Street 10", "12345", "XSample town", "XSample country"));
		final List<Employee> tmp = entity.getEmployees();
		tmp.add(new Employee("Mike"));
		tmp.add(new Employee("Tony"));
		tmp.add(new Employee("John"));
		entity.setEmployees(tmp);
		CorporationDto dto = new CorporationDto();
		dto = new EntityToDtoTransformer<Corporation, CorporationDto>().apply(entity, dto);
		assertEquals(entity.getName(), dto.getName());
		assertEquals(entity.getEmployees().size(), dto.getEmployees().size());
		assertEquals(entity.getEmployees().get(0).getName(), dto.getEmployees().get(0));
		assertEquals(dto.getAddress().getCountry(), "XSample country");
	}
}
