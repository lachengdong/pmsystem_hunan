<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>查询方案new2</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript">
		</script>
		<script src="<%=path%>/scripts/validate.js" type="text/javascript">
		</script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
.backgray *{
	background-color: #DDD !important;
}

.input {
	background-color: #DDD !important;
}

.hide{
	display: none;
}
		
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
		<script type="text/javascript">
var deleteMessage = "请先选中一个节点！";
var deleteConfirmMessage = "是否删除此节点？";
var deleteParentConfirmMessage = "此节点包含子节点,确认删除？(不会删除子节点)";
</script>
	</head>
	<body onload="toNotEditAndGray(ids)">
		<input id="solutionid" name="solutionid" class="mini-hidden" />
		<input id="detailid" name="detailid" class="mini-hidden" />
		<input id="sqlgroupid" name="sqlgroupid" class="mini-hidden" />
		<input id="paramid" name="paramid" class="mini-hidden" />
		<input id="clickFlag" name="clickFlag" class="mini-hidden" />
		<!-- 单击标志位 -->
		<!--
    <input id="operator" name="operator" value="new" class="mini-hidden" />
    <input id="tableid" name="tableid" class="mini-hidden"/>
	-->
		<div id="layout1" class="mini-layout"
			style="width: 100%; height: 100%;">
			<div title="查询方案" showSplitIcon="true" expanded="false" region="west"
				width="170px;">
				<div class="mini-toolbar"
					style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					<span style="padding-left: 5px;"></span>
					<input class="mini-textbox" vtype="maxLength:50;"
						emptyText="请输入方案名称" id="key" onenter="onKeyEnter" />
					<a class="mini-button" iconCls="icon-search" plain="true"
						onclick="search()"></a>
				</div>
				<div class="mini-fit">
					<ul id="tree1" class="mini-tree"
						url="<%=path%>/solution/getSolutionSchemeTree.json?1=1"
						style="width: 100%; height: 100%;" showTreeIcon="true"
						textField="solutionname" idField="solutionid"
						parentField="solutionpid" resultAsTree="false"
						contextMenu="#solutionTreeMenu"
						onnodedblclick="editOperate('edit');"
						onnodeclick="onSolutionSelectChanged">
						<!-- onbeforenodeselect="beforenodeselect" -->
					</ul>
				</div>
			</div>
			<div showCollapseButton="false">
				<ul id="menu2" class="mini-menubar" style="width: 100%;">
					<li iconCls="icon-print" onclick="copySolution">
						方案拷贝
					</li>
					<li class="separator"></li>
					<li>
						<span iconCls="icon-add">新增</span>
						<ul>
							<li onclick="operateSolution('new');">
								新增方案
							</li>
							<li class="separator"></li>
							<li onclick="operateSolutionDetail('new');">
								新增方案细节
							</li>
							<li class="separator"></li>
							<li onclick="operateSqlGroup('new');">
								新增细节SQL组
							</li>
							<li class="separator"></li>
							<!--  
							<li onclick="operateParamMapping('new');">
								新增SQL参数映射
							</li>
							-->
						</ul>
					</li>
					<li class="separator"></li>
					<li iconCls="icon-edit" onclick="editOperate('edit');">
						修改
					</li>
					<li class="separator"></li>
					<%--
					
					<li iconCls="icon-remove" onclick="removeComponent()">
						删除
					</li>
					<li class="separator"></li>
					
					 --%>
					<li>
						<div id="solutionId" style="display: inline;width:800"></div>
					</li>
				</ul>
				<div class="mini-fit">
					<div id="editForm1"
						style="padding: 5px; width: 100%; height: 100%;"
						class="mini-splitter">
						<div size="120" showCollapseButton="true">
							<div class="mini-toolbar"
								style="padding: 3px; border-top: 0; border-left: 0; border-right: 0;">
								<span style="padding-left: 0px;">方案细节</span>
							</div>
							<div class="mini-fit">
								<ul id="tree2" class="mini-tree"
									style="width: 100%; height: 100%; padding: 5px;"
									showTreeIcon="false" textField="detailname" idField="detailid"
									parentField="solutionid" resultAsTree="false"
									onnodedblclick="editOperate('edit');"
									onnodeclick="onSolutionDetailChanged" showCheckBox="false"
									contextMenu="#detailTreeMenu"
									expandOnLoad="true">
								</ul>
							</div>
						</div>
						<div size="600" showCollapseButton="false">
							<div id="split2" style="padding: 5px; width: 100%; height: 100%;"
								class="mini-splitter">
								<div size="100" showCollapseButton="false">
									<div class="mini-toolbar"
										style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
										<span style="padding-left: 5px;">方案细节SQL组</span>
									</div>
									<div class="mini-fit">
										<ul id="tree3" class="mini-tree"
											style="width: 100%; height: 100%;" showTreeIcon="false"
											textField="sqlgroupname" idField="sqlgroupid"
											parentField="detailid" resultAsTree="false"
											onnodedblclick="editOperate('edit');" showCheckBox="false"
											contextMenu="#sqlGroupTreeMenu"
											onnodeclick="onSqlGroupSelectChanged">
										</ul>
									</div>
								</div>
								<div size="500" showCollapseButton="false">
									<fieldset
										style="width: 98%; height: 98% border :     solid 1px #aaa; position: relative; padding: 0px;">
										<legend>
											方案细节SQL组信息
										</legend>
										<div style="padding: 5px;">
											<table style="width: 100%;">
												<tr>
													<td style="width: 20%;">
														SQL组ID：
													</td>
													<td style="width: 30%;">
														<input id="sqlgroupid" name="sqlgroupid"
															class="mini-textbox" allowInput="false" />
													</td>
												</tr>
												<tr>
													<td style="width: 20%;">
														SQL组名称：
													</td>
													<td style="width: 30%;">
														<input id="sqlgroupname" name="sqlgroupname"
															class="mini-textbox" allowInput="false" />
													</td>

													<td style="width: 20%;">
														开始顺序：
													</td>
													<td style="width: 30%;">
														<input name="startorder" class="mini-spinner"
															allowInput="false" maxValue="99" minValue="1"
															validateOnLeave style="width: 40px;" />
													</td>
												</tr>
												<tr>
													<td>
														是否循环：
													</td>
													<td>
														<input id="cycleflag" name="cycleflag"
															class="mini-checkbox" allowInput="false" text=""
															value="0" trueValue="1" falseValue="0" />
													</td>

													<td style="width: 20%;">
														循环次数：
													</td>
													<td style="width: 30%;">
														<input name="int1" class="mini-spinner" allowInput="false"
															maxValue="99" minValue="1" validateOnLeave
															style="width: 40px;" />
													</td>
												</tr>
												<tr>
													<td>
														是否主SQL组：
													</td>
													<td>
														<input id="ismaingroup" name="ismaingroup"
															class="mini-checkbox" allowInput="false" text=""
															value="0" trueValue="1" falseValue="0" />
													</td>

													<td>
														特殊位：
													</td>
													<td>
														<input id="signflag" name="signflag" class="mini-combobox"
															style="width: 60px;" valueField="id" textField="text"
															emptyText=""
															data="[{ id: 0, text: '普通' }, { id: 1, text: '下拉'} ,{ id: 2, text: '临时'},{ id: 3, text: '列表'}]"
															required="false" allowInput="false" />
													</td>
												</tr>
												<tr>
													<td>
														结果集映射KEY：
													</td>
													<td>
														<input id="resultkey" name="resultkey" maxlength="254"
															class="mini-textbox" allowInput="false" />
													</td>

													<td>
														SQL组类型：
													</td>
													<td>
														<input id="sqlgrouptype" name="sqlgrouptype"
															class="mini-combobox" style="width: 60px;"
															valueField="id" textField="text" emptyText=""
															data="[{ id: 0, text: '查询' }, { id: 1, text: '保存'} ,{ id: 2, text: '删除'}]"
															required="false" allowInput="false" />
													</td>
												</tr>
												<tr>
													<td>
														判断SQL文本：
													</td>
													<td colspan="3">
														<input id="testsql" name="testsql" allowInput="false"
															class="mini-textarea" vtype="maxLength:1330;"
															style="width: 91%; height: 60px;" />
													</td>
												</tr>
												<tr>
													<td>
														主SQL文本：
													</td>
													<td colspan="3">
														<input id="mainsql" name="mainsql" allowInput="false"
															class="mini-textarea" vtype="maxLength:1330;"
															style="width: 91%; height: 130px;" />
													</td>
												</tr>
												<tr>
													<td>
														备用SQL文本：
													</td>
													<td colspan="3">
														<input id="standbysql" allowInput="false"
															name="standbysql" class="mini-textarea"
															vtype="maxLength:1330;" style="width: 91%; height: 130px;" />
													</td>
												</tr>
												<!--
												<tr>
													<td>
														删除SQL文本：
													</td>
													<td colspan="3">
														<input id="delsql" allowInput="false" name="delsql"
															class="mini-textarea" vtype="maxLength:1330;"
															style="width: 91%; height: 60px;" />
													</td>
														<input id="delsql" name="delsql" class="mini-buttonedit" text="<CLOB>" allowInput="false" onbuttonclick="onButtonEdit"/></td> 
													<td>
												</tr>
											  -->
											</table>
										</div>
									</fieldset>
									<!--  -->
									<div class="mini-fit" style="display:none">
										<div id="grid1" class="mini-datagrid"
											style="width: 100%; height: 100%" multiSelect="true"
											onrowclick="onGridRowClick"
											onrowdblclick="editOperate('edit');"
											borderStyle="border-top:1;border-left:0;border-right:0;border-bottom:0;"
											sizeList="[10,20,50,100]" pageSize="20"
											url="<%=path%>/solution/getFormParamMappingDataList.action"
											showPager="true" allowAlternating="true"
											showFilterRow="false" allowCellSelect="true"
											allowCellEdit="true">
											<div property="columns">
												<div type="checkcolumn" width="20"></div>
												<div field="sqlparamname" width="60" headerAlign="center"
													align="center" allowSort="true">
													SQL参数名
												</div>
												<div field="formfieldname" width="60" headerAlign="center"
													align="center" allowSort="true">
													表单字段名
												</div>
												<div field="datatype" width="60" headerAlign="center"
													align="center" allowSort="true"
													renderer="onDatatypeRenderer">
													数据类型
												</div>
												<div field="cycleflag" width="60" headerAlign="center"
													align="center" allowSort="true" renderer="onCycleRenderer">
													循环标志
												</div>
												<div field="notnullflag" width="60" headerAlign="center"
													align="center" allowSort="true"
													renderer="onNotNullRenderer">
													可否为空
												</div>
												<div field="nulldelflag" width="60" headerAlign="center"
													align="center" allowSort="true"
													renderer="onNullDelRenderer">
													为空删除
												</div>
												<div field="nullemptyvalue" width="60" headerAlign="center"
													align="center" allowSort="true">
													默认值
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="editWindow" class="mini-window" title="方案拷贝" style="width:510px;height:240px"
			    showModal="true" allowResize="true" allowDrag="true"
			    >
			    <!-- 弹出框 -->
			    <div id="editform" class="form" >
			        <div class="mini-toolbar mini-grid-headerCell" style="border-bottom:0;padding:0px;margin: -5px -5px 5px;">
			            <table style="width:100%;">
			                <tr>
			                    <td style="width:100%;">
				            		<a class="mini-button" iconCls="icon-print" onclick="copyData()" plain="true" style="width:100px">开始拷贝</a>
									<a class="separator"></a>
				           			<a class="mini-button" onclick="cancelEditWindow()"  plain="true" style="width:100px;">取消</a>                       
				           		</td>
			                </tr>
			            </table>           
			        </div>
			        <table style="width:100%;">
			            <tr>
			                <td >源方案ID ：</td>
			                <td >
			                		<input id="fromid" name="fromid" class="mini-textbox" size="180" allowInput="false" style="width:190px;"
			                			inputStyle="background-color: #DDD !important;"
			                		 vtype="maxLength:100" required="true"  requiredErrorText="源方案ID不能为空"/>
			                </td>
			            </tr>
			            <tr>
			                <td >方案名：</td>
			                <td ><input id="toname" name="toname" class="mini-textbox"  size="180" style="width:190px"
			                	 vtype="maxLength:100" required="true"  requiredErrorText="name不能为空"/>
			                </td>
			            </tr>
			            <tr>
			                <td>拷贝到：</td>
			                <td>
			                	<input id="topname" name="topname" size="180" class="mini-buttonedit" onbuttonclick="selectCopySolutionData"  style="width:190px"/>
			                	<input id="topid" name="topid"  class="mini-hidden"/>
			                </td>
			            </tr>
			            <tr>
			                <td>拷贝选项：</td>
			                <td><input id="onlysub" name="onlysub" class="mini-combobox" valueField="id" textField="text" size="80" data="[{id:'0', text:'拷贝方案'},{id:'1', text:'只拷贝细节'}]"/></td>
			            </tr>
			        </table>
			    </div>
		</div>
    	
		<div class="hide">
			<!-- 查询方案树,右键菜单 -->
			<ul id="solutionTreeMenu" class="mini-contextmenu"  onbeforeopen="onBeforeOpenTreeMenu">
			    <li name="addsolution"  iconCls="icon-add" onclick="onAddSolution">新增方案</li>
			    <li name="adddetail"  iconCls="icon-add" onclick="onAddDetail">新增方案细节</li>
			    <li name="editsolution" iconCls="icon-edit" onclick="onEditSolution">编辑</li>
			    <li name="copysolution" iconCls="icon-node" onclick="onCopySolution">拷贝</li>
			    <li class="separator"></li>
			    <li name="removesolution" iconCls="icon-remove" onclick="onRemoveSolution">删除</li>
			</ul>
			<!-- 方案细节树,右键菜单 -->
			<ul id="detailTreeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpenDetailTreeMenu">
			    <li name="addsqlgroup"  iconCls="icon-add" onclick="onAddSqlGroup">新增SQL组</li>
			    <li name="editdetail" iconCls="icon-edit" onclick="onEditDetail">编辑</li>
			    <li class="separator"></li>
			    <li name="removedetail" iconCls="icon-remove" onclick="onRemoveDetail">删除</li>
			</ul>
			<!-- SQL组,右键菜单 -->
			<ul id="sqlGroupTreeMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpenSQLTreeMenu">
			    <li name="editsqlgroup" iconCls="icon-edit" onclick="onEditSqlGroup">编辑</li>
			    <li class="separator"></li>
			    <li name="removesqlgroup" iconCls="icon-remove" onclick="onRemoveSqlGroup">删除</li>
			</ul>
		</div>

		<script type="text/javascript">
