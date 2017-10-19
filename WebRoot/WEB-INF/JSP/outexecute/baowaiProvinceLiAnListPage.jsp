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
		<title>省局立案</title>
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
	 <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
	 	<input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
	 	<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
     	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
           <table >
               <tr>
                <td style="width:100%;">
                	案件号：【（<input id="curyear" name="curyear"  class="mini-textbox"  value="${curyear}" 
                		style="width:40px;text-align:center"/>）${shortname} ${casetype }
                		第 <input id="casenumber" name="casenumber"  class="mini-textbox"  value="${casenumber}" style="width:40px;text-align:center"/> 号】
                </td>
                <td style="white-space:nowrap;">
                    <input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择监狱"
                            showNullItem="true" nullItemText="--全部--" url="getJailList.action" style="width:150px;" onvaluechanged="onjianyuchanged"/>
                    <a class="mini-button" iconCls="icon-ok"  plain="true" onclick="batchLian()">批量立案</a> 
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>       
                </td>
               </tr>
		</table>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    </div>
    <div class="mini-fit" id="div_two">
		<div id="datagrid1" class="mini-datagrid" style="width:100%;height: 100%" allowResize="false" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20" 
        	 url="getProvinceOutPrisonLiAnListData.action?1=1&flowdefid=${flowdefid }"   multiSelect="true"  >
	        <div property="columns">
	            <div type="checkcolumn" ></div>
	            <!--     
	           	 <div field="changetype" width="120" headerAlign="center"  align="center" allowSort="true">案件类型</div>
	            -->
              	<div field="jailname" width="120" headerAlign="center"  align="center" allowSort="true">监狱名称</div>    
            	<div field="crimid" width="90" headerAlign="center"  align="center"  allowSort="true" >罪犯编号</div> 
	            <div field="name" width="90" headerAlign="center"  align="center"  allowSort="true">罪犯姓名</div>   
	            <div field="age" width="60" headerAlign="center"  align="center"  allowSort="true">年龄</div> 
	            <div field="causeaction" width="110" headerAlign="center"  align="center"  allowSort="true">罪名</div>
	            <div field="sentencestime" width="110" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">刑期起日</div> 
	            <div field="courtchangeto" width="110" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">刑期止日</div> 
	            <div field="inprisondate" width="100" headerAlign="center"  align="center"  allowSort="true" renderer="onDateRenderer">入监时间</div>                     
	            <div field="onActionRenderer" width="60" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">申报</div>  
			</div>
		</div>
	</div>
	<script type="text/javascript">
		 mini.parse();
		 var datagrid = mini.get("datagrid1");
		 datagrid.load();
		  
        function onActionRenderer(e) {
            var s ='';
	        s = document.getElementById('middlebtns').value;
	     	return s;
        }
        
        function onjianyuchanged(){
        	var jailid = mini.get("jailid").getValue();
			datagrid.load({jailid:jailid});
        }
        
       //动态显示案件号
        function addAnjianhao() {
       		var curyear = mini.get("curyear").getValue();
       		var flowdefid = mini.get("flowdefid").getValue();
       		$.ajax({
		         url: "/pmsys/flow/getLastCaseNum.json", 
		         data: {curyear:curyear, flowdefid:flowdefid},
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
       
       
       //省局立案
        function provinceLian(){
        	var row = datagrid.getSelected();
			if(row){
				var id = row.crimid;
				var flowdefid = mini.get("flowdefid").getValue();
				//立案前先判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回罪犯姓名，否则否回0
				var result="";
				$.ajax({
			         url: "<%=path%>/flow/isHaveCaseLiAn.action", 
			         data: {flowdefid:flowdefid,id:id},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			         	var result = eval(text);
			         }
				});
				if(result != 0){
					alert("罪犯"+result + "已立案，请刷新页面！");
					return;
				}
				
				var mess = "确定对服刑人员【"+ row.name +"】进行立案？";
				if(confirm(mess)){
            		var applyid = row.crimid;
					var applyname = row.name;
					var orgid = row.jailid;
					var casenumber = mini.get("casenumber").getValue();//案件号
            		var curyear = mini.get("curyear").getValue();//案件号年度
					var resid = '10961'; //流程的开始资源节点
					var operateType = 'save';
					var tempid = mini.get("tempid").getValue();
					var casetype = row.changetype;//根据监狱提交案件类型，确定省局立案的案件类型
					$.ajax({
				         url: "<%=path%>/flow/provinceLiAn.action",
				         data: {resid:resid,operateType:operateType, orgid:orgid, casetype:casetype, commutetype:'2',shortname:'${shortname}',
				         flowdefid:flowdefid,tempid:tempid,applyid:applyid,applyname:applyname, curyear:curyear ,casenumber:casenumber},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var obj = eval(text);
				         	if(obj=='-1') {
				         		alert("操作失败!");
				         	}else{
				         		alert("操作成功!");
				         		addAnjianhao();
				         	}
				         	datagrid.reload();
				         }
					});
            	}else{
            		datagrid.reload();
            	}
			}else{
				alert(confirmMessage);
			}
        }
       
		
		//批量立案
        function batchLian(){
        	var rows = datagrid.getSelecteds();
            if (rows.length>0) {
            	if(confirm(batchAlertOne)){     
            		var flowdefid = mini.get("flowdefid").getValue();
            		var idArr = [];
            		var dataArr = [];
            		var caseTypes = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                    	var r = rows[i];
                    	idArr.push(r.crimid);//罪犯编号集合 
                    	caseTypes.push(r.changetype);//案件类型集合
                        dataArr.push(r.crimid+"@"+r.name+"@"+r.jailid+"@2");
                    }
                    var id = idArr.join(',');
                    var dataStr = dataArr.join(',');
                    var caseTypesStr = caseTypes.join(',');
            		//立案前先判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回罪犯姓名，否则否回0
					var result="";
					$.ajax({
				         url: "<%=path%>/flow/isHaveCaseLiAn.action", 
				         data: {flowdefid:flowdefid,id:id},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var result = eval(text);
				         }
					});
					if(result != 0){
						alert("罪犯"+result + "已立案，请刷新页面！");
						return;
					}	

                    var resid = '10118_01';
					var operateType = 'save';
					var tempid = mini.get("tempid").getValue();
					var curyear = mini.get("curyear").getValue();
				    var casenumber = mini.get("casenumber").getValue();
				    var casetype = '${casetype }';
                     $.ajax({
		                 url: "<%=path%>/flow/provinceBatchLiAn.action",
				         data: {caseTypes:caseTypesStr,resid:resid,operateType:operateType,curyear:curyear, casetype:casetype,casenumber:casenumber,
				         flowdefid:flowdefid,tempid:tempid,dataStr:dataStr,shortname:'${shortname}'},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         cache: false,
		                 success: function (text) {
		                 	var obj = eval(text);
		                 	if(obj=='-1'){
		                 		alert("操作失败!");
		                 	}else{
			         			alert("操作成功!");
			         			var b = addAnjianhao();//案件号加n
			         		}
		                	datagrid.reload();
		                 },
		                 error: function (jqXHR, textStatus, errorThrown) {
		                   alert("操作失败!");
		                   datagrid.reload();
		                 }
		            });
            	}else{
            		datagrid.reload();
            	}
            }else{
            	alert(confirmMessages);
            }
        }
		
		
		//快速查询
        function search() {
        	var jailid = mini.get("jailid").getValue();
            var key = mini.get("key").getValue();
            datagrid.load({ key: key, jailid:jailid });
        }
        function onKeyEnter(e) {
            search();
        } 
 </script>
</body>
</html>
