<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//
	String userid = "";
%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>数据行文交换</title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
  		<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
  		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
   
   <style type="text/css">
	    html, body
	    {
	        font-size:12px;
	        padding:0;
	        margin:0;
	        border:0;
	        height:100%;
	        overflow:auto;
	    }
    </style>
</head>
<body>
	<div class="mini-toolbar" style="padding:2px;border:1;">
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="right">
						<a class="mini-button" onclick="copy()" plain="true">复制</a>
			            &nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">新增</a>
			            &nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
					</td>
				</tr>
			</table>
		 </div>
    </div>
    <div class="mini-fit">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/listexchangetext.json"
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        <%--
	        		<div type="checkcolumn" width="25"></div>
	         --%>
	        		  <div field="orgid" name="orgid" width="30" headerAlign="center" align="center"  allowSort="true"><strong> 
               			组织机构ID 
               		</strong></div>
               		<div field="porgid" name="porgid" width="45" headerAlign="center" align="center"  allowSort="true"><strong> 
               			组织机构父ID 
               		</strong></div>
               		<div field="classification" name="classification" width="30" headerAlign="center" align="center"  allowSort="true"><strong> 
               			类别 
               		</strong></div>
               		<div field="subclass" name="subclass" width="30" headerAlign="center" align="center"  allowSort="true"><strong> 
               			子类 
               		</strong></div>
               		<div field="status" name="status" width="70" headerAlign="center" align="center"  allowSort="true"><strong> 
               			状态（0刑期，1无刑期，2其他） 
               		</strong></div>
           			<div field="text1" name="text1" width="50" headerAlign="center" align="center"  allowSort="true"><strong> 
               			行文1 
               		</strong></div>
               		<div field="text2" name="text2" width="50" headerAlign="center"  align="center"  allowSort="true"><strong> 
               			行文2 
               		</strong></div>
               		<div field="text3" name="text3" width="50" headerAlign="center" align="center"  allowSort="true"><strong> 
               			行文3 
               		</strong></div>
               		<div field="remark" name="remark" width="40" headerAlign="center" align="center"  allowSort="true"><strong> 
               			备注 
               		</strong></div>
			        <div width="40" headerAlign="center" align="center"	allowSort="false" renderer="onActionRenderer"><strong>操作</strong></div> 
	        </div>
	    </div>
    </div>
    
	<div id="editWindow" class="mini-window" title="添加数据行文交换" style="width:240px;height:320px"
		    showModal="false" allowResize="false" allowDrag="true"  showMaxButton = "true">
		    <!-- 弹出框 -->
		    <div id="editform" class="form" >
		        <input class="mini-hidden" name="id"/>
		        <div class="mini-toolbar mini-grid-headerCell" style="border-bottom:0;padding:0px;margin: -5px -5px 5px;">
		            <table style="width:100%;">
		                <tr>
		                    <td align="left">
			            		<a class="mini-button" onclick="saveData()" plain="true" style="width:60px">保存</a>   
			            		|    
			           			<a class="mini-button" onclick="cancelEditWindow()"  plain="true" style="width:60px;">取消</a>
			           		</td>
		                </tr>
		            </table>           
		        </div>
		        <table align="center">
		            <tr>
		                <td >组织机构ID：</td>
		                <td ><input name="orgid" class="mini-textbox"   required="true" /> </td>
		            </tr>
		            <tr>
		                <td >组织机构父ID：</td>
		                <td ><input name="porgid" class="mini-textbox"   required="true" /></td>
		            </tr>
		            <tr align="center">
		                <td >类&nbsp;&nbsp;&nbsp;&nbsp;别 ：</td>
		                <td ><input name="classification" class="mini-textbox"   required="true" /> </td>
		            </tr>
		            <tr align="center">
		                <td> 子&nbsp;&nbsp;&nbsp;&nbsp;类 ：</td>
		                <td><input name="subclass" class="mini-textbox"  required="true"  /></td>
		            </tr>
		            <tr align="center">
		                <td> 状&nbsp;&nbsp;&nbsp;&nbsp;态 ：</td>
		                <td>
		                <input name="status" class="mini-combobox" size="60"  required="true" 
		                		 valueField="id" textField="text" data="databasetypeArray" />
		                </td>
		            </tr>
		            <tr align="center">
		                <td >行&nbsp;文&nbsp;1：</td>
		                <td ><input name="text1" class="mini-textbox" /> </td>
		            </tr>
		            <tr align="center">
		                <td>行&nbsp;文&nbsp;2：</td>
		                <td><input name="text2" class="mini-textbox" /></td>
		            </tr>
		            <tr align="center">
		                <td>行&nbsp;文&nbsp;3：</td>
		                <td><input name="text3" class="mini-textbox"/></td>
		            </tr>
		            <tr align="center">
		                <td>备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
		                <td><input name="remark" class="mini-textbox" /></td>
		            </tr>
		        </table>
		    </div>
	</div>
    <script type="text/javascript">
    	//
    	var databasetypeArray = [
    			{id:0,text:"刑期"}
    			,{id:1,text:"无刑期"}
    			,{id:2,text:"其他"}
    		];
    	//
    	var userid = "<%=userid %>";
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        ////////////////////////
        // 渲染操作列
        function onActionRenderer(e) {
            var s = '<a class="Edit_Button" href="javascript:editRow()" >修改</a>';
                //s += '&nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:deleteRow()">删除</a>'; 
            return s;
        };
       
		// 查询
		function search() {
		    var search_databasetype = document.getElementById("search_databasetype").value || "";
		    //
		    
		    var search_ddip = mini.get("search_ddip").getValue();
		    var search_port = mini.get("search_port").getValue();
		    //console.dir(grid);
		    var param =  {
		    	databasetype: search_databasetype
		    	,ddip: search_ddip
		    	,port: search_port
		    };
		    grid.load(param);
		};
		//
		
		// 增加
		function addRow() {
			var data = {
				islocalnew: 1
				,databasetype : "Oracle"
				,port : "1521"
			};
			// 弹出 新增/编辑框
			showEditWindow(data);
		};
		function rowdblclickfunction(e){
			editRow();
		};
		// 编辑
		function editRow() {
		    var row = grid.getSelected();
		    //
		    if (row) {
				var data = $.extend({}, row);
				//data["databasepassword"] = "";
		        showEditWindow(data , 1);
		    }
		};
		// 删除
		function deleteRow(){
		    var row = grid.getSelected();
		    //
		    if (row) {
		    	var del = confirm("确实要删除吗?");
		    	if(del){
		    		alert("不允许删除");
		    	}
				//data["databasepassword"] = "";
		        //showEditWindow(data , 1);
		        
		    }
		};
		// 显示编辑框
		function showEditWindow(data, isedit){
			data = data || {};
			//
			var editWindow = mini.get("editWindow");
			editWindow.showAtPos('right','top');
			//
			var form = new mini.Form("#editform");
			//
			var islocalnew = data.islocalnew;
			var ddorg = data.ddorg;
			//
			var ddidInput = mini.getbyName("ddid");
			var ddorgInput = mini.getbyName("ddorg");
			if(ddidInput){
				if(!islocalnew){
					ddidInput.allowInput=false;
				} else {
					ddidInput.allowInput=true;
				}
			}
			if(ddorgInput){
				if(ddorg){
					ddorgInput.setText(ddorg);
				} else {
					ddorgInput.setText("");
				}
			}
			form.loading();
			// 设置数据
	        form.setData(data);
			//
			form.setIsValid(true);
			form.setChanged(false);
			// 允许编辑
	        form.unmask();
		};

		// 隐藏
		function hideEditWindow() {
			// 界面
			var editWindow = mini.get("editWindow");
			editWindow.hide();
		};
		function cancelEditWindow() {
			hideEditWindow();
		};
		
		// 保存数据
		function saveData() {
			var form = new mini.Form("#editform");
			
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
			var data = form.getData();
			//data = JSON.parse(data);
			// 界面
			grid.loading("保存中，请稍候......");
			var editWindow = mini.get("editWindow");
			if(!data){
				editWindow.hide();
			    grid.reload();
				return ;
			}
			//
			var  url = "<%=path%>/dbms/ajax/saveexchangetext.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
						editWindow.hide();
			        	grid.reload();
	               } else {
	               		alert(info);
	               		grid.unmask();
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("保存失败");
	               	grid.unmask();
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
		};
	function requestAjax(url, data, successCallback, errotCallback){
			// 执行AJAX请求
			$.ajax({
			    url: url,
			    data: data,
		        type: "post",
			    success: function (message) {
			    	/*
			    	if(window["JSON"]){
			    		message = mini.decode(message) || {};
			    	} else { // IE6, IE7
	        	   		message = eval("("+ message + ")") || {};
			    	}
			    	*/
			    	message = mini.decode(message) || {};
			   		if(successCallback){
			    	   successCallback.call(window, message);
			   		}
	            	return false;
			    },
			    error: function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			       if(errotCallback){
			    	   errotCallback.apply(window, arguments);
			       } else {
			    	   alert("操作失败!");
			       };
			    }
			});
		};
		// 刷新本页面
		function refreshPage(){
			//
			if(!window["____refreshPage"]){
				window["____refreshPage"] = true;
				//
				location.reload();
			} else {
				window.setTimeout(function(){
					window["____refreshPage"] = false;
					},1*1000);
			}
		};
	</script>
	</body>
</html>