mini.parse();
var layout = mini.get("layout1");
var tree = mini.get("tree1");//方案树
var tree2 = mini.get("tree2");//左侧的表
var tree3 = mini.get("tree3");//对应方案的表
var grid = mini.get("grid1");//column信息
var editForm1 = new mini.Form("editForm1");
//张永有：把testsql文本框设成不可编辑并置灰:可以根据需要在ids里面加上或监区id就行
var ids = new Array("testsql","mainsql","standbysql","delsql");
function toNotEditAndGray(ids){
	for(var i=0;i<ids.length;i++){
		var inputEl=mini.get(ids[i]);
		if(!inputEl){
			continue;
		}
		inputEl.getEl().className = inputEl.getEl().className+ " backgray";//
		//window.testsql = testsql;
	}
}

//var splitter1 = mini.get("editForm1");//split1
//var splitter2 = mini.get("split2");//split2
$(document).ready(function() {
	layout.updateRegion("west", {
		expanded : true
	});
	//splitter1.collapsePane(1);
		//splitter2.collapsePane(1);
	});

function onSqlGroupSelectChanged(e) {
	var record = tree3.getSelected();
	if (record) {
		grid.load( {
			solutionid : record.solutionid,
			detailid : record.detailid,
			sqlgroupid : record.sqlgroupid
		});
		$.ajax( {
			url : "<%=path%>/solution/getFormSqlGroupData.action?solutionid="
					+ record.solutionid + "&detailid=" + record.detailid
					+ "&sqlgroupid=" + record.sqlgroupid,
			type : "post",
			success : function(text) {
				var o = mini.decode(text);
				editForm1.setData(o);
				editForm1.unmask();
			}
		});
		mini.get("solutionid").setValue(record.solutionid);//设置方案id
		mini.get("detailid").setValue(record.detailid);//设置方案细节id
		mini.get("sqlgroupid").setValue(record.sqlgroupid);//设置细节SQL组id
		mini.get("paramid").setValue("");//设置参数映射id为空
		mini.get("clickFlag").setValue("SQL_GROUP");
	}

}

