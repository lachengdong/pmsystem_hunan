<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Object mydata = request.getAttribute("mydata"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    </style> 
    <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.all.HWPostil1.JSEnv = 1;
	</script>
</head>
<body onload="myonload();">
	<input id="mydata" value="${mydata}" type="hidden"/>
	<input id="curyear" value="" type="hidden"/>
	<input id="batch" value="" type="hidden"/>
	<div style="width:100%;height:100%;">
    	<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">    
			<table>
			<tr>
			<td style="width:100%;">
    		<a class="mini-button"  id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
			<span class="separator"></span> 
			<a class="mini-button" id="saved"  iconCls="icon-close" plain="true" onclick="close();">关闭</a>    
    	  	<span class="separator"></span>
	    	<a class="mini-button"  id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	        <span class="separator"></span> 
	    	<a class="mini-button"  id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"  id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"  id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
	    	 </td>
	    <td style="white-space:nowrap;">
	    	<a class="mini-button"   style="display:none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
	    </td>
	  	</tr>
	  	</table>
    	</div>
  		<div class="mini-fit" style="width:100%; height:100%;">
       		<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
    	</div>  
	</div>
	<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
	<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
	<script type="text/javascript">document.all("MyViewer").Init(window, document,600);</script>
   <script type="text/javascript">
    	var object = document.getElementById("HWPostil1");
		object.ShowToolBar = 0;//工具栏是否显示
		object.ShowDefMenu = 0;//菜单栏
		object.ShowScrollBarButton = 1;
       	mini.parse();
       	var flag = true;
        function dakai(){
       		var da = mini.get("dakai");
       		object.HideMenuItem(16384);
        	if(flag){
        		da.setIconCls("icon-unlock");
        		object.ShowToolBar = 1;//工具栏是否显示
        		flag = false;
        	}else{
        		da.setIconCls("icon-lock");
        		object.ShowToolBar = 0;//工具栏是否显示
        		flag = true;
        	}
        }
        //标准方法接口定义
        function SetData(data) {
             //跨页面传递的数据对象，克隆后才可以安全使用
             data = mini.clone(data);
             var otherid = data.otherid;
             var tempid = data.tempid;
             var curyear = data.curyear;
             var batch = data.batch;
             $("#status").val(status);
             $("#tempid").val(tempid);
             $("#otherid").val(otherid);
             $("#curyear").val(curyear);
             $("#batch").val(batch);
             //加载报表
        //     myonload();
        }
        
        function myonload(){
        	var reportdate = $("#mydata").val();
        	var aipObj=document.getElementById("HWPostil1");
        	aipObj.LoadFileBase64(reportdate);
        	//alert(reportdate);
			//document.all("MyViewer").ShowProgress = false;
			//document.all.HWPostil1.BeforeConvert("");
			//document.all("RMVIEWER_DATA").value=reportdate;
			//document.all("MyViewer").GetReportData(reportdate);
			//document.all("MyViewer").ShowPrintDialog = false;
			//document.all("MyViewer").PrintReport();
			//document.all.HWPostil1.WaitConverting(4000);
			//document.all.HWPostil1.AfterConvert();
		}				
      	function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        } 
      	function close() {
      		CloseWindow("cancel");
      	}
      	function save(type){
          	if(confirm('保存以后将无法对会议内容进行修改!')){
               
            }
      	    var otherid = $("#otherid").val();
      	    var tempid = $("#tempid").val();
      	    var flowid = $("#flowid").val();
      	    var curyear = $("#curyear").val();
      	    var batch = $("#batch").val();
      	    var status = "bigtext";
      	    var count = 0;
            //判断 当前 会议记录 是否 已经 保存 了大字段 
      	    $.ajax({
                type:'post',
                url:'juegeMeetWhetherExistDoc.action?1=1&otherid='+otherid,
                async:false,
                success:function(text){
                    if(text == '"success"'){
                    	count = 1;
                    }
                }
          	});
          	if(count == 1){
                 alert("会议记录已经保存!");
                 return;
            }
      		var mydata=saveFile();
           	$.ajax({
                url: "saveMinutesOfTheMeeting.action?1=1&qtype=bigtext&mflag=0",
                data: {annexcontent: mydata,otherid: otherid,status:status,tempid: tempid,curyear:curyear,batch:batch},
                type:"POST",
                cache: false,
                success: function (text) {
                		if("error" == text){
	                		alert("操作失败！");
	                	}else if("have" == text){
	                		alert("已经提交过了，请务重复提交！");
	                	}else{
	                		alert("保存成功！");
	                		close();
	                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                  alert("操作失败！");
                }
            });
      	}
      	
      	function sealLogin(){
      		var aipObj = document.all.HWPostil1;
      		if(aipLogin(aipObj)==0) {
      			if(!aipObj.IsLogin()){
      				aipLogin(aipObj);
      			}
  			}else if(aipLogin(aipObj)==-200){
      			alert("电子签章未连接！");
  			}	
      	}
    </script>
 </body>
</html>