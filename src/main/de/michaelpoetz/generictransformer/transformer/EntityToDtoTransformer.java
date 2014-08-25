package de.michaelpoetz.generictransformer.transformer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.michaelpoetz.generictransformer.annotation.Dto;

public class EntityToDtoTransformer<E, D> {

	public D apply(E entity, D dto) {
		try {
			fillGenericDto(entity, dto);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final Exception e) {
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
				final String annotationField = fieldIsAnnotated(entityField);
				if (annotationField != null) {
					final Method entityGetter = getGetterMethod(entity, getterName);
					final Object getterReturnValue = entityGetter.invoke(entity, new Object[] {});
					if (entityGetter.getReturnType().isArray() || entityGetter.getReturnType().equals(Class.forName("java.util.List"))) {
						// Liste mit GenericType
						final List<Object> tmp = new ArrayList<Object>();
						for (final Object o : (ArrayList<Object>) getterReturnValue) {
							final Method listIterationGetter = getGetterMethod(o, "get" + methodNameFirstUpper(annotationField));
							tmp.add(listIterationGetter.invoke(o, new Object[] {}));
						}
						final Method dtoSetter = getSetterMethod(dto, setterName, Class.forName("java.util.List"));
						dtoSetter.invoke(dto, new Object[] { tmp });
					} else {
						final Method indirectGetterReturn = getGetterMethod(getterReturnValue, "get" + methodNameFirstUpper(annotationField));
						final Class<?> indirectReturnType = indirectGetterReturn.getReturnType();
						final Object indirectGetterReturnValue = indirectGetterReturn.invoke(getterReturnValue, new Object[] {});
						final Method dtoSetter = getSetterMethod(dto, setterName, indirectReturnType);
						dtoSetter.invoke(dto, new Object[] { indirectGetterReturnValue });
					}
				} else {
					final Method entityGetter = getGetterMethod(entity, getterName);
					final Object getterReturnValue = entityGetter.invoke(entity, new Object[] {});
					final Method dtoSetter = getSetterMethod(dto, setterName, entityField.getType());
					dtoSetter.invoke(dto, new Object[] { getterReturnValue });
				}
			}
		}
	}

	private String fieldIsAnnotated(Field entityField) throws ClassNotFoundException {
		final Dto dto = entityField.getAnnotation(Dto.class);
		if (dto != null) {
			return dto.field();
		}
		return null;

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
