<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>刑罚执行工作统计报表</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body onload="myonload()">
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
		<div size="38px;" showCollapseButton="false">
			<div class="mini-toolbar" style="height:30px;">
				<table>
					<tr>
						<td style="width:100%;">
					    	<a class="mini-button"  style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
							<a class="mini-button"  style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
					    	<span class="separator"></span> 
					    	<a class="mini-button" style="display:none;" id="jy3"  iconCls="icon-upload" plain="true" onclick="insertqrutf8('save')">报省局</a> 
							<a class="mini-button" style="display:none;" id="sj2" iconCls="icon-upload" plain="true" onclick="insertqrutf8('save')">报部局</a> 
							<a class="mini-button"  iconCls="icon-save" plain="true" onclick="reportview()">统计报表</a>
							<!-- <a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel();">关闭</a> -->
							<input id="qrCode" style="padding:2px;border-top:0;border-left:0;border-right:0;width:1px;height:1px;" />
							<input id="userid" value = "${userId}" type ="hidden" />
							<input id="unitlevel" value = "${unitlevel}" type="hidden" />
							<input id="orgId" value = "${orgId}" type="hidden" />
					    	<span class="separator"></span> 
					    	<a class="mini-button"  style="display:none;" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
					    	<a class="mini-button"  style="display:none;" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
					    	<a class="mini-button"  style="display:none;" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
					    	<span class="separator"></span> 
					    	<a class="mini-button"  style="display:none;" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
					    	<a class="mini-button"  style="display:none;" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
					    	<a class="mini-button"  style="display:none;" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
					    	<span class="separator"></span> 
					    	<a class="mini-button"  style="display:none;" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
					    	<span class="separator"></span> 
					    	<a class="mini-button"  style="display:none;" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
					    	<a class="mini-button"  style="display:none;" id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
					    	<a class="mini-button"  style="display:none;" id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
					    </td>
					    <td style="white-space:nowrap;">
					    	<a class="mini-button"   style="display:none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
					    </td>
				  	</tr>
			  	</table>
		  	</div>
			
		  	<!-- 
		  	<div class="mini-fit" style="width:100%; height:100%;">
		       <div style="width:100%;height:100%;">
					<object id=HWPostil1 height='99%' width='100%' style='LEFT: 0px; TOP: 0px' classid='clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561'></object>
				</div>
		    </div>  
		    	<a class="mini-button" style="display:none;" id="13932" iconCls="icon-save" plain="true" onclick="saveData('<%=path %>/saveStatisticalPunishment.json?1=1');">存盘</a>
		    	<a class="mini-button"  iconCls="icon-print" plain="true" onclick="doMenueButton(9);">文档打印</a>&nbsp;
			 -->
  		</div>
		<div showCollapseButton="false">
		  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
	  	</div>
  </div>
  <script type="text/javascript">
      function myonload(){
      	init('${menuid}');
		//如果用户单位级别是监狱（3），显示报省局
		if(${unitlevel} == "3"){
			$("#jy3").show(); 
		}else if(${unitlevel} == "2"){
			$("#sj2").show(); 
		}
      	var mydata = document.getElementById("mydata").value;
      	if(mydata){
      		document.getElementById("HWPostil1").LoadFileBase64(mydata);
      		object.SetValue("date",":PROP:READONLY:0");
    		object.SetValue("date","");
    		object.SetValue("date",new Date().Format("yyyyMMdd"));
    		object.SetValue("date",":PROP::LABEL:0");
		}
	}	
	
	//默认采用此种utf8编码的方式进行生成
	function insertqrutf8(type){
		document.all.HWPostil1.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
		var obj = document.getElementById("HWPostil1");
		
		var shijian = obj.GetValueEx("date",2,"",0,"");  
		var tongjiren = obj.GetValueEx("tongjiren",2,"",0,""); 
		var shenheren = obj.GetValueEx("shenheren",2,"",0,"");  
		var phone = obj.GetValueEx("phone",2,"",0,"");  
		var data = "ATA:@"+${orgId}+"@"+shijian+"@@"+"@"+tongjiren+"@"+shenheren+"@"+phone;//@@是年份、月份占位符
		for(var i=0,n=2; i<=154; i++){
			var str = "text"+i;
			var value = obj.GetValueEx(str,2,"",0,"");
			data = data+"@"+value;
		}
		saveDate(type,encodeURI(data));//上报保存
		if(type=="save"){
			document.all.HWPostil1.InsertPicture("刑罚执行工作统计报表","BARCODEUTF8DATA:"+encodeURI(data),2,34960,34000,13107400);
		} 
	}
	var count = 0;	
  	//键盘属性，当扫描枪扫描时，点击按键Shift时，num取值16
	document.onkeydown = function(){
		var oEvent = window.event;  
   	    var num = oEvent.keyCode;
        if(num == 16){ 
        	if(count >0 ){
        		return;
        	}
        	count ++;
        	document.getElementById("qrCode").focus();  
    		saveDate('qrCode',''); //扫描保存
        } 
	}
	//保存数据
	function saveDate(type,qrCode){
		if(type=="qrCode"){
			qrCode = document.getElementById("qrCode").value;
		} 
		if(qrCode){
			$.ajax({
				url : "saveTwoDimensionCode.json?1=1",
				type : "POST",
				async: false,
				data : {qrCode : qrCode},
				success : function(text) {
					if(type=="qrCode"){
						var furl = "<%=path%>/statisticsOfPenaltyExecution.page?1=1";
						self.location.href=furl;
					}else{
						if(type=="save") alert("保存成功！");
					} 
				},
				error: function () {
	                alert("保存失败！");
	            }
			});
		}
    }
	//跳转到统计报表页面
	function reportview() {
		var orgid = document.getElementById("orgId").value;
		var year = new Date().getFullYear();
		var month = new Date().getMonth()+1;
		var url = "<%=path%>/theReportPageJump.page?1=1&menuid=${menuid}&orgid="+orgid+"&year="+year+"&month="+month;
		self.location.href = url;
		insertqrutf8();
	}

   	
   	
   	
      
  //保存
  function saveData(url){
  	var MDCODE=saveFile();
  	var json = getNoteMap();
  	if(json.length>0){
  		$.ajax({
  			type:"POST", 
  			url:url, 
  			data:{data:json,tempid:'${tempid}',crimid:'${crimid}',
  			docid:'${docid}',introduction:"刑罚执行统计报表",content:MDCODE},
  			cache:false,
  			success:function (text) {
  				alert("操作成功!");
  			}
  	  	});
  	}
  }
  function CloseWindow(action) {            
      if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
      else window.close();            
  }
  function onCancel(e) {
      CloseWindow("cancel");
  }
        
    </script>
</body>
</html>
