<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	List signatureSchemes = (List)request.getAttribute("signatureSchemes");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>案件审查意见</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   		}    
    </style> 
</head>
<body onload = "alert123()">
	<div>
	<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">   
	<input id="menuid" name="menuid" type="hidden" value=""/>
            选择监区：<select id="jianqu" name="jianqu" onchange="alert123()">
						<%
			if(signatureSchemes!=null && signatureSchemes.size()>0){
				for (int i=0;i<signatureSchemes.size();i++) {
				    Map signatureScheme = (Map)signatureSchemes.get(i);
      		%>
						<option value="<%=signatureScheme.get("ORGID")%>" ><%=signatureScheme.get("NAME")%></option>
						<%
					}
				}
			%>
					</select>
     </div>
     </div>
    	<div class="mini-fit" style="width:100%; height:100%;">
	     	<iframe id="subpage" width="100%" height="100%">
   			</iframe>
         </div>  
    <script type="text/javascript">
        mini.parse();
        function alert123(){
        var id = document.getElementById("jianqu").value;
	        var url="getReducePenaltyByAnJianHao.action?1=1&tbcode=tbCode&menuid=13743&orgid="+id+"&toolbar=0&doctype=report";
	        document.getElementById("subpage").src=url;
        }
        
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        } 
      	function close(){
      		CloseWindow("cancel");
      	}
    </script>
 </body>
</html>