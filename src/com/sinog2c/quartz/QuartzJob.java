package com.sinog2c.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinog2c.util.common.LogUtil;

public class QuartzJob
{
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(ExpireJobTask.class);
    
    public void work()
    {
    	System.out.println("Date: " + LogUtil.currentTime() + " 调度 --" + LogUtil.currentMethodName());
    	logger.info("Quartz的任务调度！！！");
    }
}