//选中方案触发事件
function onSolutionSelectChanged(e) {
	tree3.loadData( []);
	editForm1.clear();
	grid.load();
	var url2 = "<%=path%>/solution/getSolutionDetailList.json?solutionid="
			+ e.node.solutionid;
	tree2.setUrl(url2);
	var solutiontype = tree.getSelectedNode().solutiontype;
	if (solutiontype == 0) {
		document.getElementById("solutionId").innerHTML = e.node.solutionname
				+ "的id： " + e.node.solutionid;
	}

	mini.get("solutionid").setValue(e.node.solutionid);//设置方案id
	mini.get("detailid").setValue("");//设置方案细节id为空
	mini.get("sqlgroupid").setValue("");//设置细节SQL组id为空
	mini.get("paramid").setValue("");//设置参数映射id为空
	mini.get("clickFlag").setValue("SOLUTION");
}

//选中方案细节触发事件
function onSolutionDetailChanged(e) {
	editForm1.clear();
	grid.load();
	var url3 = "<%=path%>/solution/getFormSqlGroupList.json?solutionid="
			+ e.node.solutionid + "&detailid=" + e.node.detailid;
	tree3.setUrl(url3);
	mini.get("solutionid").setValue(e.node.solutionid);//设置方案id
	mini.get("detailid").setValue(e.node.detailid);//设置方案细节id
	mini.get("sqlgroupid").setValue("");//设置细节SQL组id为空
	mini.get("paramid").setValue("");//设置参数映射id为空
	mini.get("clickFlag").setValue("SOLUTION_DETAIL");
}
//删除方案或类型
function removeComponent() {
	var clickFlag = mini.get("clickFlag").getValue();
	if (clickFlag) {
		//
		
		var solutionid = mini.get("solutionid").getValue();
		var detailid = mini.get("detailid").getValue();
		var sqlgroupid = mini.get("sqlgroupid").getValue();
		//
		var url = '';
		var data = '';
		//
		var info = "确定要删除吗？";
		//
		if (clickFlag == 'SOLUTION') {
			url = "<%=path%>/solution/deleteSolutionScheme.action";
			data = {
				solutionid : solutionid
			};
			info = "确定要删除查询方案(id="+solutionid+")吗?";
			//
			confirm("请使用右键菜单删除方案!");
			return false;
		} else if (clickFlag == 'SOLUTION_DETAIL') {
			url = "<%=path%>/solution/deleteSolutionDetail.action";
			data = {
				detailid : detailid
			};
			info = "确定要删除方案细节(id="+detailid+")吗?";
		} else if (clickFlag == 'SQL_GROUP') {
			url = "<%=path%>/solution/deleteDetailSqlGroup.action";
			data = {
				sqlgroupid : sqlgroupid
			};
			info = "确定要删除SQL组(id="+sqlgroupid+")吗?";
		} else if (clickFlag == 'PARAM_MAPPING') {
			url = "<%=path%>/solution/deleteParamMapping.action";
			var rows = grid.getSelecteds();
			var ids = [];
			for ( var i = 0, l = rows.length; i < l; i++) {
				var row = rows[i];
				var paramid = row.paramid;
				ids.push(paramid);
			}
			var id = ids.join(',');
			data = {
				paramid : id
			};
			info = "确定要删除参数(id="+id+")吗?";
		}
		//
		if (confirm(info)) {
			//
			url && data && publicAjax(url, data, "post", false);
		}//
	} else {
		alert("请选择删除项！");
	}
}

