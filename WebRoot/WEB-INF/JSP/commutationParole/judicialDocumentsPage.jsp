<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>司法文书</title>
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
<body onload="getAllReportResources();">
	<div>
	<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">   
	<input id="menuid" name="menuid" type="hidden" value=""/>
	<input id="crimid" type="hidden" value="${crimid}"/>
            司法文书：<select onchange="yulanwenshu()" id="wenshu">
  					
			  </select>
	    	<span class="separator"></span> 
	    	<!--<a class="mini-button" id="201" iconCls="icon-save" plain="true" onclick="save();">存盘</a>
	    	<span class="separator"></span> 
	    	-->
	    	<a class="mini-button" style="display:none;" id="210" iconCls="icon-node" plain="true" onclick="doMenueButton(3);">阅读</a>
	    	<a class="mini-button" style="display:none;" id="211" iconCls="" plain="true" onclick="doMenueButton(4);">全屏</a>
	    	<a class="mini-button" style="display:none;" id="207" iconCls="icon-folderopen" plain="true" onclick="doMenueButton(0);">打开</a>
	    	<a class="mini-button" style="display:none;" id="208" iconCls="icon-folder" plain="true" onclick="doMenueButton(1);">另存为</a>
	    	<a class="mini-button" style="display:none;" id="209" iconCls="icon-expand" plain="true" onclick="doMenueButton(2);">OCR识别</a>
     </div>
     </div>
    	<div class="mini-fit" style="width:100%; height:100%;">
	     	<iframe id="subpage" width="100%" height="100%">
   			</iframe>
         </div>  
    <script type="text/javascript">
        mini.parse();
        var crimid = document.getElementById("crimid").value;
        
        function yulanwenshu(){
        	var selectObjValue = document.getElementById("wenshu").value;
        	if(selectObjValue == 0){
                  return;
            }
        	var crimid = document.getElementById("crimid").value;
        	if(crimid){
	             //var url="toSingleJudicialDocumentPage.action?crimid="+crimid+"&datatype="+selectObjValue
	             //url = encodeURI(encodeURI(url));
	             var url="getReducePenaltyByAnJianHao.action?1=1&tbcode=tbCode&menuid="+selectObjValue+"&toolbar=0";
	             document.getElementById("subpage").src=url;
            }else{
            	 alert("请指定具体的罪犯！");
            }
        }
        //打开页面 动态 生成 下拉列表 
        function getAllReportResources(){
             var option = '<option value=0 >请选择(相应文书)</option>';
             $.ajax({
                  url:'getAllReportResources.action?1=1',
                  type:'POST',
                  async:false,
                  success:function (text){
                       var obj = eval(text);
                       for(var i=0;i<obj.length;i++){
                           var report = obj[i];
                           option += '<option value='+report.RESID+'>'+report.NAME+'</option>';
                       }
                       $("#wenshu").append(option);
                  }
             });
        }
    </script>
 </body>
</html>