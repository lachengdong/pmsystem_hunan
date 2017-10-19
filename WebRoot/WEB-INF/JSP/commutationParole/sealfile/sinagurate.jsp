<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.gkzx.common.GkzxCommon" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String nosealpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+GkzxCommon.SEALFOLDERNAME_NOSEAL+"/";
//String webUrl = "http://localhost:8080/pmsys/commutationParole/sealfile/documentEdit.page";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>批量签章页面</title>
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
 <body>
 		<!-- 
 		 -->
	        <div class="mini-toolbar" style="padding:2px;border-bottom:0;">
	             <a class="mini-button" id="dakai" iconCls="icon-lock" plain="true" onclick="dakai()">工具栏</a> 
	        </div>
        <div style="width:100%; height:100%;">
			<script src="<%=path%>/scripts/jgform/iWebPDF.js"></script>
		</div>
  </body>
<script language=javascript>
	mini.parse();
	function load(o){
			
		  var scheme = o.scheme;//签章方案
		  var writtype = o.writtype;//文书类型 
		  var nodetype = 0;
		  var x = 0;
		  var y = 0;
		  var wenzhi = '';
		  var douhao = scheme.split(",");
		  var datas = [];
		  var progress = 0;
		  //节点类型
          if(douhao[0].split(":").length>0){
        	  nodetype = douhao[0].split(":")[1];
          }
          //通过节点类型 判断 是坐标签章 else 关键字签章 
          if(nodetype == 1 || nodetype == 2 || nodetype == 3){
        	  //x轴坐标
              if(douhao[1].split(":").length>0){
                  x = douhao[1].split(":")[1];
              }
              //y轴坐标
              if(douhao[2].split(":").length>0){
                  y = douhao[2].split(":")[1];
              }
              //签章进程
              progress = douhao[3];
          }else if(nodetype == 4 || nodetype == 5 || nodetype == 6){
        	  wenzhi = douhao[1].split(":")[1];
          }
          var xy = ""+x+'*'+y+"";
		  try{
			  	var ids = o.id;
			  	var idarr = ids.split(";");
			  	var pass;
			  	//while(!WebPDF.VerifyPin(pass = window.prompt("请输入签章密码",""))){
			  		//alert("密码不正确请重新输入");
			  	//}
			  	for(var i=0, n=idarr.length; i<n; i++){
			  		var filename = idarr[i];
				    WebPDF.WebUrl="http://localhost:8080/pmsys/documentEdit.jsp";     //WebUrl:系统服务器路径，与服务器文件交互操作，如保存、打开文档  
				    WebPDF.RecordID=encodeURIComponent(filename);   //RecordID:本文档记录编号
				    WebPDF.FileName=encodeURIComponent(filename);   //FileName:文档名称
				    WebPDF.UserName="";   //UserName:操作用户名
				    WebPDF.ShowTools = 1;               //工具栏可见（1,可见；0,不可见）
				    WebPDF.SaveRight = 1;               //是否允许保存当前文档（1,允许；0,不允许）
				    WebPDF.PrintRight = 1;              //是否允许打印当前文档（1,允许；0,不允许）
				    WebPDF.AlterUser = false;           //是否允许由控件弹出提示框 true表示允许  false表示不允许 
				    //alert(filename);
				    var url = "<%=nosealpath%>"+filename;
				    url = encodeURI(url);
				    WebPDF.CurPage = 1;
				    //打开 pdf文件 
				    WebPDF.WebOpen();
				    WebPDF.CreateSignature(0,"123456",nodetype,2,xy,"-1");
			    	WebPDF.WebSave();
			    	datas.push(filename);
			  	}
			  	//只有减刑 假释 审核表 有签章 进程
			  	//alert(writtype);
			  	//if('JXJS_JXJSSHB' == writtype){
			  		//解决中文乱码 
				  	var url = 'sealfile/updateSignProgressToFlowBase.action?1=1&data='+encodeURI(encodeURI(datas))+"&progress="+progress+"&writtype="+writtype;
				  	$.ajax({
	                     type:'post',
	                     url:url,
	                     async:false,
	                     success:function (text){
	                          
	                     }
					});
				//}
			  	
	     }catch(e){
	    	alert(e.description);                       //显示出错误信息
	  	 }
	}
	
	function SetData(data){
		var o = mini.clone(data);
		load(o);
	}
</script>
</html>
