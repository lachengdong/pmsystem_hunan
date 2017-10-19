<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>我的桌面</title>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/css/top.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }     
	    #time{
	        position: absolute;
	        right: 1px;
	        top: 2px;
	        color: blue;
	    } 
	    .mini-panel-border{
	    	border-left:0;
	    	border-bottom:0;
	    }
	    .kjcdb .mini-splitter-border{
	    	border-right:0;
	    	border-bottom:0;
	    	border-top:0;
	    } 
	    .ywtxb .mini-panel-border{
	    	border-right:0;
	    }
	</style>
</head>
<body>
	 <div class="mini-splitter" style="width:100%;height:100%;border: 0px;">
		<div size="75%" showCollapseButton="false" style="padding:0px;border: 0px;">
			<div class="mini-toolbar" style="height:25px; padding:0px;border-top: 0px;border-left: 0px;border-bottom: 0px;">
	             <span style=" padding-left: 10px;color:blue;font-weight:800">待办事项</span>
	        </div>
			<div class="mini-fit" id="daibanshixiang"  style="width:100%;height:100%;">
				<div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  showPager="false" virtualScroll="true"
		        	url="<%=path%>/flow/getTodoListInfo.json?1=1"  idField="" multiSelect="true"  allowAlternating="true"  virtualScroll="true"
		        	showLoading="true" >
					<div property="columns">
						<div type="indexcolumn" width="10" headerAlign="center" align="center" allowSort="true">序号</div>
						<div field="todoinfo" width="100" headerAlign="center" align="center" allowSort="true">待办摘要</div>
						<div field="nums" width="20" headerAlign="center" align="center" allowSort="true">数量</div>   
						<!--  
							<div field="optime" width="40" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" align="center" allowSort="true">提交时间</div>  
						-->  
						<div field="" width="20" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
					</div>
				</div>
			</div>
			
		</div>
		<div showCollapseButton="false" style="border: 0px;" class="kjcdb">
			<div class="mini-splitter" vertical="true" style="width:100%;height:100%;border: 0px;">
				<div size="40%" showCollapseButton="false" style="border-top: 0px;border-left: 0px;border-right: 0px;">
					<div class="mini-toolbar" style="padding:0px;border-top: 0px;border-left: 0px;border-right: 0px;">
			             <!-- 控件临时下载地址 -->
			             <span style="padding-left: 20px;color:blue;font-weight:800">快捷方式</span>
			             &nbsp;&nbsp;&nbsp;&nbsp;
			             <a class="mini-button" onclick="mydownload('AIP_Setup.exe');"  plain="true" style="width:80px;">
			             	<span style="padding-left: 0px;color:blue;font-weight:800">控件下载</span>
			             </a> 
			        </div>
			        <div id="shortcutTree" class="mini-tree" url="public/shortcut.action" onnodeclick="onNodeSelect" 
			        	showTreeIcon="false"  textField="name" idField="resid" parentField="parentid"  expandOnLoad="false" showTreeLines="false">	
			        </div>
				</div>
				<div showCollapseButton="false" >
					 <div class="mini-toolbar" style="padding:0px;border: 0px;">
			         	<span style="padding-left: 10px;color:red;font-weight:800">业务提醒</span>
			        </div>
					 <div class="mini-fit" id="yewutixing"  style="width:100%;height:100%;">
						    <div id="datagrid2" class="mini-datagrid ywtxb"  style="width:100%;height:90%;" allowResize="false" pageSize="8" showPager="false"
						        url="getEventList.json?1=1&qtype=filterevent"  idField="" multiSelect="true" onrowdblclick="tclick" allowAlternating="true"  
						        virtualScroll="true" showLoading="true" >
						        <div property="columns" >
									<div type="indexcolumn"  width="10"></div>  
									<div field="noticeid" width="0"></div> 
									<div field="opid" width="0" id="opid"></div>
						            <div field="context" width="100" headerAlign="center" align="center" allowSort="true" >提醒内容</div>   
						            <div field="" width="20" headerAlign="center" align="center" allowSort="false" renderer="onCaoZuo">操作</div>  
						     </div>
						 </div>
						 <div align="right">
						 	<a class="mini-button"  plain="true" style="margin: 1px;" plain="true"	onclick="moreevent()">更多...</a>
						 </div>
					</div>
				</div>        
			</div>   
		</div>        
	</div>
   <div style="display:none;">
   		<iframe id="download_iframe"></iframe>
   </div>
