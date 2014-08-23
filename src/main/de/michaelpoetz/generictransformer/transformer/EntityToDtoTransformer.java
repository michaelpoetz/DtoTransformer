package de.michaelpoetz.generictransformer.transformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityToDtoTransformer<E, D> {

	public D apply(E entity, D dto) {
		Method[] entityMethods;
		try {
			entityMethods = Class.forName(entity.getClass().getName()).getMethods();
			final Method[] dtoMethods = Class.forName(dto.getClass().getName()).getMethods();

			for (final Method dtoMethod : dtoMethods) {
				if (dtoMethod.getName().startsWith("set")) {
					for (final Method entityMethod : entityMethods) {
						if (entityMethod.getName().startsWith("get")) {
							if (entityMethod.getName().endsWith(dtoMethod.getName().substring(3))) {
								dtoMethod.invoke(dto, new Object[] { entityMethod.invoke(entity, new Object[] {}) });
							}
						}
					}

				}
			}
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}
}
