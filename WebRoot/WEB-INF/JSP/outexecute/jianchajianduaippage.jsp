<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>检查监督AIP</title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
</head>
<body>
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
			<table>
				<tr>
					<td style="width:100%;">
				    	<jsp:include page="/WEB-INF/JSP/form/formButtonBefore.jsp"></jsp:include>
				    	<a class="mini-button"  iconCls="icon-save" plain="true"  id="11990" onclick="savedata();">存盘</a>	
				    	<a class="mini-button"  iconCls="icon-close" plain="true"  onclick="onCancel();">关闭</a>
				    	<jsp:include page="/WEB-INF/JSP/form/formButtonAfter.jsp"></jsp:include>
				    </td>
				    <td style="white-space:nowrap;">
				    	<a class="mini-button"   style="display:none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
				    </td>
			  	</tr>
		  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
	  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
    <script type="text/javascript">
           mini.parse();
	       function CloseWindow(action) {            
	           if (action == "close" && form.isChanged()) {
	               if (confirm("数据被修改了，是否先保存？")) {
	                   return false;
	               }
	           }
	           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	           else window.close();            
	       }
	       function onCancel() {
	           CloseWindow("cancel");
	       }

          function savedata() {
	       		var content = saveFile();
	       		var tempid = mini.get("tempid").getValue();
	       		var docid = mini.get("docid").getValue();
	       		var crimid = mini.get("crimid").getValue();
	       		var url="saveJianChaData.action?1=1";
	       		var temp;
	        	if(docid){
	        		temp = "edit";
	        	}else{
	        		temp="new";
	        	}
	            $.ajax({
	                url:url,
	                data: {content:content,tempid:tempid,crimid:crimid,docid:docid,temp:temp},
	                type: "post",
	                dataType:"text",
	                success: function (text) {
	                	alert("操作成功!");
	                	onCancel();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                	//console.log(location.href,url);
	                }
	            });
            
        }
		//标准方法接口定义
      function SetData(data) {
			
        }
    </script>
</body>
</html>
