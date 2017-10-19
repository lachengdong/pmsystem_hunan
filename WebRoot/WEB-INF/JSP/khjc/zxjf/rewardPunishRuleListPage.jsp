<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
	Map<String,String> aipmap = (Map<String,String>)request.getAttribute("aipmap");
	Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>

<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>减刑幅度列表</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
		</style>
    <script type="text/javascript">
	</script>
</head>
<body>
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
                <td style="width:100%;">
	      			<%
						if(topmap!=null && topmap.size()>0){
							for (String key : topmap.keySet()) {
							    String[] arr = topmap.get(key).split("@");
							    String[] icoarr = key.split(",");
							    String ico="";
							    if(icoarr.length==2) ico = icoarr[1];
			       	%>
			       	<a class="mini-button" iconCls="<%=ico%>" plain="true" onclick="<%=arr[1] %>"><%=arr[0] %></a>
			       	<%
							}
						}
					%>
                </td>
                <td style="white-space:nowrap;">
                	<input name="key" id="key" class="mini-textbox" emptyText="搜索码,条款名称" onenter="onKeyEnter" allowInput="true" />
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                    <script src="<%=path %>/scripts/loadaip2.js"></script>
				    <input id="criminalid" name="criminalid" type="hidden" value=""/>
				    <input id="choosecriminal" name="choosecriminal" type="hidden" value=""/>
				    <input id="fullopen" name="fullopen" type="hidden" value=""/>
                </td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" style="height:100%;width:100%;" class="mini-datagrid"  url="ajaxGetRewardPunishRuleList.action?1=1" 
	    sizeList="[10,20,50,100]" pageSize="20" sortField="sn" sortOrder="desc" allowSort="true" showLoading="true" allowAlternating="true" multiSelect="true" >
	        <div property="columns">
	        		<div type="checkcolumn" width="20"></div>  
	        		<div field="RULEID" name="RULEID" width="60" headerAlign="center" align="center" allowSort="true">
               			条款编号
               		</div>
	        		<div field="INTRODUCTION" name="INTRODUCTION" width="160" headerAlign="center" align="center" allowSort="true">
               			条款名称
               		</div>
	        		<div field="SEARCHCODE" name="SEARCHCODE" width="50" headerAlign="center" align="center" allowSort="true">
               			搜索码
               		</div>               		
               		<div field="ISPROVISIONS" name="ISPROVISIONS" renderer="onTypeRenderer" width="40" headerAlign="center" align="center" allowSort="true" >
               			是否为考核条款
               		</div>
                 	<div name="action" headerAlign="center" width="50" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    
</body>
</html>
<script type="text/javascript">

	mini.parse();
	
	var grid = mini.get("datagrid");
	grid.load();
	grid.sortBy("ruleid", "asc");
	
	function onActionRenderer(e) {
		var grid = e.sender;
        var record = e.record;
        var punid = record.RULEID;
    	var s = '';
        <%
		if(middlemap!=null && middlemap.size()>0){
			for (String key : middlemap.keySet()) {
		   		String[] arry = middlemap.get(key).split("@");
        %>
      	s +=" <a class=\"Edit_Button\" href=\"javascript:<%=arry[1] %>\" ><%=arry[0] %></a>";
        <%
			}
		}
		%>
     return s;
    }
    
	function add() {
    	mini.open({
	        url: "toRewardPunishRuleAddPage.action?1=1",
	        showMaxButton: true,
	        allowResize: false,
	        title: "新增", width: 490, height: 360,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = {action: "new"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}

	
	function edit(punid){
     	mini.open({
 	        url: "toRewardPunishRuleEditPage.action?punid=" + punid,
 	        showMaxButton: true,
 	        allowResize: false,
 	        title: "编辑", width: 490, height: 360,
 	        onload: function () {
 	            var iframe = this.getIFrameEl();
 	            var data = {action:"edit", punid:punid};
 	            iframe.contentWindow.SetData(data);
 	        },
 	        ondestroy: function (action) {
 	        	//修改后手动刷新 方便多次修改
 	        	grid.reload();
 	        }
     	});
	}

	
	function remove(punid) {
	    if (confirm("确定删除选中记录？")) {
	        $.ajax({
	            url: "deletePunishmentRang.action?id="+punid,
	            success: function (text) {
	                grid.reload();
	            },
	            error: function () {
	           		alert("操作失败");
	           		return;
	            }
	        });
	    }
    }
    
    function search(){
        var key = mini.get("key").getValue();
        grid.load({ key: key });
    }

    
    function rewardsDetail(punid){
     	window.location.href="toCommutationReferenceListPage.action?punid="+punid;   
    }
    
	function onTypeRenderer(e){
    	if (e.value == '0') {
    		return "否";
        } else if(e.value == '1') {
        	return "是";
        }
        return e.value;	
    }
    
	function onKeyEnter(e) {
           search();
	}         
</script>
