<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>监狱报送检察室</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
     <script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
	    .s{
	    	width:100%;height:100%;_height:94.8%;
	    }
    </style>
    <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
	</script>
     <script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var batchAlertOne = "您要对以下所选服刑人员进行立案吗?";
     </script>
</head>
<body onload="init('${menuid}');">
		<input name="flowdefid" id="flowdefid" class="mini-hidden" value="${flowdefid}"/>
		<input name="tempid" id="tempid" class="mini-hidden" value="${tempid}"/>
		<input name="menuid" id="menuid" class="mini-hidden" value="${menuid}"/>
		<input name="courttype" id="courttype" class="mini-hidden" value="${courttype}"/>
		<input name="ischeckseal" id="ischeckseal" class="mini-hidden" value="${ischeckseal}"/>
		<input id="initPageIndex" class="mini-hidden" value="${initPageIndex}"/>
		<input id="initPageSize" class="mini-hidden" value="${initPageSize}"/>
		<div id="div_one" class="s"> 
        <div class="mini-toolbar" style="border-bottom: 0px;padding:0px;">
        <jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
		               (<input id="curyear" name="curyear"  class="mini-textbox"  value="${curyear}" style="width:40px;text-align:center"/>)
		               <input id="shortname" name="shortname"  class="mini-textbox"  value="${shortname}" style="width:45px;text-align:center"/>
                     
						</select>
		              <input id="picibianhao" class="mini-combobox" valueField="picibianhao" textField="pici"  emptyText="批次"
		                      showNullItem="true" nullItemText="--全部--" onBlur="search()" url="getPiciSlecter.action?curyear=${curyear}" style="width:80px;" />

						</select>
                   </td>
                   
                   <td style="white-space:nowrap;">
                   <button class="mini_button" href="" onclick ="getData();"
						plain="true">获取立案信息</button>&nbsp&nbsp&nbsp
                   	姓名: <input id="crimname"
						name="crimname" class="mini-textbox" value="${crimname}"
						style="width: 80px; text-align: center" />
                
		               <a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
    <div class="mini-fit" id="div_two">
    <div id="datagrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,30,50,100]"
       url="<%=path%>/courtJoint/getPutonrecordList.action" onrowdblclick="chooseOne" 
       idField="spid" multiSelect="true" pageSize="20" allowAlternating="true"  virtualScroll="true" onselectionchanged="selectionChanged">
        <div property="columns">
            <div type="checkcolumn" width="10px;"></div>
            <div field="CRIMID" width="40px;" headerAlign="center"  align="center" allowSort="true" >罪犯号</div>
            <div field="CRIMNAME" width="40px;" headerAlign="center"  align="center" allowSort="true" >姓名</div>
            <div field="CASENUMBER" width="40px;" headerAlign="center"  align="center" allowSort="true">案件号</div>   
            <div field="LIANEXAMINEPERSON" width="40px;" headerAlign="center"  align="center" allowSort="true" >立案审查人</div> 
            <div field="LIANEXAMINEDATE" width="80px;" headerAlign="center"  align="center" allowSort="false">立案审查日期</div> 
			<div field="LIANEXAMINEOPINION" width="40px;" headerAlign="center"  align="center" allowSort="true" >立案审查意见</div> 
            <div field="LIANEXAMINEREMARK" width="80px;" headerAlign="center"  align="center" allowSort="false">立案审查备注</div> 
        	<div field="LIANAPPROVALPERSON" width="40px;" headerAlign="center"  align="center" allowSort="true" >立案审批人</div> 
            <div field="LIANAPPROVALDATE" width="80px;" headerAlign="center"  align="center" allowSort="false">立案审批日期</div> 
			<div field="LIANAPPROVALOPINION" width="40px;" headerAlign="center"  align="center" allowSort="true" >立案审批意见</div> 
            <div field="LIANAPPROVALREMARK" width="80px;" headerAlign="center"  align="center" allowSort="false">立案审批备注</div> 
        	<div field="LIANDATE" width="80px;" headerAlign="center"  align="center" allowSort="false">立案日期</div> 
        </div>
    </div>
  </div>
</div> 
  
  <div style="height: 0px;">
		<table>
			<tr>
				<td height="100%">
					<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
				</td>
			</tr>
		</table>		
	</div>
	
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("caseno", "asc");
        grid.on("drawcell",function(e){
     	});
     	//缓存当前页数，当点击弹出办案页面办理完毕后，点关闭依然在当前页面。
     	var initPageIndex = mini.get("initPageIndex").getValue();
        var initPageSize = mini.get("initPageSize").getValue();
        grid.gotoPage(parseInt(initPageIndex),parseInt(initPageSize));
     	
        function search() {
 	       var curyear = mini.get("curyear").getValue();
 	      var picibianhao = mini.get("picibianhao").getValue();
 	     var crimname = mini.get("crimname").getValue();
 	       grid.load({curyear : curyear, picibianhao:picibianhao,crimname:crimname});
       }
        
		//日期格式转化
		 function onDateRenderer(e){
		 	var value = e.value;
            if (value) return mini.formatDate(value, 'yyyy-MM-dd');
            return "";
		 }
        function onKeyEnter(e) {
            search();
        } 
 		
      
      function selectionChanged(e){
			var row = grid.getSelecteds();
			if(row.length>30){
				alert("选中条数不能多余30条，请重新选择！");
				grid.clearSelect(true);
				return;
			}
		}
      
      function getData(){
    	  var messageBox = mini.loading("正在下载获取中,稍等片刻......");
			$.ajax({
			    url: "<%=path%>/courtJoint/getPutOnDataFromCourt.action",
		        success: function (text){
		        	mini.hideMessageBox(messageBox);
		        	alert("操作成功");
		         	grid.reload();
		        },
		        error: function(){
		        	mini.hideMessageBox(messageBox);
		        	grid.reload();
		        }
			});
      }
  </script>
</body>
</html>