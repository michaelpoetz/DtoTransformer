package de.michaelpoetz.generictransformer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.michaelpoetz.generictransformer.dto.MyComplexEntityDto;
import de.michaelpoetz.generictransformer.dto.MyEntityDto;
import de.michaelpoetz.generictransformer.entity.MyComplexEntity;
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
		final MyComplexEntity entity = new MyComplexEntity("mystring", new MyEntity(100, "someOtherString"));
		MyComplexEntityDto dto = new MyComplexEntityDto();
		dto = new EntityToDtoTransformer<MyComplexEntity, MyComplexEntityDto>().apply(entity, dto);

		assertEquals(dto.getString(), entity.getString());
		assertEquals(dto.getEntity(), entity.getEntity().getString());
		assertEquals(dto.getId(), entity.getEntity().getId());
	}
}
