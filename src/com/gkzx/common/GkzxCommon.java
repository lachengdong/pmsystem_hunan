package com.gkzx.common;

/**
 * 定义系统常量类
 * @project pmisystem
 * @module systemManage
 * @class GkzxCommon
 * @author zwq
 * @data May 6, 20137:24:04 PM
 * 
 */
public class GkzxCommon { 
	
	public static final String FILECOPY="filecopy";
	

	public static final String PROCESSUPLOADPATH = "\\Upload";
	//媒体信息图片保存格式
//	public static final String MEDIAUPLOAD = "D:\\mediaupload";
	//main包下的hql存放文件名
	
	public static final String CONNECTION="connection";
	public static final String DATABASE_ORACLE="Oracle";
	public static final String DATABASE_DAMENG="dm";
	public static final String JGXT="jgxt";
	//扣分延迟类型
	public static final String JGXT_DELAY_KF="0";
	//奖分延迟类型
	public static final String JGXT_DELAY_JF="1";
	//考核起日延迟类型
	public static final String JGXT_DELAY_KHQR="2";
	public static final String TBCRIMINAL_JGXT_KHJF="TBCRIMINAL_JGXT_KHJF";
	public static final String TBCRIMINAL_JGXT_YZJC="TBCRIMINAL_JGXT_YZJC";
	public static final String JGXT_BH="bh";
	public static final String TBCRIMINAL_JXFD_LEIJI_ZH="TBCRIMINAL_JXFD_LEIJI_ZH";
//	public static final String ISCOURT="iscourt";
	
	public static final String ADDCOLUMNS="addcolumns";
	
	public static final String MAINHQL="mainhql";
	
	public static final String DATABASETYPE ="jyconfig";
	public static final String ISPOPBOX ="ispopbox";//退回、拒绝是否用弹出框1启用0不启用
	
	public static final String CRIMID ="CRIMID";
	public static final String CRIMID_AS =" CRIMID";
	public static final String XINGQIBIANDONG_TABLE ="TBYZH_CHANGE_IMPRISON_TERM";
	public static final String TONGANFAN_TABLE = "TBCRIMINAL_TONGANFAN";
	public static final String TABLE_DESCRIBE = "判裁信息表";
	public static final String XFZB ="XFZB";
	public static final String AS =" as ";

//	//main包下的sql存放文件名
//	public static final String MAINSQL="mainsql";
//	public static final String OTHERSQL="othersql";
//	public static final String PERSIONSQL="persionsql";
//	
//	//综合查询sql存放文件
//	
//	public static final String COMQUERYSQL="compositeQuery";
//	
//	//yuwugongkai包下的sql和hql存放文件名
//	public static final String YWGKSQL="ywgksql";
//	public static final String YWGKHQL="ywgkhql";
//	
//	
//	//parole包下的sql和hql存放文件名
//	public static final String PAROLESQL="parolesql";
//	public static final String PAROLEHQL="parolehql";
//	
//	//criminalexam包下的sql和hql存放文件名
//	public static final String EXAMSQL="examsql";
//	
//	//sjjs包下的sql和hql存放文件名
//	public static final String SJJSSQL="sjjssql";
//	
//	//release包下的sql和hql存放文件名
//	public static final String RELEASESQL="releasesql";
//	public static final String RELEASHSQL="releasehql";
//	
//	//baseinfor包下的hql存放文件名
//	public static final String BASEINFORHQL="baseinforhql";
//	
//	//baseinfor包下的sql存放文件名
//	public static final String BASEINFORSQL="baseinforsql";
//	//stat包下的sql存放文件名
//	public static final String STATSQL="statsql";
//	
//	//jydf包下的sql和hql存放文件名
//	public static final String JYDFSQL="jydfsql";
//	public static final String JYDFHQL="jydfhql";
//	//plqz包下的sql和hql存放文件名
//	public static final String PLQZSQL="plqzsql";
//	public static final String PLQZHQL="plqzhql";
//	
//	//保外就医包下的sql和hql存放文件名
//	public static final String BWJYSQL="bwjysql";
//	public static final String BYJYHQL="bwjyhql";
//	//考核奖惩包下的sql和hql存放文件名
//	public static final String KHJCSQL="khjcsql";
//	public static final String KHJCHQL="khjchql";
//	
//	//狱政管理包下的sql和hql存放文件名
//	public static final String YZHGLSQL="yzhglsql";
//	public static final String YZHGLHQL="yzhglhql";
//	
//	//收监管理包下的sql和hql存放文件名
//	public static final String SJGLSQL="sjglsql";
//	public static final String SJGLHQL="mainhql";
	
