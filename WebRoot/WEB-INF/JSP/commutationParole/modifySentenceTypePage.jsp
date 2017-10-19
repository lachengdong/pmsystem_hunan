<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <title>减刑类型描述</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
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
  	<input id="optype" name="optype"  required="true" class="mini-hidden" style="width:100%;"/>
    <form id="form1" method="post">
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
		            		<a class="mini-button" onclick="SaveData" plain="true" style="width:60px">确定</a>       
		           			<a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>           
	        </div>
        	<div style="padding-left:5px;padding-bottom:5px;">
            <table style="table-layout:fixed;" border="0">
				<tr > 
					<td style="width:60px;">*减刑描述</td>
					<td colspan="3">
						<input id="name" name="name"  required="true" class="mini-textbox" style="width:100%;"/>
						<input id="senid" name="senid"  required="true" class="mini-hidden" style="width:100%;"/>
			        </td>
				</tr>
				<tr>
					<td style="width:60px;">*适用起日</td>
					<td >
						<input id="stime" name="stime" class="mini-datepicker" dateFormat="yyyy-MM-dd" required="true"  />
			        </td>
			        <td style="width:60px;">*适用止日</td>
					<td >
						<input id="etime" name="etime" class="mini-datepicker" dateFormat="yyyy-MM-dd" required="true" />
			        </td>
				</tr>
				<tr>
					<td >刑期开始年</td>
					<td style="" >
						<input id="syear" name="syear" class="mini-spinner" minValue="1" maxValue="9999" />
			        </td>
			        <td >范围</td>
					<td >
						<input id="sop" name="sop" class="mini-combobox" data="sData" value="2" emptyText="选择范围" enabled="true"
							textField="text" showNullItem="false"  valueField="id" required="true" style="width:100%;"/>						
			        </td>
				</tr>
				<tr>
					<td >刑期中止年</td>
					<td >
			        	<input id="eyear" name="eyear" class="mini-spinner" minValue="1" maxValue="9999" />
			        </td>
			        <td >范围</td>
					<td style="" >
						<input id="eop" name ="eop" class="mini-combobox" data="eData" value="5" emptyText="选择范围" enabled="true"
							textField="text" showNullItem="false"  valueField="id" required="true" style="width:100%;"/>						
			        </td>
				</tr>
				<tr>
					<td >*上级目录</td>
					<td colspan="3">
						<input id="remark" name ="remark" class="mini-treeselect" url="ajaxSelectPrisonerSentence.action"  emptyText="请选择..." enabled="true"
					    textField="text" showNullItem="true"  valueField="id" parentField="remark" required="false" popupHeight="120" style="width:100%;" expandOnLoad="1"/>						
			        </td>
				</tr>
			</table>
			<div class="description">
					</br>
		        	说明：</br>
					　　1.*部分必须正确
					</br>
					　　2.上级目录为顶级时可为空
			</div>
        </div>
    </form>
  </body>
</html>
<script type="text/javascript">
	var sData = [{id:"1",text:"大于"},{id:"2",text:"大于等于"},{id:"3",text:"等于"}];
	var eData = [{id:"3",text:"等于"},{id:"4",text:"小于"},{id:"5",text:"小于等于"}];
	mini.parse();
	var form = new mini.Form("form1");
	var optype = mini.get("optype");
	function SaveData() {
        form.validate();
        if (form.isValid() == false) return;
        var o = form.getData();  
        var data = mini.encode([o]);
        var optypev = optype.getValue();
		var  url = 'ajaxBySaveSentence.json?1=1';
		$.ajax({
	         url:url, 
	         data: {data:data,optype:optypev},
	         type: "post",
	         cache:false,
	         async:false,
	         success: function (text){
	         	alert("操作成功!");
	         	CloseWindow('close');
	         }
		});
    }
	// 渲染日期
    function onDateRenderer(e) {
    	if(e && e.value){
    		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
    	}
        return "";
    }
	function onCancel(e) {
		CloseWindow("cancel");
    }
    
    function CloseWindow(action) { 
    	if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
    ////////////////////
    //标准方法接口定义
    function SetData(data) {
    	optype.setValue(data.type);
        if (data.type == "2" || data.type == "0") {
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = mini.clone(data);
            $.ajax({
                url: "ajaxBySentenceType.json?1=1&senid=" + data.senid,
                cache: false,
                success: function (text) {
                    var o = mini.decode(text);
                    form.setData(o);
                    form.setChanged(false);
                }
            });
        }
    }
    
</script>
