package com.honghe.communication.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghongbin on 2015/5/22.
 */
@Deprecated
class DicLoader implements Loader {


//    /**
//     * ȡ�õ�ǰ��·���µ�������
//     *
//     * @param cls
//     * @return
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    private static List<Class<?>> getClasses(Class<?> cls) throws IOException,
//            ClassNotFoundException {
//        String pk = CommandPlugin.class.getPackage().getName();
//        String path = pk.replace('.', '/');
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        URL url = classloader.getResource(path);
//        return getClasses(new File(url.getFile()), pk);
//    }
//
//    /**
//     * ����������
//     *
//     * @param dir
//     * @param pk
//     * @return
//     * @throws ClassNotFoundException
//     */
//    private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException {
//        List<Class<?>> classes = new ArrayList<Class<?>>();
//        if (!dir.exists()) {
//            return classes;
//        }
//        for (File f : dir.listFiles()) {
//            if (f.isDirectory()) {
//                classes.addAll(getClasses(f, pk + "." + f.getName()));
//            }
//
//            String name = f.getName();
//            if (name.endsWith(".class")) {
//                classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));
//            }
//        }
//        return classes;
//    }


    /**
     * ��ȡͬһ·�������������ӿ�ʵ����
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public List<CommandPlugin> find(Class<?> cls) {
//        List<Class<?>> classes = new ArrayList<Class<?>>();
//        try {
//            for (Class<?> c : getClasses(cls)) {
//                if (cls.isAssignableFrom(c) && !cls.equals(c)) {
//                    classes.add(c);
//                }
//            }
//        } catch (Exception e) {
//
//        }
//        return classes;
        return null;
    }
}
