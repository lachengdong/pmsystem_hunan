<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> aipmap = (Map<String,String>)request.getAttribute("aipmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path %>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
   		
		<title>省局办案审批</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
</head>
<body >
       <div class="mini-toolbar"  style="padding:2px;border-bottom:0px;">
            <table >
               <tr>
                <td style="width:100%;">
                	 <%
							if(topmap!=null && topmap.size()>0){
								for (String key : topmap.keySet()) {
								    String[] arry = topmap.get(key).split("@");
				       %>
				       <a class="mini-button" iconCls="" plain="true" onclick="<%=arry[1] %>"><%=arry[0] %></a>
				       <%
								}
							}
						%>
                </td>
                <td style="white-space:nowrap;">
                    <input class="mini-textbox" id="key" class="mini-textbox" style="width:200px;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter"/>
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="searchByAnjianhao()">查询</a>
                    <input id="sdid" class="mini-combobox" valueField="sdid" textField="sddiscribe"  emptyText="请选择监狱"
                            showNullItem="true" nullItemText="--全部--" url="" style="width:150px;" onvaluechanged="onjianyuchanged"
                           />
                    <script src="<%=path %>/scripts/loadaip2.js"></script>
				    <input id="crimid" name="crimid" class="mini-hidden" value="${crimid}" />
				    <input id="choosecriminal" name="choosecriminal" type="hidden" value=""/>
				    <input id="fullopen" name="fullopen" type="hidden" value=""/>
                </td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit">
	     <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  
	     allowResize="false" url="getsecretaryCheckList.action?1=1"  idField="" multiSelect="true" allowAlternating="true" 
	     sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">  
	        <div property="columns">
	        		<div type="checkcolumn" width="10px;"></div> 
	        		<div field="CRIMID" width="30px"  headerAlign="center"  allowSort="true" align="center" >罪犯编号</div> 
				    <div field="NAME" width="20px"  headerAlign="center"  	allowSort="true" align="center" >罪犯姓名</div>   
	            	<div field="ANJIANHAO" width="50px"  headerAlign="center"  	allowSort="true" align="center" >案件号</div>  
	            	<div field="JAILINFO" width="50px"  headerAlign="center"  	allowSort="true" align="center" >单位意见</div>  
                 	<div id="status" width="40px"  headerAlign="center" align="center" allowSort="false" renderer="onStatusRenderer">状态</div> 
                 	<div name="action" width="40px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
    </div>
 </div>
 <script type="text/javascript">
 mini.parse();
 var datagrid = mini.get("datagrid");
 datagrid.load();
 datagrid.sortBy("optime", "desc");

 // 渲染日期
 function onDateRenderer(e) {
 	if(e && e.value){
 		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
 	}
     return "";
 }
  function onStatusRenderer(){
 	var s = "";
 	var row = datagrid.getSelected();
 	s ='未处理&nbsp;&nbsp';
 	/*if(){
 		s ='未处理&nbsp;&nbsp';
 	}else if(){
 		s ='已处理&nbsp;&nbsp';
 	}else if(){
 		s ='正在处理&nbsp;&nbsp';
 	}*/
     return s;
 }
 
 function chooseOne(menuid) {
   	var row = datagrid.getSelected();
   	var url = "tosecretaryCheck.action?1=1&tempid=SJY_JXJSSHB&crimid="+row.CRIMID+"&menuid="+menuid;
			self.location.href=url;
   }   

 function chooseAll(){
 	var rows = grid.getSelecteds();
     if (rows.length > 0) {
         if (confirm("确定操作 选中记录？")) {
             var ids = [];
             for (var i = 0, l = rows.length; i < l; i++) {
                 var r = rows[i];
                 ids.push(r.docid);
             }
             var id = ids.join(',');
             $.ajax({
                 url: "",
                 type: "post",
                 success: function (text) {
                     grid.reload();
                 },
                 error: function () {
                 	alert("操作失败!");
                 }
             });
         }
     } else {
         alert(confirmMessages);
     }
	}
	
	        function onActionRenderer(e) {
	          var s ='';
       		<%
				if(middlemap!=null && middlemap.size()>0){
					for (String key : middlemap.keySet()) {
			   			String[] arry = middlemap.get(key).split("@");
	       	%>
	       				s += " <a class=\"Edit_Button\" href=\"javascript:<%=arry[1] %>\" ><%=arry[0] %></a>&nbsp;&nbsp;";
	       	<%
					}
				}
			%>
	     	return s;
	        }
	    
	      
 </script>
</body>
</html>
