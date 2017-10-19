<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
	    .actionIcons span
        {
            width:16px;
            height:16px;
            display:inline-block;
            background-position:50% 50%;
            cursor:pointer;
        }
    </style>
</head>
<body >
	<div id="div_one" class="mini-splitter"  style="width:100%;height:100%;">
    	<div showCollapseButton="false" style="padding:0px;">
	        <div id="tb1" class="mini-toolbar"  style="padding:2px;border:0;" align="right">
                <input class="mini-textbox" id="key" name="key" style="width:150px;" emptyText="编号、姓名、拼音" onenter="onKeyEnter" />
                <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search();">快速查询</a>
			</div>  
			<div class="mini-fit" >
			    <div id="datagrid1" class="mini-datagrid"  style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
			        url="<%=path %>/getTransferCrimeDataList.action" idField=""  multiSelect="true" allowAlternating="true"  virtualScroll="true">
			        <div property="columns">
			            <div type="checkcolumn"></div>
			            <div field="crimid" width="60" headerAlign="center" align="center">罪犯编号</div>   
						<div field="name" width="60" headerAlign="center" align="center" >罪犯姓名</div>   
						<div field="inprisondate" width="60" headerAlign="center" align="center" >入监时间</div>	    
			            <div field="jianqu" width="60" headerAlign="center" align="center">监区</div>
			        </div>
			    </div>
		  	</div>
		</div>
		
		<div showCollapseButton="false" size="600px;">
			 <div id="tb2" class="mini-toolbar"  style="padding:2px;border:0;" align="right">
			 	<input id="tempdata" value=""  class="mini-hidden"/>
				<table style="width:100%;height:100%">
					<tr>
						<td>
						<!--  
						申请时间：<input id="applytime" name="applytime" class="mini-datepicker" style="width:100px;" 
											format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>
						申请部门：<input id="applyunit" name="applyunit" class="mini-textbox" emptyText="部门" value="${orgname}" style="width:80px;"/>
						申请人：<input id="applypeople" name="applypeople" class="mini-textbox" emptyText="申请人" value="${username}" style="width:80px;"/>
						-->
						<a class="mini-button" plain="true" iconCls="icon-node"  onclick="save()">确定</a>
						<a class="mini-button" iconCls="icon-close"  plain="true" onclick="cancel()" >关闭</a>
						</td>
					</tr>
					<tr>
						<td>
							<textarea  id="transferparticulars" name="transferparticulars"  class="mini-textarea" style="width:100%;height:60px;" emptyText="调动原因"></textarea>
						</td>
					</tr>
				</table>
			 </div>  
			 
			 <div class="mini-fit" >
			     <div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%;" idField="id" allowCellEdit="true"  allowCellSelect="true" showPager="false" multiSelect="true">
			        <div property="columns">
			            <div type="checkcolumn" width="20" ></div>  
			            <div field="newunit"  width="40" headerAlign="center"  align="center">目标单位
			                 <input name="departname" property="editor" class="mini-combobox" textField="NAME" valueField="NAME" value="ORGID"  style="width:100%;" url="getDepartList.action?1=1&type=all&removeself=yes"/>   
			            </div>                
			            <div field="crimids" width="100" headerAlign="center" displayField="crimids_name" align="center" style="width:100%;height:100%" >被调动服刑人员</div> 
			            <div field="num" width="20" headerAlign="center"  align="center" style="width:100%;height:100%" >调动人数</div>       
			            <div cellCls="actionIcons" name="action" width="40" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>                                        
			        </div>
		    	 </div>   
		  	 </div>
		</div>
 	</div>

	
