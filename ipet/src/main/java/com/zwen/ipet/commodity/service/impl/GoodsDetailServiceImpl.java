package com.zwen.ipet.commodity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.commodity.dao.GoodsDetailDAO;
import com.zwen.ipet.commodity.domain.GoodsDetailDO;
import com.zwen.ipet.commodity.domain.GoodsDetailDTO;
import com.zwen.ipet.commodity.service.GoodsDetailService;
import com.zwen.ipet.common.util.DateProvider;

/**
 * 商品详情管理service组件
 * @author zwen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsDetailServiceImpl implements GoodsDetailService {

	/**
	 * 商品详情管理DAO组件
	 */
	@Autowired
	private GoodsDetailDAO goodsDetailDAO;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 根据商品id查询商品详情
	 * @param goodsId 商品id
	 * @return 商品详情
	 */
	@Override
	public GoodsDetailDTO getByGoodsId(Long goodsId) throws Exception {
		return goodsDetailDAO.getByGoodsId(goodsId).clone(GoodsDetailDTO.class);  
	}
	
	/**
	 * 新增商品详情
	 * @param goodsDetail 商品详情
	 * @return 商品详情id
	 * @throws Exception
	 */
	@Override
	public Long save(GoodsDetailDTO goodsDetail) throws Exception {
		goodsDetail.setGmtCreate(dateProvider.getCurrentTime());  
		goodsDetail.setGmtModified(dateProvider.getCurrentTime()); 
		return goodsDetailDAO.save(goodsDetail.clone(GoodsDetailDO.class));  
	}
	
	/**
	 * 更新商品详情
	 * @param goodsDetail 商品详情
	 * @throws Exception 
	 */
	@Override
	public void update(GoodsDetailDTO goodsDetail) throws Exception {
		goodsDetail.setGmtModified(dateProvider.getCurrentTime()); 
		goodsDetailDAO.update(goodsDetail.clone(GoodsDetailDO.class));  
	}
	
}
