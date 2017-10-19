<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title>审批人配制</title>
		<style type="text/css">
		    body{
		        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
		    }     
		    .mini-panel-border{
		    	border-left:0;
		    	border-right:0;
		    	border-bottom:0;
		    }
		</style>
</head>
<body>
	   <input id="type" name="type" value="${type}" class="mini-hidden" />
	   <input id="flowdefid" name="flowdefid" value="${flowdefid}" class="mini-hidden" />
       <div style="height:100%;">
       <div class="mini-toolbar"  style="padding:2px;border: 0px;">
            <table >
               <tr>
	                <td style="width:100%;">
	                    <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true">增加</a>
	                    <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
	                    <span class="separator"></span>
	                    <a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">保存</a>            
	                </td>
               </tr>
		   </table>
      </div>
      <div class="mini-fit">
	    <div id="datagrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="10" allowCellEdit="true" 
	    	allowCellSelect="true" multiSelect="true" editNextOnEnterKey="true" editNextRowCell="true">
            <div property="columns">
	            <div type="checkcolumn"></div>
	            <div field="id"  headerAlign="center" align="center"  visible="false" ></div>
	            <div field="flowdefid"  headerAlign="center" align="center" renderer="onFlowDeliverTypeRenderer" >流程名称
	                <input property="editor" class="mini-combobox" textField="text" valueField="id" onvaluechanged="setFlowdefid" 
	                	url="ajaxSelectData.action?id=flowdefid&text=name&table=TBXF_FLOWDELIVERTYPES" required="true" />
	            </div>
	            <div field="nodeid" headerAlign="center" align="center" renderer="onFlowDeliverNodeidRenderer">节点
	                <input property="editor" class="mini-combobox" textField="text" valueField="id"  onclick="getFlowDeliverNodeid"
	                	 required="true" />
	            </div>  
	            <div field="userid" headerAlign="center" align="center" renderer="onCurrApprovePersonRenderer">审批起始用户
	                <input property="editor" class="mini-combobox" textField="text" valueField="id" 
	                	url="ajaxGetCurrDepartUsers.json?1=1" required="true" />
	            </div>
	            <div field="duserid" width="25%" headerAlign="center" align="center" renderer="onDuserApprovePersonsRenderer">提交目标用户
	                <input property="editor" class="mini-combobox" textField="text" valueField="id"  multiSelect="true"
	                	url="ajaxGetCurrDepartUsers.json?1=1" required="true" />
	            </div>
	            <div field="orderby" width="10%" headerAlign="center" align="center" renderer="onRenderer" >排序
	                <input property="editor" class="mini-spinner"  minValue="1" maxValue="100"/>
	            </div>
	            <!--  
	            <div field="remark" headerAlign="center" align="center" allowSort="false">备注
					<input property="editor" class="mini-textbox" style="width: 10%%;" />
				</div>
				-->
            </div>
	    </div>
    </div>
    </div>
</body>
</html>

<script type="text/javascript">
	mini.parse();
	var grid = mini.get("datagrid");
	
	function reloadPage(){
		var flowdefid = mini.get('flowdefid').getValue();
		if(flowdefid){
			var url = "<%=path%>/ajaxGetFlowPersonConfigs.json?1=1&flowdefid=" + flowdefid;
			grid.setUrl(url);
			grid.load();
		}
	}
	reloadPage();
	
	function addRow(){          
        var newRow = { name: "New Row" };
        var flowdefid = mini.get('flowdefid').getValue();
    	if(flowdefid){
    		newRow.flowdefid = flowdefid;
    	}
        grid.addRow(newRow, 0);
        grid.beginEditCell(newRow, "LoginName");
    }
    
    function removeRow() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            grid.removeRows(rows, true);
        }
    }
    
    function saveData(){
        var data = grid.getChanges();
        var json = mini.encode(data);
        grid.loading("保存中...");
        $.ajax({
            url: "<%=path%>/saveFlowPersonConfig.json?1=1",
            data: { data: json },
            type: "post",
            success: function (text) {
                //grid.reload();
            if(text){
	            	var result = mini.decode(text);
	            	alert(result.info);
            	}
                reloadPage();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            }
        });
    }

    grid.on("celleditenter", function (e) {
        var index = grid.indexOf(e.record);
        if (index == grid.getData().length - 1) {
            var row = {};
            grid.addRow(row);
        }
    });

    function onRenderer(e){
        if(e.value==-1){
			return "";
        }
        return e.value;
    }
    
    function onFlowDeliverTypeRenderer(e){
    	/*
    	var flowdefid = mini.get('flowdefid').getValue();
    	if(flowdefid){
    		this.setEnabled(false);
    	}
    	*/
    	if(e.value){
        	var o;
    		$.ajax({
                url: "<%=path%>/ajaxGetFlowDeliverType.json?flowdefid="+e.value,
                type: "post",
                async:false,
                success: function (text){
    				o = mini.decode(text)
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    
                }
            });
    		return o.text;
       	}
        return e.value;
    }
    
    function onFlowDeliverNodeidRenderer(e){
    	var flowdefid = mini.get('flowdefid').getValue();
    	if(flowdefid && e.value){
    		var o;
    		$.ajax({
                url: "<%=path%>/ajaxFlowDeliverNodeName.json?1=1&flowdefid="+flowdefid+"&nodeid="+e.value,
                type: "post",
                async:false,
                success: function (text){
    				o = mini.decode(text)
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    
                }
            });
    		return o.text;
    	}
    	return e.value;
    }
    
    
    function onCurrApprovePersonRenderer(e){
    	if(e.value){
        	var o;
    		$.ajax({
                url: "<%=path%>/ajaxGetCurrApprovePerson.json?userid="+e.value,
                type: "post",
                async:false,
                success: function (text){
    				o = mini.decode(text)
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    
                }
            });
    		return o.text;
       	}
        return e.value;
    }
    
    function onDuserApprovePersonsRenderer(e){
    	if(e.value){
        	var o;
    		$.ajax({
                url: "<%=path%>/ajaxGetDuserApprovePersons.json?userids="+e.value,
                type: "post",
                async:false,
                success: function (text){
    				o = mini.decode(text)
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    
                }
            });
    		return o.text;
       	}
        return e.value;
    }
    
    function getFlowDeliverNodeid(e){
    	var flowdefid = mini.get('flowdefid').getValue();
    	if(flowdefid){
    		var url = "<%=path%>/ajaxGetFlowDeliverNodeid.json?1=1&flowdefid="+flowdefid;
    		this.load(url);
    	}
    }
    
    function onRewardRenderer(e){
    	if(!isNaN(e.value)){
        	var o;
    		$.ajax({
                url: "ajaxGetRewardName.action?id="+e.value,
                type: "post",
                async:false,
                success: function (text){
    				o = mini.decode(text)
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    
                }
            });
    		return o.text;
       	}
        return e.value;
    }
    
    function setFlowdefid(e){
    	if(e.value){
    		mini.get('flowdefid').setValue(e.value);
    	}
    }
    
</script>
