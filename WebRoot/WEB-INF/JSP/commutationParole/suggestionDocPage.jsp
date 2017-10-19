<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>建议书监督意见书生成模板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
     <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
    </style>
</head>
<body onload="SetData();">
	<input id="resid" name="resid" type="hidden" value="${resid}"/><!-- 用于建议书报表的查询方案 -->
	<input id="crimid" name="crimid" type="hidden" value="${crimid}"/>
	<input id="id" name="id" type="hidden" value="${id}"/><!-- flowdraftid拼接的数组 -->
	<input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
	<input id="flowdraftid" name="flowdraftid"  type="hidden" value="${flowdraftid}"/>
	<input id="flowid" name="flowid"  type="hidden" value="${flowid}"/>
	<input id="flowdefid" name="flowdefid"  type="hidden" value="${flowdefid}"/>
	<input id="doctype" name="doctype"  type="hidden" value="${doctype}"/>
	<input id="nowpunishmentyear" name="nowpunishmentyear"  class="mini-hidden" value="${nowpunishmentyear}"/><!-- 现刑种 -->
	<input id="paroletype" name="paroletype"  class="mini-hidden" value="${paroletype}"/> <!-- 减假暂类型 -->
    <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
    <div size="1px" showCollapseButton="false">
       <div class="mini-toolbar" style="border:0;padding:0px;">
		            <table style="width:100%;">
		                <tr>
		                	<td style="width:80%;">
		                	  <c:if test="${empty tempid}">
		                	  	模版：
		                		<input id="suggestmodel" class="mini-combobox"
		                    	 	valueField="tempid" textField="tempname" onvaluechanged="suggestselected"  
		                            url="getDocModelSelectList.action?doctype=${doctype}"   style="width:180px;" required="true"/>
		                        	建议时间：<input id="suggesttime" name="suggesttime" style="width:100px;"  class="mini-datepicker" required="true" />   
		                        <span class="separator"></span>
		                	  </c:if>
		                	  <c:if test="${!empty tempid}">
		                	  	<input id="suggestmodel" class="mini-hidden" value="${tempid}"/>
		                	  	建议时间：<input id="suggesttime" name="suggesttime" style="width:100px;"  class="mini-datepicker" required="true" />   
		                	  </c:if>
		                	  <c:if test="${modeledit eq 'yes'}">
		                	   <a class="mini-button" iconCls="icon-edit"  plain="true" onclick="editSuggestTemplate()">模板编辑</a>
		                	  </c:if>
		                        <a class="mini-button" iconCls="icon-save" id="saveDocBut" plain="true" onclick="saveBigData()">存盘</a>
		                        <a class="mini-button" iconCls="icon-new" id="saveDocPrint" plain="true" onclick="preview()">预览</a>
		                        <a class="mini-button" id="xyg" style="display:none;" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
		                        <a class="mini-button" id="gb"  style="display:none;" iconCls="icon-close" plain="true" onclick="onCanle()">关闭</a>
		                	</td>
		                    <td style="white-space:nowrap;">
			                    <input id="color"  name="mini-treeselect"  class="mini-combobox" valueField="id" textField="text" style="width:70px;"
								url="<%=path%>/txt/stype.txt" emptyText="样式选择" value="green" onvaluechanged="onselectChanged();"/>
						     	<input id="font"  name="mini-treeselect"  class="mini-combobox" valueField="id" textField="text" style="width:55px;" 
								url="<%=path%>/txt/font.txt" emptyText="字号"  value="zhong"  onvaluechanged="onselectChanged();"/>
		                    </td>
		                </tr>
		            </table>           
	       </div>
    </div>
    <div showCollapseButton="false" id="editForm1" >
       	<input id="annexcontent" name="annexcontent" class="mini-textarea"  style="width:100%; height:100%;"/>
 	</div>
