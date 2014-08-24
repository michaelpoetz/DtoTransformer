package de.michaelpoetz.generictransformer.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation marks which field of a referenced object should be saved in the Dto.
 * Retention must be set to RUNTIME for the JVM to keep the Annotation and it can be accessed by the Transformer.
 * 
 * @author michael
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Dto {

	String field();

}