	//message存放文件名
//	public static final String LOGMSG="logmessage";
	public static final String XMLLOGMSG="message";
	public static final String XMLMENUMAPPING="menumapping";
	public static final String XMLOPINION="opinion";
	public static final String XMLRJDJ="rjdj";
	
	public static final String TCHAR="iso-8859-1";
	public static final String encoding = "UTF-8";
	public static final String YES = "YES";
	public static final String JYBOUTCON = "JYBOUTCON";//续保情况
	public static final String JYLIAN = "jylian";//监狱立案
	public static final String SJLIAN = "sjlian";//省局立案
	public static final String OPCCASETYPE_BY = "1";//保外就医
	public static final String OPCCASETYPE_XB = "2";//继续保外
	public static final String OPCCASETYPE_CX = "3";//撤销保外
	public static final String JYBOUTLIAN = "jyboutlian";//监狱保外立案
	public static final String DEP_ALARM_LEVEL_JIANYU = "4";
	public static final String DEP_ALARM_LEVEL_SHENGJU = "66";
	public static final String DEP_ALARM_LEVEL_ZHONGJI_FAYUAN = "11";
	public static final String DEP_ALARM_LEVEL_GAOJI_FAYUAN = "10";
	public static final int DEP_ALARM_LEVEL_ALL = 7;
	public static final String SQL_COMMENT = "--";
	public static final String SQL_COMMENT_SQL = "注入sql：";
	
	public static final String DEFAULT_FLOWDEFID = "other_sysflow";//默认系统流程
	
	//刑期变动
	public static final String XINGQI_YOUQI = "9990";
	public static final String XINGQI_WUQI = "9995";
	public static final String XINGQI_SIHUAN = "9996";
	public static final String XINGQI_SIXING = "9997";
	
	public static final String XINGQI_YOUQI_ZH = "有期徒刑";
	public static final String XINGQI_WUQI_ZH = "无期徒刑";
	public static final String XINGQI_SIHUAN_ZH = "死刑，缓期二年执行";
	public static final String XINGQI_SIXING_ZH = "死刑";
	//剥政年限
	public static final String LOSEPOWER_97 = "97";
	public static final String LOSEPOWER_99 = "99";
	public static final String LOSEPOWER_BDZZ = "剥夺政治权利";
	public static final String LOSEPOWER_ZH = "终身";
	
	
	//减刑类型
	public static final String JIANXINGTYPEID_YOUQI = "1";
	public static final String JIANXINGTYPEID_WUQISIHUAN = "2";
	//刑种
	public static final String XINGZHONG_YOUQI = "1";
	public static final String XINGZHONG_WUQI = "2";
	public static final String XINGZHONG_SIHUAN = "3";
	//资格筛查
	public static final String ZGSCSB="资格筛查申报";
	public static final String USER_ADMIN="00000001";
	public static final String USER_ONLYREADY_PERMI = "2013072020481978131";
	
	//login session suid
	public static final String SESSION_LOGINNAME="suid";
	//获取当前监狱
	public static final String SESSION_DEPARTMENT="sdparentid";
	public static final String RESULT_ZERO="0";
	//成功
	public static final String RESULT_SUCCESS="success";
	//失败
	public static final String RESULT_ERROR="error";
	//存在
	public static final String RESULT_EXIST="exist";
	//保外就医取保书
	public static final String BWJY_QUBAOSHU="SDXF_QUBAOSHU";
	//查询病残鉴定
	public static final String BWJY_BINGCAN="SDXF_BINGCANJDB";
	//查询法定不批准出境人员报备通知书
	public static final String BWJY_BAOBEITONGZHI="SDXF_NOPASS_CHUJINGTZS";
	//request   menu使用
	public static final String DANGKA_CP="dk_caipan";//裁判
	public static final String DANGKA_CH="dk_chenghu";//其他称呼
	public static final String DANGKA_TZ="dk_tezheng";//特征
	public static final String DANGKA_JL="dk_jianli";//简历
	public static final String DANGKA_QK="dk_qianke";//前科
	public static final String DANGKA_ZJ="dk_zhengjian";//证件
	public static final String DANGKA_MT="dk_meiti";//媒体
	public static final String DANGKA_GX="dk_guanxi";//社会关系
	/**********end  zhangfei*************/
	
