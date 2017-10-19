<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	String tempid = request.getParameter("tempid");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑表单管理</title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path %>/css/CIC.css" rel="stylesheet" type="text/css"/>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; font-size: 12px;
		    }   
		</style>
	</head>

	<body>
		<div id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border: 0px;">
			<div size="30px;" showCollapseButton="false" style="border-top: 0px;border-left: 0px;border-right: 0px;">
				<div class="mini-toolbar" style="padding: 1px; border-bottom: 0px;">
					<table>
						<tr>
							<td style="width: 92%;">
								表单ID：<input name="tempid" id="tempid" class="mini-textbox" minValue="0" maxValue="20" required="true"/>
								表单名称：<input name="tempname" id="tempname" class="mini-textbox" required="true" />
			   	    			系统模块：<input name="functionname" id="functionname" class="mini-textbox" required="true"/>
			   	    			模板类型：
			   	    			<select name="type" id="type" style="width: 70px;">
			   	    				<option id="2" selected="selected">表单</option>
			   	    				<option id="1">系统模板</option>
			   	    			</select>
			   	    			<input name="content" id="content" class="mini-hidden" />
			        		</td>
							<td style="white-space: nowrap;">
								<a class="mini-button mini-button-iconLeft" plain="true" iconCls="icon-save" onclick="saveAipTemplet();" >保存</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div showCollapseButton="false">
				<jsp:include page="/WEB-INF/JSP/form/edit_form.jsp"></jsp:include>
				<div id="overlay"></div>
				<div id="setFrm" class="ModalDialog">
					<div id="setFrm_body" class="body bodycolor">
						<input type="hidden" name="FIELD_STR" id="FIELD_STR">
						<iframe frameborder=0 style="width:260px;height:115px"  id="frm_field" src="<%=path %>/form/getlist.action?1=1&math=Math.random()&tempid=<%=tempid %>"></iframe>
					</div>
				</div>
			</div>
		</div>
		<input name="actiontype" id="actiontype" class="mini-hidden" />
		<input name="departid" id="departid" class="mini-hidden" value="${departid}"/>
		<script type="text/javascript">
			mini.parse();
			var form = new mini.Form("form1");			
			function saveAipTemplet(){
				var url;
				var actiontype = mini.get("actiontype").getValue();
				var departid = mini.get("departid").getValue();
				var content = saveFile();
				var o = form.getData();            
	            form.validate();
	            if (form.isValid() == false) return;
	            var json = mini.encode([o]);
	            if(actiontype == 'edit'){
	            	url = '<%=path%>/form/updatereportmanage.action?1=1';
		        }else{
		        	url = '<%=path%>/form/addreportmanage.action?1=1';
			    }
				$.ajax({
			         url:url, 
			         data: {content:content,resid:'1',data:json,departid:departid},
			         type: "post",
			         cache:false,
			         async:false,
			         success: function (text){
			         	alert("操作成功!");
			         	back();
			         }
				});
			}
			 ////////////////////
	        //标准方法接口定义
	        function SetData(data) {
	        	mini.get("actiontype").setValue(data.action);        
	        	if (data.action == "edit") {
	                //跨页面传递的数据对象，克隆后才可以安全使用
	                data = mini.clone(data);
	                $.ajax({
	                    url: "<%=path%>/form/getformmanageinfo.action?1=1&tempid=" + data.tempid+"&departid="+data.departid,
	                    cache: false,
	                    success: function (text) {
	                        var o = mini.decode(text);
	                        form.setData(o);
	                        HWPostil1_modelEdit("'"+o.content+"'");
	                    }
	                });
	            }
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
					
		</script>
	</body>
</html>