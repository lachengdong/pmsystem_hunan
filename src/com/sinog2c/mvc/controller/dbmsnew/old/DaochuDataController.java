package com.sinog2c.mvc.controller.dbmsnew.old;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;
import com.sinog2c.mvc.controller.base.ControllerBase;

public class DaochuDataController extends ControllerBase {

//	private BaseDao baseDao;
//
//	public void setBaseDao(BaseDao baseDao) {
//		this.baseDao = baseDao;
//	}
//
//	public BaseDao getBaseDao() {
//		return baseDao;
//	}
//	
//    private DbmsDaochuListInfoBean bean = new DbmsDaochuListInfoBean();
//	@Override
//	public DbmsDaochuListInfoBean getModel() {
//		// TODO Auto-generated method stub
//		return bean;
//	}
//
//	private HttpServletRequest request;
//	@Override
//	public void setServletRequest(HttpServletRequest request) {
//		this.request = request;
//	}
//	private HttpServletResponse response;
//	
//	@Override
//	public void setServletResponse(HttpServletResponse rep) {
//		this.response = rep;
//		
//	}
//	
//	private GetAbsolutePath absolutePath=new GetAbsolutePath();
//	
//	
//	
//	/***
//	 * 查询导出方案列表
//	 * @return
//	 */
//	public String queryTableInfoList(){
//		String basicdep = "";
//		String type = request.getParameter("type");
//		request.setAttribute("imporexp", type);
//		try {
//			basicdep = (String)request.getSession().getAttribute("basicdep");
//		} catch (Exception e) {
//		}
//		
//		String countsql="";
//		if(!StringUtil.isNullOrEmpty(type) && type.equals("import")) {
//			countsql=" select count(*) from DbmsDaochuListInfo a where 1=1 and a.sdid ='" + basicdep + "' and a.remark='auto'";
//		} else {
//			countsql=" select count(*) from DbmsDaochuListInfo a where 1=1 and a.sdid ='" + basicdep + "' and a.remark is null";
//		}
//		
//		String sql="  order by nlssort(a.ddcname,'NLS_SORT=SCHINESE_PINYIN_M') asc";
//		try{
//		int count=baseDao._getCount(countsql);
//		int page = PageController.initPage(request);//当前页
//		int pageSize = 20;
//		PageController pc = new PageController(request, "pageController",
//				count, pageSize, page);
//		request.setAttribute("pageController", pc);
//		
////		List<Object> list = baseDao.queryListObjectAll(" from DbmsDaochuListInfo a order by a.createdate");
//		String hql = "";
//		if(!StringUtil.isNullOrEmpty(type) && type.equals("import")) {
//			hql="from DbmsDaochuListInfo a where a.sdid = '" + basicdep + "' and a.remark='auto' order by a.createdate desc ";
//		} else {
//			hql="from DbmsDaochuListInfo a where a.sdid = '" + basicdep + "' and a.remark is null order by a.createdate desc ";
//		}
//		List<Object> datalist = baseDao.queryListObjectAllForPage(hql, pageSize, page);
//		List<DbmsDaochuListInfo> ddList = new ArrayList<DbmsDaochuListInfo>();
//		DbmsDaochuListInfo ddl = new DbmsDaochuListInfo();
//		
//		for(Object lists : datalist){
//			ddl = (DbmsDaochuListInfo)lists;
//			//System.out.print(ddl.getFilerealname());
//			ddList.add(ddl);
//		}
//		
//		bean.setDataList(ddList);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return SUCCESS;
//	}
//	
//	
//	public String getDataInfo(){
//		
//		String fileRealName = "";
//		String filepath = "";
//		String fname = "";
//		String hqlString="from DbmsDaochuListInfo a where a.fileid='"+bean.getFileid().trim()+"'";
//		List<Object> dataList=baseDao.queryListObjectAll(hqlString);
//		if(dataList.size() > 0){
//			for(Object o:dataList){
//			DbmsDaochuListInfo ddli=(DbmsDaochuListInfo) o;
//			fileRealName = ddli.getFilerealname().trim();
//			if(!StringUtil.isNullOrEmpty(ddli.getFilename())) {
//				fname = ddli.getFilename().trim();
//			}
//			filepath = ddli.getFilepath();
//			}
//		}
//		
//		String basePath = absolutePath.getAbsolutePath( filepath);
//		
//		OutputStream outputStream=null;
//		try {
////			response.setContentType("application/octec-stream");
//			
//
//			response.setContentType("application/xml");
//			response.setHeader("Content-Disposition", "attachment;filename="
//					+ new String((fileRealName).getBytes(), "ISO-8859-1"));
//			FileInputStream fis = new FileInputStream(basePath);
//			outputStream = response.getOutputStream();
//			byte[] b = new byte[1024];
//			int len = 0;
//			while ((len = fis.read(b)) != -1) {
//				outputStream.write(b, 0, len);
//			}
//			outputStream.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				if(null!=outputStream){
//					outputStream.close();
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			outputStream = null;
//		}
//		return null;
//	}
//	
//	/***
//	 * 删除数据导出方案文件
//	 * 
//	 * @author 袁德民 2013-11-24
//	 * @return
//	 */
//	public String deleteDataInfo(){
//		
//		String fileRealName = "";
//		String hqlString="from DbmsDaochuListInfo a where a.fileid='"+bean.getFileid().trim()+"'";
//
//		List<Object> list=baseDao.queryListObjectAll(hqlString);
//		if(list.size()>0){
//			for(Object o:list){
//				DbmsDaochuListInfo ddli=(DbmsDaochuListInfo) o;
//				try {
//					  String fileName = ddli.getFilerealname();
//					  baseDao.deleteObject(ddli);
//					  String basePath = absolutePath.getAbsolutePath("/DownExcel/"+ fileName);
//					  File file = new File(basePath);
//					  if(file.exists()){
//							file.delete();
//						}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}	
//		}
//		return SUCCESS;
//	}
}
