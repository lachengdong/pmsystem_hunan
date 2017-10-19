package com.sinog2c.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface  BlueOALogAnnotation {
	public String TableName();
	public String Description();
}
