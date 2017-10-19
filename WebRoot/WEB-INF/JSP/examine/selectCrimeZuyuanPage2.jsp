<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯互监组员选择页面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    
     <style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   		}    
   		.mini-datagrid .mini-panel-border{
	    	border-left:0;
	    	border-right:0;
	    	border-bottom:0;
	    }
	    .mini-datagrid .mini-grid-border{
	    	border-left:0;
	    	border-right:0;
	    	border-bottom:0;
	    }
    </style> 
    <script type="text/javascript">
		var confirmMessage = "请选中一条记录！";
     	var confirmMessages = "请至少选中一条记录！";
	</script>
</head>
<body>
    <div id="div_one" class="mini-splitter"  style="width:100%;height:100%;">
    	<div showCollapseButton="false" style="padding:0px;">
	        <div class="mini-toolbar" style="border:0px;padding:0px;">
	        
	        	<input id="choosedCrimids" value="${choosedCrimids}"  class="mini-hidden" /><!-- 本页面已选择的罪犯编号 -->
	        	<input id="choosedCriminalname" name="choosedCriminalname" class="mini-hidden" /><!-- 本页面已选择的罪犯姓名 -->
	        	<input id="tempdata" value="${filterCrimids}"  class="mini-hidden" /> <!-- 所有已选择的罪犯，包括本页的已选择的罪犯 -->
	        	<input id="svtype" value="${svtype}"  class="mini-hidden" />
	        	
	            <table style="width:100%;">
	                <tr align="right">
	                    <td style="white-space:nowrap;">
	                    	<input class="mini-combobox" style="width:70px;" url="getCriminalPrisonData.json" id="prison" name="prison" emptyText="监舍"
                		nullItemText="--全部--"  valueField="prison" textField="prison" value="" showNullItem="true" allowInput="false" onValueChanged="search();"/>
	                    	
	                    	<input id="orgid1" class="mini-combobox"   valueField="ORGID" textField="NAME"   emptyText="监区" showNullItem="true"
	                             nullItemText="--全部--" url="getDepartList.action?1=1" style="width:70px;" popupWidth="100" onvaluechanged="search();"/>
	                        <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号、姓名" style="width:70px;" onenter="search();"/>   
	                        <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">查询</a>
	                        <span class="separator"></span> 
		                    <a class="mini-button" iconCls="icon-downgrade"  plain="true" onclick="batchAdd()">批量选中</a>
	                    </td>
	                </tr>
	            </table>           
	        </div>
	        
		    <div class="mini-fit" id="div_two"  style="width:100%;height:100%;">
			    <div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]"
			        url=""  idField="crimid" multiSelect="true"  allowAlternating="true"  virtualScroll="true" showPageSize="true" >
			        <div property="columns">
			            <div type="checkcolumn" width="10"></div>        
			            <div field="crimid" width="30" headerAlign="center"  align="center" allowSort="true">罪犯编号</div>    
			            <div field="name" width="30" headerAlign="center"  align="center" allowSort="true">姓名</div>
			            <div field="maincase" width="50" headerAlign="center"  align="center" allowSort="true">罪名</div>
			            <div field="" width="20" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
			        </div>
			    </div>
		  </div>
	</div>
	
	<div showCollapseButton="false" size="50%;">
				<div class="mini-toolbar" style="border:0;padding:0px;">
		            <table  style="width:100%;">
		                <tr align="left">
		                	<td align="left">
		                        <a class="mini-button" iconCls="icon-upgrade"  plain="true" onclick="batchCancel();">批量取消</a>
		                    </td>
		                    <td align="right">
		                    	<a class="mini-button" iconCls="icon-no"  plain="true" onclick="onClear();">清空</a>
		                    	<a class="mini-button" iconCls="icon-ok"  plain="true" onclick="onOK();">确认</a>
		                    </td>
		                </tr>
		            </table>           
	       		 </div>
	         <div class="mini-fit" id="div_two"  style="width:100%;height:100%;" >
		         	<div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;"                     
	                    idField="crimid"  multiSelect="true" showFooter="true"   sizeList="[10,20,50,100]"
	                    allowCellEdit="true" allowCellSelect="true" showPager="false">
                    <div property="columns">
                        <div type="checkcolumn" width="10"></div>
                        <div field="crimid" width="30" headerAlign="center" align="center"  >罪犯编号</div>
                        <div field="name" width="30"   headerAlign="center" align="center"  >姓名</div>
                        <div field="maincase" width="50" headerAlign="center"  align="center" allowSort="true">罪名</div>
                    </div>
                </div>
	       </div>
	</div>
	
 </div>
 
    <script type="text/javascript">
    	mini.parse();
    	var grid = mini.get("datagrid1");
		var grid2 = mini.get("datagrid2");
		//grid.load();
		
		function gridReload(paraMap){
	    	var tempdata = mini.get("tempdata").getValue();
	    	var svtype = mini.get("svtype").getValue();
	    	var url = "<%=path %>/getHujianCrimeDataList.action?1=1&svtype="+svtype;
		   if(tempdata){
		   		url += "&filterCrimids="+tempdata
		   	}
		   	grid.setUrl(url);
		   	if(paraMap){
		   		grid.load(paraMap);
		   	}else{
		   		grid.load();
		   	}
	    }
		
		gridReload();
		
    	var ids = mini.get("choosedCrimids").getValue();
    	if(ids){
    		var url = "<%=path%>/ajaxGetSelectedCriminal.json?1=1";
    		$.ajax({
    			type:"post",
    			url: url,
    			data:{ids:ids},
    			cache:false,
    			async:false,
    			success:function (text){
    				if(text){
    					var rows = mini.decode(text);
    					grid2.addRows(rows);
    				}
    			},
    			error: function (jqXHR, textStatus, errorThrown) {
    		    }
    		})
    	}
    	//处理表单传递过来的数据   -----end
    	
		
        //批量添加
      function batchAdd(){
	        var rows = grid.getSelecteds();
	        operateAdd(rows);
		}
        
    	//单个添加
      function singleAdd(uid){
    	  	var row = grid.getRowByUID(uid);
	      	var rows = [];
	      	rows.push(row);
	        operateAdd(rows);
		}
        
        function operateAdd(rows){
	        if(rows.length > 0){
            	var tempdata= mini.get("tempdata").getValue();
      	        for(var i = 0, l = rows.length; i < l; i++){
      	            var r = rows[i];
      	            if(tempdata.indexOf(r.crimid)<0){
      		            if(tempdata.length > 0){
      		        	    tempdata+=","+r.crimid;
      		            }else{
      		        	    tempdata = r.crimid;
      		            }
      	            }
      	        }
      	        mini.get("tempdata").setValue(tempdata);
      	        
      	        grid2.addRows(rows);
      	        gridReload();
      	        
            }else{
            	alert(confirmMessages);
            }
        }
        
		
		//批量取消
      function batchCancel() {
        	var rows = grid2.getSelecteds();
        	operateCancel(rows);
        	gridReload();
        }
        
		//单个取消
		function singleCancel(){
	        var row = grid2.getSelected();
	        var rows = [];
	        rows.push(row);
            //grid2.removeRow(items);
            // grid.addRow(items);
            operateCancel(rows);
        	gridReload();
		}
		
		function operateCancel(rows){
			if(rows && rows.length>0){
				var tCrimids = [];
				for(var i=0; i< rows.length; i++){
					var row = rows[i];
					if(row.crimid){
						tCrimids.push(row.crimid);
	           	 	}
				}	//for结束
				
				tCrimids = tCrimids.join(",");
				var tempdata = mini.get("tempdata").getValue();
               	var tempdata2 = "";
               	if(tempdata){
               		var crimids = tempdata.split(",");
               		for(var i=0;i<crimids.length;i++){
               			if(tCrimids.indexOf(crimids[i]) <0){
               				 tempdata2 += ","+crimids[i];
               			 }
               		}
               		if(tempdata2 && tempdata2.length >=2){
               			tempdata2 = tempdata2.substr(1);
               		}else{
               			tempdata2 = "";
               		}
               		
               		if(tempdata2){
               			tempdata2 += "," + tCrimids;
               		}else{
               			tempdata2 = tCrimids;
               		}
               	}
               	mini.get("tempdata").setValue(tempdata2);
               	
                grid2.removeRows(rows);
	        }else{
            	alert(confirmMessages);
            }
		}
		
		//确定
		function onOK(){
			grid2.selectAll();
			var rows = grid2.getSelecteds();
			if(rows){
				var crimidArr = [];
				var criminalnameArr = [];
				for(var i=0, l=rows.length; i<l; i++){
					crimidArr.push(rows[i].crimid);
					criminalnameArr.push(rows[i].name);
				}
				mini.get("choosedCrimids").setValue(crimidArr.join(","));
				mini.get("choosedCriminalname").setValue(criminalnameArr.join(","));
			}else{
				mini.get("choosedCrimids").setValue("");
				mini.get("choosedCriminalname").setValue("");
			}
			
			CloseWindow("ok");
		}
		
		
		function GetData(){
			var crimids = mini.get("choosedCrimids").getValue();
			var names = mini.get("choosedCriminalname").getValue();
			
			var data = {crimids:crimids, names:names};
			return data;
		}
		
		function onClear(){
	    	CloseWindow("clear");
	    }
		
		function cancel() {	
	   		CloseWindow("cancel");
	    } 
		
		function onActionRenderer(e) {
        	var record = e.record;
            var uid = record._uid;
         	var s = "<a class=\"New_Button\" href=\"javascript:singleAdd("+uid+");\">添加</a>";
         	//var s = '<a class="New_Button"  href="javascript:singleAdd('+uid+')">添加</a>';
         return s;
        }
	   
	   function CloseWindow(action) {            
	         if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	         else window.close();            
	   } 
	   
	   function search(){
           var key = mini.get("key").getValue();
           var selectOrgid = mini.get("orgid1").getValue(); 
           var prison = mini.get("prison").getValue(); 
           grid.sortBy("crimid","desc");
           var paraMap = { key: key, prison:prison, selectOrgid:selectOrgid};
           gridReload(paraMap);
		}
		
	/////////////////////////////////////////////////////////////////////
		
    </script>
 </body>
</html>