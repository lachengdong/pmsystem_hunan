<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String menuid = request.getParameter("menuid");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增方案页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    html, body
    {
        font-size:12px;
        padding:0;
        margin:0;
        border:0;
        height:100%;
        overflow:hidden;
    }
    </style>
</head>
<body>    
   	<input id="operate" name="operate" class="mini-hidden" value=""/>
   	<input id="schemeid" name="schemeid" class="mini-hidden" value=""/>
   	<input id="fschemeid" name="fschemeid" class="mini-hidden" value="-1"/>
    <form id="form1" method="post">
    	<div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >               
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>       
        </div>
        <div style="padding-left:2px;padding-bottom:5px;">
            <table style="table-layout:fixed;" align="center" >
            	<tr>
            		<td style="width:80px;">方案名称：</td>
                    <td style="width:150px;">    
                        <input id="name" name="name" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  required="true"  emptyText="请输入方案名称"/>
                    </td>
            	</tr>
            	<tr>
            		<td style="width:80px;">父方案名称：</td>
                    <td style="width:150px;">    
                        <input id="fname" name="fname" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  allowInput="false"/>
                    </td>
            	</tr>
            	<tr>
            		<td style="width:80px;">对应资源：</td>
                    <td style="width:150px;"> 
                        <input id="resid" name="resid" allowInput="false" value="" style="width:100%;" class="mini-buttonedit" 
                            onbuttonclick="onButtonEdit" />
                    </td>
                 </tr>
                 <tr>
            		<td style="width:80px;">数据关系：</td>
                    <td style="width:150px;">    
                        <input id="datarelation" name="datarelation" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;" />
                    </td>
            	</tr>
            	 <tr>
            		<td style="width:80px;">方案类型：</td>
                    <td style="width:150px;">    
                        <input id="reportortemp" name="reportortemp" class="mini-combobox" valueField="id" textField="text"  required="true" style="width:180px;" 
	                         emptyText="选择报表类型" data="DataSet" />
                    </td>
            	</tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        //此处添加一个列表用的类型，宁夏用，其他得方不用可以不用选择。
        var DataSet = [{ id: 0, text: '报表用' },{ id: 1, text: '模板用' },{ id: 2, text: '列表用' }];
        mini.parse();
        var form = new mini.Form("form1");
        function SaveData() {
            var o = form.getData();
            form.validate();
            if (form.isValid() == false) return;
            var operate = mini.get("operate").getValue();
            var schemeid = mini.get("schemeid").getValue();
            var fschemeid = mini.get("fschemeid").getValue();
            var json = mini.encode([o]);
            $.ajax({
                url: "saveQueryScheme.action?1=1&operate=" + operate +"&fschemeid="+fschemeid +"&schemeid="+schemeid,
                data: { data: json },
                type: "post",
                cache: false,
                success: function (text) {
                	if(text == 1){
                		alert("操作成功！");
                	}else{
                		alert("操作失败!");
                	}
                    CloseWindow("save");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                    CloseWindow();
                }
            });
        }

        //标准方法接口定义
        function SetData(data) {
        	data = mini.clone(data);
       		mini.get("operate").setValue(data.action);
       		if(data.action == "new"){
       		    mini.get("fschemeid").setValue(data.planid);
       		    mini.get("fname").setValue(data.name);
       		    mini.get("reportortemp").setValue(data.reportortemp);;
       		}
        	if(data.action == "edit"){//更新 
                mini.get("schemeid").setValue(data.planid);
                mini.get("name").setValue(data.name);
                mini.get("fschemeid").setValue(data.fplanid);
                mini.get("fname").setValue(data.fname);
                mini.get("resid").setValue(data.resid);
                mini.get("datarelation").setValue(data.datarelation);
                mini.get("reportortemp").setValue(data.reportortemp);
   
                $.ajax({
                    url: "getResourceNameById.action?1=1&resid=" + data.resid,
                    type: "post",
                    cache: false,
                    success: function (text) {
                        var data =  mini.decode(text);
                        mini.get("resid").setText(data);
                    }
                });
        	}
        }
        function onButtonEdit(e) {
            var btnEdit = this;
            mini.open({
                url: "toResourceTreePage.action?1=1",
                showMaxButton: false,
                title: "选择资源",
                width: 350,
                height: 350,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        if (data) {
                        	document.getElementById("schemeid").value = data.resid;
                            btnEdit.setValue(data.resid);
                            btnEdit.setText(data.name);
                        }
                    }
                }
            });
        } 
       function onBeforeNodeCheck(e) {
            var tree = e.sender;
            var node = e.node;
            if (tree.hasChildren(node)) {
                //e.cancel = true;
            }
        }
        function GetData() {
           var nodes = tree.getCheckedNodes();
           var ids = "";
           for (var i = 0, l = nodes.length; i < l; i++) {
               var node = nodes[i];
               if(i == nodes.length-1){
            	   ids = ids + node.spid;
               }else{
            	   ids = ids + node.spid + ",";
               }
            
            }
            return ids;
        }
     
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
    </script>
</body>
</html>