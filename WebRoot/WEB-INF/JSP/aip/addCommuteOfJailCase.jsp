<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String path = request.getContextPath();
String unitlevel = (String)request.getAttribute("unitlevel");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>监狱罪犯减刑表单页面(签章控制)</title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/loginsealV7.js" type="text/javascript"></script> 
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
			
			<input id="fathermenuid" name="fathermenuid" class="mini-hidden"  value="${fathermenuid}"/>
			<input id="lastnodeid" name="lastnodeid" type="hidden"  value="${lastnodeid}"/>
			<input id="closetype" name="closetype" type="hidden"  value="${closetype}"/>
			<input id="flowdefid" name="flowdefid" class="mini-hidden"  value="${flowdefid}"/>
			<input id="snodeid" name="snodeid" type="hidden"  value="${lastnodeid}"/>
			<input id="savetype" name="savetype" type="hidden"  value="0"/>
			<input id="commenttext" name="commenttext" type="hidden"  value="${commenttext}"/>
			<input id="ischeckseal" name="ischeckseal" type="hidden"  value="${ischeckseal}"/>			
			<input id="provinceid" name="provinceid" class="mini-hidden"  value="${provinceid}"/>
			<input id="nodeid" name="nodeid" class="mini-hidden"  value="${nodeid}"/>
			<input id="days" name="days" class="mini-hidden"  value="${days}"/>
			<!-- shanxi标签内容:控制宁夏 打开审核表进入不同的方法 -->
			<input id="shanxi" name="shanxi" type="hidden"  value="${shanxi}"/>
			<!-- chengpibiao 标签内容:控制在提交的时候 是否受呈批表的控制（1：不受控制，0：受控制） -->
			<input id="chengpibiao" name="chengpibiao" class="mini-hidden"  value="${chengpibiao}"/>
			<!-- forfeituretype 标签内容:控制在提交的时候 是否受未履行财产刑的控制（1：不受控制，0：受控制） -->
			<input id="forfeituretype" name="forfeituretype" class="mini-hidden"  value="${forfeituretype}"/>
			<!-- signcheckbiaodan 标签内容：判断需要进行流程控制的表单 -->
			<input id="signcheckbiaodan" name="signcheckbiaodan" type="hidden" value="${signcheckbiaodan }"/>
			<!-- flowdefids：标签内容：是否更新签章进程 -->
			<input id="flowdefids" name="flowdefids" type="hidden" value="${flowdefids }"/>
			 
			<!-- 经办人办案   -->
			<a class="mini-button" style="display:none;" id="10090_01"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('signature',1,'10090_01','yes','other_zfjyjxjssp','JXJS_JXJSSHB');">提交</a>
			
			<!-- 分监区管教 -->
			<a class="mini-button" style="display:none;" id="1600720"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('signature',1,'1600720','yes','other_zfjyjxjssp','JXJS_JXJSSHB');">提交</a>
			<a class="mini-button" style="display:none;" id="10090_07"  iconCls="icon-save" plain="true" onclick="doSubmitFlow('10090_07','save','other_zfjyjxjssp','JXJS_JXJSSHB');">存盘</a>
			
			<a class="mini-button" style="display:none;" id="12055"  iconCls="icon-save" plain="true" onclick="doSubmitFlow('12055','save','other_zfjyjxjssp','JXJS_JXJSSHB');">存盘</a>
			<!-- 分监区办案 -->
			<!-- doSubmitFlowAfterCheckSeal:参数1：签章or批注，参数2：批注or签章个数(废弃)，参数3:资源id，参数4：操作状态，参数5：自定义id，参数6:tempid -->
			<a class="mini-button" style="display:none;" id="12584"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('signature',2,'12584','yes','other_zfjyjxjssp','JXJS_JXJSSHB');">同意</a>
			<a class="mini-button" style="display:none;" id="12585"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('signature',1,'12585','no','other_zfjyjxjssp','JXJS_JXJSSHB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="12586"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('signature',1,'12586','back','other_zfjyjxjssp','JXJS_JXJSSHB');">退回</a>
		
			<!-- 监区评议 -->
			<a class="mini-button" style="display:none;" id="10091_01"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',1,'10091_01','yes','other_zfjyjxjssp','JXJS_JXJSSHB');">同意</a>
			<a class="mini-button" style="display:none;" id="10091_03"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',0,'10091_03','no','other_zfjyjxjssp','JXJS_JXJSSHB');">拒绝</a>
			<!-- 退回分监区 -->
			<%if("11".equals(unitlevel)){ %>
			<a class="mini-button" style="display:none;" id="1600721"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',0,'1600721','back','other_zfjyjxjssp','JXJS_JXJSSHB');">退回</a>
			<%}else{%>
			<a class="mini-button" style="display:none;" id="10091_02"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',0,'10091_02','back','other_zfjyjxjssp','JXJS_JXJSSHB');">退回</a>
			<%} %>
		    <!-- 狱政科审批 -->
		    <a class="mini-button" style="display:none;" id="12526"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',1,'12526','yes','other_zfjyjxjssp','JXJS_JXJSSHB');">同意</a>
			<a class="mini-button" style="display:none;" id="12527"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',0,'12527','no','other_zfjyjxjssp','JXJS_JXJSSHB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="12528"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',0,'12528','back','other_zfjyjxjssp','JXJS_JXJSSHB');">退回</a>
		
		    
			<!-- 部门审查/科室-->
			<a class="mini-button" style="display:none;" id="11429"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'11429','yes','other_zfjyjxjssp','JXJS_JXJSSHB');">同意</a>
			<a class="mini-button" style="display:none;" id="11430"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',0,'11430','no','other_zfjyjxjssp','JXJS_JXJSSHB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="11431"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',0,'11431','back','other_zfjyjxjssp','JXJS_JXJSSHB');">退回</a>
		
			<!-- 监狱评审-->
			<a class="mini-button" style="display:none;" id="12056"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',0,'12056','yes','other_zfjyjxjssp','JXJS_JXJSSHB');">同意</a>
			<a class="mini-button" style="display:none;" id="12057"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',1,'12057','no','other_zfjyjxjssp','JXJS_JXJSSHB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="12058"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',1,'12058','back','other_zfjyjxjssp','JXJS_JXJSSHB');">退回</a>
		
			<!-- 监狱签发 -->
			<a class="mini-button"style="display:none;"  id="11434"  iconCls="icon-downgrade" plain="true" onclick="doSubmitFlowAfterCheckSeal('seal',4,'11434','yes','other_zfjyjxjssp','JXJS_JXJSSHB');">同意</a>
			<a class="mini-button" style="display:none;" id="11435"  iconCls="icon-upgrade" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',2,'11435','no','other_zfjyjxjssp','JXJS_JXJSSHB');">拒绝</a>
			<a class="mini-button" style="display:none;" id="11436"  iconCls="icon-undo" plain="true" onclick="doApproveFlowAfterCheckSeal('seal',2,'11436','back','other_zfjyjxjssp','JXJS_JXJSSHB');">退回</a>
			
			<a class="mini-button"  id=""  iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
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
	    	<a class="mini-button"  id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
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
  <div id="longinSeal" style="display:none;"></div>
