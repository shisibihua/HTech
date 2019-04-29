package com.honghe.communication.plugin.annotation;

/**
 * Created by zhanghongbin on 2017/3/9.
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface CommandPlugin {

    public String name();
}
