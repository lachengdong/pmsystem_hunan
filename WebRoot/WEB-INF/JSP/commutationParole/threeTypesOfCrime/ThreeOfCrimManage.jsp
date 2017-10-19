<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String type = request.getParameter("type");
	Calendar cal = Calendar.getInstance();
	int mon = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>三类罪犯管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
	   body{
       font-size:12px; margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }
    </style>
    <script type="text/javascript">
	   	var confirmMessage = "请选中一条记录！";
	   	var confirmMessages = "请至少选中一条记录！";
    </script>
</head>
<body >
    <div>
		<div class="mini-toolbar" style="padding:0px;border-bottom:0;">
			<table  style="width:100%;">
			    <!--  <s:property value="#request.csan"/>
				 <s:if test="#request.csan == 1">
						<td width="80%" style="margin-left: 20px;">
							<a class="mini-button" onclick="addContent()" iconCls="icon-ok" plain="true" style="width:100px">批量加锁</a> 
						</td>
						<td style="white-space:nowrap;">
							<input id="sllock" class="mini-combobox" valueField="slscid" textField="slsccontent" emptyText="锁定状态" value=""
			            		nullItemText="" url="getSanleiLockType.action?1=1" style="width:120px;"/>
					    </td>
					 </tr>	     
				 </s:if>
				 <s:else>-->
				      <tr>
						<td width="80%" style="margin-left: 20px;">
							<a class="mini-button" onclick="lockCriminal()" iconCls="icon-ok" plain="true" style="width:100px">批量加锁</a> 
							<a class="mini-button" onclick="unLockCriminal()" iconCls="icon-ok" plain="true" style="width:100px">批量解锁</a>
							<a class="mini-button" onclick="back()" iconCls="icon-ok" plain="true" style="width:100px">退回报备</a>
						</td>
						<td style="white-space:nowrap;">
						<!-- getAllDepartment.action?1=1 -->
							<input id="jailid" class="mini-combobox" valueField="jailid" textField="jailname" emptyText="监狱过滤" onvaluechanged="jianyu"
			            		nullItemText="全部监狱" showNullItem="true" url="getJianYuName.action?1=1" style="width:120px;"/>	
			            		<!-- getSanleiType.action?1=1 -->
							<input id="sanleitype" class="mini-combobox" valueField="id" textField="text" emptyText="三类犯类别" showNullItem="true"
			            		nullItemText="全部" data="sanlf" style="width:120px;" onvaluechanged="ondeptchanged"/>	
			            		<!-- getSanleiLockType.action?1=1 -->
							<input id="state" class="mini-combobox" valueField="id" textField="text" emptyText="锁定状态" showNullItem="true"
			            		nullItemText="全部" data="sllock" style="width:120px;" onvaluechanged="ondeptchanged"/>				            			      				
						    <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名、拼音" style="width:150px;" onenter="onKeyEnter"/>   
						    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="onSearch()">快速查询</a>
						    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('ZYY_SLFRD')"></a>
					    </td>
					 </tr>
				 <!--</s:else>-->
			</table>           
		</div>
