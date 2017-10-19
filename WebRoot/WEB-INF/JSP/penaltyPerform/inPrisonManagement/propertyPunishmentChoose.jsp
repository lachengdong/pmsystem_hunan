<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>基本信息财产刑、三类犯管理、调狱信息补录</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
    <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }  
    </style>
</head>
<body onload="init('601861');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
                                        犯罪时间:
                    <input id="startfzsj" name="startfzsj" allowInput="true" format="yyyy-MM-dd" showTime="true" emptyText="起始时间" style="width: 100px;"  class="mini-datepicker"/>
                    <input id="endfzsj" name="endfzsj" allowInput="true" format="yyyy-MM-dd" showTime="true"  emptyText="结束时间"  style="width: 100px;"  class="mini-datepicker"/>                    
                    <span class="separator"></span> 
                                        判决时间:
                    <input id="startpjsj" name="startpjsj" allowInput="true" format="yyyy-MM-dd" showTime="true" emptyText="起始时间" style="width: 100px;"  class="mini-datepicker"/>
                    <input id="endpjsj" name="endpjsj" allowInput="true" format="yyyy-MM-dd" showTime="true"  emptyText="结束时间"  style="width: 100px;"  class="mini-datepicker"/>                    
                	 <a class="mini-button" iconCls="icon-add" plain="true" onclick="chooseOne(13694);">补录</a>
                </td>
                <td style="white-space:nowrap;"> 
                    <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="罪犯编号、姓名、拼音" style="width:160px;" onenter="onKeyEnter"/>   
                    <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                    <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="<%=path%>/property/ajaxGetPropertyPunishmentChoose.json?1=1&flowdefid=${flowdefid}" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
         	  <div type="indexcolumn" width="20" headerAlign="center">序号</div>
              <div field="crimid" width="50" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="50" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="maincase" width="60" headerAlign="center" align="center"  allowSort="false">罪名</div>
              <div field="judgedate" width="50" headerAlign="center" align="center" allowSort="false">判决日期</div>
              <div field="crimedate" width="50" headerAlign="center" align="center" allowSort="false">犯罪时间</div>
              <div field="punishmenteight" width="50" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer2">是否刑八</div>
              
              <!-- 用来判断系统识别，还是干警认定 -->
              <div field="teight" width="0" headerAlign="center" align="center" allowSort="false">是</div>
              
              <div field="pprovince" width="50" headerAlign="center" align="center" allowSort="false">原工作单位</div>
              <div field="duty" width="50" headerAlign="center" align="center" allowSort="false">原职务</div>
              <div field="underworld" width="50" headerAlign="center" align="center" allowSort="false" >三类犯类型</div>
              <div field="sanclassstatus" width="50" headerAlign="center" align="center" allowSort="false" visible="false">三类犯类型</div>
              <div field="importantstatus" width="50" headerAlign="center" align="center" renderer="onActionRenderer1" allowSort="false">重要罪犯类型</div>
              <div field="caseothertype" width="50" headerAlign="center" align="center" renderer="onActionRenderer1" allowSort="false">其他罪犯类型</div>
              <div field="whereto" width="50" headerAlign="center" align="center"  allowSort="false">何处调来</div>
              <div id="action" id="action" width="300" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    
    
    <!-- 财产刑 -->
	<div id="propertyEditWindow" class="mini-window" title="财产刑" style="width:500px;height:300px" showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="propertyEditForm" class="form" style="min-height: 150px;" >
				<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 100%;">
								<a class="mini-button" iconCls="icon-save" plain="true" onclick="SaveData('property')" style="width: 60px">存盘</a>
								<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel('property');" style="width: 60px">关闭</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-left: 0px; padding-bottom: 0px;">
					<table style="table-layout:fixed;" border="0">
						<tr >
							<td style="width:60px;">罪犯编号</td>
							<td style="width:165px;">
								<input id="crimid" name="crimid" class="mini-textbox" value="" enabled="false" />
							</td>
					        <td style="width:75px;">罪犯姓名</td>
							<td style="width:130px;">    
								<input name="name" class="mini-textbox" value="" enabled="false" />
							</td>
						</tr>
						<tr >
							<td style="width:60px;">罚&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;金</td>
							<td style="width:165px;">
								<input name="forfeit" class="mini-spinner"  minValue="0" maxValue="999999999999" format="#,.00" value="" />
							</td>
					        <td style="width:75px;">履行罚金</td>
							<td style="width:130px;">    
								<input name="payment" class="mini-spinner"  minValue="0" maxValue="999999999999" format="#,.00" value=""/>
							</td>
						</tr>
						<tr >
							<td style="width:60px;">赔&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;偿</td>
							<td style="width:165px;">
								<input name="compensation" class="mini-spinner"  minValue="0" maxValue="999999999999" format="#,.00" value="" />
							</td>
					        <td style="width:75px;">履行赔偿</td>
							<td style="width:130px;">    
								<input name="fulfilcompensation" class="mini-spinner"  minValue="0" maxValue="999999999999" format="#,.00" value=""/>
							</td>
						</tr>
						<tr >
							<td style="width:60px;">赃&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;款</td>
							<td style="width:165px;">
								<input name="stolenmoney" class="mini-spinner"  minValue="0" maxValue="999999999999" format="#,.00" value=""/>
							</td>
					        <td style="width:75px;">退回赃款</td>
							<td style="width:130px;">    
								<input name="returnloot" class="mini-spinner"  minValue="0" maxValue="999999999999" format="#,.00" value=""/>
							</td>
						</tr>
						<tr >
							<td style="width:60px;">没收财产</td>
							<td style="width:165px;">
								<input name="forfeitureproperty" class="mini-spinner" minValue="0" maxValue="999999999999" format="#,.00" value=""/>
							</td>
					        <td style="width:75px;">履行没收财产</td>
							<td style="width:130px;">
								<input name="expropriation" class="mini-spinner"  minValue="0" maxValue="999999999999" format="#,.00" value=""/>
							</td>
						</tr>
						<tr>
							<td colspan="4"><font color="red">说明：金额单位为元.</font></td>
						</tr>																				
            		</table>
				</div>
		   </div>
	</div>	
	<!-- 三类犯类型 -->
	<div id="threeTypeEditWindow" class="mini-window" title="三类犯类型" style="width:600px;height:400px" showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="threeTypeEditForm" class="form" style="min-height: 150px;" >
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" iconCls="icon-save" plain="true" onclick="SaveData('three')" style="width: 60px">存盘</a>
							<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel('three');" style="width: 60px">关闭</a>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left: 0px; padding-bottom: 0px;">
				<table style="table-layout:fixed;" border="0">
					<tr >
						<td style="width:60px;">罪犯编号</td>
						<td style="width:165px;">
							<input id="crimid" name="crimid" class="mini-textbox" value="" enabled="false"/>
						</td>
				        <td style="width:60px;">罪犯姓名</td>
						<td style="width:130px;">    
							<input name="name" class="mini-textbox" value="" enabled="false" />
						</td>
					</tr>
					<tr >
						<td style="width:60px;">罪名</td>
						<td style="width:165px;">
							<input name="maincase" class="mini-textbox" value="" enabled="false"/>
					    </td>
					</tr>
					<tr>
					    <td style="width:60px;">原工作单位</td>
						<td style="width:130px;">    
							<input name="pprovince" class="mini-textarea" value="" style="" />
						</td>
						<td style="width:60px;">原职务</td>
						<td style="width:130px;">    
							<input name="duty" class="mini-textarea" value="" style=""/>
						</td>
					</tr>
					<tr>
					    <td style="width:60px;">主要类型</td>
						<td style="width:130px;">    
							<input name="sanclassstatus"  id="select1" class="mini-treeselect" multiSelect="true" 
							        textField="name" valueField="codeid" parentField="pcodeid" checkRecursive="true" 
							        showFolderCheckBox="false"  expandOnLoad="true" showClose="true" oncloseclick="onCloseClick"
        							popupWidth="200" url="<%=path %>/getSanLeiType.json?1=1" />
						</td>
