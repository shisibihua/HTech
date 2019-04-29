package com.honghe.dao.importer;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * <p>Description:sql文件导入</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/9/2
 */
public final class SQLAntImporter {


    public enum Type {
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
