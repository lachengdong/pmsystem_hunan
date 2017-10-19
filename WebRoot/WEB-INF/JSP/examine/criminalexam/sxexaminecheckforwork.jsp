<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	
	Map bean = (Map) request.getAttribute("map");
    String departid = (String)request.getAttribute("departid");
    List list = (List)bean.get("list");
%>

<html  xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css"/>
		
		<title>考核、考勤月报表</title>

		<script type="text/javascript">
		mini.parse();
		
		function onButton(td) {
			td.style.backgroundImage = 'url(<%=path%>/Images/images/button_b.gif)';
			td.style.color = '#000000';
		}
		
		function offButton(td) {
			td.style.backgroundImage = '';
			td.style.color = '';
		}
		
		function DisableButton() {
			window.setTimeout("disableButton('" + window.event.srcElement.id + "')", 0);
			document.forms[0].submit();
		}
		function disableButton(buttonID) {
			document.getElementById(buttonID).value = 'loading...';
			document.getElementById(buttonID).disabled = true;
		}
		
		function dosubmit(url, obj) {
			document.form1.action = url;
			document.form1.submit();
		}
		
		function dosubmit1(url) {
			var sdid = document.getElementById("depAllList").value;
			document.form1.action = url + "?sdid=" + sdid;
			document.form1.submit();
		}
		function checkRemark(remark) {
			if (remark.length > 100) {
				return 0;
			} else {
				return 1;
			}
		}
		function saveall() {
			shezhishijifen();
			//var listsize = document.getElementById('listsize').value;
			//var sdid=document.getElementById('department').value;
			var ecwdate=mini.formatDate(mini.get('ecwdate').value,'yyyy-MM-dd');
			var count = parseInt(0);
			var datas = [];
			var criminalArr = document.getElementsByName("criminalArr");
			for ( var i = 0; i < criminalArr.length; i++) {
						var str="";
						if (criminalArr[i].checked == true) {
							//basiclevel ,ecwzhpgscore, worklevel,ecwldgzscore, ecwxzjfscore ,ecwxzkfscore, ccscore,ecwewscore, ecwkhscore, beizhu
								var criminalid = criminalArr[i].value;
								//基础分等级
								var basiclevel=document.getElementById("basiclevel"+criminalid).value;
								//基础分
								var  ecwzhpgscore=document.getElementById("ecwzhpgscore"+criminalid).value;
								//劳动等级
								var worklevel=document.getElementById("worklevel"+criminalid).value;
								//劳动改造分
								var ecwldgzscore=document.getElementById("ecwldgzscore"+criminalid).value;
								///奖分
								var ecwxzjfscore=document.getElementById("ecwxzjfscore"+criminalid).value;
								//扣分
								var ecwxzkfscore=document.getElementById("ecwxzkfscore"+criminalid).value;
								//超产奖分
								var ccscore=document.getElementById("ccscore"+criminalid).value;
								//综合奖分
								var ecwewscore=document.getElementById("ecwewscore"+criminalid).value;
								//考核实的成绩
								var ecwkhscore=document.getElementById("ecwkhscore"+criminalid).value;
								//备注
								var beizhu=document.getElementById("beizhu"+criminalid).value;
								//出工天数
								var workdaynum = document.getElementById("workdaynum"+criminalid).value;
								//alert(workdaynum);
								str="crimid:"+criminalid+"@"+"jcfdjscore:"+basiclevel+"@"+"zhjfscore:"+ecwzhpgscore+"@"+"lddjscore:"+worklevel+"@"+"ldgzscore:"+ecwldgzscore+"@"+"xzjfscore:"+ecwxzjfscore+"@"+"xzkfscore:"+ecwxzkfscore+"@"+"ccjfscore:"+ccscore+"@"+"int1:"+ecwewscore+"@"+"khscore:"+ecwkhscore+"@"+"remark:"+beizhu+"@"+"workdaynum:"+workdaynum;
								datas.push(str);
						}
					}
             var data = datas.join(',');
             if(data!=null&&data!=""){
	            $.ajax({
	                url: "ajaxSaveDataAll.action?1=1",
	                data: {data:data, ecwdate:ecwdate},
	                cache: false,
	                type: "post",
	                success: function (text) {
	                	alert("保存完毕！");
	                }
           		 });   
             }else{
            	 alert("请选择一条数据"); 
             }
		}