	//日期格式
	public static final String YEARFORMAT="yyyy";
	public static final String DATESIMFORMAT="yyyyMMdd";
	public static final String DATEPOINTFORMAT="yyyy.MM.dd";
	public static final String DATEFORMAT="yyyy-MM-dd";
	public static final String DATETIMEFORMAT="yyyy-MM-dd HH:mm:ss";
	public static final String DATETIMEFORMATTWO="yyyy-MM-dd HH:mm";
	public static final String DATETIMEFORMATNOSPLIT="yyyyMMddHHmmssSS";
	public static final String DATETIMEFORMATNOSPLIT2="yyyyMMddHHmmss";
	public static final String DATETIME24FORMAT="yyyy-MM-dd HH24:mi:ss";
	public static final String DATETIMEFORMATJSON="yyyy-MM-dd'T'HH:mm:ss";
	public static final String TIMEFORMAT=" 00:00:00";
	public static final String LASTTIMEFORMAT=" 23:59:59";
	public static final String MONTH = "月";
	public static final String MONTHS = "个月";
	public static final String YEAR = "年";
	public static final String DAY = "天";
	public static final String DAYR = "日";
	public static final String START_DATE = "1900-01-01";
	public static final String END_DATE = "9999-12-31";
	//数据库类型
	public static final String TEXT = "TEXT";
	public static final String VARCHAR2 = "VARCHAR2";
	public static final String VARCHAR = "VARCHAR";
	public static final String CHAR = "CHAR";
	public static final String NUMBER = "NUMBER";
	public static final String INT = "INT";
	public static final String SMALLINT = "SMALLINT";
	public static final String DECIMAL = "DECIMAL";
	public static final String NUMERIC = "NUMERIC";
	public static final String FLOAT = "FLOAT";
	public static final String MONEY = "MONEY";
	public static final String DATE = "DATE";
	public static final String DATETIME = "DATETIME";
	public static final String Clob = "CLOB";
	public static final String Blob = "Blob";
	public static final String NULL = "null";
	//流程标示
	public static final String START="START";
	public static final String JPMEND="YESEND";
	public static final String JBPMYESEND="YESEND";
	public static final String JBPMNOEND="NOEND";
	public static final String JBPMBACKEND="BACKEND";
	public static final String JBPMBACK="BACK";
	public static final String SHENPIZHONG="审批中...";
	public static final String RESULT="result";
	public static final String ZERO = "0";
	public static final String MINUS_ONE = "-1";
	public static final String NINENINE = "99";
	public static final String ONE = "1";
	public static final String TWO = "2";
	public static final String THREE = "3";
	public static final String FOUR = "4";
	public static final String FIVE = "5";
	public static final String SIX = "6";
	public static final String SEVEN = "7";
	public static final String EIGHT = "8";
	public static final String NINE = "9";
	public static final String SELF = "ziji";
	public static final String YEARONE = "201301";
	public static final long COMMON_APPROVEID = 0;
//	public static final String JBPM_UNSELECTED = "jbpm_unselected";
//	public static final String JBPM_UNSELECTED_PROCESS = "jbpm_unselected_process";
//	public static final String JBPM_UNSELECTED_PROCESS_TYPE_JXJS = "jxjs";
//	public static final String JBPM_UNSELECTED_PROCESS_TYPE_BWJY = "bwjy";
//	public static final String JBPM_UNSELECTED_PROCESS_TYPE_ANNEX = "annex";
//	public static final String JBPM_UNSELECTED_PROCESS_TYPE_ANDEP = "andep";
	//流程id
	public static final String JYBWJY_JPMID = "jybwjy";//监狱保外就医流程ID
	public static final String JYBWJYXB_JPMID = "sxbwxb";//陕西监狱保外就医续保流程ID
	public static final String SJBWJY_JPMID = "sjbwjy";//省局保外就医流程ID
	public static final String KHJCREPORT_JPMID = "khjcreport";//考核奖惩报表流程ID
	public static final String JYBWJYXB_JPMID_SZ = "szbwxb";//深圳监狱保外续保流程ID
	
	//保外类型
	public static final String JYBWJY_CHUBAO = "1";//监狱保外初保
	public static final String JYBWJY_XUBAO = "2";//监狱保外续保
	
	//省局保外
	public static final String BWTYPE="保字";
	public static final String GDBWTYPE="刑保字";
	public static final String SXJBWTYPE="晋刑暂字";
	
	//lyg省局保外入监
	public static final String GDBWRJ="刑收监字";
	//操作名称
	public static final String CAOZUO = "caozuolist222";
	//增删改查
	public static final String SELECT = "select";
	public static final String INSERT = "insert";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String DELETEALL = "deleteAll";
	public static final String CONDITION = "condition";
	public static final String ALL = "all";
	
	//保存大字段类型
	public static final String ANDEP = "andep";
	public static final String ANNEX = "annex";
	
