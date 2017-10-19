package com.sinog2c.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinog2c.util.common.LogUtil;

public class ExpireJobTask {
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(ExpireJobTask.class);

	/**
	 * 业务逻辑处理
	 */
	public void doBiz() {
		// 执行业务逻辑
		// ........
		System.out.println("Date: " + LogUtil.currentTime() + " 调度 --" + LogUtil.currentMethodName());
		logger.info("Quartz的任务调度！！！");
		logger.warn(this.getClass().getName() + "没有使用数据库连接?");
	}
}
