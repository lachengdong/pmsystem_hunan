<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/jgform/iWebPDF.js"></script>
		<title>签章文件</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
</head>
<body>
		<div style="display:none;">
			<script src="<%=path%>/scripts/jgform/iWebPDF.js"></script>
		</div>
       <div class="mini-toolbar"  style="padding:2px;border-bottom:0px;">
            <table >
               <tr>
                   <td style="width:500px;">
                   </td>
	               <td style="">
	                    <input id="writtype"  name="writtype" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                         style="width:120px" emptyText="文书类别" data="writType" onvaluechanged="onWritType"/>
	                    &nbsp;&nbsp;&nbsp;&nbsp;
	                    <input id="casetype"  name="casetype" class="mini-combobox" valueField="id" textField="text"  required="true" 
	                         style="width:120px" emptyText="是否签章" data="FloderType" onvaluechanged="onCaseChanged"/>
	                    &nbsp;&nbsp;&nbsp;&nbsp;
	                    <input id="signtype"  name="signtype" class="mini-combobox" valueField="scheme" textField="name"  required="true" 
	                         style="width:120px;" url="<%=path%>/sealfile/getSignScheme.json" emptyText="签章方案" />
	                    &nbsp;&nbsp;&nbsp;&nbsp;
	                    <a class="mini-button"  style="" id="10090_041"  iconCls=""  plain="true" onclick="BatchSignature()">批量签章</a>
	                    &nbsp;&nbsp;
	                    <img src="../images/help.gif" id="imgs"></img>
	                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false" style="width:100%;height:100%;" class="mini-datagrid"  
	    	allowResize="false" url="<%=path %>/sealfile/getSealFileList.action?1=1" 
	     	idField="id" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showFilterRow="true">
	        <div property="columns">
	        	<div id="ck1" type="checkcolumn"></div>
            	<div type="indexcolumn" headerAlign="center">序号</div>
            	<div field="crimid" width="100"  headerAlign="center"	allowSort="true" align="center" >
            		罪犯编号
            	</div>
            	<div field="name" width="100"  headerAlign="center" 	allowSort="true" align="center" >
            		罪犯姓名
            		<input id="keyFilter" property="filter" emptyText="罪犯姓名查询，按回车键过滤" class="mini-textbox" onvaluechanged="onNoteKeyFilterChanged" style="width:100%;" />
            	</div>
	        	<div field="filename" width="150"  headerAlign="center" 	allowSort="true" align="center" >
            		文件名称
            	</div>
            	<div field="filepath" width="200"  headerAlign="center" 	allowSort="true" align="center" >
            		文件路径
            	</div>
            	<div field="text8" width="100"  headerAlign="center" renderer="onSignPrigress" allowSort="true" align="center" >
            		是否签章
            	</div>
            	<div field="text8_8" width="100"  headerAlign="center" 	allowSort="true" align="center" >
            		签章进程
            	</div>
            	<div field="writtype1" width="100"  headerAlign="center" renderer="getWritType" align="center" >
            		文书类型
            	</div>
            	<div name="action"  headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
	        </div>
	    </div>
    </div>
    <div id="sub" style="color:blue;position: fixed;width: 170px;height:180px;right: 0px;bottom: 0px;opacity:0.2;z-index:98;position: absolute;
             background: rgb(255,255,255);display: none;font-weight:10;">
         1、批量签章必须按照签章方案由上自下的顺序进行签章。<br />
         2、不能对某个案件重复进行签章。<br />
         3、选择"是否签章",必须选择对应的签章方案才可以查看。 <br />
         4、下级不签章,上级无法进行签章操作。 <br />
    </div>
