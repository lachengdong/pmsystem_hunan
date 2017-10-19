<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String anniu = request.getParameter("anniu");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
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
<body onload="myonload()">
    <input id="reportid" name="reportid" class="mini-hidden" value="<s:property value='#request.reportid'/>"/>
    <input id="smid" class="mini-hidden" />
    <input id="mydata" value="<s:property value='#request.mydata'/>" type="hidden"/>
    <input id="sysyear" value="<s:property value='#request.sysyear'/>" type="hidden"/>
    <input id="systime" value="<s:property value='#request.systime'/>" type="hidden"/>
    <input id="aipstr" value="<s:property value='#request.aipstr'/>" type="hidden"/>
<input id="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>

<input type="hidden" name="reportDate123" id="reportDate123" value=''/>
<div style="width:100%;height:100%;" id="div1">
		<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
	        <table  style="width:100%;">
				<tr>
					<td align="left" style="margin-left: 20px;">
						<a class="mini-button" id="dakai" iconCls="icon-lock" plain="true" onclick="dakai()">工具栏</a>     
	        			<a class="mini-button" id="saved" iconCls="icon-print" plain="true"  onclick="printdoc()">打印</a> 
					</td>
					<td align="right">
					    <s:if test="#request.anniu==3">
					    <a class="mini-button" id="saved" iconCls="icon-save" plain="true"  onclick="saveData()">保存</a> 
						年度：<select id="year" onchange="changeyear();">
							<option value="0">请选择</option>
				          		<%
				          			Object obj = (List<String>)request.getAttribute("listYear"); 
				          			if(obj!=null){
				          				List<String> list = (List<String>)obj;
					          			for(int i=0,n=list.size(); i<n; i++){
					          		%>
					          			<option value="<%=list.get(i)%>"><%=list.get(i)%></option>
					          		<%
					          			}
				          			}
				          		%>
				        </select>
				         批次：<select id="listtime">
							    <option value="0">请选择</option>
				        </select>
				          	<a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>
				        </s:if>
					</td>
				</tr>   
			</table>     
	    </div>
	<s:if test="#request.anniu==2">
		 <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">    
	          <a class="mini-button" id="saved" plain="true" onclick="choosecriminal();">选择罪犯</a>        
	    </div>
    </s:if>
  	<div class="mini-fit" style="width:100%; height:100%;">
       <div style="width:100%; height:100%;">
            <!--<script src="<%=path%>/scripts/loadaip.js"></script>-->
             <script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
       </div>
    </div>  
</div>
<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" pageSize="20"
         idField="spid" multiSelect="true"  allowAlternating="true" >
<script type="text/javascript">
	document.all("MyViewer").Init(window, document,600);
</script>
</div>
    <script type="text/javascript">
    //obj.LoadFileBase64
    	var object = document.getElementById("HWPostil1");
    	object.SetPageMode(2,150);
    	object.ShowToolBar = 0;//工具栏是否显示
    	object.ShowDefMenu = 0;//菜单栏
    	object.ShowScrollBarButton = 1;    	
       	mini.parse();
       	var grid = mini.get("datagrid1");
       	var listtimes = [[],[]];
        var reportid = mini.get("reportid").getValue();
          //标准方法接口定义
        function SetData(data) {
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = mini.clone(data);
        }
        function myonload(){
        	var aipstr = document.getElementById("aipstr").value;
        	var reportdate = document.getElementById("mydata").value;
        	if(aipstr){
        		document.getElementById("HWPostil1").LoadFileBase64(reportdate);
			}else{
				var aipObj=document.getElementById("HWPostil1");	
				document.all("MyViewer").ShowProgress = false;
				document.all.HWPostil1.BeforeConvert("");
				document.all("RMVIEWER_DATA").value=reportdate;
				document.all("MyViewer").GetReportData(reportdate);
				document.all("MyViewer").ShowPrintDialog = false;
				document.all("MyViewer").PrintReport();
				document.all.HWPostil1.WaitConverting(4000);
				document.all.HWPostil1.AfterConvert();
			}
			$.ajax({
		    	url: "ajaxGetTimeList.action?1=1",
		        type:"POST",
		        cache: false,
		        success: function (text) {
		        	listtimes = text;
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        }
		    });
		}					
      	function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        } 
      	function fanhui() {
      		CloseWindow("cancel");
      	}
      	function choosecriminal(){
      		var menuidobj = document.getElementById("menuid");
      		if(menuidobj){
      			var menuid = menuidobj.value;
				window.location='<%=path%>/getCriminalList.action?menuid='+menuid+'&singlerecord=report';
      		}
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
        
        function saveData() {
        	var mydata = document.getElementById("mydata").value;
        	var listyear = document.getElementById("year").value;
        	var listtime = document.getElementById("listtime").value;
        	if(listyear==0) {
            	listyear = document.getElementById("sysyear").value;
        	}
        	if(listtime==0) {
        	    listtime = document.getElementById("systime").value;	
        	}
        	if(!confirm("确定生成" + listyear + "年第" + listtime + "批次数据？")) {
        	    return;
        	}
        	$.ajax({
		    	url: "ajaxSaveReportDataFile.action?1=1",
		        data: {listyear : listyear,listtime : listtime, mydata: mydata, reportid:reportid},
		        type:"POST",
		        async:false,
		        cache: false,
		        success: function (text) {
		        	alert("操作成功！");
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        	alert("操作失败！");
		        }
		    });
        }
        
        function changeyear() {
			var sltYear=document.getElementById("year");
            var sltTime=document.getElementById("listtime");
			var listtimess = eval(listtimes);
        	var yearTime=listtimess[sltYear.selectedIndex - 1];
        	sltTime.length=1;
        	for(var i=0;i<yearTime.length;i++){
            	sltTime[i+1]=new Option(yearTime[i],yearTime[i]);
        	}
        }
        
        function search() {
			onreload();
			myonload();
        }
        
        function onreload() {
        	var value = "";
        	var listyear = document.getElementById("year").value;
        	var listtime = document.getElementById("listtime").value;
			$.ajax({
		    	url: "getReportDataFile.action?1=1",
		    	data: {listyear : listyear,listtime : listtime, reportid:reportid},
		        type:"POST",
		        async:false,
		        cache: false,
		        success: function (text) {
		        	value = text;
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        }
		    });
		    if(value==null || value=="") {
		    	alert("暂无相关数据");
		    } else {
		        document.getElementById("mydata").value = value;
		        document.getElementById("mydata").value = value;
		    }
        }
    </script>
 </body>
</html>