package br.com.flexait.crud.observer.redirect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Stereotype
@RequestScoped
public @interface RedirectToIndex {

}
