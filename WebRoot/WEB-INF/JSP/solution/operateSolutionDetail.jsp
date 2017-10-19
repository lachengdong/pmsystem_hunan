<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>方案细节操作</title>
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
   	<input id="operate" name="operate" class="mini-hidden" />
    <form id="form1" method="post">
    	<div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >               
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>
        </div>
        <div style="padding-left:2px;padding-bottom:5px;">
        	<input id="solutionid" name="solutionid" class="mini-hidden"/>
        	<input id="detailid" name="detailid" class="mini-hidden"/>
            <table style="table-layout:fixed;" align="center" >
            	<tr>
            		<td>方案细节ID：</td>
                    <td>    
                        <input id="detailid" name="detailid" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  required="true" enabled="false" emptyText=""/>
                    </td>
            	</tr>
            	<tr>
            		<td>方案细节名称：</td>
                    <td>    
                        <input id="detailname" name="detailname" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  required="true"  emptyText=""/>
                    </td>
            	</tr>
            	 <tr>
            		<td>方案细节类型：</td>
                    <td>    
                        <input value="0" id="detailtype" name="detailtype" class="mini-combobox" valueField="id" textField="text"  required="true" style="width:180px;" 
	                         emptyText="" data="DataSet" />
                    </td>
            	</tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        var DataSet = [
	        	{ id: 0, text: '查询' }
	        	,{ id: 1, text: '保存' }
	        	,{ id: 2, text: '删除' }
	        	,{ id: 3, text: '列表' }
        	];
        mini.parse();
        var form = new mini.Form("form1");
        function SaveData(){
            var o = form.getData();
            form.validate();
            if (form.isValid() == false) return;
            var operate = mini.get("operate").getValue();
            var json = mini.encode([o]);
            $.ajax({
                url: "<%=path%>/solution/saveSolutionDetail.action?1=1",
                data: { data: json ,operate:operate},
                type: "post",
                cache: false,
                success: function (text) {
                	if(text == 1){
                		alert("操作成功！");
                		CloseWindow("ok");
                	}else{
                		alert("操作失败!");
                		CloseWindow("error");
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                    CloseWindow();
                }
            });
        }

        //标准方法接口定义
        function SetData(data){
        	data = mini.clone(data);
       		mini.get("operate").setValue(data.action);
       		mini.get("solutionid").setValue(data.solutionid);
        	if(data.action == "edit"){//更新
                $.ajax({
                    url: "<%=path%>/solution/getSingleSolutionDetail.action?1=1",
                    type: "post",
                    cache: false,
                    data:{solutionid:data.solutionid, detailid:data.detailid},
                    success: function (text){
                        var o = mini.decode(text);
                        form.setData(o);
                    }
                });
        	}
        }
        
        function onCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
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