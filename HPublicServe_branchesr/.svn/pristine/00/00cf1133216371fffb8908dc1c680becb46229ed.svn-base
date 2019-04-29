package com.honghe;

/**
 * @author HOUJT
 * dao单例工厂
 */
public class DaoFactory {

    private DaoFactory() {
    }

    private static DaoFactory daoFactory = null;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public <T extends AbstractUserDao> T getUserDaoInstance(Class<T> clz) {
        try {
            T t = (T) Class.forName(clz.getName()).newInstance();
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
