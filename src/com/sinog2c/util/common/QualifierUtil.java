package com.sinog2c.util.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;


public class QualifierUtil {
	
	private static final String IF = "if";
	private static final String ELSE = "else";
	private static final String WHEN = "when";
	private static final String REQUIRE = "require";
	private static final String BLANK = " ";
	
//	private static String ifStr = "if col1==0 require col2 > 2 else col2 >1 and col2 < 2";
//	private static String caseStr = "when col1==1 require col2 > 2 when col1==0 require col2 ==1 when col1==0 require col2 ==1 else col2 >1 and col2 < 2";

//	public static boolean parseConditionList(List<String> conditionList, Map<String,Object> paraMap){
//		List<Variable> variables = assembleVariable(paraMap);
//	}
	
//	public static void main(String[] args) {
//		Map<String,Object> paramap = new HashMap<String,Object>();
//		
//		paramap.put("col1", null);
//		paramap.put("col2", null);
//		paramap.put("col22", null);
//		String ifStr = "( ) ( ((if col1 == 0 || col2 == 2 || co222 == 3 require col21 > 2 else col22 >1 && col23 < 2";
//		
//		String result = dealExpression4NullParam(ifStr,paramap);
//		System.out.println(result);
//	}
	
	/**
	 * 如果paramap中的一些key对应的值为空，则将expression中相应的原子表达式设为false
	 * 原子逻辑表达式，如: a > 4 && b +c > 1、  a + b + c + 4 < 5  || d==5 等， 
	 * 			   	 如果paramap中的key为a的值为null，则false && b +c > 1 、 false || d==5
	 * @param expression
	 * @param paramap
	 * @return
	 */
	public static String dealExpression4NullParam(String expression, Map<String,Object> paramap){
		
		if(null != expression){
			String tempExpression = new String(expression);
			tempExpression = tempExpression.replaceAll(IF, "&&").replaceAll(ELSE, "&&").replaceAll(WHEN, "&&").replaceAll(REQUIRE, "&&");
			tempExpression = tempExpression.replaceAll("\\(", "").replaceAll("\\)", "").trim();
//			tempExpression = StringNumberUtil.removeFirstStr(tempExpression, "&&");
			
			//将逻辑表达式分割成原子表达式，主要是根据&&、||进行分割
			List<String> atomExpressList = getAtomExpressList(tempExpression);
			//
			if(null != atomExpressList && atomExpressList.size() > 0){
				expression = setNullParam2False4Expression(expression, atomExpressList, paramap);
			}
			
		}
		return expression;
	}
	
	/**
	 * 将逻辑表达式分割成原子表达式，主要是根据&&、||进行分割
	 * @param expression
	 * @return
	 */
	private static List<String> getAtomExpressList(String expression){
		
		List<String> atomExpressList = new ArrayList<String>();
		String[] splitAnd = expression.split("&&");
		if(null != splitAnd && splitAnd.length > 0){
			for(String tempStr : splitAnd){
				if(StringNumberUtil.notEmpty(tempStr)){
					String[] splitOrArr = tempStr.trim().split("\\|\\|");
					if(null != splitOrArr && splitOrArr.length >0){
						for(String tempStr2 : splitOrArr){
							if(StringNumberUtil.notEmpty(tempStr2)){
								atomExpressList.add(tempStr2.trim());
							}
						}
					}
				}
			}
		}
		
		return atomExpressList;
	}
	
	/**
	 * 如果原子表达式中的参数在Map中对的值为空，则表达式对应的原子表达式字符串设为false
	 * @param expression
	 * @param atomExpressList
	 * @param paramap
	 * @return
	 */
	private static String setNullParam2False4Expression(String expression, List<String> atomExpressList, 
			Map<String,Object> paramap){
		
		for(String atomExpress : atomExpressList){
			String[] keys = atomExpress.split(BLANK);
			for(String key : keys){
				if(null != key){
					key = key.trim();
					if(paramap.containsKey(key)){
						Object value = paramap.get(key);
						if(StringNumberUtil.isEmpty(value)){
							expression = expression.replaceAll(atomExpress, "false");
							break;
						}
					}
				}
			}
			
		}
		
		return expression;
	}
	
	
	
	/**
	 * @author YangZR	2016-08-26
	 * @描述：将表达式转成List，List中的元素为：col1 == 0
	 * 							      col2 > 0 require col3 == 2
	 * 								  col4 > 5 && col6 ==1
	 * 								  ...
	 * @param str
	 * @return
	 */
	private static List<String> parseGrammar2List(String condition){
		List<String> expList = null;
		condition = condition.toLowerCase().trim();
		if(condition.startsWith(IF)){
			expList = parseIFGrammar2List(condition);
		}else if(condition.startsWith(WHEN)){
			expList = parseCASEGrammar2List(condition);
		}else{
			expList = new ArrayList<String>();
			expList.add(condition);
		}
		return expList;
	}
	 
