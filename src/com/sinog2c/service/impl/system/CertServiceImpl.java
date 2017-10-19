package com.sinog2c.service.impl.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.user.UserCertificateMapper;
import com.sinog2c.model.user.UserCertificate;
import com.sinog2c.service.api.system.CertService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Service("certService")
public class CertServiceImpl extends ServiceImplBase implements CertService{

	@Autowired
	private UserCertificateMapper userCertificateMapper;
	
	@Override
	public int countUserCertList(Map<String, Object> map) {
		return userCertificateMapper.countUserCertList(map);
	}

	@Override
	public List<Map<String, Object>> getUserCertList(Map<String, Object> map) {
		List<Map<String,Object>> result = userCertificateMapper.getUserCertList(map);
		result = MapUtil.convertKeyList2LowerCase(result);
		return result;
	}

	@Override
	public int saveUserCert(UserCertificate cert, String type) {
		
		int row = -1;
		if(null==cert){
			return row;
		}
		
		if("new".equals(type)){
			cert.setId(StringNumberUtil.getUUID());
			row = userCertificateMapper.insertSelective(cert);
		}else if("edit".equals(type)){
			row = userCertificateMapper.updateSelective(cert);
		}
		
		return row;
	}
	
	@Override
	public int deleteUserCert(String id){
		int row = userCertificateMapper.delete(id);
		return row;
	}
	
	@Override
	public UserCertificate getCertById(String id){
		return userCertificateMapper.getById(id);
	}
	
	
	
	@Override
	public int isUserSignatureKey(String userid, String certsn){
		int result = 1;
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("userid", userid);
		List<UserCertificate> certList = userCertificateMapper.listByUserId(paramap);
		
		if(null != certList && certList.size() > 0){
			for(UserCertificate cert : certList){
				if(cert.getCertsn().equals(certsn)){
					return result;
				}
			}
			result = -1;
		}
		
		return result;
	}

}
