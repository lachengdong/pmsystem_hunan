<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String look = "";
Object lookObj = request.getAttribute("look");
if(null != lookObj){
	look = lookObj.toString();
}
%>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>档案查看</title>

	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	</style>
    <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
	</script>
  </head>
  
  <body onload="init('${menuid}');">
		<input name="orgid" id="orgid" class="mini-hidden" value="${orgname1}"/>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		 <div class="mini-splitter" style="width:100%;height:100%;padding:0;" borderStyle="border:0 solid #99bce8;">
       		<!-- 左边的树 -->
           <div size="200" maxSize="1024" minSize="150" showCollapseButton="true">
				<!--Tree-->
				<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
		            <span style="padding-left:5px;">名称：</span>
		            <input class="mini-textbox" vtype="maxLength:30;"  id="keyarch" style="width:80px;" onenter="onKeyEnter"/>
		            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="searchArch()">查找</a>                  
		        </div>
		        <div  class="mini-fit" >
				<ul id="tree1" class="mini-tree" style="width:100%;height:100%;" url="<%=path %>/flow/ajax/list.json?1=1&anjiantype=${anjiantype}" showTreeIcon="true" resultAsTree="false"
				     textField="name" idField="codeid" parentField="pcodeid" contextMenu="#treeMenu" expandOnLoad = "0" onnodeselect="onTreeNodeSelect">
				</ul>
				</div>
           </div>
           <!-- 右边的显示区域 -->
           <div showCollapseButton="false" style="overflow: auto;padding:0px;"  borderStyle="border:solid 1px #aaa;">
	           <div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
				<table>
					<tr>
						<td  style="width: 100%;">
					       <a class="mini-button" style="display:none;" id="10629" iconCls="icon-node" plain="true" onclick="lookarchives('10629');">合并查看</a>
					       <%
					       		if(StringNumberUtil.isEmpty(look) || !"1".equals(look)){
					        %>
						       <a class="mini-button" style="display:none;" id="12941" iconCls="icon-remove" plain="true" onclick="remove('12941');">批量删除</a>
						       <a class="mini-button" id="" iconCls="icon-undo" plain="true" onclick="javascript:history.go(-1);">返回</a>
						       <a class="mini-button"  iconCls="icon-print" plain="true" onclick="lookarchives();">合并打印</a>
						        打印开始页码：<input class="mini-spinner" name="pagenum"  id="pagenum" Value="-1" minValue="-1" maxValue="9999" emptyText="0和负数不打印页码"/>(0和负数不打印页码)
					       <%
					       		}else{
					        %>
					        	<a class="mini-button" id="" iconCls="icon-undo" plain="true" onclick="javascript:Close();">返回</a>
					        <%
					        	}
					         %>
						</td> 
						<td style="white-space: nowrap;" >
							<!-- 公共页面的查询框进行调整，没有填写查询条件的就不显示查询框 yanqutai -->
							<input class="mini-textbox" id="key" class="mini-textbox" 
								emptyText="档案名称" onenter="onKeyEnter" />
							<a class="mini-button" plain="true" iconCls="icon-search"
								plain="true" onclick="search()">查询</a>
							<!-- 操作说明 -->
							<a class="mini-button" plain="true" iconCls="icon-help"
								onclick="chakanshuoming('8738');"></a>
							<input id="fullopen" name="fullopen" type="hidden" value="" />
						</td>
					</tr>
				</table>
	
			</div>
			<div class="mini-fit">
				<div id="datagrid" allowMoveColumn="false" url="<%=path%>/flow/getlookarchives.action?1=1&personid=${crimid}"
					style="width: 100%; height: 100%;" class="mini-datagrid" allowResize="false" showLoading="true"
					multiSelect="true" allowAlternating="true" sizeList="[20,50,100,500,1000]" pageSize="20">
					<div property="columns">
						<div type="checkcolumn" width="10"></div>
						<div type="indexcolumn" width="10" headerAlign="center"
							align="center">序号</div>
						<div field="personid" width="20" headerAlign="center"
							allowSort="true" align="center">
							所属人编号
						</div>
						<div field="personname" width="20" headerAlign="center"
							allowSort="true" align="center">
							所属人名称
						</div>
						<div field="docyear" width="15" headerAlign="center"
							allowSort="true" align="center">
							档案年份
						</div>
						<div name="rank" field="rank" width="15" renderer="onRankRenderer" align="center" headerAlign="center">涉密级别
							<input property="editor" class="mini-textbox" style="width:100%;" data="Ranks"/>
						</div>
						<div field="isattached" width="15" renderer="onIsattachedRenderer" headerAlign="center"
							allowSort="true" align="center">
							正(副)卷<input property="editor" class="mini-textbox" style="width:100%;" data="Isattacheds"/>
						</div>
						<div field="classification"  width="15" renderer="onClassificationRenderer" headerAlign="center"
							allowSort="true" align="center">
							档案类别<input property="editor" class="mini-textbox" style="width:100%;" data="Classifications"/>
						</div>
						<div field="retention" width="14" renderer="onRetentionRenderer" headerAlign="center"
							allowSort="true" align="center">
							保存期限<input property="editor" class="mini-textbox" style="width:100%;" data="Retentions"/>
						</div>
						<div field="name" width="50" headerAlign="left"
							allowSort="true" align="left">
							档案名称
						</div>
						<div width="15" headerAlign="center" align="center"
						 	allowSort="false" renderer="onCaoZuo">操作</div>  
					</div>
					<div style="height: 0px;border: 0px;">
						<table>
							<tr>
								<td>
									<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
		<script type="text/javascript">
			mini.parse();
			var tree = mini.get("tree1");
		 	var grid = mini.get("datagrid");
		 	grid.load();
			//grid.sortBy("personid", "desc");
			if('${provincecode}'=='1300'){//河北不需要涉密级别
	        	grid.hideColumn("rank");;
	        }
			
			function search() {
	            var node = tree.getSelectedNode();
	            var codeid = '';
	            if(node){
	            	codeid = node.codeid;
		        }
			    var key = mini.get("key").getValue();
			    grid.load({ key: key,codeid:codeid});
			}
			function onKeyEnter(e) {
			   search();
			   searchArch();
			}
			function lookarchives(resid){
	            var rows = grid.getSelecteds();
	            var pagenum = -1;
            	
            	if(mini.get("pagenum")){
            		pagenum=mini.get("pagenum").getText();
            		pagenum=parseInt(pagenum);
            	}
	            	
	             if (rows &&rows.length>50 && resid){
	               alert("50条数据以下!");
	            }else if(rows && rows.length>0 && resid){	
	                var url = 'flow/lookarchives.action?1=1&resid='+resid+"&ismultipage=0&pagenum="+pagenum;
					var archiveids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        archiveids.push(r.archiveid);
                    }
                    var archiveid = archiveids.join(',');
                    
                    var urltt = grid.getUrl();
					var pageIndex = grid.getPageIndex();
					var pageSize = grid.getPageSize();
					var sortField = grid.getSortField();
					var sortOrder = grid.getSortOrder();
					var total = grid.getTotalCount();
					var key = mini.get("key").getValue();
					
                    var win = mini.open({
	                    url: url+"&archiveid="+archiveid,
	                    title: "档案", width: 600, height: 360,
	                    onload: function () {
	                        var iframe = this.getIFrameEl();
	                        var data = {url:urltt,pageIndex:pageIndex,pageSize:pageSize,pagenum:pagenum,total:total,
	                        			sortField:sortField,sortOrder:sortOrder,key:key};
	                        iframe.contentWindow.SetData(data);
	                    }
	                });
                    win.max();
	            }else if(rows && rows.length>0){
	            	
					var archiveids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                       	 var r = rows[i];
	                     $.ajax({
			             url: "<%=path%>/flow/printArchives.action?1=1&resid="+resid+"&ismultipage=0"+"&archiveid="+r.archiveid+"&pagenum="+pagenum,
			             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
			             cache: false,
			             async:false,
			             type: "post",
			             success: function (text){
			             		var objs = mini.decode(eval(text));
								var num1 = 0;
								if(null==objs || ""==objs || (num1 > objs.length) ){
									alert("请核查"+r.name+"是否存在案件！");
									return;
								}
				             	dayin(text,(pagenum+i));
				             	if(i==rows.length-1){
				             		alert("打印完毕！");
				             	}
			             },
			             error: function (jqXHR, textStatus, errorThrown) {
			             }
		              });
                    }
                   
                    
	            }else {
	                alert("请选中一条记录");
	            }
			}
		function dayin(text,num){
			var temp;
			if(parseInt(num)<0||parseInt(num)==0){
				temp='';
			}else{
				temp=num;
			}
			var aipObj=document.getElementById("HWPostil1");
			aipObj.CloseDoc(0);
		    aipObj.LoadFileBase64(text.replace(/\"/g,"").replace(/\"/g,"").replace("[","").replace("]","").replace(/\\/g,""));
			//动态插入可编辑区域 pagenum
			aipObj.InsertNote("pagenum",0,3,24000,47000,1500,1000);
			//pagenum节点赋值
			aipObj.SetValue("pagenum",temp); 
			//设置节点 pagenum 属性
			//aipObj.SetValue("Page"+num+".pagenum",":PROP::LABEL:3");//只读,只能通过接口赋值
			//aipObj.SetValue("Page"+num+".pagenum",":PROP:BORDER:0");//没有边框
			//aipObj.SetValue("Page"+num+".pagenum",":PROP:FRONTCOLOR:0");//设置字体颜色
			//aipObj.SetValue("Page"+num+".pagenum",":PROP:FONTSIZE:12");//设置字体大小
			setInterval('',1000);
			//aipObj.PrintDoc(1,1); //打印当前打开文档,打开打印对话框
			aipObj.PrintDocEx('',1,0,0,0,9999,0,1,1,0,0);
	    }
			function remove(resid){
				var url = 'removearchives.json?1=1';
	            var rows = grid.getSelecteds();
	            if (rows && rows.length>0) {
	            	if (confirm("确定删除选中记录？")){
	            		var archiveids = [];
	                    for (var i = 0, l = rows.length; i < l; i++) {
	                        var r = rows[i];
	                        archiveids.push(r.archiveid);
	                    }
	                    var archiveid = archiveids.join(',');
	                    $.ajax({
	                        url: url,
	                        type: 'POST',
	                        cache:false,
	       	             	async:false,
	                        data:{resid:resid,archiveid:archiveid},
	                        success: function (text) {
	                            alert("操作成功!");
	                            grid.reload();
	                        },error: function () {
	                        	alert("操作失败!");
	                        }
	                    });
	            	}
	            } else {
	                alert("请选中一条记录");
	            }
			}
			function onCaoZuo(){
				var s = document.getElementById("middlebtns").value;
			    return s;
			}
			
			function onRankRenderer(e) {
	            for (var i = 0, l = Ranks.length; i < l; i++) {
	                var g = Ranks[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        }
	        function onIsattachedRenderer(e) {
	            for (var i = 0, l = Isattacheds.length; i < l; i++) {
	                var g = Isattacheds[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        }
	        function onRetentionRenderer(e) {
	            for (var i = 0, l = Retentions.length; i < l; i++) {
	                var g = Retentions[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        }
			///////////////////////////////////////////////////////
	        // 选择树节点时,默认是查看信息
			function onTreeNodeSelect(e) {
			    var node = e.node;
			    if(null == node){
				    e.cancel = true;
				    return false;
			    } else {
			    	//
			    	window["userm_org"] = node;
			    }
			    var isLeaf = e.isLeaf;
			    if (isLeaf) {
			    }
				var codeid = node.codeid;
				var islocalnew = node.islocalnew;
				if(codeid && !islocalnew){
					// 保存本地 cookie
					//document.cookie="userm_orgid="+escape(codeid); 
				}
				showDataToGrid(node);
			    e.cancel = true;
			    return false;
			};
			// 将数据显示(要显示的节点,父节点)
			function showDataToGrid(node, pnode){
				if(!node){
					return;
				}
			    var param = window["param"] || {};
			    	param["codeid"] = node.codeid;
			    	param["codename"] = node.name;
			    window["param"] = param;
			   
				//
		    	// 加载数据
		        grid.load(param);
			};
			// 刷新本页面
			function refreshPage(){
				//
				location.reload();
			};
			//
			//过滤树
	        function searchArch() {
	            var key = mini.get("keyarch").getValue();
	            if (key == "") {
	                tree.clearFilter();
	            } else {
	                key = key.toLowerCase(); 
	                tree.filter(function (node) {
	                    var text = node.name?node.name.toLowerCase() : "";
	                    if (text.indexOf(key) != -1) {
	                        return true;
	                    }
	                });
	            }
	        };
				
	        var Ranks = [{ id: 1, text: '公开' },{ id: 2, text: '非涉密'},{ id: 3, text: '涉密'},
			   			 { id: 4, text: '内部'}, { id: 5, text: '秘密'},{ id: 6, text: '机密'}];
   			var Retentions = [{ id: 1, text: '1年' }, { id: 5, text: '5年'}, { id: 10, text: '10年'}, { id: 50, text: '50年'}, { id: 100, text: '100年'}];

			var Isattacheds = [{ id: 0, text: '正卷' },{ id: 1, text: '副卷'}];
			
			//
		   function CloseWindow(action){ 
		       if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
		       else window.close();
		   };
		   
		   //手动关闭
		    function Close(){
		    	if(window.parent.goBack){
		    		window.parent.goBack();
		    	}else{
		    		goBack();
		    	}
		    }
		   
		   // 关闭,返回
		    function goBack(){
		      	var r = new Date().getTime();
		       	url = parsefurl();
		      	if(url){
		       	   window.location.href=url;
		      	}else{
		         	CloseWindow("close");
		      	}
		    };
		</script>
  </body>
</html>