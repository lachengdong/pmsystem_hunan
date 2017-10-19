<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>考核起日补录</title>
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
<body onload="init('12541');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%">
				       <a class="mini-button" style="display:none;"  id="12542" iconCls="icon-save" plain="true" onclick="saveData">存盘</a> 
				       <span class="separator"></span>
               		   <a class="mini-button" iconCls="icon-reload" plain="true" onclick="refreshPage" >刷新</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="<%=path %>/assessmentDate/getList.json?1=1" idField="spid" onbeforeload="myloading" onload="myunmask"  allowResize="true"
        	 allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
          <div property="columns">
              <div field="CRIMID" width="40" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="NAME" width="40" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="AWARDEND" width="50" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer" dateFormat="yyyy-MM-dd" >考核起日
              		<input property="editor" class="mini-datepicker" style="width:100%;"/>
              </div>
              <div field="REPORTTIME" width="50" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer" dateFormat="yyyy-MM-dd">上次监狱长签字日期
              		<input property="editor" class="mini-datepicker" style="width:100%;"/>
              </div>
              <div field="EXECTIME" width="50" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">执行日期</div>
              <div field="COURTNAME" width="60" headerAlign="center" align="left"  allowSort="true">裁判法院</div>
              <div field="COURTCHANGEFROM" width="50" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">刑期起日</div>
              <div field="COURTCHANGETO" width="50" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">刑期止日</div>
              <!-- <div field="ATTACHINFO" width="30" headerAlign="center" align="left"  allowSort="true">所在监区</div> -->
              <div field="TYPEMAPPINGNAME" name="TYPEMAPPINGNAME" width="80" headerAlign="center" align="center"  allowSort="true" hideable="true">罪犯类型</div>
              <div field="TYPEMAPPING" name="TYPEMAPPING" width="50" headerAlign="center" align="left"  allowSort="true" hideable="true">罪犯类型</div>
              <div name="jyaction" width="90" headerAlign="center" align="center"  allowSort="false" renderer="onJyActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        	grid.sortBy("CRIMID","desc");
        	grid.hideColumn("TYPEMAPPING");
        	grid.load();
       
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            key = encodeURI(key);
            grid.load({ key: key });
        }
		function saveData() {
            var data = grid.getChanges();
            var json = mini.encode(data);
            grid.loading("保存中，请稍后......");
            $.ajax({
                url: "<%=path%>/assessmentDate/saveChanges.action?1=1",
                data: { data: json},
                type: "post",
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
        }
		// 刷新本页面
		function refreshPage(){
			if(!window["____refreshPage"]){window["____refreshPage"] = true;location.reload();
			} else {
				window.setTimeout(function(){window["____refreshPage"] = false;},1*1000);
			}
		};

         function onJyActionRenderer(e) {
        	var s = '${middlestr}';
	     	return s;            
        }	
        
        function chooseOne(typeid) {
            var data = grid.getSelecteds();
            var json = mini.encode(data);
            grid.loading("保存中，请稍后......");
            $.ajax({
                url: "<%=path%>/assessmentDate/setCrimeTypesMapping.action?1=1",
                data: { data: json,typeid :typeid},
                type: "post",
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });        
        }

    function reward(){
        var data = grid.getSelecteds();
        var json = mini.encode(data);
    	mini.open({
	        url: "<%=path%>/assessmentDate/toCrimMergeReferenceListPage.action?data="+json,
	        showMaxButton: true,
	        allowResize: false,
	        title: "奖励明细", width: 600, height: 400,
	        onload: function () {
	            
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
    }
      
    function consume(){
        var data = grid.getSelecteds();
        var json = mini.encode(data);
    	mini.open({
	        url: "<%=path%>/assessmentDate/toCrimConsumePage.action?data="+json,
	        showMaxButton: true,
	        allowResize: false,
	        title: "奖励明细", width: 600, height: 400,
	        onload: function () {
	            
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
    }        
		function viewscreeningexcuse() {
			var row = grid.getSelected();
			var url = "<%=path%>/assessmentDate/viewscreeningexcuse.action?1=1&crimid="
					+ row.CRIMID;
			var win = mini.open( {
				url : url,
				title : "筛查依据情况",
				width : 600,
				height : 490,
				showMaxButton : true,
				allowResize : false,
				onload : function() {
				},
				ondestroy : function(action) {
				//	grid.reload();
				}
			});
		}        
    </script>    
</body>  
</html>