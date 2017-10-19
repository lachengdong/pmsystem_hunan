<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>执行期满考察</title>
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
						<input name="operator" id="operator" class="mini-hidden" value=""/>
						<jsp:include page="/WEB-INF/JSP/form/formButtonBefore.jsp"></jsp:include>
						${topsearch}
						${topstr}
				    	<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel();">关闭</a>
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
	<script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyDocOpened">
	// 控件"HWPostil1"的NotifyDocOpened事件,该事件在AIP文档打开完成的时候触发
		//document.all.HWPostil1.showToolBar = 1;
	</script>
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
        var grid = mini.get("datagrid1");
        //保存或更新
      function savedate(){
       		var content = saveFile();
       		var operator = mini.get("operator").getValue();
       		var tempid = mini.get("tempid").getValue();
       		var docid = mini.get("docid").getValue();
       		var crimid = mini.get("crimid").getValue();
       		var url="saveCompletion.action?1=1";
        	if(operator == "new"){//新增
	            $.ajax({
	                url:encodeURI(encodeURI(url)),
	                data: {content:content,tempid:tempid,operator:operator,crimid:crimid},
	                type: "post",
	                success: function (text) {
	                	alert("操作成功!");
	                  	onCancel();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
            }
            if(operator == "edit"){//更新
	            $.ajax({
	                url: "saveCompletion.action?1=1",
	                data: {content:content,operator:operator,docid:docid},
	                type: "post",
	                success: function (text) {
	                	alert("操作成功!");
	                  	onCancel();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert("gameover");
	                	alert("操作失败!");
	                }
	            });
           }
        }
        
        //跳转到选择罪犯页面
      function sendMenuid(){
        	var url = "completionChoose.action?1=1";
			self.location.href=url;
        }
        
        //标准方法接口定义
      function SetData(data) {
            data = mini.clone(data);
            mini.get("operator").setValue(data.action); 
        }
        
        //关闭窗口
      function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
		function onCancel(e) {
            CloseWindow("cancel");
        }
    </script>
</body>
</html>