	//页面状态
	public static final String EDIT = "edit";
	public static final String VIEW = "view";
	public static final String NEW = "new";
	public static final String ADD = "add";
	public static final String DEL = "del";
	public static final String REPLY = "reply";
	public static final String DISTRI = "distri";
	public static final String ADDFORM = "addForm";
	public static final String XIUGAI = "xiugai";
	public static final String FORMXINZENG = "formxinzeng";
	public static final String XINZENG = "xinzeng";
	public static final String FORMXIUGAI = "formxiugai";
	public static final String OPERATE = "操作";
	public static final String BUTTON_NEW = "新增";//
	public static final String BUTTON_DEL = "删除";//
	public static final String BUTTON_REPLY = "回复";//
	public static final String BUTTON_DISTRI = "分配";//

	//http状态码
	public static final int STATUS_CODE_404 = 404;
	public static final int PAGE_INDEX = 0;
	public static final int PAGE_SIZE = 20;
	public static final String HTTP = "http";
	public static final String WSDL = "wsdl";
	
	//ContentType
	public static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
	public static final String CONTENT_TYPE_TEXT = "text/html";
	public static final String CONTENT_TYPE_IMAGE = "image/jpeg";
	public static final String CONTENT_TYPE_STREAM = "application/octec-stream";
	
	//response head 
	public static final String RESPONSE_HEADER_NAME = "Content-Disposition";
	public static final String RESPONSE_HEADER_ATTACHMENT = "attachment; filename=";
	
	public static final String CJIIMPRISONTYPE_YQTX = "1";
	public static final String CJIIMPRISONTYPE_WQTX = "2";
	public static final String CJIIMPRISONTYPE_SXHQENZX = "3";
	
	//权限
	public static final String UNKNOWN_PERMISSION = "11003";
	
	//公共编辑页面是否显示 1 为显示
	public static final String NEWPAGESHOW = "1";
	//公共编辑页面是否编辑 1 为编辑
	public static final String NEWPAGEEDIT = "1";
	
	//报表插件版本
	public static final String VIEWERVERSION = "12.0.0.6";
	public static final String VIEWERFILENAME = "hwpostil.inf";
	public static final String SINGLESEPALINE = "/";
	public static final String DOUBLESEPALINE = "//";
	public static final String DOUBLESEPALINE_TWO = "\\";
	public static final String SEMICOLON = ";";
	public static final String COLON  = ":";
	public static final String PAUSE_MARK  = "、";
	public static final String WENHAO = "?";
	
	public static final String XINGFAZHIXING = "刑罚执行系统导出数据";
	
	public static final String PATH="DownExcel/";
	public static final String ZIPDATA_PATH="zipdata";
	public static final String CRIMINALARCHIVES_PATH="CriminalArchives";
	public static final String CRIMINALARCHIVES_ABSOLUTEPATH="criminalarchivespath";
	public static final String DEPARTARCHIVES_ABSOLUTEPATH="departarchivespath";
	public static final String CRIMINALPHOTO_ABSOLUTEPATH="criminalphotopath";
	public static final String OUTPUTARCHIVESPDF="outputarchivespdf";
	public static final String CRIMINALARCHIVESPDF_ABSOLUTEPATH="criminalarchivespdfpath";
	public static final String WEB_XML_RESOLVE="webxmlresolve";//webservices解析xml文件临时存放路径
	
	//表空间名称
	public static final String TABLESPACENAME="tablespacename";
	
	public static final String LAO="老";
	public static final String BING="病";
	public static final String CAN="残";
	public static final String PINJIESTR="gkzx_gkzx";
	public static final String PINJIETABLE="gkzx_table";
	public static final String TO="至";
	public static final String rowStart="rowStart";
	public static final String rowEnd="rowEnd";
	public static final int CNTPERCOLUMN=3;//一个单元格放几个对象
	public static final String HAS_CRIMINAL_RECORD="有前科";//"有前科"
	public static final String NO_CRIMINAL_RECORD="无前科";//"无前科"
	public static final String MONTH_REPORT="月份计分考核";//"月份计分考核"
	
	//入监登记
	public static final String ZHIYECODE="GK041";
	
	public static final String CRRETCODE="GKM005";
	public static final String RJDJ_ANNEX_NAME="入监登记表";
	public static final String FZSS_ANNEX_NAME="犯罪事实";
	public static final String FYCP_ANNEX_NAME="法院裁判";
	public static final String SHGX_ANNEX_NAME="社会关系";
	public static final String FYAJFY_ANNEX_NAME="法院案件复议";
	public static final String FANZUISHISHI="3";
	
