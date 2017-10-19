<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String realPath = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
	String path = request.getContextPath();
%>
<script type="text/javascript">
	var error = "操作失败！";
	var success = "操作成功！";
   	var confirmMessage = "请选中一条记录！";
   	var confirmMessages = "请至少选中一条记录！";
   	var allProcessing = "确定操作选中记录？";
   	var noProcessing = "您选中的记录不能进行批量处理！";
   	var noReview = "您选中的记录不能进行批量重审！";
   	var deleteConfirmMessage = "确定删除此记录吗?";
   	var batchAlertOne = "您要对以下所选服刑人员进行立案吗？";
</script>
<script src="<%=path%>/scripts/form/approve.js" type="text/javascript"></script>
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
		            		$("#"+arr[0]).show().css("display","inline-block"); //inline
			           	}
		            }
		            //列中按钮
		          	mini.get("middlebtns").setValue(s);
		           //
		        }
	      });  
	}	
		//批量重审     
	
	function chooseReview(){
		var rows = grid.getSelecteds();
		if(rows.length > 0){
          	var idnumbers = [];
            for (var i = 0, l = rows.length; i < l; i++) {
                var r = rows[i];
                var idnumber = r.idnumber;
                var lastnodeid = r.duty;
                
                if(idnumber && (lastnodeid == '1' || lastnodeid == '-1')){//
             	   idnumbers.push(idnumber);
                }
            }
            idnumbers = idnumbers.join("','");
            idnumbers="'"+idnumbers+"'";
            if (confirm(allProcessing)) {
            	if(idnumbers && idnumbers.length>0){
					 var url="<%=path%>/flow/batchReview.json?1=1";
	                 $.ajax({
	    		        url: url,
	    		        data: {idnumbers:idnumbers},
	    		        type: "post",
	    		        async: false,
	    		        success: function (text) {
	    		            var obj = text;
	    		            if(parseInt(obj)>0) alert("处理成功!");
	    		            else alert("操作失败!");
	    		            grid.reload();
	    		        }
	    	        });  
				}else{
					alert(noReview);
				}
            }   
		}else {
            alert(confirmMessages);
        }  
	}
	//渲染日期（年月日）
	function onDateRenderer(e) {
		if(e && e.value){
			return mini.formatDate ( new Date(e.value), "yyyy-MM-dd" );
		}
	    return "";
	}
	//渲染日期（年月日时分秒）
	function onMinuteDateRenderer(e) {
		if(e && e.value){
			return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:mm:ss" );
		}
	    return "";
	}
	//给审批状态的背景颜色
    function onStatusRenderer(e){
    	var record = e.record,
  		field = e.field,
  		value = e.value;
  		var s = value;
        if(value.indexOf("已通过")!= '-1'){
        	e.cellStyle = "background:green;";
        }
        if(value.indexOf("已处理")!= '-1'){
        	e.cellStyle = "background:yellowgreen;";
        }
        if(value.indexOf("未通过")!= '-1'){
        	e.cellStyle = "background:red;";
        }
        if(value.indexOf("审批中")!= '-1'){
        	e.cellStyle = "background:yellow;";
        }
        return s;
    }
    //渲染 鉴定次数
    function onRnRenderer(e){
    	var record = e.record;
    	var rn = record.rn;
    	var s = "第"+rn+"次";
    	return s;
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
	//开始、结束时间验证
   	function timeVerification(type){  
   		var starttime = mini.get("starttime").getText();
     		var endtime = mini.get("endtime").getText();
     		var startvalue = mini.get("starttime").getValue();
  	    var endvalue = mini.get("endtime").getValue();
  	    if((type=="start" || type=="all") && startvalue<new Date()){
             	alert("开始时间不能小于当前时间");
             	starttime = start;
             	if(!start) starttime = new Date();
             	mini.get("starttime").setValue(starttime);
             	return false;
          } 
          if(endvalue){
          	if((type=="end" || type=="all") && endvalue<new Date()){
             	alert("结束时间不能小于当前时间");
             	mini.get("endtime").setValue(starttime);
             	return false;
           }
    		if(starttime && endtime && starttime>endtime){
              	alert("开始时间不能大于结束时间");
              	if(type=="start" || type=="all"){
               	starttime = start;
               	if(!start) starttime = new Date();
               	mini.get("starttime").setValue(starttime);
            } 
            if(type=="end" || type=="all"){
               	mini.get("endtime").setValue("");
            }
            return false;
           }
          }
          return true;
   	}

</script>
<input id="middlebtns" name="middlebtns" class="mini-hidden" value="${middlebtns}"/>
<input id="nodeids" name="nodeids" class="mini-hidden" value="${nodeids}"/>
<input id="conent" name="conent" class="mini-hidden" /> 
<input id="snodeid" name="snodeid" class="mini-hidden" value="${snodeid}"/> 
<input id="id" name="id" class="mini-hidden" value="${id}"/><!-- 批量处理时下一个所选罪犯编号拼接的数组-->
<input id="allid" name="allid" class="mini-hidden" value="${allid}"/><!-- 批量处理时上一个所选罪犯编号拼接的数组-->
<input id="nextflag" name="nextflag" class="mini-hidden" value="${nextflag}"/><!-- 批量处理时当处理到最后一个时是否结束 用于有上一个按钮用的情况-->
<input name="tempid" id="tempid" class="mini-hidden" value="${tempid}"/><!-- 模版ID -->
<input name="solutionid" id="solutionid" class="mini-hidden" value="${solutionid}"/><!-- 查询方案ID -->
<input name="menuid" id="menuid" class="mini-hidden" value="${menuid}"/><!-- 菜单ID -->
<input name="fatherMenuid" id="fatherMenuid" class="mini-hidden" value="${fatherMenuid}"/><!-- 菜单的父级Id -->
<input id="provincecode" name="provincecode" class="mini-hidden"  value="${provincecode }"/><!-- 省份编码对应jyconfig文件中配置的 -->
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