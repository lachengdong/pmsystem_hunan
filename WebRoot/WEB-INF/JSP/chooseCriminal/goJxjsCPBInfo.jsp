<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>减刑假释呈批表(宁夏监区)</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;padding: 0;border: 0;width: 100%;height: 100%;overflow: hidden;
		}
	</style>
</head>
<body>
    <input id="text1" name="text1" type="hidden" value="${text1 }"/><!-- 可以操作节点 -->
	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
		<table>
			<tr>
				<td style="width: 100%;">
				    <!-- 监区提交 -->
				    <a class="mini-button" iconCls="icon-downgrade" plain="true"  onclick="batchSubmit('other_jxjscpb','802056','submit')">批量提交</a>
				</td>
				<td style="white-space: nowrap;">
					<input class="mini-textbox" id="key" class="mini-textbox" emptyText="请输入罪犯编号、姓名、拼音" onenter="onKeyEnter" />
					<a class="mini-button" plain="true" iconCls="icon-search" plain="true" onclick="search()">快速查询</a>
					<!-- 操作说明 -->
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10179')"></a>
				</td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" 
		     style="width: 100%; height: 100%;"
		     class="mini-datagrid" sizeList="[10,20,50,100]" pageSize="20" 
			allowAlternating="true" allowResize="false" 
			url="basicInfo/queryChengPiBiaoContent.action?1=1" multiSelect="true">
			<div property="columns">
				<div type="checkcolumn" width="15px"></div>
				<div field="flowdraftid" headerAlign="center" align="center" allowSort="true" width="0px;">流程id</div>
				<div field="applyid" headerAlign="center" align="center" allowSort="true" width="40px;">罪犯编号</div>
				<div field="applyname" headerAlign="center" align="center" allowSort="true" width="40px;">罪犯姓名</div>
				<div field="maincase" headerAlign="center" align="center" allowSort="true" width="50px">罪名</div>
				<div field="courtchangefrom" headerAlign="center" align="center" allowSort="true" width="50px">刑期起日</div>
				<div field="courtchangeto" headerAlign="center" align="center" allowSort="true" width="50px">刑期止日</div>
				
				<div field="punishmentyear" headerAlign="center" align="center" allowSort="true" width="50px">原判刑期</div>
				<div field="courtchangeyear" headerAlign="center" align="center" allowSort="true" width="60px">剩余刑期</div>
				<div field="inprisondate" headerAlign="center" align="center" allowSort="true" width="50px">入监时间</div>
				
				
				<div field="jifenkaoheqizhi" headerAlign="center" align="center" allowSort="true" width="50px">计分考核及起止</div>
				<div field="yuzhengkehefen" headerAlign="center" align="center" allowSort="true" width="50px">狱政科考核分</div>
				
				<div field="xingzhengjiangcheng" headerAlign="center" align="center" allowSort="true" width="50px">行政奖惩</div>
				<div field="yuzhengkekaohe" headerAlign="center" align="center" renderer="onActionYuZhengKeKaoHe" allowSort="true" width="50px">狱政科审核</div>
				<div field="caichanlvxingqingkuang" headerAlign="center" align="center" width="50px">财产履行情况</div>
				
				
				<div field="submitstate" headerAlign="center" align="center" renderer="onActionSubmitState"  width="40px">提交状态</div>
				<div field="intoo" headerAlign="center" align="center" allowSort="true" width="0px">(分)监区意见</div>
				<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="50px">操作</div>
			</div>
		</div>
	</div>
	<!-- 表格内容 -->
    <div id="editWindow" class="mini-window" title="呈报内容" style="width:650px;height:400px;" 
    showModal="true" allowResize="true" allowDrag="true"
    >
	    <div id="editform" class="form" >
	        <input class="mini-hidden" name="id"/>
	        <input name="gender" class="mini-hidden"/><!-- 性别不需要 修改 -->
	        <input id="curyear" name="curyear" class="mini-hidden"/><!-- 批次年 -->
	        <input id="batch" name="batch" class="mini-hidden"/><!-- 批次次 -->
	        <input id="flowdraftid" name="flowdraftid" class="mini-hidden"/><!-- 流程草稿id -->
	        <table>
	            <tr>
	                <td>罪犯编号：</td>
	                <td><input name="crimid" class="mini-textbox"  required="true" /></td>
	                <td>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
	                <td><input name="cbiname" class="mini-textbox" required="true" /></td>
	            </tr>
	            <tr>
	                <td>民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
	                <td><input name="nation" class="mini-textbox"  required="true" /></td>
	                <td>罪&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
	                <td><input name="casemain" class="mini-textbox" required="true" /></td>
	                <td>出生日期：</td>
	                <td><input name="birthday" class="mini-datepicker"  required="true" /></td>
	            </tr>
	            <tr>
	                <td>原判刑期：</td>
	                <td><input name="yuanpanxingqi" class="mini-textbox"  required="true" /></td>
	                <td>剩余刑期：</td>
	                <td><input name="shengyuxingqi" class="mini-textbox"  required="true" /></td>
	                <td>级&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
	                <td><input name="jibie" class="mini-combobox" required="true"  data="jibie"/></td>
	            </tr>
	        </table>
	        <table>
	            <tr>
	                <td>刑期变动：</td>
	                <td><input name="xingqibiandong" class="mini-textarea" required="true"  style="width:510px;height: 70px;"/></td>
	            </tr>
	        </table>
	        <table>
	            <tr>
	                <td>刑期起日：</td>
	                <td><input name="xingqiqiri" class="mini-datepicker"  required="true" /></td>
	                <td>刑期止日：</td>
	                <td><input name="xingqizhiri" class="mini-datepicker"  required="true" /></td>
	                <td>入监时间：</td>
	                <td><input name="rujianshijina" class="mini-datepicker"  required="true" /></td>
	            </tr>
	            <tr>
	                <td style="width:60px;">计分考核及起止：</td>
	                <td><input name="jifenkaoheqizhi" class="mini-textarea"  required="true" style="height: 70px;"/></td>
	                <td>行政奖惩：</td>
	                <td><input name="xingzhengjiangcheng" class="mini-textarea" required="true"   style="height: 70px;"/></td>
	                <td style="width:60px;">财产履行情况：</td>
	                <td><input name="caichanlvxingqingkuang"class="mini-textarea"  style="height: 70px;"/></td>
	            </tr>
	            <tr>
	                <td style="width:60px;">狱政科核分：</td>
	                <td><input name="yuzhengkehefen" required="true"  enabled="false" class="mini-textbox"/></td>
	                <td style="width:60px;">狱政科审核：</td>
	                <td><input name="yuzhengkekaohe" enabled="false"  required="true"   class="mini-combobox" data="yuzhengkeshenhe"/></td>
	                <td style="width:60px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
	                <td><input name="remark" class="mini-textbox"/></td>
	            </tr>
	            <tr>
	                <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="6">
	                    <a class="mini-button" style="color: blue;" href="javascript:updateRow('other_jxjscpb','802056')">修改</a> 
	                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    <a class="mini-button" style="color: blue;" href="javascript:cancelRow()">关闭</a>
	                </td>                
	            </tr>
	        </table>
	    </div>
	</div>
	<script type="text/javascript"> 
	     var jibie = [{ id: 1, text: '一般' },{ id: 2, text: '二宽'},{ id: 3, text: '一宽'}];
	     var yuzhengkeshenhe = [{ id: 1, text: '同意' }, { id: 2, text: '不同意'}];
		 mini.parse();
	     var grid = mini.get("datagrid");
	     var editWindow = mini.get("editWindow");
	     grid.load();
	     //select3.setEnabled(true);
	     
	     //控制 是否可以进行编辑内容
	     //标签id="text1"里面的值可以控制 那些input标签是否可以编辑(编辑权限控制)
	     //由于两级审批，并且是两个页面，所以暂时不需要用代码控制。使用标签的属性直接可以控制
	     //页面goJxjsCPBInfo_yzk.jsp和当前页面是相同的
	     
	     function onActionRenderer(e) {
	    	var grid = e.sender;
	        var record = e.record;
	        var uid = record._uid;
	        var rowIndex = e.rowIndex;
	     	var s = '<a id="chuli" class="Edit_Button" href="javascript:editRow(\'' + uid + '\')">处理</a>';
		    return s;
	     }

	     function onActionSubmitState(e){
		    var grid = e.sender;
		    var record = e.record;
		    var uid = record._uid;
            var state = record.submitstate;
            var rowIndex = e.rowIndex;
            var s = "";
            if(state == 0){
                 s = "不符合";
            }
            if(state == 1){
                 s = "符合";
            }
            return s;
		 }
	     function onActionYuZhengKeKaoHe(e){
		    var grid = e.sender;
		    var record = e.record;
		    var uid = record._uid;
            var state = record.yuzhengkekaohe;
            var rowIndex = e.rowIndex;
            var s = "";
            if(state == 2){
                 s = "不同意";
            }
            if(state == 1){
                 s = "同意";
            }
            return s;
		 }
	     function chuli(){
		     var row = grid.getSelected();
		     var crimid = row.crimid;
		     var criname = row.criname;
		     var url = '<%=path%>/basicInfo/goDanGePanCaiInfoView.action?1=1&crimid='+crimid+'&name='+encodeURI(encodeURI(criname));
	    	 mini.open({
			     type:'POST',
			     url:url,
			     title: "判决详细信息", width: "750", height: "450",
			     success:function (){
			         var iframe = this.getIFrameEl();
			         var data = {};
			         iframe.contentWindow.SetData(data);
			     },
			     ondestroy:function(){
			     	grid.reload();
			     }
	        });
		 }
	     //快速查询
         function onKeyEnter(e) {
            search();
         }
	     function search(){
              var key = mini.get("key").getValue();
              grid.load({key:key});
		 }
		 //点击处理按钮 加载对应的数据
	     function editRow(row_uid) {
	            var row = grid.getRowByUID(row_uid);//获取当前选中行的信息 
	            if (row) {
		            var crimid = row.applyid;
		            var flowdraftid = row.flowdraftid;
		            mini.get("flowdraftid").setValue(flowdraftid);
	                editWindow.show();
	                var form = new mini.Form("#editform");
	                form.clear();
	                form.loading();
	                $.ajax({
		                type:'post',
	                    url: "<%=path%>/basicInfo/getCriminalCPBInfo.action?1=1&crimid="+crimid,
	                    success: function (text) {
	                        form.unmask();
	                        var o = mini.decode(text);
	                        form.setData(o);
	                    },
	                    error: function () {
	                        alert("数据加载错误!");
	                        form.unmask();
	                    }
	                });
	            }
	        }
	        //关闭 弹出框 
	     	function cancelRow() {
	            grid.reload();
	            editWindow.hide();
	        }
	        //保存需要修改的 内容
	        function updateRow(flowdefid,menuid){
	        	var form = new mini.Form("#editform");
	        	form.validate();
	            if (form.isValid() == false) return;
	        	var reoprtid = 'jy_jxjs_cpb';
	        	var data = form.getData();
	            var json = mini.encode([data]);
	            $.ajax({
                    type:'post',
                    url:'<%=path%>/basicInfo/saveJxJsCPBContent.action?1=1',
                    data:{data:json,reoprtid:reoprtid,flowdefid:flowdefid,menuid:menuid},
                    success:function (text){
                        if(text == '"insert"'){
                             alert("新增成功!");
                        }else if(text == '"update"'){
                             alert('修改成功!');
                        }else{
                             alert('操作失败,请联系管理员!');
                        }
        	            editWindow.hide();
        	            grid.reload();
                    }
		        });
		    }
		    //批量提交
		    function batchSubmit(flowdefid,menuid,caozuostates){
                 var rows = grid.getSelecteds();
                 var nosubmits = "";
                 var crimids = "";
                 for (var i=0;i<rows.length;i++){
                      //alert(rows[i].submitstate);
                      //判断，如果是 0 那么 不能提交
                      if(rows[i].submitstate == 0){
                    	   grid.deselect(rows[i],true);//取消复选框的选择状态 
                    	   nosubmits = nosubmits+rows[i].applyname+",";
                      }else{
                    	  crimids=crimids+rows[i].applyid+",";
                      }
                 }
                 nosubmits = nosubmits.substring(0,nosubmits.length-1);
                 if(nosubmits != ''){
                     alert("罪犯:"+nosubmits+"等。不符合提交条件!");
                 }
                 crimids = crimids.substring(0,crimids.length-1);
                 //提交呈批表案件信息 
                 //alert(flowdefid+"||"+menuid+"||"+caozuostates);
                 if(crimids != ""){
                	 $.ajax({
                         type:'post',
                         url:'<%=path%>/basicInfo/submitJxJsCPBCaseInfo.action?1=1&crimids='+crimids+'&flowdefid='+flowdefid+'&menuid='+menuid+'&caozuostates='+caozuostates,
                         success:function (){
                             alert("操作成功!");
                        	 grid.reload();
                         }
                     });
                 }
                 
			}
	</script>
</body>
</html>