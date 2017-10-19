<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <title>编辑面板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/demo/fileupload/swfupload/swfupload.js" type="text/javascript"></script>
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
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
	                    	<a class="mini-button" id="onOk" onclick="onOk" plain="true" style="width:60px">确定</a>
		           			<a class="mini-button" onclick="onCancel" plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                </tr>
	            </table>           
	        </div>
        	<div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
				<tr >
					<td style="width:90px;">所属栏目</td>
					<td style="width:150px;">
						<input id="moduleid" class="mini-combobox" textField="text" valueField="id" emptyText="请选择..." url="" required="true" allowInput="true" showNullItem="true" nullItemText="请选择..."/>    
			        </td>
					<td style="width:90px;">视频名称</td>
			        <td style="width:150px;">    
			        	<input id="name" name="name" class="mini-textbox" required="true" />
			        </td>
				</tr>
				<tr >
					<td style="width:90px;">过期时间 </td>
			        <td style="width:150px;">    
			        	<input id="expirationtime" name="expirationtime" required="true"  class="mini-datepicker" />
			        </td>
					<td style="width:90px;">选择文件</td>
			        <td style="width:150px;">
			        	<input id="url" class="mini-fileupload" name="url" limitType="*.jpg;*.bmp;*.gif" flashUrl="../../demo/fileupload/swfupload/swfupload.swf" uploadUrl="../../FileUploadServlet" uploadOnSelect="false" onuploadsuccess="onUploadSuccess" onuploaderror="onUploadError" onfileselect="onFileSelect" />
			        </td>			         
				</tr>
            </table>
        </div>
    </form>

  </body>
</html>
<script type="text/javascript">

	mini.parse();
    var form = new mini.Form("form1");
	function onFileSelect(e) {
		//alert("选择文件");
	}
	function onUploadSuccess(e) {
		alert("上传成功：" + e.serverData);
	    var filename=e.serverData;
	    document.getElementById("name").value=e.serverData;
	    SaveData(filename);
	    this.setText("");
	}
	function onUploadError(e) {
	}
	function startUpload() {
	    var fileupload = mini.get("url");
	    fileupload.startUpload();
	}
	//标准方法接口定义
	function SetData(data) {
		data = mini.clone(data);
	    mini.get("operatetype").setValue(data.operatetype);
	}
	function onOk(){
		startUpload();
	}
	function SaveData() {
		
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