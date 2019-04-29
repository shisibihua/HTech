package com.honghe.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhanghongbin on 2016/9/23.
 */
public final class RedisCache implements Cache {

    private static JedisPool jedisPool;

    public final static RedisCache INSTANCE = new RedisCache();

    private RedisCache() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(6);
        jedisPool = new JedisPool(config, System.getProperty("redis.ip", "localhost"), 6379, 500);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jedisPool.close();
                } catch (Exception e) {

                }
            }
        }));
    }

    public boolean isStart() {
        try {
            Jedis client = jedisPool.getResource();
            client.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void put(String key, Object value) {
        try {
            Jedis client = jedisPool.getResource();
            client.set(key.getBytes(), ObjectTranscoder.serialize(value));
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Object get(String key) {
        try {
            Jedis client = jedisPool.getResource();
            Object obj = ObjectTranscoder.deserialize(client.get(key.getBytes()));
            client.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(String key) {
        try {
            Jedis client = jedisPool.getResource();
            client.del(key);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> keys() {
        return this.keys("*");
    }

    @Override
    public List<String> keys(String pattern) {
        List<String> keys = new ArrayList<>();
        try {
            Jedis client = jedisPool.getResource();
            keys.addAll(client.keys(pattern));
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    private static class ObjectTranscoder {
        public static byte[] serialize(Object value) {
            if (value == null) {
                throw new NullPointerException("Can't serialize null");
            }
            byte[] rv = null;
            ByteArrayOutputStream bos = null;
            ObjectOutputStream os = null;
            try {
                bos = new ByteArrayOutputStream();
                os = new ObjectOutputStream(bos);
                os.writeObject(value);
                os.close();
                bos.close();
                rv = bos.toByteArray();
            } catch (IOException e) {
                throw new IllegalArgumentException("Non-serializable object", e);
            } finally {
                try {
                    if (os != null) os.close();
                    if (bos != null) bos.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return rv;
        }

        public static Object deserialize(byte[] in) {
            Object rv = null;
            ByteArrayInputStream bis = null;
            ObjectInputStream is = null;
            try {
                if (in != null) {
                    bis = new ByteArrayInputStream(in);
                    is = new ObjectInputStream(bis);
                    rv = is.readObject();
                    is.close();
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (bis != null) bis.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return rv;
        }
    }

    @Override
    public void clear(String prefixKey) {
        try {
            Jedis client = jedisPool.getResource();
            Set<String> keySet = client.keys("*");
            for (String key : keySet) {
                if (key.startsWith(prefixKey)) {
                    client.del(key);
                }
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cache cache = RedisCache.INSTANCE;
//        List<Map<String,String>> params = new ArrayList<>();
//        Map<String,String> m1 = new HashMap<>();
//        m1.put("ip","xxxx");
//        params.add(m1);
//        Map<String,String> m2 = new HashMap<>();
//        m2.put("ip","ffffff");
//        params.add(m2);
//        cache.put("device.service",params);
//        Map<String,String> www = new HashMap<>();
//        www.put("agb","sdfs");
//       cache.put("1",www);


    }
}
