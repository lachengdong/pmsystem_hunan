<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String flowdefid = request.getAttribute("flowdefid")==null?"":request.getAttribute("flowdefid").toString();
	String tempid = request.getAttribute("tempid")==null?"":request.getAttribute("tempid").toString();
	

	String topsearch = "";
	Object topsearchObj = request.getAttribute("topsearch");
	if(null != topsearchObj){
		topsearch = topsearchObj.toString();
	}
	
%>
	<head>
	    <title>罪犯考核评估总分设置页面（陕西）</title>
		<link href="<%=path %>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   		<link href="<%=path %>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>
   		<script language="JavaScript" src="<%=path%>/scripts/form/SignatureInsertNote.js"></script>
		 <link href="../demo.css" rel="stylesheet" type="text/css" />
    
    <script src="../../scripts/boot.js" type="text/javascript"></script> 
    
    <link href="../../scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
		<title></title>
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
<body>
    <div id="form1" class="mini-toolbar"  style="padding:2px;border:0;" align="right">
		<span style="padding-left: 5px;">请输入要批量设置的分数（最低50分，最高100分）</span>
		<input id="totalscore" name="totalscore" style="width: 100px" class="mini-textbox" vtype="maxLength:50;" />
		<a id="totalscorebutton" name="newgroup" class="mini-button" plain="true" onclick="setTheGivenValue1()">批量设置</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="qcpdate" name="qcpdate" format="yyyy-MM" value=${str} class="mini-monthpicker" />
		<a class="mini-button" iconCls="icon-search" plain="true" onclick="onSearch()">查询</a>
		<a class="mini-button" onclick="save()" plain="true" style="width:60px">保存</a>
		<!-- 操作说明 -->
						<a class="mini-button" plain="true" iconCls="icon-help"
							onclick="chakanshuoming('1600_001_001');"></a>
						<input id="fullopen" name="fullopen" type="hidden" value="" />
	</div>  
    
   
    <div class="mini-fit" id="div_two">
	    <div id="datagrid1" allowMoveColumn="false"  style="width:100%;height:100%;" class="mini-datagrid"  allowResize="false"  allowSortColumn="true" allowCellEdit="true"
	    	url="getKaoHeDataList.action?1=1&str=${str}"  idField="" multiSelect="true" allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"  showLoading="true" allowResize="true" 
        allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
        editNextOnEnterKey="true"  editNextRowCell="true">
	        <div property="columns">
	        	<div type="checkcolumn" width="20"></div> 
	    		<div field="RN" type="indexcolumn" headerAlign="center" align="center" allowSort="true" width="30">序号</div>
	    		<div field="crimid" headerAlign="center"  align="center" allowSort="true"width="50">罪犯编号</div>
	    		<div field="name" headerAlign="center" align="center" allowSort="true" width="60">姓名</div>
	    		<div field="qcptotalscore" headerAlign="center"  align="center" allowSort="true"width="90">分数 <input property="editor" name="qcptotalscore" class="mini-textbox" style="width:100%;" minWidth="200" /></div>
	    		
	        </div>
	        
	    </div>
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
    	var grid = mini.get("datagrid1");
        grid.load();
        
        function onActionRenderer() {
         	//var s = document.getElementById("middlebtns").value;// 资源resid 中间逗号隔开
         	var s = '${middlestr}';
    	    return s;
        }
        
	        
	        
	       function save(){
	        var obj = mini.get("datagrid1");
	 		//var rows = grid.getSelecteds();
	 		var data = grid.getChanges();
	 		var qcpdate = mini.get("qcpdate").getValue();//日期 
	 		var ids = [];
	 		if(data.length>0){
	 		var notice = data.length==1?'确定更改修改的数据？':'确定批量更改修改的数据？'; 
		    if (confirm(notice)) {
	 		  for(var i=0, l=data.length;i<l;i++){ 
		 		var r = data[i];
	 	 		   if(isNaN(r.qcptotalscore)){
	 	 			 alert('罪犯编号为'+r.crimid+'的分数输入不正确,只能输入数字！');
	 	 			 continue;
	 	 		  }else if(r.qcptotalscore > 100 || r.qcptotalscore < 50){
	 	 			alert('罪犯编号为'+r.crimid+'的分数输入不正确,分值最低为50分，最高为100分！');
	 	 			continue;
	 	 		  }else{
		 		      ids.push(r.crimid+"@"+r.qcptotalscore);
		 		      var id = ids.join(',');
		 		   }
			 	
		      }
		     }
	 		}
	 		
		     if(ids.length == '0'){
				 alert(noProcessing);
				 return ;
		    }else{
		         $.ajax( {
					url : "SaveQualityCheckData.action?1=1",
					type : "post",
					data : {
						id : id,
						qcpdate : Todate(qcpdate)			
					},
					success : function(text) {
					   grid.reload();
					   mini.alert("保存成功！");
					}
				} );
		    
		    }
	 		
	 	}
	 	
	 	function onSearch() { 
	 		var str = mini.get("qcpdate").getValue();
	 		var obj = mini.get("datagrid1");
	 		obj.setUrl("getKaoHeDataList.action?1=1&str="+Todate(str));
	        grid.load();
	   }
       function Todate(num) {
                //Fri Oct 31 18:00:00 UTC+0800 2008 
                num = num + ""; //给字符串后就一个空格
                var date = "";
                var month = new Array();
                month["Jan"] = 01; month["Feb"] = 02; month["Mar"] = 03; month["Apr"] = 04;

                month["May"] = 05; month["Jun"] = 06; month["Jul"] = 07; month["Aug"] = 08;

                month["Sep"] = 09; month["Oct"] = 10; month["Nov"] = 11; month["Dec"] = 12;
                var week = new Array();
                week["Mon"] = "一"; week["Tue"] = "二"; week["Wed"] = "三"; week["Thu"] = "四";

                week["Fri"] = "五"; week["Sat"] = "六"; week["Sun"] = "日";
                str = num.split(" "); //根据空格组成数组
                date = str[5] + "-"; //就是在2008的后面加一个“-”

                //通过修改这里可以得到你想要的格式
                if((month[str[1]])<10){
                month[str[1]]="0"+month[str[1]];
                }
                date = date + month[str[1]];
                //date=date+" 周"+week[str[0]];
                return date;
           
            }

	 
	   function setTheGivenValue1(){
	   		    var rows = grid.getSelecteds();
	            var qcpdate = mini.get("qcpdate").getValue();//日期 
				var theGivenValue = mini.get("totalscore").value;//分值
				//拼接数据字符串
				if(!theGivenValue){
				alert("请输入要设置的值")
				return;
				}else{
					if(theGivenValue>100||theGivenValue<50){
						alert("分值最低为50分，最高为100分！");
						return;
					}else if(isNaN(theGivenValue)){
						alert("只能输入数字");
						return;
					}
                   }
               if(rows.length > 0){
           		var ids = [];
                   for (var i = 0, l = rows.length; i < l; i++) {
                       var r = rows[i];
                       ids.push(r.crimid);
                   }
					var id = ids.join(',');
					$.ajax( {
					url : "UpdateQualityCheckData.action?1=1",
					type : "post",
					data : {
						id : id,
						qcpdate : Todate(qcpdate),
						theGivenValue : theGivenValue
					},
					success : function(text) {
					   grid.reload();
					   mini.alert("保存成功！");
					}
				} );
	                
				  
           	}else {
               	alert("至少选择一条数据！");
           	}  
             
	       
	    }
	 	
	        
	        
	    	
</script>
<script src="<%=path%>/scripts/publicjs.js" type="text/javascript"></script>
</body>
</html>


    