<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>罪犯信息快速检索</title>
		<meta http-equiv="Content-Type" content="no-cache" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body {
				margin: 0;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div class="mini-toolbar" style="padding: 0px; border-bottom: 0px;">
			<input id="keyvalue" name="keyvalue" class="mini-hidden"  value="${key}"/>
			<input id="provincecode" name="provincecode" class="mini-hidden"  value="${provincecode}"/>
			<table>
				<tr>
					<td style="width: 100%;"></td>
					<td style="white-space: nowrap;">
						<c:if test="${search eq 1}">
						<input name="sdid" id="sdid" class="mini-combobox" style="width:110px;" valueField="ORGID" textField="NAME" required="false" 
						url="getDepartList.action?1=1&qtype=jianqu" disabled="true" emptyText="请选择监区...." nullItemText="请选择监区...." showNullItem="true"/>
						<input class="mini-textbox" id="key" class="mini-textbox" emptyText="罪犯编号、姓名、拼音" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>
						</c:if>
						<a class="mini-button" plain="true" style="width:1px;" plain="true" ></a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit" id="div_two">
			<div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;" url="" pageSize="20">
				<div property="columns">
					<div type="indexcolumn" width="20" headerAlign="center" align="center" allowSort="true">序号</div>
					<div field="crimid" width="55" headerAlign="center" align="center" allowSort="true" renderer="onActionRendererOne">罪犯编号</div>
					<div field="name" width="45" headerAlign="center" align="center" allowSort="true">姓名</div>
					<div field="gender" width="20" headerAlign="center" align="center" allowSort="true">性别</div>
					<div field="age" width="20" headerAlign="center" align="center" allowSort="true">年龄</div>
					<div field="maincase" width="70" headerAlign="center" align="center" allowSort="true">案由</div>
					<div field="registeraddressdetail" width="120" headerAlign="center" align="left" allowSort="true">籍贯</div>
					<div field="detainstatus" width="40" headerAlign="center" align="center" allowSort="true" renderer="onStatusRenderer">状态</div>
					<div field="orgname" width="40" headerAlign="center" align="center" allowSort="true">所在监区</div>
					<div field="" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
    mini.parse();
    var grid=mini.get("datagrid1");
    var keyvalue=mini.get("keyvalue").getValue();
   	keyvalue=encodeURI(keyvalue);
   	var url="getFastCrimid.action?1=1&sdid=${sdid}&zaiyacombo1=${zaiyacombo1}&key="+keyvalue;
   	grid.setUrl(url);
    grid.load();
    
   	function onActionRendererOne(e) {
			var grid = e.sender;
			var record = e.record;
			var uid = record._uid;
			var rowIndex = e.rowIndex;
			var s = ' <a class="Edit_Button" href="javascript:viewinfo('+record.crimid+');" >' + record.crimid + '</a>&nbsp;&nbsp;';
			return s;
		}

	function onActionRenderer(e){
    	var reder=e.record;
    	var s='<a class="Edit_Button" href="javascript:viewinfo('+reder.crimid+');" >基本信息</a>';
    	return s;
    }
    //查看基本信息
    function viewinfo(e){
    	var provincecode = mini.get('provincecode').getValue();
    	///if('1300'==provincecode || '4500'==provincecode||'6100'==provincecode){
    	toCriminalBaseInfo();
    	///}else{
    		///FatherTabs('551323', 0,e);
    	///}
    }
    ////////////////////
    //标准方法接口定义
    function SetData(data) {
    
    }
   
   
   //根据罪犯编号跳转到基本信息表单页面
	function toCriminalBaseInfo(){
		var row = grid.getSelected();
		var crimid = '';
		if(row.crimid){
			crimid = row.crimid;
		}else if(row.applyid){
			crimid = row.applyid;
		}
		var ids = crimid;
		var url = '<%=path%>'+"/basicInfo/basicInformation.page?1=1&crimid="+ids+"&fathermenuid=10191&flowdefid=doc_zfrjdjsp";
		var win = mini.open( {
			url : url,
			title : "基本信息",
			width : 900,
			height : 550,
			showMaxButton : true,
			allowResize : false,
			onload : function() {
				
			},
			ondestroy : function(action) {
				//grid.reload();
			}
		});
		win.max();
	}
	
   //对于案件的打开页面
   //lock：就否判断加销  	noOperate：打开页面后不充许操作
   function FatherTabs(menuid, lock, applyid,noOperate){
   		var rows = grid.getSelecteds();
        if (rows.length > 0){
        	var temp_flowdraftid = rows[0].flowdraftid;
        	if(temp_flowdraftid){
        		var idArr = [];
	       		var flowdraftidArr = [];
	            for (var i = 0, l = rows.length; i < l; i++){
	               var row = rows[i];
	               if(row.menuid){
	               		menuid = row.menuid;
	               }
	               applyid = row.crimid;
	               idArr.push(row.flowdraftid+'@'+menuid);
	               flowdraftidArr.push(row.flowdraftid);
	            }
				var ids = idArr.join(',');
				var flowdraftids = flowdraftidArr.join(',');
				if(lock==1){
					//查询被加锁的案件是否属于当前的用户
		            var returnValue = ajaxReturnLockUser(flowdraftids);
		       		if(returnValue==false){
		       			grid.reload();
		       			return;
		       		}
		       		
			       	//相关案件加锁
			       	returnValue = lockCaseOfThisUser(flowdraftids);
			       	if(returnValue==false){
			       		grid.reload();
			       		return;
			       	}
				}
		       	//用于返回到主页面的url
		       	var furl = encodeURIComponent(location.href);
	            var url = "toFatherTabPage.action?1=1&ids="+ids+"&indexFlag="+0+"&noOperate="+noOperate+"&furl="+furl;
	            self.location.href=url;
        	}else{
        		var idArr = [];
	            for (var i = 0, l = rows.length; i < l; i++){
	               var row = rows[i];
	               idArr.push(row.crimid+'@'+menuid);
	            }
				var ids = idArr.join(',');
				
		       	//用于返回到主页面的url
		       	var furl = encodeURIComponent(location.href);
	            var url = "toFatherTabPage.action?1=1&ids="+ids+"&optype=add"+"&indexFlag="+0+"&noOperate="+noOperate+"&furl="+furl;
	            self.location.href=url;
        	}
       		
        }else {
            alert( "请至少选中一条记录！");
        }
   }

	function onKeyEnter(e) {
       search();
    };
    function search() {
    	var key = mini.get("key").getValue();
	    var sdid = mini.get("sdid").getValue();
	    key=encodeURI(key);
       	var url="getFastCrimid.action?1=1&zaiyacombo1=${zaiyacombo1}&key="+key+"&sdid="+sdid;
       	grid.setUrl(url);
        grid.load();
	}
    </script>
	</body>
</html>