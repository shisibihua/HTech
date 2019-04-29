package com.honghe;

/**
 * @author HOUJT
 */
public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    public static ServiceFactory getInstance() {
        if (null==serviceFactory) {
            synchronized (ServiceFactory.class) {
                if(null==serviceFactory) {
                    serviceFactory = new ServiceFactory();
                }
            }
        }

        return serviceFactory;
    }

    public <T extends AbstractService> T getServiceInstance(Class<T> clz) {
        try {
            T t = (T) Class.forName(clz.getName()).newInstance();
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
