<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<html>
	<head>
		<title>修改数据交换方案</title>
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
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="left">
					<a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">保存</a>
					&nbsp;&nbsp;
					<a class="mini-button" iconCls="icon-cancel" onclick="cancelAdd()" plain="true">取消</a>
					&nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-filter"
						 onclick="removeUnselectedRows()" plain="true">移除未选中的行</a>
					</td>
					
					<td align="right">
			            &nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
					</td>
				</tr>
			</table>
		 </div>
    </div>
			
    <div class="mini-fit">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/getShcemeTables.json?1=1&ddcid=${ddcid}&ddctype=${ddctype}"    pageSize="2000"
	    	allowResize="false" idField="id" multiSelect="true" allowUnselect="false"
	    	showPager="false" showLoading="true" enableHotTrack="false" allowRowSelect="true"
	    	allowCellEdit="true" allowCellSelect="true"  showFilterRow="false" onlyCheckSelection="true"
	    	>
	        <div property="columns">
	        		<div type="checkcolumn" width="10"></div>
               		<div field="tablename" name="tablename" width="55" headerAlign="center" align="left"
               			 renderer="onTableNameRender" >
               			导出表
               			<input id="nameFilter" property="filter" class="mini-textbox" style="width:100%;" />
               		</div>
               		<div field="descrition" name="descrition" width="40" headerAlign="center" align="left"  >
               			导出表描述
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="totable" name="totable" width="45" headerAlign="center" align="left" 
               			 renderer="onToTableRender" >
               			导入表
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="descrition" name="descrition" width="40" headerAlign="center" align="left"  >
               			导入表描述
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="ddtismaintable" name="ddtismaintable" 
               			 width="10" headerAlign="center" align="center" 
               			 renderer="onMainTableRender" >
               			主表
               			<input property="editor" style="width:100%;" class="mini-combobox" data="ddtismaintableArray" textField="text" valueField="id"/>
               		</div>
               		<div field="shujuguanxi" name="shujuguanxi" width="40"
               		 	headerAlign="center" align="left" >
               			数据关系
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="addcondition" name="daochusql" width="40"
               		 	headerAlign="center" align="left" >
               			额外查询条件
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
	        </div>
	    </div>
    </div>
	
    <script type="text/javascript">
    	//
    	var ddtismaintableArray = [{id: '0', text: '是'},{id: '1', text: '否'}];
    	//
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
    	//
    	function onTableNameRender(e){
    		 var record = e.record;
    		 var table_name = record.table_name;
    		 //var tablename = record.tablename || record.table_name || "";
    		 var tablename = record.tablename;
    		 // 设置初始值
    		 record.tablename = tablename;
    		 //
    		 var html = "";
    		 	 html += "<a href=\"javascript:selectColumn(\'" +
					 record.tablename + "\',\'" + record.ddcid + "\',\'" + record.ddtid + "\',\'" + record.totable + 
    		 		 " \')\">";
    		 	 html += tablename;
    		 	 html += "</a>";
    		 //
    		 return html;
    	};
    	//
    	function onToTableRender(e){
    		 var record = e.record;
    		 var totable = record.totable;
    		 // 设置初始值
    		 record.totable = totable;
    		 return totable;
    	};
    	//
    	function onToDescriptionRender(e){
    		 var record = e.record;
    		 //
    		 var todescription = record.todescription || record.remarks || "";
    		 record.todescription = todescription;
    		 //
        	 return todescription;
    	};
    	// 是否主表
    	function onMainTableRender(e){
    		var record = e.record;
    		var ddtismaintable = record.ddtismaintable || "";
    		if('0' == ddtismaintable){
    			return "是";
    		} else {
    			return "";
    		}
    	};
    	// 选择列
    	function selectColumn(tablename,ddcid,ddtid,totable){
    		//
    		var w_w = window.top.document.body.clientWidth || 1000;
    		var w_h = window.top.document.body.clientHeight || 600;
    		//
    		var url = "<%=path%>/dbms/editSchemetablecolumn.page?1=1&ddcid=" + ddcid + "&ddtid=" + ddtid + "&tablename=" + tablename + "&totable=" + totable;
            mini.open({
                url: url,
                showMaxButton: true,
                title: "请勾选需要的字段",
                width: w_w,
                height: w_h,
                ondestroy: function (action) {
                }
            });
    	};
    	//
    	
		// 移除未选中的行
		function removeUnselectedRows(){
			// 已选中的行
			var rows = grid.getSelecteds();
			// 所有数据
			var data = grid.data;
			// 校验
			if(!rows || !rows.length){
				//
				return;
			}
			if(!data || !data.length){
				//
				return;
			}
			//
			
			//
			var unselectedRows = [];
			// 遍历
			for(var i=0; i<data.length; i++){
				var d = data[i];
				if(isSelected(d)){
					// 被选中
				} else {
					// 未选中
					unselectedRows.push(d);
				}
			}
			// 将未选中的列移除
			grid.removeRows(unselectedRows);
			
			// location.reload();
			
			// 闭包函数,依赖于 rows 变量
			// 根据 tablename 判断
			function isSelected(d){
				if(!d){return false;}
				var tablename_D = d.tablename;
				if(!tablename_D){return false;}
				for(var i=0; i< rows.length; i++){
					var r = rows[i];
					var tablename_r = r.tablename; 
					//
					if(tablename_D == tablename_r){
						return true;
					}
				}
				//
				return false;
			};
		};    	
    	
        // 生成 ddcid
        function generateDDCID(ddctype){
        	ddctype = ddctype || "";
        	//
        	var curDate = new Date();
        	var dateStr = mini.formatDate(curDate, "yyyyMMddHHmmss");
        	//
        	var ddcid = "";
        		ddcid += ""+dateStr;
        	if("0" == ddctype){
        		ddcid += "IMP";
        	} else if("1" == ddctype){
        		ddcid += "EXP";
        	} else if("2" == ddctype){
        		ddcid += "ITC";
        	} else {
        		ddcid = "";
        	}
        	//
        	return ddcid;
        };
        
		// 查询导出列表
		var hasLoadFrom = false;
		function listFromTable() {
			//
			var fromdatabaseid_input = mini.get("fromdatabaseid");
			var todatabaseid_input = mini.get("todatabaseid");
			var ddcname_input = mini.get("ddcname");
			//
			var fromdatabaseid = fromdatabaseid_input.getValue();
			var todatabaseid = todatabaseid_input.getValue();
			var ddcname = ddcname_input.getValue();
			//
			if(!ddcname){
				alert("请输入方案名称");
				ddcname_input.focus();
				return;
			}
			if(!fromdatabaseid){
				alert("请选择导出数据库");
				return;
			}
			if(!todatabaseid){
				alert("请选择导入数据库");
				return;
			}
		    //console.dir(grid);
		    var param =  {
		    	fromdatabaseid: fromdatabaseid
		    	,
		    	todatabaseid : todatabaseid
		    };
		    // 
	    	//
	    	//grid,setUrl(url);
	    	if(hasLoadFrom){
	    		var cfm = confirm("重新加载导出数据库的表信息?");
	    	} else {
	    		hasLoadFrom = true;
	    		cfm = true;
	    	}
	    	if(cfm){
		    	grid.load(param);
	    	}
		};
		
		
		
		// 保存数据
		function saveData() {
	        var data = grid.getChanges();
	        var json = mini.encode(data);
	        grid.loading("保存中...");
	        $.ajax({
	            url: "saveSchemetable.json?1=1",
	            data: { data: json },
	            type: "post",
	            success: function (text) {
	                grid.reload();
	            },
	            error: function (jqXHR, textStatus, errorThrown) {
	                alert(jqXHR.responseText);
	            }
	        });
		};
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
		
		// 移除未选中的行
	    function removeRow() {
	        var rows = grid.getSelecteds();
	        if (rows.length > 0) {
	            grid.removeRows(rows, true);
	        }
	    }
		
		// 刷新本页面
		function refreshPage(){
			
			var cfm = true;
			if(hasLoadFrom){
			    cfm = confirm("刷新页面则所有临时数据清空");
	    	}
			if(cfm){
				location.reload();
			}
			return false;
		};
		//
		function cancelAdd(){
			var cfm = true;
			if(hasLoadFrom){
				cfm = confirm("确定取消吗?");
	    	}
			if(cfm){
				goBack();
			}
			return false;
		};
		// 返回
		function goBack(){
			window.history.back();
		};
	</script>
	</body>
</html>