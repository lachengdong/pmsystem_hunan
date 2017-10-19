<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> aipmap = (Map<String,String>)request.getAttribute("aipmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
		<title>省局立案</title>
		<script type="text/javascript">
     	var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
     	var batchAlertOne = "您确定要对所选案件进行立案吗?";
     </script>
		
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }   
	    .s{
    		width:100%;height:100%;_height:94.8%;
    	}   
    </style>
</head>
<body onload="init('${menuid}')">
	  <input name="flowdefid" id="flowdefid" class="mini-hidden" value="${flowdefid}"/>
      <input name="templetid" id="templetid" class="mini-hidden" value="${templetid}"/>
      <div id="div_one" class="s">
	 <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
           <table >
               <tr>
                <td style="width:100%;"> 
                	案件号：【（<input id="curyear" name="curyear"  class="mini-textbox"  value="${curyear}" 
                							style="width:40px;text-align:center"/>）${shortname} 
                		<input id="commutetype" class="mini-combobox" valueField="id" textField="text" data="[{ id: 0, text: '减字' }, { id: 1, text: '假字'} ]" 
                				onvaluechanged="search" style="width:50px;" value="0" />
                		第 <input id="casenumber" name="casenumber"  class="mini-textbox"  value="${casenumber}" style="width:40px;text-align:center"/> 号】
        				<!--   收案日期：<input id="caseacceptdate" name="caseacceptdate" style="width:120px;" class="mini-datepicker" /> -->
        				
                </td>
                <td style="white-space:nowrap;">
                    <input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择监狱"
                            showNullItem="true" nullItemText="--全部--" url="getJailList.action" style="width:150px;" onvaluechanged="search"/>
                    <!--  <a class="mini-button" iconCls="icon-ok"  plain="true" onclick="batchLian()">批量立案</a>-->
                    ${topstr}
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>      
                </td>
               </tr>
		</table>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    </div>
    <div class="mini-fit">
	     <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	     	url="getProvinceLiAnList.action?1=1&flowdefid=other_sjjxjssp"  idField="" multiSelect="true" allowAlternating="true" 
	     	sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">  
	        <div property="columns">
	        		<div type="checkcolumn" width="20px;"></div> 
            		<div field="jailname" width="70" headerAlign="center" align="center"  allowSort="true">监狱名称</div>
            		<div field="caseno" width="120" headerAlign="center" align="center"  allowSort="true">监狱案号</div>
            		<div field="crimid" width="60" headerAlign="center"  align="center" allowSort="true">罪犯编号</div>
            		<div field="name" width="60" headerAlign="center"  align="center" allowSort="true">姓名</div>    
            		<div field="jailinfo" width="130"  headerAlign="center"  align="center" allowSort="true" >监狱意见</div>
            		 <div id="action" width="60px"  headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>     
	        </div>
    </div>
 </div>
 </div>
 <script type="text/javascript">
		 mini.parse();
		 var datagrid = mini.get("datagrid");
		 datagrid.load();
		 //mini.get("caseacceptdate").setValue(new Date());//收案日期
		 // 渲染日期
		 function onDateRenderer(e) {
		 	if(e && e.value){
		 		return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
		 	}
		    return "";
		 }
		  
        function onActionRenderer(e){
            var s ='';
	        s = document.getElementById('middlebtns').value;
	     	return s;
        }
        
       //动态显示案件号
        function addAnjianhao(commutetype) {
        		var curyear = mini.get("curyear").getValue();
        		var flowdefid = mini.get("flowdefid").getValue();
        		$.ajax({
				         url: "/pmsys/ajaxGetMaxCommuteCaseNo.action?1=1", 
				         data: {curyear:curyear, flowdefid:flowdefid, commutetype:commutetype},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var a = eval(text);
				         	var b = parseInt(a);
				        	mini.get("casenumber").setValue(b);
				         }
				});
        }
       
		//立案
        function batchLian(){
        	var rows = datagrid.getSelecteds();
            if (rows.length>0) { 
            	if(confirm(batchAlertOne)){
            		var idArr = [];
                    for (var i = 0, l = rows.length; i < l; i++){
                    	var row = rows[i];
                    	idArr.push(row.crimid);
                    }
                    var ids = idArr.join(',');
					
					var curyear = mini.get("curyear").getValue();
					var templetid = mini.get("templetid").getValue();
					var flowdefid = mini.get("flowdefid").getValue();
					var commutetype = mini.get("commutetype").getValue();
                     $.ajax({
		                 url: "<%=path%>/provinceCommuteLiAn.action",
				         data: {ids:ids,curyear:curyear,flowdefid:flowdefid,templetid:templetid,commutetype:commutetype},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         cache: false,
		                 success: function (text){
		                 	if(text.status==1){
		                 		alert("操作成功!");
			         			addAnjianhao(commutetype);//案件号加n
		                 	}else{
		                 		alert(text.info);
		                 	}
		                	datagrid.reload();
		                 },
		                 error: function (jqXHR, textStatus, errorThrown) {
		                   alert("操作失败!");
		                   datagrid.reload();
		                 }
		            });
            	}else{
            		datagrid.reload();
            	}
            }else{
            	alert(confirmMessages);
            }
        }
		
		
		//快速查询
        function search() {
        	var commutetype = mini.get("commutetype").getValue();
        	addAnjianhao(commutetype)
        	var jailid = mini.get("jailid").getValue();
			datagrid.load({jailid:jailid ,commutetype:commutetype});
        }
        function onKeyEnter(e) {
            search();
        }
        
        function onPageChanged() {
        	myloading();
        }
        function myloading(){
        	var myform = new mini.Form("datagrid");
       		myform.loading();
        }
      	function unmask(){
      		myunmask();
      	}
        function myunmask() {
        	var myform = new mini.Form("datagrid");
       		myform.unmask();
        }
        
 </script>
</body>
</html>
