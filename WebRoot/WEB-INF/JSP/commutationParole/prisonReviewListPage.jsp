<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/archives.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>监区评议（假释）</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
    <script type="text/javascript">
     //快速查询
        function search() {
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }
    
	  function chooseOne(menuid) {
        	var row = grid.getSelected();
        	var url = "toPrisonReviewTabs.action?1=1&tempid=XFZX_ZFJXJSSHB&crimid="+row.CRIMID+"&menuid="+menuid;
			self.location.href=url;
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
        	function onPageChanged() {
        		myloading();
        	}
        	function myloading(){
        		var myform = new mini.Form("datagrid");
       			myform.loading();
        	}
      		function unmask(){
      			myunmask();
      		}
        	function myunmask() {
        		var myform = new mini.Form("datagrid");
       			myform.unmask();
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
        
		</script>
</head>
<body>
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
       <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
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
                   		<!-- 	<a class="mini-button" iconCls="icon-downgrade" plain="true" onclick="">批量提交</a>
                   			<a class="mini-button" iconCls="icon-undo" plain="true" onclick="">批量退案</a>
                   			<a class="mini-button" iconCls="icon-upgrade" plain="true" onclick="">不予办理</a>
                   			<a class="mini-button" iconCls="icon-collapse" plain="true" onclick="">监区长办公会</a>
                   			<a class="mini-button" iconCls="icon-collapse" plain="true" onclick="">审查报告(监区报科室)</a> -->
                </td>
                <td style="white-space:nowrap;">
                    	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="编号,姓名"  onenter="onKeyEnter"/>
                    	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('422431')"></a>
                    <script src="<%=path %>/scripts/loadaip2.js"></script>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" url="getPrisonReviewList.action"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="10"></div> 
				            			<div field="ANJIANHAO" width="50px"  headerAlign="center"  	allowSort="true" align="center" >
					               			案件号
					               		</div>  
	            						<div field="CRIMID" width="30px"  headerAlign="center"  	allowSort="true" align="center" >
					               			罪犯编号
					               		</div> 
				            			<div field="NAME" width="20px"  headerAlign="center"  	allowSort="true" align="center" >
					               			罪犯姓名
					               		</div>  
				            			<div field="COURTCHANGETO" width="30px"  headerAlign="center"  dateFormat="yyyy-MM-dd"	allowSort="true" align="center" renderer="onDateRenderer">
					               			刑期止日
					               		</div>  
				            			<div field="JIANGGEQI" width="30px"  headerAlign="center"  	allowSort="true" align="center" >
					               			间隔期
					               		</div>  
				            			<div field="JAILINFO" width="50px"  headerAlign="center"  	allowSort="true" align="center" >
					               			监区意见
					               		</div>  
				            			<div field="SHENGYUXINGQI" width="40px"  headerAlign="center"  	allowSort="true" align="center" >
					               			剩余刑期
					               		</div>  
                 						<div id="status" width="40px"  headerAlign="center" align="center" allowSort="false" renderer="onStatusRenderer">
                 							状态
                 						</div> 
             							 <div id="action" width="40px"  headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">
             							 	操作
             							 </div>  
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
    	mini.parse();
    			mini.get("datagrid").sortBy("caseno", "desc");
		        var grid = mini.get("datagrid");
		        grid.load();
		         // 渲染日期
        function onDateRenderer(e) {
        	if(e && e.value){
        		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
        	}
            return "";
        }
        
         
         function onStatusRenderer(){
        	var s = "";
        	var row = grid.getSelected();
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
    </script>
</body>
</html>

