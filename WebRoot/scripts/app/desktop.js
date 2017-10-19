var rowAppNum = 8;

window.onactive = function() {
	$(window).triggerHandler('resize');
	window.onactive = null;
};
// 判断有没有存在数组中
function inArray(e, o) {
	var flag = false;
	$.each(o, function(i, n) {
				if (typeof(o[i]) == 'array' || typeof(o[i]) == 'object') {
					if (inArray(e, o[i])) {
						flag = true;
						return false;
					}
				} else {
					if (e == o[i]) {
						flag = true;
						return false;
					}
				}
			});
	return flag;
}

// 过滤重复js元素 return array;
function unique(d) {
	var o = {};
	$.each(d, function(i, e) {
				o[e] = i;
			});
	var a = [];
	$.each(o, function(i, e) {
				a.push(d[e]);
			});
	return a;
}

function isTouchDevice() {
	try {
		document.createEvent("TouchEvent");
		return true;
	} catch (e) {
		return false;
	}
}

// 添加桌面应用 e {"func_id": ,"id": ,"name":} index 为要添加应用的屏幕索引
function addApp(e, index) {
	var s = slideBox.getScreen(index);
	if (s) {
		var ul = s.find("ul");
		if (!ul.length) {
			ul = $("<ul></ul>");
			s.append(ul);
			ul.sortable({
						revert : true,
						// delay: 200,
						distance : 10, // 延迟拖拽事件(鼠标移动十像素),便于操作性
						tolerance : 'pointer', // 通过鼠标的位置计算拖动的位置*重要属性*
						//connectWith : ".screen ul",
						scroll : false,
						stop : function(e, ui) {
							setTimeout(function() {
										$(".block.remove").remove();
										$("#trash").hide();
										ui.item.click(openUrl);
										serializeSlide();
									}, 0);
						},
						start : function(e, ui) {
							$("#trash").show();
							ui.item.unbind("click");
						}
					});
		}
		addModule(e, s.find("ul"));
	}
}

function getAppMargin() {
	var clientSize = $(document.body).outerWidth(true);
	var appsize = 120 * rowAppNum;
	if (clientSize > appsize) {
		var _margin = Math.floor((clientSize - appsize - 70 * 2) / 16);
	} else {
		var _margin = 0;
	}
	return _margin;
}

function refixAppPos() {
	var _margin = getAppMargin() + "px";
	$("#container .screen li.block").css({
				"margin-left" : _margin,
				"margin-right" : _margin
			})
}

function addModule(e, el) {
	el = $(el);
	var _id = e.id;
	var li = $("<li class=\"block\"></li>");
	var img = $("<div class='img'><p></p></div>");
	// img.find("p").append("<div class='icon " + e.icon +"'></div>");
	img.find("p").append("<img src='" + e.src + "'></img>");
	var divT = $("<div class=\"count\"></div>");
	li.attr({
				"id" : "block_" + e.id,
				"title" : e.name,
				"index" : e.id

			});
	var _margin = getAppMargin() + "px";
	li.css({
				"margin-left" : _margin,
				"margin-right" : _margin
			});
	divT.attr("id", "count_" + e.id);
	if (0 < e.count && e.count <= 9) {// 个数
		divT.addClass("count" + e.count);
	} else if (e.count > 9) {
		divT.addClass("count10");
	}
	var span = $("<span class='icon-text ellipsis'></span>").text(e.name);
	li.append(img.append(divT)).append(span);
	el.append(li);
}

function delModule(el) {
	var pObj = $("#container .screen ul li.block");
	pObj.each(function() {
				var index = $(this).attr("index");
				if (el == index) {
					$(this).remove();
					var flag = serializeSlide();
				}
			});
}

// lp 获取当前屏幕应用的个数
function getAppNums(index) {
	var index = (index == "" || typeof(index) == "undefined") ? slideBox
			.getCursor() : index;
	var num = $("#container .screen:eq(" + index + ") ul li.block").size();
	return num;
}

function initMenus() {
	var modules = [];
	var screen_count = 0;
	for (var i in funcIdObj) {
		var item_array = funcIdObj[i];
		if (item_array == "") {
			modules[screen_count++] = {};
			continue;
		}
		var items = [];
		var item_count = 0;
		for (var j = 0; j < item_array.length; j++) {
			var func_id = item_array[j];
			for (var k = 0; k < alluserMenus.length; k++) {
				if (func_id == alluserMenus[k].resid) {
					var img = alluserMenus[k].showico;
					if (!img)
						img = "default.png";
					items[item_count++] = {
						id : alluserMenus[k].resid,
						name : alluserMenus[k].name,
						icon : alluserMenus[k].showico,
						src : imgdir + img,
						url : decodeURIComponent(alluserMenus[k].srurl),
						isopen : 0,
						count : 0
					};
					break;
				}
			}

		}
		modules[screen_count++] = {
			items : items
		};
	}

	return modules;
}

