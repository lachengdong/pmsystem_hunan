package com.sinog2c.util.solution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sinog2c.util.common.StringNumberUtil;

/**
 * 简易词法分析器
 */
public class LexerParser {
	
	// 1. 从String中解析出特定格式的变量
	// 2. 尽量不依赖其他无关的类
	public static boolean DEBUG_MODE = false;
	public static boolean LOWERCASE_MODE = true;
	//
	public static final String _NULL = "null";
	public static final String _NotNULL = "notnull";
	// \\w 匹配数字字母下划线; \\s 匹配空白字符
	//private static String variableRegex_OLD = "@\\{(\\w+)\\}";
	private static String variableRegexString = "@\\{\\s*(\\w+)\\s*(,\\s*\\w+=\\w+\\s*)?\\}";//单个@
	private static String param2AtRegexString = "@@\\{\\s*(\\w+)\\s*(,\\s*\\w+=\\w+\\s*)?\\}"; // 2个 @
	
	private static String ifRegexString = "<if(\\s+\\w+=[\\w,]+\\s*)>([\\s\\S]*?)</if>";
	private static String foreachTagRegex = "<foreach\\s+collection='([\\w,]+)'\\s+split='(,|@|;)'\\s+item='([\\w,]+)'(\\s+open='([^<>'\\s]*)')?(\\s+separator='([\\s\\w]+)')?(\\s+close='([^<>'\\s]*)')?\\s*>([\\s\\S]*?)</foreach>";
	private static String whenTagRegex = "<when\\s+test='(\\s*[\\w,]+\\s*(==|!=)\\s*[\\w,]+\\s*(\\s{1,}(and|or)\\s{1,}([\\w,]+\\s*(==|!=)\\s*[\\w,]+\\s*)){0,})'\\s*>([\\s\\S]*?)</when>";
	private static String ifTagRegex = "<if\\s+test='(\\s*[\\w,]+\\s*(==|!=)\\s*[\\w,]+\\s*(\\s{1,}(and|or)\\s{1,}([\\w,]+\\s*(==|!=)\\s*[\\w,]+\\s*)){0,})'\\s*>([\\s\\S]*?)</if>";
	
	// 正则模式对象
	private static Pattern patternRegex = parseVariableRegex(variableRegexString);
	private static Pattern param2AtRegex = parseVariableRegex(param2AtRegexString);
	private static Pattern ifRegex = setIfRegexString(ifRegexString);
	private static Pattern foreachPattern = parseStrForeachPattern(foreachTagRegex);
	private static Pattern chooseWhenPattern = parseStrChooseWhenPattern();
	private static Pattern whenTagPattern = parseStrWhenTagPattern(whenTagRegex);
	private static Pattern ifTagPattern = parseStrIfTagPattern(ifTagRegex);
	
	public static Pattern parseStrIfTagPattern(String ifTagRegex) {
		if(null == ifTagRegex){
			return null;
		}
		LexerParser.ifTagRegex = ifTagRegex;
		// 标志位,因为是8421形式所以允许多个 flag 相加; 用单竖线逻辑或也可以.
		int flags = Pattern.CASE_INSENSITIVE;
		// 编译出Pattern对象
		Pattern ifTagPattern =  Pattern.compile(ifTagRegex, flags);
		//
		return ifTagPattern;
	}
	
	public static Pattern parseStrWhenTagPattern(String whenTagRegex) {
		if(null == whenTagRegex){
			return null;
		}
		LexerParser.whenTagRegex = whenTagRegex;
		// 标志位,因为是8421形式所以允许多个 flag 相加; 用单竖线逻辑或也可以.
		int flags = Pattern.CASE_INSENSITIVE;
		// 编译出Pattern对象
		Pattern whenTagPattern =  Pattern.compile(whenTagRegex, flags);
		//
		return whenTagPattern;
	}
	
	public static Pattern parseStrChooseWhenPattern() {
		String chooseTagRegexStart = "<\\s*choose\\s*>";
		String chooseTagRegexEnd = "</\\s*choose\\s*>";
		String whenTagRegex = "((\\s*<when\\s+test='(\\s*[\\w,]+\\s*(==|!=)\\s*[\\w,]+\\s*(\\s{1,}(and|or)\\s{1,}([\\w,]+\\s*(==|!=)\\s*[\\w,]+\\s*)){0,})'\\s*>([\\s\\S]*?)</when>\\s*)+)";
		String otherwiseTagRegex = "(\\s*<\\s*otherwise\\s*>([\\s\\S]*?)</\\s*otherwise\\s*>\\s*)?";
		
		String chooseWhenTagRegex = (new StringBuffer()).append(chooseTagRegexStart)
									.append(whenTagRegex).append(otherwiseTagRegex)
									.append(chooseTagRegexEnd).toString();
		
		// 标志位,因为是8421形式所以允许多个 flag 相加; 用单竖线逻辑或也可以.
		int flags = Pattern.CASE_INSENSITIVE;
		// 编译出Pattern对象
		Pattern chooseWhenPattern =  Pattern.compile(chooseWhenTagRegex, flags);
		//
		return chooseWhenPattern;
	}
	
	
	
