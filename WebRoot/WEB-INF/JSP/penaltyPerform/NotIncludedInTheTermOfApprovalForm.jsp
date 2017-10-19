<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String menuid = request.getParameter("menuid");
String myurl = request.getParameter("myurl");
request.getSession().setAttribute("amenuid",menuid);
String tempa=request.getParameter("singlerecord");
String basicdep=(String)request.getSession().getAttribute("basicdep");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>不计入刑期审批表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
     <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }   
    </style>
</head>
<body onload="myload()">
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
                <td style="width:100%;">
                   			<a class="mini-button" iconCls="icon-add" plain="true" onclick="add()">新增</a>
                   			<a class="mini-button" iconCls="icon-remove" plain="true" onclick="remove('ajaxMainRemoveShuJu.action?1=1','','','10179','')">批量删除</a>
                   			  <a class="mini-button" plain="true" iconCls="icon-user" onclick="sendMenuid()">选择罪犯</a>
                   				<span style="padding-left:40px;">罪犯编号：${crimid}</span>
			           			<input id="crimid" value="${crimid}" class="mini-hidden"/>
                   	<!-- <a class="mini-button" plain="true" iconCls="icon-user" onclick="chakanshuoming('10179')">操作说明</a> -->
                </td>
                <td style="white-space:nowrap;">
                	<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
                    	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="年号"  onenter="onKeyEnter"/>
                    	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
                    
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a>
                    <script src="<%=path%>/scripts/loadaip2.js"></script>
				    <input id="criminalid" name="criminalid" type="hidden" value=""/>
				    <input id="choosecriminal" name="choosecriminal" type="hidden" value=""/>
				    <input id="fullopen" name="fullopen" type="hidden" value=""/>
                </td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit"><!-- getTbsysDocumentList.action?1=1&tempid=SZ_SJGL_NEWGZH -->
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" url="ajaxMainGetShuJu.action?1=1&menuid=10179&criminalid=&choosecriminal="  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="10"></div> 				            			
				            			<div field="tempid" width="40px"  headerAlign="center"  	allowSort="true" align="center" >
					               			表单编号
					               		</div>  
				            			<div field="content" width="40px"  headerAlign="center"  	allowSort="true" align="center" >
					               			模版内容
					               		</div>  
				            			<div field="optime" width="40px"  headerAlign="center"  dateFormat="yyyy-MM-dd"	allowSort="true" align="center" >
					               			操作时间
					               		</div>  
                 	<div name="action" width="60px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    
</body>
<script type="text/javascript"> 
 function myload(){
	 mini.parse();
     var datagrid = mini.get("datagrid");
     datagrid.load();
     datagrid.sortBy("noyear", "desc");
 }
 //跳转到选择罪犯页面
 function sendMenuid(){
 	var url = "toChooseCriminalPage.action?1=1&action=NotIncludedInTheTermOfApprovalForm";
		self.location.href=url;
 }
 	function add(){
 	 	var crimid=mini.get("crimid").getValue();
 		mini.open({
 	        url: "NotIncludedInTheTermOfApprovalFormAdd.action?1=1&tempid=SDXF_BWJYBJRXQSPB&crimid="+crimid,
 	        showMaxButton: true,
 	        allowResize: false,
 	        title: "新增", width: 800, height: 600,
 	        onload: function () {
 	            var iframe = this.getIFrameEl();
 	            var data = { myoperatetype: "new"};
 	            iframe.contentWindow.SetData(data);
 	        },
 	        ondestroy: function (action) {
 	        	grid.reload();
 	        }
 	    });	
 	 }
    function plqz_loading(id) {
    	//alert(id);
        mini.mask({
            el: document.body,
            cls: 'mini-mask-loading',
            html: '正在对【'+id+'】的案件进行盖章，请稍候......'
        });
        setTimeout(function () {
        	id = "";
            mini.unmask(document.body);            
        }, 250);
    }
    function onActionRenderer(e) {
        var s = '<a class="Edit_Button" href="javascript:onOk()" >选择</a>'
        	
    }
    //行双击时发生  目前用于法院双机行弹出办案事件
    function rowdblclickfunction(e){
    	var record = e.record;
    	var s;
    	
    	eval(s);
    }
    //法院办案
     function fybanan(myurl,mywidth,myhight,menuid,tanchuleixing,record) {
           if (record) {
           		myurl=myurl+'&menuid='+menuid+"&operatetype=approve&id="+record.gkzxtempid;
               var win=mini.open({
                   url: myurl,
                   showMaxButton: true,
                   allowResize: false, 
                   title: "编辑", width: mywidth, height: myhight,
                   onload: function () {
                       var iframe = this.getIFrameEl();
                       var data = { action: "edit", id: record.gkzxtempid,mymenuid:menuid ,myoperatetype:'edit'};
                       iframe.contentWindow.SetData(data);
                   },
                   ondestroy: function (action) {
                       grid.reload();
                   }
               });
               if(mywidth=='901' || myhight=='551'){
            	win.max();
            }
               var fullopen=document.getElementById("fullopen").value;
            if(fullopen=='yes')
            {
            	win.max();
            }
           } else {
               alert("请选中一条记录");
           }
       }
</script>

</html>