<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
  <head>
    <title>检查监督</title>
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
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
	   <input id="id" name="id" type="hidden" value="${id}"/>
            <table >
               <tr>
                <td style="width:100%;">
                	<a class="mini-button"  iconCls="" plain="true" onclick="saveData();">确定</a>
                	<a class="mini-button"  iconCls="" plain="true" onclick="onCanle();">取消</a>
                </td>
                <td style="white-space:nowrap;">
		 		</td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
        <table>
        	<tr><td><br/></td><td></td></tr>
        	<tr>
        		<td>&nbsp;&nbsp;意见&nbsp;&nbsp;</td>
        		<td><input id="textarea" name="Area" class="mini-textarea" style="height:100px;width:410px;" required="true" value="${flow.text3}"/></td>
        	</tr>
        </table>
    </div>
    <script type="text/javascript">
    	mini.parse();
    	function saveData() {
    	    var opion = mini.get("textarea").getValue();
    	    var id = document.getElementById("id").value;
    		$.ajax({
                url: "saveOpinion.action?1=1",
                data: { opion : opion, id : id },
                type:"POST",
                cache: false,
                success: function (text) {
					CloseWindow();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("操作失败！");
                    CloseWindow();
                }
            }); 
    	}
    	function CloseWindow(action) {            
          if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
          else window.close();            
       }
       function onCanle() {
           CloseWindow("cancel");
       }
    </script>
</body>
</html>

        