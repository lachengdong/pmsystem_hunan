<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>接收检察院文书</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
     <script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
	    .s{
	    	width:100%;height:100%;_height:94.8%;
	    }
    </style>
    <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
	</script>
     <script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var batchAlertOne = "您要对以下所选服刑人员进行立案吗?";
     </script>
</head>
<body onload="init('${menuid}');">
		<input name="xmlpath" id="xmlpath" class="mini-hidden" value="${xmlpath}"/>
		<input name="flowdefid" id="flowdefid" class="mini-hidden" value="${flowdefid}"/>
		<input name="tempid" id="tempid" class="mini-hidden" value="${tempid}"/>
		<input name="menuid" id="menuid" class="mini-hidden" value="${menuid}"/>
		<input name="courttype" id="courttype" class="mini-hidden" value="${courttype}"/>
		<input name="ischeckseal" id="ischeckseal" class="mini-hidden" value="${ischeckseal}"/>
		<input id="initPageIndex" class="mini-hidden" value="${initPageIndex}"/>
		<input id="initPageSize" class="mini-hidden" value="${initPageSize}"/>
		<div id="div_one" class="s"> 
        <div class="mini-toolbar" style="border-bottom: 0px;padding:0px;">
        <jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
		               (<input id="curyear" name="curyear"  class="mini-textbox"  value="${curyear}" style="width:40px;text-align:center"/>)
		               <input id="shortname" name="shortname"  class="mini-textbox"  value="${shortname}" style="width:45px;text-align:center"/>
		               <select id="casetype" name="casetype" style="vertical-align: middle;">
						<option value="tqjx">
							减刑
						</option>
						<option value="tqjs">
							假释
						</option>
						<option value="zyjwzx">
							暂予监外执行
						</option>
						</select>
				  <input id="picibianhao" class="mini-combobox" valueField="picibianhao" textField="pici"  emptyText="批次"
		                      showNullItem="true" nullItemText="--全部--" url="getPiciSlecter.action?curyear=${curyear}" style="width:80px;" />
                      <select id="stage" name="stage" style="vertical-align: middle;">
						<option value="B_JS">
							回函
						</option>
						<option value="D_JS">
							检察意见书
						</option>
						<option value="F_JS">
							书面意见
						</option>
					</select>    
                   </td>
                   <td style="white-space:nowrap;">
                    
		               <a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
					 <a class="mini-button"  plain="true" onclick="jiazai()">接收并加载文件</a>
	                   <a class="mini-button"  plain="true" onclick="jieshou()">确认接收</a>
                    </td>
                </tr>
            </table>           
        </div>
    <div class="mini-fit" id="div_two">
    <div id="datagrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,30,50,100]"
       url="getJcyWenShuList.action?1=1&flowdefid=${flowdefid}"  
       idField="spid" multiSelect="true" pageSize="20" allowAlternating="true"  virtualScroll="true" onselectionchanged="selectionChanged">
        <div property="columns">
            <div type="checkcolumn" width="10px;"></div>
            <div field="CASENUMBER" width="40px;" headerAlign="center"  align="center" allowSort="true">案件号</div> 
            <div field="XINGMING" width="30px;" headerAlign="center"  align="center" allowSort="false" >姓名</div>  
            <div field="AGE" width="15px;" headerAlign="center"  align="center" allowSort="false"  >年龄</div>
            <div field="ZUIMING" width="50px;" headerAlign="center"  align="center" allowSort="false">罪名</div> 
            <div field="PINGSHENHUIYIJIAN" width="80px;" headerAlign="center"  align="center" allowSort="false">提请意见</div> 
        </div>
    </div>
  </div>
</div> 
  
  <div style="height: 0px;">
		<table>
			<tr>
				<td height="100%">
					<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
				</td>
			</tr>
		</table>		
	</div>
	
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("caseno", "asc");
        grid.on("drawcell",function(e){
     	});
     	//缓存当前页数，当点击弹出办案页面办理完毕后，点关闭依然在当前页面。
     	var initPageIndex = mini.get("initPageIndex").getValue();
        var initPageSize = mini.get("initPageSize").getValue();
        grid.gotoPage(parseInt(initPageIndex),parseInt(initPageSize));
     	
        function search() {
        	var stage = document.getElementById("stage").value;	
     		var tempid="";
     		if(stage=="F_JS"){
     			tempid="jcy_tzs";
     		}else if(stage=="D_JS"){
     			tempid="jcy_jys";
     		}else{
     			tempid="jcy_hh";
     		}
 	       var curyear = mini.get("curyear").getValue();
 	       var casetype = document.getElementById("casetype").value;
 	       var picibianhao = document.getElementById("picibianhao").value;
 	       grid.load({curyear : curyear,  casetype : casetype, picibianhao:picibianhao,tempid:tempid});
       }
        
		//日期格式转化
		 function onDateRenderer(e){
		 	var value = e.value;
            if (value) return mini.formatDate(value, 'yyyy-MM-dd');
            return "";
		 }
        function onKeyEnter(e) {
            search();
        }
       function jiazai(){
    	   if (confirm("请确定好要操作的按键类型、批次和文书类别？")) {
	    	   var picibianhao = mini.get("picibianhao").getValue();
	     	   var stage = document.getElementById("stage").value;
	     	   var casetype = document.getElementById("casetype").value;
	    	   $.ajax({
			         url: "<%=path%>/pmisws/getCaseDataJcy.action?1=1",
			         data: {picibianhao:picibianhao,stage:stage, casetype:casetype},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			        	 if(text){
			         	var obj = eval(text);
			         			alert("操作成功");
			         			grid.reload();
			         		}else{
			         		alert("操作失败");
			         	}
			         }
		     	}); 
    	   }
    	   search();
       } 
 		//接收
      function jieshou(){
 		var xmlpath = mini.get("xmlpath").getValue();
      	var rows = grid.getSelecteds();
      	if (rows.length > 0) {
            if (confirm("请确定好要操作的按键类型、批次和文书类别？")) {
            	var ids = [];
            	 for (var i = 0, l = rows.length; i < l; i++){
                    var row = rows[i];
					//华宇发回来文件所在的位子
					var otherid = row.OTHERID;
					var batchid = row.PICIBIANHAO;
					var path=row.PATH;
					//获取aip控件,加载pdf文件
					var aipObj=document.getElementById("HWPostil1");	
		        	aipObj.CloseDoc(0);
		        	if(path!=null){
 		           	    aipObj.LoadFile(path);//打开模板文件
		        	}
 		            var docconent = saveFile();
   		            //保存方法
   		         	$.ajax({
	   			         url: "<%=path%>/pmisws/saveJcyHuihan.action?1=1",
	   			         data: {docconent:docconent,otherid:otherid, path:path},
	   			         type: "POST",
	   			         dataType:"json",
	   			         async:false,
	   			         success: function (text){
	   			        	 if(text){
	   			         	     var obj = eval(text);
	   			         		 alert("操作成功");
	   			         		 grid.reload();
	   			         	 }else{
	   			         		alert("操作失败");
	   			         	 }
	   			         },
	   			         error:function(text){
	   			        	alert(eval(text));
	   			         }
   			     	}); 
                 }
            }
        }
      }
      function selectionChanged(e){
			var row = grid.getSelecteds();
			if(row.length>30){
				alert("选中条数不能多余30条，请重新选择！");
				grid.clearSelect(true);
				return;
			}
		}
  </script>
</body>
</html>