</div>
		<div class="mini-fit" >
		<!-- getSanleiCriminal.action?1=1 -->
			<div id="datagrid1" class="mini-datagrid" style="width:100%;height: 100%" allowResize="false" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20" 
	        	url="getSanLeiFan.action?1=1"  multiSelect="true"  >
		        <div property="columns">
		            <div type="checkcolumn" ></div>   
		            <div field="crimid" width="90" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
		            <div field="name" width="90" headerAlign="center"  align="center"  allowSort="true">罪犯姓名</div>   
		            <div field="age" width="60" headerAlign="center"  align="center"  allowSort="true">年龄</div> 
		            <div field="jailname" width="110" headerAlign="center"  align="center"  allowSort="true">所在监狱</div>
		            <div field="causeaction" width="110" headerAlign="center"  align="center"  allowSort="true">罪名</div>
		            <div field="originalyearval" width="110"   align="center"  headerAlign="center" allowSort="true">原判刑期</div> 
		            <div field="sanclassstatus" width="0"   align="center"  headerAlign="center" allowSort="true">是否三类犯</div> 
		            <div field="sentencestime" width="110"  align="center"  headerAlign="center" allowSort="true">原判刑期起日</div> 
		            <div field="sentenceetime" width="110"  align="center"  headerAlign="center" allowSort="true">原判刑期止日</div> 
		            <div field="inpersion" width="100" headerAlign="center"  align="center"  allowSort="true">入监时间</div>
		            <div field="" width="140" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>                     
				</div>
			</div>
		</div>
    <script type="text/javascript">
   	 	var sllock = [{id:1, text:'未锁'},{id:2, text:'已锁'}];
   	 	var sanlf = [{id:1, text:'金融犯罪'},{id:2, text:'黑社会性质'},{id:3, text:'职务犯罪'},{id:4, text:'其他'}];
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid","desc");
        grid.load();
        function onActionRenderer(e) {
            var record = e.record;
            var crimid = record.crimid;
            var sanleifan=record.sanclassstatus;
            if(sanleifan==1) {
				var s = '<a class="Edit_Button" href="javascript:chakanInfo(\'' + crimid + '\')" >基本信息</a>&nbsp;&nbsp;'
		        	  + '<a class="Edit_Button" href="javascript:lockCriminal()" >加锁</a>&nbsp;&nbsp;'
            } else if(sanleifan=='2'){
            	var s = '<a class="Edit_Button" href="javascript:chakanInfo(\'' + crimid + '\')" >基本信息</a>&nbsp;&nbsp;'
            		  + '<a class="Edit_Button" href="javascript:unLockCriminal()" >解锁</a>&nbsp;&nbsp;'
            }else{
            	var s ='<a class="Edit_Button" href="javascript:chakanInfo(\'' + crimid + '\')" >基本信息</a>'
            }
            return s;
        }
        
        function lockCriminal() {
	    	var rows = grid.getSelecteds();
	     	if (rows.length > 0) {
	        	if (confirm("确定为选中记录加锁？")) {
	            	var ids = [];
	             	for (var i = 0, l = rows.length; i < l; i++) {
	                	var r = rows[i];
	               		ids.push(r.crimid);
	               		if(r.sanleifan==2) {
	               			alert("已解锁不能再次加锁");
	               			grid.reload();
	               			return;
	               		}
	             	}
	        		var id = ids.join(',');
	              	grid.loading("操作中，请稍后......");	                    
	           		$.ajax({
	                	url: "lockCriminal.action?1=1&crimid=" + id+"&state="+2,
	                	type: "post",
	                 	success: function (text) {
	                 		grid.reload();
	              		},
	          			error: function () {
	          			
	          			}
	         		});
	    		}
	    	} else {
	        	alert("请至少选中一条记录");
	    	}
		}
		
        function unLockCriminal() {
	    	var rows = grid.getSelecteds();
	     	if (rows.length > 0) {
	        	if (confirm("确定为选中记录解锁？")) {
	            	var ids = [];
	             	for (var i = 0, l = rows.length; i < l; i++) {
	                	var r = rows[i];
	               		ids.push(r.crimid);
	             	}
	        		var id = ids.join(',');
	              	grid.loading("操作中，请稍后......");	                    
	           		$.ajax({
	                	url: "lockCriminal.action?1=1&crimid=" +id+"&state="+1,
	                	type: "post",
	                 	success: function (text) {
	                 		grid.reload();
	              		},
	          			error: function () {
	          			
	          			}
	         		});
	    		}
	    	} else {
	        	alert("请至少选中一条记录");
	    	}
		}		
		
		function viewjianyi(id) {
			var row = grid.getSelected();
			var tempid = row.criminalid+"gkzx_gkzx"+id+"gkzx_gkzx"+442500;
			var url = "toJdSuggestDocumentPage.action?1=1&reportid=BBreport@10156&menuid=12165&operatetype=view&id="+tempid;
            if (row) { //6541
                mini.open({
                   url:url,
                    title: "查看监督意见", width: 900, height: 550,
                    showMaxButton: true,
                    allowResize: false, 
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { mymenuid : "12165" , myoperatetype:'view',id:tempid};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        //grid.reload();
                    }
                });
                
            } else {
                alert("请选中一条记录");
            }
		}
        function chakanInfo(id) {
       		var url="basicInfo/basicInformation.page?1=1&crimid="+id+"&menuid="+11106+"&closetype=3";
        	var win=mini.open({
                    url:url,
                    title: "基本信息", width: 900, height: 550,
                    showMaxButton: true,
                    allowResize: false, 
                    onload: function () {
 
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
        	
		}

        //根据入监时间查询罪犯
         function onSearch() {
            var jailid = mini.get("jailid").getValue();
            var sanleitype = mini.get("sanleitype").getValue();
            var state = mini.get("state").getValue();
            var key = mini.get("key").getValue();
            //var url = "getSanLeiFan.action?1=1&key=" + key;
            grid.load({ key: key,jailid:jailid,state:state,sanleitype:sanleitype});
        	//grid.sortBy("crimid","desc");
        }
        
        function onKeyEnter(e) {
            onSearch();
        }	
        //报备到 法院
        function addContent(){
            var row = grid.getSelected();
            $.ajax({
                 url:'addFYBaoBei.action?data'+row.criminalid,
                 type:'POST',
                 data:{data:row.criminalid},
                 success:function(text){
                      if(text == '1'){
                           alert('添加成功!');
                      }else{
                           alert('添加失败!');
                      }
                 },
                 error:function(){
                      alert('添加失败、请联系管理员!');
                 }
            });
        }
        //撤回 报备 
        function back(){
            var rows = grid.getSelecteds();
            var state = 0;
            if(rows.length<=0){
            alert("请至少选择一条记录！");
            return;
            }
            if(confirm('确认退回报备吗?')){
                $.ajax({
                	type:'post',
                	url:'backSLFBeibei.action?crimid='+row.crimid+'&state='+state,
                	async:false,
                	success:function(text){
                	    if(text == '1'){
                	        alert('该罪犯已锁定!');
                	        state = 1;
                	    }
                	}
            	});
            	if(state == 1){
            	    return;
            	}
                $.ajax({
                	type:'post',
                	url:'backSLFBeibei.action?key='+row.criminalid,
                	success:function(){
                	    alert('撤销成功!');
                	    grid.load();
                	},
                	error:function (){
                	    alert('撤销失败!');
                	}
            	});
            }
        }
        //刷新
        function ondeptchanged() {
			onSearch();
		}
		function jianyu(){
			 onSearch();
		}
		function chakanshuoming(tempid){
		var win=mini.open({
                  url:"<%=path%>/threeOfCrimManage/showManage.action?1=1&tempid="+tempid,
                  title:"三类罪管理", width:"900", height:"600",
                  onload:function () {
                      var iframe = this.getIFrameEl();
                      var data = {};
                      iframe.contentWindow.SetData(data);
                  }
      		});
		}
    </script>
</body>
</html>