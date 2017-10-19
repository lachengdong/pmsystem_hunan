package com.sinog2c.service.impl.commutationParole.sealfile;

import static com.sinog2c.util.common.StringNumberUtil.parseInt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.system.SignatureSchemeMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.sealfile.SealFileService;
import com.sinog2c.util.common.FileUtil;

@Service("sealFileService")
public class SealFileServiceImpl implements SealFileService {

	@Autowired
	private SignatureSchemeMapper signatureSchemeMapper;
	@Override
	@SuppressWarnings("all")
	public Object getSealFileList(HttpServletRequest request,SystemUser user) {
		// 页码,0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		// 是否签章
		String existSeal = request.getParameter("casetype")==null?"":request.getParameter("casetype");
		// 当前签章人的 签章进程
		String sealProgress = request.getParameter("sign")==null?"":request.getParameter("sign");
		// 选择 需要 签章的文书的类型(默认 减刑假释 审核表 )
		String writType = request.getParameter("writtype")==null?"JXJS_JXJSSHB":request.getParameter("writtype");
		
		
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);
		//通过当前登录人的部门 查询出 本部门的罪犯信息
		String orgid = user.getOrgid();
		String departid = user.getDepartid();
		String whereSql ="";//选择已签字 和未签章的 组织sql条件
		
		//重新 组织 map 结果集
		Map<Object,Map> criminalMap = new HashMap<Object,Map>();
		List listCrimid = new ArrayList();
		//判断选择的 已签章 还是未签章
		if ("sealedFolderName".equals(existSeal)) {
			whereSql = "to_number(decode(c2.text8,null,'0','','0',c2.text8)) >= "+sealProgress;//>= 当前进程的是已经签章的
		}else if("noSealFolderName".equals(existSeal)){
			whereSql = "to_number(decode(c2.text8,null,'0','','0',c2.text8)) < "+sealProgress;//<当前进程是未签章的
		}
		Map whereMap = new HashMap();
		whereMap.put("whereSql", whereSql);
		whereMap.put("orgid", orgid);
		whereMap.put("departid", departid);
		whereMap.put("writType", writType);
		List<Map> criMaps = signatureSchemeMapper.getCrimInfoByOrgid1(whereMap);
		for (int i = 0; i < criMaps.size(); i++) {
			Map criMap = criMaps.get(i);
			Object object = criMap.get("CRIMID");
			//罪犯编号 结果集
			listCrimid.add(object);
			/**
			 * 罪犯编号 对应 本罪犯的基本信息
			 * {120901112={Map}, ...}
			 */
			criminalMap.put(object, criMap);
		}
		
		int total = 0;
		List<Map<String,String>> fnList = null;
		//总数
		String noSealPath = request.getSession().getServletContext().getRealPath(GkzxCommon.SEALFOLDERNAME_NOSEAL);
		String path = request.getContextPath();//   格式为：/pmsys
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;//格式为：http://127.0.0.1:8089/pmsys
		String noSealPathUrl = basePath+GkzxCommon.SEALFOLDERNAME_NOSEAL+"/";//格式为：http://127.0.0.1:8089/pmsys/sealfile/noseal/
		File file = new File(noSealPath);
		FileFilter fileFilter = new FileFilter(){
			@Override
			public boolean accept(File f) {
				if(f.isFile()){
					return true;
				}else{
					return false;
				}
			}
			@Override
			public String getDescription() {
				return null;
			}
		};
		File[] fileArr  = FileUtil.listAll(file, fileFilter);
		if(fileArr!=null&&fileArr.length>0){
			total = fileArr.length;//fileArr.length;
			fnList = new ArrayList<Map<String,String>>();
			for(File tempfile : fileArr){
				//所有 文件名称 开始 都是 罪犯编号开头的
				String filename = tempfile.getName();
				String[] crimids = filename.split("_");
				if (crimids.length > 0) {
					String crimid = crimids[0];
					//判断 该罪犯 是否是 当前登录人 所属监区的罪犯
					if (listCrimid.contains(crimid)) {
						Map criInfo = criminalMap.get(crimid);
						Map<String,String> map = new HashMap<String,String>();
						map.put("filename", tempfile.getName());//文件名称
						map.put("filepath", noSealPathUrl+tempfile.getName());//文件路径
						map.put("crimid", criInfo.get("CRIMID").toString());//罪犯 编号
						map.put("name", criInfo.get("NAME").toString());//罪犯姓名
						map.put("text8", criInfo.get("TEXT8").toString());//签章进程
						map.put("text8_8", criInfo.get("TEXT8_8").toString());//签章进程数字
						//判断 filename是否是 所选择的的文书类型
						if (filename.contains(writType)) {
							fnList.add(map);
						}
					}
				}
			}
		}
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(fnList);
		return message;
	}
	
    public List<Map> getSignScheme(HttpServletRequest request,SystemUser user){
    	String userid = user.getUserid();
    	return signatureSchemeMapper.getSignSchemeType(userid);
    }
	
	public int updateSignProgressToFlowBase(HttpServletRequest request) {
		String object = request.getParameter("data") ==null?"":request.getParameter("data");
		String progress = request.getParameter("progress");//签章进程
		String writtype = request.getParameter("writtype");//建议书 和 意见书(签章进程)
		try {
			object = java.net.URLDecoder.decode(object, "UTF-8");
			List list = new ArrayList();
			if (!"".equals(object)) {
				String[] datas = object.toString().split(","); 
				for (int i = 0; i < datas.length; i++) {
					String[] crimid = datas[i].split("_");
					//罪犯编号+草稿id 唯一标示 单个罪犯 
					if ("JXJS_JXJSSHB".equals(writtype)) {
						list.add(crimid[0]+""+crimid[crimid.length-1].replace(".pdf", ""));//罪犯编号+草稿id
					}else{
						list.add(crimid[1]+""+crimid[crimid.length-1].replace(".pdf", ""));//草稿id+模板id
					}
					
				}
			}
			Map map = new HashMap();
			map.put("list", list);
			map.put("progress", progress);
			map.put("writtype", writtype);
			signatureSchemeMapper.updateSignProgressToFlowBase(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
