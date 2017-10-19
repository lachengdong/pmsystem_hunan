<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page import="java.util.*,java.text.SimpleDateFormat,com.sinog2c.model.officeAssistant.TbuserNotice" %>
<%
	String path = request.getContextPath();
%>
<%
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" );
	String sysDate = dateFormat.format(new Date());
	Map<String, String> map = (Map<String, String>)request.getAttribute("dayOfWeek");
	String monday = map.get("monday");
	String tuesday = map.get("tuesday");
	String wednesday = map.get("wednesday");
	String thursday = map.get("thursday");
	String friday = map.get("friday");
	String saturday = map.get("saturday");
	String sunday = map.get("sunday");
	List<TbuserNotice> scheduleList = (List<TbuserNotice>)request.getAttribute("scheduleList");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
<title>日程安排周视图</title>
</head>
<script type="text/javascript">
	function onButton(td)
	{
		td.style.backgroundImage='url(<%=path %>/Images/images/button_b.gif)';
		td.style.color='#000000';
	}
	function offButton(td)
	{
		td.style.backgroundImage='';
		td.style.color='';
	}
	function DisableButton() {
		window.setTimeout("disableButton('" + window.event.srcElement.id + "')", 0);
		document.forms[0].submit();
	}
	function disableButton(buttonID) {
		document.getElementById(buttonID).value='loading...'; 
		document.getElementById(buttonID).disabled=true;
	}
	function add(date) {
		var newdate = mini.formatDate ( new Date(),"yyyy-MM-dd");
		if(date<newdate){
			alert("所选时间已过期");
			return;
		}
		//如果所选时间是今天，加上小时、分钟
		if(date==newdate)date = date+mini.formatDate(new Date()," HH:mm");
    	mini.open({
	        url: "toScheduleAddPage.action?date="+date,
	        showMaxButton: true,
	        allowResize: false,
	        title: "新增", width: 600, height: 360,
	        async:false,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = { action : "new"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	window.location.reload();
	        }
	    });
	}
</script>
<style type="text/css">
	body,table,tr,td,p{
		margin:0;
		padding:0;
	}
	.zstb tr td{
		border-bottom:solid 1px #8CB2E2;
		border-right:solid 1px #8CB2E2;
		width:50%;
	}
</style>
<body onload="initialize();">
<div class="mini-toolbar" style="padding:0px 0px 0px 0px;width:100%;"> 
   <table style="width:100%;">
       <tr>
       	<td style="white-space:nowrap;">
       		&nbsp;
		</td>
       </tr>
   </table>
