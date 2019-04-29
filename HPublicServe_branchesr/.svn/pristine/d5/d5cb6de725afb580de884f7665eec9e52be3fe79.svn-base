package com.honghe.communication.ioc.annotation;

import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghongbin on 2017/3/10.
 */
public final class AnnotationCommand implements com.honghe.communication.ioc.Command {

    private Map<String, Method> methodMap = new HashMap<>();
    private Class _class;

    public AnnotationCommand(Class _class) {
        this._class = _class;
        if (!this.constructorCheck(_class)) return;
        try {
            Method[] methods = _class.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Command.class)) {
                    String name = method.getAnnotation(Command.class).name();
                    methodMap.put(name, method);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造函数检查，必须有不带参数的构造函数
     *
     * @param _class
     * @return
     */
    private boolean constructorCheck(Class _class) {
        boolean flag = false;
        Constructor[] constructorArray = _class.getConstructors();
        for (Constructor constructor : constructorArray) {
            Class[] classArray = constructor.getParameterTypes();
            if (classArray.length == 1 && classArray[0] == Map.class) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        String cmd_op = map.get("cmd_op").toString();
        response.setType(cmd_op);
        if (methodMap.containsKey(cmd_op)) {
            Method method = methodMap.get(cmd_op);
            try {
                Object obj = _class.getConstructor(Map.class).newInstance(map);
                Object result = method.invoke(obj);
                if (result != null) {
                    response.setResult(result);
                } else {
                    response.setResult("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.setCode(Response.NO_METHOD);
            return response;
        }
        return response;
    }

}
