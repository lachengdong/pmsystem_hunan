<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String cancelreason=(String)request.getAttribute("cancelreason");
%>

<html>
<head>
  	<title>考核分数废弃页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
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
        	<input id="cancelreason" name="cancelreason" class="mini-hidden" />
        	<input id="crimid" name="crimid" class="mini-hidden" value="${crimid}"/>
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
	                    	<a class="mini-button" id="onOk" onclick="onOk" plain="true" style="width:60px">确定</a>
		           			<a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>           
	        </div>
        	<div style="padding-left:15px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
				<tr>
					<td style="width:80px;">废 弃 原 因：</td>
			        <td >    
			        	<input id="cancelreason" name="cancelreason" value="${cancelreason}"   class="mini-textarea" style="width:350px;height: 150px;"  required="true" />
			        </td>
				</tr>
            </table>
        </div>
    </form>
<script type="text/javascript">
	mini.parse();
	var form = new mini.Form("form1");
	function SetData(data) {
		data = mini.clone(data);
	    if(data.operatetype == 'view'){
	    	document.getElementById("onOk").style.display = 'none';
	    }
	}
	function onOk(){
		SaveData();
	}
	function SaveData() {
		var cancelreason=mini.get("cancelreason").getValue();
				form.validate();
				if (form.isValid() == false){
					return;
				}
            if (confirm("确定废弃？")) {
		var crimid=mini.get("crimid").getValue();
		$.ajax({
			url:"saveScoreCancellation.json?1=1",
			data:{crimid:crimid,cancelreason:cancelreason},
			type: "post",
			success: function (text){
				onCancel();
			},
			error:function (jqXHR,textStatus,errorThrown){
			alert("操作失败!");
			}
		 });
       }
    }
	function onCancel(e) {
            CloseWindow("cancel");
    }
    function CloseWindow(action) {            
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
</script>
  </body>
</html>