</div>

    <script type="text/javascript">
        mini.parse();
       	var form = new mini.Form("editForm1");
       	//如果不是办案菜单 就隐藏 (此处可以 自定义一个变量来控制在不同的业务中是否显示)
       	var menuid = $("#menuid").val();
       	if(menuid == '10090_06'|| menuid == '10096' || menuid == '10091_07'){
             $("#saveDocBut").hide();
             $("#saveDocPrint").hide();
        }
       	function editSuggestTemplate(){
       		var tempid = mini.get("suggestmodel").getValue();
       		if(!tempid){
       			alert("请先选择模板！");
       			return ;
       		}
       		var win = mini.open({
                    url: "<%=path%>/toMeetingModel.action?1=1",
                    title: "建议书模板编辑", width: 800, height: 500,
                    showMaxButton: true,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {action: "edit",tempid:tempid};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                    }
             });
             win.max();
       	}
        
        function saveBigData() {
           	var data = form.getData();
           	var doctype = document.getElementById("doctype").value;
           	var crimid = document.getElementById("crimid").value;
           	var flowid = document.getElementById("flowid").value;
           	var flowdraftid = document.getElementById("flowdraftid").value;
           	var tempid = mini.get("suggestmodel").getValue();
           	var suggesttime = mini.get("suggesttime").getText();//建议书显示时间
           	var keywords=/[<>]/;
           	if(doctype) {
           		tempid = doctype;
           	}
           	if(!data.annexcontent){
           		alert("建议书内容不能为空！");
           		return;
           	}
           	if(keywords.test(data.annexcontent)){
           		alert("不能包含特殊字符！例如 ‘<’ ‘>’等特殊字符");
           		return ;
           	}
           $.ajax({
	                 url: "<%=path%>/saveSuggestion.action?1=1",
	                 type: "post",
	                 data:{bigdata:data.annexcontent, crimid:crimid, flowdraftid:flowdraftid, 
		                 flowid:flowid, tempid:tempid ,suggesttime:suggesttime},
	                 success: function (text) {
	                 	var obj = eval(text);
						if(obj=='1'){
							alert("操作成功！");
						}else{
							alert("操作失败！");
						}
	                }
	         });
        }
        
        //标准方法接口定义
        function SetData() {
        	onselectChanged();
        	var tempid = mini.get("suggestmodel").getValue();
        	if(!tempid){
        		var doctype = document.getElementById("doctype").value;
        		tempid= doctype
        	}
        	var crimid = document.getElementById("crimid").value;
        	var flowdraftid = document.getElementById("flowdraftid").value;
        	var flowid = document.getElementById("flowid").value;
        	var flowdefid = document.getElementById("flowdefid").value;
        	var nowpunishmentyear = document.getElementById("nowpunishmentyear").value;
        	var paroletype = document.getElementById("paroletype").value;
        	
        	var id = document.getElementById("id").value; 
        	if(id){
				var ids = id.split(",");
				id = ids[0];
				ids = id.split("@");
				flowdraftid = ids[0];
				crimid = ids[1];
			}
        	var menuid = document.getElementById("menuid").value;   
	        if(menuid=="12513"||menuid=="16105") {//如果菜单id是建议书管理，显示关闭、下一个按钮
	         	$("#xyg").show();
	         	$("#gb").show();
	        }
       		$.ajax({
	                 url: "getSingleSuggestDocumentData.action",
	                 type: "post",
	                 data:{tempid:tempid, crimid:crimid, flowdraftid:flowdraftid, flowid:flowid, flowdefid:flowdefid, nowpunishmentyear:nowpunishmentyear, paroletype:paroletype},
	                 success: function (text) {
	                 	if(text){
	                 		 var o = mini.decode(text);
                     		form.setData(o);
                     		if(o.suggesttime){
                     			mini.get("suggesttime").setText(o.suggesttime);
                         	}else{
                         		mini.get("suggesttime").setValue(new Date());
                            }
	                 	}
	                 }
	         });
        }
        
		//预览
		function preview(){
			var nowpunishmenttype = '';
			var crimid = document.getElementById("crimid").value;
        	var flowdraftid = document.getElementById("flowdraftid").value;
        	var flowid = document.getElementById("flowid").value;
        	var doctype = document.getElementById("doctype").value;
        	var tempid = mini.get("suggestmodel").getValue();
        	var aid = mini.get("suggestmodel").getValue();
        
        	var nowpunishmentyear = mini.get("nowpunishmentyear").getValue();
           	if(doctype) {
           		tempid = doctype;
           	}
           	if(nowpunishmentyear){
				if(nowpunishmentyear == '9995'){
					nowpunishmenttype = 2;
				}else if(nowpunishmentyear == '9996'){
					nowpunishmenttype = 3;
			    }else{
			    	nowpunishmenttype = 1;
				}
            }
        	var menuid = document.getElementById("resid").value; // 用于建议书报表的查询方案
        	var returnValue = '';
       		$.ajax({
	                 url: "checkSingleSuggestDocumentData.action",
	                 type: "post",
	                 async: false,
	                 data:{crimid:crimid, flowdraftid:flowdraftid, flowid:flowid,tempid:tempid,aid:aid},
	                 success: function (text) {
	                 	returnValue = eval(text);
	                 }
	         });
	         if(returnValue =='-1'){
	         	 alert("请先保存文书！");
	         	 return;
	         }else if(returnValue != '1'){
	         	 alert("操作失败！");
	         	 return;
	         }
	         
	        //menuid为用于查询方案的id
        	var url = "toPreviewSuggestReport.action?1=1&menuid="+menuid+"&flowdraftid="+flowdraftid+"&tempid="+tempid+"&crimid="+crimid+"&nowpunishmenttype="+nowpunishmenttype+"&aid="+aid;
        	
        	/*
        	if(doctype!=null && doctype!='') {
        		url = "getReducePenaltyByAnJianHao.action?1=1&menuid=10094&flowdraftid="+flowdraftid+"&tempid=" + doctype + "&crimid="+crimid;
        		menuid = '10094';
        	}
        	*/
        	var title="意见书";
			 mini.open({
                    url: url,
                    title: title, width: 900, height: 600,
                    showMaxButton: true,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {menuid : menuid, flowdraftid:flowdraftid, flowid:flowid, tempid:tempid,nowpunishmenttype:nowpunishmenttype,aid:aid};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                    }
              });
		}
		
		//案件字号改变时自动查询,刑期改变时自动查询
		function onselectChanged(){
        	var color=mini.get("color").getValue();
        	var font=mini.get("font").getValue();
        	document.getElementById("annexcontent").className="mini-textarea";
        	mini.get("annexcontent").setCls(color);
        	mini.get("annexcontent").setCls(font);
        } 
		//建议书下拉选择
		function suggestselected(e) {
        	SetData();
        }
		
		function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }   
        //下一个
      function saveNext(){
     	var id = document.getElementById("id").value;
     	var menuid = document.getElementById("menuid").value; 
        var index = id.indexOf(",");
        if(index>0){
        	id = id.substring(index+1,id.length);
        	if(menuid=="16105"){
        	  var url = "toSuggestionDocPage.action?1=1&menuid=16105&resid=12753&doctype=jailbaowai&id="+id+"&flowdefid=other_jybwjycbsp";
        	}else{
        	  var url = "toSuggestionDocPage.action?1=1&menuid=12513&resid=12498&doctype=jailcommute&id="+id+"&flowdefid=other_zfjyjxjssp";
        	}
            self.location.href=url;
            
        }else{
         	//alert("操作完毕！");
         	onCanle();
        }
     }
     //关闭返回到建议书管理列表页
     function onCanle(){
        var menuid = document.getElementById("menuid").value;
        if(menuid=="16105"){
         url = "toSuggestionManageListPage.page?1=1&modetype=jw&menuid="+menuid;
        }else{
          url = "toSuggestionManageListPage.page?1=1&menuid="+menuid;
        }   
      	self.location.href=url;
     }
    </script>
</body>
</html>