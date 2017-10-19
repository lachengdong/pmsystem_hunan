<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
String temptype=(String)request.getAttribute("temptype");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>监狱保外办案</title>
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
			<input id="snodeid" name="snodeid" type="hidden"  value="${lastnodeid}"/>
			<input id="savetype" name="savetype" type="hidden"  value="0"/>
			<input id="commenttext" name="commenttext" type="hidden"  value="${commenttext}"/>
			<input id="shanxi" name="shanxi" type="hidden" value="${shanxi}"/>
			<input id="flowdraftid" name="flowdraftid" type="hidden" value="${flowdraftid}"/>
			<!--  -->
			<input id="signcheckbiaodan" name="signcheckbiaodan" type="hidden" value="${signcheckbiaodan }"/>
			<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			
			<!-- 经办人办案   -->
			<a class="mini-button" style="display:none;" id="12602"  iconCls="icon-save" plain="true" onclick="doSubmitFlow('12602','save','other_jybwjycbsp','${tempid}');">存盘</a>
			<a class="mini-button" style="display:none;" id="12603"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlow('12603','yes','other_jybwjycbsp','${tempid}');">提交</a>
			<!-- 流程跳级用 -->
			<a class="mini-button" style="display:none;" id="1600181"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlow('1600181','yes','other_jybwjycbsp','${tempid}');">提交</a>
			
			<a class="mini-button" style="display:none;" id="15955"  iconCls="icon-save" plain="true" onclick="saveSJBWJYSPB('15955','save','other_jybwjycbsp','SX_SJBWJYSPB');">存盘</a>
						<!-- 监狱长 -->
			<a class="mini-button" style="display:none;" id="1600721"  iconCls="icon-save" plain="true" onclick="saveSJBWJYSPB('15955','save','other_jybwjycbsp','ZFABWJYSPB');">存盘</a>
			<a class="mini-button" style="display:none;" id="11450"  iconCls="icon-save" plain="true" onclick="doSubmitFlow('11450','save','other_jybwjycbsp','${tempid}');">存盘</a>
			
			<!-- 分监区评议 -->
			<a class="mini-button" style="display:none;" id="13680"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('13680','no','other_jybwjycbsp','${tempid}');">拒绝</a>
			<a class="mini-button" style="display:none;" id="13679"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'13679','yes','other_jybwjycbsp','${tempid}');">同意</a>
		
			<!-- 监区评议 -->
			<a class="mini-button" style="display:none;" id="13682"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'13682','yes','other_jybwjycbsp','${tempid}');">同意</a>
			<a class="mini-button" style="display:none;" id="13684"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('13684','no','other_jybwjycbsp','${tempid}');">拒绝</a>
			<a class="mini-button" style="display:none;" id="13685"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('13685','back','other_jybwjycbsp','${tempid}');">退回</a>
			
			<!--初审小组（新疆）-->
			<a class="mini-button" style="display:none;" id="1000111"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('1000111','no','other_jybwjycbsp','ZFABWJYSPB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="1000110"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('1000110','back','other_jybwjycbsp','ZFABWJYSPB');">退回</a>
			<a class="mini-button" style="display:none;" id="1000105"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'1000105','yes','other_jybwjycbsp','ZFABWJYSPB');">同意</a>
			
			<!-- 监狱评审（新疆科室）-->
			<a class="mini-button" style="display:none;" id="11452"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('11452','no','other_jybwjycbsp','${tempid}');">拒绝</a>
			<a class="mini-button" style="display:none;" id="12607"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('12607','back','other_jybwjycbsp','${tempid}');">退回</a>
			<a class="mini-button" style="display:none;" id="11451"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'11451','yes','other_jybwjycbsp','${tempid}');">同意</a>
			<!-- a class="mini-button" style="display:none;" id="11451"  iconCls="icon-downgrade" plain="true" onclick="doApproveFlow('11451','yes','other_jybwjycbsp','${tempid}');">同意</a-->
			<!-- 保外，广东监狱评审 -->
			<a class="mini-button" style="display:none;" id="14745"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'14745','yes','other_jybwjycbsp','${tempid}');">同意</a>
			<a class="mini-button" style="display:none;" id="14746"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('14746','no','other_jybwjycbsp','${tempid}');">拒绝</a>
			<a class="mini-button" style="display:none;" id="14747"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('14747','back','other_jybwjycbsp','${tempid}');">退回</a>
			
			<!--复合小组（新疆）-->
			<a class="mini-button" style="display:none;" id="1000113"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('1000113','no','other_jybwjycbsp','ZFABWJYSPB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="1000112"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('1000112','back','other_jybwjycbsp','ZFABWJYSPB');">退回</a>
			<a class="mini-button" style="display:none;" id="1000108"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'1000108','yes','other_jybwjycbsp','ZFABWJYSPB');">同意</a>
			
			<!--评审会（新疆）-->
			<a class="mini-button" style="display:none;" id="1000115"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('1000115','no','other_jybwjycbsp','ZFABWJYSPB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="1000114"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('1000114','back','other_jybwjycbsp','ZFABWJYSPB');">退回</a>
			<a class="mini-button" style="display:none;" id="1000106"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'1000106','yes','other_jybwjycbsp','ZFABWJYSPB');">同意</a>
			
			<!-- 监狱签发 -->
			<a class="mini-button" style="display:none;" id="12608"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'12608','yes','other_jybwjycbsp','${tempid}');">同意</a>
			<a class="mini-button" style="display:none;" id="12609"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlow('12609','no','other_jybwjycbsp','${tempid}');">拒绝</a>
			<a class="mini-button" style="display:none;" id="12610"  iconCls="icon-undo" plain="true" onclick="doApproveFlow('12610','back','other_jybwjycbsp','${tempid}');">退回</a>
			<!-- a class="mini-button"style="display:none;"  id="12608"  iconCls="icon-downgrade" plain="true" onclick="doApproveFlow('12608','yes','other_jybwjycbsp','${tempid}');">同意</a-->
			<%if(!("sj".equals(temptype))){ %>
			 <a class="mini-button" id="" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
			<a class="mini-button"  id="" iconCls="icon-close" plain="true" onclick="Close(${closetype});">关闭</a>
		 	<%} %>
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
        	var flowdefid = document.getElementById("flowdefid").value;
        	if(id==null||id==''){
				Close(closetype);
				return;
            }
        	var menuid = mini.get("menuid").getValue();
        	var fathermenuid = mini.get("fathermenuid").getValue();
        	var tempid = mini.get("tempid").getValue();
            var index = id.indexOf(",");
            id = id.substring(index+1,id.length);
            var url= "toOutPrisonOfJailCaseTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fathermenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
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
            }else if(closetype =='1'){//跳转至监狱经办人页
            	var fathermenuid = mini.get("fathermenuid").getValue();
            	var flowdefid = mini.get("flowdefid").getValue();
            	var tempid = mini.get("tempid").getValue();
        		var url = "<%=path%>/toBaoWaiJianBanListPage.action?menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid+"&temptype=sj";
        		window.parent.location.href=url;
            }else if(closetype =='2'){//跳转至监狱案件办理页面
            	var fathermenuid = mini.get("fathermenuid").getValue();
            	var flowdefid = mini.get("flowdefid").getValue();
            	var tempid = mini.get("tempid").getValue();
            	var url = "<%=path%>/toBaoWaiOfJailCaseListPage.action?menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid+"&temptype=sj";
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
	    function saveSJBWJYSPB(){
	    
	    	var flowdraftid=mini.get("flowdraftid").getValue();
	    	var tempid=mini.get("tempid").getValue();
	    	var datafile=saveFile();
	    	$.ajax({
	    		url:"<%=path%>/saveSJBWJYSPB.action?1=1",
	    		data:{flowdraftid:flowdraftid,tempid:tempid,datafile:datafile},
    		 	type: "post",
    		 	async: true,
	    		success:function(text){
	    				alert("操作成功!");
	    		},
	    		error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	    	});
	    }
    </script>
</body>
</html>
