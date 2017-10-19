<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitionbatchReviewal.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
	<title>裁定结果处理</title>
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
<body>
	<div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
        <table >
              <tr>
                <td style="width:100%;">
                <!-- 
               	 	<a class="mini-button" iconCls="icon-remove" onclick="BuYu()" plain="true" style="width:90px">不予减假</a>
               	 -->
               	 	<a class="mini-button" iconCls="icon-save" onclick="saveAll()" plain="true" style="width:90px">保存</a>
               	 	判裁日期：<input id="courtsanction" name="courtsanction"  allowInput="true" class="mini-datepicker"  dateFormat="yyyy-MM-dd"/>
               	 	执行日期：<input id="exectime" name="exectime"  allowInput="true" class="mini-datepicker" dateFormat="yyyy-MM-dd"/>
                </td>
                <td style="white-space:nowrap;">
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="编号/姓名/拼音" style="width:130px;" onenter="onKeyEnter"/>
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                </td>
              </tr>
		</table>
    </div>
    
    <div class="mini-fit" id="div_two">
        <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
        	url="getOpSentenceChangeByBatch.json?1=1"  allowAlternating="true" allowCellSelect="true"  showLoading="true" allowSortColumn="true" allowCellEdit="true" 
        	multiSelect="true" editNextOnEnterKey="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="15"></div>
              	<div type="indexcolumn" width="15" headerAlign="center">序号</div>
              	<div field="applyid" width="40" headerAlign="center" align="center" allowSort="true">罪犯编号</div>
				<div field="applyname" width="35" headerAlign="center" align="center" allowSort="true">姓名</div>
				<div type="comboboxcolumn" field="category" width="35" headerAlign="center" align="center" allowSort="true">变动类别
					<input property="editor" class="mini-combobox" style="width:100%;" data="sentenceType" />  
				</div>
				<div field="courtname" width="60" headerAlign="center" align="center" allowSort="true">判裁法院
					<input property="editor" class="mini-textbox" style="width:100%;"/>
				</div>
				<div header="案件号" headerAlign="center">
            		<div property="columns" >
						<div field="courtyear" width="30" headerAlign="center" align="center" allowSort="true">案件年度
							<input property="editor" name="caseno" class="mini-spinner" style="width:100%;"  maxValue="3000"/>
						</div>
						<div field="courtshort" width="35" headerAlign="center" align="center" allowSort="true">案件简称
							<input property="editor" class="mini-textbox" style="width:100%;"/>
						</div>
						<div field="courtsn" width="30" headerAlign="center"  align="center" allowSort="true" >案件号
			    			<input property="editor" name="caseno" class="mini-spinner" style="width:100%;"  maxValue="100000"/>
			    		</div>
			    	</div>
			    </div>
	    		<!--  
				<div field="courtsanction" width="35" headerAlign="center" align="center" allowSort="true">判裁日期</div>
				<div field="exectime" width="35" headerAlign="center" align="center" allowSort="true">执行日期</div>
				-->
				<div field="courtchange" headerAlign="center"  align="center" allowSort="true" width="60px" displayField="courtchange" renderer="onFuDuRenderer">变动幅度 
	   			 	<input property="editor" class="mini-buttonedit" style="width:100%;" allowInput="false" onbuttonclick="onButtonEdit1"/>
	   			</div>
				<div field="sentence" width="35" headerAlign="center" align="center" allowSort="true" renderer="onAfterSentenceRenderer">变后刑期
				</div>
				<div field="courtchangefrom" width="35" headerAlign="center" align="center" allowSort="true">变后起日
				</div>
				<div field="courtchangeto" width="35" headerAlign="center" align="center" allowSort="true" dateformat="yyyy-MM-dd">变后止日
					<input property="editor" class="mini-datepicker" style="width:100%;"/>
				</div>
				<div field="losepower" width="35" headerAlign="center" align="center" allowSort="true" renderer="onBoZhengRenderer">变后剥政
					<input property="editor" class="mini-buttonedit" style="width:100%;" allowInput="false" onbuttonclick="onButtonEdit2"/>
				</div>
				<div field="awardstart" width="35" headerAlign="center" align="center" allowSort="true">考核起日
				</div>
				<div field="awardend" width="35" headerAlign="center" align="center" allowSort="true" >考核止日
				</div>
	        </div>
	    </div>
    </div>
    
   <script type="text/javascript">
   	var sentenceType = [{ id: 7, text: '减刑' }, { id: 10, text: '减余刑释放'}, { id: 15, text: '假释'}];
   	
   		mini.parse();
   		var grid = mini.get("datagrid");
   		grid.load();
   		grid.sortBy("casenos", "desc");
		
   		grid.on("drawcell", function (e) {
   	        var row = e.record;
   	        if(row.sentenceid){
   	        	e.cellStyle = "background:#c4f1da";
   	        }
   	    });
   		
   		function onFuDuRenderer(e){
   			var record = e.record;
            var courtchange = record.courtchange;
            var s = formatSentence(courtchange);
	     	return s;
   		}
   		
   		function onAfterSentenceRenderer(e){
   			var record = e.record;
            var sentence = record.sentence;
            var s = formatSentence(sentence);
	     	return s;
   		}
   		
   		function formatSentence(sentence){
            var s = '';
            if(sentence && sentence=='9996'){
        		s = "死缓";
        	}else if(sentence && sentence=='9995'){
        		s = "无期";
        	}else if(sentence && sentence.length==6){
        		var year = sentence.substr(0,2);
        		if('00' != year){
        			if('0' == year.substr(0,1)){
        				year = year.substr(1,1);
        			}
        			s += year + '年'; 
        		}
        		//
        		var month = sentence.substr(2,2);
        		if('00' != month){
        			if('0' == month.substr(0,1)){
        				month = month.substr(1,1);
        			}
        			s += month + '月'; 
        		}
        		//
        		var day = sentence.substr(4,2);
        		if('00' != day){
        			if('0' == day.substr(0,1)){
        				day = day.substr(1,1);
        			}
        			s += day + '天'; 
        		} 
        	}
	     	return s;
   		}
   		
   		
   		function onBoZhengRenderer(e){
   			var record = e.record;
            var losepower = record.losepower;
            var s = '';
            if(losepower && losepower=='97'){
        		s = "终身";
        	}else if(losepower){
        		s = losepower + "年";
        	}
	     	return s;
   		}
   		
        function saveAll(){
	 		var data = grid.getSelecteds();
	 		var courtsanction = mini.get("courtsanction").getFormValue();
	 		var exectime = mini.get("exectime").getFormValue();
	 		
	 		if (data.length>0) {
		 		if(!courtsanction){
					alert("请选择判裁日期！");
					return;
				}else if(!exectime){
					alert("请选择执行日期！");
					return;
				}
		 		
		 		for(var i=0,l=data.length; i<l; i++){
		 			var row = data[i];
		 			//没有输入案件号，不得提交
		 			if(! row.courtsn){
		 				alert('罪犯' + row.applyname + '的案件号未输入！');
		 				return;
		 			}
		 			//判为有期时，没有输入刑期止日，不得提交
		 			if(row.sentence && row.sentence !='9996' && row.sentence !='9995' && !row.courtchangeto){
		 				alert('罪犯' + row.applyname + '的变后止日未输入！');
		 				return;
		 			}
		 			
		 			if(row.courtchangeto){
		 				var tempCourtchangeto = mini.formatDate(row.courtchangeto, 'yyyy-MM-dd');
		 				if(tempCourtchangeto){
		 					row.courtchangeto = tempCourtchangeto;
		 					data[i] = row;
		 				}
		 			}
		 		}
		 		var json = mini.encode(data);
		 		
		 		//
		 		$.ajax({
	                url: "<%=path%>/saveParoleSentenceChange.json?1=1",
	                data: { data:json, courtsanction:courtsanction, exectime:exectime},
	                type: "post",
	                dateType:"text",
	                success: function (text) {
	                	if(text.status == 'success'){
	                		alert("操作成功！");
	                	}else{
	                		alert("操作失败！");
	                	}
	                	grid.reload();
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
	 	    var rows = grid.getSelecteds();//获取选中记录
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
							grid.reload();
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
	 	function onButtonEdit1(e){
            var row = grid.getSelected();
            mini.open({
                url:"<%=path%>/toEditFuduPage.page",
                title: "选择幅度",
                width: 480,
                height: 270,
                onload: function () {
                	var iframe = this.getIFrameEl();
                    var data = {courtchange:row.courtchange};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                	if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        grid.cancelEdit();
                        var row = grid.getSelected();
                        var nowpunishmentstr = row.nowpunishmentstr;
                        var courtchange = data.courtchange;
                        
                        var sentence = calculateSentence(nowpunishmentstr, courtchange);
                        grid.updateRow(row, {
                        	courtchange: courtchange,
                        	sentence:sentence
                        });
                    }
                }
            });
        }
	 	
	 	function calculateSentence(nowpunishmentstr, courtchange){
	 		var sentence = '';
	 		var url = "calculateSentence.json?1=1";
	 		$.ajax({
				url:url,
				data:{nowpunishmentstr:nowpunishmentstr, courtchange:courtchange},
				type:"post",
				async:false,
				success:function(text){
					sentence = text.sentence;
				},
				error: function(){
				}
			});
	 		return sentence;
	 	}
	 	
	 	//弹出变后剥政修改窗口
	 	function onButtonEdit2(e){
	 		var row = grid.getSelected();
	        mini.open({
	        	url:"<%=path%>/toEditBoZhengPage.page",
	            title: "选择剥政",
	            width: 480,
	            height: 270,
	            onload: function () {
	            	var iframe = this.getIFrameEl();
	                var data = {losepower:row.losepower};
	                iframe.contentWindow.SetData(data);
	            },
	            ondestroy: function (action) {
	            	if (action == "ok") {
	                    var iframe = this.getIFrameEl();
	                    var data = iframe.contentWindow.GetData();
	                    data = mini.clone(data);    //必须
	                    grid.cancelEdit();
	                    var row = grid.getSelected();
	                    grid.updateRow(row, {
	                    	losepower: data.losepower
	                    });
	                }
	            }
	        });
        }
	 	
	 	
	 	function onKeyEnter(){
	 		search();
	 	}
	 	
	 	function search(){
	 	  	var key = mini.get("key").getValue();
	 	  	grid.load({ key: key});
	 	}
	 	
	 	
	</script>  
</body>
</html>
