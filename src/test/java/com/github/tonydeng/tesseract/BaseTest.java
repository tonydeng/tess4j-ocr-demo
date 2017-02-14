package com.github.tonydeng.tesseract;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tonydeng on 2017/2/14.
 */
public class BaseTest {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test(){
        log.info("test.......");
    }
}
