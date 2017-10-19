<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePathLessSlash = request.getScheme()+"://"+request.getServerName()
						+":"+request.getServerPort()+path ; // 少1个斜线
	String basePath = basePathLessSlash + "/";
%>
<html>
	<head>
		<title>新增离线数据导出请求</title>
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
		        overflow:hidden;
		    }
		    .hide{
		    	visibility: hidden;
		    }
	    </style>
	</head>

	<body >
	<%--
		按钮是用于新增任务的.
	 --%>
	<input type="hidden" id="hiddenconditionval" name="hiddenconditionval"/>
	<input type="hidden" id="hiddenconditiontext"/>
	<div class="mini-toolbar" style="padding:2px;border:1;">
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="left">
					&nbsp;&nbsp;&nbsp;&nbsp;
						请选择数据导出方案：			             
		  				<input id="search_ddcid" name="search_ddcid" class="mini-combobox" 
		  					style="width:120px;" popupWidth="200" 
		  					  showNullItem="true" emptyText="请选择导出方案" nullItemText=""
			                	textField="ddcname" valueField="ddcid"
								url="<%=path %>/dbms/ajax/listdbscheme.json?ddctype=1" onvaluechanged="schemeChange"
		  				 />&nbsp;&nbsp;
					<a class="mini-button" iconCls="icon-find" onclick="showFilter()" plain="true">条件过滤</a>
					<a class="mini-button" iconCls="icon-find" onclick="showCaseFilter()" plain="true">案件过滤</a>
					<input name="chkpackageper" id="chkpackageper" class="mini-checkbox"
							 text="按罪犯分包导出" value="0" trueValue="1" falseValue="0" />					
					<%--
					
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="depart" id="depart" style="width:180px;" class="mini-treeselect" url="<%=path %>/dbms/ajax/ajaxGetExportdepart.json?1=1" 
						emptyText="请选择导出部门..." textField="name" parentField="porgid" valueField="orgid"/>	
					
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="mini-button" iconCls="icon-date" onclick="showDataSample()" plain="true">查看样例数据</a>--%>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="mini-button" iconCls="icon-ok" onclick="addExportDataRequest()" plain="true">导出</a>
					&nbsp;&nbsp;
					<a class="mini-button" iconCls="icon-cancel" onclick="cancelAdd()" plain="true">取消</a>
					</td>
					<td align="right">
						&nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-reload" 
							onclick="refreshPage()"	plain="true">刷新</a>
					</td>
				</tr>
			</table>
		 </div>
    </div>
    
	<div class="mini-toolbar  filter_div  hide"  style="padding:2px;border:1;">
	     <div class="crud-search">
			<table style="width:98%;">
				<tr>
					<td align="left">
					&nbsp;&nbsp;&nbsp;&nbsp;
						<b>过滤条件：</b>
		  				 <div id="filter_column" name="filter_column" class="mini-combobox" style="width:150px;"
		  				 	  showNullItem="true" emptyText="请选择..." nullItemText="请选择..."
		  				    popupWidth="300" textField="dcdfromcolumnsscribe" valueField="dcdfromcolumns" >     
						    <div property="columns">
						        <div header="条件" field="dcdfromcolumnsscribe"  headerAlign="center" align="left" ></div>
						        <div header="列名" field="dcdfromcolumns"  headerAlign="center" align="left" ></div>
						    </div>
						</div>
		  				 
		  				 &nbsp;&nbsp;
		  				<input id="filter_relation" name="filter_relation" class="mini-combobox" 
		  				  showNullItem="true" emptyText="请选择..." nullItemText="请选择..."
		  					style="width:120px;" popupWidth="200" data="datarelation" value=""/>
		  				 &nbsp;&nbsp;
						<input id="filter_value" name="filter_value" class="mini-textbox" />
						&nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-add" onclick="addCondition();" plain="true">增加条件</a>
						&nbsp;&nbsp;&nbsp;
						<a class="mini-button" iconCls="icon-cut" onclick="clearCondition();" plain="true">清空过滤条件 </a>
						&nbsp;&nbsp;&nbsp;
						<!-- <input name="chkpackageper" id="chkpackageper" class="mini-checkbox"
							 text="按罪犯分包导出" value="0" trueValue="1" falseValue="0" /> -->
						<a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
					</td>
					<td align="right">
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		 </div>
	     <div id="datagrid_condition" style="width:100%;height:100px;" class="mini-datagrid" 
	    	pageSize="2000"
	    	allowResize="false" idField="dcdtocolumns" multiSelect="true" allowUnselect="false"
	    	showPager="false" showLoading="true" enableHotTrack="false" allowRowSelect="true"
	    	allowCellEdit="true" allowCellSelect="true"  showFilterRow="false" onlyCheckSelection="false"
	    	>
	        <div property="columns">
               		<div field="conditionname" name="conditionname" renderer="onConditionnameRenderer" width="60" headerAlign="center" align="center"  allowSort="true">
               			条件
               		</div>
	        		<div field="relationtype" name="relationtype" renderer="onRelationtypeRenderer" width="40" headerAlign="center" align="left"  allowSort="true">
               			关系
               		</div>
               		<div field="conditionvalue" name="conditionvalue" width="50" headerAlign="center" align="center"  allowSort="true">
               			值
               		</div>
               		<div field="_sp" name="_sp" width="1" headerAlign="center" align="left"></div>
               		<div width="60" headerAlign="center" align="center"	allowSort="false" renderer="onActionRenderer">操作</div>
               		<div field="_sp" name="_sp" width="180" headerAlign="center" align="left"></div>
	        </div>
	    </div>
    </div>
    <div id="datagrid_list" style="width:100%;height:68.6%;" class="mini-datagrid datagrid_div hide" 
	    	pageSize="20" sizeList="[20,50,100]"
	    	allowResize="false" idField="dcdtocolumns" multiSelect="true" 
	    	>
	        <div property="columns">
	        		<div type="indexcolumn">序号</div>
            		<div type="checkcolumn" ></div>  
               		<div field="crimid" name="crimid" width="60" headerAlign="center" align="center"  allowSort="true">
               			编号
               		</div>
	        		<div field="name" name="name" width="40" headerAlign="center" align="left"  allowSort="true">
               			姓名
               		</div>
               		<div field="inprisondate" name="inprisondate" width="50" headerAlign="center" align="center"  allowSort="true">
               			入间日期
               		</div>
               		<div field="orgname2" name="orgname2" width="50" headerAlign="center" align="center"  allowSort="true">
               			单位
               		</div>
               		<div field="_sp" name="_sp" width="180" headerAlign="center" align="left"></div>
	        </div>
	    </div>	
    <div class="mini-fit hide">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" 
	    	url="<%=path %>/dbms/ajax/listdataexport.json" 
	    	idField="ddcid" sortField="ddcid" sortOrder="asc"
	    	allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]"
	    	pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
               		<div field="ddcname" name="ddcname" width="50" headerAlign="center" align="center"  allowSort="true">
               			数据方案
               		</div>
	        		<div field="filerealname" name="filerealname" width="80" headerAlign="center" align="left"  allowSort="true">
               			文件名称
               		</div>
               		<div field="createdate" name="createdate" renderer="onDateRenderer"
               			 width="40" headerAlign="center" align="center"  allowSort="true">
               			创建日期
               		</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
		var datarelation = [
				{id:"=",text:"等于"}
				,{id:"!=",text:"不等于"}
				,{id:"like",text:"包含"}
				,{id:">",text:"大于"}
				,{id:"<",text:"小于"}
				,{id:">=",text:"大于等于"}
				,{id:"<=",text:"小于等于"}
				,{id:"in",text:"in,示例: 'a','b','c'"}
			];
		var caseFilterTypes = [
				{dcdfromcolumns:"batchyear",dcdfromcolumnsscribe:"案件年"}
				,{dcdfromcolumns:"batchno",dcdfromcolumnsscribe:"批次"}
				,{dcdfromcolumns:"batchrange",dcdfromcolumnsscribe:"案件号"}
			];
        mini.parse();
        var datagrid_condition = mini.get("datagrid_condition");
        var grid = mini.get("datagrid1");
        //grid.load();
        
        ////////////////////////
        //
        function onConditionnameRenderer(e) {
        	var record = e.record || {};
        	//
        	var conditionname = record["conditionname"] || "";
        	var conditiontext = record["conditiontext"] || "";
        	//
        	return conditiontext.trim() || conditionname.trim();
        };
        //
        function onRelationtypeRenderer(e) {
        	var record = e.record || {};
        	//
        	var relationtype = record["relationtype"] || "";
        	var relationtext = record["relationtext"] || "";
        	//
        	return relationtext.trim() || relationtype.trim();
        }
        // 渲染操作列
        function onActionRenderer(e) {
            var s = '&nbsp;&nbsp;&nbsp;&nbsp;<a class="" href="javascript:deleteRow();">删除</a>'; 
            return s;
        };
        // 检查并获取导出方案ID
		function getAndCheckDdcid(showalert){
			var $search_ddcid = mini.get("search_ddcid");
	        // 变量会自动提前
			var search_ddcid = null;
		    if($search_ddcid){
	        	search_ddcid = $search_ddcid.getValue();
		    }
        	// 
        	if(!search_ddcid && showalert){
        		alert("请选择导出方案!");
        		$search_ddcid.focus();
        	}
        	return search_ddcid;
		};
		function getqueryUUID(){
			var queryuuid =  new Date().getTime().toString(16) + ""+Math.random().toString(16);
			//
			return queryuuid;
		};
		
		function schemeChange() {
		    refreshFilterCondition();
		};
		// 更新下拉框的值 ...
		function refreshFilterCondition() {
		    var ddcid = getAndCheckDdcid();
		    var searchDdctype = mini.get("filter_column");
		    searchDdctype.setValue(""); 
			var url = "<%=path %>/dbms/ajax/ajaxGetcolumnByddcid.json?ddcid=" + ddcid;
			searchDdctype.setUrl(url);
		};
		// 案件过滤更新下拉框的值 ...
		function refreshCaseFilterCondition() {
		    var ddcid = getAndCheckDdcid();
		    var searchDdctype = mini.get("filter_column");
		    searchDdctype.setValue(""); 
			searchDdctype.setData(caseFilterTypes);
		};
        // 显示过滤模块
        function showFilter(){
        	var $filter_div = $(".filter_div");
        	//
        	var search_ddcid = getAndCheckDdcid(1);
        	//
        	if(search_ddcid){
	        	// 显示过滤条件输入区域.
	        	$filter_div.removeClass("hide");
	        	// ...
	        	// 更新下拉框的值 ...
	        	refreshFilterCondition();
        	}
        };
        // 显示案件过滤模块
        function showCaseFilter(){
        	var $filter_div = $(".filter_div");
        	//
        	var search_ddcid = getAndCheckDdcid(1);
        	//
        	if(search_ddcid){
	        	// 显示过滤条件输入区域.
	        	$filter_div.removeClass("hide");
	        	// ...
	        	// 更新下拉框的值 ...
	        	refreshCaseFilterCondition();
        	}
        };
		// 添加条件
		function addCondition(){
			//
			var filter_column = mini.get("filter_column");
			var filter_relation = mini.get("filter_relation");
			var filter_value = mini.get("filter_value");
			//
			if(!filter_column || !filter_relation || !filter_value){
				alert("页面错误,请刷新重试!");
				return;
			}
			//
			var conditionname = filter_column.getValue() || "";
			var conditiontext = filter_column.getText() || "";
			var relationtype  = filter_relation.getValue() || "";
			var relationtext  = filter_relation.getText() || "";
			var conditionvalue = filter_value.getValue() || "";
			//
			if(!conditionname){
				alert("请选择过滤条件!");
				//
				filter_column.focus();
				return;
			}
			if(!relationtype){
				alert("请选择过滤关系!");
				//
				filter_relation.focus();
				return;
			}
			if(!conditionvalue){
				alert("请设置条件!");
				//
				filter_value.focus();
				return;
			}
			//
			var row = {
				conditionname  : conditionname		// 条件
				,conditiontext : conditiontext		// 条件Text 
				,relationtype  : relationtype		// 关系
				,relationtext : relationtext		// 关系Text
				,conditionvalue : conditionvalue	// 值
			};
			//
			datagrid_condition.addRow(row);
			// 清空所选内容
			filter_value.setValue("");
			filter_value.text = ("");
		};
		// 删除单行
		function deleteRow(){
		    var row = datagrid_condition.getSelected();
		    //
		    if (row) {
		    	var del = confirm("确实要删除吗?");
		    	if(del){
		        	datagrid_condition && datagrid_condition.removeRow(row,true);
		    	}
		    }
		};
		function clearCondition() {
			// 清除所有行（Javascript）
	    	var del = confirm("确实要清空过滤条件吗?");
	    	if(del){
				datagrid_condition && datagrid_condition.clearRows();
	    	}
		};
		// 获取查询条件
		function getConditionmessage(returnArray){
			var result = !!returnArray ? []:"";
			// 
			if(!datagrid_condition){
				return result;
			}
			//
			var rows = datagrid_condition.getData();
			if(!rows || !rows.length){
				return result;
			}
			if(!!returnArray){
				return rows;
			}
			//
			result = "";
			$.each(rows, function(i, v){
				if(!v){
					return;
				}
				//
				var conditionname = v.conditionname || "";
				var relationtype = v.relationtype || "";
				var conditionvalue = v.conditionvalue || "";
				// 根据 relationtype 进行判断
				if (result != "") {
			        result += " AND ";
			    }				
				if("like" == relationtype.trim()){
					result += "" + conditionname + " " + relationtype + " '%" + conditionvalue + "%'" ;
				} else if("in" == relationtype.trim()){
					result += "" + conditionname + " " + relationtype + " (" + conditionvalue + ")";
				} else {
					result += "" + conditionname + " " + relationtype + " '" + conditionvalue + "'";
				}
			});
			//
			return result;
		};
		//
		// 选择罪犯获取案件导出查询条件
		function getCaseConditionmessage(returnArray){
			var datagrid_list = mini.get("datagrid_list");
			var result = !!returnArray ? []:"";
			// 
			if(!datagrid_list){
				return result;
			}
			//
			var rows = datagrid_list.getSelecteds();
			if(!rows || !rows.length){
				return result;
			}
			if(!!returnArray){
				return rows;
			}
			//
			result = "";
			$.each(rows, function(i, v){
				if(!v){
					return;
				}
				//
				var conditionvalue = v.crimid || "";
				// 根据 relationtype 进行判断
				if (result != "") {
			        result += " AND ";
			    }				
				result += "crimid = " + " '" + conditionvalue + "'";
			});
			//
			return result;
		};
		//
		function showDataSample(){
			alert("暂时不支持");
			// 使用  iframe+JSP来拼
		};
		
		// 添加导出请求
		function addExportDataRequest(){
			//
			var search_ddcid = getAndCheckDdcid(1);
			if(!search_ddcid){
				return false;
			}
			// 将 queryuuid 作为key,查询进度
			var queryuuid =  getqueryUUID();
			window.queryuuid = queryuuid;
			//
		    //var depart = mini.get("depart").getValue();
		    var chkpackageper = mini.get("chkpackageper").getValue();
			//
			//var conditionmessage = getConditionmessage();
			var conditionmessage = getCaseConditionmessage();
			//
		    var data =  {
		    	ddcid: search_ddcid
		    	, queryuuid : queryuuid
		    	, conditionmessage : conditionmessage
		    	//, depart : depart
		    	, chkpackageper : chkpackageper
		    };
			//
			var  url = "<%=path%>/dbms/ajax/adddataexport.json";
			var successCallback = function (message) {
			       //
	        	   var info = message["info"];
	        	   //alert(info);
	               if(1 === message["status"]){
	            	   // 跳转到前一个页面
	            	   //goBack();
	            	   startQueryProgress(queryuuid);
	               } else {
	            	   alert(info);
	               }
	               return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("保存失败");
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
		//
		var hasLoadFrom = false;
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
		
        //
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
        	}
            return "";
        };
		function schemeChange_OLD() {
		    var searchDdctype = mini.get("filter_column");
		    var search_ddcid = mini.get("search_ddcid");
		    var ddcid = search_ddcid.getValue();
		    searchDdctype.setValue(""); 
			var url = "<%=path %>/dbms/ajax/ajaxGetcolumnByddcid.json?ddcid=" + ddcid;
			searchDdctype.setUrl(url);
		};
		
		function addCondition_OLD(){
			var condition = document.getElementById("conditionmessage");
			var hiddenconditionval = document.getElementById("hiddenconditionval").value;
			var hiddenconditiontext = document.getElementById("hiddenconditiontext").value;
			var columnval = mini.get("filter_column").getValue();
			var columntext = mini.get("filter_column").text;
			var relationval = mini.get("filter_relation").getValue();
			var relationtext = mini.get("filter_relation").text;
			var inputkeyval = document.getElementById("filter_value").value;
			var showtext = "";
			var conditionval = "";
			if(columnval!="" && relationval!="" && inputkeyval!="") {
				conditionval = conditionval + columnval + relationval + "'" + inputkeyval + "'";
				showtext = showtext + "         " + "查询条件：" + columntext + relationtext + inputkeyval;
			}
			if(conditionval=="") {
				return;
			}
			if(hiddenconditionval!="") {
				hiddenconditionval = hiddenconditionval + ";" + conditionval;
				hiddenconditiontext = hiddenconditiontext + ";" + showtext;			
			} else {
				hiddenconditionval = conditionval;
				hiddenconditiontext = showtext;			
			}
			document.getElementById("hiddenconditionval").value = hiddenconditionval;
			document.getElementById("hiddenconditiontext").value = hiddenconditiontext;
			condition.innerText=hiddenconditiontext;
		};
		
		function removecondition_OLD() {
			mini.get("filter_column").setValue("");
			mini.get("filter_relation").setValue("");
			document.getElementById("filter_value").value="";
			var condition = document.getElementById("conditionmessage");
			condition.innerText="";
			document.getElementById("hiddenconditionval").value="";
			document.getElementById("hiddenconditiontext").value="";
		};
		//列表查询
		function search(){
			var $datagrid_div = $(".datagrid_div");
        	//
        	var search_ddcid = getAndCheckDdcid(1);
        	//
        	if(search_ddcid){
	        	// 显示过滤条件输入区域.
	        	$datagrid_div.removeClass("hide");
	        }
        	var datagrid_list = mini.get("datagrid_list");
        	var conditionmessage = getConditionmessage();
        	datagrid_list.setUrl("ajax/ajaxExportDataList.json?1=1&conditionmessage="+conditionmessage);
        	datagrid_list.load();
		}
			// 开始查询进度
	    function startQueryProgress(queryuuid){
			// 显示遮罩
	        refreshProgress('?', "0", "正在执行...", 0);
			//
		    var data =  {
		    	queryuuid : queryuuid
		    };
			//
			var  url = "<%=path %>/dbms/ajax/queryprogress.json";
			var successCallback = function (message) {
			       //
	        	   //var info = message["info"];
	        	   //alert(info);
	               if(1 === message["status"]){
	            	   //
	            	   var meta = message["meta"] || {};
	            	   //
	            	   var taskbean = meta["taskbean"] || {};
	            	   //
	            	   var status = taskbean["status"];
            		   var counter = taskbean["counter"];
            		   var sum = taskbean["sum"];
            		   
	            	   var continueAjax = true;
	            	   //
	            	   var info = "正在执行";
	            	   if(3 == status){
	            		   info = ("执行成功");
	            		   continueAjax = false;
	            	   } else if(4 == status){
	            		   info = ("执行失败");
	            		   continueAjax = false;
	            	   } else if(5 == status){
	            		   info = ("正在分发数据..");
	            	   } else {
	            		   info = ("正在执行");
	            	   }
            		   // 更新进度
            		   refreshProgress(sum, counter, info, status);
	            	   if(continueAjax){
		            	   // 继续查询进度
		            	   window.setTimeout(function(){
		            	   		startQueryProgress(queryuuid);
		            	   }, 3*1000);
	            	   } else {
	            		   // 发请求清理进度缓存...
	            		   clearProgress(queryuuid);
	            	   }
	               } else {
	               }
	               return false;
			};
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			        alert("执行失败");
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
	    };
		function refreshProgress(sum, counter, info, status){
	    	// 刷新进度.
	    	if(!sum){
	    		sum = "?";
	    	}
	    	if(!counter){
	    		counter = "0";
	    	}
	    	if(!info){
	    		info = "正在执行!";
	    	}
	    	if(3 == status || 4 == status){
	    		// 执行完成,成功/失败
	    		alert(info);
	            mini.unmask(document.body);
	    	} else {
	    		var tip = (info+ "; 进度： "+ counter + "/总计： "+sum);
				mini.mask( {
					el : document.body,
					cls : 'mini-mask-loading',
					html : tip
				});
	    	}
	    	//
	    };
	    function clearProgress(queryuuid){
	    	// 发请求清理进度缓存...
	    	// 暂时未实现
	    	window["console"]&&console.info("queryuuid="+queryuuid+";暂未处理清理进度缓存.");
	    };
	    
	</script>
	</body>
</html>	