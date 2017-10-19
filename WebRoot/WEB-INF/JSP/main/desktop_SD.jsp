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
    <%-- --%>
    <%-- 
    <link href="<%=path%>/demo/portal/js/portal.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/demo/portal/js/Portal.js" type="text/javascript"></script>
	--%>
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
<!-- 
    <div style="width:99%;padding-bottom: 10px;">
        <div class="mini-toolbar" style="padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                    
                    <span style="padding-left: 10px;color:red;font-weight:800">监狱公告：</span>
                    <span style="padding-left: 10px;color:#3889B8;font-weight:800">请各分监区在6月5日之前上报端午节服刑人员的娱乐活动计划！</span>
                     
                    </td>
                    <td style="white-space:nowrap;">
                        
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    -->
    <!-- <input id="Button1" type="button" value="button1" />
    <input id="Button2" type="button" value="button2" />
     -->
	  
	 <div class="mini-splitter" style="width:100%;height:100%;border: 0px;">
		<div size="60%" showCollapseButton="false" style="padding:0px;border: 0px;">
			
			<div class="mini-toolbar" style="height:25px; padding:0px;border-top: 0px;border-left: 0px;border-bottom: 0px;">
	             <span style=" padding-left: 10px;color:blue;font-weight:800">待办事项</span>
	        </div>
			<div class="mini-fit" id="daibanshixiang"  style="width:100%;height:100%;">
				<div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[20,50,100]" pageSize="20"
		        url="<%=path%>/flow/getapprovelistForSD.json?1=1"  idField="" multiSelect="true"  allowAlternating="true"  virtualScroll="true"
		        showLoading="true" >
					<div property="columns">
						<div type="indexcolumn" width="10" headerAlign="center" align="center" allowSort="true">序号</div>
						<div field="tempname" width="100" headerAlign="center" align="center" allowSort="true">文件名</div>        
						<div field="count" width="10" headerAlign="center" align="center" allowSort="true">数量</div>
						<div field="tempid" width="10" headerAlign="center" align="center" allowSort="true" visible="false">模板ID</div>
						<div field="resid" width="10" headerAlign="center" align="center" allowSort="true"  visible="false">对应办理菜单ID</div>
						<div field="flowdefid" width="10" headerAlign="center" align="center" allowSort="true" visible="false">流程ID</div>   
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
			             &nbsp;&nbsp;&nbsp;&nbsp;<a class="mini-button" onclick="mydownload('AIP_Setup.exe');"  plain="true" style="width:80px;"><span style="padding-left: 0px;color:blue;font-weight:800">控件下载</span></a> 
			        </div>
			        <div id="shortcutTree" class="mini-tree" url="public/shortcut.action" onnodeclick="onNodeSelect" showTreeIcon="false"  textField="name" idField="resid" parentField="parentid"  expandOnLoad="false" showTreeLines="false">	</div>
			        <%--<span style="padding-left: 10px;color:blue;font-weight:800">快捷菜单</span>
			             
			        <div id="shortcutTree" class="mini-tree" url="shortcutMenu.action" onnodeclick="onNodeSelect" showTreeIcon="false"  textField="smdiscribe" idField="smid" parentField="smparentid"  expandOnLoad="false" showTreeLines="false">	</div>
			         --%>
					
					
				</div>
				<div showCollapseButton="false" >
					
					<!-- 
					<div class="mini-fit" id="yewutixing"  style="width:100%;height:100%;">
						<div id="datagrid2" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50]"
					url="ajaxMainGetShuJu.action?1=1&menuid=10246"  idField="" multiSelect="true"  allowAlternating="true"  virtualScroll="true"
					showLoading="true" >
							<div property="columns">
								<div field="id" width="90" headerAlign="center" align="center" allowSort="true">编号</div>        
								<div field="annexname" width="120" headerAlign="center" align="center" allowSort="true">审批件名称</div>   
								<div field="" width="50" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
							</div>
						</div>
					</div>
					 -->
					 <div class="mini-toolbar" style="padding:0px;border: 0px;">
					 <input id="ischeckseal" name="ischeckseal" type="hidden"  value="${ischeckseal}"/>	
			         <span style="padding-left: 10px;color:red;font-weight:800">业务提醒</span>
			        </div>
					 <div class="mini-fit" id="yewutixing"  style="width:100%;height:100%;">
					    <div id="datagrid2" class="mini-datagrid ywtxb"  style="width:100%;height:90%;" allowResize="false" pageSize="8" showPager="false"
					        url="getEventList.json?1=1&qtype=filterevent"  idField="" multiSelect="true" onrowdblclick="tclick" allowAlternating="true"  virtualScroll="true"
					        showLoading="true" >
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
	 /*
    var portal = new mini.ux.Portal();
    portal.set({
        style: "width: 100%;height:100%",
        //columns: ["30%", "30%", "30%"]
        columns: ["60%", "30%"]
    });
    portal.render(document.body);

    //panel
    var isClose =false;
    var isCollapse =false;
    portal.setPanels([
    	
    	{ column: 0, id: "p2", title: "待办事项", showCloseButton: isClose,showCollapseButton: isCollapse, height: 450, body: "#daibanshixiang" },
        //{ column: 0, id: "p1", title: "个人信息", showCloseButton: isClose,showCollapseButton: isCollapse, height: 220, body: "#shortcutTree"  },
       //{ column: 0, id: "p2", title: "快捷菜单", showCloseButton: isClose,showCollapseButton: isCollapse, height: 220, body: "#shortcutTree" },

        //{ column: 1, id: "p3", title: "我的申请", showCloseButton: isClose,showCollapseButton: isCollapse, height: 220},
        //{ column: 1, id: "p4", title: "我的审批", showCloseButton: isClose,showCollapseButton: isCollapse, height: 220 },

        { column: 1, id: "p2", title: "快捷菜单", showCloseButton: isClose,showCollapseButton: isCollapse, height: 220, body: "#shortcutTree" },
        { column: 1, id: "p2", title: "业务提醒", showCloseButton: isClose,showCollapseButton: isCollapse, height: 220, body: "#yewutixing" }
    ]);
    */
    
	var mainTabs = parent.mini.get("mainTabs");
    mainTabs.setTabPosition("bottom");
    var tree = mini.get("shortcutTree");
    var grid = mini.get("datagrid1");
    grid.load();
    var grid2 = mini.get("datagrid2");
    grid2.load();
  	//定时刷新
    setInterval(function(){
    	try{
    		
			$.ajax({
	                url: "<%=path%>/getUserCount.action?1=1",
	                data: {	
	                		},
	                cache: false,
	                type: "post",
	                success: function (text) {
	                	// 获取成功,有网,才加载.
			    		grid.reload();
			    		grid2.reload();
	                }
	          });
    	} catch(ex){
    		
    	}
    	},  6 *60 * 1000);//6分钟刷新一次页面
    	function tclick(){
    		moreevent();
    	}
    function onCriminalidRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;
        var s = ' <a class="Edit_Button" href="javascript:getCriminalInfo(\'' + record.id + '\')" >'+record.id+'</a>&nbsp;&nbsp;';
        return s;
    }
    function getCriminalInfo(criminalid){
    	mini.open({
              url: "openSearchPage.action?menuid=8328&criminalid="+criminalid,
              showMaxButton: true,
              allowResize: false, 
              title: "罪犯信息", width: 1000, height: 550,
              onload: function () {
                 // var iframe = this.getIFrameEl();
                  //var data = { action: "edit",menuid:menuid};
              },
              ondestroy: function (action) {
                  grid.reload();
              }
          	});	
    }
 
    function onActionRenderer(e) {
       var grid = e.sender;
       var record = e.record;
       var uid = record._uid;
       var ispass = record.ispass;
       var rowIndex = e.rowIndex;
       var islocked = record.islocked;
       var name="";
       var s = "";
   	   var button1 = ' <a class="Edit_Button" href="javascript:findres()" >审批</a>';
   	   if(islocked != 0) name = '解锁',e.rowStyle = "background:#ccffff;";
          s = button1+'  <a class="Edit_Button" href="javascript:operationLock();" >'+name+'</a>';
          var isshow = record.isshow;
          if(islocked == 1) if(isshow != '1') s='已锁';
       return s;
   }
   function shenpi(){
	   	var url = "";
		var row = grid.getSelected();
        if (row) {
           var flowdraftid = row.flowdraftid;
           returnLockUser(flowdraftid);
       } else {
           alert("请选中一条记录");
       }
    }
    //判断是否加锁
    function returnLockUser(flowdraftid){
        var flag = true;
		var url = "<%=path%>/flow/ajaxReturnLockUser.json?1=1";
		$.ajax({
            url: url,
            data: {flowdraftid:flowdraftid},
            type: "post",
            success: function (text) {
                var obj = text;
                if(obj){
					if(obj != -1){
						alert("用户"+obj+"正在审批!");
						grid.reload();
					}else{
	                	openwin();
	                }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
            	alert("操作失败!");
            	grid.reload();
            }
      });
	  return flag;
    }
	function openwin(){
		var row = grid.getSelected();
        if (row) {
           var orgid = row.orgid;
           var flowid = row.flowid;
           var applyid = row.applyid;
           var conent = row.conent;
           var snodeid = row.snodeid;
           var flowdefid = row.flowdefid;
           var flowdraftid = row.flowdraftid;
           var lastnodeid = row.snodeid;
           var url='';
           operationLock('yes');
           if(flowdefid == 'other_zfjyjxjssp'){
        	   var ids = [];
        	   ids.push(applyid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid);
        	   var id = ids.join(','); 
        	   var menuid = '10091_06';
        	   if(lastnodeid == 0 ) menuid = '10090_05';
        	   if(lastnodeid == 'N0001' && row.state == 0 ) menuid = '10090_05';
        	   
        	   var checkType = 0;
			   var isCheckSeal = document.getElementById("ischeckseal");
			   if(isCheckSeal) checkType = isCheckSeal.value;
	
               url = "<%=path%>/toCommuteOfJailCaseTabs.action?1=1&tempid=JXJS_JXJSSHB&id="+id+"&menuid="+menuid+"&closetype=0&ischeckseal="+checkType+"&flowdefid="+flowdefid;
           }else if(flowdefid == 'other_jybwjycbsp'){
           		var ids = [];
           		ids.push(applyid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid);
           		var id = ids.join(','); 
        	   var menuid = '10111_06';
        	   if(lastnodeid == 0 ) menuid = '12599';
        	   if(lastnodeid == 'N0001' && row.state == 0 ) menuid = '12599';
        	   url = "<%=path%>/toOutPrisonOfJailCaseTabs.action?1=1&tempid=ZFABWJYSPB&id="+id+"&menuid="+menuid+"&closetype=0&flowdefid="+flowdefid;
           }else if(flowdefid == 'other_jybwjyxbsp'){
           		var ids = [];
           		ids.push(applyid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid);
           		var id = ids.join(','); 
        	   var menuid = '10136_07';
        	   if(lastnodeid == 0 ) menuid = '10133_06';
        	   if(lastnodeid == 'N0001' && row.state == 0 ) menuid = '10133_06';
        	   url = "<%=path%>/toOutPrisonOfJailCaseTabs.action?1=1&tempid=ZFABWJYSPB&id="+id+"&menuid="+menuid+"&closetype=0&flowdefid="+flowdefid;
           }else if(flowdefid == 'other_sjjxjssp'){
           		var ids = [];
           		ids.push(applyid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid);
           		var id = ids.join(','); 
        	   var menuid = '10259_06';
        	   if(lastnodeid == 0 ) menuid = '10257_05';
        	   if(lastnodeid == 'N0001' && row.state == 0 ) menuid = '10257_05';
               var url= "<%=path%>/toProvinceCommuteTabs.action?1=1&tempid=JXJS_JXSH&id="+id+"&menuid="+menuid+"&closetype=0&flowdefid="+flowdefid+"&nextButton=hidden"
           }else if(flowdefid == 'other_sjbwjysp'){
           		var ids = [];
           		ids.push(applyid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid);
           		var id = ids.join(','); 
        	   var menuid = '10123_05';
        	   if(lastnodeid == 0 ) menuid = '10121_05';
        	   if(lastnodeid == 'N0001' && row.state == 0 ) menuid = '10121_05';
               var url= "<%=path%>/toProvinceOutPrisonTabs.action?1=1&tempid=SX_SJBWJYSPB&id="+id+"&menuid="+menuid+"&closetype=0&flowdefid="+flowdefid;
           }else if(flowdefid == 'other_jyjxjslasp'){
           		var url = "<%=path%>/theCommutationParoleApplicationTabs.action?1=1&menuid=900042&closetype=0&nextButton=hidden"+
        		"&crimid="+applyid+"&tempid=SH_ZFJXJSLASPB"+"&flowdefid=other_jyjxjslasp"+"&flowid="+flowid+"&flowdraftid="+flowdraftid;
           }else{
        	   url="<%=path%>/flow/approveview.action?1=1&approve=yes"+"&flowid="+flowid+"&flowdefid="+flowdefid+"&shenptype=1"+"&flowdraftid="+flowdraftid;
           }
      	   var win=mini.open({
               url: url,
               showMaxButton: true,
               allowResize: false, 
               title: "审批",
               onload: function () {
                   var iframe = this.getIFrameEl();
                   var data = { action: "edit", conent:conent,orgid:orgid,flowid:flowid,
                		   snodeid:snodeid,flowdefid:flowdefid,flowdraftid:flowdraftid};
                   iframe.contentWindow.SetData(data);
               },
               ondestroy: function (action) {
                   grid.reload();
               }
          });
      	  win.max();
        }
	}
    
    //流程加锁 解锁
	function operationLock(type){
		var row = grid.getSelected();
        if (row) {
           var flowdraftid = row.flowdraftid;
           var islocked = row.islocked;
           var optime = mini.formatDate(row.optime,"yyyy-MM-dd HH:mm:ss");
           var url="<%=path%>/flow/operationlock.action?1=1";
           $.ajax({
                url: url,
                data: {flowdraftid:flowdraftid,type:type},
                type: "post",
                success: function (text) {
                	grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	//alert("操作失败!");
                }
	      });  
      }
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
     function onNodeSelect(e) {
           var node = e.node;
           var isLeaf = e.isLeaf;
           if (isLeaf) {
               showTab(node);
           }
           onQuickClick(node.remark);
       }
	function onQuickClick(resid) {
    	var tree=parent.mini.get("leftTree");
        tree.expandPath(resid);
        tree.selectNode(resid);
	}

        
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
   function findres(){
	   //根据相关条件进行检索，如果配置相关审批页面，直接跳转到审批页面操作，否则保持原来的不变
	   var row = grid.getSelected();
	   if (row) {
		   var flowdefid = row.flowdefid;//获取当前代办事项的流程ID
		   var resid = row.resid;//获取当前待办事项对应流程的resid，通过resid处理同一流程中同一角色拥有多次审批
		   var url = "<%=path%>/resource/ajaxSearchResourceByFlowdefid.json";
           $.ajax({
                url: url,
                data: {flowdefid:flowdefid,resid:resid},
                type: "post",
                success: function (text) {
                	text = mini.decode(text);
                	if(text["TEXT1"] == "daibanshenpi"){
                		 var tabs = parent.mini.get("mainTabs");
	       		         tab = {};
	       		         tab.name = "tab$event";
	       		         tab.showCloseButton = true;
	       		         tab.title =text["NAME"];
				         tab.url =text["SRURL"]+"&menuid="+text["RESID"];
				         tabs.addTab(tab);
				         tabs.activeTab(tab);
				         tabs.setTabPosition("bottom");
                	}else{
                		openwin();
                	}
                	
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
	      });  
	   }else{
		   alert("请选中一条记录");  
	   }
   }
   
</script>