<script type="text/javascript">
	mini.parse();
	
    var grid = mini.get("datagrid1");
    grid.sortBy("crimid","desc");
	grid.load();
	
	var grid2 = mini.get("datagrid2");
	var row = {};
    grid2.addRow(row);
    grid2.beginEditRow(row);
    grid2.selectAll();
    
    
        
    //mini.get("applytime").setValue(new Date());
    var deptInfos;//所有的监区名称和监区ID
     $.get("getDepartList.action?1=1&type=all",function(data,status){ //请求出所有的监区名称和监区ID
    	 deptInfos = data ;
      });
    
   function getDeptIdByName(name){
   		for(var i = 0 ; i<deptInfos.length ; i++){
   			if(deptInfos[i].NAME == name){
   				return deptInfos[i].ORGID ;
   			}
    	}
    }
    
    function save(){
    	var transferparticulars = mini.get("transferparticulars").getValue(); //获取调动原因
    	if(!transferparticulars){
    		alert("请填写调动原因！");
    		return ;
    	}
    	//var applyunit = mini.get("applyunit").getValue();
    	//var applypeople = mini.get("applypeople").getValue();
    	//if(!applyunit) {
    	//	alert("请输入申请部门！");
    	//	return ;
    	//}
    	//if(!applypeople) {
    	//	alert("请输入申请人！");
    	//	return ;
    	//}
    	
    	grid2.selectAll();
    	var rows = grid2.getSelecteds (); //获取所有被选的行的信息
    	for(var i = 0 ; i < rows.length ; i++){
    		var r = rows[i];
    		var newunit = r.newunit;
    		var crimids = r.crimids;
    		if(! r.newunit){ //判断是否选择目标单位
    			grid2.deselectAll();
    			grid2.select(r);
        		alert("请选择目标单位！");
        		return ;
        	}  
        	if(! r.crimids){ //判断是否选择调动组
        		grid2.deselectAll();
    			grid2.select(r);
        		alert("请选择调动人员！");
        		return ;
        	}
    	}
    	CloseWindow("ok");
    }
    
	//保存
	function GetData(){
    	var rows = grid2.getSelecteds(); //获取所有被选的行的信息
    	var map = {}; //用于存放传往表单的信息
    	var transferparticulars = mini.get("transferparticulars").getValue();
    	map["gaizaobiaoxian"] = transferparticulars;
        //map["mapkey8"]="";
    	//map["mapkey21"]="";
    	//map["worktype"]="";
        //map["acceptdate"]  ="";
    	//map["mapkey23"] = "" ;
    	//map["mapkey8"]="";
    	//map["mapkey22"] = "" ;
    	//map["gaizaobiaoxian"] = "" ;
    	//map["alertdate1"] = getDateInfos("1");
    	//map["paymentzk"]= getDateInfos("1"); //--对应的是时间
    	//map["paymentpc"]= mini.get("applyunit").getValue(); //--对应的是申请部门
    	//map["paymentcc"]= mini.get("applypeople").getValue(); //对应的是申请人员
    	
    	map["popup_ptm"] = "　　因"+transferparticulars+"，" ;
    	var tValue = "";
    	for(var i = 0 ; i < rows.length ; i++){	
    		tValue += rows[i].crimids + "@" + rows[i].crimids_name + "@" + rows[i].num +"@" + getDeptIdByName(rows[i].newunit) +"@" + rows[i].newunit;
			map["popup_ptm"] += "拟将"+rows[i].crimids_name+"等"+rows[i].num+"人调往"+rows[i].newunit;
    		if(i != rows.length-1){
    			tValue += ";";
		      	map["popup_ptm"] += "；" ;		
			}else{
			    map["popup_ptm"] += "。" ; 
			}
   		} //for
   		map["transferdata"] = tValue;
        return map;
    }
	
	
    
	function newRow() { 
        var row = {};
        grid2.addRow(row);
        grid2.beginEditRow(row);
        grid2.deselectAll();
        grid2.select(row);
    }
	
    function delRow(row_uid){
         var row = grid2.getRowByUID(row_uid);
         if (row) {
             if(grid2.getData().length>1){
            	 if(row.crimids){
            		 var tCrimids = row.crimids.join(",");
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
                	 }
                	 mini.get("tempdata").setValue(tempdata2)
            	 }
            	 
            	 var tempdata = mini.get("tempdata").getValue();
            	 if(tempdata){
            		 var url = "<%=path %>/getTransferCrimeDataList.action?1=1&notInCrimid="+tempdata;
            		 grid.setUrl(url);
            		 grid.reload();
            	 }
            	 
                 grid2.removeRow(row);
                 
             }else{
               	 alert("至少保留一行，不能继续删除");
                 return;
             }
         }
     }
    
    function onActionRenderer(e){
        var record = e.record;
        var uid = record._uid;
        var s = '<span class="icon-remove" title="删除记录" onclick="delRow('+uid+')"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        		+'<span class="icon-new" title="增加记录" onclick="newRow()"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        		+'<span class="icon-ok" title="添加罪犯" onclick="onButtonEdit('+uid+')"></span>';
        return s;
    }

    function search() {
        var key = mini.get("key").getValue();
        //key = encodeURI(key);
        grid.sortBy("crimid","desc");
        grid.load({ key: key });
    }
    
    function onKeyEnter(){
    	search();
    }
   
    function onButtonEdit(row_uid){
      var rows = grid.getSelecteds();
      var row2 = grid2.getRowByUID(row_uid);
      if(row2.newunit){
       	  var crimids = [];
	      var names = [];
	      var tempdata= mini.get("tempdata").getValue();
	      for(var i = 0, l = rows.length; i < l; i++){
	          var r = rows[i];
	          if(tempdata.indexOf(r.crimid)<0){
		       	  crimids.push(r.crimid);  
		          names.push(r.name);
		          if(tempdata.length > 0){
		        	  tempdata+=","+r.crimid;
		          }else{
		        	  tempdata = r.crimid;
		          }
	          }else{
	        	  alert("罪犯"+r.name+"已在列表");
	        	  return;
	          }
	      }
	      mini.get("tempdata").setValue(tempdata);
	      
	      var num = crimids.length;
	      if(row2.crimids){  
	      	  if(row2.num){
	      		  num += parseInt(row2.num);
	      	  }
	      	  crimids.push(row2.crimids);
	          names.push(row2.crimids_name);  
		  } 
		  if(crimids.length>0){
		  	   //grid.removeRows(rows);//移除选中的数据
		  	  var tempdata = mini.get("tempdata").getValue();
        	   if(tempdata){
        		   var url = "<%=path %>/getTransferCrimeDataList.action?1=1&notInCrimid="+tempdata;
        		   grid.setUrl(url);
        		   grid.reload();
        	   }
        	   
		  	   grid2.updateRow(row2, {
		           crimids: crimids,
		           crimids_name: names,
		           num:num
	           });
		  }else{
			  alert("请至少选择一条数据");
		  }
      }else{
		  alert("请选择目标单位");
	  }
   }
    
    function cancel() {	
   		CloseWindow("cancel");
    } 
    
    function CloseWindow(action){
         if (window.CloseOwnerWindow) {
        	 return window.CloseOwnerWindow(action);
         }else{
        	 window.close();
         }
    }
    
    /*
	    function getDateInfos(fmSimple){
	   	    var applytime =  mini.get("applytime").getText();
		    var year = applytime.split("-")[0];
		    var month = applytime.split("-")[1];
		    var day = applytime.split("-")[2];
		    if(fmSimple =="0")
		        var dateinfos = year+"年"+month+"月"+day+"日";
		    else 
		    	var dateinfos = year+month+day ;
	    	return dateinfos ;
	    }
    */
</script>
</body>
</html>