<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String provincecode = (String)request.getAttribute("provincecode");
%>

<html>
<head>
  	<title>批次设置新增</title>
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
        	<input id="operatetype" name="operatetype" class="mini-hidden" />
        	<input id="batchid" name="batchid" class="mini-hidden" value="${batchid}"/>
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
        	<div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
				<tr >
					<td style="width:90px;">年　　度</td>
					<td style="width:150px;">    
			        	<input id="curyear" name="curyear" value="${curyear}" vtype="float;maxLength:10" class="mini-textbox" required="true" />
			        </td>
					<td style="width:90px;">批　　　次</td>
			        <td style="width:150px;">    
			        	<input id="batch" name="batch" value="${batch}" vtype="float;maxLength:2"    class="mini-textbox" required="true" />
			        </td>
				</tr>
				<tr >
					<td style="width:90px;">考核止日</td>
			        <td style="width:150px;">    
			        	<input id="examineend" name="examineend" value="${examineend}" required="true"  class="mini-datepicker" />
			        </td>
					<td>
						<%
							if("6100".equals(provincecode)){
						%>
							刑期及减刑止日
						<%
							}
						%>
						<%
							if(! "6100".equals(provincecode)){
						%>
							余刑起算日
						<%
							}
						%>
					</td>
			        <td style="width:150px;">    
			        	<input id="remainderpunishment" name="remainderpunishment" value="${remainderpunishment}" required="true" class="mini-datepicker" />
			        </td>			         
				</tr>
            </table>
        </div>
    </form>
<script type="text/javascript">
	//标准方法接口定义
	function SetData(data) {
		data = mini.clone(data);
	    mini.get("operatetype").setValue(data.operatetype);
	    if(data.operatetype == 'view'){
	    	document.getElementById("onOk").style.display = 'none';
	    }
	}
	function onOk(){
		SaveData();
	}
	function SaveData() {
		var operatetype = mini.get("operatetype").getValue();
		var batchid = mini.get("batchid").getValue();
		var form = new mini.Form("form1");
		var check = 1;
		var o = form.getData();
        form.validate();
        if (form.isValid() == false) return;
        var json = mini.encode([o]);
        $.ajax({
        	url: "checkUnfinishedCase.json?1=1",
            type: "post",
            dataType:"text",
            async:false,
            success: function (text) {
            	check = text;
            },
            error: function (jqXHR, textStatus, errorThrown) {
            	alert("操作失败!");
            }
        });
        if(check>0) {
	        mini.confirm("案件未全部处理,更新批次会导致数据异常,是否继续?", "确定？",
	            function (action) {
	                if (action == "ok") {
					    $.ajax({
				        	url: "saveCommuteParoleBatch.json?1=1",
				            data: {data:json, operatetype:operatetype, batchid:batchid},
				            type: "post",
				            dataType:"text",
				            async:false,
				            success: function (text) {
				            	alert("操作成功!");
				            	CloseWindow("save");
				            },
				            error: function (jqXHR, textStatus, errorThrown) {
				            	alert("操作失败!");
				            }
				    	});
	                } else {
	                    CloseWindow("cancel");
	                }
	            }
	        );
        } else {
 			$.ajax({
				url: "saveCommuteParoleBatch.json?1=1",
				data: {data:json, operatetype:operatetype, batchid:batchid},
				type: "post",
				dataType:"text",
				async:false,
				success: function (text) {
					alert("操作成功!");
					CloseWindow("save");
				},
				error: function (jqXHR, textStatus, errorThrown) {
					alert("操作失败!");
				}
			});       
        }
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
	
</script>
  </body>
</html>
