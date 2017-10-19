package com.sinog2c.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.service.api.prisoner.TbprisonerBaseInfoService;
import com.sinog2c.service.api.system.SystemConfigService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.IPUtil;
import com.sinog2c.util.common.LogUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 数据交换作业任务
 */
@Service
public class DataExchangeJobTask {
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(DataExchangeJobTask.class);

	@Autowired
	private DbmsNewDataExportService dataExportService;
	@Autowired
	private SystemLogService logService;
	@Autowired
	private TbprisonerBaseInfoService tbprisonerBaseInfoService;
	@Autowired
	DataTransferService dataTransferService;
	@Autowired
	SystemOrganizationService systemOrganizationService;
	@Autowired
	SystemConfigService systemConfigService;
	
	/**
	 * 业务逻辑处理
	 */
	public void doBiz() {
		// 执行业务逻辑
		// ........
		if(null != dataExportService){
			logger.info(LogUtil.currentTime() + ": 调度数据交换 -- " + LogUtil.currentMethodName());
			try{
				dataExportService.autoPrisonDataExchange();
				//
				String info = LogUtil.currentTime() + " -- 数据交换 -- 执行完成";
				logger.info(info);
				addOnlineLog(info,1);
				//生成姓名的大写拼音
				tbprisonerBaseInfoService.makeNamePinyinShort();
			} catch (Exception e) {
				e.printStackTrace();
				String info = LogUtil.currentTime() + ": 数据交换出错 :dataExportService ";
				logger.error(info);
				addOnlineLog(info,0);
			}
		} else {
			String info = LogUtil.currentTime() +"-"+ LogUtil.currentMethodName() + ": dataExportService="+dataExportService;
			logger.error(info);
			addOnlineLog(info,0);
		}
	}
	
	public void addOnlineLog(String opcontent, int status){
		//
		final String opid = "system";
		final String opname = "系统";
		if(null == opcontent){
			opcontent = "没有出错";
		}
		SystemLog log = new SystemLog();
		log.setLogtype("任务调度");
		log.setOpaction("数据交换");
		log.setOpcontent(opcontent);
		log.setRemark(opcontent);
		log.setStatus(status);
		//
		log.setOpid(opid);
		log.setOpname(opname);
		log.setOrgid("-1"); // orgid
		log.setLoginmac("服务器IP:"+IPUtil.getLocalIP());
		//
		if(null != logService){
			SystemUser operator = null;
			logService.add(log, operator);
		}
	}
	
	
	
	/**
	 * 省局抓取数据
	 * @author YangZR	2015-12-15
	 */
	public void provinceGrabData(){
		
		logger.info(LogUtil.currentTime() + ": 省局抓取数据开始 -- " + LogUtil.currentMethodName());
		
		//如果分布式布署的系统是陕西省局，
		String provinceid = StringNumberUtil.getJyConfigValue("provincecode",null);
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("departid", provinceid);
		paramap.put("name", "curdepart");
		paramap.put("catagory", "SYSTEM");
		SystemConfigBean currdep = systemConfigService.getConfigByMap(paramap);
		if(null !=currdep && "6100".equals(currdep.getValue())){
			//
			String excid = "200";//数据传输方案id，	excid=200是日记的数据交换
			paramap.put("excid", excid);//
			paramap.put("provinceid", provinceid);//
			//获取省局下面各监狱的单位编号：
			List<SystemOrganization> remoteDepartidList = systemOrganizationService.getJailidByProvinceid(paramap);
			
			//创建省局的虚拟用户
			SystemUser user = new SystemUser();
			user.setDepartid(provinceid);
			user.setUserid("sys"); 
			SystemOrganization organization = new SystemOrganization();
			organization.setOrgid(provinceid);
			user.setOrganization(organization);
			user.setName("sys");
			//
			for(SystemOrganization sysOrg: remoteDepartidList){
				String remoteDepartid = sysOrg.getOrgid();
				
				System.out.println(remoteDepartid);
				
				String isArch = null; // 是否传递电子档案; isArch = 1 表示传递
				String crimids = null; // 传递哪些罪犯的电子档案
				String type = "1";
				try{
					dataTransferService.dataExchange(type, paramap, user, remoteDepartid, crimids, isArch);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
