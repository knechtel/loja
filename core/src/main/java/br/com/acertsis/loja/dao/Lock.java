package br.com.acertsis.loja.dao;

import java.lang.annotation.*;
import javax.persistence.LockModeType;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {
    LockModeType value();
}
