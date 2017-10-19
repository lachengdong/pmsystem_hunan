<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
String commenttext = "";
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>审批意见窗口</title>
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
    <style type="text/css">
		html, body
	    {
	        font-size:12px;
	        padding:0;
	        margin:0;
	        border:0;
	        height:100%;
	        width:100%;
	        overflow:hidden; 
	    }
	    .big .mini-textbox-input{
		   font-size:20px;
		   line-height:25px;
		}
	</style>
  </head>
  
  <body>
  <div class="mini-toolbar" style="border-bottom:0;padding:1px;" >               
       <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
       <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a> 
    </div>
     <div class="mini-fit" style="padding-left:11px;padding-bottom:5px;padding-right:11px;border-color: 1px solid;">
         <table style="table-layout:fixed;width:100%;">
             <tr> 
             	<td style="width:80px;">不同意理由：</td>
                <td >    
                     <input name="select1" id="select1" class="mini-combobox" textField="text" valueField="id"  allowInput="false"  showNullItem="true" emptyText="请选择理由" 
                     	onvaluechanged="select1changed" data="datas" value="1" style="width:100%;"/>
                </td>
            </tr>
			<tr>
            	<td colspan="2" rowspan="1">    
                 	<input  id="commenttext" name="commenttext" class="mini-textarea" emptyText="请填写理由" style="width:100%; height: 135px"/>
             	</td>
       		</tr>  
         </table>
     </div>
     <script type="text/javascript">
     	var datas = [{id:1,text:'不同意'}];
     	
        mini.parse();
        var winid = '${yjmap.winid}';
        var crimid = '${yjmap.crimid}';
        var operateType = '${yjmap.operateType}';
        var select1 = mini.get("select1");
        if(winid || operateType == 'defer'){
        	var url="<%=path%>/ajaxGetWindowSelect.json?crimid="+crimid;
        	if(operateType == 'defer'){
        		winid = "10420";
        	}
        	url +="&winid="+winid;
        	select1.setUrl(url);
        	select1.select(0);
        }
        mini.get("commenttext").setValue(select1.getText());
        
        function CloseWindow(action) {          
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onOk(e) {
        	var result = "";
        	var commenttext = mini.get("commenttext").getValue();
            var row = new Array([1]);
            row[0] = "ok";
            row[1]=commenttext;
            window.returnValue = row;
            CloseWindow("cancel");
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        function select1changed(){
			mini.get("commenttext").setValue(select1.getText());
		}
     </script>
  </body>
</html>