	//档案模板自定义
	public static final String ARCHIVESTEMPLATE = "CRIMINAL_ARCHIVES";	
	public static final String ARCHPINJIESTR="GKZXARCH";
	public static final String ARCHIVESNAMESTR = "档案";
	public static final String ARCHIVESANTYPECODE = "9";
	//电子档案审批状态
	public static final String APPLYARCHIVESLOANSTATUS="刚申请";
	public static final String PASSLOANSTATUS="通过";
	public static final String NOPASSLOANSTATUS="未通过";	
	public static final String ZHENGJUAN="正卷";
	public static final String FUJUAN="副卷";
	public static final String SIGNATURE="已签章";
	public static final String NOSIGNATURE="未签章";
	public static final String BGQX="永久";//保管期限
	public static final String SECRECYLEVEL="非涉密";
	
	//减刑假释会议纪要
	public static final String JXJS_ANNEX_NAME="减刑假释审核表";
	public static final String JXJS_ANNEX_JXJSJYSNAME="减刑(假释)建议书";
	public static final String JXJS_ANNEX_BWJYJYSNAME="保外就医建议书";
	public static final String JXJS_ANNEX_JCJDYJSNAME="检察监督意见书";
	public static final String JXJSMEETINGSUMMARY="JXJSHYJY";
	public static final String FY_JXJS_ANNEX_NAME="减刑假释审批表";
	
	//运算符
	public static final String EQUALS = "=";
	public static final String NOTEQUALS = "!=";
	public static final String LESSTHAN = "<";
	public static final String LESSEQUAL = "<=";
	public static final String GRATER = ">";
	public static final String GRATEREQUAL = ">=";
	public static final String OR = "or";
	public static final String AND = " and ";
	public static final String WHERE = " where ";
	public static final String B_SYMBOL = "B";
	public static final String PERCENT_SYMBOL = "%";
	public static final String LEFTFUZZY = "%L";
	public static final String RIGHTFUZZY = "%R";
	public static final String BOTHFUZZY = "%B";
	
	//狱务公开榜单类型
	public static final String BDTYPE_BWJY="bwjy";
	public static final String BDTYPE_JXGS="jxgs";
	public static final String BDTYPE_JXJS="jxjs";
	
	//案件类型
	public static final String CASE_TYPE_BWJYXB="bwxb";
	public static final String CASE_TYPE_BWJY="bwjy";
	public static final String CASE_TYPE_BWJY_PARAM="保字";
	public static final String CASE_TYPE_JXJS="减字";
	public static final String CASE_TYPE_JS="假字";
	public static final String CASE_TYPE_CBZ="初保字";
	public static final String CASE_TYPE_XBZ="续保字";
	
	public static final String CASE_TYPE_JXJS_FY="刑执字";
	public static final String CASE_TYPE_FY_PARAM="xzz";
	public static final String CASE_TYPE_JXJS_FY_C="刑执查字";
	public static final String CASE_TYPE_FY_C_PARAM="xzcz";
	public static final String CASE_TYPE_JXJS_FY_B="刑执备字";
	public static final String CASE_TYPE_FY_B_PARAM="xzbz";
	public static final String CASE_TYPE_JXJS_FY_J="刑执监字";
	public static final String CASE_TYPE_FY_J_PARAM="xzjz";
	public static final String CASE_TYPE_JXJS_PARAM="jxjs";
	public static final String CTSTYPE_BWJY="8";
	public static final String CTSTYPE_JXJS="7";
	public static final String SJ_CTSTYPE_JXJS="13";
	public static final String FY_CTSTYPE_JXJS="15";
	
	//公共方法（form表单）验证(警告)
	public static final String AIP_message="：此项输入内容格式或者字数过多!";
	//fileManage包下的sql和hql存放文件名 add by zhangzhao
	public static final String FILEMANAGESQL="filemanagesql";
	public static final String FILEMANAGEHQL="filemanagehql";
	
	//考核奖惩
	public static final String KHJC_TITLE="罪犯计分考核月报表";
	//监外执行字
	
	public static final String CHANGEUKEYMSG = "此UKey已被其它用户绑定,请更换UKey重新绑定！";
	public static final String LOGINSUCCESSUKEYMSG = "绑定成功，您现在可以使用UKey登录系统！";
	public static final String LOGINERRORUKEYMSG = "绑定失败！";
	