function publicAjax(url, data, type, cache) {
	$.ajax( {
		url : url,
		data : data,
		type : type,
		cache : cache,
		success : function(text) {
			if (text >= 1) {
				alert("操作成功！");
				refreshAfterOperate("edit");
				mini.get("clickFlag").setValue("");
			} else {
				alert("操作失败!");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("操作失败!");
		}
	});
}

//修改功能
function editOperate(operate) {
	var clickFlag = mini.get("clickFlag").getValue();
	if (clickFlag) {
		if (clickFlag == 'SOLUTION') {
			operateSolution(operate);
		} else if (clickFlag == 'SOLUTION_DETAIL') {
			operateSolutionDetail(operate);
		} else if (clickFlag == 'SQL_GROUP') {
			operateSqlGroup(operate);
		} else if (clickFlag == 'PARAM_MAPPING') {
			operateParamMapping(operate);
		}
	} else {
		alert("请选择修改项！");
	}
}

function publicOperate(operate, url, title, width, height, showMaxButton,
		allowResize, data) {
	//document.getElementById("editForm1").style.display = "none";
	mini.open( {
		url : url,
		title : title,
		width : width,
		height : height,
		showMaxButton : showMaxButton,
		allowResize : allowResize,
		onload : function() {
			var iframe = this.getIFrameEl();
			//var data = { action: operate, solutionid:solutionid};
		iframe.contentWindow.SetData(data);
	},
	ondestroy : function(action) {
		//document.getElementById("editForm1").style.display = "";
		if (action == "ok") { //如果点击“确定”
			refreshAfterOperate(operate);
		}
	}
	});
}

//操作完成后刷新
function refreshAfterOperate(operate) {
	var clickFlag = mini.get("clickFlag").getValue();
	if (operate == 'new') {
		if (clickFlag == 'SOLUTION') {
			tree2.reload();
		} else if (clickFlag == 'SOLUTION_DETAIL') {
			tree3.reload();
		} else if (clickFlag == 'SQL_GROUP') {
			grid.reload();
		} else {
			tree.reload();//刷新左侧树
		}
	} else if (operate == 'edit') {
		if (clickFlag == 'SOLUTION') {
			tree.reload();//刷新左侧树
		} else if (clickFlag == 'SOLUTION_DETAIL') {
			tree2.reload();
		} else if (clickFlag == 'SQL_GROUP') {
			tree3.reload();
		} else if (clickFlag == 'PARAM_MAPPING') {
			grid.reload();
		}
	}
};

//操作方案
function operateSolution(operate) {
	if (operate == 'new') {
		mini.get("clickFlag").setValue('');
	}
	var url = "<%=path%>/solution/toOperateSolutionPage.action?1=1";
	var solutionid = mini.get("solutionid").getValue();//当前选中的方案id
	var data = {
		action : operate,
		solutionid : solutionid
	};
	publicOperate(operate, url, "", 450, 500, true, false, data);
}

//操作方案细节
function operateSolutionDetail(operate) {
	if (operate == 'new') {
		var clickFlag = mini.get("clickFlag").getValue();
		if (clickFlag != 'SOLUTION') {
			alert("请先选中方案!");
			return;
		}
		var solutiontype = tree.getSelectedNode().solutiontype;
		if (solutiontype == 1) {
			alert("无法在虚目录里创建方案细节");
			return;
		}
	}
	var url = "<%=path%>/solution/toOperateSolutionDetailPage.action?1=1";
	var solutionid = mini.get("solutionid").getValue();
	var detailid = mini.get("detailid").getValue();
	var data = {
		action : operate,
		solutionid : solutionid,
		detailid : detailid
	};
	publicOperate(operate, url, "", 450, 500, true, false, data);
}

//操作细节SQL组
function operateSqlGroup(operate) {
	if (operate == 'new') {
		var clickFlag = mini.get("clickFlag").getValue();
		if (clickFlag != 'SOLUTION_DETAIL') {
			alert("请先选中方案细节!");
			return;
		}
	}
	var url = "<%=path%>/solution/toOperateSqlGroupPage.action?1=1";
	var solutionid = mini.get("solutionid").getValue();
	var detailid = mini.get("detailid").getValue();
	var sqlgroupid = mini.get("sqlgroupid").getValue();
	var data = {
		action : operate,
		solutionid : solutionid,
		detailid : detailid,
		sqlgroupid : sqlgroupid
	};
	publicOperate(operate, url, "", 450, 500, true, false, data);
}

//操作SQL参数映射
function operateParamMapping(operate) {
	if (operate == 'new') {
		var clickFlag = mini.get("clickFlag").getValue();
		if (clickFlag != 'SQL_GROUP') {
			alert("请先选中细节SQL组!");
			return;
		}
	}
	var url = "<%=path%>/solution/toOperateParamMappingPage.action?1=1";
	var solutionid = mini.get("solutionid").getValue();
	var detailid = mini.get("detailid").getValue();
	var sqlgroupid = mini.get("sqlgroupid").getValue();
	var paramid = mini.get("paramid").getValue();
	var data = {
		action : operate,
		solutionid : solutionid,
		detailid : detailid,
		sqlgroupid : sqlgroupid,
		paramid : paramid
	};
	publicOperate(operate, url, "", 450, 500, true, false, data);
}

//禁止选中方案类型
function beforenodeselect(e) {
	if (e.isLeaf == false) {
		e.cancel = true;
		return;
	} else if (e.node.solutiontype == 1) {
		//alert(e.node.solutiontype);
		e.cancel = true;
		return;
	}
}

//过滤树
function search() {
	var key = mini.get("key").getValue();
	if (key == "") {
		tree.clearFilter();
	} else {
		key = key.toLowerCase();
		tree
				.filter(function(node) {
					var text = node.solutionname ? node.solutionname
							.toLowerCase() : "";
					var solutionid = node.solutionid;
					if (text.indexOf(key) != -1 || solutionid == key) {
						return true;
					}
				});
	}
}function onKeyEnter(e) {
	search();
}

function onButtonEdit() {
	var btnEdit = this;
	var id = btnEdit.id;
	var sqlValue = mini.get(id).getValue();
	mini.open( {
		url : "<%=path%>/solution/toEditSolutionClobPage.action?1=1",
		title : "SQL文本",
		width : 700,
		height : 350,
		showMaxButton : true,
		onload : function() {
			var iframe = this.getIFrameEl();
			var data = {
				sql : sqlValue
			};
			iframe.contentWindow.SetData(data);
		},
		ondestroy : function(action) {
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				var data = iframe.contentWindow.GetData();
				data = mini.clone(data);
				btnEdit.setValue(data.sql);
				//btnEdit.setText(data.text);
	}
}
	});
}

