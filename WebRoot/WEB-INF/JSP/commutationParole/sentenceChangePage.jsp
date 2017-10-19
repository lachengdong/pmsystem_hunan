<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitionbatchReviewal.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
	<title>法院裁定</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  	<script src="<%=path %>/scripts/boot.js" type="text/javascript"></script>
  	<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; 
	    }     
    </style>
</head>
<body onload="init('${menuid}');">
	<div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
     	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
              <tr>
                <td style="width:100%;">
                    <input id="courtsanction" name="courtsanction"  class="mini-hidden"  dateFormat="yyyyMMdd" />
               	 	<input id="exectime" name="exectime"  class="mini-hidden"  dateFormat="yyyyMMdd"/>
               	 	<input id="courtname" name="courtname" class="mini-hidden"  />
               	 	<input id="courtnamecode" name="courtnamecode"   class="mini-hidden"  />
               	 	<input id="courttitle"  name="courttitle"  style="width:100px;" class="mini-hidden" />
                	<input id="curyear" name="curyear"  class="mini-hidden"  width="40"/>
                	<input id="courtshort" name="courtshort"  class="mini-hidden"   width="80"/>
                    <a class="mini-button" iconCls="icon-edit" onclick="add()" plain="true" style="width:90px">参数设置</a>
                    <input id="content" name="content" allowInput="true" class="mini-textbox" width="650px" readonly="readonly"/>
               	 	<a class="mini-button" iconCls="icon-save" onclick="save()" plain="true" style="width:60px">保存</a>
                	
                </td>
                <td style="white-space:nowrap;">
                   <input class="mini-combobox"  id="year"  name="year"  style="width:60px;"   emptyText="年度" showNullItem="true" url="getDateUPDownArea.action?1=1" />
				   <input name="jianqu" id="jianqu" name="jianqu" class="mini-combobox" style="width:90px;" valueField="ORGID" textField="NAME"
					emptyText="请选择监区" url="/pmsystem/getDepartList.action?1=1&qtype=jianqu" required="false" showNullItem="true" nullItemText="请选择监区" onvaluechanged="search"/> 
                                                                   批次<input class="mini-spinner" id="batchid"  name="batchid"  style="width:40px;" minValue="1" maxValue="20000" value="${bathcid}" />
                   <input id="key" class="mini-textbox" vtype="maxLength:40;" emptyText="编号/姓名/拼音" style="width:110px;" onenter="onKeyEnter"/>
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                </td>
              </tr>
		</table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;"  oncellcommitedit="OnCellCommitEdit"
	    	class="mini-datagrid"  allowResize="false"  url="getSentenceChangeByBatch.json?1=1" idField="" multiSelect="true" allowAlternating="true" allowCellSelect="true" 
	    	sizeList="[10,20,50,100,200,300]" pageSize="20"  showLoading="true" allowSortColumn="true" allowCellEdit="true" multiSelect="true" allowResize="true" 
	    	editNextOnEnterKey="true"  editNextRowCell="true">
	        <div property="columns">
	            <div type="checkcolumn" width="15"></div>
              	<div type="indexcolumn" width="15" headerAlign="center">序号</div>
              	<div field="batchid" width="15" headerAlign="center" align="center" allowSort="true" >批次</div>
              	<div field="flowdraftid" width="15" headerAlign="center" align="center" allowSort="true"  visible="false">草稿ID</div>
              	<div field="anjianhao" width="20" headerAlign="center" align="center" allowSort="true">案件号</div>
              	<div field="crimid" width="32" headerAlign="center" align="center" allowSort="true">罪犯编号</div>
				<div field="crimname" width="20" headerAlign="center" align="center" allowSort="true">姓名</div>
				<div field="orgname1" width="25" headerAlign="center" align="center" allowSort="true">所在部门</div>
				<div field="category"  width="25px" headerAlign="center"  align="center" allowSort="true" renderer="onActionCategoryType">变动类型</div>
				<div field="xianxingqi"  width="33px" headerAlign="center"  align="center" allowSort="true" renderer="onActionExamineType">现刑期</div>
				<div field="oldcourtchangeto"  width="40px" headerAlign="center"  align="center" allowSort="true" renderer="onActionDateType">现刑期止日</div>
	    		<div field="courtchange"  width="35px" headerAlign="center"  align="center" allowSort="true"　 renderer="onActionExamineType">变动幅度
	    		    <input  property="editor" class="mini-textbox"  style="width:100%;"   />
	    		</div>
	    		<div field="sentence"  width="35px" headerAlign="center"  align="center" allowSort="true" renderer="onActionExamineType">变后刑期
	    		</div>
	    		<div field="losepower" width="25px" headerAlign="center"  align="center" allowSort="true" renderer="onActionExamineType">变后剥权
	    			<input  property="editor" class="mini-textbox"  style="width:100%;"   />
	    		</div>
	    		<div field="courtchangefrom"  width="40px" headerAlign="center"  align="center" allowSort="true" renderer="onActionDateType">变后起日
	    		</div>
	    		<div field="courtchangeto" width="40px" headerAlign="center"  align="center" allowSort="true" renderer="onActionDateType">变后止日
	    		</div>
	    		<div field="courtsn" width="25px" headerAlign="center"  align="center" allowSort="true">裁定字号
	    			<input  property="editor" class="mini-spinner"  minValue="1" maxValue="20000"  style="width:100%;"   />
	    		</div>
	    		<div field="awardend" width="40px" headerAlign="center"  align="center" allowSort="true" renderer="onActionDateType">考核止日
	    		</div>
	        </div>
	    </div>
    </div>
   <script type="text/javascript">
        mini.parse();
   		var datagrid = mini.get("datagrid");
        datagrid.load();
        datagrid.sortBy("casenos", "desc");

        function save(){
        	var row = datagrid.getSelecteds();
	 		var json = mini.encode(row);
	 		var courtnamecode = mini.get("courtnamecode").getValue();
	 		var courtname = mini.get("courtname").getValue();
	 		var courttitle = mini.get("courttitle").getValue();
 			var courtsanction = mini.get("courtsanction").getValue();
 			var exectime = mini.get("exectime").getValue();
 			var curyear = mini.get("curyear").getValue();
 			var courtshort = mini.get("courtshort").getValue();
 			
 			if(! courtnamecode){
 				alert("请先设置裁定法院信息！");
 				return;
 			}
 			
	 		if(row) {
		 		$.ajax({
	                url: "<%=path%>/saveChangeSentenceChangeForHuNan.action?1=1",
	                data: {data:json,courtnamecode:courtnamecode,courtname:courtname,courttitle:courttitle,
	                		courtsanction:courtsanction,exectime:exectime,curyear:curyear,courtshort:courtshort},
	                type: "post",
	                success: function (text) {
	                	alert("操作成功");
	                    datagrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert("操作失败");
	                }
	            });
			}else {
				alert("请选中至少一条数据!");
			}
	 	}
        
        function saveAll(){
	 		var data = datagrid.getSelecteds();
	 		var json = mini.encode(data);
	 		var courtnamecode = mini.get("courtnamecode").getValue();
	 		var courtname = mini.get("courtname").getValue();
	 		var courttitle = mini.get("courttitle").getValue();
 			var courtsanction = mini.get("courtsanction").getValue();
 			var exectime = mini.get("exectime").getValue();
 			var curyear = mini.get("curyear").getValue();
 			var courtshort = mini.get("courtshort").getValue();
	 		if (data.length>0) {
		 		$.ajax({
	                url: "<%=path%>/saveChangeSentenceChangeForHuNan.action?1=1",
	                data: { data:json,courtnamecode:courtnamecode,courtname:courtname,courttitle:courttitle,
	                		courtsanction:courtsanction,exectime:exectime,curyear:curyear,courtshort:courtshort},
	                type: "post",
	                success: function (text) {
	                	alert("操作成功");
	                    datagrid.reload();
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert("操作失败");
	                }
	            });
			}else {
				alert("请至少选择一条");
			}
	 		
	 	}
	 	function BuYu(){
	 	    var rows = datagrid.getSelecteds();//获取选中记录
			if(rows.length>0){
				if(confirm('确定解除选中记录吗？')){
					var ids=[];
					for(var i=0;i<rows.length;i++){
						var r=rows[i];
						ids.push(r.flowdraftid);
					}
					var id=ids.join(',');
					
					$.ajax({
						url:"BuYuJianJiaByCrimid.action?1=1",
						data:{id:id},
						type:"post",
						success:function(text){
							alert("操作成功");
							datagrid.reload();
						},
						error: function(){
							alert("操作失败");
							return;
						}
					});
				}
			}else{
				alert("请至少选中一条记录");
			}
	 	}
	 	//弹出减刑幅度修改窗口
	 	function onButtonEdit(e){
            var row = datagrid.getSelected();
            mini.open({
                url:"<%=path%>/toEditFuduPage.page",
                title: "选择幅度",
                width: 480,
                height: 270,
                onload: function () {
                	var iframe = this.getIFrameEl();
                    var data = {curyear:row.curyear,curmonth:row.curmonth,curday:row.curday,boquan:row.boquan};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                	if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        datagrid.cancelEdit();
                        var row = datagrid.getSelected();
                        datagrid.updateRow(row, {
                        	commuterange_id: data.fuduid,
                        	commuterange: data.fuduname
                        });
                    }
                }
            });
        }
	 	
	 	function onActionExamineType(e){
        	var record = e.record,
		    field = e.field,
		    value = e.value;
		    var s = "";
		    if(value == 9996){
                s = "死刑，缓期二年执行 ";
            }else if(value == 9995){
                 s = "无期";
            }else if(value == 97){
            	s= "终身";
            }else if(value == 0){
            	s= "";
            }else{
            	var year = value.substr(0,2);
            	var month = value.substr(2,2);
            	var day = value.substr(4.2);
            	if(year > 0){
	            	if(year >= 10){
	            		s += year + "年";
	            	}else{
	            		s += year.replace('0','') + "年";
	            	}
            	}
            	if(month > 0){
            		if(month >= 10){
                		s += month + "个月";
                	}else{
                		s += month.replace('0','') + "个月";
                	}
            	}
            	if(day > 0){
	            	if(day >= 10){
	            		s += day + "天";
	            	}else{
	            		s += day.replace('0','') + "天";
	            	}
            	}
				
            }
            return s;
        }

	 	function onActionDateType(e){
        	var record = e.record,
		    field = e.field,
		    value = e.value;
		    var s = "";
		    if(value != ""){
		    	var year = value.substr(0,4);
            	var month = value.substr(4,2);
            	var day = value.substr(6.2);
            	if(year >= 0){
            		s += year + "年";
            	}
            	if(month > 0){
            		if(month >= 10){
                		s += month + "月";
                	}else{
                		s += month.replace('0','') + "月";
                	}
            	}
            	if(day > 0){
	            	if(day >= 10){
	            		s += day + "日";
	            	}else{
	            		s += day.replace('0','') + "日";
	            	}
            	}
		    }
            return s;
        }
	 	
	 	function onActionCategoryType(e){
        	var record = e.record,
		    field = e.field,
		    value = e.value;
		    var s = "";
		    if(value != ""){
            	if(value == "15"){
            		s = "假释";
            	}else{
            		s = "减刑";
            	}
		    }
            return s;
        }
	 	
	 	
	 	function onKeyEnter(){
	 		search();
	 	}
	 	
	 	function add() {
	 		var url = "<%=path%>/caiDingSheZhiPage.page";
	 		mini.open({
                url: url,
                title: "裁定法院信息设置",
                width: 400,
                height: 450,
                onload: function () {
                	var iframe = this.getIFrameEl();
                    var data = {};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                	if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);   //必须
                        
                        mini.get("courtnamecode").setValue(data[0]);
        	 			mini.get("courtname").setValue(data[1]);
        	 			mini.get("courttitle").setValue(data[2]);
        	 			mini.get("courtsanction").setValue(data[3]);
        	 			mini.get("exectime").setValue(data[4]);
        	 			mini.get("curyear").setValue(data[5]);
        	 			mini.get("courtshort").setValue(data[6]);
        	 			mini.get("content").setValue("裁定时间："+data[3]+"　执行时间："+data[4]+"　"+data[1]+data[2]+"（"+data[5]+"）"+data[6]);
                    }
                }
            });
	 		
	 	}
	 		
	 	function search(){
	 		var year = mini.get("year").getValue();
	 		var batchid = mini.get("batchid").getValue();
	 	  	var key = mini.get("key").getValue();
	 	  	datagrid.load({ key:key,batchid:batchid,year:year});
	 	}
	 	
	 	
	 	function OnCellCommitEdit(e){
	 		var multiplicand = Math.pow(10,2);
        	var grid = e.sender;
        	var record = e.record,
	        column = e.column,
	        field = e.field,
	        value = e.value;
        	var xianxingqi = record.xianxingqi; //现刑期
        	var courtchange = record.courtchange;//变动幅度
        	var sentence = record.sentence;//变后刑期
        	var courtchangefrom = record.courtchangefrom;//变厚起日
        	var courtchangeto = record.courtchangeto;//变后止日
        	var oldcourtchangeto = record.oldcourtchangeto;//现刑期止日
        	
        	if (column.field == "xianxingqi") {
        		xianxingqi = value;
            }
        	if (column.field == "courtchange") {
        		courtchange = value;
            }
        	if (column.field == "sentence") {
        		sentence = value;
            }
        	if (column.field == "courtchangefrom") {
        		courtchangefrom = value;
            }
        	if (column.field == "courtchangeto") {
        		courtchangeto = value;
            }
        	//首先 变动幅度不允许为空
        	if(courtchange){
        		if(xianxingqi == '9995'){
        			//如果现刑期是无期，则变后刑期等于变动幅度
        			sentence = courtchange;
        		}else if(courtchange == '9995' || courtchange == '9996'){
        			//如果变动幅度是无期/死缓，则变后刑期为无期/死缓
        			sentence = courtchange;
        		}else{
        			//变后刑期 = 现刑期 - 变动幅度   
        			sentence = formatdate(xianxingqi,courtchange);
        		}
        		if(oldcourtchangeto){
        			//如果之前已经减刑过有期徒刑
        			//变后止日 = 现刑期止日 - 变动幅度
            		courtchangeto = subDateMonth(oldcourtchangeto,courtchange);
        		}else{
        			//变后止日 = 变后起日 + 变动幅度 - 1天
            		courtchangeto = formatdate2(courtchangefrom,sentence);
        		}
        		
        		
        		record.sentence=sentence;
        		record.courtchangeto = courtchangeto;
        	}
	 	 }	 	 
	 	function formatdate(startdatestr,enddatestr){
	 		//日期格式为六位  102000
	 		var newdatestr = '';
	 		var startyear = startdatestr.substring(0,2);
			var startmonth = startdatestr.substring(2,4);
			var startday = startdatestr.substring(4,6);
			var endyear = enddatestr.substring(0,2);
			var endmonth = enddatestr.substring(2,4);
			var endday = enddatestr.substring(4,6);
			var months = Number((Number(Number(startyear)*12)+Number(startmonth))) - Number((Number(Number(endyear)*12)+Number(endmonth)));
			var newyear =  parseInt(months/12)+"";
			var newmonth = Number(months) - Number(Number(newyear)*12)+"";
			
			if(newyear >= 10){
				newdatestr = newyear;
			}else{
				newdatestr = '0'+newyear;
			}
			if(newmonth >= 10){
				newdatestr = newdatestr+newmonth;
			}else{
				newdatestr = newdatestr+'0'+newmonth;
			}
			newdatestr = newdatestr+startday;
			return newdatestr;
	 	}
	 	//两个日期相加
	 	function formatdate2(startdatestr,enddatestr){
	 		//startdatestr格式：  20160101
	 		//enddatestr格式：  101000(十年十个月)
	 		var newdatestr = '';
	 		var startyear = startdatestr.substring(0,4);
			var startmonth = startdatestr.substring(4,6);
			var startday = startdatestr.substring(6,8);
			var newstartdatestr = startyear+'-'+startmonth+'-'+startday;
			var endyear = enddatestr.substring(0,2);
			var endmonth = enddatestr.substring(2,4);
			var endday = enddatestr.substring(4,6);
			var months = Number(Number(endyear)*12)+Number(endmonth);
			
			newdatestr = addDateMonth(newstartdatestr,months);
			return newdatestr;
	 	}
	 	
	 	//两个日期相减
	 	function formatdate3(startdatestr,enddatestr){
	 		//startdatestr格式：  20160101
	 		//enddatestr格式：  101000(十年十个月)
	 		var newdatestr = '';
	 		var startyear = startdatestr.substring(0,4);
			var startmonth = startdatestr.substring(4,6);
			var startday = startdatestr.substring(6,8);
			var newstartdatestr = startyear+'-'+startmonth+'-'+startday;
			var endyear = enddatestr.substring(0,2);
			var endmonth = enddatestr.substring(2,4);
			var endday = enddatestr.substring(4,6);
			var months = Number(Number(endyear)*12*-1)+Number(endmonth*-1);
			newdatestr = addDateMonth4(newstartdatestr,months,endday);
			return newdatestr;
	 	}
	 	
	 	//其中，date参数是要进行加减的日期，格式YYYY-MM-DD，days参数是要加减的天数，如果往前算就传入负数，往后算就传入正数，如果是要进行月份的加减，
		//就调用setMonth()和getMonth（）就可以了，需要注意的是返回的月份是从0开始计算的，也就是说返回的月份要比实际月份少一个月，因此要相应的加上1。
	 	function addDateMonth(date,months){ 
		 	var d=new Date(date); 
		 	d.setMonth(d.getMonth()+months);    
		 	d.setDate(d.getDate()-1);
		 	var month=d.getMonth()+1; 
		 	var day = d.getDate(); 
		 	if(month<10){ 
		 	month = "0"+month; 
		 	} 
		 	if(day<10){ 
		 	day = "0"+day; 
		 	} 
		 	var val = d.getFullYear()+""+month+""+day; 
		 	return val; 
	 	}
	 	
	 	function addDateMonth3(date,months){ 
		 	var d=new Date(date); 
		 	d.setMonth(d.getMonth()+months); 
		 	d.setDate(d.getDate());
		 	var month=d.getMonth()+1; 
		 	var day = d.getDate(); 
		 	if(month<10){ 
		 	  month = "0"+month; 
		 	} 
		 	if(day<10){ 
		 	  day = "0"+day; 
		 	} 
		 	var val = d.getFullYear()+""+month+""+day; 
		 	return val; 
	 	}
	 	
	 	function addDateMonth4(date,months,endday){ 
	 		var d=new Date(date); 
	 		var day = d.getDate(); 
	 		var year = d.getYear();
	 		var month = d.getMonth()+1;
	 		var endday = NUMBER(endday);
	 		if(day > "28"){
	 			if(isLeapYear(year)){
	 				
	 			}
	 			if(day > "30"){
	 				
	 			}else{
	 				
	 			}
	 		}
	 		
		 	d.setMonth(d.getMonth()+months); 
		 	var month=d.getMonth()+1; 
		 	if(month<10){ 
		 	month = "0"+month; 
		 	} 
		 	if(day<10){ 
		 	day = "0"+day; 
		 	} 
		 	var newdate = d.getFullYear()+"-"+month+"-"+day; 
	        var returnvalue = addDateMonth5(newdate,Number(endday));
	        return returnvalue; 
	 	}
	 	
	 	function addDateMonth5(date,days){ 
	 		var d=new Date(date); 
	 		alert(date);
		 	d.setDate(d.getDate()-days);
		 	var month=d.getMonth()+1; 
		 	var day = d.getDate(); 
		 	if(month<10){ 
		 	  month = "0"+month; 
		 	} 
		 	if(day<10){ 
		 	  day = "0"+day; 
		 	} 
		 	var val = d.getFullYear()+""+month+""+day; 
		 	return val; 
	 	}
	 	
	 	function subDateMonth(startdatestr,enddatestr){
	 		//startdatestr格式：  20160101
	 		//enddatestr格式：  101000(十年十个月)
	 		var newdatestr = '';
	 		var startyear = Number(startdatestr.substring(0,4));
			var startmonth = Number(startdatestr.substring(4,6));
			var startday = Number(startdatestr.substring(6,8));
			var endyear = Number(enddatestr.substring(0,2));
			var endmonth = Number(enddatestr.substring(2,4));
			var newyear = ''; 
			var newday = '';
			var newmonth = (startmonth+12-endmonth)%12;
			if(endmonth>=startmonth){
				newyear = startyear-endyear-1;
			}else{
				newyear = startyear-endyear;
			}
			if(startday==31){
				if(newmonth==2){
					if(isLeapYear==1){
						newday = 29;
					}else{
						newday = 28;
					}
				}else if(newmonth==4||newmonth==6||newmonth==9||newmonth==11){
					newday = 30;
				}else{
					newday = 31;
				}
			}else if(startday==30){
				if(newmonth==2){
					if(isLeapYear==1){
						newday = 29;
					}else{
						newday = 28;
					}
				}
			}else{
				newday = startday;
			}
			if(newmonth<10){ 
				newmonth = "0"+newmonth; 
			}
			if(newmonth==0){ 
				newmonth = 12; 
			}
			newdatestr = newyear+""+newmonth+""+newday;
			return newdatestr;
	 	}
	 	//判断是否为闰年
	 	function isLeapYear(year) {  
	 		if((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)){
	 			return 1;
	 		}else{
	 			return 0;
	 		}
	 	}
	</script>  
</body>
</html>
