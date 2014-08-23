package de.michaelpoetz.generictransformer.transformer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityToDtoTransformer<E, D> {

	public D apply(E entity, D dto) {
		try {
			fillGenericDto(entity, dto);
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dto;
	}

	private void fillGenericDto(E entity, D dto) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
			IllegalArgumentException, NoSuchMethodException, SecurityException {
		final Field[] entityFields = getFieldsArray(entity);
		final Field[] dtoFields = getFieldsArray(dto);

		for (final Field entityField : entityFields) {
			final String name = entityField.getName();
			if (dtoFieldExists(dtoFields, name)) {
				final String getterName = "get" + methodNameFirstUpper(name);
				final String setterName = "set" + methodNameFirstUpper(name);
				getSetterMethod(dto, setterName, entityField.getType())
						.invoke(dto, new Object[] { getGetterMethod(entity, getterName).invoke(entity, new Object[] {}) });
			}
		}
	}

	private boolean dtoFieldExists(Field[] dtoFields, String name) {
		for (final Field dtoField : dtoFields) {
			if (dtoField.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private String methodNameFirstUpper(final String name) {
		return name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
	}

	private Field[] getFieldsArray(Object object) throws ClassNotFoundException {
		return Class.forName(object.getClass().getName()).getDeclaredFields();
	}

	private Method getGetterMethod(Object object, String name) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException {
		final Class<?> className = Class.forName(object.getClass().getName());
		return className.getMethod(name, new Class<?>[] {});
	}

	private Method getSetterMethod(Object object, String name, Class<?> parameter) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException {
		final Class<?> className = Class.forName(object.getClass().getName());
		return className.getMethod(name, new Class<?>[] { parameter });
	}
}