	//办案类型
	public static final String BANAN_TYPE = "GK7788";
	//档案类别
	public static final String DANGAN_LEIBEI = "GKDALB";
	
//	public static final String TIMER_LISTEN_SEC = "timer.listen.sec"; //时钟间隔周期
//	public static final String TIMER_LISTEN_START = "timer.listen.start"; //启动服务后多久开始进行监听
//	public static final String TASK_START_TIME = "task.start.time"; //启动服务后多久开始进行监听
//	public static final int TASK_START = 3; //任务开始时间
//	public static final String  NIGHT_TASK_MAX_THREADS = "night_task_max_threads"; //夜间任务最大线程数
//	public static final String LISTENER_START = "开始定时监听"; //启动服务后多久开始进行监听
//	public static final String LISTENER_STOP = "停止定时监听"; //启动服务后多久开始进行监听
//	public static final String DEFAULT_FROM_TABLE = "default_from_table"; //从表里读取默认值TBSECURITY_DEFAULT_MANAGE
	
	//省份代码
	public static final String NATIVE_CHINA_TEXT = "中国";
	public static final String PROVINCE_SHANXI = "6100";
	public static final String PROVINCE_HENAN = "4100";
	public static final String PROVINCE_SHENZHEN = "4400";
	public static final String PROVINCE_SHANGDONG = "3700";
	
	public static final String PROVINCE_GDGAOYUAN = "c2550";
	
	//统计类型
	public static final String STAT_LASTYEAR = "lastyear";
	public static final String STAT_THISYEAR = "thisyear";
	public static final String STAT_THISQUARTER = "thisquarter";
	public static final String STAT_THISMONTH = "thismonth";
	public static final String STAT_PERIOD = "period";
	
	//首次非首次（减刑）
	public static final String JXORRY = "减刑OR入狱";//判断是否第一次减刑
	public static final String JX_FIRST = "入狱";
	public static final String JX_MORE = "上次减刑";
	
//	public static final String DEFAULT_ROLE = "default_role";//办公助理
//	public static final String DEFAULT_KESHI_ROLE = "default_keshi_role";//科室普通用户
//	public static final String DEFAULT_JIANQU_ROLE = "default_jianqu_role";//监区普通用户
	
	public static final String HNFANGANPCID = "20131024110948IMP";//河南判裁数据交换 方案id
	public static final String HNFANGANZMID = "20131024110948IMP";//河南罪名数据交换 方案id
	public static final String DBMS_IMPORT = "0";//数据导入
	public static final String DBMS_EXPPORT = "1";//数据导出
	public static final String DBMS_IMEXPORT = "2";//数据交换
	public static final String DBMS_IMEXPORT_JD = "3";//局地数据交换
	public static final String SANLEIFAN = "SLZF_SLZFSP";//河南三类犯表单id
	public static final String JYCONFIG = "jyconfig";
	public static final String TBCRIMINAL_INFO = "罪犯辅助信息";
	public static final String TBCRIMINAL_BIGDATA = "罪犯clob类型信息";
	public static final String SANLEIFAN_JINRONG = "jinrongsanleifan"; //金融三类犯对应罪名code备注
	public static final String SANLEIFAN_TANWUHUILU = "tanwuhuilusanleifan"; //贪污贿赂三类犯对应罪名code备注
	public static final String SANLEIFAN_DUZHI = "duzhisanleifan"; //渎职三类犯对应罪名code备注
	
	public static final String USER_HAS_LOGIN= "用户已在其他地方登陆,本次登录已导致其他地方登录强制断开.";
//	public static final String jxjsantype= "jxjsantype";
//	public static final String bwjyantype= "bwjyantype";
	
	public static final String CASETYPE_CODE = "GK7788";
	//会议记录 模板 内容
	public static final String CURYEAR = "[年度]";
	public static final String BATCH = "[批次]";
	public static final String DEPNAME = "[部门名称]";
	public static final String FULLDEPNAME = "[部门全称]";
	public static final String MEETTIME = "[会议时间]";
	public static final String MONTH1 = "[月份]";
	public static final String JIANYU = "[监狱名称]";
	public static final String JIANQU = "[监区名称]";
	public static final String USERNAME = "[主持人]";
	public static final String CRIMINAME = "[姓名]";
	public static final String CrimeCount = "[选中罪犯人数]";
	
	public static final int RESOURCE_PRINTSCHEME = 14;
	
	//jin ge seal foldername
	public static final String SEALFOLDERNAME_NOSEAL="/sealfile/noseal";
	public static final String SEALFOLDERNAME_CACHE="/sealfile/sealcache";
	public static final String SEALFOLDERNAME_SEALED="/sealfile/sealed";
	public static final String BIGTEXT = "bigtext";
	public static final String MEETING = "会议记录";
	public static final String MEETDOC = "reportMeetDoc";//会议记录类别(reportMeetDoc)
	public static final String MEETCONTENT = "meetContent";//会议记录 文本内容 标示(meetContent)
	public static final String LISTYEAR = "第";
	public static final String LISTTIME = "批次减刑假释，";
	public static final String JIANWAI = "批次监外执行，";
	public static final String NOTEXIST = "暂无相关文书！";
	public static final String PSHSUGGEST = "评审会意见";
	public static final String PICI = "批次";
	public static final String SHIFANG="释放";
	
