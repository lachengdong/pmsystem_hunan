<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>处室经办审查建议书</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
    </style>
</head>
<body >
    <div class="mini-splitter" vertical="true" style="width:100%;height:100%;">
	    <div size="1px" showCollapseButton="false">
	        <input id="crimid" name="crimid" class="mini-hidden" value="${crimid}"/>
	         <div class="mini-toolbar" style="border:0;padding:0px;">
			      <table style="width:100%;">
			          <tr>
	                	 <td style="width:700px;">
	                		   模版：<input id="tempid" name="tempid" property="editor" class="mini-combobox" style="width:180px;"
	                		       onvaluechanged="tempidchanged" required="true" data="Genders" /> 
	                	 </td>
	                     <td style="white-space:nowrap;">
	                         <a class="mini-button" iconCls="icon-save"  plain="true" onclick="savedata()">保存文书</a>
	                         <span class="separator"></span>
	                         <a class="mini-button" iconCls="icon-new"  plain="true" onclick="preview()">预览</a>
	                     </td>
			         </tr>
			     </table>           
		     </div>
	    </div>
	    <div showCollapseButton="false" id="editForm1" >
	       	<input id="annexcontent" name="annexcontent" class="mini-textarea"  style="width:100%; height:100%;"/>
	       	<input id="tmvorsionid" name="tmvorsionid" class="mini-hidden" />
	 	</div>
	</div>
    <script type="text/javascript">
    	var Genders = [{ id: 9706, text: '建议书（监外执行）'}];
        mini.parse();
       	var form = new mini.Form("editForm1");
        
        //保存或更新
        function savedata() {
           	var data = form.getData();
           	if(!data.annexcontent){
           		return;
           	}
            var json = mini.encode([data]);
            form.loading("保存中，请稍后......");
            $.ajax({
                url: url,
                data: { data: json,menuid:menuid},
                type: "post",
                success: function (text) {
                  	form.unmask();
                	if(text == "success"){
                		alert("保存成功!");
                	}else{
                		alert("保存失败！");
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
        }
		//预览
		function preview(){
			
		}
		//建议书下拉选择改变时生成文本
		function tempidchanged(e) {
        	 var tempid = mini.get("tempid").getValue();
        	 var crimid = mini.get("crimid").getValue();
               $.ajax({
                 url: "getjwzxscJianyishu.json?1=1&tempid="+tempid+"&crimid="+crimid,
                 type: "post",
                 success: function (text) {
                     var o = mini.decode(text);
                     form.setData(o);
                 }
             });
        }  
    </script>
</body>
</html>