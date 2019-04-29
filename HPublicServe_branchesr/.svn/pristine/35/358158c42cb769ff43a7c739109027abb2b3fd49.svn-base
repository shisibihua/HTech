package com.honghe.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghongbin
 * Date: 13-8-28
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public final class BigMemoryCache implements com.honghe.cache.Cache {

    public final static BigMemoryCache INSTANCE = new BigMemoryCache();

    private BigMemoryCache() {

    }

    static Cache cache = null;

//    static {
//        try{
//            String path = BigMemoryCache.class.getResource("/").getPath().replaceAll("%20", " ");
//            System.setProperty("com.tc.productkey.path", path + "terracotta-license.key");
//            Searchable searchable = new Searchable();
//            searchable.setAllowDynamicIndexing(true);
//            CacheConfiguration cacheConfiguration = new CacheConfiguration()
//                    .name("bm")
//                    .maxBytesLocalHeap(250, MemoryUnit.MEGABYTES)
//                    .maxBytesLocalOffHeap(Integer.parseInt(System.getProperty("filterItem.bigMemory.size", "1")), MemoryUnit.GIGABYTES);
//            cacheConfiguration.setEternal(true);
//            cacheConfiguration.setOverflowToOffHeap(true);
//            cacheConfiguration.searchable(searchable);
//            Configuration managerConfiguration = new Configuration();
//            managerConfiguration.addCache(cacheConfiguration);
//            managerConfiguration.updateCheck(true);
//            managerConfiguration.monitoring(Configuration.Monitoring.AUTODETECT);
//            final CacheManager manager = new CacheManager(managerConfiguration);
//            cache = manager.getCache("bm");
//
////        cache.registerDynamicAttributesExtractor(new DynamicAttributesExtractor() {
////            @Override
////            public Map<String, String> attributesFor(Element element) {
////                return (Map) element.getObjectValue();
////            }
////        });
//            Runtime.getRuntime().addShutdownHook(new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        cache.removeAll();
//                    } catch (Exception e) {
//
//                    }
//                    manager.shutdown();
//                }
//            });
//        }catch (Exception e){
//           // e.printStackTrace();
//        }
//
//    }


    public final Object get(String key) {
        if (cache.isKeyInCache(key)) {
            return cache.get(key).getObjectValue();
        } else {
            return null;
        }
    }


    public final void put(String key, Object value) {
        cache.put(new Element(key, value));
    }


    public final void remove(String key) {
        cache.remove(key);
    }

    public List<String> keys() {
        return cache.getKeys();
    }


    public static void main(String[] args) {
      //  System.out.println(BigMemoryCache.class.getResource("/").getPath());
    }
}
