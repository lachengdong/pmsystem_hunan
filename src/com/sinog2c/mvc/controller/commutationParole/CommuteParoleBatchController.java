package com.sinog2c.mvc.controller.commutationParole;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfCommuteParoleBatchMapper;
import com.sinog2c.dao.api.commutationParole.TbxfPunishmentRangMapper;
import com.sinog2c.model.commutationParole.TbxfCommuteParoleBatch;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.CommuteParoleBatchService;
import com.sinog2c.service.api.commutationParole.QualificationScreeningService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.CalendarUtil;
import com.sinog2c.util.common.StringNumberUtil;

import flexjson.JSONDeserializer;

/**
 * 批次设置
 * @author 
 *
 */
@Controller
@RequestMapping("/commutationParole")
public class CommuteParoleBatchController extends ControllerBase {
	@Resource
	private CommuteParoleBatchService commuteParoleBatchService;
	@Resource
	private SystemLogService logService;
	@Resource
	private QualificationScreeningService qualificationScreeningService;
	@Resource
	private TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper;
	@Resource
	private TbxfPunishmentRangMapper tbxfPunishmentRangMapper;
	
	/**
	 * 跳转列表页
	 * @return
	 */
	@RequestMapping(value = "/toCommuteParoleBatchList.page")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		returnResourceMap(request);
		//宁夏，批次设置页面分离
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA);
		if (!StringNumberUtil.isNullOrEmpty(ningxia) && ningxia.contains(getLoginUser(request).getDepartid())) {
			request.setAttribute("ningxia", 1);
		}else {
			request.setAttribute("ningxia", 0);
		}
		return new ModelAndView("commutationParole/commuteParoleBatchListPage");
	}
	
	/**
	 * 列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCommuteParoleBatchList.json")
	@ResponseBody
	public Object getCommuteParoleBatchList(HttpServletRequest request) {
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String key = request.getParameter("key");
		String departid = getLoginUser(request).getDepartid();
		int total = commuteParoleBatchService.getCommuteParoleBatchCount(departid, key);
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		List<TbxfCommuteParoleBatch> list = commuteParoleBatchService.getCommuteParoleBatchList(departid, key, sortField, sortOrder, start, end);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	/**
	 * 跳转新增页
	 * @return
	 */
	@RequestMapping(value="/toCommuteParoleBatchAddPage.page")
	public ModelAndView toBatchSetAddPage(HttpServletRequest request) {
		String batchidStr = request.getParameter("id");
		TbxfCommuteParoleBatch commuteParoleBatch = new TbxfCommuteParoleBatch();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		if(null!=batchidStr && !"".equals(batchidStr)){
			int batchid = Integer.valueOf(batchidStr);
			commuteParoleBatch = commuteParoleBatchService.getCommuteParoleBatchById(batchid);
			//之前书写commuteParoleBatch中日期类型的数据根本无法赋值到 对应的标签上面
			//mushuhong：修改（不知道其他省的有没有问题，宁夏点击修改，日期无法赋值到相应的框上面）
			request.setAttribute("batch", commuteParoleBatch.getBatch());//批次
			request.setAttribute("batchid", commuteParoleBatch.getBatchid());//批次Id
			request.setAttribute("curyear", commuteParoleBatch.getCuryear());//批次年
			request.setAttribute("examineend", format.format(commuteParoleBatch.getExamineend()));//考核止日
			request.setAttribute("remainderpunishment", format.format(commuteParoleBatch.getRemainderpunishment()));//剩余刑期起算日
		}  else if (StringNumberUtil.belongTo(provincecode, GkzxCommon.TIANJIN_PROVINCE)){
			SystemUser user = getLoginUser(request);
			CalendarUtil tt = new CalendarUtil(); 
			String year = String.valueOf(CalendarUtil.getYear());
			int batch = commuteParoleBatchService.selectMaxBatch(user.getDepartid(),year);
			request.setAttribute("curyear", year);//批次年
			request.setAttribute("batch", batch);//批次
			request.setAttribute("examineend", tt.strToDate(tt.getDatePlusDays(tt.getThisSeasonFirstTime(tt.getMonth()),"yyyy-MM-dd", 19)));//考核止日
			request.setAttribute("remainderpunishment", tt.strToDate(tt.getDatePlusDays(tt.getThisSeasonFirstTime(tt.getMonth()),"yyyy-MM-dd", 20)));//剩余刑期起算日
		} else {
			SystemUser user = getLoginUser(request);
			CalendarUtil tt = new CalendarUtil(); 
			String year = String.valueOf(CalendarUtil.getYear());
			int batch = commuteParoleBatchService.selectMaxBatch(user.getDepartid(),year);
			request.setAttribute("curyear", year);//批次年
			request.setAttribute("batch", batch);//批次
			String examineend = tt.getNowTime("yyyy-MM-dd");
			request.setAttribute("examineend", examineend);//考核止日
			
			String remainderpunishment = tt.getDatePlusDays(examineend, "yyyy-MM-dd", 1);
			request.setAttribute("remainderpunishment", remainderpunishment);//剩余刑期起算日
		}
		return new ModelAndView("commutationParole/commuteParoleBatchAddPage"," ",commuteParoleBatch);
	}
	
	/**
	 * 保存数据
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = { "/saveCommuteParoleBatch.json" })
	@ResponseBody
	public Object saveCommuteParoleBatch(HttpServletRequest request) throws ParseException {
		String result = "success";
		short status = 1;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String operatetype = request.getParameter("operatetype");
		SystemUser user = getLoginUser(request);
		String json = request.getParameter("data");
		ArrayList rows = (ArrayList)decode(json);
		Date examineend = new Date();
		if(rows!=null && rows.size()>0){
			for(int i=0;i<rows.size();i++){
				HashMap row = (HashMap)rows.get(i);
				String curyear = (String)row.get("curyear");
				int batch = Integer.valueOf((String)row.get("batch"));
				examineend = simpleDateFormat.parse((String)row.get("examineend"));
				Date remainderpunishment = simpleDateFormat.parse((String)row.get("remainderpunishment"));
				TbxfCommuteParoleBatch commuteParoleBatch = new TbxfCommuteParoleBatch();
				commuteParoleBatch.setCuryear(curyear);
				commuteParoleBatch.setBatch((short)batch);
				commuteParoleBatch.setExamineend(examineend);
				commuteParoleBatch.setRemainderpunishment(remainderpunishment);
				commuteParoleBatch.setDepartid(user.getDepartid());
				commuteParoleBatch.setOpid(user.getUserid());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("departid", user.getDepartid());
				if("new".equals(operatetype)){
					try {
						commuteParoleBatchService.insertCommuteParoleBatch(commuteParoleBatch);
						commuteParoleBatchService.clearMaxBatch(map);
						commuteParoleBatchService.updateMaxBatch(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("批次添加操作");
					log.setOpaction("添加");
					log.setOpcontent("添加批次");
					log.setOrgid(user.getUserid());
					log.setRemark("添加批次");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if("edit".equals(operatetype)){
					int batchid = Integer.valueOf(request.getParameter("batchid"));
					commuteParoleBatch.setBatchid(batchid);
					commuteParoleBatch.setOptime(new Timestamp(System.currentTimeMillis()));
					try {
						commuteParoleBatchService.updateCommuteParoleBatch(commuteParoleBatch);
						commuteParoleBatchService.clearMaxBatch(map);
						commuteParoleBatchService.updateMaxBatch(map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						result = "error";
						status = 0;
					}
					SystemLog log = new SystemLog();
					log.setLogtype("批次修改操作");
					log.setOpaction("修改");
					log.setOpcontent("修改批次");
					log.setOrgid(user.getUserid());
					log.setRemark("修改批次");
					log.setStatus(status);
					try {
						logService.add(log,user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//天津的未履行财产刑罪犯设置
				Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
				String provincecode = jyconfig.getProperty("provincecode");
				if (StringNumberUtil.belongTo(provincecode, GkzxCommon.TIANJIN_PROVINCE)){
					commuteParoleBatchService.updateCrimeType(map);
				}
				
			}
		}
		return result;
	}
	

	/**
	 * 生成数据
	 */
	@RequestMapping(value = "/generateCommuteDateAfterBatchSet.json")
	@ResponseBody
	public Object generateCommuteDateAfterBatchSet(HttpServletRequest request){
		
		String result = "success";
		SystemUser user = getLoginUser(request);
		String orgid = "";
		if(user.getOrganization().getUnitlevel().equals(GkzxCommon.FOUR)) {//监区级别的orgid
			orgid = user.getOrgid();
		}
		try{
			
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String provincecode = jyconfig.getProperty("provincecode");
			
			
			if(GkzxCommon.GUANGDONG_PROVINCE.equals(provincecode) || GkzxCommon.HUNAN_PROVINCE.equals(provincecode) ){
				//初始化数据
				commuteParoleBatchService.initData4Qualification(user.getDepartid());
				
				
				if(GkzxCommon.HUNAN_PROVINCE.equals(provincecode) ){
					//处理考核奖惩部分
					dealKaoHeJianChen4HuNan(user.getDepartid());
				}
				
				//资格筛查(new)
				qualificationScreeningService.qualificateCommuteData(user.getDepartid(),orgid,request);
				
				//将筛查符合条件的罪犯在TBXF_PRISONERPERFORMANCEMERGE表中做标记，用于生成资格榜单
				commuteParoleBatchService.generateQualificationList(user.getDepartid());
				
			}else{
				
				//更新TBXF_SENTENCEALTERATION（刑期变动信息表）余刑年月日和批次号
				commuteParoleBatchService.updateNowpunishment(user.getDepartid());
				//更新减刑间隔期
			    commuteParoleBatchService.updateInterval(user.getDepartid());

			    commuteParoleBatchService.callStoredProcedure(user.getDepartid());

			    qualificationScreeningService.qualificationScreening(user.getDepartid(), orgid, request);

			    commuteParoleBatchService.callCallBackProcedure(user.getDepartid());
				
				//将筛查符合条件的罪犯在TBXF_PRISONERPERFORMANCEMERGE表中做标记，用于生成资格榜单
				//commuteParoleBatchService.generateQualificationList(user.getDepartid());
			}
			
		}catch(Exception e){
			result = "error";
			e.printStackTrace();
		} finally {
			//出错时退出进度条
			request.getSession().setAttribute("percent", 100.0);
		}
		return result;
	}
	
	
	
	private void dealKaoHeJianChen4HuNan(String departid){
		commuteParoleBatchService.dealKaoHeJianChen4HuNan(departid);
	}

	
	/**
	 * 删除数据
	 */
	@RequestMapping(value = "/deleteCommuteParoleBatch.json")
	@ResponseBody
	public Object deleteCommuteParoleBatch(HttpServletRequest request){
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		if(null!=ids&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				try {
					long batchid = Long.valueOf(ids[i]);
					commuteParoleBatchService.deleteCommuteParoleBatch(batchid);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = "error";
					status = 0;
				}
				SystemLog log = new SystemLog();
				log.setLogtype("批次删除操作");
				log.setOpaction("删除");
				log.setOpcontent("删除批次");
				log.setOrgid(user.getUserid());
				log.setRemark("删除批次");
				log.setStatus(status);
				try {
					logService.add(log,user);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("departid", user.getDepartid());
			commuteParoleBatchService.clearMaxBatch(map);
			commuteParoleBatchService.updateMaxBatch(map);
		}
		return result;
	}
	
	/**
	 * 将json转化为ArrayList
	 * @param json
	 * @return
	 */
	public Object decode(String json) {
		if (null==json||"".equals(json)) return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		Object obj = deserializer.deserialize(json);
		if(obj != null && obj.getClass() == String.class){
			return decode(obj.toString());
		}
		return obj;
	}
	
	@RequestMapping(value = { "/checkUnfinishedCase.json" })
	@ResponseBody
	public Object checkUnfinishedCase(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		return  commuteParoleBatchService.checkUnfinishedCase(user.getDepartid());
	}
	
	@RequestMapping(value = { "/getPercent.json" })
	@ResponseBody
	public Object getPercent(HttpServletRequest request) {
		String percent = request.getSession().getAttribute("percent")==null?"-1":request.getSession().getAttribute("percent").toString();
		String type = request.getParameter("type");
		if(type!=null && type.equals("remove")) {
			request.getSession().setAttribute("percent", -1);
			percent = "-1";
		}
		if(percent==null || percent.equals("")) {
			percent="-1";
		}
		return  percent;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
