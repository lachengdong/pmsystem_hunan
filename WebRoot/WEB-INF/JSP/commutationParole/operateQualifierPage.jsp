<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<html>
	<head>
		<title>筛查条件编辑</title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			body {
				margin: 0;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
			.backgray {
			}
			.backgray *{
				background-color: #DDD !important;
			}
		</style>
	</head>
	<body>
       <div class="mini-splitter" style="width:100%;height:100%;padding:0;" borderStyle="border:1px solid #99bce8;">
       		<!-- 左边的树 -->
           <div size="260" maxSize="1024" minSize="150" showCollapseButton="true">
				<div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
					<input id="qid" name="qid" class="mini-hidden" value="${qid}"/>
					<input id="fid" name="fid" class="mini-hidden" value="${fid}"/>
					<input id="setlevel" name="setlevel" class="mini-hidden" value="${setlevel}"/>
					<input id="qtype" name="qtype" class="mini-hidden" value="${qtype}"/>
					<input id="operatetype" name="operatetype" class="mini-hidden" />
					
					<input id="desctemp" name="desctemp" class="mini-hidden" value=""/>
					<input id="exptemp" name="exptemp" class="mini-hidden" value=""/>
					
					<input id="redodesctemp" name="redodesctemp" class="mini-hidden" value=""/>
					<input id="redoexptemp" name="redoexptemp" class="mini-hidden" value=""/>
					         
		            <span style="padding-left:5px;">名称：</span>
		            <input class="mini-textbox" vtype="maxLength:60;" id="key" style="width:140px;" onenter="onKeyEnter"/>
		            <a class="mini-button" iconCls="icon-search" plain="true"  onclick="searchItem()">查找</a>                  
		        </div>
		        <div  class="mini-fit" >
					<ul id="tree1" class="mini-tree" style="width:100%;height:100%;" 
						url="<%=path%>/getQualifierItem.json?1=1&itemlevel=${setlevel}" showTreeIcon="true" expandOnLoad="0" resultAsTree="false"
					    textField="name" idField="col_name" contextMenu="#treeMenu" onnodeclick="onTreeNodeClick">
					</ul>
				</div>
           </div>
           
           <!-- 右边的显示区域 -->
           <div showCollapseButton="false" style="overflow: auto;padding:0px;" borderStyle="border:solid 1px #aaa;">
			    <div region="north" height="40"  showSplit="false" showHeader="false" showCollapseButton="false">
				    <div class="mini-toolbar" style="padding:2px;border:0;">
				       		<div class="crud-search">
								<table style="width:98%;">
									<tr>
										<td align="left">
										</td>
										<td align="right">
											<a class="mini-button" iconCls="icon-ok" onclick="onOk();" plain="true">确定</a>
											<a class="mini-button" iconCls="icon-close" onclick="onCancel" plain="true">取消</a>
										</td>
									</tr>
								</table>
							</div>
				    </div>
				</div>
				
			    <div  class="mini-fit" showCollapseButton="false" style="padding:5px;">
			    
				    <fieldset id="fd1" style="width:800px;">
				        <legend><span>运算符号</span></legend>
				        <div class="fieldset-body">
				            <table class="form-table" border="0" cellpadding="1" cellspacing="2">
				            
				            	<tr>
				                    <td class="form-label" style="width:80px;">算术运算符：</td>
				                    <td style="width:220px">
				                        <input id="calcusign" name="calcusign" class="mini-combobox" showNullItem="true" onvaluechanged="getOperator('calcusign');"
				                        	data="[{id:'+',text:'加'},{id:'-',text:'减'},{id:'*',text:'乘'},{id:'/',text:'除'},{id:'%',text:'取模'}]"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getOperator('calcusign');" plain="true"></a>
				                    </td>
				                    
				                    <td class="form-label" style="width:80px;">日期：</td>
				                    <td style="width:220px">
				                    	<input id="date" name="date" class="mini-datepicker" value="" onvaluechanged="getDateOperator('date');"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getDateOperator('date');" plain="true"></a>
				                    </td>
				                </tr>
				                
				                <tr>
				                    <td class="form-label">关系运算符：</td>
				                    <td >
				                        <input id="relationsign" name="relationsign" class="mini-combobox" showNullItem="true" onvaluechanged="getOperator('relationsign');"
				                        	data="[{id:'>',text:'大于'},{id:'>=',text:'大等于'},{id:'==',text:'等于'},{id:'!=',text:'不等于'},{id:'<=',text:'小等于'},{id:'<',text:'小于'}]"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getOperator('relationsign');" plain="true"></a>
				                    </td>
				                    
				                    <td class="form-label">逻辑运算符：</td>
				                    <td >
				                        <input id="logicsign" name="logicsign" class="mini-combobox" showNullItem="true" onvaluechanged="getOperator('logicsign');"
				                        	data="[{id:'&&',text:'并且'},{id:'||',text:'或者'},{id:'0 ==',text:'非'},{id:'1 ==',text:'是'}]"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getOperator('logicsign');" plain="true"></a>
				                    </td>
				                </tr>
				                
				                <tr>
				                    <td class="form-label">括号：</td>
				                    <td >
				                        <input id="bracketsign" name="bracketsign" class="mini-combobox" showNullItem="true" onvaluechanged="getOperator('bracketsign');"
				                        	data="[{id:'(',text:'('}, {id:')',text:')'}]"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getOperator('bracketsign');" plain="true"></a>
				                    </td>
				                    
				                    <td class="form-label">数值：</td>
				                    <td >
				                        <input id="num" name="num" class="mini-textbox" emptyText="数值" vtype="float" onenter="getNumber('num');"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getNumber('num');" plain="true"></a>
				                    </td>
				                </tr>
				                
				                <tr>
				                    <td class="form-label">如果条件：</td>
				                    <td >
				                        <input id="ifsign" name="ifsign" class="mini-combobox" showNullItem="true" onvaluechanged="getOperator('ifsign');"
				                        	data="[{id:'if',text:'如果'},{id:'require',text:'要求'},{id:'else',text:'否者要求'}]"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getOperator('ifsign');" plain="true"></a>
				                    </td>
				                    
				                    <td class="form-label">当条件：</td>
				                    <td >
				                        <input id="whensign" name="whensign" class="mini-combobox" showNullItem="true" onvaluechanged="getOperator('whensign');"
				                        	data="[{id:'when',text:'当'},{id:'require',text:'要求'},{id:'else',text:'否者要求'}]"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getOperator('whensign');" plain="true"></a>
				                    </td>
				                </tr>
				                
				                <tr>
				                    <td class="form-label">死缓无期：</td>
				                    <td >
				                        <input id="sentencesign" name="sentencesign" class="mini-combobox" showNullItem="true" onvaluechanged="getOperator('sentencesign');"
				                        	data="[{id:999600,text:'死缓'},{id:999500,text:'无期'}]"/>
				                        <a class="mini-button" iconCls="icon-add" onclick="getOperator('sentencesign');" plain="true"></a>
				                    </td>
				                    
				                    <td class="form-label">年限：</td>
				                    <td >
				                    	<input id="year" name="year" class="mini-textbox" style="width:27px;" emptyText="年" />年
				                    	<input id="month" name="month" class="mini-textbox" style="width:27px;" emptyText="月" />月
				                    	<input id="day" name="day" class="mini-textbox" style="width:27px;" emptyText="天" />天
				                    	
				                    	<a class="mini-button" iconCls="icon-add" onclick="getPeriod();" plain="true"></a>
				                    </td>
				                    
				                </tr>
				                
				                <tr>
				                    <td class="form-label">状态：</td>
				                    <td > <!-- getCalculateflag -->
				                        <input id="calculateflag" name="calculateflag" class="mini-combobox" showNullItem="true" onvaluechanged="getOperator('calculateflag');"
				                        	data="[{id:true,text:'结果为真'},{id:false,text:'结果为假'}]"/>
				                        	<a class="mini-button" iconCls="icon-add" onclick="getOperator('calculateflag');" plain="true"></a>
				                    </td>
				                    
				                    <td class="form-label">优先级条件：</td>
				                    <td >
				                        <input id="orderby" name="orderby" class="mini-spinner"  minValue="1" maxValue="1000" />
				                    </td>
				                </tr>
				                
				            </table>
				        </div>
				    </fieldset>
				
				    <fieldset id="fd2" style="width:800px;">
				    	<legend><span>表达式：</span></legend>
				        <div class="fieldset-body">
				            <table class="form-table" border="0" cellpadding="1" cellspacing="2">
				                <tr>
				                    <td class="form-label" style="width:80px;">描述：</td>
				                    <td colspan="3" >
				                        <input id="describe" name="describe" class="mini-textarea" style="width:550px;height:100px;" allowInput="false"/>
				                    </td>
				                    <td style="width:100px;">
				                    	<a class="mini-button" iconCls="icon-upgrade" onclick="onUndo">撤销</a>
				                    	<a class="mini-button" iconCls="icon-downgrade" onclick="onRedo">恢复</a>
				                    	<a class="mini-button" iconCls="icon-cancel" onclick="onReset">重置</a>
				                    </td>   
				                </tr>
				                
				                <tr>
				                    <td class="form-label">表达式：</td>
				                    <td colspan="3" >
				                        <input id="expression" name="expression" class="mini-textarea" style="width:550px;height:100px;" allowInput="false"/>
				                    </td>        
				                </tr>
				                              
				            </table>
				        </div>
				    </fieldset>
				    <p>操作说明：左边树通过单击树节点添加元素，右边通过单击加按钮添加元素！</p>
	            </div>
       	</div>
   	</div>	

    <script type="text/javascript">
    	// 解析并初始化
        mini.parse();
    	var tree = mini.get("tree1");
    	
    	/**
    	 * 验证表单式
    	*/
    	function validateExpression(expression){
    		//将表达式中的if、require、else、when全部替换成 &&
    		expression = replaceAllStr(expression,'if','&&');
    		expression = replaceAllStr(expression,'require','&&');
    		expression = replaceAllStr(expression,'else','&&');
    		expression = replaceAllStr(expression,'when','&&');
    		//获取所有的运算符号的数组
    		var signArr = [];
    		signArr[0]='+';
    		signArr[1]='-';
    		signArr[2]='*';
    		signArr[3]='/';
    		signArr[4]='%';
    		//
    		signArr[5]='>';
    		signArr[6]='>=';
    		signArr[7]='==';
    		signArr[8]='!=';
    		signArr[9]='<=';
    		signArr[10]='<';
    		
    		signArr[11]='&&';
    		signArr[12]='||';
    		signArr[13]='0==';
    		signArr[14]='1==';
    		
    		signArr[15]='(';
    		signArr[16]=')';
    		
    		var para = [];
    		var expressionArr = expression.split(" ");//将该表达式拆分成由各个元素组成的数组
    		for(var i=0,l=expressionArr.length; i<l; i++){
    			if(! arrContains(signArr, expressionArr[i])){//表达式中每个元素不是运算符号
    				if(! isNumber(expressionArr[i])){//并且表达式中每个元素不是数字
    					para.push(expressionArr[i]);//将该元素存放到一个新的数组中去
    				}
    			}
    		}
    		//表达式中非符号及非数字的，封装成map，此map的key为元素值，map的value为随机数字
    		//将map传给后台，并作为参数，交给expression，来判断执行此expression结果是否为布尔值。
    		var data = {};
    		if(para.length > 0){
    			for(var i=0,l=para.length; i<l; i++){
    				var key = para[i];
    				data[key] = i+1;
    			}
    		}
    		data.expression = expression;
    		
    		var result = false;
    		$.ajax({
				url: "<%=path%>/validateExpression.json?1=1",
				data: data,
				type: "post",
				dataType:"text",
				async:false,
				success: function (text) {
					if(1==text){
						result=true;
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
				}
			});
    		return result;
    	}
    	
    	function onReset(){
    		mini.get("desctemp").setValue(null);
    		mini.get("exptemp").setValue(null);
    		
    		clearRedoData();//清空Redo的临时数据
    		
    		mini.get("describe").setValue(null);
    		mini.get("expression").setValue(null);
    	}
    	
    	function setTextarea(text,value){
    		var desctemp = mini.get("desctemp").getValue();
    		var exptemp = mini.get("exptemp").getValue();
    		
    		if(! desctemp){
    			desctemp = text
    		}else{
    			desctemp += ";"+text;
    		}
    		
    		if(! exptemp){
    			exptemp = value
    		}else{
    			exptemp += ";"+value;
    		}
    		
    		mini.get("desctemp").setValue(desctemp);
    		mini.get("exptemp").setValue(exptemp);
    		
    		setTextareaValue(desctemp,exptemp);
    	}
    	
    	function setTextareaValue(desctemp,exptemp){
    		
    		desctemp = desctemp.split(";");
    		desctemp = desctemp.join(" ");
    		
    		exptemp = exptemp.split(";");
    		exptemp = exptemp.join(" ");
    		
    		mini.get("describe").setValue(desctemp);
    		mini.get("expression").setValue(exptemp);
    	}
    	
    	function onUndo(){
    		var desctemp = mini.get("desctemp").getValue();
    		var exptemp = mini.get("exptemp").getValue();
    		
    		var desc = null;
    		var exp = null;
    		if(desctemp){
    			desctemp = desctemp.split(";");
    			desc = desctemp.pop();
    			desctemp = desctemp.join(";");
    		}
    		if(exptemp){
    			exptemp = exptemp.split(";");
    			exp = exptemp.pop();
    			exptemp = exptemp.join(";");
    		}
    		
    		mini.get("desctemp").setValue(desctemp);
    		mini.get("exptemp").setValue(exptemp);
    		
    		setTextareaValue(desctemp,exptemp);
    		//
    		var redodesctemp = mini.get("redodesctemp").getValue();
    		var redoexptemp = mini.get("redoexptemp").getValue();
    		if(desc && exp){
    			if(redodesctemp){
        			redodesctemp +=";"+desc;
        		}else{
        			redodesctemp = desc;
        		}
    			
    			if(redoexptemp){
    				redoexptemp += ";"+exp;
    			}else{
    				redoexptemp = exp;
    			}
    			
    			mini.get("redodesctemp").setValue(redodesctemp);
    			mini.get("redoexptemp").setValue(redoexptemp);
    		}
    		
    	}
    	
    	function clearRedoData(){
    		mini.get("redodesctemp").setValue(null);
    		mini.get("redoexptemp").setValue(null);
    	}
    	
    	function onRedo(){
    		var redodesctemp = mini.get("redodesctemp").getValue();
    		var redoexptemp = mini.get("redoexptemp").getValue();
    		
    		var desc = null;
    		var exp = null;
    		if(redodesctemp && redoexptemp){
    			redodesctemp = redodesctemp.split(";");
    			redoexptemp = redoexptemp.split(";");
    			desc = redodesctemp.shift();
    			exp = redoexptemp.shift();
    			
    			redodesctemp = redodesctemp.join(";");
    			redoexptemp = redoexptemp.join(";");
    			
    			mini.get("redodesctemp").setValue(redodesctemp);
    			mini.get("redoexptemp").setValue(redoexptemp);
    		}
    		
    		if(desc && exp){
    			setTextarea(desc,exp);
    		}
    	}
    	
    	
    	
        // 选择树节点时
		function onTreeNodeClick(e) {
		    var node = e.node;
		    if(null == node){
			    //e.cancel = true;
			    return false;
		    }
		    setTextarea(node.name,node.col_name);
		    clearRedoData();//清空Redo的临时数据
		    //e.cancel = true;
		    return false;
		}
		
        function onKeyEnter(e) {
        	searchItem();
        }
        
		//过滤树
        function searchItem(){
            var key = mini.get("key").getValue();
            if (key == "") {
                tree.clearFilter();
            } else {
                key = key.toLowerCase(); 
                tree.filter(function (node) {
                    var text = node.name?node.name.toLowerCase() : "";
                    if (text.indexOf(key) != -1) {
                        return true;
                    }
                });
            }
        }
        
        
		
		function getOperator(id){
			var text = mini.get(id).getText();
			var value = mini.get(id).getValue();
			if(!value){
				//alert("未选择值！");
				return false;
			}
			
			setTextarea(text,value);
			clearRedoData();//清空Redo的临时数据
		}
		
		function getDateOperator(id){
			var text = mini.get(id).getText();
			if(!text){
				//alert("未选择值！");
				return false;
			}
			var value = mini.parseDate(text);
			value = mini.formatDate(value,'yyyyMMdd');
			
			setTextarea(text,value);
			clearRedoData();//清空Redo的临时数据
		}
		
		function getNumber(id){
			var value = mini.get(id).getValue();
			if(!value){
				//alert("未选择值！");
				return false;
			}
			
			if(isNumber(value)){
				setTextarea(value,value);
				clearRedoData();//清空Redo的临时数据
			}else{
				alert("请输入数字！");
				mini.get(id).setValue(null);
			}
		}
		
		
		function getPeriod(){
			var year = mini.get('year').getValue();
			var month = mini.get('month').getValue();
			var day = mini.get('day').getValue();
			
			if(!year && !month && !day){
				alert("未输入值！");
				return false;
			}
			
			if(year){
				if(!isInteger(year)){
					alert("输入的年份必须为整数！");
					mini.get('year').setValue(null);
					return false;
				}
				if(year>=100 || year<=0){
					alert("输入的年份必须介于0~100之间！");
					mini.get('year').setValue(null);
					return false;
				}
				year = parseInt(year);
			}
			
			if(month){
				if(!isInteger(month)){
					alert("输入的月份必须为整数！");
					mini.get('month').setValue(null);
					return false;
				}
				if(month>=12 || month<=0){
					alert("输入的月份必须介于0~12之间！");
					mini.get('month').setValue(null);
					return false;
				}
				month = parseInt(month);
			}
			
			if(day){
				if(!isInteger(day)){
					alert("输入的天数必须为整数！");
					mini.get('day').setValue(null);
					return false;
				}
				if(day>=31 || day<=0){
					alert("输入的天数必须介于0~31之间！");
					mini.get('day').setValue(null);
					return false;
				}
				day = parseInt(day);
			}
			
			
			var text = '';
			var value = 0;
			if(year > 0){
				text += year+'年';
				value += year * 10000;
			}
			
			if(month > 0){
				text += month + '个月';
				value += month * 100;
			}
			
			if(day > 0){
				text += day + '天';
				value += day;
			}
			setTextarea(text,value);
			clearRedoData();//清空Redo的临时数据
		}
		
		
		function getCalculateflag(){
			alert("未完善！");
		}
		
		function onOk(){
			var data = {};
			var orderby = mini.get("orderby").getValue();
			var describe = mini.get("describe").getValue();
			var expression = mini.get("expression").getValue();
			var setlevel = mini.get("setlevel").getValue();
			var qid = mini.get("qid").getValue();
			var fid = mini.get("fid").getValue();
			var qtype = mini.get("qtype").getValue();
			var operatetype = mini.get("operatetype").getValue();
			
			if(! expression){
				alert("未输入表达式!");
				return;
			}
			if(! validateExpression(expression)){
				alert("表达式书写错误!");
				return;
			}
			
			data.orderby = orderby;
			data.describe = describe;
			data.expression = expression;
			data.setlevel = setlevel;
			data.qid = qid;
			data.fid = fid;
			data.qtype = qtype;
			data.operatetype = operatetype;
			
			$.ajax({
				url: "<%=path%>/saveQualifierSet.json?1=1",
				data: data,
				type: "post",
				dataType:"text",
				async:false,
				success: function (text) {
					if(text==1){
						alert("操作成功!");
					}else{
						alert("操作失败!");
					}
					CloseWindow("save");
				},
				error: function (jqXHR, textStatus, errorThrown) {
					alert("操作失败!");
					CloseWindow("cancel");
				}
			});
			
		}
		
		function onCancel(e) {
	        CloseWindow("cancel");
	    }
		
	    function CloseWindow(action){
	        if (action == "close" && form.isChanged()) {
	            if (confirm("数据被修改了，是否先保存？")) {
	                return false;
	            }
	        }
	        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
	        else window.close();
	    }
				

	    function SetData(data) {
			data = mini.clone(data);
		    mini.get("operatetype").setValue(data.operatetype);
		    if(data.orderby){
		    	mini.get("orderby").setValue(data.orderby);
		    }
		    if(data.describe){
		    	mini.get("describe").setValue(data.describe);
		    	var desctemp = data.describe.split(" ").join(";");
		    	mini.get("desctemp").setValue(desctemp);
	    		
		    }
		    if(data.expression){
		    	mini.get("expression").setValue(data.expression);
		    	var exptemp = data.expression.split(" ").join(";");
		    	mini.get("exptemp").setValue(exptemp);
		    }
		}
		
    </script>
	</body>
</html>