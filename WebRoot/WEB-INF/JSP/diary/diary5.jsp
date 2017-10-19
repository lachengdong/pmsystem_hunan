<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>高危犯专管监区值班日志</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/yzDutyDiary.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	
	<style type="text/css">

	body{
		   margin:0;padding:0;border:0;width:100%;height:100%; font-size: 12px;background:#efefef;
	}
	
	.trd {
		margin:0;
		padding:0;
		border: 1px solid black;
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
		<input id="flowdraftid" name="flowdraftid" class="mini-hidden" value="${flowdraftid}"/>
		<input id="diarytype" name="diarytype" class="mini-hidden" value="${diarytype}"/> <!-- 日记类型，3：医疗防疫站值班日志 -->
		<input id="saveflag" name="saveflag" class="mini-hidden" value="0"/> <!-- 是否点击过保存按钮 -->
		<table >
             <tr>
                <td style="width:100%;">
                	&nbsp;&nbsp;
                </td>
                <td style="white-space:nowrap;"> 
                	<!--  
                	<a class="mini-button" iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>
                	&nbsp;
                	-->
                	<a class="mini-button" plain="true" iconCls="icon-save"  onclick="saveData();">保存</a>
                	&nbsp;
					<a class="mini-button" iconCls="icon-cancel"  plain="true" onclick="Close();" >关闭</a>
                </td>
             </tr>
        </table>
	</div>  
	
<form action="">
	<div align="center" >
	<table width="60%"  border=0 cellspacing=0 cellpadding=0  style='border-collapse:collapse;'>
		<tr>
			<td colspan="18" style="height:40px;"><span style="font-size:16px;color:#ADADAD">细节决定成败&nbsp;&nbsp;&nbsp;&nbsp;责任重于泰山</span></td>
		</tr>
		
		<tr>
			<td colspan="18"><span style="font-size:24px;font-weight:bold;">${departname}高危犯专管监区值班日志</span></td>
		</tr>
		
		
		<tr height="5px">
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
			<td width="5.56%" style="border:0">&nbsp;</td>
		</tr>
		
		<tr >
			<td colspan="4" style="border:0">日期：<input id="diarydate" name="diarydate" value="${diarydate}" class="mini-datepicker" style="width:100px;" value="" /></td>
			<td colspan="4" style="border:0">
				<input class="mini-combobox" id="weekday" name="weekday" value="${weekday}" style="width:80px;" textField="text" valueField="id" 
    				data="[{id:'星期日',text:'星期日'},{id:'星期一',text:'星期一'},{id:'星期二',text:'星期二'},{id:'星期三',text:'星期三'},{id:'星期四',text:'星期四'},{id:'星期五',text:'星期五'},{id:'星期六',text:'星期六'}]"/>
			</td>
			<td colspan="4" style="border:0">天气：
				<input class="mini-combobox" id="weather" name="weather" value="${weather}" style="width:80px;" textField="text" valueField="id" 
    				data="[{id:'晴',text:'晴'},{id:'晴转多云',text:'晴转多云'},{id:'多云',text:'多云'},{id:'阴',text:'阴'},{id:'雾',text:'雾'},{id:'雨夹雪',text:'雨夹雪'},
    				{id:'雷阵雨',text:'雷阵雨'},{id:'小雨',text:'小雨'},{id:'中雨',text:'中雨'},{id:'大雨',text:'大雨'},
    				{id:'暴雨',text:'暴雨'},{id:'小雪',text:'小雪'},{id:'大雪',text:'大雪'},{id:'冰雹',text:'冰雹'}]" />
			</td>
			<td colspan="6" style="border:0">交接班时间：
				<input id="transferhour" name="transferhour" value="${transferhour}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;时&nbsp;
				<input id="transferminute" name="transferminute" value="${transferminute}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;分
			</td>
		</tr>
		<tr>
			<td class="trd" style="border-left:solid black 1.5pt;border-top:solid black 1.5pt;">带班<br/>监区<br/>领导</td>
			<td class="trd" colspan="2" style="border-top:solid black 1.5pt;">
				<textarea id="leader" name="leader" style="width:95%;height:60px;overflow-x:hidden;overflow-y:hidden;">${leader}</textarea>
			</td>
			<td class="trd" style="border-top:solid black 1.5pt;">值班<br/>警察</td>
			<td class="trd" colspan="6" style="border-top:solid black 1.5pt;" align="left">
				<textarea id="policemen" name="policemen" style="width:97.5%;height:60px;overflow-x:hidden;overflow-y:hidden;">${policemen}</textarea>
			</td>
			<td class="trd" style="border-top:solid black 1.5pt;">备勤<br/>警察</td>
			<td class="trd" colspan="3" style="border-top:solid black 1.5pt;">
				<textarea id="bpmen" name="bpmen" style="width:95%;height:60px;overflow-x:hidden;overflow-y:hidden;">${bpmen}</textarea>
			</td>
			<td class="trd" style="border-top:solid black 1.5pt;">备注</td>
			<td class="trd" colspan="3" align="left" style="border-top:solid black 1.5pt;border-right:solid black 1.5pt;">
				<textarea id="remark" name="remark" style="width:94%;height:60px;overflow-x:hidden;overflow-y:hidden;">${remark}</textarea>
			</td>
		</tr>
		
		<tr>
			<td class="trd" colspan="10" style="border-left:solid black 1.5pt;">当日押犯基本情况</td>
			<td class="trd" colspan="8" style="border-right:solid black 1.5pt;">离监</td>
		</tr>
		<tr>
			<td class="trd" style="border-left:solid black 1.5pt;">在册</td>
			<td class="trd" >实押</td>
			<td class="trd" >留监</td>
			<td class="trd" >调入</td>
			<td class="trd" >调出</td>
			<td class="trd" >禁闭</td>
			<td class="trd"  >严管</td>
			<td class="trd" >隔离</td>
			<td class="trd" >专管</td>
			<td class="trd" >防疫站住院</td>
			<td class="trd" >离监<br/>就医</td>
			<td class="trd" >特许<br/>离监</td>
			<td class="trd" >新安<br/>医院<br/>住院</td>
			<td class="trd" >社会<br/>医院<br/>住院</td>
			<td class="trd" >保外<br/>就医</td>
			<td class="trd" >释放</td>
			<td class="trd" >死亡</td>
			<td class="trd" style="border-right:solid black 1.5pt;">其他</td>
		</tr>
		
		<tr height="30px">
			<td class="trd"  style="border-left:solid black 1.5pt;">
				<textarea id="registernum" name="registernum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${registernum}</textarea>
			</td>
			<td class="trd" ><textarea id="realnum" name="realnum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${realnum}</textarea></td>
			<td class="trd" ><textarea id="staynum" name="staynum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${staynum}</textarea></td>
			<td class="trd" ><textarea id="getinnum" name="getinnum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${getinnum}</textarea></td>
			<td class="trd" ><textarea id="takeoutnum" name="takeoutnum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${takeoutnum}</textarea></td>
			<td class="trd" ><textarea id="jinbinum" name="jinbinum"  onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${jinbinum}</textarea></td>
			<td class="trd" ><textarea id="yanguannum" name="yanguannum"  onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${yanguannum}</textarea></td>
			<td class="trd" ><textarea id="gelinum" name="gelinum"  onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${gelinum}</textarea></td>
			<td class="trd" ><textarea id="zhuanguannum" name="zhuanguannum"  onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${zhuanguannum}</textarea></td>
			<td class="trd" ><textarea id="fyzzynum" name="fyzzynum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${fyzzynum}</textarea></td>
			<td class="trd" ><textarea id="ljjynum" name="ljjynum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${ljjynum}</textarea></td>
			<td class="trd" ><textarea id="txljnum" name="txljnum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${txljnum}</textarea></td>
			<td class="trd" ><textarea id="xayynum" name="xayynum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${xayynum}</textarea></td>
			<td class="trd" ><textarea id="shyynum" name="shyynum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${shyynum}</textarea></td>
			<td class="trd" ><textarea id="bwjynum" name="bwjynum"  onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${bwjynum}</textarea></td>
			<td class="trd" ><textarea id="releasenum" name="releasenum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${releasenum}</textarea></td>
			<td class="trd" ><textarea id="deathnum" name="deathnum"  onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${deathnum}</textarea></td>
			<td  class="trd" style="border-right:solid black 1.5pt;">
				<textarea id="othernum" name="othernum"  onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${othernum}</textarea>
			</td>
		</tr>
		
		
		<tr>
			<td class="trd" colspan="3" style="border-left:solid black 1.5pt;">高危犯专管人数</td>
			<td class="trd" colspan="2">专管人数</td>
			<td class="trd" colspan="2"><textarea id="zhuanguannum1" name="zhuanguannum1" onchange="validateNum(this);" style="width:92%;height:20px;overflow-x:hidden;overflow-y:hidden;">${zhuanguannum1}</textarea></td>
			<td class="trd" colspan="2">当日专管</td>
			<td class="trd" colspan="2"><textarea id="currzhuanguannum" name="currzhuanguannum" onchange="validateNum(this);" style="width:92%;height:20px;overflow-x:hidden;overflow-y:hidden;">${currzhuanguannum}</textarea></td>
			<td class="trd" colspan="2">当日解除</td>
			<td class="trd" colspan="2"><textarea id="currjiechuzhuanguan" name="currjiechuzhuanguan" onchange="validateNum(this);" style="width:92%;height:20px;overflow-x:hidden;overflow-y:hidden;">${currjiechuzhuanguan}</textarea></td>
			<td class="trd" >其他</td>
			<td class="trd" colspan="2" style="border-right:solid black 1.5pt;">
				<textarea id="otherzhuanguannum" name="otherzhuanguannum" onchange="validateNum(this);" style="width:92%;height:20px;overflow-x:hidden;overflow-y:hidden;">${otherzhuanguannum}</textarea>
			</td>
		</tr>
		
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">起床<br/>环节</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left" valign="top">
				<table >
					<tr align="left">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="getuphour" name="getuphour" value="${getuphour}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;时&nbsp;
							<input id="getupminute" name="getupminute" value="${getupminute}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;分，组织起床，
							查人<input id="getupchecknum" name="getupchecknum" value="${getupchecknum}" type="text" style="width:40px" onchange="validateNum(this);" />名，
							洗漱及整理内务、卫生情况：
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="getupinfo" name="getupinfo" rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${getupinfo}</textarea>
						</td>
					</tr>
					
					
					<tr align="left">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="gtreporthour" name="gtreporthour" value="${gtreporthour}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;时&nbsp;
							<input id="gtreportminute" name="gtreportminute" value="${gtreportminute}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;分，
							向总值班室报告情况：
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="gtreportinfo" name="gtreportinfo" rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${gtreportinfo}</textarea>
						</td>
					</tr>
				</table>
				
			</td>
		</tr>
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">早餐<br/>环节</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left">
				<table >
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="breakfasthour" name="breakfasthour" value="${breakfasthour}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;时&nbsp;
							<input id="breakfastminute" name="breakfastminute" value="${breakfastminute}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;分，
							早餐查人<input id="breakfastchecknum" name="breakfastchecknum" value="${breakfastchecknum}" type="text" style="width:40px" onchange="validateNum(this);" />名，
							未到<input id="bkabsentnum" name="bkabsentnum" value="${bkabsentnum}" type="text" style="width:40px" onchange="validateNum(this);" />名，
							未到原因：
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="bkabsentreason" name="bkabsentreason"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${bkabsentreason}</textarea>
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现场巡查情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="bkcheckinfo" name="bkcheckinfo" rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${bkcheckinfo}</textarea>
						</td>
					</tr>
				</table>
				
			</td>
		</tr>
		
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">午餐<br/>环节</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left">
				<table >
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="lunchhour" name="lunchhour" value="${lunchhour}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;时&nbsp;
							<input id="lunchminute" name="lunchminute" value="${lunchminute}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;分，
							午餐查人<input id="lunchchecknum" name="lunchchecknum" value="${lunchchecknum}" type="text" style="width:40px" onchange="validateNum(this);"/>名，
							未到<input id="lunchabsentnum" name="lunchabsentnum" value="${lunchabsentnum}" type="text" style="width:40px" onchange="validateNum(this);"/>名，
							未到原因：
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="lunchabsentreason" name="lunchabsentreason" rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${lunchabsentreason}</textarea>
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现场巡查情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="lunchcheckinfo" name="lunchcheckinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${lunchcheckinfo}</textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">晚餐<br/>环节</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left">
				<table >
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="dinnerhour" name="dinnerhour" value="${dinnerhour}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;时&nbsp;
							<input id="dinnerminute" name="dinnerminute" value="${dinnerminute}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;分，
							晚餐查人<input id="dinnerchecknum" name="dinnerchecknum" value="${dinnerchecknum}" type="text" style="width:40px" onchange="validateNum(this);"/>名，
							未到<input id="dinnerabsentnum" name="dinnerabsentnum" value="${dinnerabsentnum}" ctype="text" style="width:40px" onchange="validateNum(this);" />名，
							未到原因：
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="dinnerabsentreason" name="dinnerabsentreason"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${dinnerabsentreason}</textarea>
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现场巡查情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="dinnercheckinfo" name="dinnercheckinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${dinnercheckinfo}</textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		
		<tr height="100px">
			<td class="trd" style="border-left:solid black 1.5pt;">就寝<br/>环节</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left">
				<table >
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="sleephour" name="sleephour" value="${sleephour}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;时&nbsp;
							<input id="sleepminute" name="sleepminute" value="${sleepminute}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;分，
							组织就寝查人<input id="sleepchecknum" name="sleepchecknum" value="${sleepchecknum}" type="text" style="width:40px" onchange="validateNum(this);" />名，
							组织洗漱情况：
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="washinfo" name="washinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${washinfo}</textarea>
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="checkfacilityhour" name="checkfacilityhour" value="${checkfacilityhour}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;时&nbsp;
							<input id="checkfacilityminute" name="checkfacilityminute" value="${checkfacilityminute}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;分，
							检查监管设施（隔离门、防护网、锁具、消防柜、卫生间和洗漱间窗网等）情况：
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="checkfacilityinfo" name="checkfacilityinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${checkfacilityinfo}</textarea>
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="sleepreporthour" name="sleepreporthour" value="${sleepreporthour}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;时&nbsp;
							<input id="sleepreportminute" name="sleepreportminute" value="${sleepreportminute}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;分，
							向总值班报告情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="sleepreportinfo" name="sleepreportinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${sleepreportinfo}</textarea>
						</td>
					</tr>
					
				</table>
				
				
			</td>
		</tr>
		
		
		<tr height="100px">
			<td class="trd" style="border-left:solid black 1.5pt;">夜间<br/>环节</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left">
				<table>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							安排监督岗情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="nightcheckinfo" name="nightcheckinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${nightcheckinfo}</textarea>
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							监督岗报告情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							23：00 <input id="nighthour23" name="nighthour23"  value="${nighthour23}" type="text"  style="width:100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							24：00 <input id="nighthour24" name="nighthour24"  value="${nighthour24}" type="text"  style="width:100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							01：00 <input id="nighthour01" name="nighthour01"    value="${nighthour01}" type="text"  style="width:100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							02：00 <input id="nighthour02" name="nighthour02"  value="${nighthour02}" type="text"  style="width:100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							03：00 <input id="nighthour03" name="nighthour03"  value="${nighthour03}" type="text"  style="width:100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							04：00 <input id="nighthour04" name="nighthour04"  value="${nighthour04}" type="text"  style="width:100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							05：00 <input id="nighthour05" name="nighthour05"  value="${nighthour05}" type="text"  style="width:100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
							06：00 <input id="nighthour06" name="nighthour06"  value="${nighthour06}" type="text"  style="width:100px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;异常情况处理：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="nightexception" name="nightexception"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${nightexception}</textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		
		<tr height="150px">
			<td class="trd" style="border-left:solid black 1.5pt;">高危<br/>罪犯<br/>管控<br/>情况</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" id="td1">
				<div class="mini-fit" height="60px"  id="trid1" style="overflow:hidden;">
				     <div id="datagrid1" class="mini-datagrid" style="height:100%;" idField="id" multiSelect="true"
				     	allowCellEdit="true" allowCellSelect="true" allowAlternating="true" virtualScroll="false"
				     	url="<%=path%>/getDiaryDetailData.json?1=1" showPager="false" onload="onGrid1Load();">
				         <div property="columns">
				         	<div type="checkcolumn" width="0"></div>
				         	<div field="id"  width="0" headerAlign="center"  visible="false" align="center"></div>  
				         	<div field="type"  width="0" headerAlign="center"  visible="false" align="center"></div>
				            <div field="crimid"  width="11%" headerAlign="center"  align="center" >罪犯编号
				                <input property="editor" class="mini-buttonedit"  allowInput="true"  onbuttonclick="onButtonEdit1" style="width:100%;"/>
				            </div>
				            <div field="name"  width="11%" headerAlign="center"  align="center">罪犯姓名
				            	<input property="editor" class="mini-buttonedit"  allowInput="true"  onbuttonclick="onButtonEdit1" style="width:100%;"/>
				            </div>  
				            <div field="orgid" displayField="orgname" width="11%" headerAlign="center"  align="center" >所在监区
				            	<input property="editor" class="mini-buttonedit"  allowInput="true"  onbuttonclick="onButtonEdit1" style="width:100%;"/>
				            </div> 
				           <div field="reason" width="22%" headerAlign="center"   align="center"  >专管原因
				                <input property="editor" class="mini-textbox"  allowInput="true"  style="width:100%;"/> 
				            </div>        
				           <div field="info" width="33%" headerAlign="center"  align="center"  >管控情况
				                <input property="editor" class="mini-textbox"  allowInput="true"  style="width:100%;"/>
				            </div>        
				            <div cellCls="actionIcons" name="action"  headerAlign="center" align="center" renderer="onActionRenderer1" cellStyle="padding:0;">
				            	<span class="icon-new" title="增加记录" onclick="newRow1()"　align="center">&nbsp;&nbsp;&nbsp;&nbsp;</span>
				            </div>                                        
				        </div>
				    </div>   
				</div>
			</td>
		</tr>
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">重点<br/>人员<br/>管控<br/>情况</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left" >
				<table>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;重点人员<input id="importantnum" name="importantnum" value="${importantnum}" type="text" style="width:40px" onchange="validateNum(this);" />名，情况为：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="importantinfo" name="importantinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${importantinfo}</textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr height="80px">
			<td class="trd" style="border-left:solid black 1.5pt;">要事<br/>记载</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left">
				<table style="width:100%;height:100%;">
					<tr>
						<td>
							<textarea id="businessrecord" name="businessrecord"  style="width:99%;height:75px;overflow-x:hidden;overflow-y:hidden;">${businessrecord}</textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr height="80px" valign="top">
			<td class="trd" valign="center" style="border-left:solid black 1.5pt;">交接<br/>提示</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;" align="left">
			
				<table style="width:100%;height:100%;">
					<tr>
						<td colspan="2">
							<textarea id="handovertips" name="handovertips"  style="width:99%;height:50px;overflow-x:hidden;overflow-y:hidden;">${handovertips}</textarea>
						</td>
					</tr>
					<tr align="center">
						<td>
							交班警察：<input id="submitpolice" name="submitpolice" value="${submitpolice}" type="text" style="width:120px" />
						</td>
						<td>
							接班警察：<input id="receivepolice" name="receivepolice" value="${receivepolice}" type="text" style="width:120px" />
						</td>
					</tr>
				</table>
				
			</td>
		</tr>
		
		<tr height="80px">
			<td class="trd" style="border-left:solid black 1.5pt;border-bottom:solid black 1.5pt;">巡查<br/>督察</td>
			<td class="trd" colspan="17" style="border-right:solid black 1.5pt;border-bottom:solid black 1.5pt;" align="left">
				<table style="width:100%;height:100%;">
					<tr>
						<td>
							<textarea id="inspector" name="inspector"  style="width:99%;height:75px;overflow-x:hidden;overflow-y:hidden;">${inspector}</textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
	</table>
	
	<p>&nbsp;</p>
 </div>
 </form>
 
