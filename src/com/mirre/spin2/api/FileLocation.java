package com.mirre.spin2.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FileLocation {
	String location();
	
    public static class LocationAnnonationParser {
		public static String parse(Class<?> c){
			if(c.isAnnotationPresent(FileLocation.class)){
				FileLocation data = c.getDeclaredAnnotation(FileLocation.class);
				return data.location();
			}
			throw new IllegalStateException("Class doesn't have the FileLocation annonation");
		}
	}
}
