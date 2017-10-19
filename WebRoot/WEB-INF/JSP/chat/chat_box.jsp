<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="T_layer wbimBox" node-type="BoxRoot" node-data="wbimBox">
	<div class="bg">
		<!-- webim对话框 -->
		<div class="wbim_chat_box">
			<div class="wbim_chat_mutipeople">
				<div class="wbim_chat_lf">
					<a hidefocus="true" style="cursor:pointer;"
						class="wbim_scrolltop_n icon-arrow-up disable"
						node-type="wbimScrolltop"></a>
					<div class="wbim_chat_friend_box">
						<ul class="wbim_chat_friend_list" node-type="_listNode"></ul>
					</div>
					<a hidefocus="true" style="cursor:pointer;"
						class="wbim_scrollbtm_n icon-arrow-down disable"
						node-type="wbimScrollbtm"></a>
				</div>
			</div>
			<div class="wbim_chat_con">
				<div class="wbim_tit2">
					<div class="wbim_titin clearfix" node-type="wbim_titin">
						<div class="wbim_tit2_lf">
							<a class="webim_list_head webim_head_30"
								node-type="single_user_head"> <span class="head_pic"><img
									alt="" node-type="user_pic" style="margin-bottom:15px;margin-left:2px" ></span>
							</a> <span class="chat_name" node-type="user_name"></span>
						</div>
						<div class="wbim_tit2_rt">
							<a class="rt_icon icon-minus" style="cursor:pointer;"
								hidefocus="true" title="最小化" rel="tooltip" node-type="wbim_mini"></a>
							<a class="rt_icon icon-close-2" hidefocus="true" title="关闭"
								rel="tooltip" node-type="wbim_close" style="cursor:pointer;font-size:15px;"></a>
						</div>
					</div>
				</div>
				<div class="wbim_wrap">
					<!-- wbim content -->
					<div class="wbim_chat_rt">
						<div class="wbim_chat_list" node-type="wbimChatList"></div>
						<div class="wbim_inputbar">
							<div class="wbim_inputarea">
								<textarea class="wbim_textarea" node-type="wbimTextarea"
									id="wbim_textarea"></textarea>
							</div>
							<div class="wbim_controls">
								<a class="mini-button"
									style="margin-left:3px;margin-top:3px;padding:3px 15px;"
									node-type="wbim_send">发送</a>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<div node-type="chatMiniRoot" class="wbim_min_chat">
			<span class="chatMiniIcon icon-bubble-dots-4"></span> <span
				node-type="chatMiniName" style="background-color:white;"></span>
		</div>
		<div node-type="chatNotifyRoot" class="wbim_notify_chat">
			<span class="chatNotifyIcon icon-bubble-dots-4"></span> <span
				node-type="chatNotifyText" ></span>
		</div>
	</div>
</div>