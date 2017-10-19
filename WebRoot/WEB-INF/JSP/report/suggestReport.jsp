
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	Object mydata = request.getAttribute("mydata");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>建议书预览</title>
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
	<input id="otherid" value="${otherid}" type="hidden"/>
	<input id="tempid" value="${tempid}" type="hidden"/>
	<input id="status" value="${status}" type="hidden"/>
	<input id="curyear" value="" type="hidden"/>
	<input id="batch" value="" type="hidden"/>
	<input id="flowdraftid" value="${flowdraftid}" type="hidden"/>
	<input id="flowid" value="${flowid}" type="hidden"/>
	<input id="doctype" value="${doctype}" type="hidden"/>
	<div style="width:100%;height:100%;">
		<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;"> 
			<a class="mini-button" id="12794"  iconCls="icon-save" plain="true" onclick="save('save');">存盘</a>
			<a class="mini-button"  iconCls="icon-close" plain="true" onclick="close();">关闭</a>
			<span class="separator"></span> 
	    	<a class="mini-button"   id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"   id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"   id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"   id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"   id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"   id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"   id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"   id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
	    	<a class="mini-button"   id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
	    	<a class="mini-button"   id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
	    </div>
  		<div class="mini-fit" style="width:100%; height:100%;">
  			<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
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
       	
        //标准方法接口定义
        function SetData(data){
             //跨页面传递的数据对象，克隆后才可以安全使用
             data = mini.clone(data);
             var otherid = data.otherid;
             var tempid = data.tempid;
             var curyear = data.curyear;
             var batch = data.batch;
             var doctype = data.doctype;
             $("#status").val(status);
             $("#tempid").val(tempid);
             $("#otherid").val(otherid);
             $("#flowdraftid").val(data.flowdraftid);
             $("#flowid").val(data.flowid);
             $("#curyear").val(curyear);
             $("#batch").val(batch);
             $("#doctype").val(doctype);
             //加载报表
             //myonload();(不能写到 setData里面执行)
        }
        function myonload(){
        	var reportdate = $("#mydata").val();//+data1;
        	//alert(reportdate);
        	//alert(document.all("MyViewer"));
			document.all("MyViewer").ShowProgress = false;
			document.all.HWPostil1.BeforeConvert("");
			document.all("RMVIEWER_DATA").value=reportdate;
			document.all("MyViewer").GetReportData(reportdate);
			document.all("MyViewer").ShowPrintDialog = false;
			document.all("MyViewer").PrintReport();
			document.all.HWPostil1.WaitConverting(4000);
			document.all.HWPostil1.AfterConvert();
		}
		
      	function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        } 
      	function close() {
      		CloseWindow("cancel");
      	}
      	function save(type){
      	    var otherid = $("#otherid").val();
      	    var tempid = $("#tempid").val();
      	    var flowid = $("#flowid").val();
      	    var flowdraftid = $("#flowdraftid").val();
      	    var curyear = $("#curyear").val();
      	    var batch = $("#batch").val();
      	    var status = "bigtext";
      	    var doctype = $("#doctype").val();
      	    var count = 0;
            //判断 当前 会议记录 是否 已经 保存 了大字段 
            var mydata=saveFile();
           	tempid = tempid+"report";
           	$.ajax({
                url: "saveSuggestion.action",
                data: {bigdata: mydata, tempid:tempid, flowdraftid:flowdraftid, flowid:flowid},
                type:"POST",
                cache: false,
                success: function (text) {
                	var result = eval(text);
                	if(result=='1'){
                		alert("保存成功！");
                	}else{
                		alert("操作失败！");
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                      alert("操作失败！");
                }
            });
      	    
      	}
      	
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
