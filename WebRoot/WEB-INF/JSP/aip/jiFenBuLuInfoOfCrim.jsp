<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%@page import="com.sinog2c.util.common.*"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>计分补录保存</title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
	</style>
</head>
<body onload="init('${menuid}');">
	<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<input id="courtsanction" name="courtsanction" class="mini-hidden" value="${courtsanction }"/>
		<input id="text1" name="text1" class="mini-hidden" value="${text1}"/>
		<input id="strs" name="strs" class="mini-hidden" value="${strs}"/>
		<input id="operator1" name="operator1" type="hidden" value="${operator}"/>
		<input id="isapprove" name="isapprove" type="hidden" value="${isapprove}"/>
		
		<input id="xfbduuid" name="xfbduuid" type="hidden" value="${xfbduuid}"/>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"  style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
			<a class="mini-button" id="12074" style="display:none;" iconCls="icon-save" plain="true" onclick="savedata();">保存</a>
			<a class="mini-button" id="12617" style="display:none;" iconCls="icon-save" plain="true" onclick="savedata();">保存</a>
			<a class="mini-button" id="900351" style="display:none;" iconCls="icon-save" plain="true" onclick="batchReview();">提交</a>
			<a class="mini-button" id="" plain="true" iconCls="icon-close" onclick="onCancel();">关闭</a>
			<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="display:none;" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"  style="display:none;" id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   style="display:none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
	  	</div>
  	</div>
	<div showCollapseButton="false">
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include> 
  	</div>
  </div>
	<script type="text/javascript">
	    mini.parse();
        //返回到罪犯选择页面
        function backChoose(){
        	history.back();
        }
        var aipObj = document.getElementById("HWPostil1");
        //alert(1);
    	//alert(2)
         //保存或更新
        function savedata() {
       		var content =saveFile();
       		var operator = $("#operator1").val();
       		var strs = mini.get("strs").getValue();
       		var docid = mini.get("docid").getValue();
       		var crimid = mini.get("crimid").getValue();
       		var text1 = mini.get("text1").getValue();
       		var xfbduuid = $("#xfbduuid").val();
       		var courtsanction = mini.get("courtsanction").getValue();
       		var noteinfo = getNoteMap();
       		var url="saveJiFenBuLu.action?1=1";
       		
        	if(operator == "new"){//新增
	            $.ajax({
	                url:encodeURI(encodeURI(url)),
	                data: {text1:text1,content:content,noteinfo:noteinfo,tempid:"XFZX_JFBL",operator:operator,crimid:crimid,xfbduuid:xfbduuid},
	                type: "post",
	                async: false,
	                success: function (text) {
	                	alert("操作成功!");
	                  	back();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
            }else if(operator == "edit"){//更新
            	aipObj.SetValue("citjudgedate",":PROP::LABEL:3");
	            $.ajax({
	                url: "saveJiFenBuLu.action?1=1",
	                data: {text1:text1,content:content,noteinfo:noteinfo,docid:docid,operator:operator,crimid:crimid,xfbduuid:xfbduuid},
	                type: "post",
	                async: false,
	                success: function (text) {
	                	alert("操作成功!");
	                  	back();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
           }
        }
        
         function getbdfd(strName){
	    	var aipObj = getmyvalue("HWPostil1");
	    	var citextent = aipObj.GetValueEx("citextent",2,"",0,"");
	    	var url = "<%=path%>/tobdfdPage.page?citextent="+encodeURI(citextent);
	    	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:250px;dialogWidth:250px");
	    	//if(vRet){
	    		if(strName=="citchangetermto"){
			    	aipObj.SetValue("Page1.citchangetermto","");
			    	aipObj.SetValue("Page1.citchangetermto",vRet);
	    		}else if(strName=="citextent"){
			    	aipObj.SetValue("Page1.citextent","");
			    	aipObj.SetValue("Page1.citextent",vRet);
	    		}else if(strName=="citchangedisfranchiseto"){
			    	aipObj.SetValue("Page1.citchangedisfranchiseto","");
			    	aipObj.SetValue("Page1.citchangedisfranchiseto",vRet);
	    		}
		    //}
	    }
         function getbz(strName){
		    	var aipObj = getmyvalue("HWPostil1");
		    	var citchangedisfranchiseto = aipObj.GetValueEx("citchangedisfranchiseto",2,"",0,"");
		    	var url = "<%=path%>/tobzPage.page?citchangedisfranchiseto="+encodeURI(citchangedisfranchiseto);
		    	vRet = window.showModalDialog(url,"","edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:250px;dialogWidth:250px");
		    	if(vRet){
				    	aipObj.SetValue("Page1.citchangedisfranchiseto","");
				    	aipObj.SetValue("Page1.citchangedisfranchiseto",vRet);
			    }
	    }
		//标准方法接口定义
        function SetData(data) {
            data = mini.clone(data);
            //alert(aipObj1);
            mini.get("operator").setValue(data.action);
            //alert();
        }
         function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
		function onCancel(e) {
            CloseWindow("cancel");
        }
		//批量重审
        function batchReview(){
        	var crimid = mini.get("crimid").getValue();
       		var courtsanction = mini.get("courtsanction").getValue();
       		//alert(courtsanction);
        	savedata();
        	var url="<%=path%>/batchReview.json?1=1";
            $.ajax({
		        url: url,
		        data: {ids:courtsanction,crimid:crimid,type:1},
		        type: "post",
		        async: false,
		        success: function (text) {
		            var obj = text;
		            grid.reload();
		        }
	       });  
        }
    </script>
</body>
</html>
