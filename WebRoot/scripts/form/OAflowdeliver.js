/**
 * 
 */
function OAflowdeliver(aipObj) {
	// aip控件
	this.$aipObj = aipObj;
	// 外部传入的aip控件事件处理函数
	this.$customAction = null;
	// 流程定义id
	this.$flowdefid = null;
	// 流程实例ID
	this.$flowid = "";
	// 流程实例当前节点
	this.$cnode = "";
	// 当前流程节点所用的表单模板id
	this.$templetid = null;
	// 当前用户允许编辑的表单栏位列表
	this.$allowEditclos = [];
	// 最后提交入库的数据对象
	this.$saveData = {};
	// 需要入库的关键字映射(id:页面栏位ID，clo:数据库栏位)
	this.$keyfieldsreflector = [];
	// 页面根路径
	this.$basePath = "";
	// 此对象为为什么动作创建 new、view、eidt
	this.$action = "";
	// 流程文档修改标志
	this.$modiflag = "";
	// 流程文档id
	this.$flowdocid = "";
	// 流程任务id
	this.$taskid = "";
	// 当前系统登录用户姓名
	this.$userName = "";
	// 当前系统登录id
	this.$userid = "";
	// 当前登录用户的部门
	this.$orgName = "";
	// 当前登录用户的部门id
	this.$orgid="";
	// 流程类型名称
	this.$flowtype = "";
	// 流程实例--公文正文文件名称
	this.$doccontenfilename = "";
	// 流程实例--公文版式正文名称
	this.$docaipcontentfilename = "";
	// 任务分配者
	this.$assigners = "";
	// 菜单ID
	this.$menuid = "";
	// 附加的js
	this.$attachjs = "";
	// 后台附加处理class --spring 中类名称
	this.$className = "";
	// 类类型
	this.$classtype = "";
	// 后台附加处理mehtod
	this.$methodName = "";
	// 附加处理参数
	this.$jsonParameter = "";

	// aip数据初始化
	this.runModelReady = function(obj, IsJS) {
		try {
			this.$aipObj.Login("sys_admin", 5, 65535, "DEMO", "");
			var aipObj = this.$aipObj;
			aipObj.ShowDefMenu = 0; // 隐藏菜单栏 0为隐藏; 1为显示
			aipObj.ShowScrollBarButton = 1; // 隐藏水平滚动条旁的工具条 1为隐藏;0为显示
			aipObj.ShowToolBar = 0; // 隐藏工具栏 0为隐藏; 1为显示
			aipObj.InDesignMode = false; // 退出设计模式

			if (IsJS) {
				aipObj.JSEnv = 1;
				if (obj) {
					// 多页显示 例如 合并查看
					if (IsJS == 0) {
						var objs = eval(obj);
						for (var j = 0; j < objs.length; j++) {
							aipObj.MergeFile(99999, 'STRDATA:' + objs[j]);
						}
						this.insertpagenum(this.$aipObj);
					} else {
						aipObj.LoadFileBase64(eval(obj));// 打开模板文件
					}
				}
			}
			aipObj.Logout();
		} catch (e) {
			alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
					+ "\r\nError Des:" + e.description);
		}
	};

	// 展示表单
	this.api_display = function(obj1, obj2) {
		var aipObj = this.$aipObj;
		// 向表单赋值
		if (obj1) {
			var objs1 = eval('(' + obj1 + ')');
			if (objs1) {
				for (var i in objs1) {
					var _value = objs1[i];
					// 有赋值操作的编辑区域设置为不可编辑，如果不希望进行此设置（即希望此编辑框可编辑）则需要在后台传值的时候在编辑区域名称后加edit后缀
					// 此处接收到以edit结尾的数据后，将此数据的值赋给去除edit之后的编辑框，并且此类数据不设置为不可编辑
					if (i.substr(i.length - 4) != 'edit') {
						// 控制是否可编辑，不以edit结尾并且有值的编辑区域不可编辑
						// if(_value){aipObj.SetValue(i,":PROP::LABEL:3");}
						// else {aipObj.SetValue(i,":PROP:READONLY:0");}
					} else {
						// 以edit结尾的编辑区域不设置不可编辑，同时将区域名称置为去除edit的字符串
						i = i.substr(0, i.length - 4);
						// aipObj.SetValue(i,":PROP:READONLY:0");
					}
					aipObj.SetValue(i, "");
					if (i == 'picrjdjimg') {// 基本信息表的图片，加载数据库的base64字符串
						aipObj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
						aipObj.SetValueEx(i, 14, 0, "STRDATA:" + _value);
						aipObj.Logout();
					} else if (i.indexOf("CheckBox") != -1
							|| i.indexOf("RadioBox") != -1) {// 单复选框
						aipObj.SetValueEx(i, 3, _value, "");
					} else {
						aipObj.SetValue(i, _value);
					}
				}
			}
		}
		// 下拉框
		if (obj2) {
			var objs2 = eval('(' + obj2 + ')');
			if (objs2) {
				for (var i in objs2) {
					aipObj.SetValueEx(i, 43, 0, objs2[i]);
				}
			}
		}
	};

	// 插入页码
	this.insertpagenum = function(aipObj) {
		var num = aipObj.PageCount;
		aipObj.Login("sys_admin", 5, 65535, "DEMO", "");
		// 跳过两页（封皮，目录）从第三页开始增加页码
		for (var i = 2; i < num; i++) {
			var j = i + 1;
			aipObj.InsertNote("Page" + j + ".pagenum", i, 3, 24000, 47000,
					1500, 1000);// 插入编辑区
			aipObj.SetValue("Page" + j + ".pagenum", ":PROP::LABEL:3");// 只读,只能通过接口赋值
			aipObj.SetValue("Page" + j + ".pagenum", ":PROP:BORDER:0");// 没有边框
			aipObj.SetValue("Page" + j + ".pagenum", ":PROP:FRONTCOLOR:0");// 设置字体颜色
			aipObj.SetValue("Page" + j + ".pagenum", ":PROP:FONTSIZE:12");// 设置字体大小
			aipObj.SetValue("Page" + j + ".pagenum", j - 2);// 设置页码
			// 设置档案查看目录
			var dangantype = aipObj.GetValueEx("Page" + j + ".dangantype", 2,
					"", 0, "");
			if (dangantype != '') {
				aipObj.SetValue("Page2." + dangantype, j - 2)
			}
		}
		aipObj.Logout();
	};
	// aip事件处理函数
	this.JSNotifyBeforeAction = function(lActionType, lType, strName, strValue) {
		try {
			if (this.$customAction != null) {
				this.$customAction(lActionType, lType, strName, strValue,
						this.$aipObj);
			}
		} catch (e) {
			alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
					+ "\r\nError Des:" + e.description);
		}
	};
	// 设置aip中个栏位的可编辑状态
	this.setNodeEditable = function(aipObj, roles) {
		var NoteInfo = "";
		this.$allowEditclos = [];
		while (NoteInfo = aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
			var temp = NoteInfo.split(".")[1];
			var rl = aipObj.DocProperty("code_" + temp);
			if (rl != null && rl != undefined && rl != "") {
				if (roles.indexOf("@" + rl + "&") < 0) {
					// alert(temp);
					aipObj.SetValue(temp, ":PROP::LABEL:2");
					aipObj.SetValue(temp, ":PROP:READONLY:2");
				} else {
					// alert(temp);
					// aipObj.SetValue(name, ":PROP::NODEL:0");
					aipObj.SetValue(temp, ":PROP::LABEL:0");
					aipObj.SetValue(temp, ":PROP:READONLY:0");
					this.$allowEditclos.push(temp);
				}
			} else {		
				if (temp.indexOf('public')<0) {				
					aipObj.SetValue(temp, ":PROP::LABEL:3"); // 永久不得编辑
					aipObj.SetValue(temp, ":PROP:READONLY:3");
				}
			}
		}
	};
	// 获取AIP文件的BASE64字符串
	this.getAipContent = function(aipObj) {
		try {
			var content = aipObj.GetCurrFileBase64();
		} catch (e) {
			alert("异常\r\nError:" + e + "\r\nError Code:" + e.number
					+ "\r\nError Des:" + e.description);
			return false;
		}
		return content;
	};
	// 保存流程过程中的数据，例程暂时不提交到下一个节点
	this.doSaveDraft = function(explain) {
		
		if (setLancuFlowData(this))
			return;
		this.$saveData.explain = explain;
		this.$saveData.flowtype = this.$flowtype;
		if (this.$doccontenfilename != '') {
			this.$saveData.doccontenfilename = this.$doccontenfilename;
		}
		if (this.$docaipcontentfilename != '') {
			this.$saveData.docaipcontentfilename = this.$docaipcontentfilename;
		}
		var msgid = mini.loading('保存中，请稍后......', '');
		var sresult = 0;
		$.ajax({
					url : this.$basePath + "/deliver/doSaveFlowDraft.action",
					data : {
						data : mini.encode(this.$saveData)
					},
					type : "POST",
					dataType : "json",
					async : false,
					success : function(text) {
						mini.hideMessageBox(msgid);
						sresult = 1;
						alert("操作成功!");
					},
					error : function(jqXHR, textStatus, errorThrown) {
						mini.hideMessageBox(msgid);
						alert("操作失败");
					}
				});
		return sresult;
	};
	// 流程流转操作
	this.Execute = function(explain) {
		if (setFlowDeliverData(this))
			return;
		this.$saveData.explain = explain;
		this.$saveData.flowtype = this.$flowtype;
		this.$saveData.flowdefid = this.$flowdefid;
		this.$saveData.flowid = this.$flowid;
		this.$saveData.templetid = this.$templetid;
		this.$saveData.menuid = this.$menuid;
		this.$saveData.attachjs = this.$attachjs;
		this.$saveData.className = this.$className;
		this.$saveData.methodName = this.$methodName;
		this.$saveData.jsonParameter = this.$jsonParameter;
		this.$saveData.classtype = this.$classtype;

		if (this.$assigners != "") {
			this.$saveData.assigners = mini.decode(this.$assigners);
			// alert(this.$saveData.assigners);
			// var temp= mini.encode(this.$saveData);
			// alert(temp);
		}
		if (this.$doccontenfilename != '') {
			this.$saveData.doccontenfilename = this.$doccontenfilename;
		}
		if (this.$docaipcontentfilename != '') {
			this.$saveData.docaipcontentfilename = this.$docaipcontentfilename;
		}
		var msgid = mini.loading('执行中，请稍后......', '');
		var eresult = 0;
		$.ajax({
					url : this.$basePath
							+ "/deliver/doflowInstanceDeliver.action",
					data : {
						data : mini.encode(this.$saveData)
					},
					type : "POST",
					dataType : "json",
					async : false,
					success : function(text) {
						mini.hideMessageBox(msgid);
						eresult = 1;
						alert("操作成功!");
					},
					error : function(jqXHR, textStatus, errorThrown) {
						mini.hideMessageBox(msgid);
						alert("操作失败");
					}
				});
		this.$saveData.className = "";
		this.$saveData.methodName = "";
		this.$saveData.jsonParameter = "";
		return eresult;
	};

	this.doMenueButton = function(operateType) {
		var temp = 0;
		var aipObj = this.$aipObj;
		// 用户未登录则自动登录 (全屏、阅读、打印、另存不判断 )
		if (operateType != 1 && operateType != 3 && operateType != 4
				&& operateType != 9 && operateType != 17 && !aipObj.IsLogin()) {
			var flag = aipLogin(aipObj);
			if (flag == -200) {
				alert("请先插入智能卡！");
				return;
			} else if (flag != 0) {
				alert("登录失败！");
				return;
			}
		}
		if (operateType == 0) {
			aipObj.InputHotKey(0xC000);
		} else if (operateType == 1) {
			aipObj.InputHotKey(0xC001);
		} else if (operateType == 2) {

		} else if (operateType == 3) {
			if (temp == 0) {
				aipObj.SetPageMode(32, 1);
				temp = 1;
			} else {
				aipObj.SetPageMode(32, 0);
				temp = 0;
			}
		} else if (operateType == 4) {
			aipObj.ShowFullScreen = 1;
		} else if (operateType == 5) {
			aipObj.CurrAction = 3592;// 2568 添加印章操作状态,印章文件取自文件。 3592
			// 设置盖网络印章的操作状态。
		} else if (operateType == 6) {
			if (operateflag == operateType) {
				aipObj.CurrAction = 1;
				operateflag = 0;
			} else {
				aipObj.CurrAction = 264;
				aipObj.WaterMarkTextColor = -1;
				operateflag = operateType;
			}
			return;
		} else if (operateType == 7) {
			if (operateflag == operateType) {
				aipObj.CurrAction = 1;
				operateflag = 0;
			} else {
				aipObj.CurrAction = 16;
				operateflag = operateType;
			}
			return;
		} else if (operateType == 8) {
			// 此方法会导致ie崩溃暂时注释，询问点聚解决方法
			// aipObj.CurrAction = 3080;
		} else if (operateType == 9) {
			aipObj.InputHotKey(0xC002);
			// aipObj.PrintDocEx('',1,0,0,0,9999,0,1,1,0,0);
		} else if (operateType == 10) {
			deleteSeal();
			alert("撤销签名成功，请存盘");
		} else if (operateType == 11) {
			aipObj.CurrAction = 1;
		} else if (operateType == 12) {
			aipObj.CurrPenColor = -1;
		} else if (operateType == 13) {
			aipObj.RunCommand(0, 32954, 0);
		} else if (operateType == 14) {

		} else if (operateType == 15) {
			if (aipObj.CurrPage <= 0) {

			} else {
				aipObj.CurrPage = aipObj.CurrPage - 1;
			}
		} else if (operateType == 16) {
			if (aipObj.CurrPage >= aipObj.PageCount - 1) {

			} else {
				aipObj.CurrPage = aipObj.CurrPage + 1;
			}
		} else if (operateType == 17) {
			aipObj.SearchText("", 0, 0);
		}
		operateflag = 0;
	}

	// 设置流程的流转数据（后台存储）
	function setFlowDeliverData(This) {
		var error = false;
		This.$saveData = {};
		error = getNodeData(This);
		This.$saveData.doccontent = This.getAipContent(This.$aipObj);
		This.$saveData.flowdefid = This.$flowdefid;
		This.$saveData.templetid = This.$templetid;
		This.$saveData.flowid = This.$flowid;
		// This.$saveData.cnode = This.$cnode;
		This.$saveData.modiflag = This.$modiflag;
		This.$saveData.flowdocid = This.$flowdocid;
		This.$saveData.taskid = This.$taskid;
		This.$saveData.action = This.$action;
		return error;
	}
	// 设置流程发起的后台存储数据
	function setLancuFlowData(This) {
		var error = false;
		This.$saveData = {};
		error = getNodeData(This);
		This.$saveData.doccontent = This.getAipContent(This.$aipObj);
		This.$saveData.flowdefid = This.$flowdefid;
		This.$saveData.templetid = This.$templetid;
		This.$saveData.flowid = This.$flowid;
		This.$saveData.taskid = This.$taskid;
		This.$saveData.action = This.$action;
		return error;
	};
	// 获取表单节点数据
	function getNodeData(This) {
		var error = false;
		var NoteInfo = "";
		while (NoteInfo = This.$aipObj.GetNextNote("sys_admin", 0, NoteInfo)) {
			var temp = NoteInfo.split(".")[1];
			if (inAllowEditclos(temp, This)) {
				var flector = inkeyfieldsreflector(temp, This);
				if (flector != null) {
					var value = This.$aipObj.GetValueEx(NoteInfo, 2, "", 0, "");
					if (value == "") {
						alert("请输入" + flector.des + "!");
						return true;
					}
					This.$saveData[flector.clo] = value;
				}
			}
		}
		return error;
	};
	// 返回字段映射信息
	function inkeyfieldsreflector(fieldName, This) {
		for (var i = 0; i < This.$keyfieldsreflector.length; i++) {
			if (fieldName == This.$keyfieldsreflector[i].id)
				return This.$keyfieldsreflector[i];
		}
		return null;
	};
	// 字段是否在用户有权限编辑的字段列表中
	function inAllowEditclos(fieldName, This) {
		for (var i = 0; i < This.$allowEditclos.length; i++) {
			if (fieldName == This.$allowEditclos[i])
				return true;
		}
		return false;
	};

};

function aipLogin(aipObj) {
	//
	var hostname = window.location.hostname || "127.0.0.1";
	//
	// return aipObj.Login("",3, 65535,
	// "","http://192.168.0.128:9230/Seal/general/interface/");
	return aipObj.Login("", 3, 65535, "", "http://" + hostname
					+ ":9230/Seal/general/interface/");
}
