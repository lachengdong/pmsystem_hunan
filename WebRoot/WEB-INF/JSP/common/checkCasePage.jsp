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
	
	
	String provincecode = "";
	Object provincecodeObj = request.getAttribute("provincecode");
	if(null != provincecodeObj){
		provincecode = provincecodeObj.toString();
	}
	
	
	String departid = "";
	Object departidObj = request.getAttribute("departid");
	if(null != departidObj){
		departid = departidObj.toString();
	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>案件查看(减刑假释11)</title>
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
</head>
<body onload="search()">
    <div >
        <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
        	<input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
			<input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                   
                    	${topstr}
                    </td>
                    <td style="white-space:nowrap;">
                    	<%
	                		if(! provincecode.equals(departid)){
	                	%>
							第<input class="mini-combobox" style="width:50px;" id="int6"  name="int6" textField="text" valueField="id" data="batchData" 
								value="${batchid}" showNullItem="true" allowInput="false" onvaluechanged="search();"/>
							批次
							<!--  
							 请选择批次<input class="mini-spinner" id="int6"  name="int6"  style="width:40px;" minValue="1" maxValue="60" value="${batchid}" onvaluechanged="search();"　/>
							-->
                        <%
	                		}
	                	%>
	                	
                    	${topsearch}
                    	
	                	<%
	                		if(StringNumberUtil.notEmpty(topsearch)){
	                	%>
	                			<a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">查询</a>
	                	<%
	                		}
	                	%>
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
         
	    <div id="datagrid1" allowMoveColumn="false" allowCellEdit="true" style="width:100%;height:100%;" class="mini-datagrid"   
	        url="<%=url%>"  idField="" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  
	        onshowrowdetail="onShowRowDetail" allowAlternating="true"  sizeList="[10,20,50,100]" pageSize="20">
	        <div property="columns">
	        	<div type="checkcolumn" width="15"></div>
	        	<div type="indexcolumn" width="15" headerAlign="center">序号</div>
	        	<div type="expandcolumn" >留痕记录</div>
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
	                 <div field="processstate" width="60" headerAlign="center" align="center" allowSort="true">办案进程</div>
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
	    
	    <div id="detailGrid_Form" style="display:none;">
	        <div id="employee_grid" class="mini-datagrid" showPager="false" showVGridLines="false" showHGridLines="false"
	        style="width:100%;height:260px;" url="jxjs/selectFlowForScar.json?1=1&tflag=1&flowdefid=${flowdefid}">
	            <div property="columns">
	                <div field="optime" width="26" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">审批时间
	                    <input property="editor" class="mini-textbox" />
	                </div>    
	                <!--              
	                <div field="duty" width="20" align="center" headerAlign="center" allowSort="true">职务</div>  
	                -->          
	                <div field="operateip" width="20" align="center" headerAlign="center" allowSort="true">客户端IP</div>
	                <div field="opname" width="20" align="center" headerAlign="center" allowSort="true">审批人</div>
	                <div field="opinion" width="20" align="center" headerAlign="center" allowSort="true">评判意见 </div>                                    
	                <div field="reason" align="left" headerAlign="center" allowSort="true">评判理由</div>                                    
	            </div>
	        </div>    
	    </div>
	    
	</div> <!-- end of detailGrid_Form -->
	
	<div style="height:0;"> <!-- display:none;  height:200px;-->
		<form id="hidden_formid" method="post" url="">
			<input type="hidden" id="hidden_ids" name="ids" value=""/>
			<input type="hidden" id="hidden_furl" name="furl" value=""/>
		</form>
	</div>
	
  </div>  <!-- end of div_two -->
    <script type="text/javascript">
    	var batchData = [{text:'1',id:1},{text:'2',id:2},{text:'3',id:3},{text:'4',id:4},{text:'5',id:5},{text:'6',id:6},
                     	{text:'7',id:7},{text:'8',id:8},{text:'9',id:9},{text:'10',id:10},{text:'11',id:11},{text:'12',id:12}];
    
       	mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        
        //grid.sortBy("text6", "asc");
        
        //全程留痕
       	var employee_grid = mini.get("employee_grid");
        var detailGrid_Form = document.getElementById("detailGrid_Form");
        function onShowRowDetail(e) {
            var grid = e.sender;
            var row = e.record;
            employee_grid.on("drawcell", function (e) {
           		e.cellStyle = "background:#fceee2";
            });
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailGrid_Form);
            detailGrid_Form.style.display = "block";
            employee_grid.load({flowdraftid: row.flowdraftid,applyid: row.crimid });
            
            
        }
        
        var path = '<%=path%>';
        
        function onActionRenderer() {
        	var s = '${middlestr}';
	     	return s;
        }
        function search(){
        	var topsearchkey = '${topsearchkey}';
        	topsearchkey = topsearchkey;
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
        	
        	if(mini.get("int6")){
        		data["int6"] =encodeURI( mini.get("int6").getValue() );
        	}
            grid.load(data);
        };
        
        function onKeyEnter(e) {
            search();
        };
        
        function onValueChanged(e) {
            search();
        };
        
         grid.on("drawcell",function(e){
      		 var record = e.record,
		     column = e.column,
		     field = e.field,
		     value = e.value;
		     var nodeid = record.nodeid;
		     //alert(field);
		     //alert(nodeid);
		     //给帐号列，增加背景色
             if (field == "processstate" ) {
             	//alert(nodeid);
             	if(nodeid=='0'){
                	e.cellStyle = "background:#c4f1da";
                }else if(nodeid=='N0001'){
                	e.cellStyle = "background:#c4c9f1";
                }else if(nodeid=='N0002'){
                	e.cellStyle = "background:#a8d8ee";
                }else if(nodeid=='N0002_01'){
                	e.cellStyle = "background:#b6d7fb";
                }else if(nodeid=='N0003'){
                    e.cellStyle = "background:#adc4f8"; 
                }else if(nodeid=='N0003_1'){
                	e.cellStyle = "background:#DDA0DD";
                }else if(nodeid=='N0004'){
                	e.cellStyle = "background:#dfe4e0";
                }else if(nodeid=='N0005'){
                	e.cellStyle = "background:#f0f7cb";
                }else if(nodeid=='N0006'){
                	e.cellStyle = "background:#228B22";
                }else if(nodeid=='N0006_1'){
                	e.cellStyle = "background:#BC8F8F";
                }else if(nodeid=='N0007'){
                	e.cellStyle = "background:#808000";
                }else if(nodeid=='N0008'){
                	e.cellStyle = "background:#8FBC8F";
                }else if(nodeid=='N0009'){
                	e.cellStyle = "background:#90EE90";
                }else if(nodeid=='N0010'){
                	e.cellStyle = "background:#6495ED";
                }else if(nodeid=='1'){
                	e.cellStyle = "background:#f9d6cd";
                }else if(nodeid=='-1'){
                	e.cellStyle = "background:#fde8c7";
                }
             }
        });
        
         // 刷新本页面
		function refreshPage(){
			location.reload();
		};
		
	 //获取当前批次
	 /*
	 $(document).ready( 
		function getCurrentBatch(){  
			$.ajax({
	               url: "getCurrentBatch.json?1=1",
	               type: "post",
	               async: false,
	               success: function (text) {
	                   mini.get("batch").setValue(text);
	                   grid.reload();
	               },
	               error: function (jqXHR, textStatus, errorThrown) {
	                   alert(jqXHR.responseText);
	               }
	           });
		
		});
	 */
		
    </script>
    <script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
    <!--  
    <script src="<%=path%>/scripts/expandjs.js" type="text/javascript"></script>
    -->
</body>
</html>