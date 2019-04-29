package com.honghe.communication.plugin;

import com.honghe.communication.plugin.annotation.AnnotationCommandPlugin;

import java.io.File;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by zhanghongbin on 2015/5/22.
 */
public class JarLoader implements Loader {

    private static List<CommandPlugin> getJar(File file, String path, String pk) {
        List<CommandPlugin> classList = new ArrayList<>();
        try {
            JarFile jar = new JarFile(file);
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.endsWith(".class")) {
                    String className;
                    if (name.startsWith(path)) {
                        className = pk + "." + name.substring(path.length() + 1, name.length() - 6);
                        Class cls = Class.forName(className);
                        if (CommandPlugin.class.isAssignableFrom(cls) && !CommandPlugin.class.equals(cls)) {
                            classList.add((CommandPlugin) cls.newInstance());
                        }
                    } else {
                        className = name.replaceAll("/", ".").replaceAll(".class", "");
                        Class<?> _class = Thread.currentThread().getContextClassLoader().loadClass(className);
                        if (_class.isAnnotationPresent(com.honghe.communication.plugin.annotation.CommandPlugin.class)) {
                            classList.add(new AnnotationCommandPlugin(_class));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classList;
    }


    @Override
    public List<CommandPlugin> find(Class<?> cls) {
        List<CommandPlugin> commandPlugins = new ArrayList<>();
        String pk = cls.getPackage().getName();
        String path = pk.replace('.', '/');
        String classPath = cls.getResource("").getPath().replaceAll("lib.*", "lib/").replaceAll("file:", "").replaceAll("%20", " ");
        try {
            File file = new File(classPath);
            for (File _file : file.listFiles()) {
                if (_file.getName().indexOf("_plugin") == -1) continue;
                commandPlugins.addAll(getJar(_file, path, pk));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commandPlugins;
    }

}
