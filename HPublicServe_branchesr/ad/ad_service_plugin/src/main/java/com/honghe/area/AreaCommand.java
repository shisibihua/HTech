package com.honghe.area;

import com.honghe.area.dao.BasicDao;
import com.honghe.area.dao.DaoFactory;
import com.honghe.area.reflect.AnnotationUtil;
import com.honghe.communication.ioc.Command;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.ioc.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhanghongbin
 * @date 2017/3/9
 */
public class AreaCommand implements Command{

    public AreaCommand(){

    }

    @Override
    public Response execute(Context context, Map<String, Object> map) {
        Response response = new Response();
        response.setCode(Response.NO_METHOD);
        if(map.containsKey("cmd_op")){
            String cmdOp = (String)map.get("cmd_op");
            String[] args = cmdOp.split("_");
            if(!DaoFactory.containsDao(args[0])){
                response.setCode(Response.NO_METHOD);
                return response;
            }
            response.setType(cmdOp);
            BasicDao basicDao = DaoFactory.getBasicDaoInstance(args[0]);
            Method[] methods = basicDao.getMethods();
            for(Method method:methods){
                if(method.getName().equals(args[1])){
                    List<String> paraList = AnnotationUtil.getAnnotationValue(method);
                    String[] var = new String[paraList.size()];
                    for(int i =0,j=paraList.size();i<j;i++){
                        var[i] = (String)map.get(paraList.get(i));
                    }
                    try {
                        Object obj = method.invoke(basicDao,var);
                        response.setCode(0);
                        response.setResult(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        response.setCode(Response.NO_METHOD);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        response.setCode(Response.PARAM_ERROR);
                    }
                    break;
                }
            }
        }else{
            response.setCode(Response.PARAM_ERROR);
        }
        return response;
    }
}
