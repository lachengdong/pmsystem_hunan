<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>呈批表、档案资料</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
     
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   		}    
    </style> 
</head>
<body onload="myload('${menuid}')">
<div  id="form1" class="mini-splitter"  vertical="true" style="width:100%;height:100%;border:0px;">
	<div size="38px;" showCollapseButton="false">
		<div class="mini-toolbar" style="height:30px;">
		<table>
		<tr>
		<td style="width:100%;">
	    	<a class="mini-button"   id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"   id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
			<input id="flag" name="flag" type="hidden" value="0"/>
			<input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
			<input id="mytempid" name="mytempid"  type="hidden" value="${tempid}"/>
			<input id="flowdraftid" name="flowdraftid"  type="hidden" value="${flowdraftid}"/>
			<a class="mini-button"  id="12321" plain="true" iconCls="icon-save"  onclick="saveBigData()">存盘</a>
			<span id="span1"><span class="separator"></span></span> 
	    	<a class="mini-button"    id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"    id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"    id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"    id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"    id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"    id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"    id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"    id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"    id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"    id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
	    </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"     id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
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

        //判断菜单id是否为空 （案件查看的时候 方法后面不跟menuid="菜单id"）
        var menuid = $("#menuid").val();
        if(menuid == '10090_06' || menuid == '10096'){
             $("#12321").hide();
             $("#span1").hide();
        }
        function myload(menuid){
        	if(menuid=='10257_05'||menuid=='10259_06'||menuid=='11279'||menuid=='11295'|| menuid == '10091_07'){
        		document.getElementById("12321").style.display="none";
        	}
        }
        function saveBigData() {
            //获取iframe的window对象
          //  var topWin = document.getElementById("subpage").contentWindow;
             //通过获取到的window对象操作HTML元素，这和普通页面一样
             var bigdata=saveFile();
             var tempid = document.getElementById("mytempid").value;
        	 var crimid = document.getElementById("crimid").value;
        	 var flowdraftid = document.getElementById("flowdraftid").value;
        	 var flowid = document.getElementById("flowid").value;
             $.ajax({
					type:"POST", 
					url:'<%=path%>/saveChengBaoReport.action?1=1', 
					data:{bigdata:bigdata,tempid:tempid, crimid:crimid, flowdraftid:flowdraftid,flowid:flowid}, 
					cache:false,
					success:function (text) {
							var obj = eval(text);
							if(obj=='1'){
								alert("操作成功！");
							}else{
								alert("操作失败！");
							}
					}
			});
        }
        
        function submitBigData(){
        	var aipDate = mini.formatDate ( new Date(), "yyyyMMdd" )
        	var topWin2 = document.getElementById("subpage").contentWindow;
        	topWin2.setAValue("shenheriqi", aipDate);
        	saveBigData();
        	close();
        }
        
      /*  function previewSuggestDoc(){
        	var flag = document.getElementById("flag").value;
        	if(flag=='0'){
        		var menuid = document.getElementById("menuid").value;
        		init(menuid);
        		document.getElementById("flag").value = flag+'1';
        	}
        	var mytempid = document.getElementById("mytempid").value;
        	var crimid = document.getElementById("crimid").value;
        	var flowdraftid = document.getElementById("flowdraftid").value;
        	if(crimid){
	           var url="toChengBaoReportDocumentPage.action?crimid="+crimid+"&tempid="+mytempid+"&flowdraftid="+flowdraftid;
	           document.getElementById("subpage").src=url;
            }else{
            	alert("请指定具体的罪犯！");
            }
        }*/
        
    </script>
 </body>
</html>