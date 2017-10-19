package com.sinog2c.service.api.commutationParole;

import com.sinog2c.model.commutationParole.TbxfWideandthinscheme;

public interface WideAndThinschemeService {
	int insertWideAndThinscheme(TbxfWideandthinscheme record);
	int updateWideAndThinscheme(TbxfWideandthinscheme record);
	TbxfWideandthinscheme getWideAndThinschemeBylssid(Integer lssid);
	int delWideAndThinschemeBylssid(Integer lssid);
}
