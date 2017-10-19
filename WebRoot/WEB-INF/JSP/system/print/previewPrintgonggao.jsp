<%@ page language="java" pageEncoding="UTF-8"  %>
<%
	String path = request.getContextPath();
	String deplevel = (String)request.getSession().getAttribute("sdalarmlevel");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
   		
		<title>预览打印(法院公告)</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
    <script type="text/javascript">
    		mini.parse();
    		function myload(){
		        //mini.get("datagrid").load();
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
	            //var grid = e.sender;
	            //var rowIndex = e.rowIndex;
	            var s='<a class="Edit_Button" href="javascript:viewbaobiao(\'' + uid+ '\')">预览</a>'
	            return s;
	        }
	        function SetData(data) {
		         if (data.action == "edit") {
		             //跨页面传递的数据对象，克隆后才可以安全使用
		             data = mini.clone(data);
		         }
		     }
		     function viewbaobiao(rid){	 //alert("还未配置。");return;
				var anjianhaos = mini.get("anjianhao").getValue();
				var kaitingdate=mini.get("kaitingdate").getValue();
				var kaitingend=mini.get("kaitingend").getValue();
				var zhangtiedate=mini.get("zhangtiedate").getValue();
				var jianyuname=mini.get("jianyuname").getValue();
				if(kaitingdate==""||kaitingend==""||zhangtiedate==""){
					alert("请设置开庭日期等相关日期！");
					return;
				}
				if(jianyuname==""){
					alert("请选择开庭监狱！");
					return;
				}
				if(anjianhaos!=""){
					var caseyear = mini.get("caseyear").getValue();
					var casetype = mini.get("casetype").getText();
					var kaitingdate=mini.get("kaitingdate").text;
					var kaitingend=mini.get("kaitingend").text;
					var zhangtiedate=mini.get("zhangtiedate").text;
					var jianyuname=mini.get("jianyuname").getValue();
					var grid = mini.get("datagrid");
					var row = grid.getSelected();
					
					
			        var myurl= "courtReport/toFayuanGonggaoPage.action?1=1&menuid=" + row.resid + "&anjianhao=" + anjianhaos+"&caseyear="+
			        caseyear+"&casetype="+encodeURI(encodeURI(casetype))+"&kaitingdate="+kaitingdate+"&kaitingend="+
			        kaitingend+"&zhangtiedate="+zhangtiedate+"&jianyuname="+jianyuname;
 					var win=mini.open({
		                url: myurl,
			            showMaxButton: true,
			            contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			            //data: { menuid:row.resid,anjianhao:anjianhao,
					    //caseyear:caseyear,tempid:row.planid,flag:"flag"},
			            allowResize: true, 
			            title: "预览", width: 900, height: 550
		            });  
                    win.max();
				}else {
					alert("请输入案件号范围");
				}
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
		     
		     //查询已保存的公告。
		     function searchdata(){
		     	var anjiannian = mini.get("caseyear").getValue();
				var anjianleixing = mini.get("casetype").getText();
				var anjianhao = mini.get("anjianhao").getValue();
				var starttime=mini.get("kaitingdate").text;
				var endtime=mini.get("kaitingend").text;
				var zhangtiedate=mini.get("zhangtiedate").text;
				var kaitingjianyu=mini.get("jianyuname").getValue();
		     
		     	mini.open({
	                url: "<%=path%>/courtReport/toSearchFayuanGonggaoPage.action?1=1",
	                title: "公告列表", width: 900, height: 550,
	                onload: function () {
	                    var iframe = this.getIFrameEl();
	                    var data = {anjiannian:anjiannian,anjianleixing:anjianleixing,anjianhao:anjianhao,starttime:starttime,endtime:endtime,zhangtiedate:zhangtiedate,kaitingjianyu:kaitingjianyu};
	                    iframe.contentWindow.SetData(data);
	                },
	                ondestroy: function (action) {
	                }
	            });
		     }		
		</script>
</head>
<body onload="myload()">
       <div class="mini-toolbar"  style="padding:2px;border-bottom:0px;">
            <table >
               <tr>
                <td style="width:100%;">
						(<input class="mini-textbox" style="width:40px" value="${caseyear}" vtype="maxLength:4;minLength:4;int;" required="true" id="caseyear" name="caseyear"/>)
						<input class="mini-textbox" style="width:40px" value="${shortname}"  required="true"/>
					    <input id="casetype"  name="casetype" class="mini-combobox" valueField="id" textField="name"  required="true"  style="width:75px"
                         style="width:100px"  url="getPreviewPringCaseType.action"  />
						第<input class="mini-textbox"  style="width:150px;height:20px" id="anjianhao" name="anjianhao" emptyText="案件号：1,2或3-5" />号
						开庭时间：<input id="kaitingdate"  class="mini-datepicker" format="yyyy-MM-dd"   emptyText="开始时间" />至<input id="kaitingend"  class="mini-datepicker" format="yyyy-MM-dd" emptyText="结束时间" />&nbsp;&nbsp;
						张贴时间：<input id="zhangtiedate"  class="mini-datepicker" format="yyyy-MM-dd"   emptyText="张贴时间" />
						开庭监狱：<input id="jianyuname" class="mini-combobox" emptyText="开庭监狱" url="<%=path %>/getJianYuList.action?1=1" textField="NAME" valueField="ORGID"  style="width:100px;" />
                </td>
                <td style="white-space:nowrap;">
                <a class="mini-button" iconCls="icon-search" plain="true" onclick="searchdata();">查询</a>
                </td>
               </tr>
		</table>
    </div>
    <div id="yulandata" class="mini-fit"><!-- ajaxGetBaoBiaoList.action?1=1&type=yldy&rtype=<s:property value='#request.rtype'/>&casetype=${casetype} -->
	    <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid"  
	    allowResize="false" url="<%=path %>/print/getPreviewReportList.action?1=1&reporttype=${type}" 
	     idField="id" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showFilterRow="true">
	        <div property="columns">
            	<div field="sn" width="5%" headerAlign="center"   align="center">排序号</div>
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