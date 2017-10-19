<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>细节SQL组操作</title>
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
        	 
            <table style="table-layout:fixed;" align="center" >
            	<tr>
            		<td>SQL组ID：</td>
                    <td> 
                    	<input id="sqlgroupid" name="sqlgroupid" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  required="true" enabled="false" emptyText=""/>   
                    </td>
            	</tr>
            	<tr>
            		<td>SQL组名称：</td>
                    <td>    
                        <input id="sqlgroupname" name="sqlgroupname" class="mini-textbox"  style="width:180px;"   vtype="maxLength:40;minLength:1;"  required="true"  emptyText=""/>
                    </td>
            	</tr>
            	 <tr>
            		<td>SQL组类型：</td>
                    <td>    
                        <input id="sqlgrouptype" name="sqlgrouptype" class="mini-combobox" valueField="id" textField="text"  required="true" style="width:180px;" 
	                         emptyText="" data="[{ id: 0, text: '查询' }, { id: 1, text: '保存'} ,{ id: 2, text: '删除'}]" />
                    </td>
            	</tr>
            	<tr>
            		<td>结果集映射KEY：</td>
                	<td><input id="resultkey" name="resultkey" maxlength="254" class="mini-textbox"  style="width:180px;"/></td>
            	</tr>
            	<tr>
                	<td >判断SQL模板：</td>
                	<td><input id="testsql" name="testsql" class="mini-buttonedit" text="<CLOB>" allowInput="false" onbuttonclick="onButtonEdit" style="width:180px;"/></td>
                </tr>
                <tr>	
                	<td >主SQL文本模板：</td>
                	<td><input id="mainsql" name="mainsql" class="mini-buttonedit" text="<CLOB>" allowInput="false" onbuttonclick="onButtonEdit" style="width:180px;"/></td>
                </tr>
                <tr>
                	<td >备用SQL文本模板：</td>
                	<td><input id="standbysql" name="standbysql" class="mini-buttonedit" text="<CLOB>" allowInput="false" onbuttonclick="onButtonEdit" style="width:180px;"/></td>
                </tr>
                <!--  
                <tr>
                	<td >删除SQL：</td>
                    <td><input id="delsql" name="delsql" class="mini-buttonedit" text="<CLOB>" allowInput="false" onbuttonclick="onButtonEdit" style="width:180px;"/></td>
                </tr>
                -->
                <tr>                	 
                	<td>是否主SQL组：<input id="ismaingroup" name="ismaingroup" class="mini-checkbox" text="" value="0" trueValue="1" falseValue="0" /></td>
                  	<td>开始顺序：<input name="startorder" class="mini-spinner" allowInput="true" maxValue="99" minValue="1"  validateOnLeave style="width:40px;"/></td>
                </tr>
                <tr>
                	 <td>特殊位：<input id="signflag" name="signflag" class="mini-combobox" style="width:60px;" valueField="id" textField="text"  emptyText=""
                       			value="0" data="[{ id: 0, text: '普通' }, { id: 1, text: '下拉'} ,{ id: 2, text: '临时'},{ id: 3, text: '列表'}]" required="false" /></td>
                	 <td>是否循环：<input id="cycleflag" name="cycleflag" class="mini-checkbox" text="" value="0" trueValue="1" falseValue="0" />
                	 循环次数：<input name="int1" class="mini-spinner" allowInput="true" maxValue="99" minValue="1"  validateOnLeave style="width:40px;"/></td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
        function SaveData(){
            var o = form.getData();
            form.validate();
            if (form.isValid() == false) return;
            var operate = mini.get("operate").getValue();
            var json = mini.encode([o]);
            $.ajax({
                url: "<%=path%>/solution/saveDetailSqlGroup.action?1=1",
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
        	if(data.action == "edit"){//更新
                $.ajax({
                    url: "<%=path%>/solution/getSingleSqlGroup.action?1=1",
                    type: "post",
                    cache: false,
                    data:{solutionid:data.solutionid, detailid:data.detailid, sqlgroupid:data.sqlgroupid},
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
        
        function onButtonEdit(){
        	var btnEdit = this;
        	var id = btnEdit.id;
        	var sqlValue = mini.get(id).getValue();
            mini.open({
                url: "<%=path%>/solution/toEditSolutionClobPage.action?1=1",         
                title: "",
                width: 700,
                height: 650,
                showMaxButton: true,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = {sql : sqlValue};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    if (action == "ok"){
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        btnEdit.setValue(data.sql);
                        //btnEdit.setText(data.text);
                    }
                }
            }); 
        }
        
    </script>
</body>
</html>