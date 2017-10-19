<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sinog2c.model.system.SystemResource"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
	String path = request.getContextPath();
	
	SystemResource grid1 = null;
	List<SystemResource> grid1ColList = new ArrayList<SystemResource>();
	String flowdefid = "";
	//
	Object grid1_attr = request.getAttribute("grid1");
	Object grid1ColList_attr = request.getAttribute("grid1ColList");
	//
	if(grid1_attr instanceof SystemResource){
		grid1 = (SystemResource)grid1_attr;
	}
	if(grid1ColList_attr instanceof List<?>){
		grid1ColList = (List<SystemResource>)grid1ColList_attr;
	}
	//
	Object flowdefidObj = request.getAttribute("flowdefid");
	if(null != flowdefidObj){
		flowdefid = flowdefidObj.toString();
	}
	//
	String topsearch = "";
	Object topsearchObj = request.getAttribute("topsearch");
	if(null != topsearchObj){
		topsearch = topsearchObj.toString();
	}
	
	String middlestr = "";
	Object middlestrObj = request.getAttribute("middlestr");
	if(null != middlestrObj){
		middlestr = middlestrObj.toString();
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
   
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
    </style>
    <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
   </script>
   
</head>
<body>
    <div>
        <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
        	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
			<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
			<input id="currnodeid" name="currnodeid" class="mini-hidden" value="${currnodeid}"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                    	<!-- 
                    	案件号：【（<input id="curyear" name="curyear"  class="mini-textbox"  value="${curyear}"
                						style="width:40px;text-align:center"/>）${shortname}
                		<input id="casetype" class="mini-combobox" valueField="id" textField="text"
                			url="<%=path%>/getCodeByCode.json?1=1&codeType=GK7799&pcodeid=GK7799&scearch=${scearch}" style="width:80px;" value="jy"/>
                			
    				 	 
	    				 	<input id="provincecasechg" class="mini-combobox" valueField="id" textField="text" 
	    				 		url="<%=path%>/getCodeByCode.json?1=1&codeType=GK8899&pcodeid=GK8899" style="width:50px;" />
    				 	第
                		<input id="casenumber" name="casenumber"  class="mini-textbox"  value="${casenumber}" style="width:40px;text-align:center"/> 号】
                		-->
                    </td>
                    
                   
                    <td style="white-space:nowrap;">
                    	${topsearch}
                    	${topstr}
	                	<!-- 操作说明 -->
		                <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a> 
                    </td>
                    
                </tr>
            </table>           
        </div>
    </div>
         

    <div class="mini-fit" id="div_two">
    	<%--
        	// 根据配置的Grid以及列进行拼接
         --%>
         <%
         if(null != grid1){
        	 //
        	 String tempid = grid1.getFormid(); //表单ID;
        	 String solutionid = grid1.getQuerysql(); //查询方案ID
        	 String url = "";
        	 //
			 StringBuffer urlSb = new StringBuffer();
        	 if(StringNumberUtil.isEmpty(grid1.getSrurl())){
        	 	urlSb.append(path).append("/getPublicListData.json?1=1");
        	 }else{
        	 	urlSb.append(path).append("/").append(grid1.getSrurl());
        	 }
        	 
        	 //从菜单的参数中获取参数，但是如果Grid中有相应参数，则以Grid为准
			 Object paramMapObject = request.getAttribute("paramMap");
			 if(null != paramMapObject && paramMapObject instanceof HashMap){
				 Map paramMap = (HashMap)paramMapObject;
				 if(StringNumberUtil.notEmpty(tempid)&&paramMap.containsKey("tempid")){
				 	paramMap.remove("tempid");
				 }
				 if(StringNumberUtil.notEmpty(solutionid)&&paramMap.containsKey("solutionid")){
				 	paramMap.remove("solutionid");
				 }
				 if(urlSb.indexOf("?") == -1){
				 	urlSb.append("?1=1");
	        	 }
	        	 
				 Set<String> set = paramMap.keySet();
				 for(String key : set){
					 Object valueObj = paramMap.get(key);
					 if(StringNumberUtil.notEmpty(valueObj)){
						 String value = valueObj.toString();
						 urlSb.append("&").append(key).append("=").append(value);
					 }
				 }
			 }
        	 
        	 //
        	 if(StringNumberUtil.notEmpty(tempid) && urlSb.indexOf("tempid")==-1){
        	 	urlSb.append("&tempid=").append(tempid);
        	 }
        	 if(StringNumberUtil.notEmpty(solutionid) && urlSb.indexOf("solutionid")==-1){
        	 	urlSb.append("&solutionid=").append(solutionid);
        	 }
        	 if(StringNumberUtil.notEmpty(flowdefid) && urlSb.indexOf("flowdefid") == -1){
        	 	urlSb.append("&flowdefid=").append(flowdefid);
        	 }
        	 url = urlSb.toString();
         %>
         
	    <div id="datagrid" allowMoveColumn="false" allowCellEdit="true" style="width:100%;height:100%;" class="mini-datagrid"   
	        url="<%=url%>"  idField="" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  
	        onshowrowdetail="onShowRowDetail" allowAlternating="true"  sizeList="[10,20,50,100]" pageSize="20">
	        <div property="columns">
	        	<div type="checkcolumn" width="15"></div>
	        	<div type="indexcolumn" width="15">序号</div>
	        	<%
			    	 //
			    	 Iterator<SystemResource> iteratorC = grid1ColList.iterator();
			    	 while(iteratorC.hasNext()){
			    		 SystemResource col = iteratorC.next();
			    		 //
			    		 String name = col.getName();
			    		 String field = col.getShowsite();
			    		 Integer width = col.getWindowwidth();
			    		 String renderer = col.getSrurl();
			    		 String additionInfo = col.getText1();
			    		 //
			    		 
			    		 //
			    		 if(StringNumberUtil.isEmpty(field)){
			    			 continue;
			    		 } else {
			    			 field = field.trim().toLowerCase();
			    		 }
			    		 if(StringNumberUtil.isEmpty(name)){
			    			 name = "";
			    		 }
			    		 if(null == width || width.intValue() < 1){
			    			 width = 30;
			    		 }
		
			    		 String rendererStr = "";
			    		 if(!StringNumberUtil.isEmpty(renderer)){
			    			 rendererStr = " renderer=\"" + renderer +"\" ";
			    		 }
			    		 
			    		 String allowSort = "";
			    		 if(StringNumberUtil.isEmpty(additionInfo)){
			    			 additionInfo = " allowSort=true ";
			    		 }else if(additionInfo.indexOf("allowSort")< 0){
			    			 additionInfo += " allowSort=true ";
			    		 }
			    		 // 列
		         %>
		              <div field="<%=field %>" width="<%=width %>" headerAlign="center" align="center" <%=additionInfo %> <%=rendererStr %> ><%=name %></div>    
		         <%
		         	}
		         %>
	            <%
		         	if(StringNumberUtil.notEmpty(middlestr)){	
		         %>
		              <div name="action" width="40" headerAlign="center" align="center"  allowSort="false" renderer="onActionRenderer">操作</div>  
		         <%
		         	}
		         %>
		          </div> <!-- end of columns -->
		        </div> <!-- end of mini-datagrid -->
		         <%
		         }
		         %>
	    
	</div> <!-- end of detailGrid_Form -->
	
	<div style="height:0;"> <!-- display:none;  height:200px;-->
		<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
		<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
		<textarea id="RMVIEWER_DATA" style="display:none"></textarea>
	</div>
	
  </div>  <!-- end of div_two -->
  
  <script type="text/javascript">
		document.all("MyViewer").Init(window, document,600);
   </script>
   
    <script type="text/javascript">
       	mini.parse();
        var grid = mini.get("datagrid");
        grid.load();
        
        //grid.sortBy("text6", "asc");
        //mini.get("casetype").select(0);
        //mini.get("provincecasechg").select(0);

        var path = '<%=path%>';
        
        function onActionRenderer() {
        	var s = '${middlestr}';
	     	return s;
        }
        function search(){
        	var topsearchkey = '${topsearchkey}';
        	if(topsearchkey){
        		var topsearchkeyArr = topsearchkey.split(',');
        	}
        	var data = {};
        	for(var i=0,l=topsearchkeyArr.length; i<l ; i++){
        		var key = topsearchkeyArr[i]
        		 var value = mini.get(key).getValue();
        		 if(value){
        			 data[key] =encodeURI( value );
            	 }
        	}
            grid.load(data);
        };
        
        function onKeyEnter(e) {
            search();
        };
        
        function onValueChanged(e) {
            search();
        };
        
         // 刷新本页面
		function refreshPage(){
			location.reload();
		};
		
		
		//动态显示案件号
		/*
        function addAnjianhao(){
       		var curyear = mini.get("curyear").getValue();
       		var flowdefid = mini.get("flowdefid").getValue();
       		var url = "/flow/getSJMaxCaseNumForGD.json";
       		$.ajax({
		         url: url, 
		         data: {curyear:curyear, flowdefid:flowdefid},
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
		*/
        
        var confirmMessages = "请至少选中一条记录！";
		var batchAlertOne = "确定对下列人员进行立案吗？";
		 //省局立案
        function provinceLian(menuid){
        	var row = grid.getSelected();
			if(row){
				var mess = "确定对服刑人员【"+ row.name +"】进行立案？";
				if(confirm(mess)){
					
					var rows = [];
					rows[0]=row;
					operateLianData(menuid, rows);
					
            	}else{
            		grid.reload();
            	}
			}else{
				alert(confirmMessage);
			}
        }
        
		//批量立案
        function batchLian(menuid){
        	var rows = grid.getSelecteds();
            if (rows && rows.length>0) {
            	if(confirm(batchAlertOne)){  
            		operateLianData(menuid, rows);
            	}else{
            		grid.reload();
            	}
            }else{
            	alert(confirmMessages);
            }
        }
		
		
		function operateLianData(menuid, rows){
			
			var flowdefid = mini.get("flowdefid").getValue();
    		
    		var approveperson = null;
			var approvepersonObj = mini.get("approveperson");
			if(approvepersonObj){
				approveperson = approvepersonObj.getValue();
				if(!approveperson){
           			alert("请先选择办案人！");
           			return;
            	}
			}
			
    		var flowdraftidArr = [];
            for (var i = 0, l = rows.length; i < l; i++) {
            	var row = rows[i];
            	flowdraftidArr.push(row.flowdraftid);
            }
            var flowdraftids = flowdraftidArr.join(',');
			
             $.ajax({
                 url:  "<%=path%>/flow/provinceLiAnForGD.json",
		         data: {flowdraftids:flowdraftids, flowdefid:flowdefid, approveperson:approveperson},
		         type: "POST",
		         dataType:"json",
		         async:false,
		         cache: false,
                 success: function (text) {
                 	var obj = eval(text);
                 	if(obj=='-1'){
                 		alert("操作失败!");
                 	}else{
                 		batchFlowOperateWithFlowdraftids(flowdraftids, menuid, 'yes');
	         			//var b = addAnjianhao();
	         		}
                	grid.reload();
                 },
                 error: function (jqXHR, textStatus, errorThrown) {
                   alert("操作失败!");
                   grid.reload();
                 }
            });
			
		}
		
    </script>
    <script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
</body>
</html>