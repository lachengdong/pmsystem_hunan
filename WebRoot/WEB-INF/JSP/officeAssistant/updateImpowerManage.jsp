<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改授权</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<style type="text/css">
			html,body {
				font-size: 12px;
				padding: 0;
				margin: 0;
				border: 0;
				height: 100%;
			} 
		</style>
	</head>
	<body>
		<form id="form1" method="post">
			<input id="aiid" name="aiid" class="mini-hidden" />
			<input id="bname" type="hidden" value="<s:property value='#request.bsuname'/>" />
			<input id="hiddenbsuid" type="hidden" value="<s:property value='#request.bsuid'/>"/>
			<input id="authid" type="hidden" value="<s:property value='#request.authid'/>" />
			<input name="aiid" class="mini-hidden" />
			<input name="str" id="str" type="hidden" value="<s:property value='#request.str'/>"/>
			<input id="operatetype" name="operatetype" type="hidden"  />
	        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
							<input name="updatetime" id="updatetime"  type="hidden"" value="<s:property value='#request.updatetime'/>" />
		            		<a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
		           			<a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>           
	        </div>
			<div style="padding-left: 11px; padding-bottom: 5px;">
			<div style="width:100%;float:left;">
					<input type="hidden" id="noticeid" name="noticeid" field="noticeid" width="0px" value="${tbuserNotice.noticeid }"/>
					<div style="float:left;margin-top:5px;">授权开始时间&nbsp;<input id="aistarttime" name="aistarttime" style="width:135px;" class="mini-datepicker" required="true"  value="${tbuserNotice.aistarttime}"/></div>
					<div style="float:left;margin-top:5px;">授权结束时间&nbsp;<input id="aiendtime" name="aiendtime" style="width:135px;" class="mini-datepicker" required="true" value="${tbuserNotice.aiendtime}"/></div>
					<div style="float:left;margin-top:5px;">被&nbsp;&nbsp;授&nbsp;&nbsp;权&nbsp;&nbsp;人&nbsp;<input id="username" name="username" style="width: 130px;"  onbuttonclick="onButtonEdit" value=${tbuserNotice.username} /></div>
			<!-- 
					<s:property value="#request.username" /><input id="username" name="bsuid" allowInput="false" value="<s:property value='#request.bsuid'/>" style="width: 135px;" class="mini-buttonedit" onbuttonclick="onButtonEdit" /> </div>
			 -->
					<div style="float:left;margin-top:5px;">授&nbsp;&nbsp;权&nbsp;&nbsp;状&nbsp;&nbsp;态&nbsp;<select id="shouquan" name="shouquan" style="width:135px;">
 								<option value ="0">已授权</option>
 								<option value ="1">未授权</option>
 								</select>
			<!-- 
						<input id="aistate"  name="aistate" class="mini-combobox" style="width:135px;" textField="text" valueField="id" showNullItem="false"
				             url="noticeState.action?1=1"  value="<s:property value='#request.aistate'/>" /> 
	         --> 			
				     </div>
					<div style="float:left;margin-top:5px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注&nbsp;<textarea id="remark" style="width:135px;"  class="mini-textarea"  rows="80"  name="remark" value=${tbuserNotice.remark}></textarea></div>
			</div>
	<!--  
			<div style="float:left;">
				所授权限：<div id="tree2" class="mini-tree"  style="width:250px;margin-left:30px;" onbeforenodecheck="onBeforeNodeCheck"
					 showTreeIcon="true"   textField="spdiscribe" idField="spid" parentField="spparent" resultAsTree="false" 
					 showCheckBox="true" checkRecursive="false"  showFolderCheckBox="true"  autoCheckParent="true" expandOnLoad="false"  allowSelect="false" enableHotTrack="false"  > </div>
			</div>
	-->
			</div>
		</form>
		<script type="text/javascript">
        mini.parse();
       // var form = new mini.Form("form1");
        //var tree = mini.get("tree2");
      
 function SaveData() {
  
        	var remarks = mini.get("remark").getValue();
            if(remarks.length>255){
                alert("所输入的内容已超出规定字数限制，请修改后再保存！");
                return false;
            }     
           var id=$("#noticeid").val();
            var aistarttime = mini.get("aistarttime").getValue();
            var aiendtime = mini.get("aiendtime").getValue();
            var remark = mini.get("remark").getValue();
            var uname = $("#username").val();
            var shouquan=$("#shouquan").val();
            var hiddenbsuid = document.getElementById("hiddenbsuid").value;
          alert(id);
            if(aistarttime>aiendtime) {
               alert("结束时间不能小于开始时间！");
               return false;
            }else{            	
            $.ajax({
					url : "updateAuthorizationAction.action?1=1",
					type : "POST",
					data : {
							id : id,
							aistarttime : mini.encode(aistarttime),
							aiendtime : mini.encode(aiendtime) ,
							remark : remark,
							uname : uname,
							shouquan : shouquan,
					},
					success : function(text) {
						var temp = eval(text);
						if (temp == "success") {
							onCancel();
						} else {
							alert("保存失败");
						}
					}
				});
				}
        }

        function GetData() {
            var o = form.getData();
            return o;
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
        function onOk(e) {
            SaveData();
        }
       
        
    </script>
	</body>
</html>