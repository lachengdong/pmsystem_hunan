<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	
	//
	//组织列中按钮的修改和查看功能：前提是配资源时，资源的相关信息有：xiugai,修改,处理	chakan,查看
	Object middlemapObj = request.getAttribute("middlemap");
	String xiugaiButton = "";
	String chakanButton = "";
	if(null != middlemapObj&&middlemapObj instanceof HashMap){
		Map middlemap = (HashMap)middlemapObj;
		Set<String> set = middlemap.keySet();
		for(String buttonkey : set){
			String buttonValue = middlemap.get(buttonkey).toString();
			if(buttonValue.indexOf("xiugai") != -1 || buttonValue.indexOf("修改") != -1 || buttonValue.indexOf("处理") != -1){
				xiugaiButton = buttonValue;
			}else if(buttonValue.indexOf("chakan") != -1 || buttonValue.indexOf("查看") != -1){
				chakanButton = buttonValue;
			}
		}
	}
	
%>
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title></title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
    	</style>
</head>
<body>
	<div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
		<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
		<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
		<input id="currnodeid" name="currnodeid" class="mini-hidden" value="${currnodeid}"/>
        <table>
               <tr>
                <td style="width:100%;">
                	${topstr}
                </td>
                <td style="white-space:nowrap;">
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('')"></a>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
	    	url="<%=path%>/getBaseDocByTempletID.action?1=1&tempid=${tempid}&flowdefid=${flowdefid}"  idField="" multiSelect="true" allowAlternating="true" showLoading="true">
	        <div property="columns">
	       		<div type="checkcolumn" width="15px"></div>
	    		<div field="conent" headerAlign="center" align="center" allowSort="true" width="100px;">文书简介</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="50px">操作人</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="50px">操作时间</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="60px">操作</div> 
	        </div>
	    </div>
    </div>
    <script type="text/javascript"> 
    	mini.parse();
    	var grid = mini.get("datagrid");
        grid.load();
        
        //
        var xiugaiButtonObj = "<%=xiugaiButton%>";
        var chakanButtonObj = "<%=chakanButton%>";
        var xiugaiButtonArr = xiugaiButtonObj.split('@');
        var chakanButtonArr = chakanButtonObj.split('@');
        function onActionRenderer(e){
        	var row = e.record;
        	var nodeid = row.nodeid;
        	var state = row.state;
         	var s = '';
         	if(nodeid == 0){
         		s = " <a class=\"Edit_Button\" href=\"javascript:"+xiugaiButtonArr[1]+"\" >"+xiugaiButtonArr[0]+"</a>";
         	}else{
         		s = " <a class=\"Edit_Button\" href=\"javascript:"+chakanButtonArr[1]+"\" >"+chakanButtonArr[0]+"</a>";
         	}
    	    return s;
        }
        //
        
	</script>
	<script src="<%=path %>/scripts/publicjs.js" type="text/javascript"></script>
</body>
</html> 