<script type="text/javascript">

    mini.parse();
    var grid1 = mini.get("datagrid1");
    
   function reLoadGrid(flowdraftid, operatetype){
	   grid1.load({flowdraftid:flowdraftid, type:"50", operatetype:operatetype});
   }
   
    function saveData(){
    	//先验证表单数据是否附合（如：小时范围0-24，分范围：0-60），后继再处理
    	
    	var formMap = getFormMapData();//获取form数据
    	var diarytype = mini.get("diarytype").getValue();
    	formMap["diarytype"] = diarytype;
    	
    	var flowdraftid = mini.get("flowdraftid").getValue();
    	if(flowdraftid){
    		formMap["flowdraftid"] = flowdraftid;
    	}
    	var formdata = mini.encode([formMap]);
    	
    	//grid1.validate();
    	var flag = grid1.isValid();
    	if(false == flag){
    		alert("高危罪犯管控情况，数据有错！");
    		return;
    	}
    	
		var rows1Data = getAllRowsData(grid1);
        var grid1data = mini.encode(rows1Data);
    	
        $.ajax({
            url: "<%=path %>/saveDiaryData.json?1=1",
            data: {formdata:formdata, grid1data:grid1data},
            dataType:"text",
            type:"POST",
            success: function (text){
            	var data = mini.decode(text);
            	if('success' == data.status){
            		mini.get("flowdraftid").setValue(data.flowdraftid);
            		mini.get("saveflag").setValue(1);
            		reLoadGrid(data.flowdraftid, 'edit');	//保存后，只查询保存后的数据
            		alert("操作成功！");
            	}else{
            		alert("操作失败！");
            	}
            	//Close();
            }
        });
    	
    }
    
    function newRow1(){
    	newRow(grid1, "trid1", "datagrid1", "50");
    }
    function delRow1(row_uid){
    	delRow(row_uid, grid1, "trid1", "datagrid1");
    }
     //
    var operatetype = '${operatetype}';	//操作类型：新增/修改
    var flowdraftid = '${flowdraftid}';
     if(operatetype && 'new' == operatetype){
    	 initial();//初始化日期、星期
    	 reLoadGrid(null, operatetype);
     }else if(operatetype && 'edit' == operatetype){
     	 reLoadGrid(flowdraftid, operatetype);
     }
 	
    function SetData(data){
    	//data = mini.clone(data);
    	//mini.get("svtype").setValue(data.svtype);
    }
    
     function onButtonEdit1(e){
    	 selectCriminal(grid1);
     }
     
     function selectCriminal(grid){
 	  	 var url = "<%=path%>/toSelectCrimePage.page?1=1";
       	mini.open({
               url : url,
               width: 800,
               height: 450,
               onload: function () {
			   //  var iframe = this.getIFrameEl();
			   //  iframe.contentWindow.SetData(null);
               },
               ondestroy: function (action) {
                   if (action == "ok"){
                       var iframe = this.getIFrameEl();
                       var data = iframe.contentWindow.GetData();
                       data = mini.clone(data);    //必须
					   grid.cancelEdit();
                       var row = grid.getSelected();
                       grid.updateRow(row, {
                           crimid: data.crimid,
                           name: data.name,
                           orgid: data.orgid,
                           orgname: data.orgname
                       });
                   }
               }
           });
   	}
      
</script>
</body>
</html>