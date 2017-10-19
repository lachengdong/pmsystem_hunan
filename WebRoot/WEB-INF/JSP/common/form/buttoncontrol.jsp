<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
	String path = request.getContextPath();
%>
<script type="text/javascript">
   	var confirmMessage = "请选中一条记录！";
   	var confirmMessages = "请至少选中一条记录！";
   	var allProcessing = "确定操作 选中记录？";
   	var noProcessing = "您选中的记录不能进行批量处理！";
   	var deleteConfirmMessage = "确定删除此记录吗?";
</script>
<script type="text/javascript">
	function init(resid){
		$.ajax({
		        url: '<%=path%>/public/getresidlist.action?1=1',
		        data: {resid:resid},
		        type: "post",
		        async: false,
		        success: function (text) {
		            var obj = eval(text);
		            var s="";
		            var toparr = [];
		            for(var i=0;i<obj.length;i++){
		            	var arr = obj[i].split("@");
		            	toparr.push(arr[0]);
		            	if(arr[2] == 13){//列中按钮 类型为13
		            		s +=" <a class=\"Edit_Button\" id=\""+arr[0]+"\" href=\"javascript:"+arr[3]+"\" >"+arr[1]+"</a>";
		            	}else{//是否拥有显示权限
		            		//document.getElementsById(arr[0]).style.display="block";
		            		$("#"+arr[0]).show(); 
			           	}
		            }
		            //列中按钮
		          	mini.get("middlebtns").setValue(s);
		           //
		        }
	      });  
	}	
	//渲染日期
	function onDateRenderer(e) {
		if(e && e.value){
			return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
		}
	    return "";
	}
	function myloading(){
		var myform = new mini.Form("div_two");
		myform.loading();
	}
	function myunmask() {
		var myform = new mini.Form("div_two");
		myform.unmask();
	}
	function onPageChanged() {
		myloading();
	}
	function unmask(){
		myunmask();
	}
</script>
<input id="middlebtns" name="middlebtns" class="mini-hidden" value="${middlebtns}"/>
<input id="id" name="id" class="mini-hidden" value="${id}"/><!-- 批量处理时所选罪犯编号拼接的数组-->
<input name="tempid" id="tempid" class="mini-hidden" value="${tempid}"/><!-- 模版ID -->
<input name="solutionid" id="solutionid" class="mini-hidden" value="${solutionid}"/><!-- 查询方案ID -->
<input id="params" name="params" class="mini-hidden" value='${params}'/><!-- 参数串 ?? -->
<input name="menuid" id="menuid" class="mini-hidden" value="${menuid}"/><!-- 菜单ID -->
<input name="fathermenuid" id="fathermenuid" class="mini-hidden" value="${fathermenuid}"/><!-- 菜单的父级Id,全小写 -->
<input name="docid" id="docid" class="mini-hidden" value="${docid}"/><!-- 系统文书信息表ID，非流程保存使用-->
<input name="orgid" id="orgid" class="mini-hidden" value="${orgid}"/><!-- 用户所属部门ID，罪犯所在部门ID-->
<input id="operator" name="operator" class="mini-hidden" value="${operator}"/><!-- 操作类型   新增、修改 -->
<input id="crimid" name="crimid" class="mini-hidden" value="${crimid}"/>
<input id="name" name="name" class="mini-hidden" value="${name}"/>
<input id="applyid" name="applyid" class="mini-hidden" value="${applyid}"/>
<input id="applyname" name="applyname" class="mini-hidden" value="${applyname}"/>
<input id="flowdraftid" name="flowdraftid" class="mini-hidden" value="${flowdraftid}"/>
<input id="flowid" name="flowid" class="mini-hidden" value="${flowid}"/>
<input id="flowdefid" name="flowdefid" class="mini-hidden"  value="${flowdefid}"/>