<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>罪犯减刑(假释)立案审批表</title>
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
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="输入编号、姓名、拼音、番号" style="width:130px;" onenter="onKeyEnter"/>   
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
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="getLiAnApproveList.action?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
       		<div property="columns">
           	    <div type="checkcolumn" width="20"></div>        
            	<div field="crimid" width="60" visible="false" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
            	<div field="aliasno" width="40" headerAlign="center" align="center"  allowSort="true">番号</div>    
            	<div field="name" width="40" headerAlign="center" align="center"  allowSort="true">姓名</div>
	            <div field="age" width="40" visible="false" headerAlign="center" align="center"  allowSort="true">年龄</div>
	            <div field="brithday" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div> 
	            <div field="originalyear" width="45" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
	            <div field="sentencestime" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div> 
	            <div field="sentenceetime" width="40" visible="false" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期止日</div> 
	            <div field="courtchangeto" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">现刑期止日</div> 
	            <div field="punishment" width="40" visible="false" headerAlign="center" align="center"  allowSort="true">裁判余刑</div>
	            <div field="nowpunishmentyear" width="40" headerAlign="center" align="center"  allowSort="true">现余刑</div>
	            <div field="predate" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">上次减刑日期</div>
	            <div field="jianggeqi" width="50" headerAlign="center" align="center"  allowSort="true">减刑间隔期</div> 
	            <div field="identity" width="50" headerAlign="center" align="center"  allowSort="true">案件审批状态</div> 
	            <div field="casenature" name="casenature" width="50" headerAlign="center" align="center" allowSort="true" >案件分类</div>    
	            <div field="" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
           </div>
        </div>
        <!-- 罪犯性质 -->
		<div id="editWindow" class="mini-window" title="案件分类" style="width:45%;height:60%"
		    showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="editform" class="form">
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 99%;">
						<tr>
							<td style="width: 100%;background-color:#cccccc;">
								<a class="mini-button" onclick="saveData('900042')" plain="true" iconCls="icon-save" style="width: 60px">存盘</a>
								<a class="mini-button" onclick="cancelEditWindow()" plain="true" iconCls="icon-close" style="width: 60px;">取消</a>
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
        	grid.sortBy("idnumber","asc");
        	grid.load();
        var nodeids = mini.get("nodeids").getValue();//权限返回的节点id
        	
        //如果idnumber存在表示已经处理,duty=-1表示审批未通过,显示处理。
         //如果duty=0存在表示已经处理,accent=1表示未进入流程审批中,显示处理。
         function onActionRenderer(e) {
	        var record = e.record;
	        var lastnodeid = record.duty;
	        var s = '';
	        //duty 节点ID
	        //accent 流程ID 保存0处理2未提交1
	        //idnumber 草稿ID
	        if(record.idnumber ){
	       		if(nodeids==lastnodeid){
	       			if(record.orgcontact=='1')
	       				s += ' <a class="Edit_Button"  href="javascript:chooseOne(900042);" >修改</a>';
	       			else
	       				s += ' <a class="Edit_Button"  href="javascript:chooseOne(900042);" >处理</a>';
	       		}else {
	       			s += ' <a class="Edit_Button"  href="javascript:chooseOne(900044);" >查看</a>';
	       		}
	       	}else{
	       		if(nodeids && nodeids!='0') s+='未上报';
	       		else s += ' <a class="Edit_Button"  href="javascript:chooseOne(900042);" >处理</a>';
	       	} 
            return s;
        }

 	    function chooseOne(menuid) {
        	var provincecode = mini.get("provincecode").getValue();  
        	var unitlevel = mini.get("unitlevel").getValue();
        	var roleidsObj = '${roleids}';
			if(provincecode=='3100' && menuid=='900042' &&
					(roleidsObj.indexOf(0)!=-1 || roleidsObj.indexOf(1)!=-1
					||roleidsObj.indexOf(3)!=-1 || roleidsObj.indexOf(4)!=-1)){//如果省份是上海部门是科室，须先填写罪犯的案件性质才能申报
				toCaseNature();
			}else{
				handling(menuid);
			}
        }
        
        
 	    //根据罪犯编号跳转到表单页面，如果寻找罪犯未处理或者审批未通过,没有流程idnumber,生成一条新数据。
        function handling(menuid) {
        	var row = grid.getSelected();
        	if(row){
        	    var url = "theCommutationParoleApplicationTabs.action?1=1&menuid="+menuid+
        		"&crimid="+row.crimid+"&tempid=SH_ZFJXJSLASPB"+"&flowdefid=${flowdefid}"+"&flowid="+row.registerarea+"&flowdraftid="+row.idnumber;
				self.location.href=url;	
        	}else{
        		alert(confirmMessage);
        	}
        }
        //批量处理
  		function chooseAll(menuid){
        	var rows = grid.getSelecteds();
        	var tempid = mini.get("tempid").getValue();
           	if(rows.length > 0){
           		var ids = [];
                   for (var i = 0, l = rows.length; i < l; i++) {
                       var r = rows[i];
                       if(r.idnumber){
                       		if(nodeids != r.duty && r.orgcontact != '1') continue;
                       		else
                       			if(r.orgcontact == '2') continue;
                       }else{
                       		if(nodeids!='0') continue;
                       }
                       //if((nodeids == lastnodeid)) continue;//审批中的罪犯不能进行批量处理
                       ids.push(r.crimid+"@"+r.idnumber+"@"+r.registerarea);
                   }
				   if(ids.length == '0'){//所选中的数据都是审批中的
				   		alert(noProcessing);
				   		return ;
				   }else {
				   		if (confirm(allProcessing)) {
				   			var id = ids.join(',');
                   			var url = "theCommutationParoleApplicationTabs.action?1=1&menuid="+menuid+"&id="+id+
                   			"&tempid=SH_ZFJXJSLASPB"+"&flowdefid=${flowdefid}";
                   			self.location.href=url;
				   		}
				   }
           	}else {
               	alert(confirmMessages);
           	}  
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
		mini.get("crimid").setValue(row.crimid);
	    if (row) {
	        var crimtype = row.crimtype;  
	        var index = crimtype.indexOf("三类犯");
	        var typevalue = 1;
	        if(index>=0){
	        	typevalue = 2;
	        }
	        editWindow.show();
	        mini.get("ptf").setValue(typevalue);
	        $.ajax({
	            url: "<%=path%>/commutationParole/loadDataGrid.action?crimid=" + row.crimid,
	            success: function (text) {
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
	        if(index>=0){
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