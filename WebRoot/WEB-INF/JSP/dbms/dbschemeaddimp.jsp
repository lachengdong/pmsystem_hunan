<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";

%>

<html>
	<head>
		<title>新增数据导入方案</title>
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
							<option value="0" selected="selected">数据导入</option>
			             </select>
		  				&nbsp;&nbsp;&nbsp;&nbsp;
		  				对应导出方案：
		  				 <input id="ddcexpscheme" name="ddcexpscheme" class="mini-combobox" 
		  					style="width:120px;" popupWidth="200" 
			                	textField="ddcname" valueField="ddcid"
								url="<%=path %>/dbms/ajax/listdbscheme.json?ddctype=1"
		  				 />
		  				&nbsp;&nbsp;&nbsp;&nbsp;
		  				方案名称：
		  				<input class="mini-textbox" type="text" width="140"
		  				 id="ddcname" name="ddcname" size="26"/>
			            &nbsp;&nbsp;
		  				导入数据库：
		  				<input id="todatabaseid" name="todatabaseid" class="mini-combobox" 
		  						style="width:120px;"   popupWidth="200" 
			                	textField="ddname" valueField="ddid"
								url="<%=path %>/dbms/ajax/listalldb.json"
		  				 />
		  				&nbsp;&nbsp;
		 				<a class="mini-button" iconCls="icon-search" 
		 					onclick="listFromTable()" plain="true">加载相关表</a>
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
	        		<div type="indexcolumn" width="5"></div>
	        		
	        	<div header="导出表信息" headerAlign="center">
	        	  <div property="columns">
               		<div field="tablename" name="tablename"
               		 	width="55" headerAlign="center" align="left"
               			renderer="onTableNameRender" >
               			导出表名
               		</div>
               		<div field="descrition" name="descrition" renderer="onDescriptionRender"
               			 width="40" headerAlign="center" align="left"  >
               			导出表描述
               		</div>
               		</div>
               	</div> <!-- end导出表信息 -->
               	
               	<div field="_sp" name="_sp"
               		 	width="1" headerAlign="center" align="left">
               	</div>
	        	<div header="导入表信息" headerAlign="center">
	        	  <div property="columns">
               		<div field="totable" name="totable"
               		 	width="55" headerAlign="center" align="left" >
               			导入表名
               			<input id="totableinput" name="totableinput"  property="editor" class="mini-combobox" style="width:100%;" 
               			 textField="table_name" valueField="table_name" 
               			 onclick="toTableListClick"
               			 showNullItem="true" allowInput="true"/>   
               		</div>
               		<div field="todescription" name="todescription" renderer="onToDescriptionRender"
               			 width="40" headerAlign="center" align="left"  >
               			导入表描述
               			<input property="editor" class="mini-textbox" style="width:100%;"/>
               		</div>
               		
               	  </div>
               	</div> <!-- 导入表信息 -->
               	<div field="_sp" name="_sp"
               		 	width="1" headerAlign="center" align="left">
               	</div>
           		 <!-- end导入表信息 -->
           		<div field="ddtismaintable" name="ddtismaintable" 
           			 width="10" headerAlign="center" align="center" 
           			 renderer="onMainTableRender" >
           			是主表
           			<input property="editor" style="width:100%;" class="mini-combobox" data="ddtismaintableArray"/>
           		</div>
           		<div field="shujuguanxi" name="shujuguanxi" width="35"
           		 	headerAlign="center" align="left" >
           			数据关系
           			<input property="editor" class="mini-textbox" style="width:100%;"/>
           		</div>
	        </div>
	    </div>
    </div>
	
	
    <script type="text/javascript">
    	//
    	var ddtismaintableArray = [{id: '0', text: '是'},{id: '1', text: '否'}];
    	// 在 parse之前将数据取出来
    	var toTableArray = [];
    	
    	//
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.on("load", onGridLoaded);
        grid.on("cellcommitedit", onToTableChanged);
        //grid.load();
        ////////////////////////
        var toTableList = [];
        function onGridLoaded(e){
        	// 获得 fromdatabaseid
			var todatabaseid_input = mini.get("todatabaseid");
			var todatabaseid = todatabaseid_input.getValue();
			if(!todatabaseid){
				alert("请选择导入数据库");
				return;
			}
			//
			var  url = "<%=path%>/dbms/ajax/listdbtables.json?fromdatabaseid="+ todatabaseid;
			/*
			var  tableURL = url + "&onlydata=1"; // 只要data部分
			
			// 设置totableinput的URL信息
		    var totableinput = mini.get("totableinput");
			//totableinput.setUrl(url);
			totableinput && totableinput.load(tableURL);
			*/
			toTableListClick();
        	// 加载to表数据
			var data = {
			};
			var successCallback = function (message) {
	        	   var info = message["info"];
	        	   var data = message["data"];
	               grid.unmask();
	               if(1 === message["status"]){
	            	   // 处理数据
        			   //
        			   if(data){
        				   toTableList = data;
        				   // 遍历, 循环设值
        				   // ...
        				   setGridToData(data);
        			   }
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("导入表信息加载失败 ^~^ ");
	               	grid.unmask();
			    };
			//
			// 界面
			grid.loading("正在加载信息，请稍候......");
			requestAjax(url,data,successCallback,errotCallback);
        };
        //
        function toTableListClick(e){
			var todatabaseid_input = mini.get("todatabaseid");
		    var totableinput = mini.get("totableinput");
		    
			var todatabaseid = todatabaseid_input.getValue();
			if(!todatabaseid){
				alert("请选择导入数据库");
				return;
			}
			if(hasCacheTable && totableinput && totableinput.data && totableinput.data.length > 0){
				return;//不继续处理
			}
			//
			var  url = "<%=path%>/dbms/ajax/listdbtables.json?fromdatabaseid="+ todatabaseid;
			var  tableURL = url + "&onlydata=1"; // 只要data部分
			
			// 设置totableinput的URL信息
			//totableinput.setUrl(url);
			//totableinput && totableinput.load(url);
			totableinput && totableinput.load(tableURL);
			hasCacheTable = true;
        };
        //
        function onToTableChanged(e){
        	//
        	var column = e.column || {};
        	var name = column.name;
        	if("totable" != name){
        		// 其他列,忽略
        		return;
        	}
        	// 改变后的值
        	var oldValue = e.oldValue;
        	var value = e.value;
        	// 找到事件发生的当前行
        	var row = e.row;
        	//
        	var fromcol = {"totable": value};
        	// 遍历, 找到 from 列
        	$.each(toTableList, function(i, v){
        		//
        		var totable = v["table_name"];
        		if(totable && totable==value){
        			//
        			fromcol = v;
        			//
        			return false;
        		}
        	});
        	//
        	//
        	updataToTableRow(fromcol,row);
        };
        //
        function setGridToData(toTables){
        	if(!toTables){
        		return setGridToDataCopy();
        	}
        	// 遍历
        	$.each(toTables, function(i, v){
        		updataToTableRow(v);
        	});
        };
        // 设置为和导出表一样?
        function setGridToDataCopy(){
        	// 暂不执行操作....
        	alert("没有请求到数据!请检查导入库");
        };
        // 更新 from信息
        function updataToTableRow(toTable, row){
        	if(!toTable){
        		return;
        	}
        	if(!row){
        		// 找到对应的列
        		row = findRowByToTable(toTable);
        	}
        	if(!row){
        		return;
        	}       	
        	//
        	var totable = toTable["table_name"];
        	var todescription = toTable["remarks"];
        	
        	// 处理数据, 
        	var nData = {
        		totable : totable || ""
        		,todescription : todescription || ""
        	};
        	//
        	grid.updateRow ( row, nData);
        };
        // 去除两端空格,忽略大小写比较
        function trimCaseEqual(str1, str2){
        	//
        	if(!str1 || !str1){
        		return false;
        	}
        	//
       		str1 = ""+str1;
       		str2 = ""+str2;
       		str1 = str1.toUpperCase().trim();
       		str2 = str2.toUpperCase().trim();
       		//
        	return str1 == str2;
        };
        // 根据from列，找出Grid中对应的列
        function findRowByToTable(toTable){
        	if(!toTable){
        		return null;
        	}
        	//
        	var toTableName = toTable["table_name"];
        	if(!toTableName){
        		return null;
        	}
        	// 原数据
        	//var gridData = grid.getData();
        	//
        	// 取得对应行
			var row = grid.findRow(function(row2){
				//
				var name2 = row2["tablename"];
				//
				if(trimCaseEqual(name2,toTableName)){
					return true;
				}
			});
        	//
        	return row;
        };
        ////////////////////////
    	//
    	function onTableNameRender(e){
    		 var record = e.record;
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
			var todatabaseid_input = mini.get("todatabaseid");
			var ddcname_input = mini.get("ddcname");
			var ddcexpscheme_input = mini.get("ddcexpscheme");
			//
			var ddcexpscheme = ddcexpscheme_input.getValue();
			var todatabaseid = todatabaseid_input.getValue();
			//
    		tablename = encodeURIComponent(tablename) || {};
    		totable = encodeURIComponent(totable) || {};
    		var databaseid = todatabaseid_input.getValue() || {};
    		//
    		var w_w = window.top.document.body.clientWidth || 1000;
    		var w_h = window.top.document.body.clientHeight || 600;
    		
    		//
    		var url = "<%=path%>/dbms/selectcolumnimport.page?1=1" 
    		        + "&totablename="+ totable + "&todatabaseid="+todatabaseid
    		        + "&fromtablename=" + tablename + "&ddcexpscheme=" + ddcexpscheme
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
		 				//grid.select(row, true);
                    } else {
                    	alert("不能确定列");
                    }
                    
                }
            });
    		//
    		return false;
    	};
    	//
    	// 选择列
    	function onDescriptionRender(e){
    		 //
    		 var record = e.record || {};
    		 var descrition = record.descrition || record.remarks || "";
    		 
    		 record.descrition = descrition;
        	 return record.descrition;
    	};
    	// 选择列
    	function onToDescriptionRender(e){
    		 //
    		 var record = e.record || {};
    		 var todescription = record.todescription || "";
    		 
    		 record.todescription = todescription;
        	 return record.todescription;
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
		var hasCacheTable = false;
		function listFromTable() {
			var ddcexpscheme_input = mini.get("ddcexpscheme");
			var todatabaseid_input = mini.get("todatabaseid");
			var todatabaseid = todatabaseid_input.getValue();
			//
			var ddcexpscheme = ddcexpscheme_input.getValue();
		    var param =  {
		    	ddcexpscheme: ddcexpscheme
		    };
		    // 
		    if(!ddcexpscheme){
		    	alert("请选择导出方案");
		    	return;
		    }
		    // 
		    if(!todatabaseid){
		    	alert("请选择导入数据库");
		    	return;
		    }
	    	if(hasLoadFrom){
	    		var cfm = confirm("重新加载导出数据库的表信息?");
	    	} else {
	    		hasLoadFrom = true;
	    		cfm = true;
	    	}
	    	if(cfm){
		    	grid.load(param);
		    	hasCacheTable = false;
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
		// 编辑
        function onImpDBClicked(e) {
			//
		    var row = grid.getSelected();
		    if (!row) {
		    	alert("没有选择记录!");
		    	return;
		    }
		    //
		    var userid = row.userid;
		    //
            var btnEdit = this;
            mini.open({
                url: "<%=path%>/user/getOrgByUser.page?1=1" + "&userid="+userid,
                showMaxButton: false,
                title: "选择部门",
                width: 350,
                height: 350,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        if (data) {
                        	var orgname = data.name || "";
                        	var orgid = data.orgid || "";
                            btnEdit.setValue(orgname);
                            btnEdit.setText(orgname);
                            if(row.organization){
                            	row.organization.name = orgname;
                            	row.organization.orgid = orgid;
                            } else {
	                            row.orgname = orgname;
	                            row.orgid = orgid;
                            }
                        }
                    }
                }
            });   
        };
		
		// 保存数据
		function saveData() {
			//
			var todatabaseid_input = mini.get("todatabaseid");
			var ddcname_input = mini.get("ddcname");
			var ddcexpscheme_input = mini.get("ddcexpscheme");
			//
			// 获取基础数据
			var todatabaseid = todatabaseid_input.getValue();
			var ddcname = ddcname_input.getValue();
        	var ddcexpscheme = ddcexpscheme_input.getValue() || "";
        	var ddctype = "0"; // 0, 数据导入
			//
			if(!ddctype){
				alert("请选择方案类型");
				return;
			}
			if(!ddcexpscheme){
				alert("请选择对应的导出方案");
				return;
			}
			if(!hasLoadFrom){
				alert("请显示导出表并核对");
				return;
	    	}
			if(!ddcname){
				alert("请输入方案名称");
				ddcname_input.focus();
				return;
			}
			if(!todatabaseid){
				alert("请选择导入数据库");
				return;
			}
			//
			var rows = grid.getData();
			//
			if(!rows || !rows.length){
				alert("没有导入的数据表");
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
						//console.dir(r);
						hasNotColumn ++;
						tableHasNotColumn.push(r.tablename);
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
				todatabaseid : todatabaseid
				, ddcname : ddcname
				, ddctype : ddctype
				, ddcid : ddcid
				, ddcexpscheme  : ddcexpscheme
				, tablelist  : tablelist
			};
			// 界面
			grid.loading("保存中，请稍候......");
			//
			var  url = "<%=path%>/dbms/ajax/adddbschemeimp.json";
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