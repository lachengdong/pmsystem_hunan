var basePath = "";
if (document.getElementById("basePath"))
	basePath = document.getElementById("basePath").value;
/*******************************************************************************
 * 
 * 替换各个会议意见函数
 * 
 * 
 ******************************************************************************/
/*
 * 监狱减刑假释
 */
function jxjsJailDanChu1(aipObj, strName) {
	var fjqSuggest = '';
	var jqSuggest = '';
	var ksSuggest = '';
	var pshSuggest = '';
	var jySuggest = '';
	var jiashi = "假释";
	var jianxing = "减刑";
	var suggest2 = aipObj.GetValueEx("suggest2", 2, "", 0, "");
	var suggest3 = aipObj.GetValueEx("suggest3", 2, "", 0, "");
	var suggest4 = aipObj.GetValueEx("suggest4", 2, "", 0, "");
	var suggest5 = aipObj.GetValueEx("suggest5", 2, "", 0, "");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, "");
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var jxjs_14 = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var jxjs_15 = aipObj.GetValueEx("isagree", 2, "", 0, "");
	var jxjs_16 = aipObj.GetValueEx("reason", 2, "", 0, "");
	var crimid = aipObj.GetValueEx("criminalid", 2, "", 0, "");
	var datetime = aipObj.GetValueEx("casedate", 2, "", 0, "");
	var boquan = aipObj.GetValueEx("boquan", 2, "", 0, "");

	var tempids = '9560,9453,9573,9574,10341';
	$.ajax({
		type : "POST",
		url : basePath + "/findContent.json",
		data : {
			crimid : crimid,
			tempids : tempids
		},
		cache : false,
		async : false,
		success : function(text) {
			var obj = eval(text);
			if (obj) {
				fjqSuggest = obj[0];
				jqSuggest = obj[1];
				ksSuggest = obj[2];
				pshSuggest = obj[3];
				jySuggest = obj[4];
			}
		}
	});
	// var jxjsyj = jxjs_1 +"@," + jxjs_2 +"@," + jxjs_3 +"@," + jxjs_4 +"@," +
	// jxjs_5 +"@," + jxjs_6 + "@,"+jxjs_0;
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	if (jxjs_9 == "1.#QNAN" || jxjs_9 == null) {
		jxjs_9 = "";
	}
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("jxjs_14:" + jxjs_14);
	jxjsyj.push("jxjs_15:" + jxjs_15);
	jxjsyj.push("jxjs_16:" + jxjs_16);
	jxjsyj.push("datetime:" + datetime);
	jxjsyj.push("boquan:" + boquan);
	/* 此处已改回 */
	url = basePath + "/tanChuOpinionPage.action?crimid=" + crimid + "&jxjsyj="
			+ encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:450px;dialogWidth:800px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1", "");
		aipObj.SetValue("Page1.jxjs_2", "");
		aipObj.SetValue("Page1.jxjs_3", "");
		aipObj.SetValue("Page1.jxjs_4", "");
		aipObj.SetValue("Page1.jxjs_5", "");
		aipObj.SetValue("Page1.jxjs_6", "");
		aipObj.SetValue("Page1.jxjs_7", "");
		aipObj.SetValue("Page1.jxjs_8", "");
		aipObj.SetValue("Page1.jxjs_9", "");
		aipObj.SetValue("Page1.jxjs_10", "");
		aipObj.SetValue("Page1.jxjs_11", "");
		aipObj.SetValue("Page1.jxjs_12", "");
		aipObj.SetValue("Page1.jxjs_13", "");
		aipObj.SetValue("Page1.payeverymon", "");
		aipObj.SetValue("Page1.isagree", "");
		aipObj.SetValue("Page1.reason", "");
		aipObj.SetValue("Page1.jxjs_date", "");
		aipObj.SetValue("Page1.casedate", "");
		// 返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1", vRet[0]);
		aipObj.SetValue("Page1.jxjs_2", vRet[1]);
		aipObj.SetValue("Page1.jxjs_3", vRet[2]);
		aipObj.SetValue("Page1.jxjs_4", vRet[3]);
		aipObj.SetValue("Page1.jxjs_5", vRet[4]);
		aipObj.SetValue("Page1.jxjs_6", vRet[5]);
		aipObj.SetValue("Page1.jxjs_7", vRet[6]);
		aipObj.SetValue("Page1.jxjs_8", vRet[7]);
		aipObj.SetValue("Page1.jxjs_9", vRet[8]);
		aipObj.SetValue("Page1.jxjs_10", vRet[9]);
		aipObj.SetValue("Page1.jxjs_11", vRet[10]);
		aipObj.SetValue("Page1.jxjs_12", vRet[11]);
		aipObj.SetValue("Page1.jxjs_13", vRet[12]);
		aipObj.SetValue("Page1.payeverymon", jxjs_14);
		aipObj.SetValue("Page1.isagree", vRet[13]);
		aipObj.SetValue("Page1.iscommitagree", vRet[13]);
		aipObj.SetValue("Page1.reason", vRet[14]);
		aipObj.SetValue("Page1.jxjs_date", vRet[16]);
		aipObj.SetValue("Page1.casedate", vRet[17]);
		if (vRet[0] == '1') {
			aipObj.SetValue("Page1.jxorjs", "");
			aipObj.SetValue("Page1.jxorjs", "罪犯假释审核表");
			fjqSuggest = fjqSuggest.replace(jianxing, jiashi);
			jqSuggest = jqSuggest.replace(jianxing, jiashi);
			ksSuggest = ksSuggest.replace(jianxing, jiashi);
			pshSuggest = pshSuggest.replace(jianxing, jiashi);
			jySuggest = jySuggest.replace(jianxing, jiashi);
		} else {
			aipObj.SetValue("Page1.jxorjs", "");
			aipObj.SetValue("Page1.jxorjs", "罪犯减刑审核表");
		}
		// 减刑假释监狱经办人意见选择----------
		if (strName == 'suggest2') {
			// 清空意见
			aipObj.SetValue("Page2.suggest2", "");
			aipObj.SetValue("Page2.suggest3", "");
			aipObj.SetValue("Page2.suggest1", "");
			aipObj.SetValue("Page2.suggest4", "");
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest2", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest2", fjqSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest3", jqSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest1", ksSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest4", pshSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest5", jySuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("commenttext", fjqSuggest.replace('@',
							vRet[15]));
					document.getElementById("commenttext").value = fjqSuggest
							.replace('@', vRet[15]);
				}
			}
		}
		// 减刑假释监狱监区长意见选择----------
		if (strName == 'suggest3') {
			// 清空意见
			aipObj.SetValue("Page2.suggest3", "");
			aipObj.SetValue("Page2.suggest1", "");
			aipObj.SetValue("Page2.suggest4", "");
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			aipObj.SetValue("Page1.text11", "");
			// 填充意见信息 }
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest3", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest3", jqSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest1", ksSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest4", pshSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest5", jySuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("commenttext", jqSuggest.replace('@',
							vRet[15]));
					document.getElementById("commenttext").value = jqSuggest
							.replace('@', vRet[15]);
				}
				aipObj.SetValue("Page2.text11", formatDate(new Date()));
			}
		}
		// 减刑假释监狱科室意见选择----------
		if (strName == 'suggest1') {
			// 清空意见
			aipObj.SetValue("Page2.suggest1", "");
			aipObj.SetValue("Page2.suggest4", "");
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest1", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest1", pshSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest4", pshSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest5", jySuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("commenttext", pshSuggest.replace('@',
							vRet[15]));
					document.getElementById("commenttext").value = pshSuggest
							.replace('@', vRet[15]);
				}
				aipObj.SetValue("Page2.text13", formatDate(new Date()));
			}
		}
		// 减刑假释监狱评审会意见选择----------
		if (strName == 'suggest4') {
			// 清空意见
			aipObj.SetValue("Page2.suggest4", "");
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest4", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest4", pshSuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("Page2.suggest5", jySuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("commenttext", pshSuggest.replace('@',
							vRet[15]));
					document.getElementById("commenttext").value = pshSuggest
							.replace('@', vRet[15]);
				}
				aipObj.SetValue("Page2.text13", formatDate(new Date()));
			}
		}
		// 减刑假释监狱监狱长意见选择----------
		if (strName == 'suggest5') {
			// 清空意见
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest5", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest5", jySuggest.replace('@',
							vRet[15]));
					aipObj.SetValue("commenttext", jySuggest.replace('@',
							vRet[15]));
					document.getElementById("commenttext").value = jySuggest
							.replace('@', vRet[15]);
				}
			}// 拼接的意见有值结束
			aipObj.SetValue("Page2.text18", formatDate(new Date()));
		}// suggest5结束

	}
}