	/**
	 * 将if语句转成List，List中的每个元素为：col1==0 require col2 > 2   或    col2 >1   等表达式
	 * @author YangZR	2016-08-26
	 * @param str
	 * @return
	 */
	private static List<String> parseIFGrammar2List(String condition){
		List<String> ifExpList = new ArrayList<String>();
		condition = StringNumberUtil.removeFirstStr(condition, IF).trim();
		
		if(condition.indexOf(ELSE)>0){
			String elseBeforeStr = StringNumberUtil.subStringFlagEnd(condition,ELSE).trim();
			String elseAfterStr = StringNumberUtil.subStringFlagBegin(condition,ELSE).trim();
			ifExpList.add(elseBeforeStr);
			ifExpList.add(elseAfterStr);
		}else{
			ifExpList.add(condition);
		}
		
		return ifExpList;
	}
	
	/**
	 * 将case when语句转成List，List中的每个元素为：col1==1 require col2 > 2   或    col2 >1   等表达式
	 * @author YangZR	2016-08-26
	 * @param data
	 * @param condition
	 * @return
	 */
	private static List<String> parseCASEGrammar2List(String condition){
		
		List<String> caseExpList = new ArrayList<String>();
		condition = StringNumberUtil.removeFirstStr(condition, WHEN).trim();
		//
		String elseStr = null;
		if(condition.indexOf(ELSE)>0){
			elseStr = StringNumberUtil.subStringFlagBegin(condition,ELSE).trim();
			condition = StringNumberUtil.subStringFlagEnd(condition,ELSE).trim();
		}
		
		String[] whenStrArr = condition.split(WHEN);
		for(int i=0;i<whenStrArr.length;i++){
			if(StringNumberUtil.notEmpty(whenStrArr[i])){
				caseExpList.add(whenStrArr[i].trim());
			}
		}
		if(StringNumberUtil.notEmpty(elseStr)){
			caseExpList.add(elseStr);
		}
		
		return caseExpList;
	}
	
	/**
	 * 解析表达式，返回布尔值：true、false、null
	 * @param condition
	 * @param variables
	 * @return
	 */
	public static Boolean parseGrammar(String condition, List<Variable> variables){
		List<String> expList = parseGrammar2List(condition);
		return parseExpressionsList(expList, variables);
	}
	
	
	
	/**
	 * List中每个元素为一个表达式，每个表达式的结果有3种：true、false、null，表达式exp1如果结果为true或false，返回其结果，
	 * 如果为null，判断下一个表达式exp2...，如果所有表达式结果都为null，返回null
	 * @author YangZR	2016-08-26
	 * @param expList : [exp1,exp2,exp3,exp4...]
	 * 		  variables : 参数
	 * @return
	 */
	private static Boolean parseExpressionsList(List<String> expList, List<Variable> variables){
		Boolean result = null;
		for(int i=0;i<expList.size();i++){
			result = parseWholeExpression(expList.get(i), variables);
			if(null==result){
				continue;
			}else{
				return result;
			}
		}
		
		return result;
	}
	
	
	/**
	 * 将    expression1 require expression2        或         expression3        解析为布尔值。
	 * true require true  => true
	 * true require false => false
	 * false require x    => null (x代表不管其为true还是false)
	 * true               => true
	 * false              => false
	 * 
	 * 
	 * expression1 require expression2
	 * eg： 体重 >= 80kg require 吃饭  <= 2碗 
	 * 	       如果给的数据中，体重超过80kg，要求吃饭小等于2碗，如果吃饭超过2碗，返回false，否则返回true，如果体重小于80kg，返回null，因为不清楚体重小于80kg到底可以吃几碗
	 * 
	 * 
	 * expression3
	 * eg: 吃饭  <= 2碗 
	 *     只要你吃饭超过2碗，返回false，否则返回true
	 * 
	 * 
	 * @author YangZR	2016-08-27
	 * @param variables
	 * @param expressions
	 * @return
	 */
	public static Boolean parseWholeExpression(String expressions, List<Variable> variables){
		String[] expressionList = expressions.split(REQUIRE);
		if(null == expressionList || 0==expressionList.length || expressionList.length > 2){
			throw new RuntimeException("筛查条件的语法有错：" + expressionList==null?"表达式为空":expressionList.toString());
		}
		
		if(1==expressionList.length){
			return parseExpression(expressionList[0],variables);
		}else{
			 boolean expression1Result = parseExpression(expressionList[0],variables);
			 if(expression1Result){
				 return parseExpression(expressionList[1],variables);
			 }else{
				 return null;
			 }
		} 
	}
	
	
	/**
	 * 解析表达式：  ((key1==value1 || key2>=value2) && key3 < value3) || key4 == value4，得到布尔值
	 * expression：表达式
	 * paraMap : 传递给表达式的参数
	 */
	public static boolean parseExpression(String expression, List<Variable> variables){
		
		Object result = ExpressionEvaluator.evaluate(expression, variables);
		try{
			return object2Boolean(result);
		}catch(Exception e){
			throw new RuntimeException("解析表达式："+expression+" 时发生错误！");
		}
	}
	
