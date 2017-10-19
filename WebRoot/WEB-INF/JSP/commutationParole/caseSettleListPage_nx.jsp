<%@ page language="java" pageEncoding="UTF-8"  %>
<%@page import="java.util.List"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" /> 
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>案件整理(宁夏)</title> 
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body >
       <div class="mini-toolbar"  style="padding:2px;border-bottom:0px;">
            <table >
               <tr>
                <td style="width:100%;">
                <!-- <s:property value="#request.menuid"/> -->
<%--                   	<a class="mini-button" iconCls="icon-save" onclick="savechangeanjianhao()" plain="true">存盘</a>--%>
<%--                   	<span class="separator"></span> --%> 
                                          罪犯类型<input id="zftype"  name="zftype" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                            style="width:70px" emptyText="罪犯类型" data="FloderTypeZF" />
	                         <span class="separator"></span>                     
                                          案件类型<input id="type"  name="type" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                            style="width:70px" emptyText="案件类型" data="FloderType" />
	                         <span class="separator"></span>
	                                  刑种类型<input id="xztype"  name="xztype" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                            style="width:70px" emptyText="刑种类型" data="FloderTypeXZ" />
                   	起始案件号<input id="anjianhao"  class="mini-spinner" style="width:60px;" emptyText="更改起始案件号"  minValue="" maxValue="20000"/>
                   	