</script>

<script type="text/javascript">
		//自动计算 只计算所有总分的和 以及劳动总分的和，优  良  一等 二等 三等 四等人数
		/**
		 *checkbox事件
		 */
		function SelectAllCheckboxes(spanChk) {
			var xState = spanChk.checked;
			var theBox = spanChk;
			elm = theBox.form.elements;
			for (i = 0; i < elm.length; i++) {
				if (elm[i].type == "checkbox" && elm[i].id != theBox.id) {
					if (elm[i].checked != xState)
						elm[i].click();
				}
			}
		}
		function shezhifenzhi() {
			var criminalArr = document.getElementsByName("criminalArr");
			var jichufen = document.getElementById("jichufen").value;
			var jichufenlevel = document.getElementById("jichufenlevel").value;
			var laodonglevel = document.getElementById("laodonglevel").value;
			var laodonglevelfenzhi = document.getElementById('laodonglevelfenzhi').value;
			var chaochanjiangfen = document.getElementById("chaochanjiangfen").value;
			var zonghejiangfen = document.getElementById("zonghejiangfen").value;
			if (jichufenlevel != '') {
				if (isNaN(jichufenlevel)) {
					alert('基础分等级应输入数字！');
					return;
				} else {
					for ( var i = 0; i < criminalArr.length; i++) {
						if (criminalArr[i].checked == true) {
							var criminalid = criminalArr[i].value;
							var basiclevel = "";
							basiclevel = jichufenlevel;
							document.getElementById('basiclevel' + criminalid).value = basiclevel;
						}
					}
				}
			}
			if (jichufen != '') {
				if (isNaN(jichufen)) {
					alert('基础分应输入数字！');
					return;
				} else {
					for ( var i = 0; i < criminalArr.length; i++) {
						if (criminalArr[i].checked == true) {
							var criminalid = criminalArr[i].value;
							var ecwjcscore = "";
							ecwjcscore = jichufen;
							document.getElementById('ecwzhpgscore' + criminalid).value = ecwjcscore;
						}
					}
				}
			}
			if (laodonglevel != '') {
				if (isNaN(laodonglevel)) {
					alert('劳动等级应输入数字！');
					return;
				} else {
					for ( var i = 0; i < criminalArr.length; i++) {
						if (criminalArr[i].checked == true) {
							var criminalid = criminalArr[i].value;
							var worklevel = "";
							worklevel = laodonglevel;
							document.getElementById('worklevel' + criminalid).value = worklevel;
						}
					}
				}
			}
			if (laodonglevelfenzhi != '') {
				if (isNaN(laodonglevelfenzhi)) {
					alert('劳动等级分值应输入数字！');
					return;
				} else {
					for ( var i = 0; i < criminalArr.length; i++) {
						if (criminalArr[i].checked == true) {
							var criminalid = criminalArr[i].value;
							var ecwldgzscore = "";
							ecwldgzscore = laodonglevelfenzhi;
							var workdaynum = document
									.getElementById('workdaynum' + criminalid).value;
							document.getElementById('ecwldgzscore' + criminalid).value = ecwldgzscore
									* workdaynum;
						}
					}
				}
			}
			if (chaochanjiangfen != '') {
				if (isNaN(chaochanjiangfen)) {
					alert("超产奖分应输入数字！");
					return;
				} else {
					for ( var i = 0; i < criminalArr.length; i++) {
						if (criminalArr[i].checked == true) {
							var criminalid = criminalArr[i].value;
							var ccscore = "";
							ccscore = chaochanjiangfen;
							document.getElementById('ccscore' + criminalid).value = ccscore;
						}
					}
				}
			}
			if (zonghejiangfen != '') {
				if (isNaN(zonghejiangfen)) {
					alert("综合奖分应输入数字！");
					return;
				} else {
					for ( var i = 0; i < criminalArr.length; i++) {
						if (criminalArr[i].checked == true) {
							var criminalid = criminalArr[i].value;
							var ecwzhjscore = "";
							ecwzhjscore = zonghejiangfen;
							document.getElementById('ecwewscore' + criminalid).value = ecwzhjscore;
						}
					}
				}
			}
		}
		function returnFloat1(value) { //保留一位小数点
			value = Math.round(parseFloat(value) * 10) / 10;
			if (value.toString().indexOf(".") < 0)
				value = value.toString() + ".0";
			return value;
		}
		function shezhishijifen() {
			var totalscore = document.getElementById('totalscore').value;
			//基础分优、良、中、差
			var younum = 0;
			var liangnum = 0;
			var zhongnum = 0;
			var chanum = 0;
			//岗位等级一、二、三、四、五
			var yinum = 0;
			var ernum = 0;
			var sannum = 0;
			var sinum = 0;
			var wunum = 0;
			var laodongtotal = 0;
			var ecwewscoresum = 0;
			var hiddendaynum = document.getElementById('hiddendaynum').value;
			var daynum = parseFloat(hiddendaynum);
			<%for (int i = 0; i < list.size(); i++) {
					Map ws = (Map)list.get(i);%>
              	 	var total=parseFloat("0");
		 			//基础分
		 			var ecwzhpgscore=document.getElementById('ecwzhpgscore'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value;
		 			if(ecwzhpgscore!=''){
		 				total=total+parseFloat(ecwzhpgscore);
		 			}
		 			var basiclevel = 0;
		 			basiclevel = document.getElementById('basiclevel'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value;
		 			//统计基础分等级优、良、中、差人数
		 			if(isNaN(basiclevel)){
		 			}else{
		 				if(basiclevel==1) younum++;
		 				if(basiclevel==2) liangnum++;
		 				if(basiclevel==3) zhongnum++;
		 				if(basiclevel==4) chanum++;
		 			}
		 			//统计岗位等级一、二、三、四人数
		 			var worklevel = 0;
		 			worklevel = document.getElementById('worklevel'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value;
		 			if(isNaN(worklevel)){
		 			}else{
		 				if(worklevel==1) yinum++;
		 				if(worklevel==2) ernum++;
		 				if(worklevel==3) sannum++;
		 				if(worklevel==4) sinum++;
		 				if(worklevel==5) wunum++;
		 			}
		 			//劳动得分
		 			var ecwldgzscore=document.getElementById('ecwldgzscore'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value;
		 			if(ecwldgzscore!=''){
		 				total=total+parseFloat(ecwldgzscore);
		 				var ldgzscore=parseFloat(ecwldgzscore);
		 				laodongtotal=parseFloat(laodongtotal)+ldgzscore;
		 			}
		 			//行政奖分
		 			var ecwxzjfscore=document.getElementById('ecwxzjfscore'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value;
		 			if(ecwxzjfscore!=''){
		 				total=total+parseFloat(ecwxzjfscore);
		 			}
		 			//行政扣分
		 			var ecwxzkfscore=document.getElementById('ecwxzkfscore'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value;
		 			if(ecwxzkfscore!=''){
		 				total=total-parseFloat(ecwxzkfscore);
		 			}
		 			//超产奖分
		 			var ccscore=document.getElementById('ccscore'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value;
		 			if(ccscore!=''){
		 				total=total+parseFloat(ccscore);
		 			}
		 			//综合奖分
		 			var ecwewscore=document.getElementById('ecwewscore'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value;
		 			if(ecwewscore!=''){
		 				total=total+parseFloat(ecwewscore);
		 				ecwewscoresum = ecwewscoresum + parseFloat(ecwewscore);
		 			}
		 			var total2=total;
		 			total=returnFloat1(total);
		 			document.getElementById('ecwkhscore'+<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>).value=total;
              	<%}%>
			    	document.getElementById("totalscore").value=returnFloat1(laodongtotal);
					document.getElementById("yousum").value=younum;
					document.getElementById("liangsum").value=liangnum;
					document.getElementById("zhongsum").value=zhongnum;
					document.getElementById("chasum").value=chanum;
					document.getElementById("onenum").value=yinum;
					document.getElementById("twonum").value=ernum;
					document.getElementById("threenum").value=sannum;
					document.getElementById("fournum").value=sinum;
				<%
					if ("6113".equals(departid)) {
				%>  
						document.getElementById("fivenum").value=wunum;
				 <%
					}
				 %>  
				document.getElementById("totalzhjfscore").value=returnFloat1(ecwewscoresum);
			}
		
</script>
<style type="text/css">

	body{
		   margin:0;padding:0;border:0;width:100%;height:100%; font-size: 12px;overflow:hidden;
	}
	table{
		margin:0;
		padding:0;
	}
	table tr td{
		font-size:12px;
	}
	#aaa{
		border: 1px solid #99BCE8;
	}
	#Datagrid1 tr td{
		border-left:1px solid #D0D0D0;
		border-right:1px solid #D0D0D0;
		border-bottom:1px solid #D0D0D0;
		background-color: transparent;
	}
	
	
	
	.hideDlg
    {
        height:129px;width:368px;
        display:none;
    }
    .showDlg
    {	
    	border: 1px solid #99BCE8;
        background-color:#ffffdd;
        height:129px;width:368px;
        position: absolute; 
        display:block;
        z-index:5;
    }
    .showDeck {
        display:block;
        top:0px;
        left:0px;
        margin:0px;
        padding:0px;
        width:100%;
        height:100%;
        position:absolute;
        z-index:3;
        background:#cccccc;
    }
    .hideDeck 
    {
        display:none;
    }
	
</style>


<script type="text/javascript">

	function setRemark(v){
		showWindow(v);
		mini.get("remarkvalue").setValue(v.value);
		mini.get("remarkid").setValue(v.id);
		mini.get("remarkvalue").focus();
	}

	
	// 隐藏
	function hideWindow() {
		document.getElementById('divBox').className='hideDlg';
        document.getElementById("deck").className="hideDeck";
	};
	
	function onRemarkOk(){
		var remarkvalue = mini.get("remarkvalue").getValue();
		var remarkid = mini.get("remarkid").getValue();
		document.getElementById(remarkid).value = remarkvalue;
		hideWindow();
	}

    function showWindow(v){
        //显示遮盖的层
      var objDeck = document.getElementById("deck");
        if(!objDeck){
            objDeck = document.createElement("div");
            objDeck.id="deck";
            document.body.appendChild(objDeck);
        }
        objDeck.className="showDeck";
        objDeck.style.filter="alpha(opacity=50)";
        objDeck.style.opacity=40/100;
        objDeck.style.MozOpacity=40/100;
        //显示遮盖的层end
        
        //改变样式
        document.getElementById('divBox').className='showDlg';
        
        //调整位置
        adjustLocation(v);
    }
    
    
	function adjustLocation(v){
        var obox=document.getElementById('divBox');
        if (obox !=null && obox.style.display !="none"){
            var w=368;
            var h=129;
            var oLeft,oTop;
			var top = getTop(v); //remark框对象的纵坐标
			var left = getLeft(v);//remark框对象的横坐标
			oTop = top;
			if(oTop >= 382){
				oTop = 382;
			}
			oLeft = left - w;
			
            obox.style.left=oLeft;
            obox.style.top=oTop;
        }
    }
	
	//获取元素的横坐标 
	function getLeft(e){ 
		var offset=e.offsetLeft; 
		if(e.offsetParent!=null) {
			offset+=getLeft(e.offsetParent); 
		}
		return offset; 
	} 
	
	 //获取元素的纵坐标()
	 function getTop(element){
		 // Iterate the offsetParents
		 // Add up offsetTop values
		 var y = 0;
		 for(var e = element; e; e = e.offsetParent){
			 y += e.offsetTop;
		 }
	    // Now loop up through the ancestors of the element, looking for
	     // any that have scrollTop set. Subtract these scrolling values from
	     // the total offset. However, we must be sure to stop the loop before
	     // we reach document.body, or we'll take document scrolling into account
	     // and end up converting our offset to window coordinates.
		 
		 for(e = element.parentNode; e && e != document.body; e = e.parentNode){
			 // subtract scrollbar values
			 if (e.scrollTop) y -= e.scrollTop; 
		 }
		 // This is the Y coordinate with document-internal scrolling accounted for.
		 return y;
	 }
    
	//获取元素的纵坐标 (绝对纵坐标，有问题作废)
	function getTop2(e){ 
		var offset=e.offsetTop; 
		if(e.offsetParent!=null) {
			offset+=getTop(e.offsetParent); 
		}
		return offset; 
	}
	
	function onScroll(){
		//alert(window.screen.width);
		//alert(window.screen.height);
	}
	
	
	
	
	//在IE9及以上正常，但IE8以下显示不正常，陕西省大部分客户端为IE8
	function setRemark_back(v){
		var atEl = document.getElementById(v.id);
		showEditWindow(atEl, 'Center', 'below');
		
		mini.get("remarkvalue").setValue(v.value);
		mini.get("remarkid").setValue(v.id);
	}
	
	function showEditWindow(atEl, xAlign, yAlign){
		var editWindow = mini.get("editWindow");
		editWindow.showAtEl( atEl, {xAlign:xAlign, yAlign:yAlign});
	}
	
	// 隐藏
	function hideEditWindow() {
		// 界面
		var editWindow = mini.get("editWindow");
		editWindow.hide();
	};
	
	function onRemarkOk1(){
		var remarkvalue = mini.get("remarkvalue").getValue();
		var remarkid = mini.get("remarkid").getValue();
		document.getElementById(remarkid).value = remarkvalue;
		hideEditWindow();
	}
	
</script>

</head>
<body>
		<form name="form1" method="post">
			<div id="fixed_div"> <!-- fixed begin -->
	        <div class="mini-toolbar" style="padding:0px;border-bottom:0;">
	        	<input type="hidden" name="listsize" value='<%=bean.get("zcrs")%>'>
				<input type="hidden" id="hiddendaynum" name="hiddendaynum" value='${cws.remark }'>
				<input type="hidden" name="department" value='${sdid }'>
				<input type="hidden" id="all" value="" />
	            <table style="width:100%;">
	                <tr>
						<td class="TableHeader" height="25" style="font-size:16px;">
							&nbsp;考核考勤管理月报表
						</td>
					</tr>
	            </table>           
	        </div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#E7EBEF" style="border-left:1px solid #99BCE8;">
				<tr>
					<td class="TableHeader" height="25" align="left" style="padding-left:10px;">
						基础分等级:<input type="text" id="jichufenlevel" name="jichufenlevel" style="width: 40px;vertical-align: middle;">
						基础分值:<input type="text" id="jichufen" name="jichufen" style="width: 40px;vertical-align: middle;">
						劳动等级:<input type="text" id="laodonglevel" name="laodonglevel" style="width: 40px;vertical-align: middle;">
						劳动等级分值:<input type="text" id="laodonglevelfenzhi" name="laodonglevelfenzhi" style="width: 40px;vertical-align: middle;">
						超产奖分:<input type="text" id="chaochanjiangfen" name="chaochanjiangfen" style="width: 40px;vertical-align: middle;">
						综合奖分:<input type="text" id="zonghejiangfen" name="zonghejiangfen" style="width: 40px;vertical-align: middle;">
						<input onclick="shezhifenzhi()" class="button" id="newgroup" onmouseover="onButton(this);" onmouseout="offButton(this);" type="button" value="批量设置" name="newgroup" style="vertical-align: middle;">
						<input onclick="shezhishijifen();" class="button" id="newgroup" onmouseover="onButton(this);" onmouseout="offButton(this);"	type="button" value="自动计算" name="newgroup" style="vertical-align: middle;">
						<input onclick="saveall();" class="button" id="newgroup" onmouseover="onButton(this);" onmouseout="offButton(this);" type="button" value="保存" name="newgroup" style="vertical-align: middle;">
					</td>
					<td align="right">
						<input id="ecwdate" name="ecwdate"  class="mini-monthpicker" value="${ecwdate}" format="yyyy-MM" style="width: 80px;"/>
						<input type="button" onclick="dosubmit('toAssessAndAttend.page','');" class="button" id="newgroup" 
								onmouseover="onButton(this);" onmouseout="offButton(this);" value="查询" name="newgroup" style="vertical-align: middle;">
					</td>
				</tr>
			</table>
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="aaa">
				<tr>
					<td class="tablebody">
						<table cellspacing="0" cellpadding="4" border="0" id="Datagrid1" style="border-collapse: collapse;">
							<tr style="height: 25px">
								<td style="border-top:none;border-left:none;" class="TableBody" align="center" width="1%" rowspan="3">
									<input type="checkbox" id="0" name="checkall" onclick="javascript:SelectAllCheckboxes(this);">
								</td>
								<td style="border-top:none;" class="TableBody" align="center" width="6%" rowspan="3">
									罪犯编号
								</td>
								<td style="border-top:none;" class="TableBody" align="center" width="4%" rowspan="3">
									姓 名
								</td>
								<td style="border-top:none;" class="TableBody" align="center" width="2%" rowspan="2" >
									在 册人 数
									<br>
									<input type="text" size="6" name="zcrs"	value='<%=bean.get("zcrs")==null?"": bean.get("zcrs")%>' readonly>
								</td>
								
								<td style="border-top:none;" class="TableBody" align="center" width="8%" colspan="2">
									综 合 评 估
								</td>
								<td style="border-top:none;" class="TableBody" align="center" width="32%" colspan="6">&nbsp;
								      优<input type="text" style="width:50px" size="6" id="yousum" name="yousum" value='<%=bean.get("yousum")==null?"": bean.get("yousum")%>' readonly>&nbsp;&nbsp;&nbsp;&nbsp;
						                              良<input type="text" style="width:50px" size="6" id="liangsum" name="liangsum" value='<%=bean.get("liangsum")==null?"": bean.get("liangsum") %>' readonly>&nbsp;&nbsp;
						                              一般<input type="text" style="width:50px" size="6" id="zhongsum" name="zhongsum" value='<%=bean.get("zhongsum")==null?"":bean.get("zhongsum") %>'>&nbsp;&nbsp;
						                             较差<input type="text" style="width:50px" size="6" id="chasum" name="chasum" value='<%=bean.get("chasum")==null?"": bean.get("chasum")%>'>
			                    </td>
								<td style="border-top:none;" class="TableBody" align="center" width="6%" rowspan="2">
									综合奖分总分
									<br>
									<br>
									<input type="text" size="6" id="totalzhjfscore"	name="totalzhjfscore" value='<%=bean.get("zhallsum")==null?"": bean.get("zhallsum")%>'>
								</td>
								<td style="border-top:none;" class="TableBody" align="center" width="6%" rowspan="2">
									劳动考核总分
									<br>
									<br>
									<input type="text" size="6" id="totalscore" name="totalscore" value='<%=bean.get("worksum")==null?"": bean.get("worksum")%>'>
								</td>
							</tr>
							<tr>
								<td class="TableBody" align="center" width="8%" colspan="2">
									岗位等级指标
								</td>
								<td class="TableBody" align="center" width="32%" colspan="6">
									    一等<input type="text" style="width:50px" size="6" id="onenum" name="onenum" value='<%=bean.get("one")==null?"": bean.get("one")%>'>&nbsp;&nbsp;
							                            二等<input type="text" style="width:50px" size="6" id="twonum" name="twonum" value='<%=bean.get("two")==null?"": bean.get("two")%>'>&nbsp;&nbsp;
							                            三等<input type="text" style="width:50px" size="6" id="threenum" name="threenum" value='<%=bean.get("three")==null?"": bean.get("three")%>'>&nbsp;&nbsp;
							                            四等<input type="text" style="width:50px" size="6" id="fournum" name="fournum" value='<%=bean.get("four")==null?"": bean.get("four")%>'>
							     <%
								  if ("6113".equals(departid)) {
								 %>               
							          &nbsp;&nbsp;五等<input type="text" style="width:50px" size="6" id="fivesum" name="fivesum" value='<%=bean.get("five")==null?"":bean.get("five") %>'>
								<%
								  }
								 %>  	
								</td>
							</tr>
							<tr style="width: 100%">
								<td class="TableBody" align="center" width="5%">
									出勤天数
								</td>
								<td class="TableBody" align="center" width="5%">
									基础分等级
								</td>

								<td class="TableBody" align="center" width="5%">
									基础分
								</td>
								<td class="TableBody" align="center" width="5%">
									劳动等级
								</td>
								<td class="TableBody" align="center" width="5%">
									劳动得分
								</td>
								<td class="TableBody" align="center" width="5%">
									行政奖分
								</td>
								<td class="TableBody" align="center" width="5%">
									行政扣分
								</td>
								<td class="TableBody" align="center" width="5%">
									超产奖分
								</td>
								<td class="TableBody" align="center" width="5%">
									综合奖分
								</td>
								<td class="TableBody" align="center" width="6%">
									考核实得成绩
								</td>
								<td class="TableBody" align="center" width="4%">
									备注
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</div> <!-- fixed end -->
			
			<div id="flow" style="OVERFLOW: scroll; scrollbar-face-color:#B3DDF7;
				scrollbar-shadow-color:#B3DDF7;scrollbar-highlight-color:#B3DDF7;
				scrollbar-3dlight-color:#EBEBE4;scrollbar-darkshadow-color:#EBEBE4;
				scrollbar-track-color:#F4F4F0;scrollbar-arrow-color:#000000; 
				width:100%;HEIGHT: 380px " align=center>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="aaa">
				<tr>
					<td class="tablebody">
					<table cellspacing="0" cellpadding="4" border="0" id="Datagrid1" style="border-collapse: collapse;">
					<%
						for (int i = 0; i < list.size(); i++) {
							Map ws = (Map) list.get(i);
					%>
					<tr style="width: 100%">
						<td class="TableBody" align="center" width="1%">
							<input type="checkbox" name="criminalArr" value="<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>">
						</td>
						<td class="TableBody" align="center" width="6%"><%=ws.get("crimid") == null ? "" : ws.get("crimid")%></td>
						<td class="TableBody" align="center" width="4%"><%=ws.get("name") == null ? "" : ws.get("name")%></td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="workdaynum" readonly id="workdaynum<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("daynum") == null ? "" : ws.get("daynum")%>'>
						</td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="basiclevel" id="basiclevel<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("int1") == null ? "" : ws.get("int1")%>'>
						</td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="ecwzhpgscore" id="ecwzhpgscore<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("basicscore") == null ? "" : ws.get("basicscore")%>'>
						</td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="worklevel" id="worklevel<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("int2") == null ? "" : ws.get("int2")%>'>
						</td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="ecwldgzscore" id="ecwldgzscore<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("ldgzscore") == null ? "" : ws.get("ldgzscore")%>'>
						</td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="ecwxzjfscore" id="ecwxzjfscore<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("xzjfscore") == null ? "" : ws.get("xzjfscore")%>'>
						</td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="ecwxzkfscore" id="ecwxzkfscore<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("xzkfscore") == null ? "" : ws.get("xzkfscore")%>'>
						</td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="ccscore" id="ccscore<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("ccjfscore") == null ? "" : ws.get("ccjfscore")%>'>
						</td>
						<td class="TableBody" align="center" width="5%">
							<input type="text" size="4" name="ecwewscore" id="ecwewscore<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("zhjfscore") == null ? "" : ws.get("zhjfscore")%>'>
						</td>
						<td class="TableBody" align="center" width="6%">
							<input type="text" size="4" name="ecwkhscore" id="ecwkhscore<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("khscore") == null ? "" : ws.get("khscore")%>'>
						</td>
						<td class="TableBody" align="center" width="4.9%">
							<input type="text"  onclick="setRemark(this);" size="4" name="beizhu" id="beizhu<%=ws.get("crimid") == null ? "" : ws.get("crimid")%>" value='<%=ws.get("remark") == null ? "" : ws.get("remark")%>'>
						</td>
					</tr>
					<%
						}
					%>
				</table>
				</td>
				</tr>
				</table>
			
				<p align="center" style="font-family: 方正小标宋简体; font-size: 18px;">
					操作说明&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</p>
				<dl>
					<dd>
						<p style="font-family: 仿宋_GB2312; font-size: 15px;" align="left">
							1、 点击左侧菜单栏【考核考勤月报表】，打开劳动考勤管理月报表界面。其中出勤天数，由劳动考勤管理界面通过出勤统计获得。
							<br />
							2、 在左侧选中要设置的罪犯，在上方填写相应的基础分等级、基础分、劳动等级、劳动分、超产奖分综合奖分等，然后点批量设置。
							<br />
							3、 行政奖分，行政扣分、超产奖分、综合奖分都可以手动输入。
							<br />
							4、完成后，点击【自动计算】系统会自动计算分值，计算完成并确认无误后，点击【保存】即可。
							<br />
							5、备注中需要填写的内容有罪犯奖扣分信息，罪犯奖惩信息，罪犯减刑、加刑信息。
						</p>
					</dd>
				</dl>
			</div>
		</form>
		
		<div id="divBox" class="hideDlg" style="" >
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom:1;padding:1px;margin: 0px 0px 0px;">
		    	<input id="remarkid" name="remarkid" class="mini-hidden" value=""/>
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;" align="right">
		            		<a class="mini-button" onclick="onRemarkOk();" plain="true" style="width:60px">确定</a>       
		           			<a class="mini-button" onclick="hideWindow()"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                </tr>
	            </table>           
	        </div>
	        
	       <textarea id="remarkvalue" name="remarkvalue" class="mini-textarea"  style="overflow-y:auto;width:100%;height:110px;"> </textarea>
		 </div>
  
		
		<!-- 该弹出窗在IE8下异常，用其它方式替换  
		<div id="editWindow" class="mini-window" title="备注" style="width:320px;height:180px"
		    showModal="true" allowResize="true" allowDrag="true">
		    
		    <div class="mini-toolbar mini-grid-headerCell" style="border-bottom:0;padding:0px;margin: -5px -5px 5px;">
		    	<input id="remarkid" name="remarkid" class="mini-hidden" value=""/>
	            <table style="width:100%;">
	                <tr>
	                    <td style="width:100%;" align="right">
		            		<a class="mini-button" onclick="onRemarkOk1();" plain="true" style="width:60px">确定</a>       
		           			<a class="mini-button" onclick="hideEditWindow()"  plain="true" style="width:60px;">取消</a>                       
		           		</td>
	                </tr>
	            </table>           
	        </div>
	        
		     <textarea id="remarkvalue" name="remarkvalue" class="mini-textarea" emptyText="请输入备注" style="width:100%;height:110px;"> </textarea>
	</div>
	-->
	
	</body>
</html>