function jxjsJailDanChu(aipObj, strName) {//
	var fjqSuggest = '经集体研究，';
	var jqSuggest = '经监区长办公会审核，';
	var ksSuggest = '经科务会审查，';
	var pshSuggest = '经评审委员会评审，';
	var jySuggest = '经监狱长办公会研究，';
	var suggest2 = aipObj.GetValueEx("suggest2", 2, "", 0, "");
	var suggest3 = aipObj.GetValueEx("suggest3", 2, "", 0, "");
	var suggest4 = aipObj.GetValueEx("suggest4", 2, "", 0, "");
	var suggest5 = aipObj.GetValueEx("suggest5", 2, "", 0, "");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, "");
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var jxjs_14 = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var jxjs_15 = aipObj.GetValueEx("isagree", 2, "", 0, "");
	var jxjs_16 = aipObj.GetValueEx("reason", 2, "", 0, "");
	var crimid = aipObj.GetValueEx("criminalid", 2, "", 0, "");
	var datetime = aipObj.GetValueEx("specicalcrim", 2, "", 0, "");
	// var jxjsyj = jxjs_1 +"@," + jxjs_2 +"@," + jxjs_3 +"@," + jxjs_4 +"@," +
	// jxjs_5 +"@," + jxjs_6 + "@,"+jxjs_0;
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	if (jxjs_9 == "1.#QNAN" || jxjs_9 == null) {
		jxjs_9 = "";
	}
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("jxjs_14:" + jxjs_14);
	jxjsyj.push("jxjs_15:" + jxjs_15);
	jxjsyj.push("jxjs_16:" + jxjs_16);
	jxjsyj.push("datetime:" + datetime);
	/* 此处已改回 */
	url = basePath + "/tanChuOpinionPage.action?crimid=" + crimid + "&jxjsyj="
			+ encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		// 清空隐藏域原来的值
		// Page*去掉默认全局搜索，不区分在第几页，因山西审批表只有一页，Page2会找不到，使用此方法时，文本框名字不能重复
		// aipObj.SetValue("Page1.jxjs_1","")；
		aipObj.SetValue("jxjs_1", "");
		aipObj.SetValue("jxjs_2", "");
		aipObj.SetValue("jxjs_3", "");
		aipObj.SetValue("jxjs_4", "");
		aipObj.SetValue("jxjs_5", "");
		aipObj.SetValue("jxjs_6", "");
		aipObj.SetValue("jxjs_7", "");
		aipObj.SetValue("jxjs_8", "");
		aipObj.SetValue("jxjs_9", "");
		aipObj.SetValue("jxjs_10", "");
		aipObj.SetValue("jxjs_11", "");
		aipObj.SetValue("jxjs_12", "");
		aipObj.SetValue("jxjs_13", "");
		aipObj.SetValue("payeverymon", "");
		aipObj.SetValue("isagree", "");
		aipObj.SetValue("reason", "");
		aipObj.SetValue("jxjs_date", "");
		aipObj.SetValue("specicalcrim", "");
		// 返回值传到隐藏域
		aipObj.SetValue("jxjs_1", vRet[0]);
		aipObj.SetValue("jxjs_2", vRet[1]);
		aipObj.SetValue("jxjs_3", vRet[2]);
		aipObj.SetValue("jxjs_4", vRet[3]);
		aipObj.SetValue("jxjs_5", vRet[4]);
		aipObj.SetValue("jxjs_6", vRet[5]);
		aipObj.SetValue("jxjs_7", vRet[6]);
		aipObj.SetValue("jxjs_8", vRet[7]);
		aipObj.SetValue("jxjs_9", vRet[8]);
		aipObj.SetValue("jxjs_10", vRet[9]);
		aipObj.SetValue("jxjs_11", vRet[10]);
		aipObj.SetValue("jxjs_12", vRet[11]);
		aipObj.SetValue("jxjs_13", vRet[12]);
		aipObj.SetValue("payeverymon", jxjs_14);
		aipObj.SetValue("isagree", vRet[13]);
		aipObj.SetValue("iscommitagree", vRet[13]);
		aipObj.SetValue("reason", vRet[14]);
		aipObj.SetValue("jxjs_date", vRet[16]);
		aipObj.SetValue("specicalcrim", vRet[17]);
		if (vRet[0] == '1') {
			aipObj.SetValue("jxojs", "");
			aipObj.SetValue("jxojs", "罪犯假释审核表");
		} else {
			aipObj.SetValue("jxojs", "");
			aipObj.SetValue("jxojs", "罪犯减刑审核表");
		}
		if (vRet[17] == '1') {
			var appendSuggest = "最高法院核准，";
			fjqSuggest = appendSuggest + '经集体研究，';
			jqSuggest = appendSuggest + '经监区长办公会审核，';
			pshSuggest = appendSuggest + '经评审委员会评审，';
			jySuggest = appendSuggest + '经监狱长办公会研究，';
		}
		// 减刑假释监狱经办人意见选择----------
		if (strName == 'suggest2') {
			// 清空意见
			aipObj.SetValue("suggest2", "");
			aipObj.SetValue("suggest3", "");
			aipObj.SetValue("suggest1", "");
			aipObj.SetValue("suggest4", "");
			aipObj.SetValue("suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest2", vRet[15]);
				} else {
					aipObj.SetValue("suggest2", fjqSuggest + vRet[15]);
					aipObj.SetValue("suggest3", jqSuggest + vRet[15]);
					aipObj.SetValue("suggest1", ksSuggest + vRet[15]);
					aipObj.SetValue("suggest4", pshSuggest + vRet[15]);
					aipObj.SetValue("suggest5", jySuggest
							+ vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("commenttext", fjqSuggest + vRet[15]);
					var commenttext = document.getElementById("commenttext");
					if (commenttext) {
						commenttext.value = fjqSuggest + vRet[15];
					}
					// document.getElementById("commenttext").value= fjqSuggest
					// + vRet[15];
				}
			}
		}
		// 减刑假释监狱监区长意见选择----------
		if (strName == 'suggest3') {
			// 清空意见
			aipObj.SetValue("suggest3", "");
			aipObj.SetValue("suggest1", "");
			aipObj.SetValue("suggest4", "");
			aipObj.SetValue("suggest5", "");
			aipObj.SetValue("commenttext", "");
			aipObj.SetValue("text11", "");
			// 填充意见信息 }
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("suggest3", vRet[15]);
				} else {
					aipObj.SetValue("suggest3", jqSuggest + vRet[15]);
					aipObj.SetValue("suggest1", ksSuggest + vRet[15]);
					aipObj.SetValue("suggest4", pshSuggest + vRet[15]);
					aipObj.SetValue("suggest5", jySuggest
							+ vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("commenttext", jqSuggest + vRet[15]);
					var commenttext = document.getElementById("commenttext");
					if (commenttext) {
						commenttext.value = jqSuggest + vRet[15];
					}
					// document.getElementById("commenttext").value = jqSuggest
					// + vRet[15];
				}
				aipObj.SetValue("text11", formatDate(new Date()));
			}
		}
		// 减刑假释监狱科室意见选择----------
		if (strName == 'suggest1') {
			// 清空意见
			aipObj.SetValue("suggest1", "");
			aipObj.SetValue("suggest4", "");
			aipObj.SetValue("suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("suggest1", vRet[15]);
				} else {
					aipObj.SetValue("suggest1", pshSuggest + vRet[15]);
					aipObj.SetValue("suggest4", pshSuggest + vRet[15]);
					aipObj.SetValue("suggest5", jySuggest
							+ vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("commenttext", pshSuggest + vRet[15]);
					var commenttext = document.getElementById("commenttext");
					if (commenttext) {
						commenttext.value = pshSuggest + vRet[15];
					}
					// document.getElementById("commenttext").value = pshSuggest
					// + vRet[15];
				}
				aipObj.SetValue("text13", formatDate(new Date()));
			}
		}
		// 减刑假释监狱评审会意见选择----------
		if (strName == 'suggest4') {
			// 清空意见
			aipObj.SetValue("suggest4", "");
			aipObj.SetValue("suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("suggest4", vRet[15]);
				} else {
					aipObj.SetValue("suggest4", pshSuggest + vRet[15]);
					aipObj.SetValue("suggest5", jySuggest
							+ vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("commenttext", pshSuggest + vRet[15]);
					var commenttext = document.getElementById("commenttext");
					if (commenttext) {
						commenttext.value = pshSuggest + vRet[15];
					}
					// document.getElementById("commenttext").value = pshSuggest
					// + vRet[15];
				}
				aipObj.SetValue("text13", formatDate(new Date()));
			}
		}
		// 减刑假释监狱监狱长意见选择----------
		if (strName == 'suggest5') {
			// 清空意见
			aipObj.SetValue("suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				aipObj.SetValue("suggest5", jySuggest + vRet[15]);
				aipObj.SetValue("commenttext", jySuggest
						+ vRet[15].replace('建议', '同意提请'));
				var commenttext = document.getElementById("commenttext");
				if (commenttext) {
					commenttext.value = jySuggest + vRet[15];
				}
				// document.getElementById("commenttext").value = jySuggest +
				// vRet[15];
			}// 拼接的意见有值结束
			aipObj.SetValue("text18", formatDate(new Date()));
		}// suggest5结束
		if (strName == 'suggest6') {
			// 清空意见
			aipObj.SetValue("suggest6", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				aipObj.SetValue("suggest6", jySuggest + vRet[15]);
				aipObj.SetValue("commenttext", jySuggest
						+ vRet[15].replace('建议', '同意提请'));
				var commenttext = document.getElementById("commenttext");
				if (commenttext) {
					commenttext.value = jySuggest + vRet[15];
				}
				// document.getElementById("commenttext").value = jySuggest +
				// vRet[15];
			}// 拼接的意见有值结束
			aipObj.SetValue("text18", formatDate(new Date()));
		}// suggest5结束
	}
}
// 山西宁夏分离
function jxjsJailDanChu_sx(aipObj, strName) {
	var jing = '    经';
	var fjqSuggest = '监区警察集体评议，';
	var jqSuggest = '监区长办公会审核，';
	var ksSuggest = '科务会审查，';
	var pshSuggest = '评审委员会评审，';
	var jySuggest = '监狱长办公会决定，';
	var suggest2 = aipObj.GetValueEx("suggest2", 2, "", 0, "");
	var suggest3 = aipObj.GetValueEx("suggest3", 2, "", 0, "");
	var suggest4 = aipObj.GetValueEx("suggest4", 2, "", 0, "");
	var suggest5 = aipObj.GetValueEx("suggest5", 2, "", 0, "");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, "");
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var jxjs_14 = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var jxjs_15 = aipObj.GetValueEx("isagree", 2, "", 0, "");
	var jxjs_16 = aipObj.GetValueEx("reason", 2, "", 0, "");
	var crimid = aipObj.GetValueEx("criminalid", 2, "", 0, "");
	var datetime = aipObj.GetValueEx("casedate", 2, "", 0, "");
	// var jxjsyj = jxjs_1 +"@," + jxjs_2 +"@," + jxjs_3 +"@," + jxjs_4 +"@," +
	// jxjs_5 +"@," + jxjs_6 + "@,"+jxjs_0;
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	if (jxjs_9 == "1.#QNAN" || jxjs_9 == null) {
		jxjs_9 = "";
	}
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("jxjs_14:" + jxjs_14);
	jxjsyj.push("jxjs_15:" + jxjs_15);
	jxjsyj.push("jxjs_16:" + jxjs_16);
	jxjsyj.push("datetime:" + datetime);
	url = basePath + "/tanChuOpinionPage_nx.action?crimid=" + crimid
			+ "&jxjsyj=" + encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1", "");
		aipObj.SetValue("Page1.jxjs_2", "");
		aipObj.SetValue("Page1.jxjs_3", "");
		aipObj.SetValue("Page1.jxjs_4", "");
		aipObj.SetValue("Page1.jxjs_5", "");
		aipObj.SetValue("Page1.jxjs_6", "");
		aipObj.SetValue("Page1.jxjs_7", "");
		aipObj.SetValue("Page1.jxjs_8", "");
		aipObj.SetValue("Page1.jxjs_9", "");
		aipObj.SetValue("Page1.jxjs_10", "");
		aipObj.SetValue("Page1.jxjs_11", "");
		aipObj.SetValue("Page1.jxjs_12", "");
		aipObj.SetValue("Page1.jxjs_13", "");
		aipObj.SetValue("Page1.payeverymon", "");
		aipObj.SetValue("Page1.isagree", "");
		aipObj.SetValue("Page1.reason", "");
		aipObj.SetValue("Page1.casedate", "");
		aipObj.SetValue("Page1.jxjs_date", "");
		// 返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1", vRet[0]);
		aipObj.SetValue("Page1.jxjs_2", vRet[1]);
		aipObj.SetValue("Page1.jxjs_3", vRet[2]);
		aipObj.SetValue("Page1.jxjs_4", vRet[3]);
		aipObj.SetValue("Page1.jxjs_5", vRet[4]);
		aipObj.SetValue("Page1.jxjs_6", vRet[5]);
		aipObj.SetValue("Page1.jxjs_7", vRet[6]);
		aipObj.SetValue("Page1.jxjs_8", vRet[7]);
		aipObj.SetValue("Page1.jxjs_9", vRet[8]);
		aipObj.SetValue("Page1.jxjs_10", vRet[9]);
		aipObj.SetValue("Page1.jxjs_11", vRet[10]);
		aipObj.SetValue("Page1.jxjs_12", vRet[11]);
		aipObj.SetValue("Page1.jxjs_13", vRet[12]);
		aipObj.SetValue("Page1.payeverymon", jxjs_14);
		aipObj.SetValue("Page1.isagree", vRet[13]);
		aipObj.SetValue("Page1.iscommitagree", vRet[13]);
		aipObj.SetValue("Page1.reason", vRet[14]);
		aipObj.SetValue("Page1.jxjs_date", vRet[16]);
		aipObj.SetValue("Page1.casedate", vRet[17]);
		if (vRet[0] == '1') {
			aipObj.SetValue("Page1.jxojs", "");
			aipObj.SetValue("Page1.jxojs", "罪犯假释审核表");
		} else {
			aipObj.SetValue("Page1.jxojs", "");
			aipObj.SetValue("Page1.jxojs", "罪犯减刑审核表");
		}
		// 减刑假释监狱经办人意见选择----------
		if (strName == 'suggest2') {
			// 清空意见
			aipObj.SetValue("Page2.suggest2", "");
			aipObj.SetValue("Page2.suggest3", "");
			aipObj.SetValue("Page2.suggest1", "");
			aipObj.SetValue("Page2.suggest4", "");
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest2", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest2", jing + vRet[17]
							+ fjqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest3", jing + vRet[17]
							+ jqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest1", jing + vRet[17]
							+ ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4", jing + vRet[17]
							+ pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5", jing + vRet[17]
							+ jySuggest + vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("commenttext", jing + vRet[17] + fjqSuggest
							+ vRet[15]);
					document.getElementById("commenttext").value = jing
							+ vRet[17] + fjqSuggest + vRet[15];
				}
			}
		}
		// 减刑假释监狱监区长意见选择----------
		if (strName == 'suggest3') {
			// 清空意见
			aipObj.SetValue("Page2.suggest3", "");
			aipObj.SetValue("Page2.suggest1", "");
			aipObj.SetValue("Page2.suggest4", "");
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			aipObj.SetValue("Page1.text11", "");
			// 填充意见信息 }
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest3", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest3", jing + vRet[17]
							+ jqSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest1", jing + vRet[17]
							+ ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4", jing + vRet[17]
							+ pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5", jing + vRet[17]
							+ jySuggest + vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("commenttext", jing + vRet[17] + jqSuggest
							+ vRet[15]);
					document.getElementById("commenttext").value = jing
							+ vRet[17] + jqSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text11", formatDate(new Date()));
			}
		}
		// 减刑假释监狱科室意见选择----------
		if (strName == 'suggest1') {
			// 清空意见
			aipObj.SetValue("Page2.suggest1", "");
			aipObj.SetValue("Page2.suggest4", "");
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest1", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest1", jing + vRet[17]
							+ pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest4", jing + vRet[17]
							+ pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5", jing + vRet[17]
							+ jySuggest + vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("commenttext", jing + vRet[17] + pshSuggest
							+ vRet[15]);
					document.getElementById("commenttext").value = jing
							+ vRet[17] + pshSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text13", formatDate(new Date()));
			}
		}
		// 减刑假释监狱评审会意见选择----------
		if (strName == 'suggest4') {
			// 清空意见
			aipObj.SetValue("Page2.suggest4", "");
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.suggest4", vRet[15]);
				} else {
					aipObj.SetValue("Page2.suggest4", jing + vRet[17]
							+ pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.suggest5", jing + vRet[17]
							+ jySuggest + vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("commenttext", jing + vRet[17] + pshSuggest
							+ vRet[15]);
					document.getElementById("commenttext").value = jing
							+ vRet[17] + pshSuggest + vRet[15];
				}
				aipObj.SetValue("Page2.text13", formatDate(new Date()));
			}
		}
		// 减刑假释监狱监狱长意见选择----------
		if (strName == 'suggest5') {
			// 清空意见
			aipObj.SetValue("Page2.suggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				aipObj.SetValue("Page2.suggest5", jing + vRet[17] + jySuggest
						+ vRet[15]);
				aipObj.SetValue("commenttext", jing + vRet[17] + jySuggest
						+ vRet[15].replace('建议', '同意提请'));
				document.getElementById("commenttext").value = jing + vRet[17]
						+ jySuggest + vRet[15];
			}// 拼接的意见有值结束
			aipObj.SetValue("Page2.text18", formatDate(new Date()));
		}// suggest5结束

	}
}
function sjzjxjsJailDanChu(aipObj, strName) {
	var cbiname = aipObj.GetValueEx("cbiname", 2, "", 0, "");
	var gjSuggest = '建议对罪犯' + cbiname;
	var jqSuggest = '经监区长办公会审核，';
	var ksSuggest = '经刑罚执行处会评审，';
	var pshSuggest = '经减刑假释评审委员会评审，';
	var jyzSuggest = '经监狱长办公会会议决议，';
	var suggest2 = aipObj.GetValueEx("sjzsuggest2", 2, "", 0, "");
	var suggest3 = aipObj.GetValueEx("sjzsuggest3", 2, "", 0, "");
	var suggest4 = aipObj.GetValueEx("sjzsuggest4", 2, "", 0, "");
	var suggest5 = aipObj.GetValueEx("sjzsuggest5", 2, "", 0, "");
	var suggest6 = aipObj.GetValueEx("sjzsuggest6", 2, "", 0, "");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, "");
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var jxjs_14 = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var jxjs_15 = aipObj.GetValueEx("isagree", 2, "", 0, "");
	var jxjs_16 = aipObj.GetValueEx("reason", 2, "", 0, "");
	var crimid = aipObj.GetValueEx("criminalid", 2, "", 0, "");
	// var jxjsyj = jxjs_1 +"@," + jxjs_2 +"@," + jxjs_3 +"@," + jxjs_4 +"@," +
	// jxjs_5 +"@," + jxjs_6 + "@,"+jxjs_0;
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	if (jxjs_9 == "1.#QNAN" || jxjs_9 == null) {
		jxjs_9 = "";
	}
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("jxjs_14:" + jxjs_14);
	jxjsyj.push("jxjs_15:" + jxjs_15);
	jxjsyj.push("jxjs_16:" + jxjs_16);
	url = basePath + "/tanChuOpinionPage.action?crimid=" + crimid + "&jxjsyj="
			+ encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1", "");
		aipObj.SetValue("Page1.jxjs_2", "");
		aipObj.SetValue("Page1.jxjs_3", "");
		aipObj.SetValue("Page1.jxjs_4", "");
		aipObj.SetValue("Page1.jxjs_5", "");
		aipObj.SetValue("Page1.jxjs_6", "");
		aipObj.SetValue("Page1.jxjs_7", "");
		aipObj.SetValue("Page1.jxjs_8", "");
		aipObj.SetValue("Page1.jxjs_9", "");
		aipObj.SetValue("Page1.jxjs_10", "");
		aipObj.SetValue("Page1.jxjs_11", "");
		aipObj.SetValue("Page1.jxjs_12", "");
		aipObj.SetValue("Page1.jxjs_13", "");
		aipObj.SetValue("Page1.payeverymon", "");
		aipObj.SetValue("Page1.isagree", "");
		aipObj.SetValue("Page1.reason", "");
		aipObj.SetValue("Page1.jxjs_date", "");
		// 返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1", vRet[0]);
		aipObj.SetValue("Page1.jxjs_2", vRet[1]);
		aipObj.SetValue("Page1.jxjs_3", vRet[2]);
		aipObj.SetValue("Page1.jxjs_4", vRet[3]);
		aipObj.SetValue("Page1.jxjs_5", vRet[4]);
		aipObj.SetValue("Page1.jxjs_6", vRet[5]);
		aipObj.SetValue("Page1.jxjs_7", vRet[6]);
		aipObj.SetValue("Page1.jxjs_8", vRet[7]);
		aipObj.SetValue("Page1.jxjs_9", vRet[8]);
		aipObj.SetValue("Page1.jxjs_10", vRet[9]);
		aipObj.SetValue("Page1.jxjs_11", vRet[10]);
		aipObj.SetValue("Page1.jxjs_12", vRet[11]);
		aipObj.SetValue("Page1.jxjs_13", vRet[12]);
		aipObj.SetValue("Page1.payeverymon", jxjs_14);
		aipObj.SetValue("Page1.isagree", vRet[13]);
		aipObj.SetValue("Page1.iscommitagree", vRet[13]);
		aipObj.SetValue("Page1.reason", vRet[14]);
		aipObj.SetValue("Page1.jxjs_date", vRet[16]);

		// 减刑假释监狱经办人意见选择----------
		if (strName == 'sjzsuggest2') {
			// 清空意见
			aipObj.SetValue("Page2.sjzsuggest2", "");
			aipObj.SetValue("Page2.sjzsuggest3", "");
			aipObj.SetValue("Page2.sjzsuggest4", "");
			aipObj.SetValue("Page2.sjzsuggest5", "");
			aipObj.SetValue("Page2.sjzsuggest6", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.sjzsuggest2", vRet[15]);
				} else {
					var gjSuggestStr = "";
					if (vRet[0] == 1) {
						gjSuggestStr = "提请假释。";
					} else {
						gjSuggestStr = "提请减刑。";
					}
					aipObj.SetValue("Page2.sjzsuggest2", gjSuggest
							+ gjSuggestStr);
					aipObj.SetValue("Page2.sjzsuggest3", jqSuggest + vRet[15]);
					aipObj.SetValue("Page2.sjzsuggest4", ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.sjzsuggest5", pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.sjzsuggest6", jyzSuggest + vRet[15]);
					aipObj.SetValue("commenttext", gjSuggest + gjSuggestStr);
					// document.getElementById("commenttext").value= gjSuggest +
					// gjSuggestStr;
				}
				aipObj.SetValue("Page2.shbtext9", formatDate(new Date()));
			}
		}
		// 减刑假释监狱监区长意见选择----------
		if (strName == 'sjzsuggest3') {
			// 清空意见
			aipObj.SetValue("Page2.sjzsuggest3", "");
			aipObj.SetValue("Page2.sjzsuggest4", "");
			aipObj.SetValue("Page2.sjzsuggest5", "");
			aipObj.SetValue("Page2.sjzsuggest6", "");
			aipObj.SetValue("commenttext", "");
			aipObj.SetValue("Page1.text11", "");
			// 填充意见信息 }
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.sjzsuggest3", vRet[15]);
				} else {
					aipObj.SetValue("Page2.sjzsuggest3", jqSuggest + vRet[15]);
					aipObj.SetValue("Page2.sjzsuggest4", ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.sjzsuggest5", pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.sjzsuggest6", jyzSuggest + vRet[15]);
					aipObj.SetValue("commenttext", jqSuggest + vRet[15]);
					// document.getElementById("commenttext").value = jqSuggest
					// + vRet[15];
				}
				aipObj.SetValue("Page2.shbtext11", formatDate(new Date()));
			}
		}
		// 减刑假释监狱科室意见选择----------
		if (strName == 'sjzsuggest4') {
			// 清空意见
			aipObj.SetValue("Page2.sjzsuggest4", "");
			aipObj.SetValue("Page2.sjzsuggest5", "");
			aipObj.SetValue("Page2.sjzsuggest6", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.sjzsuggest4", vRet[15]);
				} else {
					aipObj.SetValue("Page2.sjzsuggest4", ksSuggest + vRet[15]);
					aipObj.SetValue("Page2.sjzsuggest5", pshSuggest + vRet[15]);
					aipObj.SetValue("Page2.sjzsuggest6", jyzSuggest + vRet[15]);
					aipObj.SetValue("commenttext", ksSuggest + vRet[15]);
					// document.getElementById("commenttext").value = ksSuggest
					// + vRet[15];
				}
				aipObj.SetValue("Page2.shbtext13", formatDate(new Date()));
			}
		}
		// 减刑假释监狱监狱长意见选择----------
		if (strName == 'sjzsuggest5') {
			// 清空意见
			aipObj.SetValue("Page2.sjzsuggest5", "");
			aipObj.SetValue("Page2.sjzsuggest6", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				aipObj.SetValue("Page2.sjzsuggest5", pshSuggest + vRet[15]);
				aipObj.SetValue("Page2.sjzsuggest6", jyzSuggest + vRet[15]);
				aipObj.SetValue("commenttext", pshSuggest + vRet[15]);
				// document.getElementById("commenttext").value = pshSuggest +
				// vRet[15];
			}// 拼接的意见有值结束
			aipObj.SetValue("Page2.shbtext18", formatDate(new Date()));
		}
		if (strName == 'sjzsuggest6') {
			// 清空意见
			aipObj.SetValue("Page2.sjzsuggest6", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				aipObj.SetValue("Page2.sjzsuggest6", jyzSuggest + vRet[15]);
				aipObj.SetValue("commenttext", pshSuggest + vRet[15]);
				// document.getElementById("commenttext").value = jyzSuggest +
				// vRet[15];
			}// 拼接的意见有值结束
			aipObj.SetValue("Page2.shbtext19", formatDate(new Date()));
		}
	}
}

