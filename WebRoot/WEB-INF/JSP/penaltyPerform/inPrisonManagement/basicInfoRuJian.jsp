<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
    <script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" /> 
     <style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }  
    </style>
</head>
<body onload="init('${menuid}');">
    <div class="mini-toolbar"  style="padding:0px;border-bottom: 0px;">
	    <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
        <table >
             <tr>
                <td style="width:100%;">
                	 <a class="mini-button" id="3011243" iconCls="" plain="true" onclick="chooseAll('3011243')">批量处理</a><!-- 监狱 -->
                	 <a class="mini-button" id="3010842" iconCls="" plain="true" onclick="chooseReview('11090')">批量重审</a><!-- 监狱 -->
                	 <a class="mini-button" style="display:none;" iconCls="icon-add" id="1600215" plain="true" onclick="add(1600215)">新增</a>
                	  
				     <input id="orgid" name="orgid" class="mini-combobox" url="<%=path %>/org/ajax/getdepartunderself.action?1=1"  onvaluechanged="search" valueFromSelect="false"
						textField="name" valueField="dorgid" showNullItem="true" nullItemText="--全部--" style="width:150px;" enabled="true" emptyText="请选择部门..."/>
						        
					 <input id="status" name="status" class="mini-combobox" data="Statusdata" showNullItem="true" onvaluechanged="search"   valueFromSelect="false"
						textField="text" valueField="id" showNullItem="true" nullItemText="--全部--" style="width:150px;" enabled="true" emptyText="羁押状态..."/>
						        
					 <input id="state" name="state" class="mini-combobox" data="Comboxdata" showNullItem="true" onvaluechanged="search"   valueFromSelect="false"
						textField="text" valueField="id" showNullItem="true" nullItemText="--全部--" style="width:150px;" enabled="true" emptyText="审批状态..."/>	         
                </td>
                <td style="white-space:nowrap;"> 
                   <input id="key" class="mini-textbox" vtype="maxLength:50;" emptyText="请输入编号/姓名/拼音简拼" style="width:130px;" onenter="onKeyEnter" value="${_criminalid}"/>  
                   <a class="mini-button" iconCls="icon-search"  plain="true" onclick="search()">快速查询</a>
                 </td>
             </tr>
        </table>
    </div>
    <div class="mini-fit" id="div_two">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" allowResize="false" sizeList="[10,20,50,100]" pageSize="20"
        	 url="<%=path%>/getBasicInfoRuJian.json?1=1" idField="spid" multiSelect="true" allowAlternating="true" virtualScroll="false" 
        	 showLoading="false" onbeforeload="myloading" onload="myunmask">
          <div property="columns">
              <div type="checkcolumn" width="15"></div>
              <div type="indexcolumn" width="20" headerAlign="center">序号</div>
              <div field="crimid" width="30" headerAlign="center" align="center"  allowSort="true">罪犯编号</div>   
              <div field="crimclass" width="30px"  headerAlign="center" allowSort="true" align="center" >罪犯类型</div>
              <div field="fileno" width="30" headerAlign="center" align="center"  allowSort="true">档案号</div>    
              <div field="name" width="30" headerAlign="center" align="center"  allowSort="true">姓名</div>
              <div field="birthday" width="30" headerAlign="center" align="center"  allowSort="true" renderer="onDateRenderer">出生日期</div>
              <div field="causeaction" width="60"  headerAlign="center" allowSort="true" align="center" >罪名</div> 
              <div field="originalyear" width="30" headerAlign="center" align="center"  allowSort="true">原判刑期</div>
           	  <div field="sentencestime" width="40" headerAlign="center" dateFormat="yyyy-MM-dd"  align="center"  allowSort="true" renderer="onDateRenderer">原判刑期起日</div> 
              <div field="courtchangeto" width="40px"  headerAlign="center" align="center" allowSort="true" renderer="onDateRenderer">现刑期止日</div>
              <div field="orgname" width="30" headerAlign="center" align="center"  allowSort="true">所属部门</div>
              <div field="identity" width="60" headerAlign="center" align="center" allowSort="true" renderer="onActionExamineType">审批状态</div>    
              <div name="jyaction" width="40" headerAlign="center" align="center"  allowSort="false" renderer="onJyActionRenderer">操作</div>  
              
          </div>
        </div>
    </div>
    <script type="text/javascript">
	    var Comboxdata = [{ id: 1, text: '通过' }, { id: 2, text: '未通过'},{ id: 3, text: '审批中'},{ id: 4, text: '未处理'}];
		var Statusdata = [{ id: 1, text: '在押' }, { id: 2, text: '释放'}];
		
        mini.parse();
        var grid = mini.get("datagrid1");
        	grid.sortBy("crimid","desc");
        	grid.load();
        
        mini.get("tempid").setValue("XFZX_ZFRJDJ");
        	
        	
        function onActionExamineType(e){
		    var value = e.value;
		    var s = value;
            if(value.indexOf("通过")!= '-1'){
            	e.cellStyle = "background:green;";
            }
            if(value.indexOf("审批中")!= '-1' || value.indexOf("办理中")!= '-1'){
            	e.cellStyle = "background:yellowgreen;";
            }
            if(value.indexOf("未通过")!= '-1'){
            	e.cellStyle = "background:red;";
            }
            return s	
        }
        
        
        
         function add(menuid){
	    	mini.open({
                url: "<%=path%>/addNewCrimidPage.action?1=1&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "新犯入监", width: 600, height: 300,
                onload: function () {
                },
                ondestroy: function (text) {
                    grid.reload();
                }
            });
	    }
         
         
         
         
         
		var nodeids = mini.get("nodeids").getValue();//权限返回的节点id
		function onJyActionRenderer(e) {
	        var record = e.record;
	        var lastnodeid = record.duty;
	        var s = '';
	       	if(lastnodeid != null && lastnodeid != ''){
	       		if(nodeids==lastnodeid){
	       			var button = '${dealstr}';
	       			if(record.orgcontact=='1'){
	       				button = button.replace('处理','修改');
	       			}
	       			s += button;
	       		}else{
	       			s +=' ${viewstr}';
	       		}
	       	}else{
	       		s +=' ${dealstr}';
		    }
        	s += ' ${otherstr}';
            return s;
        }
      
         
      	//根据罪犯编号跳转到表单页面，如果寻找罪犯未处理或者审批未通过,没有流程idnumber,生成一条新数据。
        function chooseOne(menuid) {
        	var row = grid.getSelected();
        	var tempid = mini.get("tempid").getValue();
        	
        	if(row){
        		var url = "<%=path%>/basicRuJian.page?1=1&crimid="+row.crimid
        					+"&tempid="+tempid+"&menuid="+menuid+"&fathermenuid=${menuid}"+"&flowdefid=${flowdefid}"+"&lastnodeid="+row.duty;
        		if(row.idnumber && row.duty != '-1'){
        			url += "&idnumber="+row.idnumber;//如果罪犯审批未通过重新生成一条数据
        		}
				self.location.href=url;
        	}else{
        		alert(confirmMessage);
        	}
        }
        
        
        
        //批量处理
  		function chooseAll(menuid){
        	var rows = grid.getSelecteds();
        	var tempid = mini.get("tempid").getValue();
           	if(rows.length > 0){
           		var ids = [];
                   for (var i = 0, l = rows.length; i < l; i++) {
                       var r = rows[i];
                       if(r.idnumber){
                      		if(nodeids != r.duty && r.orgcontact != '1'){
                      			continue;
                      		}else{
                      			if(r.orgcontact == '2') {
                      				continue;
                      			}
                      		}
                      }else{
                      		if(nodeids!='0') {
                      			continue;
                      		}
                      }
                     //  if(r.accent=='2' && r.duty != '-1') continue;//审批中的罪犯不能进行批量处理
                if(r.idnumber && r.duty != '-1'){//如果罪犯审批未通过重新生成一条数据
                       		ids.push(r.crimid+"@"+r.idnumber);
                       }else{
                       	    ids.push(r.crimid+"@");
                       }
                   }
				   if(ids.length == '0'){//所选中的数据都是审批中的
				   		alert(noProcessing);
				   		return ;
				   }else {
				   		if (confirm(allProcessing)) {
				   			var id = ids.join(',');
                   			var url = "<%=path%>/basicRuJian.page.page?1=1&id="+id+"&menuid="+menuid+"&tempid="+tempid+"&fathermenuid=${menuid}"+"&flowdefid=${flowdefid}";
                   			self.location.href=url;
				   		}
				   }
           	}else {
               	alert(confirmMessages);
           	}  
		}
  		
        //快速查询
        function onKeyEnter(e) {
            search();
        }
        search();
        
        function search() {
            var key = mini.get("key").getValue();
            var orgid=mini.get("orgid").getValue();
            var state=mini.get("state").getValue();
            var status=mini.get("status").getValue();
            //key = encodeURI(key);
            grid.sortBy("crimid","desc");
            grid.load({ key: key , zgorgid:orgid , state:state , status:status});
        }
    </script>
</body>  
</html>