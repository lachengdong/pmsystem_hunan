<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>保外立案</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    .s{
    	width:100%;height:100%;_height:94.8%;
    }    
    </style>
     <script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var batchAlertOne = "您要对以下所选服刑人员进行保外立案吗?";
     	var batchAlertTwo = "您要对以下所选服刑人员进行保外立案吗?";
     </script>
</head>
<body>
	<!-- 隐藏四字段，已经存在的不写 创建人和更新人 更新时间 -->     
    <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
    <input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
    <input id="provincecode" name="provincecode" class="mini-hidden" value="${provincecode}"/>
    <input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
    <div id="div_one" class="s">
        <div class="mini-toolbar" style="padding:0px;border-bottom:0;">
        	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                		(${curyear}年)
        				案件号：（<input id="curyear" name="curyear" class="mini-textbox" value="${curyear}" style="width:40px;"/>）
        				${shortname} 刑收监字 第 
        				<input id="casenumber" name="casenumber" class="mini-textbox" value="${casenumber }" style="width:40px;text-align:center"/> 号
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:125px;" onenter="onKeyEnter"/>   
                        <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">查询</a>
                        <span class="separator"></span> 
                    	<span id="jxdiv">
                        	<a class="mini-button" id="10109_01" iconCls="icon-ok"  plain="true" onclick="batchCommute('2')">批量立案</a>
                        <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10109')"></a>
                        </span>
                    </td>
                </tr>
            </table>  
        </div>
   		<div class="mini-fit" id="div_two">
			<div id="datagrid1" class="mini-datagrid" style="width:100%;height: 100%" allowResize="false"  allowAlternating="true" sizeList="[10,20,50,100]" 
			pageSize="20"  url="getJailOutPrisonLiAnData123.action?1=1&flowdefid=${flowdefid}&status=${status}"    multiSelect="true"  >
		        <div property="columns">
		            <div type="checkcolumn" ></div>   
		            <div field="crimid" width="90" headerAlign="center"  align="center"  allowSort="true" >罪犯编号</div> 
		            <div field="name" width="90" headerAlign="center"  align="center"  allowSort="true">罪犯姓名</div>   
		            <div field="age" width="50" headerAlign="center"  align="center"  allowSort="true">年龄</div> 
		            <div field="causeaction" width="110" headerAlign="center"  align="center"  allowSort="true">罪名</div>
		            <div field="sentencestime" width="80" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">刑期起日</div> 
		            <div field="sentenceetime" width="80"  headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">刑期止日</div> 
		            <div field="inprisondate" width="80"  headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">入监时间</div>                     
		            <div field="" width="110" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">申报</div>  
				</div>
			</div>
		</div>
 	</div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid", "desc");
      	grid.load();
        
       var provincecode = mini.get('provincecode').getValue();
        function onActionRenderer(e) {
            var record = e.record;
            var crimid = record.crimid;
			var s = "";
			s += " <a class=\"Edit_Button\" id=\"10953\" href=\"javascript:commuteLiAn123('2');\" >立案</a>&nbsp;&nbsp;";
			s += " <a class=\"Edit_Button\" id=\"10089_02\" href=\"javascript:checkInfo(\'"+  crimid + "\');\" >查看信息</a>&nbsp;&nbsp;";
            return s;
        }
        
        //动态显示案件号
        function addAnjianhao(commutetype) {
        		var curyear = mini.get("curyear").getValue();
        		var flowdefid = mini.get("flowdefid").getValue();
        		$.ajax({
			         url: "<%=path%>/flow/getMaxCaseNum.action", 
			         data: {curyear:curyear, flowdefid:flowdefid, commutetype:commutetype},
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
        
        //立案
        function commuteLiAn123(commutetype){
        	
        	var row = grid.getSelected();
			if(row){
				var id = row.crimid;
				var flowdefid = mini.get("flowdefid").getValue();
				
				//立案前先判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回罪犯姓名，否则否回0
				$.ajax({
			         url: "<%=path%>/flow/isHaveCaseLiAn.action", 
			         data: {flowdefid:flowdefid,id:id},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			         	var result = eval(text);
			         	if(result != 0){
							alert("罪犯"+result + "已立案，请刷新页面！");
							return;
						}
			         }
				});
				var mess = "确定对服刑人员【"+ row.name +"】进行立案？";
				if(confirm(mess)){
					var applyid = row.crimid;
					var applyname = row.name;
					var orgid = row.orgid;
					var curyear = mini.get("curyear").getValue();
					//var casenumber = mini.get("casenumber").getValue();
					var resid = '10109_02'; //流程的开始资源节点
					var operateType = 'save';
					var tempid = mini.get("tempid").getValue();
					
					//var data = getNoteMap();//表单所有节点及值
					//var docconent = saveFile();//获取大字段
					$.ajax({
				         url: "<%=path%>/baoWaiJailLiAn123.action", 
				         data: {resid:resid,operateType:operateType, orgid:orgid, commutetype:commutetype,shortname:'${shortname}',
				         flowdefid:flowdefid,tempid:tempid,applyid:applyid,applyname:applyname, curyear:curyear},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var obj = eval(text);
				         	if(obj=='-1') {
				         		alert("操作失败!");
				         	}else{
				         		alert("操作成功!");
				         		//保外立案成功以后，获取最大案件号放到前台页面 
				         		addAnjianhao(commutetype);
				         		if(provincecode && provincecode=='6400'){
				         			//立案成功以后修改，在押状态修改为保外
				         			updateCriminalDetainStatus(applyid);
				         		}
				         	}
				         	grid.reload();
				         }
					});
            	}else{
            		//grid.reload();
            	}
			}else{
				alert(confirmMessage);
			}
        }
		
        //当立案成功以后，把罪犯的在押状态修改为保外即可
        function updateCriminalDetainStatus(crimids){
            $.ajax({
                 type:'post',
                 url:'<%=path%>/flow/updateCriminalDetainStatus.action?1=1&crimids='+crimids,
                 success:function (text){
                	 grid.reload();
                 }
            });
        }
		
		 //批量立案
        function batchCommute(commutetype){
        	var rows = grid.getSelecteds();
            if (rows.length>0) { 
            	if(confirm(batchAlertOne)){  
            		var flowdefid = mini.get("flowdefid").getValue();
            		var idArr = [];
            		var dataArr = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                    	var r = rows[i];
                    	idArr.push(r.crimid);
                        dataArr.push(r.crimid+"@"+r.name+"@"+r.orgid);
                    }
                    var id = idArr.join(',');
                    var dataStr = dataArr.join(',');
                    
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
            		
            			   	
                    var resid = '10109_01';
					var operateType = 'save';
					var tempid = mini.get("tempid").getValue();
					var curyear = mini.get("curyear").getValue();
				    //var casenumber = mini.get("casenumber").getValue();
                     $.ajax({
		                 url: "<%=path%>/baoWaiJailBatchLiAn.action",
				         data: {resid:resid,operateType:operateType,curyear:curyear, commutetype:commutetype,
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
			         			var b = addAnjianhao(commutetype);
			         			if(provincecode && provincecode=='6400'){
			         				updateCriminalDetainStatus(id);
			         			}
			         		}
		                	grid.reload();
		                 },
		                 error: function (jqXHR, textStatus, errorThrown) {
		                   alert("操作失败!");
		                   grid.reload();
		                 }
		            });
            	}else{
            		grid.reload();
            	}
            }else{
            	alert(confirmMessages);
            }
        }
        
		//查看罪犯详细信息
		function checkInfo() {
			var row = grid.getSelected();
			var crimid = row.crimid;
            if (row) { 
                mini.open({
                	  url:"<%=path%>/basicInfo/basicInformation.page?1=1&crimid="+crimid+"&closetype="+3,
                	   title: "罪犯信息", width: 900, height: 550,
                    showMaxButton: true,
                    allowResize: false, 
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { mymenuid : "8328" , myoperatetype:'view'};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
                
            } else {
                alert("请选中一条记录");
            }
		}

		//快速查询
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key});
        }
        function onKeyEnter(e) {
            search();
        }
    </script>
</body>
</html>