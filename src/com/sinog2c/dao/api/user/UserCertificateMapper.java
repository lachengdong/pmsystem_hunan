package com.sinog2c.dao.api.user;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.user.UserCertificate;

@Repository
public interface UserCertificateMapper {
	
    public int insert(UserCertificate cert);

    public int insertSelective(UserCertificate cert);
    
    public int insertByMap(Map<String, Object> paraMap);


    public int update(UserCertificate cert);
    
    public int updateSelective(UserCertificate cert);
    
    public int updateByMap(Map<String, Object> paraMap);

    public int delete(String certsn);
    /**
     * 根据证书号获取
     * @param certSN
     * @return
     */
    public List<UserCertificate> getByCertsn(String certsn);
    
    public UserCertificate getById(String id);
    
    /**
     * 根据用户ID获取,原则上只能获取1个
     * @param userid
     * @return
     */
    public List<UserCertificate> listByUserId(Map<String,Object> map);
    
    
    public int countUserCertList(Map<String,Object> map);
	
	public List<Map<String,Object>> getUserCertList(Map<String,Object> map);
	
}