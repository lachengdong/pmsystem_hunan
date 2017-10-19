<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>罪犯互监修改页面</title>
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
<body>
	<div id="form1" class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
		<input id="id" name="id" class="mini-hidden" value="${id}"/>
		<input id="svtype" name="svtype" class="mini-hidden" value="${svtype}"/>
		<input id="filterCrimids" name="filterCrimids"  class="mini-hidden" value=""/>
		<table >
             <tr>
                <td style="width:100%;">
                	&nbsp;&nbsp;
                	编组时间：<input id="grouptime" name="grouptime" class="mini-datepicker" style="width:100px;" required="true" 
									format="yyyy-MM-dd"  /> &nbsp;&nbsp;
					互监类型：<input id="svtypename" name="svtypename" class="mini-textbox" emptyText="互监类型"  value="${svtypename}"
										allowInput="false" required="true" style="width:100px;"/> &nbsp;&nbsp;
							 	
					第<input id="pknum" name="pknum" class="mini-textbox"  allowInput="false" required="true" 
							value="${pknum}" style="width:50px;"/>包联组&nbsp;&nbsp;
							
					包联干警<input id="policemen" name="policemen" class="mini-textbox" allowInput="false" style="width:100px;"required="true" value="${policemen}"/>
                </td>
                <td style="white-space:nowrap;"> 
                	<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
                	&nbsp;
                	<a class="mini-button" plain="true" iconCls="icon-save"  onclick="saveSurvision();">保存</a>
                	&nbsp;
					<a class="mini-button" iconCls="icon-cancel"  plain="true" onclick="Close();" >关闭</a>
                </td>
             </tr>
        </table>
	</div>  
	
	<div class="mini-fit" >
	  
	     <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" idField="id" allowCellEdit="true"allowCellSelect="true" allowAlternating="true"
	     	url="<%=path%>/getSurvisionDetailList.json?1=1&fid=${id}&svtype=${svtype}" showPager="false" onload="onGridLoad();">
         <div property="columns">
         	<div type="checkcolumn" width="20"></div>
            <div field="groupnum"  displayField="groupnum_name" width="40" headerAlign="center"  align="center">互监组数
                <!-- <input property="editor"  allowInput="false" class="mini-textbox" style="width:100%;"/> -->
            </div>
            <!--  
            <div field="prison"  displayField="prison" width="40" headerAlign="center"  align="center">监舍
                <input property="editor"  allowInput="false" class="mini-combobox" url="getCriminalPrisonData.json" valueField="prison" textField="prison" style="width:100%;"/>
            </div>  
            -->           
            <div field="crimid" width="40" headerAlign="center"  align="center"  displayField="criminal_name" >组长
                <input property="editor" class="mini-buttonedit"  allowInput="false"  onbuttonclick="onButtonEdit" style="width:100%;"/>
            </div> 
           <div field="crimids" width="120" headerAlign="center"  displayField="criminal_names" align="center"  >组员
                <input property="editor" class="mini-buttonedit"  allowInput="false"  onbuttonclick="onButtonEdit2" style="width:100%;"/> 
            </div>        
            <div cellCls="actionIcons" name="action" width="40" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>                                        
        </div>
    </div>   
  	</div>
