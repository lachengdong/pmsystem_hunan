<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
  
    <title>检索</title>
    <meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	        font-size:12px; 
	        _font-size:0.36em;
	        background:#F0F3F3;
	    }
	    .mini-textbox {
	    	width:106px !important;
	    	width:66px;
	    	_font-size:0.7em;
	     }
	    .mini-datepicker {
	    	width:106px !important;
	    	width:66px;
	    	_font-size:0.7em;
	     }
	    .td1
	    {
	        text-align:right;
	    }
	    html body .mini-radiobuttonlist{
	        font-size:12px; 
	         _font-size:0.7em;
        }
	    html body .mini-checkboxlist{
	        font-size:12px; 
	         _font-size:0.7em;
        }
	    html body .mini-button{
	        font-size:12px; 
	         _font-size:0.7em;
        }
        select{
       		font-size:12px;
	         _font-size:0.68em;
	        width:70px;
        }
	</style>
  </head>
  
  <body>
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
	                <td>
		      			条件使用选择
		      			<select>
		      				<option>组合条件</option>
		      			</select>
	                </td>
	                <td>
		      			在押离监选择
		      			<select>
		      				<option>组合条件</option>
		      			</select>
	                </td>
	                <td>
		      			截止日期
		      			<select>
		      				<option>组合条件</option>
		      			</select>
	                </td>
	                <td>
		      			<a class="mini-button" id="onOk" onclick="onOk" plain="true" >确定</a>
		      			<a class="mini-button" id="onClear" onclick="onClear" plain="true" >重置</a>
		           		<a class="mini-button" id="onCancel" onclick="onCancel"  plain="true" >关闭</a>  
	                </td>
               </tr>
		</table>
    </div>
    <div id="form1" style="padding:10px;">
    	<table >
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn1$text">变刑时单位：</label>
    			</td>
    			<td colspan="2">
    				<div name="conditioncolumn1" class="mini-radiobuttonlist" value="1" repeatItems="1" repeatDirection="vertical" repeatLayout="table" data="depttypes" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn2$text">编号：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn2"  name="conditioncolumn2" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn3$text">刑罚变动类别：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn3"  name="conditioncolumn3" class="mini-textbox" required="false"/>
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn4$text">执行日期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn41"  name="conditioncolumn41" class="mini-datepicker"  required="false" />&nbsp;至
    				<input id="conditioncolumn42"  name="conditioncolumn42" class="mini-datepicker"  required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn5$text">次数：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn51"  name="conditioncolumn51" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn52"  name="conditioncolumn52" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn6$text">减刑次数：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn61"  name="conditioncolumn61" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn62"  name="conditioncolumn62" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn7$text">裁判日期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn71"  name="conditioncolumn71" class="mini-datepicker" required="false" />&nbsp;至
    				<input id="conditioncolumn72"  name="conditioncolumn72" class="mini-datepicker" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn8$text">裁判机关：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn81"  name="conditioncolumn81" class="mini-textbox" required="false" />&nbsp;&nbsp;&nbsp;&nbsp;
    				<input id="conditioncolumn82"  name="conditioncolumn82" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn9$text">变刑前刑期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn91"  name="conditioncolumn91" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn92"  name="conditioncolumn92" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn10$text">变刑后刑期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn101"  name="conditioncolumn101" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn102"  name="conditioncolumn102" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn11$text">加减刑幅度：</label>
    			</td>
    			<td colspan="2"> 
    				<input id="conditioncolumn111"  name="conditioncolumn111" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn112"  name="conditioncolumn112" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn12$text">加刑原因：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn12"  name="conditioncolumn12" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn13$text">所在队别：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn13"  name="conditioncolumn13" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn14$text">监区呈报日期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn141"  name="conditioncolumn141" class="mini-datepicker" required="false" />&nbsp;至
    				<input id="conditioncolumn142"  name="conditioncolumn142" class="mini-datepicker" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn15$text">现队别：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn15"  name="conditioncolumn15" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn16$text">原案犯类别：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn16"  name="conditioncolumn16" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn17$text">现案犯类别：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn17"  name="conditioncolumn17" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn18$text">分管等级：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn18"  name="conditioncolumn18" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn19$text">入监日期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn191"  name="conditioncolumn191" class="mini-datepicker" required="false" />&nbsp;至
    				<input id="conditioncolumn192"  name="conditioncolumn192" class="mini-datepicker" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn20$text">调入日期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn201"  name="conditioncolumn201" class="mini-datepicker" required="false" />&nbsp;至
    				<input id="conditioncolumn202"  name="conditioncolumn202" class="mini-datepicker" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn21$text">减刑尺度：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn21"  name="conditioncolumn21" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn22$text">原判刑期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn221"  name="conditioncolumn221" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn222"  name="conditioncolumn222" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn23$text">现刑期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn231"  name="conditioncolumn231" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn232"  name="conditioncolumn232" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn24$text">余刑：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn24"  name="conditioncolumn24" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn25$text">止日：</label>
    			</td>
    			<td colspan="2"> 
    				<input id="conditioncolumn251"  name="conditioncolumn251" class="mini-datepicker" required="false" />&nbsp;至
    				<input id="conditioncolumn252"  name="conditioncolumn252" class="mini-datepicker" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn26$text">已服刑期：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn261"  name="conditioncolumn261" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn262"  name="conditioncolumn262" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn27$text">已服刑期比例：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn271"  name="conditioncolumn271" class="mini-textbox" required="false" />&nbsp;至
    				<input id="conditioncolumn272"  name="conditioncolumn272" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn28$text">累惯犯：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn28"  name="conditioncolumn28" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn29$text">分押类型：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn29"  name="conditioncolumn29" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn30$text">性别：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn30"  name="conditioncolumn30" class="mini-textbox" required="false" />
    			</td>
    			<td  class="td1">
    				<label for="conditioncolumn31$text">住址：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn311"  name="conditioncolumn311" class="mini-textbox" required="false" />&nbsp;&nbsp;&nbsp;&nbsp;
    				<input id="conditioncolumn312"  name="conditioncolumn312" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn32$text">特管人员：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn32" name="conditioncolumn32" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="specialpersons" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn33$text">特管类别：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn34"  name="conditioncolumn34" class="mini-textbox" required="false" />
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn35$text">脱逃条件：</label>
    			</td>
    			<td colspan="2">
    				<div id="escapecondition35" name="escapecondition" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="escapeconditions" textField="text" valueField="id" ></div>
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn36$text">老病残：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn36" name="conditioncolumn36" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="oldsicks" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn37$text">老病残类型：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn37"  name="conditioncolumn37" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn38$text">保外就医：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn38" name="conditioncolumn38" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="prisonexecutions" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn39$text">危重分子：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn39" name="conditioncolumn39" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="criticalmoleculars" textField="text" valueField="id" ></div>
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn40$text">驻监检查室：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn40" name="conditioncolumn40" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="examinationprisons" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn410$text">三类罪犯：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn410" name="conditioncolumn410" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="datatypes" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn420$text">重要罪犯：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn420" name="conditioncolumn420" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="datatypes" textField="text" valueField="id" ></div>
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn43$text">原县级以上干部罪犯：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn43" name="conditioncolumn43" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="datatypes" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn44$text">外国籍罪犯：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn44" name="conditioncolumn44" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="datatypes" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn45$text">港澳台罪犯：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn45" name="conditioncolumn45" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="datatypes" textField="text" valueField="id" ></div>
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				<label for="conditioncolumn46$text">报局审核案件：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn46" name="conditioncolumn46" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="datatypes" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn47$text">提请类别：</label>
    			</td>
    			<td colspan="2">
    				<div id="conditioncolumn47" name="conditioncolumn47" class="mini-checkboxlist"  repeatItems="1" 
    				repeatDirection="vertical" repeatLayout="table" data="drawcategorys" textField="text" valueField="id" ></div>
    			</td>
    			<td class="td1">
    				<label for="conditioncolumn48$text">提请幅度：</label>
    			</td>
    			<td colspan="2">
    				<input id="conditioncolumn48"  name="conditioncolumn48" class="mini-textbox" required="false" />
    			</td>
    		</tr>
    	</table>
    </div>
    <form id="queryForm"  action="<%=path%>/public/toStatisticalQuery.action?1=1" method="post" >
	        <input type="hidden" name="queryWhereData" id="queryWhereData" />
	</form>
	
    <script type="text/javascript">
    	var depttypes = [{"id": "1", "text": "本单位"},{ "id": "2", "text":"外单位"},{ "id": "3", "text":"全部"}];
		var specialpersons = [{"id": "是", "text": "特管人员"},{ "id": "否", "text":"不是特管人员"}];
		var escapeconditions = [{"id": "是", "text": "在逃"},{ "id": "否", "text":"未在逃"}];
		var oldsicks = [{"id": "是", "text": "老病残"},{ "id": "否", "text":"不是老病残"}];
		var prisonexecutions = [{"id": "是", "text": "保外就医"},{ "id": "否", "text":"未保外就医"}];
		var criticalmoleculars = [{"id": "是", "text": "危重分子"},{ "id": "否", "text":"不是危重分子"}];
		var examinationprisons = [{"id": "是", "text": "检查结果"},{ "id": "否", "text":"检查理由"},{ "id": "3", "text":"检查意见"}];
		var datatypes = [{"id": "是", "text": "是"},{ "id": "否", "text":"否"}];
		var drawcategorys = [{"id": "狱内减刑", "text": "减刑"},{ "id": "假释", "text":"假释"}];	
		mini.parse();
		var form = new mini.Form("#form1");

		var queryWhereData = eval(${parmMap.queryWhereData});
        if(queryWhereData && queryWhereData !='undefined') form.setData(queryWhereData); 
    	function onOk(menuid) {
            var data = form.getData();      //获取表单多个控件的数据
            var json = mini.encode(data);   //序列化成JSON
            document.getElementById("queryWhereData").value = json;
            var queryForm = document.getElementById("queryForm");
            queryForm.submit();
            //$.post(url,{farm_seq:seq}, function() { window.location.href = url; });
        }
		//重置
		function onClear(){
			form.clear();
		}
		//关闭
		function onCancel(){
			if (window.CloseOwnerWindow) return window.CloseOwnerWindow("cancel");
		}

    </script>
  </body>
</html>
