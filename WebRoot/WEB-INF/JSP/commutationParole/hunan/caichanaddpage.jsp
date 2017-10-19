<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title>财产刑添加</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"rel="stylesheet" type="text/css" />
	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
	<style type="text/css">
		html,body {
			font-size: 12px;padding: 0;margin: 0;border: 0;height: 100%;overflow: auto;
		}
	</style>
  </head>
  
  <body >
    <form id="form1" method="post">
	<div class="mini-toolbar mini-grid-headerCell" style="padding: 1px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<table>
			<tr>
				<td style="width: 100%;">
					${topstr}
					<a  class="mini-button" onclick="onCancel" plain="true" iconCls="icon-close" style="width: 60px;">关闭</a>
        		</td>
				<td style="white-space: nowrap;">
				</td>
			</tr>
		</table>
	</div>
	<div style="padding-left: 15px; padding-bottom: 5px;">
		<table style="table-layout: fixed;">
			<tr style="height: 10px;">
				
				<td style="width: 60px;">财产刑类别：</td>
				<td >
					<input id="caichantype" class="mini-treeselect" emptyText="请选择..." showNullItem="true" nullItemText="请选择..." value="${caichantype }" required="true"
			         textField="text" valueField="id" name="caichantype" showFolderCheckBox="false"  showClose="true" popupWidth="200" 
			         	data="[{id:'fajin',text:'罚金'},{id:'moshou',text:'没收财产'},{id:'minpei',text:'民事赔偿'},{id:'zangkuan',text:'追缴赃款'},{id:'tuipei',text:'责令退赔'}]"/>
				</td>
				<td style="width: 60px;">执行时间：</td>
				<td >
					<input id="zhixingdate" name="zhixingdate" allowInput="true" format="yyyy-MM-dd HH:mm:ss" showTime="true" style="width: 170px;"  class="mini-datepicker" required="true"/>
				</td>
			</tr>
			
			<tr style="height: 10px;">
				<td style="width: 90px;">开票时间：</td>
				<td colspan=5>
					<input id="kaipiaodate" name="kaipiaodate" allowInput="true" format="yyyy-MM-dd" showTime="true" style="width: 170px;"  class="mini-datepicker" required="true"/>
				</td>
			</tr>
			
			<tr style="height: 10px;">
				<td style="width: 90px;">执行金额：</td>
				<td colspan=5>
					<input id="zhixingjine" name="zhixingjine" class="mini-textbox" vtype="float" required="true"  style="width: 98.1%;" value="${zhixingjine }"/>
				</td>
			</tr>
			
			<tr style="height: 10px;">
				<td style="width: 90px;">执行机关：</td>
				<td colspan=5>
					<input id="zhixingjiguan" name="zhixingjiguan" class="mini-textbox" vtype="maxLength:800"  style="width: 98.1%;" value="${zhixingjiguan}"/>
				</td>
			</tr>
			
			<tr style="height: 10px;">
				<td style="width: 90px;">执行单据号：</td>
				<td colspan=5>
					<input id="danjuhao" name="danjuhao"  required="true" class="mini-textbox" vtype="maxLength:500"  style="width: 98.1%;" value="${danjuhao }"/>
				</td>
			</tr>
			
			<tr style="height: 12px;">
				<td style="width: 90px;">未执行部分：</td>
				<td colspan=5>
					<input id="weizhixing" name="weizhixing" class="mini-textarea" vtype="maxLength:500" style="width: 98.1%;" value="${weizhixing }"/>
				</td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">

	mini.parse();
  	var form = new mini.Form("form1");
  	if("${operator}"=="edit"){
  	  	 mini.get("zhixingdate").setEnabled(false);
  	  	 mini.get("caichantype").setEnabled(false);
  	}
  	if("${operator}"=="chakan"){
 	  	 mini.get("zhixingdate").setEnabled(false);
 	  	 mini.get("kaipiaodate").setEnabled(false);
 	  	 mini.get("caichantype").setEnabled(false);
 	  	 mini.get("zhixingjine").setEnabled(false);
 	  	 mini.get("zhixingjiguan").setEnabled(false);
 	  	 mini.get("danjuhao").setEnabled(false);
 	  	 mini.get("weizhixing").setEnabled(false);
 	}
  	
	function onOk(e){
		SaveData();
	}
  	
    function SaveData(resid) {
    	form.validate();
        if (form.isValid() == false) return;
       //获取财产刑下拉列表框中的值
       var status = '0';
       if(resid=='18579' ||resid=='18582'){
	   		status = '1';
       }
       var o = form.getData();  
       var json = mini.encode([o]);
       var caichantype = mini.get("caichantype").getValue();
       
       var zhixingjine = mini.get("zhixingjine").getValue(); 
       var zhixingjiguan = mini.get("zhixingjiguan").getValue(); 
       var danjuhao = mini.get("danjuhao").getValue();//执行单据号
       var zhixingdate = mini.get("zhixingdate").getText();
       var kaipiaodate = mini.get("kaipiaodate").getText();
       
       $.ajax({
            url: "isSaveCaiChanXing.action?1=1&crimid=${crimid}&danjuhao="+danjuhao,
            data: {data:json},
            type: 'POST',
            cache: false,
            success: function (text) {
            	if(text==1){
            	      alert("单据号已经存在，请重新输入！");
            	}else{
	            	 $.ajax({
		                 url: "saveCaiChanXing.action?1=1&crimid=${crimid}&operator=${operator}&zhixingdate="+zhixingdate,
		                 data: {data:json,caichantype:caichantype,zhixingdate:zhixingdate,status:status,kaipiaodate:kaipiaodate},
		                 type: 'POST',
		                 cache: false,
		                 success: function (text) {
		            	 	alert("操作成功!");
		                 	onCancel();
		                 },
		                 error: function (jqXHR, textStatus, errorThrown) {
		            	 	alert("操作失败!");
		                 }
	                 });
            	}
            	return;
            },
            error: function (jqXHR, textStatus, errorThrown) {
            	alert("操作失败!");
            }
       }); 
    }
    
    
    function onDateRenderer(e) {
     	if(e && e.value){
     		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
     	}
        return "";
	 }

	 function back(){
		 var zhixingdate = mini.get("zhixingdate").getText();
		 var danjuhao = mini.get("danjuhao").getValue();
		 $.ajax({
	            url: "backCaiChanXing.action?1=1&crimid=${crimid}&zhixingdate="+zhixingdate+"&danjuhao="+danjuhao, 
	            type: 'POST',
	            cache: false,
	            success: function (text) {
	            	alert("操作成功!");
	                onCancel();
	            },
	            error: function (jqXHR, textStatus, errorThrown) {
	            	//alert("操作失败!");
	                onCancel();
	            }
	        }); 
	 }

	  //关闭窗口
      function onCancel(e) {
    	CloseWindow("cancel");
      }
	  
	  
    function CloseWindow(action) {
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();    
    }
    
      //标准方法接口定义
      function SetData(data) {
          //跨页面传递的数据对象，克隆后才可以安全使用
          data = mini.clone(data);
    	  mini.getbyName("zhixingdate").setValue(mini.formatDate ( new Date(data.zhixingdate), "yyyy-MM-dd HH:mm:ss" ));
    	  mini.getbyName("kaipiaodate").setValue(mini.formatDate(new Date(data.kaipiaodate),"yyyy-MM-dd"))
      }
</script>
  </body>
</html>