/**
 * 离开监管区罪犯选择
 */
function leaveAreaChooseCriminal(aipObj, strName) {
	var i = strName.substr(strName.length - 1);
	url = basePath + "/chooseCriminalPage.page?1=1";
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		aipObj.SetValue("lkcrimid" + i, "");
		aipObj.SetValue("name" + i, "");
		aipObj.SetValue("charge" + i, "");
		aipObj.SetValue("yuanpan" + i, "");
		aipObj.SetValue("yuxing" + i, "");

		if (vRet[0] == 1) {
			if (vRet[1]) {
				aipObj.SetValue("lkcrimid" + i, vRet[1]);
			}
			if (vRet[2]) {
				aipObj.SetValue("name" + i, vRet[2]);
			}
			if (vRet[3]) {
				aipObj.SetValue("charge" + i, vRet[3]);
			}
			if (vRet[4]) {
				aipObj.SetValue("yuanpan" + i, vRet[4]);
			}
			if (vRet[5]) {
				aipObj.SetValue("yuxing" + i, vRet[5]);
			}
		}
	}
}

/**
 * 广东监狱减假意见弹框
 * 
 * @param {}
 *            aipObj
 * @param {}
 *            strName
 */
function gdjyjxjsJailDanChu(aipObj, strName) {
	var gjSuggest = '';          //管教意见
	var fjqcbSuggest = '';       //分监区承办人意见
	var fjqSuggest = '';         //分监区意见
	var jqcbSuggest = '';        //监区承办人意见
	var jqSuggest = '';          //监区意见
	var kscbSuggest = '';        //科室承办人意见
	var ksSuggest = '';          //科室意见
	var pshSuggest = '';         //评审会意见
	var jyzSuggest = '';         //监狱长意见
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, ""); // 减假类型
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var payeverymon = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var isagree = aipObj.GetValueEx("isagree", 2, "", 0, ""); // 是否同意
	var range = aipObj.GetValueEx("range", 2, "", 0, ""); // 默认建议减刑幅度
	var mapkey5 = aipObj.GetValueEx("mapkey5", 2, "", 0, ""); // 最后填写意见
	var crimid = aipObj.GetValueEx("crimid", 2, "", 0, ""); // 查询系统模板所用罪犯编号
	if (!crimid) {
		crimid = aipObj.GetValueEx("criminalid", 2, "", 0, "");
	}
	var boquan = aipObj.GetValueEx("boquan", 2, "", 0, ""); // 原判剥权
	var cbiname = aipObj.GetValueEx("cbiname", 2, "", 0, "");// 罪犯姓名
	var orgid = aipObj.GetValueEx("orgid", 2, "", 0, "");// 机构ID
	var tempids = aipObj.GetValueEx("tempids", 2, "", 0, "");// 减刑意见模板编号
	var nosuggest = aipObj.GetValueEx("nosuggest", 2, "", 0, "");// 不同意意见
	var jxbasis = aipObj.GetValueEx("jxbasis", 2, "", 0, "");// 筛查依据
	var reason = aipObj.GetValueEx(strName, 2, "", 0, ""); // 表单显示意见
	if (!tempids) {
		tempids = '10615,10483,10617,10602,10610,10482,10481,10487,10581,10582,10583,10584';
	}
	$.ajax({
		type : "POST",
		url : basePath + "/findContent.json",
		data : {
			crimid : crimid,
			tempids : tempids
		},
		cache : false,
		async : false,
		dataType : "text",
		success : function(text) {
			var obj = eval(text);
			if (obj) {
				gjSuggest = obj[0];          //管教意见
				fjqcbSuggest = obj[1];       //分监区承办人意见
				fjqSuggest = obj[2];         //分监区意见
				jqcbSuggest = obj[3];        //监区承办人意见
				jqSuggest = obj[4];          //监区意见
				kscbSuggest = obj[5];        //科室承办人意见
				ksSuggest = obj[6];          //科室意见
				pshSuggest = obj[7];         //评审会意见
				jyzSuggest = obj[8];         //监狱长意见
				

				csjbSjSuggest = obj[8];     //处室经办人意见
				csshSjSuggest = obj[9];    //处室审核意见
				pshSjSuggest = obj[10];     //评审会意见
				jzqfSjSuggest = obj[11];    //监狱局意见
			}
		}
	});

	switch (strName) {
    //监狱意见
	case 'gdjysuggest1':
		tempids = gjSuggest;
		break;
	case 'gdjysuggest2':
		tempids = fjqcbSuggest;
		break;
	case 'gdjysuggest3':
		tempids = fjqSuggest;
		break;
	case 'gdjysuggest4':
		tempids = jqcbSuggest;
		break;
	case 'gdjysuggest5':
		tempids = jqSuggest;
		break;
	case 'gdjysuggest6':
		tempids = kscbSuggest;
		break;
	case 'gdjysuggest7':
		tempids = ksSuggest;
		break;
	case 'gdjysuggest8':
		tempids = pshSuggest;
		break;
	case 'gdjysuggest9':
		tempids = jyzSuggest;
		break;
		
    //省局意见
	case 'sjsuggest2':
		tempids = csjbSjSuggest;
		break;
	case 'sjsuggest3':
		tempids = csshSjSuggest;
		break;
	case 'sjsuggest4':
		tempids = pshSjSuggest;
		break;
	case 'sjsuggest5':
		tempids = jzqfSjSuggest;
		break;
	}

	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	if (jxjs_9 == "1.#QNAN" || jxjs_9 == null) {
		jxjs_9 = "";
	}
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("payeverymon:" + payeverymon);
	if (!isagree) {
		isagree = "1";
	}
	jxjsyj.push("isagree:" + isagree);
	jxjsyj.push("mapkey5:" + mapkey5);
	jxjsyj.push("boquan:" + boquan);
	jxjsyj.push("range:" + range);
	jxjsyj.push("strname:" + strName);
	jxjsyj.push("cbiname:" + cbiname);
	var obj = new Object();
	obj.reason = reason;
	obj.tempids = tempids;
	obj.jxbasis = jxbasis;
	obj.nosuggest = nosuggest;
	
	url = basePath + "/tanChuOpinionPage.action?1=1&crimid=" + crimid
			+ "&jxjsyj=" + encodeURI(encodeURI(jxjsyj)) + "&strName=" + strName;
	
	if (strName.indexOf("sjsuggest") < 0) {
		vRet = window
				.showModalDialog(
						url,
						obj,
						"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:370px;dialogWidth:600px");
	} else {
		vRet = window
				.showModalDialog(
						url,
						obj,
						"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:370px;dialogWidth:600px");
	}
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("jxjs_1", "");
		aipObj.SetValue("jxjs_2", "");
		aipObj.SetValue("jxjs_3", "");
		aipObj.SetValue("jxjs_4", "");
		aipObj.SetValue("jxjs_5", "");
		aipObj.SetValue("jxjs_6", "");
		aipObj.SetValue("jxjs_7", "");
		aipObj.SetValue("jxjs_8", "");
		aipObj.SetValue("jxjs_9", "");
		aipObj.SetValue("jxjs_10", "");
		aipObj.SetValue("jxjs_11", "");
		aipObj.SetValue("jxjs_12", "");
		aipObj.SetValue("jxjs_13", "");
		aipObj.SetValue("payeverymon", "");
		aipObj.SetValue("isagree", "");
		aipObj.SetValue("mapkey5", "");
		aipObj.SetValue("reason", "");
		aipObj.SetValue("jxjs_date", "");
		aipObj.SetValue("xgreason", "");
		// 返回值传到隐藏域
		aipObj.SetValue("jxjs_1", vRet[0]);
		aipObj.SetValue("jxjs_2", vRet[1]);
		aipObj.SetValue("jxjs_3", vRet[2]);
		aipObj.SetValue("jxjs_4", vRet[3]);
		aipObj.SetValue("jxjs_5", vRet[4]);
		aipObj.SetValue("jxjs_6", vRet[5]);
		aipObj.SetValue("jxjs_7", vRet[6]);
		aipObj.SetValue("jxjs_8", vRet[7]);
		aipObj.SetValue("jxjs_9", vRet[8]);
		aipObj.SetValue("jxjs_10", vRet[9]);
		aipObj.SetValue("jxjs_11", vRet[10]);
		aipObj.SetValue("jxjs_12", vRet[11]);
		aipObj.SetValue("jxjs_13", vRet[12]);
		aipObj.SetValue("payeverymon", payeverymon);
		aipObj.SetValue("isagree", vRet[13]);
		aipObj.SetValue("mapkey5", vRet[15]);// 建议书用减刑意见
		aipObj.SetValue("reason", vRet[14]);
		aipObj.SetValue("jxjs_date", vRet[16]);
		aipObj.SetValue("xgreason", vRet[17]);

		if (vRet[0] == 1) { // 修改“符合减刑（假释）条件”
			//监狱意见
			if (gjSuggest) {
				gjSuggest = replaceAllStr(gjSuggest, '(减刑假释)', '假释');//gjSuggest.replace('(减刑假释)', '假释');
			}
			if (fjqcbSuggest) {
				fjqcbSuggest = replaceAllStr(fjqcbSuggest, '(减刑假释)', '假释');//fjqcbSuggest.replace('(减刑假释)', '假释');
			}
			if (fjqSuggest) {
				fjqSuggest = replaceAllStr(fjqSuggest, '(减刑假释)', '假释');//fjqSuggest.replace('(减刑假释)', '假释');
			}
			if (jqcbSuggest) {
				jqcbSuggest = replaceAllStr(jqcbSuggest, '(减刑假释)', '假释');//jqcbSuggest.replace('(减刑假释)', '假释');
			}
			if (jqSuggest) {
				jqSuggest = replaceAllStr(jqSuggest, '(减刑假释)', '假释');//jqSuggest.replace('(减刑假释)', '假释');
			}
			if (kscbSuggest) {
				kscbSuggest = replaceAllStr(kscbSuggest, '(减刑假释)', '假释');//kscbSuggest.replace('(减刑假释)', '假释');
			}
			if (ksSuggest) {
				ksSuggest = replaceAllStr(ksSuggest, '(减刑假释)', '假释');//ksSuggest.replace('(减刑假释)', '假释');
			}
			if (pshSuggest) {
				pshSuggest = replaceAllStr(pshSuggest, '(减刑假释)', '假释');//pshSuggest.replace('(减刑假释)', '假释');
			}
			if (jyzSuggest) {
				jyzSuggest = replaceAllStr(jyzSuggest, '(减刑假释)', '假释');//jyzSuggest.replace('(减刑假释)', '假释');
			}
            
			//省局意见
			if (csjbSjSuggest) {
				csjbSjSuggest = replaceAllStr(csjbSjSuggest, '(减刑假释)', '假释');//csjbSjSuggest.replace('(减刑假释)', '假释');
			}
			if (csshSjSuggest) {
				csshSjSuggest = replaceAllStr(csshSjSuggest, '(减刑假释)', '假释');//csshSjSuggest.replace('(减刑假释)', '假释');
			}
			if (pshSjSuggest) {
				pshSjSuggest = replaceAllStr(pshSjSuggest, '(减刑假释)', '假释');//pshSjSuggest.replace('(减刑假释)', '假释');
			}
			if (jzqfSjSuggest) {
				jzqfSjSuggest = replaceAllStr(jzqfSjSuggest, '(减刑假释)', '假释');//jzqfSjSuggest.replace('(减刑假释)', '假释');
			}

		} else {
			//监狱意见
			if (gjSuggest) {
				gjSuggest = replaceAllStr(gjSuggest, '(减刑假释)', '减刑');//gjSuggest.replace('(减刑假释)', '减刑');
			}
			if (fjqcbSuggest) {
				fjqcbSuggest = replaceAllStr(fjqcbSuggest, '(减刑假释)', '减刑');//fjqcbSuggest.replace('(减刑假释)', '减刑');
			}
			if (fjqSuggest) {
				fjqSuggest = replaceAllStr(fjqSuggest, '(减刑假释)', '减刑');//fjqSuggest.replace('(减刑假释)', '减刑');
			}
			if (jqcbSuggest) {
				jqcbSuggest = replaceAllStr(jqcbSuggest, '(减刑假释)', '减刑');//jqcbSuggest.replace('(减刑假释)', '减刑');
			}
			if (jqSuggest) {
				jqSuggest = replaceAllStr(jqSuggest, '(减刑假释)', '减刑');//jqSuggest.replace('(减刑假释)', '减刑');
			}
			if (kscbSuggest) {
				kscbSuggest = replaceAllStr(kscbSuggest, '(减刑假释)', '减刑');//kscbSuggest.replace('(减刑假释)', '减刑');
			}
			if (ksSuggest) {
				ksSuggest = replaceAllStr(ksSuggest, '(减刑假释)', '减刑');//ksSuggest.replace('(减刑假释)', '减刑');
			}
			if (pshSuggest) {
				pshSuggest = replaceAllStr(pshSuggest, '(减刑假释)', '减刑');//pshSuggest.replace('(减刑假释)', '减刑');
			}
			if (jyzSuggest) {
				jyzSuggest = replaceAllStr(jyzSuggest, '(减刑假释)', '减刑');//jyzSuggest.replace('(减刑假释)', '减刑');
			}
            
			//省局意见
			if (csjbSjSuggest) {
				csjbSjSuggest = replaceAllStr(csjbSjSuggest, '(减刑假释)', '减刑');//csjbSjSuggest.replace('(减刑假释)', '减刑');
			}
			if (csshSjSuggest) {
				csshSjSuggest = replaceAllStr(csshSjSuggest, '(减刑假释)', '减刑');//csshSjSuggest.replace('(减刑假释)', '减刑');
			}
			if (pshSjSuggest) {
				pshSjSuggest = replaceAllStr(pshSjSuggest, '(减刑假释)', '减刑');//pshSjSuggest.replace('(减刑假释)', '减刑');
			}
			if (jzqfSjSuggest) {
				jzqfSjSuggest = replaceAllStr(jzqfSjSuggest, '(减刑假释)', '减刑');//jzqfSjSuggest.replace('(减刑假释)', '减刑');
			}
		}

		// 减刑假释监狱经办人意见选择----------
		if (strName == 'gdjysuggest1') {
			// 清空意见
			aipObj.SetValue("gdjysuggest1", "");
			aipObj.SetValue("gdjysuggest2", "");
			aipObj.SetValue("gdjysuggest3", "");
			aipObj.SetValue("gdjysuggest4", "");
			aipObj.SetValue("gdjysuggest5", "");
			aipObj.SetValue("gdjysuggest6", "");
			aipObj.SetValue("gdjysuggest7", "");
			aipObj.SetValue("gdjysuggest8", "");
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// alert(vRet[14]);
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest1", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest1", vRet[14]);
					aipObj.SetValue("gdjysuggest2", fjqcbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest3", fjqSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest4", jqcbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest5", jqSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest6", kscbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest7", ksSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest8", pshSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest9", jyzSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		if (strName == 'gdjysuggest2') {
			// 清空意见
			aipObj.SetValue("gdjysuggest2", "");
			aipObj.SetValue("gdjysuggest3", "");
			aipObj.SetValue("gdjysuggest4", "");
			aipObj.SetValue("gdjysuggest5", "");
			aipObj.SetValue("gdjysuggest6", "");
			aipObj.SetValue("gdjysuggest7", "");
			aipObj.SetValue("gdjysuggest8", "");
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// alert(vRet[14]);
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest2", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest2", vRet[14]);
					aipObj.SetValue("gdjysuggest3", fjqSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest4", jqcbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest5", jqSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest6", kscbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest7", ksSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest8", pshSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest9", jyzSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		// 减刑假释监狱分监区长意见选择----------
		if (strName == 'gdjysuggest3') {
			// 清空意见
			aipObj.SetValue("gdjysuggest3", "");
			aipObj.SetValue("gdjysuggest4", "");
			aipObj.SetValue("gdjysuggest5", "");
			aipObj.SetValue("gdjysuggest6", "");
			aipObj.SetValue("gdjysuggest7", "");
			aipObj.SetValue("gdjysuggest8", "");
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// alert(vRet[14]);
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest3", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest3", vRet[14]);
					aipObj.SetValue("gdjysuggest4", jqcbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest5", jqSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest6", kscbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest7", ksSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest8", pshSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest9", jyzSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		// 减刑假释监狱监区承办人意见
		if (strName == 'gdjysuggest4') {
			// 清空意见
			aipObj.SetValue("gdjysuggest4", "");
			aipObj.SetValue("gdjysuggest5", "");
			aipObj.SetValue("gdjysuggest6", "");
			aipObj.SetValue("gdjysuggest7", "");
			aipObj.SetValue("gdjysuggest8", "");
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest4", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest4", vRet[14]);
					aipObj.SetValue("gdjysuggest5", jqSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest6", kscbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest7", ksSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest8", pshSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest9", jyzSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		// 减刑假释监狱监区意见
		if (strName == 'gdjysuggest5') {
			// 清空意见
			aipObj.SetValue("gdjysuggest5", "");
			aipObj.SetValue("gdjysuggest6", "");
			aipObj.SetValue("gdjysuggest7", "");
			aipObj.SetValue("gdjysuggest8", "");
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest5", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest5", vRet[14]);
					aipObj.SetValue("gdjysuggest6", kscbSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest7", ksSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest8", pshSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest9", jyzSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		// 减刑假释监狱刑罚执行科主管意见
		if (strName == 'gdjysuggest6') {
			// 清空意见
			aipObj.SetValue("gdjysuggest6", "");
			aipObj.SetValue("gdjysuggest7", "");
			aipObj.SetValue("gdjysuggest8", "");
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest6", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest6", vRet[14]);
					aipObj.SetValue("gdjysuggest7", ksSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest8", pshSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest9", jyzSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		// 减刑假释监狱刑罚执行科意见
		if (strName == 'gdjysuggest7') {
			// 清空意见
			aipObj.SetValue("gdjysuggest7", "");
			aipObj.SetValue("gdjysuggest8", "");
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest7", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest7", vRet[14]);
					aipObj.SetValue("gdjysuggest8", pshSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("gdjysuggest9", jyzSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		// 减刑假释监狱评审会意见
		if (strName == 'gdjysuggest8') {
			// 清空意见
			aipObj.SetValue("gdjysuggest8", "");
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest8", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest8", vRet[14]);
					aipObj.SetValue("gdjysuggest9", jyzSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		// 减刑假释监狱监狱意见
		if (strName == 'gdjysuggest9') {
			// 清空意见
			aipObj.SetValue("gdjysuggest9", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gdjysuggest9", vRet[15]);
				} else {
					aipObj.SetValue("gdjysuggest9", vRet[14]);
					aipObj.SetValue("commenttext", gjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				if(vRet[14].indexOf("无") < 0){
					var shbtext9 = aipObj.GetValueEx("shbtext9", 2, "", 0, "");
					if (shbtext9.length == 0) {
						aipObj.SetValue("shbtext9", _formatDate(new Date()));
					}
				}else{
					aipObj.SetValue("shbtext9","");
				}
			}
		}
		// 减刑假释处室经办意见选择----------
		if (strName == 'sjsuggest2') {
			// 清空意见
			aipObj.SetValue("sjsuggest2", "");
			aipObj.SetValue("sjsuggest3", "");
			aipObj.SetValue("sjsuggest4", "");
			aipObj.SetValue("sjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("sjsuggest2", vRet[15]);
				} else {
					aipObj.SetValue("sjsuggest2", vRet[14]);
					aipObj.SetValue("sjsuggest3", csshSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("sjsuggest4", pshSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("sjsuggest5", jzqfSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", csjbSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				aipObj.SetValue("shbtext24", formatDate(new Date()));
			}
		}
		// 减刑假释处室审核意见选择----------
		if (strName == 'sjsuggest3') {
			// 清空意见
			aipObj.SetValue("sjsuggest3", "");
			aipObj.SetValue("sjsuggest4", "");
			aipObj.SetValue("sjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("sjsuggest3", vRet[15]);
				} else {
					aipObj.SetValue("sjsuggest3", vRet[14]);
					aipObj.SetValue("sjsuggest4", pshSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("sjsuggest5", jzqfSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", csshSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				aipObj.SetValue("shbtext30", formatDate(new Date()));
			}
		}
		// 减刑假释评审会审批意见选择----------
		if (strName == 'sjsuggest4') {
			// 清空意见
			aipObj.SetValue("sjsuggest4", "");
			aipObj.SetValue("sjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("sjsuggest4", vRet[15]);
				} else {
					aipObj.SetValue("sjsuggest4", vRet[14]);
					aipObj.SetValue("sjsuggest5", jzqfSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
					aipObj.SetValue("commenttext", pshSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				aipObj.SetValue("shbtext36", formatDate(new Date()));
			}
		}
		// 减刑假释局长审批意见选择----------
		if (strName == 'sjsuggest5') {
			// 清空意见
			aipObj.SetValue("sjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("sjsuggest5", vRet[15]);
				} else {
					aipObj.SetValue("sjsuggest5", vRet[14]);
					aipObj.SetValue("commenttext", jzqfSjSuggest.replace('@',vRet[15]).replace('GK054', vRet[7]));
				}
				aipObj.SetValue("shbtext37", formatDate(new Date()));
			}
		}
	}
}

function _formatDate(date) {
	var year = date.getFullYear();
	var yue = date.getMonth() + 1;
	var day = date.getDate();
	var str = year + (yue > 9 ? yue.toString() : '0' + yue)
			+ (day > 9 ? day.toString() : '0' + day);

	return str;
}

/*
 * 监狱保外就医
 */

// 监狱保外就医审批表（暂予监外执行 审批表） 弹出意见
function bwjyspbTanChu(aipObj, strName) {
	var fjqSuggest = '经分监区全体警察集体评议，';
	var jqSuggest = '经监区长办公会审核，';
	var ksSuggest = '经刑罚执行科评审，';
	var pshSuggest = '经保外评审委员会评审，';
	var jySuggest = '经监狱长办公会议决定，';
	var crimid = aipObj.GetValueEx("criminalid", 2, "", 0, "");
	// var unlimit = aipObj.GetValueEx("unlimit",2,"",0,"");
	var bystartdate = aipObj.GetValueEx("bystartdate", 2, "", 0, "");
	var byenddate = aipObj.GetValueEx("byenddate", 2, "", 0, "");
	// var fcrq = aipObj.GetValueEx("fcrq",2,"",0,"");
	var select3 = aipObj.GetValueEx("select3", 2, "", 0, "");
	url = basePath + "/bwjyspbTanChuOpinionPage.action?crimid=" + crimid
			+ "&bystartdate=" + encodeURI(encodeURI(bystartdate))
			+ "&byenddate=" + encodeURI(encodeURI(byenddate)) + "&select3="
			+ encodeURI(encodeURI(select3));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:350px");
	var reg = new RegExp("-", "g");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.bystartdate", "");
		aipObj.SetValue("Page1.byenddate", "");
		// aipObj.SetValue("Page1.unlimit","");
		// aipObj.SetValue("Page1.fcrq","");
		aipObj.SetValue("Page1.select3", "");
		// 返回值传到隐藏域
		aipObj.SetValue("Page1.bystartdate", vRet[0].replace(reg, ''));
		aipObj.SetValue("Page1.byenddate", vRet[1].replace(reg, ''));
		// aipObj.SetValue("Page1.unlimit",vRet[2]);
		// aipObj.SetValue("Page1.fcrq",vRet[5]);
		aipObj.SetValue("Page1.select3", vRet[4]);
		aipObj.SetValue("Page1.jxjs_1", "2");

		// 经办人
		if (strName == 'bwjyzhpgyj') {
			aipObj.SetValue("Page2.bwjyzhpgyj", "");
			aipObj.SetValue("Page2.bwjyjqyj", "");
			aipObj.SetValue("Page2.bwjyksyj", "");
			aipObj.SetValue("Page2.bwjypshyj", "");
			aipObj.SetValue("Page2.bwjyjyyj", "");
			if (vRet[3]) {
				aipObj.SetValue("Page2.bwjyzhpgyj", fjqSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjyjqyj", jqSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjyksyj", ksSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjypshyj", pshSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjyjyyj", jySuggest + vRet[3]);
			}
		}
		// 监区
		if (strName == 'bwjyjqyj') {
			aipObj.SetValue("Page2.bwjyjqyj", "");
			aipObj.SetValue("Page2.bwjyksyj", "");
			aipObj.SetValue("Page2.bwjypshyj", "");
			aipObj.SetValue("Page2.bwjyjyyj", "");
			if (vRet[3]) {
				aipObj.SetValue("Page2.bwjyjqyj", jqSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjyksyj", ksSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjypshyj", pshSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjyjyyj", jySuggest + vRet[3]);
				aipObj.SetValue("Page2.text12", formatDate(new Date()));
			}
		}
		// 科室
		if (strName == 'bwjyksyj') {
			aipObj.SetValue("Page2.bwjyksyj", "");
			aipObj.SetValue("Page2.bwjypshyj", "");
			aipObj.SetValue("Page2.bwjyjyyj", "");
			if (vRet[3]) {
				aipObj.SetValue("Page2.bwjyksyj", ksSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjypshyj", pshSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjyjyyj", jySuggest + vRet[3]);
				aipObj.SetValue("Page2.text14", formatDate(new Date()));
			}
		}
		// 评审会
		if (strName == 'bwjypshyj') {
			aipObj.SetValue("Page2.bwjypshyj", "");
			aipObj.SetValue("Page2.bwjyjyyj", "");
			if (vRet[3]) {
				aipObj.SetValue("Page2.bwjypshyj", pshSuggest + vRet[3]);
				aipObj.SetValue("Page2.bwjyjyyj", jySuggest + vRet[3]);
				aipObj.SetValue("Page2.text13", formatDate(new Date()));
			}
		}
		// 监狱
		if (strName == 'bwjyjyyj') {
			aipObj.SetValue("Page2.bwjyjyyj", "");
			if (vRet[3]) {
				aipObj.SetValue("Page2.bwjyjyyj", jySuggest + vRet[3]);
				aipObj.SetValue("Page2.text15", formatDate(new Date()));
			}
		}
	}
}

// 监狱保外就医审批表（暂予监外执行 审批表） 弹出意见(宁夏)
function bwjyspbTanChu_nx(aipObj, strName) {
	var jingguo = '    经';
	var crimid = aipObj.GetValueEx("criminalid", 2, "", 0, "");
	var cbiname = aipObj.GetValueEx("name", 2, "", 0, "");
	// var unlimit = aipObj.GetValueEx("unlimit",2,"",0,"");
	var bystartdate = aipObj.GetValueEx("bystartdate", 2, "", 0, "");
	var byenddate = aipObj.GetValueEx("byenddate", 2, "", 0, "");
	// var fcrq = aipObj.GetValueEx("fcrq",2,"",0,"");
	var select3 = aipObj.GetValueEx("select3", 2, "", 0, "");

	var strContent = aipObj.GetValueEx(strName, 2, "", 0, ""); // 点击 当前节点的值
	url = basePath + "/bwjyspbTanChuOpinionPage.action?crimid=" + crimid
			+ "&bystartdate=" + encodeURI(encodeURI(bystartdate)) + "&strName="
			+ encodeURI(encodeURI(strName)) + "&strContent="
			+ encodeURI(encodeURI(strContent)) + "&cbiname="
			+ encodeURI(encodeURI(cbiname));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:320px;dialogWidth:350px");
	var reg = new RegExp("-", "g");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.bystartdate", "");
		aipObj.SetValue("Page1.byenddate", "");
		aipObj.SetValue("Page1.jxjs_1", "2");

		// 经办人
		if (strName == 'bwjyzhpgyj') {
			aipObj.SetValue("Page2.bwjyzhpgyj", "");
			if (vRet[0]) {
				aipObj.SetValue("Page2.bwjyzhpgyj", vRet[0]);
			}
		}
		// 监区
		if (strName == 'bwjyjqyj') {
			aipObj.SetValue("Page2.bwjyjqyj", "");
			if (vRet[0]) {
				aipObj.SetValue("Page2.bwjyjqyj", vRet[0]);
				aipObj.SetValue("Page2.text12", formatDate(new Date()));
			}
		}
		// 科室
		if (strName == 'bwjyksyj') {
			aipObj.SetValue("Page2.bwjyksyj", "");
			if (vRet[0]) {
				aipObj.SetValue("Page2.bwjyksyj", vRet[0]);
				aipObj.SetValue("Page2.text14", formatDate(new Date()));
			}
		}
		// 监狱
		if (strName == 'bwjyjyyj') {
			aipObj.SetValue("Page2.bwjyjyyj", "");
			if (vRet[0]) {
				aipObj.SetValue("Page2.bwjyjyyj", vRet[0]);
				aipObj.SetValue("Page2.text15", formatDate(new Date()));
			}
		}
	}
}

function rewardPunishsPointsEdit(aipObj) {
	var paymentzk = aipObj.GetValueEx("paymentzk", 2, "", 0, "");
	var paymentpc = aipObj.GetValueEx("paymentpc", 2, "", 0, "");
	var reason = aipObj.GetValueEx("reason", 2, "", 0, "");
	var points = aipObj.GetValueEx("points", 2, "", 0, "");
	var according = aipObj.GetValueEx("according", 2, "", 0, "");
	var paymentcc = aipObj.GetValueEx("paymentcc", 2, "", 0, "");

	url = basePath + "/rewardPunishsPointsEdit.action?1=1";
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:550px;dialogWidth:800px");

	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.paymentzk", "");
		aipObj.SetValue("Page1.paymentpc", "");
		aipObj.SetValue("Page1.reason", "");
		aipObj.SetValue("Page1.points", "");
		aipObj.SetValue("Page1.according", "");
		aipObj.SetValue("Page1.paymentcc", "");

		if (paymentzk != "") {// 将扣分条款id
			aipObj.SetValue("Page1.paymentzk", paymentzk + "," + vRet[0]);
		} else {
			aipObj.SetValue("Page1.paymentzk", vRet[0]);
		}

		if (reason != "") {// 将扣分理由
			aipObj.SetValue("Page1.reason", reason + "；" + vRet[1]);
		} else {
			aipObj.SetValue("Page1.reason", vRet[1]);
		}

		if (paymentpc != "") {// 将扣分类型
			aipObj.SetValue("Page1.paymentpc", paymentpc + "," + vRet[2]);
		} else {
			aipObj.SetValue("Page1.paymentpc", vRet[2]);
		}

		if (points != "") {
			if (vRet[2] == "0") {
				points = parseInt(points) - parseInt(vRet[3]);// 将扣分分值
				aipObj.SetValue("Page1.points", points);
				// aipObj.SetValue("Page1.points",points + "-" + vRet[3]);
				paymentcc = paymentcc + ",-" + vRet[3];
			} else {
				points = parseInt(points) + parseInt(vRet[3]);
				aipObj.SetValue("Page1.points", points);
				// aipObj.SetValue("Page1.points",points + "+" + vRet[3]);
				paymentcc = paymentcc + "," + vRet[3];
			}
		} else {
			if (vRet[2] == "0") {
				aipObj.SetValue("Page1.points", "-" + vRet[3]);
				paymentcc = "-" + vRet[3];
			} else {
				aipObj.SetValue("Page1.points", vRet[3]);
				paymentcc = vRet[3];
			}
		}

		if (according != "") {
			aipObj
					.SetValue("Page1.according", according + "\r\n\r\n"
							+ vRet[4]);// 将扣分内容
		} else {
			aipObj.SetValue("Page1.according", vRet[4]);
		}
		aipObj.SetValue("Page1.paymentcc", paymentcc);
	}
}

function rewardPunishsPointsCrimSelect(aipObj) {
	url = basePath + "/batchRewardPunishlPointsCrimSelect.action?1=1";
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:450px;dialogWidth:800px");
	if (vRet) {
		aipObj.SetValue("ids", "");
		aipObj.SetValue("jiangfenpersons", "");
		aipObj.SetValue("ids", vRet[0]);
		aipObj.SetValue("jiangfenpersons", vRet[1]);
	}
}

function rewardApprovalCrimSelect(aipObj) {
	var pastseasonrewardcrimidval = aipObj.GetValueEx("pastseasonrewardcrimid",
			2, "", 0, "");
	url = basePath + "/batchRewardApprovalCrimSelect.action?1=1&selected="
			+ pastseasonrewardcrimidval + "&sortval=seasonsort";
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:550px;dialogWidth:800px");

	if (vRet) {
		aipObj.SetValue("Page1.rewardcrimid", "");
		aipObj.SetValue("Page1.seasonrewardname", "");
		aipObj.SetValue("Page1.seasonrewardcrimid", "");

		aipObj.SetValue("Page1.seasonrewardname", vRet[1]);
		aipObj.SetValue("Page1.seasonrewardcrimid", vRet[0]);

		if (pastseasonrewardcrimidval != "") {
			aipObj.SetValue("Page1.rewardcrimid", vRet[0] + ","
					+ pastseasonrewardcrimidval);
		} else {
			aipObj.SetValue("Page1.rewardcrimid", vRet[0]);
		}
	}
}

function rewardPastseasonApprovalCrimSelect(aipObj) {
	var seasonrewardcrimidval = aipObj.GetValueEx("seasonrewardcrimid", 2, "",
			0, "");
	url = basePath + "/batchRewardApprovalCrimSelect.action?1=1&selected="
			+ seasonrewardcrimidval + "&sortval=pastseasonsort";
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:550px;dialogWidth:800px");

	if (vRet) {
		aipObj.SetValue("Page1.rewardcrimid", "");
		aipObj.SetValue("Page1.pastseasonrewardname", "");
		aipObj.SetValue("Page1.pastseasonrewardcrimid", "");

		aipObj.SetValue("Page1.pastseasonrewardname", vRet[1]);
		aipObj.SetValue("Page1.pastseasonrewardcrimid", vRet[0]);
		if (seasonrewardcrimidval != "") {
			aipObj.SetValue("Page1.rewardcrimid", vRet[0] + ","
					+ seasonrewardcrimidval);
		} else {
			aipObj.SetValue("Page1.rewardcrimid", vRet[0]);
		}
	}
}

/*
 * 省局弹出框
 */
function jxjsProvinceDanChu(aipObj, strName) {
	var csjbSjSuggest = '';
	var csshSjSuggest = '';
	var pshSjSuggest = '';
	var jzqfSjSuggest = '';
	var sjsuggest2 = aipObj.GetValueEx("sjsuggest2", 2, "", 0, "");
	var sjsuggest3 = aipObj.GetValueEx("sjsuggest3", 2, "", 0, "");
	var sjsuggest4 = aipObj.GetValueEx("sjsuggest4", 2, "", 0, "");
	var sjsuggest5 = aipObj.GetValueEx("sjsuggest5", 2, "", 0, "");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, "");
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var jxjs_14 = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var jxjs_15 = aipObj.GetValueEx("isagree", 2, "", 0, "");
	var jxjs_16 = aipObj.GetValueEx("reason", 2, "", 0, "");
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("jxjs_14:" + jxjs_14);
	jxjsyj.push("jxjs_15:" + jxjs_15);
	jxjsyj.push("jxjs_16:" + jxjs_16);

	url = basePath + "/sjtanChuOpinionPage.action?jxjsyj="
			+ encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("jxjs_1", "");
		aipObj.SetValue("jxjs_2", "");
		aipObj.SetValue("jxjs_3", "");
		aipObj.SetValue("jxjs_4", "");
		aipObj.SetValue("jxjs_5", "");
		aipObj.SetValue("jxjs_6", "");
		aipObj.SetValue("jxjs_7", "");
		aipObj.SetValue("jxjs_8", "");
		aipObj.SetValue("jxjs_9", "");
		aipObj.SetValue("jxjs_10", "");
		aipObj.SetValue("jxjs_11", "");
		aipObj.SetValue("jxjs_12", "");
		aipObj.SetValue("jxjs_13", "");
		aipObj.SetValue("payeverymon", "");
		aipObj.SetValue("isagree", "");
		aipObj.SetValue("reason", "");
		// 返回值传到隐藏域
		aipObj.SetValue("jxjs_1", vRet[0]);
		aipObj.SetValue("jxjs_2", vRet[1]);
		aipObj.SetValue("jxjs_3", vRet[2]);
		aipObj.SetValue("jxjs_4", vRet[3]);
		aipObj.SetValue("jxjs_5", vRet[4]);
		aipObj.SetValue("jxjs_6", vRet[5]);
		aipObj.SetValue("jxjs_7", vRet[6]);
		aipObj.SetValue("jxjs_8", vRet[7]);
		aipObj.SetValue("jxjs_9", vRet[8]);
		aipObj.SetValue("jxjs_10", vRet[9]);
		aipObj.SetValue("jxjs_11", vRet[10]);
		aipObj.SetValue("jxjs_12", vRet[11]);
		aipObj.SetValue("jxjs_13", vRet[12]);
		aipObj.SetValue("payeverymon", jxjs_14);
		aipObj.SetValue("isagree", vRet[13]);
		aipObj.SetValue("iscommitagree", vRet[13]);
		aipObj.SetValue("reason", vRet[14]);
		if (vRet[0] == '1') {
			aipObj.SetValue("jxorjs", "");
			aipObj.SetValue("jxorjs", "罪犯假释审批表");
		} else {
			aipObj.SetValue("jxorjs", "");
			aipObj.SetValue("jxorjs", "罪犯减刑审批表");
		}
		// 减刑假释处室经办意见选择----------
		if (strName == 'sjsuggest2') {
			// 清空意见
			aipObj.SetValue("sjsuggest2", "");
			aipObj.SetValue("sjsuggest3", "");
			aipObj.SetValue("sjsuggest4", "");
			aipObj.SetValue("sjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("sjsuggest2", vRet[15]);
				} else {
					aipObj.SetValue("sjsuggest2", csjbSjSuggest + vRet[15]);
					aipObj.SetValue("sjsuggest3", csshSjSuggest + vRet[15]);
					aipObj.SetValue("sjsuggest4", jzqfSjSuggest + vRet[15]);
					aipObj.SetValue("sjsuggest5", pshSjSuggest + vRet[15]);
					aipObj.SetValue("commenttext", csjbSjSuggest + vRet[15]);
					if (document.getElementById("commenttext")) {
						document.getElementById("commenttext").value = csjbSjSuggest
								+ vRet[15];
					}
				}
				aipObj.SetValue("shbtext24", formatDate(new Date()));
			}
		}
		// 减刑假释处室审核意见选择----------
		if (strName == 'sjsuggest3') {
			// 清空意见
			aipObj.SetValue("sjsuggest3", "");
			aipObj.SetValue("sjsuggest4", "");
			aipObj.SetValue("sjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("sjsuggest3", vRet[15]);
				} else {
					aipObj.SetValue("sjsuggest3", csshSjSuggest + vRet[15]);
					aipObj.SetValue("sjsuggest4", jzqfSjSuggest + vRet[15]);
					aipObj.SetValue("sjsuggest5", pshSjSuggest + vRet[15]);
					aipObj.SetValue("commenttext", csshSjSuggest + vRet[15]);
					if (document.getElementById("commenttext")) {
						document.getElementById("commenttext").value = csshSjSuggest
								+ vRet[15];
					}

				}
				aipObj.SetValue("shbtext30", formatDate(new Date()));
			}
		}
		// 减刑假释局长审批意见选择----------
		if (strName == 'sjsuggest4') {
			// 清空意见
			aipObj.SetValue("sjsuggest4", "");
			aipObj.SetValue("sjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("sjsuggest4", vRet[15]);
				} else {
					aipObj.SetValue("sjsuggest4", jzqfSjSuggest + vRet[15]);
					aipObj.SetValue("sjsuggest5", pshSjSuggest + vRet[15]);
					aipObj.SetValue("commenttext", jzqfSjSuggest + vRet[15]);

					if (document.getElementById("commenttext")) {
						document.getElementById("commenttext").value = jzqfSjSuggest
								+ vRet[15];
					}

				}
				aipObj.SetValue("shbtext36", formatDate(new Date()));
			}
		}
		// 减刑假释评审会审批意见选择----------
		if (strName == 'sjsuggest5') {
			// 清空意见
			aipObj.SetValue("sjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("sjsuggest5", vRet[15]);
				} else {
					aipObj.SetValue("sjsuggest5", pshSjSuggest + vRet[15]);
					aipObj.SetValue("commenttext", jzqfSjSuggest + vRet[15]);

					if (document.getElementById("commenttext")) {
						document.getElementById("commenttext").value = jzqfSjSuggest
								+ vRet[15];
					}
				}
				aipObj.SetValue("shbtext37", formatDate(new Date()));
			}
		}
	}
}

/*
 * 省局弹出框(针对广西的省局审批表的意见弹出框 单独处理)
 */
function jxjsProvinceDanChu4GX(aipObj, strName) {
	var csjbSjSuggest = '　　经处务会审查，建议提请';
	var csshSjSuggest = '　　经评审委员会会议评审，同意提请';
	var pshSjSuggest = '　　';
	var jzqfSjSuggest = '　　同意提请';

	var csjbSjSuggestAppend = '提交局减刑、假释评审委员会评审。';
	var csshSjSuggestAppend = '提交局长审定。';

	var sjsuggest2 = aipObj.GetValueEx("gxsjsuggest2", 2, "", 0, "");
	var sjsuggest3 = aipObj.GetValueEx("gxsjsuggest3", 2, "", 0, "");
	var sjsuggest4 = aipObj.GetValueEx("gxsjsuggest4", 2, "", 0, "");
	var sjsuggest5 = aipObj.GetValueEx("gxsjsuggest5", 2, "", 0, "");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, "");
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var jxjs_14 = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var jxjs_15 = aipObj.GetValueEx("isagree", 2, "", 0, "");
	var jxjs_16 = aipObj.GetValueEx("reason", 2, "", 0, "");
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("jxjs_14:" + jxjs_14);
	jxjsyj.push("jxjs_15:" + jxjs_15);
	jxjsyj.push("jxjs_16:" + jxjs_16);

	url = basePath + "/sjtanChuOpinionPage.action?jxjsyj="
			+ encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1", "");
		aipObj.SetValue("Page1.jxjs_2", "");
		aipObj.SetValue("Page1.jxjs_3", "");
		aipObj.SetValue("Page1.jxjs_4", "");
		aipObj.SetValue("Page1.jxjs_5", "");
		aipObj.SetValue("Page1.jxjs_6", "");
		aipObj.SetValue("Page1.jxjs_7", "");
		aipObj.SetValue("Page1.jxjs_8", "");
		aipObj.SetValue("Page1.jxjs_9", "");
		aipObj.SetValue("Page1.jxjs_10", "");
		aipObj.SetValue("Page1.jxjs_11", "");
		aipObj.SetValue("Page1.jxjs_12", "");
		aipObj.SetValue("Page1.jxjs_13", "");
		aipObj.SetValue("Page1.payeverymon", "");
		aipObj.SetValue("Page1.isagree", "");
		aipObj.SetValue("Page1.reason", "");
		// 返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1", vRet[0]);
		aipObj.SetValue("Page1.jxjs_2", vRet[1]);
		aipObj.SetValue("Page1.jxjs_3", vRet[2]);
		aipObj.SetValue("Page1.jxjs_4", vRet[3]);
		aipObj.SetValue("Page1.jxjs_5", vRet[4]);
		aipObj.SetValue("Page1.jxjs_6", vRet[5]);
		aipObj.SetValue("Page1.jxjs_7", vRet[6]);
		aipObj.SetValue("Page1.jxjs_8", vRet[7]);
		aipObj.SetValue("Page1.jxjs_9", vRet[8]);
		aipObj.SetValue("Page1.jxjs_10", vRet[9]);
		aipObj.SetValue("Page1.jxjs_11", vRet[10]);
		aipObj.SetValue("Page1.jxjs_12", vRet[11]);
		aipObj.SetValue("Page1.jxjs_13", vRet[12]);
		aipObj.SetValue("Page1.payeverymon", jxjs_14);
		aipObj.SetValue("Page1.isagree", vRet[13]);
		aipObj.SetValue("Page1.iscommitagree", vRet[13]);
		aipObj.SetValue("Page1.reason", vRet[14]);
		if (vRet[0] == '1') {
			aipObj.SetValue("Page1.jxorjs", "");
			aipObj.SetValue("Page1.jxorjs", "罪犯假释审批表");
		} else {
			aipObj.SetValue("Page1.jxorjs", "");
			aipObj.SetValue("Page1.jxorjs", "罪犯减刑审批表");
		}
		// 减刑假释处室经办意见选择----------
		if (strName == 'gxsjsuggest2') {
			// 清空意见
			aipObj.SetValue("gxsjsuggest2", "");
			aipObj.SetValue("gxsjsuggest3", "");
			aipObj.SetValue("gxsjsuggest4", "");
			aipObj.SetValue("gxsjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gxsjsuggest2", vRet[15]);
				} else {
					aipObj.SetValue("gxsjsuggest2", csjbSjSuggest + vRet[15]
							+ csjbSjSuggestAppend);
					aipObj.SetValue("gxsjsuggest3", csshSjSuggest + vRet[15]
							+ csshSjSuggestAppend);
					aipObj.SetValue("gxsjsuggest4", jzqfSjSuggest + vRet[15]);
					aipObj.SetValue("gxsjsuggest5", pshSjSuggest + vRet[15]);
					aipObj.SetValue("commenttext", csjbSjSuggest + vRet[15]);
					if (document.getElementById("commenttext")) {
						document.getElementById("commenttext").value = csjbSjSuggest
								+ vRet[15];
					}

				}
			}
		}
		// 减刑假释处室审核意见选择----------
		if (strName == 'gxsjsuggest3') {
			// 清空意见
			aipObj.SetValue("gxsjsuggest3", "");
			aipObj.SetValue("gxsjsuggest4", "");
			aipObj.SetValue("gxsjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gxsjsuggest3", vRet[15]);
				} else {
					aipObj.SetValue("gxsjsuggest3", csshSjSuggest + vRet[15]
							+ csshSjSuggestAppend);
					aipObj.SetValue("gxsjsuggest4", jzqfSjSuggest + vRet[15]);
					aipObj.SetValue("gxsjsuggest5", pshSjSuggest + vRet[15]);
					aipObj.SetValue("commenttext", csshSjSuggest + vRet[15]);
					if (document.getElementById("commenttext")) {
						document.getElementById("commenttext").value = csshSjSuggest
								+ vRet[15];
					}

				}
			}
		}
		// 减刑假释局长审批意见选择----------
		if (strName == 'gxsjsuggest4') {
			// 清空意见
			aipObj.SetValue("gxsjsuggest4", "");
			aipObj.SetValue("gxsjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gxsjsuggest4", vRet[15]);
				} else {
					aipObj.SetValue("gxsjsuggest4", jzqfSjSuggest + vRet[15]);
					aipObj.SetValue("gxsjsuggest5", pshSjSuggest + vRet[15]);
					aipObj.SetValue("commenttext", jzqfSjSuggest + vRet[15]);
					if (document.getElementById("commenttext")) {
						document.getElementById("commenttext").value = jzqfSjSuggest
								+ vRet[15];
					}
				}
			}
		}
		// 减刑假释评审会审批意见选择----------
		if (strName == 'gxsjsuggest5') {
			// 清空意见
			aipObj.SetValue("gxsjsuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("gxsjsuggest5", vRet[15]);
				} else {
					aipObj.SetValue("gxsjsuggest5", pshSjSuggest + vRet[15]);
					aipObj.SetValue("commenttext", jzqfSjSuggest + vRet[15]);
					if (document.getElementById("commenttext")) {
						document.getElementById("commenttext").value = jzqfSjSuggest
								+ vRet[15];
					}
				}
			}
		}
	}
}

