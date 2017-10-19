<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";

	String totablename = request.getParameter("totable");
	String fromtablename = request.getParameter("tablename");
	String databaseid = request.getParameter("databaseid");
	String ddcexpscheme = request.getParameter("ddcexpscheme");
%>

<html>
	<head>
		<title>选择列</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	   <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
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
	<body >
	
	<div class="mini-toolbar" style="padding:2px;border:1;">
	     <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>
		 <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
	
    <div class="mini-fit">
    	<input name="ddcid" value="${ddcid}" class="mini-hidden"/>
    	<input name="ddtid" value="${ddtid}" class="mini-hidden"/>
    	<input name="databaseid" id="databaseid" value="${databaseid}" class="mini-hidden"/>
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/getSchemetablecolumn.json?1=1&ddcid=${ddcid}&ddtid=${ddtid}"
	    	pageSize="2000"
	    	allowResize="false" idField="dcdtocolumns" multiSelect="true" allowUnselect="true"
	    	showPager="false" showLoading="true" enableHotTrack="false" allowRowSelect="true"
	    	allowCellEdit="true" allowCellSelect="true"
	    	showFilterRow="false" onlyCheckSelection="false"
	    	>
	        <div property="columns">
	        	<div type="indexcolumn" width="10" header="序号"></div>
	        	<div type="checkcolumn" width="10"></div>
	        	<div header="导入表: ${totable}" headerAlign="center">
	        	  <div property="columns">
               		<div field="dcdtocolumns" name="dcdtocolumns"
               		 	width="55" headerAlign="center" align="left">
               			导入列
               		</div>
               		<div field="dcdtocolumnsscribe" name="dcdtocolumnsscribe"
               			 width="40" headerAlign="center" align="left">
               			列描述
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="dcdtocolumnstype" name="dcdtocolumnstype" 
               			 width="30" headerAlign="center" align="left">
               			列类型
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="dcdtocolumnssize" name="dcdtocolumnssize" 
               			 width="25" headerAlign="center" align="center">
               			字段长度
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="dcdtocloumnsdefaultvalue" name="dcdtocloumnsdefaultvalue" width="25"
               		 	headerAlign="center" align="left" >
               			默认值
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="dcdtocolumnspoint" name="dcdtocolumnspoint" width="20"
               		 	headerAlign="center" align="center" >
               			小数位数
               			<input property="editor" class="mini-spinner" style="width:100%;"
               				   vtype="int" minValue="0" maxValue="18"/>
               		</div>
               		<div field="dcdcodetype" name="dcdcodetype" width="30"
               		 	headerAlign="center" align="left" >
               			代码映射类型
               			<input property="editor" class="mini-combobox"  style="width:120px;"
               				   popupWidth="150"
				            	textField="codetypename" valueField="codetypeid"
				            	url="<%=basePath %>dbms/ajax/listcodetype.json"
				            	/>
               		</div>
               		<div field="dcdifpkey" name="dcdifpkey" trueValue="1" falseValue="0"
               			 width="20" headerAlign="center">是主键
               			<input property="editor" class="mini-combobox" style="width:100%;"
               				 data="dcdifpkeyArray"/>  
               		</div>
               		<div field="dcdpkgenerator" name="dcdpkgenerator" width="25"
               		 	headerAlign="center" align="left" >
               			主键策略
               			<input property="editor" class="mini-combobox" style="width:100%;"
               				 data="dcdpkgeneratorArray"/>
               		</div>
               		
               	  </div>
               	</div>
               	
               	<div field="_sp" name="_sp"
               		 	width="2" headerAlign="center" align="left">
               		</div>
               		
	        	<div header="导出表: ${tablename}" headerAlign="center">
	        	  <div property="columns">
	        	    <div field="dcdfromcolumnsOLD" name="dcdfromcolumnsOLD"
               		 	width="55" headerAlign="center" align="left">
               			原列名
               			<input property="editor" class="mini-combobox" style="width:100%;"
               			  url="<%=basePath %>dbms/ajax/listfromtablecolumns.json?tablename=<%=fromtablename %>&databaseid=${requestScope.databaseid}" 
               			 textField="dcdfromcolumns" valueField="dcdfromcolumns" 
               			 showNullItem="true" allowInput="true" onvaluechanged=""/>   
               		</div>
               		<div field="dcdfromcolumns" name="dcdfromcolumns"
               		 	width="55" headerAlign="center" align="left">
               			导出列
               			<input property="editor" class="mini-textbox" style="width:100%;"/>  
               		</div>
               		<div field="dcdfromcolumnsscribe" name="dcdfromcolumnsscribe"
               			 width="40" headerAlign="center" align="left">
               			描述
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="dcdfromcloumnstype" name="dcdfromcloumnstype" 
               			 width="30" headerAlign="center" align="center">
               			类型
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="dcdfromcloumnssize" name="fromcolumnssize" 
               			 width="25" headerAlign="center" align="center">
               			长度
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="dcdfromcolumnspoint" name="dcdfromcolumnspoint" width="25"
               		 	headerAlign="center" align="left" >
               			小数位
               		</div>
               	  </div>
               	</div>
	        </div>
	    </div>
    </div>
	
    <script type="text/javascript">
    	
    	var dcdifpkeyArray = [{id: 1, text: '是'},{id: 0, text: '否'}];
    	var dcdpkgeneratorArray = [{id: '', text: ''}, {id: 'auto', text: '自动生成'}];
    	// 解析 miniUI
        mini.parse();
    	//
    	var totablename = "<%=totablename%>";
    	var fromdatabaseid = mini.get("databaseid").getValue();
    	var fromtablename = "<%=fromtablename%>";
  
        var grid = mini.get("datagrid1");
    	//
        grid.on("update", onGridLoaded);
        //grid.on("load", onGridLoaded);
        grid.on("cellcommitedit", onFromColChanged);
    	// 加载totable初始数据
        grid.load({
        	tablename : totablename
        	, databaseid : fromdatabaseid
        });
        ////////////////////////
        var fromColumnList = [];
        function onGridLoaded(e){
        	// 加载from表数据
			var data = {
				databaseid : fromdatabaseid
				, tablename : fromtablename
			};
			var  url = "<%=path%>/dbms/ajax/listfromtablecolumns.json";
			var successCallback = function (message) {
	        	   var info = message["total"];
	        	   var data = message["data"];
	               grid.unmask();
	               if(1 === message["status"]){
	            	   // 处理数据
        			   //window["console"] && console.dir(data);
        			   if(data){
        				   fromColumnList = data;
        				   // 遍历用户, 循环设值
        				   // ...
        				   setGridFromData(data);
        			   }
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("导出表信息加载失败 ^~^ ");
	               	grid.unmask();
			    };
			//
			// 界面
			grid.loading("正在加载导出表，请稍候......");
			requestAjax(url,data,successCallback,errotCallback);
        };
        //
        function onFromColChanged(e){
 
        	var column = e.column || {};
        	var name = column.name;
        	if("dcdfromcolumnsOLD" != name){
        		// 其他列,忽略
        		return;
        	}
        	// 改变后的值
        	var oldValue = e.oldValue;
        	var value = e.value;
        	// 找到事件发生的当前行
        	var row = e.row;
        	//
        	var fromcol = {"dcdfromcolumnsOLD": value, "dcdfromcolumns": value};
        	// 遍历, 找到 from 列
        	$.each(fromColumnList, function(i, v){
        		//
        		var dcdfromcolumns = v["dcdfromcolumns"];
        		if(dcdfromcolumns && dcdfromcolumns==value){
        			fromcol = v;
        			return false;
        		}
        	});
        	updataFromRow(fromcol,row);
        };
        //
        function setGridFromData(fromcols){
        	if(!fromcols){
        		return;
        	}
        	// 遍历
        	$.each(fromcols, function(i, v){
        		updataFromRow(v);
        	});
        };
        // 更新 from信息
        function updataFromRow(fromcol, row){
        	if(!fromcol){
        		return;
        	}
        	if(!row){
        		// 找到对应的列
        		row = findRowByFrom(fromcol);
        	}
        	if(!row){
        		return;
        	}
        	// 然后克隆数据,更新
        	var fData = mini.clone(fromcol);
        	
        	var f_dcdfromcolumns = fData["dcdfromcolumns"];
        	var dcdfromcolumns = row["dcdfromcolumns"];
        	var dcdfromcolumnsOLD = row["dcdfromcolumnsOLD"];
        	if(!dcdfromcolumns){
        		dcdfromcolumns = f_dcdfromcolumns;
        	}
        	if(dcdfromcolumns && !dcdfromcolumnsOLD){
        		dcdfromcolumns = dcdfromcolumns.replace(dcdfromcolumnsOLD,f_dcdfromcolumns);
        	}
        	
        	// 处理数据, 
        	var nData = {
        		dcdfromcolumnsOLD : fData["dcdfromcolumns"]
        		,dcdfromcolumns : fData["dcdfromcolumns"]||0
        	};
        	grid.updateRow (row, nData);
        };
        // 根据from列，找出Grid中对应的列
        function findRowByFrom(fromcol){
        	if(!fromcol){
        		return null;
        	}
        	//
        	var colName = fromcol["dcdfromcolumns"];
        	if(!colName){
        		return null;
        	}
        	// 原数据
        	//var gridData = grid.getData();
        	//
        	// 取得对应行
			var row = grid.findRow(function(row2){
				//
				var name2 = row2["dcdfromcolumns"];
				//
				if(name2 && (name2 == colName)){
					return true;
				}
			});
        	//
        	return row;
        };
        ////////////////////////
	    function GetData() {
        	// 
		    var rows = grid.getSelecteds();
        	//
	        return rows;
	    };
	    function SetData(data) {
	        if (data.action == "edit") {
	            //跨页面传递的数据对象，克隆后才可以安全使用
	            data = mini.clone(data);
	            mini.get("id").value=data.id;
	            //var url = "ajax/getResourceTreeByRole.json?1=1&qroleids="+data.id;
	            //tree.setUrl(url);
	            //tree.load();
	        }else{
	        	 document.getElementById("operatetype").value="new";//走新增的action
	        }
	    };
	    
        
	    //////////////////////////////////
		// 请求AJAX,工具方法
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
	    // 关闭窗口
	    function CloseWindow(action) {
	        if (window.CloseOwnerWindow){
	        	return window.CloseOwnerWindow(action);
	        } else {
	        	window.close();
	        }
	    };
	    // 确定
	    function onOk() {
	        var data = grid.getChanges();
	        var json = mini.encode(data);
	        grid.loading("保存中...");
	        $.ajax({
	            url: "saveSchemetablecolumn.json?1=1",
	            data: { data: json },
	            type: "post",
	            success: function (text) {
	                grid.reload();
	            },
	            error: function (jqXHR, textStatus, errorThrown) {
	                alert(jqXHR.responseText);
	            }
	        });
	        CloseWindow("ok");
	    };
	    // 取消
	    function onCancel() {
	        CloseWindow("cancel");
	    };
	</script>
	</body>
</html>