<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList,com.sinog2c.model.officeAssistant.*,java.util.*,java.text.SimpleDateFormat" %>
<%//清除页面缓存
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
//%>
<%
	String path = request.getContextPath();
	String timeSelect = (String)request.getAttribute("timeSelect");
	String titleNew = (String)request.getAttribute("titleNew");
	List<TbuserNotice> scheduleList = (ArrayList<TbuserNotice>)request.getAttribute("scheduleList"); 
	String SYValue = (String)request.getAttribute("SYValue");
	String SMValue = (String)request.getAttribute("SMValue");
	int x = scheduleList.size();
	if(x==0){
		scheduleList = new ArrayList<TbuserNotice>();
	}
%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	
	<meta http-equiv="pragma" content="no-cache"> 
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="expires" content="0"> 
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>日程安排月视图</title>
		<style type="text/css">
<!--
body,td,th {
	font-family: 宋体;
	font-size: 12px;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.tableBorder7{width:800;solid; background-color: #8CB2E2;}
TD{font-family: 宋体;font-size: 12px;line-height : 15px ;}
th{background-color: #f7f7f7;color: #8CB2E2;font-size: 12px;font-weight:bold;}
th.th1{background-color: #333333;}
td.TableBody7{background-color: #B1EA45;}
-->
</style>
<link href="<%=path%>/css/CIC.css" rel="stylesheet" type="text/css">
		<!-- <SCRIPT language="JavaScript" src="<%=path%>/js/bgColor.js"></SCRIPT> -->
		<script src="<%=path%>/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		
		 var bXmlHttpSupport = (typeof XMLHttpRequest != "undefined" || window.ActiveXObject);
     
    if (typeof XMLHttpRequest == "undefined" && window.ActiveXObject) {
        function XMLHttpRequest() {
            var arrSignatures = ["MSXML2.XMLHTTP.5.0", "MSXML2.XMLHTTP.4.0",
                                 "MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP",
                                 "Microsoft.XMLHTTP"];
                             
            for (var i=0; i < arrSignatures.length; i++) {
                try {        
                    var oRequest = new ActiveXObject(arrSignatures[i]);            
                    return oRequest;        
                } catch (oError) { /*ignore*/ }
            }          
    
            throw new Error("MSXML is not installed on your system.");               
        }
    }    
		</script>
		<script language="javascript">
		function dosubmit(){
			document.CLD.submit();
		}
		</script>
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
	</script>
		<script language="vbscript">
			function ShowConfig(url)
				k=showModalDialog(url,"","dialogWidth:550px;status:no;dialogHeight:380px")
			end function
		</script>
		<SCRIPT language="JScript">
<!--
var conWeekend = 2; // 周末颜色显示: 1=黑色, 2=绿色, 3=红色, 4=隔周休
/*****************************************************************************
 日期资料 From 1900 To 2100
*****************************************************************************/
var lunarInfo=new Array(
0x4bd8,0x4ae0,0xa570,0x54d5,0xd260,0xd950,0x5554,0x56af,0x9ad0,0x55d2,
0x4ae0,0xa5b6,0xa4d0,0xd250,0xd295,0xb54f,0xd6a0,0xada2,0x95b0,0x4977,
0x497f,0xa4b0,0xb4b5,0x6a50,0x6d40,0xab54,0x2b6f,0x9570,0x52f2,0x4970,
0x6566,0xd4a0,0xea50,0x6a95,0x5adf,0x2b60,0x86e3,0x92ef,0xc8d7,0xc95f,
0xd4a0,0xd8a6,0xb55f,0x56a0,0xa5b4,0x25df,0x92d0,0xd2b2,0xa950,0xb557,
0x6ca0,0xb550,0x5355,0x4daf,0xa5b0,0x4573,0x52bf,0xa9a8,0xe950,0x6aa0,
0xaea6,0xab50,0x4b60,0xaae4,0xa570,0x5260,0xf263,0xd950,0x5b57,0x56a0,
0x96d0,0x4dd5,0x4ad0,0xa4d0,0xd4d4,0xd250,0xd558,0xb540,0xb6a0,0x95a6,
0x95bf,0x49b0,0xa974,0xa4b0,0xb27a,0x6a50,0x6d40,0xaf46,0xab60,0x9570,
0x4af5,0x4970,0x64b0,0x74a3,0xea50,0x6b58,0x5ac0,0xab60,0x96d5,0x92e0,
0xc960,0xd954,0xd4a0,0xda50,0x7552,0x56a0,0xabb7,0x25d0,0x92d0,0xcab5,
0xa950,0xb4a0,0xbaa4,0xad50,0x55d9,0x4ba0,0xa5b0,0x5176,0x52bf,0xa930,
0x7954,0x6aa0,0xad50,0x5b52,0x4b60,0xa6e6,0xa4e0,0xd260,0xea65,0xd530,
0x5aa0,0x76a3,0x96d0,0x4afb,0x4ad0,0xa4d0,0xd0b6,0xd25f,0xd520,0xdd45,
0xb5a0,0x56d0,0x55b2,0x49b0,0xa577,0xa4b0,0xaa50,0xb255,0x6d2f,0xada0,
0x4b63,0x937f,0x49f8,0x4970,0x64b0,0x68a6,0xea5f,0x6b20,0xa6c4,0xaaef,
0x92e0,0xd2e3,0xc960,0xd557,0xd4a0,0xda50,0x5d55,0x56a0,0xa6d0,0x55d4,
0x52d0,0xa9b8,0xa950,0xb4a0,0xb6a6,0xad50,0x55a0,0xaba4,0xa5b0,0x52b0,
0xb273,0x6930,0x7337,0x6aa0,0xad50,0x4b55,0x4b6f,0xa570,0x54e4,0xd260,
0xe968,0xd520,0xdaa0,0x6aa6,0x56df,0x4ae0,0xa9d4,0xa4d0,0xd150,0xf252,
0xd520);
var solarMonth=new Array(31,28,31,30,31,30,31,31,30,31,30,31);
var Gan=new Array("甲","乙","丙","丁","戊","己","庚","辛","壬","癸");
var Zhi=new Array("子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥");
var Animals=new Array("鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪");
var solarTerm = new Array("小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至");
var sTermInfo = new Array(0,21208,42467,63836,85337,107014,128867,150921,173149,195551,218072,240693,263343,285989,308563,331033,353350,375494,397447,419210,440795,462224,483532,504758);
var nStr1 = new Array('日','一','二','三','四','五','六','七','八','九','十');
var nStr2 = new Array('初','十','廿','卅','□');
var monthName = new Array("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
//
//数据库中检索出阳历纪念日
//公历
var sFtv = new Array('');
//某月的第几个星期几
var wFtv = new Array('');
//农历节日
var lFtv = new Array('');
var oRequest = new XMLHttpRequest();
function lYearDays(y) {
 var i, sum = 348;
 for(i=0x8000; i>0x8; i>>=1) sum += (lunarInfo[y-1900] & i)? 1: 0;
 return(sum+leapDays(y));
}
function leapDays(y) {
 if(leapMonth(y)) return( (lunarInfo[y-1899]&0xf)==0xf? 30: 29);
 else return(0);
}
function leapMonth(y) {
 var lm = lunarInfo[y-1900] & 0xf;
 return(lm==0xf?0:lm);
}
function monthDays(y,m) {
 return( (lunarInfo[y-1900] & (0x10000>>m))? 30: 29 );
}
function Lunar(objDate) {
 var i, leap=0, temp=0;
 var offset = (Date.UTC(objDate.getFullYear(),objDate.getMonth(),objDate.getDate()) - Date.UTC(1900,0,31))/86400000;
 //alert(Date.UTC(objDate.getFullYear()));
 //alert(objDate.getMonth());
 //alert(objDate.getDate());
 for(i=1900; i<2100 && offset>0; i++) { temp=lYearDays(i); offset-=temp; }
 if(offset<0) { offset+=temp; i--; }
 this.year = i;
 leap = leapMonth(i); //闰哪个月
 this.isLeap = false;
 for(i=1; i<13 && offset>0; i++) {
 //闰月
 if(leap>0 && i==(leap+1) && this.isLeap==false)
 { --i; this.isLeap = true; temp = leapDays(this.year); }
 else
 { temp = monthDays(this.year, i); }
 //解除闰月
 if(this.isLeap==true && i==(leap+1)) this.isLeap = false;
 offset -= temp;
 }
 if(offset==0 && leap>0 && i==leap+1)
 if(this.isLeap)
 { this.isLeap = false; }
 else
 { this.isLeap = true; --i; }
 if(offset<0){ offset += temp; --i; }
 this.month = i;
 this.day = offset + 1;
}
function solarDays(y,m) {
 if(m==1)
 return(((y%4 == 0) && (y%100 != 0) || (y%400 == 0))? 29: 28);
 else
 return(solarMonth[m]);
}
function cyclical(num) {
 return(Gan[num%10]+Zhi[num%12]);
}
function calElement(sYear,sMonth,sDay,week,lYear,lMonth,lDay,isLeap,cYear,cMonth,cDay) {
 this.isToday = false;
 this.sYear = sYear; 
 this.sMonth = sMonth; 
 this.sDay = sDay; 
 this.week = week; 
 this.lYear = lYear; 
 this.lMonth = lMonth; 
 this.lDay = lDay; 
 this.isLeap = isLeap; 
 //八字
 this.cYear = cYear; //年柱, 2个中文
 this.cMonth = cMonth; //月柱, 2个中文
 this.cDay = cDay; //日柱, 2个中文
 this.color = '';
 this.lunarFestival = ''; //农历节日
 this.solarFestival = ''; //公历节日
 this.solarTerms = ''; //节气
}
function sTerm(y,n) {
 var offDate = new Date( ( 31556925974.7*(y-1900) + sTermInfo[n]*60000 ) + Date.UTC(1900,0,6,2,5) );
 return(offDate.getUTCDate());
}
function calendar(y,m) {
 var sDObj, lDObj, lY, lM, lD=1, lL, lX=0, tmp1, tmp2, tmp3;
 var cY, cM, cD; //年柱,月柱,日柱
 var lDPOS = new Array(3);
 var n = 0;
 var firstLM = 0;
 sDObj = new Date(y,m,1,0,0,0,0); 
 this.length = solarDays(y,m); 
 this.firstWeek = sDObj.getDay(); 
 if(m<2) cY=cyclical(y-1900+36-1);
 else cY=cyclical(y-1900+36);
 var term2=sTerm(y,2); //立春日期
 var firstNode = sTerm(y,m*2) //返回当月「节」为几日开始
 cM = cyclical((y-1900)*12+m+12);
 var dayCyclical = Date.UTC(y,m,1,0,0,0,0)/86400000+25567+10;
 for(var i=0;i<this.length;i++) {
 if(lD>lX) {
 sDObj = new Date(y,m,i+1); 
 lDObj = new Lunar(sDObj); 
 lY = lDObj.year; 
 lM = lDObj.month; 
 lD = lDObj.day; 
 lL = lDObj.isLeap; 
 lX = lL? leapDays(lY): monthDays(lY,lM); 
 if(n==0) firstLM = lM;
 lDPOS[n++] = i-lD+1;
 }
 if(m==1 && (i+1)==term2) cY=cyclical(y-1900+36);
 if((i+1)==firstNode) cM = cyclical((y-1900)*12+m+13);
 cD = cyclical(dayCyclical+i);
 this[i] = new calElement(y, m+1, i+1, nStr1[(i+this.firstWeek)%7],
 lY, lM, lD++, lL,
 cY ,cM, cD );
 }
 tmp1=sTerm(y,m*2 )-1;
 tmp2=sTerm(y,m*2+1)-1;
 this[tmp1].solarTerms = solarTerm[m*2];
 this[tmp2].solarTerms = solarTerm[m*2+1];
 if(m==3) this[tmp1].color = 'red';
 for(i in sFtv)
 if(sFtv[i].match(/^(\d{2})(\d{2})([\s\*])(.+)$/))
 if(Number(RegExp.$1)==(m+1)) {
 this[Number(RegExp.$2)-1].solarFestival += RegExp.$4 + ' ';
 if(RegExp.$3=='*') this[Number(RegExp.$2)-1].color = 'red';
 }
 //月周节日
 for(i in wFtv)
 if(wFtv[i].match(/^(\d{2})(\d)(\d)([\s\*])(.+)$/))
 if(Number(RegExp.$1)==(m+1)) {
 tmp1=Number(RegExp.$2);
 tmp2=Number(RegExp.$3);
 if(tmp1<5)
 this[((this.firstWeek>tmp2)?7:0) + 7*(tmp1-1) + tmp2 - this.firstWeek].solarFestival += RegExp.$5 + ' ';
 else {
 tmp1 -= 5;
 tmp3 = (this.firstWeek+this.length-1)%7; 
 this[this.length - tmp3 - 7*tmp1 + tmp2 - (tmp2>tmp3?7:0) - 1 ].solarFestival += RegExp.$5 + ' ';
 }
 }
 //农历节日
 for(i in lFtv)
 if(lFtv[i].match(/^(\d{2})(.{2})([\s\*])(.+)$/)) {
 tmp1=Number(RegExp.$1)-firstLM;
 if(tmp1==-11) tmp1=1;
 if(tmp1 >=0 && tmp1<n) {
 tmp2 = lDPOS[tmp1] + Number(RegExp.$2) -1;
 if( tmp2 >= 0 && tmp2<this.length && this[tmp2].isLeap!=true) {
 this[tmp2].lunarFestival += RegExp.$4 + ' ';
 if(RegExp.$3=='*') this[tmp2].color = 'red';
 }
 }
 }
 //复活节只出现在3或4月
 if(m==2 || m==3) {
 var estDay = new easter(y);
 if(m == estDay.m)
 this[estDay.d-1].solarFestival = this[estDay.d-1].solarFestival+' 复活节 Easter Sunday';
 }
 if(m==2) this[20].solarFestival = this[20].solarFestival+unescape('%20%u6D35%u8CE2%u751F%u65E5');
 //黑色星期五
 if((this.firstWeek+12)%7==5)
 this[12].solarFestival += '黑色星期五';
 if(m==8) this[13].solarFestival = this[13].solarFestival+unescape('%u795D%u8D3A%u6885%u7AF9%u677E%u751F%u65E5%u5FEB%u4E50%u003A%u0029');
 //今日
}
//======================================= 返回该年的复活节(春分后第一次满月周后的第一主日)
function easter(y) {
 var term2=sTerm(y,5); //取得春分日期
 var dayTerm2 = new Date(Date.UTC(y,2,term2,0,0,0,0)); //取得春分的公历日期控件(春分一定出现在3月)
 var lDayTerm2 = new Lunar(dayTerm2); //取得取得春分农历
 if(lDayTerm2.day<15) //取得下个月圆的相差天数
 var lMlen= 15-lDayTerm2.day;
 else
 var lMlen= (lDayTerm2.isLeap? leapDays(y): monthDays(y,lDayTerm2.month)) - lDayTerm2.day + 15;
 //一天等于 1000*60*60*24 = 86400000 毫秒
 var l15 = new Date(dayTerm2.getTime() + 86400000*lMlen ); //求出第一次月圆为公历几日
 var dayEaster = new Date(l15.getTime() + 86400000*( 7-l15.getUTCDay() ) ); //求出下个周日
 this.m = dayEaster.getUTCMonth();
 this.d = dayEaster.getUTCDate();
}
//====================== 中文日期
function cDay(d){
 var s;
 switch (d) {
 case 10:
 s = '初十'; break;
 case 20:
 s = '二十'; break;
 break;
 case 30:
 s = '三十'; break;
 break;
 default :
 s = nStr2[Math.floor(d/10)];
 s += nStr1[d%10];
 }
 return(s);
}
///////////////////////////////////////////////////////////////////////////////
var cld;
function drawCld(SY,SM) {
 var i,sD,s,size;
 cld = new calendar(SY,SM);
 if(SY>1874 && SY<1909) yDisplay = '光绪' + (((SY-1874)==1)?'元':SY-1874);
 if(SY>1908 && SY<1912) yDisplay = '宣统' + (((SY-1908)==1)?'元':SY-1908);
 if(SY>1911 && SY<1950) yDisplay = '民国' + (((SY-1911)==1)?'元':SY-1911);
 if(SY>1948) yDisplay = '建国' + (((SY-1949)==1)?'元':SY-1949);
 GZ.innerHTML = "";
 for(i=0;i<42;i++) {
 sObj=eval('SD'+ i);
 lObj=eval('LD'+ i);
 sObj.className = '';
 sD = i - cld.firstWeek;
 if(sD>-1 && sD<cld.length) { //日期内
 sObj.innerHTML = sD+1;
 if(cld[sD].isToday) sObj.className = 'todyaColor'; //今日颜色
 sObj.style.color = cld[sD].color; //法定假日颜色
 if(cld[sD].lDay==1) //显示农历月
 lObj.innerHTML = '';
 else //显示农历日
 lObj.innerHTML = '';
 s=cld[sD].lunarFestival;
 if(s.length>0) { //农历节日
 if(s.length>6) s = '';
 s = s.fontcolor('red');
 }
 else { //公历节日
 s='';
 if(s.length>0) {
 size = (s.charCodeAt(0)>0 && s.charCodeAt(0)<128)?8:4;
 if(s.length>size+2) s = s.substr(0, size)+'...';
 s=(s=='黑色星期五')?s.fontcolor('black'):s.fontcolor('blue');
 }
 else { //廿四节气
 s='';
 if(s.length>0) s = s.fontcolor('limegreen');
 }
 }
 if(cld[sD].solarTerms=='清明') s = '';
 if(cld[sD].solarTerms=='芒种') s = '';
 if(cld[sD].solarTerms=='夏至') s = '';
 if(cld[sD].solarTerms=='冬至') s = '';
 if(s.length>0) lObj.innerHTML = s;
 }
 else { //非日期
 sObj.innerHTML = '';
 lObj.innerHTML = '';
 }
 }
}


function pushBtm(K) {
 switch (K){
 case 'YU' :
 if(CLD.SY.selectedIndex>0) CLD.SY.selectedIndex--;
 break;
 case 'YD' :
 if(CLD.SY.selectedIndex<200) CLD.SY.selectedIndex++;
 break;
 case 'MU' :
 if(CLD.SM.selectedIndex>0) {
 CLD.SM.selectedIndex--;
 }
 else {
 CLD.SM.selectedIndex=11;
 if(CLD.SY.selectedIndex>0) CLD.SY.selectedIndex--;
 }
 break;
 case 'MD' :
 if(CLD.SM.selectedIndex<11) {
 CLD.SM.selectedIndex++;
 }
 else {
 CLD.SM.selectedIndex=0;
 if(CLD.SY.selectedIndex<200) CLD.SY.selectedIndex++;
 }
 break;
 default :
 CLD.SY.selectedIndex=tY-1900;
 CLD.SM.selectedIndex=tM;
 }
 changeCld();
}
var Today = new Date();
var tY = Today.getFullYear();
var tM = Today.getMonth();
var tD = Today.getDate();
//////////////////////////////////////////////////////////////////////////////
var width = "130";
var offsetx = 2;
var offsety = 8;
var x = 0;
var y = 0;
var snow = 0;
var sw = 0;
var cnt = 0;
var dStyle;
document.onmousemove = mEvn;
//显示详细日期资料
function mOvr(v) {
 var s,festival;
 var sObj=eval('SD'+ v);
 var d=sObj.innerHTML-1;
 //sYear,sMonth,sDay,week,
 //lYear,lMonth,lDay,isLeap,
 //cYear,cMonth,cDay
 if(sObj.innerHTML!='') {
 sObj.style.cursor ='help';
 if(cld[d].solarTerms == '' && cld[d].solarFestival == '' && cld[d].lunarFestival == '')
 festival = '';
 else
 festival = '<TABLE WIDTH=100% BORDER=0 CELLPADDING=2 CELLSPACING=0 BGCOLOR="#CCFFCC"><TR><TD>'+
 '<FONT COLOR="#000000" STYLE="font-size:9pt;">'+cld[d].solarTerms + ' ' + cld[d].solarFestival + ' ' + cld[d].lunarFestival+'</FONT></TD>'+
 '</TR></TABLE>';
 s= '<TABLE class="ISSUETBL" bordercolor=black WIDTH="130" BORDER=0 CELLPADDING="2" CELLSPACING=0 BGCOLOR="#336699" style="filter:Alpha(opacity=90)"><TR><TD>' +
 '<TABLE WIDTH=100% BORDER=0 CELLPADDING=0 CELLSPACING=0><TR><TD ALIGN="right" nowrap><FONT COLOR="#ffffff" STYLE="font-size:9pt;">'+
 cld[d].sYear+' 年 '+cld[d].sMonth+' 月 '+cld[d].sDay+' 日 星期'+cld[d].week+'<br>'+
 '<font color="violet">农历'+(cld[d].isLeap?'闰 ':' ')+cld[d].lMonth+' 月 '+cld[d].lDay+' 日</font><br>'+
 '<font color="yellow">'+cld[d].cYear+'年 '+cld[d].cMonth+'月 '+cld[d].cDay + '日</font>'+
 '</FONT></TD></TR></TABLE>'+ festival +'</TD></TR></TABLE>';
 document.all["detail"].innerHTML = s;
 if (snow == 0) {
 dStyle.left = x+offsetx-(width/2);
 dStyle.top = y+offsety;
 dStyle.visibility = "visible";
 snow = 1;
 }
 }
}
//清除详细日期资料
function mOut() {
 if ( cnt >= 1 ) { sw = 0; }
 if ( sw == 0 ) { snow = 0; dStyle.visibility = "hidden";}
 else cnt++;
}
//取得位置
function mEvn() {
 x=event.x;
 y=event.y;
 if (document.body.scrollLeft)
 {x=event.x+document.body.scrollLeft; y=event.y+document.body.scrollTop;}
 if (snow){
 dStyle.left = x+offsetx-(width/2);
 dStyle.top = y+offsety;
 }
}
///////////////////////////////////////////////////////////////////////////
function initialize() {
 var key;
 //阴历
 dStyle = detail.style;
 var syValue = "<%=SYValue%>";
 var smValue = "<%=SMValue%>";
 
 if(syValue != null && syValue != ""){
 	tY = syValue;
 }
 if(smValue != null && smValue != ""){
  	var tmvalue = smValue-1;
 	tM = tmvalue;
 	// alert("mytm fyyyyyyyyyy=="+tM);
 }

 CLD.SY.selectedIndex=tY-1900;
 CLD.SM.selectedIndex=tM;
 drawCld(tY,tM);
}
function changeCld() {
 var y,m;
 y=CLD.SY.selectedIndex+1900;
 m=CLD.SM.selectedIndex;
 drawCld(y,m);
 var time = "";
 if(m==9 || m==10 || m==11){
 	time = y+"-"+(m+1);
 }else{
 	time = y+"-0"+(m+1);
 }
 document.location.href = 'toScheduleMonthView.action?time='+time;
}

//ajax查询每天的事件列表

function selectCalendarPlanList(v){
	var s,festival;
	var sObj=eval('SD'+ v);
	var d=sObj.innerHTML-1;
	if(sObj.innerHTML!='') {
		sObj.style.cursor ='';
		if(cld[d].solarTerms == '' && cld[d].solarFestival == '' && cld[d].lunarFestival == '')
		festival = '';
		else
		festival = '';
		var date=cld[d].sYear+'-'+cld[d].sMonth+'-'+cld[d].sDay;
		$.ajax({
	        url:"ajaxCheckScheduleDetail.action?date="+date,
	        type: "post",
	        success: function (text) {
	        	json = eval("(" + text + ")");
	        	var arr = json.data;
	      		s= '<table style="FILTER:alpha(opacity=90) shadow(color=#bbbbbb,direction=135);" id=toolTipTalbe border=0><tr><td width="100%"><table class=tableBorder7 cellspacing="1" cellpadding="0" style="width:100%">';
		        for(var i=0;i<arr.length;i++){
		          s=s+'<tr><td class=tablebody7 style="padding-left:14px;padding-right:14px;padding-top: 6px;padding-bottom:6px;line-height:135%">'+arr[i].content+'</td></tr>';
		        }
	            s=s+'</table></td></tr></table>';
			 	document.all["detail"].innerHTML = s;
			 	if (snow == 0) {
			 		dStyle.left = x+offsetx-(width/2);
			 		dStyle.top = y+offsety;
			 		dStyle.visibility = "visible";
					snow = 1;
				}
	        },
	        error: function (jqXHR, textStatus, errorThrown) {
	        }
	    });
	}
}
function insertEvent(s)
{	return;
   var year=document.getElementById("SY").value;
   var month=document.getElementById("SM").value;
   if(month.length==1)
   {
     month="0"+month;
   }
   //alert(s);
   if(s<=9)
   {
    s="0"+s;
   }
   var flag=year+'-'+month+'-'+s;
   var sUrl = 'calendarPlanList.action?time='+flag;
   //var oRequest = new XMLHttpRequest();
  // oRequest.open('POST', sUrl);
   //oRequest.send(null);
 window.location.href=sUrl;
}
//--></SCRIPT>
		<STYLE>
.todyaColor {
	BACKGROUND-COLOR: #3399ff
}
</STYLE>
</head>

<body onload="initialize();" aLink="#003399" leftMargin="1" topMargin="0" text="#000000" MARGINHEIGHT="0" MARGINWIDTH="0">
<form name="CLD" id="CLD" action="addPlan" theme="simple">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          	<td>
		          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<SCRIPT language="JavaScript">
							<!--
							 if(navigator.appName == "Netscape" || parseInt(navigator.appVersion) < 4)
							 document.write("<h1>你的浏览器无法执行此程序。</h1>此程序需在 IE4 以后的版本才能执行!!")
							//-->
							function dosubmit(v){
								window.location=v+"?flagsubmit=calendarPlan.action";
							}
							</SCRIPT>
							<tr>
								<TD >
									<table border="0" width="100%" cellspacing="0" cellpadding="0" class="TableHeader">
										<tr  class="TableHeaderText" style="background-color:F1F3F6">
											<td align="right">
												<table border="0" width="100%"  height="30" cellspacing="0" cellpadding="0">
													<tr align="right">
														<td>
															<FONT style="FONT-SIZE: 9pt" size="2">
															
															<SELECT style="FONT-SIZE: 9pt; WIDTH: 60px" onchange="changeCld()" name="SY" id="SY">
															<%for(int i=1900;i<=2100;i++) { %>
															<option value="<%=i %>"><%=i %></option>
															<%} %>
															</SELECT> 年 
															<SELECT style="FONT-SIZE: 9pt; WIDTH: 40px" onchange="changeCld()" name="SM" id="SM">
															<%for(int i=1;i<=12;i++) { %>
															<option value="<%=i %>"><%=i %></option>
															<%} %>
															</SELECT>月
															</FONT>
															<FONT id="GZ" face="标楷体" size="4"></FONT>&nbsp;&nbsp;&nbsp;
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									<DIV id="detail" style="Z-INDEX: 3; FILTER: shadow(color = #333333, direction = 135); WIDTH: 100%; POSITION: absolute; HEIGHT: 100%"></DIV>
							
									<TABLE border="0" bgcolor="F3FAFF" width="100%" height="80%" cellSpacing="0">
										<TR>
											<TD>
												<TABLE bgColor="#F2F5F7" border="1" borderColorDark="#ffffff" width="100%" height="100%" borderColorLight="#8CB2E2" cellSpacing="0">
													<TR align="center" bgColor="#D8E4F2" style="FONT-SIZE: 9pt">
														<TD width="14%" >
															<FONT color="red">日</FONT>
														</TD>
														<TD width="14%">
															<FONT color="#408080">一</FONT>
														</TD>
														<TD width="14%">
															<FONT color="#408080">二</FONT>
														</TD>
														<TD width="14%">
															<FONT color="#408080">三</FONT>
														</TD>
														<TD width="14%">
															<FONT color="#408080">四</FONT>
														</TD>
														<TD width="14%">
															<FONT color="#408080">五</FONT>
														</TD>
														<TD width="14%">
															<FONT color="red">六</FONT>
														</TD>
													</TR>
													<SCRIPT language="JavaScript">
													<!--
													
													 var gNum, color1, color2;
													 // 星期六颜色
													 switch (conWeekend) {
														 case 1:
															 color1 = 'black';
															 color2 = color1;
															 break;
														 case 2:
															 color1 = 'green';
															 color2 = color1;
															 break;
														 case 3:
															 color1 = 'green';
															 color2 = color1;
															 break;
														 default :
															 color1 = 'green';
															 color2 = 'green';
													 }
													 for(i=0;i<6;i++) {
													 	document.write('<tr>');
													 	for(j=0;j<7;j++) {
															 gNum = i*7+j;
															 document.write('<td valign=top height="80" id="1GD' + gNum +'" onclick="insertEvent('+gNum+')" onMouseOver="selectCalendarPlanList(' + gNum +')" onMouseOut="mOut()"><font id="SD' + gNum +'" size=2 face="宋体"');
														
														 	if(j == 0) document.write(' color=red');
														 	if(j == 6) {
														 		if(i%2==1) document.write(' color='+color2);
														 		else document.write(' color='+color1);
													 		 }
													 	 	var x = 1;
															 document.write(' TITLE=""></font>&nbsp;&nbsp;<font id="LD' + gNum + '" size=2  style="font-size:9pt"></font><br>');
													 		 document.write('<%
													 	 					 //得到当前年月日
													 	 					SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" ); 
																			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD");
																			//String time = df.format(new Date());
																			String time = (String)request.getAttribute("time");

																			//String datetime =  tempDate.format(new Date());
																			String datetime =  time;
																			String year = datetime.substring(0,4);
																			String month = datetime.substring(5,7);
																			String day = datetime.substring(8,10);
																			
																			Calendar   cal   =   Calendar.getInstance();   
																			cal.set(Calendar.YEAR,Integer.parseInt(year));   
																			cal.set(Calendar.MONTH,Integer.parseInt(month)-1);//7月   
																			cal.set(Calendar.DATE,1); 
																			int   maxDate   =   cal.getActualMaximum(Calendar.DATE);//获取天
																			Calendar   calFirst   =   Calendar.getInstance();  
																			calFirst.set(Calendar.DATE,1);  
																			  int   dayFirst   =   cal.get(Calendar.DAY_OF_WEEK);//获取星期
																			int monthMax = maxDate+dayFirst-1;
																			int firstday = 0;
																			if(dayFirst<1){
																			firstday = dayFirst-1;
																			}else{
																			firstday = dayFirst-2;
																			}
													 	  					 if(x!=0){
																			  for(int i=0;i<scheduleList.size();i++){
																			  TbuserNotice tbuserNotice = scheduleList.get(i);
																			  
																			  //得到查询出来的数据
																			  String start = new SimpleDateFormat("yyyy-MM-dd").format(tbuserNotice.getStarttime());
																			  String end = new SimpleDateFormat("yyyy-MM-dd").format(tbuserNotice.getEndtime());
								  											  String title = tbuserNotice.getTitle();
																			  //将开始时间分成年月日
																			  String startDate = start.substring(8,10);
																			  String startMM =  start.substring(5,7);
																			  String startYear = start.substring(0,4);
																			  //将结束时间分成年月日
																			  String endDate = end.substring(8,10);
																			  String endMM =  end.substring(5,7);
																			  String endYear = end.substring(0,4);
																			  
																			  
																			  int startInt = Integer.parseInt(startYear+startMM+startDate);
																			  int endInt = Integer.parseInt(endYear+endMM+endDate);
																			  
																			  %>');
													     var number = gNum-(<%=firstday%>); 
													     if(number<10){
													        var num = "0"+(number);
													     }else{
													     	var num = number;
													     }
													     if( (<%=monthMax%>)>gNum && (<%=firstday%>)<=gNum && gNum-(<%=firstday%>)>0)
														 {
													     var titleText = "<%=title%>";
														 var titleName = "";
														 if(titleText.length>8){
															 titleName = titleText.substring(0,8)+"...";
														 }else{
															 titleName = titleText;
														 }
														 var timeMonth = <%=time.substring(5,7)%>;
														 if(timeMonth<10){
														 	timeMonth = "0"+timeMonth;
														 }else{
														 	timeMonth = timeMonth;
														 }
														 var Sysm = <%=time.substring(0,4)%>+""+timeMonth+""+num;
														 var systime = <%=time.substring(0,4)%>+""+timeMonth+"01";
														 if(Sysm >= <%=startInt%> && Sysm <= <%=endInt%>  ){
														 if(x<=2){
															 document.write(titleName);
															 document.write('<br>');
															}
															if( x == 3){
															 document.write('......');
															  x++;
															}
														 }
														 }
														 document.write('<% } }   %>');
														 document.write('</td>');
													 }
													 document.write('</tr>');
													 }
													 //-->
													 </SCRIPT>
												</TABLE>
											</TD>
										</TR>
									</TABLE>
								</td>
							</tr>
					    </table>
					</td>
				</tr>
	            <tr>
	              <td>&nbsp;</td>
	            </tr>
		    </table>
		</td>
   </tr>
</table>
</form>

</body>
</html>