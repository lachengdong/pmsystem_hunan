<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()
						+":"+request.getServerPort()+path+"/";

%>

<html>
	<head>
		<title>新增数据导出方案</title>
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
						方案类型：
						<select id="ddctype" name="ddctype">
							<option value="1" selected="selected">数据导出</option>
			             </select>
			            &nbsp;&nbsp;
		  				方案名称：
		  				<input class="mini-textbox" type="text" width="140"
		  				 id="ddcname" name="ddcname" size="26"/>
		  				&nbsp;&nbsp;&nbsp;&nbsp;
		  				导出数据库：
		  				<input id="fromdatabaseid" name="fromdatabaseid" class="mini-combobox" 
		  					style="width:120px;" popupWidth="200" 
			                	textField="ddname" valueField="ddid"
								url="<%=path %>/dbms/ajax/listalldb.json"
		  				 />
		  				&nbsp;&nbsp;&nbsp;&nbsp;
		 				<a class="mini-button" iconCls="icon-node" 
		 					onclick="listFromTable()" plain="true">加载数据表</a>
			            &nbsp;&nbsp;&nbsp;&nbsp;
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
	    	url="<%=path %>/dbms/ajax/listdbtables.json"
	    	pageSize="2000"
	    	allowResize="false" idField="id" multiSelect="true" allowUnselect="false"
	    	showPager="false" showLoading="true" enableHotTrack="false" allowRowSelect="true"
	    	allowCellEdit="true" allowCellSelect="true"
	    	showFilterRow="false" onlyCheckSelection="true"
	    	>
	        <div property="columns">
	        		<div type="checkcolumn" width="10"></div>
               		<div field="tablename" name="tablename"
               		 	width="55" headerAlign="center" align="left"
               			renderer="onTableNameRender" >
               			操作表名
               		</div>
               		<div field="descrition" name="descrition" renderer="onDescriptionRender"
               			 width="40" headerAlign="center" align="left"  >
               			操作表描述
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
               			导出顺序
               			<input property="editor" style="width:100%;" class="mini-spinner" minValue="1" maxValue="250"/>
               		</div>
               		<div field="daochusql" name="daochusql" width="65"
               			renderer="onExpSQLRender"
               		 	headerAlign="center" align="left" >
               			导出SQL
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="shujuguanxi" name="daochusql" width="35"
               		 	headerAlign="center" align="left" >
               			数据关系
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		<div field="addcondition" name="daochusql" width="35"
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
    		//
    		 var record = e.record;
    		 var table_name = record.table_name;
    		 //
    		 var tablename = record.tablename || "";
    		 if(!tablename && table_name){
    			 // 设置初始值
    			 record.tablename = table_name;
    			 // 删除原记录中的  table_name 属性
    			 delete record.table_name;
    		 }
    		 //
    		 tablename = record.tablename || "";
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
    	function selectColumn(tablename){
    		tablename = tablename||"";
    		tablename = tablename.trim();
    		//
			var fromdatabaseid_input = mini.get("fromdatabaseid");
			//			
    		tablename = encodeURIComponent(tablename) || {};
    		var databaseid = fromdatabaseid_input.getValue() || {};
    		//
    		var w_w = window.top.document.body.clientWidth || 1000;
    		var w_h = window.top.document.body.clientHeight || 600;
    		//
            mini.open({
                url: "<%=path%>/dbms/selectcolumn.page?1=1" + "&tablename="+tablename+ "&databaseid="+databaseid,
                showMaxButton: true,
                title: "请勾选需要的字段",
                width: w_w,
                height: w_h,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        // 列
                        var cols = "";
                        var colnames = [];
                        //
                        if(data && data.length) {
                        	//
                        	for(var i=0; i<data.length; i++){
                        		var c = data[i];
                        		//
                        		var column_name=c.column_name;
                        		if(column_name){
                        			colnames.push(column_name);
                        		}
                        	}
                        	// 找到对应的列
                        	if(colnames.length){
                        		cols = colnames.join(",");
                        	}
                        }
                        //
                        // 设置为 select *
                        if(!cols){
                        	cols = "*";
                        }
                        //
                        var daochusql = "SELECT " + cols +" FROM "+tablename;
                        //
                        var row = grid.findRow(function(row2){
                        	//
                        	var name2 = row2.tablename || "";
                        	//
						    if(name2 && (name2 == tablename)){
						    	return true;
						    }
						});
                        if(row){
    		 				row.daochusql = daochusql;
    		 				row.columns = data;
    		 				//
    		 				grid.updateRow( row, row);
    		 				grid.select(row, true);
                        }
                    }
                }
            });
    		//
    		return false;
    	};
    	function onDescriptionRender(e){
    		 //
    		 var record = e.record || {};
    		 var descrition = record.descrition || record.remarks || "";
    		 //
    		 record.descrition = descrition;
    			 
    		 //
        	 return record.descrition;
    	};
    	//
    	function onExpSQLRender(e){
    		 var record = e.record;
    		 var daochusql = record.daochusql || "";
    		 var tablename = record.tablename || "";
    		 //
    		 if(!daochusql){
    			daochusql = "SELECT * FROM "+tablename;
    		 	record.daochusql = daochusql;
    		 }
    		 //
    		 return daochusql;
    	};
        
        ////////////////////////
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
			var fromdatabaseid_input = mini.get("fromdatabaseid");
			var ddcname_input = mini.get("ddcname");
			//
			var fromdatabaseid = fromdatabaseid_input.getValue();
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
		    var param =  {
		    	fromdatabaseid: fromdatabaseid
		    };
		    //
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
		//
		
		// 删除
		function deleteRow(){
		    var row = grid.getSelected();
		    //
		    if (row) {
		    	grid.removeRow(row);
		    }
		};
		//
		
		// 保存数据
		function saveData() {
			//
			var fromdatabaseid_input = mini.get("fromdatabaseid");
			var ddcname_input = mini.get("ddcname");
			
		    var ddctype_input = document.getElementById("ddctype") ||{};
			//
			// 获取基础数据
			var fromdatabaseid = fromdatabaseid_input.getValue();
			var ddcname = ddcname_input.getValue();
        	var ddctype = ddctype_input.value || "";
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
				alert("请勾选需要导出的数据表");
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
			//
			var len = rows.length;
			if(1 == len && mainTablesCount < 1){
				alert("单个表的导出必须选主表");
				return false;
			}
			// 判断 ddtorder
			var invalidddtorder = false;
			$.each(rows, function(i, v){
				if(!v){
					return;
				}
				//
				var ddtorder = v.ddtorder;
				if(!ddtorder){
					invalidddtorder = true;
					//return false; // 停止迭代
				}
			});
			if(invalidddtorder){
				alert("必须填写导出顺序");
				return false;
			}
			
			// 表
			var tablelist = mini.encode(rows);
			//
			var ddcid = generateDDCID(ddctype);
			//
			var data = {
				fromdatabaseid : fromdatabaseid
				, ddcname : ddcname
				, ddctype : ddctype
				, ddcid : ddcid
				, tablelist  : tablelist
			};
			// 界面
			grid.loading("保存中，请稍候......");
			//
			var  url = "<%=path%>/dbms/ajax/adddbschemeexp.json";
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
		        async:false,
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