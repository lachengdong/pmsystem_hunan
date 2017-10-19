package com.sinog2c.util.common;

public class DistrictUtil {
	private static final String[] provinceNameArray={
		"北京","天津","河北","山西","内蒙古","辽宁","吉林","黑龙江","上海","江苏","浙江","安徽","福建","江西","山东",
		"河南","湖北","湖南","广东","广西","海南","重庆","四川","贵州","云南","西藏","陕西","甘肃","青海","宁夏","新疆",
		"香港","澳门","台湾"
	};
	private static final String[] provinceCodeArray={
		"110000","120000","130000","140000","150000","210000","220000","230000",
		"310000","320000","330000","340000","350000","360000","370000","410000","420000",
		"430000","440000","450000","460000","500000","510000","520000","530000","540000",
		"610000","620000","630000","640000","650000","810000","820000","710000"
	};
	private static final String[] provincePinYinArray={
		"beijing","tianjin","hebei","shanxi","neimenggu","liaoning","jilin","heilongjiang"
		,"shanghai","jiangsu","zhejiang","anhui","fujian","jiangxi","shandong","henan",
		"hubei","hunan","guangdong","guangxi","hainan","chongqing","sichuan","guizhou",
		"yunnan","xizang","shanxi","gansu","qinghai","ningxia","xinjiang","xianggang","aomen","taiwan"
	};
	
	public static String getProvincePinYin(String provinceCode){
		int index=0;
		while(!provinceCode.equals(provinceCodeArray[index])){
			index++;
		}
		return provincePinYinArray[index];
	}
	
	public static String getProvinceName(String provinceCode){
		int index=0;
		while(!provinceCode.equals(provinceCodeArray[index])){
			index++;
		}
		return provinceNameArray[index];
	}
	
	
	public static void main(String[] args) {
		String provinceCode="440000";
		System.out.println("省份编号["+provinceCode+"]的省份名称为："+getProvinceName(provinceCode)+",拼音为："+getProvincePinYin(provinceCode));
	}


}
