<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
   		<script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
		<title>考核奖惩审批页面</title>
		<style type="text/css">
    body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }     
    </style>
   
</head>
 <script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
		document.getElementById("HWPostil1").JSEnv=1;
	</script>
<body onload="init('${menuid}');">
       <div class="mini-toolbar"  style="padding:2px;border-bottom: 0px;">
       <jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
            <table >
               <tr>
               <td style="width:100%;">
                	${topstr}
                </td>
                <td style="white-space:nowrap;">
					<!-- 操作说明 -->
		 			<a class="mini-button" plain="true" iconCls="icon-help" onclick="chakanshuoming('9924')"></a>
                </td>
               </tr>
		</table>
    </div>
    <div class="mini-fit">
	    <div id="datagrid" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true"
	    	url="getKhjcMeetingByType.action?1=1&type=${type}"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="13"></div> 
           		<div field="docid" headerAlign="center" align="left" allowSort="true" width="150px;" allowSort="false" visible="false">主键</div>
	    		<div field="meetingcontent" headerAlign="center" align="center" allowSort="true" width="20%;">合议事项</div>
	    		<div field="meetingdepname" headerAlign="center" align="center" allowSort="true" width="10%;">单位</div>
	    		<div field="meetingtime" headerAlign="center"  align="center" allowSort="true"width="10%;">合议时间</div>
	    		<div field="createtime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="10%">创建时间</div>
	    		<div field="createmender" headerAlign="center"  align="center" allowSort="true" width="30px">创建人</div>
	    		<div field="updatetime" headerAlign="center"  align="center" allowSort="true" renderer="onDateRenderer" width="10%">修改时间</div>
	    		<div field="updatemender" headerAlign="center"  align="center" allowSort="true" width="30px">修改人</div>
	   			<div name="action" headerAlign="center" align="center" renderer="onActionRenderer" width="60px">操作</div>  
	        </div>
	    </div>
    </div>
    <div showCollapseButton="false" id="editForm1" >
	       	<input id="annexcontent" name="annexcontent" class="mini-textarea"  style="width:100%; height:100%;"/>
	       	<input id="tmvorsionid" name="tmvorsionid" class="mini-hidden" />
	 	</div>
    <div style="height: 0px;">
			<table>
				<tr>
					<td height="100%">
						<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
					</td>
				</tr>
			</table>		
		</div>
    <script type="text/javascript"> 
    	mini.parse();
    	var datagrid = mini.get("datagrid");
        datagrid.load();
        datagrid.sortBy("docid", "asc");
        
        function onActionRenderer() {
         	var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
    	    return s;
         }
        function add(menuid,tempid){
        	var crimid = mini.get("crimid").getValue();
       		var name=mini.get("name").getValue();
	 		mini.open({
	 	        url: "zhuanXiangJiangFenAdd.page?1=1&tempid="+ tempid +"&crimid="+ crimid +"&name="+name+"&menuid="+menuid,
	 	        showMaxButton: true,
	 	        allowResize: false,
	 	        title: "新增", width: 900, height: 500,
	 	        onload: function () {
	 	            var iframe = this.getIFrameEl();
	 	            var data = { action: "new" };
	 	            iframe.contentWindow.SetData(data);
	 	        },
	 	        ondestroy: function (action) {
	 	        	datagrid.reload();
	 	        }
	 	   });
	 	  }
		//修改
		function xiugai(menuid) {
			var row = datagrid.getSelected();//获取选中记录
			var crimid = mini.get("crimid").getValue();
	    	mini.open({
                url: "khjcMeetingLook.action?1=1&docid="+row.docid+"&menuid="+menuid,
                showMaxButton: true,
             	allowResize: false, 
                title: "修改", width: 900, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action:"edit" };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action){
                    datagrid.reload();
                }
            });
		}
	    
	    //批量操作，提交,退回,拒绝
	    function piliangcaozuo(state){
	    	var str = "";
	    	if(state == 'yes'){
	    		str = "批量提交";
	    	}else if (state == 'no'){
	    		str = "批量否决";
	    	}else if (state == 'back') {
	    		str = "批量退回";
	    	}
	    	var rows = datagrid.getSelecteds();
	    	if (rows.length > 0 && str != "") {
	    		if (confirm("确定【"+str+"】选中记录吗？")) {
	    			var docidArr = [];
                    for (var i = 0 ;i < rows.length; i++) {
                    	var r = rows[i];
                        docidArr.push(r.docid);
                    }
                    $.ajax({
                        url: "khjcPiLiangCaoZuo.action?1=1",
                        type: "post",
                        data: {docidArr:docidArr+"",state:state},
                        success: function (text) {
                        	var result = eval(text);
                        	if(result =="success"){
                        		alert("操作成功！");
                        	}else{
                        		alert(text);
                        	}
                            datagrid.reload();
                        },
                        error: function () {
                        	alert("操作失败!");
                        }
                    });
	    		}
	    	}else{
	    		alert("请选择需要批量操作的记录！");
	    	}
	    }
	    
	    /*
	    *批量签章(传入签章方案主键)
	    */
	    function piliangqianzhang(signaid){
	    var form = new mini.Form("editForm1");
	    	if(signaid != ""){
	    		var content;
	    		var newcontent;
	    		var signatureinfor;
	    		var rows = datagrid.getSelecteds();
	    		if (rows.length > 0) {
	    			if (confirm("确定批量签章选中记录吗？")) {
	    				//选中记录和签章方案都不为空，则进行签章操作
	                    for (var i = 0 ;i < rows.length; i++) {
	                    	var r = rows[i];
	                    	onqianzhang(r.docid,signaid);//通过docid找到原始的大字段
	                    }
	    				return false;
	    			}
	    		}else{
	    			alert("请选择需要批量操作的记录！");
	    			return false;
	    		}
	    	}else{
	    		alert("未指定签章方案！");
	    		return false;
	    	}
	    }
	    
	    /*
	    * 签章
	    */
	    function onqianzhang(docid,signaid) {
	     var returnValue = "";
               $.ajax({
                   url:"getDocContentByDocid.action?docid="+docid+"&signaid="+signaid,
                   type: 'POST',
                   success: function (text) {
                   	  getSingaBySingaid(docid,text,signaid);
                   },
                   error: function () {
                   	  alert("获取大字段失败!");
                   	  return false;
                   }
               });
        }
        
        
	    /*
	    * 获取签章方案
	    */
	    function getSingaBySingaid(docid,content,singaid) {
               $.ajax({
                   url:"getSingaBySingaid.action?singaid="+singaid,
                   type: 'POST',
                   success: function (text) {
                   	 singaDocContent(docid,content,text);
                   },
                   error: function () {
                   	  alert("获取签章方案失败!");
                   	  return false;
                   }
               });
        }
	    
	    	/*
	    	*  加盖印章
	    	*/
	    	 function singaDocContent(docid,annexcontent,signatureinfor){
	    	    var objs1 = eval('('+annexcontent+')');
	    	    var objs2 = eval('('+signatureinfor+')');
	        	var aipObj=document.getElementById("HWPostil1");
	        	var signatureinfor=objs2.signatureinfor;
	        	var annexcontent=objs1.doccontent;
	        	var sealcontent= "";//签章默认意见
	        	var sealdate= "";//签章页面日期
	        	var cppoopiniondate= "";//签章方案里的日期
	        	var cppoopinion= "";//从意见信息表里取的意见
				aipObj.LoadFileBase64(annexcontent);
				var dtrer=0;
				aipObj.Logout();
				dtrer = aipLogin(aipObj);
				if(dtrer==-200)
				{
					alert("未发现智能卡");
				}
				else if(dtrer!=0){
					alert("登录失败。错误ID:"+dtrer);
				}
				else
				{
					aipObj.SilentMode = true;
					if(aipObj.IsOpened()){
						aipObj.ForceSignType=7;
						aipObj.PageSetupSet(0,0,0,0,0,0,0,0);
						var strSignatureInfoArray = signatureinfor.split("@");
						if(null!=signatureinfor&&signatureinfor!=''&&strSignatureInfoArray.length>0)
						{
							for(var i=0;i<strSignatureInfoArray.length;i++)
							{
								var sealcontent="";
								var sealdate="";
								var cppoopiniondate="";
								var cppoopinion="";
								InsertNote(strSignatureInfoArray[i],aipObj,sealcontent,sealdate,cppoopiniondate,cppoopinion);
							}
							//保存签章文件
							var aipFileStr=aipObj.GetCurrFileBase64();
							$.ajax({
								type: 'POST',
		                        url: "updateDocByDocid.action?1=1",
		                        data: { doccontent:aipFileStr,docid:docid},
		                        //async:false,
		                        success: function (text) {
			                        currentvalue++;
	                    			oneqianzhang(url,ids,currentvalue,menuid);		                        
		                        },
		                        error: function () {
		                        	alert("操作失败!");
		                        }
		                    });
						}
					}
				}
	        }
	    	
	    	/*
	    	* 更新大字段(doc主键，大字段)
	    	*/
	    	function saveDocByDocid(docid,newcontent){
	    	
	    	}
</script>
</body>
</html>


    