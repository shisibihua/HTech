package com.honghe.communication.ioc.annotation;

import java.lang.annotation.ElementType;

/**
 * Created by zhanghongbin on 2017/3/9.
 */
@java.lang.annotation.Target({ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Command {
    public String name();
}
