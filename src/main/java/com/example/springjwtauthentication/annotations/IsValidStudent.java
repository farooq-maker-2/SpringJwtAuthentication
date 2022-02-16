package com.example.springjwtauthentication.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target in the above annotation definition defines where to apply the annotation.
 * In our case it is at the method level , so we give ElementType.METHOD as parameter
 * @Retention denotes when to apply this annotation , in our case it is at run time
 * */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#studentId.toString().equals(authentication.principal)")
public @interface IsValidStudent {
}
