<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>法院系统模板</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
    </style>
</head>
<body>
    <input id="menuid" name="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>
    <input id="operator" name="operator" value="new" class="mini-hidden" />
    <input id="tempid1" name="tempid1"  class="mini-hidden" />
    <input id="generaltype1" name="generaltype1"  class="mini-hidden" />
    <input id="requestdepartid" name="requestdepartid"  class="mini-hidden" />
    <div id="div_one" class="mini-splitter"  style="width:100%;height:100%;">
			<div showCollapseButton="true" style="padding:0px;">
		        <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
		            <table style="width:100%;">
		                <tr align="right">
		                    <td style="white-space:nowrap;">
		                        <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="模版名称" style="width:180px;" onenter="onKeyEnter"/>   
		                        <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
		                    </td>
		                </tr>
		            </table>           
		        </div>
			    <div class="mini-fit" id="div_two" >
				    <div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
				        url="getCourtSystemModelList.action?1=1"  idField="id" multiSelect="true"  allowAlternating="true"  virtualScroll="true"
				        showLoading="false" onbeforeload="myloading" onload="myunmask">
					    	<div property="columns">
					    		<div field="tempname" headerAlign="center"  align="left" allowSort="true" width="80px;">模板名称</div>
					    		<div field="temptypename" headerAlign="center"  align="center" allowSort="false" width="40px">模版类型</div>
					    		<div field="planname" headerAlign="center"  align="center" allowSort="false" width="60px">查询方案</div>
					    		<div field="generaltype" headerAlign="center"  align="center" renderer="onComboboxRenderer"  allowSort="true" width="40px">保存类型</div>
					    		<div field="sn" headerAlign="center"  align="left" allowSort="true" width="25px;">排序号</div>
					    		<div field="tempid" headerAlign="center"  align="center" allowSort="true" width="30px;">编号</div>
					   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;" width="60px">操作</div>
							</div>   
				    </div>
			  </div>
		</div>
		<div showCollapseButton="true"  size="450px;">
			<form id="form1" method="post">
				<div class="mini-toolbar" style="border:0;padding:0px;">
		            <table style="width:100%;">
		                <tr align="right">
		                    <td style="white-space:nowrap;">
		                          <a class="mini-button" iconCls="icon-add"  plain="true" onclick="xinzeng()">新增</a>
		                          <span class="separator"></span> 
		                          <a class="mini-button" iconCls="icon-save"  plain="true" onclick="savedata()">保存</a>
		                    </td>
		                </tr>
		            </table>           
	       		 </div>
	         	 <div class="mini-fit" id="div_two" >
		         	 <fieldset style="width:98%;border:solid 1px #aaa;position:relative;">
				        <legend>文书模板信息</legend>
				        <div id="editForm1" style="padding:5px;">
				            <table style="width:100%;">
				                <tr>
				                    <td  style="width:60px;">模板名称：</td>
				                    <td style="width:130px;" >
				                    	<input name="tempname" id="tempname" vtype="maxLength:200;" required="true" class="mini-textbox" style="width:100%;"/>
				                    </td>
				                	<td  style="width:60px;">模板编号：</td>
				                    <td style="width:130px;" >
				                    	<input name="tempid" id="tempid" vtype="maxLength:20;"  class="mini-textbox" style="width:100%;"/>
				                    </td>
				                </tr>
				                <tr>
				                	<td style="width:60px;">模板类型：</td>
				                	<td style="width:130px;" >
				                		<input id="temptype" name="temptype" class="mini-treeselect" url="ajaxGettemptype.json?codetype=GK1103" emptyText="请选择..."
										textField="NAME" parentField="PCODEID" valueField="CODEID" required="true" expandOnLoad="0" allowInput="false" style="width:100%;"/>
				                	</td>    
				                    <td style="width:60px;">查询方案：</td>
				                    <td style="width:130px;">
			                    		<input name="findid" id="findid" style="width:100%;" class="mini-treeselect" url="ajaxShecmeAll.action?1=1" emptyText="请选择..."
			                    		 textField="name" parentField="pplanid"  valueField="planid" showTreeIcon="true" />
				                    </td>
				                </tr>
				                <tr>
				                	<td style="width:60px;">排序号：</td>
				                    <td style="width:130px;" >
				                    	<input id="sn" name="sn" class="mini-spinner" maxValue="999999" minValue="1" allowInput="false"  style="width:100%;"/>
				                    </td>
				                	<td style="width:60px;">保存类型：</td>
				                    <td style="width:130px;">
				                    	<input id="generaltype" name="generaltype" textField="text" required="true" valueField="id" class="mini-combobox" data="SAVETYPE" value="4" style="width:100%;"/>
				                    </td>
				                </tr>
				                <tr>
				                	<td><font style="color:#0046D5;">文书内容</font></td>
				                </tr>
				                <tr>
				                    <td style="width:420px;" colspan="4">
				                    	<input name="content" id="content" required="true" class="mini-textarea" style="width:100%; height:280px;"/>
									</td>
				                </tr>
				            </table>
				        </div>
			    	 </fieldset>
	         	 </div>
	         </form>
		</div>
 	</div>
    <script type="text/javascript">
    	var SAVETYPE = [{id: 4, text : '公有'},{id : 5, text : '私有'}];
        mini.parse();
       	var form = new mini.Form("form1");
        var grid = mini.get("datagrid1");
        grid.load();
        grid.sortBy("sn", "asc");
        
		function onActionRenderer(e) {
	        var s ="";
		    s=s+'<a class="Edit_Button" href="javascript:chakan()">查看</a>&nbsp;&nbsp;'
               +'<a class="Edit_Button" href="javascript:xiugai()">修改</a>&nbsp;&nbsp;'
               +'<a class="Edit_Button" href="javascript:shanchu()">删除</a>';
	        return s;
	    }
	    // 渲染通用类型
        function onComboboxRenderer(e) {
            for (var i = 0, l = SAVETYPE.length; i < l; i++) {
                var g = SAVETYPE[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        };
         //新增
        function xinzeng() {
        	mini.get("operator").setValue("new");
        	$.ajax({
                url: "ajaxGetTempid.json?1=1",
                type: "post",
                success: function (text) {
        				mini.get("tempid").setValue(text);
                		form.setEnabled(true);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	mini.alert("操作失败!");
                }
            });
        	//form.reset();
        	//form.setEnabled(true);
        }
        //保存或更新
        function savedata() {
            form.validate();
            if (form.isValid() == false) return;
       		var operator = mini.get("operator").getValue();
       		var requestdepartid = mini.get("requestdepartid").getValue();
       		var tempid = mini.get("tempid").getValue();
       		var tempid1 = mini.get("tempid1").getValue();
       		var temptype = mini.get("temptype").getValue();
       		var tempname = mini.get("tempname").getValue();
       		var findid = mini.get("findid").getValue();
       		var content = mini.get("content").getValue();
       		var generaltype = mini.get("generaltype").getValue();
       		var generaltype1 = mini.get("generaltype1").getValue();
       		var sn = mini.get("sn").getValue();
        	if(operator == "new"){//新增
	            $.ajax({
	                url: "saveModelDetail.action?1=1&operator="+operator,
	                data: {tempid:tempid,tempname:tempname,findid:findid,functionname:'法院系统模版',
	                		content:content,temptype:temptype,generaltype:generaltype,sn:sn},
	                type: "post",
	                success: function (text) {
	                	if(text=='0'){
	                		mini.alert("操作失败，模板编号和通用类型重复！");
	                		return;
	                	}else{
	                		mini.alert("操作成功！");
	                		form.reset();
	                  		grid.reload();
	                	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	mini.alert("操作失败!");
	                }
	            });
            }else if(operator == "edit"){//更新
	            $.ajax({
	                url: "saveModelDetail.action?1=1&tempid="+tempid+"&operator="+operator,
	                data: {requestdepartid:requestdepartid,tempid1:tempid1,tempname:tempname,functionname:'法院系统模版',findid:findid,
	                		content:content,temptype:temptype,generaltype:generaltype,generaltype1:generaltype1,sn:sn},
	                type: "post",
	                success: function (text) {
	                	alert("操作成功!");
	                  	form.reset();
	                  	grid.reload();
                        mini.get("operator").setValue("new");
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
            }
        }
        //查看
		function chakan() {
			var row = grid.getSelected();//获取选中记录
               form.loading("查询中，请稍后......");
               $.ajax({
                 url: "getModelDetail.action?1=1&tempid=" + row.tempid,
                 type: "post",
                 success: function (text) {
                     var o = mini.decode(text);
                     form.setData(o);
                     form.unmask();
                     form.setEnabled(false);
                 }
             });
		}     
		 //修改
		function xiugai() {
			mini.get("operator").setValue("edit");
			var row = grid.getSelected();//获取选中记录
               form.loading("查询中，请稍后......");
               $.ajax({
                 url: "getModelDetail.action?1=1&tempid=" + row.tempid,
                 type: "post",
                 success: function (text) {
                     var o = mini.decode(text);
                     mini.get("tempid1").setValue(row.tempid);
                     mini.get("requestdepartid").setValue(row.departid);
                     mini.get("generaltype1").setValue(row.generaltype);
                     form.setData(o);
                     form.unmask();
                     form.setEnabled(true);
                     mini.get("tempid").setEnabled(false);
                     mini.get("generaltype").setEnabled(true);
                 }
             });
		}        
		//删除
        function shanchu(v){
        	var row = grid.getSelected();//获取选中记录
        	var deleteConfirmMessage = "此操作会删除相关的私有模版，确定删除此记录吗?";
	        if(confirm(deleteConfirmMessage)){
	        	form.loading("删除中，请稍后......");
           		$.ajax({
	                 url: "deleteCourtModelDetail.action?1=1&tempid=" + row.tempid+"&departid="+row.departid,
	                 type: "post",
	                 success: function (text) {
	                     form.unmask();
	                     grid.reload();
	                 }
	             });
         	}
		}
		 function myloading(){
			var myform = new mini.Form("div_one");
			myform.loading();
		}
		function myunmask() {
			var myform = new mini.Form("div_one");
			myform.unmask();
		}
		//快速查询
		function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
    </script>
</body>
</html>