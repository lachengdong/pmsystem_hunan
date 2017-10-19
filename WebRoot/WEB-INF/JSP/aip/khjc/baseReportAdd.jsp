<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body>
	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
	<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
	<input id="flowdraftid" name="flowdraftid" class="mini-hidden" value="${flowdraftid}"/>
	<input id="solutionid" name="solutionid" class="mini-hidden" value="${solutionid}"/>
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
			<a class="mini-button"  style="" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span>
	    	${topstr}
	    	<a class="mini-button"  iconCls="icon-close" plain="true" onclick="Close();">关闭</a>
	    	<span class="separator"></span>
	    	<a class="mini-button"  style="" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   style="" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
    <script type="text/javascript">
        mini.parse();
        //获取表单节点和其对应的节点属性值
		function getNoteMap() {
			var aipObj = document.getElementById("HWPostil1");
			var NoteInfo;
			var notemap = new Map();
			while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
				var temp = NoteInfo.split(".")[1];
				var value = aipObj.GetValueEx(NoteInfo, 2, "", 0, "");
				notemap.put(temp, value);
			}
			return "["+mini.encode(notemap)+"]";
		}
		
		
		 //保存或更新
		/*
			templetid: 表单ID
			flowdefid: 流程定议ID
			masterslave: 操作主从附件表,master, slave
			bizName: 回调方法
		*/
        function savedata(masterslave,operateType,bizName){
       		//表单所有节点及值
			var noteinfo = getNoteMap();
			//获取大字段信息
			var aipObj=document.getElementById("HWPostil1");
			var docconent = aipObj.GetCurrFileBase64();
			var tempid = mini.get("tempid").getValue();
			var flowdefid = mini.get("flowdefid").getValue();
			flowdefid = flowdefid || 'other_sysflow';//如果是非流程的表单保存，默认用other_sysflow(系统流程);
			var solutionid = mini.get("solutionid").getValue();
			var flowdraftid = window.parent.mini.get("flowdraftid").getValue();
			if(!flowdraftid&&masterslave=='slave'){
				alert("请先做主文书！");
				return;
			}
			var applyid = window.parent.mini.get("applyid").getValue();
			var applyname = window.parent.mini.get("applyname").getValue();
       		var url="ajaxSaveBaseDoc.action?1=1";
            $.ajax({
                url:encodeURI(encodeURI(url)),
                data: { docconent:docconent,flowdraftid:flowdraftid,solutionid:solutionid,applyid:applyid,applyname:applyname,
                		   tempid:tempid,flowdefid:flowdefid,masterslave:masterslave,operateType:operateType,
                		   bizName:bizName,noteinfo:noteinfo+""
                		},
                type: "post",
                async:false,
                success: function (text) {
                	text = mini.decode(text);
                	if(text["rows"]==1){
                		window.parent.mini.get("flowdraftid").setValue(text["flowdraftid"]);
                		alert("操作成功！");
                	}else{
                		alert(text["status"]);
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
        }
        
        function operateMasterData(operateType,bizName){
        	//如果是提交、退回、拒绝时，要判断签章进程
        	if('yes'==operateType||'back'==operateType||'no'==operateType){
        		controlSealProcedure(operateType);
        	}
        	
        	savedata('master',operateType,bizName);
        	if(operateType=='yes'||operateType=='no'||operateType=='back'){
        		window.parent.toNext(1);
        	}
        }
        
        function operateSlaveData(operateType,bizName){
        	savedata('slave','save',bizName);
        }
        
        function operateData(operateType,bizName){
        	operateMasterData(operateType,bizName);
        }
        
        function cancelCase(){
        	alert("案件撤销！一会再做");
        }
        //手动关闭
        function Close(){
            window.parent.goBack();
        }
        
        //isCommit,是否是提交操作, 默认值 0, 可指定 0,1
        function toNext(isCommit){
        	window.parent.toNext(isCommit);
        }
        
        //上一个
        function toPrevious(){
        	window.parent.toPrevious();
        }
        
        //如果是提交、退回、拒绝时，要判断签章进程
        function controlSealProcedure(operateType){
        	
        }
        
    </script>
</body>
</html>