	public static String getForeachTagRegex() {
		return foreachTagRegex;
	}
	
	public static Pattern parseStrForeachPattern(String foreachTagRegex) {
		if(null == foreachTagRegex){
			return null;
		}
		LexerParser.foreachTagRegex = foreachTagRegex;
		// 标志位,因为是8421形式所以允许多个 flag 相加; 用单竖线逻辑或也可以.
		int flags = Pattern.CASE_INSENSITIVE;
		// 编译出Pattern对象
		Pattern foreachPattern =  Pattern.compile(foreachTagRegex, flags);
		//
		return foreachPattern;
	}
	
	public static String parseTemplate2SQLWithStringParam(String textTemplate, Map<String, Object> params){
		String sql = "";

		if(null == textTemplate || textTemplate.trim().isEmpty()){
			debug("SQLtextTemplate为空: "+textTemplate);
			return sql;
		}
		if(null == params || params.isEmpty() ){
			debug("没有参数,直接返回原模板;params="+params);
			return textTemplate;
		} 
//		if(LOWERCASE_MODE){
//			//debug("将params的Key转换为全小写");
//			params = MapUtil.convertKey2LowerCase(params);
//		}
		//
		List<Map<String,String>> varMapList = parseVariables2Map(textTemplate, patternRegex);
		//
		if(null == varMapList || varMapList.isEmpty()){
			return textTemplate; // 没有变量,直接返回原值
		}
		//
		String tempSQL = textTemplate.trim();
		// 去掉可爱的多余的分号
		while(tempSQL.endsWith(";")){
			tempSQL = tempSQL.substring(0, tempSQL.length()-1);
			tempSQL = tempSQL.trim();
		}
		//
		Iterator<Map<String,String>> iteratorM = varMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<String, String> map = iteratorM.next();
			String name =  map.get("name");
			String wraptext =  map.get("wraptext");
			// 先不处理多个参数的情形
			// 原则上需要分多段来进行拆分替换
			Object value = params.get(name);
			if(null == value){
				value = "";
			}
			//
			String valueStr = String.valueOf(value);
			valueStr = "'" + valueStr + "'";
			
			// 替换所有同一个name的参数
			//tempSQL = tempSQL.replace(wraptext, valueStr);
			tempSQL = replaceAllNoRegex(tempSQL, wraptext, valueStr);
		}
		//
		sql = tempSQL;
		//
		//debug("tempSQL: " + tempSQL);
		//
		return sql;
	}
	
	
	public static boolean isSqlContain2At(String sql){
		Matcher matcher = param2AtRegex.matcher(sql);
		if(matcher.find()){
			return true;
		}
		return false;
	}
	
	public static boolean isSqlContainIfRegex(String sql){
		Matcher matcher = ifRegex.matcher(sql);
		if(matcher.find()){
			return true;
		}
		return false;
	}
	
	public static boolean isSqlContainForeachTag(String sql){
		Matcher matcher = foreachPattern.matcher(sql);
		if(matcher.find()){
			return true;
		}
		return false;
	}
	
	public static boolean isSqlContainChooseWhenTag(String sql){
		Matcher matcher = chooseWhenPattern.matcher(sql);
		if(matcher.find()){
			return true;
		}
		return false;
	}
	
	public static boolean isSqlContainIfTag(String sql){
		Matcher matcher = ifTagPattern.matcher(sql);
		if(matcher.find()){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 将 @@{xxx}这种给替换了 
	 * @param textTemplate
	 * @param paramMap
	 * @return
	 */
	public static String parseTemplateBy2At(String textTemplate, Map<String, Object> paramMap){
		// 
		String sql = "";

		if(null == textTemplate || textTemplate.trim().isEmpty()){
			debug("SQLtextTemplate为空: "+textTemplate);
			return sql;
		}
		if(null == paramMap || paramMap.isEmpty() ){
			debug("没有参数,直接返回原模板;paramMap="+paramMap);
			return textTemplate;
		} 
//		if(LOWERCASE_MODE){
//			//debug("将paramMap的Key转换为全小写");
//			paramMap = MapUtil.convertKey2LowerCase(paramMap);
//		}
		//
		List<Map<String,String>> varMapList = parseVariables2Map(textTemplate, param2AtRegex);
		//
		if(null == varMapList || varMapList.isEmpty()){
			return textTemplate; // 没有变量,直接返回原值
		}
		//
		String tempSQL = textTemplate.trim();
		// 去掉可爱的多余的分号
		while(tempSQL.endsWith(";")){
			tempSQL = tempSQL.substring(0, tempSQL.length()-1);
			tempSQL = tempSQL.trim();
		}
		//
		Iterator<Map<String,String>> iteratorM = varMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<String, String> map = iteratorM.next();
			String name =  map.get("name");
			String wraptext =  map.get("wraptext");
			// 先不处理多个参数的情形
			// 原则上需要分多段来进行拆分替换
			Object value = paramMap.get(name);
			if(null == value){
				value = "";
			}
			//
			String valueStr = String.valueOf(value);
			
			// 替换所有同一个name的参数
			//tempSQL = tempSQL.replace(wraptext, valueStr);
			tempSQL = replaceAllNoRegex(tempSQL, wraptext, valueStr);
		}
		//
		sql = tempSQL;
		//
		//
		return sql;
	}
	
	/**
	 * @author YangZR	2016-03-31
	    select * from tbflow_base tb where 1=1 and
		<foreach collection='flowdraftids' split='@' item='item' open='(' separator=' or ' close=')' >
			tb.flowdraftid=@@{item}
		</foreach>
		将类似以上的sql语句，转换成如下形式：
		select * from tbflow_base tb where 1=1 and ( tb.flowdraftid='111' or tb.flowdraftid='222' ...)
		
		必须属性collection，split，item
		可选属情open，separator，close
		标签体内容要被替换的必须为@@{key}形式
		各属性必须安顺序排列：collection split item	 [open]	[separator]	[close]
	 * @param sqlTemplate
	 * @param paramMap
	 * @return
	 */
	public static String parseSqlTemplate4ForeachTag(String sqlTemplate, Map<String, Object> paramMap){
		String resultTemplate = "";

		if(null == sqlTemplate || sqlTemplate.trim().isEmpty()){
			debug("SQLtextTemplate为空: "+sqlTemplate);
			return resultTemplate;
		}
		if(null == paramMap || paramMap.isEmpty() ){
			debug("没有参数,直接返回原模板;paramMap="+paramMap);
			return sqlTemplate;
		} 
//		if(LOWERCASE_MODE){
//			//debug("将paramMap的Key转换为全小写");
//			paramMap = MapUtil.convertKey2LowerCase(paramMap);
//		}
		
		//解析foreach标签，返回标签中各属性
		List<Map<String,String>> foreachTagMapList = parseForeachTag(sqlTemplate);
		
		//
		if(null == foreachTagMapList || foreachTagMapList.isEmpty()){
			return sqlTemplate; // 没有变量,直接返回原值
		}
		//
		String tempSQL = sqlTemplate.trim();
		//
		Iterator<Map<String,String>> iteratorM = foreachTagMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<String, String> foreachMap = iteratorM.next();
			//拼接foreach字符串
			tempSQL = appendSql4SingleForeach(tempSQL, paramMap, foreachMap);
		}
		//
		resultTemplate = tempSQL;
		//
		return resultTemplate;
		
	}
	
	
	private static String appendSql4SingleForeach(String tempSQL, Map<String,Object>paramMap,Map<String,String> foreachMap){
		
		String foreachtext = foreachMap.get("foreachtext");//整个标签
		String collection = foreachMap.get("collection");
		String split = foreachMap.get("split");
		String item = foreachMap.get("item");
		String open = foreachMap.get("open");
		String separator = foreachMap.get("separator");
		String close = foreachMap.get("close");
		String content = foreachMap.get("content");//foreach标签体
		
		String collectionValue = StringNumberUtil.returnString2(paramMap.get(collection));
		
		List<String> valueList = StringNumberUtil.formatString2List(collectionValue, split);
		String replaceText = "1=1";
		if(null != valueList && valueList.size() > 0){
			
			StringBuffer sb = new StringBuffer();
			
			//regex2At 
			String regex2At = "@@\\{\\s*("+item+")\\s*(,\\s*\\w+=\\w+\\s*)?\\}";
			for(String value : valueList){
				String tempContent = new String(content);
				tempContent = tempContent.replaceAll(regex2At, value);
				sb.append(tempContent).append(separator);
			}
			
			replaceText = sb.toString();
			
			replaceText = StringNumberUtil.removeLastStr(replaceText, separator);
			
			//开头添加open
			if(StringNumberUtil.notEmpty(open)){
				replaceText = open + replaceText;
			}
			
			//结尾添加close
			if(StringNumberUtil.notEmpty(close)){
				replaceText = replaceText + close;
			}
		}
		
		// 替换为内部内容
		tempSQL = replaceAllNoRegex(tempSQL, foreachtext, replaceText);
//		debug("foreach标签解析："+"\n"+"templateSQL: " + tempSQL + "\n" + "foreachtext："+foreachtext + "\n" + "replaceText：" + replaceText);
		
		return tempSQL;
	}
	
	
	/**
	 * @author YangZR	2016-04-01
	 * 解析foreach标签，并封装成Map的数组，每个Map代表一个foreach,Map中包含foreach的各种信息，如下：
	 * foreachtext	--整个foreach签体内容
	 * collection   --要被解析成数组的对像，此对像以split指定的分割符进行分割成数组
	 * split		--分割符
	 * item			--数组中的元素对象的引用
	 * open
	 * separator	--连接字符串
	 * close
	 * content
	 * @param tempSQL
	 * @param paramMap
	 * @param foreachMap
	 * @return
	 */
	private static List<Map<String,String>> parseForeachTag(String sqlTemplate){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		
		Matcher matcher = foreachPattern.matcher(sqlTemplate);
		while(matcher.find()){
			String textFull = matcher.group(0);
			int groupCount = matcher.groupCount();
			if(groupCount < 1){
				continue;
			}
			//
			Map<String,String> foreachTagMap = new HashMap<String,String>();
			foreachTagMap.put("foreachtext", textFull);
			//
			String collection = matcher.group(1);
			foreachTagMap.put("collection", collection);
			//
			String split = matcher.group(2);
			foreachTagMap.put("split", split);
			//
			String item = matcher.group(3);
			foreachTagMap.put("item", item);
			//
			String open = matcher.group(5);
			foreachTagMap.put("open", open);
			//
			String separator = matcher.group(7);
			foreachTagMap.put("separator", separator);
			//
			String close = matcher.group(9);
			foreachTagMap.put("close", close);
			//
			String content = matcher.group(10);
			foreachTagMap.put("content", content);
			
			result.add(foreachTagMap);
		}
		
		return result;
	}
	
	/**
	 * YangZR	2016-04-02
	 * @param sqlTemplate
	 * @param paramMap
	 * @return
	 */
	public static String parseSqlTemplate4ChooseWhenTag(String sqlTemplate, Map<String, Object> paramMap){
		String resultTemplate = "";

		if(null == sqlTemplate || sqlTemplate.trim().isEmpty()){
			debug("SQLtextTemplate为空: "+sqlTemplate);
			return resultTemplate;
		}
		if(null == paramMap || paramMap.isEmpty() ){
			debug("没有参数,直接返回原模板;paramMap="+paramMap);
			return sqlTemplate;
		} 
//		if(LOWERCASE_MODE){
//			//debug("将paramMap的Key转换为全小写");
//			paramMap = MapUtil.convertKey2LowerCase(paramMap);
//		}
		
		//解析foreach标签，返回标签中各属性
		List<Map<String,String>> chooseWhenTagMapList = parseChooseWhenTag(sqlTemplate,paramMap);
		//
		if(null == chooseWhenTagMapList || chooseWhenTagMapList.isEmpty()){
			return sqlTemplate; // 没有变量,直接返回原值
		}
		//
		String tempSQL = sqlTemplate.trim();
		//
		Iterator<Map<String,String>> iteratorM = chooseWhenTagMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<String, String> chooseWhenMap = iteratorM.next();
			String choosewhentext = chooseWhenMap.get("choosewhentext");
			String content = chooseWhenMap.get("content");
			if(StringNumberUtil.notEmpty(content)){
				tempSQL = replaceAllNoRegex(tempSQL, choosewhentext, content);
			}else{
				tempSQL = replaceAllNoRegex(tempSQL, choosewhentext, "");
			}
		}
		//
		resultTemplate = tempSQL;
		//
		return resultTemplate;
		
	}
	
	
	
	private static List<Map<String,String>> parseChooseWhenTag(String sqlTemplate,Map<String, Object> paramMap){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		Matcher matcher = chooseWhenPattern.matcher(sqlTemplate);
		
		while(matcher.find()){
			Map<String,String> whenTagMap = new HashMap<String,String>();
			String choosewhentext = matcher.group(0);
			whenTagMap.put("choosewhentext", choosewhentext);
			
			String whensTag = matcher.group(1);

			Matcher whenMatcher = whenTagPattern.matcher(whensTag);
			boolean whentest = false;
			while(whenMatcher.find()){
				String testcondi = whenMatcher.group(1);
				String whencontent = whenMatcher.group(7);
				boolean whenTest = testConditionBoolean(testcondi,paramMap);
				if(whenTest){
					whenTagMap.put("content", whencontent);
					whentest = true;
					break;
				}
			}
			
			String otherwisetag = matcher.group(10);
			String otherwisecontent = matcher.group(11);
			if(false == whentest && StringNumberUtil.notEmpty(otherwisetag)){
				whenTagMap.put("content", otherwisecontent);
			}
			
			result.add(whenTagMap);
		}
		
		return result;
	}
	
	
	public static String parseSqlTemplate4IfTag(String sqlTemplate, Map<String, Object> paramMap){
		
		String resultTemplate = "";

		if(null == sqlTemplate || sqlTemplate.trim().isEmpty()){
			debug("SQLtextTemplate为空: "+sqlTemplate);
			return resultTemplate;
		}
		if(null == paramMap || paramMap.isEmpty() ){
			debug("没有参数,直接返回原模板;paramMap="+paramMap);
			return sqlTemplate;
		} 
//		if(LOWERCASE_MODE){
//			//debug("将paramMap的Key转换为全小写");
//			paramMap = MapUtil.convertKey2LowerCase(paramMap);
//		}
		
		//解析foreach标签，返回标签中各属性
		List<Map<String,String>> ifTagMapList = parseIfTag(sqlTemplate);
		
		//
		if(null == ifTagMapList || ifTagMapList.isEmpty()){
			return sqlTemplate; // 没有变量,直接返回原值
		}
		//
		String tempSQL = sqlTemplate.trim();
		//
		Iterator<Map<String,String>> iteratorM = ifTagMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<String, String> ifMap = iteratorM.next();
			//拼接if标签字符串
			tempSQL = appendSql4SingleIfTag(tempSQL, paramMap, ifMap);
		}
		//
		resultTemplate = tempSQL;
		//
		return resultTemplate;
		
	}
	
	/**
	 * 解析if标签中的数据
	 * @param sqlTemplate
	 * @return
	 */
	private static List<Map<String,String>> parseIfTag(String sqlTemplate){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		
		Matcher matcher = ifTagPattern.matcher(sqlTemplate);
		
		while(matcher.find()){
			int groupCount = matcher.groupCount();
			String textFull = matcher.group(0);
			
			if(groupCount < 1){
				continue;
			}
			//
			Map<String,String> ifTabMap = new HashMap<String,String>();
			ifTabMap.put("iftext", textFull);
			//
			String testcondi = matcher.group(1);
			ifTabMap.put("testcondi", testcondi);
			//
			String content = matcher.group(7);
			ifTabMap.put("content", content);
			result.add(ifTabMap);
		}
		
		return result;
		
	}
	
	
	private static String appendSql4SingleIfTag(String tempSQL, Map<String,Object> paramMap, Map<String,String> ifMap){
		
		String iftext =  ifMap.get("iftext");
		String testcondi = ifMap.get("testcondi");
		String content =  ifMap.get("content");
		// 判断test中的条件是否为真
		boolean ifTest = testConditionBoolean(testcondi,paramMap);
		//
		if(ifTest){
			// 替换为内部内容
			tempSQL = replaceAllNoRegex(tempSQL, iftext, content);
		} else {
			//替换为空
			tempSQL = replaceAllNoRegex(tempSQL, iftext, "");
		}
		return tempSQL;
		
	}
	
	/**
	 * @author YangZR 2016-04-01
	 * 解析原理：
	 * testcondi = 'exp1 and exp2 or exp3 or exp4 and exp5 or exp6'
	 * 将testcondi 以or分割，分解成数组[exp1 and exp2, exp3, exp4 and exp5, exp6]，只要数组中任一元素为true，则test的结果为true
	 * 循环遍历数组，判断每个元素是否为真
	 * 元素中如果是and的表达式，则以and分割，分解成数组，如exp1 and exp2分解成[exp1,exp2]，必须数组中全部元素为true，则and表达式才为true
	 * 元素不是and的表达式，也以and分割，分解成数组，此是数组中只有一个元素
	 * 
	 * @param testcondi
	 * @param paramMap
	 * @return
	 */
	private static boolean testConditionBoolean(String testcondi,Map<String,Object> paramMap){
		boolean ifTest = false;
		String[] orCondiArr = testcondi.split(" or "); //如：testcondi="c1==t0 or c21==t1 and c22!=t2 or c3=t3"，先以or作为分隔
		//or分隔的各个条件，只要有一个条件为true，则表达式为true
		for(String orCondi : orCondiArr){
			orCondi = orCondi.trim();
			String[] andCondiArr = orCondi.split(" and ");//每一个条件以and分隔成and数组，必须整个数组的值全为true，结果才为true
			boolean[] andBooleanArr = new boolean[andCondiArr.length];
			int index = 0;
			for(String andCondi : andCondiArr){
				andCondi = andCondi.trim();
				boolean condi = logicExpress(andCondi,paramMap);
				andBooleanArr[index] = condi;
				index ++;
			}
			
			//expr1 and expr2 and expr3，每个expr必须全为true，整个and表达式才为true
			boolean andCondiBoolean = true;
			for(boolean andBoolean : andBooleanArr){
				andCondiBoolean = andCondiBoolean && andBoolean;
			}
			
			//只要or两边的条件，有一个满足true,整个or表达式为true
			if(andCondiBoolean){
				ifTest = true;
				break;
			}
			
		}
		
		return ifTest;
	}
	
	
	/**
	 * @author YangZR	2016-04-01
	 * 关条表达式： == !=   关键字： null notnull
	 * key==null或key!=notnull	 表式map.get(key)为空
	 * key==notnull或key!=null 	表式map.get(key)为非空
	 * key==value或key!=value	表式map.get(key)与value值比较，
	 * @param andCondi
	 * @param paramMap
	 * @return
	 */
	private static boolean logicExpress(String andCondi,Map<String,Object>  paramMap){
		String[] equalExpress = andCondi.split("==");
		if(null != equalExpress && equalExpress.length == 2){
			String key = equalExpress[0].trim();
			String compareObj = equalExpress[1].trim();
			String value = paramMap.get(key)==null?"":paramMap.get(key).toString();
			if(_NULL.equals(compareObj)){
				if(StringNumberUtil.isEmpty(value)){
					return true;
				}
			}else if(_NotNULL.equals(compareObj)){
				if(StringNumberUtil.notEmpty(value)){
					return true;
				}
			}else if(compareObj.equals(value)){
				return true;
			}
		}
		
		String[] notEqualExpress = andCondi.split("!=");
		if(null != notEqualExpress && notEqualExpress.length == 2){
			String key = notEqualExpress[0].trim();
			String compareObj = notEqualExpress[1].trim();
			String value = paramMap.get(key)==null?"":paramMap.get(key).toString();
			
			if(_NULL.equals(compareObj)){
				if(StringNumberUtil.notEmpty(value)){
					return true;
				}
			}else if(_NotNULL.equals(compareObj)){
				if(StringNumberUtil.isEmpty(value)){
					return true;
				}
			}else if(! compareObj.equals(value)){
				return true;
			}
			
		}
		
		return false;
		
	}
	
	
	/**
	 * 
	 * @param textTemplate
	 * @param paramMap
	 * @return
	 */
	public static String parseTemplate2TemplateByIf(String textTemplate, Map<String, Object> paramMap){
		String resultTemplate = "";

		if(null == textTemplate || textTemplate.trim().isEmpty()){
			debug("SQLtextTemplate为空: "+textTemplate);
			return resultTemplate;
		}
		if(null == paramMap || paramMap.isEmpty() ){
			debug("没有参数,直接返回原模板;paramMap="+paramMap);
			return textTemplate;
		} 
//		if(LOWERCASE_MODE){
//			//debug("将paramMap的Key转换为全小写");
//			paramMap = MapUtil.convertKey2LowerCase(paramMap);
//		}
		//
		List<Map<String,String>> ifMapList = parseIf2Map(textTemplate);
		//
		if(null == ifMapList || ifMapList.isEmpty()){
			return textTemplate; // 没有变量,直接返回原值
		}
		//
		String tempSQL = textTemplate.trim();
		// 去掉可爱的多余的分号
		while(tempSQL.trim().endsWith(";")){
			tempSQL = tempSQL.substring(0, tempSQL.length()-1);
			tempSQL = tempSQL.trim();
		}
		//
		Iterator<Map<String,String>> iteratorM = ifMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<String, String> map = iteratorM.next();
			String notnull =  map.get("notnull"); // notnull 部分
			String ifwraptext =  map.get("ifwraptext");
			String content =  map.get("content");
			// 是否为空
			boolean isNullOrEmpty = false;
			// 原则上需要分多段来进行拆分替换
			if(null == notnull || notnull.trim().isEmpty()){
				continue;
			} else {
				// 以逗号拆分
				// 处理多个参数的情形
				String[] notnullKeys = notnull.split(",");
				if(null == notnullKeys){
					continue;
				}
				for (int i = 0; i < notnullKeys.length; i++) {
					String notnullKey = notnullKeys[i];
					if(null == notnullKey){
						continue;
					} else {
						notnullKey = notnullKey.trim();
					}
					//
					Object notnullValue = paramMap.get(notnullKey);
					if(null == notnullValue || notnullValue.toString().trim().isEmpty()){
						isNullOrEmpty = true; // 有空值
					}
				}

			}
			//
			if(isNullOrEmpty){
				// 替换为空
				tempSQL = replaceAllNoRegex(tempSQL, ifwraptext, "");
			} else {
				// 替换为内部内容
				tempSQL = replaceAllNoRegex(tempSQL, ifwraptext, content);
			}
		}
		//
		resultTemplate = tempSQL;
		//
		//debug("tempSQL: " + tempSQL);
		//
		return resultTemplate;
	}
	
	private static String replaceAllNoRegex(String tempSQL, String wraptext,String valueStr){
		//
		String tempSQLStr = tempSQL;
		// 只要还有,就继续替换
		while (tempSQLStr.indexOf(wraptext) > -1) {
			tempSQLStr = tempSQLStr.replace(wraptext, valueStr);
		}
		//
		return tempSQLStr;
	}
	

	/**
	 * 解析IF V0.1版本,返回IF组成Map的List
	 * @param textTemplate
	 * @return 每个Map对象含有: notnull, ifwraptext, content 3个KEY,(变量名,需要替换的包装 text)
	 */
	public static List<Map<String,String>> parseIf2Map(String textTemplate){
		//
		List<Map<String,String>> ifMapList = new ArrayList<Map<String,String>>();
		if (null == textTemplate || textTemplate.trim().isEmpty()) {
			try {
				throw new LexerException("解析的IF文本模板为空!");
			} catch (Exception e) {
				e.printStackTrace();
				return ifMapList;
			}
		}
		//
		//debug("开始解析文本模板: " + textTemplate);
		//
		Matcher matcher = ifRegex.matcher(textTemplate);
		//
		while (matcher.find()) {
			int groupCount = matcher.groupCount();
			//debug("matcher.find() 查找找到一个匹配,分组数量 groupCount=" + groupCount);
			//
			String textFull = matcher.group(0);
			//debug("完整的匹配ifwraptext(分组0):\n" + textFull);
			//
			if(groupCount < 1){
				//debug("舍弃;原因: groupCount="+groupCount);
				continue;
			}
			//
			String notnullKeyValue = matcher.group(1);
			//debug("匹配得到的notnullKeyValue(分组1):\t" + notnullKeyValue);
			if(null == notnullKeyValue){
				//debug("舍弃;原因: notnullKeyValue="+notnullKeyValue);
				continue;
			} else {
				notnullKeyValue = notnullKeyValue.trim();
				//
			}
			if(LOWERCASE_MODE){
				notnullKeyValue = notnullKeyValue.toLowerCase();
			}
			String[] kvArray = notnullKeyValue.split("=");
			if(2 == kvArray.length){
				//
				String notnullAttr = kvArray[0];
				String notnullKey = kvArray[1];
				//
				if("notnull".equalsIgnoreCase(notnullAttr.trim())){
					notnullKeyValue = notnullKey.trim();
				}else {
					notnullKeyValue = "";
				}
			} else {
				notnullKeyValue = "";
			}
			//
			String content = matcher.group(2);
			//debug("匹配得到的content(分组2):\t" + content);
			//
			Map<String,String> map = new HashMap<String, String>();
			map.put("notnull", notnullKeyValue);
			map.put("content", content);
			map.put("ifwraptext", textFull);
			ifMapList.add(map);
			//
		}
		//
		return ifMapList;
	}
	/**
	 * 解析变量 V0.1版本,返回变量名组成的List
	 *
	 * @param textTemplate
	 * @return
	 */
	public static List<String> parseVariables2String(String textTemplate){
		//
		List<String> varList = new ArrayList<String>();
		List<Map<String,String>> varMapList = parseVariables2Map(textTemplate, patternRegex);
		//
		if(null == varMapList || varMapList.isEmpty()){
			return varList;
		}
		//
		Iterator<Map<String,String>> iteratorM = varMapList.iterator();
		while (iteratorM.hasNext()) {
			Map<String, String> map = iteratorM.next();
			String name =  map.get("name");
			varList.add(name);
		}
		//
		return varList;
	}
	
	
	/**
	 * 解析变量 V0.1版本,返回变量名组成Map的List
	 * @param textTemplate
	 * @return 每个Map对象含有: name, wraptext 2个KEY,(变量名,需要替换的包装 text)
	 */
	public static List<Map<String,String>> parseVariables2Map(String textTemplate, Pattern regex){
		//
		List<Map<String,String>> varMapList = new ArrayList<Map<String,String>>();
		if (null == textTemplate || textTemplate.trim().isEmpty()) {
			try {
				throw new LexerException("解析的文本模板为空!");
			} catch (Exception e) {
				e.printStackTrace();
				return varMapList;
			}
		}
		if(null == regex){
			regex = patternRegex; // 防止空
		}
		//
		//debug("开始解析文本模板: " + textTemplate);
		//
		Matcher matcher = regex.matcher(textTemplate);
		//
		while (matcher.find()) {
			int groupCount = matcher.groupCount();
			//debug("matcher.find() 查找找到一个匹配,分组数量 groupCount=" + groupCount);
			//
			String textFull = matcher.group(0);
			//debug("完整的匹配wraptext(分组0):\t" + textFull);
			//
			if(groupCount < 1){
				continue;
			}
			//
			String textvarName = matcher.group(1);
			//debug("匹配得到的变量name(分组1):\t" + textvarName);
			if(null == textvarName){
				continue;
			} else {
				textvarName = textvarName.trim();
			}
			if(LOWERCASE_MODE){
				textvarName = textvarName.toLowerCase();
			}
			//
			Map<String,String> map = new HashMap<String, String>();
			map.put("name", textvarName);
			map.put("wraptext", textFull);
			varMapList.add(map);
			//
		}
		//
		return varMapList;
	}

	public static List<VariableToken> parseVariables2Token(String textTemplate){
		//
		List<VariableToken> varTokenList = new ArrayList<VariableToken>();
		if (null == textTemplate || textTemplate.trim().isEmpty()) {
			try {
				throw new LexerException("解析的文本模板为空!");
			} catch (Exception e) {
				e.printStackTrace();
				return varTokenList;
			}
		}
		//
		//debug("开始解析文本模板: " + textTemplate);
		//
		Matcher matcher = patternRegex.matcher(textTemplate);
		int order = 1;
		//
		while (matcher.find()) {
			int groupCount = matcher.groupCount();
			//debug("matcher.find() 查找找到一个匹配,分组数量 groupCount=" + groupCount);
			//
			String textFull = matcher.group(0);
			//debug("完整的匹配wraptext(分组0):\t" + textFull);
			//
			if(groupCount < 1){
				continue;
			}
			//
			String textvarName = matcher.group(1);
			//debug("匹配得到的变量name(分组1):\t" + textvarName);
			if(null == textvarName){
				continue;
			} else {
				textvarName = textvarName.trim();
			}
			if(LOWERCASE_MODE){
				textvarName = textvarName.toLowerCase();
			}
			//
			// 类型
			String typeStr = null;
			if(groupCount >= 2){
				typeStr = matcher.group(2);
				//debug("匹配得到的类型(分组2):\t" + typeStr);
			}
			String jdbcType = parseJdbcType(typeStr);
			
			VariableToken token = new VariableToken();
			token.setName(textvarName);
			token.setWraptext(textFull);
			token.setOrder(order++);
			token.setJdbcType(jdbcType);
			//
			varTokenList.add(token);
		}
		//
		return varTokenList;
	}
	
	private static String parseJdbcType(String typeStr){
		// 默认类型 string
		String jdbcType = VariableToken.TYPE_STRING;
		
		if(null == typeStr){
			return jdbcType;
		} else {
			// 替换逗号
			typeStr = typeStr.replace(",", "");
			// 转为小写
			typeStr = typeStr.trim().toLowerCase();
		}
		//
		String[] typeArray = typeStr.split("=");
		if(null == typeArray){
			return jdbcType;
		}
		int typeLen = typeArray.length;
		if(1 == typeLen){
			jdbcType = typeArray[0];
		} else if(2 == typeLen){
			jdbcType = typeArray[1];
		}
		//
		if(null != jdbcType){
			jdbcType = jdbcType.trim().toLowerCase();
		}
		return jdbcType;
	}

	public static String getVariableRegex() {
		return variableRegexString;
	}
	public static Pattern parseVariableRegex(String variableRegex) {
		if(null == variableRegex){
			return null;
		}
		//LexerParser.variableRegexString = variableRegex;
		//patternRegex = Pattern.compile(variableRegex);
		// 标志位,因为是8421形式所以允许多个 flag 相加; 用单竖线逻辑或也可以.
		int flags = Pattern.CASE_INSENSITIVE;
		// 编译出Pattern对象
		Pattern _patternRegex = Pattern.compile(variableRegex, flags);
		//patternRegex = _patternRegex;
		//
		return _patternRegex;
	}
	
	public static String getIfRegex() {
		return ifRegexString;
	}
	
	public static Pattern setIfRegexString(String ifRegexString) {
		if(null == ifRegexString){
			return null;
		}
		LexerParser.ifRegexString = ifRegexString;
		// 标志位,因为是8421形式所以允许多个 flag 相加; 用单竖线逻辑或也可以.
		int flags = Pattern.CASE_INSENSITIVE;
		// 编译出Pattern对象
		Pattern _ifRegex =  Pattern.compile(ifRegexString, flags);
		ifRegex = _ifRegex;
		//
		return _ifRegex;
	}
	
	private static SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
	private static String getTimeStampStr() {
		Date now = new Date();
		return getTimeStampStr(now);
	}
	private static String getTimeStampStr(Date date) {
		if(null == date){
			date = new Date();
		}
		return TimeStampFormat.format(date);
	}
	public static void debug(String info){
		if(DEBUG_MODE){
			log(info);
		}
	}
	//
	public static void log(String info){
		System.out.println(getTimeStampStr()+":\t" +info);
	}
}
