<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="DBstep.iMsgServer2000.*" %>
<%@ page import="com.gkzx.common.GkzxCommon" %>
<%!
public class iWebOffice {
  private int mFileSize;
  private byte[] mFileBody;
  private String mFileName;
  private String mFileType;
  private String mFileDate;
  private String mFileID;

  private String mRecordID;
  private String[] reconrID;
  private String mTemplate;
  private String mDateTime;
  private String mOption;
  private String mMarkName;
  private String mPassword;
  private String mMarkList;
  private String mBookmark;
  private String mDescript;
  private String mHostName;
  private String mMarkGuid;
  private String mCommand;
  private String mContent;
  private String mHtmlName;
  private String mDirectory;
  private String mFilePath;

  private String mUserName;
  private int mColumns;
  private int mCells;
  private String mMyDefine1;
  private String mLocalFile;
  private String mRemoteFile;
  private String mLabelName;
  private String mImageName;
  private String mTableContent;

  private String Sql;

  //打印控制
  private String mOfficePrints;
  private int mCopies;
  //自定义信息传递
  private String mInfo;
  private DBstep.iMsgServer2000 MsgObj;


// *************接收流、写回流代码    结束  *******************************

  public void ExecuteRun(HttpServletRequest request, HttpServletResponse response) {
    MsgObj = new DBstep.iMsgServer2000(); //创建信息包对象
    mOption = "";
    mRecordID = "";
    mTemplate = "";
    mFileBody = null;
    mFileName = "";
    mFileType = "";
    mFileSize = 0;
    mFileID = "";
    mDateTime = "";
    mMarkName = "";
    mPassword = "";
    mMarkList = "";
    mBookmark = "";
    mMarkGuid = "";
    mDescript = "";
    mCommand = "";
    mContent = "";
    mLabelName = "";
    mImageName = "";
    mTableContent = "";
    mMyDefine1 = "";
	String sealedPath = request.getSession().getServletContext().getRealPath(GkzxCommon.SEALFOLDERNAME_SEALED)+"\\";
    System.out.println(sealedPath);
    mFilePath = request.getSession().getServletContext().getRealPath("");	//取得服务器路径
    try {
	      if (request.getMethod().equalsIgnoreCase("POST")) {
		        MsgObj.Load(request);                                                   //8.1.0.2版后台类新增解析接口，效率更高
		        if (MsgObj.GetMsgByName("DBSTEP").equalsIgnoreCase("DBSTEP")) {		//如果是合法的信息包
		          	mOption = MsgObj.GetMsgByName("OPTION");				//取得操作信息
		          	mUserName = MsgObj.GetMsgByName("USERNAME");				//取得系统用户
		          	System.out.println("mOption:"+mOption);						//打印出调试信息
					if (mOption.equalsIgnoreCase("SAVEFILE")) {			//下面的代码为保存文件在服务器的数据库里
			            mFileName = MsgObj.GetMsgByName("FILENAME");			//取得文档名称
			            mFileType = MsgObj.GetMsgByName("FILETYPE");			//取得文档类型
			            //mMyDefine1=MsgObj.GetMsgByName("MyDefine1");			//取得客户端传递变量值 MyDefine1="自定义变量值1"
			            mFileSize = MsgObj.MsgFileSize();					//取得文档大小
			            mFileBody = MsgObj.MsgFileBody();					//取得文档内容
			            System.out.println("mFileSize:"+mFileSize);	
			            System.out.println("mFileName:"+mFileName);
			            mFilePath = "";							//如果保存为文件，则填写文件路径
			            mDescript = "通用版本";						//版本说明
			            MsgObj.MsgTextClear();      //清除文本信息
			            System.out.println(sealedPath+mFileName);
			           if (!MsgObj.MsgFileSave((sealedPath+mFileName))){		//保存文档内容到文件夹中
			            	MsgObj.MsgError("保存失败!");
			            }
			            MsgObj.MsgFileClear();						//清除文档内容
		          	} else if (mOption.equalsIgnoreCase("INSERTFILE")) {			//下面的代码为插入文件
		          	} else if (mOption.equalsIgnoreCase("DATETIME")) {			//下面的代码为请求取得服务器时间
		          	} else if (mOption.equalsIgnoreCase("SENDMESSAGE")) {			//下面的代码为Web页面请求信息[扩展接口]
			        } else {
				          System.out.println("客户端发送数据包错误!");
				          MsgObj.MsgError("客户端发送数据包错误!");
				          MsgObj.MsgTextClear();
				          MsgObj.MsgFileClear();
			        }
			   }
	      }else {
	        MsgObj.MsgError("请使用Post方法");
	        MsgObj.MsgTextClear();
	        MsgObj.MsgFileClear();
	      }
	      MsgObj.Send(response);                                                    //8.1.0.2新版后台类新增的功能接口，效率更高
    }catch (Exception e) {
      System.out.println(e.toString());
    }
   }
}
%>
<%
iWebOffice officeServer = new iWebOffice();
officeServer.ExecuteRun(request,response);
 out.clear();                                                                    //用于解决JSP页面中“已经调用getOutputStream()”问题
 out=pageContext.pushBody();                                                     //用于解决JSP页面中“已经调用getOutputStream()”问题
%>