<script type="text/javascript">
    	mini.parse();
		
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
            var url= "toCommuteOfJailCaseTabs.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid+"&fathermenuid="+fathermenuid+"&closetype="+closetype+"&flowdefid="+flowdefid;
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
            }else{
            	var fathermenuid = mini.get("fathermenuid").getValue();
            	var flowdefid = mini.get("flowdefid").getValue();
            	var tempid = mini.get("tempid").getValue();
                 if(closetype =='1'){//跳转至监狱经办人页
	        		var url = "<%=path%>/toAgentCasePage.action?1=1&menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid;
	        		window.parent.location.href=url;
	            }else if(closetype =='2'){//跳转至监狱案件办理页面
	            	var provinceid = mini.get("provinceid").getValue();
	            	var nodeid = mini.get("nodeid").getValue();
	            	var days = mini.get("days").getValue();
	
	            	var url = "<%=path%>/toCommuteOfJailCasePage.action?1=1&menuid="+fathermenuid+"&flowdefid="+flowdefid+"&tempid="+tempid;
	            	url += "&provinceid="+provinceid+"&nodeid="+nodeid+"&days="+days;
	            	window.parent.location.href=url;
	            }
            }
        }
        
        //标准方法接口定义
	    function SetData(data) {
       
        }

	    function CloseWindow(action) { 
	           if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	           else window.close();            
	    }

	    function SealLogin(){
			if (!window["isIE"]) {
				//
				alert("请使用IE浏览器");
				return;
			}
			makeSeal();
			var obj=document.getElementById("DWebSignSeal");
			
			var login=obj.CardLogin("");//登录key，确认用户身份
			var subjectObj = obj.GetCertInfo(0,0,"");//获取证书主题	
			var certnoObj = obj.GetCertInfo(1,0,"");//获取证书序列号
			var certdnObj = obj.GetCertInfo(2,0,"");//获取证书DN		
			var certdataObj = obj.GetCertInfo(3,0,"");//获取公钥证书
		    return 	certdnObj+"^"+subjectObj+"^"+certnoObj;
		}
    </script>
</body>
</html>
