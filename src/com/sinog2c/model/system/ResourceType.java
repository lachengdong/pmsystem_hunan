package com.sinog2c.model.system;

/**
 * 资源类型,暂时不考虑持久化. 等待后期优化和缓存
 */
public class ResourceType {
	
	/**
	 * 资源类型, 菜单
	 */
	public static final int  TYPE_ID_MENU = 10;
	/**
	 * 资源类型, 表单
	 */
	public static final int  TYPE_ID_FORM = 11;
	/**
	 * 资源类型, 顶部按钮
	 */
	public static final int  TYPE_ID_BUTTON_TOP = 12;
	/**
	 * 资源类型, 列中按钮
	 */
	public static final int  TYPE_ID_BUTTON_INLINE = 13;
	/**
	 * 报表,打印方案
	 */
	public static final int  TYPE_ID_REPORT = 14;
	/**
	 * aip控件按钮
	 */
	public static final int  TYPE_ID_BUTTON_AIP = 15;
	
	
    private int restypeid;

    private String name;

    public int getRestypeid() {
        return restypeid;
    }

    public void setRestypeid(int restypeid) {
        this.restypeid = restypeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}