<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯保外</title>
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
			<input id="menuid" name="menuid" class="mini-hidden" value="${menuid}"/>
			<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
			<input id="fathermenuid" name="fathermenuid" class="mini-hidden"  value="${fathermenuid}"/>
			<input id="lastnodeid" name="lastnodeid" type="hidden"  value="${lastnodeid}"/>
			<input id="closetype" name="closetype" type="hidden"  value="${closetype}"/>
			<input id="flowdefid" name="flowdefid" class="mini-hidden"  value="${flowdefid}"/>
			<input id="savetype" name="savetype" type="hidden"  value="0"/>
			<input id="commenttext" name="commenttext" type="hidden"  value="${commenttext}"/>
			<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			
			<!-- 处室经办  -->
			<a class="mini-button" style="display:none;" id="11468"  iconCls="icon-save" plain="true" onclick="doSubmitFlow('11468','save','other_sjbwjysp','ZFABWJYSPB');">存盘</a>
			<a class="mini-button" style="display:none;" id="11469"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlow('11469','yes','other_sjbwjysp','ZFABWJYSPB');">提交</a>
			
			<a class="mini-button" style="display:none;" id="11479"  iconCls="icon-save" plain="true" onclick="doSubmitFlow('11479','save','other_sjbwjysp','ZFABWJYSPB');">存盘</a>
	
			<!-- 处室审核 -->
			<a class="mini-button" style="display:none;" id="11475"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('11475','no','other_sjbwjysp','ZFABWJYSPB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="11476"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('11476','back','other_sjbwjysp','ZFABWJYSPB');">退回</a>
			<a class="mini-button" style="display:none;" id="11474"  iconCls="icon-downgrade" plain="true" onclick="doApproveFlow('11474','yes','other_sjbwjysp','ZFABWJYSPB');">同意</a>
			
			<!-- 局长审批 -->
			<a class="mini-button" style="display:none;" id="11481"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('11481','no','other_sjbwjysp','ZFABWJYSPB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="11482"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('11482','back','other_sjbwjysp','ZFABWJYSPB');">退回</a>
			<a class="mini-button" style="display:none;" id="11480"  iconCls="icon-downgrade" plain="true" onclick="doApproveFlow('11480','yes','other_sjbwjysp','ZFABWJYSPB');">同意</a>
			
			<a class="mini-button" id="" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
			<a class="mini-button"  id="" iconCls="icon-close" plain="true" onclick="Close(${closetype});">关闭</a>
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
	  	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
  	</div>
  </div>
       
    <script type="text/javascript">
    	mini.parse();
		//返回到罪犯选择页面
        function backChoose(){
        	history.back();
        }

    	//手动下一个
        function saveNext (){
        	var id = mini.get("id").getValue();
        	var closetype = document.getElementById("closetype").value;
        	if(id==null||id==''){
				Close(closetype);
				return;
            }
        	var menuid = mini.get("menuid").getValue();
        	var fathermenuid = mini.get("fathermenuid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
            var index = id.indexOf(",");
            id = id.substring(index+1,id.length);
            var url= "toProvinceOutPrisonTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fathermenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
            if(index>0){
                window.parent.location.href=url;
            }else{
            	//alert("批量处理已完成！");
            	Close(closetype);
            }
        }
        //手动关闭
        function Close(closetype){
            if(closetype=='0'){ //关闭在待办事项办理案件时的弹出窗口
                var parentWindow = window.parent;
                parentWindow.CloseWindow("cancel");
            }else if(closetype =='1'){//跳转至处室经办人页
            	var fathermenuid = mini.get("fathermenuid").getValue();
            	var flowdefid = mini.get("flowdefid").getValue();
            	var tempid = mini.get("tempid").getValue();
        		var url = "<%=path%>/toOutPrisonProvinceChuShiListPage.action?menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid;
        		window.parent.location.href=url;
            }else if(closetype =='2'){//跳转至省局案件办理页面
            	var fathermenuid = mini.get("fathermenuid").getValue();
            	var flowdefid = mini.get("flowdefid").getValue();
            	var tempid = mini.get("tempid").getValue();
            	var url = "<%=path%>/toOutPrisonProvinceCaseListPage.action?menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid;
            	window.parent.location.href=url;
            }
        }
       //自动关闭本页面，跳转到下一个
        function close(){
        	Close();
        	//saveNext();
        }
       //////////////////////////
        //标准方法接口定义
	    function SetData(data) {
       
        }

	    function CloseWindow(action) { 
	           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	           else window.close();            
	    }
	    
    </script>
</body>
</html>
