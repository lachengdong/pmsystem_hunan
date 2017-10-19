<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> aipmap = (Map<String,String>)request.getAttribute("aipmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>省局保外立案</title>
		<script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var batchAlertOne = "您确定要对所选案件进行立案吗?";
     </script>
		
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }     
	    .s{
    		width:100%;height:100%;_height:94.8%;
    	}
    </style>
</head>
<body onload="init('${menuid}')">
	 <input name="flowdefid" id="flowdefid" class="mini-hidden" value="${flowdefid}"/>
     <input name="templetid" id="templetid" class="mini-hidden" value="${templetid}"/>
     <div id="div_one" class="s">
	 <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
           <table >
               <tr>
                <td style="width:100%;">
                	案件号：【（<input id="curyear" name="curyear"  class="mini-textbox"  value="${curyear}" 
                		style="width:40px;text-align:center"/>）${shortname} ${casetype}
                		第 <input id="casenumber" name="casenumber"  class="mini-textbox"  value="${casenumber}" style="width:40px;text-align:center"/> 号】
                </td>
                <td style="white-space:nowrap;">
                    <input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择监狱"
                            showNullItem="true" nullItemText="--全部--" url="getJailList.action" style="width:150px;" onvaluechanged="search"/>
                     ${topstr}
                     <!--  <a class="mini-button" iconCls="icon-ok"  plain="true" onclick="batchLian()">批量立案</a> -->
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>       
                </td>
               </tr>
		</table>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    </div>
    
    <div class="mini-fit" >
			<div id="datagrid1" class="mini-datagrid" style="width:100%;height: 100%" allowResize="false" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20" 
	        	 url="getProvinceOutPrisonLiAnListData.action?1=1&flowdefid=other_sjbwjysp"   multiSelect="true"  >
		        <div property="columns">
		            <div type="checkcolumn" ></div>   
	              	<div field="jailname" width="120" headerAlign="center"  align="center" allowSort="true">监狱名称</div>    
	            	<!--
	            		<div field="casenumber" width="120" headerAlign="center" align="center" allowSort="true">案件号</div>    
	            	-->
	            	<div field="crimid" width="90" headerAlign="center"  align="center"  allowSort="true" >罪犯编号</div> 
		            <div field="name" width="90" headerAlign="center"  align="center"  allowSort="true">罪犯姓名</div>
		            <div field="age" width="60" headerAlign="center"  align="center"  allowSort="true">年龄</div> 
		            <div field="causeaction" width="110" headerAlign="center"  align="center"  allowSort="true">罪名</div>
		            <div field="sentencestime" width="110" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">刑期起日</div> 
		            <div field="sentenceetime" width="110" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">刑期止日</div> 
		            <div field="inprisondate" width="100" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">入监时间</div>                     
		            <div field="" width="60" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">申报</div>  
				</div>
			</div>
		</div>
 	</div>
 
	 <script type="text/javascript">
		 mini.parse();
		 var datagrid = mini.get("datagrid1");
		 datagrid.load();
		 //datagrid.sortBy("optime", "desc");
		 //mini.get("caseacceptdate").setValue(new Date());//收案日期
		 // 渲染日期
		 function onDateRenderer(e) {
		 	if(e && e.value){
		 		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
		 	}
		     return "";
		 }
		  
        function onActionRenderer(e) {
            var s ='';
	        s = document.getElementById('middlebtns').value;
	     	return s;
        }
        
        function search(){
        	var jailid = mini.get("jailid").getValue();
			datagrid.load({jailid:jailid});
        }
        
       //动态显示案件号
        function addAnjianhao() {
        		var curyear = mini.get("curyear").getValue();
        		var flowdefid = mini.get("flowdefid").getValue();
        		var outexecutetype = 1;
        		$.ajax({
				         url: "/pmsys/ajaxGetMaxOutExecuteCaseNo.action?1=1", 
				         data: {curyear:curyear, flowdefid:flowdefid, outexecutetype:outexecutetype},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var a = eval(text);
				         	var b = parseInt(a);
				        	mini.get("casenumber").setValue(b);
				         }
				});
        }
       
		//批量立案
        function batchLian(){
        	var rows = datagrid.getSelecteds();
            if (rows.length>0) {
            	if(confirm(batchAlertOne)){     
            		var curyear = mini.get("curyear").getValue();
            		var flowdefid = mini.get("flowdefid").getValue();
            		var templetid = mini.get("templetid").getValue();
            		var idArr = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                    	var row = rows[i];
                    	idArr.push(row.crimid);
                    }
                    var ids = idArr.join(',');
                    var outexecutetype = '1';
				    var casetype = '保字';
				    var data = {ids:ids,curyear:curyear,flowdefid:flowdefid,templetid:templetid,outexecutetype:outexecutetype,casetype:casetype};
					var url = "<%=path%>/provinceOutExecuteLiAn.action?1=1";	   
                   //
                    $.ajax({
		                 url: url,
				         data: data,
				         type: "POST",
				         async:true,
				         cache: false,
		                 success: function (text) {
		                 	var data = eval("("+text+")");
		                 	if(data.status==1){
		                 		alert("操作成功!");
			         			addAnjianhao(outexecutetype);//案件号加n
		                 	}else{
		                 		alert("操作失败!");
		                 	}
		                	datagrid.reload();
		                 },
		                 error: function (jqXHR, textStatus, errorThrown) {
		                   alert("操作失败!");
		                   datagrid.reload();
		                 }
		            });
                   //
                   
            	}else{
            		datagrid.reload();
            	}
            }else{
            	alert(confirmMessages);
            }
        }
		
		
        function onKeyEnter(e) {
            search();
        }
        
        function onPageChanged() {
        	myloading();
        }
        function myloading(){
        	var myform = new mini.Form("datagrid");
       		myform.loading();
        }
      	function unmask(){
      		myunmask();
      	}
        function myunmask() {
        	var myform = new mini.Form("datagrid");
       		myform.unmask();
        }
        
	        
 </script>
</body>
</html>
