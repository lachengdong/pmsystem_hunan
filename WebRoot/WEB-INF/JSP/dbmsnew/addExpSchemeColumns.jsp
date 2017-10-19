<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>数据库方案addExpSchemeColumns.jsp</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
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
		<script type="text/javascript">
			var data=window.parent.window.data;
			var columnList=[];
			function dosubmit(tableName){
				var cols = document.getElementsByName("columns");
				for(var i=0;i<cols.length;i++){
					if(cols[i].checked){
						var column={};
						var attribute=eval('('+cols[i].value+')');
						column.name=attribute.fromcolumns;
						column.type=attribute.fromcloumnstype;
						column.size=attribute.fromcloumnssize;
						var fromcolumnsscribe = document.getElementById("fromcolumnsscribe"+(i+1)).value;//字段描述
						if(null!=attribute.fromcolumnsscribe&&''!=attribute.fromcolumnsscribe){
							column.describe=attribute.fromcolumnsscribe;
						}else if(null!=fromcolumnsscribe&&''!=fromcolumnsscribe){
							column.describe=fromcolumnsscribe;
						}else{
							column.describe=" ";
						}
						var fromcloumnssize = document.getElementById("fromcloumnssize"+(i+1)).value;// 字段长度
						if(null!=attribute.fromcloumnssize&&''!=attribute.fromcloumnssize){
							column.size=attribute.fromcloumnssize;
						}else if(null!=fromcloumnssize&&''!=fromcloumnssize){
							column.size=fromcloumnssize;
						}else{
							column.size=" ";
						}
						var fromcloumnsdefaultvalue = document.getElementById("fromcloumnsdefaultvalue"+(i+1)).value;//默认值
						if(null!=attribute.fromcloumnsdefaultvalue&&''!=attribute.fromcloumnsdefaultvalue){
							column.defaultValue=attribute.fromcloumnsdefaultvalue;
						}else if(null!=fromcloumnsdefaultvalue&&''!=fromcloumnsdefaultvalue){
							column.defaultValue=fromcloumnsdefaultvalue;
						}else{
							column.defaultValue=" ";
						}
						columnList.push(column);
					}
				}
				var retValue={};
				if(null!=columnList&&""!=columnList){
					retValue.tableName=tableName;
					retValue.columnList=columnList;
					window.returnValue=retValue;
				}
				window.close();
			}
			function selectFields(tableName){
				var joinColumns="";
				var table1={};
				fromdatabaseid=document.getElementById("fromdatabaseid").value;
				table1 = window.showModalDialog("getDBTableColumns.action?selectFlag=selectFields&fromdatabaseid="+fromdatabaseid+"&fromtablename="+tableName);
				if(null!=table1&&""!=table1){
					for(var k=0;k<table1.columnList.length;k++){
						joinColumns+=table1.columnList[k].name+";";
					}
					document.getElementById("ddtjoinedfields").value=joinColumns;
				}
			}
			function subSchemeTable()
			{
				var cols = document.getElementsByName("columns");
				for(var i=0;i<cols.length;i++){
					if(cols[i].checked){
						var column={};
						var attribute=eval('('+cols[i].value+')');
						column.name=attribute.fromcolumns;
						column.type=attribute.fromcloumnstype;
						column.size=attribute.fromcloumnssize;
						if(null!=attribute.fromcolumnsscribe||''!=attribute.fromcolumnsscribe){
							column.describe=attribute.fromcolumnsscribe;
						}else{
							column.describe=" ";
						}
						columnList.push(column);
					}
				}
				var screen;
				var mainTable;
				var isMain = document.getElementById("isMainTable").checked;//是否是主表
				if(isMain){
					mainTable=1;
				}else{
				    mainTable=0;
				}
				document.getElementById("ddtismaintable").value=mainTable;
				var isScreened = document.getElementById("isScreen").checked;//是否筛选
				if(isScreened){
					screen=1;
				}else{
					screen=0;
				}
				document.getElementById("ddtisscreen").value=screen;
				var columnValue="";
				for(var k=0;k<columnList.length;k++){
					columnValue+=columnList[k].name+";"+columnList[k].type+";"+columnList[k].size+";"+columnList[k].describe+"%";
				}
				document.getElementById("jsonarray").value=columnValue;
				
				var ddcid = document.getElementById("ddcid").value
				if(null!=ddcid){
					document.tableform.action="saveTableInfo.action?ddcid"+ddcid;
					document.tableform.submit();
				}else{
					alert("没有选中导出方案，无法增加导出表！请回到上一步");
				}
			}
			function goback(){
				window.close();
			}
  		</script>
	</head>
	<body>
	<s:form  id="tableform" name="tableform"  theme="simple">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
			<tr>
				
				<td  align="left">
					<s:if test="ddcid!=null">
						<a class="mini-button" onclick="subSchemeTable();" plain="true" style="width:40px">提交</a>
					</s:if>
					<s:else>
						<a class="mini-button" onclick="dosubmit('<s:property value="#request.fromtablename"/>');" plain="true" style="width:40px">确定</a>
						<input type="hidden" id="jsonarray" name="jsonarray" id="jsonarray" />
					</s:else>
						<a class="mini-button" onclick="goback();" plain="true" style="width:40px">取消</a>
				</td>
			</tr>
			<s:if test="ddcid!=null">
			<tr>
			<td >
				<input type="hidden" id="ddcid" name="ddcid" value="<s:property value="#request.ddcid"/>" />
				<input type="hidden" id="fromdatabaseid" name="fromdatabaseid" value="<s:property value="#request.fromdatabaseid"/>" />
				<input type="hidden" id="jsonarray" name="jsonarray"  />
				 <table id="tableForCheck" cellspacing="0" cellpadding="6" rules="all" border="1" id="gvList" style="width:100%;border-collapse:collapse;">
                      <tr onMouseOver="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onMouseOut="this.style.backgroundColor=e">
                      <td >编号</td>
                      <td><s:property value="id.ddtid"/></td>
                       <td >名称</td>
                       <td><input type="text" name="fromtablename" id="fromtablename" value="<s:property value="#request.fromtablename"/>"/></td>
                       <td >备注</td>
                       <td><s:textfield id="descrition" name="descrition"/></td>
                       <td >是否是主表</td>
                       <td><s:hidden id="ddtismaintable" name="ddtismaintable"></s:hidden><input type="checkbox" name="isMainTable"  id="isMainTable" /></td>
                       <td >是否筛选</td>
                       <td><s:hidden id="ddtisscreen" name="ddtisscreen" id="ddtisscreen"></s:hidden><input type="checkbox" name="isScreen" id="isScreen" /></td>
						<td >导出序号</td>
						<td style="height:20px;width:5%;">
						<input type="text" name="ddtorder" id="ddtorder" value="<s:property value="ddtorder"/>"></input>
						</td>
						<td >关联字段</td>
						<td>
                     	<s:textfield name="ddtjoinedfields" id="ddtjoinedfields"/>
							<a class="mini-button" onclick="selectFields('<s:property value="#request.fromtablename"/>');" plain="true" style="width:40px">选择关联字段</a>
						</td>
                      	</tr> 
	            </table>
			</td>
			</tr>
			</s:if>
			<tr>
				<td  colspan="3">
					<table id="columnsList" cellspacing="0"
						cellpadding="4" rules="all" border="1" id="Datagrid1"
						style="width: 100%; border-collapse: collapse;">
						<tr >
						<td colspan="6">选择要导出的列
						</td>
						</tr>
						<tr>
							<td>列名
							</td>
							<td>列描述
							</td>
							<td>字段长度
							</td>
							<td>默认值
							</td>
						</tr>
						
							<s:set name="index" id="index" value="0"/>
							<s:iterator id="fromcolumnslist" value="#request.fromColumnsList" status="st">
							<s:set name="index" id="index" value="#index + 1"/>
							<tr  align="left">
							<td >
							<input type="checkbox"  name="columns" id="column<s:property value='#index'/>"  value="{'fromcolumns':'<s:property value='#fromcolumnslist.dcdfromcolumns'/>',
													  'fromcolumnsscribe':'<s:property value='#fromcolumnslist.dcdfromcolumnsscribe'/>',
													  'fromcloumnstype':'<s:property value='#fromcolumnslist.dcdfromcloumnstype'/>',
													  'fromcloumnssize':'<s:property value='#fromcolumnslist.dcdfromcloumnssize'/>',
													  'fromcloumnsdefaultvalue':'<s:property value='#fromcolumnslist.dcdfromcloumnsdefaultvalue'/>',
													  'fromcolumnspoint':'<s:property value='#fromcolumnslist.dcdfromcolumnspoint'/>'}" /><font color="green"><s:property value="#fromcolumnslist.dcdfromcolumns"/></font>
							
							</td>
							<td>
							<input type="text" name="fromcolumnsscribe" id="fromcolumnsscribe<s:property value='#index'/>" value='<s:property value='#fromcolumnslist.dcdfromcolumnsscribe'/>'  size="10"/>
							</td>
							<td>
							<input type="text" name="fromcloumnssize" id="fromcloumnssize<s:property value='#index'/>" value='<s:property value='#fromcolumnslist.dcdfromcloumnssize'/>' size="10"/>
							</td>
							<td>
							<input type="text" name="fromcloumnsdefaultvalue" id="fromcloumnsdefaultvalue<s:property value='#index'/>" value="<s:property value='#fromcolumnslist.fromcloumnsdefaultvalue'/>"  size="10"/>
							</td>
							</tr>
							</s:iterator>
						
					</table>
				</td>
			</tr>
		</table>
	</s:form>
	</body>
</html>
