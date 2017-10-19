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
						plain="true">获取裁定信息</button>&nbsp&nbsp&nbsp
                   		姓名: <input id="crimname"
						name="crimname" class="mini-textbox" value="${crimname}"
						style="width: 80px; text-align: center" />    
		               <a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
	                </td>
                </tr>
            </table>           
        </div>
    <div class="mini-fit" id="div_two">
    <div id="datagrid" class="mini-datagrid" style="width:100%;height:90%;" allowResize="false"  sizeList="[10,20,30,50,100]"
       url="<%=path%>/courtJoint/getRulingList.action"   onselectionchanged="onSelectionChanged"
       idField="spid" multiSelect="true" pageSize="20" allowAlternating="true"  virtualScroll="true" onselectionchanged="selectionChanged">
        <div property="columns">
            <div field="CRIMID" width="40px;" headerAlign="center"  align="center" allowSort="true" >罪犯号</div> 
            <div field="CRIMNAME" width="40px;" headerAlign="center"  align="center" allowSort="true" >姓名</div> 
            <div field="CASENUMBER" width="40px;" headerAlign="center"  align="center" allowSort="true">案件号</div>   
            <div field="MANAGECOURT" width="40px;" headerAlign="center"  align="center" allowSort="true" >经办法院</div> 
            <div field="SETTLEDATE" width="40px;" headerAlign="center"  align="center" allowSort="true">结案日期</div>   
			<div field="RULINGDATE" width="40px;" headerAlign="center"  align="center" allowSort="true">裁定日期</div>   
            <div field="SETTLETYPE" width="40px;" headerAlign="center"  align="center" allowSort="true" >结案方式</div> 
			<div name="action" width="240px" headerAlign="center" align="center" renderer="seeWrit" cellStyle="padding:0;">查看裁定文书</div>   
        </div>
    </div>
    
    <div id="employee_grid" class="mini-datagrid" style="width:100%;height:10%;" 
        url="<%=path%>/courtJoint/getRulingDetailList.action" showPager="false">
        <div property="columns">
            <div field="SETTLERESON" width="40px;" headerAlign="center"  align="center" allowSort="true">结案案由</div>   
			<div field="NOSETTLEWRITRESON" width="40px;" headerAlign="center"  align="center" allowSort="true">无结案文书理由</div>   
            <div field="MAINCASE" width="40px;" headerAlign="center"  align="center" allowSort="true">变更后主刑</div>   
            <div field="ISEDUCEPENALTY" width="40px;" headerAlign="center"  align="center" allowSort="true" >是否减余刑</div> 
            <div field="THISCHANGEYEAR" width="40px;" headerAlign="center"  align="center" allowSort="true">本次减刑</div>
        	<div field="COURTCHANGYEAR" width="40px;" headerAlign="center"  align="center" allowSort="true">变更后自由刑期</div>   
			<div field="ANNULFAJIN" width="40px;" headerAlign="center"  align="center" allowSort="true">减免罚金数额</div>   
            <div field="NOTINCLUDEYEAR" width="40px;" headerAlign="center"  align="center" allowSort="true">不计入刑期</div> 
            <div field="REDUCERESON" width="40px;" headerAlign="center"  align="center" allowSort="true">减刑事由</div>  
            <div field="PRISONSTARTTIME" width="40px;" headerAlign="center"  align="center" allowSort="true">刑期起日</div>   
            <div field="PRISONENDTIME" width="40px;" headerAlign="center"  align="center" allowSort="true" >刑期止日</div> 
            <div field="LOSEPOWER" width="40px;" headerAlign="center"  align="center" allowSort="true">剥夺政治权利</div>   
			<div field="CUSTODYRESON" width="40px;" headerAlign="center"  align="center" allowSort="true">收监执行事由</div>	
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
        
        function seeWrit(e) {
      	  
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s =  '查看 &nbsp&nbsp<a class="Edit_Button" href="javascript:toSeeWrit(\'' + uid+  '\',2)">刑事裁定书</a>&nbsp&nbsp'+
            		'<a class="Edit_Button" href="javascript:toSeeWrit(\'' + uid + '\',3)">起诉书</a>&nbsp&nbsp'+
            		'<a class="Edit_Button" href="javascript:toSeeWrit(\'' + uid + '\',4)">判决书</a>&nbsp&nbsp'+
            		'<a class="Edit_Button" href="javascript:toSeeWrit(\'' + uid + '\',5)">执行通知书</a>&nbsp&nbsp'+
            		'<a class="Edit_Button" href="javascript:toSeeWrit(\'' + uid + '\',6)">结案登记表</a>&nbsp&nbsp'+
            		'<a class="Edit_Button" href="javascript:toSeeWrit(\'' + uid + '\',7)">送达回证</a>&nbsp&nbsp'
            return s; 
        }

        function toSeeWrit(uid,type){
	    	   var row = grid.getRowByUID(uid);
	            var row = grid.getSelected(); 
	            var crimid = row.CRIMID;
	            var batichid = mini.get("picibianhao").getValue();
	            if(batichid==""){
	      		  alert("请选择批次信息！");
	      		  return;
	      	  	}
	            var url = "<%=path%>/courtJoint/seeWrit.action?crimid="+crimid+"&batchid="+batichid+"&type="+type;
	            if (row) {
	                mini.open({
	                    url: url,
	                    title: "开庭通知", width: 1000, height: 1000,
	                    onload: function () {

	                    },
	                    ondestroy: function (action) {
	                        grid.reload();
	                    }
	                });
	            } else {
	                alert("请选中一条记录");
	            }  
	        }
        
      function onSelectionChanged(e) {
          var employee_grid = mini.get("employee_grid");
          var grid = e.sender;
          var record = grid.getSelected();
          var picibianhao = mini.get("picibianhao").getValue();
          if (record) {
              employee_grid.load({ crimid: record.CRIMID,picibianhao:picibianhao });
          }
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
			    url: "<%=path%>/courtJoint/getRulingDataFromCourt.action",
		        success: function (){
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