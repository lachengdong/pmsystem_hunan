<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>会议申请查看</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/scripts/jquery.multifile.js"></script>

<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
}

label {
	float: left;
	font-weight: normal;
	width: 85px;
	text-align: right;
	margin-right: 10px;
}

.file-selector {
	padding-left: 0 !important;
	position: relative;
}

.file-selector input[type=file] {
	position: absolute;
	left: 0;
	top: 0;
	height: 100%;
	overflow: hidden;
	width: 100%;
	padding: 0;
	opacity: 0;
	filter: alpha(opacity = 0);
	cursor: pointer !important;
}
</style>
</head>
<body>
<div class="mini-fit">
		<div id="yw1" class="td-nav">
			<a style="padding-left:0;padding-right:0; text-decoration:none;color:#000;font-size:13px;"
				class="btn btn-link" id="SynodTitle">会议申请</a>
			<div class="pull-right">
				
				<a class="btn btn-default btn-sm" style="padding:2px 10px;"
					href="javascript:gotSynodList();">返回</a>
			</div>
			
		</div>
		<div class="mini-fit">
			<div id="frmSynod" style="margin-top:15px;">
				<div class="control-group ">
					<input name="actionType" id="actionType" class="mini-hidden" /> <input
						name="mid" id="mid" class="mini-hidden" /> 
						<label class="control-label">会议名称</label>
					<div class="controls">
						<textarea class="mini-textarea" id="name" name="name"
							style="margin-bottom:0;
							font-size:12px;width:180px;height:35px;margin-bottom:6px;"></textarea>
					</div>
				</div>
				<div class="control-group ">
					<label class="control-label">会议主题</label>
					<div class="controls">
						<textarea class="mini-textarea" id="topic" name="topic"
							style="margin-bottom:0;
							font-size:12px;width:240px;height:35px;margin-bottom:6px;"></textarea>
					</div>
					</div>
					<div class="control-group ">
					<label class="control-label">出席人员(外部)</label>
					<div class="controls">
						<textarea class="mini-textarea" id="exteriorapp" name="exteriorapp"
							style="margin-bottom:0;
							font-size:12px;width:240px;height:30px;margin-bottom:6px;"></textarea>
					</div>
					</div>
				<div class="control-group ">
					<label class="control-label">出席人员(内部)</label>
					<div class="controls">
						<textarea class="mini-textarea" id="withinhuman" name="withinhuman" readOnly="readOnly"
								style="margin-bottom:0;font-size:10px;width:280px;height:30px;margin-bottom:4px;"></textarea>
						
					</div>
				</div>
				<div class="control-group" >
					<label class="control-label">会议室名称</label>
					<div class="controls">
					<textarea class="mini-textarea" id="divanname" name="divanname"
							style="margin-bottom:0;
							font-size:12px;width:240px;height:35px;margin-bottom:6px;"></textarea>
							</div>	
							</div>
				<div class="control-group">
					<label class="control-label">会议室设备</label>
					<div class="controls">
					<textarea class="mini-textarea" id="fixture" name="fixture"
							style="margin-bottom:0;
							font-size:12px;width:240px;height:35px;margin-bottom:6px;"></textarea>	
					</div>
				</div>
			<div class="control-group">
					<label class="control-label">开始时间</label>
					<div class="controls">
				<input id="begin" name="begin" showClearButton="true" class="mini-datepicker"
		                  format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" style="width:130px;"/> 
						</div>
						<label class="control-label">结束时间</label>
					<div class="controls">
				<input id="finish" name="finish" format="yyyy-MM-dd H:mm" timeFormat="H:mm" 
		              showTime="true" showClearButton="true" class="mini-datepicker" style="width:130px;"/>
						</div>
						</div>	
				<div class="control-group">
					<label class="control-label">附件文档</label>
					<div class="controls">
						<textarea class="mini-textarea" id="document" name="document"
							style="margin-bottom:0;
							font-size:12px;width:300px;height:35px;margin-bottom:6px;"></textarea>
							</div>
							</div>		
							<div class="control-group ">
					<label class="control-label">会议描述</label>
					<div class="controls">
						<textarea class="mini-textarea" id="depict" name="depict"
							style="margin-bottom:0;
							font-size:12px;width:300px;height:35px;margin-bottom:6px;"></textarea>
					</div>
					</div>	
	</div>
	</div>
</div>
	
		
</body>
</html>