/*
 * 法院弹出框
 */
function jxjsCourtDanChu(aipObj, strName) {
	var cbFySuggest = '';
	var hyFySuggest = '';
	var ldFySuggest = '';
	var ld2FySuggest = '';
	var fysuggest2 = aipObj.GetValueEx("fysuggest2", 2, "", 0, "");
	var fysuggest3 = aipObj.GetValueEx("fysuggest3", 2, "", 0, "");
	var fysuggest4 = aipObj.GetValueEx("fysuggest4", 2, "", 0, "");
	var fysuggest5 = aipObj.GetValueEx("fysuggest5", 2, "", 0, "");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, "");
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var jxjs_14 = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var jxjs_15 = aipObj.GetValueEx("isagree", 2, "", 0, "");
	var jxjs_16 = aipObj.GetValueEx("reason", 2, "", 0, "");
	var fdobj = document.getElementById("flowdraftid");
	var flowdraftid = '';
	if (fdobj) {
		flowdraftid = fdobj.value;
	}
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("jxjs_14:" + jxjs_14);
	jxjsyj.push("jxjs_15:" + jxjs_15);
	jxjsyj.push("jxjs_16:" + jxjs_16);
	jxjsyj.push("flowdraftid:" + flowdraftid);
	url = basePath + "/fytanChuOpinionPage.action?jxjsyj="
			+ encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1", "");
		aipObj.SetValue("Page1.jxjs_2", "");
		aipObj.SetValue("Page1.jxjs_3", "");
		aipObj.SetValue("Page1.jxjs_4", "");
		aipObj.SetValue("Page1.jxjs_5", "");
		aipObj.SetValue("Page1.jxjs_6", "");
		aipObj.SetValue("Page1.jxjs_7", "");
		aipObj.SetValue("Page1.jxjs_8", "");
		aipObj.SetValue("Page1.jxjs_9", "");
		aipObj.SetValue("Page1.jxjs_10", "");
		aipObj.SetValue("Page1.jxjs_11", "");
		aipObj.SetValue("Page1.jxjs_12", "");
		aipObj.SetValue("Page1.jxjs_13", "");
		aipObj.SetValue("Page1.payeverymon", "");
		aipObj.SetValue("Page1.isagree", "");
		aipObj.SetValue("Page1.reason", "");
		// 返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1", vRet[0]);
		aipObj.SetValue("Page1.jxjs_2", vRet[1]);
		aipObj.SetValue("Page1.jxjs_3", vRet[2]);
		aipObj.SetValue("Page1.jxjs_4", vRet[3]);
		aipObj.SetValue("Page1.jxjs_5", vRet[4]);
		aipObj.SetValue("Page1.jxjs_6", vRet[5]);
		aipObj.SetValue("Page1.jxjs_7", vRet[6]);
		aipObj.SetValue("Page1.jxjs_8", vRet[7]);
		aipObj.SetValue("Page1.jxjs_9", vRet[8]);
		aipObj.SetValue("Page1.jxjs_10", vRet[9]);
		aipObj.SetValue("Page1.jxjs_11", vRet[10]);
		aipObj.SetValue("Page1.jxjs_12", vRet[11]);
		aipObj.SetValue("Page1.jxjs_13", vRet[12]);
		aipObj.SetValue("Page1.payeverymon", jxjs_14);
		aipObj.SetValue("Page1.isagree", vRet[13]);
		aipObj.SetValue("Page1.iscommitagree", vRet[13]);
		aipObj.SetValue("Page1.reason", vRet[14]);
		if (vRet[0] == '1') {
			aipObj.SetValue("Page1.jxorjs", "");
			aipObj.SetValue("Page1.jxorjs", "罪犯假释审批表");
		} else {
			aipObj.SetValue("Page1.jxorjs", "");
			aipObj.SetValue("Page1.jxorjs", "罪犯减刑审批表");
		}
		// 减刑假释法院意见选择----------
		if (strName == 'fysuggest2') {
			// 清空意见
			aipObj.SetValue("fysuggest2", "");
			aipObj.SetValue("fysuggest3", "");
			aipObj.SetValue("fysuggest4", "");
			aipObj.SetValue("fysuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				var templet;
				if (vRet[8] != '') {
					templet = vRet[15];
					aipObj.SetValue("fysuggest2", templet);
				} else if (vRet[6]) {
					templet = vRet[6];
					if (templet.indexOf("[理由]") >= 0) {
						templet = templet.replace("[理由]", vRet[7]);
					}
					if (templet.indexOf("[建议减刑]") >= 0) {
						templet = templet.replace("[建议减刑]", vRet[18]);
					}
					if (templet.indexOf("[剥权]") >= 0) {
						templet = templet.replace("[剥权]", vRet[17]);
					}
					if (templet.indexOf("[罚金]") >= 0) {
						templet = templet.replace("[罚金]", vRet[5]);
					}
					aipObj.SetValue("fysuggest2", templet);
				} else {
					templet = vRet[15];
					aipObj.SetValue("fysuggest2", templet);
				}
				aipObj.SetValue("fysuggest3", hyFySuggest + vRet[11]);
				// aipObj.SetValue("fysuggest4",ldFySuggest + templet);
				// aipObj.SetValue("fysuggest5",ld2FySuggest + templet);
				aipObj.SetValue("commenttext", cbFySuggest + templet);
				document.getElementById("commenttext").value = cbFySuggest
						+ templet;
			}
		}
		// 减刑假释法院意见选择----------
		if (strName == 'fysuggest3') {
			// 清空意见
			aipObj.SetValue("fysuggest3", "");
			aipObj.SetValue("fysuggest4", "");
			aipObj.SetValue("fysuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				var templet;
				if (vRet[8] != '') {
					templet = vRet[15];
					aipObj.SetValue("fysuggest3", templet);
				} else if (vRet[6]) {
					templet = vRet[6];
					if (templet.indexOf("[理由]") >= 0) {
						templet = templet.replace("[理由]", vRet[7]);
					}
					if (templet.indexOf("[建议减刑]") >= 0) {
						templet = templet.replace("[建议减刑]", vRet[18]);
					}
					if (templet.indexOf("[剥权]") >= 0) {
						templet = templet.replace("[剥权]", vRet[17]);
					}
					if (templet.indexOf("[罚金]") >= 0) {
						templet = templet.replace("[罚金]", vRet[5]);
					}
					aipObj.SetValue("fysuggest3", templet);
				} else {
					templet = vRet[15];
					aipObj.SetValue("fysuggest3", templet);
				}
				// aipObj.SetValue("fysuggest4",ldFySuggest + templet);
				// aipObj.SetValue("fysuggest5",ld2FySuggest + templet);
				aipObj.SetValue("commenttext", hyFySuggest + templet);
				document.getElementById("commenttext").value = hyFySuggest
						+ templet;
			}
		}
		// 减刑假释法院意见选择----------
		if (strName == 'fysuggest4') {
			// 清空意见
			aipObj.SetValue("fysuggest4", "");
			aipObj.SetValue("fysuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				var templet;
				if (vRet[8] != '') {
					templet = vRet[15];
					aipObj.SetValue("fysuggest4", templet);
				} else if (vRet[6]) {
					templet = vRet[6];
					if (templet.indexOf("[理由]") >= 0) {
						templet = templet.replace("[理由]", vRet[7]);
					}
					if (templet.indexOf("[建议减刑]") >= 0) {
						templet = templet.replace("[建议减刑]", vRet[18]);
					}
					if (templet.indexOf("[剥权]") >= 0) {
						templet = templet.replace("[剥权]", vRet[17]);
					}
					if (templet.indexOf("[罚金]") >= 0) {
						templet = templet.replace("[罚金]", vRet[5]);
					}
					aipObj.SetValue("fysuggest4", templet);
				} else {
					templet = vRet[15];
					aipObj.SetValue("fysuggest4", templet);
				}
				// aipObj.SetValue("fysuggest5",ld2FySuggest + templet);
				aipObj.SetValue("commenttext", ldFySuggest + templet);
				document.getElementById("commenttext").value = ldFySuggest
						+ templet;
			}
		}
		// 减刑假释法院意见2选择----------
		if (strName == 'fysuggest5') {
			// 清空意见
			aipObj.SetValue("fysuggest5", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				var templet;
				if (vRet[8] != '') {
					templet = vRet[15];
					aipObj.SetValue("fysuggest5", templet);
				} else if (vRet[6]) {
					templet = vRet[6];
					if (templet.indexOf("[理由]") >= 0) {
						templet = templet.replace("[理由]", vRet[7]);
					}
					if (templet.indexOf("[建议减刑]") >= 0) {
						templet = templet.replace("[建议减刑]", vRet[15]);
					}
					aipObj.SetValue("fysuggest5", templet);
				} else {
					templet = vRet[15];
					aipObj.SetValue("fysuggest5", templet);
				}
				aipObj.SetValue("commenttext", ld2FySuggest + templet);
				document.getElementById("commenttext").value = ld2FySuggest
						+ templet;
			}
		}
	}
}
// 检查机关出庭意见弹出窗
function jxjsJianChaJiGuanDanChu(aipObj, strName) {
	var cbFySuggest = '';
	var hyFySuggest = '';
	var ldFySuggest = '';
	var ld2FySuggest = '';
	var fysuggest2 = aipObj.GetValueEx("fysuggest2", 2, "", 0, "");
	var fysuggest3 = aipObj.GetValueEx("fysuggest3", 2, "", 0, "");
	var fysuggest4 = aipObj.GetValueEx("fysuggest4", 2, "", 0, "");
	var fysuggest5 = aipObj.GetValueEx("fysuggest5", 2, "", 0, "");
	var jxjs_0 = aipObj.GetValueEx("jxjs_0", 2, "", 0, ""); // 刑期类型
	var jxjs_1 = aipObj.GetValueEx("jxjs_1", 2, "", 0, "");
	var jxjs_2 = aipObj.GetValueEx("jxjs_2", 2, "", 0, "");
	var jxjs_3 = aipObj.GetValueEx("jxjs_3", 2, "", 0, "");
	var jxjs_4 = aipObj.GetValueEx("jxjs_4", 2, "", 0, "");
	var jxjs_5 = aipObj.GetValueEx("jxjs_5", 2, "", 0, "");
	var jxjs_6 = aipObj.GetValueEx("jxjs_6", 2, "", 0, "");
	var jxjs_7 = aipObj.GetValueEx("jxjs_7", 2, "", 0, "");
	var jxjs_8 = aipObj.GetValueEx("jxjs_8", 2, "", 0, "");
	var jxjs_9 = aipObj.GetValueEx("jxjs_9", 2, "", 0, "");
	var jxjs_10 = aipObj.GetValueEx("jxjs_10", 2, "", 0, "");
	var jxjs_11 = aipObj.GetValueEx("jxjs_11", 2, "", 0, "");
	var jxjs_12 = aipObj.GetValueEx("jxjs_12", 2, "", 0, "");
	var jxjs_13 = aipObj.GetValueEx("jxjs_13", 2, "", 0, "");
	var jxjs_14 = aipObj.GetValueEx("payeverymon", 2, "", 0, "");
	var jxjs_15 = aipObj.GetValueEx("isagree", 2, "", 0, "");
	var jxjs_16 = aipObj.GetValueEx("reason", 2, "", 0, "");
	var fdobj = document.getElementById("flowdraftid");
	var flowdraftid = '';
	if (fdobj) {
		flowdraftid = fdobj.value;
	}
	var jxjsyj = [];
	jxjsyj.push("jxjs_0:" + jxjs_0);
	jxjsyj.push("jxjs_1:" + jxjs_1);
	jxjsyj.push("jxjs_2:" + jxjs_2);
	jxjsyj.push("jxjs_3:" + jxjs_3);
	jxjsyj.push("jxjs_4:" + jxjs_4);
	jxjsyj.push("jxjs_5:" + jxjs_5);
	jxjsyj.push("jxjs_6:" + jxjs_6);
	jxjsyj.push("jxjs_7:" + jxjs_7);
	jxjsyj.push("jxjs_8:" + jxjs_8);
	jxjsyj.push("jxjs_9:" + jxjs_9);
	jxjsyj.push("jxjs_10:" + jxjs_10);
	jxjsyj.push("jxjs_11:" + jxjs_11);
	jxjsyj.push("jxjs_12:" + jxjs_12);
	jxjsyj.push("jxjs_13:" + jxjs_13);
	jxjsyj.push("jxjs_14:" + jxjs_14);
	jxjsyj.push("jxjs_15:" + jxjs_15);
	jxjsyj.push("jxjs_16:" + jxjs_16);
	jxjsyj.push("flowdraftid:" + flowdraftid);
	url = basePath + "/fytanChuOpinionPage.action?jxjsyj="
			+ encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.jxjs_1", "");
		aipObj.SetValue("Page1.jxjs_2", "");
		aipObj.SetValue("Page1.jxjs_3", "");
		aipObj.SetValue("Page1.jxjs_4", "");
		aipObj.SetValue("Page1.jxjs_5", "");
		aipObj.SetValue("Page1.jxjs_6", "");
		aipObj.SetValue("Page1.jxjs_7", "");
		aipObj.SetValue("Page1.jxjs_8", "");
		aipObj.SetValue("Page1.jxjs_9", "");
		aipObj.SetValue("Page1.jxjs_10", "");
		aipObj.SetValue("Page1.jxjs_11", "");
		aipObj.SetValue("Page1.jxjs_12", "");
		aipObj.SetValue("Page1.jxjs_13", "");
		aipObj.SetValue("Page1.payeverymon", "");
		aipObj.SetValue("Page1.isagree", "");
		aipObj.SetValue("Page1.reason", "");
		// 返回值传到隐藏域
		aipObj.SetValue("Page1.jxjs_1", vRet[0]);
		aipObj.SetValue("Page1.jxjs_2", vRet[1]);
		aipObj.SetValue("Page1.jxjs_3", vRet[2]);
		aipObj.SetValue("Page1.jxjs_4", vRet[3]);
		aipObj.SetValue("Page1.jxjs_5", vRet[4]);
		aipObj.SetValue("Page1.jxjs_6", vRet[5]);
		aipObj.SetValue("Page1.jxjs_7", vRet[6]);
		aipObj.SetValue("Page1.jxjs_8", vRet[7]);
		aipObj.SetValue("Page1.jxjs_9", vRet[8]);
		aipObj.SetValue("Page1.jxjs_10", vRet[9]);
		aipObj.SetValue("Page1.jxjs_11", vRet[10]);
		aipObj.SetValue("Page1.jxjs_12", vRet[11]);
		aipObj.SetValue("Page1.jxjs_13", vRet[12]);
		aipObj.SetValue("Page1.payeverymon", jxjs_14);
		aipObj.SetValue("Page1.isagree", vRet[13]);
		aipObj.SetValue("Page1.iscommitagree", vRet[13]);
		aipObj.SetValue("Page1.reason", vRet[14]);
		if (strName == 'prosecutor') {
			// 清空意见
			aipObj.SetValue("prosecutor", "");
			aipObj.SetValue("commenttext", "");
			// 填充意见信息
			if (vRet[15]) {
				var templet;
				if (vRet[8] != '') {
					templet = vRet[15];
					aipObj.SetValue("prosecutor", templet);
				} else if (vRet[6]) {
					templet = vRet[6];
					if (templet.indexOf("[理由]") >= 0) {
						templet = templet.replace("[理由]", vRet[7]);
					}
					if (templet.indexOf("[建议减刑]") >= 0) {
						templet = templet.replace("[建议减刑]", vRet[18]);
					}
					if (templet.indexOf("[剥权]") >= 0) {
						templet = templet.replace("[剥权]", vRet[17]);
					}
					if (templet.indexOf("[罚金]") >= 0) {
						templet = templet.replace("[罚金]", vRet[5]);
					}
					aipObj.SetValue("prosecutor", templet);
				} else {
					templet = vRet[15];
					aipObj.SetValue("prosecutor", templet);
				}
				aipObj.SetValue("commenttext", hyFySuggest + templet);
				document.getElementById("commenttext").value = hyFySuggest
						+ templet;
			}
		}
	}
}