	public static final String FAYUAN = "fayuan";
//	public static final String TIANJIN = "1209";
	public static final String TIANJIN_CODE = "tianjin";
	public static final String SHAN_XI = "shanxi";
	public static final String GUANGDONG = "guangdong";
	public static final String CHENGPIBIAO = "chengpibiao";
	public static final String DANGANZILIAO = "danganziliao";
	public static final String NINGXIA = "ningxia";
	public static final String PROVINCECODE = "provincecode";
	public static final String HEBEI_CODE = "hebei";
	public static final String SHANGHAI_CODE = "shanghai";
	public static final String TIANJIN_PROVINCE = "1200";
	public static final String XINJIANG_PROVINCE = "6500";
	public static final String HEBEI_PROVINCE = "1300";
	public static final String SCREENINGAPPROVE = "screeningapprove";//判断新增减刑幅度时是否生效，0未生效，1生效
	public static final String SHANGHAI_PROVINCE = "3100";
	public static final String HUNAN_PROVINCE = "4300";
	public static final String GUANGDONG_PROVINCE = "4400";
	
	//档案相关
	public static final String NOT_EXIST_CRI="罪犯[@]不存在!";
	public static final String NOT_EXIST_FILE="罪犯[@]，文件名: # 不符合命名规则!";
	//流程相关
	public static final String FLOW_DEFID_JY = "other_zfjyjxjssp";
	
	//从宽情节
	public static final String WCNF = "未成年犯";
	public static final String LAOF="老犯";
	public static final String BF="病犯";
	public static final String CF="残犯";
	public static final String CKQT="其他";
	//从严情节
	public static final String LEIF="累犯";
	public static final String DPZF="毒品再犯";
	public static final String DCFZ="多次犯罪";
	public static final String FSZYS="犯四罪以上";
	public static final String YZWJ="严重违纪";
	public static final String SZBFWNYS="数罪并罚有两罪在五年以上";
	public static final String ZDXSF="重大刑事犯罪罪犯";
	public static final String CYQT="其他";
	//三类罪犯
	public static final String ZWFZ="职务犯罪";
	public static final String JRFZ="破坏金融管理秩序和金融诈骗犯罪";
	public static final String SHFZ="涉黑犯罪";
	//其他情节
	public static final String ZDGZ="重点关注";
	public static final String MTGZ="媒体关注";
	public static final String XDS="吸毒史";
	public static final String ZSS="自杀史";
	public static final String TTS="脱逃史";
	public static final String XJS="袭警史";
	public static final String SBCC="申报财产";
	
	//罪犯状态
	public static final String ZAIYA="在押";
	public static final String BAOWAI="保外";
	public static final String CHUJIAN="出监";
	public static final String SIWANG="死亡";
	public static final String DIAOCHU="调出";
	public static final String DAISHOUJIAN="待收监";
	public static final String JIASHI="假释";
	public static final String JIEHUIZAISHEN="解回再审";
	public static final String BWJY="保外就医";
	//描述
	public static final String DESCRIBE_ZF="罪犯@的";
	public static final String DESCRIBE_OTHER="@的";
	public static final String DESCRIBE_USER="用户@的";
	
	//山西三类罪犯 
	public static final String SANLEIZUIFAN = "三类罪犯";
	public static final String FYSAVEDATA="fysuccess";
	public static final String ZHIDINGHEYITING="指定合议庭";
	public static final String GENGGAIHEYITING="更改合议庭";
	public static final String HEYITINGSHEZHI="法院合议庭操作";
	public static final String ZHIDINGBANAN="案件分配给你，请抓紧时间办理！";
	public static final String FAJIN="罚金：";
	
