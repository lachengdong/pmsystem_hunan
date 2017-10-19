<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
  	<title>处罚意见编辑页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
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
    <form id="form1" method="post">
        	<input id="operatetype" name="operatetype" class="mini-hidden" />
        	<input id="batchid" name="batchid" class="mini-hidden" value="${batchid}"/>
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
	                    	<a class="mini-button" id="onOk" onclick="onOk" plain="true" style="width:60px">确定</a>
		           			<a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>           
	        </div>
        	<div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
				<tr >
					<td style="width:90px;">处罚类型</td>
					<td style="width:120px;">    
			        	<input id="puntype" name="puntype" class="mini-treeselect" url="ajaxLocalcodeList.action?1=1&codetype=GK100019&pcodeid=6" emptyText="请选择..."
							textField="name" valueField="codeid" allowInput="false" style="width:100%;" required="true"/>
			        </td>
				</tr>
				<tr>
					<td style="width:90px;">处罚日期</td>
			        <td style="width:120px;">    
			        	<input id="pundate" name="pundate" value="${pundate}" required="true" class="mini-datepicker" />
			        </td>		         
				</tr>
				<tr>
					<td style="width:90px;">处罚时间</td>
			        <td style="width:120px;">    
			        	<input id="puntime" name="puntime" class="mini-spinner" maxValue="100000000000" style="width:100%;" enabled="true" required="true"/>
			        </td>
				</tr>				
            </table>
        </div>
    </form>
<script type="text/javascript">

	function onOk(){
		SaveData();
	}
	function SaveData() {
		var form = new mini.Form("form1");
        form.validate();
        if (form.isValid() == false) return;
	    var puntype = mini.get("puntype").getText();
	    var pundate = mini.get("pundate").getText();
	    var puntime = mini.get("puntime").getValue();
	    var pundatearr = new Array([3]);
	    pundatearr = pundate.split("-");
	    var pundateval = pundatearr[0] + "年" + pundatearr[1] + "月" + pundatearr[2] + "日";
		var row = new Array([2]);
		row[0] = puntype;
		row[1] = pundateval;
		row[2] = puntime;
		window.returnValue = row;            	
		CloseWindow();
    }
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
	
</script>
  </body>
</html>
