<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>省局减刑假释案件查看</title>
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
<body onload="changed()">
	<div class="mini-splitter" style="width:100%;height:100%;">
		<div size="15%" showCollapseButton="true">
	        <div class="mini-toolbar" style="border-top:0;border-left:0px;border-right:0;padding:2px;">
	             <input id="key" class="mini-textbox" emptyText="请输入罪犯编号、姓名、拼音"  id="key" onenter="onKeyEnter" style="width:100px;"  enabled="true"/>
	             <a class="mini-button" onclick="search()" plain="true">查询</a>      
	        </div>
	        <table style="width:100%;">
	        		<tr height="30">
	                	<td>
	                		<input id="idids" class="mini-combobox" data="data" valueField="id" textField="name"  value="1"
				            	style="width:150px;"  enabled="true" onvaluechanged="search"/>
	                	</td>
	                </tr>
	                <tr id="sjid" height="30">
	                	<td>
	                        <input id="sj" class="mini-combobox" data="caseid"  emptyText="省局案件进程过滤" showNullItem="true" valueField="snodeid" textField="text3" 
				            	nullItemText="--全部--" url="jxjs/ajaxGetProcess.json?flowdefid=other_sjjxjssp" style="width:150px;" onvaluechanged="onKeyEnter"/>
	                	</td>
	                </tr>
	                 <tr id="danwei" height="30">
	                	<td>
	                		<input id="depid" class="mini-combobox" url="getDepartId.action" valueField="orgid" textField="name"  emptyText="监狱过滤" showNullItem="true" 
				            	nullItemText="--全部--" url="" style="width:150px;"  enabled="true" onvaluechanged="onKeyEnter"/>
	                	</td>
	                </tr>
	                <tr id="jyid" height="30">
	                	<td>
	                        <input id="jy" class="mini-combobox" data="caseid"  emptyText="监狱案件进程过滤" showNullItem="true" valueField="snodeid" textField="text3" 
				            	nullItemText="--全部--" url="jxjs/ajaxGetProcess.json?flowdefid=other_zfjyjxjssp&type=sj" style="width:150px;" onvaluechanged="onKeyEnter"/>
	                	</td>
	                </tr>
	                <tr height="30">
	                    <td style="white-space:nowrap;">
	                      	<input id="lianstarttime" class="mini-datepicker" emptyText="立案开始时间" style="width:150px;" onvaluechanged="onKeyEnter"/>
				        </td>
	                </tr>
	                <tr height="30">
	                	<td>
	                		<input id="lianendtime" class="mini-datepicker" emptyText="立案结束时间"  style="width:150px;" onvaluechanged="onKeyEnter"/>
	                	</td>
	                </tr>
	                <tr height="30">
	                	<td>
				           	<input id="caidingstarttime" class="mini-datepicker"  emptyText="裁定开始时间" style="width:150px;" onvaluechanged="onKeyEnter"/>
				        </td>
	                </tr>
	               <tr>
	               		<td>
	               			<input id="caidingendtime" class="mini-datepicker" emptyText="裁定结束时间" style="width:150px;" onvaluechanged="onKeyEnter"/>
	               		</td>
	               </tr>
	                <tr height="30">
	                	<td>
				           	<input id="birthstarttime" class="mini-datepicker" emptyText="出生开始时间" style="width:150px;" onvaluechanged="onKeyEnter"/>
	                        <!--  <span class="separator"></span>                                                
	                                                                        原判刑期&nbsp;<input id="originalyear" class="mini-combobox" style="width:100px;" />
	                        -->
	                    </td>
	                </tr>
	                <tr height="30">
	               		<td>
	               			<input id="birthendtime" class="mini-datepicker" emptyText="出生结束时间" style="width:150px;" onvaluechanged="onKeyEnter"/>
	               		</td>
	               </tr>
	                <tr height="30">
	                	<td style="white-space:nowrap;" colspan="1">
	                        <input id="anjiantype" class="mini-combobox" emptyText="案件类型" showNullItem="true" data="typeid" style="width:150px;" 
	                        nullItemText="--全部--" onvaluechanged="onKeyEnter"/>
	                    </td>
	                </tr>
	                <tr height="30">
	                	<td>
	                    	<input id="punishmenttype" class="mini-combobox" emptyText="原判刑种" showNullItem="true" style="width:150px;" 
	                    	data="xingzhong" nullItemText="--全部--" onvaluechanged="onKeyEnter"/>
	                    </td>
	                </tr>
	            </table>    
        </div>
        <div showCollapseButton="true" >
		    <div class="mini-fit">
		    	<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" pageSize="20"
		         idField="jailid" multiSelect="true"  allowAlternating="true" url="getBWCaseDataInfo.action?1=1&type=sj&flowdefid=other_sjjxjssp" onshowrowdetail="onShowRowDetail">
			        <div property="columns">
			            <div type="indexcolumn">序号</div>
			            <div type="expandcolumn" width="50">留痕记录</div>
			            <div field="nianhao" width="40" headerAlign="center" align="center" allowSort="true">年号</div> 
	            		<div field="anjianhao" width="40" headerAlign="center" align="center" allowSort="true">案件号</div>
	            		<div field="anhao" width="120" headerAlign="center" align="center" allowSort="true">案号</div>
			            <div field="crimid" width="60" headerAlign="center" align="center" allowSort="true"  renderer="onActionRenderer">罪犯编号</div> 	            		    
			            <div field="name" width="120" headerAlign="center" align="center" allowSort="true">姓名</div>
			            <div field="crimclass3" width="40px"  headerAlign="center"  	allowSort="true" align="center" >罪犯类型</div> 
			            <div field="causeaction3" width="40"  headerAlign="center"  	allowSort="true" align="center" >罪名</div>   
		                <div field="originalyear3" width="40" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
		           	    <div field="sentencestime3" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div> 
		                <div field="courtchangeto3" width="40px"  headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">现刑期止日</div> 			             
			            <div field="jailname" width="80" headerAlign="center" align="center" allowSort="true">所属监狱</div> 
			            <div field="jincheng" width="80" headerAlign="center" align="left" allowSort="true">进程</div>
			        </div>
				</div>
				<!-- 留痕记录 -->
		        <div id="detailGrid_Form" style="display:none;">
			        <div id="employee_grid" class="mini-datagrid" showPager="false" showVGridLines="false" showHGridLines="false"
			        style="width:100%;height:260px;" url="jxjs/selectFlowForScar.json">
			            <div property="columns">
			                <div field="optime" width="26" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">审批时间
			                    <input property="editor" class="mini-textbox" />
			                </div>                
			                <div field="duty" width="20" align="center" headerAlign="center" allowSort="true">职务</div>            
			                <div field="opname" width="20" align="center" headerAlign="center" allowSort="true">审批人</div>
			                <div field="opinion" width="20" align="center" headerAlign="center" allowSort="true">评判意见 </div>                                    
			                <div field="reason" align="left" headerAlign="center" allowSort="true">评判理由</div>                                    
			            </div>
			        </div>    
	    		</div>
		  </div>
		</div>
	</div>
    <script type="text/javascript">
    	var data=[{id:1,name:'省监狱管理局'},{id:2,name:'下属监狱'}]
    	var typeid = [{id:0, text: '减刑'},{id:1, text:'假释'}];
    	var caseid = [{id:'0', text: '经办人办理中'},{id:'N0001', text: '分监区办理中'},{id:'N0002', text: '监区审批中'},{id:'N0003', text: '评审会审批中'},{id:'N0004', text: '监狱长审批中'},{id:'-1', text: '监狱审批未通过'},{id:'1', text:'监狱审批已通过'},
    	{id:'0_1', text: '省局经办人办理中'},{id:'N0002_1', text: '省局处室审查中'},{id:'N0003_1', text: '局长审批中'},{id:'-1_1', text: '局长审批未通过'},{id:'1_1', text:'局长审批已通过'}];
        var xingzhong = [{id:1, text: '有期'},{id:'9995', text:'无期'},{id:'9996', text:'死缓'}];
        mini.parse();
        var grid = mini.get("datagrid1");
		grid.load();
		
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
            employee_grid.load({flowdraftid: row.flowdraftid,applyid: row.crimid,flowdefid:row.flowdefid});
        }
        
		//var menuid = document.getElementById("menuid").value;alert(menuid);
        grid.on("drawcell",function(e){
        			 var record = e.record,
				     column = e.column,
				     field = e.field,
				     value = e.value;
				     var nodeid = record.nodeid;
				     //给帐号列，增加背景色
		             if (field == "jincheng" ) {
		             	if(nodeid=='N0003'){
		                	e.cellStyle = "background:#FFD700";
		                }else if(nodeid=='N0001'){
		                	e.cellStyle = "background:yellowgreen";
		                }else if(nodeid=='N0002'){
		                	e.cellStyle = "background:#DEB887";
		                }else if(nodeid=='N0004'){
		                	e.cellStyle = "background:#FFEC8B";
		                }else if(nodeid=='1'){
		                	e.cellStyle = "background:green";
		                }else if(nodeid=='0'){
		                	e.cellStyle = "background:blue";
		                }else if(nodeid=='-1'){
		                	e.cellStyle = "background:red";
		                }
		             }
        });
         function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s = ' <a class="Edit_Button" href="javascript:checkaip(\'' + uid + '\')" >'+record.crimid+'</a>&nbsp;&nbsp;';
            return s;
        }
        function checkaip(id){
            var row = grid.getSelected();
            //alert("flowid="+row.flowid+"&flowdraftid="+row.flowdraftid+"&flowdefid="+row.flowdefid);
            if (row) {
                var tempid = "";
                var jxstempid = "";
                var flowdefid = row.flowdefid;
                if(flowdefid == 'other_zfjyjxjssp'){
                	docid="003001";//档案
                	jyxdocid="004001";
                	tempid = "JXJS_JXJSSHB"; 
                	jxstempid = "jailcommutereport";
                }else{
                	docid="";
                	jysdocid="004001";
                	tempid = "JXJS_JXSH"; 
                	jxstempid = "jailcommutereport";
                }
            	var url="tocaseLookTabPage.action?1=1&menuid=10269&crimid="+row.crimid+"&flowid="+row.flowid+"&flowdraftid="+
            	row.flowdraftid+"&flowdefid="+flowdefid+"&tempid="+tempid+"&jxstempid="+jxstempid+"&docid="+docid+"&jysdocid="+jysdocid;
                mini.open({
                    url: url,
                    showMaxButton: true,
                    allowResize: false, 
                    title: "案件查看", width: 900, height: 550,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { };
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else {
                alert("请选中一条记录");
            }
        }
        function search() {
        	changed();
        	var lianstarttime   = mini.formatDate (mini.get("lianstarttime").getValue(), 'yyyy-MM-dd' ); 
			var lianendtime     = mini.formatDate (mini.get("lianendtime").getValue(), 'yyyy-MM-dd' );
			var caidingstarttime= mini.formatDate (mini.get("caidingstarttime").getValue(), 'yyyy-MM-dd' );                                                                                               
			var caidingendtime  = mini.formatDate (mini.get("caidingendtime").getValue(), 'yyyy-MM-dd' );
			var birthstarttime  = mini.formatDate (mini.get("birthstarttime").getValue(), 'yyyy-MM-dd' );                                                                                             
			var birthendtime    = mini.formatDate (mini.get("birthendtime").getValue(), 'yyyy-MM-dd' );
			var anjiantype		= mini.get("anjiantype").getValue();
			var punishmenttype  = mini.get("punishmenttype").getValue();
           //	var caseid          = mini.get("caseid").getValue();
       	    var depid           = mini.get("depid").getValue();
       		var key             = mini.get("key").getValue();
       		var sj				=mini.get("sj").getValue();
       		var jy				=mini.get("jy").getValue();
       		var idids			=mini.get("idids").getValue();
       		var flowdefid="";
       		var jyorsj="";
       		if(idids=='1'){
       			flowdefid="other_sjjxjssp";
       		} 
       		if(idids=='2'){
       			jyorsj="jy";
       			flowdefid="other_zfjyjxjssp";
       		}
       		grid.load({ lianstarttime: lianstarttime, lianendtime: lianendtime,
	       		caidingstarttime: caidingstarttime,caidingendtime: caidingendtime,
	       		birthstarttime: birthstarttime,birthendtime: birthendtime,
	       		anjiantype: anjiantype,punishmenttype: punishmenttype,flowdefid:flowdefid,
	       		 key : key, depid: depid,processid2:sj,processid:jy ,jyorsj:jyorsj});
        
        }
        function onKeyEnter(e) {
            search();
        }
        function changed(){
        	var idids=mini.get("idids").getValue();
        	var sj=document.getElementById("sjid");
        	var jy=document.getElementById("jyid");
        	var danwei=document.getElementById("danwei");
        	if(idids=='2'){
        	sj.style.display="none";
        	jy.style.display="";
        	danwei.style.display="";
        	mini.get("sj").setValue("");
        	}else if(idids=='1'){
        	jy.style.display="none";
        	danwei.style.display="none";
        	sj.style.display="";
        	mini.get("jy").setValue("");
        	}
        }
    </script>
</body>
</html>