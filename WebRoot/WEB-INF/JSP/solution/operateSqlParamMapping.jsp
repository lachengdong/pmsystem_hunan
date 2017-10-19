<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>SQL参数映射操作</title>
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
        	<input id="sqlgroupid" name="sqlgroupid" class="mini-hidden"/>
        	<input id="paramid" name="paramid" class="mini-hidden"/>
        	
            <table style="table-layout:fixed;" align="center" >
            	<tr>
            		<td>SQL参数名：</td>
                    <td>    
                        <input id="sqlparamname" name="sqlparamname" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  required="true"  emptyText=""/>
                    </td>
            	</tr>
            	<tr>
            		<td>表单字段名：</td>
                    <td>    
                        <input id="formfieldname" name="formfieldname" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  required="true"  emptyText=""/>
                    </td>
            	</tr>
            	<tr>
            		<td>默认值：</td>
                    <td>    
                        <input id="nullemptyvalue" name="nullemptyvalue" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  emptyText=""/>
                    </td>
            	</tr>
            	 <tr>
            		<td>数据类型：</td>
                    <td>
                        <input id="datatype" name="datatype" class="mini-combobox" valueField="id" textField="text"  required="true" style="width:180px;" 
	                         emptyText="" data="[{ id: 0, text: '字符串' },{ id: 1, text: '数字' },{ id: 2, text: '日期' },{ id: 3, text: '大字段' }]" />
                    </td>
            	</tr>
            	<tr>
            		<td>循环标志：</td>
                    <td>    
                        <input id="cycleflag" name="cycleflag" class="mini-combobox" valueField="id" textField="text"  required="true" style="width:180px;" 
	                         emptyText="" data="[{ id: 0, text: '非循环' },{ id: 1, text: '循环' }]" />
                    </td>
            	</tr>
            	<tr>
            		<td>是否为空：</td>
                    <td>    
                        <input id="notnullflag" name="notnullflag" class="mini-combobox" valueField="id" textField="text"  required="true" style="width:180px;" 
	                         emptyText="" data="[{ id: 0, text: '可为空' },{ id: 1, text: '不可为空' }]" />
                    </td>
            	</tr>
            	<tr>
            		<td>为空删除：</td>
                    <td>    
                        <input id="nulldelflag" name="nulldelflag" class="mini-combobox" valueField="id" textField="text"  required="true" style="width:180px;" 
	                         emptyText="" data="[{ id: 0, text: '不删除' },{ id: 1, text: '删除' }]" />
                    </td>
            	</tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        //var DataTypeSet = [{ id: 0, text: '字符串' },{ id: 1, text: '数字' },{ id: 2, text: '日期' },{ id: 3, text: '大字段' }];
        //var CycleDataSet = [{ id: 0, text: '非循环' },{ id: 1, text: '循环' }];
        //var NotNullDataSet = [{ id: 0, text: '可为空' },{ id: 1, text: '不可为空' }];
        //var NullDelDataSet = [{ id: 0, text: '不删除' },{ id: 1, text: '删除' }];
        mini.parse();
        var form = new mini.Form("form1");
        
        function SaveData(){
            var o = form.getData();
            form.validate();
            if (form.isValid() == false) return;
            var operate = mini.get("operate").getValue();
            var json = mini.encode([o]);
            $.ajax({
                url: "<%=path%>/solution/saveParamMapping.action?1=1",
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
        function SetData(data) {
        	data = mini.clone(data);
       		mini.get("operate").setValue(data.action);
       		mini.get("solutionid").setValue(data.solutionid);
       		mini.get("detailid").setValue(data.detailid);
       		mini.get("sqlgroupid").setValue(data.sqlgroupid);
        	if(data.action == "edit"){//更新
                $.ajax({
                    url: "<%=path%>/solution/getSingleParamMapping.action?1=1",
                    type: "post",
                    cache: false,
                    data:{solutionid:data.solutionid, detailid:data.detailid, sqlgroupid:data.sqlgroupid, paramid:data.paramid},
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