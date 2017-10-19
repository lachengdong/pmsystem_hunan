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
	 <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
	 	   <input id="menuid" name="menuid" type="hidden" value="${menuid}"/>
	 	   <input id="flowdefid" name="flowdefid" class="mini-hidden" value="${flowdefid}"/>
    	   <input id="tempid" name="tempid" class="mini-hidden" value="${tempid}"/>
           <table >
               <tr>
                <td style="width:100%;">
                	案件号：【（<input id="curyear" name="curyear"  class="mini-textbox"  value="${curyear}" 
                		style="width:40px;text-align:center"/>）${shortname} 
                		<input id="casetype" class="mini-combobox" valueField="id" textField="text" data="[{ id: 0, text: '减字' }, { id: 1, text: '假字'} ]" onvaluechanged="oncasetypechanged" style="width:50px;" value="0" />
                		第 <input id="casenumber" name="casenumber"  class="mini-textbox"  value="${casenumber}" style="width:40px;text-align:center"/> 号】
                		<!--  
        				收案日期：<input id="caseacceptdate" name="caseacceptdate" style="width:120px;" class="mini-datepicker" />
        				-->
        				<!-- <input id="important" class="mini-combobox" valueField="id" textField="text" data="[{ id: -1, text: '全部罪犯' },{ id: 0, text: '一般犯' }, { id: 1, text: '重要犯'} ]" onvaluechanged="ontypechanged" style="width:80px;" value="-1" /> -->
        				<a class="mini-button" style="display:none;"   id="16322"  iconCls="" plain="true" onclick="backCases();">退回案件</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="请选择监狱"
                            showNullItem="true" nullItemText="--全部--" url="getJailList.action" style="width:150px;" onvaluechanged="onjianyuchanged"/>
                    <a class="mini-button" iconCls="icon-ok"  plain="true" onclick="batchLian()">批量立案</a>  
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>      
                </td>
               </tr>
		</table>
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    </div>
    <div class="mini-fit" id="div_two">
    	<div id="datagrid" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="getProvinceLiAnList.action?1=1&flowdefid=${flowdefid}" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
	        <div property="columns">
	        		<div type="checkcolumn" width="20px;"></div> 
            		<div field="jailname" width="70" headerAlign="center" align="center"  allowSort="true">监狱名称</div>
            		<div field="caseno" width="100" headerAlign="center" align="center"  allowSort="true">监狱案号</div>
            		<div field="crimid" width="60" headerAlign="center"  align="center" allowSort="true">罪犯编号</div>
            		<div field="crimclass" width="30px"  headerAlign="center"  	allowSort="true" align="center" >罪犯类型</div> 
            		<div field="name" width="60" headerAlign="center"  align="center" allowSort="true">姓名</div>
	            	<div field="causeaction" width="30"  headerAlign="center"  	allowSort="true" align="center" >罪名</div> 
	            	<div field="originalyear" width="30" headerAlign="center" align="center"  allowSort="true">原判刑期</div> 
	            	<div field="sentencestime" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div> 
                 	<div field="courtchangeto" width="40px"  headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">现刑期止日</div>	            	           		    

            		<div field="jailinfo" width="130"  headerAlign="center"  align="center" allowSort="true" >监狱意见</div>
            		<div id="action" width="60px"  headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>     
	        </div>
    </div>
 </div>
 <script type="text/javascript">
		 mini.parse();
		 var datagrid = mini.get("datagrid");
		 datagrid.load();
		 //datagrid.sortBy("optime", "desc");
		 //mini.get("caseacceptdate").setValue(new Date());//收案日期
		  
        function onActionRenderer(e) {
            var s ='';
	        s = document.getElementById('middlebtns').value;
	     	return s;
        }
        
        function oncasetypechanged(){
        	var casetype = mini.get("casetype").getText();
        	var commutetype = mini.get("casetype").getValue();
        	addAnjianhao(commutetype)
        	var changetype = "狱内减刑";
        	if(casetype=='假字'){
        		changetype = "假释";
            }
        	var jailid = mini.get("jailid").getValue();
			datagrid.load({jailid:jailid ,changetype:changetype});
        }
        function ontypechanged(){
        	var casetype = mini.get("casetype").getText();
        	var commutetype = mini.get("casetype").getValue();        
        	var important = mini.get("important").getValue();
        	addAnjianhao(commutetype)
        	var changetype = "狱内减刑";
        	if(casetype=='假字'){
        		changetype = "假释";
            }
        	var jailid = mini.get("jailid").getValue();
			datagrid.load({jailid:jailid ,changetype:changetype,important:important});
        }          
        function onjianyuchanged(){
        	var jailid = mini.get("jailid").getValue();
			datagrid.load({jailid:jailid });
        }
        
       //动态显示案件号
        function addAnjianhao(commutetype) {
        		var curyear = mini.get("curyear").getValue();
        		var flowdefid = mini.get("flowdefid").getValue();
        		$.ajax({
				         url: "/pmsys/flow/getLastCaseNum.json", 
				         data: {curyear:curyear, flowdefid:flowdefid, commutetype:commutetype},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var a = eval(text);
				         	var b = parseInt(a);
				        	mini.get("casenumber").setValue(b);
				        	datagrid.reload();
				         }
				});
        }
       
       
       //省局立案
        function provinceLian(commutetype){
        	//var caseacceptdate =  mini.get("caseacceptdate").text;//收案时间
        	var row = datagrid.getSelected();
			if(row){
				
				var id = row.crimid;
				var flowdefid = mini.get("flowdefid").getValue();
				//立案前先判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回罪犯姓名，否则否回0
				var result="";
				$.ajax({
			         url: "<%=path%>/flow/isHaveCaseLiAn.action", 
			         data: {flowdefid:flowdefid,id:id},
			         type: "POST",
			         dataType:"json",
			         async:false,
			         success: function (text){
			         	var result = eval(text);
			         }
				});
				if(result != 0){
					alert("罪犯"+result + "已立案，请刷新页面！");
					return;
				}
				
				var mess = "确定对服刑人员【"+ row.name +"】进行立案？";
				if(confirm(mess)){
            		var applyid = row.crimid;
					var applyname = row.name;
					var orgid = row.jailid;
					//var casenumber = mini.get("casenumber").getValue();//案件号
            		var curyear = mini.get("curyear").getValue();//案件号年度
					var resid = '10256_02'; //流程的开始资源节点
					var operateType = 'save';
					var tempid = mini.get("tempid").getValue();
					var changetype = row.changetype;
					if(changetype=='假释'){
						commutetype=1;
			        	mini.get("casetype").setValue(1);
					}else{
						mini.get("casetype").setValue(0);
					}
					//var caseacceptdate = mini.get("caseacceptdate").getValue();
					
					//var data = getNoteMap();//表单所有节点及值
					//var docconent = saveFile();//获取大字段    "/pmsys/flow/jiangQuLiAn.action", 
					$.ajax({
				         url: "<%=path%>/flow/provinceLiAn.action",
				         data: {resid:resid,operateType:operateType, orgid:orgid, commutetype:commutetype,
				         flowdefid:flowdefid,tempid:tempid,applyid:applyid,applyname:applyname, curyear:curyear},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var obj = eval(text);
				         	if(obj=='-1') {
				         		alert("操作失败!");
				         	}else{
				         		alert("操作成功!");
				         		addAnjianhao(commutetype);
				         	}
				         	datagrid.reload(commutetype);
				         }
					});
            	}else{
            		datagrid.reload();
            	}
			}else{
				alert(confirmMessage);
			}
        }
       
		
		//批量立案
        function batchLian(commutetype){
        	var rows = datagrid.getSelecteds();
            if (rows.length>0) { 
            	myloading();
            	if(confirm(batchAlertOne)){     
            		var flowdefid = mini.get("flowdefid").getValue();
            		var idArr = [];
            		var dataArr = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                    	var r = rows[i];
                    	idArr.push(r.crimid);
                    	var changetype = r.changetype;
                    	if(changetype=='假释'){
    						commutetype=1;
    			        	mini.get("casetype").setValue(1);
    					}else{
    						commutetype=0;
    						mini.get("casetype").setValue(0);
    					}
                        dataArr.push(r.crimid+"@"+r.name+"@"+r.jailid+"@"+commutetype);
                    }
                    var id = idArr.join(',');
                    var dataStr = dataArr.join(',');
                    
            		//立案前先判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回罪犯姓名，否则否回0
					var result="";
					$.ajax({
				         url: "<%=path%>/flow/isHaveCaseLiAn.action", 
				         data: {flowdefid:flowdefid,id:id},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         success: function (text){
				         	var result = eval(text);
				         }
					});
					if(result != 0){
						alert("罪犯"+result + "已立案，请刷新页面！");
						return;
					}	
					   	
                    var resid = '10256_02';
					var operateType = 'save';
					var tempid = mini.get("tempid").getValue();
					var curyear = mini.get("curyear").getValue();
				    //var casenumber = mini.get("casenumber").getValue();
				    //var casetype = '减字';
                     $.ajax({
		                 url: "<%=path%>/flow/provinceBatchLiAn.action",
				         data: {resid:resid,operateType:operateType,curyear:curyear,
				         flowdefid:flowdefid,tempid:tempid,dataStr:dataStr},
				         type: "POST",
				         dataType:"json",
				         async:false,
				         cache: false,
		                 success: function (text) {
		                 	var obj = eval(text);
		                 	if(obj=='-1'){
		                 		alert("操作失败!");
		                 	}else{
		                 		alert("操作成功!");
			         			var b = addAnjianhao(commutetype);//案件号加n
			         		}
		                	datagrid.reload(commutetype);
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
        	var jailid = mini.get("jailid").getValue();
            var key = mini.get("key").getValue();
            datagrid.load({ key: key, jailid:jailid });
        }
        function onKeyEnter(e) {
            search();
        }
        
        function view(menuid) {
			var row = datagrid.getSelected();
			var crimid = row.crimid;
			var orgid = row.jailid;
			var flowdraftid = row.flowdraftid;
			var flowid = row.flowid;
			var lastnodeid = row.lastnodeid;
        	var fatherMenuid = document.getElementById("menuid").value;
        	var tempid = mini.get("tempid").getValue();
        	var flowdefid = mini.get("flowdefid").getValue();
        	var closetype = 2;
        	var id = crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+lastnodeid;
        	
        	if(flowid==undefined){
        		flowid = "flowidnull";
        	}
        	var url = "toProvinceCommuteTabs.action?1=1&tempid="+tempid+"&menuid="+menuid+"&fathermenuid="+fatherMenuid+
        	"&id="+id+"&closetype="+closetype+"&flowdefid="+flowdefid+"&tabtype=0";
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
                    datagrid.reload();
                }
            });
		
        }

		//退回案件
		function backCases(){
			var rows = datagrid.getSelecteds();
			var menuid = '';
			var menuidObj = document.getElementById("menuid");
			if(menuidObj) menuid = menuidObj.value;
			var flowdefid = mini.get("flowdefid").getValue();
			
            if (rows.length > 0) {
                if (confirm("确定操作选中记录？")) {
                    var idArr = [];
                    var crimidArr = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        idArr.push(r.flowdraftid);
                        crimidArr.push(r.crimid);
                    }
                    var id = "10";
                    var crimids = crimidArr.join(',');
                    $.ajax({
                        url: "backCasesOfJail.action?1=1",
                        type: "post",
                        data: {flowdraftids:id, menuid:menuid, crimids:crimids},
                        success: function (text) {
                        	var result = eval(text);
                        	if(result =="success"){
                        		alert("操作成功！");
                        	}else{
                        		alert("操作失败！");
                        	}
                            datagrid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        }
                    });
                }
            } else {
                alert(confirmMessages);
            }
		}	       
 </script>
</body>
</html>
