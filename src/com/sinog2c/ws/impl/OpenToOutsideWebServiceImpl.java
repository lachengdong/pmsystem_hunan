package com.sinog2c.ws.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.common.CommonSQLMapper;
import com.sinog2c.dao.api.courtjoint.OpenToOutsideWsMapper;
import com.sinog2c.model.courtjoint.OpenToOutside;
import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.impl.dbmsnew.DbmsNewDataExportServiceImpl;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.DateUtils;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.ws.IPmsysWebService;
import com.sinog2c.ws.OpenToOutsideWebService;



/**
 * 
 *
 */
@Service("OpenToOutsideWebServiceImpl")
@WebService(endpointInterface="com.sinog2c.ws.OpenToOutsideWebService")
public class OpenToOutsideWebServiceImpl implements OpenToOutsideWebService {

	@Autowired
	OpenToOutsideWsMapper openToOutsideMapper;
	
	@Override
	public String sendData(String systemId, String dataPath, String caseId,
			String jsfCorpId, String fsfCorpId, String stageId, String sjbs,String xml) {
		OpenToOutside otos = new OpenToOutside();
		otos.setDataPath(dataPath);
		otos.setFsfCorpId(fsfCorpId);
		otos.setJsfCorpId(jsfCorpId);
		otos.setOpTime(DateUtils.getNow());
		otos.setSjbs(sjbs);
		otos.setStageId(stageId);
		otos.setSystemId(systemId);
		openToOutsideMapper.insert(otos);
   
		return null;
	}

	@Override
	public String getCaseData(String systemId, String corpId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getCaseInfo() {
		// TODO Auto-generated method stub
		return "hello";
	}

	@Override
	public String getDeclareInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrisonOpinion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJabatanPenjaraOpinion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcuratorateOpinion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCrimeTruth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalBaseInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalConsumption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOldAndSick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalSocialRelations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalResume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCriminalExecutedExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssetPerformInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAwardInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPunishmentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSentenceInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPenaltyChangeInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
