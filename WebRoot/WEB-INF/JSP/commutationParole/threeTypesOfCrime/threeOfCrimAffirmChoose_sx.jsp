<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>三类犯认定</title>
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
<body onload="init('10266');">  
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
    	<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
                	<a class="mini-button" style="display:none;" id="1026601" iconCls="" plain="true" onclick="chooseAll(1026602)">批量处理</a>
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入罪犯编号、姓名、拼音" style="width:130px;" onenter="onKeyEnter"/>   
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                   <a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming_sx('SX_SLFRDBZSM')"></a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false"  sizeList="[10,20,50,100]" pageSize="20"
          idField="spid" multiSelect="true"  allowAlternating="true"  virtualScroll="false" url="<%=path%>/threeOfCrimAffirm/getThreeOfCrimAffirmList_sx.json?1=1"
          showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
              <div type="checkcolumn" width="30"></div>
              <div field="crimid" width="50" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>    
              <div field="name" width="50" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="orgname2" width="70" headerAlign="center" align="center"  allowSort="true">所在部门</div>
              <div field="arrestauthority" width="80" headerAlign="center" align="center"  allowSort="true">逮捕机关</div>
              <div field="judgmentname" width="60" headerAlign="center" align="center"  allowSort="true" >判决法院</div>
              <div field="punishmenttype" width="60" headerAlign="center" align="center"  allowSort="true">判决类型</div>
              <div field="maincase" width="60" headerAlign="center" align="center"  allowSort="true">罪名</div>
              <div field="sentencestime" width="60" headerAlign="center" align="center" allowSort="true" >刑期起日</div>
              <div field="sentenceetime" width="60" headerAlign="center" align="center">刑期止日</div>
              <div field="sanclassstatus" width="0" headerAlign="center" align="center">三类状态</div>          
              <div id="action" width="40" headerAlign="center" align="center" allowSort="false" renderer="onActionRenderer">操作</div>  
          </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.sortBy("crimid","desc");
        grid.load();
        
        function onActionRenderer(e) {
	        var record = e.record;
	        var s = '';
	        var departid = <%=request.getAttribute("departid")%>;
		    //根据jyconfig.properties 中的shanxi属性对应的值 进行显示 需要的按钮 
			if(record.sanclassstatus == 1){
			     s += ' <a class="Edit_Button" style="color:red;" href="javascript:chooseOne_sx(0);" >取消</a>';     
			}else{
				 s += ' <a class="Edit_Button"  href="javascript:chooseOne_sx(1);" >处理</a>';
			} 
	        return s;
       }

       //山西阳泉  三类犯 认定 的功能
       function chooseOne_sx(state){
           var row = grid.getSelected();
           var statetitle = "";
           if(state == 1){
               statetitle = "确定认定<<"+row.name+">>为三类罪犯吗?";
           }else{
               statetitle = "确定取消<<"+row.name+">>为三类罪犯吗?";
           }
           if(row){
               var crimid = row.crimid;
               var name = row.name;
               if(confirm(statetitle)){
            	   $.ajax({
                       url:'<%=path%>/threeOfCrimAffirm/threeCrimeAffirm.action?1=1&crimid='+crimid+'&state='+state,
                       type:'post',
                       success:function (text){
                           if(text == 1 && state == 1){
                               alert(name+"认定成功!");
                           }
                           if(text == 1 && state == 0){
                               alert(name+"取消成功!");
                           }
                           grid.load();
                       }
                   });
               }else{

               }
           }else{
                alert("请选择一条数据!");
           }
       }
        //快速查询
        function search() {
            var key = mini.get("key").getValue();
            grid.load({ key: key });
        }
        function onKeyEnter(e) {
            search();
        }

        function chakanshuoming_sx(tempid){
           var win=mini.open({
                url:"<%=path%>/threeOfCrimAffirm/threeClassCriminalShuoMing.action?1=1&tempid="+tempid,
                //showMaxButton:true,
                //allowResize:false, 
                title:"三类罪犯认定说明", width:"900", height:"600",
                onload:function () {
                    var iframe = this.getIFrameEl();
                    var data = {};
                    iframe.contentWindow.SetData(data);
                }
	    	});
	    }
    </script>    
</body>  
</html>