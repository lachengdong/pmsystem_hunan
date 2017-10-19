package com.sinog2c.dao.api.prisoner;

import com.sinog2c.model.prisoner.CrimUserInfo;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;

public interface CrimUserInfoMapper {
          
	/**
	 * 保存一个新登录的罪犯生成该罪犯的用户信息
	 * @param crimUserInfo
	 * @return
	 */
	  int saveCrimUserInfo(CrimUserInfo crimUserInfo);
	  /**
	   * 查询一个罪犯用户信息，并放到session中
	   * @param crimid
	   * @return
	   */
	  CrimUserInfo selectByPrimaryKey(String crimid);
	
}