	//宁夏
	public static final String SIGNCHECKBIAODAN = "signcheckbiaodan";
	public static final String FLOWDEFIDS = "flowdefids";
	public static final String NX_ZUIFAN = "罪犯";
	public static final String NX_SHENGFEN = "[省份]";
	public static final String NX_NOWDATE = "[当前日期]";
	public static final String NX_YEAR = "年";
	public static final String NX_MONTH = "月";
	public static final String NX_DAY = "日";
	public static final String NX_GONGZHANG = "公章";
	public static final String NX_CRIMINAME = "[罪犯姓名]";
	//(暂时 使用，后会替换为模板)
	public static final String NX_CONTENT = "　　作为罪犯[罪犯姓名]的保证人,我承诺在其暂予监外执行期间履行以下义务:\\r\\n" +
			"　　一、协助社区矫正机构监督被保证人遵守法律和有关规定;\\r\\n" +
			"　　二、发现被保证人擅自离开居住的市、县或者变更居住地,或者有违法犯罪行为的,或者需要保外就医情形消失的,立即向社区矫正机构报告;\\r\\n" +
			"　　三、为被保证人的治疗、护理、复查以及正常生活提供必要的条件;\\r\\n" +
			"　　四、督促和协助被保证人按照规定定期复查病情和履行向社区矫正机构报告的义务。\\r\\n" +
			"　　如不能履行上述法律义务,愿承担相应法律责任";
	public static final String NX_TITLE = "罪犯减刑(假释)审核表";
	public static final String NX_LBCSPB = "老病残罪犯审批表";
	public static final String NX_ZI = "自";
	public static final String NX_QI = "起";
	public static final String NX_ZHIO = "至";
	public static final String NX_ZHIT = "止";
	public static final String NOCONTENT = "备注：该罪犯不属于生活不能自理罪犯!(病残罪犯和妊娠罪犯在监狱外医院进行鉴定)";
	public static final String DETAINSTATUS_BW = "保外";
	public static final String DETAINSTATUS_ZY = "在押";
	public static final int SENSTATUS = 0;
	public static final String WEIZHI = "未知";
	
	//间隔期开始的默认值,上次监狱长签字日期如果为空,取判裁日期
	public static final String SCREEN_INTERVAL_START = "F_ISDATE(max(t.courtsanction), 'yyyy-MM-dd')";
	//间隔期结束的默认值,考核止日
	public static final String SCREEN_INTERVAL_END = "examineend";
	//无期死缓的起始时间开始默认值,取判裁日期
	public static final String SCREEN_COMMUTATIONDATE_START_WUQISIHUAN = "i.judgedate";
	//有期的起始时间开始默认值,取执行日期
	public static final String SCREEN_COMMUTATIONDATE_START_YOUQI = "t.execdate";
	//起始时间结束的默认值,考核止日
	public static final String SCREEN_COMMUTATIONDATE_END = "h.examineend";
	//执行刑期开始默认值,取刑期起日
	public static final String SCREEN_EXECUTESENTENCE_START = "i.sentencestime";
	//执行刑期结束的默认值,考核止日
	public static final String SCREEN_EXECUTESENTENCE_END = "h.examineend";
	
	public static final String WARDEN = "监狱长";
	
	public static final String GDS="广东省";
	
	//河北省
	public static final String HB_HAO = "号";
	public static final String HB_DI = "第";
	public static final String XINBANLI = "新办理";
	public static final String XINBANLIS = "[[N]]新办理||[[N]]急保";
	
	public static final String STATISTICALREPORTSQL = "statisticalReportsql";//刑罚执行统计报表相关sql的文件
	
	public static final String BUTTON_DEAL = "处理";//
	public static final String BUTTON_MODIFY = "修改";//
	public static final String BUTTON_VIEW = "查看";//
	
	//刑期变动表 变动类型
	public static final String CATEGORY_YP = "原判";//
	public static final String CATEGORY_PJ = "判决";//

	public static final String WUQISIHUAN="死缓(无期)";
	
	public static final String SHENGJUHUIFUTITLE="省局案件回复提醒";
	public static final String SHENGJUHUIFUCONTENT="省局已对案件回复，可在案件查看功能下查看相关信息";
	
	
	
	public static final String YWGK_GOUWU = "购物";
	
	public static final String TBWA_COMPREHENSIVE_SITUATION = "TBWA_COMPREHENSIVE_SITUATION";//危安犯相关表名
	
	public static final String TBSYS_CODE = "TBSYS_CODE";//危安犯相关code类型
	
	public static final String ZHONGJIFAYUAN = "中级人民法院";
	
	public static final String GAOJIFAYUAN = "高级人民法院";
	
	public static final String DOWNLOAD = "/download";
	
	public static final String TBYZ_MAG_MTHREPORTER = "TBYZ_MAG_MTHREPORTER";//危安犯相关表名
	
	public static final String PROJECTNAME = "pmsystem";
	
	public static final String TEMPARCHIVES_PATH="TempArchives";
	
	//加解密文件的后缀类型
	public static final String ENCRYPT_FILE_SUFFIX = ".d";
	
	public static final String UPLOADDATA="uploaddata";
	
	public static final String UNZIPDATA_PATH="unzipdata";
	
	public static final String BEFOREDATA="beforedata";
	
	
}