function publicPopUpWindow(aipObj, tempid, nodeid) {
	var type = 1;// 0：系统模板，1：弹出框
	var url = path + '/ajaxGetFormDetails.json';
	$.ajax({
		type : "POST",
		url : url,
		data : {
			tempid : tempid,
			nodeid : nodeid,
			type : type
		},
		cache : false,
		async : false,
		success : function(text) {
			if (text) {
				var result = mini.decode(text);
				var tojsp = result['tojsp'];// 指向跳转的JSP
				var width = result['int1'];// 弹出框宽度
				var height = result['int2'];// 弹出框高度
				var inputparam = result['inputparam'];// 输入参数
				var outputparam = result['outputparam'];// 输出参数
				toPopUpWindow(aipObj, tojsp, width, height, inputparam,
						outputparam);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("操作失败!");
		}
	});

}

function toPopUpWindow(aipObj, tojsp, width, height, inputparam, outputparam) {
	if (true) {
		var inputparamArr = inputparam.split(',');
		inputparamData = {};
		if (inputparamArr) {
			for (var i = 0, l = inputparamArr.length; i < l; i++) {
				var key = inputparamArr[i];
				var value = aipObj.GetValueEx(key, 2, "", 0, "");
				inputparamData[key] = value;
			}
			inputparamData = mini.encode(inputparamData);
		}
		var url = basePath + '/toFormPopUpBox.action?1=1&tojsp=' + tojsp;
		mini.open({
			url : url,
			showMaxButton : true,
			allowResize : false,
			title : "表单弹出框",
			width : width,
			height : height,
			onload : function() {
				var iframe = this.getIFrameEl();
				var data = {
					action : "edit",
					inputparamData : inputparamData,
					inputparam : inputparam,
					outputparam : outputparam
				};
				iframe.contentWindow.SetData(data);
			},
			ondestroy : function(action) {
				if (action == "ok") { // 如果点击“确定”
					var iframe = this.getIFrameEl();
					var data = iframe.contentWindow.GetData();
					data = mini.clone(data); // 必须。克隆数据。
					// 返回值处理
					var outputparamArr = outputparam.split(',');
					for (var i = 0, l = outputparamArr.length; i < l; i++) {
						var key = outputparamArr[i];
						aipObj.SetValue(key, "");
						aipObj.SetValue(key, data[key]);
					}
				}
			}
		});

	}
}

function jxjsJailDanChu_SD(aipObj, strName) {//
	var fjqSuggest = '　　根据有关法律法规和该犯的改造表现，经分监区集体讨论，';
	var jqSuggest = '　　经监区长办公会审查，情况属实，';
	var ksSuggest = '　　经审核，';
	var jySuggest = '　　';
	var sgSuggest = '　　';
	url = basePath + "/tanChuOpinionPageForSD.action?crimid=" + crimid
			+ "&jxjsyj=" + encodeURI(encodeURI(jxjsyj));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:350px;dialogWidth:550px");
	if (vRet) {
		var opiniontype = vRet[0];
		var opinionyear = vRet[1];
		var opinionmonth = vRet[2];
		var opinionday = vRet[3];
		var bozhengyear = vRet[4];
		var opinion = vRet[5];

		// 根据选择减刑或者假释自动补充表头
		if (vRet[0] == '1') {
			aipObj.SetValue("jxojs", "");
			aipObj.SetValue("jxojs", "罪犯假释审核表");
		} else {
			aipObj.SetValue("jxojs", "");
			aipObj.SetValue("jxojs", "罪犯减刑审核表");
		}
		// 减刑假释监狱分监区意见选择----------
		if (strName == 'sdjxjssuggest1') {
			// 清空意见
			aipObj.SetValue("Page2.sdjxjssuggest1", "");
			aipObj.SetValue("Page2.sdjxjssuggest2", "");
			aipObj.SetValue("Page2.sdjxjssuggest3", "");
			aipObj.SetValue("Page2.sdjxjssuggest4", "");
			aipObj.SetValue("Page2.sdjxjssuggest5", "");
			// 填充意见信息
			if (opinion) {
				// 填充意见框
				aipObj.SetValue("Page2.sdjxjssuggest1", fjqSuggest + vRet[15]);
				aipObj.SetValue("Page2.sdjxjssuggest2", jqSuggest + vRet[15]);
				aipObj.SetValue("Page2.sdjxjssuggest3", ksSuggest + vRet[15]);
				aipObj.SetValue("Page2.sdjxjssuggest4", jySuggest
						+ vRet[15].replace('建议', '同意提请'));
				aipObj.SetValue("Page2.sdjxjssuggest5", sgSuggest
						+ vRet[15].replace('建议', '同意提请'));
				// 存放在隐藏域中保存数据用
				aipObj.SetValue("opiniontype", opiniontype);// 减刑类型（减刑0/假释1/减去余刑2）
				aipObj.SetValue("opinionyear", opinionyear);// 意见年
				aipObj.SetValue("opinionmonth", opinionmonth);// 意见月
				aipObj.SetValue("opinionday", opinionday);// 意见天
				aipObj.SetValue("bozhengyear", bozhengyear);// 剥夺政治权利年
				aipObj.SetValue("fenjianquopinion", opinion);// 提请意见（分监区）
				// 填充审批时间
				aipObj.SetValue("fenjianqudate", formatDate(new Date()));
			}
		}
		// 减刑假释监狱监区意见选择----------
		if (strName == 'sdjxjssuggest2') {
			// 清空意见
			aipObj.SetValue("Page2.sdjxjssuggest2", "");
			aipObj.SetValue("Page2.sdjxjssuggest3", "");
			aipObj.SetValue("Page2.sdjxjssuggest4", "");
			aipObj.SetValue("Page2.sdjxjssuggest5", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.sdjxjssuggest2", vRet[15]);
				} else {
					aipObj.SetValue("Page2.sdjxjssuggest2", jqSuggest
							+ vRet[15]);
					aipObj.SetValue("Page2.sdjxjssuggest3", ksSuggest
							+ vRet[15]);
					aipObj.SetValue("Page2.sdjxjssuggest4", jySuggest
							+ vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("Page2.sdjxjssuggest5", sgSuggest
							+ vRet[15].replace('建议', '同意提请'));
				}
				// 存放在隐藏域中保存数据用
				aipObj.SetValue("opiniontype", "");// 减刑类型（减刑0/假释1/减去余刑2）
				aipObj.SetValue("opinionyear", "");// 意见年
				aipObj.SetValue("opinionmonth", "");// 意见月
				aipObj.SetValue("opinionday", "");// 意见天
				aipObj.SetValue("bozhengyear", "");// 剥夺政治权利年
				aipObj.SetValue("jianquopinion", "");// 提请意见（监区）

				aipObj.SetValue("jianqudate", formatDate(new Date()));
			}
		}
		// 减刑假释监狱科室意见选择----------
		if (strName == 'sdjxjssuggest3') {
			// 清空意见
			aipObj.SetValue("Page2.sdjxjssuggest3", "");
			aipObj.SetValue("Page2.sdjxjssuggest4", "");
			aipObj.SetValue("Page2.sdjxjssuggest5", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.sdjxjssuggest3", vRet[15]);
				} else {
					aipObj.SetValue("Page2.sdjxjssuggest3", ksSuggest
							+ vRet[15]);
					aipObj.SetValue("Page2.sdjxjssuggest4", jySuggest
							+ vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("Page2.sdjxjssuggest5", sgSuggest
							+ vRet[15].replace('建议', '同意提请'));
				}
				// 存放在隐藏域中保存数据用
				aipObj.SetValue("opiniontype", "");// 减刑类型（减刑0/假释1/减去余刑2）
				aipObj.SetValue("opinionyear", "");// 意见年
				aipObj.SetValue("opinionmonth", "");// 意见月
				aipObj.SetValue("opinionday", "");// 意见天
				aipObj.SetValue("bozhengyear", "");// 剥夺政治权利年
				aipObj.SetValue("keshiopinion", "");// 提请意见（科室）

				aipObj.SetValue("xingfakedate", formatDate(new Date()));
			}
		}

		// 减刑假释监狱意见选择----------
		if (strName == 'sdjxjssuggest4') {
			// 清空意见
			aipObj.SetValue("Page2.sdjxjssuggest4", "");
			aipObj.SetValue("Page2.sdjxjssuggest5", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.sdjxjssuggest4", vRet[15]);
				} else {
					aipObj.SetValue("Page2.sdjxjssuggest4", jySuggest
							+ vRet[15].replace('建议', '同意提请'));
					aipObj.SetValue("Page2.sdjxjssuggest5", sgSuggest
							+ vRet[15].replace('建议', '同意提请'));
				}
				// 存放在隐藏域中保存数据用
				aipObj.SetValue("opiniontype", "");// 减刑类型（减刑0/假释1/减去余刑2）
				aipObj.SetValue("opinionyear", "");// 意见年
				aipObj.SetValue("opinionmonth", "");// 意见月
				aipObj.SetValue("opinionday", "");// 意见天
				aipObj.SetValue("bozhengyear", "");// 剥夺政治权利年
				aipObj.SetValue("jianyuopinion", "");// 提请意见（监狱）

				aipObj.SetValue("jianyudate", formatDate(new Date()));
			}
		}

		// 减刑假释监狱省局意见选择----------
		if (strName == 'sdjxjssuggest5') {
			// 清空意见
			aipObj.SetValue("Page2.sdjxjssuggest5", "");
			// 填充意见信息
			if (vRet[15]) {
				if (vRet[13] == 0) {
					aipObj.SetValue("Page2.sdjxjssuggest5", vRet[15]);
				} else {
					aipObj.SetValue("Page2.sdjxjssuggest5", sgSuggest
							+ vRet[15].replace('建议', '同意提请'));
				}
				// 存放在隐藏域中保存数据用
				aipObj.SetValue("opiniontype", "");// 减刑类型（减刑0/假释1/减去余刑2）
				aipObj.SetValue("opinionyear", "");// 意见年
				aipObj.SetValue("opinionmonth", "");// 意见月
				aipObj.SetValue("opinionday", "");// 意见天
				aipObj.SetValue("bozhengyear", "");// 剥夺政治权利年
				aipObj.SetValue("shengjuopinion", "");// 提请意见（省局）

				aipObj.SetValue("shengjudate", formatDate(new Date()));
			}
		}
	}
}

