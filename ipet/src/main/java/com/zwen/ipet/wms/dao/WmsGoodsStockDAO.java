package com.zwen.ipet.wms.dao;

import com.zwen.ipet.wms.domain.WmsGoodsStockDO;

/**
 * 商品库存管理模块的DAO组件接口
 * @author zwen
 *
 */
public interface WmsGoodsStockDAO {

	/**
	 * 根据商品sku id查询商品库存
	 * @param goodsSkuId 商品sku id
	 * @return 商品库存
	 * @throws Exception
	 */
	WmsGoodsStockDO getBySkuId(Long goodsSkuId) throws Exception;
	
	/**
	 * 新增商品库存
	 * @param goodsStockDO 商品库存DO对象
	 * @throws Exception
	 */
	void save(WmsGoodsStockDO goodsStock) throws Exception;
	
	/**
	 * 更新商品库存
	 * @param goodsStockDO 商品库存DO对象
	 * @throws Exception
	 */
	void update(WmsGoodsStockDO goodsStock) throws Exception;
	
}
