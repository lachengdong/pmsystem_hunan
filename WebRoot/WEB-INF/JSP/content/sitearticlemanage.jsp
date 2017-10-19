<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>内容管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path %>/js/showModalDiv/jquery.ui.core.js"></script>  
    	 <link rel="stylesheet" href="<%=path %>/css/L2Nzcy90aGVtZS9saWdodGJsdWUvdWkuY3Nz.css" type="text/css"></link>
		  <script type="text/javascript" src="<%=path %>/js/showModalDiv/jquery.ui.widget.js"></script>  
		  <script type="text/javascript" src="<%=path %>/js/showModalDiv/jquery.ui.mouse.js"></script>  
		  <script type="text/javascript" src="<%=path %>/js/showModalDiv/jquery.ui.draggable.js"></script>  
		  <script type="text/javascript" src="<%=path %>/js/showModalDiv/jquery.ui.position.js"></script>  
		  <script type="text/javascript" src="<%=path %>/js/showModalDiv/jquery.ui.dialog.js"></script>
		  <script type="text/javascript" src="<%=path %>/js/showModalDiv/jquery-1.4.2.js"></script>
	    <style type="text/css">
			body{
	    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	   			}    
	    </style> 
</head>
<body>
	<div class="mini-splitter" style="width: 100%; height: 100%;">
    		<div size="240" showCollapseButton="true">
				<div class="mini-toolbar" style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					<span style="padding-left: 5px;">名称：</span>
					<input class="mini-textbox" vtype="maxLength:50;" id="key1"
						onenter="onKeyEnter1" />
					<a class="mini-button" iconCls="icon-search" plain="true"
						onclick="search1()">查找</a>
				</div>
				<div class="mini-fit">
					<ul id="tree1" class="mini-tree"
						url="getArticleModuleTree.json?1=1"
						tyle="width: 100%; height: 100%;" showTreeIcon="true"
						textField="title" idField="moduleid" parentField="pmoduleid" resultAsTree="false"  showArrow="true"  expandOnNodeClick="true" >
					</ul>
				</div>
			</div>
		    <div showCollapseButton="true">
			    <div class="mini-toolbar" style="border:0;padding:0px;">
				      <table style="width:100%;">
		                <tr>
		                    <td>
		                        <a class="mini-button" iconCls="icon-add" onclick="add()" plain="true" tooltip="增加...">增加</a>
		                    </td>
		                    <td style="white-space:nowrap;" align="right">
		                    	<input id="key" class="mini-textbox" emptyText="请输入标题" style="width:100px;" onenter="onKeyEnter"/>   
		                        <a class="mini-button" plain="true" iconCls="icon-search" onclick="search()">查找</a>
		                        <span class="separator"></span>
		                        <!-- <a class="mini-button" iconCls="icon-reload" onclick="publishall()" plain="true">批量发布</a> -->
		                        <a class="mini-button" iconCls="icon-remove" onclick="batchRemove()" plain="true" >批量删除</a>
		                    </td>
		                </tr>
		            </table>           
		        </div>
				<div id="datagrid1" class="mini-datagrid" style="width:100%;height:95%;" url="getArticleList.json?1=1" idField="id"  allowResize="true" pageSize="20" 
			        	allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
			         <div property="columns">
			      		<div type="checkcolumn" ></div>  
			            <div field="title" headerAlign="center" align="center" allowSort="true">内容标题</div>   
			            <div field="expirationtime" headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">过期时间</div>    
			            <div field="state" headerAlign="center" align="center" allowSort="true" renderer="onStateRenderer" >发布状态</div>
			            <div field="" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
					</div>
			    </div>		         
		    </div>
    </div>
    <script type="text/javascript"><!--
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("sn", "desc");
        grid.load();
        
        var tree = mini.get("tree1");
        
        //菜单树
        tree.on("nodeselect", function (e) {
        	var moduleid = e.node.moduleid;
        	grid.load({ moduleid: moduleid});
        	grid.sortBy("TYPE", "asc");
        });
    
        //过滤菜单树
        function search1() {
            var key = mini.get("key1").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase();                
                tree.filter(function (node) {
                    var text = node.title ? node.title.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        }
       
        function onDateRenderer(e) {
	    	if(e && e.value){
	    		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
	    	}
	        return "";
	    }
	    
	    function onStateRenderer(e) {
	    	if(e.value == '1'){
	    		return '是';
	    	} else {
		    	return  '否';
	    	}
	    }
       
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
       		var s = '<a class="Edit_Button" href="javascript:view(\'' + uid + '\')" >查看</a>&nbsp;'
       		+ '<a class="Edit_Button" href="javascript:edit(\'' + uid + '\')" >修改</a>&nbsp;'
       		
       		if(record.state == '1'){
       			s = s + '<a class="Edit_Button" href="javascript:quxiaofabu(\'' + uid + '\')" >取消发布</a>&nbsp;'
       		} else {
       			s = s + '<a class="Edit_Button" href="javascript:quxiaofabu(\'' + uid + '\')" >发布</a>&nbsp;'
       		}
       		s = s + '<a class="Edit_Button" href="javascript:batchRemove(\'' + uid + '\')" >删除</a>'
            return s;
        }
        //新增
       function add() {
       		var moduleid = mini.get("tree1").getValue();
       		mini.open({
                url: "ywgkArticle/chakanPage.page?1=1",
                title: "内容管理", width: 900, height: 585,
                allowResize: false,
	            showMaxButton: false,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new",moduleid: moduleid};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
       
       //查看
      function view() {
           var row = grid.getSelected();
           if (row) {
	         mini.open({
		       
	            url: "ywgkArticle/chakanPage.page?1=1&operate=view",
	                    title: "查看", width: 900, height: 585,
	                    onload: function () {
	                        var iframe = this.getIFrameEl();
	                        var data = { action: "view", id: row.articleid };
	                        iframe.contentWindow.SetData(data);
	                    },
	                    ondestroy: function (action) {
	                        grid.reload();
	                    }
	                });
           }
           else {
               alert("请选中一条记录");
           }
       }
		//修改
		function edit() {
            var row = grid.getSelected();
            if (row) {
	               	mini.open({
	                    url: "ywgkArticle/chakanPage.page?1=1&id="+row.articleid,
	                    title: "编辑内容", width: 900, height: 585,
	                    onload: function () {
	                        var iframe = this.getIFrameEl();
	                        var data = { action: "edit", id: row.articleid };
	                        iframe.contentWindow.SetData(data);
	                    },
	                    ondestroy: function (action) {
	                        grid.reload();
	                        
	                    }
	                });

            } else {
                alert("请选中一条记录");
            }
            
        }
		
		//批量删除
		function batchRemove(){
			var rows = grid.getSelecteds();
			if(rows.length>0){
				if(confirm("确定删除选中记录？")){
					var str = [];
					for(var i=0; i<rows.length; i++){
						var r = rows[i];
						str.push(r.articleid);
					}
					var ids = str.join(",");
					grid.loading("操作中，请稍后......");
					$.ajax({
						url:"<%= path%>/ywgkArticle/batchRemove.json?id=" +ids,
						success: function (text){
							grid.reload();
						},
						error: function (){
							//alert("操作失败");
							grid.reload();
						}
					});
				}
			}else {
					alert("请选择至少一篇文章进行删除！");
			}
		}
		//取消发布
		function quxiaofabu(id){
				var rows = grid.getSelected();
				if(rows.state == '1'){
					if(window.confirm("确定要取消发布！是否继续执行?")){
						$.ajax({
								url: "<%= path%>/ywgkArticle/updateState.json?articleid="+rows.articleid+"&state="+rows.state,
								type: "post",
								success: function(text){
									//alert("取消成功");
									grid.reload();
								},
								error: function(){
									alert("取消失败");
									grid.reload();
								}
								
						});
						
						
					}
				} else{
					if(window.confirm("确定要发布！是否继续执行?")){
						$.ajax({
								url: "<%= path%>/ywgkArticle/updateState.json?articleid="+rows.articleid+"&state="+rows.state,
								type: "post",
								success: function(text){
									//alert("取消成功");
									grid.reload();
								},
								error: function(){
									alert("取消失败");
									grid.reload();
								}
								
						});
						
					}
				}
		}
		//上移
		/* function shangyi(){
			var rows = grid.getSelected();
            if (rows != null) {
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "moveupArticle.action?1=1&id="+rows.articleid+"&moduleid="+rows.moduleid+"&stationid="+rows.stationid,
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        	alert("操作失败");
                        }
                    });
             
            } else {
                alert("请选中一条记录");
            }
            
		}
		//下移
		function xiayi(){
			var rows = grid.getSelected();
            if (rows != null) {
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "movedownArticle.action?1=1&id="+rows.articleid+"&moduleid="+rows.moduleid+"&stationid="+rows.stationid,
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        	alert("操作失败");
                        }
                    });
            } else {
                alert("请选中一条记录");
            }
			
		}*/
		function onGenderRenderer(e) {
            if (e.value == 1) return "是";
            else return "否";
        }
		
		//快速查询   
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: encodeURLComponent(key) });
        }
        function onKeyEnter(e) {
            search();
        }
        function onKeyEnter1(e) {
            search1();
        }
    </script>
 </body>
 <div id="dialog-form" style="display:none;">
			<form>
			<fieldset>
				<label >文章发布中.请等待...</label>
				<label ><marquee style='border:1px solid #000000' direction='right' width='200' scrollamount='5' scrolldelay='10' bgcolor='#ECF2FF'>
				<table cellspacing='1' cellpadding='0'>
				 <tr height="8">
				<td bgcolor="#3399FF" width="10"></td>
				<td></td>
				 <td bgcolor="#3399FF" width="10"></td>
				 <td></td>
				 <td bgcolor="#3399FF" width="10"></td>
				 <td></td>
				     <td bgcolor="#3399FF" width="10"></td>
				     <td></td>
				 </tr>
				 </table>
				 </marquee></label>
			</fieldset>
			</form>
		</div>
</html>