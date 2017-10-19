<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>已办事项</title>
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
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
    <input id="action" name="action" class="mini-hidden" value="${action}" />
    <input id="resid" name="resid" class="mini-hidden" value="${resid}"/>
        <table >
             <tr>
                <td style="width:100%;">
                
                	<!-- <a class="mini-button" id="11145" iconCls="" plain="true" onclick="chooseAll()">批量处理</a> -->
                </td>
                <td style="white-space:nowrap;"> 
                                                文书简介：<input  id="sptype" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入文书简介" style="margin-right: 50px;" onenter="onKeyEnter"/>  
                                                审批时间：<input  id="sptime1" class="mini-datepicker"  allowSort="true" renderer="onDateRenderer"/>  
                                                至：<input  id="sptime2" class="mini-datepicker" renderer="onDateRenderer" style="margin-right: 50px;"/>
                  <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入申请人姓名" style="width:130px;" onenter="onKeyEnter"/> 
                  <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                 <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
          idField="spid" multiSelect="true"  allowAlternating="true"  virtualScroll="false" url="getHavetodoitemList.action?1=1"
          showLoading="false"   onbeforeload="myloading" onload="myunmask">
          <div property="columns">
              <!-- <div type="checkcolumn" width="30"></div> -->
              <div field="applyname" width="50px" headerAlign="center" align="center"  allowSort="true">申请人姓名</div>    
              <div field="conent" width="150" headerAlign="center" align="center"  allowSort="true">文书简介</div>
              <div field="applydatetime" width="50px" headerAlign="center" align="center"  allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">申请时间</div>
              <div field="optime" width="50px" headerAlign="center" align="center"  allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">审批时间</div>
              <div field="endsummry" width="40" headerAlign="center" align="center"  allowSort="true">审批状态</div>
              <!--  <div id="action" id="action" width="140" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div> -->
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("optime","desc");
        grid.load();
        
       /** function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
        	if(record.accent=='0'){
        		s += ' <a class="Edit_Button"  href="javascript:chooseOne(1026602);" >处理</a>';
        	}else if(record.accent=='1'){
        		s += ' <a class="Edit_Button"  href="javascript:chooseOne(1026602);" >修改</a>';
        	}else if(record.accent=='2'){
        		s += ' <a class="Edit_Button"  href="javascript:chooseOne(1026603);" >查看</a>';
        	}
            return s;
        }
        function chakan(){
        	var row = grid.getSelected();
        	var action = mini.get("action").getValue();//要跳转的action地址
        	var resid = mini.get("resid").getValue();
        	var url = action+".action?1=1&resid="+resid+"&crimid="+row.crimid+"&name="+row.name;
			self.location.href=url;
        }
        //根据action跳转到指定页面
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	//var action = mini.get("action").getValue();//要跳转的action地址
        	//var resid = mini.get("resid").getValue();
        	//if(resid == null){
        	//	resid = menuid;
        	//}
        	if(row){
        		var url = "toThreeOfCrimAffirmPage.action?1=1&menuid="+menuid+"&crimid="+row.crimid;
        		var idnumber = row.idnumber;//流程编号
	        	if(idnumber){
	        		url += "&idnumber="+idnumber;
	        	}
				self.location.href=url;
        	}else{
        		alert( "请选中一条记录！");
        	}
        }
        //批量处理，将选中的罪犯编号拼接成数组，转换成字符串
        function chooseAll(){
        	var rows = grid.getSelecteds();
        	var action = mini.get("action").getValue();//要跳转的action地址
            if (rows.length > 0) {
                if (confirm("确定操作 选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i<l ; i++) {
                        var r = rows[i];
                        ids.push(r.crimid);
                    }
                    var id = ids.join(',');
                    var url= action+".action?1=1&id="+id;
                    self.location.href=url;
                }
            } else {
                alert(confirmMessages);
            }
		}*/
        //快速查询
        function search() {
            var key = mini.get("key").getValue();
            var sptype=mini.get("sptype").getValue();
            var sptime1=mini.get("sptime1").text;
            var sptime2=mini.get("sptime2").text;
        	grid.sortBy("optime","desc");
            grid.load({ key: key,sptime1: sptime1,sptime2: sptime2,sptype:sptype});
        }
        function onKeyEnter(e) {
            search();
        }
        //渲染日期
		function onDateRenderer(e) {
			if(e && e.value){
				return mini.formatDate ( new Date(e.value), "yyyy-MM-dd HH:dd:ss" );
			}
		    return "";
		}
    </script>    
</body>  
</html>