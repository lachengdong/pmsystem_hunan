<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String deplevel = (String)request.getSession().getAttribute("sdalarmlevel");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>预览打印</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
		
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
    <script type="text/javascript">
    		function myload(){
    			mini.parse();
		        mini.get("datagrid").load();
		        mini.get("datagrid").sortBy ( "rorderby", "asc" );
		        mini.get("casetype").select(0);
    		}
			function search() {
		         var key = mini.get("key").getValue();
		         mini.get("datagrid").load("key:key");
		    }
	        function onKeyEnter(e) {
	            search();
	        }
	        $("#key").bind("keydown", function (e) {
	            if (e.keyCode == 13) {
	                search();
	            }
	        });
	        function onActionRenderer(e) {
	            var record = e.record;
	            var uid = record.rid; 
	            var name=record.name;
	            //var grid = e.sender;
	            //var rowIndex = e.rowIndex;
	            var s='<a class="Edit_Button" href="javascript:viewbaobiao(\'' + uid+ '\',0,\'' + name+ '\')">预览</a>'+
	            	  '　<a class="Edit_Button" href="javascript:viewbaobiao(\'' + uid+ '\',1,\'' + name+ '\')">预览导出</a>';
	            return s;
	        }
	        function SetData(data) {
		         if (data.action == "edit") {
		             //跨页面传递的数据对象，克隆后才可以安全使用
		             data = mini.clone(data);
		         }
		     }
		     function viewbaobiao(rid,type,name){	 //alert("还未配置。");return;
				var anjianhaos = mini.get("anjianhao").getValue();
				//if(anjianhaos!=""){
					var caseyear = mini.get("caseyear").getValue();
					var casetype = mini.get("casetype").getText();
					var grid = mini.get("datagrid");
					var row = grid.getSelected();
					
					
			        var myurl= "getReducePenaltyByAnJianHao.action?1=1&menuid=" + row.resid + "&anjianhao=" + 
			        anjianhaos+"&caseyear="+caseyear+"&casetype="+encodeURI(casetype)+"&type="+type+"&doctype=report&name="+name;
 					var win=mini.open({
		                url: encodeURI(myurl),
			            showMaxButton: true,
			            contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			            //data: { menuid:row.resid,anjianhao:anjianhao,
					    //caseyear:caseyear,tempid:row.planid,flag:"flag"},
			            allowResize: true, 
			            title: "预览", width: 900, height: 550
		            });  
                    win.max();
				//}else {
					//alert("请输入案件号范围");
				//}
			}

	        //下拉清空
	        function onCloseClick(e) {
	            var obj = e.sender;
	            obj.setText("");
	            obj.setValue("");
	        }  	
	        
	         //报表名称查询
			function onNoteKeyFilterChanged(e) {
				var grid = mini.get("datagrid");
		         var textbox = e.sender;
		         var key = textbox.getValue().toLowerCase();
		         grid.filter(function (row) {
		             var notekey = String(row.name).toLowerCase();
		             if (notekey.indexOf(key) != -1) return true;
		             return false;
		         });
		     }			
		</script>
</head>
<body onload="myload()">
       <div class="mini-toolbar"  style="padding:2px;border-bottom:0px;">
            <table >
               <tr>
                <td style="width:100%;">
                   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					案件号范围：
						(<input class="mini-textbox" style="width:60px" value="${caseyear}" vtype="maxLength:4;minLength:4;int;" required="true" id="caseyear" name="caseyear"/>)
						<input class="mini-textbox" style="width:60px" value="${shortname}"  required="true"/>
					    <input id="casetype"  name="casetype" class="mini-combobox" valueField="id" textField="name"  required="true"  style="width:75px"
                         style="width:100px"  url="getPreviewPringCaseType.action"  />
                        <c:if test="${provincecode=='1500' || provincecode=='4100' || provincecode=='4300' || provincecode=='6400'}">
	    					<input class="mini-combobox"  id="xingqileixing"  name="xingqileixing"  style="width:80px;"   emptyText="刑期类型" value="1"
	    				 	data="[{ id: 1, text: '有期' }, { id: 2, text: '无期'},{ id: 3, text: '死缓'}]" showNullItem="true" onvaluechanged="search" />
	    				</c:if> 
						第<input class="mini-textbox"  style="width:350px;height:20px" id="anjianhao" name="anjianhao" emptyText="此处输入案件号：1,2或3-5或1,2,3-5" />号
                </td>
                <td style="white-space:nowrap;">
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit"><!-- ajaxGetBaoBiaoList.action?1=1&type=yldy&rtype=<s:property value='#request.rtype'/>&casetype=${casetype} -->
	    <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid"  
	    allowResize="false" url="<%=path %>/print/getPreviewReportList.action?1=1" 
	     idField="id" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showFilterRow="true">
	        <div property="columns">
	        	<!--  
            	<div field="sn" width="5%" headerAlign="center"   align="center">排序号</div>
            	-->
            	<div type="indexcolumn">序号</div>
	        	<div field="name" width="80%"  headerAlign="center"  align="left" >
            		报表名称
            		<input id="keyFilter" property="filter" emptyText="报表名称查询，按回车键过滤" class="mini-textbox" onvaluechanged="onNoteKeyFilterChanged" style="width:100%;" />
            	</div>
            	<!-- 
            	<div field="sn"  headerAlign="center" visible="false"  	allowSort="true" align="left" >
            		主键
            	</div>
            	<div field="type" width="85%"  headerAlign="center" 	allowSort="true" align="left" >
            		数据集
            	</div>
            	 -->
            	<div name="action"  headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
</body>
</html>