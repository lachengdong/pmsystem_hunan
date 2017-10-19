<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String mServerUrl= "http://localhost:8080/pmsys/sealfile/documentEdit.page";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查看pdf文件页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
      	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	   	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	    <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    </style> 
 <body >
        <div style="width:100%; height:100%;">
			<script src="<%=path%>/scripts/jgform/iWebPDF.js"></script>
		</div>
  </body>
<script language=javascript>
	mini.parse();
	function SetData(data){
		var data = mini.clone(data);
		var fileurl = data.filepath;
		var filename = data.filename;
		Load(fileurl,filename);
	}	
	function Load(fileurl,filename){
		  try{
		    //以下属性必须设置，实始化iWebPDF
		    var WebPDF = document.getElementById("WebPDF");
		    WebPDF.WebUrl="http://localhost:8080/pmsys/documentEdit.jsp";    //WebUrl:系统服务器路径，与服务器文件交互操作，如保存、打开文档  
		    WebPDF.RecordID=encodeURIComponent("");   //RecordID:本文档记录编号
		    WebPDF.FileName=encodeURIComponent(filename);   //FileName:文档名称
		    WebPDF.UserName="";   //UserName:操作用户名
		    WebPDF.ShowTools = 1;               //工具栏可见（1,可见；0,不可见）
		    WebPDF.SaveRight = 1;               //是否允许保存当前文档（1,允许；0,不允许）
		    WebPDF.PrintRight = 1;              //是否允许打印当前文档（1,允许；0,不允许）
		    WebPDF.AlterUser = false;           //是否允许由控件弹出提示框 true表示允许  false表示不允许

		    WebPDF.ShowBookMark = 1;			//是否显示书签树按钮（1,显示；0,不显示）
		    WebPDF.ShowSigns = 1;         	    //设置签章工具栏当前是否可见（1,可见；0,不可见）
		    //alert(webform.WebPDF.SideWidth);          //显示侧边栏的宽度
		    WebPDF.SideWidth = 100;             //设置侧边栏的宽度
		    WebPDF.WebOpen();                   //打开该文档    交互OfficeServer的OPTION="LOADFILE"    <参考技术文档>
		   
		    //alert(webform.WebPDF.CreateSignature(0, "123456", 1, 2, "400*450","1"));
		    WebPDF.Zoom = 100;                  //缩放比例
		    WebPDF.Rotate = 360;                //当显示页释放角度
		    WebPDF.CurPage = 1;                 //当前显示的页码   
		  }catch(e){
		    alert(e.description);                       //显示出错误信息
		  }
		}
</script>
</html>
