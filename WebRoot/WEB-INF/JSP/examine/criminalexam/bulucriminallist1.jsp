<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.sinog2c.model.system.SystemResource" %>
<%@ page import="com.sinog2c.util.common.StringNumberUtil" %>
<%
	String path = request.getContextPath();
	int startyear = Integer.parseInt(request.getAttribute("startyear").toString());
	int endyear = Integer.parseInt(request.getAttribute("endyear").toString());
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>考核成绩补录</title>
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
<body>
   <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
   		<input id="indexFlag" name="indexFlag" class="mini-hidden" value="${indexFlag}"/>
   		<input id="id" name="id" class="mini-hidden" value="${id}"/>
   		<input id="menuid" name="menuid" class="mini-hidden" value="${menuid}"/>
   		<input id="fatherMenuid" name="fatherMenuid" class="mini-hidden" value="${fatherMenuid}"/>
          <table style="width:100%;">
              <tr>
              	  <td style="width:40%;">
	                  	<a class="mini-button" iconCls="icon-add" id="" plain="true" onclick="add()">新增</a>
	                  	<a class="mini-button" iconCls="icon-undo" plain="true" onclick="goBack()">返回</a>
	                  	<a class="mini-button" iconCls="icon-upload" plain="true" onclick="toLast();">上一个</a>
	                  	<a class="mini-button" iconCls="icon-download" plain="true" onclick="saveNext();">下一个</a>
                  </td>
                  <td style="width:60%;">
                  		服刑人员&nbsp;【${name}】&nbsp;历年计分考核信息
                  </td>
                  <td style="white-space:nowrap;">
	                  	${topsearch}
	               		<!-- 操作说明 -->
	                	<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a> 
                  </td>
              </tr>
          </table>           
    </div>
    
    <div class="mini-fit" id="div_two">
	    <div id="datagrid1" class="mini-datagrid"  allowMoveColumn="false"  style="width:100%;height:100%;"   
	        url="<%=path%>/jifen/getKaoHeBuLuList.action?1=1&crimid=${crimid}"  idField="" allowCellEdit="true" 
	        allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true"  
	        onshowrowdetail="onShowRowDetail" allowAlternating="true"  sizeList="[10,20,50,100]" pageSize="20">
	        <div property="columns">
	        	<div type="indexcolumn" width="20" headerAlign="center">序号</div>
	        	<div type="expandcolumn" >计分考核明细</div>
	        	<div field="khyear" width="30" align="center" headerAlign="center" allowSort="true">年份</div> 
	        	<div field="yufenscore" width="30" align="center" headerAlign="center" allowSort="true">有效剩余分</div> 
	        	<div field="yeartotal" width="30" align="center" headerAlign="center" allowSort="true">当年总分</div> 
	            <div field="yearkaohetotal" width="30" align="center" headerAlign="center" allowSort="true">当年考核分</div> 
	            <div field="yearjianglitotal" width="30" align="center" headerAlign="center" allowSort="true">当年奖分</div>
	            <div field="yearchengfatotal" width="30" align="center" headerAlign="center" allowSort="true">当年扣分</div>
	            <div field="remark" width="100" align="left" headerAlign="center" allowSort="true">备注</div>
	            <div id="caozuo1" width="50" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>
		    </div>
		</div>
		
		<div id="detailGrid_Form" style="display:none;">
	        <div id="employee_grid" style="padding-left: 20px; padding-bottom: 0px;">
				<table style="table-layout:fixed;" border="0">
					<tr align="center">
						<td align="center">月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份</td>
						<td >1月</td>
						<td >2月</td>
						<td >3月</td>
						<td >4月</td>
						<td >5月</td>
						<td >6月</td>
						<td >7月</td>
						<td >8月</td>
						<td >9月</td>
						<td >10月</td>
						<td >11月</td>
						<td >12月</td>
						<td  colspan="6" align="center">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</td>
					</tr>
					<tr align="center">
						<td align="center">考核成绩</td>
						<td ><input class="mini-textbox" id="khscore01" name="khscore01" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore02" name="khscore02" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore03" name="khscore03" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore04" name="khscore04" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore05" name="khscore05" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore06" name="khscore06" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore07" name="khscore07" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore08" name="khscore08" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore09" name="khscore09" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore10" name="khscore10" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore11" name="khscore11" vtype="float" value="" style="width:40px" /></td>
						<td ><input class="mini-textbox" id="khscore12" name="khscore12" vtype="float" value="" style="width:40px" /></td>
						
						<td rowspan="3" colspan="6">
							<input name="remark" class="mini-textarea" value="" vtype="maxLength:400" style="width:440px;height:74px;"/>
						</td>
						
						
					 </tr>
					 <tr align="center">
						<td align="center">奖&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</td>
						<td ><input class="mini-textbox" id="jfscore01" name="jfscore01" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore02" name="jfscore02" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore03" name="jfscore03" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore04" name="jfscore04" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore05" name="jfscore05" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore06" name="jfscore06" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore07" name="jfscore07" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore08" name="jfscore08" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore09" name="jfscore09" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore10" name="jfscore10" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore11" name="jfscore11" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="jfscore12" name="jfscore12" vtype="float" style="width:40px" value="" /></td>
					 </tr>
					 <tr align="center">
						<td align="center">扣&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</td>
						<td ><input class="mini-textbox" id="kfscore01" name="kfscore01" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore02" name="kfscore02" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore03" name="kfscore03" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore04" name="kfscore04" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore05" name="kfscore05" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore06" name="kfscore06" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore07" name="kfscore07" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore08" name="kfscore08" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore09" name="kfscore09" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore10" name="kfscore10" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore11" name="kfscore11" vtype="float" style="width:40px" value="" /></td>
						<td ><input class="mini-textbox" id="kfscore12" name="kfscore12" vtype="float" style="width:40px" value="" /></td>
					 </tr>
            	</table>
			</div>   
	    </div>  <!-- end of detailGrid_Form -->
	    
	</div> <!-- end of div_two -->
	
	
	<div id="assessmentEditWindow" class="mini-window" title="考核成绩补录" style="width:690px;height:360px" 
			showModal="true" allowResize="false" allowDrag="true" showMaxButton="true">
		<div id="assessmentEditForm" class="form" style="min-height: 150px;" >
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;margin: -5px -5px 5px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" id="addSave" iconCls="icon-save" plain="true" onclick="SaveData('add')" style="width: 60px">保存</a>
							<a class="mini-button" id="editSave" iconCls="icon-save" plain="true" onclick="SaveData('edit')" style="width: 60px">保存</a>
							<a class="mini-button" iconCls="icon-close" plain="true" onclick="onCancel();" style="width: 60px">关闭</a>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left: 20px; padding-bottom: 0px;">
				<table style="table-layout:fixed;" border="0">
					<tr >
						<td colspan="18" >
							有效剩余<input class="mini-textbox" id="yufenscore" name="yufenscore" style="width:40px;border-style:none;outline:medium;" />。（
							<select id="selectyear" name="selectyear" class="selector">
                                <%
	                                for (int i=endyear;i>=startyear;i--) {
	                                	if(i==endyear){
	                                		%><option value="<%=i%>" selected><%=i%></option><%
	                                	} else{
	                                		%><option value="<%=i%>" ><%=i%></option><%
	                                	} 
	                                }
                                %>
                            </select>年,
							总分<input class="mini-textbox" id="yeartotal" name="yeartotal" style="width:40px;outline:medium;" />分。
							其中:考核总分<input class="mini-textbox" id="yearkaohetotal" name="yearkaohetotal" style="width:40px" />分,
							奖励总分<input class="mini-textbox" id="yearjianglitotal" name="yearjianglitotal" style="width:40px;" />分,
							扣除总分<input class="mini-textbox" id="yearchengfatotal" name="yearchengfatotal" style="width:40px;" />分） 
						</td>
					</tr>
					<tr align="center">
						<td align="center">月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份</td>
						<td >1月</td>
						<td >2月</td>
						<td >3月</td>
						<td >4月</td>
						<td >5月</td>
						<td >6月</td>
						<td >7月</td>
						<td >8月</td>
						<td >9月</td>
						<td >10月</td>
						<td >11月</td>
						<td >12月</td>
					</tr>
					<tr align="center">
						<td align="center">考核成绩</td>
						<td ><input class="mini-textbox" id="khscore01" name="khscore01" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore02" name="khscore02" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore03" name="khscore03" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore04" name="khscore04" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore05" name="khscore05" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore06" name="khscore06" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore07" name="khscore07" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore08" name="khscore08" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore09" name="khscore09" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore10" name="khscore10" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore11" name="khscore11" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
						<td ><input class="mini-textbox" id="khscore12" name="khscore12" vtype="float" value="" style="width:40px" onvaluechanged="khscore()" /></td>
					 </tr>
					 <tr align="center">
						<td align="center">奖&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</td>
						<td ><input class="mini-textbox" id="jfscore01" name="jfscore01" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore02" name="jfscore02" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore03" name="jfscore03" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore04" name="jfscore04" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore05" name="jfscore05" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore06" name="jfscore06" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore07" name="jfscore07" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore08" name="jfscore08" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore09" name="jfscore09" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore10" name="jfscore10" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore11" name="jfscore11" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
						<td ><input class="mini-textbox" id="jfscore12" name="jfscore12" vtype="float" style="width:40px" value="" onvaluechanged="jfscore()" /></td>
					 </tr>
					 <tr align="center">
						<td align="center">扣&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分</td>
						<td ><input class="mini-textbox" id="kfscore01" name="kfscore01" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore02" name="kfscore02" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore03" name="kfscore03" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore04" name="kfscore04" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore05" name="kfscore05" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore06" name="kfscore06" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore07" name="kfscore07" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore08" name="kfscore08" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore09" name="kfscore09" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore10" name="kfscore10" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore11" name="kfscore11" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
						<td ><input class="mini-textbox" id="kfscore12" name="kfscore12" vtype="float" style="width:40px" value="" onvaluechanged="kfscore()" /></td>
					 </tr>
					 <tr align="center">
						<td align="center">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</td>
						<td rowspan="3" colspan="12">
							<input name="remark" class="mini-textarea" value="" vtype="maxLength:400" style="width:580px;height:90px;"/>
						</td>					 
					 </tr>
            	</table>
			</div>
			
	    </div> <!-- End of assessmentEditForm -->
    </div><!-- End of assessmentEditWindow -->
	
    <script type="text/javascript">
       	mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        grid.sortBy("khyear", "desc");
        
      //全程留痕
      var employee_grid = mini.get("employee_grid");
        var detailGrid_Form = document.getElementById("detailGrid_Form");
        
        var employee_gridForm = new mini.Form("employee_grid");
        function onShowRowDetail(e) {
        	 var grid = e.sender;
             var row = e.record;
             var td = grid.getRowDetailCellEl(row);
             td.appendChild(detailGrid_Form);
            detailGrid_Form.style.display = "block";
            var row = grid.getSelected();
            if(row){
				var data = row || {};
				employee_gridForm.setData(data);
	    	}
        }
        
        
        
        var assessmentWindow = mini.get("assessmentEditWindow");
      var assessmentForm = new mini.Form("assessmentEditForm");
        
	    //根据类型显示弹出窗口，显示数据
	   function add(){
	    	var endyear = "<%=endyear%>";
	    	mini.get("addSave").show();
	    	mini.get("editSave").hide();
	    	assessmentForm.setData();
			assessmentWindow.show();
			$(".selector").val(endyear);
	    }
	    
	    //根据类型显示弹出窗口，显示数据
	   function edit(){
	    	var row = grid.getSelected();
			if(row){
				var data = row || {};
				mini.get("addSave").hide();
		    	mini.get("editSave").show();
		    	
				assessmentWindow.show();
				assessmentForm.setData(data);
				assessmentForm.setIsValid(true);
				assessmentForm.setChanged(false);
				$(".selector").val(data.khyear);
		        form.unmask();
	    	}else{
        		alert(confirmMessage);
        	}
	    }
	    
	    //根据类型隐藏弹框
	   function onCancel() {
	    	assessmentWindow.hide();
			grid.load();
		}    
		
		function SaveData(type){
			if (!assessmentForm.isValid()) {
				return;
			}
			var obj=document.getElementById('selectyear');   
			var index=obj.selectedIndex;   
			var khyear = obj.options[index].text;
			if('add' == type){
				
				var url = "<%=path%>/jifen/judgehavekhyear.action?1=1&crimid=${crimid}"+"&khyear="+khyear;
				$.ajax({
	                url:url,
	                type: "post",
	                dataType:"text",
	                success: function (text) {
	                  	//var status= mini.decode(text);
	              	if(text=='yes'){
	                  		if (confirm("该年份成绩已存在，是否进行修改？")) {
								valueSave(khyear,"update");
				            }else{
				            	return;
				            }
	                  	}else{
	                  		valueSave(khyear,"insert");
	                  	}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                	alert("操作失败!");
	                }
	            });
				
			}else if('edit' == type){
				valueSave(khyear,"update");
			}
			
		}
		
		function valueSave(khyear,operate){
			var o = assessmentForm.getData();
			var noteinfo = mini.encode([o]);
			var url = "<%=path%>/jifen/saveKaoHeBuLu.action?1=1&&crimid=" + ${crimid};
			$.ajax({
                url: url,
                type: "post",
                dataType:"text",
                data:{noteinfo : noteinfo,operate :operate, khyear :khyear},
                success: function (text){
                	if(text == 1){
                		alert("操作成功!");
                	}else{
                		alert("操作失败!");
                	}
                	onCancel();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                	alert("操作失败!");
                }
            });
		}
		
        
		function onActionRenderer(e){
         	var s = '';
         	s += ' <a class="Edit_Button" href="javascript:edit();" >修改</a>';
         	s += ' <a class="Edit_Button" href="javascript:del();" >删除</a>';
    	    return s;
        }
		
		function goBack() {
			var url = "<%=path%>/toPublicListPage.page?1=1&menuid="+${fatherMenuid};
	        self.location.href=url;
		}
		
		//删除
      function del(){
        	var row = grid.getSelected();//获取选中记录
        if (row) {
                if (confirm("删除后数据无法恢复，确认删除？")) {
                    //grid.loading("操作中，请稍后......");
              var url = "<%=path%>/jifen/deleteKaoHeBuLu.action?1=1&crimid="+${crimid}+"&khyear="+row.khyear;
                    $.ajax({
                        url: url,
                        type: "post",
                        dataType:"text",
                        success: function (text) {
                        	if(text == 1){
                        		alert("操作成功!");
                        	}else{
                        		alert("操作失败!");
                        	}
                            grid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        }
                    });
                }
                
            }else {
                alert(confirmMessage);
            }
		}
		
		
    	//下一个
      function saveNext(){
	       	var id = mini.get("id").getValue();
	       	var indexFlag = mini.get("indexFlag").getValue();
	       	var menuid = mini.get("menuid").getValue();
	       	var fatherMenuid = mini.get("fatherMenuid").getValue();
	       	var idArr = id.split(",");
	       	indexFlag = parseInt(indexFlag);
	       	indexFlag ++;
	       	
	       	if(indexFlag < idArr.length){
	       		var url = "<%=path%>/jifen/publicBuluPage.page?1=1&id="+id+"&indexFlag="+indexFlag+"&menuid="+menuid+"&fatherMenuid="+fatherMenuid;
	            self.location.href=url;
	       	}else{
	       		//alert("操作完毕！");
	           	goBack();
	       	}
       }
    	
    //上一个
      function toLast(){
	       	var id = mini.get("id").getValue();
	       	var indexFlag = mini.get("indexFlag").getValue();
	       	var menuid = mini.get("menuid").getValue();
	       	var fatherMenuid = mini.get("fatherMenuid").getValue();
	       	var idArr = id.split(",");
	       	indexFlag = parseInt(indexFlag);
	       	indexFlag --;
	       	
	       	if(indexFlag >=0){
	       		var url = "<%=path%>/jifen/publicBuluPage.page?1=1&id="+id+"&indexFlag="+indexFlag+"&menuid="+menuid+"&fatherMenuid="+fatherMenuid;
	            self.location.href=url;
	       	}else{
	       		//alert("操作完毕！");
	       		goBack();
	       	}
         
       }
      //计算考核总分  
      function khscore(){
    	  var khscore = 0;
    	  for(var i=1;i<13;i++){
    		  var score = 0;
    		  if(i<10){
                  score = mini.get("khscore0"+i).getValue();
              }else{
                  score = mini.get("khscore"+i).getValue();
              }
    		  if(score){
    			  khscore = addNum(parseFloat(khscore),parseFloat(score)); 
    		  }   		  
    	  }
      	  mini.get("yearkaohetotal").setValue(khscore);
      	  yeartotal();
      }	  
      //计算奖分总分
      function jfscore(){
    	  var jfscore = 0;
    	  for(var i=1;i<13;i++){
    		  var score = 0;
    		  if(i<10){
                  score = mini.get("jfscore0"+i).getValue();
              }else{
                  score = mini.get("jfscore"+i).getValue();
              }
    		  if(score){
    			  jfscore = addNum(parseFloat(jfscore),parseFloat(score)); 
    		  }   		  
    	  }
      	  mini.get("yearjianglitotal").setValue(jfscore);
      	  yeartotal();
      }
      //计算扣分总分
      function kfscore(){
    	  var kfscore = 0;
    	  for(var i=1;i<13;i++){
    		  var score = 0;
    		  if(i<10){
                  score = mini.get("kfscore0"+i).getValue();
              }else{
                  score = mini.get("kfscore"+i).getValue();
              }
    		  if(score){
    			  kfscore = addNum(parseFloat(kfscore),parseFloat(score)); 
    		  }   		  
    	  }
      	  mini.get("yearchengfatotal").setValue(kfscore);
      	  yeartotal();
      }	
      //计算总分  
      function yeartotal(){
    	  var yeartotal = 0;
    	  
    	  var yearkaohetotal = mini.get("yearkaohetotal").getValue();
    	  var yearjianglitotal = mini.get("yearjianglitotal").getValue();
    	  var yearchengfatotal = mini.get("yearchengfatotal").getValue();
    	  if(!yearkaohetotal){
    		  yearkaohetotal = 0; 
		  }
    	  if(!yearjianglitotal){
    		  yearjianglitotal = 0; 
		  }
    	  if(!yearchengfatotal){
    		  yearchengfatotal = 0; 
		  }
    	  yeartotal = addNum(parseFloat(yearkaohetotal),parseFloat(yearjianglitotal)); 
    	  yeartotal = subNum(parseFloat(yeartotal),parseFloat(yearchengfatotal));
      	  mini.get("yeartotal").setValue(yeartotal);
      }	  
      //分数相加
      function addNum (num1, num2) {      
    		var sq1,sq2,m;      
    		try {          
    			sq1 = num1.toString().split(".")[1].length;      
    		}catch (e) {          
    			sq1 = 0;      
    		}      
    		try {          
    			sq2 = num2.toString().split(".")[1].length;      
    		}catch (e) {          
    			sq2 = 0;      
    		}      
    		m = Math.pow(10,Math.max(sq1, sq2));
    		return (num1 * m + num2 * m)/m; 
     }
      
		function subNum(arg1, arg2){
			var r1, r2, m, n;
			try {
				r1 = arg1.toString().split(".")[1].length;
			} catch (e) {
				r1 = 0;
			}
			try {
				r2 = arg2.toString().split(".")[1].length;
			} catch (e) {
				r2 = 0;
			}
			m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
			n = (r1 >= r2) ? r1 : r2;
			return ((arg1 * m - arg2 * m) / m).toFixed(n);
		}
		
	</script>
</body>
</html>