<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Object setlevelObj = request.getAttribute("setlevel");
	String setlevel = "";
	if(null != setlevelObj){
		setlevel = setlevelObj.toString();
	}
	
	String furl = request.getParameter("furl");
	if(null == furl){
		furl = "";
	}
	 
%>

<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>条件配置</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
		</style>
	</head>
<body>
     <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
     	 <input id="fid" name="fid" class="mini-hidden" value="${fid}"/>
     	 <input id="setlevel" name="setlevel" class="mini-hidden" value="${setlevel}"/>
     	 <input id="qtype" name="qtype" class="mini-hidden" value="${qtype}"/>
         <table >
            <tr>
               <td style="width:100%;">
               	  <%
                     if("1".equals(setlevel)){
                  %>
                  		一级条件设置<span class="separator"></span>
                  <%
                     }else if("2".equals(setlevel)){
                  %>
                  		二级条件设置<span class="separator"></span>
                  		<a class="mini-button" iconCls="icon-cancel" onclick="goBack();" plain="true">返回</a>
                  <% 	 
                     }else if("3".equals(setlevel)){
            	  %>
            	  		三级条件设置<span class="separator"></span>
                  <%    	 
                     }
                  %>
				  <a class="mini-button" iconCls="icon-add" onclick="addQualifier();" plain="true">新增</a>
               </td>
               <td style="white-space:nowrap;">
               </td>
            </tr>
	   </table>
    </div>
    
    
    <div class="mini-fit">
	    <div id="datagrid" style="height:100%;width:100%;" class="mini-datagrid" allowAlternating="true" multiSelect="true" sizeList="[20,50,100]"
	      url="getPublicListData.json?1=1&solutionid=${solutionid}&setlevel=${setlevel}&qtype=${qtype}&fid=${fid}" pageSize="100" allowSort="true" showLoading="true" >
	        <div property="columns">
	        		<div type="checkcolumn" width="20"></div>  
	        		<div field="orderby" name="orderby" width="20px" headerAlign="center" align="center" allowSort="true">优先级</div>
	        		<div field="describe" name="describe" width="300px" headerAlign="center" align="left" allowSort="true">表达式</div>
	        		<!--  
	        		<div field="range" name="range" width="20px" headerAlign="center" align="center" allowSort="true">建议幅度</div>
	        		-->
                 	<div name="action" headerAlign="center" width="50px" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    
</body>
</html>
<script type="text/javascript">

	mini.parse();
	var grid = mini.get("datagrid");
	grid.load();
	
	var _furl = "<%=furl %>";
	
	function operateQualifier(url,data,title){
		mini.open({
	        url: url,
	        showMaxButton: true,
	        allowResize: false,
	        title: title, width: 1200, height: 675,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}
	
	// 关闭,返回
	function goBack(){
	  	var r = new Date().getTime();
	   	url = parsefurl();
	   	window.location.href=url;
	};
	
	function addQualifier(){
		var fid = mini.get("fid").getValue();
		var setlevel = mini.get("setlevel").getValue();
		var qtype = mini.get("qtype").getValue();
		var url = "operateQualifierPage.page?1=1&fid="+fid+"&setlevel="+setlevel+"&qtype="+qtype;
		var data = {operatetype: "new"};
		var title = "新增";
		
		operateQualifier(url,data,title);
	}
	
	function editQualifier(){
		var row = grid.getSelected();
		var qid = row.qid;
		var setlevel = mini.get("setlevel").getValue();
		var qtype = mini.get("qtype").getValue();
		var url = "operateQualifierPage.page?1=1&qid="+qid+"&setlevel="+setlevel+"&qtype="+qtype;
		var data = row;
		data.operatetype = "edit";
		var title = "修改";
		
		operateQualifier(url,data,title);
	}
	
	var setlevel = '<%=setlevel%>';
	function onActionRenderer(e) {
		var grid = e.sender;
        var record = e.record;
    	var s = '';
    	s = "  <a class=\"Edit_Button\" href=\"javascript:editQualifier()\" >修改</a>";
    	if(1==setlevel){
    		s += "  <a class=\"Edit_Button\" href=\"javascript:setLevelTwoQualifier()\" >二级条件</a>";
    	}
    	
    	s += "  <a class=\"Edit_Button\" href=\"javascript:remove()\" >删除</a>";
     	return s;
    }
	
	function setLevelTwoQualifier(){
		var furl = encodeURIComponent(location.href);
		var row = grid.getSelected();
		var qid = row.qid;
		var qtype = mini.get("qtype").getValue();
		var url = "toQualifierSetPage.page?1=1&solutionid=3100373&setlevel=2&qtype="+qtype+"&fid="+qid+"&furl="+furl;
		window.location.href = url;
	}
	
	
	function remove() {
	    if (confirm("确定删除选中记录？")) {
	    	var row = grid.getSelected();
	    	var qid = row.qid;
	        $.ajax({
	            url: "removeQualifierSet.json?qid="+qid,
	            type: "post",
				dataType:"text",
				async:false,
	            success: function (text) {
	            	if(text>=1){
						alert("操作成功!");
					}else{
						alert("操作失败!");
					}
	                grid.reload();
	            },
	            error: function () {
	           		alert("操作失败");
	           		grid.reload();
	            }
	        });
	    }
    }
    
</script>
