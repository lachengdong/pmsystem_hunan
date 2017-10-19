package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.user.UserCertificate;

public interface CertService {
	
	int countUserCertList(Map<String,Object> map);
	
	List<Map<String,Object>> getUserCertList(Map<String,Object> map);
	
	public int saveUserCert(UserCertificate cert, String type);
	
	public int deleteUserCert(String id);
	
	public UserCertificate getCertById(String id);
	
	public int isUserSignatureKey(String userid, String certsn);

}