function onGridRowClick() {
	mini.get("clickFlag").setValue("PARAM_MAPPING");
	var row = grid.getSelected();
	mini.get("solutionid").setValue(row.solutionid);
	mini.get("detailid").setValue(row.detailid);
	mini.get("sqlgroupid").setValue(row.sqlgroupid);
	mini.get("paramid").setValue(row.paramid);
}

function onDatatypeRenderer(e) {
	if (e) {
		if (e.value == 0) {
			return '字符串';
		} else if (e.value == 1) {
			return '数字';
		} else if (e.value == 2) {
			return '日期';
		} else if (e.value == 3) {
			return '大字段';
		}
	}
	return "";
}

function onCycleRenderer(e) {
  		if(e){
  			if(e.value==0){
      			return '非循环';
      		}else if(e.value==1){
      			return '循环';
      		}
  		}
          return "";
}
      
function onNotNullRenderer(e) {
	if(e){
		if(e.value==0){
	  			return '可为空';
	  		}else if(e.value==1){
	  			return '不可为空';
	  		}
	}
      return "";
}

function onNullDelRenderer(e) {
	if(e){
		if(e.value==0){
	  			return '不删除';
	  		}else if(e.value==1){
	  			return '删除';
	  		}
	}
      return "";
}
    
;
// 打开树的右键菜单前
function onBeforeOpenTreeMenu(e) {
    var menu = e.sender;
    var tree = mini.get("tree1");
    if (!tree) {
        e.cancel = true;
        return;
    }
	// 取得点击的节点
    var node = tree.getSelectedNode();
    if (!node) {
        e.cancel = true;
        return;
    }
    ////////////////////////////////
    // 对某些特殊的菜单执行隐藏,显示操作
    var addsolution = mini.getbyName("addsolution", menu);			    
    var adddetail = mini.getbyName("adddetail", menu);			    
    var editsolution = mini.getbyName("editsolution", menu);			    
    var copysolution = mini.getbyName("copysolution", menu);			    
    var removesolution = mini.getbyName("removesolution", menu);		    
    // 设置为初始值
    addsolution && addsolution.show();
    adddetail && adddetail.show();
    editsolution && editsolution.show();
    copysolution && copysolution.show();
    removesolution && removesolution.show();
    // 屏蔽某些菜单
    if (node.children || "0" === node.solutionpid || "-1" === node.solutionpid) {
    	removesolution.hide();//不显示删除菜单
    }
    //
    if(node.children || node.solutiontype){
    	adddetail.hide();//不显示新增细节
    	copysolution.hide();//不显示copysolution
    } else {
    	addsolution.hide();
    }
};

