package com.sinog2c.quartz;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfCommuteParoleBatchMapper;
import com.sinog2c.dao.api.officeAssistant.TbuserNoticeMapper;
import com.sinog2c.dao.api.officeAssistant.TbuserUserNoticeMapper;
import com.sinog2c.dao.api.system.SystemUserMapper;
import com.sinog2c.dao.api.system.UserOrgWrapperMapper;
import com.sinog2c.model.commutationParole.TbxfCommuteParoleBatch;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.officeAssistant.TbuserUserNotice;
import com.sinog2c.model.system.SystemConfigBean;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.UserOrgWrapper;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.officeAssistant.UserNoticeService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemConfigService;
import com.sinog2c.util.common.DateUtils;
import com.sinog2c.util.common.StringNumberUtil;

/*
 *  插入事件到用户通知信息表
 */
public class InsertUserNoticeData {
	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private SentenceAlterationService sas;
	@Autowired
	private UserNoticeService userNoticeService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper;
	@Autowired
	private TbuserUserNoticeMapper tbuserUserNoticeMapper;
	@Autowired
	private UserOrgWrapperMapper userorgwrappermapper;
	@Autowired
	private TbuserNoticeMapper TbuserNoticeMapper;
	@Autowired
	private SystemUserMapper systemusermapper;
	private static HashMap<String, String> xmlLogMsg = new GetProperty().readXml(GkzxCommon.XMLLOGMSG, null);//获取Message
	/*
	 * 将新增的批次插入到用户通知表; 即将出监的信息插入到通知表
	 */
	public void autoInsertData() {
		try {
			List<String> departList = tbxfCommuteParoleBatchMapper
					.selectdepartidlist();
			if (departList.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < departList.size(); i++) {
					map.put("departid", departList.get(i));
					List<TbxfCommuteParoleBatch> list = userNoticeService
							.getBath(map);
					if (null != list && list.size() > 0) {
						TbuserNotice notice = new TbuserNotice();
						TbxfCommuteParoleBatch tcpb = list.get(0);
						String content = tcpb.getCuryear() + "年第"
								+ tcpb.getBatch() + "批减刑假释案件已启动！";
						//根据序列获取noticeid值
						int noticeid=TbuserNoticeMapper.selectNoticeid();
						notice.setNoticeid(noticeid);
						notice.setText1(tcpb.getCuryear().toString() + "_"
								+ tcpb.getBatch().toString());// 放入年号_批次
						notice.setMessagetype(3);
						notice.setTitle("新一批次案件提醒");
						notice.setContent(content);
						notice.setUsername(tcpb.getDepartid());
						notice.setEndtime(new Date());
						notice.setStarttime(new Date());
						notice.setState(0);
						notice.setOptime(new Date());
						notice.setOpid("sysauto");
						userNoticeService.insertDataToUserNotice(notice);
						
						
						//List<UserOrgWrapper> UserOrgWrapperList=userorgwrappermapper.getAllByOrgid(departList.get(i));
						List<UserOrgWrapper> UserOrgWrapperList=userorgwrappermapper.getIdsByDepartid(departList.get(i));
						for(UserOrgWrapper userOrgWrapper:UserOrgWrapperList){
							TbuserUserNotice usernotice=new TbuserUserNotice();
							usernotice.setNoticeid(noticeid);//通知id
							usernotice.setOpid("sysauto");
							usernotice.setOptime(new Date());
							usernotice.setUserid(userOrgWrapper.getUserid());//用户id
							tbuserUserNoticeMapper.insert(usernotice);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 自动删除通知表中的到期提醒事件
	 * 消息提示5天后系统删除
	 */
	public void autoDelMessage() {
		List<TbuserNotice> noticelist = new ArrayList<TbuserNotice>();
		noticelist = userNoticeService.getNoticeList();
		if (null != noticelist && 0 < noticelist.size()) {
			for (int i = 0; i < noticelist.size(); i++) {
				TbuserNotice tempnotice = new TbuserNotice();
				tempnotice = noticelist.get(i);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date datetime = tempnotice.getStarttime();//信息添加时间
				if (null != datetime && !"".equals(datetime)) {
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(datetime);
					cal.add(GregorianCalendar.DATE, 5);// 信息提示5天
					if (format.format(cal.getTime()).equals(
							format.format(new Date()))) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("noticeid", tempnotice.getNoticeid());
						map.put("state", 1);
						//通知表tbuser_notice表过期通知置为已读
						//userNoticeService.setEventStatus(map);
						//删除tbuser_user_notice表过期通知提示
						userNoticeService.delectNotice(tempnotice.getNoticeid().toString());
						//删除tbuser_notice表过期通知
						//userNoticeService.deleteNoticeByid(tempnotice.getNoticeid());
					}
				}
			}
		}
	}

	/*
	 * 即将出监罪犯提醒
	 * 刑期满前15日
	 */
	public void autoInsertChuJianData() {
		List<String> deptidlist = tbxfSentencealterationService.departidlist();//获取所有减刑罪犯所在监狱id
		if (deptidlist.size() > 0) {
			for (int j = 0; j < deptidlist.size(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("departid", deptidlist.get(j));
				map.put("name", "outprisonday");
				String day = null;
		        SystemConfigBean systemConfigBean = systemConfigService.getConfigByMap(map);//查询系统配置到达刑期止日天数
				if(!StringNumberUtil.isNullOrEmpty(systemConfigBean)){
					day = systemConfigBean.getValue()==null?"15":systemConfigBean.getValue();
				}
				List<TbxfSentenceAlteration> tblist = tbxfSentencealterationService
						.sentenceAlterationList(map);//获取所有没有出监提醒罪犯信息
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (int i = 0; i < tblist.size(); i++) {
					TbxfSentenceAlteration tsa = tblist.get(i);
					if (null != tsa.getCourtchangeto() && !"".equals(tsa.getCourtchangeto())) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime(new Date());
						cal.add(GregorianCalendar.DATE, Integer.parseInt(day));// 到达刑期止日前15天提醒     
						if (DateUtils.isAfter(cal.getTime(),tsa.getCourtchangeto())) {
							String name = tsa.getName();
							String crimid = tsa.getCrimid();
							String courtchangeto = sdf.format(tsa.getCourtchangeto());
							String areaname = tsa.getAreaname();
							String areaid=tsa.getAreaid();
							if (null != areaid && !"".equals(areaid)){
								//根据序列获取noticeid
								int noticeid=TbuserNoticeMapper.selectNoticeid();
								
								Map<String,Object> areaidMap=new HashMap<String,Object>();
								areaidMap.put("dorgid",areaid);
								//根据罪犯所在部门查询所能处理本部门事务的部门所有用户
								List<Map> areaidList =tbxfSentencealterationService.getAreaidList(areaidMap);
								String deptid = tsa.getJailid();
								TbuserNotice notice = new TbuserNotice();
								notice.setNoticeid(noticeid);
								notice.setContent(areaname + name + courtchangeto + "到达刑期止日,请做好准备工作！");
								notice.setTitle("刑期结束提醒");
								notice.setMessagetype(3);
								notice.setOpid("sysauto");
								notice.setUsername(deptid);
								notice.setStarttime(new Date());
								notice.setEndtime(new Date());
								notice.setOptime(new Date());
								notice.setState(0);
								notice.setText1(crimid);
								userNoticeService.insertDataToUserNotice(notice);
								for(int k=0;k<areaidList.size();k++){
									//用户插入通知
									if(null!=areaidList.get(k)){
										TbuserUserNotice usernotice=new TbuserUserNotice();
										usernotice.setNoticeid(noticeid);//通知id
										usernotice.setOpid("sysauto");
										usernotice.setOptime(new Date());
										String userid= areaidList.get(k).get("USERID").toString();
										usernotice.setUserid(userid);//用户id
										tbuserUserNoticeMapper.insert(usernotice);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	/*
	 * 用户通知
	 * */
	public void selectUserInfo() {
		
		List<UvFlow> uf = TbuserNoticeMapper.queryAllflowe("other_zfjyjxjssp");// 根据id来查看flow
		List<SystemUser> userlist = null;
		TbuserNotice notice = null;
		StringBuffer excuseBuffer = new StringBuffer();
		StringBuffer ex = new StringBuffer();
		for (int i = 0; i < uf.size(); i++) {
			UvFlow uvflow = uf.get(i);// 循环获得每个字段
			String departid = uvflow.getDepartid();
			userlist = systemusermapper.getUserIdByOrgids(departid);
			List<TbuserNotice> tn = TbuserNoticeMapper.queryNotice(uvflow.getApplyid());// 根据编号来查看notice
			if (tn.size() == 0) {// =0表示里面没有改信息，继续添加到notice表中
				String d = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); // 得到当前系统时间
				StringBuffer str = excuseBuffer.append(MessageFormat.format(
						xmlLogMsg.get("SCREEN.MSG.00041"), uvflow.getOpname(),
						uvflow.getCommenttext(), uvflow.getConent()));
				StringBuffer tit = ex.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00042"),"",""));
				str.append(",时间:" + d); // 拼接出时间
				int noticeid = TbuserNoticeMapper.selectNoticeid();
				notice = new TbuserNotice();
				notice.setNoticeid(noticeid);
				notice.setTitle(tit.toString());
				notice.setMessagetype(3);
				notice.setContent(str.toString());
				notice.setUsername(uvflow.getOpname());
				notice.setEndtime(new Date());
				notice.setStarttime(new Date());
				notice.setState(0);
				notice.setText1(uvflow.getApplyid());
				notice.setOptime(new Date());
				notice.setOpid("sysauto");
				notice.setRemark("casecheck");
				userNoticeService.insertDataToUserNotice(notice);
				for (int k = 0; k < userlist.size(); k++) {
					SystemUser user = userlist.get(i);
					String userid = user.getUserid();
					TbuserUserNotice usernotice = new TbuserUserNotice();
					usernotice.setNoticeid(noticeid);// 通知
					usernotice.setOpid("sysauto");
					usernotice.setOptime(new Date());
					usernotice.setUserid(userid);// 用户id
					tbuserUserNoticeMapper.insert(usernotice);
				}
			}
		}
	}
}