// 首先获得刑罚种类,如果为空则提示.如果不是有期徒刑则显示相应的值且刑期不可编辑
function zhuxing(strName) {
	var aipObj = getmyvalue("HWPostil1");
	var type = aipObj.GetValueEx("cjiimprisontype", 2, "", 0, "");// 刑罚种类
	if (type) {
		if (type == "有期徒刑") {
			var zhuxing0 = aipObj.GetValueEx("zhuxing0", 2, "", 0, "");
			var zhuxing1 = aipObj.GetValueEx("zhuxing1", 2, "", 0, "");
			var zhuxing2 = aipObj.GetValueEx("zhuxing2", 2, "", 0, "");
			if(zhuxing0==null || zhuxing0==''){
				zhuxing0 = "0";
			}
			if(zhuxing1==null || zhuxing1==''){
				zhuxing1 = "0";
			}
			if(zhuxing2==null || zhuxing2==''){
				zhuxing2 = "0";
			}
			var zhuxingnum = aipObj.GetValueEx("zhuxingnum", 2, "", 0, "");
			if (!zhuxingnum){
				zhuxingnum = zhuxing0 + "," + zhuxing1 + "," + zhuxing2;
			}
			selectXingQi(aipObj, zhuxingnum);
		} else if (type == "无期徒刑") {
			aipObj.SetValue(strName, "");
			aipObj.SetValue(strName, "无期");
			aipObj.SetValue("zhuxing0", "");
			aipObj.SetValue("zhuxing0", "9995");
		} else if (type == "死刑，缓期二年执行") {
			aipObj.SetValue(strName, "");
			aipObj.SetValue(strName, "死缓");
			aipObj.SetValue("zhuxing0", "");
			aipObj.SetValue("zhuxing0", "9996");
		} else {
			aipObj.SetValue(strName, "");
			aipObj.SetValue(strName, "死刑");
			aipObj.SetValue("zhuxing0", "");
			aipObj.SetValue("zhuxing0", "9997");
		}
	} else {
		alert("请先选择刑罚种类！");
		aipObj.SetValue(strName, ":PROP:READONLY:0");
	}
}
function selectXingQi(aipObj, zhuxingnum) {
	var url = basePath + "/toXingQiSelectPage.action?1=1&zhuxingnum="
			+ zhuxingnum;
	var vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:270px;dialogWidth:480px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("zhuxing", "");
		aipObj.SetValue("zhuxing0", "");
		aipObj.SetValue("zhuxing1", "");
		aipObj.SetValue("zhuxing2", "");
		// 返回值传到隐藏域
		aipObj.SetValue("zhuxing", vRet[0]);
		aipObj.SetValue("zhuxing0", vRet[1]);
		aipObj.SetValue("zhuxing1", vRet[2]);
		aipObj.SetValue("zhuxing2", vRet[3]);
	}
}
// 判决书号
function getcjicourtnumber(strName) {
	var aipObj = getmyvalue("HWPostil1");
	var caseno1 = aipObj.GetValueEx("caseno1", 2, "", 0, "");
	var caseno2 = aipObj.GetValueEx("caseno2", 2, "", 0, "");
	var caseno3 = aipObj.GetValueEx("caseno3", 2, "", 0, "");
	var arry = [];
	arry.push("caseno1:" + caseno1);
	arry.push("caseno2:" + caseno2);
	arry.push("caseno3:" + caseno3);
	var url = basePath + "/getCjicourtNumber.json?cjicourtzh="
			+ encodeURI(encodeURI(arry));
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:150px;dialogWidth:430px");
	if (vRet) {
		// 清空隐藏域原来的值
		aipObj.SetValue("Page1.caseno1", "");
		aipObj.SetValue("Page1.caseno2", "");
		aipObj.SetValue("Page1.caseno3", "");
		aipObj.SetValue("Page1.cjicourtnumber", "");

		aipObj.SetValue("Page1.caseno1", vRet[0]);
		aipObj.SetValue("Page1.caseno2", vRet[1]);
		aipObj.SetValue("Page1.caseno3", vRet[2]);
		aipObj.SetValue("Page1.cjicourtnumber", vRet[3]);
	}
}