//
function onBeforeOpenDetailTreeMenu(e){
    var menu = e.sender;
    var tree = mini.get("tree2");
    if (!tree) {
        e.cancel = true;
        return;
    }
	// 取得点击的节点
    var node = tree.getSelectedNode();
    if (!node) {
        e.cancel = true;
        return;
    }
};

//
function onBeforeOpenSQLTreeMenu(e){
    var menu = e.sender;
    var tree = mini.get("tree3");
    if (!tree) {
        e.cancel = true;
        return;
    }
	// 取得点击的节点
    var node = tree.getSelectedNode();
    if (!node) {
        e.cancel = true;
        return;
    }
};

// 新增方案
function onAddSolution(e) {
    var tree = mini.get("tree1");
    var node = tree.getSelectedNode();
    if (!node) {
        //e.cancel = true;
        return false;
    }
    var solutionid = node.solutionid;
	// 不要怪这里代码写的烂了,直接调用原来的...
	// mini.get("clickFlag") && mini.get("clickFlag").setValue("SOLUTION");
	mini.get("solutionid") && mini.get("solutionid").setValue(solutionid);
	operateSolution('new');
	//mini.get("clickFlag").setValue("SOLUTION");
};



//新增方案细节菜单
function onAddDetail(e) {
	var tree = mini.get("tree1");
	var node = tree.getSelectedNode();
	if (!node) {
	    return false;
	}
    var solutionid = node.solutionid;
    var operate = 'new';
	// 不要怪这里代码写的烂了,直接调用原来的...
	mini.get("clickFlag") && mini.get("clickFlag").setValue("SOLUTION");
	mini.get("solutionid") && mini.get("solutionid").setValue(solutionid);
	//
	operateSolutionDetail(operate);
};

