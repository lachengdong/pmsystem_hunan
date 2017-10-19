var P = {
	CurrentUser : '',
	CurrentUserName : '',
	Dom : {
		f : $('#footer'),
		cf : $('#container'),
		online : $('#onlineStatus')
	},
	// 存储基本信息
	Base : {
		onlineStatus : {
			'0' : {
				'desc' : '在线',
				'className' : 'T_online_status_online'
			},
			'1' : {
				'desc' : '离开',
				'className' : 'T_online_status_away'
			},
			'2' : {
				'desc' : '忙碌',
				'className' : 'T_online_status_busy'
			},
			'3' : {
				'desc' : '请勿打扰',
				'className' : 'T_online_status_donttrouble'
			},
			'4' : {
				'desc' : '隐身',
				'className' : 'T_online_status_invisible'
			},
			'5' : {
				'desc' : '离线',
				'className' : 'T_online_status_offline'
			}
		},
		recentMsgNum : 40,
		highlightCss : 'T_highlight',
		title : {
			'message' : '新微讯',
			'notification' : '新提醒',
			'msg&ntf' : '新微讯和提醒'
		}
	},
	Status : {
		sidePanelColse : $.cookie('PsidePanelColse') == "true" ? $
				.cookie('PsidePanelColse') : "false",
		headNavInside : "false"
		// skin: $CONFIG['defaultSkin']
	},
	// webim的记忆状态
	Chat : {},
	Timer : {
		mon : {
			'message' : {
				name : null,
				time : 1000
			},
			'notification' : {
				name : null,
				time : 1000
			}
		},
		browserTitle : {
			name : null,
			time : 1000
		},
		taskBarSpeed : 300,
		notificationBox : {
			name : null,
			time : 3000
		},
		fbox : {
			show : {
				name : null,
				time : 500
			},
			trigger : {
				name : null,
				time : 500
			}
		},
		weatherBox : {
			show : {
				name : null,
				time : 500
			},
			trigger : {
				name : null,
				time : 500
			}
		},
		skinBox : {
			show : {
				name : null,
				time : 500
			},
			trigger : {
				name : null,
				time : 500
			}
		},
		portalBox : {
			show : {
				name : null,
				time : 500
			},
			trigger : {
				name : null,
				time : 500
			}
		},
		recentlist : {
			show : {
				name : null,
				time : 500
			},
			trigger : {
				name : null,
				time : 500
			}
		},
		personBox : {
			show : {
				name : null,
				time : 500
			},
			trigger : {
				name : null,
				time : 500
			}
		},
		timeBox : {
			show : {
				name : null,
				time : 500
			},
			trigger : {
				name : null,
				time : 500
			}
		},
		logoutBox : {
			show : {
				name : null,
				time : 500
			},
			trigger : {
				name : null,
				time : 500
			}
		}
	},
	cookieExpires : {
		'PsidePanelColse' : null
	},
	AppCate : {},
	App : {},
	ws : null,
	isConnect : false,

	init : function() {
		var kedit1 = CKEDITOR.replace('wbim_textarea', {
					toolbar : [

							['Bold', 'Italic', '-', 'NumberedList',
									'BulletedList'], ['Smiley', 'SpecialChar']],
					height : '95px',
					margin : '0px',
					startupFocus : true
				});
		this.webIm.input = kedit1;
		var self = this;

		CKEDITOR.instances["wbim_textarea"].on("instanceReady", function(e1) {
					if (this.document.$.addEventListener) {
						this.document.$.addEventListener('keydown',
								function(e) {
									if (e.keyCode == 13 && e.ctrlKey) {
										// alert('按下了ctrl+Enter');
										self.webIm.sendMsg();
									}
								}, false);
					} else if (this.document.$.attachEvent) {
						this.document.$.attachEvent('onkeydown', function(e) {
									if (e.keyCode == 13 && e.ctrlKey) {
										// alert('按下了ctrl+Enter');
										self.webIm.sendMsg();
									}
								}, false);
					}
				});

		// 初始化在线聊天，延时加载
		setTimeout($.proxy(function() {
							this.webIm.init();
						}, this), 1000);
		this.connect();
	},

	connect : function() {
		var url = $CONFIG['path'] + '/chat.action';
		var self = this;
		if (window.location.protocol == 'http:') {
			url = 'ws://' + window.location.host + url;
		} else {
			url = 'wss://' + window.location.host + url;
		}
		if ('WebSocket' in window) {
			this.ws = new WebSocket(url);
		} else if ('MozWebSocket' in window) {
			this.ws = new MozWebSocket(url);
		} else {
			url = $CONFIG['basePath'] + 'sockjs/chat.action';
			this.ws = new SockJS(url, undefined, {
						protocols_whitelist : ['xhr-streaming']
					});
		}
		this.ws.onopen = function() {
			this.isConnect = true;
			var dom_online = self.Dom.online;			
			dom_online.removeClass("status_offline");
			dom_online.removeClass("status_online");
			dom_online.addClass("status_online");
			dom_online.attr("title", "及时消息（已上线）");
		};

		this.ws.onmessage = function(event) {
			var data = $.parseJSON(event.data);
			if (data.msgType == 1) // 新用户上线
			{
				mini.showMessageBox({
							showModal : false,
							width : 250,
							title : "提示",
							message : data.createusername + "已上线！",
							timeout : 3000,
							x : 'right',
							y : 'bottom'
						});
				return;
			}

			self.webIm.onMessage(data);
		};
		this.ws.onclose = function(event) {
			this.isConnect = false;
			var dom_online = self.Dom.online;
			dom_online.removeClass("status_offline");
			dom_online.removeClass("status_online");
			dom_online.addClass("status_offline");			
			dom_online.attr("title", "及时消息（已断线）");
		};
	},

	disconnect : function() {
		if ('WebSocket' in window) {
			if (this.ws != null) {
				this.ws.close();
				this.ws = null;
			}
		} else {
			var data = {
				createUser : this.CurrentUser,
				createusername : this.CurrentUserName,
				msgType : 4,
				toUid : '',
				content : ''
			};
			this.ws.send($.toJSON(data));
			this.ws.close();
			this.ws = null;
		}
	},

	reconnectAndSendMsg : function(msg) {
		var url = $CONFIG['path'] + '/chat.action';
		var self = this;
		if (window.location.protocol == 'http:') {
			url = 'ws://' + window.location.host + url;
		} else {
			url = 'wss://' + window.location.host + url;
		}
		if ('WebSocket' in window) {
			this.ws = new WebSocket(url);
		} else if ('MozWebSocket' in window) {
			this.ws = new MozWebSocket(url);
		} else {
			url = $CONFIG['basePath'] + 'sockjs/chat.action';
			this.ws = new SockJS(url, undefined, {
						protocols_whitelist : ['xhr-streaming']
					});
		}
		this.ws.onopen = function() {
			self.isConnect = true;
			var dom_online = self.Dom.online;
			if (dom_online.hasClass("status_offline")) {
				dom_online.removeClass("status_offline");
			}
			if (!dom_online.hasClass("status_online")) {
				dom_online.addClass("status_online");
			}
			dom_online.attr("title", "及时消息（已上线）");
			self.ws.send(msg);
		};

		this.ws.onmessage = function(event) {
			var data = $.parseJSON(event.data);
			if (data.msgType == 1) // 新用户上线
			{
				return;
			}
			self.webIm.onMessage(data);
		};
		this.ws.onclose = function(event) {
			self.isConnect = false;
			var dom_online = self.Dom.online;
			if (dom_online.hasClass("status_online")) {
				dom_online.removeClass("status_online");
			}
			if (!dom_online.hasClass("status_offline")) {
				dom_online.addClass("status_offline");
			}
			dom_online.attr("title", "及时消息（已断线）");
		};
	},

	deletenotify : function(id) {
		if (this.Chat.notifyItems[id]) {
			clearInterval(this.Chat.notifyItems[id]);
			delete this.Chat.notifyItems[id];
		}

	},
	// webim
	webIm : {
		dom : $("[node-type='BoxRoot'][node-data='wbimBox']"),
		mutiCss : "wbim_muti",
		mutiTemplate : '<li node-type="_root"><div class="list_head_state"></div><div class="wbim_username" node-type="_nickNode"></div><a class="wbim_icon_wbclose icon-close-2" href="javascript:void(0);" hidefocus="true" node-type="_closeBtn"></a></li>',
		msgTemplate : '<div class="chat_item me"><div class="chat_time"></div><div class="cloud cloudText"><div class="cloudPannel"><div class="sendStatus"></div><div class="cloudBody"><div class="cloudContent"></div></div><div class="cloudArrow"></div></div></div></div>',
		msgDiaLineTemplate : '<div class="wbim_dia_line"></div>',
		pane : $("[node-type='wbimChatList']"),
		faceContent : $("#face_wrap"),
		input : null,
		expandCss : "wbim_exp",
		miniCss : "wbim_mini",
		notifyCss : "wbim_notify",
		hasReceive : false,
		init : function() {
			var self = this;
			// 读取cookie
			P.Chat = $.cookie(P.CurrentUser + "PwebIm") !== undefined ? $
					.evalJSON($.cookie(P.CurrentUser + "PwebIm")) : {
				"active" : false,
				"items" : [],
				"notifyItems" : {},
				"mini" : false,
				"notify" : false,
				"max" : false,
				"keep" : false
			};

			P.Chat.active = false;
			P.Chat.items = [];

			// 初始化位置
			if (!($.cookie('PsidePanelColse') == "true")) {
				this.expand();
			}
			// P.face.init();
			// 切换聊天
			this.dom.find("[node-type='_listNode']").delegate(
					"[node-type='_root']", 'click', function() {
						self.activate({
									"id" : $(this).attr("node-data-id"),
									"name" : $(this).attr("node-data-name"),
									"avatar" : $(this).attr("node-data-avatar")
								});
					});

			// 最小化
			this.dom.find("[node-type='wbim_mini']").on('click', function() {
						self.mini();
					});

			// 最大化
			this.dom.find("[node-type='chatMiniRoot']").on('click', function() {
						self.max();
					});

			// 新消息
			this.dom.find("[node-type='chatNotifyRoot']").on('click',
					function() {
						var data = $(this).data('item');
						$.each(data, function(i, n) {
									if (!self.exists(data[i].id)) {
										P.Chat.items.push({
													"id" : parseInt(data[i].id),
													"name" : data[i].name,
													"avatar" : data[i].avatar
												});
										self.add(data[i]);
									}
								});
						self.startChat(data[0]);
						P.browserTitle.reset();
					});

			// 关闭
			this.dom.find("[node-type='wbim_close']").on('click', function() {
						self.close();
					});

			// 发送微讯
			this.dom.find(".mini-button").on('click', function() {
						if ($.trim(self.input.getData()) == "") {
							alert("发送内容不能为空");
						} else {
							self.sendMsg();
						}
					});

			// 结束会话
			this.dom.find("[node-type='_listNode']").delegate(
					"[node-type='_closeBtn']", 'click', function() {
						self.endChat($(this).parent("li"));
						return false;
					});
			var menu_time_height = 32;
			var scroll_height = 4 * menu_time_height;
			// 点击向上滚动按钮
			this.dom.find("[node-type='wbimScrolltop']").on('click',
					function() {
						var listNode = self.dom.find("[node-type='_listNode']");
						var ul = listNode.parent();
						ul.animate({
									'scrollTop' : (ul.scrollTop() - scroll_height)
								}, 600);
						if (ul.height() < listNode.height()) {
							$(this).addClass("disable");
							self.dom.find("[node-type='wbimScrollbtm']")
									.removeClass("disable");
						}
					});

			// 点击向下滚动按钮
			this.dom.find("[node-type='wbimScrollbtm']").on('click',
					function() {
						var listNode = self.dom.find("[node-type='_listNode']");
						var ul = listNode.parent();
						ul.animate({
									'scrollTop' : (ul.scrollTop() + scroll_height)
								}, 600);
						if (ul.height() < listNode.height()) {
							$(this).addClass("disable");
							self.dom.find("[node-type='wbimScrolltop']")
									.removeClass("disable");
						}
					});

			// //选择表情
			// this.dom.find("[node-type='face']").on('click', function() {
			// P.face.init();
			// });
			// 刷新页面时渲染
			// var PwebIm = $.cookie("PwebIm");
			// this.pane.jScrollPane({"animateScroll": true, "autoReinitialise":
			// true});
			this.pane.niceScroll({
						cursorcolor : "#ccc"
					});
			this.faceContent.niceScroll({
						cursorcolor : "#ccc"
					});

			// if (PwebIm !== undefined) {
			// var PwebImObj = $.evalJSON(PwebIm);
			// if (PwebImObj.active)
			// this.build();
			// }

		},
		getUnreadMsg : function(id) {
			var self = this;
			var chatArea = self.chatArea();
			$.ajax({
						type : "post",
						url : $CONFIG['url_getUnreadMsg'],
						data : {
							createUser : id,
							// limit : P.Base.recentMsgNum TODO:以后再加
							toUid : P.CurrentUser
						},
						beforeSend : function() {
							// todo 加载提示文字
						},
						success : function(data) {
							if (data == '')
								return;
							$.each(data, function(i, n) {
										chatArea
												.append(self.msgformat(data[i]));
									});
							chatArea.append(self.msgDiaLineTemplate);
							self.pane.niceScroll({
										cursorcolor : "#ccc"
									});
							self.pane.getNiceScroll()[0].resize();
							self.pane.getNiceScroll()[0].scrollTop(self.pane
									.getNiceScroll()[0].page.maxh);

							data2 = {
								createUser : id,
								msgType : 3,
								toUid : P.CurrentUser
							};
							// 更新消息已读标志
							P.ws.send($.toJSON(data2));
						}
					});
		},

		// 新消息
		NmsgStore : {},
		// 历史消息
		HmsgStore : {},
		onMessage : function(data) {
			this.hasReceive = true; // ie8下，仅仅用来检测client端连接正常
			if (!P.Chat.active) // 即时消息窗口未打开
			{
				var dom_online = P.Dom.online;
				if (!this.NmsgStore[data.createUser]) {
					this.NmsgStore[data.createUser] = [];
					if (!P.Chat.notifyItems) {
						P.Chat.notifyItems = {};
					}
					P.Chat.notifyItems[data.createUser] = P.util.highlight2(
							dom_online, "T_highlight");
				}
				this.NmsgStore[data.createUser].push(data);
				return;
			}

			var chatArea = this.chatArea();
			chatArea.append(this.msgformat(data));
			this.pane.niceScroll({
						cursorcolor : "#ccc"
					});
			this.pane.getNiceScroll()[0].resize();
			this.pane.getNiceScroll()[0]
					.scrollTop(this.pane.getNiceScroll()[0].page.maxh);

			if (data.createUser != P.CurrentUser) // 不是自己写给自己的消息,发送回执消息
			{
				data.msgType = 3;
				P.ws.send($.toJSON(data));
			}

		},
		activate : function(d) {
			var chatArea1 = this.chatArea();
			// 存储当前的聊天记录
			if (P.Chat.chatWithId) {
				this.HmsgStore[P.Chat.chatWithId] = chatArea1.html();
			}

			// 切换用户图片、姓名以及头像的地址
			this.dom.find("[node-type='user_pic']").attr("src", d.avatar);
			this.dom.find("[node-type='user_name']").text(d.name);
			// this.dom.find("[node-type='single_user_head']").attr("href",$CONFIG['url_userDetail'].replace('pk',
			// d.id));

			// 显示聊天记录
			if (this.HmsgStore[d.id]) {
				chatArea1.html(this.HmsgStore[d.id]);
			} else {
				chatArea1.empty();
				// 上服务器查询离线消息
				this.getUnreadMsg(d.id);
			}

			// 显示已经接收的新消息
			if (this.NmsgStore[d.id]) {
				$.each(this.NmsgStore[d.id], $.proxy(function(i, n) {
									var chatArea = this.chatArea();
									chatArea.append(this.msgformat(n));
								}, this));
				this.pane.niceScroll({
							cursorcolor : "#ccc"
						});
				this.pane.getNiceScroll()[0].resize();
				this.pane.getNiceScroll()[0].scrollTop(this.pane
						.getNiceScroll()[0].page.maxh);
				this.NmsgStore[d.id] = null;

			}

			var api = this.pane.getNiceScroll()[0];
			api.resize();
			api.scrollTop(api.page.maxh);
			// 更新对话列表状态
			this.dom.find("[node-type='_listNode'] [node-type='_root']")
					.removeClass("wbim_active");
			this.dom
					.find("[node-type='_listNode'] [node-type='_root'][node-data-id='"
							+ d.id + "']").addClass("wbim_active");

			// 记录当前收信人
			P.Chat.chatWithId = d.id;
			if (!P.Chat.active)
				P.Chat.active = true;
			this.saveCookie();
			setTimeout($.proxy(function() {
								this.input.focus();
							}, this), 500);
		},
		build : function() {
			var _tmp = {};
			if (P.Chat.items) {
				$.each(P.Chat.items, $.proxy(function(i, n) {
									this.add(n, false);
									if (n.id == P.Chat.chatWithId) {
										_tmp = n;
									}
								}, this));
			} else {
				return;
			}

			if (P.Chat.mini) {
				this.mini();
			} else {
				this.activate(_tmp);
			}
			this.dom.show();
		},
		startChat : function(d) {
			if (P.Chat.items && this.exists(d.id)) {
				this.max();
				this.activate(d);
				return;
			} else {
				P.Chat.items.push({
							"id" : d.id,
							"name" : d.name,
							"avatar" : d.avatar
						});
			}
			this.max();
			this.add(d, true);
			this.activate(d);
			this.dom.show();

		},
		endChat : function(obj) {
			var id = obj.attr("node-data-id");
			var p = obj.prev();
			var n = obj.next();
			obj.remove();
			$.each(P.Chat.items, function(i, n) {
						if (n.id == id) {
							P.Chat.items = P.util.arrayDel(P.Chat.items, i);
							return false;
						}
					});
			this.saveCookie();
			if (P.Chat.items.length == 1) {
				this.dom.removeClass(this.mutiCss);
			}

			if (n.length > 0) {
				n.trigger("click");
			} else {
				p.trigger("click");
			}
		},
		add : function(d, a) {
			var tmp = $(this.mutiTemplate);
			var t = P.Chat.items.length;
			if (t > 1 && !this.dom.hasClass(this.mutiCss))
				this.dom.addClass(this.mutiCss);
			tmp.attr({
						"title" : d.name,
						"node-data-id" : d.id,
						"node-data-name" : d.name,
						"node-data-avatar" : d.avatar
					});

			tmp.find("[node-type='_nickNode']").text(d.name);
			this.dom.find("[node-type='_listNode']").append(tmp);

			// 如果立刻激活
			if (a) {
				this.dom.find("[node-type='user_pic']").attr("src", d.avatar);
				this.dom.find("[node-type='user_name']").text(d.name);
			}

			var menu_time_height = 32;
			var scroll_height = 2 * menu_time_height;

			var listNode = this.dom.find("[node-type='_listNode']");
			var ul = listNode.parent();
			ul.animate({
						'scrollTop' : (ul.scrollTop() + scroll_height)
					}, 600);
			if (ul.height() < listNode.height()) {
				this.dom.find("[node-type='wbimScrollbtm']")
						.addClass("disable");
				this.dom.find("[node-type='wbimScrolltop']")
						.removeClass("disable");
			}

		},
		newMsg : function(d) {
			// 如果已有此人
			var self = this;
			if (P.Chat.items && this.exists(d.id)) {
				if (d.id == P.Chat.chatWithId) {
					self.activate(d);
					P.browserTitle.blink('message');
					setTimeout(function() {
								P.browserTitle.reset();
							}, 3000);
				} else {

				}
			}
		},
		sendMsg : function() {
			var self = this;
			var send_btn = this.dom.find("[node-type='wbim_send']");
			var c = this.input;
			if (c.getData() != "") {
				data = {
					createUser : P.CurrentUser,
					createusername : P.CurrentUserName,
					msgType : 2, // 一般消息
					toUid : P.Chat.chatWithId,
					content : c.getData()
				};
				send_btn.button('loading').off("click");
				this.hasReceive = false;
				P.ws.send($.toJSON(data));
				this.checkReceive($.toJSON(data), false);
				send_btn.button('reset').on("click", function() {
							self.sendMsg();
						});

				c.setData('', function() {
							if (this.document.$.addEventListener)
								this.document.$.addEventListener('keydown',
										keydown, false);
							else if (this.document.$.attachEvent) {
								this.document.$.attachEvent('onkeydown',
										keydown, false);
							}

						});
				setTimeout($.proxy(function() {
									this.input.focus();
								}, this), 100);
			};
			function keydown(e) {
				if (e.keyCode == 13 && e.ctrlKey) {
					self.sendMsg();
				}
			}
		},

		checkReceive : function(msg, force) {
			// 如果不强制，并且当前浏览器支持websocket，则不需检测是否否发送成功
			if (!force && 'WebSocket' in window)
				return;
			var t = 0;
			var n = null;
			var _this = this;
			n = setInterval(function() {
						if (t == 5 || _this.hasReceive) {
							clearInterval(n);
							n = null;
							if (!_this.hasReceive) // 如果还没有接收到回应，重新连接server并重发数据
							{
								P.reconnectAndSendMsg(msg);
							}
							return;
						}
						t++;
					}, 500);
		},

		msgformat : function(obj) {
			var tpl = $(this.msgTemplate);
			if (obj != null) {
				if (obj.createUser != P.CurrentUser) // 不是自己当前写的信息
					tpl.attr("class", "chat_item you");
				tpl.find(".chat_time").text(obj.createusername + "   "
						+ obj.time);
				// tpl.find(".cloud").attr("msgid", obj.id);
				tpl.find(".cloudContent").html(obj.content);
				return tpl;
			}
		},
		close : function() {
			this.reset();
			this.dom.hide();
		},
		mini : function() {
			$.each(P.Chat.items, $.proxy(function(i, n) {
				if (n.id == P.Chat.chatWithId) {
					this.dom
							.find("[node-type='chatMiniRoot'] [node-type='chatMiniName']")
							.text(n.name);
					return false;
				}
			}, this));
			P.Chat.mini = true;
			P.Chat.active = false;
			this.dom.addClass(this.miniCss);
			this.saveCookie();
		},
		max : function() {
			P.Chat.mini = P.Chat.notify = false;
			P.Chat.max = true;
			P.Chat.active = true;
			this.dom.removeClass(this.miniCss).removeClass(this.notifyCss);
			this.pane.niceScroll({
						cursorcolor : "#ccc"
					});
			this.pane.getNiceScroll()[0].resize();
			this.pane.getNiceScroll()[0]
					.scrollTop(this.pane.getNiceScroll()[0].page.maxh);
			this.saveCookie();

		},
		notify : function(d, c) {

			if (P.Chat.max) {
				// 直接加人

			} else {

			}
			if (!this.dom.hasClass(this.notifyCss))
				this.dom.addClass(this.notifyCss);
			this.notifyDom = this.dom.find("[node-type='chatNotifyRoot']");
			this.notifyDom.find("[node-type='chatNotifyText']").text("您有" + c
					+ "条新微讯");
			this.notifyDom.data('item', d);
			this.dom.show();
			P.util.highlight(this.notifyDom.find(".chatNotifyIcon"));
		},
		exists : function(id) {
			var flag = false;
			$.each(P.Chat.items, function(i, n) {
						if (n.id == id) {
							flag = true;
							return false;
						}
					});
			return flag;
		},
		expand : function() {
			this.dom.addClass(this.expandCss);
		},
		collaspe : function() {
			this.dom.removeClass(this.expandCss);
		},
		reset : function() {
			this.dom.find("[node-type='_listNode'] [node-type='_root']")
					.remove();
			this.dom.removeClass(this.mutiCss);
			P.Chat = {
				"active" : false,
				"items" : [],
				"mini" : false,
				"max" : false,
				"notify" : false,
				"keep" : false
			};
			$.removeCookie(P.CurrentUser + 'PwebIm', {
						path : '/'
					});
		},
		saveCookie : function() {
			$.cookie(P.CurrentUser + "PwebIm", $.toJSON(P.Chat), {
						'path' : '/'
					});
		},
		refresh : function() {
			$.cookie('PsidePanelColse') == "true" ? this.collaspe() : this
					.expand();
		},
		chatArea : function() {
			return $("[node-type='wbimChatList']");
		}
	},

	util : {
		arrayDel : function(a, n) {
			if (n < 0)
				return a;
			else
				return a.slice(0, n).concat(a.slice(n + 1, a.length));
		},
		highlight : function(dom) {
			var css = P.Base.highlightCss;
			var t = 0;
			var n = null;
			dom.removeClass(css);
			n = setInterval(function() {
						if (t == 5) {
							clearInterval(n);
							n = null;
							return;
						}
						if (!dom.hasClass(css))
							dom.addClass(css);
						else
							dom.removeClass(css);
						t++;
					}, 500);
		},
		highlight2 : function(dom, css) {
			dom.removeClass(css);
			var n = setInterval(function() {
						if (!dom.hasClass(css))
							dom.addClass(css);
						else
							dom.removeClass(css);
					}, 500);
			return n;
		}
	}

};