function initModules(modules, el) {
	window.slideBox = $("#container").slideBox({
				count : modules.length,
				cancel : isTouchDevice() ? "" : ".block",
				obstacle : "200",
				speed : "slow",
				touchDevice : isTouchDevice(),
				control : "#control .control-c"
			});
	el = $(el);
	var count = 0;
	$.each(modules || [], function(i, e) {
				var ul = $("<ul></ul>");
				slideBox.getScreen(i).append(ul)
				$.each(e.items || [], function(j, e) {
							addModule(e, ul);
						});
				i++;
			});
}

function initBlock() {
	$('#container').delegate('.screen ul li.block', 'click', openUrl);
}

function resizeContainer() {
	var wWidth = Math
			.floor(parseInt((window.innerWidth || (window.document.documentElement.clientWidth || window.document.body.clientWidth))
					* 0.9));
	var blockWidth = $('#container > .block:first').outerWidth();
	if (blockWidth <= 0)
		return;

	var count = Math.min(4, Math.max(3, Math.floor(wWidth / blockWidth)));
	$('#container').width(blockWidth * count);
}

function openUrl() {
	var id = this.id.substr(6);
	$.each(alluserMenus || [], function(i, n) {
				if (n.resid == id) {
					node = mini.clone(n);
					node.srurl = decodeURIComponent(node.srurl);
					return false;
				}
			});
	parent.showTab(node);
}

var dataBox = {
	init : function() {
		this.dom = $('[node-type="BoxRoot"][node-data="DataBox"]');
		this.content = this.dom.find('[node-type="inner"]');
		this.closeBtn = this.dom.find('[node-type="close"]');
		this.titleContent = this.dom.find('[node-type="title_content"]');
		this.closeBtn.click($.proxy(function() {
					this.hide();
				}, this));

		this.dom.find("[node-type='dataTitle']").on('click', function() {
					var type = $(this).attr("node-data");
					var id = $(this).attr("data-id");
					var name = $(this).attr("data-title");
					var url = $(this).attr("data-url");
					// /TODO:需修改
					// /TUtil.openUrl(url);
					// /window.setTimeout(getCounts, 1000);
					if ($(this).parents(".grid-view").find("tbody tr").length != '1') {
						$(this).parent().parent().parent().remove();
					} else {
						$(this).parents(".DataBox").hide();
						overlay.hide();
					}
					return false;

				});
	},
	setTitle : function(t) {
		this.titleContent.empty().html(t);
		return this;
	},
	load : function(module_id) {
		var self = this;
		$.ajax({
					type : 'GET',
					url : $CONFIG['url_queryContent'],
					data : {
						id : module_id
					},
					beforeSend : function() {

					},
					success : function(data) {
						self.content.css("height", "350px");
						self.content.empty().html(data);
					},
					error : function(request, textStatus, errorThrown) {
						self.content.css("height", "350px");
						self.content.empty().html('获取内容错误：' + request.status);
					}
				});
		return this;
	},
	show : function() {
		overlay.show();
		this.dom.show();
		this.refixDialogPos();
	},
	hide : function() {
		overlay.hide();
		this.dom.hide();
	},
	refixDialogPos : function() {
		var dialog = this.dom;
		height = dialog.height();
		width = dialog.width();
		var wWidth = (window.innerWidth || (window.document.documentElement.clientWidth || window.document.body.clientWidth));
		var hHeight = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
		var top = left = 0;
		var bst = document.body.scrollTop || document.documentElement.scrollTop;
		top = Math.round((hHeight - height) / 2 + bst);
		mleft = "-" + Math.round(width / 2) + "px";
		top = top < 0 ? '10px' : top + 'px';
		dialog.css({
					"top" : top,
					"left" : "50%",
					"margin-left" : mleft
				});
		return this;
	}
};

function initTrash() {
	$("#trash").droppable({
				over : function() {
					$("#trash").addClass("hover");
				},
				out : function() {
					$("#trash").removeClass("hover");
				},
				drop : function(event, ui) {
					ui.draggable.addClass("remove").hide();
					delModule && delModule(ui.draggable.attr("index"));
					$(".ui-sortable-placeholder").animate({
								width : "0"
							}, "normal", function() {
							});
					$("#trash").removeClass("hover");
				}
			});
}