function onEditDetail(e){

	var tree = mini.get("tree2");
	var node = tree.getSelectedNode();
	if (!node) {
	    return false;
	}
    var detailid = node.detailid;
    var operate = 'edit';
	// 不要怪这里代码写的烂了,直接调用原来的...
	mini.get("clickFlag") && mini.get("clickFlag").setValue("SOLUTION_DETAIL");
	mini.get("detailid") && mini.get("detailid").setValue(detailid);
	
	operateSolutionDetail(operate);
};

function onRemoveDetail(e){

	var tree = mini.get("tree2");
	var node = tree.getSelectedNode();
	if (!node) {
	    return false;
	}
    var detailid = node.detailid;

	// 不要怪这里代码写的烂了,直接调用原来的...
	mini.get("clickFlag") && mini.get("clickFlag").setValue("SOLUTION_DETAIL");
	mini.get("detailid") && mini.get("detailid").setValue(detailid);
    //
    removeComponent();
};



function onEditSqlGroup(e){

	var tree = mini.get("tree3");
	var node = tree.getSelectedNode();
	if (!node) {
	    return false;
	}
    var sqlgroupid = node.sqlgroupid;
    var operate = 'edit';
	// 不要怪这里代码写的烂了,直接调用原来的...
	mini.get("clickFlag") && mini.get("clickFlag").setValue("SQL_GROUP");
	mini.get("sqlgroupid") && mini.get("sqlgroupid").setValue(sqlgroupid);
	
	operateSqlGroup(operate);
};

function onRemoveSqlGroup(e){

	var tree = mini.get("tree3");
	var node = tree.getSelectedNode();
	if (!node) {
	    return false;
	}
    var sqlgroupid = node.sqlgroupid;

	// 不要怪这里代码写的烂了,直接调用原来的...
	mini.get("clickFlag") && mini.get("clickFlag").setValue("SQL_GROUP");
	mini.get("sqlgroupid") && mini.get("sqlgroupid").setValue(sqlgroupid);
    //
    removeComponent();
};


//新增SqlGroup
function onAddSqlGroup(e) {
	var tree = mini.get("tree2");
	var node = tree.getSelectedNode();
	if (!node) {
	    return false;
	}
  var detailid = node.detailid;
  var operate = 'new';
	// 不要怪这里代码写的烂了,直接调用原来的...
	mini.get("clickFlag") && mini.get("clickFlag").setValue("SOLUTION_DETAIL");
	mini.get("detailid") && mini.get("detailid").setValue(detailid);
	
	//var detailid = mini.get("detailid").getValue();
	//
	operateSqlGroup(operate);
};


//编辑
function onEditSolution(e) {
	var tree = mini.get("tree1");
	var node = tree.getSelectedNode();
	if (!node) {
		return;
	}
    var solutionid = node.solutionid;
	// 不要怪这里代码写的烂了,直接调用原来的...
	mini.get("clickFlag") && mini.get("clickFlag").setValue("SOLUTION");
	mini.get("solutionid") && mini.get("solutionid").setValue(solutionid);
	editOperate('edit');
	//
};

//拷贝方案
function onCopySolution(e) {
	var tree = mini.get("tree1");
	var node = tree.getSelectedNode();
	if (!node) {
		return;
	}
	//
    var solutionid = node.solutionid;
    var solutionname = node.solutionname;
	var data = {
			fromid : solutionid,
			toname : solutionname,
			onlysub : 0
	};
	
	// 弹出输入框
	showEditWindow(data);
};

