package com.gkzx.common;

import java.util.HashMap;

public class OAParameter {
	public static final String SYS_NAME = "国科协同办公系统";
	// 流程标示
	public static final String START = "START";
	public static final String JPMEND = "YESEND";
	public static final String JBPMYESEND = "YESEND";
	public static final String JBPMNOEND = "NOEND";
	public static final String JBPMBACKEND = "BACKEND";
	public static final String JBPMBACK = "BACK";
	public static final String SHENPIZHONG = "审批中...";
	public static final String RESULT = "result";
	public static final String ZERO = "0";
	public static final String MINUS_ONE = "-1";
	public static final String NINENINE = "99";
	public static final String ONE = "1";
	public static final String TWO = "2";
	public static final String THREE = "3";
	public static final String FOUR = "4";
	public static final String FIVE = "5";
	public static final String EIGHT = "8";
	public static final String SELF = "ziji";
	public static final String YEARONE = "201301";
	public static final long COMMON_APPROVEID = 0;
	public static final int PAGESIZE = 20;

	public static final String STARTNODE = "0";

	public static final String STATE_START = "0"; // 新任务
	// public static final String STATE_SUBMIT = "2";
	public static final String STATE_FINISHED = "1"; // 已经完成的任务
	public static final String STATE_Waitting = "3"; // 多人任务，自己已经完成等到别人完成
	public static final String STATE_RETURN = "2"; // 被退回的任务
	public static final String STATE_DOING = "4";
	public static final String STATE_Draft = "5"; // 草稿

	//页面顶部按钮
	public static final int FORMTOPBUTTON = 12;
	//页面aip button
	public static final int FORMAIPBUTTON = 15;	
	//grid列中按钮
	public static final int GRIDBUTTON=13;
	//tabs按钮
	public static final int TABSBUTTON=23;	
	//Grid右键菜单
	public static final int GRIDCONTEXTMENU=24;
	
	public static final int LEFTOUTLOOKMENU=25;
	
	//流程按钮
	public static final int FLOWBUTTON = 8;
	
	
	public static final String FLOWRESPOSEYES = "1";
	public static final String FLOWRESPOSENO = "0";
	public static final String FLOWRESPOSERETURN = "-1";
	public static final String ENCRYPTKEY = "MWxZbdAWVP1O+znsajkg5A==";
	public static final String EMAILMODUEL = "OA_EMAIL_CONTENT";

	public static final String WEBSOCKET_USERNAME = "userName";
	public static final String WEBSOCKET_USERID = "userID";

	public static final String OATEMPLATETYPE = "3";
	// /{module,model,path}
	// public static final String[][] FileFolderPrivate = { };
	public static final HashMap<String, String[]> attachmentsConfig = new HashMap<String, String[]>();
	static {
		attachmentsConfig.put("privateFolder", new String[] { "file",
				"file_folder_private", "private" ,"attachment"});
		attachmentsConfig.put("publicFolder", new String[] { "file",
				"file_folder_public", "public" ,"attachment"});
		attachmentsConfig.put("archive", new String[] { "archive",
				"archive", "file" ,"attachment"});
	}
}
