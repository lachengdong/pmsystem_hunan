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
    <title>三类罪犯报备</title>
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
			    <!--  <s:property value="#request.csan"/>	-->  
			        <tr>
						<td align="left" style="margin-left: 20px;">
							<a class="mini-button" onclick="addContent()" iconCls="icon-ok" plain="true" style="width:100px">批量报备</a> 
						</td>
						<td align="right">
						   <!-- <input id="sdid" class="mini-combobox" valueField="sdid" textField="sddiscribe" emptyText="选择部门"
			            		nullItemText="所有部门" url="getAllDepartment.action?1=1" style="width:120px;"/> -->
			            		<!-- <%=path%>/txt/sanleifan.txt -->
			            	<input id="anfanleibie" class="mini-treeselect" url="" multiSelect="true" 
		       				textField="text" valueField="id" parentField="pid" checkRecursive="true"  emptyText="罪犯类别"
		        			showFolderCheckBox="true"  expandOnLoad="false" showClose="true" style="width:180px;" oncloseclick="onCloseClick"  popupWidth="250" />
			            	
			            	<input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名、拼音" style="width:150px;" onenter="onKeyEnter"/>   
						    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="onSearch()">快速查询</a>
						</td>
						<!-- <td style="white-space:nowrap;">
							<input id="sllock" class="mini-combobox" valueField="slscid" textField="slsccontent" emptyText="锁定状态" value=""
			            		nullItemText="" type='hidden' url="getSanleiLockType.action?1=1" style="width:120px;"/>
					    </td> -->
					 </tr>   
				 
			</table>           
		</div>
   </div>
		<div class="mini-fit" >
		<!-- getAllSlCriminal.action?1=1 -->
			<div id="datagrid1" class="mini-datagrid" style="width:100%;height: 100%"  allowResize="false" allowAlternating="true" sizeList="[10,20,50,100,1000]" pageSize="20" 
	        	url=""  multiSelect="true"  >
		        <div property="columns">
		            <div type="checkcolumn" ></div>   
		            <div field="criminalid" width="90" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
		            <div field="cbiname" width="90" headerAlign="center"  align="center"  allowSort="true">罪犯姓名</div>   
		            <div field="age" width="60" headerAlign="center"  align="center"  allowSort="true">年龄</div> 
		            <div field="zhuanyou" width="110" headerAlign="center"  align="center"  allowSort="true">罪名</div>
		            <div field="cjibegindate" width="110"  dateFormat="yyyy-MM-dd"  align="center"  headerAlign="center" allowSort="true">刑期起日</div> 
		            <div field="cjienddate" width="110"  dateFormat="yyyy-MM-dd"  align="center"  headerAlign="center" allowSort="true">刑期止日</div> 
		            <div field="criofficiallyplacedate" width="100" headerAlign="center"  align="center"  dateFormat="yyyy-MM-dd" allowSort="true">入监时间</div>
		            <div field="" width="140" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>                     
				</div>
			</div>
		</div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        //grid.load();
        //grid.sortBy("criminalid","desc");
        function onActionRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var criminalid = record.criminalid;
            var s = "";
            s = '<a class="Edit_Button" href="javascript:addContent(\'' + criminalid + '\')" >单个报备</a>&nbsp;&nbsp;';
            /**var sllock = mini.get("sllock").getValue();
            if(sllock==''||sllock=='0') {
				var s = '<a class="Edit_Button" href="javascript:chakanInfo(\'' + criminalid + '\')" >基本信息</a>&nbsp;&nbsp;'
		        	  + '<a class="Edit_Button" href="javascript:lockCriminal()" >加锁</a>&nbsp;&nbsp;'
            } else if(sllock=='1'){
            	var s = '<a class="Edit_Button" href="javascript:chakanInfo(\'' + criminalid + '\')" >基本信息</a>&nbsp;&nbsp;'
            		  + '<a class="Edit_Button" href="javascript:unLockCriminal()" >解锁</a>&nbsp;&nbsp;'
            } else if(sllock=='2'){
            	var s = '<a class="Edit_Button" href="javascript:chakanInfo(\'' + criminalid + '\')" >基本信息</a>&nbsp;&nbsp;'
            }*/
            return s;
        }
        
        function lockCriminal() {
	    	var rows = grid.getSelecteds();
	     	if (rows.length > 0) {
	        	if (confirm("确定为选中记录加锁？")) {
	            	var ids = [];
	             	for (var i = 0, l = rows.length; i < l; i++) {
	                	var r = rows[i];
	               		ids.push(r.criminalid);
	               		if(r.sanleifan==2) {
	               			alert("已解锁不能再次加锁");
	               			grid.reload();
	               			return;
	               		}
	             	}
	        		var id = ids.join(',');
	              	grid.loading("操作中，请稍后......");	                    
	           		$.ajax({
	                	url: "lockCriminal.action?1=1&id=" + id,
	                	type: "post",
	                 	success: function (text) {
	                 		grid.reload();
	              		},
	          			error: function () {
	          			
	          			}
	         		});
	    		}
	    	} else {
	        	alert("请选中一条记录");
	    	}
		}
		
        function unLockCriminal() {
	    	var rows = grid.getSelecteds();
	     	if (rows.length > 0) {
	        	if (confirm("确定为选中记录加锁？")) {
	            	var ids = [];
	             	for (var i = 0, l = rows.length; i < l; i++) {
	                	var r = rows[i];
	               		ids.push(r.criminalid);
	             	}
	        		var id = ids.join(',');
	              	grid.loading("操作中，请稍后......");	                    
	           		$.ajax({
	                	url: "lockCriminal.action?1=1&type=unlock&id=" + id,
	                	type: "post",
	                 	success: function (text) {
	                 		grid.reload();
	              		},
	          			error: function () {
	          			
	          			}
	         		});
	    		}
	    	} else {
	        	alert("请选中一条记录");
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
			var row = grid.getSelected();
			var tempid = row.criminalid+"gkzx_gkzx"+id+"gkzx_gkzx"+442500;
            if (row) { //6541
                mini.open({
                   url:"ruJianDjFormAction.action?choosecriminal=yes&menuid=8328&operatetype=view&id="+tempid,
                    title: "罪犯信息", width: 900, height: 550,
                    showMaxButton: true,
                    allowResize: false, 
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { mymenuid : "8328" , myoperatetype:'view'};
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

        //根据入监时间查询罪犯
         function onSearch() {
          //  var sdid = mini.get("sdid").getValue();
            var key = mini.get("key").getValue();
            key =  encodeURI(encodeURI(key));
            var anfanleibie = mini.get("anfanleibie").getValue("true");
            var url = "getAllSlCriminal.action?1=1&anfanleibie="+anfanleibie+"&key=" + key;
            grid.setUrl(url);
            grid.load();
        }
        
        function onKeyEnter(e) {
            onSearch();
        }
        //报备到 法院
        function addContent(text){
               var rows = grid.getSelecteds();
               if(rows.length==0){
               	alert(confirmMessages);
               	return;
               }
          if(confirm("确认报备吗?")){
               if (rows.length>0) { 
               		var crimis = [];
               		for (var i = 0, l = rows.length; i < l; i++) {
	                        var r = rows[i];
                        	crimis.push(r.criminalid);
	                }
	                var crimi = crimis.join(',');
	                $.ajax({
	                url:'addFYBaoBei.action?1=1',
	                type:'POST',
	                data:{data:crimi},
	                success:function(text){
	                     if(text == '1'){
	                          alert('添加成功!');
	                          grid.load();
	                     }else{
	                          alert('添加失败!');
	                     }
	                 },
	                 error:function(){
	                      alert('添加失败、请联系管理员!');
	                 }
	            });
               }
          }else{
            
          } 
        }
    </script>
</body>
</html>