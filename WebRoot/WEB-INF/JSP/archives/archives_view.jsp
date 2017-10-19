﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String  isapprove = (String)request.getAttribute("isapprove");
String formcontent = (String) request.getAttribute("formcontent");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查看档案</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
	</style>
</head>
<body onload="init('${resid}');">
<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
	<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
		<a class="mini-button"  id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
		<a class="mini-button"  id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
		<span class="separator"></span> 
		<a class="mini-button" style="display:none;" id="11613" style="display: none;" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('10613','yes','');">同意</a>
    	<a class="mini-button" style="display:none;" id="11614" iconCls="icon-upgrade" plain="true" onclick="operationOpprove('11614','no','');">不同意</a>
		<a class="mini-button" id="" iconCls="icon-close" plain="true" onclick="close();">关闭</a>
    	<span class="separator"></span> 
    	<a class="mini-button"  id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
    	<a class="mini-button"  id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
    	<a class="mini-button"  id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
    	<span class="separator"></span> 
    	<a class="mini-button"  id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
    	<a class="mini-button"  id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
    	<a class="mini-button"  id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
    	<span class="separator"></span> 
    	<a class="mini-button"  id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
    	<span class="separator"></span> 
    	<a class="mini-button"  id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
    	<a class="mini-button"  id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
    	<a class="mini-button"  id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
