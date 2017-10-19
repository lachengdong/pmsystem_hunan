<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>代码类型管理codechememanages.jsp</title>
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
		<script type="text/javascript">
			function SelectAllCheckboxes(spanChk)
			{
				var xState=spanChk.checked;
				var theBox=spanChk;
				elm=theBox.form.elements;
				for(i=0;i<elm.length;i++)
				{
					if(elm[i].type=="checkbox" && elm[i].id!=theBox.id)
					{
						if(elm[i].checked!=xState)
						elm[i].click();
					}
				}
	        }
			function dosubmit(v){
				document.getCodechemeList.action=v;
				document.getCodechemeList.submit();
			}
			function subForm(v){
				document.getCodechemeList.action=v;
				document.getCodechemeList.submit();
			}
			function setValue(v1,v2,v3,v4,v5,v6){
				document.getElementById("trd").style.display='block';
				document.getElementById("id2").style.display='block';
				document.getElementById("codetypes").value=v1;
			    document.getElementById("content").value=v2;
				document.getElementById("dccid").value=v3;
				document.getElementById("codeid").value=v4;
				document.getElementById("codecontent").value=v5;
				document.getElementById("targetid").value=v6;
			}
			function doadd(){
				document.getElementById("trd").style.display='block';
				document.getElementById("ids").style.display='block';
			}
			function goback(){
				document.getElementById("trd").style.display='none';
			}
			
			function myload(){
				var myid = document.getElementById("myid").value;
				var selectobj = document.getElementById("codetype");
				jsSelectItemByValue(selectobj,myid);
				
			}
			function jsSelectItemByValue(objSelect, objItemText) {            
			    //判断是否存在        
			    var isExit = false;        
			    for (var i = 0; i < objSelect.options.length; i++) {        
			        if (objSelect.options[i].value == objItemText) {        
			            objSelect.options[i].selected = true;        
			            isExit = true;        
			            break;        
			        }        
			    }              
			}  
		</script>
	</head>
	<body onload="myload();">
			<input type="hidden" id="myid" value="${myid }"/>
			<form id="getCodechemeList" name="getCodechemeList"  theme="simple">
				<table width="100%; border-collapse: collapse" class="mini-toolbar" border="0" cellspacing="0" cellpadding="0" border="1">
						<tr>
							<td width="80%" align="right">
								代码类型：<select name="codetype" id="codetype"> 
								     <option value="-1">--请选择--</option>
									<iterator id="codeList" value="#request.codeList">
										<option value="<property value="#codeList.codetypeid" />"><property value="#codeList.codetypename" /></option>
									</iterator>
								</select>		
								源代码内容：<input type="text" id="codecontent2" name="codecontent2" size="15"/>
								<a class="mini-button" onclick="dosubmit('getCodechemeList.action');" plain="true" style="width:60px">查询</a>
								<a class="mini-button" onclick="doadd();" plain="true" style="width:60px">增加</a>
							</td>
						</tr>
						<tr id="trd" style="display: none">
							<td  height="40" align="center">
								<table id="Table3" cellspacing="0" cellpadding="0" width="100%">
									 <tr>
										<td align="center">
											代码类型<input type="hidden" id="dccid" name="dccid" value=""/>
											</td>
										<td id="ids"style="display: none"><!-- 添加的时候显示 -->
										    <select id="codetypeid" name="codetypeid" >
									              <option value="-1">--请选择--</option>
													<iterator id="codeList" value="#request.codeList">
														<option value="<property value="#codeList.codetypeid" />"><property value="#codeList.codetypename" /></option>
													</iterator>
							                  </select>
										</td>
											<td id="id2"  style="display: none">
											<input type="hidden" id="codetypes" name="codetypes" value=""/>
											<input type="text" id="content" name="content" value="" size="10" readonly="readonly"/>
										
										</td>
										<td  align="center" >
											源代码编号
										</td>
										<td >
											<input type="text" id="codeid" size="10" name="codeid" value=""/>
										</td>
										<td  align="center">
											源代码内容
										</td>
										<td >
											<input type="text" id="codecontent" size="20" name="codecontent" value=""/>
										</td>
										<td align="center">目标代码编号</td>
										<td ><input type="text" id="targetid" size="10" name="targetid"/></td>
										<td  colspan="4" align="center">	
										<a class="mini-button" onclick="subForm('insertCodeCheme.action');" plain="true" style="width:60px">保存</a>
										<a class="mini-button" onclick="goback();" plain="true" style="width:60px">返回</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>	
						<tr>
							<td colspan="2">
								<table cellspacing="0" cellpadding="4"
												rules="all" border="1" id="Datagrid1"
												style="width: 100%; border-collapse: collapse;">
									<tr  align="center">
										<td height="10">
											<input type="checkbox" id="gvList_ctl01_chkAll"
												name="checkall"
												onclick="javascript:SelectAllCheckboxes(this);"/>
										</td>
										<td height="10">
											代码类型描述
										</td>
										<td height="10">
											源代码编号
										</td>
										<td height="10">
											源代码内容
										</td>
										<td height="10">
											目标代码编号
										</td>
										<td height="10">
											操作
										</td>
									</tr>
									<iterator id="codechemeList" value="#request.codechemeList"
										status="st">
										<tr onmouseover="e=this.style.backgroundColor; this.style.backgroundColor='LightSeaGreen'" onmouseout="this.style.backgroundColor=e">
											<td>
												<center>
													<input type="checkbox" name="connArr" id="connArr"
														value='<property value="#codechemeList.id.codetype" />;<property value="#codechemeList.id.dccid" />' />
												</center>
											</td>
											<td align="center">
												<property value="#codechemeList.dbmsCodeTypeNew.codetypename" />
											</td>
											<td align="center">
												<property value="#codechemeList.codeid" />
											</td>
											<td align="center">
												<property value="#codechemeList.codecontent" />
											</td>
											<td align="center">
												<property value="#codechemeList.targetid" />
											</td>
											<td align="center">
												<a class="mini-button" onclick="setValue('#codechemeList.id.codetype','#codechemeList.codetypename','#codechemeList.id.dccid','#codechemeList.codeid','#codechemeList.codecontent','#codechemeList.targetid')" plain="true" style="width:60px">编辑</a>
												<a class="mini-button" href="deleteCodeCheme.action?codetype=#codechemeList.id.codetype&dccid=#codechemeList.id.dccid" plain="true" style="width:60px">删除</a>
											</td>
										</tr>
									</iterator>
								</table>
							</td>
						</tr>
					</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<if test="#codeList.codetypeid != null">
						<tr>
							<td align="right" valign="bottom">
								<page:page name="pageController" title="" unit="条"
									frmName="getCodechemeList" actionName="getCodechemeList.action" />
							</td>
						</tr>
					</if>
					<else>
						<tr>
							<td align="right" valign="bottom">
								<page:page name="pageController" title="" unit="条"
									frmName="getCodechemeList" actionName="getCodeManagesList.action" />
							</td>
						</tr>
					</else>
				</table>
			</form>
	</body>
</html>