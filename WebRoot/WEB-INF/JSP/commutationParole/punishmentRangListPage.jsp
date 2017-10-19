<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
	Map<String,String> aipmap = (Map<String,String>)request.getAttribute("aipmap");
	Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>

<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/SignatureInsertNote.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>减刑幅度列表</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }
		</style>
    <script type="text/javascript">
	</script>
</head>
<body>
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
            <table >
               <tr>
                <td style="width:100%;">
	      			<%
						if(topmap!=null && topmap.size()>0){
							for (String key : topmap.keySet()) {
							    String[] arr = topmap.get(key).split("@");
							    String[] icoarr = key.split(",");
							    String ico="";
							    if(icoarr.length==2) ico = icoarr[1];
			       	%>
			       	<a class="mini-button" iconCls="<%=ico%>" plain="true" onclick="<%=arr[1] %>"><%=arr[0] %></a>
			       	<%
							}
						}
					%>
					<input id="toid" class="mini-treeselect" url="ajaxByWideandthins.json?1=1" multiSelect="true" textField="NAME" valueField="ID"  parentField="PID"
					checkRecursive="true" oncloseclick="onCloseClick" emptyText="从严从宽情况" showFolderCheckBox="true" expandOnLoad="1" showClose="true" popupWidth="195"/>
					<a class="mini-button" plain="true" iconCls="icon-add" onclick="create();">生成从严从宽方案</a>&nbsp;&nbsp;
                </td>
                <td style="white-space:nowrap;">
                	<input name="key" id="key" class="mini-combobox" textField="text" valueField="id" emptyText="罪犯类别"   url="ajaxSelectData.action?id=typeid&text=name&table=tbxf_crimetypes" allowInput="true" showNullItem="true" nullItemText="请选择..." />
                    <a class="mini-button"  plain="true" iconCls="icon-search" plain="true"	onclick="search()">查询</a>
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('${menuid}')"></a>
                    <script src="<%=path %>/scripts/loadaip2.js"></script>
				    <input id="criminalid" name="criminalid" type="hidden" value=""/>
				    <input id="choosecriminal" name="choosecriminal" type="hidden" value=""/>
				    <input id="fullopen" name="fullopen" type="hidden" value=""/>
                </td>
               </tr>
		</table>
		 
    </div>
    <div class="mini-fit">
	    <div id="datagrid" style="height:100%;width:100%;" class="mini-datagrid"  url="ajaxGetPunishmentRangList.action?1=1" 
	    sizeList="[10,20,50,100]" pageSize="100" sortField="sn" sortOrder="desc" allowSort="true" showLoading="true" allowAlternating="true" multiSelect="true" >
	        <div property="columns">
	        		<div type="checkcolumn" width="22"></div>  
	        		<div field="PUNID" name="PUNID" width="20" headerAlign="center" align="center" allowSort="true">
               			PUID
               		</div>
	        		<div field="SN" name="SN" width="20" headerAlign="center" align="center" allowSort="true">
               			排序
               		</div>
	        		<div field="SETIME" name="SETIME" width="60" headerAlign="center" align="center" allowSort="true">
               			适用时间
               		</div>
	        		<div field="PTYPE" name="PTYPE" width="30" headerAlign="center" align="center" >
               			案件类型
               		</div>               		
               		<div field="ORIGINAL" name="ORIGINAL" width="45" headerAlign="center" align="center" allowSort="true" >
               			原判刑期
               		</div>
               		<div field="SENTENCE" name="SENTENCE" width="30" headerAlign="center" align="center" allowSort="true" >
               			现刑期
               		</div>
               		<div field="SPERIOD" name="SPERIOD" width="45" headerAlign="center" align="center" allowSort="true" >
               			起始时间
               		</div>
               		<div field="EXECUTESENTENCE" name="EXECUTESENTENCE" width="30" headerAlign="center" align="center" allowSort="true" >
               			执行刑期
               		</div>               		
               		<div field="IPERIOD" name="IPERIOD" width="45" headerAlign="center" align="center" allowSort="true" >
               			间隔时间
               		</div>
               		<div field="ALREADYCOMMUTATIONFLAG" name="ALREADYCOMMUTATIONFLAG" width="20" headerAlign="center" align="center" allowSort="true" >
               			减过刑
               		</div> 
               		<div field="FIRSTCOMMUTATIONFLAG" name="FIRSTCOMMUTATIONFLAG" width="20" headerAlign="center" align="center" allowSort="true" >
               			首次减刑
               		</div>                		              		
               		<div field="APPROVETYPE" name="APPROVETYPE" width="30" renderer="onApprovetypERenderer" headerAlign="center" align="center" >
               			是否生效
               		</div>
                 	<div name="action" headerAlign="center"  align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    
