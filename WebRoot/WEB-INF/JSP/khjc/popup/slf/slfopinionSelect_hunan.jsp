<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
     <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
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
          <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >
          <input type="hidden" value="${code}" id="code"/> 
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>       
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
					<tr>
					    <td style="width:60px;">主要类型</td>
						<td style="width:500px;">    
							<input id="sanclassstatus" name="sanclassstatus"  class="mini-treeselect" multiSelect="true" 
							        textField="name" valueField="codeid" parentField="pcodeid" checkRecursive="true" 
							        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onCloseClick"
        							popupWidth="400" url="/pmsystem/getSanLeiType.json?1=1" value="${code}"/>
						</td>
					</tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
        
      function onOk(e) {
           var row = new Array([2]);
           var sanclassstatus = mini.get("sanclassstatus").getValue();
           //var sanclassstatustext = mini.get("sanclassstatus").getText();
           
           var sanclassstatustext = "";
           if(sanclassstatus.indexOf("h")>=0){
        	   sanclassstatustext += "、"+"黑社会性质";
           }
           if(sanclassstatus.indexOf("J")>=0){
        	   sanclassstatustext += "、"+"金融犯罪";
           }
           if(sanclassstatus.indexOf("z")>=0){
        	   sanclassstatustext += "、"+"职务罪犯";
           }
           if(sanclassstatustext){
        	   sanclassstatustext = sanclassstatustext.substring(1);
           }
           
           row[0] = sanclassstatus;
           row[1] = sanclassstatustext;
      	 window.returnValue = row;
 		 CloseWindow("cancel");
            
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
