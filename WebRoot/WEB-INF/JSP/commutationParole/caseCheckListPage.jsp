  <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>案件检查</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }     
    </style>
    <script type="text/javascript">
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
		
		function chooseAll(){
			var menuid = mini.get("menuid").getValue();
        	var rows = grid.getSelecteds();
        	var tempid="JXJS_JXJSSHB";
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var row = rows[i];
                        var flowid = row.flowid;
                        var lastnodeid = row.lastnodeid;
                        if(flowid==undefined){
        					flowid = "flowidnull";
        				}
                        ids.push(row.applyid+"@"+row.areaid+"@"+row.flowdraftid+"@"+flowid+"@"+lastnodeid);
                    }
                    var id = ids.join(',');
                    var url = "toCaseCheckTab.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid;
                    url = encodeURI(encodeURI(url));
                    self.location.href=url;
                }
            } else {
                alert( "请至少选中一条记录！");
            }
		}
		
		
		function chooseOne() {
			var row = grid.getSelected();
			var menuid = $("#menuid").val();
			var crimid = row.applyid;
			//alert(menuid);return;
			var orgid = row.areaid;
			var flowdraftid = row.flowdraftid;
			var flowid = row.flowid;
			var lastnodeid = row.lastnodeid;
        	var tempid="JXJS_JXJSSHB";
        	if(flowid==undefined){
        		flowid = "flowidnull";
        	}
        	
        	var id = crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid;
        	var url = "toCaseCheckTab.action?1=1&tempid="+tempid+"&id="+id+"&menuid="+menuid;
			self.location.href=url;
        }
	</script>
</head>
<body onload="init('${menuid}');">
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
	   <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
	   <input id="middlebtns" name="middlebtns" class = "mini-hidden"/>
            <table>
               <tr>
	                <td style="width:100%;">
	                	<a class="mini-button" id="11296"  iconCls="" plain="true" onclick="chooseAll();">批量处理</a>
	                </td>
	                <td style="white-space:nowrap;">
	                	<!-- <input id="nodeid" class="mini-combobox" valueField="id" textField="text"  emptyText="进程过滤" showNullItem="true" 
				        	nullItemText="--全部--" data="typeid" style="width:150px;"  enabled="true"
			            	onvaluechanged="search" />
	                	<input id="approvestype" class="mini-combobox" valueField="id" textField="text"  emptyText="处理状态" showNullItem="true" 
				        	nullItemText="--全部--" data="approvestype" style="width:150px;"  enabled="true"
			            	onvaluechanged="search" />	 -->	            	
	                    <input class="mini-textbox" id="key" class="mini-textbox" emptyText="罪犯姓名、罪犯编号"  onenter="onKeyEnter"/>
	                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
			 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid }')"></a>
			 		</td>
               </tr>
		</table>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	    	url="getCaseCheckList.action"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="10"></div> 
				<div field="anjianhao" width="50px" headerAlign="center" allowSort="true" align="center" >
					案件号
				</div>  
	           	<div field="applyid" width="30px" headerAlign="center" allowSort="true" align="center" >
					罪犯编号
				</div> 
			    <div field="applyname" width="30px" headerAlign="center" allowSort="true" align="center" >
					罪犯姓名
				</div>  
			    <div field="originalyear" width="30px" headerAlign="center" dateFormat="yyyy-MM-dd"	allowSort="true" align="center" >
					原判刑期
				</div>  
			    <div field="commenttext" width="60px" headerAlign="center" align="center">
					监狱意见
				</div>
				<div field="fuyiflag" width="60px" headerAlign="center" align="center">
					检查意见
				</div>
			    <div field="approvestate" width="30px" renderer="onTypeRenderer" headerAlign="center" align="center">
					处理状态
				</div>				
                <div name="action" width="30px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
        var typeid = [{id:'N0001', text: '分监区审批中'},{id:'N0002', text: '监区审批中'},{id:'N0003', text: '评审会审批中'},{id:'N0004', text: '监狱长审批中'},{id:'1', text:'已通过'}];
        var approvestype = [{id:'22', text: '未处理'},{id:'0', text: '已查看'},{id:'1', text: '已处理'}];
    	mini.parse();
		var grid = mini.get("datagrid");
		grid.load();

		grid.on("drawcell", function (e) {
            var record = e.record,
        	column = e.column,
       		field = e.field,
       		value = e.value;
       		var approvestate = record.approvestate;
            if (approvestate=='1') {
               	e.cellStyle = "background:#48d1cc";
            }
        });		
        
		function onActionRenderer(e) {
	         var s ='';
				s += " <a class=\"Edit_Button\" href=\"javascript:chooseOne(0);\" >处理</a>&nbsp;";
	     	return s;
	    }
	    
	    function search() {
	        //var nodeid = mini.get("nodeid").getValue();
	        //var approvestype = mini.get("approvestype").getValue();
	        //, nodeid : nodeid, approvestype : approvestype 
			var key = mini.get("key").getValue();
			grid.load({ key : key});
		}
		
		function onKeyEnter(e) {
		   search();
		}
		
	    function onTypeRenderer(e){
    	    if (e.value == '0') {
    		    return "未处理";
            } else if(e.value == '1') {
        	    return "已处理";
            }
            return e.value;	
    	}		
    </script>
</body>
</html>

        