package com.sinog2c.service.impl.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.system.SignatureSchemeService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sun.org.apache.commons.collections.MapUtils;

@Service("signatureSchemeService")
public class SignatureSchemeServiceImpl extends ServiceImplBase implements SignatureSchemeService {

	@Resource
	private SignatureSchemeMapper signatureSchemeMapper;

	@Override
	public int insert(SignatureScheme schema, SystemUser operator) {
		if(null == schema){
			return -1;
		}
		//
		if(null != operator){
		}
		//
		int result = signatureSchemeMapper.insertSelective(schema);
		return result;
	}
	@Override
	public int insertByMap(Map<String, Object> paraMap, SystemUser operator) {
		int row = signatureSchemeMapper.insertByMap(paraMap);
		return row;
	}

	@Override
	public int update(SignatureScheme scheme, SystemUser operator) {
		int row = signatureSchemeMapper.updateSelective(scheme);
		return row;
	}

	@Override
	public int updateByMap(Map<String, Object> paraMap, SystemUser operator) {
		int row = signatureSchemeMapper.updateByMap(paraMap);
		return row;
	}

	@Override
	public int delete(Integer signid,String toid,String tempid) {
		int row = signatureSchemeMapper.delete(signid,toid,tempid);
		return row;
	}

	@Override
	public SignatureScheme getById(Integer schemaid) {
		SignatureScheme systemLog = signatureSchemeMapper.getById(schemaid);
		return systemLog;
	}
	
	@Override
	public Map getMapById(Integer schemaid) {
		return MapUtil.turnKeyToLowerCase(signatureSchemeMapper.getMapById(schemaid));
	}
	@Override
	public Map getMapById_nx(Integer schemaid) {
		return MapUtil.turnKeyToLowerCase(signatureSchemeMapper.getMapById(schemaid));
	}
	@Override
	public int countAll(String key,String orgid,String tempid) {
		int result = signatureSchemeMapper.countAll(key,orgid,tempid);
		return result;
	}

	@Override
	public List<SignatureScheme> listAllByPage(int pageIndex, int pageSize, String sortField, String sortOrder,String key,String orgid,String tempid) {

		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		List<SignatureScheme> result = signatureSchemeMapper.listAllByPage(start, end, sortField, sortOrder,key,orgid,tempid);
		return result;
	}
	@Override
	public List<SignatureScheme> getListAll(HashMap map) {
		return signatureSchemeMapper.getListAll(map);
	}
	@Override
	public SignatureScheme selectSignatureScheme(String id) {
		return null;
	}
	@SuppressWarnings("all")
	public List<Map> batchExportHandCaseFile(HttpServletRequest request,SystemUser user){
		String scope = request.getParameter("fanwei");
		//存在 大字段的内容 保存到集合中
		List<Map> listMaps = new ArrayList<Map>();
		//导出不成功的罪犯 信息
		String orgid = user.getOrganization().getOrgid();
		//文件类型(前台已控制，不会为空)
		String writtype = request.getParameter("writtype");
		try {
			Map mapsMap = new HashMap();
			mapsMap.put("scope", scope);
			mapsMap.put("orgid", orgid);
			mapsMap.put("tempid", writtype);
			List<Map> handCase = this.signatureSchemeMapper.batchExportHandCaseFile(mapsMap);
			for (int i = 0; i < handCase.size(); i++) {
				Map maps= handCase.get(i);
				//大字段
				Clob clob =(Clob) maps.get("DOCCONENT");
				if (clob != null) {
					String docconent = clob.getSubString(1, (int)clob.length());
					//罪犯姓名 and 编号
					String crimid = maps.get("APPLYID") == null?"":maps.get("APPLYID").toString();
					String criname = maps.get("APPLYNAME") == null?"":maps.get("APPLYNAME").toString();
					String flowdraftid = maps.get("FLOWDRAFTID") ==null?"":maps.get("FLOWDRAFTID").toString();
					Map contentMaps = new HashMap();
					contentMaps.put("docconent", docconent);
					contentMaps.put("crimid", crimid);
					contentMaps.put("criname", criname);
					contentMaps.put("flowdraftid", flowdraftid);
					listMaps.add(contentMaps);
				}else{
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMaps;
	}
	@SuppressWarnings("all")
	public String savePDFDocToService(HttpServletRequest request){
		String fileUrl = request.getParameter("diskUrl");
		Object data = request.getParameter("data")==null?"":request.getParameter("data");
		//获取 服务器 相对路径
		String url = request.getRealPath(""); 
		File file = new File(fileUrl+""+data+".pdf");
		String value = "error";
		try {
			url=url+"\\sealfile\\noseal";
			//通过路径 读取 对应的文件
			InputStream isStream = new FileInputStream(file);
			File file2 = new File(url, data+".pdf");
			//把相应的文件 保存到服务器的某个路径下面
			OutputStream oStream = new FileOutputStream(file2);
			byte[] buffer = new byte[1024];
			int length=0;
			while(-1!=(length=(isStream.read(buffer)))){
				oStream.write(buffer, 0, length);
			}
			//关闭 输入输出流
			isStream.close();
			oStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("all")
	public Map signGetMaxCuryear(HttpServletRequest request, SystemUser user) {
		Map map = new HashMap();
		try {
			map.put("departid", user.getDepartid());
			map = signatureSchemeMapper.signGetMaxCuryearMapper(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public List<SignatureScheme> getSignatureSchemeList(HashMap map){
		return signatureSchemeMapper.getSignatureSchemeList(map);
	}
	
	@Override
	public List<SignatureScheme> getSignatureSchemeList_nx(HashMap map) {
		return signatureSchemeMapper.getSignatureSchemeList_nx(map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SignatureScheme> getCourtSignatureSchemeList(HashMap map) {
		return signatureSchemeMapper.getCourtSignatureSchemeList(map);
	}
	@Override
	public int getCountByDepartid(String fromid,String tempid) {
		int result = signatureSchemeMapper.getCountByDepartid(fromid,tempid);
		return result;
	}
	@Override
	public List<Map> getSignByDepart(){
		return MapUtil.turnKeyToLowerCaseOfList(signatureSchemeMapper.getSignByDepart());
	}

	@Override
	public void copySignByDepartid(String fromid,String toid,String tempid){
		signatureSchemeMapper.copySignByDepartid(fromid, toid, tempid);
	}
	
	@Override
	public List<SignatureScheme> getSignatureSchemesByUser( Map<String, Object> map){
		return signatureSchemeMapper.getSignatureSchemesByUser(map);
	}
	
	@Override
	public List<SignatureScheme> getSignatureSchemesByPsignid(Map<String, Object> map){
		return signatureSchemeMapper.getSignatureSchemesByPsignid(map);
	}
	
	@Override
	public SignatureScheme getPreSignatureScheme(SignatureScheme signatureScheme){
		Integer progress = signatureScheme.getProgress();
		signatureScheme.setProgress(progress-1);
		return signatureSchemeMapper.getPreSignatureScheme(signatureScheme);
	}
	
	@Override
	public SignatureScheme getSignatureSchemeByCondition(Map<String,Object> map){
		return signatureSchemeMapper.getSignatureSchemeByCondition(map);
	}
	
	@Override
	public String getSignatureSchemeByResid(String resid){
		//初始化批注个数、签章个数、签章进程
		String result = "-1@-1&-1";
		SignatureScheme sign= signatureSchemeMapper.getSignatureSchemeByResid("<"+resid+">");
		if(sign!=null){
			result = sign.getNotation()+"@"+sign.getSeal()+"&"+sign.getProgress();
		}
		return result;
	}
}
