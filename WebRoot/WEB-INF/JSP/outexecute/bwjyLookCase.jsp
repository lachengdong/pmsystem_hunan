<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%> 
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		
		<title>保外案件查看(宁夏)</title>
		
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
		</style>
  </head>

  <body>
    <div >
        <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-remove" onclick="casechexiao()" plain="true">案件撤销</a>
                    </td>
                    <td style="white-space:nowrap;">
                        
                    	<input id="depid" class="mini-combobox" valueField="orgid" textField="name" emptyText="单位过滤" showNullItem="true" enabled="true"
			            	nullItemText="--全部--" url="<%=path%>/jxjs/getDeptInfo.action?1=1" style="width:150px;"   onvaluechanged="onKeyEnter" />
			            	<input id="typeid" class="mini-combobox" emptyText="案件过滤"  style="width:150px;" value="other_jybwjycbsp"  
                        data="typeid" onvaluechanged="onKeyEnter" />  
			            <input id="nodeid" class="mini-combobox" valueField="id" textField="text"  emptyText="进程过滤" showNullItem="true" 
			            	nullItemText="--全部--" data="progress" style="width:150px;"  enabled="true" onvaluechanged="onsdidchanged" />
			            <input id="key" class="mini-textbox" emptyText="罪犯编号、姓名、拼音"  id="key" onenter="onKeyEnter"  enabled="true"/>
                        <a class="mini-button" onclick="search()" plain="true">查询</a>
                        <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10141')"></a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div class="mini-fit">
        <!--url="getBWCaseDataInfo.action?flowdefid=other_jybwjycbsp"   -->
	    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" pageSize="20"
	        url="bwjyLookCaseDataInfo.action?flowdefid=other_jybwjycbsp"
	          idField="jailid" multiSelect="true"  allowAlternating="true" > 
	        <div property="columns">
	           <!-- -->
	            <div type="checkcolumn" ></div>        
	         
	            <div field="flowid" width="0" headerAlign="center" align="center" allowSort="false">年号</div>    
	            <div field="flowdraftid" width="0" headerAlign="center" align="center" allowSort="false">年号</div>    
	            <div field="flowdefid" width="0" headerAlign="center" align="center" allowSort="false">年号</div>   
	            
	            <div field="nianhao" width="40" headerAlign="center" align="center" allowSort="false">年号</div>    
	            <div field="anjianhao" width="120" headerAlign="center" align="center" allowSort="false">案件号</div> 
	            <div field="name" width="120" headerAlign="center" align="center" allowSort="false">姓名</div>   
	            <div field="crimid" width="120" headerAlign="center" align="center" allowSort="false"  renderer="onActionRenderer">罪犯编号</div>  
	            <div field="areaname" width="120" headerAlign="center" align="center" allowSort="false">所属监区</div> 
	            <div field="jincheng" width="120" headerAlign="center" align="center" allowSort="false">进程</div>
	        </div>
	    </div>
    </div>
    <script type="text/javascript">
	    var progress = [{id:'N0002_0_0_0_0', text: '分监区办理中'},
		          	   {id:'N0002_-1_2_N0003_0', text: '监区长办理中'},
		          	   {id:'N0003_-1_2_N0004_0', text: '刑罚科办理中'},
		          	   {id:'N0004_-1_2_1_0', text: '监狱长办理中'},
		          	   {id:'-1_3_3_-1_3', text: '监狱办理未通过'},
		          	   {id:'1_1_1_1_1', text:'监狱办理通过'}];
	    
    	var typeid = [{id:'other_jybwjycbsp', text: '初保'},{id:'other_jybwjyxbsp', text:'续保'}];
    	//var processid = [{id:'0', text: '立案审批中'},{id:'2', text:'科室审批中'},{id:'3', text:'评审会审批中'},{id:'N0003', text:'监狱审批中'},{id:'1', text:'已通过'},{id:'-1', text:'未通过'}];
        mini.parse(); 
        var grid = mini.get("datagrid1");
        grid.load();
        
		grid.on("drawcell",function(e){
        			 var record = e.record,
				     column = e.column,
				     field = e.field,
				     value = e.value;//alert(field+":"+value);
				     var nodeid = record.nodeid;
				     //给帐号列，增加背景色
		             if (field == "jincheng" ) {
		             	if(value=='分监区办理中'){
		                	e.cellStyle = "background:#FFEC8B";
		                }else if(value=='监区办理中'){
		                	e.cellStyle = "background:#DEB887";
		                }else if(value=='科室办理中'){
		                	e.cellStyle = "background:#66DD00";
		                }else if(value=='监狱长办理中'){
		                	e.cellStyle = "background:#00DDDD";
		                }else if(value=='监狱办理通过'){
		                	e.cellStyle = "background:#FFFF33";
		                }else if(value=='监狱办理未通过'){
		                	e.cellStyle = "background:red";
		                }
		             }
        });
         function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            //var rowIndex = e.rowIndex;
            var s = ' <a class="Edit_Button" href="javascript:checkaip(\'' + uid + '\')" >'+record.crimid+'</a>&nbsp;&nbsp;';
            return s;
        }
        //查看 案件内容 
        function checkaip(id){
            var row = grid.getSelected();
            if (row) {
            	//var url="toCaseLook.action?1=1&crimid="+row.crimid;
            	var tempid = "ZFABWJYSPB";
            	var typeid = mini.get("typeid").getValue();
            	if(typeid == 'other_jybwjyxbsp') tempid = "XB_ZYJWZX";
            	var url="tocaseLookTabPage.action?1=1&crimid="+row.crimid+"&flowid="+row.flowid+"&flowdraftid="+row.flowdraftid+
            	"&flowdefid="+row.flowdefid+"&menuid=10096"+"&tempid="+tempid+"&jxstempid=NX_JWZXJYSreport";
            	//jxstempid=NX_JWZXJYSreport 值report前面的内容是模板id，根据不同省份的不能写死
            	//var url = "<%=path%>/basicInfo/basicInformation.page?1=1&crimid="+row.crimid;
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
        //查询
        function search() {
            var typeid = mini.get("typeid").getValue();
            //var processid = mini.get("processid").getValue();
            var orgid = mini.get("depid").getValue();
       		var key = mini.get("key").getValue();
       		var nodeid = mini.get("nodeid").getValue();
       		var nodeidname = mini.get("nodeid").getText();
       		//if(typeid == 2) flowdefid = "other_jybwjyxbsp";
       		//mini.get("processid").setUrl("jxjs/ajaxGetProcess.json?flowdefid="+flowdefid);//other_jybwjyxbsp
       		grid.load({key : key, depid: orgid,typeid:typeid,nodeidname:nodeidname,nodeid:nodeid});
        }
        //回车键 出发查询事件
        function onKeyEnter(e) {
            search();
        }

        //案件撤销
        function casechexiao(){
            var rows = grid.getSelecteds();
            if(rows.length > 0){
                 if(!confirm('撤销以后将无法恢复!')){
                     return;
                 }
                 var i = 0;
                 var ids = [];//罪犯编号
                 var flowdraftids = [];//流程草稿id，唯一标识一条记录
                 var jysptg = "";//监狱审批通过的罪犯 是不能撤销的
                 for(i=0;i<rows.length;i++){
                     var jincheng = rows[i].jincheng;
                     if(jincheng == '监狱办理通过'){
                    	 jysptg += rows[i].name+',';
                    	 grid.deselect(rows[i],true);//取消不符合条件复选框的选择状态
                     }else{
                    	 ids.push(rows[i].crimid);
                    	 flowdraftids.push(rows[i].flowdraftid);
                     }
                 }
                 //审批通过的罪犯是无法撤销的，牵扯到留痕的问题，
                 //只有审批不通过或者还在审批中的罪犯才可以进行撤销操作
                 if(jysptg != ""){
                     alert("罪犯<"+jysptg.substring(0,jysptg.length-1)+">案件审批通过无法撤销!");
                     return;
                 }
                 //下面我们将要执行，符合撤销条件的罪犯进行撤销
                 $.ajax({
                      type:'post',
                      url:'cheXiaoBwjyCase.action?1=1&crimids='+ids+"&flowdraftids="+flowdraftids,
                      success:function (text){
                          alert('操作成功!');
                          grid.reload();
                      }
                 });
            }else{
                 alert('请选择需要撤销案件!');
            }
        }
  </script>
</body>
</html>
