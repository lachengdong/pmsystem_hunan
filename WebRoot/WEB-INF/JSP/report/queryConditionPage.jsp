<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
  <head>
    <title>查询条件</title>
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
	<input id="showtype" name="returnval" type="hidden" value="hidden"/>
    <form id="form1" method="post">
          <div class="mini-toolbar" style="border-bottom:0;padding:1px;height:30px;" >               
           <a class="mini-button" onclick="onOk" plain="true" style="width:60px">确定</a>       
           <a class="mini-button" onclick="onCancel"  plain="true" style="width:60px;">取消</a>       
        </div>
        <div style="padding-left:11px;padding-bottom:5px;padding-right:11px;">
            <table style="table-layout:fixed;">
                <tr>
                    <td width="80px;">查询条件：</td>
                    <td width="60px;">    
						入监日期
                    </td>
                    <td width="80px;">    
                          <input id="select1" class="mini-combobox" textField="text" valueField="id" allowInput="false" style="width:100%;" data="gtltarray" required="true"/>
                    </td>
               
                    <td width="130px;">    
                          <input id="date1" class="mini-datepicker" style="width:100%;" required="true"/>
                    </td>
                    <td width="80px;">    
                          <input id="select2" class="mini-combobox" textField="text" valueField="id" allowInput="false" style="width:100%;" data="andordata" onvaluechanged="select2changed();"/>
                    </td>
                </tr>
            </table>
         <div id="queryconditiondiv" style="display:none">
            <table>
                <tr>
                    <td width="80px;"></td>
                    <td width="60px;">    
						入监日期
                    </td>
                    <td width="80px;">    
                          <input id="select3" class="mini-combobox" textField="text" valueField="id" allowInput="false" style="width:100%;" data="gtltarray" required="true"/>
                    </td>
               
                    <td width="130px;">    
                          <input id="date2" class="mini-datepicker" style="width:100%;" required="true"/>
                    </td>
                    <td width="80px;">    

                    </td>
                </tr> 
            </table>
        </div>
        </div>
    </form>
    <script type="text/javascript">
    	var gtltarray = [{id:0, text: ''},{id:1, text: '>'},{id:2, text:'<'},{id:3, text:'='}];
    	var andordata = [{id:0, text: ''},{id:'and', text: '并且'},{id:'or', text:'或者'}];
        mini.parse();
		var s1 = mini.get("select1");
		var s2 = mini.get("select2");
		var s3 = mini.get("select3");
		var date1 = mini.get("date1");
		var date2 = mini.get("date2");
        var form = new mini.Form("form1");
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
        	form.validate();
		    if (form.isValid() == false) return;
		    var returnval = "";
            var showtype = document.getElementById("showtype").value;
        	var dateone = date1.value.getFullYear() + "-" + (date1.value.getMonth()+1) + "-" + date1.value.getDate();
        	returnval = returnval + " and (a.inprisondate" + s1.text + "to_date('" + dateone + "','yyyy-MM-dd') ";
        	var datetwo = "";
        	if(showtype=="show") {
        		datetwo = date2.value.getFullYear() + "-" + (date2.value.getMonth()+1) + "-" + date2.value.getDate();
        		if(s2) {
        		    returnval = returnval + s2.value + " a.inprisondate " + s3.text + "to_date('" + datetwo + "','yyyy-MM-dd') ";
        		}
        	}
        	returnval = returnval + ")";
            window.returnValue = returnval;
            CloseWindow("cancel");
        }
        function select2changed() {
			if(s2.value!='0') {
				document.getElementById("queryconditiondiv").style.display="";
				document.getElementById("showtype").value = "show";
			} else {
				document.getElementById("queryconditiondiv").style.display="none";
				document.getElementById("showtype").value = "hidden";
			}
        }

        function onCancel(e) {
            CloseWindow("cancel");
        }
        function SetData(data){
        
        }
        function superPermission(e) {
        	var a = mini.get("select2").value;
        }
        function selectMenu(e) {
        	var b = mini.get("select3").value;
        }
    </script>
</body>
</html>

        