<!-- 						<td style="width:60px;">附加类型</td> -->
<!-- 						<td style="width:130px;">     -->
<!-- 							<input name="fujialeixing" class="mini-combobox" multiSelect="true" emptyText="请选择..." showNullItem="true" nullItemText="请选择..." value="" /> -->
<!-- 						</td> -->
					</tr>
					<tr >
						<td style="width:60px;">认定依据</td>
						<td  colspan="3">
							<input name="sanremark" class="mini-textarea" value="" style="width:360px;height:80px;"/>
						</td>
					</tr>																			
           		</table>
			</div>
	    </div>
    </div>	   
	<!-- 重要罪犯类型 -->
	<div id="importantEditWindow" class="mini-window" title="重要罪犯类型" style="width:500px;height:300px" showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="importantEditForm" class="form" style="min-height: 150px;" >
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" iconCls="icon-save" plain="true" onclick="SaveData('important')" style="width: 60px">存盘</a>
							<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel('important');" style="width: 60px">关闭</a>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left: 0px; padding-bottom: 0px;">
				<table style="table-layout:fixed;" border="0">
					<tr >
						<td style="width:60px;">罪犯编号</td>
						<td style="width:165px;">
							<input id="crimid" name="crimid" class="mini-textbox" value="" enabled="false" />
						</td>
				        <td style="width:60px;">罪犯姓名</td>
						<td style="width:130px;">    
							<input name="name" class="mini-textbox" value="" enabled="false" />
						</td>
					</tr>
					<tr >
						<td style="width:60px;">罪名</td>
						<td style="width:165px;">
							<input name="maincase" class="mini-textbox" value="" enabled="false"/>
					    </td>
					</tr>
					<tr >
						<td style="width:60px;">重要罪犯</td>
						<td style="width:260px;" colspan="3">    
							<input id="importantstatus" name="importantstatus" style="width:260px;" class="mini-combobox" emptyText="请选择..." showNullItem="true" nullItemText="请选择..."
									valueField="codeid" textField="name" multiSelect="true" url="<%=path %>/getChinaCodeid.json?1=1&codeType=GKZY001" />
							
						</td>
					</tr>																			
           		</table>
			</div>
	    </div>
    </div>	   
    <!-- 何处调来 -->
	<div id="adjustEditWindow" class="mini-window" title="何处调来" style="width:500px;height:300px" showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="adjustEditForm" class="form" style="min-height: 150px;" >
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" iconCls="icon-save" plain="true" onclick="SaveData('adjust')" style="width: 60px">存盘</a>
							<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel('adjust');" style="width: 60px">关闭</a>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left: 0px; padding-bottom: 0px;">
				<table style="table-layout:fixed;" border="0">
					<tr >
						<td style="width:60px;">罪犯编号</td>
						<td style="width:130px;">
							<input id="crimid" name="crimid" class="mini-textbox" value="" enabled="false" />
						</td>
				        <td style="width:60px;">罪犯姓名</td>
						<td style="width:130px;">    
							<input name="name" class="mini-textbox" value="" enabled="false" />
						</td>
					</tr>
					<tr >
						<td style="width:60px;">入监时间</td>
						<td style="width:130px;" align="center">
							<input name="inprisondate" class="mini-textbox" showNullItem="true" value="" enabled="false"/>
					    </td>
				        <td style="width:60px;">何处调来</td>
						<td style="width:130px;">    
							<input name="whereto" class="mini-textbox"  showNullItem="true"  value="" />
						</td>
					</tr>
            	</table>
			</div>
	    </div>
    </div>	
    
    <!-- 刑八认定 -->
	<div id="xbEditWindow" class="mini-window" title="刑八认定" style="width:500px;height:300px" showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="xbEditForm" class="form" style="min-height: 150px;" >
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" iconCls="icon-save" plain="true" onclick="SaveData('xingba')" style="width: 60px">存盘</a>
							<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel('xingba');" style="width: 60px">关闭</a>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left: 0px; padding-bottom: 0px;">
				<table style="table-layout:fixed;" border="0">
					<tr >
						<td style="width:60px;">罪犯编号</td>
						<td style="width:130px;">
							<input id="crimid" name="crimid" class="mini-textbox" value="" enabled="false" />
						</td>
				        <td style="width:60px;">罪犯姓名</td>
						<td style="width:130px;">    
							<input name="name" class="mini-textbox" value="" enabled="false" />
						</td>
					</tr>
					<tr>
				        <td style="width:60px;">判决日期</td>
						<td style="width:130px;">    
							<input name="judgedate" class="mini-textbox"  showNullItem="true"  value="" enabled="false"/>
						</td>
						<td style="width:60px;">犯罪时间</td>
						<td style="width:130px;" align="center">
							<input name="crimedate" class="mini-textbox" showNullItem="true" value="" enabled="false"/>
					    </td>
					</tr>
					<tr >
						<td style="width:60px;">刑八认定</td>
						<td style="width:130px;" align="left">
							<input class="mini-checkbox"  id="teight" name="teight" text=""/>
							<!-- onvaluechanged="onValueChanged" -->
					    </td>
					</tr>
            	</table>
			</div>
	    </div>
    </div>	 
    
    <!-- 其他罪犯类型 -->
	<div id="otherEditWindow" class="mini-window" title="其他罪犯类型" style="width:500px;height:300px" showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="otherEditForm" class="form" style="min-height: 150px;" >
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" iconCls="icon-save" plain="true" onclick="SaveData('other')" style="width: 60px">存盘</a>
							<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel('other');" style="width: 60px">关闭</a>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left: 0px; padding-bottom: 0px;">
				<table style="table-layout:fixed;" border="0">
					<tr >
						<td style="width:60px;">罪犯编号</td>
						<td style="width:165px;">
							<input id="crimid" name="crimid" class="mini-textbox" value="" enabled="false" />
						</td>
				        <td style="width:60px;">罪犯姓名</td>
						<td style="width:130px;">    
							<input name="name" class="mini-textbox" value="" enabled="false" />
						</td>
					</tr>
					<tr >
						<td style="width:60px;">罪名</td>
						<td style="width:165px;">
							<input name="maincase" class="mini-textbox" value="" enabled="false"/>
					    </td>
					</tr>
					<tr >
						<td style="width:60px;">其他类型</td>
						<td style="width:260px;" colspan="3">    
							<input id="otherstatus" name="otherstatus" style="width:260px;" class="mini-combobox" emptyText="请选择..." showNullItem="true" nullItemText="请选择..."
									valueField="codeid" textField="name" multiSelect="true" url="<%=path %>/getChinaCodeid.json?1=1&codeType=GKQT001" />
							
						</td>
					</tr>																			
           		</table>
			</div>
	    </div>
    </div>	 
    
     <!-- 部门调动 -->
	<div id="orgchangeEditWindow" class="mini-window" title="部门调动" style="width:500px;height:300px" showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="orgchangeEditForm" class="form" style="min-height: 150px;" >
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" iconCls="icon-save" plain="true" onclick="SaveData('orgchange')" style="width: 60px">存盘</a>
							<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel('orgchange');" style="width: 60px">关闭</a>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left: 0px; padding-bottom: 0px;">
				<table style="table-layout:fixed;" border="0">
					<tr >
						<td style="width:60px;">罪犯编号</td>
						<td style="width:165px;">
							<input id="crimid" name="crimid" class="mini-textbox" value="" enabled="false" />
						</td>
				        <td style="width:60px;">罪犯姓名</td>
						<td style="width:130px;">    
							<input name="name" class="mini-textbox" value="" enabled="false" />
						</td>
					</tr>
					<tr >
					    <td style="width:60px;">原部门</td>
					    <td style="width:130px;">    
							<input name="orgname" class="mini-textbox" value="" enabled="false" />
						</td>
					</tr>
					<tr >
						<td style="width:60px;">监区</td>
						<td style="width:165px;">
						        
						<input id="deptCombo" name="org1" class="mini-combobox" url="/pmsystem/org/ajax/getOrganizationByPorgid.action?1=1&unitlevel=4"  onvaluechanged="onDeptChanged"  
						        textField="name" valueField="dorgid" showNullItem="true" nullItemText="--全部--"
						    	 style="width:150px;" enabled="true" emptyText="请选择部门..."/>
					    </td>
					</tr>
					<tr >
						<td style="width:60px;">分监区</td>
						<td style="width:260px;" colspan="3">    
							<input id="positionCombo" name="org2" class="mini-combobox" style="width:150px;" textField="name" valueField="dorgid" />         
						</td>
					</tr>																			
           		</table>
			</div>
	    </div>
    </div>	        

   
    <script type="text/javascript">
    	var threetypearray = [{id:"1",text:"金融诈骗"},{id:"2",text:"黑社会性质"},{id:"3",text:"职务犯罪"}];
    	//渲染三类犯类型
        function onGenderRenderer(e) {
        	var ISMENU_TEXT = [{id:"1",text:"金融诈骗"},{id:"2",text:"黑社会性质"},{id:"3",text:"职务犯罪"}];
            for (var i = 0, l = ISMENU_TEXT.length; i < l; i++) {
                var g = ISMENU_TEXT[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        };
        
        

        function onDeptChanged(e) {
        	var deptCombo = mini.get("deptCombo");
            var positionCombo = mini.get("positionCombo");
            var id = deptCombo.getValue();
            positionCombo.setValue("");
            var url = "/pmsystem/org/ajax/getOrganizationByPorgid.action?1=1&unitlevel=11&porgid=" + id;
            positionCombo.setUrl(url);
            positionCombo.select(0);
        }
        
        //刑八字体换成红色
        function  onActionRenderer2(e){
        	var punishmenteight = e.value;
        	var s = "";
        	if(punishmenteight.indexOf("是") > -1){
        		s = '<span style="color:red;">'+punishmenteight+'</span>';
        	}else{
        		s = '<span style="">否</span>';
        	}
        	return s;
        }
        
        mini.parse();		
        var propertyWindow = mini.get("propertyEditWindow");//财产刑
        var propertyForm = new mini.Form("propertyEditForm");
        var threeTypeWindow = mini.get("threeTypeEditWindow");//三类犯
        var threeTypeForm = new mini.Form("threeTypeEditForm");
        var adjustWindow = mini.get("adjustEditWindow");//调狱信息
        var adjustForm = new mini.Form("adjustEditForm");
        var importantWindow = mini.get("importantEditWindow"); //重要罪犯
        var importantForm = new mini.Form("importantEditForm");
        var otherWindow = mini.get("otherEditWindow"); //其他罪犯类型
        var otherForm = new mini.Form("otherEditForm");
        var orgchangeWindow = mini.get("orgchangeEditWindow"); //部门调动
        var orgchangeForm = new mini.Form("orgchangeEditForm");
        

        var xbeditwindow = mini.get("xbEditWindow"); //刑八
        var xbeditform = new mini.Form("xbEditForm");
        
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid","desc");
        grid.load();

		function onActionRenderer() {
	  		var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
			return s;
	    }
	    
	    
	    //根据类型显示弹出窗口，显示数据
	    function editData(type){
	    	var row = grid.getSelected();
			if(row){
				var flag = getJxJsCase(row.crimid);
				if(!flag){alert("《"+row.name+"》已经开始办理、无法再操作!");return;}
				var data = row || {};
				if(type=='property'){//财产刑
					propertyWindow.show();
					propertyForm.setData(data);
					propertyForm.setIsValid(true);
					propertyForm.setChanged(false);
				}else if(type=='three'){//三类犯
					threeTypeWindow.show();
					threeTypeForm.setData(data);
					threeTypeForm.setIsValid(true);
					threeTypeForm.setChanged(false);
				}else if(type=='adjust'){//何处调来
					adjustWindow.show();
					adjustForm.setData(data);
					adjustForm.setIsValid(true);
					adjustForm.setChanged(false);
				}else if(type=='important'){//重要罪犯
					importantWindow.show();
					importantForm.setData(data);
					importantForm.setIsValid(true);
					importantForm.setChanged(false);
				}else if(type=='xingba'){//重要罪犯
					xbeditwindow.show();
					xbeditform.setData(data);
					xbeditform.setIsValid(true);
					xbeditform.setChanged(false);
				}else if(type=='other'){//其他罪犯类型
					otherWindow.show();
					otherForm.setData(data);
					otherForm.setIsValid(true);
					otherForm.setChanged(false);
				}else if(type=='orgchange'){//部门调动
					orgchangeWindow.show();
					orgchangeForm.setData(data);
					orgchangeForm.setIsValid(true);
					orgchangeForm.setChanged(false);
				}
		        form.unmask();
	    	}else{
        		alert(confirmMessage);
        	}
	    } 
	    //根据类型隐藏弹框
	    function onCancel(type) {
    		if(type=='property') propertyWindow.hide();//财产刑
			else if(type=='three') threeTypeWindow.hide();//三类犯
			else if(type=='adjust') adjustWindow.hide();//何处调来\
			else if(type=='important') importantWindow.hide(); //重要罪犯
			else if(type=='xingba') xbeditwindow.hide(); //重要罪犯
			else if(type=='other') otherWindow.hide(); //其他罪犯类型
			else if(type=='orgchange') orgchangeWindow.hide(); //其他罪犯类型
			grid.load();
		}    
		
		function SaveData(type){
			var o = null;
			var opaction = null;
			var row = grid.getSelected();
       		//var nodeid = row.nodeid;
       		var crimid = row.crimid;
			if(type=='property'){//财产刑
				opaction = "财产刑信息";
				o = propertyForm.getData();
				propertyForm.validate();
	        	if (propertyForm.isValid() == false) return; 
       			//if(nodeid=='1') {alert("你所选罪犯的基本信息审批已通过，财产刑不能修改！");return;}
			}else if(type=='three'){//三类犯
				opaction = "三类犯信息";
				o = threeTypeForm.getData();
				propertyForm.validate();
	        	if (propertyForm.isValid() == false) return;
			}else if(type=='adjust'){//何处调来
				opaction = "何处调来";
				o = adjustForm.getData();
				propertyForm.validate();
	        	if (propertyForm.isValid() == false) return;
			}else if(type == 'important'){
				opaction = "重案犯";
				o = importantForm.getData();
				importantForm.validate();
	        	if (importantForm.isValid() == false) return;
			}else if(type == 'xingba'){
				opaction = "刑八罪犯";
				o = xbeditform.getData();
				xbeditform.validate();
	        	if (xbeditform.isValid() == false) return;
			}else if(type == 'other'){
				opaction = "其他罪犯类型";
				o = otherForm.getData();
				otherForm.validate();
	        	if (otherForm.isValid() == false) return;
			}else if(type == 'orgchange'){
				opaction = "部门调动";
				o = orgchangeForm.getData();
				orgchangeForm.validate();
	        	if (orgchangeForm.isValid() == false) return;
			}
        	var json = mini.encode([o]);
			$.ajax({
                url: "<%=path%>/property/saveBaseCrime.json?1=1",
                data: {data:json, crimid:crimid,opaction:opaction,type:type},
                type: "post",
                dataType:"text",
                async:false,
                success: function (text) {
                	alert("操作成功!");
                	onCancel(type);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
			
		}
        
		//判断案件该罪犯是否已经正在进行案件办理 如果正在进行那么就不能操作
		function getJxJsCase(crimid){
			var nodeid = -1;
			$.ajax({
				url:'<%=path%>/flow/isDealJxjsCase.json',
				data:{crimid:crimid, flowdefid:'other_zfjyjxjssp'},
				type:'post',
				dataType:'json',
				async:false,
				success:function (text){
					nodeid = parseInt(text);
				}
			});
			if(nodeid == 0){
			    return true;
			}
			return false;
		}
		
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        function search() {
        	var fzsj = "";
        	var pjsj = "";
            var key = mini.get("key").getValue();
            //犯罪开始时间--结束时间
            var startfzsj = mini.get("startfzsj").getValue();
            var endfzsj = mini.get("endfzsj").getValue();
            
            //裁定开始时间--结束时间
            var startpjsj = mini.get("startpjsj").getValue();
            var endpjsj = mini.get("endpjsj").getValue();
            
            if(startfzsj){
            	startfzsj = startfzsj.getFullYear()+"-"+(startfzsj.getMonth()+1)+"-"+startfzsj.getDate();
            	fzsj = fzsj+"e.judgedate >= to_date('"+startfzsj+"','yyyy/MM/dd')";
            }
            if(endfzsj){
            	endfzsj = endfzsj.getFullYear()+"-"+(endfzsj.getMonth()+1)+"-"+endfzsj.getDate();
            	if(fzsj == ""){
            		fzsj = fzsj+" e.judgedate <= to_date('"+endfzsj+"','yyyy/MM/dd')";
            	}else{
            		fzsj = fzsj+" and e.judgedate <= to_date('"+endfzsj+"','yyyy/MM/dd')";
            	}
            }
            
            if(startpjsj){
            	startpjsj = startpjsj.getFullYear()+"-"+(startpjsj.getMonth()+1)+"-"+startpjsj.getDate();
            	pjsj = pjsj+"e.crimedate >= to_date('"+startpjsj+"','yyyy/MM/dd')";
            }
            if(endpjsj){
            	endpjsj = endpjsj.getFullYear()+"-"+(endpjsj.getMonth()+1)+"-"+endpjsj.getDate();
            	if(pjsj == ""){
            		pjsj = pjsj+" e.crimedate <= to_date('"+endpjsj+"','yyyy/MM/dd')";
            	}else{
            		pjsj = pjsj+" and e.crimedate <= to_date('"+endpjsj+"','yyyy/MM/dd')";
            	}
            }
            
        	grid.sortBy("crimid","desc");
            grid.load({key:key,fzsj:fzsj,pjsj:pjsj});
        }
        
		//信息补录
        function chooseOne(menuid) {
	       	var tempid = mini.get("tempid").getValue();
       		var url = "<%=path%>/property/basicInformationbl.page?1=1"+"&menuid="+menuid+"&fathermenuid=${menuid}"+"&flowdefid=${flowdefid}";
			self.location.href=url;
        }
    </script>    
</body>  
</html>