package com.sinog2c.service.impl.flow;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import srvSeal.PdfAutoSeal;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfPunishmentRangMapper;
import com.sinog2c.dao.api.flow.FlowArchivesMapper;
import com.sinog2c.dao.api.flow.FlowBaseArchivesMapper;
import com.sinog2c.dao.api.flow.FlowMapper;
import com.sinog2c.model.flow.Flow;
import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.flow.FlowArchivesCode;
import com.sinog2c.model.flow.FlowBaseArchives;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("flowArchivesService")
public class FlowArchivesServiceImpl extends ServiceImplBase implements FlowArchivesService{
	@Autowired
    private FlowArchivesMapper flowArchivesMapper;
	@Autowired
	private FlowBaseArchivesMapper flowBaseArchivesMapper;
	@Autowired
	private FlowMapper flowMapper;
	@Autowired
	private TbxfPunishmentRangMapper tbxfPunishmentRangMapper;
	@Autowired
	private UvFlowService uvFlowService;
	
	public FlowArchivesMapper getFlowArchivesMapper() {
		return flowArchivesMapper;
	}

	public void setFlowArchivesMapper(FlowArchivesMapper flowArchivesMapper) {
		this.flowArchivesMapper = flowArchivesMapper;
	}

	public int countAll() {
        return this.flowArchivesMapper.countAll();
    }
	
	public String getArchiveid(String departid) {
		return this.flowArchivesMapper.getArchiveid(departid);
	}

    public FlowArchives findByFlowid(String flowid) {
        return this.flowArchivesMapper.findByFlowid(flowid);
    }
    
    public FlowArchives findByArchiveid(String archiveid){
    	return this.flowArchivesMapper.findByArchiveid(archiveid);
    }
//    @Transactional
//    public int insert(FlowArchives flowArchives) {
//    	return this.flowArchivesMapper.insert(flowArchives);
//    }
    @Transactional
    public int insertSelective(FlowArchives flowArchives) {
        return this.flowArchivesMapper.insertSelective(flowArchives);
    }
    @Transactional
    public int delete(String id) {
    	return this.flowArchivesMapper.delete(id);
    }

    public List<FlowArchives> selectAll() {
        return this.flowArchivesMapper.selectAll();
    }
    
    public List<FlowArchives> selectAllWithOutClob() {
    	return this.flowArchivesMapper.selectAllWithOutClob();
    }

	@Override
	public List<HashMap<Object, Object>> selectAllByArchDoc(String personid) {
		return this.flowArchivesMapper.selectAllByArchDoc(personid);
	}

	@Override
	public int insertArchtive(String resid, String flowdraftid,
			String personid, String personname, String docconent,
			SystemUser user, FlowArchivesCode code) {
		//用户对象
		SystemLog log = new SystemLog();
		FlowArchives flowArchives = new FlowArchives();
		FlowBaseArchives flowBaseArchives = new FlowBaseArchives();
		JSONMessage message = JSONMessage.newMessage();
		
		//定义变量
		int rows = 0;
		String orgid = "";
		String flowid = "";
		String departid = "";
		Date now = new Date();
		String rank = GkzxCommon.ONE;//涉密程度
		String flowdefid = "arch_zfdajdsp";//档案流程自定义ID
		
		SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.YEARFORMAT);
		
		if(user!=null){
			orgid = user.getOrganization().getOrgid();
			departid = user.getDepartid();
		}
		