	private static boolean object2Boolean(Object obj){
		return ((Boolean)obj).booleanValue();
	}
	
	
	/**
	 * 将Map中的数据转成表达式参数的List
	 * @author YangZR	2016-08-27
	 * @param map
	 * @return
	 */
	public static List<Variable> assembleVariable(Map<String,Object> map){
		List<Variable> variables = new ArrayList<Variable>();
		Set<String> keySet = map.keySet();
		for(String key : keySet){
			Object value = map.get(key);
			variables.add(Variable.createVariable(key, value));
		}
		return variables;
	}
	
	/**
	 * 判断是哪一级筛查条件
	 * @author YangZR	2016-08-30
	 * @param setlevel
	 * @return
	 */
	private static String levelDescribe(String setlevel){
		if("1".equals(setlevel)){
			return "一级条件";//筛查
		}else if("2".equals(setlevel)){
			return "二级条件";
		}else if("3".equals(setlevel)){
			return "三级条件";
		}
		return null;
	}
	
	
	public static String getProcess(String expression, String setlevel, Map<String,Object> levelMap, Map<String,Object> crimMap, 
			Map<String,Map<String,Object>> allQualifierItem){
		
		String describe = StringNumberUtil.getStrFromMap("describe", levelMap);
		String qid = StringNumberUtil.getStrFromMap("qid", levelMap);
		String levelProcess = generateProcess(expression, describe, qid, setlevel, crimMap, allQualifierItem);
		
		return levelProcess;
	}
	
	/**
	 * 描述：生成表达式的筛查过程
	 * @author YangZR	2016-08-30
	 * @param expression 表达式
	 * @param describe  表达式的中文描述
	 * @param qid  筛查条件编号
	 * @param setlevel 筛查条件等级
	 * @param crimMap  罪犯初始化数据
	 * @param allQualifierItem 所有筛查元素集合(如：原判刑期、三类犯等)
	 * @return 返回筛查过程
	 */
	public static String generateProcess(String expression, String describe, String qid, String setlevel, Map<String,Object> crimMap, 
			Map<String,Map<String,Object>> allQualifierItem){
		
		StringBuilder processSb = new StringBuilder();
		String[] expressionArr = expression.split(BLANK);
		
		Set<String> set = StringNumberUtil.strArr2Set(expressionArr);
		
		for(String key : set){
			key = key.trim();
			if(allQualifierItem.containsKey(key)){
				Map<String,Object> qualifierItem = allQualifierItem.get(key);
				processSb = organizeItemDescribe(key, crimMap,qualifierItem, processSb);
				processSb.append(BLANK);
			}
		}
		processSb = new StringBuilder(StringNumberUtil.removeLastStr(processSb.toString(), BLANK));
		processSb.append("（");
//		processSb.append("编号为").append("的");
		processSb.append(levelDescribe(setlevel)).append(qid).append("：").append(describe).append("）");
		
		return processSb.toString();
	}
	
	
	
	
	private static StringBuilder organizeItemDescribe(String key, Map<String,Object> crimMap,
			Map<String,Object> qualifierItem, StringBuilder processSb){
		
		String value = StringNumberUtil.getStrFromMap(key, crimMap);
		if(StringNumberUtil.isEmpty(value)){
			value = "值为空";
			processSb.append(qualifierItem.get("name"));
			processSb.append(value);
		}else{
			
			int value_type = Integer.parseInt(StringNumberUtil.getStrFromMap("value_type", qualifierItem));//值类型
			
			if(1==value_type || 2== value_type){//整数类型或浮点数类型类型
				processSb.append(qualifierItem.get("name"));
				processSb.append("=");
				processSb.append(value);
			}else if(3==value_type){//布尔类型
				
				double valueDob = Double.parseDouble(value);
				if(1==valueDob){
					value = "是";
					processSb.append(value);
					processSb.append(qualifierItem.get("name"));
				}else if(0==valueDob){
					value = "不是";
					processSb.append(value);
					processSb.append(qualifierItem.get("name"));
				}else{
					value = "数据有问题";
					processSb.append(qualifierItem.get("name"));
					processSb.append(value);
				}
				
			}else if(4==value_type){//日期类型
				Date date = DateUtils.string2Date(value, "yyyyMMdd");
				value = DateUtils.date2String(date, "yyyy年MM月dd日");
				
				processSb.append(qualifierItem.get("name"));
				processSb.append("=");
				processSb.append(value);
			}else if(5==value_type){//时间区域，其中999600为死缓、999500为无期
				int valueInt = Integer.parseInt(value);
				value = StringNumberUtil.parseYearMonthDay(valueInt);
				processSb.append(qualifierItem.get("name"));
				processSb.append("=");
				processSb.append(value);
				
			}else{//其它类型
				processSb.append(qualifierItem.get("name"));
				processSb.append("=");
				processSb.append(value);
			}
		}
		
		return processSb;
		
	}
	
}
