<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加表单管理</title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
		<link href="<%=path %>/css/CIC.css" rel="stylesheet" type="text/css"/>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; font-size: 12px;
		    }   
		</style>
	</head>

	<body>
		<input name="actiontype" id="actiontype" class="mini-hidden" />
		<form id="form1" method="post">
		<div class="mini-toolbar mini-grid-headerCell" style="padding: 1px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="width: 100%;">
						<a class="mini-button mini-button-iconLeft" plain="true" iconCls="icon-save" onclick="saveAipTemplet();" >保存</a>
	        		</td>
					<td style="white-space: nowrap;">
					</td>
				</tr>
			</table>
		</div>
		<fieldset style="width:100%;border:solid 0px #aaa;margin-top:8px;position:relative;">
        <div style="padding:5px;">
            <input class="mini-hidden" name="id"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:60px;">表&nbsp;单&nbsp;ID：</td>
                    <td style="width:60px;"><input id="tempid" name="tempid" class="mini-textbox" required="true" onvalidation="onEnglishAndNumberValidation" /></td>
                    <td style="width:60px;">表单名称：</td>
                    <td style="width:60px;"><input id="tempname" name="tempname" class="mini-textbox" required="true"/></td>
                </tr>
                <tr>
                    <td>系统模块：</td>
                    <td><input id="functionname" name="functionname" class="mini-textbox" required="true"/></td>
                    <td>模板类型：</td>
                    <td><input id="type" name="type" class="mini-combobox" data="Types" value="2" required="true"/></td>
                </tr>
                <tr >
                	<td>通用类型：</td>
                	<td colspan="3">
                		<input id="rbl" class="mini-radiobuttonlist" repeatItems="0" repeatLayout="table" repeatDirection="vertical" onvalueChanged="onEnglishAndNumberValidation"
						    textField="text" valueField="id" value="1"  data="Radiodata" />
                	</td>
                </tr>
            </table>
        </div>
    </fieldset>
		</form>
		<script type="text/javascript">
			var Types = [{ id: 1, text: '系统模板' }, { id: 2, text: '表单'}];
			var Radiodata = [{ id: 1, text: '单位特有' }, { id: 2, text: '省局通用'}, { id: 3, text: '全国通用'}];
			mini.parse();
			var form = new mini.Form("form1");
			function search() {
			     var key = mini.get("key").getValue();
			     grid.load({ key: key });
			}
			
			function onKeyEnter(e) {
			   search();
			}
			
			function saveAipTemplet(){
				var o = form.getData();           
	            form.validate();
	            if (form.isValid() == false) return;
	            var json = mini.encode([o]);
	            
	          	//验证 表单ID是否已存在
	          	if(tempid){
	          		saveAipContent(json);
		        }
			}
			//保存大字段
			function saveAipContent(data){
				var rbl = mini.get("rbl").getValue();
				var  url = '<%=path%>/form/addreportmanage.action?1=1';
				$.ajax({
			         url:url, 
			         data: {resid:'1',rbl:rbl,data:data},
			         type: "post",
			         cache:false,
			         async:false,
			         success: function (text){
			         	alert("操作成功!");
			         	CloseWindow('close');
			         }
				});
			}
			////////////////////
	        //标准方法接口定义
	        function SetData(data) {
	        	mini.get("actiontype").setValue(data.action);        
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
		   
		   //验证表单ID
	       function onEnglishAndNumberValidation(e) {
	       		var rbl = mini.get("rbl").getValue();
	            var tempid = mini.get("tempid").getValue();
	            var url = '<%=path%>/form/isexisttempid.json?1=1';
	            $.ajax({
			         url:url, 
			         data: {resid:'1',tempid:tempid,rbl:rbl},
			         type: "post",
			         cache:false,
			         async:false,
			         success: function (text){
				        var obj = eval(text);
				        var message = '必须输入英文+数字';
				        if (e.isValid) {
			                if (isEnglishAndNumber(e.value) == false) {
			                    e.errorText = message;
			                    e.isValid = false;
			                }
			            }
				        if(obj == 0){//已存在
				        	message = '此模板id已存在!';
				        	e.errorText = message;
				        	e.isValid = false;
							return;
					    }
			         }
				});
	       }

	       /* 是否英文+数字 */
	       function isEnglishAndNumber(v) {
	            var re = new RegExp("^[0-9a-zA-Z\_]+$");
	            if (re.test(v)) return true;
	            return false;
	       }		
		</script>
	</body>
</html>