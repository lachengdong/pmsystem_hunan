<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<html>
	<head>
		<title>新增数据交换方案</title>
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
						新增方案类型：
						<select id="ddctype" name="ddctype">
							<option value="2" selected="selected">数据交换</option>
			             </select>
			            &nbsp;&nbsp;
		  				方案名称：
		  				<input class="mini-textbox" type="text" width="120"
		  				 id="ddcname" name="ddcname" size="26"/>
		  				&nbsp;&nbsp;
		  				导出库：
		  				<input id="fromdatabaseid" name="fromdatabaseid" class="mini-combobox" 
		  					style="width:100px;" popupWidth="200" 
			                	textField="ddname" valueField="ddid"
								url="<%=path %>/dbms/ajax/listalldb.json"
		  				 />
		  				&nbsp;&nbsp;
		  				导入库：
		  				<input id="todatabaseid" name="todatabaseid" class="mini-combobox" 
		  						style="width:100px;"   popupWidth="200" 
			                	textField="ddname" valueField="ddid"
								url="<%=path %>/dbms/ajax/listalldb.json"
		  				 />
		  				&nbsp;&nbsp;
		 				<a class="mini-button" iconCls="icon-node" 
		 					onclick="listFromTable()" plain="true">加载数据表</a>
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
	    	url="<%=path %>/dbms/ajax/listdbtables.json"    pageSize="2000"
	    	allowResize="false" idField="tablename" multiSelect="true" allowUnselect="false"
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
               		<div field="descrition" name="descrition" renderer="onDescriptionRender"
               			 width="40" headerAlign="center" align="left"  >
               			导出表描述
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="totable" name="totable" width="45" headerAlign="center" align="left" 
               			 renderer="onToTableRender" >
               			导入表
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="todescription" name="todescription"
               			 width="40" headerAlign="center" align="left"  
               			 renderer="onToDescriptionRender" >
               			导入表描述
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="ddtismaintable" name="ddtismaintable" 
               			 width="10" headerAlign="center" align="center" 
               			 renderer="onMainTableRender" >
               			主表
               			<input property="editor" style="width:100%;" class="mini-combobox" data="ddtismaintableArray"/>
               		</div>
               		<div field="ddtorder" name="ddtorder" 
               			 width="15" headerAlign="center" align="center">
               			排序
               			<input property="editor" style="width:100%;" class="mini-spinner" minValue="1" maxValue="250"/>
               		</div>
               		<div field="shujuguanxi" name="daochusql" width="40"
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
        //grid.load();
    	//
    	function onTableNameRender(e){
    		 var record = e.record;
    		 var table_name = record.table_name;
    		 //
    		 var tablename = record.tablename || record.table_name || "";
    		 // 设置初始值
    		 record.tablename = tablename;
    		 //
    		 var onclickStr =  " onclick=\"selectColumn(\'" + tablename +  "\')\" ";
    		 //
    		 var html = "";
    		 	 html += "<a " +onclickStr +" href=\"javascript:void(0)\">";
    		 	 html += tablename;
    		 	 html += "</a>";
    		 
    		 //
    		 return html;
    	};
    	//
    	function onToTableRender(e){
    		 var record = e.record;
    		 var table_name = record.table_name;
    		 //
    		 var totable = record.totable || record.table_name || "";
    		 // 设置初始值
    		 record.totable = totable;
    		 return totable;
    	};
    	//
    	function onDescriptionRender(e){
    		 var record = e.record;
    		 //
    		 var descrition = record.descrition || record.remarks || "";
    		 record.descrition = descrition;
    		 //
        	 return descrition;
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
    	//
    	
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
    	function selectColumn(tablename, totable){
    		//
    		tablename = tablename||"";
    		tablename = tablename.trim();
    		// 取得当前行
			var row = grid.findRow(function(row2){
				//
				var name2 = row2.tablename;
				//
				if(name2 && (name2.trim() == tablename)){
					return true;
				}
			});
    		if(!totable){
    			// 遍历记录
    			if(row){
    				totable = row.totable;
    			}
    			if(!totable){
    				totable = tablename;
    			}
    			if(!totable) {
	    			//
	    			//tablename = tablename;
	    			alert("导入表不能为空");
	    			return;
    			}
    		}
    		
    		totable = totable||"";
    		totable = totable.trim();
    		//
			var fromdatabaseid_input = mini.get("fromdatabaseid");
			var todatabaseid_input = mini.get("todatabaseid");
			var ddcname_input = mini.get("ddcname");
			//
			var fromdatabaseid = fromdatabaseid_input.getValue();
			var todatabaseid = todatabaseid_input.getValue();
			//
    		tablename = encodeURIComponent(tablename) || {};
    		totable = encodeURIComponent(totable) || {};
    		//var databaseid = fromdatabaseid_input.getValue() || {};
    		var databaseid = todatabaseid_input.getValue() || {};
    		//
    		var w_w = window.top.document.body.clientWidth || 1000;
    		var w_h = window.top.document.body.clientHeight || 600;
    		
    		//
    		var url = "<%=path%>/dbms/selectcolumninterchange.page?1=1" 
    		        + "&totablename="+ totable + "&todatabaseid="+todatabaseid
    		        + "&fromtablename=" + tablename + "&fromdatabaseid=" + fromdatabaseid
    		       ; 
    		//
            mini.open({
                url: url,
                showMaxButton: true,
                title: "请勾选需要的字段",
                width: w_w,
                height: w_h,
                ondestroy: function (action) {
                    if (action != "ok") {
                    	return;
                    }
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = mini.clone(data);
                    //
                    if(row){
		 				row.columns = data;
		 				grid.select(row, true);
                    } else {
                    	alert("不能确定列");
                    }
                    
                }
            });
    		//
    		return false;
    	};
    	//
    	
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
			//
			var fromdatabaseid_input = mini.get("fromdatabaseid") ||{};
			var todatabaseid_input = mini.get("todatabaseid") ||{};
			var ddcname_input = mini.get("ddcname") ||{};
		    var ddctype_input = document.getElementById("ddctype") ||{};
		    
			// 获取基础数据
			var fromdatabaseid = fromdatabaseid_input.getValue();
			var todatabaseid = todatabaseid_input.getValue();
			//
			var ddcname = ddcname_input.getValue();
        	var ddctype = ddctype_input.value;
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
			if(!ddctype){
				alert("请选择方案类型");
				return;
			}
			if(!hasLoadFrom){
				alert("尚未导出数据表,不能保存");
				return;
	    	}
			//
			var rows = grid.getSelecteds();
			//
			if(!rows || !rows.length){
				alert("请勾选需要数据交换的数据表");
				return;
	    	}
			// 判断主表记录,只能选择一个主表
			// ddtismaintable
			var mainTablesCount = 0;
			//
			function checkMainTable(rows){
				for(var i=0; i < rows.length; i++){
					var r = rows[i];
					if(r &&  r.ddtismaintable &&  r.ddtismaintable == '0'){
						mainTablesCount ++;
					}
				}
				if(mainTablesCount > 1){
					return false;
				} else {
					return true;
				}
			};
			if(!checkMainTable(rows)){
				alert("主表记录只能有一条");
				return false;
			}
			// ... 所有数据表必须选择列
			var hasNotColumn = 0;
			var tableHasNotColumn = [];
			//
			function checkHasNotColumn(rows){
				for(var i=0; i < rows.length; i++){
					var r = rows[i];
					if(r &&  r.columns &&  r.columns.length > 0){
						//
					} else {
						hasNotColumn ++;
						tableHasNotColumn.push(r.table_name);
					}
				}
				if(hasNotColumn > 0){
					return false;
				} else {
					return true;
				}
			};
			if(!checkHasNotColumn(rows)){
				alert(tableHasNotColumn.join("、")+" 表没有选择数据列!");
				return false;
			}
			// 表
			var tablelist = mini.encode(rows);
			//
			var ddcid = generateDDCID(ddctype);
			//
			var data = {
				fromdatabaseid : fromdatabaseid
				, todatabaseid : todatabaseid
				, ddcname : ddcname
				, ddctype : ddctype
				, ddcid : ddcid
				, tablelist  : tablelist
			};
			// 界面
			grid.loading("保存中，请稍候......");
			//
			var  url = "<%=path%>/dbms/ajax/adddbschemeinterchange.json";
			var successCallback = function (message) {
	        	   var info = message["info"];
	               alert(info);
	               grid.unmask();
	               if(1 === message["status"]){
			    		// 成功则回到前一个页面
			        	//
			        	window.location = "<%=path%>/dbms/dbschememanage.page";
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