<%--                 	<a class="mini-button"  plain="true" iconCls="icon-edit" plain="true"	onclick="changeanjianhao()">批量更改</a>--%>
                 	<a class="mini-button"  plain="true" iconCls="icon-edit" plain="true"	onclick="batchupdatecasenumber()">批量更改</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="caseyear" class="mini-combobox" style="width:80px;display: none;" emptyText="年份" url="<%=path %>/getDateUPDownArea.action" value="${year}"  onenter="onKeyEnter"/>
                    <input class="mini-textbox" id="casenums" class="mini-textbox" style="width:200px;display: none;" emptyText="案件号范围 例如：1，8-37"  onenter="onKeyEnter"/>
                    <input class="mini-textbox" id="key" class="mini-textbox"  emptyText="请输入罪犯编号、姓名、拼音"  onenter="onKeyEnter"/>
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">快速查询</a>&nbsp;
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10095')"></a>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" allowCellEdit="true" style="width:100%;height:100%;" class="mini-datagrid"   url="<%=path%>/getCaseCriminalInfo.json?approval=yes" 
	     idField="" allowCellEdit="true" allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true" 
	      allowAlternating="true"  sizeList="[10,20,50,100]" pageSize="20">
	        <div property="columns">
	        		<div type="checkcolumn" width="15"></div> 
		            <div visible="false" field="ccid" name="ccid" id="ccid"  headerAlign="center" allowSort="true" align="center" >
               			<input property="editor" class="mini-textbox" style="width:100%;" />
               		</div>  
               		<div visible="false" field="criminalid" name="criminalid" id="criminalid"  headerAlign="center" allowSort="true" align="center"  >
               			<input property="editor" class="mini-textbox" style="width:100%;" />
               		</div>  
           			<div field="applyid"   headerAlign="center" allowSort="true" align="center" readOnly="true">罪犯编号
               			<input property="editor" class="mini-textbox" style="width:100%;" />
               		</div>  
               		<div field="applyname"   headerAlign="center" allowSort="true" align="center" readOnly="true">姓名
               			<input property="editor" class="mini-textbox" style="width:100%;" />
               		</div>
               		<div field="areaname"   headerAlign="center" allowSort="false" align="center" readOnly="true" >监区
               			<input property="editor" class="mini-textbox" style="width:100%;" />
               		</div>
               		<div field="casetype"   headerAlign="center" allowSort="true" align="center" readOnly="true" >案件号
               			<input property="editor" class="mini-textbox" style="width:100%;" />
               		</div>
               		 <div field="casenumber" headerAlign="center" align="center" allowSort="false" style="background: #50F0B4;">案号(可编辑)
		                <input property="editor" class="mini-spinner" style="text-align: center;" minValue="0" maxValue="20000"/>
		            </div>  
	        </div>
	    </div>
	    </div>
	    <div showCollapseButton="false">
			<div id="showContent" style="height:30px;overflow:auto;display:none;background:#EFF2F5;color:#3789B8;text-align:center;">
	         <script language="JavaScript" src="<%=path%>/scripts/form/loadaip.js"></script>
	         </div> 
    </div> 
    <script type="text/javascript">
            var FloderTypeZF = [{ id: 0, text: '普通犯' },{ id: 1, text: '三类犯' },{ id: 1, text: '病残犯' }];
            var FloderType = [{ id: 0, text: '减刑' },{ id: 1, text: '假释' }];
            var FloderTypeXZ = [{ id: 0, text: '有期' },{ id: 1, text: '无期' },{ id: 1, text: '死缓' }];
		    mini.parse();
			var grid= mini.get("datagrid");
			grid.sortBy("anjianhao","desc");
			grid.load();
		    grid.on("drawcell",function(e){
				     var field = e.field;
		             if (field == "casenumber" ) {
		                	e.cellStyle = "background:#D9E7F8";
		             }
			});
			//快速 查询
			function search() {
		         var caseyear = mini.get("caseyear").getValue();
		         var casenums = mini.get("casenums").getValue();
		         if(caseyear != ''){
		        	 if(caseyear.length != 4){
	                     alert("年份输入不规范:"+caseyear);return;
				     }
			     }
		         var type = mini.get("type").getValue();
		         var key = mini.get("key").getValue();
		         mini.get("datagrid").load({ key: key,caseyear: caseyear,casenums:casenums,type:type});
		    }
	        function onKeyEnter(e) {
	            search();
	        }
	        $("#key").bind("keydown", function (e) {
	            if (e.keyCode == 13) {
	                search();
	            }
	        });
            //批量更改案件号
            //批量更改案件号 其实是不需要选择罪犯的，单个更改的时候在选择
            //批量更改:首先按照监区进行排序，然后在按照监区里面提交时间顺序进行修改
            //减刑的和假释的是否分开。
            function batchupdatecasenumber(){
                var type = mini.get("type").getValue();
                var qishi=mini.get("anjianhao").getValue();
                var zftype = mini.get("zftype").getValue();
                var xztype = mini.get("xztype").getValue();
                //有一类不选择均不可以
                if((parseInt(type) > -1) && (parseInt(zftype) > -1) && (parseInt(xztype) > -1)){
                    $.ajax({
                        type:'post',
                        url:'<%=path%>/batchUpdateCaseNumber.action?1=1&type='+type+'&qishi='+qishi,
                        success:function (text){
                             alert("更改成功!");
                        }
                    });
                }else{
                    alert("请选择罪犯类型/案件类型/刑种类型!");
                }
            }
	        
	        function changeanjianhao(){
	        	var qishi=mini.get("anjianhao").getValue();
        		var grid= mini.get("datagrid");
	            var rows = grid.getSelecteds();
	            if (rows.length > 0) {
	            	var notice = rows.length==1?'确定更改案件号？':'确定批量更改案件号？';
		            if (confirm(notice)) {
		            	var ids = [];
		            	 for (var i = 0, l = rows.length; i < l; i++) {
	                        var r = rows[i];
	                        ids.push(r.flowdraftid);
	                        $.ajax({
	                        url:"<%=path%>/commutationParole/ajaxGetAnnexcontent.json?1=1",
	                        data: { flowdraftid:r.flowdraftid,parolenumber:qishi+i,text6:r.text6},
	                         type: 'POST',
	                        success: function (text) {
	                        	//var objs = eval('('+text+')');
	                        	//var aipObj = document.getElementById("HWPostil1");
  	  							//aipObj.CloseDoc(0);
  	  							//aipObj.LoadFileBase64(objs.aipFileStr);
  	  							//if(aipObj.IsOpened()){
  	  							//	var parolenumber = objs.parolenumber;
  	  							//	aipObj.setValue("Page1.parolenumber","");
  	  							//	aipObj.setValue("Page1.parolenumber",parolenumber);
  	  							//	aipObj.SleepSecond(1);
									//保存表单
								//	var newaipFileStr=aipObj.GetCurrFileBase64();
								//	$.ajax({
							    //         url: "<%=path%>/commutationParole/ajaxSaveAnnexcontent.json?1=1",
							    //        data: {flowdraftid:r.flowdraftid,aipFileStr:newaipFileStr},
							    //         contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
							    //        cache: false,
							    //         type: 'POST',
							    //         success: function (text) {
							             	
							    //         },
							    //         error: function (jqXHR, textStatus, errorThrown) {
							    //            alert(errorThrown);
							    //         }
							    //     });
  	  							//}
	                        },
	                        error: function () {
	                        }
	                    });
	                    }
		            }
		            grid.reload();
	        	}else{
	        		alert("请选择要更改的案件！");
	        	}
	        	
	        }
	        function savechangeanjianhao(){
	        	var grid = mini.get("datagrid");
	            var data = grid.getChanges();
	            if (data.length > 0) {
	            	var notice = data.length==1?'确定更改案件号？':'确定批量更改案件号？';
		            if (confirm(notice)) {
		            	 for (var i = 0, l = data.length; i < l; i++) {
	                        var r = data[i];
	                        $.ajax({
	                        url:"<%=path%>/commutationParole/ajaxGetAnnexcontent.json?1=1",
	                        data: {flowdraftid:r.flowdraftid,parolenumber:r.casenumber,text6:r.text6},
	                         type: 'POST',
	                        success: function (text) {
	                        	//var objs = eval('('+text+')');
	                        	//var aipObj = document.getElementById("HWPostil1");
  	  							//aipObj.CloseDoc(0);
  	  							//aipObj.LoadFileBase64(objs.aipFileStr);
  	  							//if(aipObj.IsOpened()){
  	  							//	var parolenumber = objs.parolenumber;
  	  							//	aipObj.setValue("Page1.parolenumber","");
  	  							//	aipObj.setValue("Page1.parolenumber",parolenumber);
  	  							//	aipObj.SleepSecond(1);
									//保存表单
								//	var newaipFileStr=aipObj.GetCurrFileBase64();
								//	$.ajax({
							    //         url: "<%=path%>/commutationParole/ajaxSaveAnnexcontent.json?1=1",
							    //         data: {flowdraftid:r.flowdraftid,aipFileStr:newaipFileStr},
							    //         contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
							    //        cache: false,
							    //         type: 'POST',
							    //         success: function (text) {
							    //         	
							    //         },
							    //         error: function (jqXHR, textStatus, errorThrown) {
							    //             alert(errorThrown);
							    //         }
							    //     });
  	  							//}
	                        },
	                        error: function () {
	                        	
	                        }
	                    });
	               }
	            }
	            }
	            grid.reload();
	        }
		</script> 
</body>
</html>