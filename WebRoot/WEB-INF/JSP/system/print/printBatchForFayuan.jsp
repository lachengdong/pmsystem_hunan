<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>法院批量打印</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    </style> 
</head>
<body >
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
		<div size="20px;" showCollapseButton="false">
	        <div class="mini-toolbar" style="padding:2px;border-bottom:0;">
	            <!--  <a class="mini-button" id="dakai" iconCls="icon-lock" plain="true" onclick="dakai()">工具栏</a>  -->
	             案件号：(<input id="caseyear" name="caseyear" class="mini-textbox" style="width:50px" value="${caseyear}" vtype="maxLength:4;minLength:4;int;" required="true" />)
						<input id="shortname" class="mini-textbox" style="width:60px" value="${shortname}" enabled="false" required="true"/>
					    <select id="casetype" name="casetype">
							  <c:forEach var="item" items="${casetype}">
							   <option  value='<c:out value="${item.codeid}"/>'>
							   <c:out value="${item.name}"/></option>
							  </c:forEach>
					   	</select>
					第<input id="anjianhao" name="anjianhao" class="mini-textbox" style="width:200px;height:20px" emptyText="请输入案件号:1,2或3-5或1,2,3-5"/>号
					<!-- 
					<select name="printType">
						<option value="printbydoc">按文书批量打印</option>
						<option value="printbycase">按案件成套打印</option>
					</select>
					 -->
					  <select name="printscheme" id="printscheme">
						  <c:forEach var="item" items="${printscheme}">
						   <option  value="<c:out value="${item.psid}"/>">
						   <c:out value="${item.name}"/></option>
						  </c:forEach>
					   </select>
					&nbsp;&nbsp;<a class="mini-button"  iconCls="icon-new" plain="true" onclick="dosubmit('merge');">预览</a>  
					<span class="separator"></span> 
					<a class="mini-button"  iconCls="icon-print" plain="true" onclick="dosubmit('dayin');">批量打印</a>
					<span class="separator"></span>   
					<select name="daochugeshi" id="daochugeshi">
					    <option value="doc">word</option>
					    <option value="pdf">pdf</option>
					    <option value="aip">aip</option>
					    <option value="txt">txt</option>
					    <option value="bmp">bmp</option>
					    <option value="jpg">jpg</option>
					    <option value="gif">gif</option>
					</select>
					<a class="mini-button" iconCls="icon-addfolder" plain="true" onclick="dosubmit('daochu');">导出</a>
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10129')"></a>
	        </div>
        </div>
		<div showCollapseButton="false">
			<div id="showContent" style="height:30px;overflow:auto;display:none;background:#EFF2F5;color:#3789B8;text-align:center;"></div> 
	        <script src="<%=path%>/scripts/form/loadaip.js"></script>
         </div>
	</div>  
    <script type="text/javascript">
    	 var object = document.getElementById("HWPostil1");
    	 object.HideMenuItem(16384);
    	 object.ShowToolBar = 1;//工具栏是否显示
    	 object.ShowDefMenu = 0;//菜单栏
    	 object.ShowScrollBarButton = 1;
         mini.parse();
        var shortname=mini.get('shortname').getValue();
    	function dosubmit(submittype){
    		object.CloseDoc(0);
			var caseyear = mini.get("caseyear").getValue();
			var casetype = document.getElementById('casetype').value;
			var anjianhao = mini.get("anjianhao").getValue();
			var printscheme = document.getElementById("printscheme").value;
			var casetypetext=document.getElementById('casetype').options[document.getElementById('casetype').selectedIndex].text;
			if(anjianhao){
				$.ajax({
		             url: "<%=path%>/print/printBatch.action?1=1",
		             data: { caseyear:caseyear, casetype:casetype, printscheme:printscheme, anjianhao:anjianhao, casetypetext : casetypetext},
		             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		             cache: false,
		             type: "post",
		             success: function (text) {
		             	if(submittype=='dayin'){
			             	dayin(text);
		             	}else if(submittype=='merge') {
		             		merge(text);
		             	}else{
		             		daochu(text);
		             	}
		             },
		             error: function (jqXHR, textStatus, errorThrown) {
		             }
		         });
			}else{
				alert("请输入案件号！");
			}
		}

   function dayin(text){
		var aipObj=document.getElementById("HWPostil1");
		var showContent=document.getElementById('showContent');
		var anjianhao = mini.get("anjianhao").getValue();
		var anjianhaoArr = anjianhao.replace(new RegExp("，","g"),",").split(",");
		var casetypetext=document.getElementById('casetype').options[document.getElementById('casetype').selectedIndex].text;
		var num = 0;
		for(var i=0, n=anjianhaoArr.length; i<n; i++){
			var anjianhaoStr = anjianhaoArr[i];
			if(anjianhaoStr.indexOf("-") > 0){
				var arr = anjianhaoStr.split("-");
				num = num + arr[1] -arr[0] + 1;
			}else{
				num = num + 1;
			}

		}
		var objs = mini.decode(text);
		if(null==objs || ""==objs || (num > objs.length) ){
			alert("对不起，请核查是否存在打印案件！");
			return;
		}
		for(var j=0;j<objs.length;j++){ 
			aipObj.CloseDoc(0);
			aipObj.LoadFileBase64(objs[j].text);
			var num = 1;
			var tnum = objs[j].num;
			if(tnum){
				num = tnum;
			}
			showContent.innerHTML="正在打印:(" + objs[j].niandu + ")" + shortname + casetypetext + "第" + objs[j].anhao + "号";
			setInterval('',1000);
            showContent.style.display = "";
			aipObj.PrintDocEx('',1,0,0,0,9999,0,num,1,0,0);
		}
		showContent.innerHTML="打印结束！"
	}
	
        var flag = true;
        function dakai(){
       		var da = mini.get("dakai");
        	if(flag){
	       		object.HideMenuItem(16384);
        		da.setIconCls("icon-unlock");
        		object.ShowToolBar = 1;//工具栏是否显示
        		flag = false;
        	}else{alert(1);return;
        		da.setIconCls("icon-lock");
        		object.ShowToolBar = 0;//工具栏是否显示
        		flag = true;
        	}
        }
        
       function daochu(text){//导出
			var aipObj=document.getElementById("HWPostil1");
			aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
			var objs = eval(text);
			var Folder = BrowseFolder();
        	if(Folder==null){
        		return;
        	}
        	var daochugeshi = document.getElementById("daochugeshi").value;
        	var casetypetext=document.getElementById('casetype').options[document.getElementById('casetype').selectedIndex].text;
        	var tempgeshi = "."+daochugeshi;
			for(var j=0;j<objs.length;j++){ 
				aipObj.CloseDoc(0);
				aipObj.LoadFileBase64(objs[j].text);
				var num = 1;
				var tnum = objs[j].num;
				if(tnum){
					num = tnum;
				}
				if(aipObj.IsOpened()){
					var filename = "";
					if(objs[j].printschemename.indexOf("建议书") != -1){
						 filename =  objs[j].applyname+"减刑";
					}else{
					   filename = "(" + objs[j].niandu + ")" + shortname + casetypetext + "第" + objs[j].anhao + "号";
					}
					document.all.HWPostil1.SetValue("SET_TEMPFLAG_MODE", "2");//不加锁
        			document.all.HWPostil1.SaveTo(Folder+filename+tempgeshi,daochugeshi,0);
        		}
			}
		}
       function merge(text){//合并查看
			var aipObj=document.getElementById("HWPostil1");
			aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
			var objs = eval(text);
			for(var j=0;j<objs.length;j++){ 
				var num = 1;
				var tnum = objs[j].num;
				if(tnum){
					num = tnum;
				}
				for(var i=0;i<num;i++){//根据方案配置的打印数量 合并多份
					aipObj.MergeFile(99999,'STRDATA:'+objs[j].text);
				}
			}
		}		
         function BrowseFolder(){
			 try{
			  var Message = "请选择文件夹";  //选择框提示信息
			  var Shell = new ActiveXObject( "Shell.Application" );
			  var Folder = Shell.BrowseForFolder(0,Message,0x0040,0x11);//起始目录为：我的电脑
			  //var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
			  if(Folder != null){
			    Folder = Folder.items();  // 返回 FolderItems 对象
			    Folder = Folder.item();  // 返回 Folderitem 对象
			    Folder = Folder.Path;   // 返回路径
			    if(Folder.charAt(Folder.length-1) != "\\"){
			      Folder = Folder + "\\";
			    }
			    return Folder;
			  }
			  return null;
			 }catch(e){ 
			  	alert("请在ie设置中启用AcitveX控件相关选项。");
			  	return null;
			 }
		}
    </script>
 </body>
</html>