<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯基本信息页面</title>
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
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"  style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
			<input id="selectid" name="selectid" class="mini-hidden"  value=""/><!-- 个人简介，家庭成员，同案犯ID数组 -->
			<input id="closetype" name="closetype" type="hidden"  value="${closetype}"/>
			<input id="provincecode" name="provincecode" class="mini-hidden"  value="${provincecode}"/>
			<input id="fathermenuid" name="fathermenuid" class="mini-hidden"  value="${fathermenuid}"/>
			
			
	    	<a class="mini-button" style="display:none;" id="11096" iconCls="icon-save" plain="true" onclick="savedata('','save','doc_zfrjdjsp','SDXF_BASE_RJDJB')">保存</a>
	    	<a class="mini-button" style="display:none;" id="10900" iconCls="icon-downgrade" plain="true" onclick="savedata('','yes','doc_zfrjdjsp','SDXF_BASE_RJDJB')">提交</a>
	    	
	    	
	    	<a class="mini-button" style="display:none;" id="3100483" iconCls="icon-save" plain="true" onclick="savedata('','save','','SDXF_BASE_AJXXBL')">保存</a>
	    	<a class="mini-button" style="display:none;" id="3100485" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('','yes','');">同意</a>
	    	<a class="mini-button" style="display:none;" id="3100489" iconCls="icon-downgrade" plain="true" onclick="operationOpprove('','back','');">退回</a>
			
	    	
			<a class="mini-button" style="display:black;" id="11415" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
	    	<a class="mini-button" style="display:black;" id="12752" iconCls="icon-close" plain="true" onclick="onCanle();">关闭</a>
	    	
	    	<span class="separator" id="span1" style="display:black;"></span>
	    	<a class="mini-button"  iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"  iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"  iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
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
        var flowdefid = mini.get("flowdefid").getValue();
        var jianxing = "${jxjsapprove}";
        if('${closetype}'=='1' || '${closetype}'=='2'){
        	$("#12752").hide();
        	$("#span1").hide();	
        }
      
      //保存、提交
      function savedata(menuid,type,flowdefid,tempid) {
    	  	var aipObj=document.getElementById("HWPostil1");
     		var crimid = mini.get("crimid").getValue();
     		var selectid = mini.get("selectid").getValue();
     		var noteinfo = getNoteMap();
     		var url="<%=path%>/basicInfo/saveCrimInfo.json?1=1";
            $.ajax({
               url: encodeURI(encodeURI(url)),
               data: {noteinfo:noteinfo,crimid:crimid,tempid:tempid,selectid:selectid},
               type: "post",
               dataType:"text",
               success: function (text) {
             	   if(text.substring(0,7)=='success'){
	               	   mini.get("selectid").setValue("["+text.substring(7,text.length)+"]");
	               	   //alert("操作成功！");
	               	   saveImg(crimid);
	               	   if('yes' == type){
	               	   	doSubmitFlow(menuid,type,flowdefid,tempid);//保存大字段
	               	   }else{
	               		alert("操作成功！");
	               	   }
               	   }else if(text=='fysuccess'){
               	    	alert("操作成功！");
               	   }else{
               	   		alert("操作失败！");
               	   }
               },
               error: function (jqXHR, textStatus, errorThrown) {
               	   alert("操作失败!");
               }
           });
        }
        
       //关闭
       function onCanle() {
       		var closetype = document.getElementById("closetype").value;
       		var fathermenuid = mini.get("fathermenuid").getValue();
       		var _criminalid = mini.get("crimid").getValue();
       		if(closetype){
       			Close(closetype);
       			return;
       		}
       		if(jianxing){
       			url = "<%=path%>/toPublicListPage.page?1=1&flowdefid=other_zfjyjxjssp&tempid=JXJS_JXJSSHB";
       		}else{
	    	    url = "<%=path%>/basicInfo/toBasicInfoChoosePage.page?1=1&menuid="+fathermenuid+"&flowdefid="+flowdefid+"&_criminalid="+_criminalid;
       		}
        	self.location.href=url;
       }
       
       //手动关闭
        function Close(closetype){
        	var fathermenuid = mini.get("fathermenuid").getValue();
            if(closetype=='0'){ //关闭在待办事项办理案件时的弹出窗口
                var parentWindow = window.parent;
                parentWindow.CloseWindow("cancel");
            }else if(closetype =='1'){//跳转至监狱 经办人页
        		var url = "<%=path%>/toAgentCasePage.action?menuid="+fathermenuid;
        		window.parent.location.href=url;
            }else if(closetype =='2'){//跳转至监狱案件办理页面
            	var url = "<%=path%>/toCommuteOfJailCasePage.action?menuid="+fathermenuid;
            	window.parent.location.href=url;
            }else if(closetype =='3'){//跳转至资格筛查页面
            	window.CloseOwnerWindow("cancel");
            }else if(closetype=='4'){
            	var url="<%=path%>/gotofastCriminalJianSuo.action?1=1&key=${key}"+"&zaiyacombo1=${zaiyacombo1}"+"&sdid=${sdid}";
            	window.self.location.href=url;
            }
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
        function saveNext(){
        	var id = mini.get("id").getValue();
        	var menuid = mini.get("menuid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var fathermenuid = mini.get("fathermenuid").getValue();
            var index = id.indexOf(",");
            id = id.substring(index+1,id.length);
            var url = "";
            if(jianxing){
       			url = "<%=path%>/toPublicListPage.page?1=1&flowdefid=other_zfjyjxjssp&tempid=JXJS_JXJSSHB";
       		}else{
       			url= "<%=path%>/basicInfo/basicInformation.page?1=1&id="+id+"&menuid="+menuid+"&fathermenuid="+fathermenuid+"&tempid="+tempid+"&flowdefid="+flowdefid;
       		}
            if(index>0){
                self.location.href=url;
            }else{
            	//alert("操作完毕！");
            	onCanle();
            }
        }
        function saveImg(crimid){
        	var url = "<%=path%>/sign/copyPic.json?1=1"; 
            var aipFileStr = saveFile();//获取大字段
            var aipObj=document.getElementById("HWPostil1");
            aipObj.Logout();
            //大字段 格式化为 控件识别文件对象
			//aipObj.LoadFileBase64(aipFileStr);
			var image = aipObj.GetValueEx("picrjdjimg",14, "jpg", 0, "ToFile");
            //alert(aipFileStr);
            //return;
            //alert(image);
            //,aipFileStr:aipFileStr
            if(image){
            	$.ajax({
	               url: url,
	               data: {image:image,crimid:crimid,imgtype:"_1.jpg"},
	               type: "post",
	               async: false,
	               success: function (text){
	            	   //alert(text);
	            	   //aipObj.SetValueEx(i,14,0, "STRDATA:"+text);
	                   //aipObj.CloseDoc(0);
	               },
	               error: function (jqXHR, textStatus, errorThrown){
	                     alert("操作失败!");
	               }
	           });
            }
         }
    </script>
</body>
</html>
