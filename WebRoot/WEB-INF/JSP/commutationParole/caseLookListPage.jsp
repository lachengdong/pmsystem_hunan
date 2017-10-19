<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>减刑假释案件查看</title>
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
                    </td>
                    <td style="white-space:nowrap;">
                    	 <input id="nodeid" class="mini-combobox" valueField="snodeid" textField="text3"  emptyText="进程过滤" showNullItem="true" onvaluechanged="changed"
			            	nullItemText="--全部--" url="jxjs/ajaxGetProcess.json?flowdefid=other_zfjyjxjssp" style="width:150px;" valueField="snodeid" textField="text3" />
			            <input id="key" class="mini-textbox" emptyText="请输入罪犯编号、姓名、拼音"  id="key" onenter="onKeyEnter"  enabled="true"/>
                        <a class="mini-button" onclick="search()" plain="true">查询</a>
                        <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10096')"></a>
                        <!-- <a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a> -->
                    </td>
                </tr>
            </table>           
        </div>
         </div>
    <div class="mini-fit">
	    <div id="datagrid1" allowMoveColumn="false" allowCellEdit="true" style="width:100%;height:100%;" class="mini-datagrid"   
	        url="getBWCaseDataInfo.action?1=1&flowdefid=other_zfjyjxjssp" 
		     idField="" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  onshowrowdetail="onShowRowDetail"
		      allowAlternating="true"  sizeList="[10,20,50,100]" pageSize="20">
	        <div property="columns">
	        	<div type="indexcolumn"></div>
	        	<div type="expandcolumn" >留痕记录</div>
	            <div field="nianhao" width="40" headerAlign="center" align="center" allowSort="false">年号</div>    
	            <div field="anjianhao" width="40" headerAlign="center" align="center" allowSort="true">案件号</div>
	            <div field="anhao" width="100" headerAlign="center" align="center" allowSort="true">案号</div>     
	            <div field="crimid" width="60" headerAlign="center" align="center" allowSort="true"  renderer="onActionRenderer">罪犯编号</div>  
	            <div field="name" width="80" headerAlign="center" align="center" allowSort="false">姓名</div>   
	            <div field="areaname" width="80" headerAlign="center" align="center" allowSort="false">所属监区</div> 
	            <!-- <div field="casestate" width="100" headerAlign="center" align="center" allowSort="false">办理状态</div> -->
		        <div field="jincheng" width="100" headerAlign="center" align="center" allowSort="false">办案进程</div>
	        </div>
	    </div>
	    <div id="detailGrid_Form" style="display:none;">
	        <div id="employee_grid" class="mini-datagrid" showPager="false" showVGridLines="false" showHGridLines="false"
	        style="width:100%;height:260px;" url="jxjs/selectFlowForScar.json?1=1&flowdefid=other_jyjxjslasp">
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
        
         // 分页填充细节处理
    	function fillData(pageIndex, pageSize, dataResult, grid) {
	        var data = dataResult.data, totalCount = dataResult.total;
	        var arr = [];
	        var start = pageIndex * pageSize, end = start + pageSize;
	        for (var i = start, l = end; i < l; i++) {
	            var record = data[i];
	            if (!record) continue;
	            arr.push(record);
	        }
	        
	
	        grid.setTotalCount(totalCount);
	        grid.setPageIndex(pageIndex);
	        grid.setPageSize(pageSize);
	
	        grid.setData(arr);
	    }
        function search() {
       		var key = mini.get("key").getValue();
       		var nodeid = mini.get("nodeid").getValue();
       		grid.load({key : key,processid:nodeid});
        }
        function onKeyEnter(e) {
            search();
        }
        
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record.crimid;
            var rowIndex = e.rowIndex;
            var s = ' <a class="Edit_Button" href="javascript:checkaip(\'' + uid + '\')" >'+uid+'</a>&nbsp;&nbsp;';
            return s;
        }
         grid.on("drawcell",function(e){
      		 var record = e.record,
		     column = e.column,
		     field = e.field,
		     value = e.value;
		     var casestate = record.jincheng;
		     //给帐号列，增加背景色
             if (field == "jincheng" ) {
             	if(casestate=='评审会办理中'){
                	e.cellStyle = "background:#FFD700";
                }else if(casestate=='监区办理中'){
                	e.cellStyle = "background:#DEB887";
                }else if(casestate=='监狱办理中'){
                	e.cellStyle = "background:#FFEC8B";
                }else if(casestate=='已通过'){
                	e.cellStyle = "background:green";
                }else if(casestate=='经办人办理中'){
                	e.cellStyle = "background:yellow";
                }else if(casestate=='分监区办理中'){
                	e.cellStyle = "background:yellowgreen";
                }else if(casestate=='未通过'){
                	e.cellStyle = "background:red";
                }if(casestate=='刑罚科办理中'){
                    e.cellStyle = "background:#00FFFF"; 
                }else if(casestate=='狱政科办理中'){
                    e.cellStyle = "background:#FF0FFF";
                }
             }
        });


        function checkaip(id){
            var row = grid.getSelected();
            if (row) {
            	var url="tocaseLookTabPage.action?1=1&crimid="+row.crimid+"&flowid="+row.flowid+"&flowdraftid="+row.flowdraftid+"&flowdefid="
            	+row.flowdefid+"&menuid=10096"+"&docid=003001"+"&tempid=JXJS_JXJSSHB"+"&cpbid=003002"+"&cpbtempid=CQAJPSB"+"&jysdocid=004001"+"&jxstempid=jailcommutereport";
                mini.open({
                    url: url,
                    showMaxButton: true,
                    allowResize: false, 
                    title: "案件查看", width: 900, height: 600,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = {};
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
       function onsdidchanged(e){
       		search();
       }
         // 刷新本页面
		function refreshPage(){
			location.reload();
		};
		function changed(){
			search();
		}
    </script>
</body>
</html>