</body>
</html>
<script type="text/javascript" >
	 mini.parse();
    
	var mainTabs = parent.mini.get("mainTabs");
    mainTabs.setTabPosition("bottom");
    var tree = mini.get("shortcutTree");
    var grid = mini.get("datagrid1");
    grid.load();
    var grid2 = mini.get("datagrid2");
    grid2.load();
  	
  	//定时刷新
    setInterval(
    	function(){
	    	try{
				$.ajax({
		                url: "<%=path%>/getUserCount.action?1=1",
		                data: {},
		                cache: false,
		                type: "post",
		                success: function (text) {
		                	parent.document.$("#onlineNumber").html(text);
		                	// 获取成功,有网,才加载.
				    		grid.reload();
				    		grid2.reload();
		                }
		          });
	    	}catch(ex){
	    	}
    	}, 
     6 *60 * 1000); //6分钟刷新一次页面
    
    	
   	function tclick(){
   		moreevent();
   	}
 
    function onActionRenderer(e) {
       var grid = e.sender;
       var record = e.record;
       var s = "";
   	   var s = ' <a class="Edit_Button" href="javascript:handleCase()" >办理</a>';
   	   //e.rowStyle = "background:#ccffff;";
       return s;
   }
   
   function handleCase(){
   		var row = grid.getSelected();
	   	parent.openTodoListPage(row.resid);
   }
    
    //事件提醒
   function moreevent(){
    	var tabs = parent.mini.get("mainTabs");
    	tab = {};
        tab.name = "tab$event";
        tab.title = '事件提醒';
        tab.showCloseButton = true;
        tab.url ='toEventManage.action?qtype=tab3';
        tabs.addTab(tab);
         tabs.activeTab(tab);
       tabs.setTabPosition("bottom");
   }
   
	function mydownload(filename){
		var download_iframe = document.getElementById('download_iframe');
		if(download_iframe){
			download_iframe.src="<%=path %>/download/" + filename;// +"?_="+Math.random();
			return false;
		}
		return false;
    }
    
    function onCaoZuo(e){
    	//var opid=e.record;
       var s = ' <a class="Edit_Button" href="javascript:setstatus()" >已读</a>';
       //if(opid.opid=='sysauto'){
       	//s='公共消息';
       //}
       return s;
   }
   
   function setstatus(){
     	var row = grid2.getSelected();
     	if(row){
	 	$.ajax({
              url: "setEventStatus.action?1=1&noticeid="+row.noticeid,
              data: {	
              	},
              type: "post",
              success: function (text) {
              	grid2.reload();
              },
              error: function (jqXHR, textStatus, errorThrown) {
              	//alert("操作失败!");
              }
          });  
         }      
    }
    //
     function onNodeSelect(e) {
        var node = e.node;
        var isLeaf = e.isLeaf;
        if (isLeaf) {
            showTab(node);
        }
        onQuickClick(node.remark);
    }
    //
	function onQuickClick(resid) {
    	var tree=parent.mini.get("leftTree");
        tree.expandPath(resid);
        tree.selectNode(resid);
	}
	//
   function showTab(node) {
      var tabs = parent.mini.get("mainTabs");
      var id = "tab$" + node.remark;
      var tab = tabs.getTab(id);
      if (!tab) {
          tab = {};
          tab.name = id;
          tab.title = node.name;
          tab.showCloseButton = true;
          tab.url =node.srurl+'&menuid='+node.remark;
          tabs.addTab(tab);
      }
      tabs.activeTab(tab);
      tabs.setTabPosition("bottom");
  }
</script>