// 删除
function onRemoveSolution(e) {
    var tree = mini.get("tree1");
    var node = tree.getSelectedNode();
    if (!node) {
    	return;
    }
    //
    var solutionid = node.solutionid;
	var info = "确定要删除查询方案(id="+solutionid+")吗?";
   	//
    if (confirm(info)) {
    	//
   		var url = "<%=path%>/solution/ajax/removesolution.json";
    	//
    	var data = {
    			solutionid : node.solutionid
    	};
    	var successCallback = function (message){
    		var status = message["status"];
    		var meta = message["meta"] || {};
    		var info = message["info"];
    		//
    		alert(info);
    		if(1 === status){
    			// 刷新
    			refreshPage();
    		} else {
    		}
    	};
    	var errorCallback = function (message){
    		alert("删除失败");
    	};
    	// ajax
    	requestAjax(url,data,successCallback,errorCallback);
   }
};


     // 拷贝方案
     ;function copySolution(){
     	
     	//	1. 获取方案ID
     	//  2. 弹出拷贝窗口.
     	//  3. 选择目标路径
     	//  4. 填写信息，确定
     	//  5. 请求后台
     	//  6. 关闭窗口,提示成功/失败
     	
     	// 
     	//

		// 获取
	    var tree = mini.get("tree1");
	    if(!tree){
	    	return false;
	    }
	    var node = tree.getSelectedNode();
	    if (!node) {
     		alert("请选择需要拷贝的方案");
	        //e.cancel = true;
	        return false;
	    }
     	var solutionid = node["solutionid"] || mini.get("solutionid").getValue();//当前选中的方案id
     	var solutiontype = node["solutiontype"];
     	var solutionname = node["solutionname"];
     	var hasChildren = tree.hasChildren(node);
     	//
     	if(!solutionid){
     		alert("请选择需要拷贝的方案");
     		return false;
     	}
     	if(hasChildren || solutiontype > 0){
     		alert("只允许拷贝单个[方案]");
     		return false;
     	}
     	//
     	var data = {
     			fromid : solutionid,
     			toname : solutionname,
     			onlysub : 0
     	};
     	
     	// 弹出输入框
     	showEditWindow(data);
     	// 接着就在弹出框内部处理了。
     };
     
     // 选择拷贝到哪里
     function selectCopySolutionData(){

    	//
    	var fromid = mini.get("fromid").getValue();
 		//
 		var myurl = "solution/getsinglesolution.page" + "?fromid="+fromid;
 		var mywidth = 600;
 		var myhight = 400;
 		//
        var win=mini.open({
             url: myurl,
             showMaxButton: true,
             allowResize: true, 
             title: "选择拷贝目的节点", width: mywidth, height: myhight,
             onload: function () {
                 var iframe = this.getIFrameEl();
                 // 注入数据?
             },
             ondestroy: function (action) {
             	//
                 var iframe = this.getIFrameEl();
             	//
             	if("ok" == action){
                     // 调用内部的方法
                     var data = iframe.contentWindow.GetData() ||{};
                     if(!data){
                    	 return;
                     }
                     data = mini.clone(data);
                     //
                     var topid = data.solutionid || "";
                     var topname = data.solutionname || "";
                     var solutiontype = data.solutiontype || "";
                     //
                 	 // 执行 Ajax请求,设置值
                 	 if(!topid){
                 		 return;
                 	 }
                     if(!solutiontype){ // solutiontype 不能为0
                    	 return;
                     }
                     //
                     var $_topid = mini.get("topid");// .getValue()
                     var $_topname = mini.get("topname");// .getValue()
                     if($_topid){
                    	 $_topid.setValue(topid);
                     }
                     if($_topname){
                    	 $_topname.setText(topname + "(" + topid + ")" );
                     }
             	};
             	
             }
         });
     };
     
		// 编辑
		function editRow() {
		    var row = grid.getSelected();
		    if (row) {
		        showEditWindow(row);
		    }
		};
		// 显示编辑框
		function showEditWindow(data, meta){
			//
			data = data || {};
			meta = meta || {
				saveText : "保存"
				, cancelText : "取消"
				, callback : function(){}
			};
			
			var editWindow = mini.get("editWindow");
			editWindow.show();
			var form = new mini.Form("#editform");
			
			//
			var nameInput = mini.get("name");
			
			var islocalnew = data.islocalnew;
			//form.loading();
			// 设置数据
	        form.setData(data);
			form.setIsValid(true);
			form.setChanged(false);
			// 允许编辑
	        form.unmask();
			if(nameInput){
				nameInput.focus();
			}
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
		// 拷贝数据
		function copyData() {
			var form = new mini.Form("#editform");
			
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
			var data = form.getData();
			//data = JSON.parse(data);
			// 界面
			var editWindow = mini.get("editWindow");
			if(!data){
				editWindow.hide();
				return ;
			}
			//
			var  url = "<%=path%>/solution/ajax/copySolution.json";
			var successCallback = function (message) {
			       	        	   
	        	   var info = message["info"];
	               if(1 === message["status"]){
			    		// 判断是否成功
			    		alert(info);
			    		refreshPage();
						//editWindow.hide();
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
    </script>
	</body>
</html>