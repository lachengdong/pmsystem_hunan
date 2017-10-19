<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>监狱局罪犯减刑表单页面</title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }
	</style>
</head>
<body onload="myload('${menuid}');">
<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"  style="display:none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="display:none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
			<input id="lastnodeid" name="lastnodeid" type="hidden"  value="${lastnodeid}"/>
			<input id="snodeid" name="snodeid" type="hidden"  value="${lastnodeid}"/>
			<input id="closetype" name="closetype" type="hidden"  value="${closetype}"/>
			<input id="savetype" name="savetype" type="hidden"  value="0"/>
			<input id="commenttext" name="commenttext" type="hidden"  value="${commenttext}"/>
			<input id="nextButton" name="nextButton" type="hidden"  value="${nextButton}"/>
			<input id="ischeckseal" name="ischeckseal" type="hidden"  value="${ischeckseal}"/>
			 
			<!-- 处室经办  -->
			<a class="mini-button" style="display:none;" id="11275"  iconCls="icon-save" plain="true" onclick="doSubmitFlow('11275','save','other_sjjxjssp','JXJS_JXSH');">存盘</a>
			<a class="mini-button" style="display:none;" id="11278"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',3,'11278','yes','other_sjjxjssp','JXJS_JXSH');">提交</a>
			
			<a class="mini-button" style="display:none;" id="11290"  iconCls="icon-save" plain="true" onclick="doSubmitFlow('11290','save','other_sjjxjssp','JXJS_JXSH');">存盘</a>
	
			<!-- 处室审核 -->
			<a class="mini-button" style="display:none;" id="11358"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',4,'11358','yes','other_sjjxjssp','JXJS_JXSH');">同意</a>
			<a class="mini-button" style="display:none;" id="11357"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',4,'11357','no','other_sjjxjssp','JXJS_JXSH');">拒绝</a>
			<a class="mini-button" style="display:none;" id="11356"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',4,'11356','back','other_sjjxjssp','JXJS_JXSH');">退回</a>
			
			<!-- 评审会审核 -->			
			<a class="mini-button" style="display:none;" id="14029"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',5,'14029','yes','other_sjjxjssp','JXJS_JXSH');">同意</a>
			<a class="mini-button" style="display:none;" id="14030"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',4,'14030','no','other_sjjxjssp','JXJS_JXSH');">拒绝</a>
			<a class="mini-button" style="display:none;" id="14031"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',4,'14031','back','other_sjjxjssp','JXJS_JXSH');">退回</a>
			
			<a class="mini-button" style="display:none;" id="900330"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',5,'900330','yes','other_sjjxjssp','JXJS_JXSH');">同意</a>
			<a class="mini-button" style="display:none;" id="900331"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',4,'900331','no','other_sjjxjssp','JXJS_JXSH');">拒绝</a>
			<a class="mini-button" style="display:none;" id="900332"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',4,'900332','back','other_sjjxjssp','JXJS_JXSH');">退回</a>
						
			<!-- 局长审批 -->
			<a class="mini-button" style="display:none;" id="11293"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',7,'11293','yes','other_sjjxjssp','JXJS_JXSH');">同意</a>
			<a class="mini-button" style="display:none;" id="11292"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',5,'11292','no','other_sjjxjssp','JXJS_JXSH');">拒绝</a>
			<a class="mini-button" style="display:none;" id="11291"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',5,'11291','back','other_sjjxjssp','JXJS_JXSH');">退回</a>

			<a class="mini-button" style="display:none;" id="601641"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',7,'601641','yes','other_sjjxjssp','JXJS_JXSH');">同意</a>
			<a class="mini-button" style="display:none;" id="601642"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',5,'601642','no','other_sjjxjssp','JXJS_JXSH');">拒绝</a>
			<a class="mini-button" style="display:none;" id="601643"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',5,'601643','back','other_sjjxjssp','JXJS_JXSH');">退回</a>
						
			<a class="mini-button" id="next" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
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

		function myload(menuid){
			init(menuid);
			var nextButton = document.getElementById("nextButton").value;
			if(menuid=='11279'||menuid=='11295'||nextButton=='hidden'){
				document.getElementById("next").style.display="none";
			}
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
        	var fathermenuid = mini.get("fatherMenuid").getValue();
        	var tempid = mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
            var index = id.indexOf(",");
            id = id.substring(index+1,id.length);
            var url= "toProvinceCommuteTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fathermenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
            if(index>0){
                window.parent.location.href=url;
            }else{
            	//alert("批量处理已完成！");
            	Close(closetype);
            }
        }
        //手动关闭
        function Close(closetype){
        	var fathermenuid = mini.get("fatherMenuid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
        	var tempid = mini.get("tempid").getValue();
            if(closetype=='0'){ //关闭在待办事项办理案件时的弹出窗口
                var parentWindow = window.parent;
                //parentWindow.close();
                parentWindow.CloseWindow("cancel");
            }else if(closetype =='1'){//跳转至处室经办人页
        		var url = "<%=path%>/toOfficeCheckPage.action?menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid;
        		window.parent.location.href=url;
            }else if(closetype =='2'){//跳转至省局案件办理页面
            	var url = "<%=path%>/toProvinceLeaderPage.action?menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid;
            	window.parent.location.href=url;
            }
        }
       //自动关闭本页面，跳转到下一个
        function close(){
        	Close();
        	//saveNext();
        }
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
