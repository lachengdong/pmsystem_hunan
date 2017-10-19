<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String operate = request.getParameter("operate");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>内容管理</title>
   
    <meta http-equiv="X-UA-Compatible" content="IE=IE9" /> 
   
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <script src="<%=path%>/demo/fileupload/swfupload/swfupload.js" type="text/javascript"></script>
    <script charset="utf-8" src="<%=path%>/keditor/kindeditor.js"></script>
    <script charset="utf-8" src="<%=path%>/keditor/lang/zh_CN.js"></script>
    <script charset="utf-8" src="<%=path%>/keditor/plugins/code/prettify.js"></script>
      <style type="text/css">
	    html, body
	    {
	        font-size:12px;
	        padding:0;
	        margin:0;
	        border:0;
	        height:100%;
	        overflow:hidden
	    }
		.grzma:link,.grzma:visited,.grzma:hover,.grzma:active{
			text-decoration:none;
			color:#000;
		}
    </style>
</head>
<body> 
<form id="form1" method="post" style="background-color: #7a96eb;height:100%; margin-left: 0px;">
        <input name="articleid" id="articleid" class="mini-hidden" />
       	<input id="operator" name="operator" value="new" class="mini-hidden" />
       	<input id="menuid" name="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>
       	<input id="content" name="content" class="mini-hidden" />
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;width:100%;">
            <div style="width:100%;height:30px;line-height:30px;">&nbsp
            	<span id="save"><a class="mini-button" id="buttons" onclick="SaveData" plain="true" style="width:60px;">保存</a></span>
       			<a class="mini-button" onclick="onCancel" plain="true" style="width:60px;">返回</a>                       
            </div>           
        </div>
        <div style="padding:0px">
            <table border="0" style="height:90%;width:100%;background:#ecf0f6;border-top:#99bce8 1px solid;" cellpadding="2" cellspacing="0" align="center">
                <tr>
                    
                    <td style="width:60px;padding-left:20px;">所属栏目：</td>
                    <td colspan="3">    
                        <input name="moduleid" id="moduleid" class="mini-combobox" valueField="moduleid" textField="title" emptyText="请选择栏目" width="200" />
                    </td>
                </tr>
                <tr>
                    <td style="width:60px;padding-left:20px;">文章标题：</td>
                    <td colspan="7">    
                        <input name="title" class="mini-textbox" vtype="maxLength:1500" required="true" style="width:700px;" />
                    </td>
                </tr>
                <tr>
                    <td style="padding-left:20px;">副&nbsp;标&nbsp;&nbsp;题：</td>
                    <td colspan="7">    
                        <input name="subtitle" class="mini-textbox" vtype="maxLength:1500"  style="width:700px;"/>
                    </td>
                </tr>
                
                 <tr>
                 	<td style="padding-left:20px;">文章摘要：</td>
                    <td colspan="3">    
                        <input name="abstracts" class="mini-textarea" vtype="maxLength:500" style="width:350px;" />
                    </td>
                    <td>关&nbsp;键&nbsp;字：</td>
                    <td colspan="3">    
                        <input name="keywords" class="mini-textarea" vtype="maxLength:254" style="width:320px;"/>
                    </td>
                </tr>
                <!-- <tr>
                    <td>图片摘要：</td>
                    <td colspan="3">    
   						<input id="imgpath" class="mini-fileupload" name="imgpath" limitType="*.jpg;*.bmp;*.gif" limitSize="1MB"
   						flashUrl="../../../demo/fileupload/swfupload/swfupload.swf" uploadUrl="../../../FileUploadServlet" uploadOnSelect="false"
   						onuploadsuccess="" onuploaderror="onUploadError" onfileselect="onFileSelect" width="200"/>          
                    </td>
                    <td>pdf文件摘要：</td>
                   <td colspan="3">
                   		<input id="pdfpath" class="mini-fileupload" name="pdfpath" limitType="*.pdf" limitSize="5MB"
   						flashUrl="../../../demo/fileupload/swfupload/swfupload.swf" uploadUrl="../../../FileUploadServlet" uploadOnSelect="false"
   						onuploadsuccess="" onuploaderror="onUploadError" onfileselect="onFileSelect" width="200"/>        
                    </td>
                </tr>   -->  
                <tr>
                	<td style="padding-left:20px;">发布时间：</td>
                    <td>    
                        <input id="releasetime" name="releasetime" class="mini-datepicker"  format="yyyy-MM-dd" required="true" emptyText="请选择发布时间"/>
                    </td>
                    <td style="width:60px;">过期时间：</td>
                    <td>    
                        <input name="expirationtime" id="expirationtime" class="mini-datepicker"  format="yyyy-MM-dd" ondrawDate=onDrawDate  required="true" emptyText="请选择过期时间"/>
                    </td>
                    <td style="width:60px;">信息来源：</td>
                    <td>    
                        <input name="sources" class="mini-textbox" vtype="maxLength:500" style="width: 320px;"/>
                    </td>
                </tr>
                <tr>
	                <td style="width:60px;padding-left:20px;">文章内容：</td>
	                <td colspan="7">  
	                    <textarea name="contents" style="width:780px;height:360px;visibility:hidden;"></textarea>                     
	                </td>
            	</tr>                               
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
        //保存或更新
        function SaveData() {
       		var operator = mini.get("operator").getValue();
       		var content = editor.html();
       		mini.get("content").setValue(content);		        
        	if(operator == "new"){   
	           	var data = form.getData();
	            form.validate();
	            if (form.isValid() == false) return;
	            var json = mini.encode([data]);
	            form.loading("保存中，请稍后......");
	            $.ajax({
	                url: "<%=path%>/ywgkArticle/saveArticle.json?1=1",
	                data: { data: json },
	                type: "post",
	                success: function (text) {
                    	CloseWindow("save");
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    CloseWindow();
	                }

	            });
            }
             if(operator == "edit"){//更新
            	var data = form.getData();
            	form.validate();
            	if (form.isValid() == false) return;
	            var json = mini.encode([data]);
	            form.loading("更新中，请稍后......");
	            $.ajax({
	                url: "<%=path%>/ywgkArticle/saveArticle.json?1=1",
	                data: { data: json },
	                type: "post",
	                cache: false,
	                success: function (text) {
                    CloseWindow("save");
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    //alert("操作失败");
	                    CloseWindow();
	                }
	            });
            }
        }
        //标准方法接口定义
        function SetData(data) {
            data = mini.clone(data);
            if (data.action == "edit" || data.action == "view" ) {
                //跨页面传递的数据对象，克隆后才可以安全使用
                $.ajax({//更新
                    url: "<%=path%>/ywgkArticle/getDetailDate.json?id=" + data.id,
                    cache: false,
                    success: function (text) {
                        var o = mini.decode(text);
                        form.setData(o);
                        mini.get("operator").setValue(data.action);
                		var content=mini.get("content").getValue();
	        			editor.html(content)
                        form.setChanged(false);
                       	mini.get("moduleid").load("<%=path%>/ywgkArticle/getModules.json?1=1");
       					mini.get("moduleid").setValue(o.moduleid);
            			var releasetime = mini.get("releasetime");
						releasetime.disable();
						if (data.action == "view" ) {
							document.getElementById("buttons").style.display="none";
						}
                    }
                });
            }else if(data.action == "new"){
            	var moduleid = data.moduleid;
       			mini.get("operator").setValue(data.action);
       			mini.get("moduleid").load("<%=path%>/ywgkArticle/getModules.json?1=1");
       			mini.get("moduleid").setValue(moduleid);
       			var releasetime = mini.get("releasetime");
				releasetime.setValue(new Date());
				releasetime.disable();
            }
        }
 
        function GetData() {
            var o = form.getData();
            return o;
        }
		function onOk(e) {
    		SaveData();
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
	    function onDrawDate(e) {
            var date = e.date;
            var d = new Date();
            if (date.getTime() < d.getTime()) {
                e.allowSelect = false;
            }
        }
    </script>
    <script type="text/javascript">
			/*KindEditor.ready(function(K) {
	               editor = K.create('textarea[name="contents"]',{
	               themeType: 'simple',
	           	   resizeType : 1,
	           	   cssPath : '/website/editor/plugins/code/prettify.css',
	               uploadJson : '/website/editor/jsp/upload_json.jsp',
	               fileManagerJson : '/website/editor/jsp/file_manager_json.jsp',
	               allowFileManager : true,
	               
       	   	   }); 	   
	     });  */
	        
	     $(function() {
	     	       window.editor = KindEditor.create('textarea[name="contents"]',{
	           	   resizeType : 0,
	           	   cssPath : '<%=path%>/keditor/plugins/code/prettify.css',
	               uploadJson : '<%=path%>/keditor/jsp/upload_json.jsp',
	               fileManagerJson : '<%=path%>/keditor/jsp/file_manager_json.jsp',
	               allowFileManager : true,
	               afterBlur: function(){this.sync(); }
       	   	   });
	     });	
	
	</script>
</body>
</html>