function getwanf(strName) {
	var provincecode = mini.get("provincecode").getValue();
	if (provincecode == '6500') {// 新疆
		var aipObj = getmyvalue("HWPostil1");
		var cjicriminalsort = aipObj
				.GetValueEx("cjicriminalsort", 2, "", 0, "");
		var url = basePath + "/towanfPage.page?cjicriminalsort="
				+ encodeURI(cjicriminalsort);
		vRet = window
				.showModalDialog(
						url,
						"",
						"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:150px;dialogWidth:250px");
		if (vRet) {
			aipObj.SetValue("Page1.cjicriminalsort", "");
			aipObj.SetValue("Page1.cjicriminalsort", vRet);
		}
	}
}

//三类犯选择框
function getSanLei(strName) {
	var aipObj = getmyvalue("HWPostil1");
	var code = aipObj.GetValueEx("sanleicode", 2, "", 0, "");
	var url = basePath + "/toSlfopinionSelect.page?code="+code;
	vRet = window.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:400px;dialogWidth:500px");
	if (vRet) {
		
		aipObj.SetValue("sanleicode","");
		aipObj.SetValue("sanleifanleixing","");
		
		aipObj.SetValue("sanleicode",vRet[0]);
		aipObj.SetValue("sanleifanleixing",vRet[1]);
	}
}

