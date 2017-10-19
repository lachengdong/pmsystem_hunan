<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
  	<title>批次设置新增</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
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
        	<input id="crimids" name="crimids" class="mini-hidden" value="${crimids}"/>
        	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;">
	                    	<a class="mini-button" id="onOk" onclick="onOk();" plain="true" style="width:60px">确定</a>
		           			<a class="mini-button" onclick="onCancel();"  plain="true" style="width:60px;">取消</a>                      
		           		</td>
	                    <td style="white-space:nowrap;">
	                    </td>
	                </tr>
	            </table>           
	        </div>
        	<div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
				<tr >
					<td style="width:90px;" align="left">三类犯：</td>
			        <td style="width:250px;">    
			        	<input id="sanclassstatus" name="sanclassstatus" class="mini-combobox" multiSelect="true" data="threetypearray" style="width:250px;"
			        		emptyText="请选择..." showNullItem="false"  value="" />
			        </td>
				</tr>
            </table>
        </div>
    </form>
<script type="text/javascript">
	
	var threetypearray = [{id:"1",text:"金融诈骗"},{id:"2",text:"黑社会性质"},{id:"3",text:"职务犯罪"}];

	//标准方法接口定义
	function SetData(data) {
		
	}
	
	function onOk(){
		SaveData();
	}
	
	function SaveData() {
		var crimids = mini.get("crimids").getValue();
		var sanclassstatus = mini.get("sanclassstatus").getValue();
		
		$.ajax({
            url: "<%=path%>/saveThreeKindCrimeIdentify.json?1=1",
            data: {crimids:crimids, sanclassstatus:sanclassstatus},
            type: "post",
            dataType:"text",
            async:false,
            success: function (text) {
            	alert("操作成功!");
            },
            error: function (jqXHR, textStatus, errorThrown) {
            	alert("操作失败!");
            }
        });
		CloseWindow("cancel");
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
