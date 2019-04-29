package com.honghe.cache;

/**
 * Created by zhanghongbin on 2016/7/15.
 */
public final class CacheFactory {


    private CacheFactory() {
    }

    public enum Type {
        EFFICIENT, REDIS
    }

    private static Cache cache = null;

    public synchronized final static Cache newIntance() {
        if (cache == null) {
            if (RedisCache.INSTANCE.isStart()) {
                System.out.println("------------ReisCache----------");
                cache = RedisCache.INSTANCE;
            } else {
                System.out.println("------------EfficientCache----------");
                cache = EfficientCache.INSTANCE;
            }
        }
        return cache;
    }

    public final static Cache newIntance(Type type) {
        if (type == Type.EFFICIENT) {
            return EfficientCache.INSTANCE;
        } else {
            return RedisCache.INSTANCE;
        }

    }


}
