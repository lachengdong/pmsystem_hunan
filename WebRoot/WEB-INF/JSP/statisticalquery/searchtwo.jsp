<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.gkzx.util.property.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>检索</title>
    <meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
 	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
  
    <style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:auto;background:#F0F3F3;
	    }
	    .td1
	    {
	        text-align:left;
	    }
	</style>
    
  </head>
  
  <body>
    <div class="mini-toolbar"  style="padding:1px;">
            <table >
               <tr>
	                <td class="td1">
		      			刑罚变动
	                </td>
               </tr>
		</table>
    </div>
    <div id="form1" name="form1" style="padding: 3px;border: 1px solid #f0f0f0;">
    	<table width="100%;" >
    		<tr>
    			<td class="td1">
    				显示字段：
    			</td>
    		</tr>
    		<tr>
    			<td class="td1"> 
    				01&nbsp;<input id="displaycolumn1" name="displaycolumn1" class="mini-checkbox" text="罪犯编号" trueValue="罪犯编号"  />
    			</td>
    			<td class="td1">
    				02&nbsp;<input id="displaycolumn2" name="displaycolumn2" class="mini-checkbox" text="番号" trueValue="番号"  />
    			</td>
    			<td class="td1">
    				03&nbsp;<input id="displaycolumn3" name="displaycolumn3" class="mini-checkbox" text="姓名" trueValue="姓名"  />
    			</td>
    			<td class="td1">
    				04&nbsp;<input id="displaycolumn4" name="displaycolumn4" class="mini-checkbox" text="变动类别" trueValue="变动类别"  />
    			</td>
    			<td class="td1">
    				05&nbsp;<input id="displaycolumn5" name="displaycolumn5" class="mini-checkbox" text="变动幅度" trueValue="变动幅度"  />
    			</td>
    			<td class="td1">
    				06&nbsp;<input id="displaycolumn6" name="displaycolumn6" class="mini-checkbox" text="裁判日期" trueValue="裁判日期"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				07&nbsp;<input id="displaycolumn7" name="displaycolumn7" class="mini-checkbox" text="裁判机关" trueValue="裁判机关"  />
    			</td>
    			<td class="td1">
    				08&nbsp;<input id="displaycolumn8" name="displaycolumn8" class="mini-checkbox" text="裁判字号" trueValue="裁判字号"  />
    			</td>
    			<td class="td1">
    				09&nbsp;<input id="displaycolumn9" name="displaycolumn9" class="mini-checkbox" text="加刑原因" trueValue="加刑原因"  />
    			</td>
    			<td class="td1">
    				10&nbsp;<input id="displaycolumn10" name="displaycolumn10" class="mini-checkbox" text="裁判刑期" trueValue="裁判刑期"  />
    			</td>
    			<td class="td1">
    				11&nbsp;<input id="displaycolumn11" name="displaycolumn11" class="mini-checkbox" text="裁判起日" trueValue="裁判起日"  />
    			</td>
    			<td class="td1">
    				12&nbsp;<input id="displaycolumn12" name="displaycolumn12" class="mini-checkbox" text="裁判止日" trueValue="裁判止日"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				13&nbsp;<input id="displaycolumn13" name="displaycolumn13" class="mini-checkbox" text="裁判剥政" trueValue="裁判剥政"  />
    			</td>
    			<td class="td1">
    				14&nbsp;<input id="displaycolumn14" name="displaycolumn14" class="mini-checkbox" text="执行日期" trueValue="执行日期"  />
    			</td>
    			<td class="td1">
    				15&nbsp;<input id="displaycolumn15" name="displaycolumn15" class="mini-checkbox" text="所在队别" trueValue="所在队别"  />
    			</td>
    			<td class="td1">
    				16&nbsp;<input id="displaycolumn16" name="displaycolumn16" class="mini-checkbox" text="监区呈报日期" trueValue="监区呈报日期"  />
    			</td>
    			<td class="td1">
    				17&nbsp;<input id="displaycolumn17" name="displaycolumn17" class="mini-checkbox" text="年龄" trueValue="年龄"  />
    			</td>
    			<td class="td1">
    				18&nbsp;<input id="displaycolumn18" name="displaycolumn18" class="mini-checkbox" text="身份证" trueValue="身份证"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				19&nbsp;<input id="displaycolumn19" name="displaycolumn19" class="mini-checkbox" text="出生日期" trueValue="出生日期"  />
    			</td>
    			<td class="td1">
    				20&nbsp;<input id="displaycolumn20" name="displaycolumn20" class="mini-checkbox" text="户口分类" trueValue="户口分类"  />
    			</td>
    			<td class="td1">
    				21&nbsp;<input id="displaycolumn21" name="displaycolumn21" class="mini-checkbox" text="性别" trueValue="性别"  />
    			</td>
    			<td class="td1">
    				22&nbsp;<input id="displaycolumn22" name="displaycolumn22" class="mini-checkbox" text="民族" trueValue="民族"  />
    			</td>
    			<td class="td1">
    				23&nbsp;<input id="displaycolumn23" name="displaycolumn23" class="mini-checkbox" text="文化程度" trueValue="文化程度"  />
    			</td>
    			<td class="td1">
    				24&nbsp;<input id="displaycolumn24" name="displaycolumn24" class="mini-checkbox" text="捕前职业" trueValue="捕前职业"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				25&nbsp;<input id="displaycolumn25" name="displaycolumn25" class="mini-checkbox" text="捕前面貌" trueValue="捕前面貌"  />
    			</td>
    			<td class="td1">
    				26&nbsp;<input id="displaycolumn26" name="displaycolumn26" class="mini-checkbox" text="婚否" trueValue="婚否"  />
    			</td>
    			<td class="td1">
    				27&nbsp;<input id="displaycolumn27" name="displaycolumn27" class="mini-checkbox" text="籍贯/国籍" trueValue="籍贯/国籍"  />
    			</td>
    			<td class="td1">
    				28&nbsp;<input id="displaycolumn28" name="displaycolumn28" class="mini-checkbox" text="家庭住址" trueValue="家庭住址"  />
    			</td>
    			<td class="td1">
    				29&nbsp;<input id="displaycolumn29" name="displaycolumn29" class="mini-checkbox" text="街道" trueValue="街道"  />
    			</td>
    			<td class="td1">
    				30&nbsp;<input id="displaycolumn30" name="displaycolumn30" class="mini-checkbox" text="捕前单位" trueValue="捕前单位"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				31&nbsp;<input id="displaycolumn31" name="displaycolumn31" class="mini-checkbox" text="户籍住址" trueValue="户籍住址"  />
    			</td>
    			<td class="td1">
    				32&nbsp;<input id="displaycolumn32" name="displaycolumn32" class="mini-checkbox" text="逮捕机关" trueValue="逮捕机关"  />
    			</td>
    			<td class="td1">
    				33&nbsp;<input id="displaycolumn33" name="displaycolumn33" class="mini-checkbox" text="逮捕日期" trueValue="逮捕日期"  />
    			</td>
    			<td class="td1">
    				34&nbsp;<input id="displaycolumn34" name="displaycolumn34" class="mini-checkbox" text="判决机关" trueValue="判决机关"  />
    			</td>
    			<td class="td1">
    				35&nbsp;<input id="displaycolumn35" name="displaycolumn35" class="mini-checkbox" text="判决日期" trueValue="判决日期"  />
    			</td>
    			<td class="td1">
    				36&nbsp;<input id="displaycolumn36" name="displaycolumn36" class="mini-checkbox" text="罪名" trueValue="罪名"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				37&nbsp;<input id="displaycolumn37" name="displaycolumn37" class="mini-checkbox" text="刑期" trueValue="刑期"  />
    			</td>
    			<td class="td1">
    				38&nbsp;<input id="displaycolumn38" name="displaycolumn38" class="mini-checkbox" text="起日" trueValue="起日"  />
    			</td>
    			<td class="td1">
    				39&nbsp;<input id="displaycolumn39" name="displaycolumn39" class="mini-checkbox" text="止日" trueValue="止日"  />
    			</td>
    			<td class="td1">
    				40&nbsp;<input id="displaycolumn40" name="displaycolumn40" class="mini-checkbox" text="剥政年限" trueValue="剥政年限"  />
    			</td>
    			<td class="td1">
    				41&nbsp;<input id="displaycolumn41" name="displaycolumn41" class="mini-checkbox" text="原判刑期" trueValue="原判刑期"  />
    			</td>
    			<td class="td1">
    				42&nbsp;<input id="displaycolumn42" name="displaycolumn42" class="mini-checkbox" text="原判起日" trueValue="原判起日"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				43&nbsp;<input id="displaycolumn43" name="displaycolumn43" class="mini-checkbox" text="累惯犯" trueValue="累惯犯"  />
    			</td>
    			<td class="td1">
    				44&nbsp;<input id="displaycolumn44" name="displaycolumn44" class="mini-checkbox" text="原判止日" trueValue="原判止日"  />
    			</td>
    			<td class="td1">
    				45&nbsp;<input id="displaycolumn45" name="displaycolumn45" class="mini-checkbox" text="原剥政" trueValue="原剥政"  />
    			</td>
    			<td class="td1">
    				46&nbsp;<input id="displaycolumn46" name="displaycolumn46" class="mini-checkbox" text="原案犯类别" trueValue="原案犯类别"  />
    			</td>
    			<td class="td1">
    				47&nbsp;<input id="displaycolumn47" name="displaycolumn47" class="mini-checkbox" text="现案犯类别" trueValue="现案犯类别"  />
    			</td>
    			<td class="td1">
    				48&nbsp;<input id="displaycolumn48" name="displaycolumn48" class="mini-checkbox" text="前科次数" trueValue="前科次数"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				49&nbsp;<input id="displaycolumn49" name="displaycolumn49" class="mini-checkbox" text="劣迹次数" trueValue="劣迹次数"  />
    			</td>
    			<td class="td1">
    				50&nbsp;<input id="displaycolumn50" name="displaycolumn50" class="mini-checkbox" text="劣迹" trueValue="劣迹"  />
    			</td>
    			<td class="td1">
    				51&nbsp;<input id="displaycolumn51" name="displaycolumn51" class="mini-checkbox" text="流窜类别" trueValue="流窜类别"  />
    			</td>
    			<td class="td1">
    				52&nbsp;<input id="displaycolumn52" name="displaycolumn52" class="mini-checkbox" text="已服刑期" trueValue="已服刑期"  />
    			</td>
    			<td class="td1">
    				53&nbsp;<input id="displaycolumn53" name="displaycolumn53" class="mini-checkbox" text="入监日期" trueValue="入监日期"  />
    			</td>
    			<td class="td1">
    				54&nbsp;<input id="displaycolumn54" name="displaycolumn54" class="mini-checkbox" text="调入日期" trueValue="调入日期"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				55&nbsp;<input id="displaycolumn55" name="displaycolumn55" class="mini-checkbox" text="剩余刑期" trueValue="剩余刑期"  />
    			</td>
    			<td class="td1">
    				56&nbsp;<input id="displaycolumn56" name="displaycolumn56" class="mini-checkbox" text="何处调来" trueValue="何处调来"  />
    			</td>
    			<td class="td1">
    				57&nbsp;<input id="displaycolumn57" name="displaycolumn57" class="mini-checkbox" text="入监时余刑" trueValue="入监时余刑"  />
    			</td>
    			<td class="td1">
    				58&nbsp;<input id="displaycolumn58" name="displaycolumn58" class="mini-checkbox" text="调入时余刑" trueValue="调入时余刑"  />
    			</td>
    			<td class="td1">
    				59&nbsp;<input id="displaycolumn59" name="displaycolumn59" class="mini-checkbox" text="收押类别" trueValue="收押类别"  />
    			</td>
    			<td class="td1">
    				60&nbsp;<input id="displaycolumn60" name="displaycolumn60" class="mini-checkbox" text="在押档号" trueValue="在押档号"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				61&nbsp;<input id="displaycolumn61" name="displaycolumn61" class="mini-checkbox" text="变动后等级" trueValue="变动后等级"  />
    			</td>
    			<td class="td1">
    				62&nbsp;<input id="displaycolumn62" name="displaycolumn62" class="mini-checkbox" text="已服刑期比例" trueValue="已服刑期比例"  />
    			</td>
    			<td class="td1">
    				63&nbsp;<input id="displaycolumn63" name="displaycolumn63" class="mini-checkbox" text="减刑次数" trueValue="减刑次数"  />
    			</td>
    			<td class="td1">
    				64&nbsp;<input id="displaycolumn64" name="displaycolumn64" class="mini-checkbox" text="队别" trueValue="队别"  />
    			</td>
    			<td class="td1">
    				65&nbsp;<input id="displaycolumn65" name="displaycolumn65" class="mini-checkbox" text="监区" trueValue="监区"  />
    			</td>
    			<td class="td1">
    				66&nbsp;<input id="displaycolumn66" name="displaycolumn66" class="mini-checkbox" text="组别" trueValue="组别"  />
    			</td>
    		</tr>
    		<tr>	
    			<td class="td1">
    				67&nbsp;<input id="displaycolumn67" name="displaycolumn67" class="mini-checkbox" text="分押类型" trueValue="分押类型"  />
    			</td>
    			<td class="td1">
    				68&nbsp;<input id="displaycolumn68" name="displaycolumn68" class="mini-checkbox" text="分管等级" trueValue="分管等级"  />
    			</td>
    			<td class="td1">
    				69&nbsp;<input id="displaycolumn69" name="displaycolumn69" class="mini-checkbox" text="顽危类别" trueValue="顽危类别"  />
    			</td>
    			<td class="td1">
    				70&nbsp;<input id="displaycolumn70" name="displaycolumn70" class="mini-checkbox" text="老病残" trueValue="老病残"  />
    			</td>
    			<td class="td1">
    				71&nbsp;<input id="displaycolumn71" name="displaycolumn71" class="mini-checkbox" text="特管类别" trueValue="特管类别"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				72&nbsp;<input id="displaycolumn72" name="displaycolumn72" class="mini-checkbox" text="剥政止日" trueValue="剥政止日"  />
    			</td>
    			<td class="td1">
    				73&nbsp;<input id="displaycolumn73" name="displaycolumn73" class="mini-checkbox" text="捕前派出所" trueValue="捕前派出所"  />
    			</td>
    			<td class="td1">
    				74&nbsp;<input id="displaycolumn74" name="displaycolumn74" class="mini-checkbox" text="犯罪事实" trueValue="犯罪事实"  />
    			</td>
    			<td class="td1">
    				75&nbsp;<input id="displaycolumn75" name="displaycolumn75" class="mini-checkbox" text="简历" trueValue="简历"  />
    			</td>
    			<td class="td1">
    				76&nbsp;<input id="displaycolumn76" name="displaycolumn76" class="mini-checkbox" text="社会关系" trueValue="社会关系"  />
    			</td>
    			<td class="td1">
    				77&nbsp;<input id="displaycolumn77" name="displaycolumn77" class="mini-checkbox" text="体貌特征" trueValue="体貌特征"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				78&nbsp;<input id="displaycolumn78" name="displaycolumn78" class="mini-checkbox" text="特长" trueValue="特长"  />
    			</td>
    			<td class="td1">
    				79&nbsp;<input id="displaycolumn79" name="displaycolumn79" class="mini-checkbox" text="内务分工" trueValue="内务分工"  />
    			</td>
    			<td class="td1">
    				80&nbsp;<input id="displaycolumn80" name="displaycolumn80" class="mini-checkbox" text="检查结果" trueValue="检查结果"  />
    			</td>
    			<td class="td1">
    				81&nbsp;<input id="displaycolumn81" name="displaycolumn81" class="mini-checkbox" text="检查理由" trueValue="检查理由"  />
    			</td>
    			<td class="td1">
    				82&nbsp;<input id="displaycolumn82" name="displaycolumn82" class="mini-checkbox" text="检查意见" trueValue="检查意见"  />
    			</td>
    			<td class="td1">
    				83&nbsp;<input id="displaycolumn83" name="displaycolumn83" class="mini-checkbox" text="三类罪犯" trueValue="三类罪犯"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				84&nbsp;<input id="displaycolumn84" name="displaycolumn84" class="mini-checkbox" text="重要罪犯" trueValue="重要罪犯"  />
    			</td>
    			<td class="td1">
    				85&nbsp;<input id="displaycolumn85" name="displaycolumn85" class="mini-checkbox" text="涉黑罪犯" trueValue="涉黑罪犯"  />
    			</td>
    			<td class="td1">
    				86&nbsp;<input id="displaycolumn86" name="displaycolumn86" class="mini-checkbox" text="原县级以上干部罪犯" trueValue="原县级以上干部罪犯"  />
    			</td>
    			<td class="td1">
    				87&nbsp;<input id="displaycolumn87" name="displaycolumn87" class="mini-checkbox" text="外国籍罪犯" trueValue="外国籍罪犯"  />
    			</td>
    			<td class="td1">
    				88&nbsp;<input id="displaycolumn88" name="displaycolumn88" class="mini-checkbox" text="港澳台罪犯" trueValue="港澳台罪犯"  />
    			</td>
    			<td class="td1">
    				89&nbsp;<input id="displaycolumn89" name="displaycolumn89" class="mini-checkbox" text="报局审核案件" trueValue="报局审核案件"  />
    			</td>
    		</tr>
    		<tr>
    			<td class="td1">
    				90&nbsp;<input id="displaycolumn90" name="displaycolumn90" class="mini-checkbox" text="提请类别" trueValue="提请类别"  />
    			</td>
    			<td class="td1">
    				91&nbsp;<input id="displaycolumn91" name="displaycolumn91" class="mini-checkbox" text="提请幅度" trueValue="提请幅度"  />
    			</td>
    		</tr>
    	</table>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="width:100%;"
        allowCellEdit="true" showPager="false" allowCellSelect="true">
    </div>
    <div style="clear:both;"></div>
    <div style="border:1px solid #324B62;margin-top:5px;display: none;width: 0px;">
    	<table>
    		<tr>
    			<td colspan="3">
    				<input type="checkbox" value=""/>是否显示统计信息
    				<input type="checkbox" value=""/>是否显示分组信息
    			</td>
    		</tr>
    		<tr >
    			<table style="width:100%;border-top:2px solid #324B62;border-bottom:1px solid #324B62;">
    				<tr>
		    			<td colspan="3">
		    				排序条件:
		    			</td>
		    		</tr>
		    		<tr>
		    			<td class="td1">
		    				排序条件1:<select style="width:80px;"><option>1</option></select>
		    				<input type="radio" value="" checked="checked" />升序
		    				<input type="radio" value="" />降序
		    			</td>
		    			<td class="td1">
		    				排序条件2:<select style="width:80px;"><option>1</option></select>
		    				<input type="radio" value="" checked="checked"/>升序
		    				<input type="radio" value="" />降序
		    			</td>
		    			<td class="td1">
		    				排序条件3:<select style="width:80px;"><option>1</option></select>
		    				<input type="radio" value="" checked="checked"/>升序
		    				<input type="radio" value="" />降序
		    			</td>
		    		</tr>
		    		<tr>
		    			<td class="td1">
		    				排序条件4:<select style="width:80px;"><option>1</option></select>
		    				<input type="radio" value="" checked="checked" />升序
		    				<input type="radio" value="" />降序
		    			</td>
		    			<td class="td1">
		    				排序条件5:<select style="width:80px;"><option>1</option></select>
		    				<input type="radio" value="" checked="checked"/>升序
		    				<input type="radio" value="" />降序
		    			</td>
		    			<td class="td1">
		    				排序条件6:<select style="width:80px;"><option>1</option></select>
		    				<input type="radio" value="" checked="checked"/>升序
		    				<input type="radio" value="" />降序
		    			</td>
		    		</tr>
    			</table>
    		</tr>
    		<tr>
    			<table style="width:100%;border-top:1px solid #324B62;border-bottom:1px solid #324B62;">
    				<tr>
    					<td class="td1">统计选项</td>
    				</tr>
    				<tr>
    					<td class="td1"><select style="width:80px;"><option>1</option></select></td>
    				</tr>
    			</table>
    		</tr>
    		<tr>
    			<table style="width:100%;border-top:1px solid #324B62;">
    				<tr>
    					<td  align="center">
    						<input type="button" value="保存默认值"/>
    						<a class="mini-button" id="onOk" onclick="onOk" plain="true" >确定</a>
    						<a class="mini-button" id="onExportExcel" onclick="ExportExcel();" plain="true" >导出Excel</a>
    						<a class="mini-button" id="onStatistics" onclick="onStatistics" plain="true" >统计</a>
    						<a class="mini-button" id="onClear" onclick="onClear" plain="true" >重置</a>
		           			<a class="mini-button" id="onBack" onclick="onBack"  plain="true" >返回</a>  
    					</td>
    				</tr>
    			</table>
    		</tr>
    	</table>
    </div>
   	<div  style="border-bottom:0;padding:0px;">
            <table style="width:100%;" >
                <tr >
                    <td style="width:100%;text-align:center;">
                        <a class="mini-button" id="onOk" onclick="onOk" plain="" >确定</a>
                        <!-- 此处先注掉，本为查询数据库导出 -->
						<!--  <a class="mini-button" id="onExportExcel" onclick="ExportExcel();" plain="" >导出Excel</a>-->
						<a class="mini-button" id="onStatistics" onclick="onStatistics" plain="" >统计</a>
						<a class="mini-button" id="onClear" onclick="onClear" plain="" >重置</a>
        				<a class="mini-button" id="onBack" onclick="onBack"  plain="" >返回</a>       
                    </td>
                </tr>
            </table>           
        </div>
   	<!--导出Excel相关HTML-->
   	<iframe id="exportIFrame" style="display:none;">
   	</iframe>
    <form id="excelForm"  action="<%=path%>/public/exportExcel.action?1=1" method="post" target="excelIFrame">
        <input type="hidden" name="columns" id="excelData" />
    </form>
    <iframe id="excelIFrame" name="excelIFrame" style="display:none;"></iframe>
    <form id="statisticForm"  action="<%=path%>/public/listStatisticalQuery.action?1=1" method="post" >
        <input type="hidden" name="columnsData" id="columnsData" />
        <input type="hidden" name="columnsjson" id="columnsjson" />
        <input type="hidden" name="queryWhereData" id="queryWhereData" />
    </form>
    <form id="queryForm"  action="<%=path%>/public/statisticalQuery.page?1=1" method="post" >
        <input type="hidden" name="queryWhereData" id="queryData" />
    </form>
    <script type="text/javascript">
   		mini.parse();
   		var form = new mini.Form("#form1");
   		var grid = new mini.get("datagrid1");

		//数据回显
   		var columnsData = mini.decode(${parmMap.columnsData});
        if(columnsData && columnsData !='undefined') form.setData(columnsData);
        
	    function onOk(menuid) {
	    	var data = form.getData();
	    	var datavalue = mini.encode(data);
	    	var fields = form.getFields();    
	    	var gridcolumns; 
	    	gridcolumns = '{type: "indexcolumn",header:"序号",headerAlign: "center",align:"center",width: "10"}';
	    	if(fields && fields.length>0){
	    		 for (var i = 0, l = fields.length; i < l; i++) {
	                 var c = fields[i];
	                 if(c.value && c.value !='false'){
	                 	gridcolumns +=",{ field: '"+c.name+"', width: 30,headerAlign: 'center',align:'center', header: '"+c.value+"', allowSort: false}";
	                 }
	             }
	    	}
    	    grid.set({
    	        columns: mini.decode("["+gridcolumns+"]")
    	    });
    	    document.getElementById("columnsjson").value = "["+gridcolumns+"]";
	    }
	    function onStatistics(menuid) {
	    	var data = form.getData(true);
	    	var datavalue = mini.encode(data);
	    	var fields = form.getFields();    
	    	var gridcolumns;
	    	gridcolumns = '{type: "indexcolumn",header:"序号",headerAlign: "center",align:"center",width: "10"}';
	    	if(fields && fields.length>0){
	    		 for (var i = 0, l = fields.length; i < l; i++) {
	                 var c = fields[i];
	                 if(c.value && c.value !='false'){
	                 	gridcolumns +=",{ field: '"+c.name+"', width: 30,headerAlign: 'center',align:'center', header: '"+c.value+"', allowSort: false}";
	                 }
	             }
	    	}
	    	
	    	document.getElementById("columnsjson").value = "["+gridcolumns+"]";
	    	var url = "<%=path%>/public/listStatisticalQuery.action?1=1";
	    	document.getElementById("columnsData").value = datavalue;
	    	//where 条件参数
	    	var queryWhereData = mini.encode(${parmMap.queryWhereData});
			document.getElementById("queryWhereData").value = queryWhereData;
            var statisticForm = document.getElementById("statisticForm");
            statisticForm.submit();
	    }
		//重置
		function onClear(){
			form.reset();
		}
		//关闭
		function onBack(){
			var url = "<%=path%>/public/statisticalQuery.page?1=1";
			var queryWhereData = mini.encode(eval(${parmMap.queryWhereData}));
			document.getElementById("queryData").value = queryWhereData;
			var queryForm = document.getElementById("queryForm");
			queryForm.submit();
			
		}
		function ExportExcel() {
            var columns = grid.getBottomColumns();
            
            function getColumns(columns) {
                columns = columns.clone();
                for (var i = columns.length - 1; i >= 0; i--) {
                    var column = columns[i];
                   /* if (!column.field) {
                        columns.removeAt(i);
                    } else {
                        var c = { header: column.header, field: column.field };
                        columns[i] = c;
                    }*/
                    var c = { header: column.header, field: column.field };
                    columns[i] = c;
                }
                return columns;
            }
            
            var columns = getColumns(columns);
            var json = mini.encode(columns);      
            document.getElementById("excelData").value = json;
            var excelForm = document.getElementById("excelForm");
            excelForm.submit(); 
        }
    </script>
  </body>
</html>
