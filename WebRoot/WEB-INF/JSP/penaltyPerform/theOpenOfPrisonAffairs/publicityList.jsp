<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%> 
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>狱务公开</title>
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
	</style>
</head>

<body >
    <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
    <input id="type" name="type" class="mini-hidden" value="${type}"/>
         <table >
            <tr>
                <td style="width:100%;">
        			<a class="mini-button" iconCls="icon-add" plain="true" onclick="add();">新增</a>
        			<a class="mini-button" iconCls="icon-remove" plain="true" onclick="shanchu();">批量删除</a>
        			<a class="mini-button" iconCls="" plain="true" onclick="chexiao();">撤销</a>
            	</td>
             	<td style="white-space:nowrap;">
                	<input class="mini-textbox" id="key" class="mini-textbox" emptyText="公示标题"  onenter="onKeyEnter"/>
                	<a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
					<!-- 操作说明 -->
					<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('10238')"></a>
             	</td>
            </tr>
		</table> 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" onrowdblclick="rowdblclickfunction" style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false" 
	    	 url="<%=path %>/theOpenOfPrisonAffairs/ajaxGetPublicityList.json?1=1&type=${type}" idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
        		 <div type="checkcolumn" width="10"></div> 
          		 <div field="opcnumber" width="40px" headerAlign="center" allowSort="true" align="center" >公示标题</div>  
       			 <div field="crimid" width="40px" headerAlign="center" allowSort="true" align="center" onDateRenderer>公示开始时间</div> 
          		 <div field="name" width="40px" headerAlign="center" allowSort="true" align="center" onDateRenderer>公示结束时间</div>  
           		 <div field="instanceid" width="40px"  headerAlign="center" allowSort="true" align="center" >公示天数</div>  
		         <div field="opcillinstance" width="40px" headerAlign="center" allowSort="true" align="center" >公示人</div>  
		         <div field="opcillcheckresult" width="40px" headerAlign="center" allowSort="true" align="center" onDateRenderer>公示日期</div>  
           		 <div field="instancestate" width="40px" headerAlign="center" allowSort="true" align="center" >公示状态</div>   
               	 <div name="action" width="60px" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
           </div>
	    </div>
    </div>
    <script type="text/javascript">
 		mini.parse(); 
        var grid = mini.get("datagrid");
        grid.load();
        grid.sortBy("crimid", "desc");

        function onActionRenderer(e) {
	        var s ="";
		    s=s+'<a class="Edit_Button" href="javascript:shezhimingxi()">设置明细</a>&nbsp;&nbsp;'
               +'<a class="Edit_Button" href="javascript:fabu()">发布</a>&nbsp;&nbsp;'
               +'<a class="Edit_Button" href="javascript:chakan()">查看</a>&nbsp;&nbsp;'
               +'<a class="Edit_Button" href="javascript:xiugai()">修改</a>&nbsp;&nbsp;'
               +'<a class="Edit_Button" href="javascript:shanchu()">删除</a>&nbsp;&nbsp;'
	        return s;
	    }
	    //新增
	    function add(menuid){
	    	var type = mini.get("type").getValue();
	    	mini.open({
                url: "theOpenOfPrisonAffairs/addPublicityList.page?1=1&type="+type,
                showMaxButton: true,
             	allowResize: false, 
                title: "编辑", width: 600, height: 400,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"new" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (text) {
                    grid.reload();
                }
            });
	    }
        //查看
		function chakan() {
			var row = grid.getSelected();//获取选中记录
	    	mini.open({
                url: "theOpenOfPrisonAffairs/showReviewTheList.page?1=1",
                showMaxButton: true,
             	allowResize: false, 
                title: "编辑", width: 600, height: 400,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"show" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (text){
                    grid.reload();
                }
            });
		}     
		 //修改
		function xiugai(menuid) {
			var row = grid.getSelected();//获取选中记录
	    	mini.open({
                url: "theOpenOfPrisonAffairs/updateReviewTheList.page?1=1",
                showMaxButton: true,
             	allowResize: false, 
                title: "编辑", width: 600, height: 400,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"edit" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action){
                    grid.reload();
                }
            });
		}        
		//删除
        function shanchu(){
        	var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.docid);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    $.ajax({
                        url: "theOpenOfPrisonAffairs/deleteReviewTheList.json?1=1",
                        type: "post",
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        alert("操作失败!");
                        }
                    });
                }
            } else {
                alert("请选中一条记录");
            }
		}
		function onKeyEnter(e) {
            search();
        }
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
        //撤销
        function chexiao()  {
                var rows = grid.getSelecteds();
                if(rows.length > 0){
                     if(!confirm('撤销以后将无法恢复!')){
                         return;
                     }
                     var i = 0;
                     var ids = [];//罪犯编号
                     var flowdraftids = [];//流程草稿id，唯一标识一条记录
                     var jysptg = "";//案件审批通过的  是不能撤销的
                     for(i=0;i<rows.length;i++){
                         var jincheng = rows[i].jincheng;
                         if(jincheng == '案件办理通过'){
                        	 jysptg += rows[i].name+',';
                        	 grid.deselect(rows[i],true);//取消不符合条件复选框的选择状态
                         }else{
                        	 ids.push(rows[i].crimid);
                        	 flowdraftids.push(rows[i].flowdraftid);
                         }
                     }
                     //审批通过的是无法撤销的，牵扯到留痕的问题，
                     //只有审批不通过或者还在审批中的才可以进行撤销操作
                     if(jysptg != ""){
                         alert("案件<"+jysptg.substring(0,jysptg.length-1)+">审批通过无法撤销!");
                         return;
                     }
                     //下面我们将要执行，符合撤销条件的进行撤销
                     $.ajax({
                          type:'post',
                          url:'cheXiaoBwjyCase.action?1=1&crimids='+ids+"&flowdraftids="+flowdraftids,
                          success:function (text){
                              alert('操作成功!');
                              grid.reload();
                          }
                     });
                }else{
                     alert('请选择需要撤销案件!');
                }
            }
    </script>
</body>
</html>