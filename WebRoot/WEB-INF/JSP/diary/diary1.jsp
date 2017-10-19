<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>监区值班日志</title>
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
			<td colspan="18"><span style="font-size:24px;font-weight:bold;">${departname}监区值班日志</span></td>
		</tr>
		
		
		<tr height="5px">
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
			<td width="5.23%" style="border:0">&nbsp;</td>
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
			<td class="trd" colspan="3" style="border-top:solid black 1.5pt;">
				<textarea id="leader" name="leader" style="width:95%;height:68px;overflow-x:hidden;overflow-y:hidden;">${leader}</textarea>
			</td>
			<td class="trd" style="border-top:solid black 1.5pt;">值班<br/>警察</td>
			<td class="trd" colspan="3" style="border-top:solid black 1.5pt;" align="left">
				<textarea id="policemen" name="policemen" style="width:95%;height:68px;overflow-x:hidden;overflow-y:hidden;">${policemen}</textarea>
			</td>
			<td class="trd" style="border-top:solid black 1.5pt;">带工<br/>警察</td>
			<td class="trd" colspan="2" style="border-top:solid black 1.5pt;" align="left">
				<textarea id="takepolicemen" name="takepolicemen" style="width:93%;height:68px;overflow-x:hidden;overflow-y:hidden;">${takepolicemen}</textarea>
			</td>
			<td class="trd" style="border-top:solid black 1.5pt;">备勤<br/>警察</td>
			<td class="trd" colspan="3" style="border-top:solid black 1.5pt;">
				<textarea id="bpmen" name="bpmen" style="width:95%;height:68px;overflow-x:hidden;overflow-y:hidden;">${bpmen}</textarea>
			</td>
			<td class="trd" style="border-top:solid black 1.5pt;">外协</td>
			
			<td class="trd" width="10%" colspan="3" style="border-top:solid black 1.5pt;border-right:solid black 1.5pt;">
			
				<table style="width:100%;height:68px;"  border=0 cellspacing=0 cellpadding=0 >
					<tr>
						<td style="width:35%;border-bottom: 1px solid black;">男</td>
						<td style="border-left: 1px solid black;border-bottom: 1px solid black;">
							<textarea id="wxmen" name="wxmen" style="width:93%;height:30px;overflow-x:hidden;overflow-y:hidden;">${wxmen}</textarea>
						</td>
					</tr>
					<tr>
						<td  style="width:35%">女</td>
						<td style="border-left:1px solid black;">
							<textarea id="wxwomen" name="wxwomen" style="width:93%;height:30px;overflow-x:hidden;overflow-y:hidden;">${wxwomen}</textarea>
						</td>
					</tr>
				</table>
				
			</td>
			
		</tr>
		
		<tr>
			<td class="trd" colspan="11" style="border-left:solid black 1.5pt;">当日押犯基本情况</td>
			<td class="trd" colspan="8" style="border-right:solid black 1.5pt;">离监</td>
		</tr>
		<tr>
			<td class="trd" style="border-left:solid black 1.5pt;">在册</td>
			<td class="trd" >实押</td>
			<td class="trd" >出工</td>
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
			<td class="trd" ><textarea id="worknum" name="worknum" onchange="validateNum(this);" style="width:86%;height:20px;overflow-x:hidden;overflow-y:hidden;">${worknum}</textarea></td>
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
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">起床<br/>环节</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left" valign="top">
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
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
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
			<td class="trd" style="border-left:solid black 1.5pt;">出工<br/>环节</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
				<table >
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="wkhour" name="wkhour" value="${wkhour}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;时&nbsp;
							<input id="wkminute" name="wkminute" value="${wkminute}" type="text" style="width:30px" onchange="validateNum(this);"/>&nbsp;分，
							出工<input id="worknum1" name="worknum1" value="${worknum1}" type="text" style="width:40px" onchange="validateNum(this);" />名，
							清查罪犯人身<input id="wkcheckbodynum" name="wkcheckbodynum" value="${wkcheckbodynum}" type="text" style="width:40px" onchange="validateNum(this);" />名，
							清查情况：
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="wkcheckbodyinfo" name="wkcheckbodyinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${wkcheckbodyinfo}</textarea>
						</td>
					</tr>
					
					<tr align="center">
						<td>
							由值班警察：<input id="frompolice" name="frompolice" value="${wkfrompolice}" type="text" style="width:120px" />
						</td>
						<td>
							交带工警察：<input id="topolice" name="topolice" value="${wktopolice}" type="text" style="width:120px" />
						</td>
					</tr>
					
				</table>
				
			</td>
		</tr>
		
		<tr height="90px">	
			<td class="trd" style="border-left:solid black 1.5pt;">留监<br/>罪犯<br/>管控<br/>情况</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
				<table >
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							留监舍罪犯<input id="stayhomenum" name="stayhomenum" value="${stayhomenum}" type="text" style="width:40px" onchange="validateNum(this);"/>名，
							留监舍原因分别为：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="stayhomereason" name="stayhomereason" rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${stayhomereason}</textarea>
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;管控情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="stayhomeinfo" name="stayhomeinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${stayhomeinfo}</textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">午餐<br/>环节</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
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
			<td class="trd" style="border-left:solid black 1.5pt;">收工<br/>环节</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
				<table >
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="kofworkhour" name="kofworkhour" value="${kofworkhour}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;时&nbsp;
							<input id="kofworkminute" name="kofworkminute" value="${kofworkminute}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;分，
							收工<input id="kofworknum" name="kofworknum" value="${kofworknum}" type="text" style="width:40px" onchange="validateNum(this);"/>名，
							清查罪犯人身<input id="kofcheckbodynum" name="kofcheckbodynum" value="${kofcheckbodynum}" type="text" style="width:40px" onchange="validateNum(this);"/>名，
							清查情况：
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="kofworkinfo" name="kofworkinfo" rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${kofworkinfo}</textarea>
						</td>
					</tr>
					
					<tr align="center">
						<td>
							由值班警察：<input id="frompolice" name="frompolice" value="${koffrompolice}" type="text" style="width:120px" />
						</td>
						<td>
							交带工警察：<input id="topolice" name="topolice" value="${koftopolice}" type="text" style="width:120px" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">晚餐<br/>环节</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
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
		
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">工余<br/>环节</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
				<table >
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="activehour" name="activehour" value="${activehour}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;时&nbsp;
							<input id="activeminute" name="activeminute" value="${activeminute}" type="text" style="width:30px" onchange="validateNum(this);" />&nbsp;分，
							查人<input id="activechecknum" name="activechecknum" value="${activechecknum}" type="text" style="width:40px" onchange="validateNum(this);"/>名。
							工余活动开展情况(如教育、文娱、就诊等)：
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="activeinfo" name="activeinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${activeinfo}</textarea>
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							“三课”教育<input id="educatenum" name="educatenum" value="${educatenum}" type="text" style="width:40px" onchange="validateNum(this);"/>人，
							现场管理巡查情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="educateinfo" name="educateinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${educateinfo}</textarea>
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							娱乐<input id="entertainmentnum" name="entertainmentnum" value="${entertainmentnum}" type="text" style="width:40px" onchange="validateNum(this);"/>人，
							现场管理巡查情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="entertainmentinfo" name="entertainmentinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${entertainmentinfo}</textarea>
						</td>
					</tr>
					
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							就诊<input id="treatnum" name="treatnum" value="${treatnum}" type="text" style="width:40px" onchange="validateNum(this);"/>人，
							现场管理巡查情况：
						</td>
					</tr>
					<tr>
						<td align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<textarea id="treatinfo" name="treatinfo"  rows="2" cols="55" style="overflow-x:hidden;overflow-y:hidden;">${treatinfo}</textarea>
						</td>
					</tr>
					
					
				</table>
			</td>
		</tr>
		
		
		<tr height="100px">
			<td class="trd" style="border-left:solid black 1.5pt;">就寝<br/>环节</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
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
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
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
		
		
		<tr height="90px">
			<td class="trd" style="border-left:solid black 1.5pt;">重点<br/>人员<br/>管控<br/>情况</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left" >
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
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
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
			<td class="trd" style="border-left:solid black 1.5pt;">交接<br/>提示</td>
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;" align="left">
			
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
			<td class="trd" colspan="18" style="border-right:solid black 1.5pt;border-bottom:solid black 1.5pt;" align="left">
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
    	
        $.ajax({
            url: "<%=path %>/saveDiaryData.json?1=1",
            data: {formdata:formdata},
            dataType:"text",
            type:"POST",
            success: function (text){
            	var data = mini.decode(text);
            	if('success' == data.status){
            		mini.get("flowdraftid").setValue(data.flowdraftid);
            		mini.get("saveflag").setValue(1);
            		alert("操作成功！");
            	}else{
            		alert("操作失败！");
            	}
            	//Close();
            }
        });
    	
    }
    
     //
    var operatetype = '${operatetype}';
     var flowdraftid = '${flowdraftid}';
     if(operatetype && 'new' == operatetype){
    	initial();
     }
 	
    function SetData(data){
    	//data = mini.clone(data);
    	//mini.get("svtype").setValue(data.svtype);
    }
      
</script>
</body>
</html>