<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
	String path = request.getContextPath();
	String flowdefid = request.getAttribute("flowdefid")==null?"":request.getAttribute("flowdefid").toString();
	String tempid = request.getAttribute("tempid")==null?"":request.getAttribute("tempid").toString();
	
	//
	String topsearch = "";
	Object topsearchObj = request.getAttribute("topsearch");
	if(null != topsearchObj){
		topsearch = topsearchObj.toString();
	}
	
	//
	String unlockbtn = ""; //解锁铵钮
	Object unlockbtnObj = request.getAttribute("unlockbtn");
	if(null != unlockbtnObj){
		unlockbtn = unlockbtnObj.toString().trim();
	}
	
%>
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
   		<script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
		<title></title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }      
    </style>
   
</head>
 <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
</script>
<body>
	<div id="form1"  class="mini-splitter" vertical="true" style="display: none;" >
	</div>
	<div id="datagrid" style="width:100%;height:100%;">
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
       		<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
			<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
			<input id="currnodeid" name="currnodeid" class="mini-hidden" value="${currnodeid}"/>
       		<input class="mini-hidden" id="sealOrRevoke" name="sealOrRevoke" value=""/> <!-- 签章或撤销签章 	临时存放 -->
            <table >
               <tr>
               <td style="width:100%;">
                	${topstr}
                </td>
                <td style="white-space:nowrap;">
                	<input name="jianqu" id="jianqu" class="mini-combobox" style="width:120px;" valueField="ORGID" textField="NAME"  emptyText="请选择部门" onvaluechanged="onValueChanged"
                            url="getDepartList.action?1=1&qtype=jianqu" required="false" showNullItem="true" nullItemText="请选择部门"/>
                    <input class="mini-textbox"  id="key"  name="key"  style="width:100px;"   emptyText="编号/姓名/拼音"   onenter="onKeyEnter" />
                   <%
                   		if(StringNumberUtil.isEmpty(tempid)){
                    %>
                		<!-- <input name="tempid" id="tempid" class="mini-combobox" style="width:120px;" valueField="templetid" textField="templetname"  
                				emptyText="请选择审批类型" onvaluechanged="onValueChanged"
                            	url="getShenPiTempletIdList.action?1=1&nodeid=${nodeid}" required="false" showNullItem="true" nullItemText="请选择审批类型"/> -->
                    <%
                    	}
                     %>
                     <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">查询</a>
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help"  onclick="chakanshuoming('9924')"></a>
		 			
		 			<%
                		if(StringNumberUtil.notEmpty(unlockbtn)&& "1".equals(unlockbtn)){
                	%>
                		<span class="separator"></span>
	                	<a class="mini-button" iconCls="icon-unlock" plain="true" onclick="unlockBtn();">去锁</a>
                	<%
                		}
                	%>
                	
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit" id="div_two">
	    <div id="datagrid1" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	    	url="getPublicTodoDataList.action?1=1&applyid=${applyid}&flowdefid=${flowdefid}&notinnodeid=${notinnodeid}"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="50"  showLoading="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="20"></div> 
	        	<div type="indexcolumn" width="20" headerAlign="center" align="center" allowSort="true">序号</div>
	    		<div field="conent" headerAlign="center" align="center" allowSort="true" width="130">文书简介</div>
	    		<div field="flowdraftid" headerAlign="center" align="center" allowSort="true" width="130" visible="false">草稿id</div>
	    		<div field="applyid" headerAlign="center" align="center" allowSort="true" width="50" >编号</div>
	    		<div field="applyname" headerAlign="center" align="center" allowSort="true" width="50">姓名</div>
	    		<div field="orgname" headerAlign="center"  align="center" allowSort="true"width="50">所在监区</div>
	    		<div field="optime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="50">提交时间</div>
	    		<div field="opname" headerAlign="center"  align="center" allowSort="true" width="50px">提交人</div>
	    		<div field="state" headerAlign="center"  align="center" allowSort="true"width="50" renderer="onStatusRenderer">审批状态</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="50px">操作</div>  
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
		
		<form id="hidden_formid" method="post" url="">
			<input type="hidden" id="hidden_ids" name="ids" value=""/>
			<input type="hidden" id="hidden_furl" name="furl" value=""/>
		</form>		
	</div>
	
	</div>
    <script type="text/javascript"> 
    	mini.parse();
    	var grid = mini.get("datagrid1");
    	grid.sortBy("applyid","desc");
        grid.load();
        
        var path = '<%=path%>';
        
      function onActionRenderer() {
         	//var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
         	var s = '${middlestr}';
    	    return s;
        }
        
        
	        function search(){
		       	var data = {};
		       	var jianqu = mini.get('jianqu').getValue();
		       	if(jianqu){
		       		data['jianqu'] = jianqu;
		       	}
		       	//
		       	var key = mini.get('key').getValue();
		       	if(key){
		       		data['key'] = key;
		       	}
		       	//
		       	var tempid = '';
		       	var tempidObj = mini.get('tempid');
		       	if(tempidObj){
		       		tempid = tempidObj.getValue();
		       	}
		       	if(tempid){
		       		data['tempid'] = tempid;
		       	}
		        grid.load(data);
		    };
	    	//
	    	function onKeyEnter(){
	        	search();
	        }
	        //
	    	function onValueChanged(){
	        	search();
	        }
	        
	    	grid.on("drawcell",function(e){
	      		 var record = e.record,
			     column = e.column,
			     field = e.field,
			     value = e.value;
	        });
</script>
 <script language="JavaScript" src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
</body>
</html>