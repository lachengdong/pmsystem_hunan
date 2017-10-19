<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>数据库管理databaseconnectionmessagelist.jsp</title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
  		<script src="<%=path%>/scripts/form/SignatureInsertNote.js" type="text/javascript"></script>
  		<script src="<%=path%>/scripts/archives.js" type="text/javascript"></script>
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
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mini-toolbar">
		<tr>
			<td class="mini-toolbar" height="30" >
			 数据库类型：<select name="databasetype">
	                       <option value="choose" >---请选择---</option>
							<option value="MySQL"/>MySQL</option>
							<option value="Oracle"/>Oracle</option>
							<option value="Microsoft SQL Server" />Microsoft SQL Server</option>
							<option value="Microsoft SQL Server 2005" />Microsoft SQL Server 2005</option>
							<option value="Sybase">Sybase</option>
							<option value="DB2" >DB2</option>
							<option value="Informix" >Informix</option> 
							<option value="Dameng" >Dameng</option> 
			             </select>&nbsp;&nbsp;&nbsp;&nbsp;
			  数据库IP：<input type="text" name="ddip" size="12" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;
			 数据库端口：<input type="text" name="port" size="4" value=""/>
							<a class="mini-button" onclick="findBySome()" plain="true" style="width:60px">查询</a>
							<a class="mini-button" onclick="dosubmit()" plain="true" style="width:60px">增加</a>  
							<a class="mini-button" onclick="deleteMult()" plain="true" style="width:80px">批量删除</a>    
			</td>
		</tr>
	</table>
			
    <div class="mini-fit">
	    <div id="datagrid1" style="width:100%;height:100%;" class="mini-datagrid" url="showDatabaseInfo.action" allowResize="false" idField="id" multiSelect="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true" enableHotTrack="false" allowRowSelect="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="25"></div>
	        		<div field="ddid" name="ddid" width="50" headerAlign="center" align="center" >
               			编号
               		</div>
               		<div field="ddname" name="ddname" headerAlign="center" align="center" >
               			数据库描述
               		</div>
               		<div field="databasetype" name="databasetype" headerAlign="center" align="center" >
               			数据库类型
               		</div>
               		<div field="ddip" name="ddip" headerAlign="center" align="center" >
               			数据库IP
               		</div>
           			<div field="databasename" name="databasename" headerAlign="center" align="center" >
               			数据库名称（监听名称）
               		</div>
               		<div field="ddorg" name="ddorg" headerAlign="center"  align="center" >
               			数据库所属部门
               		</div>
               		<div field="databaseuser" name="databaseuser" headerAlign="center" align="center" >
               			数据库用户名
               		</div>
               		<div field="databasepassword" name="databasepassword" headerAlign="center" align="center" >
               			数据库密码
               		</div>
               		<div field="port" name="port" headerAlign="center" align="center" >
               			数据库端口
               		</div>
			        <div width="50" headerAlign="center" align="center"	allowSort="false" renderer="onActionRenderer">操作</div> 
	        </div>
	    </div>
    </div>				
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
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
	    function dosubmit(){
		   window.location.href="addConnectionMessage.page";
		}
	 	function deleteMult(){
		 var rad=document.getElementsByName("connArr");
			if(rad.length>0)
			{
				var flag="0";
			    var val="";
			    for(var i=0;i<rad.length;i++)
			    {
			    	if(rad[i].checked==true)
			    	{
			        	flag="1";
			            break;
			        }
			           
				}
		        if(flag=="1")
		        {
			        if(window.confirm("此操作将从数据库中删除，不能恢复！是否继续执行?")){
			        	document.getConnectionMessageList.action="deleteConnectionMessageList.page";
			        	document.getConnectionMessageList.submit();
					}   
		        }
		        else
		        {
		          alert("请选择一条或多条信息进行删除");
		        }
			} 
		}
		function tiaozhuan(url){
			document.getConnectionMessageList.action=url;
	 		document.getConnectionMessageList.submit();
		}
		function shifoushanchu(url){
			if(window.confirm("数据删除后无法恢复,确认删除?"))
			{
				document.getConnectionMessageList.action=url;
	 			document.getConnectionMessageList.submit();
			}
		}
		function findBySome(){
		    document.getConnectionMessageList.action="getConnectionMessageList.action";
		 	document.getConnectionMessageList.submit();
		}
		
        function onActionRenderer(e) {
            var s = '<a class="Edit_Button" href="javascript:EditOne()" >编辑</a>';
               // s += '&nbsp;&nbsp;<a class="Edit_Button" href="javascript:DeleteOne()">删除</a>'; 
            return s;
        }
        function EditOneOne() {
        	var row = grid.getSelected();
        	var url = "showConnectionMessage.action?ddid="+row.ddid;
			self.location.href=url;
        }	
	</script>
	</body>
</html>