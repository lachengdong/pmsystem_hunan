<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <title>编辑面板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    

    <style type="text/css">
    html, body
    {
        font-size:12px;
        padding:0;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden; 
    }
    </style>
</head>

  
  <body>
    <form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <input name="mymenuid" class="mini-hidden" />
        <input name="gkzxtempid" id="gkzxtempid" class="mini-hidden" />
        <input id="operatetype" name="operatetype" class="mini-hidden" />
        <input id="punid" name="punid" value="${tbxfPunishmentRang.punid}" class="mini-hidden" />
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
		            		<a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
		           			<a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>           
	        </div>
        	<div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;" border="0">
				<tr >
			        <td>犯罪时间：</td>
					<td width="150">
			        	<input name="senid" id="senid" value="${tbxfPunishmentRang.senid}" class="mini-treeselect" popupWidth="150" valueField="id" textField="text"  emptyText="请选择..." parentField="remark" showNullItem="true" checkRecursive="true" expandOnload="true" nullItemText="" url="ajaxSelectPrisonerSentence.action" style="width:125px;" allowInput="true" required="true" />
			        </td>
			        <td width="85">案件类别：</td>
					<td>    
			        	<input id="type" name="type" value="${tbxfPunishmentRang.type}" class="mini-combobox" data="typearray" emptyText="请选择..." showNullItem="true" nullItemText="请选择..." required="true"/>
			        </td>
			        <td>是否减过刑：</td>
			        <td>
			        	<input id="alreadycommutation" name="alreadycommutation" value="${tbxfPunishmentRang.alreadycommutation}" class="mini-combobox" data="yesorno" emptyText="请选择..." showNullItem="true" nullItemText="请选择..."  required="true" />
			        </td>
				</tr>
				<tr>
					<td>原判刑期种类：</td>
					<td>
						<input id="sentenceType" name="sentenceType" value="${tbxfPunishmentRang.sentenceType}" class="mini-combobox" data="sentenceTypeData" emptyText="请选择..." showNullItem="true" nullItemText="请选择..." onvaluechanged="sentenceTypeChange"/>
					</td>
					<td>原判刑期起：</td>
					<td width="150">    
					    <div id="showoriginalstart" <c:if test="${tbxfPunishmentRang.sentenceType!=1}">style="display:none" </c:if>>
			        		<input id="originalstart" name="originalstart" value="${tbxfPunishmentRang.originalstart}" class="mini-textbox" vtype="int;maxLength:9" enabled="true"/>
			        	</div>
					    <div id="hiddenoriginalstart" <c:if test="${tbxfPunishmentRang.sentenceType==1}">style="display:none"</c:if>>
			        		<input id="originalstart1" name="originalstart1" value="${tbxfPunishmentRang.originalstart}" class="mini-textbox" vtype="int;maxLength:9" enabled="false"/>
			        	</div>
			        </td>
					<td>原判刑期止：</td>
					<td>    
					    <div id="showoriginalend" <c:if test="${tbxfPunishmentRang.sentenceType!=1}">style="display:none" </c:if>>
			        	    <input id="originalend" name="originalend" value="${tbxfPunishmentRang.originalend}" class="mini-textbox" vtype="int;maxLength:9" enabled="true"/>
			        	</div>
			        	<div id="hiddenoriginalend" <c:if test="${tbxfPunishmentRang.sentenceType==1}">style="display:none"</c:if>>
			        		<input id="originalend1" name="originalend1" value="${tbxfPunishmentRang.originalend}" class="mini-textbox" vtype="int;maxLength:9" enabled="false"/>
			        	</div>
			        </td>					
				</tr>
				<tr>
					<td>现刑期种类：</td>
					<td>
						<input id="nowsentencetype" name="nowsentencetype" value="${tbxfPunishmentRang.nowsentencetype}" class="mini-combobox" data="sentenceTypeData" emptyText="请选择..." showNullItem="true" nullItemText="请选择..." onvaluechanged="nowsentenceTypeChange"/>
					</td>
					<td>现刑期起：</td>
					<td>    
					    <div id="showsentencestart" <c:if test="${tbxfPunishmentRang.nowsentencetype!=1}">style="display:none" </c:if>>
			        		<input id="sentencestart" name="sentencestart" value="${tbxfPunishmentRang.sentencestart}" class="mini-textbox" vtype="int;maxLength:9" enabled="true"/>
			        	</div>
					    <div id="hiddensentencestart" <c:if test="${tbxfPunishmentRang.nowsentencetype==1}">style="display:none"</c:if>>
			        		<input id="sentencestart1" name="sentencestart1" value="${tbxfPunishmentRang.sentencestart}" class="mini-textbox" vtype="int;maxLength:9" enabled="false"/>
			        	</div>
			        </td>
					<td>现刑期止：</td>
					<td>    
					    <div id="showsentenceend" <c:if test="${tbxfPunishmentRang.nowsentencetype!=1}">style="display:none" </c:if>>
			        	    <input id="sentenceend" name="sentenceend" value="${tbxfPunishmentRang.sentenceend}" class="mini-textbox" vtype="int;maxLength:9" enabled="true"/>
			        	</div>
			        	<div id="hiddensentenceend" <c:if test="${tbxfPunishmentRang.nowsentencetype==1}">style="display:none"</c:if>>
			        		<input id="sentenceend1" name="sentenceend1" value="${tbxfPunishmentRang.sentenceend}" class="mini-textbox" vtype="int;maxLength:9" enabled="false"/>
			        	</div>
			        </td>					
				</tr>				
				<tr >
					<td>间隔期起：</td>
					<td>    
			        	<input id="intervalperiod" name="intervalperiod" value="${tbxfPunishmentRang.intervalperiod}" class="mini-textbox" vtype="int;maxLength:9"/>
			        </td>
					<td>间隔期止：</td>
					<td>    
			        	<input id="endintervalperiod" name="endintervalperiod" value="${tbxfPunishmentRang.endintervalperiod}" class="mini-textbox" vtype="int;maxLength:9"/>
			        </td>
					<td>间隔期占原判比例：</td>
					<td>    
			        	<input id="intervalperiodscale" name="intervalperiodscale" value="${tbxfPunishmentRang.intervalperiodscale}" style="width:100px;" class="mini-textbox"/>以上
			        </td>		        
				</tr>
				<tr >
					<td>执行刑期起：</td>
					<td>    
			        	<input id="executionsentencesstart" name="executionsentencesstart" value="${tbxfPunishmentRang.executionsentencesstart}" class="mini-textbox" vtype="int;maxLength:9"/>
			        </td>
					<td>执行刑期止：</td>
					<td>    
			        	<input id="executionsentencesend" name="executionsentencesend" value="${tbxfPunishmentRang.executionsentencesend}" class="mini-textbox" vtype="int;maxLength:9"/>
			        </td>
					<td>执行刑期占原判比例：</td>
					<td>    
			        	<input id="executionsentencescale" name="executionsentencescale" value="${tbxfPunishmentRang.executionsentencescale}" style="width:100px;" class="mini-textbox"/>以上
			        </td>			        
				</tr>
				<tr >
					<td>起始时间起：</td>
					<td>    
			        	<input id="startperiod" name="startperiod" value="${tbxfPunishmentRang.startperiod}" class="mini-textbox" vtype="int;maxLength:9" />
			        </td>
					<td>起始时间止：</td>
					<td>    
			        	<input id="endperiod" name="endperiod" value="${tbxfPunishmentRang.endperiod}" class="mini-textbox" vtype="int;maxLength:9"/>
			        </td>
			        <td>减为有期徒刑后首次减刑：</td>
			        <td>
			        	<input id="firstcommutation" name="firstcommutation" value="${tbxfPunishmentRang.firstcommutation}" class="mini-combobox" data="yesorno" emptyText="请选择..." showNullItem="true" nullItemText="请选择..."  required="true" />
			        </td>
				</tr>
				<tr >
			        <td>减刑幅度限制：</td>
			        <td>
			        	<input id="rangelimit" name="rangelimit" value="${tbxfPunishmentRang.rangelimit}" class="mini-textbox" vtype="int;maxLength:9"/>
			        </td>				
					<td>执行刑期限制：</td>
					<td>    
			        	<input id="executesentencelimit" name="executesentencelimit" value="${tbxfPunishmentRang.executesentencelimit}" class="mini-textbox" vtype="int;maxLength:9" />
			        </td>
					<td>执行刑期占原判比例限制：</td>
					<td>    
			        	<input id="executesentencescalelimit" name="executesentencescalelimit" value="${tbxfPunishmentRang.executesentencescalelimit}" style="width:100px;" class="mini-textbox"/>以上
			        </td>
				</tr>
				<tr >
		        	<td>排序：</td>
					<td colspan="5">
					<input name="sn" id="sn" value="${tbxfPunishmentRang.sn}" class="mini-spinner" minValue="1"/>
			        </td>
				</tr>
				<tr>
					<td></br></td>
				</tr>			
				<tr>
					<td colspan="4"><font color="red">说明：单位均为月,比例为小数.</font></td>
				</tr>												
            </table>
        </div>
    </form>

  </body>
