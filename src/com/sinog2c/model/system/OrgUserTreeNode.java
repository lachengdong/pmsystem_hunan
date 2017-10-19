package com.sinog2c.model.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 部门与用户树的节点
 */
public class OrgUserTreeNode {
	/**
	 * 节点 id
	 */
	private String id;
	/**
	 * 节点 pid
	 */
    private String pid;
    /**
     * 节点名字
     */
    private String name;
    /**
     * 用户ID,用户独有
     */
	private String userid;
	/**
	 * 机构ID，用户和机构都有
	 */
	private String orgid;
	/**
	 * 机构父ID，机构独有
	 */
	private String porgid;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getPorgid() {
		return porgid;
	}
	public void setPorgid(String porgid) {
		this.porgid = porgid;
	}
	
	public static OrgUserTreeNode parseOrg(SystemOrganization org){
		if(null == org){
			return null;
		}
		OrgUserTreeNode node = new OrgUserTreeNode();
		node.setId(org.getOrgid());
		node.setPid(org.getPorgid());
		node.setName(org.getName());
		node.setUserid(null);
		node.setOrgid(org.getOrgid());
		node.setPorgid(org.getPorgid());
		//
		return node;
	}

	public static List<OrgUserTreeNode> parseOrgList(List<SystemOrganization> orgs){
		if(null == orgs){
			return null;
		}
		List<OrgUserTreeNode> result = new ArrayList<OrgUserTreeNode>();
		//
		Iterator<SystemOrganization> iteratorO = orgs.iterator();
		while (iteratorO.hasNext()) {
			SystemOrganization systemOrganization = (SystemOrganization) iteratorO.next();
			//
			OrgUserTreeNode node = parseOrg(systemOrganization);
			if(null != node){
				result.add(node);
			}
		}
		//
		return result;
	}
	
	public static OrgUserTreeNode parseUser(SystemUser user){
		if(null == user){
			return null;
		}
		OrgUserTreeNode node = new OrgUserTreeNode();
		node.setId(user.getUserid());
		node.setPid(user.getOrgid());
		node.setName(user.getName());
		node.setUserid(user.getUserid());
		node.setOrgid(user.getOrgid());
		node.setPorgid(null);
		//
		return node;
	}
	

	public static List<OrgUserTreeNode> parseUserList(List<SystemUser> users){
		if(null == users){
			return null;
		}
		List<OrgUserTreeNode> result = new ArrayList<OrgUserTreeNode>();
		//
		Iterator<SystemUser> iteratorU = users.iterator();
		while (iteratorU.hasNext()) {
			SystemUser user = (SystemUser) iteratorU.next();
			//
			OrgUserTreeNode node = parseUser(user);
			if(null != node){
				result.add(node);
			}
		}
		//
		return result;
	}
}
