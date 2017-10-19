﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String formcontent = (String) request.getAttribute("formcontent");
String approve = (String) request.getAttribute("approve");
List<String> toplist = (ArrayList<String>)request.getAttribute("toplist");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>待办审批</title>
    <script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body >
    <!-- 宁夏用 勿动 -->
    <input id="ningxia" name="ningxia" value="${ningxia }" type="hidden"/>
	<div class="mini-toolbar">
	<table>
		<tr>
		<td style="width:100%;">
		<a class="mini-button"  id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span>
    	<%
			if(toplist!=null && toplist.size()>0){
				for (String obj : toplist) {
				    String[] top_= obj.split("@");
       %>
       <a class="mini-button" iconCls="<%=top_[3] %>" plain="true" onclick="<%=top_[2] %>"><%=top_[1] %></a>
       <%
				}
			}
		%>
		
		<a class="mini-button"  iconCls="icon-close" plain="true"  onclick="onCancel();">关闭</a>
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
	    	</td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
  	</div>
  	<input id="orgid" name="orgid" class="mini-hidden" />
  	<input id="shenptype" name="shenptype" class="mini-hidden" value="${shenptype}"/>
  	<input id="conent" name="conent" class="mini-hidden" />
  	<input id="snodeid" name="snodeid" class="mini-hidden" />
  	<input id="flowid" name="flowid" class="mini-hidden" />
  	<input id="flowdefid" name="flowdefid" class="mini-hidden" />
  	<input id="flowdraftid" name="flowdraftid" class="mini-hidden" />
  	<input id="isapprove" name="isapprove" class="mini-hidden"/>
	<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
    <script type="text/javascript">
    	var path = "<%=path%>";
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
	        mini.get("snodeid").setValue(data.snodeid);
	        mini.get("isapprove").setValue(1);
	        var flowdefid = document.getElementById("flowdefid").value;
	        if(flowdefid)isEditAttribute();//代办事项审批时，获取参数为空，导致节点不受控制，此处重新调用该方法
	    }
	    function operateData(type){
		    var resid='${resid}';
	    	operationOpprove(resid,type,"");
	    }
	    function operationOpprove(resid,isagree,opionval){
			var commenttext = '';
			var action='ok';
			var jumptype;
			var suggest2;
			var orgid = mini.get("orgid").getValue();
			var conent = mini.get("conent").getValue();
			var flowid = mini.get("flowid").getValue();
			var flowdefid = mini.get("flowdefid").getValue();
			var flowdraftid = mini.get("flowdraftid").getValue();
			var ningxia = $("#ningxia").val();
			//普通业务意见审批
			var selecturl = "<%=path%>/flow/toapproveview.action?1=1&flowdefid="+flowdefid+"&operateType="+isagree;
			
			if(isagree == 'no' || isagree == 'back' || isagree == 'defer'){
                if(ningxia == 1){
                	 commenttext = '不同意';
                	 action = 'ok';
                }else if(ningxia == 0 && (flowdefid == 'other_zfjyjxjssp' || flowdefid == 'other_jyjxjslasp')){
                	var vRet = window.showModalDialog(selecturl,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:200px;dialogWidth:500px");
    			  	if(vRet){ 
    					action = vRet[0]; 
    					commenttext = vRet[1];
    				}
                }
			}else{
				commenttext = '同意';
				action = 'ok';
			}	
			if(action == 'ok'){
   		  		jumpsave(resid,isagree,orgid,flowid,flowdefid,flowdraftid,conent,commenttext,opionval);
   	   		}
		}
		//js 转换日期格式
	    function formatDate(date){    
	    	var year = date.getFullYear();
	    	var yue = date.getMonth()+1;
	    	var day = date.getDate();
	    	var str = year +(yue>9?yue.toString():'0' + yue) + (day>9?day.toString():'0' + day);
	       
	        return str;    
	    } 
	    //普通业务意见审批
	    function jumpsave(resid,isagree,orgid,flowid,flowdefid,flowdraftid,conent,commenttext,opionval){
	    	var aipObj=document.getElementById("HWPostil1");
	    	var snodeid = mini.get("snodeid").getValue();
	    	if(opionval){//填写意见的字段和日期  name_date
				var val = opionval.split(';');
				
				if(val){
					for(var i=0;i<val.length;i++){
						var names = val[i].split('_');
						var value = aipObj.GetValueEx(names[0], 2, "", 0, "");
						if(value && isagree == 'yes'){
							commenttext = value;
						}
						//alert("names:"+names+",value="+value+",commenttext:"+commenttext);
						aipObj.SetValue(names[0],"");
						aipObj.SetValue(names[0],commenttext);
						aipObj.SetValue(names[1],"");
						aipObj.SetValue(names[1],formatDate(new Date()));
					}
				}
			}
	    	//表单所有节点及值
	    	var data = getNoteMap();
	    	//获取大字段
	    	var docconent = saveFile();
	    	
	    	//验证意见未填写时 不能提交
	    	var url = basePath+"/flow/ajaxGetFlowOpinionColumn.json?1=1";
			$.ajax({
		        url: url,
		        data: {resid:resid,flowdefid:flowdefid,operateType:isagree,snodeid:snodeid},
		        type: "post",
		        success: function (text) {
		       		var _flag = true;
		       		var objstr = eval(text);
		       		var obj;
		       		var ispopbox;
		       		if(objstr.indexOf(",")!=-1){
		       			obj=objstr.split(",")[0];
		       			ispopbox = objstr.split(",")[1];
		       		}
		            if(obj!=-1) {
						var aipObj = document.getElementById("HWPostil1");
		            	var comments = aipObj.GetValueEx(obj,2,"",0,"");
		            	if(!comments) _flag = false;
		            }
		            if(_flag) approveviewCommentsCheck(conent,commenttext,docconent,orgid,data,resid,flowid,flowdefid,flowdraftid,isagree)
		            else{
		            	alert("请填写意见!");
		            	retrun;
		            }
		        }
		    });
	    	
		}
	    //验证审批意见是否填写 
		function approveviewCommentsCheck(conent,commenttext,docconent,
		orgid,data,resid,flowid,flowdefid,flowdraftid,operateType){
			$.ajax({
   	            url: path+"/flow/flowapprove.action", 
   	            data: {conent:conent,commenttext:commenttext,docconent:docconent,orgid:orgid,data:data,
   	            resid:resid,flowid:flowid,flowdefid:flowdefid,flowdraftid:flowdraftid,operateType:operateType},
   	            type: "post",
   	            cache:false,
   	            async:false,
   	         	success: function (text) {
                    alert("操作成功!");
                    if(operateType != 'save')  
                    onCancel("close");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("操作失败!");
                }
   			});
		}
    </script>
</body>
</html>