function getZhongYao(strName) {
	var aipObj = getmyvalue("HWPostil1");
	var url = basePath + "/toZYZFopinionSelect.page";
	vRet = window.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:400px;dialogWidth:500px");
	if (vRet) {
		
		aipObj.SetValue("zhongyaocode","");
		aipObj.SetValue("zhongyaozuifanleixing","");
		
		aipObj.SetValue("zhongyaocode",vRet[0]);
		aipObj.SetValue("zhongyaozuifanleixing",vRet[1]);
	}
}

/**湖南基本信息表获取并罚情况*/
function getBingfan(strName) {
	var aipObj = getmyvalue("HWPostil1");
	var url = basePath + "/toBFQKopinionSelect.page";
	vRet = window.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:400px;dialogWidth:500px");
	if (vRet) {
		
		//aipObj.SetValue("zhongyaocode","");
		aipObj.SetValue("xingba","");
		
		//aipObj.SetValue("zhongyaocode",vRet[0]);
		aipObj.SetValue("xingba",vRet[1]);
	}
}

function getQiTa(strName) {
	var aipObj = getmyvalue("HWPostil1");
	var url = basePath + "/toQTLXopinionSelect.page";
	vRet = window.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:400px;dialogWidth:500px");
	if (vRet) {
		
		aipObj.SetValue("qitacode","");
		aipObj.SetValue("othertype","");
		
		aipObj.SetValue("qitacode",vRet[0]);
		aipObj.SetValue("othertype",vRet[1]);
	}
}

/**
 * 方法描述：省+市+县、区 不带详细地址
 * 
 * @author Administrator
 */
function getGJNXxdz(strName) {
	var provincecode = mini.get("provincecode").getValue();
	var aipObj = getmyvalue("HWPostil1");

	var url = basePath + "/toGuoJiPage.page?fazhengjiguan="
			+ encodeURI(fazhengjiguan) + "&sfaddress=" + par1 + "&sqaddress="
			+ par2 + "&dqxjaddress=" + par3 + "&xxdzaddress=" + encodeURI(par4);
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:250px;dialogWidth:450px");

}

/**
 * 方法描述：省+市+县、区+详细地址
 * 
 * @param strName
 */
function getGuoJi(strName) {
	var provincecode = mini.get("provincecode").getValue();
	var aipObj = getmyvalue("HWPostil1");
	// var fazhengjiguan = aipObj.GetValueEx("fazhengjiguan",2,"",0,"");
	// 家庭住址
	var sfaddress = aipObj.GetValueEx("sfaddress", 2, "", 0, "");// 获取省份codeid
	var sqaddress = aipObj.GetValueEx("sqaddress", 2, "", 0, "");// 获取市区codeid
	var dqxjaddress = aipObj.GetValueEx("dqxjaddress", 2, "", 0, "");// 获取县或地区codeid
	var xxdzaddress = aipObj.GetValueEx("xxdzaddress", 2, "", 0, "");// 获取详细地址

	// 户籍地
	var hjdzaddress = aipObj.GetValueEx("hjdzaddress", 2, "", 0, "");// 获取省份codeid
	var hjdzaddress1 = aipObj.GetValueEx("hjdzaddress1", 2, "", 0, "");// 获取市区codeid
	var hjdzaddress2 = aipObj.GetValueEx("hjdzaddress2", 2, "", 0, "");// 获取县或地区codeid
	var hjdzaddress3 = aipObj.GetValueEx("hjdzaddress3", 2, "", 0, "");// 获取详细地址

	// 籍贯
	var jgdzaddress = aipObj.GetValueEx("jgdzaddress", 2, "", 0, "");// 获取省份codeid
	var jgdzaddress1 = aipObj.GetValueEx("jgdzaddress1", 2, "", 0, "");// 获取市区codeid
	var jgdzaddress2 = aipObj.GetValueEx("jgdzaddress2", 2, "", 0, "");// 获取县或地区codeid

	// 发证机关
	var fzaddress = aipObj.GetValueEx("fzaddress", 2, "", 0, "");// 发证机关省codeid
	var fzaddress1 = aipObj.GetValueEx("fzaddress1", 2, "", 0, "");// 发证机关市区codeid
	var fzaddress2 = aipObj.GetValueEx("fzaddress2", 2, "", 0, "");// 发证机关县或地区codeid
	var fzaddress3 = aipObj.GetValueEx("fzaddress3", 2, "", 0, "");// 发证机关详细地址

	// 逮捕机关
	var jfaddress = aipObj.GetValueEx("jfaddress", 2, "", 0, "");// 逮捕机关省codeid
	var jfaddress1 = aipObj.GetValueEx("jfaddress1", 2, "", 0, "");// 逮捕机关市区codeid
	var jfaddress2 = aipObj.GetValueEx("jfaddress2", 2, "", 0, "");// 逮捕机关县或地区codeid
	var jfaddress3 = aipObj.GetValueEx("jfaddress3", 2, "", 0, "");// 逮捕机关详细地址

	// 原判法院
	var court_name = aipObj.GetValueEx("court_name", 2, "", 0, "");// 判决法院的名称

	var par1 = "";// 变量1 省份codeid
	var par2 = "";// 变量2 市codeid
	var par3 = "";// 变量3 县或区codeid
	var par4 = "";// 变量4 详细地址
	var par5 = "";// 变量5 用于控制目标弹出页面的一些标签或者是其他内容的显示或者控制
	var par6 = "";// 判断跳转不通的jsp页面
	if (strName == "cbihomeaddress") {// 家庭住址
		par1 = sfaddress;
		par2 = sqaddress;
		par3 = dqxjaddress;
		par4 = xxdzaddress;
		par5 = 0;
	} else if (strName == "cbiresidenceaddress") {// 户籍所在地
		par1 = hjdzaddress;
		par2 = hjdzaddress1;
		par3 = hjdzaddress2;
		par4 = hjdzaddress3;
		par5 = 0;
	} else if (strName == 'cbinativeplaceaddress') {// 籍贯
		par1 = jgdzaddress;
		par2 = jgdzaddress1;
		par3 = jgdzaddress2;
		par5 = 1;
	} else if (strName == 'fazhengjiguan') {// 发证机关
		par1 = fzaddress;
		par2 = fzaddress1;
		par3 = fzaddress2;
		par4 = fzaddress3;
		par5 = 0;
	} else if (strName == 'criofficiallydepartment') {// 交付机关
		par1 = "";
		par2 = "";
		par3 = "";
		par4 = "";
		par5 = 0;
	} else if (strName == 'caiarrestoffice') {// 逮捕籍贯
		par1 = jfaddress;
		par2 = jfaddress1;
		par3 = jfaddress2;
		par4 = jfaddress3;
		par5 = 0;
	} else if (strName == 'courtnamecode' || strName == 'cjicourtname') {// 判决法院
		par1 = "";
		par2 = "";
		par3 = "";
		par4 = court_name;
		par5 = 0;
		par6 = "yes";
	}

	var url = basePath + "/toGuoJiPage.page?1=1&sfaddress=" + par1
			+ "&sqaddress=" + par2 + "&dqxjaddress=" + par3 + "&xxdzaddress="
			+ encodeURI(par4) + "&par5=" + par5 + "&par6=" + par6;
	vRet = window
			.showModalDialog(
					url,
					"",
					"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:250px;dialogWidth:450px");
	if (vRet) {
		if (strName == 'fazhengjiguan') {// 发证机关
			aipObj.SetValue("Page1.fzaddress", "");
			aipObj.SetValue("Page1.fzaddress", vRet[0]);

			aipObj.SetValue("Page1.fzaddress1", "");
			aipObj.SetValue("Page1.fzaddress1", vRet[1]);

			aipObj.SetValue("Page1.fzaddress2", "");
			aipObj.SetValue("Page1.fzaddress2", vRet[2]);

			aipObj.SetValue("Page1.fzaddress3", "");
			aipObj.SetValue("Page1.fzaddress3", vRet[4]);

			aipObj.SetValue("Page1.fazhengjiguan", "");
			aipObj.SetValue("Page1.fazhengjiguan", vRet[3] + vRet[4]);
		} else if (strName == 'cbiresidenceaddress') {// 户籍所在地
			aipObj.SetValue("Page1.hjdzaddress", "");
			aipObj.SetValue("Page1.hjdzaddress", vRet[0]);

			aipObj.SetValue("Page1.hjdzaddress1", "");
			aipObj.SetValue("Page1.hjdzaddress1", vRet[1]);

			aipObj.SetValue("Page1.hjdzaddress2", "");
			aipObj.SetValue("Page1.hjdzaddress2", vRet[2]);

			aipObj.SetValue("Page1.hjdzaddress3", "");
			aipObj.SetValue("Page1.hjdzaddress3", vRet[4]);

			aipObj.SetValue("Page1.cbiresidenceaddress", "");
			aipObj.SetValue("Page1.cbiresidenceaddress", vRet[3] + vRet[4]);
		} else if (strName == 'caiarrestoffice') {// 逮捕机关

			aipObj.SetValue("Page1.jfaddress", "");
			aipObj.SetValue("Page1.jfaddress", vRet[0]);

			aipObj.SetValue("Page1.jfaddress1", "");
			aipObj.SetValue("Page1.jfaddress1", vRet[1]);

			aipObj.SetValue("Page1.jfaddress2", "");
			aipObj.SetValue("Page1.jfaddress2", vRet[2]);

			aipObj.SetValue("Page1.jfaddress3", "");
			aipObj.SetValue("Page1.jfaddress3", vRet[4]);

			aipObj.SetValue("Page1.caiarrestoffice", "");
			aipObj.SetValue("Page1.caiarrestoffice", vRet[3] + vRet[4]);
		} else if (strName == 'criofficiallydepartment') {
			aipObj.SetValue("Page1.criofficiallydepartment", "");
			aipObj.SetValue("Page1.criofficiallydepartment", vRet);
		} else if (strName == 'cbihomeaddress') {// 家庭住址
			aipObj.SetValue("Page1.sfaddress", "");
			aipObj.SetValue("Page1.sfaddress", vRet[0]);

			aipObj.SetValue("Page1.sqaddress", "");
			aipObj.SetValue("Page1.sqaddress", vRet[1]);

			aipObj.SetValue("Page1.dqxjaddress", "");
			aipObj.SetValue("Page1.dqxjaddress", vRet[2]);

			aipObj.SetValue("Page1.xxdzaddress", "");
			aipObj.SetValue("Page1.xxdzaddress", vRet[4]);

			aipObj.SetValue("Page1.cbihomeaddress", "");
			aipObj.SetValue("Page1.cbihomeaddress", vRet[3] + vRet[4]);
		} else if (strName == "cbinativeplaceaddress") {

			aipObj.SetValue("Page1.jgdzaddress", "");
			aipObj.SetValue("Page1.jgdzaddress", vRet[0]);

			aipObj.SetValue("Page1.jgdzaddress1", "");
			aipObj.SetValue("Page1.jgdzaddress1", vRet[1]);

			aipObj.SetValue("Page1.jgdzaddress2", "");
			aipObj.SetValue("Page1.jgdzaddress2", vRet[2]);

			aipObj.SetValue("Page1.cbinativeplaceaddress", "");
			aipObj.SetValue("Page1.cbinativeplaceaddress", vRet[3]);
		} else if (strName == 'courtnamecode') {
            
			aipObj.SetValue("Page1.court_code", "");
			aipObj.SetValue("Page1.court_code", vRet[0]);
			
			aipObj.SetValue("Page1.court_name", "");
			aipObj.SetValue("Page1.court_name", vRet[1]);
			
			aipObj.SetValue("Page1.court_title", "");
			aipObj.SetValue("Page1.court_title", vRet[2]);

			aipObj.SetValue("Page1.courtnamecode", "");
			aipObj.SetValue("Page1.courtnamecode", vRet[1] + vRet[2]);
		} else if (strName == 'cjicourtname'){
			
			aipObj.SetValue("Page1.court_code", "");
			aipObj.SetValue("Page1.court_code", vRet[0]);
			
			aipObj.SetValue("Page1.court_name", "");
			aipObj.SetValue("Page1.court_name", vRet[1]);

			aipObj.SetValue("Page1.court_title", "");
			aipObj.SetValue("Page1.court_title", vRet[2]);

			aipObj.SetValue("Page1.cjicourtname", "");
			aipObj.SetValue("Page1.cjicourtname", vRet[1] + vRet[2]);
		}
	}
}

/**
 * describe : 外来人员进出监计算男、女人数 author : YangZR date : 2015-10-05
 */
function countMaleAndFemaleNum(aipObj, nodeName, nodeValue) {
	var male = 0;
	var female = 0;
	for (var i = 0; i < 10; i++) {
		var key = "wlrygender" + i;
		var value = aipObj.GetValueEx(key, 2, "", 0, "");
		if (value && value == '男') {
			male++;
		} else if (value && value == '女') {
			female++;
		}
	}

	aipObj.SetValue("malenum", "");
	aipObj.SetValue("malenum", male);

	aipObj.SetValue("femalenum", "");
	aipObj.SetValue("femalenum", female);
}

/**
 * describe : 外来人员进出监计算总人数 author : YangZR date : 2015-10-05
 */
function countTotalNum(aipObj, nodeName, nodeValue) {
	var count = 0;
	for (var i = 0; i < 10; i++) {
		var key = "wlryname" + i;
		var value = aipObj.GetValueEx(key, 2, "", 0, "");
		if (value) {
			count++;
		}
	}
	aipObj.SetValue("jinjiannum", "");
	aipObj.SetValue("jinjiannum", count);
}

/**
 * author : YangZR date : 2015-11-02
 */
function getOrgidByOrgname(orgname) {
	var orgid = "";
	$.ajax({
		type : "POST",
		url : basePath + "/org/getOrginfoByOrgnameAndDepartid.json",
		data : {
			orgname : orgname
		},
		cache : false,
		async : false,
		success : function(text) {
			if (text) {
				orgid = text.orgid;
			}
		}
	});
	return orgid;
}
