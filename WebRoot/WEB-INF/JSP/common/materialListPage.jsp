<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${title}</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<!-- 必须指定,否则高度为0 -->
		<style type="text/css">
			body {
				margin: 0;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: auto;
			}
			
			.hide {
				display: none;
			}
		</style>
	</head>
	<body >
		<!--Splitter-->
		<div class="mini-splitter" style="width: 100%; height: 100%;" borderStyle="border:0;">
			<div size="200" maxSize="1024" minSize="150" showCollapseButton="true">
				<div class="mini-toolbar" >
					<span style="padding-left: 5px;">${title }：</span>
				</div>
				<div class="mini-fit">
					<!--Tree-->
					<ul id="tree1" name="tree1" class="mini-tree" style="width: 100%; height: 100%;"
						url="<%=path%>/ajaxListByMap.action?codetype=${codetype}" showTreeIcon="true"
						resultAsTree="false" textField="name" idField="codeid" parentField="pcodeid" >
					</ul>
				</div>
			</div>
			<div showCollapseButton="true">
				<div class="mini-toolbar" style="height:30px;">
					<table>
						<tr>
							<td style="width:100%;">
								<input id="flowdraftid" name="flowdraftid" class="mini-hidden" value="${flowdraftid}"/>
								<input id="applyid" name="applyid" class="mini-hidden" value="${applyid}"/>
								<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
								<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
								
								<a class="mini-button"  style="" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
								<a class="mini-button"  style="" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
								${topsearch}
						    	${topstr}
						    	<!--  <a class="mini-button"  iconCls="icon-close" plain="true" onclick="Close();">关闭</a>-->
						    	<span class="separator"></span>
						    	<a class="mini-button"  style="" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
						    	<a class="mini-button"  style="" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
						    	<a class="mini-button"  style="" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
						    	<span class="separator"></span> 
						    	<a class="mini-button"  style="" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
						    	<a class="mini-button"  style="" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
						    	<a class="mini-button"  style="" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
						    	<span class="separator"></span> 
						    	<a class="mini-button"  style="" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
						    	<span class="separator"></span> 
						    	<a class="mini-button"  style="" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
						    	<a class="mini-button"  style="" id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true"  onclick="doMenueButton(0);"></a>&nbsp;
						    	<a class="mini-button"  style="" id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true"  onclick="doMenueButton(1);"></a>&nbsp;
						    </td>
						    <td style="white-space:nowrap;">
						    	<a class="mini-button"   style="" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true"  onclick=""></a>&nbsp;
						    </td>
					  	</tr>
				  	</table>
				  </div>
	    		  <div class="mini-fit" style="width:100%; height:100%;">
		              <jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
	         	  </div>  
	         	</div>
			</div>
		
		<script type="text/javascript">
			mini.parse();
			var flowdraftid = mini.get('flowdraftid').getValue();
			var applyid = mini.get('applyid').getValue();
			var tempid = mini.get('tempid').getValue();
			var flowdefid = mini.get('flowdefid').getValue();
			
			// 当前选中的节点
			var tree = mini.get("tree1");
        	tree.on("nodeselect", function (e){
        		var url = "<%=path%>/ajaxGetMaterials.json?1=1";
        		var node = e.node;
        		var codeid = node.codeid;
        		var solutionid = node.scearch;
        		
        		$.ajax({
	                url: url,
	                data: {flowdraftid:flowdraftid, applyid:applyid, tempid:tempid, flowdefid:flowdefid, solutionid:solutionid, docid:codeid },
	                type:"post",
	                success: function (text){
	                	var objs = mini.decode(text);
	                	var aipObj=document.getElementById("HWPostil1");
	                	if(objs.length > 0){
	                		/*
							for(var j=0;j<objs.length;j++){ 
								alert(objs[j]);
								aipObj.MergeFile(99999,'STRDATA:'+objs[j]);
							}
							*/
	                		aipObj.LoadFileBase64(objs[0]);//加载表单
	                	}else{
	                		aipObj.CloseDoc(0);
	                	}
	                	
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    CloseWindow();
	                }
            	});
           });
			
		</script>
	</body>
</html>