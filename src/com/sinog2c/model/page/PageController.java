package com.sinog2c.model.page;

/**
 * 分页Model
 * @author 
 *
 */
public class PageController {
	
	private int dataCount;//数据总条数
	private int thisPage;//当前页
	private int pageSize;//每页显示条数
	private int pageCount;//总页数
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		pageCount = dataCount/pageSize;
		if(dataCount%pageSize != 0){
			pageCount++;
		}
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getDataCount() {
		return dataCount;
	}
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}
	public int getThisPage() {
		return thisPage;
	}
	public void setThisPage(int thisPage) {
		this.thisPage = thisPage;
	}
	public int getPrePage() {
		return thisPage - 1;
	}
	public int getNextPage() {
		return thisPage + 1;
	}
}
