<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<title>案件整理</title>
	<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%; 
			height: 100%;
			overflow: hidden;
		}
	</style>
</head>
<body> 
	<div class="mini-toolbar" style="padding:2px;border-bottom:0px;">
		<table>
			<tr>
				<td style="white-space:nowrap;width: 100%">

					<div id="anhao" name="anhao" class="mini-checkbox" checked="false" readOnly="false" text="重号筛选" onvaluechanged="search">重号筛选</div>

                    <input id="casetype" class="mini-combobox" valueField="id" textField="text" url="<%=path%>/getCodeByCode.json?1=1&codeType=GK7799&pcodeid=GK7799" 
    					style="width:70px;" onvaluechanged="search" />
                    
                    <input class="mini-combobox"  id="xingqileixing"  name="xingqileixing"  style="width:80px;"   emptyText="刑期类型"
    					data="[{ id: 1, text: '有期' }, { id: 2, text: '无期'},{ id: 3, text: '死缓'}]" showNullItem="true" onvaluechanged="search" />
    				
    				<input class="mini-combobox"  id="crimetype"  name="crimetype"  style="width:100px;"   emptyText="罪犯类型" showNullItem="true"
    					data="[{ id: 0, text: '三类罪犯'},{ id: 1, text: '重要罪犯'},{ id: 2, text: '普通罪犯'},{ id: 3, text: '老病残犯'}]"  onvaluechanged="search()" />

                    <input class="mini-combobox"  id="nodeid"  name="nodeid"  style="width:150px;"   emptyText="进程过滤" showNullItem="true" 
                    	url="<%=path%>/jxjs/ajaxGetCaseProcess.json?1=1&flowdefid=other_zfjyjxjssp"  onvaluechanged="search()" />
                                                                  
                                                              第<input class="mini-textbox" id="casenums" name="casenums" class="mini-textbox" style="width:100px;" emptyText="例：1/1,2/1-10" onenter="onKeyEnter" />号
					
					<input name="jianqu" id="jianqu" name="jianqu" class="mini-combobox" style="width:110px;" valueField="ORGID" textField="NAME"
					   emptyText="请选择监区" url="<%=path%>/getDepartList.action?1=1&qtype=jianqu" required="false" showNullItem="true" nullItemText="请选择监区" onvaluechanged="search"/> 
					   
					年度<input class="mini-combobox"  id="caseyear"  name="caseyear"  style="width:70px;"   emptyText="年度" showNullItem="true" value="${caseyear}"
						url="<%=path%>/getDateUPDownArea.action?1=1"  onvaluechanged="search" />
						
					请选择批次<input class="mini-spinner" id="batch"  name="batch"  style="width:40px;" minValue="1" maxValue="200" value="${batch}" />
					
					<input class="mini-textbox" id="key" class="mini-textbox" style="width:85px;" emptyText="编号/姓名/拼音" onenter="onKeyEnter" /> 
					
					<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>&nbsp; 
					
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10095')"></a>
				</td>
			</tr>
			<tr>
			   <td>
					起始案件号<input id="anjianhao" class="mini-spinner" style="width:60px;"  maxValue="20000" /> 
					<a class="mini-button" plain="true" iconCls="icon-edit" plain="true" onclick="changeanjianhao()">批量更改</a>
				</td>
			   <td>
			      
			   </td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="datagrid" allowMoveColumn="false" allowCellEdit="true" style="width:100%;height:100%;" class="mini-datagrid" editNextOnEnterKey="true" allowAlternating="true"
			url="<%=path%>/getCaseLookList.json?1=1&approval=yes&flowdefid=other_zfjyjxjssp" idField="" allowCellEdit="true" allowCellSelect="true" multiSelect="true" sizeList="[10,20,50,100]" pageSize="20">
			<div property="columns">
				<div type="checkcolumn" width="15"></div>
				<div visible="false" field="ccid" name="ccid" id="ccid" headerAlign="center" allowSort="true" align="center">
					<input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div visible="false" field="crimid" name="crimid" id="crimid" headerAlign="center" allowSort="true" align="center">
					<input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="applyid" headerAlign="center" allowSort="true" align="center" readOnly="true">
					罪犯编号 <input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="applyname" headerAlign="center" allowSort="true" align="center" readOnly="true">
					姓名 <input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="areaname" headerAlign="center" allowSort="false" align="center" readOnly="true">
					监区 <input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="anjianhao" headerAlign="center" allowSort="true" align="center" readOnly="true">
					案件号 <input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="text7" headerAlign="center" allowSort="true" align="center" >
					类型<input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="casenumber" headerAlign="center" align="center" allowSort="false" style="background: #50F0B4;">
					案号(可编辑)<input property="editor" class="mini-spinner" style="text-align: center;" minValue="0" maxValue="20000" />
				</div>
			</div>
		</div>
	</div>
	
	<div showCollapseButton="false">
		<div id="showContent"
			style="height:30px;overflow:auto;display:none;background:#EFF2F5;color:#3789B8;text-align:center;">
			<script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
		</div>
	</div>
	
	
	<script type="text/javascript">
		    mini.parse();
			var grid= mini.get("datagrid");
			
		    grid.on("drawcell",
		    	function(e){
				     var field = e.field;
		             if (field == "casenumber" ) {
		                	e.cellStyle = "background:#D9E7F8";
		             }
				}
		    );
		    
		    //mini.get("casetype").select(0);
		    mini.get('anjianhao').setValue(null);
		    
			function search() {
		         var casenums = mini.get("casenums").getValue();
		         var xingqileixing = mini.get("xingqileixing").getValue();
		         var crimetype = mini.get("crimetype").getValue(); 
		         var nodeid = mini.get("nodeid").getValue();
		         var key = mini.get("key").getValue();
		         var caseyear= mini.get("caseyear").getValue();
		         var casetype = mini.get("casetype").getText();
		         var jianqu = mini.get("jianqu").getValue();
		         var anhao = mini.get("anhao").getValue();
		         var batch = mini.get("batch").getValue();
		         
		         var data = {};
		         data.key = key;
		         data.casenums = casenums;
		         data.casetype = casetype;
		         data.jianqu = jianqu;
		         data.anhao = anhao;
		         data.xingqileixing = xingqileixing;
		         data.crimetype = crimetype;
		         data.nodeid = nodeid;
		         data.caseyear = caseyear;
		         data.batch = batch;
		         //
		         mini.get("datagrid").load(data);
		    }
			
			search();
			
			
	        function onKeyEnter(e) {
	            search();
	        }
	        
	        
	        $("#key").bind("keydown", function (e) {
	            if (e.keyCode == 13) {
	                search();
	            }
	        });
	        
	        
	        function changeanjianhao(){
	        	var qishi=mini.get("anjianhao").getValue();
        		var grid= mini.get("datagrid");
	            var rows = grid.getSelecteds(); 
	            if(qishi && qishi > 0){ 
	            	if (rows.length > 0) { 
		            	var notice = rows.length==1?"确定选择<"+qishi+">初始案件号进行更改？":"确定选择<"+qishi+">初始案件号批量更改？"; 
			            if (confirm(notice)) {
			    	   		var messageid = mini.loading("批量保存中 请稍后 ...", "处理中");
			            	var ids = [];
			            	 for (var i = 0, l = rows.length; i < l; i++) {
		                        var r = rows[i];
		                        ids.push(r.flowdraftid);
		                        $.ajax({
			                        url:"<%=path%>/commutationParole/ajaxGetAnnexcontent.json?1=1",
			                        data: {flowdraftid:r.flowdraftid,casetype:r.text7,parolenumber:qishi+i,text6:r.text6},
			                        type: 'POST',
			                        async:false,
			                        success: function (text) {
			                        	
			                        },
			                        error: function () {
			                        	alert("更改失败,请联系管理员!");
			                        }
			                    });
		                    }
                            mini.hideMessageBox(messageid);
			            }
			            grid.reload();
		        	}else{
		        		alert("请选择要更改的案件！");
		        	}
	            }else{
	            	//alert("请填写起始案件号！");
	            	savechangeanjianhao();
	            }
	        }
	        
	        
	        function savechangeanjianhao(){
	        	var grid = mini.get("datagrid");
	            var data = grid.getChanges();
	            if (data.length > 0) {
	            	var notice = data.length==1?'确定更改案件号？':'确定批量更改案件号？';
		            if (confirm(notice)) {
		            	 for (var i = 0, l = data.length; i < l; i++) {
	                        var r = data[i];
	                        $.ajax({
		                        url:"<%=path%>/commutationParole/ajaxGetAnnexcontent.json?1=1",
		                        data: {flowdraftid:r.flowdraftid,casetype:r.text7,parolenumber:r.casenumber,text6:r.text6},
		                        type: 'POST',
		                        success: function (text) {
		                        	
		                        },
		                        error: function () {
		                        	
		                        }
		                    });
	               		}
	            	}
	            }
	            grid.reload();
	        }
	        
		</script>
	</body>
</html>