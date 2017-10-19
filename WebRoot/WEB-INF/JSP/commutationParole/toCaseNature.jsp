<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>案件类别</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }  
    </style>
</head>
<body onload="init('${menuid}');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
	    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	    <input id="provincecode" name="provincecode" class="mini-hidden" value="${provincecode}"/>
	    <input id="unitlevel" name="unitlevel" class="mini-hidden" value="${unitlevel}"/>
        <table >
             <tr>
                <td style="width:100%;">
					<a class="mini-button" style="display:none;" id="900043" plain="true" onclick="chooseAll(900042);">批量处理</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="番号/姓名/拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <c:if test="${unitlevel=='2'}">
	    	  		<input id="jailid" name="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择监狱"
                            showNullItem="true" nullItemText="--全部--" url="getJailList.action" style="width:150px;" onvaluechanged="search"/>
	    	  	   </c:if>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="<%=path %>/commutationParole/listCaseNature.json?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
       		<div property="columns">
           	    <div type="checkcolumn" width="40"></div>        
            	<div field="aliasno" width="80" headerAlign="center" align="center"  allowSort="true">番号</div>    
            	<div field="name" width="60" headerAlign="center" align="center"  allowSort="true">姓名</div>
            	<div field="brithday" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div> 
	            <div field="causeaction" width="60" headerAlign="center" align="center"  allowSort="true">罪名</div>
	            <div field="yuanpanxq" width="75" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
	            <div field="registeraddressdetail" width="60" headerAlign="center" align="center"  allowSort="true" >户籍地址</div> 
	            <div field="courtchangeto" width="60" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">现刑期止日</div> 
	            <div field="casenature" width="60" headerAlign="center" align="center"  allowSort="true" >案件分类</div> 
	            <div field="" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
           </div>
        </div>
        <!-- 案件分类-->
		<div id="editWindow" class="mini-window" title="案件分类" style="width:500px;height:300px"
		    showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="editform" class="form" style="min-height: 150px;" >
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" onclick="saveData('900042')" plain="true" iconCls="icon-save" style="width: 60px">存盘</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true" style="width: 60px;">取消</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 0px; padding-bottom: 0px;">
					<table style="table-layout: fixed;width:99%;">
						<tr>
							<td style="width:17%;">
							案件分类:  
							</td>
							<td style="width:81%;">  
								<input id="ptf" name="ptf" class="mini-radiobuttonlist" repeatItems="1" repeatLayout="table" 
								repeatDirection="vertical" textField="text" valueField="id"  data="types"  />
							</td>
						</tr>
						<tr>
						<td style="width:17%;">  
							&nbsp;
							</td>
							<td style="width:81%;">  
								<input type="hidden" id="crimid" />
								<div id="codeid" name="codeid" class="mini-checkboxlist" repeatItems="1" repeatLayout="table" 
								     textField="name" valueField="codeid" style="width:97%;" url="<%=path%>/syscode/ajax/listAll.json?1=1&codetype=GK1104&pcodeid=GK1104" >
							    </div>
							     
							</td>
						</tr>
					</table>
				</div>
		   </div>
		</div>
    </div>
    <script type="text/javascript">
    	var types = [{"id": "1", "text": "普通犯"},{ "id": "2", "text":"三类犯"}];
        mini.parse();
        var editWindow = mini.get("editWindow");
        var grid = mini.get("datagrid1");
        	grid.sortBy("crimid","desc");
        	grid.load();
        var nodeids = mini.get("nodeids").getValue();//权限返回的节点id
        	
        //如果idnumber存在表示已经处理,duty=-1表示审批未通过,显示处理。
         //如果duty=0存在表示已经处理,accent=1表示未进入流程审批中,显示处理。
         function onActionRenderer(e) {
        	 var s = '${middlestr}';
            return s;
        }
        
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
        	var jailid = mini.get("jailid");
        	if(jailid) jailid = jailid.getValue();
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key,jailid: jailid});
        }
        
 	//罪犯性质编辑
	function toCaseNature() {
	    var row = grid.getSelected();
	    var ptf = mini.get("ptf");
		mini.get("crimid").setValue(row.crimid);
	    if (row) { 
	        var form = new mini.Form("#editform");
	        form.clear();
	        var crimtype = row.crimtype;  
	        var index = crimtype.indexOf("三类犯");
	        var typevalue = 1;
	        if(index>=0){
	        	typevalue = 2;
	        }
	        editWindow.show();
	        ptf.setValue(typevalue);
	        $.ajax({
	            url: "<%=path%>/commutationParole/loadDataGrid.action?crimid=" + row.crimid,
	            success: function (text) {
	                form.unmask();
	                var o = mini.decode(text);
	                mini.get("codeid").setValue(o);
	            },
	            error: function () { 
	                form.unmask();
	            }
	        });
	    }
	}
	
	function cancelEditWindow() {
		var row = grid.getSelected();
		var ptf = mini.get("ptf");
	    document.getElementById("crimid").value=row.crimid;
	    if (row) { 
	        var form = new mini.Form("#editform");
	        form.clear();
	        var crimtype = row.crimtype;  
	        var index = crimtype.indexOf("三类犯");
	        var typevalue = 1;
	        if(index !='-1'){
	        	typevalue = 2;
	        }
	        ptf.setValue(typevalue);
	    }
		var  url="<%=path%>/syscode/ajax/listAll.json?1=1&codetype=GK1104&pcodeid=GK1104";
	    mini.get("codeid").setUrl(url);
		editWindow.hide();
		grid.reload();
	}
	function saveData(menuid){
		var codeid1 =  mini.get("codeid");
		var rows = codeid1.getSelecteds();
		var form = new mini.Form("#editform");
		var crimid = mini.get("crimid").getValue();
		var ptf = mini.get("ptf").getSelected().id;
		var ids = [];
		if(rows.length>0){
			for ( var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				ids.push(r.codeid);
			}
		}
		var id = ids.join(',');
		$.ajax( {
			url : "<%=path%>/commutationParole/saveData.action?1=1",
			data:{codeid:id,ptf:ptf,crimid:crimid,pcodeid:'GK1104',typeid:'10000'},
			success : function(text) {
				form.unmask();
				handling(menuid);//办案
			},
			error: function () {
              form.unmask();
           }
		});
		cancelEditWindow();//关闭罪犯性质弹框
	}
	//更新数据
	function updateData(rid){
		var form = new mini.Form("#editform");
		// 校验
		form.validate();
		if (form.isValid() == false){
			return;
		}
		var data = form.getData();
		 var json = mini.encode([data]);
		 $.ajax({
	           url: "/report/updateReportManage.action?1=1&rid="+rid,
	           data: { data: json},
	           cache: false,
	           type:"post",
	           success: function (text) {
	           		editWindow.hide();
	           		grid.reload();
	           },
	           error: function () {
	             // form.unmask();
	           }
	       });
		}
    </script>    
</body>  
</html>