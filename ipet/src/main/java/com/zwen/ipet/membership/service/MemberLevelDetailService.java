package com.zwen.ipet.membership.service;

import java.util.List;

import com.zwen.ipet.membership.domain.MemberLevelDetailDTO;
import com.zwen.ipet.membership.domain.MemberLevelDetailQuery;

/**
 * 会员等级明细管理service接口
 * @author zwen
 *
 */
public interface MemberLevelDetailService {

	/**
	 * 分页查询会员等级变更明细 
	 * @param query 查询调价你
	 * @return 会员等级变更明细
	 * @throws Exception
	 */
	List<MemberLevelDetailDTO> listByPage(MemberLevelDetailQuery query) throws Exception;
	
}
