package com.sinog2c.dao.api.courtjoint;

import java.util.List;
import java.util.Map;

public interface OpenToOutsideMapper {

	List<Map> getPiciInfo(Map map);

	List getCourtCaseHandleList(Map<String, Object> map);

	int countCourtCaseHandleList(Map<String, Object> map);

	List getCaseDateInfo(Map<Object, Object> map);

	List getAJXXCaseDateInfo(Map<Object, Object> map);

	List getBQXXCaseDateInfo(Map<Object, Object> map);

	List getJYYJCaseDateInfo(Map<Object, Object> map);

	List getJYJYJCaseDateInfo(Map<Object, Object> map);

	List getFZSSCaseDateInfo(Map<Object, Object> map);

	List getZFJBXXCaseDateInfo(Map<Object, Object> map);

	List getXFBGCaseDateInfo(Map<Object, Object> map);

	List getZFXFQKCaseDateInfo(Map<Object, Object> map);
	
	List getLRBCCaseDateInfo(Map<Object, Object> map);
	List getQKXXCaseDateInfo(Map<Object, Object> map);
	List getSHGXCaseDateInfo(Map<Object, Object> map);
	List getJLXXCaseDateInfo(Map<Object, Object> map);
	List getFXBXCaseDateInfo(Map<Object, Object> map);
	List getCCQKCaseDateInfo(Map<Object, Object> map);
	List getQLQKCaseDateInfo(Map<Object, Object> map);
	List getCFQKCaseDateInfo(Map<Object, Object> map);
	List getYPXXCaseDateInfo(Map<Object, Object> map);

	Map findsome();

	List getyjDataInfo(Map<Object, Object> dataMap);

	List getWritDataInfo(Map<Object, Object> map);

	Integer getCaseDataB(Map<Object, Object> bmap);

	Integer getCaseDataC(Map<Object, Object> cmap);

	Integer getCaseDataD(Map<Object, Object> dmap);

	Integer getCaseDataE(Map<Object, Object> emap);

	Integer getCaseDataF(Map<Object, Object> fmap);

	Integer insertCaseData(Map datamap);

	Integer insertWritData(Map wsmap);

	List getReceiptData(Map<Object, Object> map);

	Map getReceiptDatatest(Map map);
	
	List<Map> getPrisonInfo(Map map);

	List<Map> getCourt(Map map);

	List getOtherWritDataInfo(Map<Object, Object> dataMap);

	List<Map> getCourtP(Map map);
    
}