<!--     	<a class="mini-button"  id="12468" iconCls="icon-gk_forward" tooltip="前一页" plain="true" onclick="doMenueButton(15);"></a>-->
<!--    	<a class="mini-button"  id="12469" iconCls="icon-gk_next" tooltip="后一页" plain="true" onclick="doMenueButton(16);"></a>-->
    	<a class="mini-button"  id="1246801" iconCls="icon-gk_forward" tooltip="前一页" plain="true" onclick="pageup();"></a>
    	<a class="mini-button"  id="1246901" iconCls="icon-gk_next" tooltip="后一页" plain="true" onclick="pagedown();"></a>
    	</td>
    	<td style="white-space:nowrap;">
	    	<a class="mini-button"   style="display:none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	    </tr>
	  	</table>
     </div>	
  	</div>
	<div showCollapseButton="false">
		<input id="archiveShenPiSeal" value="${archiveShenPiSeal}" class="mini-hidden" />
		<input id="orgid" name="orgid" class="mini-hidden" />
	  	<input id="conent" name="conent" class="mini-hidden" />
	  	<input id="flowid" name="flowid" class="mini-hidden" />
	  	<input id="flowdefid" name="flowdefid" class="mini-hidden" />
	  	<input id="flowdraftid" name="flowdraftid" class="mini-hidden" />
	  	<input id=rank name="rank" class="mini-hidden"/>
	  	<input id=retention name="retention" class="mini-hidden"/>
	  	<input id=archiveid name="archiveid" class="mini-hidden"/>
	  	<input id=applyid name="applyid" class="mini-hidden"/>
	  	<input id=applyname name="applyname" class="mini-hidden"/>
	  	<input id="pageIndex" name="pageIndex" class="mini-hidden" />
	  	<input id="pageSize" name="pageSize" class="mini-hidden" />
	  	<input id="url" name="url" class="mini-hidden" />
	  	<input id="key" name="key" class="mini-hidden" />
	  	<input id="resid" name="resid" class="mini-hidden" />
	  	<input id="codeid" name="codeid" class="mini-hidden" />
	  	<input id="treevalue" name="treevalue" class="mini-hidden" />
	  	<input id="sortField" name="sortField" class="mini-hidden" />
	  	<input id="sortOrder" name="sortOrder" class="mini-hidden" />
	  	<input id="total" name="total" class="mini-hidden" />
	  	<input id="pagenum" name="pagenum" class="mini-hidden" />
	  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
    <script type="text/javascript">
        mini.parse();
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }

		 //标准方法接口定义
	    function SetData(data) {
	        //跨页面传递的数据对象，克隆后才可以安全使用
	        data = mini.clone(data);
	        mini.get("orgid").setValue(data.orgid);
	        mini.get("conent").setValue(data.conent);
	        mini.get("flowid").setValue(data.flowid);
	        mini.get("flowdefid").setValue(data.flowdefid);
	        mini.get("flowdraftid").setValue(data.flowdraftid);
	        mini.get("rank").setValue(data.rank);
	        mini.get("retention").setValue(data.retention);
	        mini.get("archiveid").setValue(data.archiveid);
	        mini.get("applyid").setValue(data.applyid);
	        mini.get("applyname").setValue(data.applyname);
	        
	        mini.get("url").setValue(data.url);
	        mini.get("pageIndex").setValue(data.pageIndex);
	        mini.get("pageSize").setValue(data.pageSize);
	        mini.get("sortField").setValue(data.sortField);
	        mini.get("sortOrder").setValue(data.sortOrder);
	        mini.get("key").setValue(data.key);
	        mini.get("total").setValue(data.total);
	        mini.get("pagenum").setValue(data.pagenum);
	    }
	    function close(){
	    	onCancel();
		}
	    function operationOpprove(resid,isagree,opionval){
			var commenttext = '';
			var action;
			var jumptype;
			var suggest2;
			var orgid = mini.get("orgid").getValue();
			var conent = mini.get("conent").getValue();
			var flowid = mini.get("flowid").getValue();
			var flowdefid = mini.get("flowdefid").getValue();
			var flowdraftid = mini.get("flowdraftid").getValue();
			var archiveShenPiSeal = mini.get("archiveShenPiSeal").getValue();
			
			//普通业务意见审批
			var selecturl = "<%=path%>/flow/toapproveview.action?1=1&flowdefid="+flowdefid;
			if(isagree == 'no' || isagree == 'back'){
				var vRet = window.showModalDialog(selecturl,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:200px;dialogWidth:500px");
			  	if(vRet){ 
					action = vRet[0]; 
					commenttext = vRet[1];
				}
			}else{
				commenttext = '同意';
				action = 'ok';
			}	
			if(action == 'ok'){
				if(archiveShenPiSeal=='1'){//需要签章审批
					jumpsaveSeal(resid,isagree,orgid,flowid,flowdefid,flowdraftid,conent,commenttext,opionval);
				}else{//不需要签章审批
   		  			jumpsave(resid,isagree,orgid,flowid,flowdefid,flowdraftid,conent,commenttext,opionval);
   		  		}
   	   		}
		}
	  	//普通业务意见审批
	    function jumpsave(resid,isagree,orgid,flowid,flowdefid,flowdraftid,conent,commenttext,opionval){
		    var rank = mini.get("rank").getValue();
		    var retention = mini.get("retention").getValue();
		    var archiveid = mini.get("archiveid").getValue();
		    var applyid = mini.get("applyid").getValue();
		    var applyname = mini.get("applyname").getValue();
	    	$.ajax({
   	            url:"<%=path%>/flow/batchapprove.action", 
	    		data: {applyids:applyid,applynames:applyname,conents:conent,flowids:flowid,resid:resid,isapprove:'yes',commenttext:commenttext,
            	rank:rank,retention:retention,archiveids:archiveid,approve:isagree,orgids:orgid,flowdraftids:flowdraftid,flowdefid:'arch_zfdajdsp'},
   	            type: "post",
   	            cache:false,
   	            async:false,
   	            success: function (text){
            		close();
   	            }
   			});
		}
		//需要签章业务意见审批
	    function jumpsaveSeal(resid,isagree,orgid,flowid,flowdefid,flowdraftid,conent,commenttext,opionval){
		    var rank = mini.get("rank").getValue();
		    var retention = mini.get("retention").getValue();
		    var archiveid = mini.get("archiveid").getValue();
		    var applyid = mini.get("applyid").getValue();
		    var applyname = mini.get("applyname").getValue();
		    var aipObj=document.getElementById("HWPostil1");
		    var dtrer=0;
			aipObj.Logout();
			dtrer = aipLogin(aipObj);
			if(dtrer==-200)
			{
				alert("未发现智能卡");return;
			}
			else if(dtrer!=0){
				alert("登录失败。错误ID:"+dtrer);return;
			}
		    var vNoteName ="Page1.PageText4";
			aipObj.InsertNote(vNoteName,0,3,"37000","46200","7000","1600");//添加编辑域
		    aipObj.Setvalue(vNoteName,":PROP:BORDER:0");//设置区域无边框
			aipObj.Setvalue(vNoteName,":PROP::LABEL:1"); //设置为不可编辑
			aipObj.Setvalue(vNoteName,":PROP::LABEL:2"); //可选,(对于编辑器不可点击)	
			aipObj.Setvalue(vNoteName,":PROP:VALIGN:1");//设置竖直剧中
		    aipObj.Setvalue(vNoteName,":PROP:FACENAME:黑体");//设置字体
		    aipObj.Setvalue(vNoteName,":PROP:FRONTCOLOR:0");//设置字体颜色
		    aipObj.Setvalue(vNoteName,":PROP:FONTSIZE:16" );//设置文字域字体大小
		    aipObj.Setvalue(vNoteName,":PROP:FONTBOLD:1"); //设置文字是否为BOLD	
		    aipObj.Setvalue(vNoteName,":PROP:FONTITALIC:0"); //设置文字是否为斜体0正
		    aipObj.Setvalue(vNoteName,"审核人：");//设置值
		    var sealcontent="";
			var sealdate="";
			var cppoopiniondate="";
			var cppoopinion="";
			var strSignatureNoteInfo ="节点类型:1,页码:1,上:47000,左:46000,高:7500,宽:2000";
			var flag = InsertNote(strSignatureNoteInfo,aipObj,sealcontent,sealdate,cppoopiniondate,cppoopinion);
			if(flag){
				var docconent = aipObj.GetCurrFileBase64();//获取大字段
		    	$.ajax({
	   	            url:"<%=path%>/flow/batchapprove.action", 
		    		data: {docconent:docconent,applyids:applyid,applynames:applyname,conents:conent,flowids:flowid,resid:resid,isapprove:'yes',commenttext:commenttext,
	            	rank:rank,retention:retention,archiveids:archiveid,approve:isagree,orgids:orgid,flowdraftids:flowdraftid,flowdefid:'arch_zfdajdsp'},
	   	            type: "post",
	   	            cache:false,
	   	            async:false,
	   	            success: function (text){
	            		close();
	   	            }
	   			});
   			}
		}
		function pageup(){
			if(mini.get("pageIndex").getValue()==0){
				alert("已经是第一页！");
				return;
			}
			mini.get("pageIndex").setValue(mini.get("pageIndex").getValue()-1);
			nextPage(-1);
		}
		function pagedown(){
			if(mini.get("pageIndex").getValue()+1>mini.get("total").getValue()/mini.get("pageSize").getValue()){
				alert("已经是最后一页！");
				return;
			}
			mini.get("pageIndex").setValue(mini.get("pageIndex").getValue()+1);			
			var aipObj = document.getElementById("HWPostil1");
			mini.get("pagenum").setValue(parseInt(mini.get("pagenum").getValue())+aipObj.PageCount);
			nextPage();
		}
		function nextPage(direction){
			var pagenum = '';
			if(direction==-1){
				pagenum = 0-parseInt(mini.get("pagenum").getValue());
			}else{
				pagenum = parseInt(mini.get("pagenum").getValue());
			}
			var url = mini.get("url").getValue();
			url = url.replace('getlookarchives','getlookarchivesforaip');
			var pageIndex = mini.get("pageIndex").getValue();
			var pageSize = mini.get("pageSize").getValue();
			var sortField = mini.get("sortField").getValue();
			var sortOrder = mini.get("sortOrder").getValue();
			var key = mini.get("key").getValue();
			$.ajax({
		             url: url,
		             data: {pageIndex:pageIndex,pageSize:pageSize,sortField:sortField,sortOrder:sortOrder,key:key},
		             type: 'POST',
		             async:false,
		             success: function (text){
		             	var archiveids = eval(text);
		             	$.ajax({
			             url: "<%=path%>/flow/printArchives.action?1=1&resid=0&ismultipage=0"+"&archiveid="+archiveids+"&pagenum="+pagenum,
			             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			             cache: false,
			             async:false,
			             type: "post",
			             success: function (text){
			             		var objs = mini.decode(eval(text));
								var aipObj=document.getElementById("HWPostil1");
								aipObj.CloseDoc(0);
		    					HWPostil1_modelReady(objs,'0',pagenum);
			             },
			             error: function (jqXHR, textStatus, errorThrown) {
			             	alert(errorThrown);
			             }
		              });
		             },
		             error: function (jqXHR, textStatus, errorThrown) {
		                 alert(errorThrown);
		             }
		         });
		}
    </script>
</body>
</html>
