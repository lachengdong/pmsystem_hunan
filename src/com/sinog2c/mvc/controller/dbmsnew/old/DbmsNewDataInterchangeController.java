package com.sinog2c.mvc.controller.dbmsnew.old;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.dbmsnew.DbmsDatasChemeNewService;

/**
 * 数据交换Controller
 * @author 
 *
 */
@Controller
@RequestMapping("/dbms")
public class DbmsNewDataInterchangeController extends ControllerBase{
	
	@Resource
	private DbmsDatasChemeNewService dbmsDatasChemeNewService;
	
	/**
	 * 数据交换——数据交换方案下拉列表
	 */	
	@RequestMapping(value = "/downSchemaList.action")
	@ResponseBody
	public Object downSchemaList(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String sdid=user.getDepartid();//根据用户Id获取所在部门Id
		List<Map<String, Object>> list = dbmsDatasChemeNewService.selectBySdid(sdid);
		return list;
	}
//	private static final long serialVersionUID = 1L;
//
//	private HttpServletRequest request;
//	public void setServletRequest(HttpServletRequest request) {
//		this.request = request;
//	}
//	public void prepare() throws Exception {}
//
//	private DbmsNewDataInterchangeService dbmsNewDataInterchangeService;
//	
//	public DbmsNewDataInterchangeService getDbmsNewDataInterchangeService() {
//		return dbmsNewDataInterchangeService;
//	}
//	public void setDbmsNewDataInterchangeService(
//			DbmsNewDataInterchangeService dbmsNewDataInterchangeService) {
//		this.dbmsNewDataInterchangeService = dbmsNewDataInterchangeService;
//	}
//	
//	/**得到项目绝对路径*/
//	private GetAbsolutePathModelImpl absolutePath=new GetAbsolutePathModelImpl();
//	/**得到XML文件的路径*/
//	private String queryPath = absolutePath.getAbsolutePath("WEB-INF/classes/com/gkzx/dbmsnew/dbconfig");
//	/**导入数据的地址*/
//	private String importXMLPath=absolutePath.getAbsolutePath("dataxml/importxml");
//	
//	/**得到所有的数据交换的方案信息*/
//	public String getDataInterchangeChemeInfo(){
//		String basicdep = "";
//		try {
//			basicdep = (String)request.getSession().getAttribute("basicdep");
//		} catch (Exception e) {
//		}
//		List<DbmsDatasChemeNew> dbmsDatasChemeList=dbmsNewDataInterchangeService.getAllDataInterchangeInfo(basicdep);
//		ActionContext.getContext().put("datascheme", dbmsDatasChemeList);
//		return SUCCESS;
//	} 
//	
//	/**所选中的方案ID*/
//	public String selectOptionsId;
//	
//	/**得到自定义查询的信息*/
//	@SuppressWarnings("unchecked")
//	public String getCustomQueryInfo(){
//		String retemp=SUCCESS;
////		if(null!=selectOptionsId&&!"".equals(selectOptionsId)){
////			String queryDocName=selectOptionsId+"_Query";
////			Document document=Dom4jXML.inits(queryPath, queryDocName);//得到该方案的查询XML文件
////			//字段集合
////			List<String[]> strList = new ArrayList<String[]>();
////			Element root=document.getRootElement();
////			Element sy = root.element("SYLLABLE");
////			if(null!=sy){
////				//取出字段节点的子节点迭代器集合，
////				Iterator<Element> ies = sy.elementIterator();
////				while(ies.hasNext()){
////					//字段属性数组
////					String[] strs = new String[4];
////					Element e=ies.next();
////					Iterator<Attribute> ias = e.attributeIterator();
////					while(ias.hasNext()){
////						Attribute a=ias.next();
////						if("COLUMNSNAME".equals(a.getName())){
////							strs[0]=a.getValue();
////						}else if("COMM".equals(a.getName())){
////							strs[1]=a.getValue();
////						}else if("TYPE".equals(a.getName())){
////							strs[2]=a.getValue();
////						}else if("ISCODE".equals(a.getName())){
////							if(a.getValue()!=null)
////								strs[3]=a.getValue();
////						}
////					}
////					strList.add(strs);
////				}
////				List<String[]> logList=new ArrayList<String[]>();
////				Element log = root.element("LOGICSYMBOL");
////				Iterator<Element> logs = log.elementIterator();
////				while(logs.hasNext()){
////					String[] logStr=new String[2];
////					Element e=logs.next();
////					Iterator<Attribute> ias = e.attributeIterator();
////					while(ias.hasNext()){
////						Attribute a=ias.next();
////						if("KEY".equals(a.getName())){
////							logStr[0]=a.getValue();
////						}else if("VALUE".equals(a.getName())){
////							logStr[1]=a.getValue();
////						}
////					}
////					logList.add(logStr);
////				}
////				request.setAttribute("docName", queryDocName);
////				request.setAttribute("StringList", strList);
////				request.setAttribute("LogList", logList);
////				request.setAttribute("ChemeId", selectOptionsId);
////			}else{
////				this.addActionError("缺少数据源文件！");
////				retemp="commonPage";
////			}
////		}
//		return retemp;
//	}
//	
//	/**选中的条件*/
//	public String condition;
//	
//	/**得到数据信息*/
//	public String getInformation(){
//		//得到数据方案对象
//		DbmsDatasChemeNew dataCheme=dbmsNewDataInterchangeService.getDbmsDatasChemeInfoById(selectOptionsId);
//		//得到导入的数据库管理对象信息
//		DbmsDatabaseNew database=dbmsNewDataInterchangeService.getDbmsDatabaseById(dataCheme.getFromdatabaseid());
//		Connection conn=dbmsNewDataInterchangeService.getConnection(database);
//		String tion=null;
//		if(null!=condition&&!"".equals(condition)){
//			//条件分开
//			String[] single=condition.split(";");
//			for(int i=0;i<single.length;i++){
//				//得到单个条件
//				String[] sing=single[i].split(",");
//				if(sing.length<=3){
//					if(tion==null){
//						tion=sing[0];
//					}else{
//						tion+=" and "+sing[0];
//					}
//					tion+=replaceLog(sing[1],sing[2]);
//				}else{
////					System.out.println("介于");
//				}
//			}
//		}
//		if(conn!=null){
//			String queryDocName=selectOptionsId+"_Query";
//			Document document=Dom4jXMLUtil.inits(queryPath, queryDocName);//得到该方案的查询XML文件
//			Element root=document.getRootElement();
//			Element SQLElement=root.element("SQL");
//			String SQL=SQLElement.getText();//得到SQL语句
//			Element coumlus=root.element("SYLLABLE");//得到视图的所有列
//			Iterator<Element> it=coumlus.elementIterator();
//			List<String> tableInfo=new ArrayList<String>();//列名
//			List<String> coumlusComm=new ArrayList<String>();//列描述
//			while(it.hasNext()){
//				Element coumlu=it.next();
//				coumlusComm.add(coumlu.attributeValue("COMM"));
//				tableInfo.add(coumlu.attributeValue("COLUMNSNAME"));
//			}
//			String tableName=SQL.substring(SQL.lastIndexOf("from")+4,SQL.length()).trim();//截取SQL语句得到表名
//			if(null!=tion&&!"".equals(tion)){
//				SQL+=" where "+tion;
//			}
////			List<String> tableInfo=getTableInfo(conn,SQL);//根据SQL语句得到表信息
//			Statement st=null;
//			ResultSet rs=null;
//			try {
//				st=conn.createStatement();
//				rs=st.executeQuery(SQL);
//				//将得到的ResultSet转换成Object数组的集合
//				List<Object[]> objList=replaceResultSet(rs,tableInfo);
//				//列描述
//				request.setAttribute("coumlusComm", coumlusComm);
//				request.setAttribute("valueList", objList);
//				request.setAttribute("coulmList", tableInfo);
//				//文件名称如：  1information20100101000000.xml
//				String docName="ViewQuery@"+selectOptionsId+"information"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
////				String tableId = dbmsNewDataInterchangeService.getOperationTableId(tableName,selectOptionsId);
//				request.setAttribute("tableName", tableName);
//				request.setAttribute("docName", docName);
//				request.setAttribute("path", importXMLPath);
//				request.setAttribute("chemeId", selectOptionsId);
////				request.setAttribute("tableId", tableId);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}finally{
//				try {
//					//关闭JDBC连接
//					rs.close();
//					st.close();
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return SUCCESS;
//	}
//	
//	/**替换逻辑符号 (不包括介于)*/
//	private String replaceLog(String log,Object value){
//		String don=null;
//		String sqlValue=null;
//		//如果是时间格式就转换
//		try{if(value.toString().split("-").length>1){
//			sqlValue="to_date('"+value+"','yyyy-MM-dd')";
//		}else{
//			sqlValue=value.toString();
//		}
//		//等于
//		if(log.equals("eqeq")){
//			don=" = '"+sqlValue+"'";
//		}else if(log.equals("!eq")){
//			don=" != '"+sqlValue+"'";
//		}else if(log.equals("1gt")){
//			don=" > "+sqlValue;
//		}else if(log.equals("1lt")){
//			don=" < "+sqlValue;
//		}else if(log.equals("1gteq")){
//			don=" >= "+sqlValue;
//		}else if(log.equals("1lteq")){
//			don=" <= "+sqlValue;
//		}else if(log.equals("like")){
//			don=" like '%"+sqlValue+"%'";
//		}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return don;
//	}
//	
//	/**得到表信息*/
//	private List<String> getTableInfo(Connection conn,String SQL){
//		List<String> str=new ArrayList<String>();
//		try {
//			Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
//			ResultSet rs=st.executeQuery(SQL);
//			ResultSetMetaData rsmd=rs.getMetaData();
//			for(int i=1;i<=rsmd.getColumnCount();i++){
//				String name=rsmd.getColumnName(i);
//				str.add(name);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return str;
//	}
//
//	/**将ResultSet 转换成 List
//	 * @param rs	得到的ResultSet对象
//	 * @param cname	得到的表信息集合
//	 * */
//	private List<Object[]> replaceResultSet(ResultSet rs,List<String> cname) throws SQLException{
//		List<Object[]> list = new ArrayList<Object[]>();   
//		try{while(rs.next()){
//			Object[] obj=new Object[cname.size()];
//			for(int i=0;i<cname.size();i++){
//				obj[i]=rs.getObject(cname.get(i));
//			}
//			list.add(obj);
//		}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//	//删除文件
//	public String dataInterChangeSuccess(){
//		try{if(null!=condition&&!"".equals(condition)){
//			File f=new File(condition);
//			if(f.exists()){
//				File[] files=f.listFiles();
//				for(int i=0;i<files.length;i++){
//					File file=files[i];
//					file.delete();
//				}
//			}
//		}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return SUCCESS;
//	}
	
}