</body>
</html>
<script type="text/javascript">

	mini.parse();
	
	var grid = mini.get("datagrid");
	//grid.load();
	//合并行
	//grid.on("load", function () {
		//grid.mergeColumns(["SETIME", "SENNAME", "TYPENAME", "PHNAME", "AGE", "GENDER", "ORIGINAL", "SENTENCE", "SPERIOD", "IPERIOD"]);
    //});
	grid.load();
	grid.sortBy("sn,stime", "desc");
	
	function onActionRenderer(e) {
		var grid = e.sender;
        var record = e.record;
        var punid = record.PUNID;
        var approvetype = record.APPROVETYPE;
    	var s = '';
        <%
		if(middlemap!=null && middlemap.size()>0){
			for (String key : middlemap.keySet()) {
		   		String[] arry = middlemap.get(key).split("@");
        %>
        var keys = "<%=arry[1]%>";
        var keyName = keys.substring(0,6);
		if(keyName == 'tongyi' && approvetype == '1'){
			
		}else{
	      	s +=" <a class=\"Edit_Button\" href=\"javascript:<%=arry[1] %>\" ><%=arry[0] %></a>";
		}
       	
        <%
			}
		}
		%>
     return s;
    }

	function onTypeAndHealthRenderer(e){
    	if (e.value == ' ') {
    		return "适用所有";
        } else if(e.value == '-2') {
        	return "";
        } else if(e.value == '-3') {
        	return "";
        }
        return e.value;	
    }

	function onApprovetypERenderer(e){
    	if (e.value == '0') {
    		return "未生效";
        } else if(e.value == '1') {
        	return "已生效";
        }
        return e.value;	
    }
        
	function onAgeRenderer(e){
    	var str = e.value;
      	var reg = /-1/g;
        var arr  = str.match(reg);
    	if (null!=arr&&arr.length == 2) {
    		return "适用所有";
        }else if (null!=arr&&arr.length == 1) {
         	if(e.value.indexOf("-1")==0){
             	return e.value.replace("-1岁~","")+"以下";
             }else{
             	return e.value.replace("~-1岁","以上");
             }
        }
       	return e.value;	
    }

    
    var Genders = [{ id: -1, text: '适用所有' }, { id: 0, text: '男' }, { id: 1, text: '女'}];
	function onGenderRenderer(e) {
        for (var i = 0, l = Genders.length; i < l; i++) {
            var g = Genders[i];
            if (g.id == e.value) return g.text;
        }
        return "";
    }

    
    function onSentenceRenderer(e){
    	var str = e.value;
      	var reg = /-1/g;
        var arr  = str.match(reg);
    	if (null!=arr&&arr.length == 2) {
    		return "适用所有";
        }else if (null!=arr&&arr.length == 1) {
         	if(e.value.indexOf("-1")==0){
             	return e.value.replace("-1~","")+"以下";
             }else{
             	return e.value.replace("~-1个月","个月以上");
             }
        }else if(e.value.indexOf("-2")>-1) {
        	return "无期徒刑";
        }else if (e.value.indexOf("-3")>-1) {
        	return "死刑缓期两年执行";
        }
       	return e.value;	
    }

    
    function onPeriodRenderer(e){
    	var str = e.value;
      	var reg = /-1/g;
        var arr  = str.match(reg);
    	if (null!=arr&&arr.length == 2) {
    		return "不限";
        }else if (null!=arr&&arr.length == 1) {
         	if(e.value.indexOf("-1")==0){
             	return e.value.replace("-1~","")+"以下";
             }else{
             	return e.value.replace("~-1个月","个月以上");
             }
        }
       	return e.value;	
    }

    
	function add() {
    	mini.open({
	        url: "toPunishmentRangAddPage.action?1=1",
	        showMaxButton: true,
	        allowResize: false,
	        title: "新增", width: 820, height: 300,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            var data = {action: "new"};
	            iframe.contentWindow.SetData(data);
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}

	
	function edit(punid){
     	mini.open({
 	        url: "toPunishmentRangEditPage.action?punid=" + punid,
 	        showMaxButton: true,
 	        allowResize: false,
 	        title: "编辑", width: 820, height: 300,
 	        onload: function () {
 	            var iframe = this.getIFrameEl();
 	            var data = {action:"edit", punid:punid};
 	            iframe.contentWindow.SetData(data);
 	        },
 	        ondestroy: function (action) {
 	        	//修改后手动刷新 方便多次修改
 	        	//grid.reload();
 	        }
     	});
	}

	
	function remove(punid) {
	    if (confirm("确定删除选中记录？")) {
	        $.ajax({
	            url: "deletePunishmentRang.action?id="+punid,
	            success: function (text) {
	                grid.reload();
	            },
	            error: function () {
	           		alert("操作失败");
	           		return;
	            }
	        });
	    }
    }
    
    function search(){
        var key = mini.get("key").getValue();
        grid.load({ key: key });
    }

    
    function rewardsDetail(punid){
     	window.location.href="toCommutationReferenceListPage.action?punid="+punid;   
    }
  
    function wideAndThin(punid){
     	window.location.href="toWideAndThinschemeListPage.action?punid="+punid;   
    }
        
	function importscheme() {
    	mini.open({
	        url: "toImportschemePage.action?1=1",
	        showMaxButton: true,
	        allowResize: false,
	        title: "方案导入", width: 270, height: 300,
	        onload: function () {
	        
	        },
	        ondestroy: function (action) {
	        	grid.reload();
	        }
    	});
	}
	//减刑类型描述
	function tosentencetype(resid){
     	window.location.href="toSentenceTypePage.page?menuid="+resid;   
    }
	function appedit(punid) {
        mini.confirm("确定使该方案生效？", "处理",
            function (action) {
                if (action == "ok") {
		            $.ajax({
			            url: "updateApproveType.action?id="+punid,
			            success: function (text) {
			                grid.reload();
			            },
			            error: function () {
			           		alert("操作失败");
			           		return;
			            }
			        });
                }
            }
        );
    }
	function create(){
		var rows = grid.getSelecteds();
   		var punid;
   		var toid=mini.get("toid").getValue();
   		if(!rows){
   			mini.alert("请选中方案!");
   			return ;
   		}
   		if(toid==''){
   			mini.alert("请选择从严从宽方案！");
   			return;
   		}
   		 if (rows.length > 0) {
	          var punids = [];
	          for (var i = 0, l = rows.length; i < l; i++) {
                 var r = rows[i];
                 punids.push(r.PUNID);
              }
	          punid = punids.join(',');
	      }
   		var url="ajaxCreateWideandthins.action";
   		mini.confirm("确认操作？","",function temp(action){
   		if(action=='ok'){
   			$.ajax({
   			url:url,
   			data:{punid:punid,toid:toid},
   			type:"post",
   			success:function(text){
   				mini.alert("操作成功！");
   				grid.load();
   			},
   			error:function (jqXHR, textStatus, errorThrown){
   				mini.alert("操作失败！");
   			}
   		});
   		}
   		});
   	}
	 //从严从宽方案的清空
        function onCloseClick(e) {
            var obj = e.sender;
            obj.setText("");
            obj.setValue("");
        }
	 
	 //同意
	 function tongyi(punid){
	 	 if (confirm("确定本方案是否生效？")){
			 $.ajax({
		            url: "editPunishmentRang.json?id="+punid,
		            success: function (text) {
		                grid.reload();
		            },
		            error: function () {
		           		alert("操作失败");
		           		return;
		            }
		        });
			 }
	 }
</script>