// 应用盒子
var appbox = {
	base : {
		'screen_max_app_num' : 32
	},
	init : function() {
		this.dom = $("[node-type='BoxRoot'][node-data='AppBox']");
		this.cateListDom = this.dom.find("[node-type='appCateList']");
		this.cateDetailDom = this.dom.find("[node-type='appCateDetail']");
		this.cateDetailDomWrap = this.dom
				.find("[node-type='appCateDetailWrap']");
		this.closeBtn = this.dom.find("[node-type='close']");
		this.screenListDom = $("[node-type='screenList']");
		var self = this;
		this.cateListDom.parent().niceScroll({
					cursorcolor : "#ccc"
				});
		this.cateListDom2 = this.dom.find(".mini-grid-rows-view");

		this.trcateList = mini.get("trMenus");
		// 绑定关闭按钮
		this.closeBtn.bind('click', function() {
					self.dom.hide();
					overlay.hide();
					// self.cateListDom.parent().niceScroll({
					// cursorcolor : "#ccc"
					// }).hide();
					self.cateDetailDomWrap.niceScroll({
								cursorcolor : "#ccc"
							}).hide();
				});

		this.trcateList.on('nodeclick', self.cateListItemClick, this);

		// 绑定点击应用事件
		this.cateDetailDom.delegate("li", "click", function() {
					var appid = $(this).attr("app_id");
					var appname = $(this).attr("app_name");
					var appicon = $(this).attr("app_icon");
					var appsrc = $(this).attr("app_src");
					var appurl = $(this).attr("app_path");
					var appisopen = $(this).attr("is_open");
					if (getAppNums() >= self.base.screen_max_app_num) {
						alert('当前屏幕应用不能超过' + self.base.screen_max_app_num
								+ '个！');
						return false;
					}
					addApp({
								"func_id" : appid,
								"id" : appid,
								"name" : appname,
								"icon" : appicon,
								"src" : appsrc,
								"url" : appurl,
								"isopen" : appisopen
							}, slideBox.getCursor());
					var flag = serializeSlide();

					if (flag) {
						$(this).fadeOut((isIE() ? 1 : 300), function() {
									$(this).remove();
								});

						$('#add_success').animate({
									opacity : 'show'
								}, "slow", "swing", function() {
									$(this).animate({
												opacity : 'hide'
											})
								}).text("应用添加成功！");
					} else {
						$('#add_failed').animate({
									opacity : 'show'
								}, "slow", "swing", function() {
									$(this).animate({
												opacity : 'hide'
											})
								});
					}
				});

	},
	cateListItemClick : function(e) {
		var self = this;
		var resid = e.node.resid;
		self.cateDetailDom.empty();
		$.each(alluserMenus || [], function(i, n) {
					if (n.presid == resid && n.srurl) {
						if (!inArray(n.resid, funcIdObj)) {
							if (!n.showico)
								n.showico = "default.png";
							self.cateDetailDom.append('<li app_id="' + n.resid
									+ '" app_name=' + n.name + ' app_icon='
									+ n.showico + ' app_path='
									+ decodeURIComponent(n.srurl) + ' app_src='
									+ imgdir + n.showico
									+ '><a href="javascript:;" title="'
									+ n.name + '"><div class="img"><img src="'
									+ imgdir + n.showico
									+ '"></img></div><span>' + n.name
									+ '</span></a></li>');

						}
						// return false;
					}
				});
		self.cateDetailDomWrap.niceScroll({
					cursorcolor : "#ccc"
				}).show();
		self.cateDetailDomWrap.niceScroll({
					cursorcolor : "#ccc"
				}).resize();
	},
	show : function() {
		var self = this;

		this.refixDialogPos();
		overlay.show();
		this.dom.show();
		// self.cateListDom.parent().niceScroll({
		// cursorcolor : "#ccc"
		// }).show();
		// self.cateListDom.parent().niceScroll({
		// cursorcolor : "#ccc"
		// }).resize();

		// self.cateListDom2.niceScroll({
		// cursorcolor : "#ccc"
		// }).show();
		// self.cateListDom2.niceScroll({
		// cursorcolor : "#ccc"
		// }).resize();

		self.cateDetailDomWrap.niceScroll({
					cursorcolor : "#ccc"
				}).show();
		self.cateDetailDomWrap.niceScroll({
					cursorcolor : "#ccc"
				}).resize();
		self.screenListDom.parent().niceScroll({
					cursorcolor : "#ccc"
				}).hide();
	},
	refixDialogPos : function() {
		var dialog = this.dom;
		height = dialog.height();
		width = dialog.width();
		var wWidth = (window.innerWidth || (window.document.documentElement.clientWidth || window.document.body.clientWidth));
		var hHeight = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
		var top = left = 0;
		var bst = document.body.scrollTop || document.documentElement.scrollTop;
		top = Math.round((hHeight - height) / 2 + bst) + "px";
		mleft = "-" + Math.round(width / 2) + "px";
		top = top < 0 ? top = 0 : top;
		dialog.css({
					"top" : top,
					"left" : "50%",
					"margin-left" : mleft
				});
		return this;
	}
};

