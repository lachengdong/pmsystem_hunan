<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<html>
	<head>
		<title>拷贝数据交换方案</title>
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
					<a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">拷贝</a>
					&nbsp;&nbsp;
					<a class="mini-button" iconCls="icon-cancel" onclick="cancelAdd()" plain="true">取消</a>
					&nbsp;&nbsp;
		  				源方案:
		  				<input id="sourcescheme" name="sourcescheme" class="mini-combobox" 
		  					style="width:130px;" popupWidth="200" 
			                	textField="ddcname" valueField="ddcid"
								url="<%=path %>/dbms/ajax/listdbscheme.json?ddctype=2&iscopymode=1"
		  				 />
			            &nbsp;&nbsp;
		 				<a class="mini-button" iconCls="icon-node" 
		 					onclick="listFromTable()" plain="true">查看</a>
			            &nbsp;&nbsp;
		  				新方案名称：
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
		  				所属机构：
		  				 <input id="orgname" name="orgname" class="mini-buttonedit" onbuttonclick="onOrgNameEdit"/>
						 <input id="sdid" name="sdid" class="mini-hidden"/>
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
	    	allowCellEdit="false" allowCellSelect="true"  showFilterRow="false" onlyCheckSelection="true"
	    	>
	        <div property="columns">
	        		<div type="indexcolumn" width="10"></div>   
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
    		 return tablename;
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
        		ddcid += "SWP";
        	} else {
        		ddcid += "";
        	}
        	//
        	return ddcid;
        };
        
		// 查询导出列表
		var hasLoadFrom = false;
		function listFromTable() {
			//
			var sourcescheme_input = mini.get("sourcescheme");
			if(!sourcescheme_input){
				alert("页面错误: sourcescheme 不存在");
				return;
			}
			var sourcescheme = sourcescheme_input.getValue();
			if(!sourcescheme){
				alert("请选择源方案");
				return;
			}
			// 别管参数名了
		    var param =  {
		    	ddcexpscheme: sourcescheme
		    };
	    	grid.load(param);
    		hasLoadFrom = true;
		};
		
		// 编辑部门
        function onOrgNameEdit(e) {
			//
			var orgname_input = mini.get("orgname");
			var sdid_input = mini.get("sdid");
			if(!orgname_input || !sdid_input){
				//
				alert("页面错误!");
				return;
			}
		    //
            var btnEdit = this;
            mini.open({
                url: "<%=path%>/user/getOrgByUser.page?1=1",
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
                        	var sdid = data.orgid || "";
                        	//
                            btnEdit.setValue(orgname);
                            btnEdit.setText(orgname);
                            //
                            sdid_input.setValue(sdid);
                        }
                    }
                }
            });   
        };
		
		
		// 保存数据
		function saveData() {
			//
			var fromdatabaseid_input = mini.get("fromdatabaseid") ||{};
			var todatabaseid_input = mini.get("todatabaseid") ||{};
			var ddcname_input = mini.get("ddcname") ||{};
			var sourcescheme_input = mini.get("sourcescheme");
			var sdid_input = mini.get("sdid");
			//
			if(!fromdatabaseid_input || !todatabaseid_input || !ddcname_input || !sourcescheme_input){
				alert("页面错误");
				return;
			}
		    
			// 获取基础数据
			var fromdatabaseid = fromdatabaseid_input.getValue();
			var todatabaseid = todatabaseid_input.getValue();
			var ddcname = ddcname_input.getValue();
			var sourcescheme = sourcescheme_input.getValue();
			var sdid = sdid_input.getValue();
			//
        	var ddctype = "2";//ddctype_input.value;
			//
			if(!sourcescheme){
				alert("请选择源方案");
				return;
			}
			if(!ddcname){
				alert("请输入新方案名称");
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
			if(!sdid){
				alert("请选择所属机构");
				return;
			}
			if(!ddctype){
				alert("请选择方案类型");
				return;
			}
			if(!hasLoadFrom){
				alert("请查看是否是需要的方案");
				return;
	    	}
			//
			var ddcid = generateDDCID(ddctype);
			//
			var data = {
				fromdatabaseid : fromdatabaseid
				, todatabaseid : todatabaseid
				, ddcname : ddcname
				, ddctype : ddctype
				, ddcid : ddcid
				, sourcescheme  : sourcescheme
				, sdid  : sdid
			};
			// 界面
			grid.loading("正在拷贝,请稍候......");
			//
			var  url = "<%=path%>/dbms/ajax/copyswapscheme.json";
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