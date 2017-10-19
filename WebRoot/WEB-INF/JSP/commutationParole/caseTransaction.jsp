<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title></title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
    <script type="text/javascript">
			function search() {
		         var key = mini.get("key").getValue();
		          mini.get("datagrid").load({ key:key});
		    }
	        function onKeyEnter(e) {
	            search();
	        }
	        $("#key").bind("keydown", function (e) {
	            if (e.keyCode == 13) {
	                search();
	            }
	        });
	        
	        function onActionRenderer(e) {
	            var grid = e.sender;
	            var record = e.record;
	            var uid = record._uid;
	            var rowIndex = e.rowIndex;
	            var gkzxtempid=record.gkzxtempid;
	            var s ="";
         		s=s+'<a class="New_Button" href="javascript:daibanshenpi(\'toPrisonInfoPage.action?1=1&qtype=tab10\',\'900\',\'550\',\'6680\',\'blank\')">办案</a>&nbsp;&nbsp;';
         		s=s+'<a class="New_Button" href="javascript:daibanshenpi(\'publicModelAction.action?1=1&qtype=15&reportid=BBreport@12377&reportid2=BBreport@12377&stype=annex\',\'900\',\'550\',\'6680\',\'blank\')">开庭笔录</a>&nbsp;&nbsp;';
	            return s;
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
        	
	    function onCriminalidRenderer(e) {
            var record = e.record;
            var s = ' <a class="Edit_Button" href="javascript:getCriminalInfo(\'' + record.criminalid + '\')" >'+record.criminalid+'</a>&nbsp;&nbsp;';
            return s;
        }
        function getCriminalInfo(criminalid){
        	alert("测试");
        }
        
        function onKeyEnter(e) {
            search();
        }
        
	</script>
</head>
<body onload="myload()">
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
                <td style="width:100%;">
                 	<a class="mini-button" iconCls="" plain="true" onclick="allqianzhang('SDXFshowHandleAllSeal.action?1=1','900','550','6680','')">批量盖章</a>
                 	<a class="mini-button" iconCls="icon-downgrade" plain="true" onclick="allcaozuo('ajaxAllCaoZuo.action?tijiaotype=fyla&amp;operate=YES','900','550','6680','')">批量提交</a>
                 	<a class="mini-button" iconCls="" plain="true" onclick="setdate('setDiscussionDate.action?1=1','300','350','6680','')">设置合议日期</a>
                 	<a class="mini-button" iconCls="" plain="true" onclick="setdate('setCourtPage.action?1=1','400','300','6680','')">开庭设置</a>
                 	<a class="mini-button" iconCls="" plain="true" onclick="allcaozuo('saveBatchReportAction.action?1=1','900','550','6680','')">批量报备</a>
                </td>
                <td style="white-space:nowrap;">
                   	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="编号、姓名、拼音首字母"  onenter="onKeyEnter"/>
                   	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true" onclick="search()">查询</a>
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('6680')"></a>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    	url="ajaxGetDataById.action"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        		<div type="checkcolumn" width="10"></div>
            			<div field="parolenumber" width="40px"  headerAlign="center"  	allowSort="true" align="center" >
	               			案件号
	               		</div>  
         				<div field="criminalid" width="40px"  headerAlign="center"  	allowSort="true" align="center" >
	               			罪犯编号
	               		</div> 
			            		<div field="cpdregisteryear" visible="false"  headerAlign="center"  allowSort="true" align="center" >
			               			立案年度
			               		</div>  
			            		<div field="cppopersonid" visible="false"  headerAlign="center"  allowSort="true" align="center" >
			               			经办人编号
			               		</div>  
			            		<div field="cpddepartmentsimplename" visible="false"  headerAlign="center"  allowSort="true" align="center" >
			               			单位简称
			               		</div>  
			            		<div field="cppopersonstatus" visible="false"  headerAlign="center"  allowSort="true" align="center" >
			               			办案人身份
			               		</div>  
            			<div field="cpdregisterdate" width="40px"  headerAlign="center"  dateFormat="yyyy-MM-dd"	allowSort="true" align="center" >
	               			立案时间
	               		</div>  
            			<div field="cpdenddiscussdate" width="40px"  headerAlign="center"  dateFormat="yyyy-MM-dd"	allowSort="true" align="center" >
	               			合议时间
	               		</div>  
            			<div field="zhushenyijian" width="40px"  headerAlign="center"  	allowSort="true" align="center" >
	               			主审意见
	               		</div>  
                 	<div name="action" width="40px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    
</body>
</html>
<script type="text/javascript"> 
    mini.parse();
	var datagrid = mini.get("datagrid");
	
	function myload(){
   			datagrid.sortBy("cpdcasenumber", "desc");
	        datagrid.load();
	        datagrid.on("drawcell",function(e){
       			 var record = e.record,
			     column = e.column,
			     field = e.field,
			     value = e.value;
			     var instancestate = record.instancestate;//流程状态，判断是否退回案件
			     var isreport = record.isreport;//是否已报备
			     var sanleifan = record.sanleifan;//三类罪犯
			     //给帐号列，增加背景色
             	if(instancestate=='BACK'){//退回需复议的 加颜色
                	//e.cellStyle = "background:#D97E96";//粉红色
                }else if(instancestate=='YESEND'){
                	e.cellStyle = "background:#00FF00";//浅绿色
                }
                if(isreport=='1'){
                	e.cellStyle = "background:#52C3DD";//浅蓝色
                }
                if(sanleifan=='0'||sanleifan=='1'||sanleifan=='2'){
                	e.cellStyle = "background:#AD0505";
                }
       		});
   		}
	
    //行双击时发生  目前用于法院双机行弹出办案事件
    function rowdblclickfunction(e){
    	var record = e.record;
    	var s;
    	eval(s);
    }
    
    //法院办案
     function fybanan() {
           
     }
</script>
