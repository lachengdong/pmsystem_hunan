package com.sinog2c.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.LogUtil;

public class UpdateSentenceChangeJobTask {
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(UpdateSentenceChangeJobTask.class);
	
	@Autowired
	private SentenceAlterationService sentenceAlterationService;
	@Autowired
	private SystemLogService logService;
	/**
	 * 业务逻辑处理
	 */
	public void doBiz() {
		if(null != sentenceAlterationService){
			logger.info(LogUtil.currentTime() + ": 开始更新刑期变动信息 -- " + LogUtil.currentMethodName());
			try{
				sentenceAlterationService.autoUpdateSentenceChangeData();
				//
				String info = LogUtil.currentTime() + " -- 刑期变动信息 -- 更新完毕";
				logger.info(info);
				
				
//				Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//				String provincecode = jyconfig.getProperty("provincecode");
				//HB立功、重大立功的存储过程有问题，改有coding
//				info = LogUtil.currentTime() + " -- 立功、重大立功信息 -- 更新开始";
//				logger.info(info);
//				sentenceAlterationService.autoUpdateLiGongData(provincecode);
//				info = LogUtil.currentTime() + " -- 立功、重大立功信息 -- 更新完毕";
//				logger.info(info);
				
			} catch (Exception e) {
				e.printStackTrace();
				String info = LogUtil.currentTime() + ": 更新刑期变动信息出错 :sentenceAlterationService ";
				logger.error(info);
			}
		} else {
			String info = LogUtil.currentTime() +"-"+ LogUtil.currentMethodName() + ": sentenceAlterationService="+sentenceAlterationService;
			logger.error(info);
		}
	}
}
