package com.honghe.area.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xinye
 * @date 2016/11/10
 */
public final class AnnotationUtil {

    public static List<String> getAnnotationValue(Method method){
        Annotation[][] annotations = method.getParameterAnnotations();
        List<String> list = new ArrayList<>();
        for(Annotation[] annotations1:annotations){
            for(Annotation annotation:annotations1){
                if(annotation instanceof ParameterValue){
                   ParameterValue parameterValue = (ParameterValue)annotation;
                    list.add(parameterValue.value());
                }
            }
        }
        return list;
    }
}
