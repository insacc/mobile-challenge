package org.insacc.mobilechallenge;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by can on 31.07.2017.
 * Scope declaration for the dagger modules other than the application module and application component.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomScope {
}
