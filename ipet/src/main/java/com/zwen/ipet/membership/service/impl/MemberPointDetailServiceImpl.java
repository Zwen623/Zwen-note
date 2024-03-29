package com.zwen.ipet.membership.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.common.util.ObjectUtils;
import com.zwen.ipet.membership.dao.MemberPointDetailDAO;
import com.zwen.ipet.membership.domain.MemberPointDetailDTO;
import com.zwen.ipet.membership.domain.MemberPointDetailQuery;
import com.zwen.ipet.membership.service.MemberPointDetailService;

/**
 * 会员积分明细管理service组件
 * @author zwen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberPointDetailServiceImpl implements MemberPointDetailService {

	/**
	 * 会员积分明细管理DAO组件
	 */
	@Autowired
	private MemberPointDetailDAO memberPointDetailDAO;
	
	/**
	 * 分页查询会员积分变更明细 
	 * @param query 查询调价你
	 * @return 会员积分变更明细
	 */
	@Override
	public List<MemberPointDetailDTO> listByPage(
			MemberPointDetailQuery query) throws Exception {
		return ObjectUtils.convertList(memberPointDetailDAO.listByPage(query), 
				MemberPointDetailDTO.class); 
	}
	
}
