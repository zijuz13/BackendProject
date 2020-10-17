package com.huajun123.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopCounter {
    private Integer times;
    private static final Logger LOGGER= LoggerFactory.getLogger(StopCounter.class);
    public StopCounter(){
        this.times=0;
    }
    public void createCount(){
        LOGGER.info("第{}次请求",++this.times);
    }
    public Boolean canIStop(){
        return this.times==3;
    }
}
