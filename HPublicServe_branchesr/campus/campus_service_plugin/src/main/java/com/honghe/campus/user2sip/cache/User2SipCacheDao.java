package com.honghe.campus.user2sip.cache;

import com.honghe.cache.Cache;
import com.honghe.cache.CacheFactory;
import com.honghe.campus.user2sip.dao.User2SipDao;

import java.util.*;

/**
 * Created by zhanghongbin on 2016/10/14.
 */
public final class User2SipCacheDao extends User2SipDao {


    public static User2SipCacheDao INSTANCE = new User2SipCacheDao();

    private User2SipCacheDao() {

    }


    @Override
    public Set<String> find() {
        Set<String> sip = new HashSet<>();
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("config.user2sip");
        if (obj == null) {
            return sip;
        }
        Map<String, Set<String>> user2sip = (Map<String, Set<String>>) obj;
        Collection<Set<String>> sipCollection = user2sip.values();
        for (Set<String> sipSet : sipCollection) {
            sip.addAll(sipSet);
        }
        return sip;
    }


    @Override
    public List<Map<String, String>> findByOrgId(String orgId) {
        List<Map<String, String>> result = super.findByOrgId(orgId);
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("config.user2sip");
        if (obj == null) {
            return result;
        }
        Map<String, Set<String>> user2sip = (Map<String, Set<String>>) obj;
        for (Map<String, String> r : result) {
            String sipId = r.get("sipId");
            if (sipId.equals("")) continue;
            String userId = r.get("userId");
            if (user2sip.containsKey(userId)) {
                if (user2sip.get(userId).contains(sipId)) {
                    r.put("status", "1");
                }
            }
        }
        return result;
    }

    @Override
    public boolean add(String userId, String sipId) {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("config.user2sip");
        if (obj != null) {
            Map<String, Set<String>> user2sip = (Map<String, Set<String>>) obj;
            if (user2sip.containsKey(userId)) {
                user2sip.get(userId).add(sipId);
            } else {
                Set<String> sipIdList = new HashSet<>();
                sipIdList.add(sipId);
                user2sip.put(userId, sipIdList);
            }
            cache.put("config.user2sip", user2sip);
        } else {
            Map<String, Set<String>> user2sip = new HashMap<>();
            Set<String> sipIdList = new HashSet<>();
            sipIdList.add(sipId);
            user2sip.put(userId, sipIdList);
            cache.put("config.user2sip", user2sip);
        }
        if (!this.has(userId, sipId)) {
            return super.add(userId, sipId);
        }
        return true;
    }

    @Override
    public List<Map<String, String>> findByUserId(String userId) {
        List<Map<String, String>> result = super.findByUserId(userId);
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("config.user2sip");
        if (obj == null) {
            return result;
        }
        Map<String, Set<String>> user2sip = (Map<String, Set<String>>) obj;
        if (!user2sip.containsKey(userId)) {
            return result;
        }
        Set<String> sipIdList = user2sip.get(userId);
        for (Map<String, String> r : result) {
            String sipId = r.get("sipId");
            if (sipId.equals("")) continue;
            if (sipIdList.contains(sipId)) {
                r.put("status", "1");
            }
        }
        return result;
    }

    @Override
    public Map<String, String> findBySipId(String sipId) {
        Map<String, String> result = super.findBySipId(sipId);
        if (result.isEmpty()) return result;
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("config.user2sip");
        if (obj == null) {
            return result;
        }
        Map<String, Set<String>> user2sip = (Map<String, Set<String>>) obj;
        if (!user2sip.containsKey(result.get("userId"))) {
            return result;
        }
        Set<String> sipIdSet = user2sip.get(result.get("userId"));
        if (sipIdSet.contains(result.get("sipId"))) {
            result.put("status", "1");
        }
        return result;
    }

    @Override
    public void delete(String userId) {
        Cache cache = CacheFactory.newIntance();
        Object obj = cache.get("config.user2sip");
        if (obj != null) {
            Map<String, Set<String>> user2sip = (Map<String, Set<String>>) obj;
            if (user2sip.containsKey(userId)) {
                user2sip.remove(userId);
                cache.put("config.user2sip", user2sip);
            }
        }
    }
}
