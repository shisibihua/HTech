package com.honghe;

import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;
import com.honghe.exception.MethodException;
import com.honghe.exception.ParamException;
import com.honghe.user.util.BeanToMapUtil;
import com.honghe.user.util.JsonUtil;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

/**
 * 反射获取命令的基础类
 *
 * @author lyx
 * @create 2016-11-28 17:46
 **/
public abstract class BaseReflectCommand implements Command {


    /**
     * 获取日志输出对象
     *
     * @return
     */
    public abstract Logger getLogger();

    /**
     * 获取反射调用的类
     *
     * @return
     */
    public abstract Class getCommandClass();

    /**
     * 方法调用入口
     *
     * @param context
     * @param map
     * @return
     */
    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        if (!map.containsKey("cmd_op")) {
            response.setCode(Response.PARAM_ERROR);
            return response;
        }
        String cmd_op = map.get("cmd_op").toString();
        response.setType(cmd_op);
        try {
            Object res = executeByMethod(getCommandClass(), map);
            response.setResult(res);
        } catch (Exception e) {
            System.out.println(e.toString());
            if (e instanceof InvocationTargetException) {

                InvocationTargetException targetEx = (InvocationTargetException) e;
                Throwable t = targetEx.getTargetException();
                String message = t.getMessage();
                if (message.startsWith("方法参数错误")) {
                    response.setCode(Response.PARAM_ERROR);
                    getLogger().error(e);
                } else if (message.startsWith("方法名错误！")) {
                    response.setCode(Response.NO_METHOD);
                    getLogger().error(e);
                } else if (message.startsWith("数据重复")) {
                    response.setResult(-2);
                } else {
                    response.setCode(Response.INNER_ERROR);
                    e.printStackTrace();
                    getLogger().error("反射调用方法失败", e);
                }
            } else if (e instanceof RuntimeException) {
                response.setResult(Response.INNER_ERROR);
                response.setOther("错误！",e.getMessage());
                getLogger().error(e.getMessage(), e);
            }else {
                response.setResult(Response.INNER_ERROR);
                getLogger().error(e.getMessage(), e);
            }
        }
        return response;
    }

    /**
     * 执行对应的类的方法
     */
    private Object executeByMethod(Class clasz, Map<String, Object> map) throws Exception {

        String methodname = String.valueOf(map.get("cmd_op"));

        if (methodname == null || "".equals(methodname)) {
            throw new MethodException();
        }

        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(this.getClass()));
        CtClass cc = pool.get(clasz.getName());
        //获取方法的对象
        CtMethod method = cc.getDeclaredMethod(methodname);

        //获取方法的对象的信息
        MethodInfo methodInfo = method.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            throw new ParamException();
        }

        //当前仅支持非静态方法，静态方法无法精确获取到其参数值
        if (Modifier.isStatic(cc.getModifiers())) {
            throw new MethodException("不支持静态方法进行反射！");
        }


        //获取传入的Map值
        String[] keys = new String[map.size()];
        Object[] values = new Object[map.size()];
        Iterator iter = map.entrySet().iterator();
        int index = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            keys[index] = (String) entry.getKey();
            values[index] = entry.getValue();
            index++;
        }


        //获取参数类型组
        CtClass[] ctClasses = method.getParameterTypes();
        Class<?>[] classes = new Class<?>[ctClasses.length];
        for (int i = 0; i < ctClasses.length; i++) {
            classes[i] = Class.forName(ctClasses[i].getName());
        }
        //获取方法的参数名
        String[] paramNames = new String[method.getParameterTypes().length];
        int offset = getIndex(attr);
        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = attr.variableName(i + offset);
        }
        //给方法的参数赋值
        //存储当前方法参数的值
        Object[] objects = new Object[method.getParameterTypes().length];
        for (int i = 0; i < paramNames.length; i++) {
            Object name = paramNames[i];
            int pos = getPos(keys, name);
            if (pos != -1) {
                //新接口支持任意对象类型参数
                objects[i] = getValue(classes[i], values[pos]);
            }
        }

        //执行该方法

        Object object = clasz.getMethod(methodname, classes).invoke(clasz.newInstance(), objects);

        return object;
    }

    /**
     * 根据参数类型获取值
     * 目前仅支持 Integer，Long
     *
     * @param clasz
     * @param value
     * @return
     */
    public Object getValue(Class clasz, Object value) throws IllegalAccessException, InstantiationException {
        Object obj = value;
        if (clasz.getName().equals(Integer.class.getName())) {
            obj = Integer.valueOf(String.valueOf(value));
        } else if (clasz.getName().equals(Long.class.getName())) {
            obj = Integer.valueOf(String.valueOf(value));
        } else if (clasz.getName().equals(boolean.class.getName())) {
            obj = Boolean.valueOf(String.valueOf(value));
        } else if (clasz.getName().equals(String.class.getName())) {
            obj = String.valueOf(value);
        } else {
            Map<String, Object> map = JsonUtil.jsonToMap(JSONObject.fromObject(value));
            obj = BeanToMapUtil.transMap2Bean(map, clasz.newInstance());
        }
        return obj;
    }


    /**
     * 获取参数所在位置
     *
     * @param attr
     * @return
     */
    private int getIndex(LocalVariableAttribute attr) {
        int re_value = -1;
        for (int i = 0; i < attr.tableLength(); i++) {
            if ("this".equals(attr.variableName(i).toString())) {
                re_value = i + 1;
                break;
            }
        }
        return re_value;
    }

    /**
     * 获取字符串在数组中的定位
     *
     * @param strArray
     * @param str
     * @return
     */
    private int getPos(String[] strArray, Object str) {
        int re_value = -1;
        for (int i = 0; i < strArray.length; i++) {
            if (str.toString().equals(strArray[i])) {
                re_value = i;
                break;
            }
        }
        return re_value;
    }

}
