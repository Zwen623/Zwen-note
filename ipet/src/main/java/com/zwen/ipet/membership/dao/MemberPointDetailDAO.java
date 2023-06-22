package com.zwen.ipet.membership.dao;

import java.util.List;

import com.zwen.ipet.membership.domain.MemberPointDetailDO;
import com.zwen.ipet.membership.domain.MemberPointDetailQuery;

/**
 * 会员积分变更明细管理DAO接口
 * @author zwen
 *
 */
public interface MemberPointDetailDAO {

	/**
	 * 分页查询会员积分变更明细 
	 * @param query 查询调价你
	 * @return 会员积分变更明细
	 * @throws Exception
	 */
	List<MemberPointDetailDO> listByPage(MemberPointDetailQuery query) throws Exception;
	
	/**
	 * 新增会员积分明细
	 * @param memberPointDetail 会员积分明细
	 * @throws Exception
	 */
	void save(MemberPointDetailDO memberPointDetail) throws Exception;
	
}
