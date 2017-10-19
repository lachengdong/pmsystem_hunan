<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>法院减刑假释案件查看</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
    </style>
</head>
<body onload="init('${menuid}');">
    <div>
    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    <input id="menuid" name="menuid" type="hidden" value="<s:property value='#request.menuid'/>"/>
        <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                    	<a class="mini-button" style="display:none" id="10270_01" iconCls="icon-undo" onclick="piliangtuihui()" plain="true">批量退回</a>
                    </td>
                    <!-- getCaseComBox.action?qtype=fyjxjs
                    getCaseComBox.action?qtype=fyjxjs
                    getCaseComBox.action?qtype=fyjxjs
                    getDepartList.action?1=1&qtype=fyjianyu
                     -->
                    <td style="white-space:nowrap;">
                    	<input class="mini-combobox" id="14367" style="display:none"  data="ANJIANLEIBIE" valueField="id" textField="text"  emptyText="案件类型"  onvaluechanged="changeAnjianleixing"  showNullItem="true"  style="width:150px;"/>
                    	<div id="checkboxshenpi" class="mini-checkbox" checked="false" readOnly="false" text="本人审批案件" onvaluechanged="search('')"></div>
                    	<input id="lianstartdate"  class="mini-datepicker" format="yyyy-MM-dd" valueField="id" textField="text"  emptyText="立案开始时间" showNullItem="true" 
		             		style="width:150px;" /> 
			             <input id="lianenddate"  class="mini-datepicker" format="yyyy-MM-dd" valueField="id" textField="text"  emptyText="立案结束时间" showNullItem="true" 
			              	style="width:150px;" /> 
                        <input id="caseid" class="mini-combobox"  valueField="CODEID" textField="NAME"   emptyText="进程过滤" showNullItem="true" 
			            	nullItemText="--全部--" url="jxjs/getFYjincheng.action?1=1" style="width:150px;" 
			            	onvaluechanged="search('')"/> 
			             <!--  <input id="anyou" class="mini-combobox" valueField="scid" textField="sccontent"  emptyText="案由检索" showNullItem="true" 
			            	nullItemText="" url="getAnyou.action?1=1" style="width:150px;" 
			            	onvaluechanged="oncasechanged"
			             />  -->
                    	 <input id="depid" class="mini-combobox" valueField="jailid" textField="jailname"  emptyText="单位过滤" showNullItem="true" 
			            	nullItemText="--全部--" url="getJailListUnderCourt.action" style="width:150px;"  enabled="true"
			            	onvaluechanged="search('')" /> 
			              <input id="key" class="mini-textbox" emptyText="编号、姓名、案号、年号、案由、单位" width="210" id="key" onenter="onKeyEnter"  enabled="true"/>
                        <a class="mini-button" onclick="search('')" plain="true">查询</a>
                    </td>
                </tr>
            </table>           
        </div>
     </div>
    <div class="mini-fit">
    <!-- getFyJxjsCaseDataList.action?1=1&qtype=all -->
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" pageSize="20"
         idField="spid" multiSelect="true"  allowAlternating="true" sizeList="[10,20,50,100]" onrowdblclick="rowdblclickfunction"  url="CourtCaseData.action?1=1&type3=local">
        <div property="columns">
            <div type="checkcolumn" width="20px;"></div>
            <div field="anjiannianhao" width="60" headerAlign="center" align="center" allowSort="true">案件号</div> 
            <div field="anhao" class="hidden" width="0" headerAlign="center" align="center" allowSort="true">案号</div> 
            <div field="xingming" width="40" headerAlign="center" align="center" allowSort="true">姓名</div>   
            <div field="anjianleixing" width="0" headerAlign="center" align="center" allowSort="true">案件类型</div>   
            <div field="zuiming" width="40" headerAlign="center" align="center" allowSort="true">案由</div> 
            <div field="zhixingjiguan" width="60" headerAlign="center" align="center" allowSort="true">执行机关</div> 
            <div field="chengbanren" width="60" headerAlign="center" align="center" allowSort="true">案件承办人</div>
            <div field="jincheng" width="120" headerAlign="center" align="center" allowSort="false" renderer="zhuangtai">进程</div>
        </div>
    </div>
  </div>

    <script type="text/javascript">
    	var ANJIANLEIBIE = [{ id: 'other_fyjxjssp', text: '减刑案件' }, { id: 'other_fyjxjsbbsp', text: '报备案件'}, { id: 'other_fyjxjsjcsp', text: '检查案件'}, { id: 'other_fyjxjsjdsp', text: '监督案件'}]; 
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("anjiannianhao", "asc");
		grid.load();
		var menuid = document.getElementById("menuid").value;
        //行双击时发生  目前用于法院双击行弹出办案事件
	    function rowdblclickfunction(e){
	    	var record = e.record;
	    	daibanshenpi('toPrisonInfoPage.action?1=1',record.parolenumber ,record.anjianleixing,record.flowdraftid,record.crimid,'toCourtcaseLookPage.action?1=1@2=2@menuid=10270');
	    }
        function daibanshenpi(myurl,anjianhao,anjiantype,flowdraftid,crimid,backaction) {
            var row = grid.getSelected();
            var type=encodeURI(anjiantype);
            if (row) {
            	myurl=myurl+'&anjianhao='+anjianhao+"&anjiantype="+type+"&flowdraftid="+flowdraftid+"&crimid="+crimid+"&backaction="+backaction;
                self.location.href=myurl;
            } else {
                alert("请选中一条记录");
            }
        }
        grid.on("drawcell",function(e){
        			 var record = e.record,
				     column = e.column,
				     field = e.field,
				     value = e.value;
				     var jinchengid = record.jincheng;
				     //给帐号列，增加背景色
		             if (field == "jincheng" ) {
		            	if(jinchengid=='20'){
		                	e.cellStyle = "background:#87CEEB";
		                }else if(jinchengid=='22'){
		                	e.cellStyle = "background:#50F0B4";
		                }else if(jinchengid=='24'){
		                	e.cellStyle = "background:#F08080";
		                }else if(jinchengid=='25'){
		                	e.cellStyle = "background:#20B2AA";
		                }else if(jinchengid=='26'){
		                	e.cellStyle = "background:#FFFFE0";
		                }else if(jinchengid=='27'){
		                	e.cellStyle = "background:#DDA0DD";
		                }else if(jinchengid=='28'){
		                	e.cellStyle = "background:#B0E0E6";
		                }else if(jinchengid=='29'){
		                	e.cellStyle = "background:#98FB98";
		                }else if(jinchengid=='30'){
		                	e.cellStyle = "background:#D97E96";
		                }else if(jinchengid=='31'){
		                	e.cellStyle = "background:#00FF7F";
		                }else if(jinchengid=='32'){
		                	e.cellStyle = "background:#D2B48C";
		                }else if(jinchengid=='33'){
		                	e.cellStyle = "background:#00FFFF";
		                }
		             }
        });
        function changeAnjianleixing(e){
        	var flowdefid = e.value;
        	search(flowdefid);
        }
        function search(flowdefid) {
        	var checkboxshenpi=mini.get("checkboxshenpi").getValue();
       		var depid = mini.get("depid").getValue();
       		//var anyou=mini.get("anyou").getValue();
       		var key = mini.get("key").getValue();
           	var caseid = mini.get("caseid").getValue();
       		var depid = mini.get("depid").getValue();
       		var lianstartdate=mini.get("lianstartdate").getValue();
        	var lianenddate=mini.get("lianenddate").getValue();
        	var jincheng=mini.get("caseid").getValue();
        	var jincheng=mini.get("caseid").getValue();
        	if(lianstartdate>lianenddate){
        		alert("立案开始时间不能大于立案结束时间！请重新选择！");
        		return ;
        	}
       		//var anyou=mini.get("anyou").getValue();
       		var key = mini.get("key").getValue();
       		grid.load({flowdefid:flowdefid, caseid: caseid, key : key, depid: depid,lianstartdate:mini.formatDate(lianstartdate,"yyyy-MM-dd"),lianenddate:mini.formatDate(lianenddate,"yyyy-MM-dd"),checkboxshenpi:checkboxshenpi,jincheng:jincheng});//格式化时间
        }
        function onKeyEnter(e) {
            search('');
        }
        
        function piliangtuihui(){
        	 var rows = grid.getSelecteds();
        	  if (rows.length > 0) {
                if (confirm("此操作会清空案件相关的所有信息，退回至法院立案。是否确定批量退回选中案件？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.flowdraftid);
                    }
                    var id = ids.join(',');
                    $.ajax({
		                url:"piliangtuihuiAnJianForFy.action?1=1",
		                data: {	id : id },
		                type: "post",
		                success: function (text) {
		                	alert("操作成功！");
		                	grid.reload();
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                	 alert("操作失败!");
		                }
		            });
                }
            } else {
                alert("请选中一条记录");
            }
        }
        
        //弹出柱状图窗口
        function showChartWindow(){
        	 var url = "showCharByshenpan.action?1=1";
        	 mini.open({
                url: url,
                showMaxButton: true,
                allowResize: false, 
                title: "审判流程管理（柱状图）", width: 600, height: 450,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = {action:"new" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
         function zhuangtai(e){
        	var s=e.record;
        	if(s.jincheng=='18'){s='已开庭'}
        	if(s.jincheng=='19'){s='承办人在办理'}
        	if(s.jincheng=='20'){s='已立案,待分案'}
        	if(s.jincheng=='21'){s='承办人已办理'}
        	if(s.jincheng=='22'){s='已合议'}
        	if(s.jincheng=='23'){s='承办人未办理'}
        	if(s.jincheng=='24'){s='已报审判长'}
        	if(s.jincheng=='25'){s='已报主管副庭长'}
        	if(s.jincheng=='26'){s='已报庭长'}
        	if(s.jincheng=='27'){s='已报主管副院长'}
        	if(s.jincheng=='28'){s='已报院长'}
        	if(s.jincheng=='29'){s='领导审批同意'}
        	if(s.jincheng=='30'){s='退回复议'}
        	if(s.jincheng=='31'){s='已报备'}
        	if(s.jincheng=='32'){s='已结案'}
        	if(s.jincheng=='33'){s='已归档'}
        	 return s;
        }
        
    </script>
</body>
</html>