<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
	String path = request.getContextPath();
	
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
    <title>案件查看</title>
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
<body>
    <div >
        <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                    	${topstr}
                    </td>
                    <td style="white-space:nowrap;">
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
    <div class="mini-fit">
	    <div id="datagrid1" allowMoveColumn="false" allowCellEdit="true" style="width:100%;height:100%;" class="mini-datagrid"   
	        url="<%=path%>/getPublicListData.json?1=1&flowdefid=${flowdefid}&solutionid=${solutionid}" 
		     idField="" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  onshowrowdetail="onShowRowDetail"
		      allowAlternating="true"  sizeList="[10,20,50,100]" pageSize="20">
	        <div property="columns">
	        	<div type="checkcolumn" width="15"></div>
	        	<div type="indexcolumn" width="15"></div>
	        	<!--  
	        	<div type="expandcolumn" >筛查记录</div>
	        	-->
	            <div field="crimid" width="40" headerAlign="center" align="center" allowSort="true"  renderer="onCriminalRenderer">罪犯编号</div>  
	            <div field="name" width="40" headerAlign="center" align="center" allowSort="false">姓名</div>   
	            <div field="orgname" width="40" headerAlign="center" align="center" allowSort="false">所属监区</div> 
	            <div field="original" width="40" headerAlign="center" align="center" allowSort="false">原判刑期</div> 
	            <div field="sentencestime" width="40" headerAlign="center" align="center" renderer="onDateRenderer" allowSort="false">刑期起日</div> 
	            <div field="executiondate" width="40" headerAlign="center" align="center" renderer="onDateRenderer" allowSort="false">执行日期</div> 
	            <div field="nowpunishmentyear" width="40" headerAlign="center" align="center" allowSort="false">现刑期</div> 
	            <div field="predate" width="40" headerAlign="center" align="center" renderer="onDateRenderer" allowSort="false">上次裁减日期</div> 
	            <div field="jianggeqi" width="40" headerAlign="center" align="center" allowSort="false">减刑间隔期</div> 
	            <div field="crimtype" width="40" headerAlign="center" align="center" allowSort="false">罪犯类型</div> 
	            <div field="kaohefen" width="40" headerAlign="center" align="center" allowSort="false">考核分</div> 
		        <div name="action" width="60" headerAlign="center" align="center"  allowSort="false" renderer="onActionRenderer">操作</div>
	        </div>
	    </div>
	    <div id="detailGrid_Form" style="display:none;">
	        <div id="employee_grid" class="mini-datagrid" showPager="false" showVGridLines="false" showHGridLines="false"
	        style="width:100%;height:260px;" url="jxjs/selectFlowForScar.json">
	            <div property="columns">
	                <div field="optime" width="26" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">审批时间
	                    <input property="editor" class="mini-textbox" />
	                </div>    
	                <div field="opname" width="20" align="center" headerAlign="center" allowSort="true">审批人</div>
	                <div field="opinion" width="20" align="center" headerAlign="center" allowSort="true">评判意见 </div>                                    
	                <div field="reason" align="left" headerAlign="center" allowSort="true">评判理由</div>                                    
	            </div>
	        </div>    
	    </div>
	</div>
  
    <script type="text/javascript">
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
        
        function onActionRenderer(e) {
	       var record = e.record;
	       var cstatus = record.cstatus;
	       var s = "";
	   	   if(cstatus == 0) s = '<a class="Edit_Button" href="javascript:dealSpecialCase();" >特案办理</a>';
	   	   //s += '&nbsp;&nbsp;&nbsp;&nbsp;<a class="Edit_Button" href="javascript:viewScreeningExcuse();" >筛查依据</a>';
	       return s;
	   	}
	   	
	   	function dealSpecialCase(){
	   		var rows = grid.getSelecteds();
	    	if(rows.length > 0){
	    		if(confirm("确定操作选中记录吗？")){
	    			var crimidArr = [];
		            for (var i = 0, l = rows.length; i < l; i++){
		               var row = rows[i];
		               crimidArr.push('\''+row.crimid+'\'');
		            }
					var crimids = crimidArr.join(',');
					var url = path + '/dealSpecialCase.json?1=1';
		    		//
		    		$.ajax({
		                url: url,
		                data: {crimids:crimids},
		                type: "post",
		                success: function (message){
		                	message = mini.decode(message);
		                	var info = message["info"];
			        	    var status = message["status"];
			        	    if(info){
			        	    	alert(info);
			        	    }else{
			        	    	if(status==1){
			        	    		alert("操作成功！");
			        	    	}else{
			        	    		alert("操作失败！");
			        	    	}
			        	    }
			        	    //
			        	    grid.reload();
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                	alert("操作失败！");
		                }
		            });
	    		}
		        //    
	    	}else{
	    		alert("请选择记录！");
	    		return false;
	    	}
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
                	e.cellStyle = "background:yellow";
                }else if(nodeid=='N0001'){
                	e.cellStyle = "background:yellowgreen";
                }else if(nodeid=='N0002'){
                	e.cellStyle = "background:#DEB887";
                }else if(nodeid=='N0003'){
                    e.cellStyle = "background:#00FFFF"; 
                }else if(nodeid=='N0004'){
                	e.cellStyle = "background:#FFD700";
                }else if(nodeid=='N0005'){
                	e.cellStyle = "background:#FFEC8B";
                }else if(nodeid=='1'){
                	e.cellStyle = "background:palegreen";
                }else if(nodeid=='-1'){
                	e.cellStyle = "background:palered";
                }
             }
        });
        
         // 刷新本页面
		function refreshPage(){
			location.reload();
		};
		
		function viewScreeningExcuse() {
			var row = grid.getSelected();
			var url = path + "/assessmentDate/viewscreeningexcuse.action?1=1&crimid="+ row.crimid;
			var win = mini.open( {
				url : url,
				title : "筛查依据情况",
				width : 600,
				height : 490,
				showMaxButton : true,
				allowResize : false,
				onload : function(){
					
				},
				ondestroy : function(action) {
					//	grid.reload();
				}
			});
		}  
		
		//渲染日期
	function onDateRenderer(e) {
		if(e && e.value){
			return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
		}
	    return "";
	}

    </script>
    <script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
    <!--  
    <script src="<%=path%>/scripts/expandjs.js" type="text/javascript"></script>
    -->
</body>
</html>