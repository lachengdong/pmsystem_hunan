<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>数据库列表showTables.jsp</title>
		<script language="JavaScript" src="<%=path %>/js/json.js"></script>
		<script language="JavaScript" src="<%=path %>/js/CutString.js"></script>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	   <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	   <style type="text/css">
	   		 html, body
		    {
		        font-size:12px;
		        padding:0;
		        margin:0;
		        border:0;
		        overflow:auto;
		        width:98%;
		    }
	    </style>
		<script type="text/javascript">
			function SelectAllCheckboxes(spanChk)
			{
				var xState=spanChk.checked;
				var theBox=spanChk;
				elm= document.getElementsByName("tables");
				for(i=0;i<elm.length;i++)
				{
					if(elm[i].type=="checkbox" && elm[i].id!=theBox.id)
					{
						if(elm[i].checked!=xState)
						elm[i].click();
					}
				}
			 }
			 function SelectAllColumns(spanChk)
			 {
				var xState=spanChk.checked;
				var theBox=spanChk;
				elm= document.getElementsByName("isScreen");
				for(i=0;i<elm.length;i++)
				{
					if(elm[i].type=="checkbox" && elm[i].id!=theBox.id)
					{
						if(elm[i].checked!=xState)
						elm[i].click();
					}
				}
			 }
			function dosubmit()
			{
				for(var i=0;i<data.tableList.length;i++){
					alert(data.tableList[i].name);
				}
			}
			function selectTables(){
				elm= document.getElementsByName("tables");
				for(i=elm.length;i>0;i--)
				{
					if(!elm[i-1].checked){
					  var Container = document.getElementById("tableForCheck");
					  Container.deleteRow(i); 
					}
				}
			}
			function showColumns(index,tableName)
			{
				var fromdatabaseid=window.parent.document.getElementById("fromdatabaseid").value;
				var table11 = window.showModalDialog("getDBTableColumns.action?fromdatabaseid="+fromdatabaseid+"&fromtablename="+tableName,"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: yes;dialogHeight:700px;dialogWidth:1000px");
				if(table11!=null){
					var revalue=table11.columnList;
					var hiddenvalue="[";
					var textvalue="";
					for(var k=0;k<revalue.length;k++){
						if(textvalue=="")
						{
							textvalue=revalue[k].name;
						}
						else{
							textvalue=textvalue+","+revalue[k].name;
						}
						if(hiddenvalue=="[")
						{
							hiddenvalue=hiddenvalue+"{'name':'"+revalue[k].name+"','type':'"+revalue[k].type+"','size':'"+revalue[k].size+"','describe':'"+revalue[k].describe+"','defaultValue':'"+revalue[k].defaultValue+"'}";
						}
						else{
							hiddenvalue=hiddenvalue+",{'name':'"+revalue[k].name+"','type':'"+revalue[k].type+"','size':'"+revalue[k].size+"','describe':'"+revalue[k].describe+"','defaultValue':'"+revalue[k].defaultValue+"'}";
						}
					}
					hiddenvalue=hiddenvalue+"]";
					if(textvalue==""){
						textvalue="select * from "+tableName;
					}
					else{
						textvalue="select "+textvalue+" from "+tableName; 
					}
					document.getElementById("ddtjoinedfields"+index).value=textvalue;
					document.getElementById("joinedfields"+index).value=hiddenvalue;
				}
			}
			function selectFields(index,tableName){
				var joinColumns="";
				var joinFields="";
				var table1={};
				var fromdatabaseid=document.getElementById("fromdatabaseid").value;
				table1 = window.showModalDialog("getDBTableColumns.action?selectFlag=selectFields&fromdatabaseid="+fromdatabaseid+"&fromtablename="+tableName,"","edge: Raised; center: Yes; help: Yes; scroll:Yes; resizable: Yes; status: yes;dialogHeight:700px;dialogWidth:1000px");
				if(null!=table1&&""!=table1){
					for(var k=0;k<table1.columnList.length;k++){
						joinColumns+=table1.columnList[k].name+";";
						if(null!=table1.columnList[k].type){
							if('INT'==table1.columnList[k].type ||'INTEGER'==table1.columnList[k].type ||'FLOAT'==table1.columnList[k].type 
									 ||'NUMERIC'==table1.columnList[k].type ||'DECIMAL'==table1.columnList[k].type ||'BIGINT'==table1.columnList[k].type
									 ||'SMALLINT'==table1.columnList[k].type ||'NUMBER'==table1.columnList[k].type ||'DOUBLE'==table1.columnList[k].type)
								joinFields+="a."+table1.columnList[k].name+"=[@_"+table1.columnList[k].name+"_@] and ";
							else
								joinFields+="a."+table1.columnList[k].name+"='[@_"+table1.columnList[k].name+"_@]' and ";
						}
					}
					joinFields=joinFields.substring(0,joinFields.length-4);
					document.getElementById("ddtjoinedfieldsarr"+index).value=joinFields;
					document.getElementById("joinedfieldsarr"+index).value=joinFields;
				}
			}
			function subScheme()
			{
				//获取主页面数据并提交子页面
				document.getElementById("fromdatabaseid").value=window.parent.document.getElementById("fromdatabaseid").value;
				document.getElementById("todatabaseid").value=window.parent.document.getElementById("todatabaseid").value;
				document.getElementById("ddcname").value=window.parent.document.getElementById("ddcname").value;
				document.getElementById("ddctype").value=window.parent.document.getElementById("ddctype").value;
				if(confirm('如果不选择导出列，默认导出表全部列!')){
					window.document.form1.action="saveExpScheme.action";
					window.document.form1.submit();
				}
			}
			function upstep(){
				window.parent.document.getElementById("schememain").style.display="";
				window.parent.document.getElementById("schemeiframe").style.display="none";
			}
			function doback(){
				window.parent.window.location.href=("showSchemeList.action");
			}
			function mainTable(isMain){
				window.document.getElementById("ddtismaintable").value=isMain.value;
			}
			function myreset()
			{
				if(confirm("刷新后将清空现有配置，确定要刷新吗？"))
					window.parent.window.myreset();
			}
		</script>
	</head>
	<body >
		<s:form name="form1" action="" theme="simple">
			<s:hidden id="fromdatabaseid" name="fromdatabaseid"></s:hidden>
			<s:hidden id="todatabaseid"  name="todatabaseid"></s:hidden>
			<s:hidden id="ddcname"  name="ddcname"></s:hidden>
			<s:hidden id="ddctype"  name="ddctype"></s:hidden>
			<s:hidden id="ddtismaintable"  name="ddtismaintable"></s:hidden>
			<table width="100%" height="" border="0" cellspacing="0" cellpadding="0" class="mini-toorbar">
				<tr >
					<td align="right">
						<a class="mini-button" onclick="selectTables();" plain="true" style="width:60px">筛选表</a>  
						<a class="mini-button" onclick="myreset();" plain="true" style="width:40px">刷新</a> 
					</td>
				</tr>
				<tr>
				<td >
				 	<table id="tableForCheck"  cellspacing="0" cellpadding="0" rules="all" border="1" id="gvList" style="width:100%;border-collapse:collapse;">
                                 <tr >
                                 	<th style="height:20px;width:5%;" height="10">
											<input type="checkbox" id="gvList_ctl01_chkAll"
												name="checkall"
												onclick="javascript:SelectAllCheckboxes(this);"
												/>
									</th>
	                                <th style="height:20px;width:20%;" scope="col" >名称</th>
	                                 <th style="height:20px;width:15%;" scope="col" >描述</th>
	                                <th style="height:20px;width:5%;" scope="col" >主表</th>
									<th style="height:20px;width:10%;" scope="col" >排序</th>
									 <th style="height:20px;width:30%;" scope="col" >查询SQL</th>
									 <th style="height:20px;width:25%;" scope="col" >数据关系</th>
									 <th style="height:20px;width:20%;" scope="col" >额外查询条件</th>
                                       </tr>
                                <s:set id="index" name="index" value="0"/>
         						 <s:iterator value="#request.tablesList">
         						 <s:set id="index" name="index" value="#index + 1"/>
                            <tr  onMouseOver="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onMouseOut="this.style.backgroundColor=e">
                            	 <td>
									<center>
										<input type="checkbox" name="tables"  id="<s:property value="tableName"/>" value="<s:property value="tableName"/>"   />
									</center>
								</td> 
                            	<td><input type="hidden" name="tableNamearr" id="tableName<s:property value='#index'/>" value="<s:property value="tableName"/>"/><a href="#" onclick="showColumns('<s:property value="#index"/>','<s:property value="tableName"/>')"><script language="javascript">CutStr('<s:property value="tableName"/>',30); </script></a></td>
                            	<td><input type="text" name="descritionarr" id="descrition<s:property value='#index'/>" value="<s:property value="tableRemarks"/>" size="15"/></td>
                            	<td align="center"><input type="radio" name="isMainTable" value="<s:property value="tableName"/>" id="isMainTable<s:property value='#index'/>" onclick="mainTable(this);" /></td>
                            	<td align="center"><input type="text" name="ddtorderarr"  id="ddtorder<s:property value='#index'/>" size="3" /></td>
                            	<td><input type="hidden" name="joinedfieldsarr" id="joinedfields<s:property value='#index'/>"/>
                            	<input type="text" name="ddtjoinedfieldsarr" id="ddtjoinedfields<s:property value='#index'/>" size="50" value="select * from <s:property value='tableName'/>"/>
								</td>
								<td>
									<input type="text" name="shujuguanxiarr" id="shujuguanxi<s:property value='#index'/>" size="15"/>
								</td>
								<td>
									<input type="text" name="addconditionarr" id="addcondition<s:property value='#index'/>" size="15"/>
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