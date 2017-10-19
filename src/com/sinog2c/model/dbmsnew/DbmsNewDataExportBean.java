package com.sinog2c.model.dbmsnew;

import java.io.File;
import java.util.List;

public class DbmsNewDataExportBean implements java.io.Serializable,Cloneable{
	private String ddid;
	private String ddcid;
	private List<String> titlelist; 
	private List<String> valuelist; 
	private List<DbmsDatasChemeDetailNew> lielist;
	private List<Object> panduanlist;
	private String condition;
	private String operate;
	private String operatetype;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	// 查询UUID
	private String queryuuid;
	public String getQueryuuid() {
		return queryuuid;
	}
	public void setQueryuuid(String queryuuid) {
		this.queryuuid = queryuuid;
	}
	public String getOperatetype() {
		return operatetype;
	}
	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	private String basetype;
	private int totalnum;
	private String hiddencon;
	private String insertonly;//只是插入不更新;1:新建；0：覆盖更新
	private String packageper;//按罪犯分包导出
	public String getPackageper() {
		return packageper;
	}
	public void setPackageper(String packageper) {
		this.packageper = packageper;
	}
	private List<DbmsDaochuListInfo> dataList;
	
	
	public List<DbmsDaochuListInfo> getDataList() {
		return dataList;
	}
	public void setDataList(List<DbmsDaochuListInfo> dataList) {
		this.dataList = dataList;
	}
	public String getInsertonly() {
		return insertonly;
	}
	public void setInsertonly(String insertonly) {
		this.insertonly = insertonly;
	}
	private File upload;// 封装上传文件
	private String uploadFileName;// 设置上传文件的文件名
	private String uploadContentType;// 上传文件的类型
	private List<DbmsDatasChemeNew> dataslist;
	private String basePathfile;
	
	//查询条件
	private String conditionMessage;
	private String depart;
	
	public String getBasePathfile() {
		return basePathfile;
	}
	public void setBasePathfile(String basePathfile) {
		this.basePathfile = basePathfile;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public List<DbmsDatasChemeNew> getDataslist() {
		return dataslist;
	}
	public void setDataslist(List<DbmsDatasChemeNew> dataslist) {
		this.dataslist = dataslist;
	}
	public String getHiddencon() {
		return hiddencon;
	}
	public void setHiddencon(String hiddencon) {
		this.hiddencon = hiddencon;
	}
	public String getBasetype() {
		return basetype;
	}
	public void setBasetype(String basetype) {
		this.basetype = basetype;
	}
	public List<DbmsDatasChemeDetailNew> getLielist() {
		return lielist;
	}
	public void setLielist(List<DbmsDatasChemeDetailNew> lielist) {
		this.lielist = lielist;
	}
	public List<Object> getPanduanlist() {
		return panduanlist;
	}
	public void setPanduanlist(List<Object> panduanlist) {
		this.panduanlist = panduanlist;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getDdid() {
		return ddid;
	}
	public void setDdid(String ddid) {
		this.ddid = ddid;
	}
	public String getDdcid() {
		return ddcid;
	}
	public void setDdcid(String ddcid) {
		this.ddcid = ddcid;
	}
	public List<String> getTitlelist() {
		return titlelist;
	}
	public void setTitlelist(List<String> titlelist) {
		this.titlelist = titlelist;
	}
	public List<String> getValuelist() {
		return valuelist;
	}
	public void setValuelist(List<String> valuelist) {
		this.valuelist = valuelist;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public int getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}
	public String getConditionMessage() {
		return conditionMessage;
	}
	public void setConditionMessage(String conditionMessage) {
		this.conditionMessage = conditionMessage;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
}