</body>
<script type="text/javascript">
            var writType = [{id:'JXJS_JXJSSHB',text:'减刑假释审核表'},{id:'suggestreport',text:'减刑假释建议书'},{id:'casecheckreport',text:'监督意见书'}];
    	    var FloderType = [{ id: 'noSealFolderName', text: '未签章' },{ id: 'sealedFolderName', text: '已签章' }];
    	  	mini.parse();
        	var grid = mini.get("datagrid");
       		grid.reload();
			function search() {
		         var key = mini.get("key").getValue();
		         mini.get("datagrid").load("key:key");
		    }
            //选择 "是否签章"触发事件 (是否签章 应该指的是 ，某个节点(即某个级别是否签章))
	        function onCaseChanged(){
		        //是否签章 选项 
                var box = mini.get("casetype");
                var casetype = box.getValue();
                //选择 签章方案
                var scheme = mini.get("signtype").getValue();//得到选择的签章方案
    		    var schemes = scheme.split(",");
    		    var sign = 0;//选择 的签章 方案对应进程
    		    var obj = "";//不符合签章的罪犯信息 
    		    if(schemes.length == 4){
                     sign = parseInt(schemes[3]);
    			}
    			if(scheme == '' || scheme == null){
                     alert("请选择签章方案!");
                     return;
        	    }
                var grid = mini.get("datagrid");
                //如果 签章进程的值 >= 当前选择的签章进程的值 那么就说明已经签章
                grid.load({casetype:casetype,sign:sign});
		    }
		    //选择 文书 类别 触发的函数 
		    function onWritType(){
                var box = mini.get("writtype");
                var writtype = box.getValue();
                grid.load({writtype:writtype});
			}
	         //报表名称查询
			function onNoteKeyFilterChanged(e) {
				var grid = mini.get("datagrid");
		         var textbox = e.sender;
		         var key = textbox.getValue().toLowerCase();
		         grid.filter(function (row) {
		             var notekey = String(row.filename).toLowerCase();
		             if (notekey.indexOf(key) != -1) return true;
		             return false;
		         });
		     }		
		 //批量签章
		function BatchSignature(){  
			var WebPDF = document.getElementById("WebPDF");
		    var sn=WebPDF.WebGetKeySN();
		    alert(sn);
		    if(sn == ''){
	            alert("请插入未插入签章!");
		        return;
	        }
		    var rows = grid.getSelecteds();
		    //文书类型 
		    var box = mini.get("writtype");
            var writtype = box.getValue();
            if(writtype == ''){
            	writtype='JXJS_JXJSSHB';
            }
		    var scheme = mini.get("signtype").getValue();//得到选择的签章方案
		    var schemes = scheme.split(",");
		    var sign = 0;//选择 的签章 方案对应进程
		    var obj = "";//不符合签章的罪犯信息 
		    if(schemes.length == 4){
                 sign = parseInt(schemes[schemes.length-1]);
			}
		    var noSeal = [];
		    if(scheme == ""){
			    alert("请选择签章方案!");
                return;
			}
            if (rows.length > 0) {
                if (confirm("确定对选中记录进行批量签章？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        var filename = r.filename;
                        var text8 = r.text8_8;
                        //alert("text8-->"+text8+",sign-->"+sign);
                        //如果 当前选择的签章进程 - 已经签上章的签章进程 = 1 ，说明下级已经签章完成
                        if('JXJS_JXJSSHB' == writtype){
                        	if(sign-parseInt(text8) == 1 || sign-parseInt(text8) == 0){
                                ids.push(filename);
                            }else{
                            	obj += r.name+","; 
                            	if(i%4==0 && i!=0){
                                    obj+="\n";
                                }
                            }
                        }else{
                        	ids.push(filename);
                        }
                        
                    }
                    if('JXJS_JXJSSHB' == writtype){
                    	if(obj != ''){
                            alert(obj+"不符合签章规则,下级部门签章完成以后才可以进行签章!");
                            return;
                        }
                    }
                    var id = ids.join(';');
                    grid.loading("操作中，请稍后......");
                    mini.open({
		                url: "<%=path%>/sealfile/toSinaguratePage.action?1=1",
		                title: "批量签章", width: 1000, height: 600,
		                onload: function () {
		                    var iframe = this.getIFrameEl();
		                    var data = {id: id,scheme:scheme,writtype:writtype};
		                    iframe.contentWindow.SetData(data);
		                },
		                ondestroy: function (action) {
		                    grid.reload();
		                }
	            	});
                }
            } else {
                alert("请选中一条记录");
            }
		} 

    function onActionRenderer(e) {
         var grid = e.sender;
         var record = e.record;
         var filepath=record.filepath;
         var s ="";
          s='<a class="New_Button" href="javascript:checkPdf(\'' + filepath + '\')">查看</a>';
         return s;
	};
	function onSignPrigress(e){
         var grid = e.sender;
         var record = e.record;
         var text8 = record.text8;
         var text = "";
         switch(parseInt(text8)){
              case 1:text='分监区长已签章';break;
              case 2:text='分监区长指导员已签章';break;
              case 3:text='监区长已签章';break;
              case 4:text='监区教导员已签章';break;
              case 5:text='刑罚科长已签章';break;
              case 6:text='评审会已签章';break;
              case 7:text='监狱长已签章';break;
              case 8:text='监狱公章已签章';break;
              default:text ='未签章';
         }
         return text;
    }   
	function checkPdf(filepath){
		 var row = grid.getSelected();
		 mini.open({
              url: "<%=path%>/sealfile/checkPdfFilePage.action?1=1",
              title: "查看", width: 1000, height: 600,
              onload: function () {
                  var iframe = this.getIFrameEl();
                  var data = { filepath: filepath,filename:row.filename};
                 iframe.contentWindow.SetData(data);
              },
              ondestroy: function (action) {
                  grid.reload();
              }
         });
	}
	//help 
	$(document).ready(function (){
         $("#imgs").click(function (){
               $("#sub").toggle(1000);
         });
         $("#sub").click(function (){
               $(this).hide(1000);
         });
    });
    function getWritType(e){
    	var box = mini.get("writtype");
        var writtype = box.getValue();
        var s = "";
        if(writtype == 'suggestreport'){
            s = '减刑假释建议书';
        }else if(writtype == 'casecheckreport'){
        	s = '监督意见书';
        }else{
        	s = '减刑假释审核表';
        }
        return s;
    }
 </script>
</html>