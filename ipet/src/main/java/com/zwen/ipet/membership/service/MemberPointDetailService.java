package com.zwen.ipet.membership.service;

import java.util.List;

import com.zwen.ipet.membership.domain.MemberPointDetailDTO;
import com.zwen.ipet.membership.domain.MemberPointDetailQuery;

/**
 * 会员积分明细管理service接口
 * @author zwen
 *
 */
public interface MemberPointDetailService {

	/**
	 * 分页查询会员积分变更明细 
	 * @param query 查询调价你
	 * @return 会员积分变更明细
	 * @throws Exception
	 */
	List<MemberPointDetailDTO> listByPage(MemberPointDetailQuery query) throws Exception;
	
}
