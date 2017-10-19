<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>指定合议庭</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
.mini-panel-border{
	border:0;
}
</style>
	</head>
	<body>
		<input name="createmender" class="mini-hidden" />
		<input name="updatemender" class="mini-hidden" />
		<input name="updatetime" class="mini-hidden" dateFormat="yyyy-MM-dd" />
		<input name="createtime" class="mini-hidden" dateFormat="yyyy-MM-dd" />
		<!-- <s:property value="#request.menuid"/> -->
		<input name="menuid" type="hidden"  id="menuid" value=""/>
		<!-- <s:property value="#request.type"/> -->
		<input name="type" type="hidden"  id="type" value=""/>
		<div id="datagrid" class="mini-splitter"
			style="width: 100%; height: 100%;">
			<div size="216" showCollapseButton="false">
				<div class="mini-toolbar"
					style="padding: 2px; border-top: 0;border-left: 0; border-right: 0;  ">
					<span style="padding-left:0px;"><a class="mini-button" plain="true" iconCls="icon-user" onclick="openPeoplesAssessorPage();">人民陪审员库</a></span>
					<a class="mini-button" plain="true" iconCls="icon-goto"
						onclick="settingCourt();">合议庭设置</a>				
				</div>
				<div  id ="heyi" style="padding: 10px; border-top: 0; border-left: 0; border-right: 0;">
				<input id="cpdcsnames" name="cpdcsnames"  type="hidden"/>
				<s:form id="form1">
				<table>
				<tr align="left">
					<td>
					合议庭名称
					</td>
					<td>
					<!-- getCollegiatePanelList.action?1=1 -->
					<input id="cpdcsname"  name="xuhao" class="mini-combobox" style="width:125px;" textField="text" valueField="id" emptyText="选择合议庭"
					url="" onvaluechanged="onDeptChanged" allowInput="true" showNullItem="true" nullItemText="请选择..." />
					</td>
				</tr>
				<tr align="left">
				<td>
					审判长
					</td>
					<td>
					<!-- getUserName.action?1=1 -->
					<input id="cpdcschiefjudge" showNullItem="false" required="true" onvaluechanged="chengbanren" name="cpdcschiefjudge" url="" class="mini-combobox" style="width:125px;" textField="text" valueField="id" />
					</td>
				</tr>
				<tr align="left">
					<td colspan="2">
					<input id="cpdcsjudgefirstduty" showNullItem="false" required="true" name="cpdcsjudgefirstduty" class="mini-combobox" style="width:92px;" textField="text" valueField="id" 
					onvaluechanged="onCpdmChanged('cpdcsjudgefirstduty','cpdcsjudgefirstid');" url="ajaxCodeShuJu.action?sctid=HY001" showNullItem="true" emptyText="请选择"/>
					<!-- getUserName.action?1=1 -->
					<input id="cpdcsjudgefirstid" onvaluechanged="chengbanren" required="true" showNullItem="false" name="cpdcsjudgefirstid" url="" class="mini-combobox" style="width:93px;" textField="text" valueField="id" />
					</td>
				</tr>
				<tr  align="left">
					<td colspan="2">
					<!-- ajaxCodeShuJu.action?sctid=HY001 -->
					<input id="cpdcsjudgesecondduty" showNullItem="false" required="true" name="cpdcsjudgesecondduty" class="mini-combobox" style="width:92px;" textField="text" valueField="id" 
					 onvaluechanged="onCpdmChanged('cpdcsjudgesecondduty','cpdcsjudgesecondid');" url="" showNullItem="true"  emptyText="请选择"/>
					 <!-- getUserName.action?1=1 -->
					<input id="cpdcsjudgesecondid" onvaluechanged="chengbanren" required="true" showNullItem="false" name="cpdcsjudgesecondid" url="" class="mini-combobox" style="width:93px;" textField="text" valueField="id" />
					</td>
				</tr>
				<tr align="left">
					<td>
					书记员
					</td>
					<td>
					<!-- getUserName.action?1=1 -->
					<input id="cpdcsclerk" name="cpdcsclerk" required="true" url="" class="mini-combobox" style="width:125px;" textField="text" valueField="id" />
					</td>
				</tr>
				<tr align="left">
				<td>
					承办人
					</td>
					<td>
					<!-- getUserName.action?othertype=-9999 -->
					<input id="chengbanren" name="chengbanren" url=""  class="mini-combobox" style="width:125px;" textField="text" valueField="id" required="true"/>
					</td>
				</tr>
				<!-- 
				<tr align="left">
				<td>
					人民陪审员
					</td>
					<td>
					<input name="renminpeishen" id="renminpeishen" class="mini-textarea" style="width: 125px;height: 100px;"/>
					</td>
				</tr>
				<tr align="left">
				<td>
					拟合议日期
					</td>
					<td>
					<input name="cpdenddiscussdate" class="mini-datepicker" allowInput="true" emptyText="请选择日期" value="${cpdenddiscussdate}" style="width:125px;" id="cpdenddiscussdate"/>
					</td>
				</tr>
				<tr align="left">
				<td>
					拟宣判日期
					</td>
					<td>
					<input name="cpdadjudgedate" class="mini-datepicker" allowInput="true" emptyText="请选择日期" value="${cpdadjudgedate}" style="width:125px;" id="cpdadjudgedate"/>
					</td>
				</tr> -->
				
				</table>
					</s:form>
				</div>
			</div>
			<div showCollapseButton="false">
				<div class="mini-toolbar" style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;" align="left">
				&nbsp;分案日期：<input id='fyfenandate' name="fyfenandate" class="mini-datepicker" allowInput="true" emptyText="请选择日期"  style="width:100px;" id="cpdadjudgedate"/>
				<!-- 案件号：【（	<input id="years" name="years" url="ajaxGetYearListForCollegialPanel.action?1=1" allowInput="true" class="mini-combobox" style="width:70px;" textField="text" valueField="id" value="113" />）年度  第 -->
					案件号：（<input id="years" name="years"  class="mini-textbox"  value="${years }" style="width:40px;text-align:center"/>）
                        <input id="casetype" name="casetype"  class="mini-textbox"  value="${sdnameforshort}" style="width:45px;text-align:center"/>
                        <!-- ajaxCodeShuJu.action?sctid=GK7788&scparentid=<s:property value='#request.scparentid'/> -->
                         <input id="qtype" name="qtype"  class="mini-combobox" value="${qtype }" url="" valueField="id" textField="text"  
                         style="width:75px;text-align:center" onvaluechanged="changed()"/>  第
					<input class="mini-textbox" vtype="maxLength:50;" style="width: 140px;" id="key1" emptyText="案件号范围：1,2或3-5"
						onenter="onKeyEnter" />
						号
						<!-- getDepartList.action?1=1&qtype=fyjianyu -->
					<input id="zhixingjiguan" class="mini-combobox" valueField="sdid" textField="sddiscribe" emptyText="执行机关过滤"
                          showNullItem="true" nullItemText="--全部--" url="" style="width:100px;" onvaluechanged="search222()"
                         />	
					<a class="mini-button" iconCls="icon-search" plain="true"
						onclick="search222()">查找</a>
						<a class="mini-button" plain="true" iconCls="icon-ok"
						onclick="piliangshezhi();">批量指定合议庭</a>
				</div>
				<div class="mini-fit">
					<div id="datagrid1" class="mini-datagrid"
						style="width: 100%; height: 100%;" url="ajaxGetDataById.action" allowAlternating="true"
						idField="" sizeList="[10,20,50,100,200,500]"
						pageSize="20" multiSelect="true">
						
						<div property="columns">
							<div type="checkcolumn" width="15"></div>
							<div field="parolenumber"  headerAlign="center" width="70"
								align="center" allowSort="true">
								案件号
							</div>
							<div field="cpdcasenumber" class="mini-hidden" width="0">
								案号
							</div> 
							<div field="jianyu"  headerAlign="center" width="40"
								align="center" allowSort="true" >
								执行机关
							</div>
							<div field="cbiname"  headerAlign="center" width="35"
								align="center" allowSort="true">
								姓名
							</div>
							<div field="cppoopinion"  headerAlign="center"
								align="center" allowSort="true">
								监狱意见
							</div>
							<div field="cpdregisterdate" headerAlign="center" width="40"
								align="center"  dateFormat="yyyy-MM-dd" allowSort="true">
								立案时间
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		<script type="text/javascript">
        mini.parse();
       	var heyi = mini.get("heyi");
       	var grid = mini.get("datagrid1");
       	grid.hideColumn("chengbanren");
       	mini.get("fyfenandate").setValue(new Date());//分案时间
      
       	var form = new mini.Form("#form1");
       	var menuid = document.getElementById("menuid").value;
       	var type = document.getElementById("type").value;
       	//"getLiAnCriminalList22.action?1=1&menuid="+menuid+"&type="+type
       	url="ajaxGetDataById.action";
       	grid.setUrl(url);
        grid.load();
        grid.sortBy("parolenumber", "desc");
        function onCriminalidRenderer(e) {
            var grid = e.sender;
            var record = e.record;
            var uid = record._uid;
            var rowIndex = e.rowIndex;
            var s = ' <a class="Edit_Button" href="javascript:getCriminalInfo(\'' + record.criminalid + '\')" >'+record.criminalid+'</a>&nbsp;&nbsp;';
            return s;
        }
        function getCriminalInfo(criminalid){
        	mini.open({
                  url: "openSearchPage.action?menuid=8328&criminalid="+criminalid,
                  showMaxButton: true,
                  allowResize: false, 
                  title: "罪犯信息", width: 1000, height: 550,
                  onload: function () {
                     // var iframe = this.getIFrameEl();
                      //var data = { action: "edit",menuid:menuid};
                  },
                  ondestroy: function (action) {
                      grid.reload();
                  }
              	});	
        }
		
		function changed(){
			search222();
		}
        
        
		//联动选择
        function onDeptChanged(e) {
	        var ss = mini.get("cpdcsname").getValue();
	        $.ajax( {
					url : "ajaxSelectNameById.action?id="+ss,
					type : "post",
					cache : false,
					success : function(text) {
						var o = mini.decode(text);
						form.setData(o);
						form.setChanged(false);
					}
				});
			setTimeout(chengbanren,1000);
        }
        
        function chengbanren(e) {
            var cpdcschiefjudge = mini.get("cpdcschiefjudge").getValue();
            var cpdcsjudgefirstid = mini.get("cpdcsjudgefirstid").getValue();
            var cpdcsjudgesecondid = mini.get("cpdcsjudgesecondid").getValue();
            var temcon=cpdcschiefjudge+"_"+cpdcsjudgefirstid+"_"+cpdcsjudgesecondid;
            var tempurl="getUserName.action?othertype="+temcon;
            mini.get("chengbanren").load(tempurl);
       }
        
        
         function dosubmit() {
     var form = new mini.Form("form1");
		var pici = mini.get("pici").getValue();
		var fanwei = mini.get("fanwei").getValue();
		var picidayin='no';
		//if(pici != undefined){
		if(pici == "true"){
			//if( pici.getChecked()){
				picidayin='yes';
			//}
		} else {
			if (fanwei == ""){
				alert("请输入案件号范围(示例：1-9 或者1,6,9)或者选择当前批次！");
				return;
			}
		}
		var o = form.getData(); 
		var json = mini.encode([o]);
		var rbl = document.getElementById("rbl").value;
		var fangan = document.getElementById("fangan").value;
		if(picidayin=='no')
		{
			form.validate();
			if (form.isValid() == false) return;
			if(fangan!="")
			{
				$.ajax({
		             url: "ajaxgetallwenshu.action?1=1",
		             data: { data: json,rbl:rbl,fangan:fangan,pici:picidayin},
		             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		             cache: false,
		             type: "post",
		             success: function (text) {
		             	hebing(text);
		             },
		             error: function (jqXHR, textStatus, errorThrown) {
		            	 alert("操作失败!");
		             }
		         });
			}else{
				alert("请选择合并方案");
			}
		}
		else{
			$.ajax({
	             url: "ajaxgetallwenshu.action?1=1",
	             data: { data: json,rbl:rbl,fangan:fangan,pici:picidayin},
	             contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
	             cache: false,
	             type: "post",
	             success: function (text) {
	             	hebing(text);
	             },
	             error: function (jqXHR, textStatus, errorThrown) {
	            	 alert("操作失败!");
	             }
	         });
		}
	}   
        
        function check(obj){
    		if(isNaN(obj.value)){
				window.alert("您必须输入数字");
				obj.select();
		}
	}
	
	function isNumber(value){
		return /^[(-?\d+\.\d+)|(-?\d+)|(-?\.\d+)]+$/.test(value + '');
	}
       
        function search222() {
	        var key1 = mini.get("key1").getValue();
	        var years = mini.get("years").text;
			var qtype=mini.get("qtype").getValue();
	        if(""!=key1&!isNumber(key1)){
	        	alert("请输入数字，格式为：1,2,3或5-10等！");
	        	return false;
	        }
	        var zhixingjiguan = mini.get("zhixingjiguan").getValue();
	        url="getLiAnCriminalList22.action?1=1&key1="+key1+"&years="+years+"&type=" + type+"&zhixingjiguan="+zhixingjiguan+"&qtype="+qtype;
	    	grid.setUrl(url);
	       	grid.load();
        }
        
        
        
        
        function onKeyEnter(e) {
           // search();
           search222();
        }
        //批量指定合议庭
        function piliangshezhi(){
		 var rows = grid.getSelecteds();
		 var qtype=mini.get("qtype").getValue();
		 var cpdcsname = mini.get("cpdcsname").getValue();
		 var cpdcschiefjudge = mini.get("cpdcschiefjudge").getValue();
		 var cpdcsjudgefirstduty = mini.get("cpdcsjudgefirstduty").getValue();
		 var cpdcsjudgefirstid = mini.get("cpdcsjudgefirstid").getValue();
		 var cpdcsjudgesecondduty = mini.get("cpdcsjudgesecondduty").getValue();
		 var cpdcsjudgesecondid = mini.get("cpdcsjudgesecondid").getValue();
		 var cpdcsclerk = mini.get("cpdcsclerk").getValue();
		 var xuhao = mini.get("cpdcsname").getValue();
		 var chengbanren = mini.get("chengbanren").getValue();
		if ('' == cpdcschiefjudge) {
			alert("审判长不能为空，请填写！");
			return false;
		}
		if ('' == chengbanren||chengbanren==null) {
			alert("承办人不能为空，请填写！");
			return false;
		}
		if ('' == cpdcsclerk) {
			alert("书记员不能为空，请填写！");
			return false;
		}
		if ('' == cpdcsjudgefirstid || '' == cpdcsjudgesecondid) {
			alert("审判员不能为空，请填写！");
			return false;
		}
		if ('' == cpdcsjudgefirstduty || '' == cpdcsjudgesecondduty) {
			alert("职务不能为空，请填写！");
			return false;
		}

		if ('' != cpdcschiefjudge) {
			if (cpdcschiefjudge == cpdcsjudgefirstid
					|| cpdcschiefjudge == cpdcsjudgesecondid
					|| cpdcschiefjudge == cpdcsclerk) {
				alert("审判长与其他名字重复，请重新填写！");
				return false;
			}
		}
		if ('' != cpdcsjudgefirstid) {
			if (cpdcsjudgefirstid == cpdcschiefjudge
					|| cpdcsjudgefirstid == cpdcsjudgesecondid
					|| cpdcsjudgefirstid == cpdcsclerk) {
				alert("审判员1与其他名字重复，请重新填写！");
				return false;
			}
		}
		if ('' != cpdcsjudgesecondid) {
			if (cpdcsjudgesecondid == cpdcschiefjudge
					|| cpdcsjudgesecondid == cpdcsjudgefirstid
					|| cpdcsjudgesecondid == cpdcsclerk) {
				alert("审判员2与其他名字重复，请重新填写！");
				return false;
			}
		}
		if ('' != cpdcsclerk) {
			if (cpdcsclerk == cpdcsjudgefirstid
					|| cpdcsclerk == cpdcsjudgesecondid
					|| cpdcsclerk == cpdcschiefjudge) {
				alert("书记员与其他名字重复，请重新填写！");
				return false;
			}
		}
		if ('' == xuhao) {
			alert("请先选择合议庭再进行设置！");
			return false;
		}
		if (rows.length > 0) {
			var criminalids = [];
			var ccids = [];
			var sdids = [];
			var anhaos =[];
			for ( var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				anhaos.push(r.cpdcasenumber);
				criminalids.push(r.criminalid);
				ccids.push(r.ccid);
				sdids.push(r.sdid);
			}
			var anhao=anhaos.join(',');
			var criminalid = criminalids.join(',');
			var ccid = ccids.join(',');
			var sdid = sdids.join(',');
			$.ajax( {
				url : "ajaxSaveCollegiatePanel.action?1=1",
				type : "post",
				data : {
					cpdcasenumber:anhao,
					criminalid : criminalid,
					cpdcschiefjudge : cpdcschiefjudge,
					ccid : ccid,
					sdid : sdid,
					cpdcsjudgefirstid : cpdcsjudgefirstid,
					cpdcsjudgefirstduty : cpdcsjudgefirstduty,
					cpdcsjudgesecondduty : cpdcsjudgesecondduty,
					cpdcsjudgesecondid : cpdcsjudgesecondid,
					cpdcsclerk : cpdcsclerk,
					chengbanren : chengbanren,
					menuid : menuid,
					type : type
				},
				success : function(text) {
					if ('error' == text) {
						alert("已经正在审批中，无法再次更改审判长！");
						return false;
					}
					grid.reload();
					alert("指定成功！");
				},
				error : function() {
					alert("操作失败！")
				}
			});
		} else {
			alert("请选择一条记录进行操作！");
		}

	}

	//合议庭设置
	function settingCourt() {
		mini.open( {
			url : "fullCourtSettle.action?1=1&menuid=7263",//对应合议庭设置菜单，此菜单对应合议庭设置表
			title : "合议庭列表",
			width : 1000,
			height : 600,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {
					action : "new"
				};
				//iframe.contentWindow.SetData(data);
		},
		ondestroy : function(action) {
			grid.reload();
			mini.get("cpdcsname").setUrl("getCollegiatePanelList.action?1=1");
		}
		});
	}
	function openPeoplesAssessorPage(){
		mini.open( {
			url : "openPeoplesAssessorPage.action?1=1&menuid=7263",
			title : "人民陪审员列表",
			width : 1000,
			height : 600,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {
					action : "new"
				};
				//iframe.contentWindow.SetData(data);
		},
		ondestroy : function(action) {
			grid.reload();
		}
		});
	}
	function onCpdmChanged(cpdcsjudgeduty,cpdcsjudgeid){
		var cpdcsjudgeduty = mini.get(cpdcsjudgeduty);
		var cpdcsjudgeid = mini.get(cpdcsjudgeid);
		var assessorurl = "getAssessorName.action?1=1"
		var judgeurl = "getUserName.action?1=1"
		if(cpdcsjudgeduty.getValue()=='3'){
			cpdcsjudgeid.setUrl(assessorurl);
        	cpdcsjudgeid.select(0);
		}else{
			cpdcsjudgeid.setUrl(judgeurl);
        	cpdcsjudgeid.select(0);
		}
	}
</script>
	</body>
</html>