var overlay = {
	template : '<div class="overlayer fade" node-type="overLayer"></div>',
	init : function() {
		$('body').append(this.template);
		this.dom = $("[node-type='overLayer']");
	},
	show : function() {
		this.dom.show();
	},
	hide : function() {
		this.dom.hide();
	}
}

// 盒子设置按钮
var appBoxSet = {
	init : function() {
		this.appDom = $("[node-type='changeTypeDom'][node-data='app']");
		this.screenDom = $("[node-type='changeTypeDom'][node-data='screen']");
		this.dom = $("[node-type='BoxRoot'][node-data='AppBox']");
		this.cateListDom = this.dom.find("[node-type='appCateList']");
		this.cateDetailDomWrap = this.dom
				.find("[node-type='appCateDetailWrap']");
		this.screenListDom = $("[node-type='screenList']");
		var self = this;

		// /TODO:
		$("[node-action='changeType']").on("click", function() {
					var type = $(this).attr("node-type");
					$("[node-action='changeType']").removeClass('active');
					$(this).addClass('active');
					self.changeType(type);
				});

		// 默认打开app
		this.lastType = 'app';
	},
	changeType : function(t) {
		if (t == "app") {
			$("[node-type='changeTypeDom']").hide();
			this.appDom.show();
			appbox.show();
			this.lastType = 'app';
			this.screenListDom.parent().niceScroll({
						cursorcolor : "#ccc"
					}).hide();
		} else {
			$("[node-type='changeTypeDom']").hide();
			this.screenDom.show();
			this.cateListDom.parent().niceScroll({
						cursorcolor : "#ccc"
					}).hide();
			this.cateDetailDomWrap.niceScroll({
						cursorcolor : "#ccc"
					}).hide();
			this.screenListDom.parent().niceScroll({
						cursorcolor : "#ccc"
					}).show();
			this.screenListDom.parent().niceScroll({
						cursorcolor : "#ccc"
					}).resize();
			this.lastType = 'screen';
		}
	},
	run : function() {
		$("[node-action='changeType'][node-type='" + this.lastType + "']")
				.trigger('click');
	}
};

// 修正点击按钮出现屏幕小按钮width为0的现象
function refixminScreenbtn() {
	$('#control').width(window.document.documentElement.clientWidth);
}

$(window).resize(function() {

			refixAppPos();

			refixminScreenbtn();

			appbox.refixDialogPos();

		});

