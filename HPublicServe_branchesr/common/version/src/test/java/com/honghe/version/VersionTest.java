package com.honghe.version;

import static org.junit.Assert.*;

/**
 * Created by zhanghongbin on 2015/9/2.
 */
public class VersionTest {

    @org.junit.Test
    public void testGetVersionInfo() throws Exception {
        System.out.println(Version.getVersionInfo());
            assertEquals("3.0.1.20150902 (8771)",Version.getVersionInfo());
    }
}