</html>
<script type="text/javascript">
		var genderarray = [{id:"0",text:"男"},{id:"1",text:"女"}];
		var typearray = [{id:"1",text:"减刑"},{id:"2",text:"假释"}];
		var yesorno = [{id:"0",text:"否"},{id:"1",text:"是"}];
		var sentenceTypeData = [{id:"1",text:"有期徒刑"},{id:"2",text:"无期徒刑"},{id:"3",text:"死刑缓期两年执行"}];
		function onOk(){
			SaveData();
		}
		function SaveData() {
			var operatetype = mini.get("operatetype").getValue();
			var punid = mini.get("punid").getValue();
			var form = new mini.Form("form1");
			var o = form.getData();
	        form.validate();
	        check();
	        if (form.isValid() == false) return;
	        var json = mini.encode([o]);
	        $.ajax({
                url: "savePunishmentRang.action?1=1",
                data: {data:json, operatetype:operatetype, punid:punid},
                type: "post",
                async:false,
                success: function (text) {
                	CloseWindow("save");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
	    }
		//标准方法接口定义
        function SetData(data) {
        	data = mini.clone(data);
            mini.get("operatetype").setValue(data.action);
        }
		function onCancel(e) {
            CloseWindow("cancel");
        }
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        }
        function check(){
			var executionsentencesstart = mini.get("executionsentencesstart").getValue();
			var executionsentencesend = mini.get("executionsentencesend").getValue();
			if(executionsentencesstart!=""&&executionsentencesend!=""&&parseInt(executionsentencesend)<=parseInt(executionsentencesstart)){
				mini.get("executionsentencesstart").setIsValid(false);
				mini.get("executionsentencesend").setIsValid(false);
				alert("执行刑期止应大于执行刑期起！");
			}
			        
			var sentencestart = mini.get("sentencestart").getValue();
			var sentenceend = mini.get("sentenceend").getValue();
			if(sentencestart!=""&&sentenceend!=""&&parseInt(sentenceend)<=parseInt(sentencestart)){
				mini.get("sentencestart").setIsValid(false);
				mini.get("sentenceend").setIsValid(false);
				alert("现刑期止应大于现刑期起！");
			}
			var startperiod = mini.get("startperiod").getValue();
			var endperiod = mini.get("endperiod").getValue();
			if(startperiod!=""&&endperiod!=""&&parseInt(endperiod)<=parseInt(startperiod)){
				mini.get("startperiod").setIsValid(false);
				mini.get("endperiod").setIsValid(false);
				alert("考核时间止应大于考核时间起！");
			}
			var intervalperiod = mini.get("intervalperiod").getValue();
			var endintervalperiod = mini.get("endintervalperiod").getValue();
			if(intervalperiod!=""&&endintervalperiod!=""&&parseInt(endintervalperiod)<=parseInt(intervalperiod)){
				mini.get("intervalperiod").setIsValid(false);
				mini.get("endintervalperiod").setIsValid(false);
				alert("间隔期止应大于间隔期起！");
			}
            return true;
        }
        
        function sentenceTypeChange(e) {
            if(e.value=='1') {
                document.getElementById("showoriginalstart").style.display="";
        		document.getElementById("hiddenoriginalstart").style.display="none";
        		document.getElementById("showoriginalend").style.display="";
        		document.getElementById("hiddenoriginalend").style.display="none";
            } else {
                document.getElementById("showoriginalstart").style.display="none";
        		document.getElementById("hiddenoriginalstart").style.display="";         
                document.getElementById("showoriginalend").style.display="none";
        		document.getElementById("hiddenoriginalend").style.display="";         		   
            }
        }
        function nowsentenceTypeChange(e) {
            if(e.value=='1') {
                document.getElementById("showsentencestart").style.display="";
        		document.getElementById("hiddensentencestart").style.display="none";
        		document.getElementById("showsentenceend").style.display="";
        		document.getElementById("hiddensentenceend").style.display="none";
            } else {
                document.getElementById("showsentencestart").style.display="none";
        		document.getElementById("hiddensentencestart").style.display="";         
                document.getElementById("showsentenceend").style.display="none";
        		document.getElementById("hiddensentenceend").style.display="";         		   
            }
        }        
</script>