// 屏幕设置
var screenBox = {
	init : function() {
		this.screenListDom = $("[node-type='screenList']");
		// 生成虚拟屏幕
		var _len = slideBox.getCount();
		for (var i = 0; i < _len; i++)
			this.screenListDom.append('<li class="minscreenceil" index=' + i
					+ '>' + (i + 1) + '</li>');
		this.screenListDom
				.append("<li id='btnAddScreen' class='no-draggable-holder icon-plus' title='添加屏幕'></li>");
		this.addBtnDom = this.screenListDom.find("#btnAddScreen");

		var self = this;
		this.screenListDom.parent().niceScroll({
					cursorcolor : "#ccc"
				}).show();
		this.screenListDom.parent().niceScroll({
					cursorcolor : "#ccc"
				}).resize();
		// 调整虚拟屏幕位置事件
		this.screenListDom.sortable({
					cursor : 'move',
					tolerance : 'pointer',
					cancel : '#btnAddScreen',
					stop : function() {
						var arrScreen = new Array();
						$(this).find("li").each(function() {
									arrScreen.push($(this).attr("index"));
								});
						slideBox.sortScreen(arrScreen);
						$(this).find("li").each(function(i) {
									$(this).attr("index", i);
								});
						var flag = serializeSlide();
						if (flag) {
							$('#add_success').animate({
										opacity : 'show'
									}, "slow", "swing", function() {
										$(this).animate({
													opacity : 'hide'
												})
									}).text("屏幕順序調整成功！");
						}
					}
				});

		// 添加屏幕
		this.addBtnDom.bind("click", function() {
					slideBox.addScreen();
					slideBox.scroll(slideBox.getCount() - 1);
					var _max = 0;
					self.screenListDom.find(".minscreenceil").each(function() {
						_max = _max > parseInt($(this).attr("index"))
								? _max
								: parseInt($(this).attr("index"));
					});
					$(this).before("<li class='minscreenceil' index='"
							+ (_max + 1) + "'>" + (_max + 2) + "</li>");
					var flag = serializeSlide();
					$('#add_success').animate({
								opacity : 'show'
							}, "slow", "swing", function() {
								$(this).animate({
											opacity : 'hide'
										})
							}).text("屏幕添加成功！");
				});

		// 鼠标滑过屏幕样式
		this.screenListDom.delegate(".minscreenceil", {
			'mouseenter' : function() {
				$(this).css({
							"font-size" : "60px"
						});
				if ($('span.closebtn', this).length <= 0)
					$(this)
							.append("<span class='closebtn' title='移除此屏'></span>");
				$('span.closebtn', this).show();
			},
			'mouseleave' : function() {
				$(this).css({
							"font-size" : ""
						});
				$('span.closebtn', this).hide();
			}
		});

		// 删除屏幕
		this.screenListDom.delegate(".minscreenceil span", "click", function() {

					if (confirm('删除桌面，将删除桌面全部应用模块，确定要删除吗？')) {
						var currentDom = $(this).parent("li");
						slideBox.removeScreen(currentDom
								.index("li.minscreenceil"));
						var flag = serializeSlide();
						if (flag) {
							$('#add_success').animate({
										opacity : 'show'
									}, "slow", "swing", function() {
										$(this).animate({
													opacity : 'hide'
												})
									}).text("桌面删除成功！");
							currentDom.remove();
							self.reSortMinScreen();
						}
					}
				});

	},
	reSortMinScreen : function() {
		this.screenListDom.find(".minscreenceil").each(function(i) {
					$(this).text(i + 1);
					$(this).attr("index", i);
				});
	}
}

$(document).ready(function() {

			$("body").focus();

			// 初始化图标
			initModules(initMenus());

			// 初始化图标间距
			refixAppPos();

			// 模块点击事件
			initBlock();

			// 对话框事件
			appbox.init();

			// 设置按钮事件
			appBoxSet.init();

			// 遮罩层
			overlay.init();

			// /需修改
			// /定时获取未处理信息
			// /getCounts();

			// 垃圾箱
			initTrash();

			// 显示屏幕设置
			screenBox.init();

			// 内容盒子
			dataBox.init();

			// 初始化屏幕
			$(".screen ul").sortable({
						revert : true,
						// delay: 200,
						distance : 10, // 延迟拖拽事件(鼠标移动十像素),便于操作性
						tolerance : 'pointer', // 通过鼠标的位置计算拖动的位置*重要属性*
						//connectWith : ".screen ul",
						scroll : false,
						//cursor : 'move',					
						stop : function(e, ui) {
							setTimeout(function() {
										$(".block.remove").remove();
										$("#trash").hide();
										ui.item.click(openUrl);
										serializeSlide();
									}, 0);
						},
						start : function(e, ui) {
							$("#trash").show();
							refixminScreenbtn();
							ui.item.unbind("click");
						}
					});
		

			// lp 绑定“界面设置”事件
			$("#openAppBox").click(function() {

						// 控制操作区域
						refixminScreenbtn();

						// 显示应用盒子
						appbox.show();

						// 应用设置和屏幕设置的操作
						appBoxSet.run();

					});
		});

var __sto = setTimeout;
window.setTimeout = function(callback, timeout, param) {
	var args = Array.prototype.slice.call(arguments, 2);
	var _cb = function() {
		callback.apply(null, args);
	}
	return __sto(_cb, timeout);
};

// 序列化桌面上的图标,并且更新
function serializeSlide() {
	var s = {};
	$("#container .screen").each(function(i, e) {
				s[i] = new Array();
				$(this).find("li.block").each(function(j, el) {
							if (!$(el).attr("index"))
								return true;
							s[i].push($(el).attr("index"));
						});
			});
	var flag = false;
	$.ajax({
				type : 'POST',
				async : false,
				data : {
					"app_ids" : mini.encode(s)
				},
				url : url_appSort,
				success : function(r) {
					funcIdObj = s;
					flag = true;
				}
			});
	return flag;
}

function isIE() {
	var obj = browserVersion();
	if (obj.ie)
		return true;
	else
		return false;
};

function browserVersion() {
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] : (s = ua
			.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
			.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
			.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
			.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

	return Sys;
};