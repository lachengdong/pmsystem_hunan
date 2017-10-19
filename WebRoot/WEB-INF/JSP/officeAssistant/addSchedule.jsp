<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>日程安排新增日程事件</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
	    html, body{
	        font-size:12px;padding:0;margin:0;border:0;height:100%;overflow:hidden; 
	    }
    </style>
</head>
<body>     
    <form id="form1" method="post">
        <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >   
           <input id="noticeid" name="noticeid" class="mini-hidden" value=""/>       
           <input id="operator" name="operator" class="mini-hidden" value="new"/>     
           <a class="mini-button" id="save" onclick="SaveData()" plain="true" iconCls="icon-save" style="display:blak;width:60px">存盘</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" iconCls="icon-close" style="width:60px;">关闭</a>       
        </div>
        <div style="padding-left:15px;padding-bottom:5px;padding-right:15px;">
        	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
            <table  style="width:100%;" border="0">
                <tr>
                    <td style="width:60px;">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
                    <td colspan="3">    
                        <input id="title" name="title" class="mini-textbox"  style="width:91%;" vtype="maxLength:16;"  required="true"  emptyText="请输入标题"/>
                    </td>
                </tr>
                 <tr>
                    <td style="width:60px;">开始时间：</td>
                    <td style="width:150px;">
                    	<input id="starttime" name="starttime"  property="editor" format="yyyy-MM-dd HH:mm" showTime="true" class="mini-datepicker" emptyText="开始时间" style="width:80%;*+.width:100%;" onvaluechanged="timeVerification('start');" allowInput="true"  required="true"/>
                    </td>
                    <td style="width:60px;">结束时间：</td>
                    <td style="width:150px;">
                    	<input id="endtime" name="endtime" property="editor" format="yyyy-MM-dd HH:mm" showTime="true" class="mini-datepicker" emptyText="结束时间" allowInput="true" style="width:78%;*+.width:100%;" onvaluechanged="timeVerification('end');" required="true"/>
					</td>
                </tr>
              	<tr>
             		<td style="width:60px;">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容：</td>
                    <td colspan="3">
                    	<input id="content" name="content" class="mini-textarea"  vtype="maxLength:1330;"  style="width:91%;height:160px;" required="true"/>
					</td>
              	</tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
       mini.parse();
       var form = new mini.Form("form1");
       var start = "${starttime}";
    	    
    	    
       //标准方法接口定义
       function SetData(data) {
       	   var starttime = "${starttime}";
       	   if(!starttime) starttime = new Date();
       	   mini.get("starttime").setValue(starttime);
           data = mini.clone(data);
           mini.get("operator").getValue(data.action);
           if(data.action !="new"){//修改、查看时回显数据
           		if(data.action =="view")$("#save").hide();//查看时隐藏确定按钮
           		mini.get("noticeid").getValue(data.noticeid);
           		$.ajax({
	                 url: "ajaxGetSchedule.action?1=1&noticeid="+data.noticeid,
	                 type: "post",
	                 success: function (text){
	                     var o = mini.decode(text);
	                     form.setData(o);
	                 }
	             });
           }
       }
       function onOk(e) {
            SaveData();
        }	
       function SaveData() {
    	    var title = mini.get("title").getValue();
    	    var starttime = mini.get("starttime").getText();
       		var endtime = mini.get("endtime").getText();
        	var content = mini.get("content").getValue();
        	var noticeid = mini.get("noticeid").getValue();
        	form.validate();
            if (form.isValid('all') == false) return;
            if (timeVerification() == false) return;
			$.ajax({
				url : "saveSchedule.action?1=1",
				type : "POST",
				data : {noticeid :noticeid,
					title : title,starttime : starttime,
					endtime : endtime,content : content
				},
				success : function(text) {									
					if (text == "success") {
						alert("保存成功");
						onCancel();
					} else {
						alert("保存失败");
						onCancel();
					}
				},
				error: function () {
                    	alert("操作失败!");
                   }
			});
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
        
        function onCancel(e) {
            CloseWindow("cancel");
        }
       
    </script>
</body>
</html>