<script type="text/javascript">
    mini.parse();
    mini.get("grouptime").setValue(new Date());
    var grid = mini.get("datagrid1");
    grid.load();
    
    function onGridLoad(){
    	var filterCrimids = "";
    	grid.selectAll();
    	var rows = grid.getSelecteds();
    	for(var i=0; i<rows.length; i++){
    		filterCrimids = mergeStr(filterCrimids, rows[i].crimid);
    		filterCrimids = mergeStr(filterCrimids, rows[i].crimids);
    	}
    	mini.get("filterCrimids").setValue(filterCrimids);
    }
    
	function mergeStr(pStr, str){
		if(pStr){
			pStr += "," + str;
		}else{
			pStr = str;
		}
		return pStr;
	}   
	
    grid.on("drawcell", function (e) {
        var row = e.record;
        if(row.groupid){
        	e.cellStyle = "background:#c4f1da";
        }
    });
    
    function SetData(data){
    	//data = mini.clone(data);
    	//mini.get("svtype").setValue(data.svtype);
    	//mini.get("svtypename").setValue(data.svtypename);
    	//mini.get("pknum").setValue(data.pknum);
    	//mini.get("policemen").setValue(data.policemen);
    }
    
	//保存
	function saveSurvision(){
		var svtype=mini.get("svtype").getValue();
		if(! svtype){
			alert("互监类型不能为空！");
			return;
		}
		 var form = new mini.Form("form1");
		 form.validate();
		 var flag = form.isValid();
		 if(! flag){
			 return;
		 }
		 var o = form.getData();
		 var formdata = mini.encode([o]);
		 
		 var rowsData = grid.getChanges();
         var griddata = mini.encode(rowsData);
         $.ajax({
             url: "<%=path %>/saveEditSanrenhujianData.json",
             data: { griddata:griddata, formdata:formdata},
             success: function (text){
            	 if('success' == text){
            		 alert("操作成功！");
            	 }else{
            		 alert("操作失败！");
            	 }
            	 Close();
             }
         });
	} 
	
	function newRow() { 
		var num = grid.getData().length+1;
		var groupnum_name = '第'+num+'互监组';     
        var row = {groupnum_name:groupnum_name, groupnum:num};
        grid.addRow(row);
        grid.beginEditRow(row);
    }
	
    function delRow(row_uid) {
         var row = grid.getRowByUID(row_uid);
         if (row) {
	         if(grid.getData().length>1){
	        	 if(row.groupid){
	        		 deleteSupervisionData(row.groupid)
	        	 }else{
	        		 grid.removeRow(row);
	        	 }
	         }else{
		         alert("至少保留一行，不能继续删除");
		         return;
	         }
      	 }
     }
    
    function deleteSupervisionData(groupids){
    	
    	if(confirm("确定要删除选中的互监组吗？")){
    		var url = "<%=path%>/deleteSupervisionData.json?1=1";
    		$.ajax({
	            url: url,
	            data: {groupids:groupids},
	            dataType:'text',
	            type: 'POST',
	            async:false,
	            success: function (text){
	            	if(text && parseInt(text) > 0){
	            		alert("操作成功！");
	            	}else{
	            		alert("操作失败！");
	            	}
	            },
	            error: function (jqXHR, textStatus, errorThrown){
	            	alert("操作失败！");
	            }
	        });
    		
    		refreshPage();
    	}
    	
    }
    
    function onActionRenderer(e) {
        //var grid = e.sender;
      var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;
        var s = '<span class="icon-remove" title="删除记录" onclick="delRow('+uid+')"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
        		+'<span class="icon-new" title="增加记录" onclick="newRow()"></span>';
        		
        //if(record.groupid){
    	//	s += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon-edit" title="修改记录" onclick="editRow()"></span>';
        //}
      return s;
    }
      
    function removeInFilterCrimids(crimids){
  		var filterCrimids = mini.get("filterCrimids").getValue();
  		if(filterCrimids && crimids){
  			var newFilterCrimidArr = [];
  			var filterCrimidArr = filterCrimids.split(",");
  			for(var i=0,l=filterCrimidArr.length; i<l; i++){
  				if(crimids.indexOf(filterCrimidArr[i])<0){
  					newFilterCrimidArr.push(filterCrimidArr[i]);
  				}
  			}
  			var newFilterCrimids = newFilterCrimidArr.join(',');
  			mini.get("filterCrimids").setValue(newFilterCrimids);
  		}
  	}
    
    function delCrimidsInPcrimids(pcrimids, crimids){
  		if(pcrimids && crimids){
  			var newPcrimidArr = [];
  			var pcrimidArr = pcrimids.split(",");
  			for(var i=0,l=pcrimidArr.length; i<l; i++){
  				if(crimids.indexOf(pcrimidArr[i])<0){
  					newPcrimidArr.push(pcrimidArr[i]);
  				}
  			}
  			var newPcrimids = newPcrimidArr.join(',');
  			return newPcrimids;
  		}
  		return pcrimids;
  	}
  	
  	function addInFilterCrimids(crimids){
  		if(crimids){
  			var newFilterCrimids = '';
  			var filterCrimids = mini.get("filterCrimids").getValue();
  			if(filterCrimids){
  				newFilterCrimids = filterCrimids + "," + crimids;
  			}else{
  				newFilterCrimids = crimids;
  			}
  			mini.get("filterCrimids").setValue(newFilterCrimids);
  		}
  	}
  	
      function onButtonEdit(e) {
            var row = grid.getSelected();
            var groupid = row.groupid;
            if(groupid){
            	editSurvisionZuZhang();
            }else{
            	newSurvisionZuZhang();
            }
        }
      
      function newSurvisionZuZhang(){
    	  	var url = "<%=path%>/toSelectCrimeZuzhangPage.action?1=1";
         var svtype = mini.get('svtype').getValue();
          	url = url +"&svtype="+svtype;
          	var filterCrimids = mini.get("filterCrimids").getValue();
           	if(filterCrimids){
           		//filterCrimids = delCrimidsInPcrimids(filterCrimids, row.crimid);
           		url = url + "&filterCrimids="+filterCrimids;
           	}
           	mini.open({
                   url : url,
                   title: "选择组长",
                   width: 800,
                   height: 450,
                   onload: function () {
 			//          var iframe = this.getIFrameEl();
 			//          iframe.contentWindow.SetData(null);
                   },
                   ondestroy: function (action) {
                       if (action == "ok") {
                           var iframe = this.getIFrameEl();
                           var data = iframe.contentWindow.GetData();
                           data = mini.clone(data);    //必须
   						  grid.cancelEdit();
                           var row = grid.getSelected();
                           removeInFilterCrimids(row.crimid);
                           addInFilterCrimids(data.crimid);
                           
                           grid.updateRow(row, {
                               crimid: data.crimid,
                               criminal_name: data.name
                           });
                       }else if(action == "clear"){
   						grid.cancelEdit();
                           var row = grid.getSelected();
                           removeInFilterCrimids(row.crimid);
                           grid.updateRow(row, {
                           	crimid: '',
                           	criminal_name: ''
                           });
                       }
                   }
               });
      }
      
         function onButtonEdit2(e){
        	 var row = grid.getSelected();
             var groupid = row.groupid;
             if(groupid){
            	 editSurvisionZuYuan(groupid)
             }else{
            	 newSurvisionZuYuan(row);
             }
        }
         
         function newSurvisionZuYuan(row){
        	 	var url = "<%=path%>/toSelectCrimeZuyuanPage2.page?1=1";
        	 	var svtype = mini.get('svtype').getValue(); //互监类型
          	    url = url + "&svtype="+svtype;
        	 	
          	    var row = grid.getSelected();
          	    var filterCrimids = mini.get("filterCrimids").getValue();
          	    if(filterCrimids){
          		    //filterCrimids = delCrimidsInPcrimids(filterCrimids, row.crimids);
          	 	    url = url + "&filterCrimids="+filterCrimids + "&choosedCrimids="+row.crimids;
          	    }
            	  
         		 mini.open({
                      url : url,
                      title: "选择组员",
                      width: 800,
                      height: 450,
                      onload: function (){
//                          var iframe = this.getIFrameEl();
//                          iframe.contentWindow.SetData(null);
                      },
                      ondestroy: function (action) {
                          if (action == "ok") {
                              var iframe = this.getIFrameEl();
                              var data = iframe.contentWindow.GetData();
                              data = mini.clone(data);    //必须
      						 grid.cancelEdit();
      						 var row = grid.getSelected();
                              removeInFilterCrimids(row.crimids);
                              addInFilterCrimids(data.crimids);
                             
                              grid.updateRow(row, {
                             	 crimids: data.crimids,
                             	 criminal_names: data.names
                              });
                              
                          }else if(action == "clear"){
                         	 grid.cancelEdit();
                              var row = grid.getSelected();
                              removeInFilterCrimids(row.crimids);
                              
                              grid.updateRow(row, {
                             	 crimids: '',
                             	 criminal_names: ''
                              });
                          }
                      }
                  });
        }
         
         
         function editSurvisionZuZhang(){
        	 var row = grid.getSelected();
        	 var id = mini.get("id").getValue();
        	 var groupid = row.groupid;
        	 var groupnum = row.groupnum;
        	 var svtype = mini.get("svtype").getValue();
			 var type = "edit";
			 
             var url = "<%=path%>/toEditCrimeZuzhangPage.page?1=1";
             
          	 url += "&id="+id;
          	 url += "&groupid="+groupid;
          	 url += "&groupnum="+groupnum;
          	 url += "&svtype="+svtype;
          	 url += "&type="+type;
           	mini.open({
                   url : url,
                   title: "选择组长",
                   width: 800,
                   height: 450,
                   ondestroy: function (action) {
                	   refreshPage();
                	   //grid.reload();
                   }
               });
         }
         
         function editSurvisionZuYuan(groupid){
        	 
        	 var row = grid.getSelected();
        	 var id = mini.get("id").getValue();
        	 var groupid = row.groupid;
        	 var groupnum = row.groupnum;
        	 var svtype = mini.get("svtype").getValue();
			 var type = "edit";
			 
             var url = "<%=path%>/toEditCrimeZuYuanPage2.page?1=1";
             
          	 url += "&id="+id;
          	 url += "&groupid="+groupid;
          	 url += "&groupnum="+groupnum;
          	 url += "&svtype="+svtype;
          	 url += "&type="+type;
          	 url += "&choosedCrimids="+row.crimids
          	 url += "&filterCrimids="+row.crimids
          	 
          	 
           	mini.open({
                   url : url,
                   title: "选择组员",
                   width: 800,
                   height: 450,
                   ondestroy: function (action) {
                	   refreshPage();
                	   //grid.reload();
                   }
               });
         }
         
         
         function CloseWindow(action){ 
             if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
             else window.close();
         };
         
         
          //手动关闭
     function Close(){
    	 	CloseWindow("cancel");
       }
          
  		// 刷新本页面
		function refreshPage(){
			//
			if(!window["____refreshPage"]){
				window["____refreshPage"] = true;
				//
				location.reload();
			} else {
				window.setTimeout(function(){
					window["____refreshPage"] = false;
					},1*1000);
			}
		};
		
</script>
</body>
</html>