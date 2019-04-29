package com.honghe.dao.importer;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhanghongbin on 2017/3/14.
 */
public final class SQLFileOnceImporter extends SQLFileImporter {

    private Class _class;
    public SQLFileOnceImporter(Class _class){
        this._class = _class;
    }

    @Override
    public void importFile(String connectionInfo, String sqlContent, String dbName, String sqlDelimiter) {
        String importClassName = _class.getSimpleName();
        Map<String, String> importClassNameMap = get();
        if (importClassNameMap.isEmpty() || !importClassNameMap.containsKey(importClassName)) {
            super.importFile(connectionInfo, sqlContent, dbName, sqlDelimiter);
            importClassNameMap.put(importClassName, "true");
            save(importClassNameMap);
        }
    }



    private  LinkedHashMap<String, String> get() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Properties properties = new Properties();
        try {
            InputStream inputStream = SQLFileOnceImporter.class.getResourceAsStream("/dbImport.properties");
            if (inputStream == null) return map;
            properties.load(inputStream);
            Iterator<Object> keys = properties.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                String value = properties.getProperty(key);
                map.put(key.trim(), value.trim());
            }
        } catch (Exception e) {
        }
        return map;

    }

    private  boolean save(Map<String, String> info) {
        Properties properties = new Properties();
        properties.putAll(info);
        FileOutputStream fileOutputStream = null;
        try {
            String path = SQLFileOnceImporter.class.getClassLoader().getResource("").getPath();
            fileOutputStream = new FileOutputStream(path + "dbImport.properties");
            properties.store(fileOutputStream, "");
            return true;
        } catch (Exception e) {
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
            }
        }
        return false;
    }
}
