<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html>
<head>
	<title>建议书管理</title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
  	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }     
    </style>
</head>
<body onload="init('${menuid}');">
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    	 <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
         <table >
            <tr>
               <td style="width:100%;">
               	   <a class="mini-button"  id="${menuid}" plain="true" onclick="chooseAll();">批量处理</a>
               </td>
               <td style="white-space:nowrap;">
               		<input class="mini-textbox" id="casenums" class="mini-textbox" style="width:200px;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter"/>
                   	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="请输入罪犯编号、姓名、拼音"  onenter="onKeyEnter"/>
                   	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
				    <!-- 操作说明 -->
	 			    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming()"></a>
	 		   </td>
            </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	    	url="getCrimSuggestionList.action?1=1&flowdefid=${flowdefid}"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="15px"></div>
				    <div field="anjianhao" width="50px"  headerAlign="center" allowSort="true" align="center" >案件号</div> 
	            	<div field="crimid" width="30px"  headerAlign="center" allowSort="true" align="center" >罪犯编号</div> 
				    <div field="name" width="30px"  headerAlign="center" allowSort="true" align="center" >罪犯姓名</div>  
					<div field="commenttext" width="100" headerAlign="center" align="left"  allowSort="true">审核人意见</div>
                 	<div id="status" width="40px"  headerAlign="center" align="left" allowSort="false" renderer="onStatusRenderer">审批状态</div> 
             		<div id="action" width="30px"  headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>       			
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
    	mini.parse();
		var grid = mini.get("datagrid");
		grid.load();
		var menuids=${menuid};
		function onActionRenderer(e) {
	        var s ='';
	        if(menuids=='12513'){
	         s += " <a class=\"Edit_Button\" href=\"javascript:chooseOne('10090_05');\" >办理</a>&nbsp;";
	        }    //16105为保外就医建议书管理资源id  资源配置url为 toSuggestionManageListPage.page?1=1&modetype=jw
			else if(menuids=='16105'){
			 s += " <a class=\"Edit_Button\" href=\"javascript:chooseOne('12599');\" >办理</a>&nbsp;";
			}
	     	return s;
	    }
        function onStatusRenderer(e){
        	var s = "";
        	var row = e.record;
        	var state = row.state;
        	if(state=='0'){
        		s ='正在审批&nbsp';
        	}else{
        		s =row.conent;
        	}
            return s;
        }
        //快速查询          	crimid="+crimid+"&flowdraftid="+flowdraftid+"&flowid="+flowid+"&flowdefid="+flowdefid+"&doctype=jailcommute&resid=12498";
        
     	function onKeyEnter(e) {
            search();
        }
        function search() {
        	var casenums = mini.get("casenums").getValue();
            var key = mini.get("key").getValue();
        	grid.sortBy("crimid","desc");
            grid.load({ key: key, casenums:casenums});
        }
        function chooseOne(menuid){
          	var row = grid.getSelected();
          	if (row){
          	if(menuids=='12513'){
          	  var url = "toSuggestionDocPage.action?1=1&menuid=12513&resid=12498&doctype=jailcommute&flowdraftid="+row.flowdraftid+"&crimid="+row.crimid+"&flowdefid=other_zfjyjxjssp";
          	}
        	else if(menuids=='16105'){
        	  var url = "toSuggestionDocPage.action?1=1&menuid=16105&resid=12753&doctype=jailbaowai&flowdraftid="+row.flowdraftid+"&crimid="+row.crimid+"&flowdefid=other_jybwjycbsp";
        	}	
				window.location.href=url;
          	} else {
              	alert(confirmMessage);
          	}
        }
        //批量处理          
	    function chooseAll(menuid){
	    	var rows = grid.getSelecteds();
	   		if (rows.length > 0) {
                if (confirm("确定批量处理选中记录？")) {
                    var ids = [];
                    for (var i = 0; i < rows.length; i++) {
                        var r = rows[i];
                        ids.push(r.flowdraftid+"@"+r.crimid);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    if(menuids=='12513'){
                      var url = "toSuggestionDocPage.action?1=1&menuid=12513&resid=12498&doctype=jailcommute&id="+id+"&flowdefid=other_zfjyjxjssp";
                    }
                    else if(menuids=='16105'){
                      var url = "toSuggestionDocPage.action?1=1&menuid=16105&resid=12753&doctype=jailbaowai&id="+id+"&flowdefid=other_jybwjycbsp";
                    }
                   	
					self.location.href=url;
                }
            } else {
                alert(confirmMessages);
            }
	    }
    </script>
</body>
</html>

        