</div>
<TABLE class="zstb" style="border:none;border-left:solid 1px #8CB2E2;" bgColor="#F2F5F7" borderColorDark="#ffffff" width="100%" height="90%" borderColorLight="#8CB2E2"  cellpadding="0" cellspacing="0">
	<tr align="center" bgColor="#D8E4F2" style="font-size:9pt;height:25px;">
		<td><%if(sysDate.equals(monday)){%><font color="red"><%=monday %> 星期一</font><%}else{ %><%=monday %> 星期一<%} %></td>
		<td><%if(sysDate.equals(tuesday)){%><font color="red"><%=tuesday %> 星期二</font><%}else{ %><%=tuesday %> 星期二<%} %></td>
	</tr>
	<tr valign="top" style="height:120px;">
		<td align="left" onclick="add('<%=monday %>');" >
			<%
				List<TbuserNotice> scheduleMondayList = new ArrayList<TbuserNotice>();
				if(scheduleList!=null&&scheduleList.size()>0){
					for(int i=0;i<scheduleList.size();i++){
						Date date = dateFormat.parse(monday);
						String start = dateFormat.format(scheduleList.get(i).getStarttime());
						Date starttime = dateFormat.parse(start);
						Date endtime = scheduleList.get(i).getEndtime();
						if(date.getTime()>=starttime.getTime()&&date.getTime()<=endtime.getTime()){
							scheduleMondayList.add(scheduleList.get(i));
						}
					}
				}
				for(int i=0;i<(scheduleMondayList.size()<5?scheduleMondayList.size():5);i++){
					out.println("<p style='font-size:12px;height:23px;line-height:23px;padding-left:5px;'>"+(scheduleMondayList.get(i).getTitle().length()>30?scheduleMondayList.get(i).getTitle().substring(0,30):scheduleMondayList.get(i).getTitle())+"</p>");
				}
			%>
			&nbsp;
		</td>
		<td align="left"  onclick="add('<%=tuesday %>');" >
			<%
				List<TbuserNotice> scheduleTuesdayList = new ArrayList<TbuserNotice>();
				if(scheduleList!=null&&scheduleList.size()>0){
					for(int i=0;i<scheduleList.size();i++){
						Date date = dateFormat.parse(tuesday);
						String start = dateFormat.format(scheduleList.get(i).getStarttime());
						Date starttime = dateFormat.parse(start);
						Date endtime = scheduleList.get(i).getEndtime();
						if(date.getTime()>=starttime.getTime()&&date.getTime()<=endtime.getTime()){
							scheduleTuesdayList.add(scheduleList.get(i));
						}
					}
				}
				for(int i=0;i<(scheduleTuesdayList.size()<5?scheduleTuesdayList.size():5);i++){
					out.println("<p style='font-size:12px;height:23px;line-height:23px;padding-left:5px;'>"+(scheduleTuesdayList.get(i).getTitle().length()>30?scheduleTuesdayList.get(i).getTitle().substring(0,30):scheduleTuesdayList.get(i).getTitle())+"</p>");
				}
			%>
			&nbsp;
		</td>
	</tr>
	<tr align="center" bgColor="#D8E4F2" style="font-size:9pt;height:25px;">
		<td><%if(sysDate.equals(wednesday)){%><font color="red"><%=wednesday %> 星期三</font><%}else{ %><%=wednesday %> 星期三<%} %></td>
		<td><%if(sysDate.equals(thursday)){%><font color="red"><%=thursday %> 星期四</font><%}else{ %><%=thursday %> 星期四<%} %></td>
	</tr>
	<tr valign="top" style="height:120px;">
		<td align="left"  onclick="add('<%=wednesday %>');" >
			<%
				List<TbuserNotice> scheduleWednesdayList = new ArrayList<TbuserNotice>();
				if(scheduleList!=null&&scheduleList.size()>0){
					for(int i=0;i<scheduleList.size();i++){
						Date date = dateFormat.parse(wednesday);
						String start = dateFormat.format(scheduleList.get(i).getStarttime());
						Date starttime = dateFormat.parse(start);
						Date endtime = scheduleList.get(i).getEndtime();
						if(date.getTime()>=starttime.getTime()&&date.getTime()<=endtime.getTime()){
							scheduleWednesdayList.add(scheduleList.get(i));
						}
					}
				}
				for(int i=0;i<(scheduleWednesdayList.size()<5?scheduleWednesdayList.size():5);i++){
					out.println("<p style='font-size:12px;height:23px;line-height:23px;padding-left:5px;'>"+(scheduleWednesdayList.get(i).getTitle().length()>30?scheduleWednesdayList.get(i).getTitle().substring(0,30):scheduleWednesdayList.get(i).getTitle())+"</p>");
				}
			%>
			&nbsp;
		</td>
		<td align="left"  onclick="add('<%=thursday %>');" >
			<%
				List<TbuserNotice> scheduleThursdayList = new ArrayList<TbuserNotice>();
				if(scheduleList!=null&&scheduleList.size()>0){
					for(int i=0;i<scheduleList.size();i++){
						Date date = dateFormat.parse(thursday);
						String start = dateFormat.format(scheduleList.get(i).getStarttime());
						Date starttime = dateFormat.parse(start);
						Date endtime = scheduleList.get(i).getEndtime();
						if(date.getTime()>=starttime.getTime()&&date.getTime()<=endtime.getTime()){
							scheduleThursdayList.add(scheduleList.get(i));
						}
					}
				}
				for(int i=0;i<(scheduleThursdayList.size()<5?scheduleThursdayList.size():5);i++){
					out.println("<p style='font-size:12px;height:23px;line-height:23px;padding-left:5px;'>"+(scheduleThursdayList.get(i).getTitle().length()>30?scheduleThursdayList.get(i).getTitle().substring(0,30):scheduleThursdayList.get(i).getTitle())+"</p>");
				}
			%>
			&nbsp;
		</td>
	</tr>
	<tr align="center" bgColor="#D8E4F2" style="font-size:9pt;height:25px;">
		<td><%if(sysDate.equals(friday)){%><font color="red"><%=friday %> 星期五</font><%}else{ %><%=friday %> 星期五<%} %></td>
		<td><%if(sysDate.equals(saturday)){%><font color="red"><%=saturday %> 星期六</font><%}else{ %><%=saturday %> 星期六<%} %></td>
	</tr>
	<tr valign="top" style="">
		<td rowspan="3" align="left" onclick="add('<%=friday %>');" style="height:120px;">
			<%
				List<TbuserNotice> scheduleFridayList = new ArrayList<TbuserNotice>();
				if(scheduleList!=null&&scheduleList.size()>0){
					for(int i=0;i<scheduleList.size();i++){
						Date date = dateFormat.parse(friday);
						String start = dateFormat.format(scheduleList.get(i).getStarttime());
						Date starttime = dateFormat.parse(start);
						Date endtime = scheduleList.get(i).getEndtime();
						if(date.getTime()>=starttime.getTime()&&date.getTime()<=endtime.getTime()){
							scheduleFridayList.add(scheduleList.get(i));
						}
					}
				}
				for(int i=0;i<(scheduleFridayList.size()<5?scheduleFridayList.size():5);i++){
					out.println("<p style='font-size:12px;height:23px;line-height:23px;padding-left:5px;'>"+(scheduleFridayList.get(i).getTitle().length()>30?scheduleFridayList.get(i).getTitle().substring(0,30):scheduleFridayList.get(i).getTitle())+"</p>");
				}
			%>
			&nbsp;
		</td>
		<td align="left"  style="height:60px;" onclick="add('<%=saturday %>');" >
			<%
				List<TbuserNotice> scheduleSaturdayList = new ArrayList<TbuserNotice>();
				if(scheduleList!=null&&scheduleList.size()>0){
					for(int i=0;i<scheduleList.size();i++){
						Date date = dateFormat.parse(saturday);
						String start = dateFormat.format(scheduleList.get(i).getStarttime());
						Date starttime = dateFormat.parse(start);
						Date endtime = scheduleList.get(i).getEndtime();
						if(date.getTime()>=starttime.getTime()&&date.getTime()<=endtime.getTime()){
							scheduleSaturdayList.add(scheduleList.get(i));
						}
					}
				}
				for(int i=0;i<(scheduleSaturdayList.size()<2?scheduleSaturdayList.size():2);i++){
					out.println("<p style='font-size:12px;height:23px;line-height:23px;padding-left:5px;'>"+(scheduleSaturdayList.get(i).getTitle().length()>30?scheduleSaturdayList.get(i).getTitle().substring(0,30):scheduleSaturdayList.get(i).getTitle())+"</p>");
				}
			%>
			&nbsp;
		</td>
	</tr>
	<tr align="center" bgColor="#D8E4F2" style="FONT-SIZE:9pt;height:25px;">
		<td><%if(sysDate.equals(sunday)){%><font color="red"><%=sunday %> 星期日</font><%}else{ %><%=sunday %> 星期日<%} %></td>
	</tr>
	<tr valign="top" style="height:60px;" onclick="add('<%=sunday %>');">
		<td align="left">
			<%
				List<TbuserNotice> scheduleSundayList = new ArrayList<TbuserNotice>();
				if(scheduleList!=null&&scheduleList.size()>0){
					for(int i=0;i<scheduleList.size();i++){
						Date date = dateFormat.parse(sunday);
						String start = dateFormat.format(scheduleList.get(i).getStarttime());
						Date starttime = dateFormat.parse(start);
						Date endtime = scheduleList.get(i).getEndtime();
						if(date.getTime()>=starttime.getTime()&&date.getTime()<=endtime.getTime()){
							scheduleSundayList.add(scheduleList.get(i));
						}
					}
				}
				for(int i=0;i<(scheduleSundayList.size()<2?scheduleSundayList.size():2);i++){
					out.println("<p style='font-size:12px;height:23px;line-height:23px;padding-left:5px;'>"+(scheduleSundayList.get(i).getTitle().length()>30?scheduleSundayList.get(i).getTitle().substring(0,30):scheduleSundayList.get(i).getTitle())+"</p>");
				}
			%>
			&nbsp;
		</td>
	</tr>
</TABLE>
</body>
</html>