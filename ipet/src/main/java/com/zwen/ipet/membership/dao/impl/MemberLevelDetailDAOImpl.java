package com.zwen.ipet.membership.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.membership.dao.MemberLevelDetailDAO;
import com.zwen.ipet.membership.domain.MemberLevelDetailDO;
import com.zwen.ipet.membership.domain.MemberLevelDetailQuery;
import com.zwen.ipet.membership.mapper.MemberLevelDetailMapper;

/**
 * 会员等级变更明细管理DAO组件
 * @author zwen
 *
 */
@Repository
public class MemberLevelDetailDAOImpl implements MemberLevelDetailDAO {

	/**
	 * 会员等级明细管理mapper组件
	 */
	@Autowired
	private MemberLevelDetailMapper memberLevelDetailMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 分页查询会员等级变更明细 
	 * @param query 查询调价你
	 * @return 会员等级变更明细
	 */
	@Override
	public List<MemberLevelDetailDO> listByPage(MemberLevelDetailQuery query) throws Exception {
		return memberLevelDetailMapper.listByPage(query);
	}
	
	/**
	 * 新增会员等级明细
	 * @param memberLevelDetail 会员等级明细
	 */
	@Override
	public void save(MemberLevelDetailDO memberLevelDetail) throws Exception {
		memberLevelDetail.setGmtCreate(dateProvider.getCurrentTime()); 
		memberLevelDetail.setGmtModified(dateProvider.getCurrentTime()); 
		memberLevelDetailMapper.save(memberLevelDetail); 
	}
	
}
