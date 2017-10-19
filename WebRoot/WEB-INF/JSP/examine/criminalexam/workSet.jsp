<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>劳动考勤管理</title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden; font-size: 12px;
		    }   
		</style>
	</head>

	<body>
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<table style="width:100%;">
	           <tr>
	               <td style="width:100%;">
	                    <input class="mini-combobox" data="wtype" id="wtype" value="√" style="width:70px"/>
	                    <a class="mini-button" iconCls="icon-edit" onclick="saveData()" plain="true">单个设置</a>
	                    <span class="separator"></span>
	            
	                  	<input class="mini-textbox" data="batchop" id="batchop" emptyText="输入1,5-10等"  style="width:100px;"/>
	                  	<input class="mini-combobox" data="wtype" id="batchwtype" value="√" style="width:70px;"/>
	                  	<a class="mini-button" iconCls="icon-ok" onclick="batchSearch()" plain="true">批量设置</a> 
	                    <span class="separator"></span>
	                    <!-- <input class="mini-combobox" style="width:85px;" url="<%=path%>/getCriminalPrisonData.json" id="prison" name="prison" emptyText="请选择监舍"
                        nullItemText="--全部--"  valueField="prison" textField="prison" value="" showNullItem="true" allowInput="false" onValueChanged="search();"/> -->
	                    <input id="editdate" name="editdate" style="width:8%;*+.width:8%;" class="mini-monthpicker" onvaluechanged="search();" value="${ecwdate}" format="yyyy-MM"/>
	                    <input id="key" class="mini-textbox" emptyText="编号或姓名" style="width:85px; font-size: 12px;" onenter="search();"/>
	                    <a class="mini-button" iconCls="icon-search" onclick="search();">查询</a>
	                  	<span style="font-size: 12px;color:red">备注：出工:'√';&nbsp;禁闭或严管:'Ⅹ';&nbsp;病假='O';&nbsp;未出工='△';&nbsp;休息:'□'</span>          
	               </td>
	           </tr>
	       </table>  
		</div>
		
		<div class="mini-fit">
			<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="true" sizeList="[10,20,50,100,200,500]" pageSize="20"
		        url="sxexamineWorkSetData.json?1=1" idField="id" allowCellEdit="true" allowCellSelect="true"  showPager="false"
		        multiSelect="true" editNextOnEnterKey="true"  editNextRowCell="true">
		        <div property="columns">
		            <div type="checkcolumn" width="20"></div>
		            <div type="indexcolumn">序号</div>
		            <div field="crimid" headerAlign="center" width="60px" align="center" allowSort="true"  onrowdblclick="editrow">罪犯编号</div>
		            <div field="name" width="40px" headerAlign="center" align="center" allowSort="true" >姓名</div>
		            <div field="yeardate" width="0" headerAlign="center" allowSort="true" >日期</div> 
		            <div name="DAY01"  field="day01" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >1
		                <input property="editor" id="day01" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('01')"/>
		            </div>
		            <div name="DAY02" field="day02" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >2
		                <input property="editor" id="day02"  class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('02')"/>
		            </div>
		            <div name="DAY03" field="day03" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >3
		                <input property="editor" id="day03"  class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('03')"/>
		            </div>
		            <div name="DAY04" field="day04" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >4
		                <input property="editor" id="day04" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('04')"/>
		            </div>
		            <div name="DAY05"  field="day05" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >5
		                <input property="editor" id="day05" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('05')"/>
		            </div>
		            <div name="DAY06"  field="day06" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >6
		                <input property="editor" id="day06" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('06')"/>
		            </div>
		            <div name="DAY07"  field="day07" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >7
		                <input property="editor" id="day07" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('07')"/>
		            </div>
		            <div name="DAY08"  field="day08" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >8
		                <input property="editor" id="day08" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('08')"/>
		            </div>
		            <div name="DAY09"  field="day09" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >9
		                <input property="editor" id="day09" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('09')"/>
		            </div>
		            <div name="DAY10"  field="day10" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >10
		                <input property="editor" id="day10" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('10')"/>
		            </div>
		            <div name="DAY11"  field="day11" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >11
		                <input property="editor" id="day11" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('11')"/>
		            </div>
		            <div name="DAY12"  field="day12" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >12
		                <input property="editor" id="day12" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('12')"/>
		            </div>
		            <div name="DAY13"  field="day13" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >13
		                <input property="editor" id="day13" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('13')"/>
		            </div>
		            <div name="DAY14"  field="day14" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >14
		                <input property="editor" id="day14" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('14')"/>
		            </div>
		            <div name="DAY15"  field="day15" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer">15
		                <input property="editor" id="day15" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('15')"/>
		            </div>
		            <div name="DAY16"  field="day16" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >16
		                <input property="editor" id="day16" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('16')"/>
		            </div>
		            <div name="DAY17"  field="day17" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >17
		                <input property="editor" id="day17" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('17')"/>
		            </div>
		            <div name="DAY18"  field="day18" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer">18
		                <input property="editor" id="day18" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('18')"/>
		            </div>
		            <div name="DAY19"  field="day19" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >19
		                <input property="editor" id="day19" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('19')"/>
		            </div>
		            <div name="DAY20"  field="day20" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >20
		                <input property="editor" id="day20" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('20')"/>
		            </div>
		            <div name="DAY21"  field="day21" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >21
		                <input property="editor" id="day21" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('21')"/>
		            </div>
		            <div name="DAY22"  field="day22" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >22
		                <input property="editor" id="day22" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('22')"/>
		            </div>
		            <div name="DAY23"  field="day23" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >23
		                <input property="editor" id="day23" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('23')"/>
		            </div>
		            <div name="DAY25"  field="day24" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >24
		                <input property="editor" id="day24" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('24')"/>
		            </div>
		            <div name="DAY25"  field="day25" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >25
		                <input property="editor" id="day25" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('25')"/>
		            </div>
		            <div name="DAY26"  field="day26" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >26
		                <input property="editor" id="day26" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120"  onvaluechanged="danSheZhi('26')"/>
		            </div>
		            <div name="DAY27"  field="day27" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >27
		                <input property="editor" id="day27" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('27')"/>
		            </div>
		            <div name="DAY28"  field="day28" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >28
		                <input property="editor" id="day28" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('28')"/>
		            </div>
		            <div name="DAY29"  field="day29" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >29
		                <input property="editor" id="day29" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('29')"/>
		            </div>
		            <div name="DAY30"  field="day30" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >30
		                <input property="editor" id="day30" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('30')"/>
		            </div>
		            <div name="DAY31"  field="day31" headerAlign="center" align="center" width="18px" renderer="onTypeRenderer" >31
		            	<input property="editor" id="day31" class="mini-combobox" data="TypeData" popupWidth="80"  popupHeight="120" onvaluechanged="danSheZhi('31')"/>
		            </div>                     
		        </div>
		    </div>
		</div>
		
		<script type="text/javascript">
			var Genders = [{ id: 1, text: '男' }, { id: 2, text: '女'}];
	        //var batchop = [{ id: 1, text: '1日'},{id: 2, text: '2日'},{id: 3, text: '3日'},{id: 4, text: '4日'},{id: 5, text: '5日'},{id: 6, text: '6日'},{id: 7, text: '7日'}
	        //,{id: 8, text: '8日'},{id: 9, text: '9日'},{id: 10, text: '10日'},{id: 11, text: '11日'},{id: 12, text: '12日'},{id: 13, text: '13日'},{id: 14, text: '14日'},{id: 15, text: '15日'}
	        //,{id: 16, text: '16日'},{id: 17, text: '17日'},{id: 18, text: '18日'},{id: 19, text: '19日'},{id: 20, text: '20日'}
	        //,{id: 21, text: '21日'},{id: 22, text: '22日'},{id: 23, text: '23日'},{id: 24, text: '24日'},{id: 25, text: '25日'}
	       // ,{id: 26, text: '26日'},{id: 27, text: '27日'},{id: 28, text: '28日'},{id: 29, text: '29日'},{id: 30, text: '30日'},{id: 31, text: '31日'}];
	        
	        var wtype = [{ id: '√', text: '出工' },{ id: 'Ⅹ', text: '禁闭或严管' },{ id: 'O', text: '病假' },{ id: '△', text: '未出工' },{ id: '□', text: '休息' }];
	        var TypeData = [{ id: '1', text: '√出工' },{ id: '2', text: 'Ⅹ禁闭或严管' },{ id: '3', text: 'O病假' },{ id: '4', text: '△未出工' },{ id: '5', text: '□休息' }];
	        var TypeData2 = [{ id: '1', text: '√' },{ id: '2', text: 'Ⅹ' },{ id: '3', text: 'O' },{ id: '4', text: '△' },{ id: '5', text: '□' }];
	       
	        mini.parse();
	        var grid = mini.get("datagrid1");
	        grid.load({yeardate:mini.formatDate(new Date(),'yyyy-MM')});

	        
	        function onKeyEnter(e) {
	            search();
	        }
	        function search() {
	        	var key=mini.get("key").getValue();
	        	var datetime = getYearMonth();
	        	grid.load({key:key,yeardate:datetime});
	        }
	        
	        function getYearMonth(){
	        	var batchdate = mini.get("editdate").getValue();
	        	var datetime="";
	        	if(batchdate){
		        	var time=new Date(batchdate);
		        	var year = time.getFullYear();  //获取年
		        	var month = time.getMonth() + 1;
		        	if((month+'').length==1){
		        		month='0'+month;
		        	}
	        		datetime = year+'-'+month;
	        	}
	        	return datetime;
	        }
	        
	        function saveData() {
	             var wtype=mini.get("wtype").getValue();//出勤类型
	         var editdate=mini.formatDate(mini.get("editdate").getValue(),'yyyy-MM');//出勤日期
	         var batchop = mini.get("batchop").getValue();
	        	 batchop=formatFanwei(batchop);//出勤日期天
	         var row = grid.getSelected();
	             var rows = grid.getSelecteds();
	             if(editdate == null||editdate == ''){
		         	editdate = mini.formatDate(new Date(),'yyyy-MM');
	             }
				 if(rows.length==0 ){
					alert("请选择一条记录！");
					return;
				 }else if(rows.length>1){
					alert("请选择一条记录！");
					return;
				 }
				 var Type = toType(wtype);
	             $.ajax({
	                  url: "<%=path%>/sxexamineCheckForWork/workSetAddOrUpdate.action?1=1",
	                  data: { crimid:row.crimid ,wtype:Type, editdate:editdate,batchop:batchop},
	                  type: "post",
	                  success: function (text) {
	                  	  alert("操作成功！");
	                      grid.reload();
	                  },
	                  error: function (jqXHR, textStatus, errorThrown) {
	                      alert(jqXHR.responseText);
	                  }
	              });
	        }
	        
	        grid.on("celleditenter", function (e) {
	            var index = grid.indexOf(e.record);
	            if (index == grid.getData().length - 1) {
	                var row = {};
	                grid.addRow(row);
	            }
	        });
	        function onTypeRenderer(e) {
	            for (var i = 0, l = TypeData2.length; i < l; i++) {
	                var g = TypeData2[i];
	                if (g.id == e.value) return g.text;
	            }
	            return "";
	        }
	        
	        function validateDays(days){
	        	days = days.replace(/\，/g,",");
    			var strArr = days.split(",");
    			var dayArr = [];
    			for(var i=0;i<strArr.length;i++){
    				var str = trim(strArr[i]);
    				if(str.indexOf("-")>-1){
    					var start = trim(str.split("-")[0]);
    					var end = trim(str.split("-")[1]);
    					dayArr.push(start);
    					dayArr.push(end);
    				}else{
    					dayArr.push(str);
    				}
    			}
    			
    			for(var i=0;i<dayArr.length;i++){
    				if(! isNum(dayArr[i])){
    					alert("输入的格式有错！");
    					return false;
    				}
    			}
    			return true;
	        }
	        
	        function formatFanwei(batchop){
	    		//fanwei = '1-31';
	    		if(batchop){
	    			var fanweilist = new Array();
	    			batchop= batchop.replace(/\，/g,",");
	    			var strArr = batchop.split(",");
	    			for(var i=0;i<strArr.length;i++){
	    				var str = trim(strArr[i]);
	    				if(str.indexOf("-")>-1){
	    					var start = trim(str.split("-")[0]);
	    					var end = trim(str.split("-")[1]);
	    					if(start && end){
	    						for(var j=0;j <= parseInt(end)-parseInt(start);j++){
	    							fanweilist.push(parseInt(start)+j);
	    						}
	    					}
	    				}else{
	    					fanweilist.push(str);
	    				}
	    			}
	    			return fanweilist.join(",");
	    		}
	    	}
	    	//var ab = formatFanwei();
	    	//alert(ab);
	    	/**
	    	* 删除左右两端的空格
	    	*/
	    	function trim(str)
	    	{
	    		 return str.replace(/(^\s*)|(\s*$)/g, '');
	    	}
	    	
	    	function isNum(s){
	    	     if (s!=null && s!=""){
	    	         return !isNaN(s);
	    	     }
	    	     return false;
	    	}
			
	    	function meaninglessOperate(){
	    		var opfg = ""; 
  	         	$.ajax({
            		url: "<%=path%>/sxexamineCheckForWork/meaninglessOperate.json?1=1",
            		dataType:"text",
            		async:false,
                	success: function (text) {
                		opfg = text;
                	},
                	error: function () {
                	}
            	}); 
  	         	
  	         	if(1==opfg){
  	         		//设置遮罩
  	  	         	grid.loading("操作中请稍后...");
  	         	}
	    	}
	    	
	    	
	        function batchSearch(){
	        	setTimeout(meaninglessOperate, 50);
	        	
				var wtype = mini.get("batchwtype").getValue();//出勤类型
				var batchop = mini.get("batchop").getValue();
				var flag = validateDays(batchop);
				if (!flag) {
					return false;
				}
				batchop = formatFanwei(batchop);//出勤日期天
				var editdata = mini.get("editdate").getFormValue();

				var rows = grid.getSelecteds();
				if (rows.length > 0) {
					var w_Type = toType(wtype);
					if (confirm("确定设置选中记录？")) {
						var ids = [];
						for (var i = 0; i < rows.length; i++) {
							var r = rows[i];
							ids.push(r.crimid);
							//years.push(editdate);
						}
						var id = ids.join(',');
						//var year = years.join(',');
						$.ajax({
							url : "workAddOrUpdate.action?1=1",
							data : {
								crimid : id,
								wtype : w_Type,
								yeardate : editdata,
								batchop : batchop
							},
							type : "post",
							async : "false",
							success : function(text) {
								grid.reload();
							},
							error : function(jqXHR, textStatus, errorThrown) {
								alert(jqXHR.responseText);
							}
						});

					}
				} else {
					alert("请至少选择一条记录！");
				}
				//setTimeout(func, 2000);
				//撤消遮罩
				grid.unmask();

			}

			function toType(wtype) {
				var w_Type = null;
				if (wtype == "√") {
					w_Type = "1";
				} else if (wtype == "Ⅹ") {
					w_Type = "2";
				} else if (wtype == "O") {
					w_Type = "3";
				} else if (wtype == "△") {
					w_Type = "4";
				} else if (wtype == "□") {
					w_Type = "5";
				}
				return w_Type;
			}

			function danSheZhi(batchop) {
				var wtype = mini.get("day" + batchop).getValue();
				var row = grid.getSelected();
				var json = mini.encode(row);
				$.ajax({
					url : "danSheZhiUpdate.action?1=1",
					data : {
						data : json,
						batchop : "day" + batchop,
						wtype : wtype
					},
					type : "post",
					success : function(text) {
						grid.reload();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert(jqXHR.responseText);
					}
				});
			}
		</script>
	</body>
</html>