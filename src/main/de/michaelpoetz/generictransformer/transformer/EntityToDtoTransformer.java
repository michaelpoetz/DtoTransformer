package de.michaelpoetz.generictransformer.transformer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.michaelpoetz.generictransformer.annotation.Dto;

public class EntityToDtoTransformer<E, D> {

	public D apply(E entity, D dto) {
		try {
			fillGenericDto(entity, dto);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	private void fillGenericDto(E entity, D dto) throws
			ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		for (final Field entityField : getFieldsArray(entity)) {
			final String name = entityField.getName();
			final Class<?> returnType = getDtoFieldReturnType(getFieldsArray(dto), name);
			if (returnType != null) {
				final String annotationField = fieldIsAnnotated(entityField);
				final Method dtoSetter = getSetterMethod(dto, name, returnType);
				final Method entityGetter = getGetterMethod(entity, name);
				Object valueToBeSet = new Object();
				if (annotationField != null) {
					final Object getterReturnValue = entityGetter.invoke(entity, new Object[] {});
					// Build in a check so that Collection types must be matched by a java.util.List on Dto-Side
					if (returnType.equals(Class.forName("java.util.List"))) {
						valueToBeSet = fillList(annotationField, getterReturnValue);
					} else {
						final Method indirectGetterReturn = getGetterMethod(getterReturnValue, annotationField);
						valueToBeSet = indirectGetterReturn.invoke(getterReturnValue, new Object[] {});

					}
				} else {
					valueToBeSet = entityGetter.invoke(entity, new Object[] {});
				}
				dtoSetter.invoke(dto, new Object[] { valueToBeSet });
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Object fillList(final String annotationField, final Object getterReturnValue) throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Object valueToBeSet;
		valueToBeSet = new ArrayList<Object>();
		for (final Object o : (ArrayList<Object>) getterReturnValue) {
			final Method listIterationGetter = getGetterMethod(o, annotationField);
			((ArrayList<Object>) valueToBeSet).add(listIterationGetter.invoke(o, new Object[] {}));
		}
		return valueToBeSet;
	}

	private String fieldIsAnnotated(Field entityField) {
		final Dto dto = entityField.getAnnotation(Dto.class);
		if (dto != null) {
			return dto.field();
		}
		return null;

	}

	private Class<?> getDtoFieldReturnType(Field[] dtoFields, String name) {
		for (final Field dtoField : dtoFields) {
			if (dtoField.getName().equals(name)) {
				return dtoField.getType();
			}
		}
		return null;
	}

	private String methodNameFirstUpper(final String name) {
		return name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
	}

	private Field[] getFieldsArray(Object object) {
		return object.getClass().getDeclaredFields();
	}

	private Method getGetterMethod(Object object, String name) throws NoSuchMethodException {
		return object.getClass().getMethod("get" + methodNameFirstUpper(name), new Class<?>[] {});
	}

	private Method getSetterMethod(Object object, String name, Class<?> parameter) throws NoSuchMethodException {
		return object.getClass().getMethod("set" + methodNameFirstUpper(name), new Class<?>[] { parameter });
	}
}
