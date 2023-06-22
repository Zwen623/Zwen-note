package com.zwen.ipet.commodity.dao;

import com.zwen.ipet.commodity.domain.GoodsDetailDO;

/**
 * 商品详情管理DAO组件
 * @author zwen
 *
 */
public interface GoodsDetailDAO {

	/**
	 * 根据商品id查询商品详情
	 * @param goodsId 商品id
	 * @return 商品详情
	 */
	GoodsDetailDO getByGoodsId(Long goodsId);
	
	/**
	 * 新增商品详情
	 * @param goodsDetail 商品详情
	 * @return 商品详情id
	 */
	Long save(GoodsDetailDO goodsDetail);
	
	/**
	 * 更新商品详情
	 * @param goodsDetail 商品详情
	 */
	void update(GoodsDetailDO goodsDetail);
	
	/**
	 * 删除商品详情
	 * @param id 商品详情id
	 */ 
	void remove(Long id);
	
}
