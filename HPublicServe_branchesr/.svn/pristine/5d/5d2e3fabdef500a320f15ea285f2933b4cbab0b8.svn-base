package com.honghe.dao.importer;

import com.honghe.dao.connection.JNDIConectionPool;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

import java.util.Map;

/**
 * Created by zhanghongbin on 2017/3/14.
 */
public class SQLFileImporter {


    public void importFile(String connectionInfo,String sqlContent, String dbName,String sqlDelimiter) {
        SQLAntImporter sqlExecuter;
        if (connectionInfo.startsWith("jdbc:")) {
            String[] urls = connectionInfo.split("\\?");
            String[] params = urls[1].split("&");
            String userName = "";
            String password = "";
            StringBuilder stringBuilder = new StringBuilder();
            for (String param : params) {
                if (param.startsWith("user")) {
                    userName = param.split("=")[1].trim();
                    continue;
                }
                if (param.startsWith("password")) {
                    password = param.split("=")[1].trim();
                    continue;
                }
                stringBuilder.append(param + "&");
            }
            String p = stringBuilder.toString().substring(0, stringBuilder.length() - 1);
            sqlExecuter = new SQLAntImporter(SQLAntImporter.Type.MYSQL, urls[0].replaceAll(dbName, "") + "?" + p + "&allowMultiQueries=true", userName, password,sqlDelimiter);
        } else {
            Map<String, String> connectionInfoMap = JNDIConectionPool.getConnectionInfo(connectionInfo);
            if (connectionInfoMap.isEmpty()) return;
            sqlExecuter = new SQLAntImporter(SQLAntImporter.Type.MYSQL, connectionInfoMap.get("url").replaceAll(dbName, "") + "&allowMultiQueries=true", connectionInfoMap.get("userName"),
                    connectionInfoMap.get("password"),sqlDelimiter);
        }
        sqlExecuter.importFile(sqlContent);
    }


     static class SQLAntImporter {

        public static enum Type {
            MYSQL("com.mysql.jdbc.Driver");
            private String type;

            Type(String type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return this.type;
            }
        }

        private Type type;
        private String url;
        private String userName;
        private String password;

        private String delimiter;

        public SQLAntImporter(Type type, String url, String userName, String password, String delimiter) {
            this.type = type;
            this.url = url;
            this.userName = userName;
            this.password = password;
            this.delimiter = delimiter;
        }

        public SQLAntImporter(Type type, String url, String userName, String password) {
            this(type, url, userName, password, ";");
        }

        public void importFile(String sqlContent) {
            SQLExec sqlExec = new SQLExec();
            //设置数据库参数
            sqlExec.setDriver(type.toString());
            sqlExec.setDelimiter(delimiter);
            sqlExec.setUrl(this.url);
            sqlExec.setUserid(this.userName);
            sqlExec.setPassword(this.password);
            //要执行的脚本
            sqlExec.addText(sqlContent);
            //有出错的语句该如何处理
            sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(
                    SQLExec.OnError.class, "abort")));
            sqlExec.setPrint(true); //设置是否输出

            //输出到文件 sql.out 中；不设置该属性，默认输出到控制台
            sqlExec.setProject(new Project());
            try {
                sqlExec.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