		try{
			//判断该档案是否归档，如果归档就返回档案ID，没有就自动生成档案ID
			String archiveid = flowArchivesMapper.judgeArchivesWhetherArchiving(personid, code.getCodeid());
			if(StringNumberUtil.isNullOrEmpty(archiveid)){
				archiveid = flowArchivesMapper.getArchiveid(user.getDepartid());
				if(!StringNumberUtil.isNullOrEmpty(archiveid)){
					String tempflowval = flowMapper.getFlowid(orgid,departid,flowdefid,1);
					//判断该单位是否存在流程
					if(tempflowval.length()>0){
						String[] flowval = tempflowval.split(",");
						flowid = flowval[0];
					}
					//保持档案大字段
					flowArchives.setArchiveid(archiveid);
					flowArchives.setDepartid(departid);
					flowArchives.setDocconent(docconent);
					flowArchives.setFlowid(flowid);
					flowArchives.setOpid(user.getUserid());
					flowArchives.setOptime(now);
					flowArchives.setSn(String.valueOf(tbxfPunishmentRangMapper.getPunid()));
					flowArchives.setText1(flowdraftid);
					rows = flowArchivesMapper.insertSelective(flowArchives);
					
					String docyear = sdf.format(now);
					String name = code.getName();//档案名称
					String isattached = String.valueOf(code.getIspositive());//是否正副卷
					String docid = code.getCodeid();//档案类型
					String classifcation = code.getPcodeid()==null?"":code.getPcodeid();//档案类别
					if(GkzxCommon.ONE.equals(isattached)){
						rank = GkzxCommon.THREE;
					}
					//保存历史档案
					flowBaseArchives.setPersonid(personid);
					flowBaseArchives.setPersonname(personname);
					flowBaseArchives.setArchiveid(archiveid);
					flowBaseArchives.setDepartid(departid);
					flowBaseArchives.setDocyear(Integer.parseInt(docyear));
					flowBaseArchives.setClassification(classifcation);
					flowBaseArchives.setDocid(docid);
					flowBaseArchives.setName(name);
					flowBaseArchives.setIsattached(Short.valueOf(isattached));
					flowBaseArchives.setOpid(user.getUserid());
					flowBaseArchives.setOptime(now);
					flowBaseArchives.setRetention(Short.valueOf("10"));
					flowBaseArchives.setType(Short.valueOf(GkzxCommon.ONE));
					flowBaseArchives.setRank(Short.valueOf(rank));
					rows = flowBaseArchivesMapper.insert(flowBaseArchives);
				}
			}else{
				if(!StringNumberUtil.isNullOrEmpty(archiveid)){
//					String tempflowval = flowMapper.getFlowid(orgid,departid,flowdefid,1);
//					//判断该单位是否存在流程
//					if(tempflowval.length()>0){
//						String[] flowval = tempflowval.split(",");
//						flowid = flowval[0];
//					}
					//保持档案大字段
					flowArchives.setArchiveid(archiveid);
					flowArchives.setDocconent(docconent);
					flowArchives.setOpid(user.getUserid());
					flowArchives.setOptime(now);
					flowArchives.setSn(String.valueOf(tbxfPunishmentRangMapper.getPunid()));
//					flowArchives.setDepartid(departid);
//					flowArchives.setFlowid(flowid);
//					flowArchives.setText1(flowdraftid);
					rows = flowArchivesMapper.update(flowArchives);
				}
			}
			
			

			
		}catch(Exception e){
			e.printStackTrace();
		}
		return rows;
	}
	
	@Override
	public int fileCaseData(Map<String, Object> caseFileDataMap,SystemUser user){
		
		int rows = 0;
		
		if(null != caseFileDataMap){
			//用户对象
			FlowArchives flowArchives = new FlowArchives();
			FlowBaseArchives flowBaseArchives = new FlowBaseArchives();
			
			
			String orgid = user.getOrgid();
			String departid = user.getDepartid();
			String filedate = caseFileDataMap.get("filedate")==null?"":caseFileDataMap.get("filedate").toString();
			String applyid = caseFileDataMap.get("applyid").toString();
			String applyname = caseFileDataMap.get("applyname").toString();
			String flowid = "";
			String rank = GkzxCommon.ONE;//涉密程度
			String flowdefid = "arch_zfdajdsp";//档案流程自定义ID
			if(StringNumberUtil.isEmpty(filedate)){
				SimpleDateFormat sdf = new SimpleDateFormat(GkzxCommon.YEARFORMAT);
				filedate = sdf.format(new Date());
			}
			
			//获取归档ID
			String archiveid = flowArchivesMapper.getArchiveid(departid);
			String tempFlowid = flowMapper.getFlowid(orgid,departid,flowdefid,1);
			//判断该单位是否存在流程
			if(tempFlowid.length()>0){
				String[] flowval = tempFlowid.split(",");
				flowid = flowval[0];
			}
			
			String aipFileStr = caseFileDataMap.get("aipFileStr").toString();
			String savetype = caseFileDataMap.get("savetype")==null? "1": caseFileDataMap.get("savetype").toString();//保存类型	1：保存至目录，2：保存至DB
			
			String flowdraftid = caseFileDataMap.get("flowdraftid")==null?"":caseFileDataMap.get("flowdraftid").toString();
			if("2".equals(savetype.trim())){//存至数据库
				
				//保持档案大字段
				flowArchives.setArchiveid(archiveid);
				flowArchives.setDepartid(departid);
				flowArchives.setDocconent(aipFileStr);
				flowArchives.setFlowid(flowid);
				flowArchives.setOpid(user.getUserid());
				flowArchives.setOptime(new Date());
				flowArchives.setSn(String.valueOf(tbxfPunishmentRangMapper.getPunid()));
				flowArchives.setText1(flowdraftid);
				rows = flowArchivesMapper.insertSelective(flowArchives);
				if(rows <= 0){
					throw new RuntimeException();
				}
			}else{//存至目录
				rows = uvFlowService.saveFlowArchives(aipFileStr,flowid,flowdraftid,applyid,user);
				if(rows <= 0){
					throw new RuntimeException();
				}
			}
			
			
			String name = caseFileDataMap.get("tempname").toString();//档案名称
			String isattached = String.valueOf("1");//是否正副卷
			String docid = caseFileDataMap.get("codeid").toString();//档案类型
			String classifcation = docid.substring(0,3);//档案类别
			
			if(GkzxCommon.ONE.equals(isattached)){
				rank = GkzxCommon.THREE;
			}
			
			//保存历史档案
			flowBaseArchives.setPersonid(applyid);
			flowBaseArchives.setPersonname(applyname);
			flowBaseArchives.setArchiveid(archiveid);
			flowBaseArchives.setDepartid(departid);
			flowBaseArchives.setDocyear(Integer.parseInt(filedate));
			flowBaseArchives.setClassification(classifcation);
			flowBaseArchives.setDocid(docid);
			flowBaseArchives.setName(name);
			flowBaseArchives.setIsattached(Short.valueOf(isattached));
			flowBaseArchives.setOpid(user.getUserid());
			flowBaseArchives.setOptime(new Date());
			flowBaseArchives.setRetention(Short.valueOf("10"));
			flowBaseArchives.setType(Short.valueOf(GkzxCommon.ONE));
			flowBaseArchives.setRank(Short.valueOf(rank));
			flowBaseArchives.setRemark("归档完成");
			
			rows = flowBaseArchivesMapper.insert(flowBaseArchives);
			if(rows <= 0){
				throw new RuntimeException();
			}
		}
		
		return rows;
	}
	
	
	public int fileCaseDataUpdate(Map<String, Object> caseFileDataMap,String archiveid, SystemUser user){
		String aipFileStr = caseFileDataMap.get("aipFileStr").toString();
		int rows = 0;
		if(!StringNumberUtil.isNullOrEmpty(archiveid)){
			String savetype = caseFileDataMap.get("savetype") == null ? "" : caseFileDataMap.get("savetype").toString();
			if(StringNumberUtil.notEmpty(savetype) && "2".equals(savetype)){
				FlowArchives flowArchives = new FlowArchives();
				//保持档案大字段
				flowArchives.setArchiveid(archiveid);
				flowArchives.setDocconent(aipFileStr);
				flowArchives.setOpid(user.getUserid());
				flowArchives.setOptime(new Date());
				flowArchives.setSn(String.valueOf(tbxfPunishmentRangMapper.getPunid()));
//				flowArchives.setDepartid(departid);
//				flowArchives.setFlowid(flowid);
//				flowArchives.setText1(flowdraftid);
				rows = flowArchivesMapper.update(flowArchives);
			}else{
//				saveCaseFileToHD(aipFileStr, jailid, personid, archiveid);
			}
		}
		return rows;
		
	}
	
	
	
	
	
	
	
	/**
     * 更新罪犯电子档案信息
     */
	@Transactional
	public int updateArchtive(String flowid,String flowdraftid,
			String commenttext,String docconent,SystemUser user) {
		//用户对象
		Date now = new Date();
		Flow flow = new Flow();
		FlowArchives flowArchives = new FlowArchives();
		int rows = 0;
		
		//更新flow表
		flow.setNodeid("N0002");
		flow.setState(Short.valueOf("-1"));
		flow.setOpid(user.getUserid());
		flow.setOptime(now);
		flow.setOpname(user.getName());
		flow.setCommenttext(commenttext);
//		flow.setFlowdraftid(Integer.valueOf(flowdraftid));
		flow.setFlowdraftid(flowdraftid);
		rows = flowMapper.updateByFlowdraftid(flow);
		//更新罪犯电子档案信息
		flowArchives.setFlowid(flowid);
		flowArchives.setDocconent(docconent);
		flowArchives.setOpid(user.getUserid());
		flowArchives.setOptime(now);
		rows = flowArchivesMapper.update(flowArchives);
		return rows;
	}

	@Override
	public List<HashMap<Object, Object>> selectAllByArchDocNoFlow(
			Map<String, Object> map) {
		return this.flowArchivesMapper.selectAllByArchDocNoFlow(map);
	}
	
	@Override
	public String getArchiveDocconentByFlowid(String flowid){
		String docconent = null;
		FlowArchives flowArchives = flowArchivesMapper.findByFlowid(flowid);
		if(flowArchives!=null){
			if(!StringNumberUtil.isNullOrEmpty(flowArchives.getInt1()) && flowArchives.getInt1() == 1) {
				//从文件读取
				try {
//					docconent = FileUtil.readFromFile(GetAbsolutePath.getAbsolutePath(flowArchives.getDocconent()),GkzxCommon.encoding);
					Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
					String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
					String targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, flowArchives.getDocconent());					
					docconent = FileUtil.readFromFile(targetPath,GkzxCommon.encoding);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				//从数据库读取
				docconent = flowArchives.getDocconent();
			}
		}
		
		return docconent;
	}
    
	@Override
    public String getArchiveDocconentByArchiveid(String archiveid){
		String docconent = null;
		FlowArchives flowArchives = flowArchivesMapper.findByArchiveid(archiveid);
		if(flowArchives!=null){
			if(!StringNumberUtil.isNullOrEmpty(flowArchives.getInt1()) && flowArchives.getInt1() == 1) {
				//从文件读取
				try {
//					docconent = FileUtil.readFromFile(GetAbsolutePath.getAbsolutePath(flowArchives.getDocconent()),GkzxCommon.encoding);
					Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
					String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
					String targetPath = GetAbsolutePath.getAbsolutePathAppend(strPath, flowArchives.getDocconent());
					docconent = FileUtil.readFromFile(targetPath,GkzxCommon.encoding);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				//从数据库读取
				docconent = flowArchives.getDocconent();
			}
		}
		
		return docconent;
		
    }

	@Override
	public FlowArchives getArchivesData(Map map) {
		return flowArchivesMapper.getArchivesData(map);
	}

	@Override
	public String getDocconent(Map map) {
		return flowArchivesMapper.getDocconent(map);
	}
	
	
	/**
	 * 2016/5/17 修改 by luan
	 */
	@Override
	public Object getOneArchive(String applyidorgids, String archiveid) {
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
		String srcPath = GetAbsolutePath.getAbsolutePathAppend(strPath, GkzxCommon.CRIMINALARCHIVES_PATH);
		String strPdfPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVESPDF_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVESPDF_ABSOLUTEPATH);
		String srcPdfPath = GetAbsolutePath.getAbsolutePathAppend(strPdfPath, GkzxCommon.CRIMINALARCHIVES_PATH);

		int intIndex = applyidorgids.indexOf("|");
		String orgid = "";
		String applyid = "";
		if (intIndex > 0) {
			applyid = applyidorgids.substring(0, intIndex);
			orgid = applyidorgids.substring(intIndex + 1);
			String desArchPath = srcPdfPath + File.separator + orgid + File.separator + applyid;
			String srcArchPath = srcPath + File.separator + orgid + File.separator + applyid;
			try {
				//目录里的文件转成pdf文件
				FileUtil.batchCopyFileAipToPdf(srcArchPath, desArchPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//导出档案表里的大字段成pdf
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("applyids", applyid);
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			list = flowArchivesMapper.getOneArchive(map);
			if (list != null && list.size() > 0) {
				for(int i=0; i<list.size()-1; i++) {
					map = list.get(i);
					String docconentStr = getArchiveDocconentByArchiveid(map.get("ARCHIVEID"));
					
					try {
						PdfAutoSeal.aipAutoPdf("STRDATA:"+docconentStr,desArchPath + File.separator + map.get("ARCHIVEID")+ ".pdf");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} 
		}

		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> retmap = new HashMap<String,String>();
		retmap.put("theend", "1");
		list.clear();
		list.add(retmap);
		return list;
	}
	
	
	@Override
	public List<Map<String,Object>> getFlowarchiveidByFlowid(String flowids){
		return MapUtil.convertKeyList2LowerCase(flowArchivesMapper.getFlowarchiveidByFlowid(flowids));
	}
    
}